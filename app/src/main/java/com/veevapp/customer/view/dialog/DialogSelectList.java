package com.veevapp.customer.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.StringItem;
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
public class DialogSelectList extends AppDialog {

    private String title;
    private String[] items;

    public DialogSelectList(@NonNull Context context, String title, String[] items) {
        super(context);

        this.title = title;
        this.items = items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext(), title, items);
    }

    @BindView(R.id.tv_title)
    AppTextView tvTitle;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    AdapterSelectableList mAdapter;

    OnItemClickListener mListener;

    private void init(Context context, String title, String[] items) {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_select_item, null, false);
        setContentView(v);
        ButterKnife.bind(this);

        mAdapter = new AdapterSelectableList(context, (v1, vh, pos) -> {
            AdapterSelectableList.ViewHolderSelectableList viewHolder = (AdapterSelectableList.ViewHolderSelectableList) vh;
            if (v1 == viewHolder.rlContainer) {
                if (mListener != null)
                    mListener.onItemClick(pos);
            }
        });
        rvContent.setLayoutManager(new LinearLayoutManager(context));
        rvContent.setAdapter(mAdapter);
        rvContent.setHasFixedSize(true);

        setTitle(title);
        setItems(items);
    }

    @Override
    public void setTitle(CharSequence title) {
        tvTitle.setText(title);
    }

    public void setItems(String[] items) {
        List<StringItem> stringItems = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            stringItems.add(new StringItem(i, items[i]));
        }
        mAdapter.resetItems(stringItems);
    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

}
