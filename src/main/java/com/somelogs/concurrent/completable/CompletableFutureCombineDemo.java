package com.somelogs.concurrent.completable;

import cn.hutool.json.JSONUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述
 *
 * @author LBG - 2022/12/3
 */
public class CompletableFutureCombineDemo {

	public static void main(String[] args) throws Exception {
		// AND
		//thenCombine();
		//thenAcceptBoth();
		// runAfterBoth();

		// OR
		//applyToEither();
		//acceptEither();
		//runAfterEither();

		//allOf();
		anyOf();
	}

	/****************** AND 两个任务都执行完后执行 ******************/

	/**
	 * 两个任务的结果作为方法入参，且有返回值
	 */
	private static void thenCombine() throws Exception  {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> "one");
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> "two");

		CompletableFuture<String> threeFuture = twoFuture.thenCombine(oneFuture, (two, one) -> {
			System.out.println(one);
			System.out.println(two);
			return "three";
		});

		System.out.println(threeFuture.get());
	}

	/**
	 * 两个任务的结果作为方法入参，无返回值
	 */
	private static void thenAcceptBoth() throws Exception {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> "one");
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> "two");

		CompletableFuture<Void> voidFuture = twoFuture.thenAcceptBoth(oneFuture, (s, w) -> {
			System.out.println(s);
			System.out.println(w);
		});
	}

	/**
	 * 无入参，无返回值
	 */
	private static void runAfterBoth() {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> "one");
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> "two");

		twoFuture.runAfterBoth(oneFuture, () -> System.out.println("three"));
	}

	/****************** OR 两个任务有一个执行完执行 ******************/

	private static String sleepAndReturn(String str) {
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		System.out.println(str);
		return str;
	}

	private static String returnStr(String str) {
		System.out.println(str);
		return str;
	}

	/**
	 * 传参、有返回值
	 */
	private static void applyToEither() throws Exception {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> sleepAndReturn("one"));
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> returnStr("two"));

		CompletableFuture<String> threeFuture = twoFuture.applyToEither(oneFuture, (val) -> {
			System.out.println(val);
			return "three";
		});

		System.out.println(threeFuture.get());
	}

	/**
	 * 传参、无返回值
	 */
	private static void acceptEither() {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> sleepAndReturn("one"));
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> returnStr("two"));

		// void
		CompletableFuture<Void> threeFuture = twoFuture.acceptEither(oneFuture, System.out::println);
	}

	/**
	 * 无传参，无返回值
	 */
	private static void runAfterEither() {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> sleepAndReturn("one"));
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> returnStr("two"));

		// void
		CompletableFuture<Void> threeFuture = twoFuture.runAfterEither(oneFuture, () -> System.out.println("three"));
	}

	/****************** AllOf ******************/

	private static void allOf() throws Exception {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> sleepAndReturn("one"));
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> returnStr("two"));
		CompletableFuture<String> threeFuture = CompletableFuture.supplyAsync(() -> returnStr("three"));

		// void
		CompletableFuture<Void> allFuture = CompletableFuture.allOf(oneFuture, twoFuture, threeFuture);
		allFuture.get();

		/*
		 * Notice that the return type of the CompletableFuture.allOf() is a CompletableFuture<Void>.
		 * The limitation of this method is that it does not return the combined results of all Futures.
		 * Instead, we have to get results from Futures manually.
		 * Fortunately, CompletableFuture.join() method and Java 8 Streams API makes it simple:
		 */
		List<String> resultList = Stream.of(oneFuture, twoFuture, threeFuture).map(CompletableFuture::join).collect(Collectors.toList());
		System.out.println(JSONUtil.toJsonStr(resultList));
	}

	/****************** anyOf ******************/

	private static void anyOf() throws Exception {
		CompletableFuture<String> oneFuture = CompletableFuture.supplyAsync(() -> sleepAndReturn("one"));
		CompletableFuture<String> twoFuture = CompletableFuture.supplyAsync(() -> returnStr("two"));
		CompletableFuture<String> threeFuture = CompletableFuture.supplyAsync(() -> returnStr("three"));

		// object
		CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(oneFuture, twoFuture, threeFuture);
		System.out.println("result is " + anyFuture.get());
	}

	/****************** thenCompose ******************/

	private static void thenCompose() {

	}
}
