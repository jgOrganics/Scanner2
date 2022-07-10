package com.example.scanner2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.example.scanner.AboutUs.AboutUs;
//import com.example.scanner.PageViewer.CustomVolleyRequest;
//import com.example.scanner.PageViewer.SliderUtils;
//import com.example.scanner.PageViewer.ViewPagerAdapter;
//import com.example.scanner.Profile.Profile;
//import com.example.scanner.policy.Policy;
//import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.example.scanner2.AboutUs.AboutUs;
import com.example.scanner2.PageViewer.CustomVolleyRequest;
import com.example.scanner2.PageViewer.SliderUtils;
import com.example.scanner2.PageViewer.ViewPagerAdapter;
import com.example.scanner2.Profile.Profile;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
//import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String apiurl = "https://texiri.com/jg/customer.php";
    CardView chatbot;
    TextView contact_us;
    CardView contact_us_page;
    Context context;
    TextView dialog_language;
    /* access modifiers changed from: private */
    public ImageView[] dots;
    /* access modifiers changed from: private */
    public int dotscount;
    DrawerLayout drawerLayout;
    TextView email;
    ImageView img;
    int lang_selected;
    TextView mail_us;
    TextView myname;
    ImageView nointernet;
    TextView pesticide;
    CardView pesticides;
    ProgressDialog progressDialog;
    ImageView qrcode;
    String request_url = "https://texiri.com/jg/autoimageslider.php";
    Resources resources;

    /* renamed from: rq */
    RequestQueue requestQueue;
    RelativeLayout show_lan_dialog;
    LinearLayout sliderDotspanel;
    List<SliderUtils> sliderImg;
//    private   SliderView sliderView;
    Button submit;
    TextView textView1;
    TextView textView2;
    TextView title;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    TextView total_cm;
    TextView total_customers;
    TextView total_farmers;
    TextView total_fm;
    TextView total_pd;
    TextView total_products;
    TextView txtaddress;
    TextView txtfirstname;
    TextView txtmobileno;
    Typeface typeface;
    TextView uaddress;
    TextView uemail;
    TextView umobile;
    ViewPager viewPager;
   // ViewPagerAdapter viewPagerAdapter;
    TextView website;
    private ViewPagerAdapter viewPagerAdapter;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.about_us:
                       MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), AboutUs.class));
                       MainActivity.this.overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), MainActivity.class));
                        MainActivity.this.overridePendingTransition(0, 0);
                        return true;
                    case R.id.my_profile:
                        Intent intent = new Intent(MainActivity.this.getApplicationContext(), Profile.class);
                       overridePendingTransition(0, 0);
                        intent.putExtra("email", MainActivity.this.getIntent().getStringExtra("email"));
                        startActivity(intent);
                        return true;
                    case R.id.share:
                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), MainActivity.class));
                        MainActivity.this.overridePendingTransition(0, 0);
                        return true;
                    default:
                        return false;
                }
            }
        });

        requestQueue = CustomVolleyRequest.getInstance(this).getRequestQueue();
        this.sliderImg = new ArrayList();
        this.viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        sendRequest();
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                for (int i = 0; i < MainActivity.this.dotscount; i++) {
                    MainActivity.this.dots[i].setImageDrawable(ContextCompat.getDrawable(MainActivity.this.getApplicationContext(), R.drawable.non_active_dot));
                }
                MainActivity.this.dots[position].setImageDrawable(ContextCompat.getDrawable(MainActivity.this.getApplicationContext(), R.drawable.active_dot));
            }

            public void onPageScrollStateChanged(int state) {
            }
        });



        getTotalFarmers();
        getTotalCustomers();
        getTotalProducts();
        clockwise();
        this.contact_us_page = (CardView) findViewById(R.id.contact_us_page);
        this.chatbot = (CardView) findViewById(R.id.chatbot);
        this.total_farmers = (TextView) findViewById(R.id.total_farmers);
        this.total_customers = (TextView) findViewById(R.id.total_customers);
        this.total_products = (TextView) findViewById(R.id.total_products);
        this.contact_us = (TextView) findViewById(R.id.contact_us);
        this.mail_us = (TextView) findViewById(R.id.mail_us);
        this.title = (TextView) findViewById(R.id.title);
        this.chatbot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, ChatBotResponse.class));
            }
        });
        this.contact_us_page.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), AboutUs.class));
            }
        });
       // setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.progressDialog = new ProgressDialog(this);
      Toolbar toolbar2 = (Toolbar) findViewById(R.id.toolbar);
    //   getSupportActionBar().setDisplayHomeAsUpEnabled(false);
       // getSupportActionBar().setIcon((int) R.drawable.ic_baseline_notifications_24);
        this.qrcode = (ImageView) findViewById(R.id.qrcode);
      //  this.img = (ImageView) findViewById(R.id.img);
        System.out.println("https://media.geeksforgeeks.org/wp-content/cdn-uploads/gfg_200x200-min.png");
        this.qrcode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setPrompt("for flash use Volume up key");
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.logout) {
//            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (item_id == R.id.exit) {
            finishAffinity();
            System.exit(0);
            return true;
        } else if (item_id == R.id.more_apps) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps")));
            return true;
        } else if (item_id != R.id.privacy) {
            return true;
        } else {
          //  startActivity(new Intent(getApplicationContext(), Policy.class));
            return true;
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle((int) R.string.app_name);
        builder.setIcon((int) R.mipmap.ic_launcher);
        builder.setMessage((CharSequence) "Do you want to exit?").setCancelable(false).setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainActivity.this.finish();
            }
        }).setNegativeButton((CharSequence) "No", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private void getFarmerDetails(String qr_val) {
        this.progressDialog.setMessage("Please Wait....");
        this.progressDialog.show();
        final String str = qr_val;
        Volley.newRequestQueue(this).add(new StringRequest(1, apiurl, new Response.Listener<String>() {
            public void onResponse(String response) {
                System.out.println(response);
                Double d2;
                String str1;
                String str2;
                String str3;
                String pname="";
                String str6;
                String str5;
                String str7;
                String str9;
                String str10;
                Double d;
                Double lat = null;
                Double lng = null;
                Double d3;
                String str11;
                String str14;
                String str13;
                Double d4;
                String str15;
                int n=0;
                String str16;
                String str12;

                    try {
//                        JSONObject jSONObject=new JSONObject(response);
                     //   JSONObject responseObj =response.getJSONObject(i);

                        JSONArray jSONArray=new JSONArray(response);
                        for(int i=0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);
                            String hash = jSONObject.getString("hash");
                            String photo_url = jSONObject.getString("photo_url");
                            String name = jSONObject.getString("name");
                            String mobile = jSONObject.getString("mobile");
                            String address = jSONObject.getString("address");
//                            System.out.println(hash);
//                            System.out.println(name);
//                            System.out.println(mobile);
//                            System.out.println(address);
//                            System.out.println(photo_url);
                            JSONObject jSONObject2 = jSONArray.getJSONObject(1);
                            Double d5 = Double.valueOf((String) jSONObject2.getString("lng"));
                            Double d6 = Double.valueOf((String) jSONObject2.getString("lat"));
//                            System.out.println(d5);
//                            System.out.println(d6);
                            String title = jSONObject2.getString("title");
                            String video = jSONObject2.getString("video");
                            String family_name = jSONObject2.getString("family_name");
                            String family_photo = jSONObject2.getString("family_photo");
//                            System.out.println(title);
//                            System.out.println(family_photo);
//                            System.out.println(family_name);
//                            System.out.println(video);
                            StringBuilder sb = new StringBuilder();
                            StringBuilder sb1 = new StringBuilder();
                            StringBuilder sb2 = new StringBuilder();
                            for (int k=2; k<jSONArray.length(); k++) {
                                JSONObject jSONObject3 = jSONArray.getJSONObject(k);
                                sb.append(jSONObject3.getString("pname")+"\n");
                                sb1.append(jSONObject3.getString("qty")+"\n");
                                sb2.append(jSONObject3.getString("rate")+"\n");
//                                    System.out.println(sb);
//                                    System.out.println(sb1);
//                                    System.out.println(sb2);

                            }
                            Intent intent = new Intent(MainActivity.this, DisplayData.class);
                            intent.putExtra("hash",hash);
                            intent.putExtra("name",name);
                            intent.putExtra("mobile",mobile);
                            intent.putExtra("address",address);
                            intent.putExtra("photo_url",photo_url);
                            intent.putExtra("title",title);
                            intent.putExtra("video",video);
                            intent.putExtra("family_name",family_name);
                            intent.putExtra("family_photo",family_photo);
                            intent.putExtra("lat",d5);
                            intent.putExtra("lng", d6);
                            intent.putExtra("pname", (Serializable) sb);
                            intent.putExtra("qty", (Serializable) sb1);
                            intent.putExtra("rate", (Serializable) sb2);
                         startActivity(intent);
                        }

                    } catch (JSONException e) {
                    e.printStackTrace();
                }

              //  throw new UnsupportedOperationException("Method not decompiled: com.example.scanner.MainActivity.C31588.onResponse(java.lang.String):void");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                MainActivity.this.progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Fail to get farmer details", Toast.LENGTH_SHORT).show();
            }
        }) {
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("qr_val", str);
                return params;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null) {
            getFarmerDetails(intentResult.getContents().toString());
        } else {
            Toast.makeText(getApplicationContext(), "Opps you did not scan anything", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    private void getTotalFarmers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        this.total_fm = (TextView) findViewById(R.id.total_fm);
        queue.add(new JsonObjectRequest(0, "https://texiri.com/jg/total_farmers.php", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    JSONArray jsonArray = response.getJSONArray("farmer");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String count = jsonArray.getJSONObject(i).getString("count(*)");
                        System.out.println(count);
                        MainActivity.this.total_fm.setText(count);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    private void getTotalCustomers() {
        RequestQueue queue = Volley.newRequestQueue(this);
        this.total_cm = (TextView) findViewById(R.id.total_cm);
        queue.add(new JsonObjectRequest(0, " https://texiri.com/jg/total_customers.php", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    JSONArray jsonArray = response.getJSONArray("users");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String count = jsonArray.getJSONObject(i).getString("count(*)");
                        System.out.println(count);
                        MainActivity.this.total_cm.setText(count);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    private void getTotalProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);
        this.total_pd = (TextView) findViewById(R.id.total_pd);
        queue.add(new JsonObjectRequest(0, "https://texiri.com/jg/total_products.php", (JSONObject) null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                System.out.println(response);
                try {
                    JSONArray jsonArray = response.getJSONArray("product");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String count = jsonArray.getJSONObject(i).getString("count(*)");
                        System.out.println(count);
                       total_pd.setText(count);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }));
    }

    public void sendRequest() {
        CustomVolleyRequest.getInstance(this).addToRequestQueue(new JsonArrayRequest(0, this.request_url, (JSONArray) null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    SliderUtils sliderUtils = new SliderUtils();
                    try {
                        sliderUtils.setSliderImageUrl(response.getJSONObject(i).getString("images"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderImg.add(sliderUtils);
                }
               viewPagerAdapter = new ViewPagerAdapter(MainActivity.this.sliderImg, MainActivity.this);
              viewPager.setAdapter(viewPagerAdapter);
                new Timer().scheduleAtFixedRate(new MyTimeTask(), AdaptiveTrackSelection.DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS, 4000);
                MainActivity mainActivity = MainActivity.this;
                int unused = mainActivity.dotscount = mainActivity.viewPagerAdapter.getCount();
                MainActivity mainActivity2 = MainActivity.this;
                ImageView[] unused2 = mainActivity2.dots = new ImageView[mainActivity2.dotscount];
                for (int i2 = 0; i2 < MainActivity.this.dotscount; i2++) {
                    MainActivity.this.dots[i2] = new ImageView(MainActivity.this);
                    MainActivity.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(MainActivity.this.getApplicationContext(), R.drawable.non_active_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                    params.setMargins(8, 0, 8, 0);
                    MainActivity.this.sliderDotspanel.addView(MainActivity.this.dots[i2], params);
                }
                MainActivity.this.dots[0].setImageDrawable(ContextCompat.getDrawable(MainActivity.this.getApplicationContext(),R.drawable.active_dot));
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
            }
        }));
    }



    public class MyTimeTask extends TimerTask {
        public MyTimeTask() {
        }

        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    if (MainActivity.this.viewPager.getCurrentItem() == 0) {
                        MainActivity.this.viewPager.setCurrentItem(1);
                    } else if (MainActivity.this.viewPager.getCurrentItem() == 1) {
                        MainActivity.this.viewPager.setCurrentItem(2);
                    } else {
                        MainActivity.this.viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    public void clockwise() {
        ((ImageView) findViewById(R.id.qrcode)).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise));
    }
}
