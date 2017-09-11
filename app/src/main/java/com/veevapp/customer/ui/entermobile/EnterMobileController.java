package com.veevapp.customer.ui.entermobile;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.veevapp.customer.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.ui.confirmcode.ConfirmCodeController;
import com.veevapp.customer.util.BundleBuilder;
import com.veevapp.customer.util.GlobalToast;

import butterknife.BindView;
import butterknife.OnClick;

import static com.veevapp.customer.ui.confirmcode.ConfirmCodeController.MUST_GET_SELLER_INFO;

public class EnterMobileController extends BaseController implements EnterMobileContract.View {

    @BindView(R.id.edit_text_mobile_number)
    EditText mobileNumberEditText;

    @BindView(R.id.button_send)
    Button sendButton;

    private EnterMobileContract.Presenter presenter;
    private ProgressDialog progressDialog = null;

    public static EnterMobileController newInstance() {
        return new EnterMobileController();
    }

    public EnterMobileController() {
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        setActive(true);
        presenter = new EnterMobilePresenter(DataRepository.getInstance(), this);
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
    public void showInvalidMobileNumberError() {
        GlobalToast.makeToast(getActivity(), getResources().getString(R.string.toast_invalid_mobile_number), Toast.LENGTH_SHORT);
    }

    @Override
    public void showEmptyMobileNumberError() {
        GlobalToast.makeToast(getActivity(), getResources().getString(R.string.toast_enter_your_mobile_number), Toast.LENGTH_SHORT);
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

    @OnClick(R.id.button_send)
    public void sendMobileNumberOnCLick() {
        presenter.sendMobileToServer(mobileNumberEditText.getText().toString().trim());
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_enter_mobile, container, false);
    }

    @Override
    public void showConfirmCodeUI(boolean mustGetSellerInfo) {
        ConfirmCodeController confirmCodeController = ConfirmCodeController.newInstance(
                new BundleBuilder().putBoolean(MUST_GET_SELLER_INFO, mustGetSellerInfo).build(),
                mobileNumberEditText.getText().toString().trim()
        );
        confirmCodeController.setTargetController(this);
        getRouter().pushController(
                RouterTransaction.with(confirmCodeController)
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }
}
