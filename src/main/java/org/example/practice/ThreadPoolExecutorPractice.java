package org.example.practice;

import java.util.concurrent.*;

/**
 * @author Ken
 * @date 2021/04/26
 * @project JavaThreadPractice
 */
public class ThreadPoolExecutorPractice {

    // 直接提交隊列：corePoolSize設置為1，maximumPoolSize設置為2，拒絕策略為AbortPolicy策略，直接拋出異常
    private static ThreadPoolExecutor synchronousQueuePool = new ThreadPoolExecutor(1, 2,
            1000, TimeUnit. MILLISECONDS, new SynchronousQueue<Runnable>(),Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());;

    // 有界任務隊列：corePoolSize設置為1，maximumPoolSize設置為2，隊列容量設置為5，拒絕策略為AbortPolicy策略，直接拋出異常
    private static ThreadPoolExecutor arrayBlockingQueuePool = new ThreadPoolExecutor(1, 2,
            1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(5),Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 無界任務隊列：corePoolSize設置為2，maximumPoolSize設置為2，拒絕策略為AbortPolicy策略，直接拋出異常
    private static ThreadPoolExecutor linkedBlockingQueuePool = new ThreadPoolExecutor(2, 2,
            1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    // 優先任務隊列：corePoolSize設置為1，maximumPoolSize設置為2，拒絕策略為AbortPolicy策略，直接拋出異常
    private static ThreadPoolExecutor priorityBlockingQueuePool = new ThreadPoolExecutor(1, 2,
            1000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(),Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    private static void synchronousQueuePoolTest(){
        for (int i=0;i<3;i++) {
            synchronousQueuePool.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        synchronousQueuePool.shutdown();
    }

    private static void arrayBlockingQueuePoolTest(){
        for (int i=0;i<7;i++) {
            arrayBlockingQueuePool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                return null;
            });
        }
        System.out.println(arrayBlockingQueuePool.getPoolSize());
        System.out.println(arrayBlockingQueuePool.getQueue().size());

        arrayBlockingQueuePool.shutdown();
    }

    private static void linkedBlockingQueuePool(){
        for (int i=0;i<10;i++) {
            linkedBlockingQueuePool.submit(() -> {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(1000);
                return null;
            });
        }

        //此時PoolSize及QueueSize參數皆為0
        System.out.println(arrayBlockingQueuePool.getPoolSize());
        System.out.println(arrayBlockingQueuePool.getQueue().size());

        linkedBlockingQueuePool.shutdown();
    }

    private static void priorityBlockingQueuePool(){
        for (int i=0;i<20;i++) {
            priorityBlockingQueuePool.execute(new PriorityThreadTask(i));
        }

        //此時PoolSize及QueueSize參數皆為0
        System.out.println(arrayBlockingQueuePool.getPoolSize());
        System.out.println(arrayBlockingQueuePool.getQueue().size());

        priorityBlockingQueuePool.shutdown();
    }

    public static class PriorityThreadTask implements Runnable,Comparable<PriorityThreadTask> {

        private int priority;

        public int getPriority() {
            return priority;
        }

        public void setPriority( int priority) {
            this .priority = priority;
        }

        public PriorityThreadTask() {

        }

        public PriorityThreadTask(int priority) {
            this.priority = priority;
        }

        @Override
        //當前對象和其他對像做比較，當前優先級大就返回-1，優先級小就返回1,值越小優先級越高
        public int compareTo(PriorityThreadTask o) {
            return  this.priority > o.priority ? -1 : 1;
        }

        @Override
        public void run() {
            try {
                //讓線程阻塞，使後續任務進入緩存隊列
                Thread.sleep(1000 );
                System.out.println( "priority:"+ this .priority+",ThreadName:"+ Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){

//        synchronousQueuePoolTest();

//        arrayBlockingQueuePoolTest();

//        linkedBlockingQueuePool();

//        priorityBlockingQueuePool();

    }

}
