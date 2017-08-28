package com.veevapp.customer.controller.base;

import com.veevapp.customer.BaseController;

public abstract class BaseBackStackController extends BaseController {
    public abstract boolean canHandleBackStack();
}
