package com.next.view.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ClassName:圆角横向滑动布局类
 *
 * @author Afton
 * @time 2024/1/5
 * @auditor
 */
public class RoundHorizontalScrollView extends HorizontalScrollView {

    //圆角半径
    private float radius = 0;

    public RoundHorizontalScrollView(@NonNull Context context) {
        super(context);
    }

    public RoundHorizontalScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化
        this.init(context, attrs);
    }

    public RoundHorizontalScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        this.init(context, attrs);
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @param attrs   属性
     */
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundView);
        float radius = ta.getDimension(R.styleable.RoundView_radius, 0);
        ta.recycle();

        //设置圆角半径
        this.setRadius(radius);
    }

    /**
     * 设置圆角半径
     *
     * @param radius 圆角半径
     */
    public void setRadius(float radius) {
        if (this.radius == radius) {
            return;
        }

        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(radius);
        this.setOutlineProvider(roundOutlineProvider);
        this.setClipToOutline(true);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }
}