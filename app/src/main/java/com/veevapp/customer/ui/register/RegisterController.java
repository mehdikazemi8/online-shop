package com.veevapp.customer.ui.register;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.remote.request.RegisterRequest;
import com.veevapp.customer.ui.main.MainController;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterController extends HeaderController implements RegisterContract.View {

    private RegisterContract.Presenter presenter;
    private ProgressDialog progressDialog = null;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.family)
    EditText family;
    @BindView(R.id.mobile_number)
    EditText mobileNumber;
    @BindView(R.id.telegram_id)
    EditText telegramID;

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
        presenter.registerCustomer(
                new RegisterRequest(
                        name.getText().toString().trim(),
                        family.getText().toString().trim(),
                        mobileNumber.getText().toString().trim()
                )
        );
    }

    public static RegisterController newInstance() {
        return new RegisterController();
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

        presenter = new RegisterPresenter(this, DataRepository.getInstance());
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
