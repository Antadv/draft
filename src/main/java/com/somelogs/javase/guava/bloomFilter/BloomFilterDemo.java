package com.somelogs.javase.guava.bloomFilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器: 高效, 内存占用小
 *
 * 文章
 * https://developer.aliyun.com/article/743706
 * https://www.baeldung.com/guava-bloom-filter
 * https://www.cnblogs.com/ysocean/p/12594982.html
 * https://blog.csdn.net/helloxiaozhe/article/details/106872357
 *
 * 布隆过滤器原理: 底层用的 Bitmap (位图), Bitmap就是用n个二进制位来记录0-m之间的某个数有没有出现过。
 * 精确判断一个数不存在
 * 判断一个存在, 有一定的误判率(hash 冲突),
 *
 * 所以布隆过滤器中会有参数 k, 表示 hash(使用的是不同的 hash 函数) 的次数来降低误判率
 *
 * 		   // 通过期望元素数量和误判率计算出最佳的bit数量
 *         long numBits = optimalNumOfBits(expectedInsertions, fpp);
 *         // 再通过期望元素数量和bit位数计算出需要hash的次数
 *         int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
 *
 * 有几点需要注意:
 * 1. 布隆过滤器不能删除(也就是不能直接将指定位置改为0), 因为这些 bit 位可能会被其他key共享
 * 2. 如果 expectedInsertions 设置的很小, 后面随着 insert 的元素越来越多, 会导致误判率偏高
 *
 * redis 中可以使用 Redisson 来创建分布式布隆过滤器 RBloomFilter
 *
 * @author LBG - 2022/1/25
 */
public class BloomFilterDemo {

	public static void main(String[] args) {
		int expectedInsertions = 100000;
		double fpp = 0.01d;

		/*
		 * expectedInsertions 预期存放的元素数量
		 * fpp 误判率
		 */
		BloomFilter<Integer> filter = BloomFilter.create(
				Funnels.integerFunnel(),
				expectedInsertions,
				fpp);

		filter.put(23);
		filter.put(100);
		filter.put(324);

		System.out.println(filter.mightContain(23));
		System.out.println(filter.mightContain(100));
		System.out.println(filter.mightContain(555));
	}
}
