package com.veevapp.customer.view.customwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.veevapp.customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class SelectableFieldView extends RelativeLayout {

    public SelectableFieldView(Context context) {
        super(context);
        init(context);
    }

    public SelectableFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        init(context);
    }

    public SelectableFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context,attrs);
        init(context);
    }

    String mText;

    @BindView(R.id.tv_title)
    public AppTextView tvTitle;
    @BindView(R.id.cs_container)
    ConstraintLayout csContainer;

    Object selectedObject;

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            int[] attrsArray = {
                    android.R.attr.text
            };
            TypedArray array = context.obtainStyledAttributes(
                    attrs, attrsArray);
            try {
                mText = array.getString(0);
            } catch (Exception e) {
                mText = "";
            }
            array.recycle();
        }
    }

    private void init(Context context) {
        inflate(context, R.layout.selectable_field_view,this);
        ButterKnife.bind(this);
        tvTitle.setText(mText);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        csContainer.setOnClickListener(view -> {
            tvTitle.setError(null);
            if(l!=null)
                l.onClick(view);
        });
    }


    public Object getSelectedObject() {
        return selectedObject;
    }

    public void setSelectedObject(Object selectedObject) {
        this.selectedObject = selectedObject;
    }

    public void setIsSelected(boolean isSelected){
        if(isSelected) {
            tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.fields_text_selected));
            tvTitle.setTypeface(null, Typeface.BOLD);
        }else{
            tvTitle.setTextColor(ContextCompat.getColor(getContext(),R.color.fields_text_unselected));
            tvTitle.setTypeface(null, Typeface.NORMAL);
        }
    }

    public String getText() {
        return tvTitle.getText().toString();
    }

    public void setText(String mText) {
        this.mText = mText;
        tvTitle.setText(mText);
    }
}
