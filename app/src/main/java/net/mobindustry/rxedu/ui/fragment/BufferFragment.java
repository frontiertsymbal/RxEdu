package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.ui.adapter.ResultAdapter;
import net.mobindustry.rxedu.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class BufferFragment extends Fragment {

    private static final String TAG = BufferFragment.class.getSimpleName();
    private final int BUFFER_TIMEOUT = 2;

    private TextView vTapButton;
    private ListView vResultListView;

    private ResultAdapter mResultAdapter;
    private ArrayList<String> mResultList;
    private Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buffer, container, false);
        vResultListView = (ListView) view.findViewById(R.id.resultListView);
        vTapButton = (TextView) view.findViewById(R.id.tapMeButton);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mResultList = new ArrayList<>();
        mResultAdapter = new ResultAdapter(getContext());
        vResultListView.setAdapter(mResultAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSubscription = getBufferedSubscription();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSubscription.unsubscribe();
    }

    private Subscription getBufferedSubscription() {
        return RxView.clicks(vTapButton)
                .map(onClickEvent -> {
                    showResult(getContext().getString(R.string.gotATap));
                    return 1;
                })
                .buffer(BUFFER_TIMEOUT, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        integers -> {
                            if (integers.size() > 0) {
                                showResult(getContext().getString(R.string.tapsCount, integers.size()));
                            } else {
                                showResult(getContext().getString(R.string.noTapsInPeriod));
                            }
                        },
                        throwable -> showResult(getContext().getString(R.string.onError)),
                        () -> Log.e(TAG, getContext().getString(R.string.onCompleted)) // you'll never reach here
                );
    }

    private void showResult(String resultMessage) {
        Utils.showResult(resultMessage, mResultList, mResultAdapter);
    }
}
