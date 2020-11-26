package com.example.sihaluh.ui.launch.fragment.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.RegisterModel;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.example.sihaluh.utils.test.TestActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends Fragment {
    // ui
    private EditText ed_register_name, ed_register_phone, ed_register_email, ed_register_passwored;
    private CheckBox ch_register_policy;
    private Button btn_register;


    // var
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseDatabase base = FirebaseDatabase.getInstance();
    private final DatabaseReference ref_database_register = base.getReference().child(AllFinal.FIREBASE_DATABASE_LOGIN);
    private String name, email, password, phone, img_url;
    private PrefViewModel prefViewModel;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        Log.d("zzzzzzz", "onViewCreated: " + prefViewModel.getPhone());
        actions();
    }

    private void actions() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEditedTextRegister()) {
                    return;
                } else if (!ch_register_policy.isChecked()) {
                    Snackbar.make(v, "you must accept policy first!", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                startActivity(new Intent(getContext(), TestActivity.class));

//                register();


            }


        });
    }

    private void register() {
        name = ed_register_name.getText().toString().trim();
        email = ed_register_email.getText().toString().trim();
        phone = ed_register_phone.getText().toString().trim();
        password = ed_register_passwored.getText().toString().trim();
        img_url = "";
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RegisterModel registerModel = new RegisterModel(task.getResult().getUser().getUid()
                            , name, email, phone, img_url);

                    ref_database_register.child(registerModel.getPhone())
                            .setValue(registerModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                ed_register_email.setText("");
                                ed_register_passwored.setText("");
                                ed_register_name.setText("");
                                ed_register_phone.setText("");
                                ch_register_policy.setChecked(false);
                                Toast.makeText(getContext(), "A new email has been created", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });
    }

    private Boolean checkEditedTextRegister() {
        if (TextUtils.isEmpty(ed_register_name.getText().toString())) {
            ed_register_name.setError("enter name please!");
            ed_register_name.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ed_register_phone.getText().toString())) {
            ed_register_phone.setError("enter phone please!");
            ed_register_phone.requestFocus();
            return false;
        }
        if (ed_register_phone.getText().toString().length() < 11) {
            ed_register_phone.setError("enter correct phone number please!");
            ed_register_phone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ed_register_email.getText().toString())) {
            ed_register_email.setError("enter  email please!");
            ed_register_email.requestFocus();
            return false;
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(ed_register_email.getText().toString()).matches()) {
            ed_register_email.setError("enter correct email pattern please!");
            ed_register_email.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(ed_register_passwored.getText().toString())) {
            ed_register_passwored.setError("enter password please!");
            ed_register_passwored.requestFocus();
            return false;
        }
        if (ed_register_passwored.getText().toString().length() < 6) {
            ed_register_passwored.setError("the password at least 6 letters please!");
            ed_register_passwored.requestFocus();
            return false;
        }
        return true;
    }

    private void init(View v) {
        ed_register_email = v.findViewById(R.id.ed_register_email);
        ed_register_name = v.findViewById(R.id.ed_register_name);
        ed_register_passwored = v.findViewById(R.id.ed_register_passwored);
        ed_register_phone = v.findViewById(R.id.ed_register_phone);

        ch_register_policy = v.findViewById(R.id.ch_register_policy);

        btn_register = v.findViewById(R.id.btn_register);

    }
}