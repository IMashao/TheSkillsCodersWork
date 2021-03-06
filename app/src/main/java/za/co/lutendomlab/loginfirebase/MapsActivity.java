package za.co.lutendomlab.loginfirebase;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;

public class MapsActivity extends AppCompatActivity {

    Config config;

    double longitude = 28.267422941027917;
    double latitude = -25.74899823783382;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendEmail();
    }

    public void sendEmail() {
        //Getting content for email
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String email = "lseboya101@gmail.com";
        String subject = "Att Register ";
        String message = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + " is now " + "Present \n " + currentDateTimeString;

        Toast.makeText(this," " +message,Toast.LENGTH_LONG).show();
        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }
}