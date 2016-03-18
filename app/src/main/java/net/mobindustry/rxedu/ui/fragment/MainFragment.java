package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import net.mobindustry.rxedu.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    @Bind(R.id.tapBufferFragmentButton)
    TextView buttonTapFragment;
    @Bind(R.id.searchFragmentButton)
    TextView buttonSearchFragment;
    @Bind(R.id.validationFragmentButton)
    TextView buttonValidationFragment;
    @Bind(R.id.retrofitFragmentButton)
    TextView buttonRetrofitFragment;
    @Bind(R.id.doubleBindingFragmentButton)
    TextView buttonDoubleBindingFragment;
    @Bind(R.id.rxBusFragmentButton)
    TextView buttonRxBusFragment;
    @Bind(R.id.testFragmentButton)
    TextView buttonTestFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        RxView.clicks(buttonTapFragment).subscribe(v -> onClickAction(new BufferFragment()));
        RxView.clicks(buttonSearchFragment).subscribe(v -> onClickAction(new SearchFragment()));
        RxView.clicks(buttonValidationFragment).subscribe(v -> onClickAction(new ValidationFragment()));
        RxView.clicks(buttonRetrofitFragment).subscribe(v -> onClickAction(new RetrofitFragment()));
        RxView.clicks(buttonDoubleBindingFragment).subscribe(v -> onClickAction(new DoubleBindingFragment()));
        RxView.clicks(buttonRxBusFragment).subscribe(v -> onClickAction(new RxBusFragment()));
        RxView.clicks(buttonTestFragment).subscribe(v -> onClickAction(new TestFragment()));

        return view;
    }

    private void onClickAction(@NonNull Fragment fragment) {
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }
}
