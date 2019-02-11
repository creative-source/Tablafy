package com.jantzapps.jantz.tablafy;

import android.util.Log;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by jantz on 9/8/2017.
 */

public class StructureUkulele extends PlayabilityStructure {

    StructureUkulele (String musicProcess, double minRange,double maxRange, ArrayList<Double> notePitches) {
        super (musicProcess, minRange ,maxRange, notePitches);

        playOptimize();

    }

    double [] [] ukuleleFrequencies = {
            {391.92,415.28,440.00,466.24,493.92,523.20,554.40,587.36,622.24,659.20,698.40,740.00,783.84},
            {261.60,277.20,293.68,311.12,329.60,349.20,370.00,391.92,415.28,440.00,466.24,493.92,523.20},
            {329.60,349.20,370.00,391.92,415.28,440.00,466.24,493.92,523.20,554.40,587.36,622.24,659.20},
            {440.00,466.24,493.92,523.20,554.40,587.36,622.24,659.20,698.40,740.00,783.84,830.56,880.00},
    };

    int [] yStringCoordinates = {2,3,4,5};

    int [] fretPositions = {R.drawable.tab_0, R.drawable.tab_1,R.drawable.tab_2,R.drawable.tab_3,
            R.drawable.tab_4, R.drawable.tab_5,R.drawable.tab_6,R.drawable.tab_7,R.drawable.tab_8,
            R.drawable.tab_9, R.drawable.tab_10,R.drawable.tab_11,R.drawable.tab_12};

    int [] strings;
    int [] frets;
    int groupLength = 4;
    int stringCount = 4;
    int fretCount = 12;
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
                    if (this.notePitches.get(i) == ukuleleFrequencies[s][f])
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
