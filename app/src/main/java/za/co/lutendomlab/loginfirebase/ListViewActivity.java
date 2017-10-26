package za.co.lutendomlab.loginfirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
<<<<<<< HEAD
import android.widget.ListView;
import android.widget.TextView;
=======
import android.widget.ImageView;
import android.widget.ListView;
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends android.support.v4.app.Fragment {

    private ListView listView;
<<<<<<< HEAD
    private TextView txtTotalNumber;
=======
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
    private UserAdapter userAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ValueEventListener childEventListener;
    FirebaseAuth firebaseAuth;
<<<<<<< HEAD

    List<User> allUsers = new ArrayList<>();
    User user;

    Context context;
    int counter;

=======
    List<User> allUsers = new ArrayList<>();
    User user;
    ImageView call;

    Context context;
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.activity_list_of_users, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

<<<<<<< HEAD
        txtTotalNumber = (TextView)rootview.findViewById(R.id.txtTotalNumber);
=======

>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000

//        context = getApplicationContext();
        listView = (ListView) rootview.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                User user = allUsers.get(position);
<<<<<<< HEAD
=======

>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
                Intent intent = new Intent(context,AdminOptionStudents.class);
                intent.putExtra("userProfile",user);
                startActivity(intent);

            }
        });

<<<<<<< HEAD
=======


>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference().child("User");

        childEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Log.i("Ygritte", snapshot.toString());

                    user  = snapshot.getValue(User.class);

                    if("Codetribe TIH".equals(user.getFacility()) && "Student".equals(user.getRole())) {
                        allUsers.add(user);
<<<<<<< HEAD
                        counter++;
                    }
                }
                txtTotalNumber.setText(""+ counter);

=======
                    }
                }
>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
                userAdapter = new UserAdapter(context,R.layout.model,allUsers);
                listView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(childEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReference.removeEventListener(childEventListener);
    }
<<<<<<< HEAD
=======


>>>>>>> e337382d1cb35965593f8aea6d788a41f46ba000
}
