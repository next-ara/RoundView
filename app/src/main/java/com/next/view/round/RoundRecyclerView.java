package com.next.view.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ClassName:圆角列表控件类
 *
 * @author Afton
 * @time 2024/1/5
 * @auditor
 */
public class RoundRecyclerView extends RecyclerView {

    //圆角半径
    private float radius = 0;

    //是否开启圆角半径偏移量
    private boolean isOffset = true;

    public RoundRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RoundRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化
        this.init(context, attrs);
    }

    public RoundRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        this.radius = ta.getDimension(R.styleable.RoundView_radius, 0);
        this.isOffset = ta.getBoolean(R.styleable.RoundView_isOffset, true);
        ta.recycle();

        //更新圆角半径
        this.updateRadius();
    }

    /**
     * 设置圆角半径
     *
     * @param radius 圆角半径
     */
    public void setRadius(float radius) {
        this.radius = radius;
        //更新圆角半径
        this.updateRadius();
    }

    /**
     * 设置是否开启圆角半径偏移量
     *
     * @param isOffset 是否开启
     */
    public void setOffset(boolean isOffset) {
        this.isOffset = isOffset;
        //更新圆角半径
        this.updateRadius();
    }

    /**
     * 更新圆角半径
     */
    private void updateRadius() {
        RoundOutlineProvider roundOutlineProvider = new RoundOutlineProvider(this.radius, this.isOffset);
        this.setOutlineProvider(roundOutlineProvider);
        this.setClipToOutline(true);
    }

    public float getRadius() {
        return radius;
    }

    public boolean isOffset() {
        return isOffset;
    }
}