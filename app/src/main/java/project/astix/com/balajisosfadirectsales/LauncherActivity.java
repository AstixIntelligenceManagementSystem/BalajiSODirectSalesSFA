package project.astix.com.balajisosfadirectsales;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import com.astix.Common.CommonInfo;


//import com.astix.sfatju.R;


import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



public class LauncherActivity extends BaseActivity
{

	public  int click_but_distribtrStock=0;
	public Button but_distribtrStock;
	int CstmrNodeId=0,CstomrNodeType= 0;


	public static int RowId=0;
	
	public String ReasonId;
	public String ReasonText="NA";
	public  Button but_GetStore,but_NoStoreVisit;
	public ProgressDialog mProgressDialog;
	public String passDate;
	public String fDate;
	public String fDate2write;
	
	public String imei;
	public String rID;
	ImageView imgVw_logout;
	public int powerCheck=0;
	
	public  PowerManager.WakeLock wl;
	public String Noti_text="Null";
	public int MsgServerID=0;
	public SimpleDateFormat sdf;
	
	public Date currDate;
	public SimpleDateFormat currDateFormat;
	public String currSysDate;
	public String currPassedSysDate;
	
	public int chkFlgForErrorToCloseApp=0;
	public int IsSchemeApplicable=3;
	public String[] route_name;	
	public String[] route_name_id;
	
	public String selected_route_id="0";
	public  Button but_Invoice;
	public String myExistingPickerDate;
	
	//ProgressDialog pDialog2;
	public ProgressDialog pDialogGetStores;
	public Spinner routeDDL;
	public String srvcDone = "0";
	
	ArrayList<String> exCaseList = new ArrayList<String>();
	PRJDatabase dbengine = new PRJDatabase(this);
	//LocationManager newLM333;
	Date strDate;
	//	DatePicker selectedDate;
	
	
	private int selected = 0; // change selected to global 
	public TextView tv_Route;
	public String twoDigitDate;
	public String CompareDate="";
	
	public RadioButton TodaySelection;
	public RadioButton TomrSelection;
	
	public String temp_select_routename="NA";
	public String temp_select_routeid="NA";
	public ProgressBar progressBar;
	public boolean serviceException=false;
	
	
	LinkedHashMap<String, String> hmapReasonIdAndDescr_details=new LinkedHashMap<String, String>();
	
	
	 String[] reasonNames;
	public String newfullFileName;
	DatabaseAssistantDistributorEntry DA = new DatabaseAssistantDistributorEntry(this);
	SyncXMLfileData task2;
	public String[] xmlForWeb = new String[1];
	int serverResponseCode = 0;
	public int syncFLAG = 0;
private void getReasonDetail() throws IOException 
{
	
	int check=dbengine.countDataIntblNoVisitReasonMaster();
		
	hmapReasonIdAndDescr_details=dbengine.fetch_Reason_List();
		
		int index=0;
		if(hmapReasonIdAndDescr_details!=null)
    	{
			reasonNames=new String[hmapReasonIdAndDescr_details.size()];
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapReasonIdAndDescr_details); 
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
            	 Map.Entry me2 = (Map.Entry)iterator.next();
            	 reasonNames[index]=me2.getKey().toString();
                 index=index+1;
            }
    	}
    	
		
	}
	
	
	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		 but_GetStore.setEnabled(true);
		 but_Invoice.setEnabled(true);
		// System.out.println("Shivam Checking OnPause ");
	}
	
	
	private class GetUpdateInfo extends AsyncTask<Void, Void, Void> 
	{
		
		ProgressDialog pDialogGetStores;
		public GetUpdateInfo(LauncherActivity activity) 
		{
			pDialogGetStores = new ProgressDialog(activity);
		}
		
		
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.UpdatingNewVersionMsg));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			
	        try 
	 	      {
	        	downloadapk();
		      }
	 	   catch(Exception e) 
	 	      {}

	 	   finally 
	 	      {}
	  
			return null;
		}

		
		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			
			try 
			{
			    if(pDialogGetStores.isShowing()) 
			      {
			    	   pDialogGetStores.dismiss();
				  }
			}
			catch(Exception e)
			{
				
			}
			
			installApk();
		}
	}
	
private void downloadapk()
	{
	    try {
	    	
	    	//ParagIndirectTest
	       // URL url = new URL("http://115.124.126.184/downloads/ParagIndirect.apk");
	      //  URL url = new URL("http://115.124.126.184/downloads/ParagIndirectTest.apk");
	        URL url = new URL(CommonInfo.VersionDownloadPath.trim()+CommonInfo.VersionDownloadAPKName);
	        URLConnection connection = url.openConnection();
	        HttpURLConnection urlConnection = (HttpURLConnection) connection;
	        //urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
	        urlConnection.setRequestMethod("GET");
	        urlConnection.setDoInput(true);
	        //urlConnection.setDoOutput(false);
	       // urlConnection.setInstanceFollowRedirects(false);
	        urlConnection.connect();
	        
	       //if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) 
	       // {
	        	 File sdcard = Environment.getExternalStorageDirectory();
	 	        
	 	      //  //System.out.println("sunil downloadapk sdcard :" +sdcard);
	 	        //File file = new File(sdcard, "neo.apk");
	 	        
	 	        String PATH = Environment.getExternalStorageDirectory() + "/download/";
	 	      // File file2 = new File(PATH+"ParagIndirect.apk");
	 	       File file2 = new File(PATH+CommonInfo.VersionDownloadAPKName);
	 	      if(file2.exists())
	          {
	 	    	 file2.delete();
	          }
	 	       
	             File file1 = new File(PATH);
	             file1.mkdirs();
	            
	            // File file = new File(file1, "ParagIndirect.apk");
	             File file = new File(file1, CommonInfo.VersionDownloadAPKName);
	           //  FileOutputStream fos = new FileOutputStream(file);
	 	        
	             
	           //  //System.out.println("sunil downloadapk making directory :" +sdcard);
	 	        
	             int size = connection.getContentLength();
	           //  //System.out.println("sunil downloadapk getting size :" +size);
	 	      
	 	        FileOutputStream fileOutput = new FileOutputStream(file);
	 	      //  //System.out.println("two");
	 	        InputStream inputStream = urlConnection.getInputStream();
	 	      //  //System.out.println("sunil downloadapk sdcard called :" +sdcard);
	 	        byte[] buffer = new byte[10240];
	 	        int bufferLength = 0;
	 	       int current = 0;
	 	        while ( (bufferLength = inputStream.read(buffer)) != -1 ) {
	 	            fileOutput.write(buffer, 0, bufferLength);
	 	        }
	 	       //current+=bufferLength;
	 	        fileOutput.close();
	 	       
	 	      //  //System.out.println("");
	 	     //   //System.out.println("sunil downloadapk completed ");
	 	      //checkUnknownSourceEnability();
	 	       //initiateInstallation();
	      //  }
	       

	    } catch (MalformedURLException e) 
	    {
	          //  e.printStackTrace();
	         //   //System.out.println("sunil downloadapk failed ");
	    } catch (IOException e) {
	         //   e.printStackTrace();
	         //   //System.out.println("sunil downloadapk failed ");
	            
	    }
	    }

	 private void installApk()
	 {
		    this.deleteDatabase(PRJDatabase.DATABASE_NAME);
		    Intent intent = new Intent(Intent.ACTION_VIEW);
	       // Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "ParagIndirect.apk"));
	        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + CommonInfo.VersionDownloadAPKName));
	       
	        intent.setDataAndType(uri, "application/vnd.android.package-archive");
	        startActivity(intent);
	        finish();
	        
	        
	  }
	
	public void showNewVersionAvailableAlert()
    {
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
		//AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
		AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(LauncherActivity.this);

		alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
		alertDialogNoConn.setCancelable(false);
		alertDialogNoConn.setMessage(getText(R.string.NewVersionMsg));
		alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
		        {
					public void onClick(DialogInterface dialog, int which)
					 {
						GetUpdateInfo task = new GetUpdateInfo(LauncherActivity.this);
						task.execute();
						dialog.dismiss();
					 }
				});
	
		alertDialogNoConn.setIcon(R.drawable.info_ico);
		AlertDialog alert = alertDialogNoConn.create();
		alert.show();
	
}
	
	public void showAlertForEveryOne(String msg) 
	 {
			//AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
		 AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(LauncherActivity.this);

		 alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
			alertDialogNoConn.setMessage(msg);
			alertDialogNoConn.setCancelable(false);
			alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
			       {
						public void onClick(DialogInterface dialog, int which) 
						 {
	                       dialog.dismiss();
	                       finish();
                         }
					});
			alertDialogNoConn.setIcon(R.drawable.info_ico);
			AlertDialog alert = alertDialogNoConn.create();
			alert.show();
	}
	 private class CheckUpdateVersion extends AsyncTask<Void, Void, Void> 
		{
			@Override
			protected Void doInBackground(Void... params)
			{
			 try 
		 	   {
				   
		           int DatabaseVersion=dbengine.DATABASE_VERSION;
		           int ApplicationID=dbengine.Application_TypeID;
		           ServiceWorker newservice = new ServiceWorker();
		           //newservice = newservice.getAvailableAndUpdatedVersionOfApp(getApplicationContext(), imei,fDate,DatabaseVersion,ApplicationID);
		           newservice = newservice.getAvailableAndUpdatedVersionOfAppNew(getApplicationContext(), imei,fDate,DatabaseVersion,ApplicationID);
		           if(!newservice.director.toString().trim().equals("1"))
		           {	
						if(chkFlgForErrorToCloseApp==0)
						{
							chkFlgForErrorToCloseApp=1;
                     }
					
				  } 
					
		 	  }
		 	   catch (Exception e) 
		 	       {}

		 			finally 
		 			{}
		  
				return null;
			}

			
			@Override
			protected void onPostExecute(Void result) 
			{
				super.onPostExecute(result);
				
				if(chkFlgForErrorToCloseApp==1)   // if Webservice showing exception or not excute complete properly
				{
					chkFlgForErrorToCloseApp=0;

                 Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError),Toast.LENGTH_LONG).show();
                 finish();
				}
				else
				{
					
					dbengine.open();
					dbengine.maintainPDADate();
					String getPDADate=dbengine.fnGetPdaDate();
					String getServerDate=dbengine.fnGetServerDate();
					dbengine.close();
				
				
				
					if(!getPDADate.equals("") )   //|| !getPDADate.equals("NA") || !getPDADate.equals("Null") || !getPDADate.equals("NULL")
					{
						if(!getServerDate.equals(getPDADate))
						{
							showAlertBox(getResources().getString(R.string.txtErrorPhnDate));

							return;
						}
					}
					else
					{
					dbengine.open();
					int checkUserAuthenticate=dbengine.FetchflgUserAuthenticated();
				    dbengine.close();
					
					if(checkUserAuthenticate==0)   // 0 means-->New user        1 means-->Exist User
					{
						showAlertForEveryOne(getResources().getString(R.string.phnRegisterError));

						return;
						
					}
					dbengine.open();
					int check=dbengine.FetchVersionDownloadStatus();  // 0 means-->new version install  1 means-->new version not install
					dbengine.close();
					if(check==1)
					{
						showNewVersionAvailableAlert();
					}
					}
					
					}
			
				}
		}

	public boolean onKeyDown(int keyCode, KeyEvent event)    // Control the PDA Native Button Handling
	{
		  // TODO Auto-generated method stub
		  if(keyCode==KeyEvent.KEYCODE_BACK)
		  {
			  return true;
			 // finish();
		  }
		  if(keyCode==KeyEvent.KEYCODE_HOME)
		  {
			 // finish();
			  
		  }
		  if(keyCode==KeyEvent.KEYCODE_MENU)
		  {
			  return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_SEARCH)
		  {
			  return true;
		  }

		  return super.onKeyDown(keyCode, event);
	}
	
	public void radioButtonHandling()
	{
		
		OnClickListener radioGroupListener = new OnClickListener()
        {
			public void onClick(View v) 
			{
              switch (v.getId()) 
				{

				case R.id.radio0:
					
					/*dbengine.open();
					String strGetAllActiveRouteId=dbengine.GetAllActiveRouteID();
					String strGetAllActiveRouteDescr=dbengine.GetAllActiveRouteDescr();
					dbengine.close();
					
					tv_Route.setText(""+strGetAllActiveRouteDescr);
				    rID=strGetAllActiveRouteId;*/
					
					
					dbengine.open();
					String strGetActiveRouteId=dbengine.GetCurrentDayActiveRouteID();
					dbengine.close();
					if(route_name.length>0)
					{
						for(int i=0;i<route_name.length;i++)
						{
							if(route_name_id[i].equals(strGetActiveRouteId))
							{
								selected=i;
								break;
							}
							else
							{
								selected=0;
							}
						}
						tv_Route.setText(""+route_name[selected]);
						selected_route_id=route_name_id[selected];
						temp_select_routename=route_name[selected];
					}
					
					rID=selected_route_id;
					
					temp_select_routeid=selected_route_id;//=temp_select_routeid;
			        rID=temp_select_routeid;
			      
					

					break;
				
				case R.id.radio1:
					
					showDialogButtonClick();
					
					break;
				
				}

			}
		};
		
		TodaySelection.setOnClickListener(radioGroupListener);
		TomrSelection.setOnClickListener(radioGroupListener);
	}
	
	public void showDialogButtonClick() 
	{


		//AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, AlertDialog.THEME_HOLO_DARK));

		//AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, android.R.style.Theme_Holo_Light_Dialog));
      //  ContextThemeWrapper cw= new ContextThemeWrapper(this,R.style.AlertDialogTheme);
		//AlertDialog.Builder builder = new AlertDialog.Builder(cw);
		//AlertDialog.Builder builder = new AlertDialog.Builder(LauncherActivity.this,android.R.style.Theme_Material_Light_Dialog_NoActionBar);
		AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));

		builder.setTitle(R.string.AvailableRoutesMsg);

         
         builder.setSingleChoiceItems(route_name,selected,new DialogInterface.OnClickListener()
           {
                @Override
                public void onClick(DialogInterface dialog,int which) 
                {
                    selected = which;
                    temp_select_routename=route_name[selected];
                    temp_select_routeid=route_name_id[selected];
                    rID=temp_select_routeid;
                    
                }
            })
            .setCancelable(false)
            .setPositiveButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener() 
                {
                    @Override
                    public void onClick(DialogInterface dialog,int which)
                         {
	                    	  dialog.dismiss();
	                          tv_Route.setText(""+temp_select_routename);
	                          selected_route_id=temp_select_routeid;
	                          rID=selected_route_id;
	                    	  
                        }
                });
             
            AlertDialog alert = builder.create();
            alert.show();
        }
	
	
	private class GetStoresForDay extends AsyncTask<Void, Void, Void>
	{		
		
		
		public GetStoresForDay(LauncherActivity activity) 
		{
			pDialogGetStores = new ProgressDialog(activity);
		}
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			

			dbengine.open();
			String getPDADate=dbengine.fnGetPdaDate();
			String getServerDate=dbengine.fnGetServerDate();
			
			
			
			dbengine.close();
			
			//System.out.println("Checking  Oncreate Date PDA GetStoresForDay:"+getPDADate);
			//System.out.println("Checking  Oncreate Date Server GetStoresForDay :"+getServerDate);
			
			if(!getPDADate.equals(""))  // || !getPDADate.equals("NA") || !getPDADate.equals("Null") || !getPDADate.equals("NULL")
			{
				if(!getServerDate.equals(getPDADate))
				{
					
					showAlertBox(getResources().getString(R.string.txtErrorPhnDate));

					dbengine.open();
					dbengine.maintainPDADate();
					dbengine.reCreateDB();
					dbengine.close();
					return;
				}
			}
			
			
			
			
			
			
			dbengine.open();
			dbengine.fnSetAllRouteActiveStatus();
			
			//rID="17^18^19";
			
			StringTokenizer st = new StringTokenizer(rID, "^");
            
    		while (st.hasMoreElements()) 
    		{
    			//System.out.println("Anand StringTokenizer Output: ");
    			dbengine.updateActiveRoute(st.nextElement().toString(), 1);
    		}
			
			
			
			
			long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String startTS = df.format(dateobj);
			
			int DayEndFlg=0;
			int ChangeRouteFlg=0;
			
			int DatabaseVersion=dbengine.DATABASE_VERSION;
			String AppVersionID=dbengine.AppVersionID;
			dbengine.insertTblDayStartEndDetails(imei,startTS,rID,DayEndFlg,ChangeRouteFlg,fDate,AppVersionID);//DatabaseVersion;//getVersionNumber
			dbengine.close();
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.RetrivingDataMsg));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		}

		@Override
		protected Void doInBackground(Void... args) 
		{
			ServiceWorker newservice = new ServiceWorker();
			
		try
		{

			String RouteType="0";
			try
			{
				dbengine.open();
				RouteType=dbengine.FetchRouteType(rID);
				dbengine.close();
				dbengine.deleteAllSingleCallWebServiceTableWhole();
			}
			catch(Exception e)
			{

			}
			for(int mm = 1; mm < 40  ; mm++)
			{
				System.out.println("Excecuted function : "+newservice.flagExecutedServiceSuccesfully);
				if(mm==1)
					{
					
					System.out.println("bywww");
					
					
				       /* newservice = newservice.getallStores(getApplicationContext(), fDate, imei, rID,RouteType,1);
						if(newservice.flagExecutedServiceSuccesfully!=1)
						{
							serviceException=true;
							break;
						}*/
					}
				if(mm==2)
					{  
					
						/*newservice = newservice.getallProduct(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=2)
						{
							serviceException=true;
							break;
						}*/
					}
				if(mm==3)
					{  
			
						newservice = newservice.getCategory(getApplicationContext(), imei);
						if(newservice.flagExecutedServiceSuccesfully!=3)
						{
							serviceException=true;
							break;
						}
						
					}
				if(mm==4)
					{ 
					
						Date currDateNew = new Date();
						SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
						
						String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
						newservice = newservice.getAllNewSchemeStructure(getApplicationContext(), currSysDateNew, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=4)
						{
							serviceException=true;
							break;
						}
					
					}
				if(mm==5)
					{  
					
						Date currDateNew = new Date();
						SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
						
						String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
						newservice = newservice.getallPDASchAppListForSecondPage(getApplicationContext(), currSysDateNew, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=5)
						{
							serviceException=true;
							break;
						}
					}
				if(mm==6)		
					{

					/*Date currDateNew = new Date();
					SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
					
					String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
					newservice = newservice.getAllPOSMaterialStructure(getApplicationContext(), currSysDateNew, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=4)
					{
						serviceException=true;
						break;
					}*/
					}
				if(mm==7)
					{ 

					 
					
					/*Date currDateNew = new Date();
					SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
					
					String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
					newservice = newservice.callGetLastVisitPOSDetails(getApplicationContext(), currSysDateNew, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=4)
					{
						serviceException=true;
						break;
					}*/
				
				
				
					}
				if(mm==8)
					{
						newservice = newservice.getfnGetStoreWiseTarget(getApplicationContext(), fDate, imei, rID,RouteType);
					}
				if(mm==9)
					{  
					
					}
				if(mm==10)
					{     
					
					}
				if(mm==11)
					{   
					
					}
				if(mm==12)
					{   
					
					}
				if(mm==13)
					{  
					
					}
				if(mm==14)
					{  
					
					}
				if(mm==15)
					{  
					
					}
				if(mm==16)
					{  
					
					}
				if(mm==17)
                    {
					
					}
				if(mm==18)
					{
					
					}
				if(mm==19)
					{ 
					
					}
				if(mm==20)
					{  
					
					}
				if(mm==21) 
					{  
						newservice = newservice.GetPDAIsSchemeApplicable(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=21)
						{
							serviceException=true;
							break;
						}
						
					}
				
				if(mm==22)
					{  
						/*newservice = newservice.getallPDAtblSyncSummuryDetails(getApplicationContext(), fDate, imei, rID);
						if(newservice.flagExecutedServiceSuccesfully!=22)
						{
							serviceException=true;
							break;
						}
						*/
					}
				if(mm==23) 
					{  
					//newservice = newservice.getallPDAtblSyncSummuryForProductDetails(getApplicationContext(), fDate, imei, rID);
					}
				if(mm==24)
					{ 
					/*newservice = newservice.GetSchemeCoupon(getApplicationContext(), fDate, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=24)
					{
						serviceException="GetSchemeCoupon";
						break;
					}*/
					}
				if(mm==25)
					{ 
				/*	newservice = newservice.GetSchemeCouponSlab(getApplicationContext(), fDate, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=25)
					{
						serviceException="GetSchemeCouponSlab";
						break;
					}*/
					}
				if(mm==26)
					{
				/*	newservice = newservice.GetDaySummaryNew(getApplicationContext(), fDate, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=26)
					{
						serviceException="GetDaySummaryNew";
						break;
					}*/
					}
				if(mm==27)
					{/* 
					newservice = newservice.GetOrderDetailsOnLastSalesSummary(getApplicationContext(), fDate, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=27)
					{
						serviceException="GetOrderDetailsOnLastSalesSummary";
						break;
					}
					*/}
				if(mm==28)
					{
				/*	newservice = newservice.GetVisitDetailsOnLastSalesSummary(getApplicationContext(), fDate, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=28)
					{
						serviceException="GetVisitDetailsOnLastSalesSummary";
						break;
					}*/
					}
				if(mm==29)
					{ 
						newservice = newservice.GetLODDetailsOnLastSalesSummary(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=29)
						{
							serviceException=true;
							break;
						}
					}
				
				if(mm==31)
					{ 
						newservice = newservice.GetCallspForPDAGetLastVisitDate(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=31)
						{
							serviceException=true;
							break;
						}
					}
				if(mm==32)
					{ 
						newservice = newservice.GetCallspForPDAGetLastOrderDate(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=32)
						{
							serviceException=true;
							break;
						}
					}
				if(mm==33)
					{ 
						newservice = newservice.GetCallspForPDAGetLastVisitDetails(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=33)
						{
							serviceException=true;
							break;
						}
					}
				if(mm==34)
					{ 
						newservice = newservice.GetCallspForPDAGetLastOrderDetails(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=34)
						{
							serviceException=true;
							break;
						}
					}
				if(mm==35)
					{ 
						newservice = newservice.GetCallspForPDAGetLastOrderDetails_TotalValues(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=35)
						{
							serviceException=true;
							break;
						}
					}
				if(mm==36)
					{ 
						newservice = newservice.GetCallspForPDAGetExecutionSummary(getApplicationContext(), fDate, imei, rID,RouteType);
						if(newservice.flagExecutedServiceSuccesfully!=36)
						{
							serviceException=true;
							break;
						}
					}
				
				if(mm==37)
				{ 
					newservice = newservice.getQuotationDataFromServer(getApplicationContext(), fDate, imei, rID);
					if(newservice.flagExecutedServiceSuccesfully!=37)
					{
						serviceException=true;
						break;
					}
				}
				
				if(mm==38)
				{ 

						/*newservice=newservice.fnGetDistStockData(getApplicationContext(),imei);
						if(newservice.flagExecutedServiceSuccesfully!=38)
						{
							serviceException=true;
							break;
						}*/
					newservice = newservice.fnGetVanStockData(getApplicationContext(), CommonInfo.imei);
					if (newservice.flagExecutedServiceSuccesfully != 39) {
						serviceException = true;
					}
					/*newservice = newservice.fnGetVanStockData(getApplicationContext(), CommonInfo.imei);
					if (!newservice.movie_name.equals("1")) {
						serviceException = true;
					}*/
				}
				if(mm==39)
				{

					newservice = newservice.getStoreWiseOutStandings(getApplicationContext(), fDate, imei, rID,RouteType);
					/*if(newservice.flagExecutedServiceSuccesfully!=1)
					{
						serviceException=true;
						break;
					}*/
				}
				
			}
			
		
		 } 
		catch (Exception e) 
		  {
				Log.i("SvcMgr", "Service Execution Failed!", e);
          }
       finally 
          {
               Log.i("SvcMgr", "Service Execution Completed...");
          }
			return null;
		}
		
	
		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			
			Log.i("SvcMgr", "Service Execution cycle completed");
			
			try 
			{
			    if(pDialogGetStores.isShowing()) 
			      {
			    	   pDialogGetStores.dismiss();
				  }
			}
			catch(Exception e)
			{
				
			}
            if(serviceException)
            {
            	try 
    			{
            		 but_GetStore.setEnabled(true);

            	showAlertException(getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrorRetrievingData));
    			}

            	catch(Exception e)
    			{
    				
    			}
            	 dbengine.open();
		      serviceException=false;
		         dbengine.maintainPDADate();
		         dbengine.dropRoutesTBL();
		         dbengine.reCreateDB();
		         
		         dbengine.close();
            }
            else
            {
              	Intent storeIntent = new Intent(LauncherActivity.this, StoreSelection.class);
    			storeIntent.putExtra("imei", imei);
    			storeIntent.putExtra("userDate", currSysDate);
    			storeIntent.putExtra("pickerDate", fDate);
    			storeIntent.putExtra("rID", rID);
    			
    		 
    				startActivity(storeIntent);
    				finish();
    			
            }
		 
		}
	}
	
	
	public void showSettingsAlert()
	{
		        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		        alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
		        alertDialog.setIcon(R.drawable.error_info_ico);
		        alertDialog.setCancelable(false);
		       
		        alertDialog.setMessage(R.string.genTermGPSDisablePleaseEnable);
		        alertDialog.setPositiveButton(R.string.AlertDialogOkButton, new DialogInterface.OnClickListener()
		        {
		            public void onClick(DialogInterface dialog,int which)
		            {
		             Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		             startActivity(intent);
		            }
		        });
		 
		        alertDialog.show();
		 }
	
	
	
	


	
	 @Override
	  public void onDestroy() 
	  {
	   super.onDestroy();
	   wl.release();
	  }
	 
	 public void displayAlertDialog() 
	 {
			LayoutInflater inflater = getLayoutInflater();
			View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_nostore, null);
			final EditText et_Reason = (EditText) alertLayout.findViewById(R.id.et_Reason);
			et_Reason.setVisibility(View.INVISIBLE);
			
			 final Spinner spinner_reason=(Spinner) alertLayout.findViewById(R.id.spinner_reason);
			
			 ArrayAdapter adapterCategory=new ArrayAdapter(LauncherActivity.this, android.R.layout.simple_spinner_item,reasonNames);
		     adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		     spinner_reason.setAdapter(adapterCategory);
			
			spinner_reason.setOnItemSelectedListener(new OnItemSelectedListener() 
			{

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
				{
					// TODO Auto-generated method stub
				String	spinnerReasonSelected = spinner_reason.getSelectedItem().toString();
				ReasonText=spinnerReasonSelected;
					int check=dbengine.fetchFlgToShowTextBox(spinnerReasonSelected);
					ReasonId=dbengine.fetchReasonIdBasedOnReasonDescr(spinnerReasonSelected);
					if(check==0)
					{
						et_Reason.setVisibility(View.INVISIBLE);
					}
					else
					{
						et_Reason.setVisibility(View.VISIBLE);
					}
					
					
					//ReasonId,ReasonText
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) 
				{
					// TODO Auto-generated method stub
					
				}
			});
			
			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle(R.string.AlertDialogHeaderMsg);
			alert.setView(alertLayout);
			//alert.setIcon(R.drawable.info_ico);
			alert.setCancelable(false);
			alert.setNegativeButton(getResources().getString(R.string.txtCancel), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					
				}
			});

			
			alert.setPositiveButton(getResources().getString(R.string.txtSubmit), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) 
				{
					
					 but_NoStoreVisit.setEnabled(false);
					
					if (ReasonText.equals("")||ReasonText.equals("Select Reason"))
					  {
					       AlertDialog.Builder alertDialog = new AlertDialog.Builder(LauncherActivity.this);
						    alertDialog.setTitle(getResources().getString(R.string.txtErr));

					        alertDialog.setMessage(getResources().getString(R.string.txtSelectReason));
					        alertDialog.setIcon(R.drawable.error);
					        alertDialog.setCancelable(false);
					      
					        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
					        {
					            public void onClick(DialogInterface dialog,int which) 
					            {
					                dialog.dismiss();
					                displayAlertDialog(); 
					            	
					            }
					        });
					        alertDialog.show();
					}
					else
					{
					
					// code for matching password
					String reason;
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
					
					
					Date pdaDate=new Date();
					SimpleDateFormat	sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
					String CurDate = sdfPDaDate.format(pdaDate).toString().trim();
					
					if(et_Reason.isShown())
					{
					
							if(TextUtils.isEmpty(et_Reason.getText().toString().trim()))
							{
								    AlertDialog.Builder alertDialog = new AlertDialog.Builder(LauncherActivity.this);
								    alertDialog.setTitle(getResources().getString(R.string.txtErr));
							        alertDialog.setMessage(getResources().getString(R.string.txtEnterReason));

							        alertDialog.setIcon(R.drawable.error);
							        alertDialog.setCancelable(false);
							      
							        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener()
							        {
							            public void onClick(DialogInterface dialog,int which) 
							            {
							                dialog.dismiss();
							                displayAlertDialog(); 
							            	
							            }
							        });
							        alertDialog.show();
							}
							else
							{
								ReasonText = et_Reason.getText().toString();
								if(isOnline())
								 {
									GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(LauncherActivity.this);
								    task.execute();				
								 }
								 else
								 {
									 dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
									 dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
									 dbengine.updateSstattblNoVisitStoreDetails(3);
									 
									//String[] aa= dbengine.fnGetALLDataInfo();
									 String aab=dbengine.fetchReasonDescr();
										but_NoStoreVisit.setText("Reason :"+ReasonText);
									 
									 
									 
									 showNoConnAlert();
						
								 }
								
								
								
							}
					}
					else
					{
						if(isOnline())
						 {
							GetNoStoreVisitForDay task = new GetNoStoreVisitForDay(LauncherActivity.this);
						    task.execute();				
						 }
						 else
						 {
							 dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
							 dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
							 dbengine.updateSstattblNoVisitStoreDetails(3);
							 showNoConnAlert();
				
						 }
						
						 
					}
					
					
				}	
					
				}
			});
			AlertDialog dialog = alert.create();
			dialog.show();
		}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	 {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launcher_new);
		
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
		
		
		 //imei="867290023248924"; //Abhinav sir
		
		
		
		//imei="865897021202836";  // Indrsh Dubey
		
		//Intent passedvals = getIntent();
		//imei = passedvals.getStringExtra("imei");
		
		try {
			getReasonDetail();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		currDate = new Date();
		currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		
		currSysDate = currDateFormat.format(currDate).toString();
		


	    Date date1=new Date();
		sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		passDate = sdf.format(date1).toString();
		fDate = passDate.trim().toString();
		
		
		tv_Route=(TextView)findViewById(R.id.tv_Route);
		imgVw_logout=(ImageView) findViewById(R.id.imgVw_logout);
		
		imgVw_logout.setOnClickListener(new OnClickListener() 
		 {
			@Override
			public void onClick(View v) 

			{
//				imgVw_logout.setBackgroundResource(R.drawable.logout_new);
				imgVw_logout.setImageResource(R.drawable.logout_hover);
				dialogLogout();
			}
		 });
		
		
		if(powerCheck==0)
		   {
		        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
		        wl.acquire();
		   }
		 
		dbengine.open();
		
		//dbengine.maintainPDADate();
		route_name = dbengine.fnGetRouteInfoForDDL();
		route_name_id = dbengine.fnGetRouteIdsForDDL();
		/*String strGetAllActiveRouteId=dbengine.GetAllActiveRouteID();
		String strGetAllActiveRouteDescr=dbengine.GetAllActiveRouteDescr();
		
		dbengine.close();
		
		tv_Route.setText(""+strGetAllActiveRouteDescr);
		rID=strGetAllActiveRouteId;*/
		
		String strGetActiveRouteId=dbengine.GetActiveRouteID();
		dbengine.close();
		if(route_name.length>0)
		{
			for(int i=0;i<route_name.length;i++)
			{
				if(route_name_id[i].equals(strGetActiveRouteId))
				{
					selected=i;
				}
			}
		tv_Route.setText(""+route_name[selected]);
		selected_route_id=route_name_id[selected];
		temp_select_routename=route_name[selected];
		}
		rID=selected_route_id;
		//tv_Route.setText(""+temp_select_routename);
		
		temp_select_routeid=selected_route_id;//=temp_select_routeid;
        rID=temp_select_routeid;
        
        
        
        
       currDate = new Date();
	   currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
	   currSysDate = currDateFormat.format(currDate).toString();
	   String newdateformattoday=fDate;
		
		
		TodaySelection = (RadioButton) findViewById(R.id.radio0);
		TomrSelection = (RadioButton) findViewById(R.id.radio1);
		TodaySelection.setText(getResources().getString(R.string.radioTodayRoute)+newdateformattoday);
		TomrSelection.setText(getResources().getString(R.string.radioChooseDifferentRoute));
		
		
		
		dbengine.open();
		int checkValueForRadioButton=dbengine.GetActiveRouteIDForRadioButton();
		dbengine.close();
		
		if(checkValueForRadioButton==0)
		{
			TodaySelection.setChecked(false);
			TomrSelection.setChecked(true);
		}
		
		radioButtonHandling();
		
		
		but_NoStoreVisit = (Button) findViewById(R.id.but_NoStoreVisit);
		
		//int submitFlag=dbengine.checkAnyDataSubmitORNot(4);
		
		int submitFlag=CommonInfo.AnyVisit;
		
		int check=dbengine.fetchflgHasVisitFromtblNoVisitStoreDetails(""+4);
		if(check==0 && submitFlag==0) // 0 means user did not do any visit or getStore 
		{
			but_NoStoreVisit.setEnabled(true);
		}
		else
		{
			but_NoStoreVisit.setEnabled(false);
			String aab=dbengine.fetchReasonDescr();
			if(ReasonText.equals("") || ReasonText.equals("NA") || ReasonText.equals(null))
			{
				
				but_NoStoreVisit.setText("No Store Visit today");
			}
			else
			{
			but_NoStoreVisit.setText("Reason :"+ReasonText);
			}
		}
		
		
		
		
		but_NoStoreVisit.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub

				displayAlertDialog();
				
				   /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(LauncherActivity.this);
				    alertDialog.setTitle("Info");
			        alertDialog.setMessage("Are you sure,there is no store visit today.");
			        alertDialog.setIcon(R.drawable.info_ico);
			        alertDialog.setCancelable(false);
			        // Setting Positive "Yes" Button
			        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {
			 
			            	dialog.dismiss();
			            	
			            	displayAlertDialog();
			            	
			            }
			        });
			 
			        // Setting Negative "NO" Button
			        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            // Write your code here to invoke NO event
			            	dialog.dismiss();
			            }
			        });
			 
			        // Showing Alert Message
			        alertDialog.show();*/
			 
			}
		});
		
		but_Invoice = (Button) findViewById(R.id.but_Invoice);
		but_Invoice.setEnabled(true);
		but_Invoice.setOnClickListener(new OnClickListener() 
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
               if(isOnline())
				 {
					   try
						  {
						     but_Invoice.setEnabled(false);
							 GetInvoiceForDay task = new GetInvoiceForDay(LauncherActivity.this);
							 task.execute();
						  } 
					   catch (Exception e) 
						  {
							// e.printStackTrace();
						  }
				 }
			   else
				 {
					 showNoConnAlert();
				 }
			}
		});




		 but_distribtrStock=(Button) findViewById(R.id.but_distribtrStock);
		 but_distribtrStock.setEnabled(true);
		 but_distribtrStock.setOnClickListener(new OnClickListener()
		 {
			 @Override
			 public void onClick(View v)
			 {

				 // old code


				/*dbengine.deleteDistributorStockTbles();
				Intent i=new Intent(LauncherActivity.this,DistributorEntryActivity.class);
				i.putExtra("imei", imei);
				i.putExtra("CstmrNodeId", CstmrNodeId);
				i.putExtra("CstomrNodeType", CstomrNodeType);
				i.putExtra("fDate", fDate);
				startActivity(i);
				finish();*/


				 //new code
				 dbengine.open();
				 dbengine.maintainPDADate();
				 String getPDADate=dbengine.fnGetPdaDate();
				 String getServerDate=dbengine.fnGetServerDate();

				 dbengine.close();


				 if(!getServerDate.equals(getPDADate))
				 {
					 if(isOnline())
					 {

						 try
						 {
							 click_but_distribtrStock=1;
							 FullSyncDataNow task = new FullSyncDataNow(LauncherActivity.this);
							 task.execute();
						 }
						 catch(Exception e)
						 {

						 }
					 }
					 else
					 {
						 showNoConnAlertDistributor();
					 }
				 }
				 else
				 {
					 Intent i=new Intent(LauncherActivity.this,DistributorEntryActivity.class);
					 i.putExtra("imei", imei);
					 i.putExtra("CstmrNodeId", CstmrNodeId);
					 i.putExtra("CstomrNodeType", CstomrNodeType);
					 i.putExtra("fDate", fDate);
					 startActivity(i);
					 finish();
				 }


				 //Now  check here IF server date is not equal to PDA Date
				 //IF Check Net Online Starts Here
				 //CREATE XML and Send All Files to Server
				 //Case When All File Sent To Server
				 //Delete Table :-dbengine.deleteDistributorStockTbles();
				 //Now Move To DistributorEntryActivity

				 //IF Check Net Online Ends Here
				 //Else IF Check Net not available Starts Here

				 //Alert Msg Net Not available so Not able to fetch Distributor Todays Stock Details
				 // Else IF Check Net not available Starts Here
				 //Else Starts Here
					/*Intent i=new Intent(LauncherActivity.this,DistributorEntryActivity.class);
					i.putExtra("imei", imei);
					i.putExtra("CstmrNodeId", CstmrNodeId);
					i.putExtra("CstomrNodeType", CstomrNodeType);
					i.putExtra("fDate", fDate);
					startActivity(i);
					finish();*/
				 //Else Starts Here

			 }
		 });


		 but_GetStore = (Button) findViewById(R.id.but_GetStore);
	    but_GetStore.setEnabled(true);
		but_GetStore.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) 
			{
				 if(isOnline())
				 {
					 try 
						{
						 try 
						    {
							 but_GetStore.setEnabled(false);
							 CheckUpdateVersionOnGetStoreClick OnGetStoreClick= new CheckUpdateVersionOnGetStoreClick();
							 OnGetStoreClick.execute();
						    } 
						    catch (Exception e) 
						    {
						     e.printStackTrace();
						   //  System.out.println("Splash Screen error CheckUpdateVersion :"+e);
						    }
						 
						 
											
						} 
					 catch (Exception e) 
					    {
							e.printStackTrace();
						}
				 }
				 else
				 {
					 showNoConnAlert();
				 }
				
				
			}
		});
		
		
		dbengine.open();
		boolean DBCurrentName=dbengine.doesDatabaseExist(this, dbengine.DATABASE_NAME) ;
		dbengine.close();
	
		
		
		
		
		if((DBCurrentName== true))
		{
			
			dbengine.open();
			boolean valGetPrevDateChk=true;//dbengine.GetPrevDateChk();
			dbengine.close();
			//if(( valGetPrevDateChk== true))
			if(( true))
			{
			
				Intent trans2storeList = new Intent(LauncherActivity.this, StoreSelection.class);
				trans2storeList.putExtra("imei", imei);
				trans2storeList.putExtra("userDate", currSysDate);
				trans2storeList.putExtra("pickerDate", fDate);
				trans2storeList.putExtra("rID", rID);
				startActivity(trans2storeList);
				finish();
			
			}
			
			else
			{
				
				if(isOnline())
				 {
					 try 
					    {
					     CheckUpdateVersion cuv= new CheckUpdateVersion();
					     cuv.execute();
					    } 
					    catch (Exception e) 
					    {
					     e.printStackTrace();
					  
					    }
				 }
				
			
			}
			

		}
		else
		{

			/*dbengine.open();
			//dbengine.maintainPDADate();
			String getPDADate=dbengine.fnGetPdaDate();
			String getServerDate=dbengine.fnGetServerDate();
			
			dbengine.close();
			
			if(!getPDADate.equals("") )   //|| !getPDADate.equals("NA") || !getPDADate.equals("Null") || !getPDADate.equals("NULL")
			{
				if(!getServerDate.equals(getPDADate))
				{
					
					showAlertBox("Your Phone  Date is not Up to date.Please Correct the Date.");
					return;
				}
			}*/
			
		}
		
	}

	 
	 @Override
		protected void onStop()
		{
			super.onStop();
			
		}
	 
	@Override
	protected void onResume()
	{
		super.onResume();
		
		
		try
		{

		dbengine.open();
		String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
		dbengine.close();
		System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
		if(!Noti_textWithMsgServerID.equals("Null"))
		{
		StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");
		
		MsgServerID= Integer.parseInt(token.nextToken().trim());
		Noti_text= token.nextToken().trim();
		
		
		if(Noti_text.equals("") || Noti_text.equals("Null"))
		{
			
		}
		else
		{
			
			

			 final AlertDialog builder = new AlertDialog.Builder(LauncherActivity.this).create();
		       

				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        View openDialog = inflater.inflate(R.layout.custom_dialog, null);
		        openDialog.setBackgroundColor(Color.parseColor("#ffffff"));
		        
		        builder.setCancelable(false);
		     	TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
		     	final TextView msg=(TextView)openDialog. findViewById(R.id.msg);
		     	
				final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
				final Button cancel=(Button)openDialog. findViewById(R.id.but_no);
				
				cancel.setVisibility(View.GONE);
			    header_text.setText("Alert");
			     msg.setText(Noti_text);
			     	
			     	ok_but.setText("OK");
			     	
					builder.setView(openDialog,0,0,0,0);

			        ok_but.setOnClickListener(new OnClickListener() 
			        {
						
						@Override
						public void onClick(View arg0) 
						{
							// TODO Auto-generated method stub

							long syncTIMESTAMP = System.currentTimeMillis();
							Date dateobj = new Date(syncTIMESTAMP);
							SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
							String Noti_ReadDateTime = df.format(dateobj);
				    	
							dbengine.open();
							dbengine.updatetblNotificationMstr(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
							dbengine.close();
							
							try
							{
								dbengine.open();
							    int checkleftNoti=dbengine.countNumberOFNotificationtblNotificationMstr();
							    if(checkleftNoti>0)
							    {
								    	String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
										System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
										if(!Noti_textWithMsgServerID.equals("Null"))
										{
											StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");
											
											MsgServerID= Integer.parseInt(token.nextToken().trim());
											Noti_text= token.nextToken().trim();
											
											dbengine.close();
											if(Noti_text.equals("") || Noti_text.equals("Null"))
											{
												
											}
											else
											{
												  msg.setText(Noti_text);
											}
										}
							    	
							    }
								else
								{
									builder.dismiss();
								}
							
							}
							catch(Exception e)
							{
								
							}
				            finally
				            {
				            	dbengine.close();
							   
				            }
			            
						
						}
					});
			        
			       
			      
			 
			     	builder.show();
				
				
				

		
			 
		}
		}
		}
		catch(Exception e)
		{
			
		}
		
		
	
		
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
		
		
		 //imei="867290023248924"; //Abhinav sir
		
		
		//imei="865897021202836";  // Indrsh Dubey
		
		
		
		
		currDate = new Date();
		currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		
		currSysDate = currDateFormat.format(currDate).toString();
		
		
		
       dbengine.open();
		
		//dbengine.maintainPDADate();
		route_name = dbengine.fnGetRouteInfoForDDL();
		route_name_id = dbengine.fnGetRouteIdsForDDL();
		
		String strGetActiveRouteId=dbengine.GetActiveRouteID();
		dbengine.close();
		if(route_name.length>0)
		{
			for(int i=0;i<route_name.length;i++)
			{
				if(route_name_id[i].equals(strGetActiveRouteId))
				{
					selected=i;
				}
			}
		tv_Route.setText(""+route_name[selected]);
		selected_route_id=route_name_id[selected];
		temp_select_routename=route_name[selected];
		}
		rID=selected_route_id;
		//tv_Route.setText(""+temp_select_routename);
		
		temp_select_routeid=selected_route_id;//=temp_select_routeid;
        rID=temp_select_routeid;
		
		}
		
		
		
	public String getDateFormat(String DToChange)
	{
		CompareDate=DToChange;
    	StringTokenizer token=new StringTokenizer(DToChange, "-");
        twoDigitDate=token.nextToken().trim();
    	if(twoDigitDate.toString().trim().length()==1)
    	{
    		twoDigitDate="0"+twoDigitDate;
    	}
    	String twoDigitMonth=token.nextToken().trim();
    	String fourDigitYear=token.nextToken().trim();
    	
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==01)
    	{
    		DToChange=twoDigitDate+"-"+"Jan"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==02)
    	{
    		DToChange=twoDigitDate+"-"+"Feb"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==03)
    	{
    		DToChange=twoDigitDate+"-"+"Mar"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==04)
    	{
    		DToChange=twoDigitDate+"-"+"Apr"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==05)
    	{
    		DToChange=twoDigitDate+"-"+"May"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==06)
    	{
    		DToChange=twoDigitDate+"-"+"Jun"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==07)
    	{
    		DToChange=twoDigitDate+"-"+"Jul"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==(Integer.parseInt(("0"+8).toString().trim())))
    	{
    		DToChange=twoDigitDate+"-"+"Aug"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==(Integer.parseInt(("0"+9).toString().trim())))
    	{
    		DToChange=twoDigitDate+"-"+"Sep"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==10)
    	{
    		DToChange=twoDigitDate+"-"+"Oct"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==11)
    	{
    		DToChange=twoDigitDate+"-"+"Nov"+"-"+fourDigitYear;
    	}
    	if(Integer.parseInt(twoDigitMonth.toString().trim())==12)
    	{
    		DToChange=twoDigitDate+"-"+"Dec"+"-"+fourDigitYear;
    	}
    	return DToChange;
	}
	
	 public void showAlertBox(String msg) 
	  {
	   AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(LauncherActivity.this);
	   alertDialogNoConn.setTitle(getResources().getString(R.string.genTermInformation));
	   alertDialogNoConn.setMessage(msg);
	   
	   alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
	     {
	      public void onClick(DialogInterface dialog, int which) 
	          {
			         dialog.dismiss();
			         dbengine.open();
			         dbengine.reTruncateRouteMstrTbl();
			         dbengine.maintainPDADate();
			         dbengine.reCreateDB();
			         dbengine.close();
			         finish();
			                       
	         }
	     });
	   alertDialogNoConn.setIcon(R.drawable.info_ico);
	   AlertDialog alert = alertDialogNoConn.create();
	   alert.show();
	   
	  }
	
	private class GetInvoiceForDay extends AsyncTask<Void, Void, Void> 
	{		
		ServiceWorker newservice = new ServiceWorker();
		
		ProgressDialog pDialogGetInvoiceForDay;
		
		public GetInvoiceForDay(LauncherActivity activity) 
		{
			pDialogGetInvoiceForDay = new ProgressDialog(activity);
		}
		
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			
			/*dbengine.open();
			dbengine.reTruncateInvoiceButtonTable();
			dbengine.close();
			*/
			
			pDialogGetInvoiceForDay.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetInvoiceForDay.setMessage(getText(R.string.RetrivingDataMsg));
			pDialogGetInvoiceForDay.setIndeterminate(false);
			pDialogGetInvoiceForDay.setCancelable(false);
			pDialogGetInvoiceForDay.setCanceledOnTouchOutside(false);
			pDialogGetInvoiceForDay.show();
			
	       
		}

		@Override
		protected Void doInBackground(Void... params) 
		{

		try {
			
			HashMap<String,String> hmapInvoiceOrderIDandStatus=new HashMap<String, String>();
			 hmapInvoiceOrderIDandStatus=dbengine.fetchHmapInvoiceOrderIDandStatus();
			
			for(int mm = 1; mm < 5  ; mm++)
			{
				if(mm==1)
				{
					newservice = newservice.callInvoiceButtonStoreMstr(getApplicationContext(), fDate, imei, rID,hmapInvoiceOrderIDandStatus);
					
					if(!newservice.director.toString().trim().equals("1"))
					{
						if(chkFlgForErrorToCloseApp==0)
						{
							chkFlgForErrorToCloseApp=1;
						}
					
					}
					
				}
				if(mm==2)
				{  
					newservice = newservice.callInvoiceButtonProductMstr(getApplicationContext(), fDate, imei, rID);
					
					if(!newservice.director.toString().trim().equals("1"))
					{	
						if(chkFlgForErrorToCloseApp==0)
						{
							chkFlgForErrorToCloseApp=1;
						}
						
					}
					
				}
				if(mm==3)
				{  
					newservice = newservice.callInvoiceButtonStoreProductwiseOrder(getApplicationContext(), fDate, imei, rID,hmapInvoiceOrderIDandStatus);
				}
				if(mm==4)
				{  
					dbengine.open();
					int check1=dbengine.counttblCatagoryMstr();
					dbengine.close();
					if(check1==0)
					{
					newservice = newservice.getCategory(getApplicationContext(), imei);
					}
				}
				/*if(mm==5)
				{  
					//System.out.println("Geting Product List: ");
					newservice = newservice.getallProduct(getApplicationContext(), fDate, imei, rID);
					
					if(!newservice.director.toString().trim().equals("1"))
					{	
						if(chkFlgForErrorToCloseApp==0)
						{
							chkFlgForErrorToCloseApp=1;
						}
						
					}
					
				}*/
				
				
			}
			
		
		} catch (Exception e) 
		    {
				Log.i("SvcMgr", "Service Execution Failed!", e);
            }

			finally
			 {
                Log.i("SvcMgr", "Service Execution Completed...");
             }
			
			return null;
		}

		@Override
		protected void onCancelled()
		{
			Log.i("SvcMgr", "Service Execution Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			
			
			if(pDialogGetInvoiceForDay.isShowing()) 
		      {
				pDialogGetInvoiceForDay.dismiss();
			  }
			
			Intent storeIntent = new Intent(LauncherActivity.this, InvoiceStoreSelection.class);
			storeIntent.putExtra("imei", imei);
			storeIntent.putExtra("userDate", currSysDate);
			storeIntent.putExtra("pickerDate", fDate);
			
			
		    if(chkFlgForErrorToCloseApp==0)
			{
				chkFlgForErrorToCloseApp=0;
				startActivity(storeIntent);
				finish();
			}
			else
			{
				AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(LauncherActivity.this);
				alertDialogNoConn.setTitle(getResources().getString(R.string.genTermInformation));
				alertDialogNoConn.setMessage(getResources().getString(R.string.txtInvoicePending));

				alertDialogNoConn.setCancelable(false);
				alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
						new DialogInterface.OnClickListener()
				             {
							   public void onClick(DialogInterface dialog, int which) 
							      {
	                                   dialog.dismiss();
	                                   but_Invoice.setEnabled(true);
	                                   chkFlgForErrorToCloseApp=0;
							      }
						     });
				alertDialogNoConn.setIcon(R.drawable.info_ico);
				AlertDialog alert = alertDialogNoConn.create();
				alert.show();
				return;
			
			}
		}
	}
	 public static boolean deleteNon_EmptyDir(File dir) 
	  {
	        if (dir.isDirectory()) 
	        {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) 
	            {
	                boolean success = deleteNon_EmptyDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        return dir.delete();
	    }

	public void dialogLogout()
	   {
		
		//AlertDialog.Builder alertDialog = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
		   AlertDialog.Builder alertDialog = new AlertDialog.Builder(LauncherActivity.this);

		   alertDialog.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialog.setMessage(R.string.LogoutMsg);
        alertDialog.setPositiveButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener()
             {
                 public void onClick(DialogInterface dialog,int which) 
                     {
                	 
                	 CommonInfo.AnyVisit=0;
                	 
                	 File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);
       				 if (!OrderXMLFolder.exists())
        				{
							OrderXMLFolder.mkdirs();
        				}
                	 
                	 File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);
                     deleteNon_EmptyDir(del);
                     
                     File ImagesFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);
      				 if (!ImagesFolder.exists())
        				{
							ImagesFolder.mkdirs();
        				}
                	 
                	 File del1 = new File(Environment.getExternalStorageDirectory(),  CommonInfo.ImagesFolder);
                     deleteNon_EmptyDir(del1);

						 File TextFileFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.TextFileFolder);
						 if (!ImagesFolder.exists())
						 {
							 ImagesFolder.mkdirs();
						 }

						 File del2 = new File(Environment.getExternalStorageDirectory(),  CommonInfo.TextFileFolder);
						 deleteNon_EmptyDir(del2);
                	 
                	 dbengine.deleteViewAddedStore();
            	         finishAffinity();
                         dialog.dismiss();
                     }
             });
 
         alertDialog.setNegativeButton(R.string.AlertDialogNoButton, new DialogInterface.OnClickListener()
             {
                   public void onClick(DialogInterface dialog, int which) 
                     {
						 imgVw_logout.setImageResource(R.drawable.logout_new);
                         dialog.cancel();
                     }
              });
 
        alertDialog.show();
	}
	
	 private class CheckUpdateVersionOnGetStoreClick extends AsyncTask<Void, Void, Void> 
		{
			@Override
			protected Void doInBackground(Void... params)
			{
			 try 
		 	   {
				   
		           int DatabaseVersion=dbengine.DATABASE_VERSION;
		           int ApplicationID=dbengine.Application_TypeID;
		           ServiceWorker newservice = new ServiceWorker();
		           //newservice = newservice.getAvailableAndUpdatedVersionOfApp(getApplicationContext(), imei,fDate,DatabaseVersion,ApplicationID);
		           newservice = newservice.getAvailableAndUpdatedVersionOfAppNew(getApplicationContext(), imei,fDate,DatabaseVersion,ApplicationID);
		           if(!newservice.director.toString().trim().equals("1"))
		           {	
						if(chkFlgForErrorToCloseApp==0)
						{
							chkFlgForErrorToCloseApp=1;
                  }
					
				  } 
					
		 	  }
		 	   catch (Exception e) 
		 	       {}

		 			finally 
		 			{}
		  
				return null;
			}

			
			@Override
			protected void onPostExecute(Void result) 
			{
				super.onPostExecute(result);
				
				if(chkFlgForErrorToCloseApp==1)   // if Webservice showing exception or not excute complete properly
				{
					chkFlgForErrorToCloseApp=0;

                   Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError),Toast.LENGTH_LONG).show();
                   finish();
				}
				else
				{
					dbengine.open();
					int checkUserAuthenticate=dbengine.FetchflgUserAuthenticated();
				    dbengine.close();
					
					if(checkUserAuthenticate==0)   // 0 means-->New user        1 means-->Exist User
					{
						showAlertForEveryOne(getResources().getString(R.string.phnRegisterError));

						return;
						
					}
					dbengine.open();
					int check=dbengine.FetchVersionDownloadStatus();  // 0 means-->new version install  1 means-->new version not install
					dbengine.close();
					if(check==1)
					{
						showNewVersionAvailableAlert();
					}
					else
					{
						  GetStoresForDay task = new GetStoresForDay(LauncherActivity.this);
						   task.execute();
					}
				
					
					}
			
				}
		}
	
	public void showAlertException(String title,String msg)
	 {
		 AlertDialog.Builder alertDialog = new AlertDialog.Builder(LauncherActivity.this);
		 
	        // Setting Dialog Title
	        alertDialog.setTitle(title);
	 
	        // Setting Dialog Message
	        alertDialog.setMessage(msg);
	        alertDialog.setIcon(R.drawable.error);
	        alertDialog.setCancelable(false);
	        // Setting Positive "Yes" Button

	        alertDialog.setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog,int which) {
	 
	            	new GetStoresForDay(LauncherActivity.this).execute();
	            	dialog.dismiss();
	            }
	        });
	 
	        // Setting Negative "NO" Button

	        alertDialog.setNegativeButton(getResources().getString(R.string.txtCancel), new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            // Write your code here to invoke NO event
	            	dialog.dismiss();
	            }
	        });
	 
	        // Showing Alert Message
	        alertDialog.show();
	 }
	
	
	private class GetNoStoreVisitForDay extends AsyncTask<Void, Void, Void> 
	{		
		ServiceWorker newservice = new ServiceWorker();
		
		ProgressDialog pDialogGetNoStoreVisitForDay;
		
		public GetNoStoreVisitForDay(LauncherActivity activity) 
		{
			pDialogGetNoStoreVisitForDay = new ProgressDialog(activity);
		}
		
		
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			
			
			pDialogGetNoStoreVisitForDay.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetNoStoreVisitForDay.setMessage(getText(R.string.RetrivingDataMsg));
			pDialogGetNoStoreVisitForDay.setIndeterminate(false);
			pDialogGetNoStoreVisitForDay.setCancelable(false);
			pDialogGetNoStoreVisitForDay.setCanceledOnTouchOutside(false);
			pDialogGetNoStoreVisitForDay.show();
			
	       
		}

		@Override
		protected Void doInBackground(Void... params) 
		{

		try {
			
			
			for(int mm = 1; mm < 2  ; mm++)
			{
				if(mm==1)
				{
					/*newservice = newservice.getCallspSaveReasonForNoVisit(getApplicationContext(), fDate, imei, ReasonId,ReasonText);
					
					if(!newservice.director.toString().trim().equals("1"))
					{
						if(chkFlgForErrorToCloseApp==0)
						{
							chkFlgForErrorToCloseApp=1;
						}
					
					}*/
					
				}
				
				
				
			}
			
		
		} catch (Exception e) 
		    {
				Log.i("SvcMgr", "Service Execution Failed!", e);
            }

			finally
			 {
                Log.i("SvcMgr", "Service Execution Completed...");
             }
			
			return null;
		}

		@Override
		protected void onCancelled()
		{
			Log.i("SvcMgr", "Service Execution Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			super.onPostExecute(result);
			
			
			if(pDialogGetNoStoreVisitForDay.isShowing()) 
		      {
				pDialogGetNoStoreVisitForDay.dismiss();
			  }
			
			 if(chkFlgForErrorToCloseApp==0)
			{
				 dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
				 dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
				 
				 dbengine.updateSstattblNoVisitStoreDetails(3);
				 String aab=dbengine.fetchReasonDescr();
					but_NoStoreVisit.setText("Reason :"+ReasonText);
			}
			else
			{
				
				
				if(RowId==0)
				{
					 dbengine.updateReasonIdAndDescrtblNoVisitStoreDetails(ReasonId,ReasonText);
					 dbengine.updateCurDatetblNoVisitStoreDetails(fDate);
					 dbengine.updateSstattblNoVisitStoreDetails(3);
					 String aab=dbengine.fetchReasonDescr();
						but_NoStoreVisit.setText("Reason :"+ReasonText);
					 but_NoStoreVisit.setEnabled(false);
					 
				}
				else
				{
					 dbengine.updateSstattblNoVisitStoreDetailsAfterSync(4);
					 String aab=dbengine.fetchReasonDescr();
						but_NoStoreVisit.setText("Reason :"+ReasonText);
					 but_NoStoreVisit.setEnabled(false);
					 
				}
			}
		}
	}



	private class FullSyncDataNow extends AsyncTask<Void, Void, Void>
	{


		ProgressDialog pDialogGetStores;
		public FullSyncDataNow(LauncherActivity activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getResources().getString(R.string.genTermLoadData));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();


		}

		@Override

		protected Void doInBackground(Void... params)
		{

			int Outstat=3;

			long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = df.format(dateobj);



			dbengine.open();
			String presentRoute=dbengine.GetActiveRouteIDForDistributor();
			dbengine.close();

			SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);
			newfullFileName=imei+"."+presentRoute+"."+ df1.format(dateobj);

			try
			{

				File MeijiDistributorEntryXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorStockXMLFolder);
				if (!MeijiDistributorEntryXMLFolder.exists())
				{
					MeijiDistributorEntryXMLFolder.mkdirs();
				}

				int checkNoFiles=dbengine.counttblDistributorSavedData();
				if(checkNoFiles==1)
				{
					String routeID=dbengine.GetActiveRouteIDSunil();
					DA.open();
					DA.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);
					DA.close();

				}

				dbengine.open();
				dbengine.maintainPDADate();
				String getPDADate=dbengine.fnGetPdaDate();
				String getServerDate=dbengine.fnGetServerDate();

				dbengine.close();

				//dbengine.deleteDistributorStockTbles();
				if(!getServerDate.equals(getPDADate))
				{
					dbengine.deleteDistributorStockTbles();
				}



			}
			catch (Exception e)
			{

				e.printStackTrace();
				if(pDialogGetStores.isShowing())
				{
					pDialogGetStores.dismiss();
				}
			}
			return null;
		}

		@Override
		protected void onCancelled()
		{

		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			if(pDialogGetStores.isShowing())
			{
				pDialogGetStores.dismiss();
			}

			try
			{

				task2 = new SyncXMLfileData(LauncherActivity.this);
				task2.execute();
			}
			catch(Exception e)
			{

			}


		}
	}




	private class SyncXMLfileData extends AsyncTask<Void, Void, Integer>
	{



		public SyncXMLfileData(LauncherActivity activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();


			File MeijiIndirectSFAxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorStockXMLFolder);

			if (!MeijiIndirectSFAxmlFolder.exists())
			{
				MeijiIndirectSFAxmlFolder.mkdirs();
			}

			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

			pDialogGetStores.setMessage(getResources().getString(R.string.genTermLoadData));

			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();

		}

		@Override
		protected Integer doInBackground(Void... params)
		{


			// This method used for sending xml from Folder without taking records in DB.

			// Sending only one xml at a times

			File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorStockXMLFolder);

			// check number of files in folder
			String [] AllFilesName= checkNumberOfFiles(del);


			if(AllFilesName.length>0)
			{
				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);


				for(int vdo=0;vdo<AllFilesName.length;vdo++)
				{
					String fileUri=  AllFilesName[vdo];


					System.out.println("Sunil Again each file Name :" +fileUri);

					if(fileUri.contains(".zip"))
					{
						File file = new File(fileUri);
						file.delete();
					}
					else
					{
						String f1=Environment.getExternalStorageDirectory().getPath()+"/"+CommonInfo.DistributorStockXMLFolder+"/"+fileUri;
						System.out.println("Sunil Again each file full path"+f1);
						try
						{
							upLoad2ServerXmlFiles(f1,fileUri);
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}
			else
			{

			}



			// pDialogGetStores.dismiss();

			return serverResponseCode;
		}

		@Override
		protected void onCancelled()
		{
		//	Log.i("SyncMasterForDistributor", "Sync Cancelled");
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			super.onPostExecute(result);
			if(!isFinishing())
			{

			//	Log.i("SyncMasterForDistributor", "Sync cycle completed");


				if(pDialogGetStores.isShowing())
				{
					pDialogGetStores.dismiss();
				}




			}
			if(click_but_distribtrStock==1)
			{
				click_but_distribtrStock=0;

				Intent i=new Intent(LauncherActivity.this,DistributorEntryActivity.class);
				i.putExtra("imei", imei);
				i.putExtra("CstmrNodeId", CstmrNodeId);
				i.putExtra("CstomrNodeType", CstomrNodeType);
				i.putExtra("fDate", fDate);
				startActivity(i);
				finish();
			}
		}





	}


	public  int upLoad2ServerXmlFiles(String sourceFileUri,String fileUri)
	{

		fileUri=fileUri.replace(".xml", "");

		String fileName = fileUri;
		String zipFileName=fileUri;

		String newzipfile = Environment.getExternalStorageDirectory() + "/"+CommonInfo.DistributorStockXMLFolder+"/" + fileName + ".zip";
		///storage/sdcard0/PrabhatDirectSFAXml/359648069495987.2.21.04.2016.12.44.02.zip

		sourceFileUri=newzipfile;

		xmlForWeb[0] = Environment.getExternalStorageDirectory() + "/"+CommonInfo.DistributorStockXMLFolder+"/" + fileName + ".xml";
		//[/storage/sdcard0/PrabhatDirectSFAXml/359648069495987.2.21.04.2016.12.44.02.xml]

		try
		{
			zip(xmlForWeb,newzipfile);
		}
		catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			//java.io.FileNotFoundException: /359648069495987.2.21.04.2016.12.44.02: open failed: EROFS (Read-only file system)
		}


		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;


		File file2send = new File(newzipfile);

		String urlString = CommonInfo.DistributorSyncPath.trim()+"?CLIENTFILENAME=" + zipFileName;

		try {

			// open a URL connection to the Servlet
			FileInputStream fileInputStream = new FileInputStream(file2send);
			URL url = new URL(urlString);

			// Open a HTTP  connection to  the URL
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("zipFileName", zipFileName);

			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
					+ zipFileName + "\"" + lineEnd);

			dos.writeBytes(lineEnd);

			// create a buffer of  maximum size
			bytesAvailable = fileInputStream.available();

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0)
			{
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();
			String serverResponseMessage = conn.getResponseMessage();

			Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

			if(serverResponseCode == 200)
			{
						  /* dbengine.open();
						   dbengine.upDateXMLFileFlag(fileUri, 4);
						   dbengine.close();*/

				//new File(dir, fileUri).delete();
				syncFLAG=1;

				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
				SharedPreferences.Editor editor = pref.edit();
				// editor.remove(xmlForWeb[0]);
				editor.putString(fileUri, ""+4);
				editor.commit();

				String FileSyncFlag=pref.getString(fileUri, ""+1);

				delXML(xmlForWeb[0].toString());
						   		/*dbengine.open();
					            dbengine.deleteXMLFileRow(fileUri);
					            dbengine.close();*/

			}
			else
			{
				syncFLAG=0;
			}

			//close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex)
		{
			ex.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}




		return serverResponseCode;

	}

	public void delXML(String delPath)
	{
		File file = new File(delPath);
		file.delete();
		File file1 = new File(delPath.toString().replace(".xml", ".zip"));
		file1.delete();
	}

	public static void zip(String[] files, String zipFile) throws IOException
	{
		BufferedInputStream origin = null;
		final int BUFFER_SIZE = 2048;

		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
		try
		{
			byte data[] = new byte[BUFFER_SIZE];

			for (int i = 0; i < files.length; i++)
			{
				FileInputStream fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER_SIZE);
				try
				{
					ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
					out.putNextEntry(entry);
					int count;
					while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1)
					{
						out.write(data, 0, count);
					}
				}
				finally
				{
					origin.close();
				}
			}
		}
		finally
		{
			out.close();
		}
	}

	public static String[] checkNumberOfFiles(File dir)
	{
		int NoOfFiles=0;
		String [] Totalfiles = null;

		if (dir.isDirectory())
		{
			String[] children = dir.list();
			NoOfFiles=children.length;
			Totalfiles=new String[children.length];

			for (int i=0; i<children.length; i++)
			{
				Totalfiles[i]=children[i];
			}
		}
		return Totalfiles;
	}

	public void showNoConnAlertDistributor()
	{
		AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(LauncherActivity.this);
		alertDialogNoConn.setTitle(R.string.genTermNoDataConnection);
		alertDialogNoConn.setMessage(R.string.genTermNoDataConnectionFullMsg);
		alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						// finish();
					}
				});
		alertDialogNoConn.setIcon(R.drawable.error_ico);
		AlertDialog alert = alertDialogNoConn.create();
		alert.show();

	}





}
