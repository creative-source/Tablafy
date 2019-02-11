package com.jantzapps.jantz.tablafy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.string.no;
import static android.R.string.yes;
import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment {
    View rootView;
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;


    public InformationFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_information, container, false);

        return rootView;
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("Legal");

        List <String> legal = new ArrayList<>();
        legal.add("Privacy Policy");
        legal.add("Disclaimer");

        listHash.put(listDataHeader.get(0),legal);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final FragmentManager fm = getActivity().getSupportFragmentManager();

        Button faqs = (Button) view.findViewById(R.id.btnFaqs);
        Button learn = (Button) view.findViewById(R.id.btnLearn);

        final FragmentTransaction fragmentTransaction= fm.beginTransaction();

        listView = (ExpandableListView) view.findViewById(R.id.infolistview);

        TextView about = (TextView) view.findViewById(R.id.tvAbout);
        TextView about2 = (TextView) view.findViewById(R.id.tvAbout2);
        TextView about3 = (TextView) view.findViewById(R.id.tvAbout3);
        //TextView about2 = (TextView) view.findViewById(R.id.tvAbout2);

        about.setText("\t"+"Tablafy allows users to easily play their favorite songs on a variety of instruments with the push of a button. As the name implies, Tablafy creates musical tablature.");
        about2.setText("\t"+"Tablature or Tab, as opposed to traditional musical notation, allows for a more intuitive way of understanding and playing an instrument.");
        about3.setText("\t"+"With Tablafy, all users need to do is place their devices near music, press the listen button, than press it again once they are ready to receive their Tab.");

        initData();
        listAdapter = new ExpandableListAdapter(getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        listView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition,long id) {
                if(groupPosition == 0) {
                    if(childPosition == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                        builder.setTitle("Privacy Policy");
                        builder.setIcon(R.mipmap.tablafy_launcher);
                        builder.setMessage(
                                "\n" +
                                "\t" +
                                "The creator of Tablafy built this application as a free, ad-supported mobile app. This service is provided by the creator of Tablafy and is intended for use as is. This page is used to inform users of policies regarding the collection, use, and disclosure of Non-Personal user information. \n" +
                                "\n" +
                                "\t" +
                                "If you choose to use the Service, then you agree to the collection and use of information in relation with this policy. The creator will not use or share this information with anyone except as described in this Privacy Policy. Information Collection and Use. For a better experience while using our Service, the information that the creator requests is will be used as described in this privacy policy." +
                                "\n" +
                                "\n" +
                                "\n" +
                                "Service Providers\n" +
                                "\n" +
                                "The creator may employ third-party companies and individuals due to the following reasons:\n" +
                                "\n" +
                                "To facilitate our Service.\n" +
                                "\n" +
                                "To provide the Service on our behalf.\n" +
                                "\n" +
                                "To assist us in analyzing how our Service is used.\n" +
                                "\n" +
                                "\n" +
                                "\t" +
                                "The creator wants to inform users of this Service that these third parties have access to your Non-Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n" +
                                "\n" +
                                "\n" +
                                "Security\n" +
                                "\n" +
                                "\t" +
                                "The creator values your trust in providing us your Non-Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and the creator cannot guarantee its absolute security.\n" +
                                "\n" +
                                "\n" +
                                "Links to Other Sites\n" +
                                "\n" +
                                "\t" +
                                "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by the creator. Therefore, it is strongly advised that you review the Privacy Policy of these websites. The creator has no control over, and assumes no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n" +
                                "\n" +
                                "\n" +
                                "Changes to This Privacy Policy\n" +
                                "\n" +
                                "\t" +
                                "The creator may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. These changes are effective immediately, after they are posted on this page.\n" +
                                "\n")
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    if(childPosition == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.AlertDialogCustom);
                        builder.setTitle("Disclaimer");
                        builder.setIcon(R.mipmap.tablafy_launcher);
                        builder.setMessage(
                                "\n" +
                                "\t" +
                                "The creator of this application does not explicitly grant use of data generated using this application's services. It is up to the user of this application to determine the scope of allowable usage of data supplied to this application's services. However, the use of this application's services will not alter any copyrights or usage permissions of the supplied data. " +
                                "\n" +
                                "\n" +
                                "\t" +
                                "The creator of this application does not make any warranties about the completeness, reliability and accuracy of the generated data. Any action you take upon the information you find on this application is strictly at your own risk. The creator of this application will not be liable for any losses and/or damages in connection with the use of this application." +
                                "\n" +
                                "\n" +
                                "By using this application, you hereby consent to the disclaimer and agree to it's terms.")
                                .setCancelable(true)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
                return false;
            }
        });

        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new FAQFragment(),("FAQS")).commit();
            }
        });

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new LearnFragment(),("LEARN")).commit();
            }
        });


    }


}