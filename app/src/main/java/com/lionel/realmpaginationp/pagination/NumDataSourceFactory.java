package com.lionel.realmpaginationp.pagination;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.lionel.realmpaginationp.realm.NumModel;
import com.lionel.realmpaginationp.realm.RealmHelper;

import java.util.List;

public class NumDataSourceFactory extends DataSource.Factory<Integer, NumModel> {
    private static final int PAGE_SIZE = 100;


    public static LiveData<PagedList<NumModel>> getLivePagedList(){
        PagedList.Config pagedListConfig = new PagedList.Config.Builder().setPageSize(PAGE_SIZE).build();
        return new LivePagedListBuilder(new NumDataSourceFactory(), pagedListConfig).build();
    }

    @NonNull
    @Override
    public DataSource<Integer, NumModel> create() {
        return new NumDataSource();
    }


    private class NumDataSource extends PageKeyedDataSource<Integer, NumModel> {
        private int initPosition = RealmHelper.getDataCount();

        @Override
        public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, NumModel> callback) {
            Log.d("<>", "loadInitial: " + Thread.currentThread().getName());

            List<NumModel> data = RealmHelper.getData(initPosition, 0, -1, PAGE_SIZE);  // get last 100 data
            callback.onResult(data, -1, 0);
        }

        @Override
        public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NumModel> callback) {
            Log.d("<>", "loadBefore: " + Thread.currentThread().getName());

            int page = params.key;
            List<NumModel> data = RealmHelper.getData(initPosition, page, page - 1, PAGE_SIZE);

            boolean isTherePrevious = RealmHelper.checkIsPreviousPage(initPosition, page - 2, PAGE_SIZE);
            callback.onResult(data, isTherePrevious ? page - 1 : null);
        }

        @Override
        public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NumModel> callback) {
            Log.d("<>", "loadAfter: " + Thread.currentThread().getName());

            int page = params.key;
            List<NumModel> data = RealmHelper.getData(initPosition, page, page + 1, PAGE_SIZE);

            boolean isThereNext = RealmHelper.checkIsNextPage(initPosition, page + 2, PAGE_SIZE);
            callback.onResult(data, isThereNext ? page + 1 : null);
        }
    }
}
