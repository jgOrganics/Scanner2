package com.example.scanner2.Profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.scanner2.R;


import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String USER_ID = "id";
    private final int PICK_IMAGE_REQUEST = 71;
    /* access modifiers changed from: private */
    public EditText address;
    /* access modifiers changed from: private */
    public AwesomeValidation awesomeValidation;
    Bitmap bitmap;
    private Button btnUpdate;
    private Button btn_upload;
    /* access modifiers changed from: private */
    public EditText email;
    String encodedimage;

    /* renamed from: id */
    String id;
    private ImageView image;
    /* access modifiers changed from: private */
    public EditText mobileno;
    /* access modifiers changed from: private */
    public EditText name;
    /* access modifiers changed from: private */
    public EditText password;
    ProgressDialog progressDialog;
    private String selectedPicture = "";
    SharedPreferences sharedpreferences;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        name = (EditText) findViewById(R.id.name);
        mobileno = (EditText) findViewById(R.id.mobileno);
        email = (EditText) findViewById(R.id.email);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);
//         awesomeValidation.addValidation(R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
//        awesomeValidation.addValidation(R.id.email, Patterns.EMAIL_ADDRESS,  R.string.emailerror);
//        awesomeValidation.addValidation(R.id.mobileno, "^[0-9]{2}[0-9]{8}$",  R.string.mobileerror);
//        awesomeValidation.addValidation(R.id.password, "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}",R.string.passworderror);
        sharedpreferences = getSharedPreferences("shared_prefs", 0);
        image = (ImageView) findViewById(R.id.image);
        Button button = (Button) findViewById(R.id.btn_update);
        this.btnUpdate = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                UpdateProfile.this.progressDialog = new ProgressDialog(UpdateProfile.this);
                if (UpdateProfile.this.awesomeValidation.validate()) {
                    UpdateProfile.this.updateProfile();
                //    UpdateProfile.this.startActivity(new Intent(UpdateProfile.this.getApplicationContext(), LoginActivity.class));
                    return;
                }
                Toast.makeText(UpdateProfile.this, "Please provide valid information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateProfile() {
        this.progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        this.progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", 0);
        this.sharedpreferences = sharedPreferences;
        id = sharedPreferences.getString("id", (String) null);
        StringRequest stringRequest = new StringRequest(1, "https://texiri.com/jg/update.php", new Response.Listener<String>() {
            public void onResponse(String response) {
                System.out.println(response);
                UpdateProfile.this.progressDialog.dismiss();
                Toast.makeText(UpdateProfile.this.getApplicationContext(), "Profile has been updated", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                UpdateProfile.this.progressDialog.dismiss();
            }
        }) {
            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("name", UpdateProfile.this.name.getText().toString());
                params.put("mobile",mobileno.getText().toString());
                params.put("email", email.getText().toString());
                params.put("address", address.getText().toString());
                params.put("password", password.getText().toString());
                params.put("update_profile","application/x-www-form-urlencoded");
                return params;
            }

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, 1, 1.0f));
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 111 && resultCode == -1) {
            Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
            this.bitmap = bitmap2;
            this.image.setImageBitmap(bitmap2);
            encodebitmap(this.bitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodebitmap(Bitmap bitmap2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        this.selectedPicture = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
    }
}
