package com.somelogs.netty.protocol.custom.message;

/**
 * 描述
 *
 * @author LBG - 2022/10/20
 */
public abstract class Message {

	private int sequenceId;
	private int messageType;

	public abstract int getMessageType();

	public static final int LOGIN_REQUEST_MESSAGE = 0;
}
