package com.veevapp.customer.ui.register;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.data.remote.request.RegisterRequest;
import com.veevapp.customer.ui.main.MainController;
import com.veevapp.customer.view.customwidget.AppEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterController extends HeaderController implements RegisterContract.View {

    private RegisterContract.Presenter presenter;
    private ProgressDialog progressDialog = null;

    @BindView(R.id.et_firstName)
    AppEditText etFirstName;
    @BindView(R.id.et_lastName)
    AppEditText etLastName;
    @BindView(R.id.mobile_number)
    AppEditText mobileNumber;
    @BindView(R.id.telegram_id)
    AppEditText telegramID;

    String mobile;

    @Override
    public void showProgressBar() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(null);
        progressDialog.setMessage(getActivity().getString(R.string.please_wait));
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        if (progressDialog != null) {
            progressDialog.hide();
        }
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_register, container, false);
    }

    @BindView(R.id.button_next_step)
    Button buttonNextStep;

    @OnClick(R.id.button_next_step)
    public void nextStepOnClick() {
        String strFname = etFirstName.getText().toString().trim();
        if(TextUtils.isEmpty(strFname)){
            etFirstName.setError(getActivity().getString(R.string.please_fill_this_field));
            return;
        }


        String strLname = etLastName.getText().toString().trim();
        if(TextUtils.isEmpty(strLname)){
            etLastName.setError(getActivity().getString(R.string.please_fill_this_field));
            return;
        }

        presenter.registerCustomer(
                new RegisterRequest(
                        strFname, strLname,
                        mobileNumber.getText().toString().trim()
                )
        );
    }

    public static RegisterController newInstance(String mobile) {
        RegisterController controller = new RegisterController();
        controller.mobile = mobile;
        return controller;
    }

    public RegisterController() {
    }

    @Override
    public boolean handleBack() {
        // todo, handle back and show dialog not to lose chance to complete registration
        return super.handleBack();
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        headerTitle.setText(getResources().getString(R.string.app_name));
        headerBackButton.setVisibility(View.INVISIBLE);

        mobileNumber.setText(mobile);

        presenter = new RegisterPresenter(this, DataRepository.getInstance(), PreferenceManager.getInstance(getApplicationContext()));
        presenter.start();
    }

    @Override
    public void showMainPageUI() {
        getRouter().setRoot(
                RouterTransaction.with(MainController.newInstance())
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);

        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }
}
