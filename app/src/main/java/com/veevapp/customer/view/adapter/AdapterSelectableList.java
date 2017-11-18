package com.veevapp.customer.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.StringItem;
import com.veevapp.customer.view.customwidget.AppTextView;

import java.util.List;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AdapterSelectableList extends
        BaseRecyclerAdapter<AdapterSelectableList.ViewHolderSelectableList,StringItem>{

    Context mContext ;
    OnAdapterViewClickedListener mListener;
    public AdapterSelectableList(Context ctx,OnAdapterViewClickedListener listener){
        mContext = ctx;
        mListener = listener;
    }

    @Override
    public ViewHolderSelectableList onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.selectable_list_item,parent,false);
        return new ViewHolderSelectableList(v,mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolderSelectableList holder, int position) {
        holder.tvTitle.setText(mItems.get(position).text);
    }

    @Override
    public void resetItems(List<StringItem> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ViewHolderSelectableList extends RecyclerView.ViewHolder{

        public AppTextView tvTitle;
        public RelativeLayout rlContainer;

        OnAdapterViewClickedListener mListener;

        public ViewHolderSelectableList(View itemView,OnAdapterViewClickedListener listener) {
            super(itemView);
            mListener = listener;
            tvTitle = itemView.findViewById(R.id.tv_title);
            rlContainer = itemView.findViewById(R.id.rl_container);
            rlContainer.setOnClickListener(view -> {
                if(mListener!=null)mListener.onAdapterViewClicked(rlContainer,ViewHolderSelectableList.this,getAdapterPosition());
            });
        }

    }
}