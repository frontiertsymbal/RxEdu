package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.ui.adapter.ResultAdapter;
import net.mobindustry.rxedu.ui.dialog.ProgressDialog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class TestFragment extends Fragment {

    private static final String TAG = TestFragment.class.getSimpleName();

    @Bind(R.id.testButton)
    TextView buttonTest;

    @Bind(R.id.viewContainer)
    LinearLayout viewContainer;

    private ArrayList<String> dataString;
    private ProgressDialog progressDialog;
    private Subscription subscription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        ButterKnife.bind(this, view);
        progressDialog = ProgressDialog.newInstance(getString(R.string.loading));

        dataString = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataString.add(getString(R.string.dataString) + i);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = new ListView(getContext());
        ResultAdapter resultAdapter = new ResultAdapter(getContext());
        listView.setAdapter(resultAdapter);
        viewContainer.addView(listView);
        Observable.range(0, 100).subscribe(i -> resultAdapter.add(String.valueOf(i)));

        RxView.clicks(buttonTest).subscribe(click -> {
                    TextView text = new TextView(getContext());
                    text.setText("Hello");
                    viewContainer.addView(text);
                    Observable
                            .interval(1, TimeUnit.SECONDS)
                            .takeUntil(RxView.detaches(text))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Long>() {
                                @Override
                                public void onCompleted() {
                                    Log.e(TAG, "onCompleted: ");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "onError: ", e);
                                }

                                @Override
                                public void onNext(Long aLong) {
                                    Log.e(TAG, "onNext: " + aLong);
                                    text.setText("hello " + aLong);
                                    if (aLong == 10) {
                                        viewContainer.removeAllViews();
                                    }
                                }
                            });
                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    /*
    Observable.create(sub -> {
                    for (String dataStr : dataString) {
                        sub.onNext(dataStr);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            sub.onError(e);
                        }
                    }
                    sub.onCompleted();
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(() -> DialogHelper.showProgressDialog(getContext(), progressDialog))
                        .doOnCompleted(() -> {
                            DialogHelper.dismissProgressDialog(getContext());
                            Toast.makeText(getContext(), R.string.longOperationComplete, Toast.LENGTH_LONG).show();
                        })
                        .subscribe(s -> Log.e(TAG, getString(R.string.onNext) + s),
                                e -> Log.e(TAG, getString(R.string.onError), e),
                                () -> Log.e(TAG, getString(R.string.onCompleted))
                        )
     */
}
