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

import org.w3c.dom.Text;

/**
 * A placeholder fragment containing a simple view.
 */
public class daily_schedule extends Fragment {
    int day_number = 2;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String DAY_NUMBER = "day_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public  daily_schedule newInstance(int day_number) {
        daily_schedule fragment = new daily_schedule();
        Bundle args = new Bundle();
        args.putInt(DAY_NUMBER, day_number);
        fragment.setArguments(args);
        return fragment;
    }

    public daily_schedule() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_main, container, false);
       System.out.println(getArguments().getInt(DAY_NUMBER));
        LinearLayout list = (LinearLayout)rootView.findViewById(R.id.daily_schedule_list);
        CardView slot;
        TextView txt;
        slot = (CardView)inflater.inflate(R.layout.daily_schedule_slot_view, container, false);

        txt = (TextView) slot.findViewById(R.id.daily_schedule_text);
        txt.setText(Utilities.get_day());

        list.addView(slot);
        slot = (CardView)inflater.inflate(R.layout.daily_schedule_slot_view, container, false);
         txt = (TextView) slot.findViewById(R.id.daily_schedule_text);
        txt.setText("sunday");
        list.addView(slot);
        return rootView;
    }
}
