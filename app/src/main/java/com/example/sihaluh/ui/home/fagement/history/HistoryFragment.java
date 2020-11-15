package com.example.sihaluh.ui.home.fagement.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.EndOrderModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.history.adapter.HistoryAdapter;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HistoryFragment extends Fragment {

    // ui
    private ConstraintLayout constraintLayout;
    private RecyclerView rec_history;


    // var
    private DatabaseReference ref_history = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_ORDER_COLLECTION);
    private List<EndOrderModel> endOrderModelList = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private PrefViewModel prefViewModel;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        actions();
    }

    private void actions() {
        historyAdapter.setOnItemClick(new HistoryAdapter.OnItemClick() {
            @Override
            public void onClick(int pos) {
                Toast.makeText(getContext(), pos + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(View v) {
        constraintLayout = v.findViewById(R.id.cons_his);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);

        buildRec(v);

        getDataFromColletion();

    }

    private void getDataFromColletion() {

        ref_history.child(prefViewModel.getPhone()).orderByValue()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            EndOrderModel endOrderModel = dataSnapshot.getValue(EndOrderModel.class);
                            endOrderModel.setOwner_id(dataSnapshot.getKey());
                            endOrderModelList.add(endOrderModel);
                        }
                        checkRec();
                        Collections.reverse(endOrderModelList);
                        historyAdapter.addHistoryItems(endOrderModelList, snapshot.getKey());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void checkRec() {
        if (endOrderModelList.isEmpty()) {
            showConstrain(true);
        } else {
            showConstrain(false);
        }

    }

    private void buildRec(View v) {
        rec_history = v.findViewById(R.id.rec_history);
        rec_history.setHasFixedSize(true);
        rec_history.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter();

        rec_history.setAdapter(historyAdapter);
    }

    private void showConstrain(Boolean b) {
        if (b) {
            constraintLayout.setVisibility(View.VISIBLE);
        } else {
            constraintLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        HomeActivity.txt_name_bar.setText(getString(R.string.title_post_job));

    }
}