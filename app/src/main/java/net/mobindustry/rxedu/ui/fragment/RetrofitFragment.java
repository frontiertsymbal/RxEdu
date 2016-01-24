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
import net.mobindustry.rxedu.model.User;
import net.mobindustry.rxedu.ui.adapter.UserListAdapter;
import net.mobindustry.rxedu.ui.dialog.ProgressDialog;
import net.mobindustry.rxedu.utils.DialogHelper;
import net.mobindustry.rxedu.utils.RxUtils;

import rx.Subscription;

public class RetrofitFragment extends Fragment {

    private static final String TAG = RetrofitFragment.class.getSimpleName();

    private TextView vLoadUsersListButton;
    private ListView vResultListView;

    private ProgressDialog mProgressDialog;
    private ApiManager mApiManager = ApiManager.getInstance();
    private UserListAdapter mUserListAdapter;

    private Subscription mLoadingSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        vResultListView = (ListView) view.findViewById(R.id.resultListView);
        vLoadUsersListButton = (TextView) view.findViewById(R.id.loadUsersListButton);
        mProgressDialog = ProgressDialog.newInstance(getContext().getString(R.string.loadingUsers));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUserListAdapter = new UserListAdapter(getContext());
        vResultListView.setAdapter(mUserListAdapter);
        vResultListView.setOnItemClickListener((parent, view, position, id) -> openUserInfoFragment(mUserListAdapter.getItem(position)));

        mLoadingSubscription = mApiManager.getUsers()
                .compose(RxUtils.showProgressDialogAndApplySchedulers(getContext(), mProgressDialog))
                .subscribe(
                        mUserListAdapter::add,
                        e -> Log.e(TAG, "onError", e),
                        () -> DialogHelper.showTextDialogSomeTimeAndDismiss(getContext(), "Loading complete", DialogHelper.TEXT_DIALOG_SHOW_TIME)
                );

        RxView.clicks(vLoadUsersListButton).subscribe(click -> {
            mUserListAdapter.clear();

        });
    }

    private void openUserInfoFragment(User user) {
        UserInfoFragment fragment = new UserInfoFragment();
        fragment.putUser(user);
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
