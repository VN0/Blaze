package com.tac.blaze.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.tac.blaze.R;
import com.tac.blaze.UserClient;
import com.tac.blaze.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends AppCompatActivity implements
        View.OnClickListener
{
    Bitmap decodedByte=null;
    private static final String TAG = "ProfileActivity";


    //widgets
    private CircleImageView mAvatarImage;

    //vars
    private ImageListFragment mImageListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAvatarImage = findViewById(R.id.image_choose_avatar);

        findViewById(R.id.image_choose_avatar).setOnClickListener(this);
        findViewById(R.id.text_choose_avatar).setOnClickListener(this);

        retrieveProfileImage();
    }



    private void retrieveProfileImage(){
        try{
            //avatar = Integer.parseInt(((UserClient)getApplicationContext()).getUser().getAvatar());
            //byte[] decodedString = Base64.decode(((UserClient)getApplicationContext()).getUser().getAvatar(), Base64.DEFAULT);
            decodedByte = ImageUtil.convert(((UserClient)getApplicationContext()).getUser().getAvatar());
        }catch (NumberFormatException e){
            Log.e(TAG, "retrieveProfileImage: no avatar image. Setting default. " + e.getMessage() );
        }
        mAvatarImage.setImageBitmap(decodedByte);

    }


    @Override
    public void onClick(View v) {
//        mImageListFragment = new ImageListFragment();
//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up)
//                .replace(R.id.fragment_container, mImageListFragment, getString(R.string.fragment_image_list))
//                .commit();
        PickSetup pick=new PickSetup();

        PickImageDialog.build(pick)
                .setOnPickResult(r -> {
                    if (r.getError() == null) {
                        Bitmap bt=r.getBitmap();
                        mAvatarImage.setImageBitmap(r.getBitmap());
                        User user = ((UserClient)getApplicationContext()).getUser();
                        String base64String = ImageUtil.convert(bt);
                        user.setAvatar(base64String);
                        FirebaseFirestore.getInstance()
                                .collection(getString(R.string.collection_users))
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(user);
                    } else {
                        Toast.makeText(this, "Make", Toast.LENGTH_SHORT).show();
                    }

                }).show(getSupportFragmentManager());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


}
