/*package com.example.jossu.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioRecord;
import android.media.AudioFormat;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class AudioMixer implements ActivityCompat.OnRequestPermissionsResultCallback {

    Activity activityUsed;
    Context contextUsed;

    private static final int RECORDER_SAMPLERATE = 441000;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    private AudioRecord audioRecorder;



    public AudioMixer(Activity activity, Context context) {
        this.contextUsed = context;
        this.activityUsed = activity;
    }

    public void recordAudio(){

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
*/