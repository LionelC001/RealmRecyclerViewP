package com.lionel.realmpaginationp.realm;

import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmHelper {

    private static RealmConfiguration sConfig;

    private static RealmConfiguration getRealmConfig() {
        if (sConfig == null) {
            synchronized (RealmConfiguration.class) {
                if (sConfig == null) {
                    sConfig = new RealmConfiguration.Builder()
                            .name("realm_pagination_p.realm")
                            .schemaVersion(0)
                            .build();
                }
            }
        }
        return sConfig;
    }


    public static void insertData(final List<NumModel> data) {
        Realm realm = Realm.getInstance(getRealmConfig());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmList<NumModel> realmList = new RealmList<>();
                realmList.addAll(data);
                realm.insertOrUpdate(realmList);
            }
        });
        realm.close();
    }

    public static int getDataCount() {
        Realm realm = Realm.getInstance(getRealmConfig());
        return realm.where(NumModel.class).findAll().size();
    }

    public static List<NumModel> getData(int initPosition, int fromPage, int toPage, int pageSize) {
        Log.d("<>", "realm thread: " + Thread.currentThread().getName());

        Realm realm = Realm.getInstance(getRealmConfig());
        RealmResults<NumModel> realmResult = realm.where(NumModel.class).findAll();

        int from, to;
        if (fromPage > toPage) {  // backward
            to = initPosition + fromPage * pageSize;        //1000 +  0*100   |  1000 + -1*100
            from = initPosition + toPage * pageSize;        //1000 + -1*100   |  1000 + -2*100
            if (from < 0) from = 0;
        } else {   // forward
            from = initPosition + fromPage * pageSize;      //1000 +  0*100
            to = initPosition + toPage * pageSize;          //1000 +  1*100

            int nowMaxCount = getDataCount();
            if (to > nowMaxCount) to = nowMaxCount;
        }

        List<NumModel> listResult = realm.copyFromRealm(realmResult.subList(from, to));
        return listResult;
    }

    public static boolean checkIsPreviousPage(int initPosition, int toPage, int pageSize) {
        int toPosition = initPosition - toPage * pageSize;
        return toPosition > 0;
    }

    public static boolean checkIsNextPage(int initPosition, int toPage, int pageSize) {
        int toPosition = initPosition + toPage * pageSize;
        return getDataCount() > toPosition;
    }
}
