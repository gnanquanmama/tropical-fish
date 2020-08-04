package com.mcoding.base.common.util.date;

import cn.hutool.core.lang.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author mazehong
 * @date 2020/3/3
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String SHORT_DATE_GBK_FORMAT = "yyyy年MM月dd日";

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分";

    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String LONG_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String MAIL_DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String MAIL_DATE_HHMM_FORMAT = "HH:mm";

    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String SQL_FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String FULL_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";

    public static final String FULL_DATE_COMPACT_FORMAT = "yyyyMMddHHmmssSSS";

    public static final String LDAP_DATE_FORMAT = "yyyyMMddHHmm'Z'";

    public static final String US_LOCALE_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final String MAIL_DATE_DT_PART_FORMAT = "yyyyMMdd";

    public static final String MAIL_TIME_TM_PART_FORMAT = "HHmmss";

    public static final String LONG_DATE_TM_PART_FORMAT = "HH:mm:ss";

    public static final String LONG_DATE_TM_PART_GBK_FORMAT = "HH时mm分ss秒";

    public static final String MAIL_DATA_DTM_PART_FORMAT = "MM月dd日HH:mm";

    public static final String POINT_DATA_DTM_PART_FORMAT = "yyyy.MM.dd";

    public static final String DEFAULT_DATE_FORMAT = US_LOCALE_DATE_FORMAT;

    public static final long NANO_ONE_SECOND = 1000;

    public static final long NANO_ONE_MINUTE = 60 * NANO_ONE_SECOND;

    public static final long NANO_ONE_HOUR = 60 * NANO_ONE_MINUTE;

    public static final long NANO_ONE_DAY = 24 * NANO_ONE_HOUR;

    /**
     * 5分钟
     */
    public static final long FIVE_MINUTE = 5 * NANO_ONE_MINUTE;

    /**
     * 3天
     */
    public static final long THREE_DAY = 3 * NANO_ONE_DAY;

    public static final String MORE_THAN = ">";

    public static final String LESS_THAN = "<";

    public static final String EQUAL = "=";

    public static final String DASH = "-";

    public static final String COLON = ":";

    public static final String BLANK = " ";

    /**
     * 字符串转日期，模糊判断， 超过10的则精确到秒，反之精确到天
     *
     * @param arg
     * @return
     * @throws java.text.ParseException
     */
    public static Date ignoreDate(String arg) throws ParseException {
        SimpleDateFormat ACCURATE_SECONDS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ACCURATE_DAYS = new SimpleDateFormat("yyyy-MM-dd");
        return arg.length() > 10 ? ACCURATE_SECONDS.parse(arg) : ACCURATE_DAYS.parse(arg);
    }

    /**
     * 获取当前日期类型时间
     */
    public static Date getNow() {
        return new Date();
    }

    /**
     * 获取当前时间戳
     */
    public static long getNowTimestamp() {
        return getNow().getTime();
    }

    /**
     * 获取当前日期 yyyyMMdd
     */
    public static String getCurrentDate() {
        return toMailDateDtPartString(getNow());
    }

    /**
     * 获取当期时间HHmmss
     *
     * @return
     */
    public static String getCurrentTime() {
        return toMailTimeTmPartString(getNow());
    }

    /**
     * 获取当期时间MM月dd日HH:mm
     *
     * @return
     */
    public static String getCurrentMmDdHmTime() {
        return toMailDtmPart(getNow());
    }

    /**
     * 获取当前日期和时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return toMailDateString(getNow());
    }

    //============================1.Date2String=====================================

    /**
     * 将一个日期型转换为指定格式字串
     *
     * @param aDate
     * @param formatStr
     * @return
     */
    public static final String toFormatDateString(Date aDate, String formatStr) {
        if (aDate == null) {
            return StringUtils.EMPTY;
        }
        return new SimpleDateFormat(formatStr).format(aDate);

    }

    /**
     * 将一个日期型转换为'yyyy-MM-dd'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toShortDateString(Date aDate) {
        return toFormatDateString(aDate, SHORT_DATE_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyyMMdd'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDateDtPartString(Date aDate) {
        return toFormatDateString(aDate, MAIL_DATE_DT_PART_FORMAT);
    }

    /**
     * 将一个日期型转换为'HHmmss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailTimeTmPartString(Date aDate) {
        return toFormatDateString(aDate, MAIL_TIME_TM_PART_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyyMMddHHmmss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDateString(Date aDate) {
        return toFormatDateString(aDate, MAIL_DATE_FORMAT);
    }

    /**
     *
     */
    /**
     * 将一个日期型转换为MM月dd日HH:mm格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toMailDtmPart(Date aDate) {
        return toFormatDateString(aDate, MAIL_DATA_DTM_PART_FORMAT);
    }

    /**
     *
     */
    /**
     * 将一个日期型转换为yyyy.MM.dd格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toPointDtmPart(Date aDate) {
        return toFormatDateString(aDate, POINT_DATA_DTM_PART_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy-MM-dd HH:mm:ss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toLongDateString(Date aDate) {
        return toFormatDateString(aDate, LONG_DATE_FORMAT);
    }

    /**
     * 将一个日期型转换为'HH:mm:ss'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toLongDateTmPartString(Date aDate) {
        return toFormatDateString(aDate, LONG_DATE_TM_PART_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy年MM月dd日'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toShortDateGBKString(Date aDate) {
        return toFormatDateString(aDate, SHORT_DATE_GBK_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy年MM月dd日 HH时mm分'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toDateGBKString(Date aDate) {
        return toFormatDateString(aDate, DATE_GBK_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy年MM月dd日 HH时mm分ss秒'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toLongDateGBKString(Date aDate) {
        return toFormatDateString(aDate, LONG_DATE_GBK_FORMAT);
    }

    /**
     * 将一个日期型转换为'HH时mm分ss秒'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toLongDateTmPartGBKString(Date aDate) {
        return toFormatDateString(aDate, LONG_DATE_TM_PART_GBK_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy-MM-dd HH:mm:ss:SSS'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toFullDateString(Date aDate) {
        return toFormatDateString(aDate, FULL_DATE_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyy年MM月dd日 HH时mm分ss秒SSS毫秒'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toFullDateGBKString(Date aDate) {
        return toFormatDateString(aDate, FULL_DATE_GBK_FORMAT);
    }

    /**
     * 将一个日期型转换为'yyyyMMddHHmmssSSS'格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toFullDateCompactString(Date aDate) {
        return toFormatDateString(aDate, FULL_DATE_COMPACT_FORMAT);
    }

    /**
     * 将一个日期型转换为LDAP格式字串
     *
     * @param aDate
     * @return
     */
    public static final String toLDAPDateString(Date aDate) {
        return toFormatDateString(aDate, LDAP_DATE_FORMAT);
    }

    //============================2.String2Date=====================================

    /**
     * 将一个符合指定格式的字串解析成日期型
     *
     * @param aDateStr
     * @param formatter
     * @return
     * @throws java.text.ParseException
     */
    public static final Date parser(String aDateStr, String formatter) throws ParseException {
        if (StringUtils.isBlank(aDateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.parse(aDateStr);

    }

    /**
     * 将一个符合指定格式的字串解析成日期型
     *
     * @param aDateStr
     * @param formatter
     * @param lenient false表示需要对字符串进行严格校验，有多余的空格都不行  true表示不进行严格校验，是SimpleDateFormat默认的方式
     * @return
     * @throws java.text.ParseException
     */
    public static final Date parser(String aDateStr, String formatter, boolean lenient) throws ParseException {
        if (StringUtils.isBlank(aDateStr)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        sdf.setLenient(lenient);
        return sdf.parse(aDateStr);

    }

    /**
     * 将一个符合'yyyy-MM-dd HH:mm:ss'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseLongDateString(String aDateStr) throws ParseException {
        return parser(aDateStr, LONG_DATE_FORMAT);

    }

    /**
     * 将一个符合'yyyy-MM-dd HH:mm:ss'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseLongDateDtPartString(String aDateStr) throws ParseException {
        return parser(aDateStr, LONG_DATE_FORMAT);

    }

    /**
     * 将一个符合'yyyy-MM-dd HH:mm:ss'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseLongDateTmPartString(String aDateStr) throws ParseException {
        return parser(aDateStr, LONG_DATE_FORMAT);

    }

    /**
     * 将一个符合'yyyy-MM-dd'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseShortDateString(String aDateStr) throws ParseException {
        return parser(aDateStr, SHORT_DATE_FORMAT);

    }

    /**
     * 将一个符合'yyyyMMddHHmmss'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseMailDateString(String aDateStr) throws ParseException {
        return parser(aDateStr, MAIL_DATE_FORMAT);

    }

    /**
     * 将一个符合'yyyyMMdd'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseMailDateDtPartString(String aDateStr) throws ParseException {
        return parser(aDateStr, MAIL_DATE_DT_PART_FORMAT);
    }

    /**
     * 将一个符合'HHmmss'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseMailDateTmPartString(String aDateStr) throws ParseException {
        return parser(aDateStr, MAIL_TIME_TM_PART_FORMAT);
    }

    /**
     * 将一个符合'yyyy-MM-dd HH:mm:ss:SSS'格式的字串解析成日期型
     *
     * @param aDateStr
     * @return
     */
    public static final Date parseFullDateString(String aDateStr) throws ParseException {
        return parser(aDateStr, FULL_DATE_FORMAT);

    }

    /**
     * 将一个符合'yyyy-MM-dd'、'yyyy-MM-dd HH:mm:ss'或'EEE MMM dd HH:mm:ss zzz
     * yyyy'格式的字串解析成日期型， 如果为blank则返回空，如果不为blank又不符合格式则报错
     *
     * @param aDateStr
     * @return
     */
    public static Date parseDateString(String aDateStr) {
        Date ret = null;
        if (StringUtils.isNotBlank(aDateStr)) {
            try {
                if (DateValidator.isLongDateStr(aDateStr)) {
                    ret = parseLongDateString(aDateStr);
                } else if (DateValidator.isShortDateStr(aDateStr)) {
                    ret = parseShortDateString(aDateStr);
                } else if (DateValidator.isDateStrMatched(aDateStr, DEFAULT_DATE_FORMAT)) {
                    ret = parser(aDateStr, DEFAULT_DATE_FORMAT);
                } else {
                    throw new IllegalArgumentException("date format mismatch");
                }
            } catch (ParseException e) {
                log.warn("parseDateString failed", e);
            }
        }
        return ret;
    }

    //============================3.String2String=====================================

    /**
     * 转换日期格式 yyyy-MM-dd => yyyyMMdd
     *
     * @param dt yyyy-MM-dd
     * @return yyyyMMdd
     */
    public static String transfer2ShortDate(String dt) {
        if (dt == null || dt.length() != 10) {
            return dt;
        }
        Assert.notNull(dt, "格式错误");
        String[] tmp = StringUtils.split(dt, DASH);
        Assert.isTrue(tmp != null && tmp.length == 3, "格式错误");
        return tmp[0].concat(StringUtils.leftPad(tmp[1], 2, "0")).concat(StringUtils.leftPad(tmp[2], 2, "0"));
    }

    /**
     * 转换日期格式 yyyyMMdd HH:mm:ss => yyyy-MM-dd HH:mm:ss
     *
     * @param dt yyyyMMdd
     * @param tm HHmmss
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String transfer2LongDatePart(String dt, String tm) {
        return transfer2LongDateDtPart(dt).concat(BLANK).concat(transfer2LongDateTmPart(tm));
    }

    /**
     * 转换日期格式 yyyyMMdd => yyyy-MM-dd
     *
     * @param dt yyyyMMdd
     * @return yyyy-MM-dd
     */
    public static String transfer2LongDateDtPart(String dt) {
        if (dt == null || dt.length() != 8) {
            return dt;
        }
        Assert.notNull(dt, "格式错误");
        Assert.isTrue(dt.length() == 8, "格式错误");
        return dt.substring(0, 4).concat(DASH).concat(dt.substring(4, 6)).concat(DASH).concat(dt.substring(6));
    }

    /**
     * 转换日期格式 HHmmss => HH:mm:ss
     *
     * @param tm HHmmss
     * @return HH:mm:ss
     */
    public static String transfer2LongDateTmPart(String tm) {
        if (tm == null || tm.length() != 6) {
            return tm;
        }
        Assert.notNull(tm, "格式错误");
        Assert.isTrue(tm.length() == 6, "格式错误");
        return tm.substring(0, 2).concat(COLON).concat(tm.substring(2, 4)).concat(COLON).concat(tm.substring(4));
    }

    /**
     * 转换日期格式 yyyyMMdd => yyyy年MM月dd日
     *
     * @param dt yyyyMMdd
     * @return yyyy年MM月dd日
     */
    public static String transfer2LongDateGbkDtPart(String dt) {
        if (dt == null || dt.length() != 8) {
            return dt;
        }
        Assert.notNull(dt, "格式错误");
        Assert.isTrue(dt.length() == 8, "格式错误");
        return dt.substring(0, 4).concat("年").concat(dt.substring(4, 6)).concat("月").concat(dt.substring(6))
                .concat("日");
    }

    /**
     * 转换日期格式 yyyyMMdd => yyyy/MM/dd
     *
     * @param dt yyyyMMdd
     * @return yyyy年MM月dd日
     */
    public static String transfer2LongDate(String dt) {
        if (dt == null || dt.length() != 8) {
            return dt;
        }
        Assert.notNull(dt, "格式错误");
        Assert.isTrue(dt.length() == 8, "格式错误");
        return dt.substring(0, 4).concat("-").concat(dt.substring(4, 6)).concat("-").concat(dt.substring(6));
    }

    /**
     * 转换日期格式HHmmss => HH时mm分ss秒
     *
     * @param tm HHmmss
     * @return HH时mm分ss秒
     */
    public static String transfer2LongDateGbkTmPart(String tm) {
        if (tm == null || tm.length() != 6) {
            return tm;
        }
        Assert.notNull(tm, "格式错误");
        Assert.isTrue(tm.length() == 6, "格式错误");
        return tm.substring(0, 2).concat("时").concat(tm.substring(2, 4)).concat("分").concat(tm.substring(4))
                .concat("秒");
    }

    //============================4.时间加减=====================================

    /**
     * 为一个日期加上指定年数
     *
     * @param aDate
     * @param amount 年数
     * @return
     */
    public static final Date addYears(Date aDate, int amount) {
        return addTime(aDate, Calendar.YEAR, amount);
    }

    /**
     * 为一个日期加上指定月数
     *
     * @param aDate
     * @param amount 月数
     * @return
     */
    public static final Date addMonths(Date aDate, int amount) {
        return addTime(aDate, Calendar.MONTH, amount);
    }

    /**
     * 为一个日期加上指定天数
     *
     * @param aDate
     * @param amount 天数
     * @return
     */
    public static final Date addDays(Date aDate, int amount) {
        return addTime(aDate, Calendar.DAY_OF_YEAR, amount);
    }

    /**
     * 为一个日期加上指定天数
     *
     * @param aDate  yyyyMMdd格式字串
     * @param amount 天数
     * @return
     */
    public static final String addDays(String aDate, int amount) {
        try {
            return toMailDateDtPartString(addTime(parseMailDateDtPartString(aDate), Calendar.DAY_OF_YEAR, amount));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 为一个日期加上指定小时数
     *
     * @param aDate
     * @param amount 小时数
     * @return
     */
    public static final Date addHours(Date aDate, int amount) {
        return addTime(aDate, Calendar.HOUR, amount);

    }

    /**
     * 为一个日期加上指定分钟数
     *
     * @param aDate
     * @param amount 分钟数
     * @return
     */
    public static final Date addMinutes(Date aDate, int amount) {
        return addTime(aDate, Calendar.MINUTE, amount);
    }

    /**
     * 为一个日期加上指定秒数
     *
     * @param aDate
     * @param amount 秒数
     * @return
     */
    public static final Date addSeconds(Date aDate, int amount) {
        return addTime(aDate, Calendar.SECOND, amount);

    }

    private static final Date addTime(Date aDate, int timeType, int amount) {
        if (aDate == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        cal.add(timeType, amount);
        return cal.getTime();
    }

    //======================================5.时间国际化=================================

    /**
     * 得到当前时间的UTC时间
     *
     * @return
     */
    public static final String getUTCTime() {
        return getSpecifiedZoneTime(Calendar.getInstance().getTime(), TimeZone.getTimeZone("GMT+0"));
    }

    /**
     * 得到指定时间的UTC时间
     *
     * @param aDate 时间戳
     * @return yyyy-MM-dd HH:mm:ss 格式
     */
    public static final String getUTCTime(Date aDate) {
        return getSpecifiedZoneTime(aDate, TimeZone.getTimeZone("GMT+0"));
    }

    /**
     * 得到当前时间的指定时区的时间
     *
     * @param tz
     * @return
     */
    public static final String getSpecifiedZoneTime(TimeZone tz) {
        return getSpecifiedZoneTime(Calendar.getInstance().getTime(), tz);

    }

    /**
     * 得到指定时间的指定时区的时间
     *
     * @param aDate 时间戳,Date是一个瞬间的long型距离历年的位移偏量，
     *              在不同的指定的Locale/TimeZone的jvm中，它toString成不同的显示值，
     *              所以没有必要为它再指定一个TimeZone变量表示获取它时的jvm的TimeZone
     * @param tz    要转换成timezone
     * @return yyyy-MM-dd HH:mm:ss 格式
     */
    public static final String getSpecifiedZoneTime(Date aDate, TimeZone tz) {
        if (aDate == null) {
            return StringUtils.EMPTY;
        }
        Assert.notNull(tz, "格式错误");
        SimpleDateFormat sdf = new SimpleDateFormat(LONG_DATE_FORMAT);
        sdf.setTimeZone(tz);
        return sdf.format(aDate);
    }

    //==================================6. miscellaneous==========================

    /**
     * 计算两个日期之间相差的月数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static final int getDifferenceMonths(Date startDate, Date endDate) {
        Assert.notNull(startDate, "格式错误");
        Assert.notNull(endDate, "格式错误");
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        return Math.abs((startCal.get(Calendar.YEAR) - endCal.get(Calendar.YEAR)) * 12
                + (startCal.get(Calendar.MONTH) - endCal.get(Calendar.MONTH)));
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param startDateStr yyyy-mm-dd
     * @param endDateStr   yyyy-mm-dd
     * @return
     */
    public static final int getDifferenceMonths(String startDateStr, String endDateStr) {
        DateValidator.checkShortDateStr(startDateStr);
        DateValidator.checkShortDateStr(endDateStr);
        try {
            return getDifferenceMonths(parseShortDateString(startDateStr), parseShortDateString(endDateStr));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取两个日期间的月份，返回一个list,包含如下内容：yyyy-MM
     * @return
     */
    public static List<Map<String,Object>> getMonthBetween(String minDate, String maxDate){
        Date start = parseDateString(minDate);
        Date end = parseDateString(maxDate);
        return getBetweenMonthFirstAndEnd(start,end);
    }

    /**
     * 获取两个日期间的月份，返回一个list,
     * @return
     */
    public static List<Map<String,Object>> getBetweenMonthFirstAndEnd(Date minDate, Date maxDate) {
        List<Map<String,Object>> result = new ArrayList<>();

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            Map<String,Object> map = new HashMap<>(4);
            Date time = curr.getTime();
            Date monthFirst = getMonthFirst(time);
            Date monthEnd = getMonthEnd(time);

            map.put("startDate",toFormatDateString(monthFirst,SQL_FULL_DATE_FORMAT));
            map.put("endDate",toFormatDateString(monthEnd,SQL_FULL_DATE_FORMAT));

            result.add(map);
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDateStr yyyy-mm-dd
     * @param endDateStr   yyyy-mm-dd
     * @return
     */
    public static final int getDifferenceDays(String startDateStr, String endDateStr) {
        return Double.valueOf(getDifferenceMillis(startDateStr, endDateStr) / (NANO_ONE_DAY)).intValue();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDateStr yyyymmdd
     * @param endDateStr   yyyymmdd
     * @return
     */
    public static final int getDifferenceDays2(String startDateStr, String endDateStr) {
        return Double.valueOf(getDifferenceMillis(startDateStr, endDateStr, MAIL_DATE_DT_PART_FORMAT) / (NANO_ONE_DAY))
                .intValue();
    }

    /* ------- start ------------ */

    /**
     * 两个日期之间相减（存在负数）
     *
     * @param startDateStr yyyy-mm-dd
     * @param endDateStr   yyyy-mm-dd
     * @return
     */
    public static final int getDaysSubtract(String startDateStr, String endDateStr) {
        return Double.valueOf(getDaysSubtractMillis(startDateStr, endDateStr) / (NANO_ONE_DAY)).intValue();
    }

    /**
     * 两个日期的相差天数（存在负数）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static final int getDaysSubtract(Date startDate, Date endDate) {
        return DateUtils.getDaysSubtract(DateUtils.toShortDateString(startDate),
                DateUtils.toShortDateString(endDate));
    }

    /**
     * 两个日期之间相减（存在负数）
     *
     * @param startDateStr yyyymmdd
     * @param endDateStr   yyyymmdd
     * @return
     */
    public static final int getDaysSubtract2(String startDateStr, String endDateStr) {
        return Double.valueOf(
                getDaysSubtractMillis(startDateStr, endDateStr, MAIL_DATE_DT_PART_FORMAT) / (NANO_ONE_DAY)).intValue();
    }

    /**
     * 两个日期之间相减（存在负数）
     *
     * @param startDateStr yyyy-mm-dd
     * @param endDateStr   yyyy-mm-dd
     * @return
     * @throws java.text.ParseException
     */
    public static final long getDaysSubtractMillis(String startDateStr, String endDateStr) {
        return getDaysSubtractMillis(startDateStr, endDateStr, SHORT_DATE_FORMAT);
    }

    /**
     * 计算两个日期之间相差的的毫秒数（存在负数）
     *
     * @param startDateStr
     * @param endDateStr
     * @param dateFormat
     * @return
     */
    public static final long getDaysSubtractMillis(String startDateStr, String endDateStr, String dateFormat) {
        try {
            return getDaysSubtractMillis(parser(startDateStr, dateFormat), parser(endDateStr, dateFormat));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算两个日期之间相差的的毫秒数（存在负数）
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static final long getDaysSubtractMillis(Date startDate, Date endDate) {
        Assert.notNull(startDate, "格式错误");
        Assert.notNull(endDate, "格式错误");
        return endDate.getTime() - startDate.getTime();
    }

    /* ------- end ------------ */

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static final int getDifferenceDays(Date startDate, Date endDate) {
        return Double.valueOf(getDifferenceMillis(startDate, endDate) / (NANO_ONE_DAY)).intValue();

    }

    /**
     * 计算两个日期之间相差的的毫秒数
     *
     * @param startDateStr yyyy-mm-dd
     * @param endDateStr   yyyy-mm-dd
     * @return
     * @throws java.text.ParseException
     */
    public static final long getDifferenceMillis(String startDateStr, String endDateStr) {
        return getDifferenceMillis(startDateStr, endDateStr, SHORT_DATE_FORMAT);
    }

    /**
     * 计算两个日期之间相差的的毫秒数
     *
     * @param startDateStr yyyyMMddHHmmss
     * @param endDateStr   yyyyMMddHHmmss
     * @return
     * @throws java.text.ParseException
     */
    public static final long getDifferenceMillis2(String startDateStr, String endDateStr) {
        return getDifferenceMillis(startDateStr, endDateStr, MAIL_DATE_FORMAT);
    }

    /**
     * 计算两个日期之间相差的的毫秒数
     *
     * @param startDateStr
     * @param endDateStr
     * @param dateFormat
     * @return
     */
    public static final long getDifferenceMillis(String startDateStr, String endDateStr, String dateFormat) {
        try {
            return getDifferenceMillis(parser(startDateStr, dateFormat), parser(endDateStr, dateFormat));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算两个日期之间相差的的毫秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static final long getDifferenceMillis(Date startDate, Date endDate) {
        Assert.notNull(startDate, "格式错误");
        Assert.notNull(endDate, "格式错误");
        return Math.abs(endDate.getTime() - startDate.getTime());
    }

    /**
     * 检验 日期是否在指定区间内，如果格式错误，返回false
     * 如果maxDateStr或minDateStr为空则比较时变为正负无穷大，如果都为空，则返回false
     *
     * @param aDate
     * @param minDateStr 必须是yyyy-MM-dd格式，时分秒为00:00:00
     * @param maxDateStr 必须是yyyy-MM-dd格式，时分秒为00:00:00
     * @return
     */
    public static final boolean isDateBetween(Date aDate, String minDateStr, String maxDateStr) {
        Assert.notNull(aDate, "格式错误");
        boolean ret = false;
        try {
            Date dMaxDate = null;
            Date dMinDate = null;
            dMaxDate = parseShortDateString(maxDateStr);
            dMinDate = parseShortDateString(minDateStr);
            switch ((dMaxDate != null ? 5 : 3) + (dMinDate != null ? 1 : 0)) {
                case 6:
                    ret = aDate.before(dMaxDate) && aDate.after(dMinDate);
                    break;
                case 5:
                    ret = aDate.before(dMaxDate);
                    break;
                case 4:
                    ret = aDate.after(dMinDate);
                    break;
            }
        } catch (ParseException e) {
            log.warn("isDateBetween parse failed", e);
        }
        return ret;
    }

    /**
     * 计算某日期所在月份的天数
     *
     * @param aDateStr yyyy-mm-dd
     * @return
     */
    public static final int getDaysInMonth(String aDateStr) {
        DateValidator.checkShortDateStr(aDateStr);
        try {
            return getDaysInMonth(parseShortDateString(aDateStr));
        } catch (ParseException e) {
            log.warn("getDaysInMonth parse failed", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算某日期所在月份的天数
     *
     * @param aDate
     * @return
     */
    public static final int getDaysInMonth(Date aDate) {
        Assert.notNull(aDate, "日期入参不能为空");
        Calendar cal = Calendar.getInstance();
        cal.setTime(aDate);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * yyyyMM
     *
     * @param aDate
     * @return
     */
    public static final int getYearAndMonth(Date aDate) {
        return Integer.parseInt(toMailDateDtPartString(aDate).substring(0, 6));
    }

    /**
     * @param date
     * @return
     */
    public static Date getPreviousMonthFirst(Date date) {
        Calendar lastDate = new GregorianCalendar();
        lastDate.setTime(date);
        lastDate.set(5, 1);
        lastDate.add(2, -1);
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        return lastDate.getTime();
    }

    public static Date getMonthFirst(Date date){
        Calendar lastDate = new GregorianCalendar();
        lastDate.setTime(date);
        lastDate.set(5, 1);
        lastDate.set(Calendar.HOUR_OF_DAY, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND,0);
        return lastDate.getTime();
    }

    /**
     * 获取指定天的Date格式
     *
     * @param dd          dd格式天
     * @param monthOffSet 月份偏移量
     * @return
     */
    public static Date getAssignDate(String dd, Integer monthOffSet) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthOffSet);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
        return calendar.getTime();
    }

    /**
     * @param date
     * @return
     * @throws java.text.ParseException
     */
    public static Date getPreviousMonthEnd(Date date) throws ParseException {
        Calendar lastDate = new GregorianCalendar();
        lastDate.setTime(date);
        lastDate.add(2, -1);
        lastDate.set(5, 1);
        lastDate.roll(5, -1);
        lastDate.set(Calendar.HOUR_OF_DAY, 23);
        lastDate.set(Calendar.MINUTE, 59);
        lastDate.set(Calendar.SECOND, 59);

        return lastDate.getTime();
    }

    public static Date getMonthEnd(Date date){
        Calendar lastDate = new GregorianCalendar();
        lastDate.setTime(date);
        lastDate.set(5, 1);
        lastDate.roll(5, -1);
        lastDate.set(Calendar.HOUR_OF_DAY, 23);
        lastDate.set(Calendar.MINUTE, 59);
        lastDate.set(Calendar.SECOND, 59);
        lastDate.set(Calendar.MILLISECOND,999);

        return lastDate.getTime();
    }

    /**
     * 获取小
     *
     * @return
     */
    public static Date getTodayBegin() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    /**
     * 获取某天的开始时间
     *
     * @param someDay
     * @return
     */
    public static Date getOneDayBegin(Date someDay) {
        if (someDay == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(someDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getOneDayBegin(String someDay, String format) throws ParseException {
        return getOneDayBegin(parser(someDay, format));
    }

    /**
     * 获取当天末尾时间
     *
     * @return
     */
    public static Date getTodayEnd() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return now.getTime();
    }

    /**
     * 获取某天的末尾时间
     *
     * @param someDay
     * @return
     */
    public static Date getOneDayEnd(Date someDay) {
        if (someDay == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(someDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
    public static Date getOneDayEnd2(Date someDay) {
        if (someDay == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(someDay);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND,999);
        return cal.getTime();
    }

    /**
     * 获取指定月日的日期
     *
     * @param date
     * @param monthNum
     * @param dayNum
     * @return
     */
    public static Date getSpecifyDate(Date date, Integer monthNum, Integer dayNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthNum);
        calendar.set(Calendar.DAY_OF_MONTH, dayNum);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取推迟或者提前几周指定的星期的日期
     *
     * @param weekNum
     * @param weekDay
     * @return
     */
    public static Date getSpecifyWeekDate(Integer weekNum, Integer weekDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, weekNum * 7);
        calendar.add(Calendar.DAY_OF_WEEK, weekDay);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 昨天的开始时间
     *
     * @return
     */
    public static Date startOfyesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 昨天的结束时间
     *
     * @return
     */
    public static Date endOfyesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.DATE, -1);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 获得一个日期的field部分
     *
     * @param field
     * @param aDate
     * @return
     */
    public static int getFieldOfDate(int field, Date aDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(aDate);
        return calendar.get(field);
    }

    /**
     * @param strDate
     * @param strDateBegin
     * @param strDateEnd
     * @return
     * @throws java.text.ParseException
     */
    public static boolean isInDates(String strDate, String strDateBegin, String strDateEnd) throws ParseException {
        SimpleDateFormat sd = new SimpleDateFormat(LONG_DATE_TM_PART_FORMAT);
        Date myDate = sd.parse(strDate);
        Date dateBegin = sd.parse(strDateBegin);
        Date dateEnd = sd.parse(strDateEnd);
        strDate = String.valueOf(myDate);
        strDateBegin = String.valueOf(dateBegin);
        strDateEnd = String.valueOf(dateEnd);

        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));

        int strDateBeginH = Integer.parseInt(strDateBegin.substring(11, 13));
        int strDateBeginM = Integer.parseInt(strDateBegin.substring(14, 16));
        int strDateBeginS = Integer.parseInt(strDateBegin.substring(17, 19));

        int strDateEndH = Integer.parseInt(strDateEnd.substring(11, 13));
        int strDateEndM = Integer.parseInt(strDateEnd.substring(14, 16));
        int strDateEndS = Integer.parseInt(strDateEnd.substring(17, 19));

        if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
            if (strDateH > strDateBeginH && strDateH < strDateEndH) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM > strDateBeginM && strDateH < strDateEndH) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS > strDateBeginS && strDateH < strDateEndH) {
                return true;
            } else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS == strDateBeginS && strDateH < strDateEndH) {
                return true;
            } else if (strDateH > strDateBeginH && strDateH == strDateEndH && strDateM < strDateEndM) {
                return true;
            } else if (strDateH > strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM && strDateS < strDateEndS) {
                return true;
            } else if (strDateH > strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM && strDateS == strDateEndS) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static Date getNextDate(Date now, int next, int dateField) {
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(dateField, next);
        return c.getTime();
    }

    /**
     * 把任意格式的字符串日期转化为日期
     *
     * @param date
     * @return
     */
    public static Date parse(String date) {
        Date result;
        String parse = date.replaceFirst("[0-9]{4}([^0-9]?)", "yyyy$1");
        parse = parse.replaceFirst("^[0-9]{2}([^0-9]?)", "yy$1");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1MM$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}( ?)", "$1dd$2");
        parse = parse.replaceFirst("( )[0-9]{1,2}([^0-9]?)", "$1HH$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1mm$2");
        parse = parse.replaceFirst("([^0-9]?)[0-9]{1,2}([^0-9]?)", "$1ss$2");
        SimpleDateFormat format = new SimpleDateFormat(parse);
        try {
            result = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            result = null;
        }
        return result;
    }

}
