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

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import net.mobindustry.rxedu.R;
import net.mobindustry.rxedu.ui.dialog.ProgressDialog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static android.text.TextUtils.isEmpty;

public class DoubleBindingFragment extends Fragment {

    private EditText number1;
    private EditText number2;
    private TextView result;

    Subscription subscription;
    PublishSubject<Double> resultEmitterSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doublt_binding, container, false);
        number1 = (EditText) view.findViewById(R.id.number1);
        number2 = (EditText) view.findViewById(R.id.number2);
        result = (TextView) view.findViewById(R.id.doubleBindingResult);

        resultEmitterSubject = PublishSubject.create();
        subscription = resultEmitterSubject.asObservable().subscribe(aDouble -> result.setText(String.valueOf(aDouble)));

        RxTextView.textChanges(number1).subscribe(charSequence -> onNumberChanged());
        RxTextView.textChanges(number2).subscribe(charSequence -> onNumberChanged());

        return view;
    }

    public void onNumberChanged() {
        double num1 = 0;
        double num2 = 0;

        if (!isEmpty(number1.getText().toString())) {
            num1 = Double.parseDouble(number1.getText().toString());
        }

        if (!isEmpty(number2.getText().toString())) {
            num2 = Double.parseDouble(number2.getText().toString());
        }

        resultEmitterSubject.onNext(num1 + num2);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
