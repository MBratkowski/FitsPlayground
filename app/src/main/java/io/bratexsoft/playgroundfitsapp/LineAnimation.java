package io.bratexsoft.playgroundfitsapp;

import android.view.animation.Animation;
import android.view.animation.Transformation;

public class LineAnimation extends Animation {

    private final LineView view;
    private final float newAngle;
    private final float oldAngle;

    public LineAnimation(LineView view) {
        this.view = view;
        this.oldAngle = view.getCurrentLevelAngleValue();
        this.newAngle = view.getExpectedLevelAngle();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        super.applyTransformation(interpolatedTime, transformation);
        float angle = 0 + ((newAngle - oldAngle) * interpolatedTime);

        view.setCurrentLevelAngleValue(angle);
        view.requestLayout();
    }
}
