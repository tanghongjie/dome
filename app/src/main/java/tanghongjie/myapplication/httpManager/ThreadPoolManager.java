package tanghongjie.myapplication.httpManager;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import tanghongjie.myapplication.common.utils.SystemUtils;
import tanghongjie.myapplication.constast.Constant;

/**
 * 创建时间: 2016/08/02 21:45
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: 网络请求专用的线程池管理者
 * 修改时间:
 * 修改描述:
 */
public class ThreadPoolManager {
    private static ThreadPoolProxy threadPoolProxy;
    private ThreadPoolManager() {}

    //获取线程池的代理对象
    public static ThreadPoolProxy getProxy(){
        if (threadPoolProxy == null) {
            synchronized (ThreadPoolManager.class){
                if (threadPoolProxy == null){
                    int cupCount = SystemUtils.getCpuCount()+1;
                    //线程数量
                    int maxThreadCount = Integer.MAX_VALUE;
                    threadPoolProxy = new ThreadPoolProxy(cupCount, maxThreadCount, 20);
                }
            }
        }
        return threadPoolProxy;
    }

    /**
     * 线程池的代理类
     */
    public static class ThreadPoolProxy {
        //线性池的执行者, jdk api
        private ThreadPoolExecutor threadPoolExecutor;
        //核心线程数
        private int corePoolSize;
        //最大线程数量
        private int maximumPoolSize;
        //空闲线程的最大存活时间
        private long keepAliveTime;

        /**
         * @param coreThreadCount 线程池的核心数量
         * @param maxThreadCount 线程池的最大数量
         * @param time 空闲线程的存活时间
         */
        public ThreadPoolProxy(int coreThreadCount, int maxThreadCount, long time) {
            this.corePoolSize = coreThreadCount;
            this.maximumPoolSize = maxThreadCount;
            this.keepAliveTime = time;
        }

        //执行任务
        public void execute(Runnable r) {
            if (r != null) {
                //创建操作线程池的对象
                if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                    synchronized (ThreadPoolProxy.class){
                        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
                            threadPoolExecutor = new ThreadPoolExecutor(
                                    corePoolSize,//线程池的核心数量
                                    maximumPoolSize, //线程池的最大数量
                                    keepAliveTime, //空闲线程的存活时间
                                    TimeUnit.SECONDS, //时间单元
                                    new SynchronousQueue<Runnable>(), //线程队列
                                    Executors.defaultThreadFactory());//创建线程的线程工厂
                        }
                    }
                }

                if (Constant.IS_DEBUG&&false){
                    BlockingQueue<Runnable> queueList=threadPoolExecutor.getQueue();
       /*             LogUtils.e("ThreadPoolManger线程池" +
                            "\n---最大线程数量="+ threadPoolExecutor.getMaximumPoolSize()+
                            "\n---核心线程数量="+ threadPoolExecutor.getCorePoolSize()+
                            "\n---getPoolSize(当前数量)="+ threadPoolExecutor.getPoolSize()+
                            "\n---线程池曾经创建过的最大线程数量="+ threadPoolExecutor.getLargestPoolSize()+
                            "\n---线程池在运行过程中已完成的任务数量="+ threadPoolExecutor.getCompletedTaskCount()+
                            "\n---线程池需要执行的任务数量="+ threadPoolExecutor.getTaskCount()+
                            "\n---getQueue().size(队列线程数量)="+(queueList!=null?queueList.size():null) +
                            "\n---getActiveCount(线程池活着的线程数量)="+ threadPoolExecutor.getActiveCount());*/
                }

                //执行任务
                threadPoolExecutor.execute(r);
            }
        }

        /**
         * 取消线程队列中的任务的方法
         * @param runnable
         */
        public void cancelRunnable(Runnable runnable) {
            if (runnable != null) {
                if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                    //获取线程池中排队的任务队列,然后移除对应的任务
                    BlockingQueue<Runnable> listRunnable= threadPoolExecutor.getQueue();
                    if (listRunnable!=null&&listRunnable.size()>0){
                        listRunnable.remove(runnable);
                    }
                }
            }
        }
    }

    public static void destroyPool(){
        try{
            if (threadPoolProxy!=null && threadPoolProxy.threadPoolExecutor != null
                    && !threadPoolProxy.threadPoolExecutor.isShutdown()){
                threadPoolProxy.threadPoolExecutor.shutdownNow();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
