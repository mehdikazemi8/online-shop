package com.veevapp.customer.ui.filter;

import com.veevapp.customer.BasePresenter;
import com.veevapp.customer.BaseView;
import com.veevapp.customer.data.models.Category;
import com.veevapp.customer.data.models.SubCategory;

import java.util.List;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class FilterContract {
    public interface View extends BaseView<Presenter>{
        void showCategories(List<Category> categoryList);

        void showSubCategories(List<SubCategory> subCategoryList);

        void setPriceFrom(String s);

        void setPriceTo(String s);

        void setCategory(Category cat);

        void setSubCategory(SubCategory subCat);
    }

    public interface Presenter extends BasePresenter{
        void loadSubCategories(String categoryID);
    }
}
