package net.codebuff.intentio.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.codebuff.intentio.R;
import net.codebuff.intentio.helpers.Utilities;
import net.codebuff.intentio.preferences.PrefsManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class DailySchedule extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String DAY_NUMBER = "day_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public DailySchedule newInstance(int day_number) {
        DailySchedule fragment = new DailySchedule();
        Bundle args = new Bundle();
        args.putInt(DAY_NUMBER, day_number);
        fragment.setArguments(args);
        return fragment;
    }

    public DailySchedule() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        LinearLayout list = (LinearLayout)rootView.findViewById(R.id.daily_schedule_list);
        populate_schedule(inflater,container,rootView,getArguments().getInt(DAY_NUMBER),list);
        return rootView;
    }

    public void populate_schedule(LayoutInflater inflater,ViewGroup container,View view,int day_number,LinearLayout list){
        PrefsManager user = new PrefsManager(getActivity().getApplicationContext());
        String day = Utilities.get_day_name(day_number);
        String sl = user.get_slots().replace("[","").replace("]","");
        String[] slots = sl.split(",");
        slots = Utilities.sort_slots(slots);
        CardView slot;
        TextView slot_time;
        TextView slot_info;

        for(int i = 0 ; i<slots.length ;i++){
            //System.out.println(slots[i]);
            slot = (CardView)inflater.inflate(R.layout.daily_schedule_slot_view, container, false);
            slot_time = (TextView) slot.findViewById(R.id.slot_time);
            slot_time.setText(slots[i].trim());
            slot_info = (TextView)slot.findViewById(R.id.slot_info);
            slot_info.setText(user.get_schedule_slot(day,slots[i].trim()));
            list.addView(slot);
        }
    }
}
