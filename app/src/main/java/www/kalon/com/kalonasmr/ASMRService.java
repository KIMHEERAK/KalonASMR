package www.kalon.com.kalonasmr;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import static www.kalon.com.kalonasmr.MainActivity.mp;

public class ASMRService extends Service {

    boolean isPlaying = false;
    int mpDuration = 0;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 서비스에서 가장 먼저 호출됨(최초에 한번만)
        Log.d("test", "서비스의 onCreate");
        mp = MediaPlayer.create(this, R.raw.rain);
        mp.setVolume(0.0f, 0.0f);
        mp.setLooping(true);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 서비스가 호출될 때마다 실행
        Log.d("test", "서비스의 onStartCommand");
        mpDuration=mp.getDuration();
        mp.start();
        isPlaying = true;
        VolumeOption();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        mp.stop();

        Log.d("test", "서비스의 onDestroy");
    }

    public void VolumeOption() {

        Thread myThread = new Thread(new Runnable() {

            int mil = 1000;
            int durSec = (mpDuration/mil)/20;
            int sleepMilSec = 10;
            int calculateCount = (durSec * mil) / sleepMilSec;
            float min = 0.0f;
            float max = 1.0f;
            float add = max / (float) (calculateCount);

            public void run() {
                while (true) {
                    try {
                        for (float currentVolume = min; currentVolume <= max; currentVolume += add) {
                            if (mp.isPlaying()) {
                                SystemClock.sleep(sleepMilSec);
                                Log.v("SoundService", "LeftToRight-Current :" + currentVolume);
                                mp.setVolume(0.0f, currentVolume);
                            } else {
                                break;
                            }
                        }
                        for (float currentVolume = max; currentVolume >= min; currentVolume -= add) {
                            if (isPlaying) {
                                SystemClock.sleep(sleepMilSec);
                                Log.v("SoundService", "LeftToRight-Current :" + currentVolume);
                                mp.setVolume(0.0f, currentVolume);
                            } else {
                                break;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        });

        myThread.start();
        Log.d("VolumeOption", "working");
    }

}
