package com.lionel.realmpaginationp.pagination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.paging.AsyncPagedListDiffer;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lionel.realmpaginationp.R;
import com.lionel.realmpaginationp.databinding.ItemNumBinding;
import com.lionel.realmpaginationp.realm.NumModel;

public class NumAdapter extends PagedListAdapter<NumModel, NumAdapter.CustomViewHolder> {
    private final Context context;
    private  AsyncPagedListDiffer<NumModel> asyncPagedListDeffer;

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
//        asyncPagedListDeffer = new AsyncPagedListDiffer<>(this, diffCallback);
        this.context = context;

        setHasStableIds(true);
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


    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

//    @Override
//    public void submitList(@Nullable PagedList<NumModel> pagedList) {
//       pagedList.addWeakCallback(pagedList.snapshot(), new PagedList.Callback() {
//           @Override
//           public void onChanged(int position, int count) {
//
//           }
//
//           @Override
//           public void onInserted(int position, int count) {
//               asyncPagedListDeffer.submitList(pagedList);
//           }
//
//           @Override
//           public void onRemoved(int position, int count) {
//
//           }
//       });
//    }
}
