package com.veevapp.customer.view.customwidget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.veevapp.customer.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class AddImage extends RelativeLayout{
    public static final byte TYPE_ADD_IMAGE = 0;
    public static final byte TYPE_SHOW_IMAGE = 1;


    @BindView(R.id.cv_container)
    AppCardView cvContainer;

    @BindView(R.id.cs_addImage)
    ConstraintLayout csAddImage;

    @BindView(R.id.cs_showImage)
    ConstraintLayout csShowImage;

    @BindView(R.id.iv_showingImage)
    public AppImageView ivShowingImage;

    public AddImage(Context context) {
        super(context);
        init(context);
    }

    public AddImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }

    private void init(Context context) {
        inflate(context, R.layout.add_image,this);
        ButterKnife.bind(this);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        cvContainer.setOnClickListener(l);
    }

    public void setType(int type){
        switch (type){
            case TYPE_ADD_IMAGE :
                csAddImage.setVisibility(View.VISIBLE);
                csShowImage.setVisibility(View.GONE);
                break;

            case TYPE_SHOW_IMAGE :
                csAddImage.setVisibility(View.GONE);
                csShowImage.setVisibility(View.VISIBLE);
                break;
        }
    }
}