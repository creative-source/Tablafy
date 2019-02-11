package com.jantzapps.jantz.tablafy;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jantz on 9/8/2017.
 */

public class StructureHarmonica extends PlayabilityStructure {

    StructureHarmonica (String musicProcess, double minRange,double maxRange, ArrayList<Double> notePitches) {
        super (musicProcess, minRange ,maxRange, notePitches);

        position();
    }

    double [] harmonicaFrequencies = {261.60,277.20,293.68/*,311.12*/,329.60,349.20,370.00,391.92,415.28,440.00,
            466.24,493.92,523.20,554.40,587.36/*,622.24*/,659.20,698.40/*,740.00*/,783.84,830.56,880.00/*,932.48*/,987.84,
            1046.4/*,1108.80*/,1174.72,1244.48,1318.40,1396.80,1480.00,1567.68/*,1661.12*/,1760.00,1864.96,1975.68,2092.80
    };

    int [] yBlowOrDrawCoordinates = {3,4};

    int [] holePositions = {R.drawable.tab_1,R.drawable.tab_2,R.drawable.tab_3,
            R.drawable.tab_4, R.drawable.tab_5,R.drawable.tab_6,R.drawable.tab_7,R.drawable.tab_8,
            R.drawable.tab_9, R.drawable.tab_10};

    int [] blowsDraws;
    int [] holes;

    int outOfRangeBD = 2;
    int outOfRangeHole = R.drawable.tab_base;

    void position () {

        if(this.notePitches.contains(311.12) || this.notePitches.contains(622.24) ||
                this.notePitches.contains(740.00) || this.notePitches.contains(932.48) ||
                this.notePitches.contains(1108.80) || this.notePitches.contains(1661.12)) {

            NoNoteFix();
        }

        blowsDraws =  new int[notePitches.size()];
        holes =  new int[notePitches.size()];

        for(int o = 0; o < this.notePitches.size(); o++) {
            if(this.notePitches.get(o) < minRange || this.notePitches.get(o) > maxRange) {

                blowsDraws[o] = outOfRangeBD;
                holes[o] = outOfRangeHole;
            }

            if(this.notePitches.get(o) == 311.12 || this.notePitches.get(o) == 622.24 ||
                    this.notePitches.get(o) == 740.00 || this.notePitches.get(o) == 932.48 ||
                    this.notePitches.get(o) == 1108.80 || this.notePitches.get(o) == 1661.12) {

                    blowsDraws[o] = outOfRangeBD;
                    holes[o] = outOfRangeHole;
            }

            if(notePitches.get(o).equals(harmonicaFrequencies [0]) ||
                    notePitches.get(o).equals(harmonicaFrequencies [3])
                    || notePitches.get(o).equals(harmonicaFrequencies [6])
                    || notePitches.get(o).equals(harmonicaFrequencies [11])
                    || notePitches.get(o).equals(harmonicaFrequencies [14])
                    || notePitches.get(o).equals(harmonicaFrequencies [16])
                    || notePitches.get(o).equals(harmonicaFrequencies [20])
                    || notePitches.get(o).equals(harmonicaFrequencies [22])
                    || notePitches.get(o).equals(harmonicaFrequencies [23])
                    || notePitches.get(o).equals(harmonicaFrequencies [25])
                    || notePitches.get(o).equals(harmonicaFrequencies [26])
                    || notePitches.get(o).equals(harmonicaFrequencies [28])
                    || notePitches.get(o).equals(harmonicaFrequencies [29])
                    || notePitches.get(o).equals(harmonicaFrequencies [30]))
                blowsDraws [o] = yBlowOrDrawCoordinates [1];
            if(notePitches.get(o).equals(harmonicaFrequencies [1]) ||
                    notePitches.get(o).equals(harmonicaFrequencies [2])
                    || notePitches.get(o).equals(harmonicaFrequencies [5])
                    || notePitches.get(o).equals(harmonicaFrequencies [7])
                    || notePitches.get(o).equals(harmonicaFrequencies [8])
                    || notePitches.get(o).equals(harmonicaFrequencies [9])
                    || notePitches.get(o).equals(harmonicaFrequencies [10])
                    || notePitches.get(o).equals(harmonicaFrequencies [11])
                    || notePitches.get(o).equals(harmonicaFrequencies [12])
                    || notePitches.get(o).equals(harmonicaFrequencies [13])
                    || notePitches.get(o).equals(harmonicaFrequencies [15])
                    || notePitches.get(o).equals(harmonicaFrequencies [17])
                    || notePitches.get(o).equals(harmonicaFrequencies [18])
                    || notePitches.get(o).equals(harmonicaFrequencies [19])
                    || notePitches.get(o).equals(harmonicaFrequencies [21])
                    || notePitches.get(o).equals(harmonicaFrequencies [24])
                    || notePitches.get(o).equals(harmonicaFrequencies [27]))
                blowsDraws [o] = yBlowOrDrawCoordinates [0];
            if(notePitches.get(o).equals(harmonicaFrequencies [0]) || notePitches.get(o).equals(harmonicaFrequencies [1])
                    || notePitches.get(o).equals(harmonicaFrequencies [2]))
                holes [o] = holePositions [0];
                    if(notePitches.get(o).equals(harmonicaFrequencies [3]) || notePitches.get(o).equals(harmonicaFrequencies [4])
                            || notePitches.get(o).equals(harmonicaFrequencies [5]))
                        holes [o] = holePositions [1];
                    if(notePitches.get(o).equals(harmonicaFrequencies [6]) || notePitches.get(o).equals(harmonicaFrequencies [7])
                            || notePitches.get(o).equals(harmonicaFrequencies [8]) || notePitches.get(o).equals(harmonicaFrequencies [9])
                            || notePitches.get(o).equals(harmonicaFrequencies [10]))
                        holes [o] = holePositions [2];
                    if(notePitches.get(o).equals(harmonicaFrequencies [11]) || notePitches.get(o).equals(harmonicaFrequencies [12])
                            || notePitches.get(o).equals(harmonicaFrequencies [13]))
                        holes [o] = holePositions [3];
                    if(notePitches.get(o).equals(harmonicaFrequencies [14]) || notePitches.get(o).equals(harmonicaFrequencies [15]))
                        holes [o] = holePositions [4];
                    if(notePitches.get(o).equals(harmonicaFrequencies [16]) || notePitches.get(o).equals(harmonicaFrequencies [17])
                            || notePitches.get(o).equals(harmonicaFrequencies [18]))
                        holes [o] = holePositions [5];
                    if(notePitches.get(o).equals(harmonicaFrequencies [19]) || notePitches.get(o).equals(harmonicaFrequencies [20]))
                        holes [o] = holePositions [6];
                    if(notePitches.get(o).equals(harmonicaFrequencies [21]) || notePitches.get(o).equals(harmonicaFrequencies [22])
                            || notePitches.get(o).equals(harmonicaFrequencies [23]))
                        holes [o] = holePositions [7];
                    if(notePitches.get(o).equals(harmonicaFrequencies [24]) || notePitches.get(o).equals(harmonicaFrequencies [25])
                            || notePitches.get(o).equals(harmonicaFrequencies [26]))
                        holes [o] = holePositions [8];
                    if(notePitches.get(o).equals(harmonicaFrequencies [27]) || notePitches.get(o).equals(harmonicaFrequencies [28])
                            || notePitches.get(o).equals(harmonicaFrequencies [29]) || notePitches.get(o).equals(harmonicaFrequencies [30]))
                        holes [o] = holePositions [9];

        }
    }

    void NoNoteFix () {                     // ATTEMPTS TO REMOVE NOTES THAT ARE UNPLAYABLE ON THE HARMONICA
        ArrayList<Double> tempNotes = new ArrayList<>();
        tempNotes.addAll(notePitches);

            while (tempNotes.contains(311.12) || tempNotes.contains(622.24) ||
                    tempNotes.contains(740.00) || tempNotes.contains(932.48) ||
                    tempNotes.contains(1108.80) || tempNotes.contains(1661.12)) {
                for (int o = 0; o < this.notePitches.size(); o++) {
                    tempNotes.set(o, this.notePitches.get(o) * 2);
                }

                if (getInRangeCount(tempNotes) > inRangeCount) {
                    this.notePitches.clear();
                    this.notePitches.addAll(tempNotes);
                }
                if (getInRangeCount(tempNotes) == notePitches.size()) {
                    this.notePitches.clear();
                    this.notePitches.addAll(tempNotes);
                    break;
                }
            }

        for(int i = 0; i < tempNotes.size(); i++) {                          //debug
            Log.e("DEBUG","this is the new #"+ i +" pitch from noNoteFix"+ " " + notePitches.get(i));
        }

            while (tempNotes.contains(311.12) || tempNotes.contains(622.24) ||
                    tempNotes.contains(740.00) || tempNotes.contains(932.48) ||
                    tempNotes.contains(1108.80) || tempNotes.contains(1661.12)) {
                for (int o = 0; o < this.notePitches.size(); o++) {
                    tempNotes.set(o,this.notePitches.get(o) / 2);
                }

                if (getInRangeCount(tempNotes) > inRangeCount) {
                    this.notePitches.clear();
                    this.notePitches.addAll(tempNotes);
                }
                if (getInRangeCount(tempNotes) == notePitches.size()) {
                    this.notePitches.clear();
                    this.notePitches.addAll(tempNotes);
                    break;
                }
            }

        for(int i = 0; i < tempNotes.size(); i++) {                          //debug
            Log.e("DEBUG","this is the new #"+ i +" pitch from noNoteFix"+ " " + notePitches.get(i));
        }

        if(musicProcess.equals("KeyOct")) {

                while (this.notePitches.contains(311.12) || this.notePitches.contains(622.24) ||
                        this.notePitches.contains(740.00) || this.notePitches.contains(932.48) ||
                        this.notePitches.contains(1108.80) || this.notePitches.contains(1661.12)) {
                    for (int o = 0; o < this.notePitches.size(); o++) {
                        for (int p = 0; p < fullFreqRange.length - 1; p++) {
                            if (this.notePitches.get(o) == fullFreqRange[p]) {
                                tempNotes.set(o,fullFreqRange[p + 1]);
                            }
                        }
                    }
                    if (getInRangeCount(tempNotes) > inRangeCount) {
                        this.notePitches.clear();
                        this.notePitches.addAll(tempNotes);
                    }
                    if (getInRangeCount(tempNotes) == notePitches.size()) {
                        this.notePitches.clear();
                        this.notePitches.addAll(tempNotes);
                        break;
                    }
            }


                while (this.notePitches.contains(311.12) || this.notePitches.contains(622.24) ||
                        this.notePitches.contains(740.00) || this.notePitches.contains(932.48) ||
                        this.notePitches.contains(1108.80) || this.notePitches.contains(1661.12)) {
                    for (int o = 0; o < this.notePitches.size(); o++) {
                        for (int p = 0; p < fullFreqRange.length; p++) {
                            if (this.notePitches.get(o) == fullFreqRange[p]) {
                                if (p != 0)
                                    tempNotes.set(o,fullFreqRange[p - 1]);
                            }
                        }
                    }
                    if (getInRangeCount(tempNotes) > inRangeCount) {
                        this.notePitches.clear();
                        this.notePitches.addAll(tempNotes);
                    }
                    if (getInRangeCount(tempNotes) == notePitches.size()) {
                        this.notePitches.clear();
                        this.notePitches.addAll(tempNotes);
                        break;
                    }
                }

        }
    }




}
