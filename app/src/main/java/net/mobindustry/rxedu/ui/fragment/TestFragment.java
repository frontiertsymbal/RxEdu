package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.ui.dialog.ProgressDialog;
import net.mobindustry.rxedu.utils.DialogHelper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TestFragment extends Fragment {

    private static final String TAG = TestFragment.class.getSimpleName();

    private TextView buttonTest;
    private ArrayList<String> dataString;
    private ProgressDialog progressDialog = ProgressDialog.newInstance("Loading");

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        buttonTest = (TextView) view.findViewById(R.id.testButton);
        dataString = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataString.add("dataString " + i);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RxView.clicks(buttonTest).subscribe(click ->
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
                            Toast.makeText(getContext(), "Long operation completed", Toast.LENGTH_LONG).show();
                        })
                        .subscribe(s -> Log.e(TAG, "onNext: " + s),
                                e -> Log.e(TAG, "onError", e),
                                () -> Log.e(TAG, "onCompleted")
                        ));
    }
}
