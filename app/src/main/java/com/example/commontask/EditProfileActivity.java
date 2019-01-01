package com.example.commontask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commontask.Helper.Helper;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity{

    private static final String TAG = EditProfileActivity.class.getSimpleName();
    private EditText editProfileName;
    private EditText editProfileVillage;
    private EditText editProfilePhoneNumber;
    private EditText editProfileAge;
    private EditText editProfileEmail;
    String test,userTestEmail;
    private DatabaseReference mCurrentUserDatabaseReference1;
    Animation shake;
    private Vibrator vib;
    String name,location,field,job,ageuser,use,userTestField, userTestLocation, userTestAge,userTestName,userTestkind,userTestJob,userTestUse;
    private FirebaseAuth mFirebaseAuth;
    private String currentUserEmail;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseDatabase mFirebaseDatabase1;
    private DatabaseReference mCurrentUserDatabaseReference;
    Context context;
    private ProgressDialog mProgress;
    private TextInputLayout namel, local, emaill, phonel,surnamel,agel,jobl;
    ArrayAdapter<CharSequence> arrayAdapter,arrayAdapter1;
    Button saveEditButton;
    Query query;
    BetterSpinner spinner1,spinner2,spinner3,spinner4;
    ImageButton mOrder;
    EditText mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    String item2;
    TextView textView6;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    ArrayList<Integer> mUserItems = new ArrayList<>();
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageView checkmark = (ImageView) findViewById(R.id.saveChanges);
        editProfileName = (EditText)findViewById(R.id.name);
        editProfileEmail= (EditText)findViewById(R.id.email);

        spinner1=(BetterSpinner) findViewById(R.id.location);
        spinner2=(BetterSpinner) findViewById(R.id.age);
        spinner3=(BetterSpinner) findViewById(R.id.jobin);
        spinner4=(BetterSpinner)  findViewById(R.id.categoryuse);
        String[] list1 = getResources().getStringArray(R.array.villages);
        String[] list2 = getResources().getStringArray(R.array.age);
        String[] list3 = getResources().getStringArray(R.array.job);
        String[] list4 = getResources().getStringArray(R.array.use_truck);

        agel=(TextInputLayout)  findViewById(R.id.ag);
        namel = (TextInputLayout) findViewById(R.id.singup);
        local = (TextInputLayout) findViewById(R.id.loc);
        jobl = (TextInputLayout) findViewById(R.id.jobout);

        imageButton=(ImageButton)  findViewById(R.id.alert);


        imageButton.setOnClickListener(new View.OnClickListener() {
                          @Override
                       public void onClick(View view) {

    AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfileActivity.this);
         builder1.setTitle("Υπενθύμιση Λίπανσης Αλκυστήρα");
         builder1.setMessage("Η ελαφρά χρήση ενός ελκυστήρα σημαίνει ότι υπάρχει ανάγκη λίπανησης κάθε τρείς μήνες...Μέτρια χρήση του ελκυστήρα σημαίνει ότι υπάρχει ανάγκη λίπανησης κάθε έξι μήνες  και η βαριά χρήση ενός ελκυστήρα σημαίνει ότι υπάρχει ανάγκη λίπανησης κάθε δώδεκα μήνες!");
         builder1.setCancelable(true);
         builder1.setNeutralButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int id) {
                                 dialog.cancel();
                                    }
                                  });

                              AlertDialog alert11 = builder1.create();
                              alert11.show();
                                               }
                                           });

           ImageView backArrow = (ImageView) findViewById(R.id.backArrow);


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                finish();
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list1);
        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list2);
        spinner2.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list3);
        spinner3.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list4);
        spinner4.setAdapter(adapter3);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner1.getAdapter().getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             spinner2.getAdapter().getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner3.getAdapter().getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION
                        + "/" + currentUserEmail);

        query = mCurrentUserDatabaseReference;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                test= EmailEncoding.commaDecodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
                userTestEmail = ((HashMap) dataSnapshot.getValue()).get("email").toString();
                if (userTestEmail.equalsIgnoreCase(test)) {

                    userTestName = ((HashMap) dataSnapshot.getValue()).get("username").toString();
                    userTestLocation = ((HashMap) dataSnapshot.getValue()).get("location").toString();
                    userTestEmail = ((HashMap) dataSnapshot.getValue()).get("email").toString();
                    userTestAge = ((HashMap) dataSnapshot.getValue()).get("age").toString();
                    userTestJob = ((HashMap) dataSnapshot.getValue()).get("job").toString();
                    userTestUse = ((HashMap) dataSnapshot.getValue()).get("use").toString();

                   if(userTestName!=null){
                       editProfileName.setText(userTestName);
                   }

                    if(userTestLocation!=null){
                        spinner1.setText(userTestLocation);
                    }
                    if(userTestAge!=null){
                        spinner2.setText(userTestAge);
                    }
                    if(userTestJob!=null){
                        spinner3.setText(userTestJob);
                    }
                    if(userTestUse!=null){
                        spinner4.setText(userTestUse);
                    }

                    if(userTestEmail!=null){
                        editProfileEmail.setText(userTestEmail);
                        editProfileEmail.setEnabled(false);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update();
            }
        });

        }

    private void update() {

         name = editProfileName.getText().toString();
         location = spinner1.getText().toString();
         ageuser = spinner2.getText().toString();
         job= spinner3.getText().toString();
         use= spinner4.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(job)|| TextUtils.isEmpty(ageuser)|| TextUtils.isEmpty(use)) {
                    Helper.displayMessageToast(EditProfileActivity.this, "Όλα τα πεδία πρέπει να ειναι συμπληρωμένα!");

                }

                    if (!validate()) {
                        onSignupFailed();
                    }

                 else{

                        Query  query = mCurrentUserDatabaseReference;

                        query.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot snapshot) {

                                userTestEmail = ((HashMap) snapshot.getValue()).get("email").toString();
                                String currentUserEmailfinal= EmailEncoding.commaDecodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
                                if (userTestEmail.equals(currentUserEmailfinal)) {
                                    mCurrentUserDatabaseReference.child("username").setValue(name);
                                    mCurrentUserDatabaseReference.child("location").setValue(location);
                                    mCurrentUserDatabaseReference.child("age").setValue(ageuser);
                                    mCurrentUserDatabaseReference.child("job").setValue(job);
                                    mCurrentUserDatabaseReference.child("use").setValue(use);
                                }

                                Intent intent = new Intent(getApplicationContext(),com.example.commontask.ui.ProfileActivity.class);
                                onSignupSuccess();

                                intent.putExtra("username", name);
                                intent.putExtra("location",location);
                                intent.putExtra("age",ageuser);
                                intent.putExtra("job",job);
                                intent.putExtra("use",use);
                                startActivity(intent);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

    }

    private void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Επιτυχής Ενημέρωση Στοιχείων Προφίλ!", Toast.LENGTH_LONG).show();
    }

    private void onSignupFailed() {
            Toast.makeText(getBaseContext(), "Ανεπιτυχής Ενημέρωση Στοιχείων Προφίλ!", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String n = editProfileName.getText().toString();

        if (n.isEmpty() || n.length() < 5) {
            editProfileName.setAnimation(shake);
            editProfileName.startAnimation(shake);
            vib.vibrate(180);
            editProfileName.setError("Το όνομα πρέπει να περιέχει τουλαχιστόν 5 χαρακτήρες..");
            valid = false;
        } else {
            editProfileName.setError(null);
        }

        return valid;
    }

    public String getUid() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        return currentUserEmail;
    }
}



