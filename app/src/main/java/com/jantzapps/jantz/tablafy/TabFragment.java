package com.jantzapps.jantz.tablafy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.jantzapps.jantz.tablafy.MyTabsFragment.emptyList;
import static com.jantzapps.jantz.tablafy.MyTabsFragment.tabs;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static int int_items= 3;
    DbHelper dbHelper;

    //ArrayAdapter<String> adapter;


    public TabFragment() {
        // Required empty public constructor
    }

    public void loadChannelList() {
        ArrayList<String> data = new ArrayList<String>();

        MyListAdapter adapter = new MyListAdapter(getContext(),R.layout.tab_list_layout, data);
        tabs.setAdapter(adapter);
        adapter.clear();
        adapter.addAll(dbHelper.getChannelList());

        try {
            if (adapter.isEmpty()) {
                emptyList.setVisibility(View.VISIBLE);
            } else {
                emptyList.setVisibility(View.GONE);

            }
        } catch (Exception e) {

        }

        adapter.notifyDataSetChanged();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(tabLayout.getVisibility() == View.VISIBLE)
            tabLayout.setVisibility(View.GONE);
        else
            tabLayout.setVisibility(View.VISIBLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tab,null);
        tabLayout=(TabLayout)v.findViewById(R.id.tabs);
        viewPager=(ViewPager)v.findViewById(R.id.viewpager);
        dbHelper = new DbHelper(getContext());


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.ic_listen_white_24dp);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                if(tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.tab_standon);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                }
                if(tab.getPosition() == 2) {
                    loadChannelList();
                    tab.setIcon(R.drawable.ic_tab_list_white_24dp);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                //also you can use tab.setCustomView() too
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    tab.setIcon(R.drawable.ic_listen_gray_24dp);
                }
                if(tab.getPosition() == 1) {
                    tab.setIcon(R.drawable.score_standoff);
                }
                if(tab.getPosition() == 2) {
                    tab.setIcon(R.drawable.ic_tab_list_gray_24dp);

                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //set an adpater

        viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

                int[] imageResId = {
                        R.drawable.ic_listen_white_24dp,
                        R.drawable.score_standoff,
                        R.drawable.ic_tab_list_gray_24dp};

                for (int i = 0; i < imageResId.length; i++) {
                    TabLayout.Tab tab = tabLayout.getTabAt(i).setIcon(imageResId[i]);
                    if (tab != null) tab.setCustomView(R.layout.tab_with_icon);
                }
            }
        });
        return v;
    }

    public class MyListAdapter extends ArrayAdapter<String> {
        private int layout;

        public MyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TabFragment.ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                final TabFragment.ViewHolder viewHolder = new TabFragment.ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.tvTabName);
                viewHolder.title.setText(getItem(position));
                viewHolder.button = (ImageButton) convertView.findViewById(R.id.btnDelete);
                viewHolder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.app_name);
                        builder.setIcon(R.mipmap.tablafy_launcher);
                        builder.setMessage("Are you sure you want to remove"+" "+"\u201C"+getItem(position)+"\u201D"+" "+"from your tab book?")
                                .setCancelable(false)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        delete(getItem(position));

                                    }
                                })
                                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                convertView.setTag(viewHolder);
            }
            else {
                mainViewholder = (TabFragment.ViewHolder) convertView.getTag();
                mainViewholder.title.setText(getItem(position));
            }
            return convertView;
        }
    }

    public class ViewHolder {
        TextView title;
        ImageButton button;
    }

    //DELETE
    private void delete(String position) {
        ArrayList <String> data = new ArrayList<String>();
        MyListAdapter adapter = new MyListAdapter(getContext(),R.layout.tab_list_layout, data);
        //int pos = channels.getCheckedItemPosition();
        //String channel = addEntry.getText().toString();
        //if(pos > -1) {
        //REMOVE
        adapter.remove(position);
        dbHelper.deleteChannel(position);

        loadChannelList();

        //REFRESH
        adapter.notifyDataSetChanged();

        //addEntry.setText("");
        Toast.makeText(getContext(),  "\u201C"+position+"\u201D"+" "+"has been removed from your tab book.", Toast.LENGTH_SHORT).show();

        //} else {
        //Toast.makeText(getContext(), "no deletion", Toast.LENGTH_SHORT).show();

        //}
    }



}
