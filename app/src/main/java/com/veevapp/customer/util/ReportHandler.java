package com.veevapp.customer.util;

import com.veevapp.customer.data.models.ReportTypeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class ReportHandler {
    private static ReportHandler mInstance;
    public static ReportHandler getInstance(){
       if(mInstance==null)
           mInstance = new ReportHandler();
       return mInstance;
    }


    private List<ReportTypeItem> reportTypes;
    public ReportHandler(){
        reportTypes = new ArrayList<>();
        reportTypes.add(new ReportTypeItem(1,"قیمت نا مناسب"));
        reportTypes.add(new ReportTypeItem(2,"محتوا نا مناسب"));
    }

    public List<ReportTypeItem> getReportTypes(){
        return reportTypes;
    }
}
