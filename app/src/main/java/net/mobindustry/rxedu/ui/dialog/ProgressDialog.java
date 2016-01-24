package net.mobindustry.rxedu.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.mobindustry.rxedu.R;

public class ProgressDialog extends DialogFragment {

    public static final String TAG = ProgressDialog.class.getSimpleName();

    private static final String ID_STRING_RESOURCE = "ID_STRING_RESOURCE";
    private static final String STRING_RESOURCE = "STRING_RESOURCE";
    private static final String DIALOG_INDETERMINATE_BUNDLE_PARAM = "DIALOG_INDETERMINATE_BUNDLE_PARAM";
    private static final String DIALOG_CANCELABLE_BUNDLE_PARAM = "DIALOG_CANCELABLE_BUNDLE_PARAM";

    public static boolean DIALOG_INDETERMINATE = true;
    public static boolean DIALOG_CANCELABLE = false;

    private TextView vMessage;

    public static ProgressDialog newInstance(int message) {
        return newInstance(message, null, DIALOG_INDETERMINATE, DIALOG_CANCELABLE);
    }

    public static ProgressDialog newInstance(String messageString) {
        return newInstance(-1, messageString, DIALOG_INDETERMINATE, DIALOG_CANCELABLE);
    }

    public static ProgressDialog newInstance(int message, String messageString, boolean indeterminate, boolean cancelable) {
        ProgressDialog ProgressDialog = new ProgressDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(ID_STRING_RESOURCE, message);
        bundle.putString(STRING_RESOURCE, messageString);
        bundle.putBoolean(DIALOG_INDETERMINATE_BUNDLE_PARAM, indeterminate);
        bundle.putBoolean(DIALOG_CANCELABLE_BUNDLE_PARAM, cancelable);
        ProgressDialog.setArguments(bundle);
        return ProgressDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setCancelable(getArguments().getBoolean(DIALOG_CANCELABLE_BUNDLE_PARAM));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.progress_dialog, null);

        ProgressBar v = (ProgressBar) view.findViewById(R.id.loadingProgress);
        v.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.progressBarColor),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        vMessage = (TextView) view.findViewById(R.id.loadingDialogMessage);

        if (getArguments().getInt(ID_STRING_RESOURCE) == -1) {
            vMessage.setText(getArguments().getString(STRING_RESOURCE));
        } else {
            vMessage.setText(getActivity().getString(getArguments().getInt(ID_STRING_RESOURCE)));
        }

        return view;
    }

    public void onProgressUpdate(String updateMessage) {
        vMessage.setText(updateMessage);
    }

    public void onProgressUpdate(int updateMessage) {
        vMessage.setText(updateMessage);
    }
}
