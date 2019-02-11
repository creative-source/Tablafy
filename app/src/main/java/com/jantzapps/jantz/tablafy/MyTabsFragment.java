package com.jantzapps.jantz.tablafy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static com.jantzapps.jantz.tablafy.MainActivity.tvPageTitle;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyTabsFragment extends Fragment {

    public static class ToStand {
        private final String title;

        public ToStand(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    static ListView tabs;
    EditText addEntry;
    DbHelper dbHelper;
    FragmentManager fm;
    static TextView emptyList;

    public MyTabsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        final RelativeLayout mainLayout = (RelativeLayout) getView().findViewById(R.id.rlMain);
        if(mainLayout.getVisibility() == View.VISIBLE)
            mainLayout.setVisibility(View.GONE);
        else
            mainLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_tabs, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emptyList = (TextView) view.findViewById(R.id.tvListEmpty);

        tabs = (ListView) view.findViewById(R.id.lvTabs);

        tabs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"\u201C"+tabs.getItemAtPosition(i).toString()+"\u201D"+" "+"has been moved to your music stand.", Toast.LENGTH_LONG).show();
                TabFragment.viewPager.setCurrentItem(1);
                EventBus.getDefault().post(new ToStand(tabs.getItemAtPosition(i).toString()));
            }
        });

        fm = getActivity().getSupportFragmentManager();
        dbHelper = new DbHelper(getContext());

    }



    //UPDATE
    private void update () {
        String channel = addEntry.getText().toString();

        // GET POS OF SELECTED ITEM
        int pos = tabs.getCheckedItemPosition();

        if (pos > -1) {
            ArrayList <String> data = new ArrayList<String>();

            if (!channel.isEmpty() && channel.length() > 0) {

                //REMOVE
                //data.remove(Arraychannels.get(pos));

                //INSERT
                //data.insert(channel, pos);

                //REFRESH

                //data.notifyDataSetChanged();

                Toast.makeText(getContext(),  "Your"+ channel +"has been updated." , Toast.LENGTH_SHORT).show();

            } else {

            }
        } else {

        }
    }

}