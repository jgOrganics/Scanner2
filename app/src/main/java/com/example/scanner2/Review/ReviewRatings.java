package com.example.scanner2.Review;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.scanner2.R;

import java.util.HashMap;
import java.util.Map;

public class ReviewRatings extends AppCompatActivity {
    public static final String EMAIL_KEY = "email_key";
    public static final String SHARED_PREFS = "shared_prefs";
    private Button ShowDialog;
    /* access modifiers changed from: private */
    public AwesomeValidation awesomeValidation;
    Button btnsubmit;
    /* access modifiers changed from: private */
    public Dialog dialog;
    /* access modifiers changed from: private */
    public Dialog dialog1;
    String email;

    /* renamed from: my */
    String f518my;
    ProgressDialog progressDialog;
    float rateValue;
    TextView ratecount;
    RatingBar ratingBar;
    EditText review;
    SharedPreferences sharedpreferences;
    TextView showRating;
    String temp;
    TextView tvFeedback;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_ratings);
        this.awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        Intent intent = getIntent();
        final String hash = intent.getStringExtra("farmer_hash");
        final String name = intent.getStringExtra("farmer_name");
        this.awesomeValidation.addValidation((Activity) this, R.id.review, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", (int) R.string.feedbackerr);
        this.tvFeedback = (TextView) findViewById(R.id.tvFeedback);
        this.ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        this.ratecount = (TextView) findViewById(R.id.rateCount);
        this.review = (EditText) findViewById(R.id.review);
        this.btnsubmit = (Button) findViewById(R.id.btnsubmit);
        this.progressDialog = new ProgressDialog(this);
        this.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ReviewRatings.this.rateValue = ratingBar.getRating();
                if (ReviewRatings.this.rateValue <= 1.0f && ReviewRatings.this.rateValue > 0.0f) {
                    System.out.println(rating);
                    ReviewRatings.this.ratecount.setText("Very Dissatisfied " + rating);
                } else if (ReviewRatings.this.rateValue <= 2.0f && ReviewRatings.this.rateValue > 1.0f) {
                    ReviewRatings.this.ratecount.setText("Dissatisfied " + rating);
                } else if (ReviewRatings.this.rateValue <= 3.0f && ReviewRatings.this.rateValue > 2.0f) {
                    System.out.println(rating);
                    ReviewRatings.this.ratecount.setText("OK " + rating);
                } else if (ReviewRatings.this.rateValue <= 4.0f && ReviewRatings.this.rateValue > 3.0f) {
                    System.out.println(rating);
                    ReviewRatings.this.ratecount.setText("Satisfied" + rating);
                } else if (ReviewRatings.this.rateValue <= 5.0f && ReviewRatings.this.rateValue > 4.0f) {
                    ReviewRatings.this.ratecount.setText("Very Satisfied " + rating);
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", 0);
        this.sharedpreferences = sharedPreferences;
        this.email = sharedPreferences.getString("email_key", (String) null);
        System.out.println(this.email);
        this.review.setText("");
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.setContentView(R.layout.custom_dialog_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            this.dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        this.dialog.getWindow().setLayout(-1, -2);
        this.dialog.setCancelable(false);
        this.dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button Okay = (Button) this.dialog.findViewById(R.id.btn_okay);
        Button Cancel = (Button) this.dialog.findViewById(R.id.btn_cancel);
        Dialog dialog3 = new Dialog(this);
        this.dialog1 = dialog3;
        dialog3.setContentView(R.layout.custom_dialog_layout2);
        if (Build.VERSION.SDK_INT >= 21) {
            this.dialog1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        }
        this.dialog1.getWindow().setLayout(-1, -2);
        this.dialog1.setCancelable(false);
        this.dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Okay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ReviewRatings.this, "Okay", Toast.LENGTH_SHORT).show();
                ReviewRatings.this.dialog.dismiss();
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ReviewRatings.this, "Cancel", Toast.LENGTH_SHORT).show();
                ReviewRatings.this.dialog.dismiss();
            }
        });
        ((Button) this.dialog1.findViewById(R.id.btn_okay1)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ReviewRatings.this, "Okay", Toast.LENGTH_SHORT).show();
                ReviewRatings.this.dialog1.dismiss();
            }
        });
        ((Button) this.dialog1.findViewById(R.id.btn_cancel1)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ReviewRatings.this, "Cancel",Toast.LENGTH_SHORT).show();
                ReviewRatings.this.dialog1.dismiss();
            }
        });
        this.btnsubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ReviewRatings.this.ratingBar.setRating(0.0f);
                if (ReviewRatings.this.awesomeValidation.validate()) {
                    ReviewRatings reviewRatings = ReviewRatings.this;
                    reviewRatings.review(reviewRatings.email, name, hash);
                    ReviewRatings.this.dialog.show();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void review(String email2, String name, String hash) {
        this.progressDialog.setMessage("Please Wait....");
        this.progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        this.temp = this.ratecount.getText().toString();
        Log.d("ContentValues", "onClick: " + this.temp);
        this.f518my = this.review.getText().toString().trim();
        final String str = hash;
        final String str2 = name;
        final String str3 = email2;
        queue.add(new StringRequest(1, "https://texiri.com/jg/Review.php", new Response.Listener<String>() {
            private static final String USER_ID = "id";

            public void onResponse(String response) {
                System.out.println(response);
                ReviewRatings.this.progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                ReviewRatings.this.progressDialog.dismiss();
                Toast.makeText(ReviewRatings.this, "Fail to get data" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("farmer_hash", str);
                params.put("farmer", str2);
                params.put("email", str3);
                params.put("review", review.toString());
                params.put("ratings", temp);
                return params;
            }
        });
    }
}
