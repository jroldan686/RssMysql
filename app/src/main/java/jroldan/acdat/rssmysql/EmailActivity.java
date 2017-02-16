package jroldan.acdat.rssmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class EmailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_API = "email" ;
    public static final String MAIL = MainActivity.MAIL;
    public static final int OK = 1;
    @BindView(R.id.from) EditText from;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.to) EditText to;
    @BindView(R.id.subject) EditText subject;
    @BindView(R.id.message) EditText message;
    @BindView(R.id.accept) Button accept;
    @BindView(R.id.cancel) Button cancel;

    //EditText from, password, to, subject, message;
    //Button accept, cancel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        ButterKnife.bind(this);
        //from = (EditText) findViewById(R.id.from);
        //password = (EditText) findViewById(R.id.password);
        //to = (EditText) findViewById(R.id.to);
        //subject = (EditText) findViewById(R.id.subject);
        //message = (EditText) findViewById(R.id.message);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Intent i = getIntent();
        to.setText(i.getStringExtra(MAIL));
    }

    @Override
    public void onClick(View v) {
        if (v == accept) {
            String f = from.getText().toString();
            String p = password.getText().toString();
            String t = to.getText().toString();
            String s = subject.getText().toString();
            String m = message.getText().toString();
            if (f.isEmpty() || p.isEmpty() || t.isEmpty()) {
                Toast.makeText(this, "Please, fill emails and password", Toast.LENGTH_SHORT).show();
            } else {
                Email email = new Email(f, p, t, s, m);
                connection(email);
            }
        }
        if (v == cancel) {
            finish();
        }
    }

    private void connection(Email e) {
        final ProgressDialog progreso = new ProgressDialog(this);
        Gson gson = new Gson();
        RequestParams params = new RequestParams();
        //params.put(MAIL, gson.toJson(e));
        params.put("from", e.getFrom());
        params.put("password", e.getPassword());
        params.put("to", e.getTo());
        params.put("subject", e.getSubject());
        params.put("message", e.getMessage());
        RestClient.post(URL_API, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Connecting . . .");
                progreso.setCancelable(false);
                progreso.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Handle resulting parsed JSON response here
                progreso.dismiss();
                Result result;
                Gson gson = new Gson();
                String message;
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null)
                    if (result.getCode()) {
                        message = "Email sent ok";
                        Intent i = new Intent();
                        setResult(OK, i);
                        finish();
                    } else
                        message = "Error sending the email:\n" + result.getMessage();
                else
                    message = "Null data";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            // on Failure

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progreso.dismiss();
                Toast.makeText(EmailActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progreso.dismiss();
                Toast.makeText(EmailActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}