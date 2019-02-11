package com.jantzapps.jantz.tablafy;

import android.util.Log;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by jantz on 9/7/2017.
 */

public class PlayabilityStructure {

    boolean lowRange;
    boolean highRange;
    boolean inRange;
    ArrayList<Double> notePitches = new  ArrayList<>();
    double minRange, maxRange;
    String musicProcess;
    private static final String TAG = "<< DEBUG >>";
    int inRangeCount;

    //FULL LIST OF RECOGNIZABLE FREQUENCIES
    double [] fullFreqRange = {
            27.50,29.14,30.87,32.70,34.65,36.71,38.89,41.20,43.65,46.25,48.99,51.91,55.00,58.28,61.74,65.40,69.30,
            73.42,77.78,82.40,87.30,92.50,97.98,103.80,110.00,116.56,123.48,130.80,138.60,146.84,155.56,164.80,174.60,
            185.00,195.96,207.64,220.00,233.12,246.96,261.60,277.20,293.68,311.12,329.60,349.20,370.00,391.92,415.28,440.00,
            466.24,493.92,523.20,554.40,587.36,622.24,659.20,698.40,740.00,783.84,830.56,880.00,932.48,987.84,1046.4,1108.80,
            1174.72,1244.48,1318.40,1396.80,1480.00,1567.68,1661.12,1760.00,1864.96,1975.68,2092.80,2217.60,2349.44,2488.96,
            2636.80,2793.60,2960.00,3135.36,3322.24,3520.00,3729.92,3951.36,4185.60
    };

    //CONSTRUCTOR USED BY EACH INSTRUMENT STRUCTURE CLASS
    PlayabilityStructure(String musicProcess, double minRange, double maxRange, ArrayList<Double> notePitches) {
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.musicProcess = musicProcess;

        checkFNRange(roundFrequencies(notePitches));
    }

    //CHECKS IF THE FREQUENCIES PASSED IN ARE IN THE INSTRUMENTS RANGE
    //ALSO CHECKS IF OCTAVE LOWERING OR RAISING (Octave Optimization) WILL BRING FREQUENCIES INTO INSTRUMENTS RANGE
    public void checkFNRange (ArrayList<Double> notePitches) {
        double minPitch, maxPitch;
        minPitch = maxPitch = notePitches.get(0);
        for(int i = 0; i < notePitches.size(); i++) {                                //debug
            Log.e(TAG,"this is the #"+ i +" pitch"+ " " + notePitches.get(i));       //debug
        }
        for(int i = 0; i < notePitches.size(); i++) {
            if(notePitches.get(i) < minPitch) minPitch = notePitches.get(i);
            if(notePitches.get(i) > maxPitch) maxPitch = notePitches.get(i);
        }
        Log.e(TAG,"this is max pitch"+ " " + maxPitch);                              //debug
        Log.e(TAG,"this is min pitch"+ " " + minPitch);                              //debug

        inRangeCount = getInRangeCount(notePitches);

        //CHECK WHICH PITCHES ARE IN RANGE
        if(minPitch >= minRange) {
            Log.e(TAG, "minPitch is equal or greater than " + minRange);             //debug
            lowRange = true;
        }
        else {
            lowRange = false;
            Log.e(TAG, "minPitch is lesser than " + minRange);                       //debug
                tryOctaveUp(minPitch, maxPitch, notePitches);
        }

        if(maxPitch <= maxRange) {
            highRange = true;
            Log.e(TAG, "maxPitch is equal or lesser than " + maxRange);              //debug
        }
        else {
            highRange = false;
            Log.e(TAG, "maxPitch is greater than " + maxRange);                      //debug
                tryOctaveDown(minPitch, maxPitch, notePitches);
        }

        if(highRange & lowRange) {
            inRange = true;
            this.notePitches = notePitches;
        }
        else inRange = false;
    }

    // AUTOMATIC (Octave Optimization pt1)
    // ALSO CHECKS THE USER'S MUSIC PROCESSING PREFERENCE.
    // IF (Key and Octave Optimization) IS SET, KEY OPTIMIZATION BEGINS NEXT.
    public void tryOctaveUp (double minPitch, double maxPitch, ArrayList<Double> notePitches) {
        ArrayList<Double> newNotePitches = new ArrayList<>();
        newNotePitches.addAll(notePitches);
        double newMinPitch = minPitch;
        double newMaxPitch = maxPitch;

        do {
        for(int i = 0; i < newNotePitches.size(); i++) {
                newNotePitches.set(i, newNotePitches.get(i) * 2);
        }
        newMinPitch = newMinPitch * 2;
            newMaxPitch = newMaxPitch * 2;

            if(newMaxPitch <= maxRange & newMinPitch >= minRange) {
                inRange = true;
                inRangeCount = newNotePitches.size();
                this.notePitches = newNotePitches;
                break;
            }
            if(inRangeCount < getInRangeCount(newNotePitches)){
                inRangeCount = getInRangeCount(newNotePitches);
                this.notePitches = newNotePitches;
            }
        } while (newMinPitch <= minRange);

        if(inRangeCount == getInRangeCount(notePitches)){
            this.notePitches = notePitches;
        }

        for(int i = 0; i < this.notePitches.size(); i++) {                          //debug
            Log.e(TAG,"this is the new #"+ i +" pitch from tryUp"+ " " + this.notePitches.get(i));
        }

        if(musicProcess.equals("KeyOct") & (inRangeCount != newNotePitches.size()))
            keyOctaveOptimize("up",minPitch,maxPitch,notePitches);

    }

    // AUTOMATIC (Octave Optimization pt2)
    // ALSO CHECKS THE USER'S MUSIC PROCESSING PREFERENCE.
    // IF (Key and Octave Optimization) IS SET, KEY OPTIMIZATION BEGINS NEXT.
    public void tryOctaveDown (double minPitch, double maxPitch, ArrayList<Double> notePitches) {
        ArrayList<Double> newNotePitches = new ArrayList<>();
        newNotePitches.addAll(notePitches);
        double newMinPitch = minPitch;
        double newMaxPitch = maxPitch;
        //int inRangeCount = getInRangeCount(newNotePitches);
        do {
        for(int i = 0; i < newNotePitches.size(); i++) {
            newNotePitches.set(i,newNotePitches.get(i) / 2);
        }
            newMinPitch = newMinPitch / 2;
            newMaxPitch = newMaxPitch / 2;

            if(newMaxPitch <= maxRange & newMinPitch >= minRange) {
                inRange = true;
                inRangeCount = newNotePitches.size();
                this.notePitches = newNotePitches;
                break;
            }

            if(inRangeCount < getInRangeCount(newNotePitches)){
                inRangeCount = getInRangeCount(newNotePitches);
                this.notePitches = newNotePitches;
            }
        } while (newMaxPitch >= maxRange);

        if(inRangeCount == getInRangeCount(notePitches)){
            this.notePitches = notePitches;
        }

        for(int i = 0; i < this.notePitches.size(); i++) {                          //debug
            Log.e(TAG,"this is the new #"+ i +" pitch from tryDown"+ " " + this.notePitches.get(i));
        }

        if(musicProcess.equals("KeyOct") & (inRangeCount != newNotePitches.size()))
            keyOctaveOptimize("down",minPitch,maxPitch,notePitches);
    }

    // OPTIONAL (Key and Octave Optimization)
    public void keyOctaveOptimize(String direction,double minPitch, double maxPitch,ArrayList<Double> notePitches) {
        ArrayList<Double> newNotePitches = new ArrayList<>();
        newNotePitches.addAll(notePitches);
        //int inRangeCount = getInRangeCount(newNotePitches);
        double newMinPitch = minPitch;
        double newMaxPitch = maxPitch;
        do {
            for (int i = 0; i < newNotePitches.size(); i++) {
                for (int o = 0; o < fullFreqRange.length; o++) {
                    if (newNotePitches.get(i) == fullFreqRange[o]) {
                        if (direction.equals("up")) {
                            if(o != (fullFreqRange.length - 1)) {
                            newNotePitches.set(i, fullFreqRange[o + 1]);
                                if (newMinPitch == fullFreqRange[o]) {
                                        newMinPitch = fullFreqRange[o + 1];
                                }
                                if (newMaxPitch == fullFreqRange[o]) {
                                            newMaxPitch = fullFreqRange[o + 1];
                                }
                            }
                            break;
                        }
                        if (direction.equals("down")) {
                            if(o != 0) {
                                newNotePitches.set(i, fullFreqRange[o - 1]);
                                if (newMinPitch == fullFreqRange[o]) {
                                            newMinPitch = fullFreqRange[o - 1];
                                }
                                if (newMaxPitch == fullFreqRange[o]) {
                                            newMaxPitch = fullFreqRange[o - 1];
                                }
                            }
                            break;
                        }
                    }
                }
            }
            if(inRangeCount < getInRangeCount(newNotePitches)){
                inRangeCount = getInRangeCount(newNotePitches);
                this.notePitches = newNotePitches;
            }
            if (direction.equals("up")){
                if (newNotePitches.contains(newMinPitch) & newNotePitches.size() == inRangeCount) {
                    this.notePitches = newNotePitches;
                    break;
                }
                if(newMinPitch == minRange)
                    //this.notePitches = notePitches;
                    break;
            }
            if (direction.equals("down")){
                if (newNotePitches.contains(newMaxPitch) & newNotePitches.size() == inRangeCount) {
                    this.notePitches = newNotePitches;
                    break;
                }
                if(newMaxPitch == maxRange)
                    //this.notePitches = notePitches;
                    break;
            }
        } while (newNotePitches.size() != inRangeCount);

        for(int i = 0; i < this.notePitches.size(); i++) {                          //debug
            Log.e(TAG,"this is the new #"+ i +" pitch from keyOctaveOptimize"+ " " + this.notePitches.get(i));
        }
    }

    // SET AUDIO FILE FREQUENCIES TO CLOSEST DEFINITION FREQUENCIES
    private ArrayList<Double> roundFrequencies(ArrayList<Double> notePitches) {
        ArrayList<Double> newNotePitches = new ArrayList<>();

        for (int i = 0; i < notePitches.size(); i++) {
            for (int o = 0; o < (fullFreqRange.length - 1); o++) {

                if (abs(notePitches.get(i) - fullFreqRange[o]) > abs(notePitches.get(i) - fullFreqRange[o+1])) {

                    if(abs(notePitches.get(i) - fullFreqRange[o+1]) == 0) {
                        try{
                            newNotePitches.set(i,fullFreqRange[o+1]);
                        }
                        catch (Exception e) {
                            newNotePitches.add(i,fullFreqRange[o+1]);
                        }

                        break;
                    }
                    try{
                        newNotePitches.set(i,fullFreqRange[o+1]);
                    }
                    catch (Exception e) {
                        newNotePitches.add(i,fullFreqRange[o+1]);
                    }

                }
               // else newNotePitches.set(i,fullFreqRange[o]);
            }

        }
        return newNotePitches;
    }

    // DETERMINE THE NUMBER OF IN RANGE FREQUENCIES INCLUDING RECURRING
    public int getInRangeCount (ArrayList<Double> newNotePitches) {
        int inRangeCount = 0;
        for(int i = 0; i < newNotePitches.size(); i++) {
            if(newNotePitches.get(i) >= minRange & newNotePitches.get(i) <= maxRange)
                inRangeCount += 1;
            if(minRange == 261.60 || maxRange == 2092.80)              // ****HARMONICA SPECIFIC NOTE WORKAROUND****
            if (newNotePitches.get(i) == 311.12 || newNotePitches.get(i) == 622.24 ||
                    newNotePitches.get(i) == 740.00 || newNotePitches.get(i) == 932.48 ||
                    newNotePitches.get(i) == 1108.80 || newNotePitches.get(i) == 1661.12) {
                inRangeCount -= 1;
                Log.e(TAG,"this is the Harmonica's inRangeCount"+ " " + inRangeCount);        //debug
            }
        }
        return inRangeCount;
    }

}
