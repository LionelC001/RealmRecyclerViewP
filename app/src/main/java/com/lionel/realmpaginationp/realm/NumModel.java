package com.lionel.realmpaginationp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class NumModel extends RealmObject {
    @PrimaryKey
    private long id;
    private int num;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
