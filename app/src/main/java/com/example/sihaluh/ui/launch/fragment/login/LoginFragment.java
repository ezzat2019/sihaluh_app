package com.example.sihaluh.ui.launch.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.RegisterModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.launch.fragment.login.forget_password.ForgetPasswordBottomSheetFragment;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {
    // ui
    private EditText ed_login_phone, ed_login_pass;
    private Button btn_login;
    private TextView txt_forget_pass;
    private BottomSheetDialogFragment bottomSheetDialog;

    // var
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref_login = firebaseDatabase.getReference().child(AllFinal.FIREBASE_DATABASE_LOGIN);
    private String phone, password;
    private PrefViewModel prefViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);




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

    @Override
    public void onStart() {
        super.onStart();
        if (prefViewModel.getPhone().equals("")||mAuth.getCurrentUser()==null)
        {
            return;
        }
        startActivity(new Intent(getContext(), HomeActivity.class));
        getActivity().finish();
    }

    private Boolean checkEditedTextLogin() {
        if (TextUtils.isEmpty(ed_login_phone.getText().toString())) {
            ed_login_phone.setError("enter phone please!");
            ed_login_phone.requestFocus();
            return false;
        }
        if (ed_login_phone.getText().toString().length() < 11) {
            ed_login_phone.setError("enter correct phone number please!");
            ed_login_phone.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(ed_login_pass.getText().toString())) {
            ed_login_pass.setError("enter password please!");
            ed_login_pass.requestFocus();
            return false;
        }
        return true;
    }

    private void actions() {





        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkEditedTextLogin()) {
                    return;
                }
                login();
            }
        });



        txt_forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bottomSheetDialog.show(getParentFragmentManager(),"sh1");



            }
        });
    }

    private void login() {
        phone = ed_login_phone.getText().toString().trim();
        password = ed_login_pass.getText().toString();

        ref_login.child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    RegisterModel registerModel = snapshot.getValue(RegisterModel.class);
                    Log.d("zzzzzz", "onDataChange: " + registerModel.getEmail());
                    mAuth.signInWithEmailAndPassword(registerModel.getEmail(), password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        prefViewModel.putPhone(phone);
                                        Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
                                        ed_login_pass.setText("");
                                        ed_login_phone.setText("");
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "incorrect phone or password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void init(View v) {
        ed_login_phone = v.findViewById(R.id.ed_login_phone);
        ed_login_pass = v.findViewById(R.id.ed_login_pass);

        btn_login = v.findViewById(R.id.btn_login);

        txt_forget_pass = v.findViewById(R.id.txt_forget_pass);



        bottomSheetDialog=new ForgetPasswordBottomSheetFragment();


    }
}