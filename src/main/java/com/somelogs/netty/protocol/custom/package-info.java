/**
 * 协议设计要素：
 * <p>
 * 魔数：用来在第一时间判定是否是无效数据包，例如 java class 文件前 4 个字节就是魔数 0xCAFEBABE
 * 版本号：可以支持协议的升级
 * 序列化算法：消息正文到底采用哪种序列化反序列化方式，可以由此扩展，例如 json、protobuf、hessian、jdk 等
 * 指令类型：是登陆、注册、单聊、群聊...跟业务相关
 * 请求序号：为了双工通信，提供异步能力
 * 正文长度：
 * 消息正文：
 *
 * @author LBG - 2022/10/18
 */
package com.somelogs.netty.protocol.custom;