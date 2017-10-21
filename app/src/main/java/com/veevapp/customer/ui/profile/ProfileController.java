package com.veevapp.customer.ui.profile;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.models.Customer;
import com.veevapp.customer.ui.entermobile.EnterMobileController;
import com.veevapp.customer.view.DialogMaker;
import com.veevapp.customer.view.customwidget.AppTextView;

import butterknife.BindView;
import butterknife.OnClick;

public class ProfileController extends BaseBackStackController implements ProfileContract.View {

    public static ProfileController newInstance() {
        return new ProfileController();
    }

    private ProfileContract.Presenter presenter;


    @BindView(R.id.tv_name)
    AppTextView tvName;

    @BindView(R.id.tv_lastName)
    AppTextView tvLastName;

    @BindView(R.id.tv_phoneNumber)
    AppTextView tvPhoneNumber;

    @OnClick(R.id.btn_logout)
    public void logoutOnClick() {
        presenter.onLogoutClicked();
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

        tvName.setText(getActivity().getString(R.string.template_name,customer.name()));
        tvLastName.setText(getActivity().getString(R.string.template_last_name,customer.family()));
        tvPhoneNumber.setText(getActivity().getString(R.string.template_mobile,customer.mobile()));
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

    @Override
    public void showLogoutConfirmation() {
        DialogMaker.makeLogoutConfirmDialog(getActivity(), (dialogInterface, i) -> presenter.onLogoutConfirmed()).show();
    }
}
