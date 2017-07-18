package com.dylan.getphonenumber;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTvPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvPhoneNumber = (TextView) findViewById(R.id.tv_phone_number);

        displayPhoneNumber();
    }

    private void displayPhoneNumber() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            mTvPhoneNumber.setText(getPhoneNumber());
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 13);
            }
        }
    }

    private String getPhoneNumber() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String phoneNumber = tm.getLine1Number();
        return "Phone: " + phoneNumber;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 13:
                // At least one result must be checked.
                if(grantResults.length < 1){
                    return;
                }

                // Verify that each required permission has been granted, otherwise return false.
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                mTvPhoneNumber.setText(getPhoneNumber());
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
