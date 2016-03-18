package net.mobindustry.rxedu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import net.mobindustry.rxedu.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.subjects.PublishSubject;

import static android.text.TextUtils.isEmpty;

public class DoubleBindingFragment extends Fragment {

    @Bind(R.id.number1)
    EditText number1;
    @Bind(R.id.number2)
    EditText number2;
    @Bind(R.id.doubleBindingResult)
    TextView result;

    private Subscription subscription;
    private PublishSubject<Double> resultEmitterSubject;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doublt_binding, container, false);
        ButterKnife.bind(this, view);

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
