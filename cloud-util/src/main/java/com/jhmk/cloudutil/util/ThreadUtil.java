package com.jhmk.cloudutil.util;


import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.concurrent.*;

public class ThreadUtil {
    public static ThreadPool instance;
    private ThreadPoolExecutor longExecutor;
    private ThreadPoolExecutor shortExecutor;

    public static ThreadPool getInstance() {
        if (instance == null) {
            synchronized (ThreadUtil.class) {
                if (instance == null) {
//                    int cpuNum = Runtime.getRuntime().availableProcessors();//获取处理器储量
                    int cpuNum = 4;//获取处理器储量
                    int threadNum = cpuNum * 2 + 1;//根据cpu数量，计算出合理的线程并发数
                    instance = new ThreadPool(threadNum - 1, threadNum, Integer.MAX_VALUE);
                }
            }
        }
        return instance;
    }


    public static class ThreadPool {
        private ThreadPoolExecutor mExecutor;
        private int corePoolSize;
        private int maximunPoolSize;
        private long keepAliveTime;

        private ThreadPool(int corePoolSize, int maximunPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.keepAliveTime = keepAliveTime;
            this.maximunPoolSize = maximunPoolSize;
        }

        public void execute(Runnable runnable) {
            if (runnable == null) {
                return;
            }
            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(corePoolSize, maximunPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(Integer.MAX_VALUE),
                        new ThreadPoolExecutor.AbortPolicy() {
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                                super.rejectedExecution(r, e);
                            }
                        });
            }
            mExecutor.execute(runnable);
        }


        public Future<T> execute(Callable<T> callable) {
            if (callable == null) {
                return null;
            }
            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(corePoolSize, maximunPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(Integer.MAX_VALUE),
                        new ThreadPoolExecutor.AbortPolicy() {
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                                super.rejectedExecution(r, e);
                            }
                        });
            }
            Future submit = mExecutor.submit(callable);
            return submit;
        }


        public List<Future<T>> invokeAll(List<Callable<T>> callableList) {
            if (callableList == null) {
                return null;
            }
            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(corePoolSize, maximunPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                        new LinkedBlockingDeque<>(Integer.MAX_VALUE),
                        new ThreadPoolExecutor.AbortPolicy() {
                            @Override
                            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                                super.rejectedExecution(r, e);
                            }
                        });
            }
            List<Future<T>> futures = null;
            try {
                futures = mExecutor.invokeAll(callableList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return futures;
        }

        public void cancal(Runnable runnable) {
            if (mExecutor != null) {
                mExecutor.getQueue().remove(runnable);
            }
        }
    }


//    public static void main(String[] args) {
//
//        ThreadPool instance = ThreadUtil.getInstance();
//        for (int i = 0; i < 100000; i++) {
//            instance.execute(() -> {
//                System.out.print(1);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        while (true) {
//            System.out.println();
//
//            int queueSize = instance.mExecutor.getQueue().size();
//            System.out.println("当前排队线程数：" + queueSize);
//
//            int activeCount = instance.mExecutor.getActiveCount();
//            System.out.println("当前活动线程数：" + activeCount);
//            int corePoolSize = instance.mExecutor.getCorePoolSize();
//            System.out.println("当前核心线程数：" + corePoolSize);
//            int poolSize = instance.mExecutor.getPoolSize();
//            System.out.println("当前所有线程数：" + poolSize);
//
//
//            long completedTaskCount = instance.mExecutor.getCompletedTaskCount();
//            System.out.println("执行完成线程数：" + completedTaskCount);
//
//            long taskCount = instance.mExecutor.getTaskCount();
//            System.out.println("总线程数：" + taskCount);
//
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
