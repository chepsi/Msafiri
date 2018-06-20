package com.dev.chepsi.msafiri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPass;
    private Button loginBtn;
    private ProgressBar loginProgress;
    private FirebaseAuth auth;
    private TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        loginEmail = findViewById(R.id.signInEmail);
        loginPass = findViewById(R.id.signInPassword);
        loginBtn = findViewById(R.id.sign_in_button);
        loginProgress = findViewById(R.id.signinProgress);
        signUpLink = findViewById(R.id.signUpLink);

        loginProgress.setVisibility(View.GONE);

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String email = loginEmail.getText().toString().trim();
                final String password = loginPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Input email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Input password", Toast.LENGTH_SHORT).show();
                }

                loginProgress.setVisibility(View.VISIBLE);

                try {

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    loginProgress.setVisibility(View.GONE);
                                    Intent openMain = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(openMain);
                                    finish();
                                }
                            });

                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
