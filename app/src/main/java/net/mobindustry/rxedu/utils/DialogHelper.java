package net.mobindustry.rxedu.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import net.mobindustry.rxedu.ui.dialog.ProgressDialog;
import net.mobindustry.rxedu.ui.dialog.TextDialogFragment;

import java.util.concurrent.TimeUnit;

public class DialogHelper {

    public static final int TEXT_DIALOG_SHOW_TIME = 2;

    public static void showDialog(Context context, final String TAG, DialogFragment dialogFragment) {
        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment prev = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        dialogFragment.show(ft, TAG);
    }

    public static void dismissDialog(Context context, final String TAG) {
        Fragment dialog = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(TAG);
        if (dialog != null) {
            FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            ft.remove(dialog);
            ft.commitAllowingStateLoss();
        }
    }

    public static void showTextDialogSomeTimeAndDismiss(Context context, int messageResourceId, int seconds) {
        showTextDialogSomeTimeAndDismiss(context, messageResourceId, null, seconds, null);
    }

    public static void showTextDialogSomeTimeAndDismiss(Context context, String message, int seconds) {
        showTextDialogSomeTimeAndDismiss(context, -1, message, seconds, null);
    }

    public static void showTextDialogSomeTimeAndDismiss(Context context, int messageResourceId, int seconds, final TextDialogFragment.OnDialogEventInterface onDialogEventInterface) {
        showTextDialogSomeTimeAndDismiss(context, messageResourceId, null, seconds, onDialogEventInterface);
    }

    public static void showTextDialogSomeTimeAndDismiss(Context context, String message, int seconds, final TextDialogFragment.OnDialogEventInterface onDialogEventInterface) {
        showTextDialogSomeTimeAndDismiss(context, -1, message, seconds, onDialogEventInterface);
    }

    private static void showTextDialogSomeTimeAndDismiss(final Context context, final int messageResourceId, final String message, final int seconds, final TextDialogFragment.OnDialogEventInterface onDialogEventInterface) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                TextDialogFragment textDialogFragment;
                if (messageResourceId != -1) {
                    textDialogFragment = TextDialogFragment.newInstance(messageResourceId);
                } else {
                    textDialogFragment = TextDialogFragment.newInstance(message);
                }
                DialogHelper.showDialog(context, TextDialogFragment.TAG, textDialogFragment);
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    TimeUnit.SECONDS.sleep(seconds);
                } catch (InterruptedException e) {
                    Log.e(context.getClass().getSimpleName(), "Dialog InterruptedException", e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                DialogHelper.dismissDialog(context, TextDialogFragment.TAG);
                if (onDialogEventInterface != null) {
                    onDialogEventInterface.onDismissDialog();
                }
                super.onPostExecute(aVoid);
            }
        }.execute();
    }

    // TODO: AlexTsymbal: reformat to showDialog
    public static void showProgressDialog(Context context, ProgressDialog loadDataProgressDialog) {
        FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        Fragment prev = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(ProgressDialog.TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        loadDataProgressDialog.show(ft, ProgressDialog.TAG);
    }

    public static void dismissProgressDialog(Context context) {
        Fragment dialog = ((FragmentActivity) context).getSupportFragmentManager().findFragmentByTag(ProgressDialog.TAG);
        if (dialog != null) {
            FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            ft.remove(dialog);
            ft.commitAllowingStateLoss();
        }
    }
}
