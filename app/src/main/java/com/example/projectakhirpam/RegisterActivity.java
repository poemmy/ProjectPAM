package com.example.projectakhirpam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null ){
            finish();
            return;
        }

        Button _btnRegis = findViewById(R.id.button_regis);
        _btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        Button _btnSwitch = findViewById(R.id.bck_login);
        _btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToLogin();
            }
        });
    }

    private void registerUser() {
        EditText etFN = findViewById(R.id.firstname);
        EditText etLN = findViewById(R.id.lastname);
        EditText etRegEmail = findViewById(R.id.email_regis);
        EditText etPass = findViewById(R.id.password_regis);

        String fn = etFN.getText().toString();
        String ln = etLN.getText().toString();
        String email = etRegEmail.getText().toString();
        String password = etPass.getText().toString();

        if (fn.isEmpty() || ln.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill in the form!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fn, ln, email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            showMainActivity();
                                        }
                                    });


                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication Failed.", Toast.LENGTH_LONG).show();

                        }
                    }
                });

    }

    private void showMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void switchToLogin(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

