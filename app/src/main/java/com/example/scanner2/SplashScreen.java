package com.example.scanner2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;

public class SplashScreen extends AppCompatActivity {
    private static final String PREF_LOGIN = "LOGIN_PREF";
    private static final String USER_EMAIL = "EMAIL_ADDRESS";
    private static final String USER_PASSWORD = "PASSWORD";
    LottieAnimationView lottieAnimationView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) findViewById(R.id.animationView);
        lottieAnimationView = lottieAnimationView2;
        lottieAnimationView2.animate().translationY(0.0f).setDuration(1000).setStartDelay(2500);
        if (!isConnected(this)) {
            showInternetDialog();
        }
        new Thread() {
            public void run() {
                Intent intent;
                Intent intent2;
                try {
                    Thread.sleep(4000);
                    SharedPreferences preferences = SplashScreen.this.getSharedPreferences(SplashScreen.PREF_LOGIN, 0);
//                    intent = (!preferences.contains(SplashScreen.USER_EMAIL) || !preferences.contains(SplashScreen.USER_PASSWORD)) ? new Intent(SplashScreen.this, LoginActivity.class) : new Intent(SplashScreen.this, MainActivity.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    SharedPreferences preferences2 = SplashScreen.this.getSharedPreferences(SplashScreen.PREF_LOGIN, 0);
//                    intent = (!preferences2.contains(SplashScreen.USER_EMAIL) || !preferences2.contains(SplashScreen.USER_PASSWORD)) ? new Intent(SplashScreen.this, LoginActivity.class) : new Intent(SplashScreen.this, MainActivity.class);
                } catch (Throwable th) {
                    SharedPreferences preferences3 = SplashScreen.this.getSharedPreferences(SplashScreen.PREF_LOGIN, 0);
                    if (!preferences3.contains(SplashScreen.USER_EMAIL) || !preferences3.contains(SplashScreen.USER_PASSWORD)) {
//                        intent2 = new Intent(SplashScreen.this, LoginActivity.class);
                    } else {
                        intent2 = new Intent(SplashScreen.this, MainActivity.class);
                    }
//                    SplashScreen.this.startActivity(intent2);
                    SplashScreen.this.finish();
                    throw th;
                }
//                SplashScreen.this.startActivity(intent);
                SplashScreen.this.finish();
            }
        }.start();
    }

    /* access modifiers changed from: private */
    public void showInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = LayoutInflater.from(this).inflate(R.layout.no_internet_dialog, (ViewGroup) findViewById(R.id.no_internet_layout));
        view.findViewById(R.id.try_again).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SplashScreen splashScreen = SplashScreen.this;
                if (!splashScreen.isConnected(splashScreen)) {
                    SplashScreen.this.showInternetDialog();
                    SplashScreen.this.finishActivity(0);
                    return;
                }
                SplashScreen.this.startActivity(new Intent(SplashScreen.this.getApplicationContext(), SplashScreen.class));
                SplashScreen.this.finish();
            }
        });
        builder.setView(view);
        builder.create().show();
    }

    /* access modifiers changed from: private */
    public boolean isConnected(SplashScreen SplashScreen) {
        ConnectivityManager connectivityManager = (ConnectivityManager) SplashScreen.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(1);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(0);
        if (wifiConn != null && wifiConn.isConnected()) {
            return true;
        }
        if (mobileConn == null || !mobileConn.isConnected()) {
            return false;
        }
        return true;
    }
}
