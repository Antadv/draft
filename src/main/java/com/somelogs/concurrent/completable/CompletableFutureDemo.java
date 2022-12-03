package com.somelogs.concurrent.completable;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.CompletableFuture;

/**
 * 总结：
 * run：没参数传递，没返回值
 * accept：有参数传递，没返回值
 * apply：有参数传递，有返回值
 *
 * @author LBG - 2022/12/3
 */
public class CompletableFutureDemo {

	public static void main(String[] args) throws Exception {
		//runAsync();
		//supplyAsync();

		//thenRun();

		//thenAccept();

		//thenApply();

		//exceptionally();

		//whenComplete();

		 handle();

		Thread.sleep(1000L);
	}

	/****************** 创建异步任务 ******************/

	/**
	 * 创建一个无返回值的异步任务
	 */
	private static void runAsync() {
		CompletableFuture<Void> future = CompletableFuture
				.runAsync(() -> System.out.println("runAsync demo"));
	}

	/**
	 * 创建一个有返回值的异步任务
	 */
	private static void supplyAsync() throws Exception {
		CompletableFuture<String> supplyAsync = CompletableFuture
				.supplyAsync(() -> "has return value");
		System.out.println(supplyAsync.get());
	}

	/****************** 异步任务完成后回调（没有参数传递，没有返回值） ******************/

	/**
	 * 两个任务之间没有参数传递，且没有返回值
	 */
	private static void thenRun() {
		CompletableFuture<Void> voidFuture =
				CompletableFuture.supplyAsync(() -> "return value is thenRun")
						.thenRun(() -> System.out.println("then run"));
	}

	/**
	 * 和 thenRun 的区别就是 thenRunAsync 可以传入自定义线程池
	 */
	private static void thenRunAsync() {
		CompletableFuture<Void> voidFuture =
				CompletableFuture.supplyAsync(() -> "return value is thenRunAsync")
						.thenRunAsync(() -> System.out.println("thenRunAsync"));
	}

	/****************** 异步任务完成后回调（有参数传递，没有返回值） ******************/

	/**
	 * 两个任务之间有参数传递，没有返回值
	 */
	private static void thenAccept() {
		// thenAccept thenAcceptAsync 区别：后者可以传入线程池
		CompletableFuture<Void> voidFuture =
				CompletableFuture.supplyAsync(() -> "thenAccept value")
						.thenAccept((value) -> System.out.println("previous value is " + value));

		//ExecutorService pool = Executors.newFixedThreadPool(1);
		//CompletableFuture<Void> voidFuture =
		//		CompletableFuture.supplyAsync(() -> "thenAccept value")
		//				.thenAcceptAsync((value) -> System.out.println("previous value is " + value), pool);
	}

	/****************** 异步任务完成后回调（有参数传递，有返回值） ******************/

	/*
	 * 两个任务之间有参数传递，回调有返回值
	 */
	private static void thenApply() throws Exception {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "one").thenApply((value) -> value + " two");
		System.out.println(future.get());

		//CompletableFuture<String> future = CompletableFuture
		//		.supplyAsync(() -> "one")
		//		.thenApplyAsync((value) -> value + " two", Executors.newFixedThreadPool(1));
		//System.out.println(future.get());
	}

	/****************** 任务异常 ******************/

	private static void exceptionally() throws Exception {
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
			int num = RandomUtils.nextInt(1, 10);
			if (num < 6) {
				throw new RuntimeException("exceptionally num " + num);
			}
			return num;
		});

		future.exceptionally((e) -> {
			e.printStackTrace();
			return null;
		});

		System.out.println(future.get());
	}

	/****************** 某个任务执行完成后，执行的回调方法，无返回值 ******************/

	private static void whenComplete() throws Exception {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "task value")
				.whenComplete((val, e) -> System.out.println("task complete " + val));
		System.out.println(future.get()); // 返回的是任务的返回值，不是 whenComplete
	}

	/****************** 某个任务执行完成后，执行的回调方法，返回值是任务的返回值 ******************/

	/**
	 * 任务执行完的回调，有自己的返回值
	 */
	private static void handle() throws Exception {
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "handle value")
				.handle((val, throwable) -> val + " handle");
		System.out.println(future.get());
	}
}
