package com.veevapp.customer.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veevapp.customer.R;
import com.veevapp.customer.data.models.StringItem;
import com.veevapp.customer.view.adapter.AdapterSelectableListRadio;
import com.veevapp.customer.view.customwidget.AppTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogSelectListRadio extends AppDialog {

    private String title;
    private List<StringItem> items;
    private int defaultSelectedPos;

    public DialogSelectListRadio(@NonNull Context context,OnItemSelectedListener listener,
                                 String title,int defaultSelectedPos, List<StringItem> items) {
        super(context);

        this.mListener = listener;
        this.title = title;
        this.items = items;
        this.defaultSelectedPos = defaultSelectedPos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext(), title,defaultSelectedPos, items);
    }

    @BindView(R.id.tv_title)
    AppTextView tvTitle;

    @BindView(R.id.rv_content)
    RecyclerView rvContent;

    AdapterSelectableListRadio mAdapter;

    OnItemSelectedListener mListener;

    private void init(Context context, String title,
                      int defaultSelectedPos, List<StringItem> stringItems) {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_select_item, null, false);
        setContentView(v);
        ButterKnife.bind(this);

        mAdapter = new AdapterSelectableListRadio(context, (v1, vh, pos) -> {
            AdapterSelectableListRadio.ViewHolderSelectableListRadio viewHolder =
                    (AdapterSelectableListRadio.ViewHolderSelectableListRadio) vh;
            if (v1 == viewHolder.csContainer || v1 == viewHolder.rbSelect) {
                if (mListener != null)
                    mListener.onItemSelected(pos);
            }
        });
        mAdapter.refreshCurrentSelectedPos(defaultSelectedPos);
        mAdapter.resetItems(stringItems);

        rvContent.setLayoutManager(new LinearLayoutManager(context));
        rvContent.setAdapter(mAdapter);
        rvContent.setHasFixedSize(true);

        setTitle(title);
    }

    @Override
    public void setTitle(CharSequence title) {
        if(TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        }else {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
    }

    public void setListener(OnItemSelectedListener listener) {
        mListener = listener;
    }


    public interface OnItemSelectedListener {
        void onItemSelected(int pos);
    }

}
