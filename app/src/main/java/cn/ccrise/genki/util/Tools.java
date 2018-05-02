package cn.ccrise.genki.util;

import android.content.Context;
import android.os.Build;

import cn.ccrise.genki.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.ccrise.genki.MyApplication;

/**
 * Created by wangxl on 2016/10/28.
 */
public class Tools {

    /**
     * 得到color的中间值
     *
     * @param start    起始值
     * @param end      最终值
     * @param fraction 进度(0-1之间)
     * @return
     */
    public static int getColorEvaluate(int start, int end, float fraction) {
        if (fraction < 0) {
            fraction = 0;
        }
        if (fraction > 1) {
            fraction = 1;
        }
        int startA = (start >> 24) & 0xff;
        int startR = (start >> 16) & 0xff;
        int startG = (start >> 8) & 0xff;
        int startB = start & 0xff;

        int endA = (end >> 24) & 0xff;
        int endR = (end >> 16) & 0xff;
        int endG = (end >> 8) & 0xff;
        int endB = end & 0xff;

        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double parseDouble(String str) {
        return parseDouble(str, 0);
    }

    public static double parseDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getColor(Context context, int colorId) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorId, context.getTheme());
        }
        return context.getResources().getColor(colorId);
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date parseDate(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String getMonth() {
        return getMonth(new Date());
    }

    /**
     * 获得月份
     *
     * @param date 时间
     * @return
     */
    public static String getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    /**
     * dp换算成px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        return (int) (dp * MyApplication.getInstance().getResources().getDisplayMetrics().density);
    }
}
