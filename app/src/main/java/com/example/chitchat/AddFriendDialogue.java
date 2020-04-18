package com.example.chitchat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddFriendDialogue extends AppCompatDialogFragment {
    private EditText addEmail,username;
    private dialogueListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener=(dialogueListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() +"must implent dialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        final View view=inflater.inflate(R.layout.layout_dialogue,null);
        builder.setView(view)
                .setTitle("Add Friend")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addEmail=view.findViewById(R.id.addFriendemail);
                        String email=addEmail.getText().toString();
                        listener.applytext(email);
                    }
                });
        return builder.create();
    }
    public interface dialogueListener
    {
        void applytext(String email);
    }

}
