package com.somelogs.netty.future;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述
 *
 * @author LBG - 2022/10/16
 */
@Slf4j
public class FutureTaskTest {

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new DefaultEventLoopGroup();
		EventLoop next = group.next();
		Future<Integer> future = next.submit(() -> {
			log.info("doing start...");
			// sleep 1s, getNow() 返回 null
			Thread.sleep(1000L);
			return 20;
		});

		// 1. add listener
		//future.addListener(future1 -> log.info("get result {}", future1.getNow()));

		// 2. 直接 get()
		//log.info("get() result is {}", future.get());

		// 3. getNow(), 让任务 sleep 1s
		log.info("getNow() result is {}", future.getNow());
	}
}
