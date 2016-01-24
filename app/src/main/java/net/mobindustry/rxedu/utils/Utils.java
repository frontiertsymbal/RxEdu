package net.mobindustry.rxedu.utils;

import android.os.Handler;
import android.os.Looper;

import net.mobindustry.rxedu.ui.adapter.ResultAdapter;

import java.util.ArrayList;

public class Utils {

    public static boolean isCurrentlyOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void showResult(String resultMessage, ArrayList<String> resultList, ResultAdapter resultAdapter) {
        if (Utils.isCurrentlyOnMainThread()) {
            resultList.add(0, resultMessage + " (main thread) ");
            updateAdapter(resultList, resultAdapter);
        } else {
            resultList.add(0, resultMessage + " (NOT main thread) ");

            // You can only do below stuff on main thread.
            new Handler(Looper.getMainLooper()).post(() -> updateAdapter(resultList, resultAdapter));
        }
    }

    private static void updateAdapter(ArrayList<String> resultList, ResultAdapter resultAdapter) {
        resultAdapter.clear();
        resultAdapter.addAll(resultList);
    }
}
