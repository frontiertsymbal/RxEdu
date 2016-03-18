package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mobindustry.rxedu.R;

public class RxBusFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rx_bus, container, false);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.rxBusTopLayout, new RxBusTopFragment())
                .replace(R.id.rxBusBottomLayout, new RxBusBottomFragment())
                .commit();

        return view;
    }
}
