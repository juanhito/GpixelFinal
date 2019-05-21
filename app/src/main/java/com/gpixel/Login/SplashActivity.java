package com.gpixel.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gpixel.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvSlogan = findViewById(R.id.tvSlogan);
        final ImageView img = findViewById(R.id.imageView);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation myanim2 = AnimationUtils.loadAnimation(this, R.anim.pop_up);

        tvSlogan.startAnimation(myanim);
        img.startAnimation(myanim2);

        openApp(true);
    }

    private void openApp(boolean locationPermission) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
