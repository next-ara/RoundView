package com.next.view.round;

import android.graphics.Path;
import android.graphics.RectF;

/**
 * ClassName:圆角路径工具类
 *
 * @author Afton
 * @time 2024/1/5
 * @auditor
 */
public class RoundPathTool {

    /**
     * 获取圆角矩形的路径
     *
     * @param rectF  矩形的边框
     * @param radius 圆角的半径
     * @return 圆角矩形的路径
     */
    public static Path getRoundPath(RectF rectF, float radius) {
        return getRoundPath(rectF.left, rectF.top, rectF.width(), rectF.height(), radius);
    }

    /**
     * 获取圆角矩形的路径
     *
     * @param left   矩形的左边
     * @param top    矩形的上边
     * @param width  矩形的宽度
     * @param height 矩形的高度
     * @param radius 圆角的半径
     * @return 圆角矩形的路径
     */
    public static Path getRoundPath(float left, float top, float width, float height, float radius) {
        radius = Math.max(radius, 0);

        // 开始画圆角的位置对比圆角大小的偏移比例
        float radiusOffsetRatio = 128f / 100f;
        // 靠近圆弧两个端点的点的xy坐标比例
        float endPointRatio = 83f / 100f;
        // 左上角第一条曲线第二个点x坐标的比例(其他三个点通过矩阵转换可以使用同样的比例）
        float firstCSecondPXRatio = 67f / 100f;
        // 左上角第一条曲线第二个点Y坐标的比例（其他三个点通过矩阵转换可以使用同样的比例)
        float firstCSecondPYRatio = 4f / 100f;
        // 左上角第一条曲线第三个点x坐标的比例（其他三个点通过矩阵转换可以使用同样的比例)
        float firstCThirdPXRatio = 51f / 100f;
        // 左上角第一条曲线第三个点Y坐标的比例(其他三个点通过矩阵转换可以使用同样的比例)
        float firstCThirdPYRatio = 13f / 100f;
        // 左上角第二条曲线第一个点X坐标的比例（其他三个点通过矩阵转换可以使用同样的比例)
        float secondCFirstPXRatio = 34f / 100f;
        // 左上角第二条曲线第一个点Y坐标的比例(其他三个点通过矩阵转换可以使用同样的比例)
        float secondCFirstPYRatio = 22f / 100f;

        Path mRoundPath = new Path();
        mRoundPath.reset();
        //顶部直线和右上角圆角
        mRoundPath.moveTo((width / 2.0f) + left, top);

        //顶部直线和右上角圆角
        mRoundPath.lineTo(
                coerceAtLeast((width / 2.0f), (width - radius * radiusOffsetRatio) + left),
                top
        );

        mRoundPath.cubicTo(
                left + width - radius * endPointRatio, top,
                left + width - radius * firstCSecondPXRatio,
                top + radius * firstCSecondPYRatio,
                left + width - radius * firstCThirdPXRatio,
                top + radius * firstCThirdPYRatio
        );
        mRoundPath.cubicTo(
                left + width - radius * secondCFirstPXRatio,
                top + radius * secondCFirstPYRatio,
                left + width - radius * secondCFirstPYRatio,
                top + radius * secondCFirstPXRatio,
                left + width - radius * firstCThirdPYRatio,
                top + radius * firstCThirdPXRatio
        );
        mRoundPath.cubicTo(
                left + width - radius * firstCSecondPYRatio,
                top + radius * firstCSecondPXRatio,
                left + width,
                top + radius * endPointRatio,
                left + width,
                top + coerceAtMost((height / 2.0f), radius * radiusOffsetRatio)
        );

        //右边直线和右下角圆角
        mRoundPath.lineTo(left + width, coerceAtLeast((height / 2.0f), height - radius * radiusOffsetRatio) + top);
        mRoundPath.cubicTo(
                left + width,
                top + height - radius * endPointRatio,
                left + width - radius * firstCSecondPYRatio,
                top + height - radius * firstCSecondPXRatio,
                left + width - radius * firstCThirdPYRatio,
                top + height - radius * firstCThirdPXRatio
        );
        mRoundPath.cubicTo(
                left + width - radius * secondCFirstPYRatio,
                top + height - radius * secondCFirstPXRatio,
                left + width - radius * secondCFirstPXRatio,
                top + height - radius * secondCFirstPYRatio,
                left + width - radius * firstCThirdPXRatio,
                top + height - radius * firstCThirdPYRatio
        );

        mRoundPath.cubicTo(
                left + width - radius * firstCSecondPXRatio,
                top + height - radius * firstCSecondPYRatio,
                left + width - radius * endPointRatio,
                top + height,
                left + coerceAtLeast((width / 2.0f), width - radius * radiusOffsetRatio),
                top + height
        );

        //底部直线和左下角圆角
        mRoundPath.lineTo(coerceAtMost((width / 2.0f), radius * radiusOffsetRatio) + left, top + height);
        mRoundPath.cubicTo(
                left + radius * endPointRatio,
                top + height,
                left + radius * firstCSecondPXRatio,
                top + height - radius * firstCSecondPYRatio,
                left + radius * firstCThirdPXRatio,
                top + height - radius * firstCThirdPYRatio
        );
        mRoundPath.cubicTo(
                left + radius * secondCFirstPXRatio,
                top + height - radius * secondCFirstPYRatio,
                left + radius * secondCFirstPYRatio,
                top + height - radius * secondCFirstPXRatio,
                left + radius * firstCThirdPYRatio,
                top + height - radius * firstCThirdPXRatio
        );
        mRoundPath.cubicTo(
                left + radius * firstCSecondPYRatio,
                top + height - radius * firstCSecondPXRatio,
                left,
                top + height - radius * endPointRatio,
                left,
                top + coerceAtLeast((height / 2.0f), height - radius * radiusOffsetRatio)
        );
        //左边直线和左上角圆角
        mRoundPath.lineTo(left, coerceAtMost((height / 2.0f), radius * radiusOffsetRatio) + top);
        mRoundPath.cubicTo(
                left,
                top + radius * endPointRatio,
                left + radius * firstCSecondPYRatio,
                top + radius * firstCSecondPXRatio,
                left + radius * firstCThirdPYRatio,
                top + radius * firstCThirdPXRatio
        );
        mRoundPath.cubicTo(
                left + radius * secondCFirstPYRatio,
                top + radius * secondCFirstPXRatio,
                left + radius * secondCFirstPXRatio,
                top + radius * secondCFirstPYRatio,
                left + radius * firstCThirdPXRatio,
                top + radius * firstCThirdPYRatio
        );

        mRoundPath.cubicTo(
                left + radius * firstCSecondPXRatio,
                top + radius * firstCSecondPYRatio,
                left + radius * endPointRatio,
                top,
                left + coerceAtMost((width / 2.0f), radius * radiusOffsetRatio),
                top
        );

        mRoundPath.close();
        return mRoundPath;
    }

    /**
     * 确保value不超过maximumValue
     *
     * @param value
     * @param maximumValue
     * @return
     */
    private static float coerceAtMost(float value, float maximumValue) {
        if (value > maximumValue) {
            return maximumValue;
        }

        return value;
    }

    /**
     * 确保value不小于minimumValue
     *
     * @param value
     * @param minimumValue
     * @return
     */
    private static float coerceAtLeast(float value, float minimumValue) {
        if (value < minimumValue) {
            return minimumValue;
        }

        return value;
    }
}