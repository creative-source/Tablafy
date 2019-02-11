package com.jantzapps.jantz.tablafy;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.SpectralPeakProcessor;
import be.tarsos.dsp.filters.BandPass;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.writer.WriterProcessor;

/**
 * Created by jantz on 8/18/2017.
 */

public class AnalyzeMusic {
    private static final String TAG = "<< DRIVE >>";
    static AudioRecord alteredRecord = null;
    static AudioDispatcher dispatcher;
    static float freqChange;
    static float tollerance;
    private static final int RECORDER_BPP = 16;
    private static final String AUDIO_RECORDER_FILE = "music_record.wav";
    private static final String AUDIO_RECORDER_TEMP_FILE = "record_temp.raw";
    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static int bufferSize = 1024;
    private static Thread recordingThread = null;
    static boolean isRecording;
    static int running;

    /*
    private static void stopRecording(){
        if(null != alteredRecord) {
            isRecording = false;
            int i = alteredRecord.getState();
            if (i == 1) {
                running = 0;
                alteredRecord.stop();
                alteredRecord.release();
                alteredRecord = null;
                recordingThread = null;
            }
        }
        if(null !=dispatcher){
            isRecording = false;
            running = 0;
            dispatcher.stop();
            recordingThread = null;
        }
        copyWaveFile(AUDIO_RECORDER_TEMP_FILE,AUDIO_RECORDER_FILE);
        deleteTempFile();
    }

    private static void deleteTempFile() {
        File file = new File(AUDIO_RECORDER_TEMP_FILE);
        file.delete();
    }

    private static void copyWaveFile(String inFilename, String outFilename){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = RECORDER_SAMPLERATE;
        int channels = 1;
        long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels/8;
        byte[] data = new byte[bufferSize];
        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);
            while(in.read(data) != -1){
                out.write(data);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R';header[1] = 'I'; header[2] = 'F';header[3] = 'F';// RIFF/WAVE header
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';header[9] = 'A';header[10] = 'V';header[11] = 'E';header[12] = 'f';header[13] = 'm';header[14] = 't';header[15] = ' ';// 'fmt ' chunk
        header[16] = 16;header[17] = 0;header[18] = 0;header[19] = 0;// 4 bytes: size of 'fmt ' chunk
        header[20] = 1;header[21] = 0;header[22] = (byte) channels;header[23] = 0;// format = 1
        header[24] = (byte) (longSampleRate & 0xff);header[25] = (byte) ((longSampleRate >> 8) & 0xff);header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);header[28] = (byte) (byteRate & 0xff);header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff); header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8);header[33] = 0;// block align
        header[34] = RECORDER_BPP;header[35] = 0;header[36] = 'd';header[37] = 'a';header[38] = 't';header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);header[41] = (byte) ((totalAudioLen >> 8) & 0xff);header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);// bits per sample
        out.write(header, 0, 44);
    }

    private static void startRecording() {

        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(RECORDER_SAMPLERATE, bufferSize, 0);
            AudioProcessor p = new BandPass(freqChange, tollerance, RECORDER_SAMPLERATE);
            dispatcher.addAudioProcessor(p);
            isRecording = true;
            // Output
        RandomAccessFile outputFile = null;
        try {
            outputFile = new RandomAccessFile(AUDIO_RECORDER_FILE, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        TarsosDSPAudioFormat outputFormat = new TarsosDSPAudioFormat(44100, 16, 1, true, false);
            WriterProcessor writer = new WriterProcessor(outputFormat, outputFile);
            dispatcher.addAudioProcessor(writer);
            recordingThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dispatcher.run();
                }
            }, "Record_Music Thread");
            recordingThread.start();
    }
*/
    public static void analyzingMusic(String processMethod, Boolean active) {
        final int sampleRate = 44100;
        final int fftsize = 32768 / 2;
        final int overlap = fftsize / 2;//50% overlap
        final SpectralPeakProcessor spectralPeakFollower;

        spectralPeakFollower = new SpectralPeakProcessor(fftsize, overlap, sampleRate);

        /*
        //set the min buffer size in onCreate event
        bufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLERATE,
                RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING)*4;
                */


        /*
        dispatcher.addAudioProcessor(spectralPeakFollower);
        dispatcher.addAudioProcessor(new AudioProcessor() {
            @Override
            public boolean process(AudioEvent audioEvent) {
                float [] magintudes = spectralPeakFollower.getMagnitudes();
                float [] frequencies = spectralPeakFollower.getFrequencyEstimates();
                //do something with the arrays in your code here
                Log.e(TAG,String.valueOf(frequencies));
                return true;
            }

            @Override
            public void processingFinished() {

            }
        });
        */

        int duration;
        ArrayList<Integer> noteTimes = new ArrayList<Integer>();
        ArrayList<Integer> notePitch = new ArrayList<Integer>();

        duration = 2000;
        noteTimes.add(0);
        noteTimes.add(0);
        notePitch.add(50);
        notePitch.add(200);

        final ArrayList<Float> pitches = new ArrayList<Float>();
        final ArrayList<Long> pitchTimes = new ArrayList<Long>();
        pitches.add(0f);
        pitchTimes.add(0L);
        pitches.add(50f);
        pitchTimes.add(20L);
        pitchTimes.add(35L);
        pitchTimes.add(50L);
        pitchTimes.add(65L);
        pitchTimes.add(80L);
        pitchTimes.add(95L);
        pitchTimes.add(110L);
        pitchTimes.add(125L);
        pitchTimes.add(140L);
        pitchTimes.add(155L);
        pitchTimes.add(170L);
        pitchTimes.add(185L);

        final AudioDispatcher dispatcher =
                AudioDispatcherFactory.fromDefaultMicrophone(66150, 16384, 0);

        if (active) {

            //startRecording();

            /*
            PitchDetectionHandler pdh = new PitchDetectionHandler() {

                @Override
                public void handlePitch(PitchDetectionResult res, AudioEvent e) {

                    final float pitchInHz = res.getPitch();

                    if (pitchInHz != -1.0) {
                        Long tempTime = System.currentTimeMillis();
                        pitches.add(pitchInHz);
                        pitchTimes.add(tempTime);

                        Log.e(TAG, String.valueOf(pitchInHz));
                        Log.e(TAG, String.valueOf(tempTime));
                    }

                }
            };
            AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 66150, 16384, pdh);
            dispatcher.addAudioProcessor(pitchProcessor);

            new Thread(dispatcher, "Audio Thread").start();
            */
        }


        if (!active) {

                //stopRecording();
           /*
            try {
                if (!dispatcher.isStopped()) {
                    dispatcher.stop();
                }
            } catch (Exception ex) {

            }
            */

            Long audioDuration = 0L;
            for(int i = 0; i < pitchTimes.size(); i++) {
                audioDuration += pitchTimes.get(i);
            }

            audioDuration /= 7;

            CreateTabs.createGuitarTab(processMethod, audioDuration, pitchTimes, pitches);
            CreateTabs.createPianoTab(processMethod, audioDuration, pitchTimes, pitches);
            CreateTabs.createBassTab(processMethod, audioDuration, pitchTimes, pitches);
            CreateTabs.createHarmonicaTab(processMethod, audioDuration, pitchTimes, pitches);
            CreateTabs.createUkuleleTab(processMethod, audioDuration, pitchTimes, pitches);

        }


    }
}
