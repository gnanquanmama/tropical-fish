package com.mcoding.base.common.util.date;

import cn.hutool.core.lang.Assert;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mazehong
 * @date 2020/3/3
 */
@Slf4j
@UtilityClass
public class DateValidator {

	/**
	 * 利用正则表达式检查是否完整匹配
	 *
	 * @param text
	 * @param reg
	 * @return
	 */
	public static final boolean isMatch(String text, String reg) {
		if (StringUtils.isNotEmpty(text) && StringUtils.isNotBlank(reg)) {
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(text);
			return m.matches();
		}
		return false;
	}

	/**
	 * 判断字串是否符合yyyy-MM-dd HH:mm:ss格式
	 *
	 * @param aDateStr
	 * @return
	 */
	public static final boolean isLongDateStr(String aDateStr) {
		try {
			DateUtils.parseLongDateString(aDateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字串是否符合yyyy-MM-dd格式
	 *
	 * @param aDateStr
	 * @return
	 */
	public static final boolean isShortDateStr(String aDateStr) {
		try {
			DateUtils.parseShortDateString(aDateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字串是否符合yyyyMMdd格式
	 *
	 * @param aDateStr
	 * @return
	 */
	public static final boolean isMailDateDtPartStr(String aDateStr) {
		try {
			DateUtils.parseMailDateDtPartString(aDateStr);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字串是否符合指定的日期格式
	 *
	 * @param aDateStr
	 * @param formatter
	 * @return
	 */
	public static final boolean isDateStrMatched(String aDateStr, String formatter) {
		try {
			DateUtils.parser(aDateStr, formatter);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * 检查字串是否符合yyyy-MM-dd格式
	 *
	 * @param aDateStr
	 */
	public static final void checkShortDateStr(String aDateStr) {
		Assert.isTrue(isShortDateStr(aDateStr), "The str-'" + aDateStr + "' must match 'yyyy-MM-dd' format.");
	}

}