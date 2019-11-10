package com.lionel.realmpaginationp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lionel.realmpaginationp.databinding.ActivityMainBinding;
import com.lionel.realmpaginationp.pagination.NumAdapter;
import com.lionel.realmpaginationp.realm.NumModel;
import com.lionel.realmpaginationp.realm.RealmHelper;
import com.lionel.realmpaginationp.realm.RealmRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dataBinding;
    private Context context;
    private NumViewModel viewModel;
    private RealmRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;
        viewModel = new ViewModelProvider(this).get(NumViewModel.class);

        insertData();
        initRecyclerView();
//        initObserve();
        initSimulateCallApi();
    }

    private void insertData() {
        List<NumModel> data = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            NumModel model = new NumModel();
            model.setId(i);
            model.setNum(i);
            data.add(model);
        }
        RealmHelper.insertData(data);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = dataBinding.recyclerView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

//        adapter = new NumAdapter(this);

        RealmResults<NumModel> data = RealmHelper.getData(RealmHelper.getDataCount(), 0, -1, 100);
        adapter = new RealmRecyclerViewAdapter(this,data);
        recyclerView.setAdapter(adapter);
    }

//    private void initObserve() {
//        viewModel.getLivePagedListNumModel().observe(this, adapter::submitList);
//        viewModel.initLiveNumModel();
//    }

    private void initSimulateCallApi() {
            viewModel.onFakeCallApi();
    }
}
