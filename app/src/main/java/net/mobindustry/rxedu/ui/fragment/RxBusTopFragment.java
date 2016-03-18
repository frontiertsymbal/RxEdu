package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding.view.RxView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.rx.rxBus.RxBus;
import net.mobindustry.rxedu.rx.rxBus.TapEvent;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RxBusTopFragment extends Fragment {

    @Bind(R.id.rxBusTap)
    Button tapButton;

    private RxBus mRxBus;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rxbus_top, container, false);
        ButterKnife.bind(this, view);

        RxView.clicks(tapButton).subscribe(v -> {
            if (mRxBus.hasObservers()) {
                mRxBus.send(new TapEvent());
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRxBus = RxBus.getRxBusSingleton();
    }
}
