package com.jantzapps.jantz.tablafy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FAQFragment extends Fragment {

    public FAQFragment() {
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
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView musicStand = (TextView) view.findViewById(R.id.tvMusicStand);
        TextView differentTab = (TextView) view.findViewById(R.id.tvDifferentTab);
        TextView tabFiles = (TextView) view.findViewById(R.id.tvTabFiles);
        musicStand.setText("\t" + "The music stand is where a user's new and saved tabs are viewed from. Users can swipe vertically to change instruments and horizontally to move forward or backward in a piece of music. Users can also tap the music stand to show and hide the tab type and save button.");
        differentTab.setText("\t" + "Noticing different tab is common while using Tablafy. Tablafy generates tab by recording audio, Therefore, changes in recording conditions can result in a different tablature outcome.");
        tabFiles.setText("\t" + "Tablafy tab is saved and read in a way that does not allow for files to be usable outside of the Tablafy application.");

    }
}
