package net.codebuff.intentio.helpers;

import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by deepankar on 24/11/14.
 *
 * This class contains static methods which perform repititive tasks (if any line(s) of code is
 * needed more than 3 times in the code , then create a method for it.
 *
 */
public class Utilities {

    public static String get_day_name(int num){
        switch(num){
            case 6 : return "monday";
            case 7 : return "tuesday";
            case 8 : return "wednesday";
            case 9 : return "thursday";
            case 10 : return "friday";
            case 11 : return "saturday";
            case 12 : return "sunday";
            default : return "invalid";

        }
    }

    public static String get_day(){
        String day;
        Calendar calendar = Calendar.getInstance();
        day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        day = day.toLowerCase();
        //System.out.println("today is " + day);
        return day;
    }

    public static int get_day_number(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
