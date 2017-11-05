package io.bratexsoft.playgroundfitsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.DecelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    LineView firsLineView;
    LineView secondLineView;
    LineView thirdLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firsLineView = (LineView) findViewById(R.id.first_level);
        secondLineView = (LineView) findViewById(R.id.second_level);
        thirdLineView = (LineView) findViewById(R.id.third_level);

        LineAnimation animation = new LineAnimation(firsLineView);
        animation.setDuration(800);
        animation.setInterpolator(new DecelerateInterpolator());
        firsLineView.startAnimation(animation);

        LineAnimation animationSecond = new LineAnimation(secondLineView);
        animationSecond.setDuration(800);
        animationSecond.setInterpolator(new DecelerateInterpolator());
        secondLineView.startAnimation(animationSecond);

        LineAnimation animationThrid = new LineAnimation(thirdLineView);
        animationThrid.setDuration(800);
        animationThrid.setInterpolator(new DecelerateInterpolator());
        thirdLineView.startAnimation(animationThrid);

    }
}
