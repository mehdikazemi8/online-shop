package com.veevapp.customer.ui.main;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.support.RouterPagerAdapter;
import com.veevapp.customer.BaseController;
import com.veevapp.customer.R;
import com.veevapp.customer.controller.base.BaseBackStackController;
import com.veevapp.customer.controller.base.HeaderController;
import com.veevapp.customer.customview.NonSwipeableViewPager;
import com.veevapp.customer.data.DataRepository;
import com.veevapp.customer.data.local.PreferenceManager;
import com.veevapp.customer.ui.addbuyrequest.AddBuyRequestController;
import com.veevapp.customer.ui.buyrequests.BuyRequestsController;
import com.veevapp.customer.ui.profile.ProfileController;
import com.veevapp.customer.ui.specialoffers.SpecialOffersController;

import butterknife.BindView;

public class MainController extends HeaderController implements MainContract.View {

    private final String[] pageTitles = {"پروفایل", "پیشنهاد ویژه", "درخواست های من", "ثبت درخواست"};

    private final int NUMBER_OF_TABS = 4;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    NonSwipeableViewPager viewPager;

    public static MainController newInstance() {
        return new MainController();
    }

    private MainContract.Presenter presenter;
    private final RouterPagerAdapter pagerAdapter;
    private BaseBackStackController[] controllers = new BaseBackStackController[NUMBER_OF_TABS];

    public MainController() {
        pagerAdapter = new RouterPagerAdapter(this) {
            @Override
            public void configureRouter(@NonNull Router router, int position) {
                if (!router.hasRootController()) {
                    router.setRoot(RouterTransaction.with(getPage(position)));
                }
            }

            @Override
            public int getCount() {
                return NUMBER_OF_TABS;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return pageTitles[position];
            }
        };
        pagerAdapter.setMaxPagesToStateSave(NUMBER_OF_TABS);
    }

    private BaseController getPage(int position) {
        switch (position) {
            case 0:
                controllers[0] = ProfileController.newInstance();
                break;

            case 1:
                controllers[1] = SpecialOffersController.newInstance();
                break;

            case 2:
                controllers[2] = BuyRequestsController.newInstance();
                break;

            default:
                controllers[3] = AddBuyRequestController.newInstance();
                break;
        }

        controllers[position].setTargetController(MainController.this);
        return controllers[position];
    }

    @Override
    protected void onViewBound(@NonNull View view) {
        super.onViewBound(view);

        headerBackButton.setVisibility(View.INVISIBLE);
//        headerTitle.setText(getActivity().getString(R.string.veevapp));
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(NUMBER_OF_TABS - 1);
        viewPager.setOffscreenPageLimit(NUMBER_OF_TABS - 1);

        headerTitle.setText(getActivity().getString(R.string.app_name));

        initTabLayout();

        setActive(true);
        presenter = new MainPresenter(PreferenceManager.getInstance(getActivity()), DataRepository.getInstance(), this);
        presenter.start();
    }

    void initTabLayout() {
        for (int tabIdx = 0; tabIdx < NUMBER_OF_TABS; tabIdx++) {
            TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.template_tab, null);
            textView.setText(pageTitles[tabIdx]);
            tabLayout.getTabAt(tabIdx).setCustomView(textView);
        }
    }

    @Override
    protected View inflateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return inflater.inflate(R.layout.controller_main, container, false);
    }

    @Override
    public boolean handleBack() {
        if (controllers[viewPager.getCurrentItem()].canHandleBackStack()) {
            return true;
        } else {
            if (viewPager.getCurrentItem() != NUMBER_OF_TABS - 1) {
                viewPager.setCurrentItem(NUMBER_OF_TABS - 1);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    protected void onDestroyView(View view) {
        if (!getActivity().isChangingConfigurations()) {
            viewPager.setAdapter(null);
        }
        tabLayout.setupWithViewPager(null);
        super.onDestroyView(view);
    }

    public void changeTab(int idx) {
        viewPager.setCurrentItem(idx);
    }

    public int getNUMBER_OF_TABS() {
        return NUMBER_OF_TABS;
    }
}
