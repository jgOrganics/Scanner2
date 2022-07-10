package com.example.scanner2.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.scanner2.AboutUs.AboutUs;
import com.example.scanner2.MainActivity;
import com.example.scanner2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* renamed from: com.example.scanner.Profile.Profile */
public class Profile extends AppCompatActivity {
    public static final String EMAIL_KEY = "email_key";
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String SHARED_PREFS = "shared_prefs";
    Context context;
    TextView dialog_language;
    String email;
    int lang_selected;
    Button logout;
    TextView myname;
    ImageView picture;
    ProgressDialog progressDialog;
    Resources resources;
    SharedPreferences sharedpreferences;
    RelativeLayout show_lan_dialog;
    TextView uaddress;
    TextView uemail;
    TextView umobile;
    Button update;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Locale locale = new Locale("cn");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplication().getResources().updateConfiguration(config, getApplication().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_profile);
        this.myname = (TextView) findViewById(R.id.myname);
        this.umobile = (TextView) findViewById(R.id.uphone);
        this.uemail = (TextView) findViewById(R.id.uemail);
        this.uaddress = (TextView) findViewById(R.id.uaddress);
        this.logout = (Button) findViewById(R.id.logout);
        this.update = (Button) findViewById(R.id.update);
        this.dialog_language = (TextView) findViewById(R.id.dialog_language);
        this.sharedpreferences = getSharedPreferences("shared_prefs", 0);
        this.logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Profile.this.startActivity(new Intent(Profile.this.getApplicationContext(), LoginActivity.class));
            }
        });
        this.update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Profile.this.startActivity(new Intent(Profile.this.getApplicationContext(), UpdateProfile.class));
            }
        });
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.progressDialog = new ProgressDialog(this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.my_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about_us:
                        Profile.this.startActivity(new Intent(Profile.this.getApplicationContext(), AboutUs.class));
                        Profile.this.overridePendingTransition(0, 0);
                        break;
                    case R.id.home:
                        Profile.this.startActivity(new Intent(Profile.this.getApplicationContext(), MainActivity.class));
                        Profile.this.overridePendingTransition(0, 0);
                        break;
                    case R.id.my_profile:
                        Intent intent = new Intent(Profile.this.getApplicationContext(), Profile.class);
                        Profile.this.overridePendingTransition(0, 0);
                        Profile.this.startActivity(intent);
                        break;
                }
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", 0);
        this.sharedpreferences = sharedPreferences;
        String string = sharedPreferences.getString("email_key", (String) null);
        this.email = string;
        getProfileDetails(string);
    }

    private void getProfileDetails(String email2) {
        this.progressDialog.setMessage("Please Wait....");
        this.progressDialog.show();
        final String str = email2;
        Volley.newRequestQueue(this).add(new StringRequest(1, "https://texiri.com/jg/profile.php", new Response.Listener<String>() {
            private static final String USER_ID = "id";

            public void onResponse(String response) {
                String str = response;
                String str2 = "id";
                System.out.println(str);
                Profile.this.progressDialog.dismiss();
                try {
                    JSONArray jsonarray = new JSONArray(str);
                    int i = 0;
                    while (i < jsonarray.length()) {
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String uid = jsonobject.getString(str2);
                        String name = jsonobject.getString("name");
                        String emailid = jsonobject.getString("email");
                        String mobile = jsonobject.getString("mobile");
                        String address = jsonobject.getString("address");
                        System.out.println(uid);
                        String id = uid.toString().trim();
                        SharedPreferences.Editor editor = Profile.this.sharedpreferences.edit();
                        editor.putString(id, uid);
                        editor.apply();
                        System.out.println(emailid);
                        System.out.println(name);
                        System.out.println(mobile);
                        SharedPreferences.Editor editor2 = editor;
                        System.out.println("https://texiri.com/jg/" + "/" + Profile.this.picture);
                        ((TextView) Profile.this.findViewById(R.id.email)).setText(emailid);
                        ((TextView) Profile.this.findViewById(R.id.username)).setText(name);
                        ((TextView) Profile.this.findViewById(R.id.mobileno)).setText(mobile);
                        ((TextView) Profile.this.findViewById(R.id.address)).setText(address);
                        i++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Profile.this.progressDialog.dismiss();
                Toast.makeText(context, "Failed to get details", Toast.LENGTH_SHORT).show();
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
