package com.veevapp.customer.ui.addbuyrequest;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.BuyRequest;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

public class AddBuyRequestContract {

    public interface View extends BaseView<Presenter> {

        void showCategories(List<Category> categoryList);

        void showSubCategories(List<SubCategory> subCategoryList);

    }

    public interface Presenter extends BasePresenter {

        void onSubmitBuyRequest(BuyRequest buyRequest);

        void loadSubCategories(String categoryID);
    }
}
