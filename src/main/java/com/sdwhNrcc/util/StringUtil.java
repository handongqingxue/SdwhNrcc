package com.sdwhNrcc.util;

import java.util.UUID;

public class StringUtil {

	/**
	 * ����36λUUID
	 * @return
	 */
	public static String createUUID() {
		return UUID.randomUUID().toString();
	}
}
