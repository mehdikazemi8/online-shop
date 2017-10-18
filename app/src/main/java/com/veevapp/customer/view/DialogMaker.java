package com.veevapp.customer.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.veevapp.customer.view.dialog.DialogSelectList;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogMaker {
    public static Dialog makeSelectListDialog(Context ctx,String title,
                                              String[] items,DialogInterface.OnClickListener onClickListener){

        DialogSelectList d = new DialogSelectList(ctx);
        d.setTitle(title);
        d.setItems(items);
        d.setListener(pos -> {
            d.dismiss();
            if(onClickListener!=null)
                onClickListener.onClick(null,pos);
        });
        return d;

    }
}
