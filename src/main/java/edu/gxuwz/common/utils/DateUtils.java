package edu.gxuwz.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 * 
 * @author epmc
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     * 
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算两个时间差 - 天
     */
    public static String getDatePoorForDay(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        return day + "天";
    }

    /**
     * 计算两个时间差超过24小时, true 超过，false没超过
     */
    public static boolean getDatePoorOver24(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        return nd < diff;
    }

    /**
     * 获取当前时间的下一天
     * @param date
     * @return
     */
    public static String getNextDate(Date date){
        long nd = 1000 * 24 * 60 * 60;
        long d = date.getTime() + nd;
        java.sql.Date day = new java.sql.Date(d);
        return parseDateToStr(YYYY_MM_DD, day);
    }

    /*public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("2020-02-21");
        list.add("2020-02-22");
        list.add("2020-02-23");
        //list.add("2020-02-24");
        list.add("2020-02-25");
        list.add("2020-02-26");
        list.add("2020-02-27");
        list.add("2020-02-28");
        list.add("2020-02-29");
        list.add("2020-03-01");
        //list.add("2020-03-02");
        list.add("2020-03-03");
        list.add("2020-03-04");
        //list.add("2020-03-05");
        list.add("2020-03-06");
        String min = list.get(0);
        String max = list.get(list.size()-1);
        Date date = parseDate(min); // 开始时间
        System.out.println(date.getTime());
        Date date1 = parseDate(max); // 结束时间
        System.out.println(date1.getTime());

        String datePoor = getDatePoor(date1, DateUtils.parseDate(DateUtils.getDate()));
        System.out.println(datePoor);

        // 获取开始时间到结束时间内的日期数据
        String date2 = getNextDate(parseDate(DateUtils.getDate()));
        LinkedList<String> all = new LinkedList<>();
        String end = min;
        while (!date2.equals(end)){
            all.add(end);
            end = getNextDate(parseDate(end));
        }
        for (String a: all) {
            System.out.println(a);
        }
        System.out.println("天数" + all.size());
        all.removeAll(list);
        System.out.println("天数" + all.size());
        for (String a: all) {
            System.out.println(a);
        }

        // 为空，就是all的大小，不为空，取all最后一条数据与当前日期对比
        String datePoor1 = getDatePoorForDay(DateUtils.parseDate(DateUtils.getDate()), parseDate("2020-03-06"));
        System.out.println(datePoor1);
    }
*/
}
