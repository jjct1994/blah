package com.example.jossu.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.media.AudioPlaybackConfiguration;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class RecordingService
                        implements ActivityCompat.OnRequestPermissionsResultCallback {

    Activity activityUsed;
    Context contextUsed;

    private int requestCode = 1000;
    private Timer myTimer;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private ArrayList<String> trackList = new ArrayList<>();
    private ArrayList<String> listForPlayBack;
    public RecordingService(Activity activity, Context context) {
        this.contextUsed = context;
        this.activityUsed = activity;
    }

    public ArrayList<String> getTrackList() {
        return listForPlayBack;
    }

    public void loopRec() {


        myTimer = new Timer(true);


        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run() {
                                            holdRecordStop();
                                            String trackNum = Long.toString(System.currentTimeMillis());
                                            holdToRecord(trackNum);

                                        }
                                    },
                10000, 10000);
        holdToRecord("1");

    }
    public void loopRecStop(){
        myTimer.cancel();
        myTimer = null;
        holdRecordStop();
        listForPlayBack = trackList;

    }

    public void loopPlay(){

        myTimer = new Timer(true);
        myTimer.scheduleAtFixedRate(new TimerTask() {
                                        @Override
                                        public void run(){

                                            stopPlayBack();
                                            listForPlayBack.remove(0);
                                            playBack();
                                        }
                                    },
                10000, 10000);
        playBack();
    }

    public void loopPlayStop(){

        stopPlayBack();
        myTimer.cancel();
        myTimer = null;
    }

    public void holdToRecord(String trackNum) {


        if (ContextCompat.checkSelfPermission(contextUsed,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(contextUsed, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                prepareRecorder(trackNum);
            }
        } else {
            requestStoragePermissions();
        }

    }

    public void holdRecordStop() {
        if (ContextCompat.checkSelfPermission(contextUsed,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            mediaRecorder.stop();
            mediaRecorder.reset();

        } else {
            requestStoragePermissions();
        }
    }

    public void prepareRecorder(String trackNum) {

        mediaRecorder = new MediaRecorder();
        String pathSave = contextUsed.getFilesDir().getAbsolutePath() + "audio_slice_" + trackNum + ".3gp";
        trackList.add(pathSave);


        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void playBack() {
        mediaPlayer = new MediaPlayer();

        if(listForPlayBack.size() != 0){

            if (ContextCompat.checkSelfPermission(contextUsed,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                try {
                    mediaPlayer.setDataSource(trackList.get(0));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                requestStoragePermissions();
            }
        }
        else
        {
            myTimer.cancel();
            listForPlayBack = trackList;
        }
    }
    public void stopPlayBack(){
            if (ContextCompat.checkSelfPermission(activityUsed,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {


                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                } else {
                    Toast.makeText(contextUsed, "There is no trake saved", Toast.LENGTH_SHORT).show();
                }
            } else {
                requestStoragePermissions();
            }



    }

    public void requestStoragePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activityUsed, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(contextUsed)
                    .setTitle("Permission needed")
                    .setMessage("Permission needed to access")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activityUsed,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.RECORD_AUDIO}, requestCode);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(activityUsed,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO}, requestCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(contextUsed, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(contextUsed, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

}

