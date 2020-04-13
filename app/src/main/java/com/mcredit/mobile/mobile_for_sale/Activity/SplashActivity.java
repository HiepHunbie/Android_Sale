package com.mcredit.mobile.mobile_for_sale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.mcredit.mobile.mobile_for_sale.Base.PermissionActivity;
import com.mcredit.mobile.mobile_for_sale.Contants.AppContants;
import com.mcredit.mobile.mobile_for_sale.R;

/**
 * Created by hiepnt on 14/03/2018.
 */

public class SplashActivity extends PermissionActivity {

    private Handler handler = new Handler();
    private Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        runnable =
                new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                };

        handler.postDelayed(runnable,3000);

    }


}
