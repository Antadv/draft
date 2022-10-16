package com.somelogs.netty.promise;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * Special Future which is writable.
 *
 * 结果容器
 *
 * @author LBG - 2022/10/16
 */
@Slf4j
public class PromiseSuccessTest {

	public static void main(String[] args) throws Exception {
		EventLoop loop = new NioEventLoopGroup().next();
		DefaultPromise<Integer> promise = new DefaultPromise<>(loop);

		new Thread(() -> {
			log.info("worker1 doing...");
			promise.setSuccess(20);
		}).start();

		log.info("main start");

		// get() 同步阻塞
		log.info("main get result from promise {}", promise.get());
	}
}
