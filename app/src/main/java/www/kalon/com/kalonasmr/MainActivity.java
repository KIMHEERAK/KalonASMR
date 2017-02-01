package www.kalon.com.kalonasmr;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


public class MainActivity extends Activity {

    public static MediaPlayer mp;
//    MediaPlayer mp = new MediaPlayer();
//    public static MediaPlayer mp= new MediaPlayer();

    AudioManager audioManager;
    int maxVolume;
    int currentVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        // admob source
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-5308881903932145/2435339315");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        // real ad request
        // AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) // test ad request
                .build();
        mAdView.loadAd(adRequest);

        // Variables
        ImageView imageViewMicLeft = (ImageView) findViewById(R.id.imageViewMicLeft) ;
        imageViewMicLeft.setImageResource(R.drawable.im_mic);

        ToggleButton toggleButtonRain = (ToggleButton) findViewById(R.id.toggleButtonRain);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int)currentVolume,0);


        // Choice Music
        toggleButtonRain.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // if toggle button is enabled/on


                    Intent intent = new Intent(getApplicationContext(), ASMRService.class);
                    startService(intent);


                    // Make a toast to display toggle button status
                    Toast.makeText(getApplicationContext(), "Toggle is on", Toast.LENGTH_SHORT).show();
                }
                else{
                    // If toggle button is disabled/off
                    Intent intent = new Intent(getApplicationContext(), ASMRService.class);
                    stopService(intent);

                    // Make a toast to display toggle button status
                    Toast.makeText(getApplicationContext(), "Toggle is off", Toast.LENGTH_SHORT).show();
                }
            }
        });


    } // onCreate

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // STREAM_MUSIC conroller
        SeekBar sb;
        sb= (SeekBar) findViewById(R.id.seekBar);
        sb.setProgress(currentVolume);
        sb.setMax(maxVolume);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,audioManager.FLAG_SHOW_UI);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(getApplicationContext(), ASMRService.class);
        stopService(intent);

    }




}

