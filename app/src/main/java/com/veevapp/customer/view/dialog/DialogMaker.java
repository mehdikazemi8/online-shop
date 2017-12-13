package com.veevapp.customer.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import com.veevapp.customer.R;
import com.veevapp.customer.view.dialog.DialogSelectList;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class DialogMaker {
    public static Dialog makeSelectListDialog(Context ctx,String title,
                                              String[] items,DialogInterface.OnClickListener onClickListener){
        DialogSelectList d = new DialogSelectList(ctx, title, items);
//        d.setTitle(title);
//        d.setItems(items);
        d.setListener(pos -> {
            d.dismiss();
            if(onClickListener!=null)
                onClickListener.onClick(null,pos);
        });
        return d;
    }

    public static Dialog makeLogoutConfirmDialog(Context ctx, DialogInterface.OnClickListener positiveListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(ctx.getResources().getString(R.string.logout));
        builder.setMessage(ctx.getResources().getString(R.string.logout_confirmation));
        builder.setPositiveButton(ctx.getString(R.string.yes), (dialogInterface, i) -> {
            if(positiveListener!=null)
                positiveListener.onClick(dialogInterface,i);

            dialogInterface.dismiss();
        });
        builder.setNegativeButton(ctx.getString(R.string.no), (dialogInterface, i) -> dialogInterface.dismiss());
        return builder.create();
    }
}
