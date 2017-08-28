package com.veevapp.customer.ui.addbuyrequest;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;

public class AddBuyRequestController extends BaseBackStackController {

    public static AddBuyRequestController newInstance() {
        return new AddBuyRequestController();
    }

    @Override
    public boolean canHandleBackStack() {
        return false;
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_add_buy_request, container, false);
    }
}
