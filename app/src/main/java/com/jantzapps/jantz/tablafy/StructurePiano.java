package com.jantzapps.jantz.tablafy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jantz on 9/7/2017.
 */

public class StructurePiano extends PlayabilityStructure {

    StructurePiano (String musicProcess, double minRange, double maxRange, ArrayList<Double> notePitches) {
        super (musicProcess, minRange, maxRange, notePitches);

        position();
    }

    double [] pianoFrequencies = {
            65.40,69.30,73.42,77.78,82.40,87.30,92.50,97.98,103.80,110.00,116.56,123.48,130.80,138.60,146.84,
            155.56,164.80,174.60,185.00,195.96,207.64,220.00,233.12,246.96,261.60,277.20,293.68,311.12,329.60,
            349.20,370.00,391.92,415.28,440.00,466.24,493.92,523.20,554.40,587.36,622.24,659.20,698.40,740.00,
            783.84,830.56,880.00,932.48,987.84
    };

    int [] yOctaveCoordinates = {2,3,4,5};

    int [] notePositions = {R.drawable.tab_a, R.drawable.tab_a_upper,R.drawable.tab_b,R.drawable.tab_c,
            R.drawable.tab_c_upper,R.drawable.tab_d,R.drawable.tab_d_upper,R.drawable.tab_e,
            R.drawable.tab_f, R.drawable.tab_f_upper,R.drawable.tab_g,R.drawable.tab_g_upper};

    int [] octaves;
    int [] notes;
    int outOfRangeNote = R.drawable.tab_out_of_range;
    int outOfRangeOctave = 1;

    void position () {
        octaves =  new int[notePitches.size()];
        notes =  new int[notePitches.size()];

        for(int o = 0; o < this.notePitches.size(); o++) {

            if(this.notePitches.get(o) < minRange || this.notePitches.get(o) > maxRange) {
                octaves[o] = outOfRangeOctave;
                notes[o] = outOfRangeNote;
            }

            if(notePitches.get(o).equals(pianoFrequencies [0]) ||
                    notePitches.get(o).equals(pianoFrequencies [1])
                    || notePitches.get(o).equals(pianoFrequencies [2])
                    || notePitches.get(o).equals(pianoFrequencies [3])
                    || notePitches.get(o).equals(pianoFrequencies [4])
                    || notePitches.get(o).equals(pianoFrequencies [5])
                    || notePitches.get(o).equals(pianoFrequencies [6])
                    || notePitches.get(o).equals(pianoFrequencies [7])
                    || notePitches.get(o).equals(pianoFrequencies [8])
                    || notePitches.get(o).equals(pianoFrequencies [9])
                    || notePitches.get(o).equals(pianoFrequencies [10])
                    || notePitches.get(o).equals(pianoFrequencies [11]))
                octaves [o] = yOctaveCoordinates [0];
            if(notePitches.get(o).equals(pianoFrequencies [12]) ||
                    notePitches.get(o).equals(pianoFrequencies [13])
                    || notePitches.get(o).equals(pianoFrequencies [14])
                    || notePitches.get(o).equals(pianoFrequencies [15])
                    || notePitches.get(o).equals(pianoFrequencies [16])
                    || notePitches.get(o).equals(pianoFrequencies [17])
                    || notePitches.get(o).equals(pianoFrequencies [18])
                    || notePitches.get(o).equals(pianoFrequencies [19])
                    || notePitches.get(o).equals(pianoFrequencies [20])
                    || notePitches.get(o).equals(pianoFrequencies [21])
                    || notePitches.get(o).equals(pianoFrequencies [22])
                    || notePitches.get(o).equals(pianoFrequencies [23]))
                octaves [o] = yOctaveCoordinates [1];
            if(notePitches.get(o).equals(pianoFrequencies [24]) ||
                    notePitches.get(o).equals(pianoFrequencies [25])
                    || notePitches.get(o).equals(pianoFrequencies [26])
                    || notePitches.get(o).equals(pianoFrequencies [27])
                    || notePitches.get(o).equals(pianoFrequencies [28])
                    || notePitches.get(o).equals(pianoFrequencies [29])
                    || notePitches.get(o).equals(pianoFrequencies [30])
                    || notePitches.get(o).equals(pianoFrequencies [31])
                    || notePitches.get(o).equals(pianoFrequencies [32])
                    || notePitches.get(o).equals(pianoFrequencies [33])
                    || notePitches.get(o).equals(pianoFrequencies [34])
                    || notePitches.get(o).equals(pianoFrequencies [35]))
                octaves [o] = yOctaveCoordinates [2];
            if(notePitches.get(o).equals(pianoFrequencies [36]) ||
                    notePitches.get(o).equals(pianoFrequencies [37])
                    || notePitches.get(o).equals(pianoFrequencies [38])
                    || notePitches.get(o).equals(pianoFrequencies [39])
                    || notePitches.get(o).equals(pianoFrequencies [40])
                    || notePitches.get(o).equals(pianoFrequencies [41])
                    || notePitches.get(o).equals(pianoFrequencies [42])
                    || notePitches.get(o).equals(pianoFrequencies [43])
                    || notePitches.get(o).equals(pianoFrequencies [44])
                    || notePitches.get(o).equals(pianoFrequencies [45])
                    || notePitches.get(o).equals(pianoFrequencies [46])
                    || notePitches.get(o).equals(pianoFrequencies [47]))
                octaves [o] = yOctaveCoordinates [3];
            if(notePitches.get(o).equals(pianoFrequencies [8]) ||   // g#
                    notePitches.get(o).equals(pianoFrequencies [20])
                    || notePitches.get(o).equals(pianoFrequencies [32])
                    || notePitches.get(o).equals(pianoFrequencies [44]))
                notes [o] = notePositions [11]; // g#
            if(notePitches.get(o).equals(pianoFrequencies [9]) ||    // a
                    notePitches.get(o).equals(pianoFrequencies [21])
                    || notePitches.get(o).equals(pianoFrequencies [33])
                    || notePitches.get(o).equals(pianoFrequencies [45]))
                notes [o] = notePositions [0]; // a
            if(notePitches.get(o).equals(pianoFrequencies [10]) ||   // a#
                    notePitches.get(o).equals(pianoFrequencies [22])
                    || notePitches.get(o).equals(pianoFrequencies [34])
                    || notePitches.get(o).equals(pianoFrequencies [46]))
                notes [o] = notePositions [1]; // a#
            if(notePitches.get(o).equals(pianoFrequencies [11]) ||  // b
                    notePitches.get(o).equals(pianoFrequencies [23])
                    || notePitches.get(o).equals(pianoFrequencies [35])
                    || notePitches.get(o).equals(pianoFrequencies [47]))
                notes [o] = notePositions [2]; // b
            if(notePitches.get(o).equals(pianoFrequencies [0]) ||   // c
                    notePitches.get(o).equals(pianoFrequencies [12])
                    || notePitches.get(o).equals(pianoFrequencies [24])
                    || notePitches.get(o).equals(pianoFrequencies [36]))
                notes [o] = notePositions [3]; // c
            if(notePitches.get(o).equals(pianoFrequencies [1]) ||   // c#
                    notePitches.get(o).equals(pianoFrequencies [13])
                    || notePitches.get(o).equals(pianoFrequencies [25])
                    || notePitches.get(o).equals(pianoFrequencies [37]))
                notes [o] = notePositions [4]; // c#
            if(notePitches.get(o).equals(pianoFrequencies [2]) ||   // d
                    notePitches.get(o).equals(pianoFrequencies [14])
                    || notePitches.get(o).equals(pianoFrequencies [26])
                    || notePitches.get(o).equals(pianoFrequencies [38]))
                notes [o] = notePositions [5]; // d
            if(notePitches.get(o).equals(pianoFrequencies [3]) ||   // d#
                    notePitches.get(o).equals(pianoFrequencies [15])
                    || notePitches.get(o).equals(pianoFrequencies [27])
                    || notePitches.get(o).equals(pianoFrequencies [39]))
                notes [o] = notePositions [6]; // d#
            if(notePitches.get(o).equals(pianoFrequencies [4]) ||   // e
                    notePitches.get(o).equals(pianoFrequencies [16])
                    || notePitches.get(o).equals(pianoFrequencies [28])
                    || notePitches.get(o).equals(pianoFrequencies [40]))
                notes [o] = notePositions [7]; // e
            if(notePitches.get(o).equals(pianoFrequencies [5]) ||   // f
                    notePitches.get(o).equals(pianoFrequencies [17])
                    || notePitches.get(o).equals(pianoFrequencies [29])
                    || notePitches.get(o).equals(pianoFrequencies [41]))
                notes [o] = notePositions [8]; // f
            if(notePitches.get(o).equals(pianoFrequencies [6]) ||   // f#
                    notePitches.get(o).equals(pianoFrequencies [18])
                    || notePitches.get(o).equals(pianoFrequencies [30])
                    || notePitches.get(o).equals(pianoFrequencies [42]))
                notes [o] = notePositions [9]; // f#
            if(notePitches.get(o).equals(pianoFrequencies [7]) ||   // g
                    notePitches.get(o).equals(pianoFrequencies [19])
                    || notePitches.get(o).equals(pianoFrequencies [31])
                    || notePitches.get(o).equals(pianoFrequencies [43]))
                notes [o] = notePositions [10]; // g

        }

        for(int i = 0; i < notePitches.size(); i++) {                          //debug
            Log.e("DEBUG","this is the new #"+ i +" " + notes[i] + " and " + octaves[i]
            +" for note frequencies: " +notePitches.get(i));
        }
    }
}
