package com.tac.blaze.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.tac.blaze.R;
import com.tac.blaze.UserClient;
import com.tac.blaze.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.constraint.motion.MotionScene.TAG;


/**
 * @author vidit
 */
public class AboutDialogFragment extends DialogFragment  implements View.OnClickListener{

    CircleImageView disp;
    Bitmap decodedByte;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(R.layout.aboutlayout)
                .create();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        TextView btn = alertDialog.findViewById(R.id.accman);
        TextView logout=alertDialog.findViewById(R.id.log);
        disp=alertDialog.findViewById(R.id.accountpic);
        try{
            decodedByte = ImageUtil.convert(((UserClient)getActivity().getApplicationContext()).getUser().getAvatar());
        }catch (NumberFormatException e){
            Log.e(TAG, "retrieveProfileImage: no avatar image. Setting default. " + e.getMessage() );
        }
        disp.setImageBitmap(decodedByte);
        btn.setOnClickListener(v -> startActivity(new Intent(getActivity(), ProfileActivity.class)));
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        });
        return alertDialog;
    }


    @Override
    public void onClick(View v) {
        PickSetup pick=new PickSetup();

        PickImageDialog.build(pick)
                .setOnPickResult(r -> {
                    if (r.getError() == null) {
                        Bitmap bt=r.getBitmap();
                        disp.setImageBitmap(r.getBitmap());
                        User user = ((UserClient)getActivity().getApplicationContext()).getUser();
                        String base64String = ImageUtil.convert(bt);
                        user.setAvatar(base64String);
                        FirebaseFirestore.getInstance()
                                .collection(getString(R.string.collection_users))
                                .document(FirebaseAuth.getInstance().getUid())
                                .set(user);
                    } else {
                        Toast.makeText(getActivity(), "Make", Toast.LENGTH_SHORT).show();
                    }

                }).show(getFragmentManager());
    }

//    public static void openAppRating(Context context) {
//        // you can also use BuildConfig.APPLICATION_ID
//        String appId = context.getPackageName();
//        Intent rateIntent = new Intent(Intent.ACTION_VIEW,
//                Uri.parse("market://details?id=" + appId));
//        boolean marketFound = false;
//
//        // find all applications able to handle our rateIntent
//        final List<ResolveInfo> otherApps = context.getPackageManager()
//                .queryIntentActivities(rateIntent, 0);
//        for (ResolveInfo otherApp: otherApps) {
//            // look for Google Play application
//            if (otherApp.activityInfo.applicationInfo.packageName
//                    .equals("com.android.vending")) {
//
//                ActivityInfo otherAppActivity = otherApp.activityInfo;
//                ComponentName componentName = new ComponentName(
//                        otherAppActivity.applicationInfo.packageName,
//                        otherAppActivity.name
//                );
//                // make sure it does NOT open in the stack of your activity
//                rateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                // task reparenting if needed
//                rateIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                // if the Google Play was already open in a search result
//                //  this make sure it still go to the app page you requested
//                rateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                // this make sure only the Google Play app is allowed to
//                // intercept the intent
//                rateIntent.setComponent(componentName);
//                context.startActivity(rateIntent);
//                marketFound = true;
//                break;
//
//            }
//        }
//
//        // if GP not present on device, open web browser
//        if (!marketFound) {
//            Intent webIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://play.google.com/store/apps/details?id="+appId));
//            context.startActivity(webIntent);
//        }
}
