package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Context;
import android.content.Intent;

public class ProcessingThread extends Thread {
    Context context;
    String studentName;
    String studentGroup;

    public ProcessingThread(Context context, String studentName, String studentGroup) {
        this.context = context;
        this.studentName = studentName;
        this.studentGroup = studentGroup;
    }

    @Override
    public void run() {
        while (true) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intentGroup = new Intent();
        intentGroup.setAction(Constants.studentGroupAction);
        intentGroup.putExtra(Constants.studentGroup, studentGroup);
        context.sendBroadcast(intentGroup);
        sleep();

        Intent intentStudent = new Intent();
        intentStudent.setAction(Constants.studentNameAction);
        intentStudent.putExtra(Constants.studentName, studentName);
        context.sendBroadcast(intentStudent);
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
