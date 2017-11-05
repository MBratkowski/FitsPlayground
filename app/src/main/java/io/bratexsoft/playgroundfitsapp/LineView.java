package io.bratexsoft.playgroundfitsapp;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class LineView extends View {

    private static final float BACKGROUND_LINE_ANGLE = 180;

    private static final int WIDTH_FRONT_LINE = 14;
    private static final int WIDTH_BACK_LINE = 8;

    private Paint paint;

    private int baseColor;
    private int levelColor;
    private int widthFrontLine;
    private int widthBackLine;

    private float iconSize;
    private float currentLevelAngle = 0;
    private float expectedLevelAngle = 0;
    private Drawable icon;

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas, widthBackLine, baseColor, BACKGROUND_LINE_ANGLE);
        drawLine(canvas, widthFrontLine, levelColor, currentLevelAngle);
    }

    public float getExpectedLevelAngle() {
        return expectedLevelAngle;
    }

    public float getCurrentLevelAngleValue() {
        return currentLevelAngle;
    }

    public void setCurrentLevelAngleValue(float currentLevelAngle) {
        this.currentLevelAngle = currentLevelAngle;
    }


    private int convertLevelValueToRadius(int level) {
        int radiusValue = 180;
        int maxLevel = 100;
        int minLevel = 0;
        if (level >= minLevel && level <= maxLevel) {
            return (level * radiusValue) / 100;
        } else {
            throw new IllegalArgumentException("Invalid levelValue of value");
        }
    }

    private void drawLine(Canvas canvas, float width, int color, float levelValue) {
        paint.setStrokeWidth(convertDpToPixel(width, getContext()));
        paint.setColor(color);

        float centerWidth = 2 * 14;
        float centerHeight = canvas.getHeight() / 2;
        float circleDistance = canvas.getWidth() - (centerWidth * 2);

        float padding = convertDpToPixel(8, getContext());
        float horizontalPadding = convertDpToPixel(2, getContext());
        int left = (int) horizontalPadding;
        int top = (int) ((int) centerHeight - padding);
        int bottom = (int) ((int) iconSize + centerHeight - padding);
        int right = (int) ((int) iconSize + horizontalPadding);

        paint.setStyle(Paint.Style.STROKE);
        icon.setBounds(left, top, right, bottom);

        final Path arrowPath = new Path();

        final RectF arrowOval = new RectF();
        arrowOval.set(centerWidth,
                centerHeight - (circleDistance / 2),
                centerWidth + circleDistance,
                centerHeight + (circleDistance / 2));

        arrowPath.addArc(arrowOval, -180, levelValue);

        canvas.drawPath(arrowPath, paint);
        icon.draw(canvas);
        invalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LevelProgressView,
                0, 0);

        levelColor = typedArray.getColor(R.styleable.LevelProgressView_levelColor, 0);
        baseColor = typedArray.getColor(R.styleable.LevelProgressView_baseColor, 0);

        widthBackLine = typedArray.getInteger(R.styleable.LevelProgressView_widthBackLine, WIDTH_BACK_LINE);
        widthFrontLine = typedArray.getInteger(R.styleable.LevelProgressView_widthFrontLine, WIDTH_FRONT_LINE);
        iconSize = typedArray.getDimension(R.styleable.LevelProgressView_iconSize, 0);
        expectedLevelAngle = convertLevelValueToRadius(typedArray.getInteger(R.styleable.LevelProgressView_level, 0));
        icon = typedArray.getDrawable(R.styleable.LevelProgressView_icon);

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setAntiAlias(true);
    }

    private float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
