package com.example.commontask.fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dd.morphingbutton.MorphingButton;
import com.example.commontask.BottomNavigationViewHelper;
import com.example.commontask.FilesAdapter;
import com.example.commontask.FilterTask;
import com.example.commontask.MainActivityCalendar;
import com.example.commontask.MainActivityPdf;
import com.example.commontask.PostDetailActivity;

import com.example.commontask.R;
import com.example.commontask.TaskUpdate;
import com.example.commontask.model.Post;
import com.example.commontask.utils.Constants;
import com.example.commontask.utils.EmailEncoding;
import com.example.commontask.viewholder.PostViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static java.util.Collections.singletonList;


public  class PostListFragment extends AppCompatActivity {

    private static final String TAG = "PostListFragment";
    private DatabaseReference mCurrentUserDatabaseReference;


    // [START define_database_reference]
    private DatabaseReference mDatabase;

    ArrayList<String> imagesUri;
    File pdfDir;
    // [END define_database_reference]
    public FirebaseRecyclerAdapter<Post, PostViewHolder> mAdapter;
    public RecyclerView mRecycler;
    private LinearLayoutManager mManager;
    private FirebaseDatabase mFirebaseDatabase;
    public PostListFragment() {}
    Document document;
    private Context mView;

    private FirebaseAuth mFirebaseAuth;
    private String currentUserEmail;
    ImageView imageView;

    MorphingButton createPdf;
    MorphingButton openPdf;

    Bitmap screen;
    Context activity;
    static String path;
    String filename;
    Image image;
    int temp=0;
    Bitmap b=null;
    ArrayList<String> scoresOnly;
     ImageButton imageButton;
    View u;
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
    String body,body1,body2,body3;
    View v1;
    String postid;
    Button button;
    Boolean changed;
    String string="";
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    String str4,str5,str6,str7,str8,str9,str10,str11,str12,str13,str14,str15,str16;
    Boolean flag=false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_all_posts);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        imagesUri = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();


        activity=this.getApplicationContext();
        openPdf = (MorphingButton) findViewById(R.id.pdfOpen);
        createPdf = (MorphingButton) findViewById(R.id.pdfCreate);

        morphToSquare(createPdf, integer(R.integer.mb_animation));

        openPdf.setVisibility(View.GONE);

        // [END create_database_reference]
        imageView = (ImageView)  findViewById(R.id.author_photo);
        mRecycler = (RecyclerView) findViewById(R.id.messages_list);
        mRecycler.setHasFixedSize(true);
        imageButton=(ImageButton)findViewById(R.id.sendButton);
        v1=findViewById(R.id.messages_list);

         BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

         BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

         bottomNavigationView.setItemIconTintList(null);


      Bundle extras = getIntent().getExtras();

  if (extras != null) {


         changed = true;
         flag=true;

            str4 = extras.getString("str4");
            str5 = extras.getString("str5");
            str6 = extras.getString("str6");
            str7= extras.getString("str7");
            str8= extras.getString("str8");
            str9= extras.getString("str9");
            str10= extras.getString("str10");
            str11= extras.getString("str11");
            str12= extras.getString("str12");
            str13= extras.getString("str13");
            str14= extras.getString("str14");
            str15= extras.getString("str15");
            str16= extras.getString("str16");


       if(str4!=null || str5!=null ||str6!=null || str7!=null|| str8!=null||str9!=null || str10!=null|| str11!=null||str12!=null ||str13!=null||str14!=null||str15!=null||str16!=null){

                    sort_select();

                    }
       else {

          default_query();
      }


      }

        if (extras == null) {

      default_query();

  }


        // Set up Layout Manager, reverse layout


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                        switch (item.getItemId()) {

                            case R.id.filter:

                                Intent intent = new Intent(PostListFragment.this, FilterTask.class);
                                startActivityForResult(intent, 0);

                                break;


                            case R.id.pdf:

                                // Get runtime permissions if build version >= Android M
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if ((ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                   != PackageManager.PERMISSION_GRANTED) ||
                                  (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                                   != PackageManager.PERMISSION_GRANTED) ||
                                   (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    != PackageManager.PERMISSION_GRANTED)) {
                                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                     Manifest.permission.READ_EXTERNAL_STORAGE,
                                     Manifest.permission.CAMERA},
                                  PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT);


                                    }
                                }

                                createPdf.setVisibility(View.VISIBLE);

                                createPdf.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        createPdf();
                                    }


                                });

                                openPdf.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        openPdf();
                                    }
                                });


                                break;

                        }


                        return false;
                    }
                });

    }



  void openPdf() {

        File file = new File(path);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file), getString(R.string.pdf_type));
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, getString(R.string.open_file));
        try {

            startActivity(intent);

        }
        catch (ActivityNotFoundException e) {

            Toast.makeText(activity, R.string.toast_no_pdf_app, Toast.LENGTH_LONG).show();

        }
    }

    private void morphToSquare(final MorphingButton btnMorph, int duration) {

        MorphingButton.Params square = MorphingButton.Params.create()
                .duration(duration)
                .cornerRadius(dimen(R.dimen.mb_corner_radius_2))
                .width(dimen(R.dimen.mb_width_200))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_blue))
                .colorPressed(color(R.color.mb_blue_dark))
                .text(getString(R.string.mb_button));
        btnMorph.morph(square);
    }


    // Create Pdf of selected images
    void createPdf() {

        new MaterialDialog.Builder(PostListFragment.this)
                .title(R.string.creating_pdf)
                .content(R.string.enter_file_name)
                .input(getString(R.string.example), null, new MaterialDialog.InputCallback() {


                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {

              if (input == null || input.toString().trim().equals("")) {

                      Toast.makeText(PostListFragment.this, R.string.toast_name_not_blank, Toast.LENGTH_LONG).show();
                        }

                else {

                   filename = input.toString();

                  new CreatingPdf().execute();

                        }

                    }

                })
                .show();

    }

   private void morphToSuccess(final MorphingButton btnMorph) {

        MorphingButton.Params circle = MorphingButton.Params.create()
                .duration(integer(R.integer.mb_animation))
                .cornerRadius(dimen(R.dimen.mb_height_56))
                .width(dimen(R.dimen.mb_height_56))
                .height(dimen(R.dimen.mb_height_56))
                .color(color(R.color.mb_green))
                .colorPressed(color(R.color.mb_green_dark))
                .icon(R.drawable.ic_done);

        btnMorph.morph(circle);

    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }


    public class CreatingPdf extends AsyncTask<String, String, String> {


        MaterialDialog.Builder builder = new MaterialDialog.Builder(PostListFragment.this)
                .title(R.string.please_wait)
                .content(R.string.populating_list)
                .cancelable(false)
                .progress(true, 0);

        MaterialDialog  dialog = builder.build();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();

        }

        @Override
      protected String doInBackground(String... params) {

           path = Environment.getExternalStorageDirectory().getAbsolutePath() + PostListFragment.this.getString(R.string.pdf_dir);

            File folder = new File(path);

            if (!folder.exists()) {
                boolean success = folder.mkdir();
                if (!success) {
                    Toast.makeText(activity, "Error on creating application folder", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            path = path + filename + PostListFragment.this.getString(R.string.pdf_ext);

                Log.v("stage 1", "store the pdf in sd card");

              v1.setDrawingCacheEnabled(true);
              Bitmap.createBitmap(v1.getDrawingCache());
            //int totalHeight = v1.getChildAt(0).getHeight();// parent view height
           // int  totalWidth = mRecycler.getChildAt(0).getWidth();// parent view width
              b= loadBitmapFromView(v1, v1.getWidth(), v1.getHeight());
             // v1.setDrawingCacheEnabled(false);
              FileOutputStream fos = null;

            try {
                fos = new FileOutputStream(path);
                b.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            createpdf();
           /* try {

                Log.v("stage 2", "Document Created");
               /* Document document = new Document();
                Rectangle documentRect = document.getPageSize();
                PdfWriter.getInstance(document, new FileOutputStream(path));
                document.open();*/

                Log.v("Stage 3", "Pdf writer");

              /*  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                screen.compress(Bitmap.CompressFormat.PNG, 100, stream);
                 byte[] byteArray = stream.toByteArray();

                Log.v("Stage 4", "Document opened");

                Bitmap bmp = null;
                int size =  temp;
                int height = 0;
                Paint paint = new Paint();
                int iHeight = 0;
                Log.v("Stage 41", "Document opened");//Create a directory for your PDF*/

             /*  for (int i =0; i < temp; i++) {

                    Log.v("Stage 42", "Document opened");

                    PostViewHolder holder = mAdapter.createViewHolder(mRecycler, mAdapter.getItemViewType(i));
                   // mAdapter.onBindViewHolder(holder, i);
                    holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(mRecycler.getWidth(), View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                    holder.itemView.setDrawingCacheEnabled(true);
                    holder.itemView.buildDrawingCache();

                    Log.v("Stage 433", "Document opened");

                    final int maxMemory = (int) (Runtime.getRuntime().maxMemory());
                    Log.v("Stage 44", "Document opened");

                    LruCache<String, Bitmap> bitmaCache = new LruCache<>(maxMemory);
                    Bitmap drawingCache = holder.itemView.getDrawingCache();
                    Log.v("Stage 45", "Document opened");
                     image =Image.getInstance(byteArray);

                    if (drawingCache != null) {
                        Log.v("Stage 46", "Document opened");

                        bitmaCache.put(String.valueOf(i), drawingCache);
                    }
                    height += holder.itemView.getMeasuredHeight();
                    Log.v("Stage 47", "Document opened");

                    bmp = Bitmap.createBitmap(mRecycler.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
                    Canvas bigCanvas = new Canvas(bmp);
                    bigCanvas.drawColor(Color.WHITE);
                    Log.v("Stage 48", "Document opened");

                    if (bmp.getWidth() > documentRect.getWidth()
                            || bmp.getHeight() > documentRect.getHeight()) { Log.v("Stage 412", "Document opened");

                        //bitmap is larger than page,so set bitmap's size similar to the whole page
                        image.scaleAbsolute(documentRect.getWidth(), documentRect.getHeight());
                    }

                    else {
                        //bitmap is smaller than page, so add bitmap simply.
                        //[note: if you want to fill page by stretching image,
                        // you may set size similar to page as above]
                        image.scaleAbsolute(bmp.getWidth(), bmp.getHeight());
                    }
                    Log.v("Stage 49", "Document opened");

                    Log.v("Stage 6", "Image path adding");

                  image.setAbsolutePosition(
                            (documentRect.getWidth() - image.getScaledWidth()) / 2,
                            (documentRect.getHeight() - image.getScaledHeight()) / 2);
                    Log.v("Stage 7", "Image Alignments");
                //
                    image.setBorder(Image.BOX);

                    image.setBorderWidth(5);

                    document.add(image);

                    document.newPage();
                }

                Log.v("Stage 8", "Image adding");

                Log.v("Stage 7", "Document Closed" + path);
            }*/


            /*catch (Exception e) {
                e.printStackTrace();
                document.close();
            }*/

           //

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            openPdf.setVisibility(View.VISIBLE);

            Snackbar.make(findViewById(android.R.id.content)
                    , R.string.snackbar_pdfCreated
                    , Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_viewAction, new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            ArrayList<File> list = new ArrayList<>(singletonList(new File(path)));
                            FilesAdapter filesAdapter = new FilesAdapter(activity, list);
                            filesAdapter.openFile(path);

                        }
                    }).show();


            dialog.dismiss();

            morphToSuccess(createPdf);

           /*  Intent intent = new Intent(getApplicationContext(), MainActivityPdf.class);
             startActivityForResult(intent, 0);*/


        }
    }

    public Bitmap loadBitmapFromView(View u,int totalWidth,int totalHeight) {
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                  b=Bitmap.createBitmap(totalWidth,totalHeight,Bitmap.Config.ARGB_8888);
                   Canvas c=new Canvas(b);
                   u.layout(0,0,totalWidth,totalHeight);
                   Drawable drawable=u.getBackground();


                   if(drawable!=null)
                       drawable.draw(c);
                   else
                       c.drawColor(Color.WHITE);
                   u.draw(c);

               }
           });

        return b;
    }


    private static void addImage(Document document,byte[] byteArray)
    {
        Image image = null;
        try
        {
            image = Image.getInstance(byteArray);
        }
        catch (BadElementException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (MalformedURLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // image.scaleAbsolute(150f, 150f);
        try
        {
            document.add(image);
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
  public void default_query() {

        mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


        // Set up FirebaseRecyclerAdapter with the Query
        final DatabaseReference mCurrentUserDatabaseReference1 = mFirebaseDatabase
                .getReference().child(Constants.USERS_POST).child(getUid());

        final  Query postsQuery =  mCurrentUserDatabaseReference1;

        postsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    return;
                }
                mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.item_post,
                        PostViewHolder.class, postsQuery) {

                    @Override
                    protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, final int position) {
                        final DatabaseReference postRef = getRef(position);


                        scoresOnly = new ArrayList<String>();

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Post post = postSnapshot.getValue(Post.class);
                            scoresOnly.add(post.getAuthor());
                        }

                        temp=scoresOnly.size();


           if(!model.getProfilePicLocation().equalsIgnoreCase("")){

               StorageReference storageRef = FirebaseStorage.getInstance()
                        .getReference().child(model.getProfilePicLocation());

                      /* Glide.with(getApplicationContext()).
                               using(new FirebaseImageLoader())
                               .load(storageRef).
                               asBitmap().
                               centerCrop()
                               //.bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                               .into(new BitmapImageViewTarget(viewHolder.imageView){

                                   @Override
                                   protected void setResource(Bitmap resource){

                                       super.setResource(resource);
                                   }
                               });*/


               Glide.with(getApplicationContext())
                       .using(new FirebaseImageLoader())
                       .load(storageRef)
                       .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                       .into(viewHolder.imageView);


                   }


            if(!model.getImagepic().equalsIgnoreCase("")){

                   StorageReference storageRef1 = FirebaseStorage.getInstance()
                                    .getReference().child(model.getImagepic());


                      /*  Glide.with(getApplicationContext()).
                                using(new FirebaseImageLoader())
                                .load(storageRef1).
                                asBitmap().
                                centerCrop()
                                //.bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                .into(new BitmapImageViewTarget(viewHolder.imageView1){

                                    @Override
                                    protected void setResource(Bitmap resource){

                                        super.setResource(resource);
                                    }
                                });*/


                Glide.with(getApplicationContext())
                        .using(new FirebaseImageLoader())
                        .load(storageRef1)
                        .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                        .into(viewHolder.imageView1);


                        }


                        // Set click listener for the whole post view
                        final String postKey = postRef.getKey();


                        String currentUserEmail1 = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());

                        final DatabaseReference mCurrentUserDatabaseReference1 = mFirebaseDatabase
                                .getReference().child(Constants.USERS_POST
                                        + "/" + currentUserEmail1);

                        final Query querying = mCurrentUserDatabaseReference1;


                        viewHolder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                view.startAnimation(buttonClick);
                                //creating a popup menu
                                PopupMenu popup = new PopupMenu(getApplicationContext(), viewHolder.buttonViewOption);
                                //inflating menu from xml resource
                                popup.inflate(R.menu.options_menu);
                                //adding click listener
                                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.menu1:

                                                final Query querying1 = mCurrentUserDatabaseReference1;

                                    querying1.addListenerForSingleValueEvent(new ValueEventListener() {

                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){


                                                            postid = ((HashMap) dataSnapshot1.getValue()).get("id").toString();


                                                            if(postid.equalsIgnoreCase(model.getId())){

                                                                body=((HashMap) dataSnapshot1.getValue()).get("body").toString();
                                                                body1=((HashMap) dataSnapshot1.getValue()).get("body1").toString();
                                                                body2=((HashMap) dataSnapshot1.getValue()).get("body2").toString();
                                                                body3=((HashMap) dataSnapshot1.getValue()).get("body3").toString();
                                                            }

                                                        }


                                        Intent intent = new Intent(PostListFragment.this,TaskUpdate.class);

                                                  intent.putExtra("body", body);
                                                  intent.putExtra("body1",body1);
                                                  intent.putExtra("body2",body2);
                                                  intent.putExtra("body3",body3);
                                                  intent.putExtra("postid",postid);

                                                startActivity(intent);

                                        }


                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                                    }
                                                });


                                                break;


                                     case R.id.menu2:


                                      querying.addListenerForSingleValueEvent(new ValueEventListener() {

                                                    @Override

                                  public void onDataChange(DataSnapshot dataSnapshot) {



                                         for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                      String postid = ((HashMap) dataSnapshot1.getValue()).get("id").toString();



                                           if(postid.equalsIgnoreCase(model.getId())){

                                                 dataSnapshot1.getRef().removeValue();

                                                        }

                                                  }

                                             }

                                                    @Override

                                                    public void onCancelled(DatabaseError databaseError) {

                                                    }

                                                });

                                                break;

                                        }
                                        return false;
                                    }
                                });

                                //displaying the popup
                                popup.show();

                            }
                        });


                        viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {


                            @Override
                            public void onClick(View v) {
                                // Launch PostDetailActivity
                                Intent intent = new Intent(PostListFragment.this, PostDetailActivity.class);
                                intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                                startActivity(intent);
                            }


                        });


                        // Determine if the current user has liked this post and set UI accordingly
                        if (model.stars.containsKey(getUid())) {
                            viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                        }

                        else {
                            viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                        }


                        // Bind Post to ViewHolder, setting OnClickListener for the star button
                        viewHolder.bindToPost(model, new View.OnClickListener() {
                            @Override
                            public void onClick(View starView) {
                                // Need to write to both places the post is stored
                                DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                                DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                                // Run two transactions
                                onStarClicked(globalPostRef);
                                onStarClicked(userPostRef);
                            }
                        });


                    }

                };
                mRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void createpdf() {


      PdfDocument document=null;
        PdfDocument.Page page=null;
        PdfDocument.PageInfo pageInfo=null;
        for (int i =0; i < temp; i++) {
            document  = new PdfDocument();
            pageInfo   = new PdfDocument.PageInfo.Builder(v1.getWidth(), v1.getHeight(), temp).create();
            page  = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();

            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawPaint(paint);

            Bitmap bitmap = Bitmap.createScaledBitmap(b, v1.getWidth(), v1.getHeight(), true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);
        }
        File filePath = new File(path);
        try {

                document.writeTo(new FileOutputStream(filePath));
            }

        catch (IOException e) {
            e.printStackTrace();

        }

        // close the document
        document.close();


    }
    public  void sort_select() {

        mCurrentUserDatabaseReference = mFirebaseDatabase
                .getReference().child(Constants.USERS_POST).child(getUid());


        final Query query = mCurrentUserDatabaseReference.orderByChild("timestamp");

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getApplicationContext());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    return;
                }
                mAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(Post.class, R.layout.item_post,
                        PostViewHolder.class, query) {

                    @Override
                    protected void populateViewHolder(final PostViewHolder viewHolder, final Post model, final int position) {
                        final DatabaseReference postRef = getRef(position);

                        

 if (model.getBody2().equalsIgnoreCase(str4) || model.getBody2().equalsIgnoreCase(str5)|| model.getBody2().equalsIgnoreCase(str6)|| model.getBody2().equalsIgnoreCase(str7)|| model.getBody2().equalsIgnoreCase(str8)|| model.getBody2().equalsIgnoreCase(str9)|| model.getBody2().equalsIgnoreCase(str10)|| model.getBody2().equalsIgnoreCase(str11)|| model.getBody2().equalsIgnoreCase(str12)||model.getBody2().equalsIgnoreCase(str13)||model.getBody2().equalsIgnoreCase(str14)||model.getBody2().equalsIgnoreCase(str15)||model.getBody2().equalsIgnoreCase(str16)) {

                         if(!model.getProfilePicLocation().equalsIgnoreCase("")){


                             StorageReference storageRef = FirebaseStorage.getInstance()
                                     .getReference().child(model.getProfilePicLocation());

                             StorageReference storageRef1 = FirebaseStorage.getInstance()
                                     .getReference().child(model.getImagepic());

                             Glide.with(getApplicationContext())
                                     .using(new FirebaseImageLoader())
                                     .load(storageRef)
                                     .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                     .into(viewHolder.imageView);

                             Glide.with(getApplicationContext())
                                     .using(new FirebaseImageLoader())
                                     .load(storageRef1)
                                     .bitmapTransform(new CropCircleTransformation(getApplicationContext()))
                                     .into(viewHolder.imageView1);

                         }




                        // Set click listener for the whole post view
                        final String postKey = postRef.getKey();


                        // Determine if the current user has liked this post and set UI accordingly
                        if (model.stars.containsKey(getUid())) {
                            viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                        }

                        else {
                            viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                        }

                        // Bind Post to ViewHolder, setting OnClickListener for the star button
                        viewHolder.bindToPost(model, new View.OnClickListener() {
                            @Override
                            public void onClick(View starView) {
                                // Need to write to both places the post is stored
                                DatabaseReference globalPostRef = mDatabase.child("posts").child(postRef.getKey());
                                DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                                // Run two transactions
                                onStarClicked(globalPostRef);
                                onStarClicked(userPostRef);
                            }
                        });

                         }

   else if(!model.getBody2().equalsIgnoreCase(str4) || !model.getBody2().equalsIgnoreCase(str5)|| !model.getBody2().equalsIgnoreCase(str6)|| !model.getBody2().equalsIgnoreCase(str7)|| !model.getBody2().equalsIgnoreCase(str8)|| !model.getBody2().equalsIgnoreCase(str9)|| !model.getBody2().equalsIgnoreCase(str10)|| !model.getBody2().equalsIgnoreCase(str11)|| !model.getBody2().equalsIgnoreCase(str12)|| !model.getBody2().equalsIgnoreCase(str13)) {

                 viewHolder.cardView.removeAllViews();

                      }

                    }

                 };

                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public Document generatePDF(RecyclerView view) {

        // Progress dialog
  RecyclerView.Adapter adapter = view.getAdapter();
   Bitmap bigBitmap = null;


    if (adapter != null) {

         int size =  temp;
         int height = 0;
       Paint paint = new Paint();
         int iHeight = 0;


     final int maxMemory = (int) (Runtime.getRuntime().maxMemory());

            LruCache<String, Bitmap> bitmaCache = new LruCache<>(maxMemory);

           for (int i = 0; i < size; i++) {

               PostViewHolder holder = (PostViewHolder) adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);


         holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
         holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
         holder.itemView.setDrawingCacheEnabled(true);
         holder.itemView.buildDrawingCache();



        Bitmap drawingCache = holder.itemView.getDrawingCache();

                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }

                height += holder.itemView.getMeasuredHeight();

            }

           bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
           Canvas bigCanvas = new Canvas(bigBitmap);
           bigCanvas.drawColor(Color.WHITE);


          path = Environment.getExternalStorageDirectory().getAbsolutePath() + PostListFragment.this.getString(R.string.pdf_dir);

                    final File folder = new File(path);

                 if (!folder.exists()) {

                    boolean success = folder.mkdir();

                         if (!success) {
                             Toast.makeText(activity, "Error on creating application folder", Toast.LENGTH_SHORT).show();
                            }


                        }

          path = path + filename + PostListFragment.this.getString(R.string.pdf_ext);


             document  = new Document(PageSize.A4, 38, 38, 50, 38);


                    try {

                   PdfWriter.getInstance(document, new FileOutputStream(folder));


                        document.open();


                    }

                    catch (DocumentException | FileNotFoundException e) {

                        e.printStackTrace();

                    }

               for (int i = 0; i < size; i++) {

                        try {
                            //Adding the content to the document
                         Bitmap bmp = bitmaCache.get(String.valueOf(i));

                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);


                            Image image = Image.getInstance(stream.toByteArray());
                            image.scalePercent(70);
                            image.setAlignment(Image.MIDDLE);


                            if (!document.isOpen()) {
                                document.open();
                            }


                            document.add(image);


                        }

                        catch (Exception ex) {
                            Log.e("TAG-ORDER PRINT ERROR", ex.getMessage());
                        }


                    }


                    if (document.isOpen()) {
                        document.close();
                    }

            document.close();


                }
    return  document;

          }


    // [START post_stars_transaction]
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                if (p.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1;
                    p.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1;
                    p.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }


    // [END post_stars_transaction]

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }

    }
    public String getUid() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        currentUserEmail = EmailEncoding.commaEncodePeriod(mFirebaseAuth.getCurrentUser().getEmail());
        return currentUserEmail;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PostListFragment.this, MainActivityCalendar.class);
        startActivity(intent);
    }


    private class MyRunnable implements Runnable {

        int [] imageSize;
        String path;

        public MyRunnable( int [] imageSize,String path) {

            this.imageSize = imageSize;
            this.path = path;

        }

        @Override
        public void run() {
            android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

            try {
                Bitmap bitmap = Glide.with(getApplicationContext()).load(path).
                                 asBitmap().listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;



                    }
                 }).into(imageSize[0],imageSize[1]).get();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

          }
        }

}
