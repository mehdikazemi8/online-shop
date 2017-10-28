package com.veevapp.customer.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veevapp.customer.R;
import com.veevapp.customer.view.adapter.AdapterSelectableList;
import com.veevapp.customer.view.customwidget.AppTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogSelectList extends Dialog {
    public DialogSelectList(@NonNull Context context) {
        super(context);
        init(context);
    }

    public DialogSelectList(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected DialogSelectList(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }


    @BindView(R.id.tv_title)
    AppTextView tvTitle;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    AdapterSelectableList mAdapter;

    OnItemClickListener mListener;


    private void init(Context context) {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        View v = LayoutInflater.from(context).inflate(R.layout.dialog_select_item,null,false);
        setContentView(v);
        ButterKnife.bind(this);

        mAdapter = new AdapterSelectableList(context, (v1, vh, pos) -> {
            AdapterSelectableList.ViewHolderSelectableList viewHolder = (AdapterSelectableList.ViewHolderSelectableList) vh;
            if(v1 == viewHolder.rlContainer){
                if(mListener!=null)
                    mListener.onItemClick(pos);
            }
        });
        rvContent.setLayoutManager(new LinearLayoutManager(context));
        rvContent.setAdapter(mAdapter);
        rvContent.setHasFixedSize(true);
    }


    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setItems(String[] items){
        List<AdapterSelectableList.StringItem> stringItems = new ArrayList<>();
        for(int i=0;i<items.length;i++) {
            stringItems.add(new AdapterSelectableList.StringItem(i,items[i]));
        }
        mAdapter.resetItems(stringItems);
    }

    public void setListener(OnItemClickListener listener){
        mListener = listener;
    }


    public interface OnItemClickListener{
        void onItemClick(int pos);
    }

}
