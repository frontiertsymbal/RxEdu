package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import net.mobindustry.rxedu.R;

import rx.Observable;
import rx.Observer;
import rx.Subscription;

import static android.text.TextUtils.isEmpty;
import static android.util.Patterns.EMAIL_ADDRESS;

public class ValidationFragment extends Fragment {

    private static final String TAG = ValidationFragment.class.getSimpleName();

    private TextView vSubmitButton;
    private EditText vEmailEditText;
    private EditText vPasswordEditText;
    private EditText vNumberEditText;

    private Observable<CharSequence> mEmailChangeObservable;
    private Observable<CharSequence> mPasswordChangeObservable;
    private Observable<CharSequence> mNumberChangeObservable;

    private Subscription mSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_validation, container, false);
        vSubmitButton = (TextView) view.findViewById(R.id.submitButton);
        vEmailEditText = (EditText) view.findViewById(R.id.emailEditText);
        vPasswordEditText = (EditText) view.findViewById(R.id.passwordEditText);
        vNumberEditText = (EditText) view.findViewById(R.id.numberEditText);

        vSubmitButton.setEnabled(false);
        RxView.clicks(vSubmitButton).subscribe(v -> Toast.makeText(getContext(), R.string.submitSuccess, Toast.LENGTH_LONG).show());

        mEmailChangeObservable = RxTextView.textChanges(vEmailEditText).skip(1);
        mPasswordChangeObservable = RxTextView.textChanges(vPasswordEditText).skip(1);
        mNumberChangeObservable = RxTextView.textChanges(vNumberEditText).skip(1);

        combineLatestEvents();

        return view;
    }

    private void combineLatestEvents() {
        mSubscription = Observable.combineLatest(mEmailChangeObservable, mPasswordChangeObservable, mNumberChangeObservable,
                (newEmail, newPassword, newNumber) -> {
                    boolean emailValid = !isEmpty(newEmail) && EMAIL_ADDRESS.matcher(newEmail).matches();
                    boolean passValid = !isEmpty(newPassword) && newPassword.length() > 8;
                    boolean numValid = !isEmpty(newNumber);

                    if (!emailValid) {
                        vEmailEditText.setError(getContext().getString(R.string.invalidEmail));
                    }

                    if (!passValid) {
                        vPasswordEditText.setError(getContext().getString(R.string.invalidPassword));
                    }

                    if (numValid) {
                        int num = Integer.parseInt(newNumber.toString());
                        numValid = num > 0 && num <= 100;
                    } else {
                        vNumberEditText.setError(getContext().getString(R.string.invalidNumber));
                    }

                    return emailValid && passValid && numValid;
                }).subscribe(vSubmitButton::setEnabled,
                e -> Log.e(TAG, getContext().getString(R.string.onError), e),
                () -> Log.e(TAG, getContext().getString(R.string.onCompleted))
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

}
