package com.example.scanner2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
//import com.example.scanner.Review.ReviewRatings;
//import com.example.scanner.video.Farmer_video;
import com.example.scanner2.Review.ReviewRatings;
import com.example.scanner2.video.Farmer_video;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import okio.DeflaterSink;

public class DisplayData extends AppCompatActivity {
    Button btn_Button;
    TextView col1;
    TextView col2;
    private ConstraintLayout constraintLayout;
    Context context;
    TextView dialog_language;
    FloatingActionButton fab;
    private ImageView family_img;
    TextView farmer_details;
    Button farmer_videos;
    Button feedback;
    private ImageView img;
    private ImageView img2;
    private boolean isOpen = false;
    int lang_selected;
    private ConstraintSet layout1;
    private ConstraintSet layout2;
    TextView locate;
    Button location;
    Button maps;
    TextView messanger;
    private ImageView p_image;
    TextView product_details;
    RatingBar ratingBar;
    TextView ratings;
    Resources resources;
    RelativeLayout show_lan_dialog;

    /* renamed from: t1 */
    TextView t1;

    /* renamed from: t2 */
    TextView t2,t3;

    /* renamed from: t3 */
    TextView f630t3;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView txtaddress;
    TextView txtfirstname;
    TextView txtmobileno;
    private WebView webView;
    TextView whatsapp;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(8192, 8192);
        setContentView(R.layout.activity_display_data);
        family_img = (ImageView) findViewById(R.id.family);
        location = (Button) findViewById(R.id.location);
        locate = (TextView) findViewById(R.id.locate);
        col1 = (TextView) findViewById(R.id.col1);
        col2 = (TextView) findViewById(R.id.col2);
        farmer_details = (TextView) findViewById(R.id.farmer_details);
        product_details = (TextView) findViewById(R.id.product_details);
        feedback = (Button) findViewById(R.id.feedback);
        farmer_videos = (Button) findViewById(R.id.video);
        ratings = (TextView) findViewById(R.id.ratings);
        dialog_language = (TextView) findViewById(R.id.dialog_language);
        show_lan_dialog = (RelativeLayout) findViewById(R.id.showlangdialog);
        if (LocaleHelper.getLanguage(this).equalsIgnoreCase("en")) {
            Context locale = LocaleHelper.setLocale(this, "en");
            context = locale;
            resources = locale.getResources();
            dialog_language.setText("ENGLISH");
            col1.setText(this.resources.getString(R.string.col1));
            col2.setText(this.resources.getString(R.string.col2));
            farmer_details.setText(this.resources.getString(R.string.farmer_details));
            product_details.setText(this.resources.getString(R.string.product_details));
            ratings.setText(this.resources.getString(R.string.rate));
            locate.setText(this.resources.getString(R.string.location));
            setTitle(this.resources.getString(R.string.app_name));
            lang_selected = 0;
        } else if (LocaleHelper.getLanguage(this).equalsIgnoreCase("kn")) {
            Context locale2 = LocaleHelper.setLocale(this, "kn");
            context = locale2;
            resources = locale2.getResources();
            dialog_language.setText("ಕನ್ನಡ");
            col1.setText(this.resources.getString(R.string.col1));
            col2.setText(this.resources.getString(R.string.col2));
            farmer_details.setText(this.resources.getString(R.string.farmer_details));
            product_details.setText(this.resources.getString(R.string.product_details));
            ratings.setText(this.resources.getString(R.string.rate));
            locate.setText(this.resources.getString(R.string.location));
            setTitle(this.resources.getString(R.string.app_name));
            lang_selected = 2;
        }
        show_lan_dialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final String[] Language = {"ENGLISH", "ಕನ್ನಡ"};
                Log.d("Clicked", "Clicked");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DisplayData.this);
                dialogBuilder.setTitle((CharSequence) "Select a Language").setSingleChoiceItems((CharSequence[]) Language, DisplayData.this.lang_selected, (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DisplayData.this.dialog_language.setText(Language[i]);
                        if (Language[i].equals("ENGLISH")) {
                           context = LocaleHelper.setLocale(DisplayData.this, "en");
                           resources = DisplayData.this.context.getResources();
                           lang_selected = 0;
                           col1.setText(DisplayData.this.resources.getString(R.string.col1));
                           col2.setText(DisplayData.this.resources.getString(R.string.col2));
                           farmer_details.setText(DisplayData.this.resources.getString(R.string.farmer_details));
                            product_details.setText(DisplayData.this.resources.getString(R.string.product_details));
                            locate.setText(DisplayData.this.resources.getString(R.string.location));
                           ratings.setText(DisplayData.this.resources.getString(R.string.rate));
                         setTitle(DisplayData.this.resources.getString(R.string.app_name));
                        }
                        if (Language[i].equals("ಕನ್ನಡ")) {
                              context = LocaleHelper.setLocale(DisplayData.this, "kn");
                             resources = DisplayData.this.context.getResources();
                             lang_selected = 2;
                             col1.setText(DisplayData.this.resources.getString(R.string.col1));
                            col2.setText(DisplayData.this.resources.getString(R.string.col2));
                            farmer_details.setText(DisplayData.this.resources.getString(R.string.farmer_details));
                            product_details.setText(DisplayData.this.resources.getString(R.string.product_details));
                            locate.setText(DisplayData.this.resources.getString(R.string.location));
                            ratings.setText(DisplayData.this.resources.getString(R.string.rate));
                            setTitle(DisplayData.this.resources.getString(R.string.app_name));
                        }
                    }
                }).setPositiveButton((CharSequence) "OK", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialogBuilder.create().show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle("Farmer Profile");
        img = (ImageView) findViewById(R.id.img);
        txtfirstname = (TextView) findViewById(R.id.txtfirstname);
        txtmobileno = (TextView) findViewById(R.id.txtmobile);
        txtaddress = (TextView) findViewById(R.id.txtaddress);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
//        txt1 = (TextView) findViewById(R.id.txt1);
//        txt2 = (TextView) findViewById(R.id.txt2);
        Intent intent = getIntent();
         String hash = intent.getStringExtra("hash");
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String photo = intent.getStringExtra("photo_url");
        String mobile = intent.getStringExtra("mobile");
        String products = intent.getStringExtra("pname");
        String quantity = intent.getStringExtra("qty");
        String rate = intent.getStringExtra("rate");
        Double lat = intent.getDoubleExtra("lat",0.0);
        System.out.println(hash);
        System.out.println(name);
        System.out.println(address);
        System.out.println(photo);
        System.out.println(lat);
        Double lng = intent.getDoubleExtra("lng",0.0);
        System.out.println(lng);
        String title = intent.getStringExtra("title");
        String video = intent.getStringExtra("video");
      //  Toolbar toolbar2 = toolbar;
        String family_name = intent.getStringExtra("family_name");
        CollapsingToolbarLayout collapsingToolbarLayout = collapsingToolbar;
        String family_photo = intent.getStringExtra("family_photo");
        Intent intent2 = intent;
        System.out.println(title);
        System.out.println(video);
        System.out.println(rate);
        System.out.println(family_name);
        System.out.println(family_photo);
        txtfirstname.setText(name);
        txtmobileno.setText(mobile);
        txtaddress.setText(address);
        t1.setText(products);
        t2.setText(quantity);
        t3.setText(rate);
        String photo_url = "https://texiri.com/jg/"+hash+"/"+photo;
        Picasso.get().load(photo_url).into(img);
        System.out.println(photo_url);
         String farmer_video = "https://texiri.com/jg/"+ hash + "/others/" + video;
        String farmer_family = "https://texiri.com/jg/" + hash + "/" + "others/" + family_photo;
        Picasso.get().load(farmer_family).into(family_img);
        System.out.println(farmer_family);
        System.out.println(farmer_video);
        this.farmer_videos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
              Intent i3 = new Intent(DisplayData.this.getApplicationContext(), Farmer_video.class);
               i3.putExtra("farmer_video", farmer_video);
               i3.putExtra("hahs",hash);
             startActivity(i3);
            }
        });
        this.feedback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i2 = new Intent(DisplayData.this.getApplicationContext(), ReviewRatings.class);
                i2.putExtra("farmer_hash", hash);
                i2.putExtra("name", name);
                startActivity(i2);
            }
        });
        this.location.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                Intent intent1 = new Intent(DisplayData.this.getApplicationContext(), MapsActivity.class);
//                intent1.putExtra("lat", lat);
//                intent1.putExtra("lng", lng);
//                DisplayData.this.startActivity(intent1);
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
