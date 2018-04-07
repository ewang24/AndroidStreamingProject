package com.example.evan.seniorproject;

import android.content.*;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.evan.seniorproject.stream.ByteDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Evan on 4/6/2018.
 */

public class ConnectionManager {

    public static volatile ArrayList<Byte> data = new ArrayList<Byte>();
    ConnectionManagerAsyncTask task;
    ByteDataSource source;
    MediaPlayer mpC;
    boolean started=false;

    public static volatile int segmentsPlayed = 0;
//    AtomicInteger segPlayed;


    public static volatile int segmentsReceived = 0;


    public ConnectionManager(MainActivity context){
        task = new ConnectionManagerAsyncTask(context,data,this);

    }

    public void initialize()
    {
        source = new ByteDataSource(data,this);
    }

    public int getSegmentsPlayed()
    {
        return segmentsPlayed;
    }

    public static int getSegmentsReceived() {
        return segmentsReceived;
    }

    public static void setSegmentsPlayed(int segmentsPlayed) {
        ConnectionManager.segmentsPlayed = segmentsPlayed;
    }

    public static void setSegmentsReceived(int segmentsReceived) {
        ConnectionManager.segmentsReceived = segmentsReceived;
    }

    public void incrementSegmentsReceived()
    {
        segmentsReceived++;
        Log.i("segments",segmentsReceived+"");
        if(!started && segmentsReceived>20)
        {
            started = true;
            start();
        }
//        else if(segmentsReceived-segmentsPlayed>20 && !mpC.isPlaying() && mpC.getCurrentPosition()>1)
//        {
//            mpC.start();
//        }
    }

    public void          incrementSegmentsPlayed()
    {
        segmentsPlayed++;
//        if(segmentsPlayed == segmentsReceived && mpC!= null && mpC.isPlaying())
//        {
//            mpC.pause();
//        }
    }


    public static void test(){

    }

    public void connect(String ip)
    {
        segmentsPlayed=0;
        segmentsReceived=0;
        task.connect(ip);
        Log.i("connect","test");


    }

    private void start()
    {
        Log.i("player","starting player");
        mpC = new MediaPlayer();
        mpC.reset();
        try {
            mpC.setDataSource(source);
            mpC.prepare();
            mpC.start();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Illegal state",e.getMessage());
        }
//        catch (Exception e)
//        {
//            Log.e("Illegal state",e.getMessage());
//        }
    }
}
