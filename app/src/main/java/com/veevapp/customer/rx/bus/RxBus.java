package com.veevapp.customer.rx.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by iman.
 * iman.neofight@gmail.com
 */
public class RxBus {

    private static RxBus mInstance;
    public static RxBus getInstance(){
        if(mInstance==null)
            mInstance = new RxBus();
        return  mInstance;
    }

    public RxBus() {}

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

}