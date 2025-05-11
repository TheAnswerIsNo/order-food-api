package com.wait.app.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 天
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    //Runtime.getRuntime().availableProcessors() * 2 + 有效磁盘数;
    @Value("${async.executor.thread.core_pool_size}")
    private int corePoolSize;

    @Value("${async.executor.thread.max_pool_size}")
//    最大数根据实际业务设置 IO密集型为2n或2n+1 CPU密集型为n或n+1
//    (每秒并发数-corePoolSize大小) / 10
//    （最大任务数-队列容量）/每个线程每秒处理能力 = 最大线程数
    private int maxPoolSize;

    //queueCapacity = (coreSizePool/taskcost)*responsetime
    @Value("${async.executor.thread.queue_capacity}")
    private int queueCapacity;
 
    @Bean(name = "executor")
    public Executor executor() {
        //在这里修改
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("thread-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //AbortPolicy：默认策略，抛出未检查的 RejectedExecutionException。
        //CallerRunsPolicy：调用者运行策略，当任务被拒绝时，由调用线程自己执行任务。
        //DiscardOldestPolicy：丢弃最旧策略，丢弃队列中等待最久的任务，然后尝试重新提交当前任务。
        //DiscardPolicy：丢弃策略，直接丢弃当前任务。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();
        return executor;
    }
}