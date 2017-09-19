package com.veevapp.customer.ui.profile;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.ui.entermobile.EnterMobileController;

import butterknife.BindView;
import butterknife.OnClick;

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

    @OnClick(R.id.logout)
    public void logoutOnClick() {
        presenter.logout();
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_profile, container, false);
    }

    private void init() {
        Customer customer = PreferenceManager.getInstance(getActivity()).getCustomerInfo();
        if (customer == null) {
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
        presenter = new ProfilePresenter(DataRepository.getInstance(), PreferenceManager.getInstance(getActivity()), this);
        presenter.start();
    }

    @Override
    public boolean canHandleBackStack() {
        return false;
    }

    @Override
    public void showEnterMobileUI() {
        getParentController().getRouter().setRoot(
                RouterTransaction.with(EnterMobileController.newInstance())
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }
}
