package com.next.view.round;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * ClassName:阻尼反弹嵌套滚动控件类
 *
 * @author Afton
 * @time 2024/5/20
 * @auditor
 */
public class DampingReboundNestedScrollView extends NestedScrollView {

    //圆角半径
    private float radius = 0;

    //y方向上当前触摸点的前一次记录位置
    private int previousY = 0;

    //y方向上的触摸点的起始记录位置
    private int startY = 0;

    //y方向上的触摸点的起始记录位置
    private int currentY = 0;

    //y方向上两次移动间移动的相对距离
    private int deltaY = 0;

    //第一个子视图
    private View childView;

    //用于记录childView的初始位置
    private Rect topRect = new Rect();

    //水平移动搞定距离
    private float moveHeight;

    //列表控件对象的控件id
    private int recyclerViewId;

    //列表控件对象
    private RoundRecyclerView recyclerView;

    public DampingReboundNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public DampingReboundNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //初始化
        this.init(context, attrs);
    }

    public DampingReboundNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置填充视图
        this.setFillViewport(true);
        //初始化
        this.init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (this.recyclerView != null) {
            this.recyclerView.setMaxHeight(height);
        } else {
            this.findRecyclerView(this);
            if (this.recyclerView != null) {
                this.recyclerView.setMaxHeight(height);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (this.recyclerView != null) {
            View child = getChildAt(0);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int topHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin - getMeasuredHeight();
            int scrollY = getScrollY();
            boolean topIsShow = scrollY >= 0 && scrollY < topHeight;
            if (topIsShow) {
                int remainScrollY = topHeight - scrollY;
                int selfScrollY = Math.min(remainScrollY, dy);
                scrollBy(0, selfScrollY);
                consumed[1] = selfScrollY;
            } else {
                super.onNestedPreScroll(target, dx, dy, consumed, type);
            }
        } else {
            super.onNestedPreScroll(target, dx, dy, consumed, type);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.getChildCount() > 0) {
            this.childView = this.getChildAt(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.childView == null) {
            return super.dispatchTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.startY = (int) ev.getY();
                this.previousY = this.startY;

                //记录childView的初始位置
                this.topRect.set(this.childView.getLeft(), this.childView.getTop(), this.childView.getRight(), this.childView.getBottom());

                this.moveHeight = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                this.currentY = (int) ev.getY();
                this.deltaY = this.currentY - this.previousY;
                this.previousY = this.currentY;

                //判定是否在顶部或者滑到了底部
                if ((!this.childView.canScrollVertically(-1) && (this.currentY - this.startY) > 0) || (!this.childView.canScrollVertically(1) && (this.currentY - this.startY) < 0)) {
                    float distance = this.currentY - this.startY;
                    if (distance < 0) {
                        distance *= -1;
                    }

                    //阻尼值
                    float damping = 0.3f;

                    float height = this.getHeight();
                    if (height != 0) {
                        if (distance > height) {
                            damping = 0;
                        } else {
                            damping = (height - distance) / height;
                        }
                    }
                    if (this.currentY - this.startY < 0) {
                        damping = 1 - damping;
                    }

                    damping *= 0.3;
                    damping += 0.3;

                    this.moveHeight = this.moveHeight + (this.deltaY * damping);

                    this.childView.layout(this.topRect.left, (int) (this.topRect.top + this.moveHeight), this.topRect.right, (int) (this.topRect.bottom + this.moveHeight));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!this.topRect.isEmpty()) {
                    //开始回移动画
                    this.upDownMoveAnimation();
                    //子控件回到初始位置
                    this.childView.layout(this.topRect.left, this.topRect.top, this.topRect.right, this.topRect.bottom);
                }

                this.startY = 0;
                this.currentY = 0;
                this.topRect.setEmpty();
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始回移动画
     */
    private void upDownMoveAnimation() {
        TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, this.childView.getTop(), this.topRect.top);
        animation.setDuration(600);
        animation.setFillAfter(true);
        //设置阻尼动画效果
        animation.setInterpolator(input -> 1 - (1 - input) * (1 - input) * (1 - input) * (1 - input) * (1 - input));
        this.childView.setAnimation(animation);
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
        this.recyclerViewId = ta.getResourceId(R.styleable.RoundView_recyclerViewId, 0);
        ta.recycle();

        //设置圆角半径
        this.setRadius(radius);
    }

    /**
     * 递归查找列表控件
     *
     * @param view 父控件
     */
    private void findRecyclerView(View view) {
        if (view instanceof ViewGroup viewGroup && !(view instanceof RecyclerView)) {
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = viewGroup.getChildAt(i);
                this.findRecyclerView(childView);
            }
        } else {
            if (view.getId() == this.recyclerViewId && view instanceof RoundRecyclerView) {
                this.recyclerView = (RoundRecyclerView) view;
            }
        }
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