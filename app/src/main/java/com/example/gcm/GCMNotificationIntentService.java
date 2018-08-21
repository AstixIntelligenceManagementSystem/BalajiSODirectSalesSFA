package com.example.gcm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;





import com.google.android.gms.gcm.GoogleCloudMessaging;

import project.astix.com.balajisosfadirectsales.PRJDatabase;
import project.astix.com.balajisosfadirectsales.R;

public class GCMNotificationIntentService extends IntentService
{
	// Sets an ID for the notification, so it can be updated
	public static final int notifyID = 9001;
	NotificationCompat.Builder builder;
	PRJDatabase dbengine = new PRJDatabase(this);

	//public String DuplicateMsgServerID="";

	public GCMNotificationIntentService()
	{
		super("GcmIntentService");
	}


	@Override
	protected void onHandleIntent(Intent intent)
	{


		Bundle extras = intent.getExtras();
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

		String messageType = gcm.getMessageType(intent);

		if (!extras.isEmpty())
		{
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType))
			{
				sendNotification("Send error: " + extras.toString(),"");
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType))
			{
				sendNotification("Deleted messages on server: "+ extras.toString(),"");
			}
			else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
					.equals(messageType))
			{
				try
				{
					if(!extras.get(ApplicationConstants.MSG_KEY).toString().equals("") || !extras.get(ApplicationConstants.MSG_KEY).toString().equals("Null"))
					{
						sendNotification(extras.get(ApplicationConstants.MSG_KEY).toString(),extras.get(ApplicationConstants.MSG_SenderFrom).toString());
						//System.out.println("Sunil Recieve MSG :"+extras.get(ApplicationConstants.MSG_KEY));
						String str=TextUtils.htmlEncode(extras.get(ApplicationConstants.MSG_KEY).toString());
						String strMsgSendTime=extras.get(ApplicationConstants.MSG_SendTime).toString();
						TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String imei = tManager.getDeviceId();



						long syncTIMESTAMP = System.currentTimeMillis();
						Date dateobj = new Date(syncTIMESTAMP);
						SimpleDateFormat df = new SimpleDateFormat(
								"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
						String Noti_ReadDateTime = df.format(dateobj);
						String MsgSendingTime=strMsgSendTime;
						//StringTokenizer tokens = new StringTokenizer(String.valueOf(str), "^");


						String MsgFrom=extras.get(ApplicationConstants.MSG_SenderFrom).toString();
						String NotificationMessage=TextUtils.htmlEncode(str);

						int MsgServerID=Integer.parseInt(extras.get(ApplicationConstants.MSG_SendMsgID).toString());

						dbengine.open();
						int SerialNo=dbengine.countNoRowIntblNotificationMstr();
						if(SerialNo>=10)
						{
							dbengine.deletetblNotificationMstrOneRow(1);
						}
						else
						{
							SerialNo=SerialNo+1;
						}

						int DuplicateMsgServerID=dbengine.checkMessageIDExistOrNotForNotification(MsgServerID);

						if(DuplicateMsgServerID==0)
						{
							dbengine.inserttblNotificationMstr(SerialNo,imei,NotificationMessage,MsgSendingTime,1,1,
									Noti_ReadDateTime,0,MsgServerID);
							dbengine.close();

						}
					/*else
					{
						duplicate="";
					}*/

					}
				}
				catch(Exception e)
				{

				}



			}
		}
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	public void sendNotification(String msg,String title)
	{
		// For sending to Notification Activity

		//  Intent resultIntent = new Intent(this, NotificationActivity.class);
		//  resultIntent.putExtra("msg", msg);
		//  resultIntent.putExtra("comeFrom", "0");
		// PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,resultIntent, PendingIntent.FLAG_ONE_SHOT);


		// For Not sending to Notification Activity
		//new Intent()--->First Way
		//Intent notificationIntent = new Intent(this, null);--->Second Way

		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,new Intent(), PendingIntent.FLAG_ONE_SHOT);


		NotificationCompat.Builder mNotifyBuilder;
		NotificationManager mNotificationManager;

		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		mNotifyBuilder = new NotificationCompat.Builder(this)
				.setContentText("You've received new message.")
				.setSmallIcon(R.drawable.app_logo);

		// Set pending intent
		mNotifyBuilder.setContentIntent(resultPendingIntent);

		// Set Vibrate, Sound and Light
		int defaults = 0;
		defaults = defaults | Notification.DEFAULT_LIGHTS;
		defaults = defaults | Notification.DEFAULT_VIBRATE;
		defaults = defaults | Notification.DEFAULT_SOUND;

		mNotifyBuilder.setDefaults(defaults);
		// Set the content for Notification
		mNotifyBuilder.setContentText(" "+msg);
		mNotifyBuilder.setGroupSummary(true);
		// Set autocancel
		mNotifyBuilder.setAutoCancel(true);
		NotificationCompat.BigTextStyle bigtextStyle=new  NotificationCompat.BigTextStyle(mNotifyBuilder);
		bigtextStyle.setBigContentTitle(title);
		bigtextStyle.bigText(msg);


		// Post a notification
		mNotificationManager.notify(notifyID, mNotifyBuilder.build());
		// startActivity(resultIntent);
	}
//.setSmallIcon(R.drawable.ltfood_logo);



}
