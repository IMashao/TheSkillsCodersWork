package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by codetribe on 8/23/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private Button btnSignup;
    private Button btnLogin;
    private Button btnReset;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view now
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Login");

        //get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){

           /// startActivity(new Intent(LoginActivity.this, HomeScreenUser.class));
            Intent intent = new Intent(LoginActivity.this,HomeScreenUser.class);
            intent.putExtra("userid",auth.getCurrentUser().getUid());
            startActivity(intent);
            finish();
        }


//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        inputEmail = (EditText)findViewById(R.id.email);
        inputPassword = (EditText)findViewById(R.id.password);
        btnSignup = (Button)findViewById(R.id.btn_signup);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnReset = (Button)findViewById(R.id.btn_reset_password);

        //get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if(TextUtils.isEmpty(email)){

                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog.setMessage("Logging in. Please wait...");
                progressDialog.show();

                //authentication user
                auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                progressDialog.dismiss();

                                if(!task.isSuccessful()){
                                    //there was an error
                                    if(password.length() < 6){
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    }else {
                                        Toast.makeText(LoginActivity.this,getString(R.string.auth_failed),Toast.LENGTH_SHORT).show();
                                    }
                                }else {

                                    finish();
                                    Intent intent = new Intent(LoginActivity.this, HomeScreenUser.class);
                                    intent.putExtra("userid",auth.getCurrentUser().getUid());
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }
}
