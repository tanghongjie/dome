package tanghongjie.myapplication.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;

import static android.text.format.Formatter.formatFileSize;

/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:查看内存相关的工具方法
 * 修改时间:
 * 修改描述:
 */
public class MemoryUtils {

    /**
     * 通过代码查看每个进程可用的最大内存，即heapgrowthlimit值
     * 或：
     $adb shell getprop dalvik.vm.heapgrowthlimit
     *  红米note4都是 256M
     *  华为AL00 192M
     * @param context
     * @return
     */
    public static int getMemoryClass(@NonNull Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = activityManager.getMemoryClass();//64，以m为单位
        return memClass;
    }

    /**
     * 通过代码查看每个进程可用的最大内存，即heapgrowthlimit值
     * 你可以通过在manifest的application标签下添加largeHeap=true的属性来声明一个更大的heap空间
     *
     * large heap并不一定能够获取到更大的heap。在某些有严格限制的机器上，large heap的大小和通常的heap size是一样的。
     * 因此即使你申请了large heap，你还是应该通过执行getMemoryClass()来检查实际获取到的heap大小
     * @param context
     * @return
     */
    public static int getLargeMemoryClass(@NonNull Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int memClass = activityManager.getLargeMemoryClass();//以m为单位
        return memClass;
    }

    /**
     * 获取当前应用已经使用的内存字节数(与as中memory显示的内存一致)
     * 对应as中Monitors的Allocated
     * @return
     */
    public static long getCurrentUseSize(){
        //已经分配的总内存
        long totalBytes= getTotalMemorySize();
        //已经分配的内存中,剩余的内存
        long freeBytes=getFreeMemorySize();
        return totalBytes-freeBytes;
    }

    /**
     * 当前应用已经分配的内存中剩余可用内存
     * 对应as中Monitors的Free
     * @return
     */
    public static long getFreeMemorySize(){
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 当前应用已经分配的总内存
     * 对应as中Monitors的Free+Allocated总和
     * @return
     */
    public static long getTotalMemorySize(){
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * 当前应用能分配的最大内存
     * @return
     */
    public static long getMaxMemorySize(){
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 当前应用剩余最大可用内存
     * 当前应用能分配的最大内存减去已经使用的内存
     * @return
     */
    public static long getMaxFreeMemorySize(){
        return getMaxMemorySize()- getCurrentUseSize();
    }

    /**
     * 获取当前应用的基本内存信息,主要用于显示
     * @param context
     * @return
     */
    public static String getAppMemoryInfo(Context context){
        StringBuffer sb=new StringBuffer();
        sb.append("\n");
        sb.append("可分配总内存:");
        sb.append(formatFileSize(context, MemoryUtils.getMaxMemorySize()));
        sb.append("\n");
        sb.append("\n");
        sb.append("剩余可分配内存:");
        sb.append(formatFileSize(context, MemoryUtils.getMaxFreeMemorySize()));
        sb.append("\n");
        sb.append("\n");
        sb.append("已分配的总内存:");
        sb.append(formatFileSize(context, MemoryUtils.getTotalMemorySize()));
        sb.append("\n");
        sb.append("\n");
        sb.append("已用内存:");
        sb.append(formatFileSize(context, MemoryUtils.getCurrentUseSize()));
        sb.append("\n");
        sb.append("\n");
        sb.append("已分配的内存中剩余内存:");
        sb.append(formatFileSize(context, MemoryUtils.getFreeMemorySize()));
        sb.append("\n");
        return sb.toString();
    }
}
