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

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL = "site";
    public static final int OK = 1;
    @BindView(R.id.nameSite) EditText nameSite;
    @BindView(R.id.linkSite) EditText linkSite;
    @BindView(R.id.emailSite) EditText emailSite;
    @BindView(R.id.accept) Button accept;
    @BindView(R.id.cancel) Button cancel;
    //EditText nameSite, linkSite, emailSite;
    //Button accept, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        //nameSite = (EditText) findViewById(R.id.nameSite);
        //linkSite = (EditText) findViewById(R.id.linkSite);
        //emailSite = (EditText) findViewById(R.id.emailSite);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String n, l, e;
        Site s;
        if (v == accept) {
            n = nameSite.getText().toString();
            l = linkSite.getText().toString();
            e = emailSite.getText().toString();
            if (n.isEmpty() || l.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else
            {
                s = new Site(n, l , e);
                connection(s);
            }
        }
        if (v == cancel)
            finish();
    }
    private void connection(final Site s) {
        final ProgressDialog progreso = new ProgressDialog(this);
        RequestParams params = new RequestParams();
        //params.put("site", gson.toJson(s));
        params.put("name", s.getName());
        params.put("link", s.getLink());
        params.put("email", s.getEmail());
        RestClient.post(URL, params, new JsonHttpResponseHandler() {
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
                //Site site;
                result = gson.fromJson(String.valueOf(response), Result.class);
                if (result != null)
                    if (result.getCode()) {
                        message = "Added site ok";
                        //site = gson.fromJson(String.valueOf(result.getSites()), Site.class);
                        Intent i = new Intent();
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("id", result.getLast());
                        mBundle.putString("name", s.getName());
                        mBundle.putString("link", s.getLink());
                        mBundle.putString("email", s.getEmail());
                        i.putExtras(mBundle);
                        setResult(OK, i);
                        finish();
                    }
                    else
                        message = "Error adding the site:\n"
                        + result.getMessage();
                else
                    message = "Null data";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            //onFailure

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progreso.dismiss();
                Toast.makeText(AddActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progreso.dismiss();
                Toast.makeText(AddActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
