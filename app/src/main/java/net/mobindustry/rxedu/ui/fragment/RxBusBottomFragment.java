package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.rxBus.RxBus;
import net.mobindustry.rxedu.rxBus.TapEvent;
import net.mobindustry.rxedu.utils.AnimationHelper;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.observables.ConnectableObservable;
import rx.subscriptions.CompositeSubscription;

public class RxBusBottomFragment
        extends Fragment {

    private final int DEBOUNCE_TIMEOUT = 1;

    private TextView vTapCount;
    private TextView vTapText;

    private RxBus mRxBus;
    private CompositeSubscription mCompositeSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxbus_bottom, container, false);

        vTapCount = (TextView) view.findViewById(R.id.rxbusTapCount);
        vTapText = (TextView) view.findViewById(R.id.rxbusTapText);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRxBus = RxBus.getRxBusSingleton();
    }

    @Override
    public void onStart() {
        super.onStart();
        mCompositeSubscription = new CompositeSubscription();

        ConnectableObservable<Object> tapEventEmitter = mRxBus.toObserverable().publish();

        mCompositeSubscription
                .add(tapEventEmitter.subscribe(event -> {
                    if (event instanceof TapEvent) {
                        AnimationHelper.showTapText(vTapText);
                    }
                }));


        mCompositeSubscription.add(tapEventEmitter.publish(stream ->
                        stream.buffer(stream.debounce(DEBOUNCE_TIMEOUT, TimeUnit.SECONDS)))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taps -> {
                    AnimationHelper.showTapCount(vTapCount, taps.size());
                })
        );

        mCompositeSubscription.add(tapEventEmitter.connect());
    }

    @Override
    public void onStop() {
        super.onStop();
        mCompositeSubscription.clear();
    }
}