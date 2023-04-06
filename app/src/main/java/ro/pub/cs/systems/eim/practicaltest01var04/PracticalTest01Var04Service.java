package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Var04Service extends Service {
    public PracticalTest01Var04Service() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PracticalTest01Service", "onStartCommand() method was invoked");
        new ProcessingThread(this, intent.getStringExtra(Constants.studentName), intent.getStringExtra(Constants.studentGroup)).start();
        return START_REDELIVER_INTENT;
    }
}