package net.mobindustry.rxedu.rx.rxBus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {

    private static RxBus rxBusSingleton = null;
    private final Subject<Object, Object> rxBus = new SerializedSubject<>(PublishSubject.create());

    public static RxBus getRxBusSingleton() {
        if (rxBusSingleton == null) {
            rxBusSingleton = new RxBus();
        }

        return rxBusSingleton;
    }

    public void send(Object object) {
        rxBus.onNext(object);
    }

    public Observable<Object> toObserverable() {
        return rxBus;
    }

    public boolean hasObservers() {
        return rxBus.hasObservers();
    }
}
