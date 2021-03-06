package za.co.lutendomlab.loginfirebase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class HomeScreenUser extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView textViewUserEmail;
    private TextView textViewUserName;
    String userName;
    String surname;
    private ImageView imageProfileSelect;
    private ImageView profile_Pic;
    Uri filePath;
    Bitmap bitmap;
    //image uploader
    int PICK_IMAGE_REQUEST = 111;
    String weekdays;
    String formattedDate;
    String time_in = "";
    String time_out;
    int week_number;
    String Sign;
    private DatabaseReference db;
    private DatabaseReference databaseReference;
    FirebaseUser user;
    String userID;
    ProgressDialog pd;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    StorageReference storageRef = storage.getReferenceFromUrl("gs://the-skills-coders-work.appspot.com/");    //change the url according to your firebase app
    ImageView imageView;
    DrawerLayout drawer;
    NavigationView navigationView;
    Toolbar toolbar;

    String role;
    View header;
    private FirebaseAuth firebaseAuth;
    ImageView mark_reg;

    String infor =
            "Geo-timesheet app allows all you to register and login in their accounts in the system." +
            " Any smartphone with Android operating system can be turned into a mobile time track clock" +
            " by downloading the Geotimesheet app, so it makes tracking time and attendance for travelling" +
            " users facile. Users can only clock in and out for their shifts. Now more than ever, students " +
            "can expect self-service tools with easy-to-use resources from their facilitators and administrator,";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen_user);
        imageView =(ImageView)findViewById(R.id.imageView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setSubtitle(R.string.unofficial);
        setSupportActionBar(toolbar);
        NavigationView navigationView1 = (NavigationView)findViewById(R.id.nav_view);

        header = navigationView1.findViewById(R.id.the_name);

        TextView information = (TextView)findViewById(R.id.information);
        information.setText(infor);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
//        user = firebaseAuth.getCurrentUser();


//        imageProfileSelect = (ImageView) findViewById(R.id.profile_picture_select);
        profile_Pic = (ImageView) findViewById(R.id.profile_picture);
//        imageProfileSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                pd = new ProgressDialog(HomeScreenUser.this);
//                pd.setMessage("Uploading....");
//
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
//
//            }
//        });

        user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (user.getPhotoUrl() != null) {
                String url = user.getPhotoUrl().toString();
                Glide.with(getApplicationContext()).load(url).into(profile_Pic);

            }
        }
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child("User").child(userID);
        //db = database.getReference().child("User");
        db = database.getReference().child("TimeSheet").child(userID);


        //userID = user.getUid();


        //Toast.makeText(this, userID, Toast.LENGTH_SHORT).show();
        //  staff_number = (TextView) findViewById(R.id.staff_number);



        //db.addChildEventListener(new ChildEventListener() {
        //@Override
        //public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        //if (userID.equals(dataSnapshot.child("userId").getValue().toString())) {
//
//                    Register reg = dataSnapshot.getValue(Register.class);
//                    User user = dataSnapshot.getValue(User.class);
//                    role = user.getRole();
//
//                    userName = user.getName();
//                    surname =user.getLastName();
//                    textViewUserName.setText("Name: " + userName);
//
//                    //  textViewUserName.setText("Name: "+user.getName());
//                    //  Toast.makeText(HomeScreenUser.this, user.getName(), Toast.LENGTH_SHORT).show();
//                    //  staff_number.setText(String.valueOf("Location: " + user.getFacility()));
////                    Intent intent = new Intent(HomeScreenUser.this,LeaveApply.class);
////                    intent.putExtra(USER,user);
////                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
        textViewUserEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewUserName = (TextView) findViewById(R.id.textViewName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                role = user.getRole();

                userName = user.getName();
                surname = user.getLastName();
                textViewUserName.setText("Name: " + userName + ", " + surname);
                textViewUserEmail.setText("Email: " + user.getEmail());

//                TextView the_name = (TextView)header.findViewById(R.id.the_name);
//                the_name.setText(userName);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        textViewUserEmail.setText("Email :" + user.getEmail());

    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        int id = item.getItemId();

        switch (id) {

            case R.id.markRegister:
                signRegister();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.about:
                startActivity(new Intent(HomeScreenUser.this, AboutUsActivity.class));
                break;

            case R.id.update_profile:
                Intent intent = new Intent(HomeScreenUser.this, ViewProfileStudent.class);
                startActivity(intent);
                break;

            case R.id.send_notification:
                SendNotification();
                break;

            case R.id.apply_leave:
                ApplyForLeave();
                break;

            case R.id.logout:
                SignOut();
                break;
//            case R.id.nav_view:
//                navigationView.setItemBackgroundResource(R.drawable.call);
//                break;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void ApplyForLeave() {
//
        Intent intent = new Intent(HomeScreenUser.this, LeaveApply.class);
        intent.putExtra("name", userName);
        intent.putExtra("surname", surname);
        startActivity(intent);
    }

    public void SendNotification() {
        Intent intent = new Intent(this, SendMessage.class);
        startActivity(intent);
    }

    public void signRegister() {

        //current date
        final Calendar calendar = Calendar.getInstance();
        System.out.println("Current time => " + calendar.getTime());
        final SimpleDateFormat df = new SimpleDateFormat("dd, MMMM , yyyy");

        //time
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+02:00"));
        final Date currentLocalTime = cal.getTime();
        final DateFormat date = new SimpleDateFormat("KK:mm:ss");
        date.setTimeZone(TimeZone.getTimeZone("GMT+02:00"));

        //month
        final SimpleDateFormat month = new SimpleDateFormat("MMMM");
        final String month_year = month.format(calendar.getTime());

        //week
        week_number = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        final String weekNumber = Integer.toString(week_number);

        //Day
        final SimpleDateFormat Day = new SimpleDateFormat("EEEE");
        weekdays = Day.format(calendar.getTime());

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeScreenUser.this);
        // Setting Dialog Title
        alertDialog.setTitle("     Mark Register");

        // Setting Dialog Message
        alertDialog.setMessage("You want to sign IN ot OUT.?");

        alertDialog.setPositiveButton("Sign IN \t", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
//                Intent intent1 = new Intent(HomeScreenUser.this, MapsActivity.class);
//                startActivity(intent1);

                //Getting content for email
//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                String email = "lseboya101@gmail.com";
//                String subject = "Att Register ";
////            String message = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + " is now " + "Present \n " + currentDateTimeString;
//                String message = userName + " is now " + "Absent (NOT IN) \n " + currentDateTimeString;
//
//                //Toast.makeText(HomeScreenUser.this," " +message,Toast.LENGTH_LONG).show();
//                //Creating SendMail object
//                SendMail sm = new SendMail(HomeScreenUser.this, email, subject, message);
//
//                //Executing sendmail to send email
//                sm.execute();

                if (!df.format(calendar.getTime()).equals(formattedDate)) {

                    time_in = date.format(currentLocalTime);
                    formattedDate = df.format(calendar.getTime());
                    Sign = "Sign_in";


                    // db= database.getReference().child(month_year);

//Register regi = new Register(formattedDate,weekdays,month_year,time_in,time_out,weekNumber);

//                    db.child(user.getUid()).child("Register");


                    Register register = new Register(formattedDate, weekdays, month_year, time_in, time_out, weekNumber);
                    Map<String, Object> registerValues = register.toMap();

                    Map<String, Object> childUpdate = new HashMap<>();
                    childUpdate.put(userID, registerValues);
                    db.child(month_year).child("Week" + weekNumber).child("Weekdays").child(weekdays).updateChildren(childUpdate);

                    mark_reg = (ImageView)findViewById(R.id.mark_reg);
                    mark_reg.setImageResource(R.drawable.in);
//                    mark_reg.setImageResource(R.drawable.call);
                   /* db.child(user.getUid()).child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("month").setValue(month_year);
                    db.child(user.getUid()).child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("weekNumbr").setValue(weekNumber);
                    db.child(user.getUid()).child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("day").setValue(weekdays);
                    db.child(user.getUid()).child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("date").setValue(formattedDate);
                    db.child(user.getUid()).child(month_year).child("Week" + weekNumber).child(weekNumber).child("Days").child(weekdays).child("timeIn").setValue(time_in);
*/

                  /* db.child(user.getUid()).child("Register");
                    db.child(user.getUid()).child("Register").child("Month").setValue(month_year);
                    db.child(user.getUid()).child("Register").child("Week").setValue(weekNumber);
                    db.child(user.getUid()).child("Register").child("Days").setValue(weekdays);
                            db.child(user.getUid()).child("Register").child("month").setValue(month_year);*/
                    //  db.child(user.getUid()).child("Month").child(month_year).child("Week").child(weekNumber).child("Days").child(weekdays).child("weekNumbr").setValue(weekNumber);
                    // db.child(user.getUid()).child("Month").child(month_year).child("Week").child(weekNumber).child("Days").child(weekdays).child("day").setValue(weekdays);

                    /**
                     * save to fireBase
                     * Month
                     * Week number
                     * Day of the week
                     * Date
                     * Time in
                     */
                    Toast.makeText(HomeScreenUser.this, "Signed in", Toast.LENGTH_SHORT).show();
                } else if (!Day.format(calendar.getTime()).equals(weekdays)) {
                    Sign = "Sign_in";


                    /**
                     * save to fireBase
                     * Date
                     * Time in
                     */
                } else {
                    Toast.makeText(HomeScreenUser.this, "you already signed in for today", Toast.LENGTH_LONG).show();

                }
            }
        });
        alertDialog.setNegativeButton("Sign Out", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                Register register = new Register( formattedDate, weekdays,  month_year,  time_in,  time_out,  weekNumber);
//                Map<String , Object >registerValues = register.toMap();
//                Map<String , Object> childUpdate = new HashMap<>();
//                childUpdate.put(userID,registerValues);
//                db.child(month_year).child("Week" + weekNumber).child("Weekdays").child(weekdays).updateChildren(childUpdate);

                //Toast.makeText(HomeScreenUser.this,"Registered",Toast.LENGTH_SHORT).show();
//                Intent intent1 = new Intent(HomeScreenUser.this, MapsActivity.class);
//                startActivity(intent1);

                //Getting content for email
//                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//                String email = "lseboya101@gmail.com";
//                String subject = "Att Register ";
////            String message = FirebaseAuth.getInstance().getCurrentUser().getDisplayName() + " is now " + "Present \n " + currentDateTimeString;
//                String message = userName + " is now " + "Signed out \n " + currentDateTimeString;
//
//               // Toast.makeText(HomeScreenUser.this," " +message,Toast.LENGTH_LONG).show();
//                //Creating SendMail object
//                SendMail sm = new SendMail(HomeScreenUser.this, email, subject, message);
//
//                //Executing sendmail to send email
//                sm.execute();
                Sign = "Sign_out";

                if (time_in.equals("")) {
                    Toast.makeText(HomeScreenUser.this, "you didnt sign in", Toast.LENGTH_LONG).show();
                } else {
                    weekdays = Day.format(calendar.getTime());
                    time_out = date.format(currentLocalTime);

                    db.child(month_year).child("Week" + weekNumber).child("Weekdays").child(weekdays).child(userID).child("timeOut").setValue(time_out);

                    mark_reg = (ImageView)findViewById(R.id.mark_reg);
                    mark_reg.setImageResource(R.drawable.out);

                    Toast.makeText(HomeScreenUser.this, "Signed in", Toast.LENGTH_SHORT).show();
                    time_in = "";
                }
            }
        });

        // Setting Netural "Cancel" Button
        alertDialog.setNeutralButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting image to ImageView
                profile_Pic.setImageBitmap(bitmap);
                imageView.setImageBitmap(bitmap);

                // uploading
                if (filePath != null) {
                    pd.show();

                    StorageReference childRef = storageRef.child(user.getUid() + ".jpg");

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot task) {
                            Uri downloadUrl = task.getDownloadUrl();
                            assert downloadUrl != null;
                            String profile_url = downloadUrl.toString();


                            pd.dismiss();
                            Toast.makeText(HomeScreenUser.this, "Upload successful", Toast.LENGTH_SHORT).show();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setPhotoUri(Uri.parse(profile_url))
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                            }
                                        }
                                    });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(HomeScreenUser.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(HomeScreenUser.this, "Select an image", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void SignOut() {

        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}

