package com.veevapp.customer.view.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.StringItem;
import com.veevapp.customer.view.customwidget.AppTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AdapterSelectableListRadio extends
        BaseRecyclerAdapter<AdapterSelectableListRadio.ViewHolderSelectableListRadio,StringItem>{

    Context mContext ;
    OnAdapterViewClickedListener mListener;
    int currentSelectedPos = -1;
    public AdapterSelectableListRadio(Context ctx, OnAdapterViewClickedListener listener){
        mContext = ctx;
        mListener = listener;
    }

    @Override
    public ViewHolderSelectableListRadio onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.selectable_list_item_radio,parent,false);
        return new ViewHolderSelectableListRadio(v, (v1, vh, pos) -> {
            ViewHolderSelectableListRadio vRadio =
                    (ViewHolderSelectableListRadio) vh;
            if(v1 == vRadio.rbSelect || v1 == vRadio.csContainer){
                //SetSelectedItem
                refreshCurrentSelectedPos(pos);
            }

            if(mListener!=null)
                mListener.onAdapterViewClicked(v1,vh,pos);
        });
    }

    @Override
    public void onBindViewHolder(ViewHolderSelectableListRadio holder, int position) {
        holder.tvTitle.setText(mItems.get(position).text);

        boolean selected = position==currentSelectedPos;
        holder.rbSelect.setChecked(selected);
    }

    public void refreshCurrentSelectedPos(int pos){
        currentSelectedPos = pos;
        notifyDataSetChanged();
    }

    public static class ViewHolderSelectableListRadio extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_title)
        public AppTextView tvTitle;

        @BindView(R.id.rb_select)
        public RadioButton rbSelect;

        @BindView(R.id.cs_container)
        public ConstraintLayout csContainer;

        OnAdapterViewClickedListener mListener;

        public ViewHolderSelectableListRadio(View itemView, OnAdapterViewClickedListener listener) {
            super(itemView);
            mListener = listener;

            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.cs_container)
        public void onContainerClicked(){
            rbSelect.setSelected(true);
            if(mListener!=null)
                mListener.onAdapterViewClicked(csContainer,ViewHolderSelectableListRadio.this,getAdapterPosition());
        }

        @OnClick(R.id.rb_select)
        public void onRbSelectClicked(){
            rbSelect.setSelected(true);
            if(mListener!=null)
                mListener.onAdapterViewClicked(rbSelect,ViewHolderSelectableListRadio.this,getAdapterPosition());
        }

    }
}