package com.somelogs.netty.protocol.custom.message;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述
 *
 * @author LBG - 2022/10/20
 */
@Data
public abstract class Message implements Serializable {

	private static final long serialVersionUID = 4526369954883025761L;

	private int sequenceId;
	private int messageType;

	public abstract int getMessageType();

	public static final int LOGIN_REQUEST_MESSAGE = 0;
}
