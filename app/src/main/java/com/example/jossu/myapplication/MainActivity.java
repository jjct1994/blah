
package com.example.jossu.myapplication;


        import android.annotation.SuppressLint;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

        import android.view.MotionEvent;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;


        import android.widget.ImageButton;
        import android.widget.ListView;

        import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    Button playButton;
    ImageButton recordButton;





    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.PlayButton);
        recordButton = findViewById(R.id.RecordingButton);



        final RecordingService recordingService = new RecordingService(MainActivity.this, getBaseContext());


        recordButton.setOnTouchListener(new View.OnTouchListener(){


            @Override
            public boolean onTouch (View v, MotionEvent event){


                if(event.getAction() == MotionEvent.ACTION_DOWN) {


                    recordingService.loopRec();


                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    recordingService.loopRecStop();

                }

                return true;


            }


        });

        playButton.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event){

                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    recordingService.loopPlay();
                }else if(event.getAction()== MotionEvent.ACTION_UP){
                    recordingService.loopPlayStop();
                }

                return true;

            }


        });
    }




}


