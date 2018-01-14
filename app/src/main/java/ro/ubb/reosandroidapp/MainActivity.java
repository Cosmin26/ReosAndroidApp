package ro.ubb.reosandroidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ubb.reosandroidapp.globals.Globals;
import ro.ubb.reosandroidapp.model.Apartment;
import ro.ubb.reosandroidapp.repository.ApartmentRepository;
import ro.ubb.reosandroidapp.service.OnGetDataListener;
//import ro.ubb.reosandroidapp.repository.ApartmentRepository;
//import ro.ubb.reosandroidapp.repository.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView authFailed;
    private Button loginButton,registerButton;
    private EditText emailEdit,passwordEdit;
    private static final String TAG = "LOGIN_SIGNUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn(emailEdit.getText().toString(), passwordEdit.getText().toString());
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(emailEdit.getText().toString(),passwordEdit.getText().toString());
            }
        });

//        if(Globals.apartmentRepository == null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
//                    Globals.apartmentRepository = new ApartmentRepository(appDatabase);
//                }
//            }).start();
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

//    public void btnClick(View view) {
//        EditText emailEditText = findViewById(R.id.email);
//        EditText passwordEditText = findViewById(R.id.password);
//
//        String email = emailEditText.getText().toString();
//        String pass = passwordEditText.getText().toString();
//
//        String oemail = "admin";
//        String opass = "admin";
//
//        if (email.equals(oemail) && pass.equals(opass)) {
//            Intent intent = new Intent(this, Navigation.class);
//            startActivity(intent);
//
//        } else {
//            Toast toast = Toast.makeText(this, "Invalid Email/Pass", Toast.LENGTH_LONG);
//            toast.show();
//        }
//    }

    public void logIn(String email, String password){
        Log.d(TAG,"log in: "+email);

        //TODO validate

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //log in successful,updated UI with the user information
                    FirebaseUser user= mAuth.getCurrentUser();

                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    rootRef.child("admins").child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String email=dataSnapshot.getValue(String.class);

                            Globals.personId = mAuth.getCurrentUser().getUid();
                            if(email.equals(mAuth.getCurrentUser().getEmail())){
                                loggedIn(true);
                            }
                            else{
                                loggedIn(false);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    //log in fails
                    Toast.makeText(MainActivity.this,"Authentication failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void register(String email,String password){
        //TODO validate email and password

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
//                    DatabaseReference myDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users");
//                    String userId = myDatabaseReference.push().getKey();
//                    String apartmentsId= FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("apartments").push().getKey();
//                    setValue(user.getUid())

//                    Map<String, Map<String, Apartment>> apartmentMap = new HashMap<>();
//                    apartmentMap.put("apartments", new HashMap<String, Apartment>());
//                    myDatabaseReference.child(personId).child("apartments").push();

                    //register successful

                    loggedIn(false);
                }
                else{
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this,"Authentication failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loggedIn(final boolean isAdmin){
        Globals.apartmentRepository = new ApartmentRepository(Globals.isAdmin, new OnGetDataListener() {
            @Override
            public void onStart() {
                //DO SOME THING WHEN START GET DATA HERE
            }

            @Override
            public void onSuccess(DataSnapshot data) {
                Intent intent;
                intent = new Intent(MainActivity.this,Navigation.class);
                Globals.isAdmin = isAdmin;
                startActivity(intent);
            }

            @Override
            public void onFailed(DatabaseError databaseError) {
                //DO SOME THING WHEN GET DATA FAILED HERE
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
