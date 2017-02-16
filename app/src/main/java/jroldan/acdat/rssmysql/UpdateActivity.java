package jroldan.acdat.rssmysql;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String URL_API = "site";
    public static final int OK = 1;
    @BindView(R.id.idSite) TextView idSite;
    @BindView(R.id.nameSite) EditText nameSite;
    @BindView(R.id.linkSite) EditText linkSite;
    @BindView(R.id.emailSite) EditText emailSite;
    @BindView(R.id.accept)
    Button accept;
    @BindView(R.id.cancel) Button cancel;
    //EditText nameSite, linkSite, emailSite;
    //TextView idSite;
    //Button accept, cancel;
    Site s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        //idSite = (TextView) findViewById(R.id.idSite);
        //nameSite = (EditText) findViewById(R.id.nameSite);
        //linkSite = (EditText) findViewById(R.id.linkSite);
        //emailSite = (EditText) findViewById(R.id.emailSite);
        //accept = (Button) findViewById(R.id.accept);
        //cancel = (Button) findViewById(R.id.cancel);
        accept.setOnClickListener(this);
        cancel.setOnClickListener(this);
        Intent i = getIntent();
        s = (Site) i.getSerializableExtra("site");
        idSite.setText(String.valueOf(s.getId()));
        nameSite.setText(s.getName());
        linkSite.setText(s.getLink());
        emailSite.setText(s.getEmail());
    }
    @Override
    public void
    onClick(View v) {
        String n, l, e;
        if (v == accept) {
            n = nameSite.getText().toString();
            l = linkSite.getText().toString();
            e = emailSite.getText().toString();
            if (n.isEmpty() || l.isEmpty())
                Toast.makeText(this, "Please, fill the name and the link", Toast.LENGTH_SHORT).show();
            else
            {
                //s = new Site(id, n, l , e);
                s.setName(n);
                s.setLink(l);
                s.setEmail(e);
                connection(s);
            }
        }
        if (v == cancel)
            finish();
    }
    private void connection(final Site s) {
        final ProgressDialog progreso = new ProgressDialog(this);
        Gson gson = new Gson();
        RequestParams params = new RequestParams();
        //params.put("site", gson.toJson(s));
        params.put("name", s.getName());
        params.put("link", s.getLink());
        params.put("email", s.getEmail());
        RestClient.put(URL_API + "/" + s.getId(), params, new JsonHttpResponseHandler() {
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
                        message = "Modified site ok";
                        //site = gson.fromJson(String.valueOf(result.getSites()), Site.class);
                        Intent i = new Intent();
                        Bundle mBundle = new Bundle();
                        mBundle.putInt("id", s.getId());
                        mBundle.putString("name", s.getName());
                        mBundle.putString("link", s.getLink());
                        mBundle.putString("email", s.getEmail());
                        i.putExtras(mBundle);
                        setResult(OK, i);
                        finish();
                    }
                    else
                        message = "Error modifying the site:\n" + result.getMessage();
                else
                    message = "Null data";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            // onFailure

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progreso.dismiss();
                Toast.makeText(UpdateActivity.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progreso.dismiss();
                Toast.makeText(UpdateActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}