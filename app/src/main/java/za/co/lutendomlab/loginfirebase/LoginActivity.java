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
<<<<<<< HEAD
=======
import com.google.firebase.auth.FirebaseUser;
>>>>>>> 7cccf0b9599b5e3177d0bfd1144611a04d6a5689
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
<<<<<<< HEAD

/**
 * Created by codetribe on 8/23/2017.
 */
=======
import com.google.firebase.database.ValueEventListener;
>>>>>>> 7cccf0b9599b5e3177d0bfd1144611a04d6a5689

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog progressDialog;
    private Button btnSignup;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    FirebaseUser user;
    private Button btnReset;
    final FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference db = database.getReference().child("User");



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set the view now
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("Login");

        //get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


<<<<<<< HEAD
            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    User user = dataSnapshot.getValue(User.class);

                    if (user.getRole().equals("User"))
                    {
                        Toast.makeText(LoginActivity.this,"my role is " +user.getRole(),Toast.LENGTH_SHORT);

                    }

//                    Intent intent = new Intent(LoginActivity.this,HomeScreenUser.class);
//                    intent.putExtra("userid",auth.getCurrentUser().getUid());
//                    startActivity(intent);
//                    finish();


                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


=======
        if(user != null){
            navigateToUserScreen(user.getUid());
>>>>>>> 7cccf0b9599b5e3177d0bfd1144611a04d6a5689
        }




        inputEmail = (EditText)findViewById(R.id.email);
        inputPassword = (EditText)findViewById(R.id.password);
        btnSignup = (Button)findViewById(R.id.btn_signup);
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
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String email = inputEmail.getText().toString();
//                final String password = inputPassword.getText().toString();
//
//                if(TextUtils.isEmpty(email)){
//
//                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if (TextUtils.isEmpty(password)) {
//                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                progressDialog.setMessage("Logging in. Please wait...");
//                progressDialog.show();
//
//                //authentication user
//                auth.signInWithEmailAndPassword(email,password)
//                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                // If sign in fails, display a message to the user. If sign in succeeds
//                                // the auth state listener will be notified and logic to handle the
//                                // signed in user can be handled in the listener.
//                                progressDialog.dismiss();
//
//                                if(!task.isSuccessful()){
//                                    //there was an error
//                                    if(password.length() < 6){
//                                        inputPassword.setError(getString(R.string.minimum_password));
//                                    }else {
//                                        Toast.makeText(LoginActivity.this,getString(R.string.auth_failed),Toast.LENGTH_SHORT).show();
//                                    }
//                                }else {
//
//                                    finish();
//                                    Intent intent = new Intent(LoginActivity.this, HomeScreenUser.class);
//                                    intent.putExtra("userid",auth.getCurrentUser().getUid());
//                                    startActivity(intent);
//                                }
//                            }
//                        });
//            }
//        });


    }

<<<<<<< HEAD
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





                                    db.addChildEventListener(new ChildEventListener() {
                                        @Override
                                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                            User user = dataSnapshot.getValue(User.class);

                                            if (user.getRole().equals("Student"))
                                            {
                                                Toast.makeText(LoginActivity.this,"my role is " +user.getRole()+user.getName(),Toast.LENGTH_SHORT);

                                            }



                                        }

                                        @Override
                                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                                        }

                                        @Override
                                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                                }
=======
    public void LogIn(View view){

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
>>>>>>> 7cccf0b9599b5e3177d0bfd1144611a04d6a5689
                            }
                        }else {

                            navigateToUserScreen(task.getResult().getUser().getUid());

                        }
                    }
                });
    }

    public void navigateToUserScreen(String user_id) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("User").child(user_id);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                if ( "Facilitator".equals(user.getRole())) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(LoginActivity.this, HomeScreenUser.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
