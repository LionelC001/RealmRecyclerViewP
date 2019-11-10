package com.lionel.realmpaginationp.pagination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lionel.realmpaginationp.R;
import com.lionel.realmpaginationp.databinding.ItemNumBinding;
import com.lionel.realmpaginationp.realm.NumModel;

public class NumAdapter extends PagedListAdapter<NumModel, NumAdapter.CustomViewHolder> {
    private final Context context;

    private static DiffUtil.ItemCallback diffCallback = new DiffUtil.ItemCallback<NumModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull NumModel oldItem, @NonNull NumModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull NumModel oldItem, @NonNull NumModel newItem) {
            return oldItem.getNum() == newItem.getNum();
        }
    };

    public NumAdapter(Context context) {
        super(diffCallback);
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
