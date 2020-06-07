package c.b.s.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by guiqingqing on 2018/8/30.
 */
public class DateUtil {
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final int MILLIS_OF_DAY = 24 * 60 * 60 * 1000;
    private static final String YYYYMMDD_PATTERN = "((19[7-9][0-9]|20[0-9]{2})$YM(((0?[13578]|1[02])$MD(0?[1-9]|[12][0-9]|3[01]))|(0?[469]|11)$MD(0?[1-9]|[12][0-9]|30)|(0?2$MD(0?[1-9]|1[0-9]|2[0-8])))|(19[79][26]|198[048]|20[13579][26]|20[02468][048])$YM0?2$MD29)";
    private static final String HHMMSS_PATTERN = "((0?[0-9]|1[0-9]|2[0-3])$HM(0?[0-9]|[1-5][0-9])$MS(0?[0-9]|[1-5][0-9]))";
    private static final String SLANT_LINE = "/";
    private static final String TRANSVERSE_LINE = "-";
    private static final String COLON = ":";
    private static final String DATE_PATTERN_WITH_SLANT_LINE_COLON = YYYYMMDD_PATTERN.replaceAll("\\$YM", SLANT_LINE).replaceAll("\\$MD", SLANT_LINE);
    private static final String DATE_PATTERN_WITH_TRANSVERSE_LINE_COLON = YYYYMMDD_PATTERN.replaceAll("\\$YM", TRANSVERSE_LINE).replaceAll("\\$MD", TRANSVERSE_LINE);
    private static final Pattern SLANT_LINE_COLON_PATTERN = Pattern.compile(DATE_PATTERN_WITH_SLANT_LINE_COLON);
    private static final Pattern TRANSVERSE_LINE_COLON_PATTERN = Pattern.compile(DATE_PATTERN_WITH_TRANSVERSE_LINE_COLON);

    private static final String DATE_TIME_PATTERN_WITH_TRANSVERSE_LINE_COLON = YYYYMMDD_PATTERN.replaceAll("\\$YM", TRANSVERSE_LINE).replaceAll("\\$MD", TRANSVERSE_LINE)
            + " " + HHMMSS_PATTERN.replaceAll("\\$HM", COLON).replaceAll("\\$MS", COLON);
    private static final String DATE_TIME_PATTERN_WITH_SLANT_LINE_COLON = YYYYMMDD_PATTERN.replaceAll("\\$YM", SLANT_LINE).replaceAll("\\$MD", SLANT_LINE)
            + " " + HHMMSS_PATTERN.replaceAll("\\$HM", COLON).replaceAll("\\$MS", COLON);

    private static final Pattern DATE_TIME_TRANSVERSE_LINE_COLON_PATTERN = Pattern.compile(DATE_TIME_PATTERN_WITH_TRANSVERSE_LINE_COLON);
    private static final Pattern DATE_TIME_SLANT_LINE_COLON_PATTERN = Pattern.compile(DATE_TIME_PATTERN_WITH_SLANT_LINE_COLON);
    private static final int DAYS_OF_WEEK = 7;

    /**
     * 返回当前日期
     * @return
     */
    public static Date newDate() {
        return new Date();
    }

    /**
     * 返回当前日期字符串
     * @return
     */
    public static String now() {
        return format(newDate());
    }

    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, DEFAULT_PATTERN);
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 转换日期
     * @param source
     * @return
     * @throws ParseException
     */
    public static Date parse(String source) throws ParseException {
        return parse(source, DEFAULT_PATTERN);
    }

    /**
     * 转换日期
     * @param source
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date parse(String source, String pattern) throws ParseException {
        if (source == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        return sdf.parse(source);
    }

    /**
     * 计算当前日期与传进来的日期相差的天数
     * @param date
     * @return
     */
    public static int differOfToday(Date date) {
        return dateDiff(newDate(), date);
    }

    /**
     * 计算date1与date2相差的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int dateDiff(Date date1, Date date2) {
        date1 = truncateDate(date1);
        date2 = truncateDate(date2);
        long defultMills = date1.getTime() - date2.getTime();
        return (int) (defultMills / MILLIS_OF_DAY);
    }

    public static Date truncateDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(truncateDate(date));
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    public static boolean check(String source, String pattern) {
        return Pattern.compile(pattern).matcher(source).matches();
    }

    public static String calcTime(Date startDate, Date endDate) {
        StringBuilder buffer = new StringBuilder();
        long timeMillis = endDate.getTime() - startDate.getTime();
        long timeSeconds = timeMillis / 1000;
        int days = (int) timeSeconds / (60 * 60 * 24);
        int hours = (int) timeSeconds % (60 * 60 * 24) / (60 * 60);
        int minutes = (int) timeSeconds % (60 * 60) / 60;
        if (days > 0) {
            buffer.append(days).append("天");
        }
        if (hours > 0 || buffer.length() > 0) {
            buffer.append(hours).append("小时");
        }
        buffer.append(minutes).append("分钟");
        return buffer.toString();
    }


    public static LocalDateTime parseToLocalDateTime(String str) {

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, fmt);
    }

    public static String fmtLocalDateTimeToStr(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(pattern);
        return fmt.format(dateTime);
    }

    public static LocalDateTime dateToLocalDateTime(Date date){
        if(date == null){
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime){
        if(localDateTime == null){
            return null;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 验证日期是否合法
     * 1970年1月1日0时0分0秒~2099年12月31日23时59分59秒
     * @param source
     * @return
     */
    public static boolean isValidDateWithSlantLineColon(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return SLANT_LINE_COLON_PATTERN.matcher(source).matches();
    }

    /**
     * 验证日期时间是否合法
     * 1970年1月1日0时0分0秒~2099年12月31日23时59分59秒
     * @param source
     * @return
     */
    public static boolean isValidDateTimeWithSlantLineColon(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return DATE_TIME_SLANT_LINE_COLON_PATTERN.matcher(source).matches();
    }

    /**
     * 验证日期是否合法
     * 1970年1月1日0时0分0秒~2099年12月31日23时59分59秒
     * @param source
     * @return
     */
    public static boolean isValidDateWithTransverseLineColon(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return TRANSVERSE_LINE_COLON_PATTERN.matcher(source).matches();
    }

    /**
     * 验证日期时间是否合法
     * 1970年1月1日0时0分0秒~2099年12月31日23时59分59秒
     * @param source
     * @return
     */
    public static boolean isValidDateTimeWithTransverseLineColon(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return DATE_TIME_TRANSVERSE_LINE_COLON_PATTERN.matcher(source).matches();
    }

    public static Date firstDayOfYear() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).withDayOfYear(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfLastYear() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusYears(1).withDayOfYear(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDayOfYear() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfYear());
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDayOfLastYear() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusYears(1).with(TemporalAdjusters.lastDayOfYear());
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).withDayOfMonth(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfLastMonth() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusMonths(1).withDayOfMonth(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDayOfMonth() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDayOfLastMonth() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        switch (localDateTime.getDayOfWeek()) {
            case MONDAY:
                localDateTime = localDateTime.minusDays(0);
                break;
            case TUESDAY:
                localDateTime = localDateTime.minusDays(1);
                break;
            case WEDNESDAY:
                localDateTime = localDateTime.minusDays(2);
                break;
            case THURSDAY:
                localDateTime = localDateTime.minusDays(3);
                break;
            case FRIDAY:
                localDateTime = localDateTime.minusDays(4);
                break;
            case SATURDAY:
                localDateTime = localDateTime.minusDays(5);
                break;
            case SUNDAY:
                localDateTime = localDateTime.minusDays(6);
                break;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfLastWeek() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusWeeks(1);
        switch (localDateTime.getDayOfWeek()) {
            case MONDAY:
                localDateTime = localDateTime.minusDays(0);
                break;
            case TUESDAY:
                localDateTime = localDateTime.minusDays(1);
                break;
            case WEDNESDAY:
                localDateTime = localDateTime.minusDays(2);
                break;
            case THURSDAY:
                localDateTime = localDateTime.minusDays(3);
                break;
            case FRIDAY:
                localDateTime = localDateTime.minusDays(4);
                break;
            case SATURDAY:
                localDateTime = localDateTime.minusDays(5);
                break;
            case SUNDAY:
                localDateTime = localDateTime.minusDays(6);
                break;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDayOfWeek() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        switch (localDateTime.getDayOfWeek()) {
            case MONDAY:
                localDateTime = localDateTime.plusDays(6);
                break;
            case TUESDAY:
                localDateTime = localDateTime.plusDays(5);
                break;
            case WEDNESDAY:
                localDateTime = localDateTime.plusDays(4);
                break;
            case THURSDAY:
                localDateTime = localDateTime.plusDays(3);
                break;
            case FRIDAY:
                localDateTime = localDateTime.plusDays(2);
                break;
            case SATURDAY:
                localDateTime = localDateTime.plusDays(1);
                break;
            case SUNDAY:
                localDateTime = localDateTime.plusDays(0);
                break;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date lastDayOfLastWeek() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusWeeks(1);
        switch (localDateTime.getDayOfWeek()) {
            case MONDAY:
                localDateTime = localDateTime.plusDays(6);
                break;
            case TUESDAY:
                localDateTime = localDateTime.plusDays(5);
                break;
            case WEDNESDAY:
                localDateTime = localDateTime.plusDays(4);
                break;
            case THURSDAY:
                localDateTime = localDateTime.plusDays(3);
                break;
            case FRIDAY:
                localDateTime = localDateTime.plusDays(2);
                break;
            case SATURDAY:
                localDateTime = localDateTime.plusDays(1);
                break;
            case SUNDAY:
                localDateTime = localDateTime.plusDays(0);
                break;
        }
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date minTimeToday() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date minTimeYesterday() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).minusDays(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date maxTimeToday() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date maxTimeYesterday() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MAX).minusDays(1);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getFirstDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(truncateDate(newDate()));
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getFirstDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(truncateDate(newDate()));
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static int getDaysOfYear() {
        return dateDiff(lastDayOfYear(), firstDayOfYear()) + 1;
    }

    public static int getRemainDaysOfYear() {
        return dateDiff(lastDayOfYear(), newDate()) + 1;
    }

    public static int getDaysOfMonth() {
        return dateDiff(lastDayOfMonth(), firstDayOfMonth()) + 1;
    }

    public static int getRemainDaysOfMonth() {
        return dateDiff(lastDayOfMonth(), newDate()) + 1;
    }

    public static int getRemainDaysOfWeek() {
        return dateDiff(lastDayOfWeek(), newDate()) + 1;
    }

    public static BigDecimal getPercentOfYear() {
        return new BigDecimal(getRemainDaysOfYear()).divide(new BigDecimal(getDaysOfYear()), 10, RoundingMode.UP);
    }

    public static BigDecimal getPercentOfMonth() {
        return new BigDecimal(getRemainDaysOfMonth()).divide(new BigDecimal(getDaysOfMonth()), 10, RoundingMode.UP);
    }

    public static BigDecimal getPercentOfWeek() {
        return new BigDecimal(getRemainDaysOfWeek()).divide(new BigDecimal(DAYS_OF_WEEK), 10, RoundingMode.UP);
    }
}