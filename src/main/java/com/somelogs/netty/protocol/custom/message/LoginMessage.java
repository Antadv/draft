package com.somelogs.netty.protocol.custom.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述
 *
 * @author LBG - 2022/10/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoginMessage extends Message {

	private static final long serialVersionUID = 5240070288418571090L;

	private String username;
	private String password;
	private String nickname;

	@Override
	public int getMessageType() {
		return Message.LOGIN_REQUEST_MESSAGE;
	}
}
