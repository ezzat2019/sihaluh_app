package com.example.sihaluh.ui.product_detial.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.sihaluh.R;
import com.github.chrisbanes.photoview.PhotoView;


public class FullImageFragment extends DialogFragment {
    private static final String ARG_PARAM1 = "img_url";

    // ui
    private PhotoView photoView;

    // var
    private String mParam1;


    public FullImageFragment() {

    }


    public static FullImageFragment newInstance(String param1) {
        FullImageFragment fragment = new FullImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            Log.d("zzzzzzzzzz", "onCreateDialog: 2");
        }
    }


    private void init(View v) {
        photoView = v.findViewById(R.id.img_full_product);
        Glide.with(getContext()).load(mParam1)
                .into(photoView);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.fragment_full_image, null);
        builder.setView(view);
        Log.d("zzzzzzzzzz", "onCreateDialog: 1"+mParam1);
        init(view);
        return builder.create();
    }


}