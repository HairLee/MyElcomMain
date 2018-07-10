package com.elcom.hailpt.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Hailpt on 6/14/2018.
 */
public class DateTimeUtils {

    public static String getDayMonthYearFromDate(Context context, Date date){
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return  inputFormat.format(date);
    }

    public static String getToDayDateTime(Context context){
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return  dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String getDayMonthYear(){
        DateFormat dateFormat = new SimpleDateFormat("dd");
        return  dateFormat.format(Calendar.getInstance().getTime());
    }


    public static String getToDayDateTimeFormat(Context context){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return  dateFormat.format(Calendar.getInstance().getTime());
    }

    public static String convertLongToTime(String TimeinMilliSeccond){
        if (TimeinMilliSeccond.equals("")){
            return "";
        }
        String dateString = new SimpleDateFormat("MM/dd/yyyy").format(new Date(Long.parseLong(TimeinMilliSeccond)));
        return dateString;
    }

    public static  List<List<Date>> getBigListCurrentDate(Context context){
        List<List<Date>> mParts;
        List<Date> mDates;
        int currentPosOfDay = 0;

        Calendar calSt = Calendar.getInstance();
        calSt.set(Calendar.YEAR, 2018);
        calSt.set(Calendar.MONTH, Calendar.JANUARY);
        calSt.set(Calendar.DAY_OF_MONTH, 1);
        Date stDate = calSt.getTime();

        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.YEAR, 2018);
        calEnd.set(Calendar.MONTH, Calendar.UNDECIMBER);
        calEnd.set(Calendar.DAY_OF_MONTH, 1);
        Date stEnd = calEnd.getTime();

        mDates =  getDatesBetweenUsingJava7(stDate, stEnd);


        for (int i = mDates.size() - 1; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(mDates.get(i));
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
                mDates.remove(i);
            }
        }

        for (int i = mDates.size() - 1; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(mDates.get(i));
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
                mDates.remove(i);
            }
        }


        mParts = chopped(mDates, 5);


        return mParts;
    }



    public static  List<Date> getListCurrentDate(Context context){
        List<List<Date>> mParts;
        List<Date> mDates;
        int currentPosOfDay = 0;

        Calendar calSt = Calendar.getInstance();
        calSt.set(Calendar.YEAR, 2018);
        calSt.set(Calendar.MONTH, Calendar.JANUARY);
        calSt.set(Calendar.DAY_OF_MONTH, 1);
        Date stDate = calSt.getTime();

        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.YEAR, 2018);
        calEnd.set(Calendar.MONTH, Calendar.UNDECIMBER);
        calEnd.set(Calendar.DAY_OF_MONTH, 1);
        Date stEnd = calEnd.getTime();

        mDates =  getDatesBetweenUsingJava7(stDate, stEnd);


        for (int i = mDates.size() - 1; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(mDates.get(i));
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
                mDates.remove(i);
            }
        }

        for (int i = mDates.size() - 1; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(mDates.get(i));
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){
                mDates.remove(i);
            }
        }

        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        Calendar calendar = Calendar.getInstance();
        Date CurremtDate = calendar.getTime();
        mParts = chopped(mDates, 5);

        for (int i = 0; i < mParts.size(); i++) {
            for (int i1 = 0; i1 < mParts.get(i).size(); i1++) {
                if(dateFormat.format(CurremtDate).equals(dateFormat.format(mParts.get(i).get(i1)))){
                    currentPosOfDay = i;
                }
            }
        }

        return mParts.get(currentPosOfDay);
    }

    static int getCurrentPositionOfDayInList(List<Date> dateList, Date CurremtDate, Context context){
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        int pos = 0;
        for (int i = 0; i < dateList.size(); i++) {
            if(dateFormat.format(CurremtDate).equals(dateFormat.format(dateList.get(i)))){
                pos = i;
            }
        }
        return pos;
    }

    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

    public static List<Date> getDatesBetweenUsingJava7(
            Date startDate, Date endDate) {
        List<Date> datesInRange = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return datesInRange;
    }

    public static boolean isCurrentTimeIsBefore9Am(){
        DateFormat dateFormat = new SimpleDateFormat("H");
        int curentTime = Integer.parseInt(dateFormat.format(Calendar.getInstance().getTime()));
        return curentTime < 9;
    }

    public static int currentDay(){
        DateFormat dateFormat = new SimpleDateFormat("d");
        int curentTime = Integer.parseInt(dateFormat.format(Calendar.getInstance().getTime()));
        return curentTime;
    }

    public static String convertLongToTimeDate(String longV){

        if (longV.equals("")){
            return "Không có dữ liệu";
        }

        long millisecond = Long.parseLong(longV);
        String dateString = android.text.format.DateFormat.format("h:mm", new Date(millisecond)).toString();
        return  dateString;
    }

}
