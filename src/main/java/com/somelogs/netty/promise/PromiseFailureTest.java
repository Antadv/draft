package com.somelogs.netty.promise;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * Special Future which is writable.
 *
 * 结果容器
 *
 * @author LBG - 2022/10/16
 */
@Slf4j
public class PromiseFailureTest {

	public static void main(String[] args) throws Exception {
		EventLoop loop = new NioEventLoopGroup().next();
		DefaultPromise<Integer> promise = new DefaultPromise<>(loop);

		new Thread(() -> {
			log.info("worker1 doing...");
			try {
				int i = 1/0;
			} catch(Exception e) {
				promise.setFailure(e);
			}
		}).start();

		log.info("main start");

		// get() 同步阻塞
		log.info("main get result from promise {}", promise.get());
	}
}
