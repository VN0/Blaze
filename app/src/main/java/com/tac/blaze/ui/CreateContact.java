package com.tac.blaze.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.tac.blaze.R;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author vidit
 */
public class CreateContact extends DialogFragment implements IPickResult{

    CircleImageView circle;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        PickSetup pick=new PickSetup();

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setView(R.layout.createcontactfragment)
                .create();

        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

       circle=alertDialog.findViewById(R.id.dispict);
        circle.setOnClickListener(v -> {
            PickImageDialog.build(pick)
                    .setOnPickResult(r -> {
                        if (r.getError() == null) {
                            circle.setImageBitmap(r.getBitmap());
                        } else {
                            Toast.makeText(getActivity(), "Make", Toast.LENGTH_SHORT).show();
                        }
                    }).show(getFragmentManager());
        });
        return alertDialog;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onPickResult(PickResult r) {

    }
}
