package net.mobindustry.rxedu.rx;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

public final class OnSubscribeBoundedCache<T> implements Observable.OnSubscribe<T> {

    @SuppressWarnings("rawtypes")
    static final AtomicIntegerFieldUpdater<OnSubscribeBoundedCache> SRC_SUBSCRIBED_UPDATER
            = AtomicIntegerFieldUpdater.newUpdater(OnSubscribeBoundedCache.class, "sourceSubscribed");
    protected final Observable<? extends T> source;
    protected final Subject<? super T, ? extends T> cache;
    volatile int sourceSubscribed;

    public OnSubscribeBoundedCache(Observable<? extends T> source, int bufferSize) {
        this.source = source;
        this.cache = ReplaySubject.<T>createWithSize(bufferSize);
    }

    @Override
    public void call(Subscriber<? super T> s) {
        if (SRC_SUBSCRIBED_UPDATER.compareAndSet(this, 0, 1)) {
            source.subscribe(cache);
        }
        cache.unsafeSubscribe(s);
    }
}