package com.mcoding.base.common.util.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

/**
 * @author mazehong
 * @date 2020/3/26
 */
public class DateTimeUtils {

	/**
	 * Date转LocalDateTime
	 *
	 * @param date
	 * @return
	 */
	public static LocalDateTime toLocalDateTime(Date date) {
		Optional.ofNullable(date).orElseThrow(() -> new IllegalArgumentException("Date转LocalDateTime异常"));
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).withNano(0);
	}

	/**
	 * LocalDateTime转Date
	 *
	 * @param localDateTime
	 * @return
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		Optional.ofNullable(localDateTime).orElseThrow(() -> new IllegalArgumentException("LocalDateTime转Date异常"));
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
