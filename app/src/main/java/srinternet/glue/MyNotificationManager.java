package srinternet.glue;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

/**
 * Created by JAVIER OLIVER ASUS on 09/08/2017.
 */

public class MyNotificationManager {
    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }
    public void showSmallNotification(String title, String message, Intent intent) {

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        if (Memoria.vibracion==1){
            Vibrator vibrator =(Vibrator) mCtx.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(500);
        }
        if(Memoria.sonidos==1){
            MediaPlayer mp = MediaPlayer.create(mCtx, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            mp.start();
        }

        String men;

        try{
            JSONObject j=new JSONObject(message);
            men=j.getString("message");

        }catch(Exception e){
            men=message;
        }
        String mensajeDeco = men;

        if(men.contains("u00F1")){
            mensajeDeco = men.replace("u00F1","ñ");
        }

        mensajeDeco= StringEscapeUtils.unescapeJava(mensajeDeco);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.drawable.icono_notificacion).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.icono_notificacion)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.icono_notificacion))
                .setContentText(mensajeDeco)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }

}
