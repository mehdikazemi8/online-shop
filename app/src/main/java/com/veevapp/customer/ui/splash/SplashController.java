package com.veevapp.customer.ui.splash;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.veevapp.customer.controller.base.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.ui.entermobile.EnterMobileController;
import com.veevapp.customer.ui.main.MainController;
import com.veevapp.customer.ui.register.RegisterController;

public class SplashController extends BaseController implements SplashContract.View {

    private SplashContract.Presenter presenter;

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_splash, container, false);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
        presenter = new SplashPresenter(DataRepository.getInstance(), PreferenceManager.getInstance(getActivity()), this);
        presenter.start();
    }

    @Override
    public void showEnterMobileUI() {
        EnterMobileController enterMobileController = new EnterMobileController();
        enterMobileController.setTargetController(this);
        getRouter().replaceTopController(
                RouterTransaction.with(enterMobileController)
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }

    @Override
    public void showMainPageUI() {
        MainController mainController = new MainController();
        getRouter().setRoot(
                RouterTransaction.with(mainController)
                        .pushChangeHandler(new VerticalChangeHandler())
                        .popChangeHandler(new VerticalChangeHandler())
        );
    }

    @Override
    public void showRegisterUI() {
        getRouter().setRoot(
                RouterTransaction.with(RegisterController.newInstance())
                        .pushChangeHandler(new FadeChangeHandler())
                        .popChangeHandler(new FadeChangeHandler())
        );
    }
}
