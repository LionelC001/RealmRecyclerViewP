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
}
