package com.lionel.realmpaginationp;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lionel.realmpaginationp.databinding.ActivityMainBinding;
import com.lionel.realmpaginationp.pagination.NumAdapter;
import com.lionel.realmpaginationp.pagination.NumDataSourceFactory;
import com.lionel.realmpaginationp.realm.NumModel;
import com.lionel.realmpaginationp.realm.RealmHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding dataBinding;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        context = this;

        insertData();
        initRecyclerView();
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
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        final NumAdapter adapter = new NumAdapter(this);
        recyclerView.setAdapter(adapter);
        NumDataSourceFactory.getLivePagedList().observe(this, adapter::submitList);
    }
}
