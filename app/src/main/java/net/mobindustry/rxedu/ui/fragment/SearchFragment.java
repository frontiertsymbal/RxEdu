package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.ui.adapter.ResultAdapter;
import net.mobindustry.rxedu.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class SearchFragment extends Fragment {

    private static final String TAG = SearchFragment.class.getSimpleName();
    private final int DEBOUNCE_DURATION = 400;

    @Bind(R.id.buttonClear)
    TextView vClearButton;
    @Bind(R.id.resultListView)
    ListView vResultListView;
    @Bind(R.id.searchEditText)
    EditText vSearchEditText;

    private ResultAdapter mResultAdapter;
    private ArrayList<String> mResultList;
    private Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mResultList = new ArrayList<>();
        mResultAdapter = new ResultAdapter(getContext());
        vResultListView.setAdapter(mResultAdapter);

        RxView.clicks(vClearButton).subscribe(v -> {
            mResultList = new ArrayList<>();
            mResultAdapter.clear();
        });

        mSubscription = RxTextView.textChangeEvents(vSearchEditText)
                .debounce(DEBOUNCE_DURATION, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        textChangeEvent -> showResult(getString(R.string.searchingFor,
                                textChangeEvent.text().toString())),
                        e -> showResult(getString(R.string.onError)),
                        () -> Log.e(TAG, getString(R.string.onCompleted))
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    private void showResult(String resultMessage) {
        Utils.showResult(resultMessage, mResultList, mResultAdapter);
    }
}
