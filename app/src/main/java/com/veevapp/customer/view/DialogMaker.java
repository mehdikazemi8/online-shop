package com.veevapp.customer.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogMaker {
    public static Dialog makeSelectListDialog(Context ctx,String title,
                                              String[] items,DialogInterface.OnClickListener onClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setItems(items, (dialogInterface, i) -> {
            if(onClickListener!=null)
                onClickListener.onClick(dialogInterface,i);
        });
        return builder.create();
    }
}
