package net.mobindustry.rxedu.utils;

import android.content.Context;
import net.mobindustry.rxedu.ui.dialog.ProgressDialog;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> showProgressDialogAndApplySchedulers (Context context, ProgressDialog dialog) {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> DialogHelper.showProgressDialog(context, dialog))
                .doOnCompleted(() -> DialogHelper.dismissProgressDialog(context));
    }

}
