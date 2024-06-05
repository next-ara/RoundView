package com.next.view.round;

import android.graphics.Outline;
import android.graphics.Path;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

/**
 * ClassName:圆角轮廓裁剪类
 *
 * @author Afton
 * @time 2024/1/5
 * @auditor
 */
public class RoundOutlineProvider extends ViewOutlineProvider {

    //圆角半径
    private float radius;

    public RoundOutlineProvider(float radius, boolean isOffset) {
        //设置圆角半径
        this.setRadius(radius, isOffset);
    }

    @Override
    public void getOutline(View view, Outline outline) {
        //验证圆角半径
        float radius = this.verifyRadius(view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Path path = RoundPathTool.getRoundPath(0, 0, view.getWidth(), view.getHeight(), radius);
            outline.setPath(path);
        } else {
            outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), radius);
        }
    }

    /**
     * 设置圆角半径
     */
    public void setRadius(float radius, boolean isOffset) {
        this.radius = isOffset ? radius + RoundConfig.getInstance().getRoundOffset() : radius;
    }

    /**
     * 验证圆角半径
     */
    private float verifyRadius(View view) {
        float radius = this.radius;

        float halfWidth = view.getWidth() / 2.0f;
        float halfHeight = view.getHeight() / 2.0f;

        if (radius > halfWidth) {
            radius = halfWidth;
        }

        if (radius > halfHeight) {
            radius = halfHeight;
        }

        if (radius < 0) {
            radius = 0;
        }

        return radius;
    }
}