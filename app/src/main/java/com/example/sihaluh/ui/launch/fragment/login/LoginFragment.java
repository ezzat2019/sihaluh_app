package com.example.sihaluh.ui.launch.fragment.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sihaluh.R;


public class LoginFragment extends Fragment {
    // ui
    private EditText ed_login_phone,ed_login_pass;
    private Button btn_login;
    private TextView txt_forget_pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        actions();
    }

    private void actions() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void init(View v) {
        ed_login_phone= v.findViewById(R.id.ed_login_phone);
        ed_login_pass= v.findViewById(R.id.ed_login_pass);

        btn_login=v.findViewById(R.id.btn_login);

        txt_forget_pass=v.findViewById(R.id.txt_forget_pass);
    }
}