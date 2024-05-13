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
    private float radius = 0;

    public RoundOutlineProvider(float radius) {
        this.radius = radius + RoundConfig.getInstance().getRoundOffset();
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