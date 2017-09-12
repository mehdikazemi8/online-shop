package com.veevapp.customer.ui.profile;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;

import butterknife.BindView;

public class ProfileController extends BaseBackStackController implements ProfileContract.View {

    public static ProfileController newInstance() {
        return new ProfileController();
    }

    private ProfileContract.Presenter presenter;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.family)
    TextView family;
    @BindView(R.id.mobile)
    TextView mobile;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_profile, container, false);
    }

    private void init() {
        Customer customer = PreferenceManager.getInstance(getActivity()).getCustomerInfo();
        if(customer == null) {
            return;
        }

        name.setText(customer.name());
        family.setText(customer.family());
        mobile.setText(customer.mobile());
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        init();

        setActive(true);
        presenter = new ProfilePresenter();
        presenter.start();
    }

    @Override
    public boolean canHandleBackStack() {
        return false;
    }
}
