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
import net.mobindustry.rxedu.api.ApiManager;
import net.mobindustry.rxedu.briteDb.DbHelper;
import net.mobindustry.rxedu.model.User;
import net.mobindustry.rxedu.rx.OnSubscribeBoundedCache;
import net.mobindustry.rxedu.rx.RxUtils;
import net.mobindustry.rxedu.ui.adapter.UserListAdapter;
import net.mobindustry.rxedu.ui.dialog.ProgressDialog;
import net.mobindustry.rxedu.utils.DialogHelper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class RetrofitFragment extends Fragment {

    private static final String TAG = RetrofitFragment.class.getSimpleName();

    private TextView vLoadUsersListButton;
    private ListView vResultListView;

    private ProgressDialog mProgressDialog;
    private ApiManager mApiManager = ApiManager.getInstance();
    private UserListAdapter mUserListAdapter;

    private Subscription mLoadingSubscription;
    private Observable<User> mUserListObservable;
    private Observable<User> cachedObservable;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        vResultListView = (ListView) view.findViewById(R.id.resultListView);
        vLoadUsersListButton = (TextView) view.findViewById(R.id.loadUsersListButton);
        mProgressDialog = ProgressDialog.newInstance(getString(R.string.loadingUsers));
        mUserListObservable = mApiManager.getUsers();
        cachedObservable = Observable.create(new OnSubscribeBoundedCache<>(mUserListObservable, 20));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUserListAdapter = new UserListAdapter(getContext());
        vResultListView.setAdapter(mUserListAdapter);
        vResultListView.setOnItemClickListener((parent, view, position, id) -> openUserInfoFragment(mUserListAdapter.getItem(position)));
//        if (cachedObservable != null) {
//            mLoadingSubscription = cachedObservable.subscribe(mUserSubscriber);
//        }

        RxView.clicks(vLoadUsersListButton)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(click -> {
                    mUserListAdapter.clear();
                    mLoadingSubscription = cachedObservable
                            .doOnNext(DbHelper::addUser)
                            .compose(RxUtils.showProgressDialogAndApplySchedulers(getContext(), mProgressDialog))
                            .subscribe(new Subscriber<User>() {
                                @Override
                                public void onCompleted() {
                                    DialogHelper.showTextDialogSomeTimeAndDismiss(getContext(), getString(R.string.loadingComplete), DialogHelper.TEXT_DIALOG_SHOW_TIME);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, getString(R.string.onError), e);
                                }

                                @Override
                                public void onNext(User user) {
                                    mUserListAdapter.add(user);
                                }
                            });
                });
    }

    private void openUserInfoFragment(User user) {
        UserInfoFragment fragment = UserInfoFragment.newInstance(user);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(UserInfoFragment.TAG)
                .replace(android.R.id.content, fragment, UserInfoFragment.TAG)
                .commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLoadingSubscription != null && !mLoadingSubscription.isUnsubscribed()) {
            mLoadingSubscription.unsubscribe();
        }
    }
}
