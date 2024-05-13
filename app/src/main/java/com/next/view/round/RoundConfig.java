package com.next.view.round;

/**
 * ClassName:圆角配置类
 *
 * @author Afton
 * @time 2024/5/9
 * @auditor
 */
public class RoundConfig {

    private static RoundConfig instance;

    //全局圆角偏移量
    private float roundOffset = 0f;

    public static RoundConfig getInstance() {
        if (instance == null) {
            instance = new RoundConfig();
        }

        return instance;
    }

    /**
     * 设置全局圆角偏移量
     *
     * @param roundOffset 偏移量
     * @return 圆角配置对象
     */
    public RoundConfig setRoundOffset(float roundOffset) {
        this.roundOffset = roundOffset;
        return this;
    }

    /**
     * 获取全局圆角偏移量
     *
     * @return 偏移量
     */
    public float getRoundOffset() {
        return this.roundOffset;
    }
}