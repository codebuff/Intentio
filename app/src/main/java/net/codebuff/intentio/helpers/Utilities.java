package net.codebuff.intentio.helpers;

import android.util.Log;

import net.codebuff.intentio.preferences.PrefsManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by deepankar on 24/11/14.
 *
 * This class contains static methods which perform repititive tasks (if any line(s) of code is
 * needed more than 3 times in the code , then create a method for it.
 *
 */
public class Utilities {

    public static String get_day_name(int day_number){
        switch(day_number){
            case 1 : return "sunday";
            case 2 : return "monday";
            case 3 : return "tuesday";
            case 4 : return "wednesday";
            case 5 : return "thursday";
            case 6 : return "friday";
            case 7 : return "saturday";

            default : return "invalid";

        }
    }
    public static String get_day_name(String cell_data){
        cell_data = cell_data.toLowerCase();
        if(cell_data.contains("mon")){
            return "monday";
        }
        if(cell_data.contains("tue")){
            return "tuesday";
        }
        if(cell_data.contains("wed")){
            return "wednesday";
        }
        if(cell_data.contains("thu")){
            return "thursday";
        }
        if(cell_data.contains("fri")){
            return "friday";
        }
        if(cell_data.contains("sat")){
            return "saturday";
        }
        if(cell_data.contains("sun")){
            return "sunday";
        }
        return "invalid";
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
        //System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String[] sort_slots(String slots[]) {
        int current;
        int location;
        String temp;
        int j = 0;
        while (j < slots.length) {
            location = -1;
            current = 25;
            for (int i = j; i < slots.length; i++) {
                String slot_hour[] = slots[i].split(":");
                if (Integer.parseInt(slot_hour[0].trim()) < current) {
                    current = Integer.parseInt(slot_hour[0].trim());
                    location = i;
                }
            }
            if (location != -1) {
                temp = slots[j];
                slots[j] = slots[location];
                slots[location] = temp;
            }
            j++;

        }
        return slots;
    }

    public static String find_current_slot(String[] slots) {
        Calendar calendar = Calendar.getInstance();
        String slot = "invalid";

        int slot_start_hour = -1;
        int slot_start_minute = -1;
        int slot_end_hour = -1;
        int slot_end_minute = -1;

        String[] slot_exploded;
        int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
        int current_minute = calendar.get(Calendar.MINUTE);
        for (int i = 0; i < slots.length; i++) {
            slot_exploded = slots[i].split(":");
            slot_start_hour = Integer.parseInt(slot_exploded[0].trim());
            slot_end_minute = Integer.parseInt(slot_exploded[2].trim());
            slot_exploded = slot_exploded[1].split("-");
            slot_start_minute = Integer.parseInt(slot_exploded[0].trim());
            slot_end_hour = Integer.parseInt(slot_exploded[1].trim());

           /* System.out.println("===============");
            System.out.print("current hour ");
            System.out.println(current_hour);
            System.out.print("current minute ");
            System.out.println(current_minute);
            System.out.println("---------------");
            System.out.print("start hour ");
            System.out.println(slot_start_hour);
            System.out.print("start minute ");
            System.out.println(slot_start_minute);
            System.out.println("---------------");
            System.out.print("end hour ");
            System.out.println(slot_end_hour);
            System.out.print("end minute ");
            System.out.println(slot_end_minute);
            System.out.println("---------------");*/

            if (current_hour == slot_start_hour) {
                if (slot_start_minute <= current_minute) {
                    Constants.current_slot_number = i;
                    return slots[i];
                }
            }

            if (current_hour == slot_end_hour) {
                if (slot_end_minute >= current_minute) {
                    Constants.current_slot_number = i;
                    return slots[i];
                }
            }
        }

        /*if((current_hour >= slot_start_hour) && (current_minute >= slot_start_minute) && (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) ){
            Constants.week_finished = true;
        }*/

        if((current_hour >= slot_end_hour) && (current_minute >= slot_end_minute) ){
            Constants.current_time_is_past_last_slot = true;


        }

        return slot;
    }

    public static HashMap<String, String> find_next_slot(PrefsManager user,String[] slots){
        HashMap<String, String> next_slot = new HashMap<String, String>();
        String day ;
        String next_slot_info;
        next_slot.put("day","invalid");
        next_slot.put("next_slot_info","invalid");
        next_slot.put("next_slot_time","invalid");
        next_slot.put("day_number","-1");
        Calendar calendar = Calendar.getInstance();
        int[] day_number = new int[7];
        day_number[0] = calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < 7; i++) {
            if (day_number[i - 1] == 7) {
                day_number[i] = 1;
            } else {
                day_number[i] = day_number[i - 1] + 1;
            }
        }


        if(!Constants.current_time_is_past_last_slot) {
            for (int i = Constants.current_slot_number + 1; i < slots.length; i++) {
                day = get_day_name(day_number[0]);
                next_slot_info = user.get_schedule_slot(day, slots[i].trim());
                next_slot_info = next_slot_info.trim();
                //Log.e("slot", next_slot_info);
                if (!next_slot_info.equals(Constants.empty_slot)) {
                    next_slot.put("day", "Today");
                    next_slot.put("next_slot_info", next_slot_info);
                    next_slot.put("next_slot_time", slots[i]);
                    next_slot.put("day_number", Integer.toString(day_number[0]));
                    return next_slot;
                }
            }

            if((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)){
                Constants.week_finished = true;
            }

        }
        for(int j = 1;j < day_number.length;j++){
            day = get_day_name(day_number[j]);
            for(int i = 0 ;i< slots.length;i++){
                next_slot_info = user.get_schedule_slot(day,slots[i].trim());
                next_slot_info = next_slot_info.trim();

                if(!next_slot_info.equals(Constants.empty_slot)){
                    next_slot.put("day",get_day_name(day_number[j]));
                    next_slot.put("next_slot_info",next_slot_info);
                    next_slot.put("next_slot_time",slots[i]);
                    next_slot.put("day_number",Integer.toString(day_number[j]));
                    return next_slot;
                }
            }
        }

        return next_slot;
    }
}
