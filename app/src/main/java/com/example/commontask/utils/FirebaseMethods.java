package com.example.commontask.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.commontask.Home.HomeActivity;
import com.example.commontask.Home.HomeFragment;
import com.example.commontask.Profile.AccountSettingsActivity;
import com.example.commontask.R;
import com.example.commontask.materialcamera.MaterialCamera;
import com.example.commontask.model.User;
import com.example.commontask.models.Photo;
import com.example.commontask.models.Story;
import com.example.commontask.models.UserAccountSettings;
import com.example.commontask.models.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;
    private DatabaseReference mCurrentUserDatabaseReference;
    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_LOCATION);

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    public void uploadNewPhoto(String photoType, final String caption,final int count, final String imgUrl,
                               Bitmap bm){
        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");

        FilePaths filePaths = new FilePaths();
        //case1) new photo
        if(photoType.equals(mContext.getString(R.string.new_photo))){
            Log.d(TAG, "uploadNewPhoto: uploading NEW photo.");

            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));

            //convert image url to bitmap
            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);
            }

            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "Image upload!", Toast.LENGTH_SHORT).show();

                    //add the new photo to 'photos' node and 'user_photos' node
                    addPhotoToDatabase(caption, firebaseUrl.toString());

                    //navigate to the main feed so the user can see their photo
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    mContext.startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Photo upload failed.");
                    Toast.makeText(mContext, "Image not upload ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "Photo upload mode: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });

        }
        //case new profile photo
        else if(photoType.equals(mContext.getString(R.string.profile_photo))){
            Log.d(TAG, "uploadNewPhoto: uploading new PROFILE photo");


            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");

            //convert image url to bitmap
            if(bm == null){
                bm = ImageManager.getBitmap(imgUrl);
            }
            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();

                    Toast.makeText(mContext, "Image upload!", Toast.LENGTH_SHORT).show();

                    //insert into 'user_account_settings' node
                    setProfilePhoto(firebaseUrl.toString());

                    ((AccountSettingsActivity)mContext).setViewPager(
                            ((AccountSettingsActivity)mContext).pagerAdapter
                                    .getFragmentNumber(mContext.getString(R.string.edit_profile_fragment))
                    );

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: Photo upload failed.");
                    Toast.makeText(mContext, "Image not upload! ", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    if(progress - 15 > mPhotoUploadProgress){
                        Toast.makeText(mContext, "Photo upload mode: " + String.format("%.0f", progress) + "%", Toast.LENGTH_SHORT).show();
                        mPhotoUploadProgress = progress;
                    }

                    Log.d(TAG, "onProgress: upload progress: " + progress + "% done");
                }
            });
        }

    }

    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mContext.getString(R.string.profile_photo))
                .setValue(url);
    }

    public void uploadNewStory(Intent intent, final HomeFragment fragment){
        Log.d(TAG, "uploadNewStory: attempting to upload new story to storage.");

        final String uri = intent.getDataString();
        final boolean deleteCompressedVideo = intent.getBooleanExtra(MaterialCamera.DELETE_UPLOAD_FILE_EXTRA, false);
         /*
            upload a new photo to firebase storage
         */
        if(!isMediaVideo(uri)){
            Log.d(TAG, "uploadNewStory: uploading new story (IMAGE) to firebase storage.");
            fragment.mStoriesAdapter.startProgressBar();
            FilePaths filePaths = new FilePaths();

            //specify where the photo will be stored
            final StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_STORY_STORAGE + "/" + userID + "/" + uri.substring(uri.indexOf("Stories/") + 8, uri.indexOf(".")));

            BackgroundGetBytesFromBitmap getBytes = new BackgroundGetBytesFromBitmap();
            byte[] bytes = getBytes.doInBackground(uri);

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseURL = taskSnapshot.getDownloadUrl();
                    fragment.mStoriesAdapter.stopProgressBar();
                    Toast.makeText(mContext, "Upload Success", Toast.LENGTH_SHORT).show();
                    addNewStoryImageToDatabase(firebaseURL.toString());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    fragment.mStoriesAdapter.stopProgressBar();
                    Toast.makeText(mContext, "Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }
        else{
            Log.d(TAG, "uploadNewStory: uploading new story (VIDEO) to firebase storage.");
            fragment.mStoriesAdapter.startProgressBar();
            FilePaths filePaths = new FilePaths();

            //specify where the photo will be stored
            final StorageReference storageReference = mStorageReference
                    .child(filePaths.FIREBASE_STORY_STORAGE + "/" + userID + "/" + uri.substring(uri.indexOf("Stories/") + 8, uri.indexOf(".")));


            FileInputStream fis = null;
            File file = new File(uri);
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] bytes = new byte[0];
            try {
                bytes = readBytes(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "uploadNewStory: video upload bytes: " + bytes.length);
            final byte[] uploadBytes = bytes;

            UploadTask uploadTask = null;
            uploadTask = storageReference.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri firebaseURL = taskSnapshot.getDownloadUrl();
                    fragment.mStoriesAdapter.stopProgressBar();
                    Toast.makeText(mContext, "Upload Success", Toast.LENGTH_SHORT).show();
                    addNewStoryVideoToDatabase(firebaseURL.toString(), uploadBytes);

                    if(deleteCompressedVideo){
                        deleteOutputFile(uri);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    fragment.mStoriesAdapter.stopProgressBar();
                    Toast.makeText(mContext, "Upload Failed", Toast.LENGTH_SHORT).show();
                    if(deleteCompressedVideo){
                        deleteOutputFile(uri);
                    }
                }
            });
        }
    }

    private void deleteOutputFile(@Nullable String uri) {
        if (uri != null)
            //noinspection ResultOfMethodCallIgnored
            new File(Uri.parse(uri).getPath()).delete();
    }

    public byte[] readBytes(FileInputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }


    private class BackgroundGetBytesFromBitmap extends AsyncTask<String, Integer, byte[]> {

        @Override
        protected byte[] doInBackground(String... params) {
            byte[] bytes = null;

//            Bitmap bm = ImageManager.getBitmap(Uri.parse(params[0]).getPath());
            Bitmap bm = null;
            try{
                RotateBitmap rotateBitmap = new RotateBitmap();
                bm = rotateBitmap.HandleSamplingAndRotationBitmap(mContext, Uri.parse("file://" + params[0]));
            }catch (IOException e){
                Log.e(TAG, "BackgroundGetBytesFromBitmap: IOException: " + e.getMessage());
            }

            bytes = ImageManager.getBytesFromBitmap(bm, ImageManager.IMAGE_SAVE_QUALITY);
            return bytes;
        }
    }

    private void addNewStoryImageToDatabase(String url){
        Log.d(TAG, "addNewStoryToDatabase: adding new story to database.");

        Story story =  new Story();
        story.setImage_url(url);
        String newKey = myRef.push().getKey();
        story.setStory_id(newKey);
        story.setTimestamp(getTimestamp());
        story.setUser_id(userID);
        story.setViews("0");

        myRef.child(mContext.getString(R.string.dbname_stories))
                .child(userID)
                .child(newKey)
                .setValue(story);

    }

    private void addNewStoryVideoToDatabase(String url, byte[] bytes){
        Log.d(TAG, "addNewStoryToDatabase: adding new story to database.");

        Story story =  new Story();
        story.setVideo_url(url);
        String newKey = myRef.push().getKey();
        story.setStory_id(newKey);
        story.setTimestamp(getTimestamp());
        story.setUser_id(userID);
        story.setViews("0");

        // calculate the estimated duration.
        // need to do this for the progress bars in the block. We can't get the video duration of MP4 files
        double megabytes = bytes.length / 1000000.000;
        Log.d(TAG, "addNewStoryVideoToDatabase: estimated MB: " + megabytes);
        String duration = String.valueOf(Math.round(15 * (megabytes / 6.3)));
        Log.d(TAG, "addNewStoryVideoToDatabase: estimated video duration: " + duration);
        story.setDuration(duration);

        myRef.child(mContext.getString(R.string.dbname_stories))
                .child(userID)
                .child(newKey)
                .setValue(story);

    }

    private boolean isMediaVideo(String uri){
        if(uri.contains(".mp4") || uri.contains(".wmv") || uri.contains(".flv") || uri.contains(".avi")){
            return true;
        }
        return false;
    }




    /**
     * Register a new email and password to Firebase Authentication
     * @param email
     * @param password
     */
    public void registerNewEmail(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();

                        }
                        else if(task.isSuccessful()){
                            //send verificaton email
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }


    public void createUser(FirebaseUser user) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        final DatabaseReference usersRef = mFirebaseDatabase.getReference(Constants.USERS_LOCATION);
        final DatabaseReference userssaccountRef = mFirebaseDatabase.getReference(Constants.USERS_ACCOUNTS);
        final String user_id =currentFirebaseUser.getUid();
        final String encodedEmail = EmailEncoding.commaEncodePeriod(mAuth.getCurrentUser().getEmail());
        final DatabaseReference userRef = usersRef.child(encodedEmail);
        final DatabaseReference useraccountRef = userssaccountRef.child(user_id);
        final String username = "";
        final String profilePicLocation="";
        final String field="";
        final String finalemail= EmailEncoding.commaDecodePeriod(mAuth.getCurrentUser().getEmail());
        final String location="";
        final String age="";
        final int score=0;
        final String kind="";
        final String job="";
        final String use="";
        final int position=0;

        final String description=" ";
        final String display_name=" ";
        final long followers=0;
        final long following=0;
        final long posts=0;
        final String profile_photo=" ";
        final String website=" ";

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    User newUser = new User(user_id,finalemail,username, profilePicLocation,location,age,score,kind,job,use);
                    userRef.setValue(newUser);
                }
                if (dataSnapshot.getValue() == null) {
                    UserAccountSettings userAccountSettings = new UserAccountSettings(description,display_name, followers,following,posts,profile_photo,username,website,user_id);
                    useraccountRef.setValue(userAccountSettings);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private String getTimestamp(){
        Locale locale = new Locale("el-GR");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",locale);
        return sdf.format(new Date());
    }

    private void addPhotoToDatabase(String caption, String url){
        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

        String tags = StringManipulation.getTags(caption);
        String newPhotoKey = myRef.child(mContext.getString(R.string.dbname_photos)).push().getKey();
        Photo photo = new Photo();
        photo.setCaption(caption);
        photo.setDate_created(getTimestamp());
        photo.setImage_path(url);
        photo.setTags(tags);
        photo.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
        photo.setPhoto_id(newPhotoKey);

        //insert into database
        myRef.child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser()
                        .getUid()).child(newPhotoKey).setValue(photo);
        myRef.child(mContext.getString(R.string.dbname_photos)).child(newPhotoKey).setValue(photo);

    }

    public int getImageCount(DataSnapshot dataSnapshot){
        int count = 0;
        for(DataSnapshot ds: dataSnapshot
                .child(mContext.getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .getChildren()){
            count++;
        }
        return count;
    }

    public void updateUserAccountSettings(String displayName, String website, String description, String phoneNumber){

        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");

        if(displayName != null){
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_display_name))
                    .setValue(displayName);
        }


        if(website != null) {
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_website))
                    .setValue(website);
        }

        if(description != null) {
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_description))
                    .setValue(description);
        }

        if(phoneNumber != null) {
            myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                    .child(userID)
                    .child(mContext.getString(R.string.field_phone_number))
                    .setValue(phoneNumber);
        }
    }

    /**
     * update username in the 'users' node and 'user_account_settings' node
     * @param username
     */
    public void updateUsername(String username){
        Log.d(TAG, "updateUsername: upadting username to: " + username);
        String  currentUserEmail = EmailEncoding.commaEncodePeriod(mAuth.getCurrentUser().getEmail());

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(currentUserEmail)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }

    /**
     * update the email in the 'user's' node
     * @param email
     */
    public void updateEmail(String email){
        Log.d(TAG, "updateEmail: upadting email to: " + email);

        myRef.child(mContext.getString(R.string.dbname_users))
                .child(userID)
                .child(mContext.getString(R.string.field_email))
                .setValue(email);

    }



    /**
     * Add information to the users nodes
     * Add information to the user_account_settings node
     * @param email
     * @param username
     * @param description
     * @param website
     * @param profile_photo
     */


    /**
     * Retrieves the account settings for teh user currently logged in
     * Database: user_acount_Settings node
     * @param dataSnapshot
     * @return
     */
    public UserSettings getUserSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase.");


        UserAccountSettings settings  = new UserAccountSettings();
        User user = new User();

        for(DataSnapshot ds: dataSnapshot.getChildren()){

            // user_account_settings node
            if(ds.getKey().equals(mContext.getString(R.string.dbname_user_account_settings))) {
                Log.d(TAG, "getUserAccountSettings: user account settings node datasnapshot: " + ds);

                try {

                    settings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDisplay_name()
                    );
                    settings.setUsername(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getUsername()
                    );
                    settings.setWebsite(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getWebsite()
                    );
                    settings.setDescription(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getDescription()
                    );
                    settings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getProfile_photo()
                    );
                    settings.setPosts(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getPosts()
                    );
                    settings.setFollowing(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowing()
                    );
                    settings.setFollowers(
                            ds.child(userID)
                                    .getValue(UserAccountSettings.class)
                                    .getFollowers()
                    );

                    Log.d(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + settings.toString());
                } catch (NullPointerException e) {
                    Log.e(TAG, "getUserAccountSettings: NullPointerException: " + e.getMessage());
                }
            }


         String  currentUserEmail = EmailEncoding.commaEncodePeriod(mAuth.getCurrentUser().getEmail());

            mCurrentUserDatabaseReference = mFirebaseDatabase
                    .getReference().child(Constants.USERS_LOCATION
                            + "/" + currentUserEmail);
            Query query = mCurrentUserDatabaseReference;
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String userTestEmail = ((HashMap) dataSnapshot.getValue()).get("user_id").toString();
                    if(userTestEmail.equalsIgnoreCase(userID)){
                        User user = new User();

                        user.setUsername((String) ((HashMap) dataSnapshot.getValue()).get("username"));
                        user.setEmail((String) ((HashMap) dataSnapshot.getValue()).get("email"));
                        user.setField((String) ((HashMap) dataSnapshot.getValue()).get("field"));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        return new UserSettings(user, settings);

    }

}













































