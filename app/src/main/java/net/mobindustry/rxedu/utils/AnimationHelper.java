package net.mobindustry.rxedu.utils;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.TextView;

public class AnimationHelper {

    private static final float ANIMATION_IN = 1f;
    private static final float ANIMATION_OUT = -1f;
    private static final int ANIMATION_START_DELAY = 100;
    private static final int ANIMATION_COUNT_DURATION = 800;
    private static final int ANIMATION_TAP_DURATION = 400;

    public static void showTapText(TextView view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(ANIMATION_IN);
        ViewCompat.animate(view)
                .alphaBy(ANIMATION_OUT)
                .setDuration(ANIMATION_TAP_DURATION);
    }

    public static void showTapCount(TextView view, int size) {
        view.setText(String.valueOf(size));
        view.setVisibility(View.VISIBLE);
        view.setScaleX(ANIMATION_IN);
        view.setScaleY(ANIMATION_IN);
        ViewCompat.animate(view)
                .scaleXBy(ANIMATION_OUT)
                .scaleYBy(ANIMATION_OUT)
                .setDuration(ANIMATION_COUNT_DURATION)
                .setStartDelay(ANIMATION_START_DELAY);
    }
}
