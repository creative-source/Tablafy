package com.jantzapps.jantz.tablafy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    View rootView;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    SharedPreferences.Editor settingsEditor;
    TextView tvVibrate, tvSound;
    Switch swVibrate, swSound;
    RelativeLayout rlVibrate, rlSound;
    LinearLayout llOctOptimize, llKeyOctOptimize;
    RadioButton rbOctOp, rbKeyOctOp;
    ImageView ivInfo;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        rlVibrate = (RelativeLayout) rootView.findViewById(R.id.rlVibrate);
        rlSound = (RelativeLayout) rootView.findViewById(R.id.rlSound);
        swVibrate = (Switch) rootView.findViewById(R.id.swVibrate);
        swSound = (Switch) rootView.findViewById(R.id.swSound);
        tvVibrate = (TextView) rootView.findViewById(R.id.tvVibrate);
        tvSound = (TextView) rootView.findViewById(R.id.tvSound);
        llOctOptimize = (LinearLayout) rootView.findViewById(R.id.llOctOptimize);
        llKeyOctOptimize = (LinearLayout) rootView.findViewById(R.id.llKeyOctOptimize);
        rbKeyOctOp = (RadioButton) rootView.findViewById(R.id.rbKeyOct);
        rbOctOp = (RadioButton) rootView.findViewById(R.id.rbOct);
        ivInfo = (ImageView) rootView.findViewById(R.id.ivInfo);

        return rootView;
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();
        //SharedPreferences pref = getActivity().getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        SharedPreferences preference = getActivity().getSharedPreferences("settings.conf", Context.MODE_PRIVATE);
        settingsEditor = preference.edit();
        //editor = pref.edit();
        //final int adminPermission = pref.getInt("adminPermission", 0);

        listDataHeader.add("Main Instrument");
        listDataHeader.add("Language");

        List <String> language = new ArrayList<>();
        language.add("English");
        language.add("Espanol");
        language.add("Francais");
        language.add("Portuguese");

        List <String> instrument = new ArrayList<>();
        instrument.add("Guitar");
        instrument.add("Piano");
        instrument.add("Bass");
        instrument.add("Ukulele");
        instrument.add("Harmonica");

        listHash.put(listDataHeader.get(0),instrument);
        listHash.put(listDataHeader.get(1),language);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //final SharedPreferences pref = getActivity().getSharedPreferences("login.conf", Context.MODE_PRIVATE);
        final SharedPreferences preference = getActivity().getSharedPreferences("settings.conf", Context.MODE_PRIVATE);
        //final int adminPermission = pref.getInt("adminPermission", 0);
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        String vibratePosition = preference.getString("vibratePref","on");
        String soundPosition = preference.getString("soundPref","on");
        String processMethod = preference.getString("processPref","Oct");

        if(processMethod.equals("Oct")) {
            rbOctOp.setChecked(true);
            rbKeyOctOp.setChecked(false);
        }
        if(processMethod.equals("KeyOct")) {
            rbKeyOctOp.setChecked(true);
            rbOctOp.setChecked(false);
        }

        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                builder.setTitle("Music Processing Method");
                builder.setIcon(R.mipmap.tablafy_launcher);
                builder.setMessage("\n\tThe \"Music Processing Method\" is the method used to generate tab for any given instrument in Tablafy." +
                        "It is only used when the audio recorded by a user doesn't fall within an instrument's playable range." +
                        "\n" +
                        "\n" +
                        "\t" +
                        "By default, the music processing method is set to \"Octave Optimization\"." +
                        "When necessary Octave Optimization automatically moves the recorded audio up or down one or more octaves in order to move the most amount of notes into each instrument's playing range." +
                        "\n" +
                        "\n" +
                        "\t" +
                        "\"Key and Octave Optimization\" is the other music processing method." +
                        "When a user selects Key and Octave Optimization, two things will happen." +
                        "\n" +
                        "\n" +
                        "\t" +
                        "Like Octave Optimization, Key and Octave Optimization will attempt to move the recorded up or down one or more octaves when necessary." +
                        "It will also change the key of the audio to maximize the amount of notes that can be played on each instrument." +
                        "Therefore, this method will form tab with the most notes at the cost of changing the original key." +
                        "")
                        .setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        llOctOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbOctOp.isChecked()) {
                    rbOctOp.setChecked(false);
                    rbKeyOctOp.setChecked(true);
                    settingsEditor.putString("processPref","KeyOct");
                    settingsEditor.apply();
                }
                else if(!rbOctOp.isChecked()) {
                    rbOctOp.setChecked(true);
                    rbKeyOctOp.setChecked(false);
                    settingsEditor.putString("processPref","Oct");
                    settingsEditor.apply();
                }
            }
        });

        llKeyOctOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rbKeyOctOp.isChecked()) {
                    rbKeyOctOp.setChecked(false);
                    rbOctOp.setChecked(true);
                    settingsEditor.putString("processPref","Oct");
                    settingsEditor.apply();
                }
                else if (!rbKeyOctOp.isChecked()) {
                    rbKeyOctOp.setChecked(true);
                    rbOctOp.setChecked(false);
                    settingsEditor.putString("processPref","KeyOct");
                    settingsEditor.apply();
                }
            }
        });

        if(vibratePosition.equals("on")) {
            swVibrate.setChecked(true);
            tvVibrate.setText("On");
        }
        if(vibratePosition.equals("off")) {
            swVibrate.setChecked(false);
            tvVibrate.setText("Off");
        }
        if(soundPosition.equals("on")) {
            swSound.setChecked(true);
            tvSound.setText("On");
        }
        if(soundPosition.equals("off")) {
            swSound.setChecked(false);
            tvSound.setText("Off");
        }

        swSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!swSound.isChecked()){
                    swSound.setChecked(false);
                    tvSound.setText("Off");
                    settingsEditor.putString("soundPref","off");
                    settingsEditor.apply();
                }
                else if(swSound.isChecked()) {
                    swSound.setChecked(true);
                    tvSound.setText("On");
                    settingsEditor.putString("soundPref","on");
                    settingsEditor.apply();
                }
            }
        });

        swVibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!swVibrate.isChecked()){
                    swVibrate.setChecked(false);
                    tvVibrate.setText("Off");
                    settingsEditor.putString("vibratePref","off");
                    settingsEditor.apply();
                }
                else if(swVibrate.isChecked()) {
                    swVibrate.setChecked(true);
                    tvVibrate.setText("On");
                    settingsEditor.putString("vibratePref","on");
                    settingsEditor.apply();
                }
            }
        });

        rlVibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swVibrate.isChecked()) {
                    swVibrate.setChecked(false);
                }
                else if (!swVibrate.isChecked()){
                    swVibrate.setChecked(true);
                }
            }
        });

        rlSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swSound.isChecked()) {
                    swSound.setChecked(false);
                }
                else if (!swSound.isChecked()){
                    swSound.setChecked(true);
                }
            }
        });

        listView = (ExpandableListView) view.findViewById(R.id.settingslistview);
        initData();
        listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(0));
                parent.isGroupExpanded(index);
                if(groupPosition == 0) {
                    parent.collapseGroup(1);
                    listView.clearChoices();
                }
                if(groupPosition == 1) {
                    listView.clearChoices();
                }


                return false;
            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if(groupPosition == 0) {
                    //listView.collapseGroup(1);
                    listView.clearChoices();
                }
                if(groupPosition == 1) {
                    //listView.collapseGroup(0);
                    listView.clearChoices();
                }
            }
        });

        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                final String instrumentPref = preference.getString("instrumentPref", "guitar");
                final String langPref = preference.getString("langPref", "");
                if(groupPosition == 0) {
                    listView.collapseGroup(1);
                    listView.clearChoices();
                    if(instrumentPref.equals("guitar")) {
                        listView.setItemChecked(1,true);
                    }
                    if(instrumentPref.equals("piano")) {
                        listView.setItemChecked(2,true);
                    }
                    if(instrumentPref.equals("bass")) {
                        listView.setItemChecked(3,true);
                    }
                    if(instrumentPref.equals("ukulele")) {
                        listView.setItemChecked(4,true);
                    }
                    if(instrumentPref.equals("harmonica")) {
                        listView.setItemChecked(5,true);
                    }
                }
                if(groupPosition == 1) {
                    listView.collapseGroup(0);
                    listView.clearChoices();
                    if(langPref.equals("en")) {
                        listView.setItemChecked(2,true);
                    }
                    if(langPref.equals("es")) {
                        listView.setItemChecked(3,true);
                    }
                    if(langPref.equals("fr")) {
                        listView.setItemChecked(4,true);
                    }
                    if(langPref.equals("pt")) {
                        listView.setItemChecked(5,true);
                    }
                }
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition,long id) {
                if(groupPosition == 1) {
                    if(childPosition == 0) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,3));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("langPref", "en");
                        settingsEditor.apply();
                        ((App)getActivity().getApplicationContext()).changeLang(preference.getString("langPref", ""));
                        Intent refresh = new Intent(getContext(), MainActivity.class);
                        startActivity(refresh);
                        getActivity().finish();
                        Toast.makeText(getContext(), "English", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 1) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,3));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("langPref", "es");
                        settingsEditor.apply();
                        ((App)getActivity().getApplicationContext()).changeLang(preference.getString("langPref", ""));
                        Intent refresh = new Intent(getContext(), MainActivity.class);
                        startActivity(refresh);
                        getActivity().finish();
                        Toast.makeText(getContext(), "Spanish", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 2) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,3));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("langPref", "fr");
                        settingsEditor.apply();
                        ((App)getActivity().getApplicationContext()).changeLang(preference.getString("langPref", ""));
                        Intent refresh = new Intent(getContext(), MainActivity.class);
                        startActivity(refresh);
                        getActivity().finish();
                        Toast.makeText(getContext(), "French", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 3) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("langPref", "pt");
                        settingsEditor.apply();
                        ((App)getActivity().getApplicationContext()).changeLang(preference.getString("langPref", ""));
                        Intent refresh = new Intent(getContext(), MainActivity.class);
                        startActivity(refresh);
                        getActivity().finish();
                        Toast.makeText(getContext(),"Portuguese", Toast.LENGTH_SHORT).show();
                    }
                }
                if(groupPosition == 0) {
                    if(childPosition == 0) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,3));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("instrumentPref", "guitar");
                        settingsEditor.apply();
                        listView.collapseGroup(0);
                        Toast.makeText(getContext(), "Guitar", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 1) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,3));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("instrumentPref", "piano");
                        settingsEditor.apply();
                        listView.collapseGroup(0);
                        Toast.makeText(getContext(), "Piano", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 2) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,3));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("instrumentPref", "bass");
                        settingsEditor.apply();
                        listView.collapseGroup(0);
                        Toast.makeText(getContext(), "Bass", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 3) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("instrumentPref", "ukulele");
                        settingsEditor.apply();
                        listView.collapseGroup(0);
                        Toast.makeText(getContext(),"Ukulele", Toast.LENGTH_SHORT).show();
                    }
                    if(childPosition == 4) {
                        int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,childPosition));
                        int index1 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,1));
                        int index2 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,2));
                        int index3 = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition,0));
                        parent.setItemChecked(index1,false);
                        parent.setItemChecked(index2,false);
                        parent.setItemChecked(index3,false);
                        parent.setItemChecked(index,true);
                        settingsEditor.putString("instrumentPref", "harmonica");
                        settingsEditor.apply();
                        listView.collapseGroup(0);
                        Toast.makeText(getContext(),"Harmonica", Toast.LENGTH_SHORT).show();
                    }
                }


                return false;
            }
        });


    }


}

