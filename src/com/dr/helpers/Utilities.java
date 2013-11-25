package com.dr.helpers;

import android.widget.DatePicker;
import android.widget.TimePicker;

import com.dr.objects.Interview;
import com.dr.objects.JobApplication;
import com.dr.objects.dao.JobApplicationDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pranit on 11/25/13.
 */
public class Utilities {


    public static Calendar getCalendarFromDateTimePicker(DatePicker datePicker, TimePicker timePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);

        return calendar;
    }

    public static void setDateTimePickerFromCalendar(DatePicker datePicker, TimePicker timePicker, Calendar calendar) {
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
    }

    public static String printDateTime(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d, HH:mma");
        try {
            return format.format(calendar.getTime());
        } catch (Exception e) {
            return null;
        }
    }

    public static String printDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(calendar.getTime());
    }

    public static String printTime(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mma");
        return format.format(calendar.getTime());
    }

    public static String getStringFromCalendar(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = calendar.getTime();
        return sdf.format(date);
    }
}
