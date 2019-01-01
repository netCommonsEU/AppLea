package com.example.commontask;


import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.commontask.ui.ProfileActivity;
import com.example.commontask.utils.EmailEncoding;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import butterknife.BindDimen;
import butterknife.BindView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class BaseDrawerActivity extends BaseActivityProfile {
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.vNavigation)
    NavigationView vNavigation;

    private StorageReference mStorage;
    private static final String TAG = "ProfileActivity";
    private FirebaseAuth mFirebaseAuth;
    private String currentUserEmail,currentUserName;
    private ImageView profileImage;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mCurrentUserDatabaseReference;
    private DatabaseReference mCurrentUserDatabaseReference1;

    @BindDimen(R.dimen.global_menu_avatar_size)
    int avatarSize;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentViewWithoutInject(R.layout.activity_drawer);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
        LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
        bindViews();
        setupHeader();
    }

    @Override
    protected void setupToolbar() {
        super.setupToolbar();
        if (getToolbar() != null) {
            getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }
    }

    private void setupHeader() {
        View headerView = vNavigation.getHeaderView(0);
        headerView.findViewById(R.id.vGlobalMenuHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGlobalMenuHeaderClick(v);
            }
        });

        final ImageView imageView = (ImageView) findViewById(R.id.profilePicture);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(com.example.commontask.utils.Constants.USERS_LOCATION
                        + "/" + currentUserEmail);
        mCurrentUserDatabaseReference
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        com.example.commontask.model.User user = dataSnapshot.getValue(com.example.commontask.model.User.class);
                        try{
                            if(user.getProfilePicLocation() != null){
                                StorageReference storageRef = FirebaseStorage.getInstance()
                                        .getReference().child(user.getProfilePicLocation());

                                Glide.with(getApplicationContext())
                                       // .using(new FirebaseImageLoader())
                                        .load(storageRef)
                                       // .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                        .into(imageView);
                            }
                        }catch (Exception e){
                            Log.e("Err", "glide");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void onGlobalMenuHeaderClick(final View v) {
        drawerLayout.closeDrawer(Gravity.RIGHT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int[] startingLocation = new int[2];
                v.getLocationOnScreen(startingLocation);
                startingLocation[0] += v.getWidth() / 2;
                ProfileActivity.startUserProfileFromLocation(startingLocation, BaseDrawerActivity.this);
                overridePendingTransition(0, 0);
            }
        }, 200);
    }

}
