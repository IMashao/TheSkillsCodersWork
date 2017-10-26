package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import za.co.lutendomlab.loginfirebase.timeSheet.TimeSheetsActivity;

/**
 * Created by codetribe on 10/20/2017.
 */

public class AdminOptionStudents extends AppCompatActivity {

        User user;

        FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_admin_role_student);
//
                Intent intent = getIntent();
                user = intent.getParcelableExtra("userProfile");

                Toast.makeText(this, "user " + user.getName(), Toast.LENGTH_SHORT).show();

        }

        public void TimeSheet(View v) {

                Intent intent = new Intent(AdminOptionStudents.this, TimeSheetsActivity.class);
                intent.putExtra("userProfile", user);
                startActivity(intent);
        }

        public void Disable(View view) {
        }

        public void MakaCall(View view) {

                user.getPhoneNumber();
                String phoneNumbur = "0790420795";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                        Uri.fromParts("tel", phoneNumbur, null));
                startActivity(phoneIntent);
        }
}