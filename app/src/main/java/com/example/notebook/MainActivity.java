package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.notebook.finger.FingerprintListener;
import com.example.notebook.finger.IFingerprint;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    IFingerprint fingerprint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fingerprint = new IFingerprint.Builder(getApplicationContext()).build();

    }

    public void authenticate(View view) {
        fingerprint.authenticate(new FingerprintListener() {
            @Override
            public void onSuccess() {
                startActivity(new Intent(MainActivity.this,NoteActivity.class));




                Log.d(TAG, "onSuccess:");
            }

            @Override
            public void onFailed() {
                Log.d(TAG, "onFailed");
            }
        });
    }

}