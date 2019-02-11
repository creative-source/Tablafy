package com.jantzapps.jantz.tablafy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class LearnFragment extends Fragment {

    public LearnFragment() {
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
        return inflater.inflate(R.layout.fragment_learn, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final FragmentManager fm = getActivity().getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();

        TextView guitar = (TextView) view.findViewById(R.id.btnGuitar);
        TextView piano = (TextView) view.findViewById(R.id.btnPiano);
        TextView bass = (TextView) view.findViewById(R.id.btnBass);
        TextView ukulele = (TextView) view.findViewById(R.id.btnUkulele);
        TextView harmonica = (TextView) view.findViewById(R.id.btnHarmonica);

        TextView passage1 = (TextView) view.findViewById(R.id.tvPassage1);
        TextView passage2 = (TextView) view.findViewById(R.id.tvPassage2);
        TextView passage3 = (TextView) view.findViewById(R.id.tvPassage3);

        passage1.setText("\t"+"Tablafy represents all tab as horizontal lines with circular note indicators along each line.");
        passage2.setText("\t"+"Additionally, above all the tab in Tablafy there are time markers; One for each second and one after each thirty second interval.");
        passage3.setText("\t"+"Lastly, Tablafy represents notes that are out of an instruments range by empty note indicators below the lines of tab.");

        guitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new GuitarFragment(),("GUITAR")).commit();
            }
        });
        piano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new PianoFragment(),("PIANO")).commit();
            }
        });
        bass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new BassFragment(),("BASS")).commit();
            }
        });
        ukulele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new UkuleleFragment(),("UKULELE")).commit();
            }
        });
        harmonica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTransaction.replace(R.id.containerView, new HarmonicaFragment(),("HARMONICA")).commit();
            }
        });
    }
}
