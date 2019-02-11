package com.jantzapps.jantz.tablafy;

import android.util.Log;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by jantz on 9/8/2017.
 */

public class StructureBass extends PlayabilityStructure {

    StructureBass (String musicProcess, double minRange,double maxRange, ArrayList<Double> notePitches) {
        super (musicProcess, minRange ,maxRange, notePitches);

        playOptimize();

    }

    double [] [] bassFrequencies = {
            {41.20,43.65,46.25,48.99,51.91,55.00,58.28,61.74,65.40,69.30,73.42,77.78,82.40,87.30,92.50,97.98,103.80,110.00,116.56,123.48,130.80},
            {55.00,58.28,61.74,65.40,69.30,73.42,77.78,82.40,87.30,92.50,97.98,103.80,110.00,116.56,123.48,130.81,138.60,146.84,155.56,164.80,174.60},
            {73.42,77.78,82.40,87.30,92.50,97.98,103.80,110.00,116.56,123.48,130.81,138.60,146.84,155.56,164.80,174.60,185.00,195.96,207.64,220.00,233.12},
            {98,103.80,110.00,116.56,123.48,130.81,138.60,146.84,155.56,164.80,174.60,185.00,195.96,207.64,220.00,233.12,246.96,261.60,277.20,293.68,311.12},
    };

    int [] yStringCoordinates = {2,3,4,5};

    int [] fretPositions = {R.drawable.tab_0, R.drawable.tab_1,R.drawable.tab_2,R.drawable.tab_3,
            R.drawable.tab_4, R.drawable.tab_5,R.drawable.tab_6,R.drawable.tab_7,R.drawable.tab_8,
            R.drawable.tab_9, R.drawable.tab_10,R.drawable.tab_11,R.drawable.tab_12,R.drawable.tab_13,
            R.drawable.tab_14, R.drawable.tab_15,R.drawable.tab_16,R.drawable.tab_17,R.drawable.tab_18,
            R.drawable.tab_19, R.drawable.tab_20};

    int [] strings;
    int [] frets;
    int groupLength = 4;
    int stringCount = 4;
    int fretCount = 20;
    int outOfRangeFret = R.drawable.tab_out_of_range;
    int outOfRangeString = 1;

    private static final String TAG = "<< DEBUG >>";

    ArrayList<Integer> fretGap = new ArrayList<>();  // ARRAY USED TO CHECK THE "FRET SPREAD" OF THE NOTE GROUP

    // INSTRUCTIONS FOR OPTIMIZING THE INSTRUMENT FRETTING
    void playOptimize () {
        strings =  new int[notePitches.size()];
        frets =  new int[notePitches.size()];

        for (int i = 0; i < this.notePitches.size(); i++) {

            int fretDifference = fretCount;

            notes:
            for (int s = 0; s < stringCount; s++) {

                if(notePitches.get(i) < minRange || notePitches.get(i) > maxRange) {
                    strings [i] = outOfRangeString;
                    frets [i] = outOfRangeFret;
                    break;
                }

                strings:
                for (int f = 0; f < fretCount; f++) {
                    if (this.notePitches.get(i) == bassFrequencies[s][f])
                    {
                        if(i == 0) {
                            fretGap.add(f);
                            strings[i] = s;
                            frets[i] = f;

                            break notes;                        //add label to begin at next notes loop
                        }
                        if(i > 0) {
                            if (abs(frets[i - 1] - f) < fretDifference) {
                                fretDifference = abs(frets[i - 1] - f);
                                strings[i] = s;
                                frets[i] = f;

                                if(checkFGroups(f)) {
                                    fretGap.add(f);
                                    strings[i] = s;
                                    frets[i] = f;

                                    break notes;                //add label to begin at next notes loop
                                }

                                break;                          //add label to begin at next strings loop
                            }
                        }

                    }
                    if(s == (stringCount - 1) & f == (fretCount - 1)) {
                        if(!checkFGroups(i)) {
                            fretGap.clear();
                            fretGap.add(frets[i]);
                        }
                    }

                }
            }

        }

        for(int o = 0; o < this.notePitches.size(); o++) {                          //debug
            Log.e(TAG,"This is the #"+ o +" note. Frequency "+notePitches.get(o)+" .\n Play the " + (strings[o] + 1) +" string at the "+ frets[o]+" fret.\n\n");
        }

        // SETS THE ARRAY INDEX OF THE STRING NUMBERS AND FRET POSITIONS TO THE ACTUAL CORRESPONDING DATA.
        for(int o = 0; o < this.notePitches.size(); o++) {
            if(strings[o] != outOfRangeString) {
                strings[o] = yStringCoordinates[strings[o]];
                frets[o] = fretPositions[frets[o]];
            }
        }

        for(int o = 0; o < this.notePitches.size(); o++) {                          //debug
            Log.e(TAG,"This is the #"+ o +" note. Frequency "+notePitches.get(o)+" .\n Play the " + (strings[o] + 1) +" string at the "+ frets[o]+" fret.\n\n");
        }

    }

    // CHECKS TO SEE IF THE NOTE IS ABLE TO BE GROUPED BASED ON IT'S ASSOCIATED FRET NUMBER
    public boolean checkFGroups (int fret) {
        boolean check = false;
        for (int i = 0; i < fretGap.size(); i++) {
            if(abs(fret - fretGap.get(i)) > groupLength) {
                check = false;
                break;
            }
            else check = true;
        }
        return check;
    }


}
