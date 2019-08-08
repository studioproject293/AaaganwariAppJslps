package com.jslps.aaganbariapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.jslps.aaganbariapp.R;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUserName, editTextPassword;
    Button sigiin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        editTextUserName = findViewById(R.id.userName);
        editTextPassword = findViewById(R.id.userPassword);
        sigiin = findViewById(R.id.sigiin);
        sigiin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    private void showError(EditText editText) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        editText.startAnimation(shake);
    }
}
