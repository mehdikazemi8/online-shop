package com.veevapp.customer.view.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.veevapp.customer.R;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class ByeRequestDecoration extends DividerItemDecoration{
    int padding;

    public ByeRequestDecoration(Context context) {
        super(context, VERTICAL);
        padding = context.getResources().getDimensionPixelOffset(R.dimen.tbr_padding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = padding/2;
        outRect.top = padding/2;
        outRect.left = padding/2;
        outRect.right = padding/2;
    }
}
