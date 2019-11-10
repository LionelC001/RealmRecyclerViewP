package com.lionel.realmpaginationp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;

import com.lionel.realmpaginationp.pagination.NumDataSourceFactory;
import com.lionel.realmpaginationp.realm.NumModel;
import com.lionel.realmpaginationp.realm.RealmHelper;

import java.util.ArrayList;
import java.util.List;

public class NumViewModel extends AndroidViewModel {
    private MutableLiveData<Integer> liveTempTrigger = new MutableLiveData<>();
    private LiveData<PagedList<NumModel>> livePagedListNumModel = Transformations.switchMap(liveTempTrigger, this::initLivePagedNumModel);

    public NumViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PagedList<NumModel>> getLivePagedListNumModel() {
        return livePagedListNumModel;
    }

    public void initLiveNumModel() {
        liveTempTrigger.setValue(0);
    }

    private LiveData<PagedList<NumModel>> initLivePagedNumModel(int fakeTrigger) {
        return NumDataSourceFactory.getLivePagedList();
    }

    public void onFakeCallApi() {
        // simulate call api
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                NumModel theLastItem = RealmHelper.getLastData();
                int nowEndNum = theLastItem.getNum();
                List<NumModel> data = new ArrayList<>();
                for (int i = nowEndNum + 1; i < nowEndNum + 1 + 10; i++) {
                    NumModel model = new NumModel();
                    model.setId(i);
                    model.setNum(i);
                    data.add(model);
                }
                RealmHelper.insertData(data);
            }
        }).start();


//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("<>", "handler: " + Thread.currentThread().getName());
//                NumModel theLastItem = RealmHelper.getLastData();
//                int nowEndNum = theLastItem.getNum();
//                List<NumModel> data = new ArrayList<>();
//                for (int i = nowEndNum + 1; i < nowEndNum + 1 + 10; i++) {
//                    NumModel model = new NumModel();
//                    model.setId(i);
//                    model.setNum(i);
//                    data.add(model);
//                }
//                RealmHelper.insertData(data);
//
//                new Handler().postDelayed(this, 3000);
//            }
//    });
    }
}
