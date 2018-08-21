package com.example.gcm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.astix.Common.CommonInfo;

import project.astix.com.balajisosfadirectsales.BaseActivity;
import project.astix.com.balajisosfadirectsales.PRJDatabase;
import project.astix.com.balajisosfadirectsales.LauncherActivity;
import project.astix.com.balajisosfadirectsales.R;
import project.astix.com.balajisosfadirectsales.StoreSelection;

public class NotificationActivity extends BaseActivity
{

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	
	public TableLayout tbl1_dyntable_For_Notification; 
	public TableRow tr1PG2;
	PRJDatabase dbengine = new PRJDatabase(this);
	public int ComeFromActivity=0;
	
	
	  public String imei;
      public String date;
      public String pickerDate;
      public String rID;
      public int chkActivity=1;
      
      
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		
		Intent passedvals = getIntent();
		
		
		
		if( getIntent().getExtras() != null)
		{
			if(getIntent().hasExtra("chkActivity"))
			{
				chkActivity=0;
				String str = getIntent().getStringExtra("msg");
				String comeFrom = getIntent().getStringExtra("comeFrom");
				ComeFromActivity=Integer.parseInt(comeFrom);
			}
		}
		
		/*public long inserttblNotificationMstr(String IMEI,String Noti_text,String Noti_DateTime,int Noti_ReadStatus,int Noti_NewOld,
				String Noti_ReadDateTime,int Noti_outStat)*/
		tbl1_dyntable_For_Notification = (TableLayout) findViewById(R.id.dyntable_For_Notification);
		
		dbengine.open();
		int SerialNo=dbengine.countNoRowIntblNotificationMstr();
		System.out.println("Sunil LastNitificationrList SerialNo : "+SerialNo);
		//String LastOrderDetail[]=dbengine.fetchAllDataFromtblFirstOrderDetailsOnLastVisitDetailsActivity(storeID);
		String LastNitificationrList[]=dbengine.LastNitificationrListDB();
		//String LastNitificationrList[]={"10-06-2015_Hi ","11-06-2015_Bye "};
		
		System.out.println("Sunil LastNitificationrList : "+LastNitificationrList.length);
		dbengine.close();
		
		LayoutInflater inflater = getLayoutInflater();
		
		DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    double x = Math.pow(dm.widthPixels/dm.xdpi,2);
	    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
	    double screenInches = Math.sqrt(x+y);
		
		
		for (int current = 0; current <= (LastNitificationrList.length - 1); current++) 
		{

			final TableRow row = (TableRow)inflater.inflate(R.layout.table_notification, tbl1_dyntable_For_Notification, false);
			
			TextView tv1 = (TextView)row.findViewById(R.id.tvDate);
			TextView tv2 = (TextView)row.findViewById(R.id.tvMessage);
			
			
			if(screenInches>6.5)
			{
				tv1.setTextSize(14);
				tv2.setTextSize(14);
				
			}
			else
			{
				
			}
			
			//System.out.println("Abhinav Raj LTDdet[current]:"+LTDdet[current]);
			StringTokenizer tokens = new StringTokenizer(String.valueOf(LastNitificationrList[current]), "_");
			
			tv1.setText("  "+tokens.nextToken().trim());
			
			tv2.setText("  "+tokens.nextToken().trim());
			
		
			tbl1_dyntable_For_Notification.addView(row);
		}
    	
		
		Button backbutton=(Button)findViewById(R.id.backbutton);
		backbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent submitStoreIntent = new Intent(NotificationActivity.this, LauncherActivity.class);
				startActivity(submitStoreIntent);
				finish();
				
			}
		});
	
	}
	@Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if(chkActivity ==1)
		{
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		imei = tManager.getDeviceId();
		if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
		{
			imei = tManager.getDeviceId();
			CommonInfo.imei=imei;
		}
		else
		{
			imei=CommonInfo.imei.trim();
		}
		

		Date currDate = new Date();
		SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		
		date = currDateFormat.format(currDate).toString();
		


	    Date date1=new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		String passDate = sdf.format(date1).toString();
		String fDate = passDate.trim().toString();
		dbengine.open();
		rID=dbengine.GetActiveRouteID();
		dbengine.close();
        Intent submitStoreIntent = new Intent(NotificationActivity.this, StoreSelection.class);
        
        
        submitStoreIntent.putExtra("imei", imei);
        submitStoreIntent.putExtra("userDate", date);
        submitStoreIntent.putExtra("pickerDate", fDate);
        submitStoreIntent.putExtra("rID", rID);
		startActivity(submitStoreIntent);
		finish();
		}
        else
        {
        	 Intent submitStoreIntent = new Intent(NotificationActivity.this, LauncherActivity.class);
     		startActivity(submitStoreIntent);
     		finish();
        }
    }
	
	

}
