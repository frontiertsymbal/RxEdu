package net.mobindustry.rxedu.ui.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import net.mobindustry.rxedu.R;

public class TextDialogFragment extends DialogFragment {

    public static final String TAG = TextDialogFragment.class.getSimpleName();
    public static final String MESSAGE = "MESSAGE";
    public static final String RES_ID = "RES_ID";

    public interface OnDialogEventInterface {

        void onDismissDialog();
    }

    public static TextDialogFragment newInstance(int stringResourceId) {
        return init(null, stringResourceId);
    }

    public static TextDialogFragment newInstance(String message) {
        return init(message, -1);
    }

    private static TextDialogFragment init(String message, int messageResId) {
        Bundle bundle = new Bundle();
        bundle.putString(MESSAGE, message);
        bundle.putInt(RES_ID, messageResId);
        TextDialogFragment textDialogFragment = new TextDialogFragment();
        textDialogFragment.setCancelable(false);
        textDialogFragment.setArguments(bundle);
        return textDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.text_dialog, null);

        TextView message = (TextView) view.findViewById(R.id.textDialogMessageTextView);

        int messageResId = getArguments().getInt(RES_ID);
        String messageString = getArguments().getString(MESSAGE);

        if (messageResId == -1) {
            message.setText(messageString);
        } else {
            message.setText(getString(messageResId));
        }
        return view;
    }
}