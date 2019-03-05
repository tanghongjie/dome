package tanghongjie.myapplication.interfaces;

/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: activity切换时的动画类型枚举常量
 * 修改时间:
 * 修改描述:
 */
public interface TransitionMode {
    /**
     * 从左到右的动画
     */
    int LEFT_RIGHT=0x01;
    /**
     * 从下到上的动画
     */
    int TOP_BOTTOM=0x02;
    /**
     * 渐变动画
     */
    int FADE=0x03;
}