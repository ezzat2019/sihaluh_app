package com.example.sihaluh.ui.home.fagement.history;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sihaluh.R;
import com.example.sihaluh.data.model.EndOrderModel;
import com.example.sihaluh.ui.home.HomeActivity;
import com.example.sihaluh.ui.home.fagement.history.adapter.HistoryAdapter;
import com.example.sihaluh.ui.home.fagement.history.viewmodel.HistoryViewModel;
import com.example.sihaluh.utils.AllFinal;
import com.example.sihaluh.utils.shared_preferense.PrefViewModel;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private DatabaseReference ref_start= FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_STARORDER);
    private DatabaseReference ref_history = FirebaseDatabase.getInstance().getReference().child(AllFinal.FIREBASE_DATABASE_ORDER_COLLECTION);
    private List<EndOrderModel> endOrderModelList = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private PrefViewModel prefViewModel;
    private HistoryViewModel historyViewModel;
    private ConnectivityManager connectivityManager;


    public HistoryFragment() {
        // Required empty public constructor
    }
    private void deteFirstOrderIsExcit() {
        ref_start.child(prefViewModel.getPhone()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    ref_start.removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        connectivityManager= (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        init(view);

        actions();
    }

    private void actions() {
        historyAdapter.setOnItemClick(new HistoryAdapter.OnItemClick() {
            @Override
            public void onLongClick(int pos) {
                if (connectivityManager.getActiveNetworkInfo() != null) {
                    ref_history.child(prefViewModel.getPhone())
                            .child(endOrderModelList.get(pos).getOwner_id()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            historyViewModel.deleteHistoryItem(endOrderModelList.get(pos).getOwner_id());

                            endOrderModelList.remove(pos);
                            historyAdapter.notifyItemRemoved(pos);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    historyAdapter.addHistoryItems(endOrderModelList);
                                }
                            }, 1000);


                        }
                    });
                } else {
                    Toast.makeText(getContext(), "check internet connetion", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void init(View v) {
        constraintLayout = v.findViewById(R.id.cons_his);
        prefViewModel = new ViewModelProvider(this).get(PrefViewModel.class);
        historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        deteFirstOrderIsExcit();
        checkRec();
        buildRec(v);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromColletion();

            }
        }, 100);


    }

    private void getDataFromColletion() {

        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager.getActiveNetworkInfo() == null) {


            historyViewModel.getHistoryLiveData().observe(this, new Observer<List<EndOrderModel>>() {
                @Override
                public void onChanged(List<EndOrderModel> endOrderModels) {

                    if (endOrderModels.isEmpty()) {

                    } else {
                        endOrderModelList = endOrderModels;

                        Collections.reverse(endOrderModelList);
                        historyAdapter.addHistoryItems(endOrderModelList);
                    }
                    checkRec();
                }
            });

        } else {
            ref_history.child(prefViewModel.getPhone())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                EndOrderModel endOrderModel = dataSnapshot.getValue(EndOrderModel.class);
                                endOrderModel.setOwner_id(dataSnapshot.getKey());
                                endOrderModelList.add(endOrderModel);
                                historyViewModel.addNewHistoryItem(endOrderModel);
                            }
                            checkRec();
                            Collections.reverse(endOrderModelList);
                            historyAdapter.addHistoryItems(endOrderModelList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), error.getMessage() + "", Toast.LENGTH_SHORT).show();

                        }
                    });

        }


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