package com.veevapp.customer.ui.confirmcode;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.veevapp.customer.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.ui.main.MainController;
import com.veevapp.customer.ui.register.RegisterController;
import com.veevapp.customer.util.GlobalToast;

import butterknife.BindView;
import butterknife.OnClick;

public class ConfirmCodeController extends BaseController implements ConfirmCodeContract.View {

    public static String MUST_GET_SELLER_INFO = "MUST_GET_SELLER_INFO";

    private Bundle bundle = null;
    private String mobile = null;

    public static ConfirmCodeController newInstance(Bundle bundle, String mobile) {
        ConfirmCodeController instance = new ConfirmCodeController();
        instance.bundle = bundle;
        instance.mobile = mobile;
        return instance;
    }

    @BindView(R.id.button_confirm)
    Button confirmButton;
    @BindView(R.id.edit_text_code)
    EditText codeEditText;

    @OnClick(R.id.button_confirm)
    public void confirmOnClick() {
        presenter.sendCodeToServer(codeEditText.getText().toString().trim());
    }

    private ConfirmCodeContract.Presenter presenter;
    private ProgressDialog progressDialog = null;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_confirm_code, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter = new ConfirmCodePresenter(PreferenceManager.getInstance(getActivity()), DataRepository.getInstance(), this, bundle.getBoolean(MUST_GET_SELLER_INFO, false), mobile);
        setActive(true);
        presenter.start();
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    @Override
    public void showEmptyCodeError() {
        GlobalToast.makeToast(getActivity(), getResources().getString(R.string.toast_enter_code), Toast.LENGTH_SHORT);
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getResources().getString(R.string.please_wait));
        }

        progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.hide();
    }

    @Override
    public void showFailureError() {
        GlobalToast.makeToast(getActivity(), getResources().getString(R.string.error_wrong_code), Toast.LENGTH_SHORT);

    }

    @Override
    public void showNetworkFailureError() {
        GlobalToast.makeToast(getActivity(), getResources().getString(R.string.error_network_failure), Toast.LENGTH_SHORT);
    }

    @Override
    public void showMainPageUI() {
        MainController mainController = MainController.newInstance();
        getRouter().setRoot(RouterTransaction.with(mainController));
    }

    @Override
    public void showRegisterUI() {
        getRouter().setRoot(RouterTransaction.with(RegisterController.newInstance()));
    }
}
