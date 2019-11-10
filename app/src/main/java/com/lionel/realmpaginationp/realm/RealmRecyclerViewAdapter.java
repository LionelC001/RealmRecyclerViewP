package com.lionel.realmpaginationp.realm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lionel.realmpaginationp.R;
import com.lionel.realmpaginationp.databinding.ItemNumBinding;

import io.realm.RealmResults;

public class RealmRecyclerViewAdapter extends io.realm.RealmRecyclerViewAdapter<NumModel, RealmRecyclerViewAdapter.CustomViewHolder> {

    private final Context context;

    public RealmRecyclerViewAdapter(Context context, RealmResults<NumModel> data) {
        super(data, true, true);
        this.context = context;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private final ItemNumBinding dataBinding;

        public CustomViewHolder(@NonNull ItemNumBinding dataBinding) {
            super(dataBinding.getRoot());

            this.dataBinding = dataBinding;
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNumBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_num, parent, false);
        return new CustomViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.dataBinding.setData(getItem(position));
    }
}
