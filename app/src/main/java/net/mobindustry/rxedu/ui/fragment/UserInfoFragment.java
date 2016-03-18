package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.model.User;

public class UserInfoFragment extends Fragment {

    public static final String TAG = UserInfoFragment.class.getSimpleName();
    private static final String USER_ARG = "USER_ARG";
    private User user;

    public static UserInfoFragment newInstance(User user) {
        UserInfoFragment userInfoFragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_ARG, user);
        userInfoFragment.setArguments(args);
        return userInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        user = (User) getArguments().getSerializable(USER_ARG);
        if (user != null) {
            Log.e(TAG, "onActivityCreated: " + user.toString());
        }
    }
}
