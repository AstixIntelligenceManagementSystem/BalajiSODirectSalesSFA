package project.astix.com.balajisosfadirectsales;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.astix.Common.CommonInfo;


public class SyncMaster extends Activity
{

	public Timer timerForDataSubmission;
	public	MyTimerTaskForDataSubmission myTimerTaskForDataSubmission;



	// New Sync way

	 SyncImageData task1;
	 SyncXMLfileData task2;

	 private String FilePathStrings;
     public String fnameIMG;

	 public String UploadingImageName;

	 private File[] listFile;
	 public  File fileintial;
	private File[] listFileDSRSignature;
	public  File fileintialDSRSignature;
	 public String routeID="0";
	 String xmlFileName;
	 int serverResponseCode = 0;












	Timer timer,timer2;
	String progressMsg;
	/* MyTimerTask myTimerTask;
	 MyTimerTask2 mytimerTask2;
	 SyncPROdata task;*/
	 ProgressDialog pDialogGetStores;


	public String[] xmlForWeb = new String[1];

	//ProgressDialog dialog = null;

	//public TextView chkString;
	HttpEntity resEntity;
	private SyncMaster _activity;

	private static final String DATASUBDIRECTORY = "NMPphotos";


	public int syncFLAG = 0;
	public int res_code;
	public String zipFileName;
	ProgressDialog PDpicTasker;
	public String whereTo;
	public int IMGsyOK = 0;
	ProgressDialog pDialog2;
	InputStream inputStream;



	// SyncTextFileTaster task1;
	 public  File dir;

	PRJDatabase db = new PRJDatabase(this);
	PRJDatabase dbengine = new PRJDatabase(this);

	public void showSyncError() {
		AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(
				SyncMaster.this);
		alertDialogSyncError.setTitle(getText(R.string.genTermSyncErrornew));
		alertDialogSyncError.setCancelable(false);
		/*alertDialogSyncError
				.setMessage("Sync was not successful! Please try again.");*/
		if(whereTo.contentEquals("11"))
		{
			alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsgDayEndOrChangeRoute));
		}
		else
		{
			alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsg));
		}
		alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {


						dialog.dismiss();
						if(whereTo.contentEquals("DayStart"))
						{
							finish();
						}
						else {

							Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
							startActivity(submitStoreIntent);
							finish();
						}

					}
				});
		alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

		AlertDialog alert = alertDialogSyncError.create();
		alert.show();
		// alertDialogLowbatt.show();
	}
	public void showSyncErrorStart() {
		AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(SyncMaster.this);
		alertDialogSyncError.setTitle(getText(R.string.genTermSyncErrornew));
		alertDialogSyncError.setCancelable(false);
		alertDialogSyncError.setMessage(getText(R.string.genTermSyncErrorFullMsg));
		alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {


						dialog.dismiss();
						Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
						startActivity(submitStoreIntent);
						finish();


					}
				});
		alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

		AlertDialog alert = alertDialogSyncError.create();
		alert.show();
		// alertDialogLowbatt.show();
	}
	public void showSyncSuccessStart()
	{
		AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(SyncMaster.this);
		alertDialogSyncOK.setTitle(getText(R.string.genTermInformation));
		alertDialogSyncOK.setCancelable(false);
		if(StoreSelection.flgChangeRouteOrDayEnd==3)
		{
			alertDialogSyncOK.setMessage(getText(R.string.syncAlertStoreQuotationOKMsg));
		}
		else
		{
			alertDialogSyncOK.setMessage(getText(R.string.syncAlertOKMsg));
		}

		alertDialogSyncOK.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener()
		         {
					public void onClick(DialogInterface dialog, int which)
					{

					dialog.dismiss();

					db.open();
					//System.out.println("Indubati flgChangeRouteOrDayEnd :"+StoreSelection_Old.flgChangeRouteOrDayEnd);
					/*if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
					{
						db.reTruncateRouteTbl();
					}*/


					db.reCreateDB();
					db.close();

					Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
					startActivity(submitStoreIntent);
					finish();
					/*destroyNcleanup(1);
					imgs = null;
					uComments.clear();*/

				//	finish();


					}
				});
		alertDialogSyncOK.setIcon(R.drawable.info_ico);

		AlertDialog alert = alertDialogSyncOK.create();
		alert.show();

	}



	public  int upLoad2Server(String sourceFileUri,String fileUri)
	{

		     String fileName = fileUri;
		   //  String upLoadServerUri ="http://103.16.141.16/ReadTxtFileForFerrero/Default.aspx?CLIENTFILENAME="+fileUri;



			   String upLoadServerUri = CommonInfo.OrderTextSyncPath.trim()+"?CLIENTFILENAME=" +fileUri;


		   //  String upLoadServerUri ="http://103.16.141.16/ReadTxtFileForPrabhatFDPSFALive/Default.aspx?CLIENTFILENAME="+fileUri;



		    // http://103.16.141.16/ReadTxtFileForPrabhatFDPSFALive/Default.aspx

		      int serverResponseCode = 0;
		      HttpClient httpclient = null;
			  HttpURLConnection conn = null;
			  DataOutputStream dos = null;
			  DataInputStream inStream = null;
			  String lineEnd = "\r\n";
			  String twoHyphens = "--";
			  String boundary = "*****";
			  int bytesRead, bytesAvailable, bufferSize;
			  byte[] buffer;
			  int maxBufferSize = 1024;
			  String responseFromServer = "";
		      String Title="MyVedio";


			  File sourceFile = new File(sourceFileUri);
			  if (!sourceFile.isFile())
			  {
				  System.out.println("S6 I M in the upLoad2Server End"+"Source File Does not exist");
			      Log.e("Huzza", "Source File Does not exist");
			      return 0;
			  }

			  try
			      { 	// open a URL connection to the Servlet
				  		FileInputStream fileInputStream = new FileInputStream(sourceFile);
				  		//  System.out.println("End_fileInputStream"+fileInputStream);



			   URL url = new URL(upLoadServerUri);
			 //  System.out.println("End_fileInputStream_url"+url);
			   conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
			 //  System.out.println("End_fileInputStream_url_conn"+conn);
			   conn.setDoInput(true); // Allow Inputs
			   conn.setDoOutput(true); // Allow Outputs
			   conn.setUseCaches(false); // Don't use a Cached Copy
			   conn.setRequestMethod("POST");
			   conn.setRequestProperty("Connection", "Keep-Alive");
			   conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			   conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			   conn.setRequestProperty("uploaded_file", fileName);
			  // System.out.println("uploaded_file"+fileName);
			   dos = new DataOutputStream(conn.getOutputStream());
			  // System.out.println("End_fileInputStream_url_dos"+dos);
			   dos.writeBytes(twoHyphens + boundary + lineEnd);
			  // System.out.println("FileName_Niti"+fileName);
			   dos.writeBytes("Content-Disposition: form-data; name=\"title\"" + lineEnd);
			   dos.writeBytes(lineEnd);
			   dos.writeBytes(Title);
			   dos.writeBytes(lineEnd);
			   dos.writeBytes(twoHyphens + boundary + lineEnd);
			   dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
			   dos.writeBytes(lineEnd);

			   bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size
			  // Log.i("Huzza", "Initial .available : " + bytesAvailable);

			   bufferSize = Math.min(bytesAvailable, maxBufferSize);
			   buffer = new byte[bufferSize];

			   // read file and write it into form...
			   bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			  // System.out.println("End_fileInputStream_url_dos_bytesRead"+bytesRead);
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
			   fileInputStream.close();
			   // Responses from the server (code and message)
			   serverResponseCode = conn.getResponseCode();
			   if(serverResponseCode==200)
			    {
				// System.out.println("Ajay delete file from sdcard"+ new File(dir, fileUri).delete());
				   dbengine.open();
				   dbengine.upDateTextFileFlag(fileUri, 1);
				   dbengine.close();
				   		new File(dir, fileUri).delete();
			           // System.out.println("Ajay file deleted");
			         //   dbengine.open();
			           // System.out.println("Ajay  delete file from our database start");
				   		dbengine.open();
			            dbengine.deleteTextFileRow(fileUri);
			            dbengine.close();
			           // System.out.println("Ajay delete file from our database end");
			          //  dbengine.close();


			        //}
			     //   nitishStart();
			    }


			  // System.out.println("Ajay Server return serverResponseCode :"+serverResponseCode);
			   String serverResponseMessage = conn.getResponseMessage();

			  // Log.i("Upload file to server", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

			   // close streams
			  // Log.i("Upload file to server", fileName + " File is written");

			   dos.flush();
			   dos.close();
			  } catch (MalformedURLException ex) {
			   ex.printStackTrace();
			   Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
			  } catch (Exception e) {
			   e.printStackTrace();
			  }
			//this block will give the response of upload link
			  try {
			   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			   String line;
			   while ((line = rd.readLine()) != null)
			   {
				  // System.out.println("End_fileInputStream_url_dos_bytesRead_line"+line);

			    Log.i("Huzza", "RES Message: " + line);
			   }
			   rd.close();
			  } catch (IOException ioex) {
			   Log.e("Huzza", "error: " + ioex.getMessage(), ioex);
			  }
			  System.out.println("End_fileInputStream_url_dos_bytesRead_serverResponseCode"+serverResponseCode);

			  return serverResponseCode;  // like 200 (Ok)

			 }



	public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
	{

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length�..
        //System.out.println("contentLength : " + contentLength);
        //Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0)
        {
        }
        else
        {
               byte[] data = new byte[512];
               int len = 0;
               try
               {
                   while (-1 != (len = inputStream.read(data)) )
                   {
                       buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer�..
                   }
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               try
               {
                   inputStream.close(); // closing the stream�..
               }
               catch (IOException e)
               {
                   e.printStackTrace();
               }
               res = buffer.toString();     // converting stringbuffer to string�..

               //System.out.println("Result : " + res);
               //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
               ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
   }

	public void sysncStart()
	{
		/*if(isOnline()){*/

			 String[] fp2s; // = "/mnt/sdcard/NMPphotos/1539_24-05-2013_1.jpg";

				try {
					db.open();
					String[] sySTidS = db.getStoreIDTblSelectedStoreIDinChangeRouteCase();
					//String date= db.GetPickerDate();
					db.close();
					/*for(int chkCountstore=0; chkCountstore < sySTidS.length;chkCountstore++)
					{
						db.open();
						int syUPlimit = db.getExistingPicNos(sySTidS[chkCountstore].toString());
						String[] syP2F = db.getImgsPath(sySTidS[chkCountstore].toString());
						String[] syC4P = db.getImgsComment(sySTidS[chkCountstore].toString());

						String actRid = db.GetActiveRouteID();
						db.close();

						//fp2s = new String[syUPlimit];
						fp2s = new String[5];

						for(int syCOUNT = 0; syCOUNT < syUPlimit; syCOUNT++){
							//int arrCOUNT = 0;
							fp2s[0] = syP2F[syCOUNT];
							fp2s[1] = syC4P[syCOUNT];
							fp2s[2] = sySTidS[chkCountstore];
							fp2s[3] = date;
							fp2s[4] = actRid;
							//
							new SyncImgTasker().execute(fp2s).get();

							if(IMGsyOK == 1){
								//System.out.println("Breaking here..error occured! XoX");
								break;
							}
						}


					}*/
					/*if(IMGsyOK == 1){
						IMGsyOK = 0;

						showSyncErrorStart();
					}
					else{


					showSyncSuccessStart();
					}*/
					showSyncSuccessStart();



				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					db.close();
					e.printStackTrace();
				} /*catch (ExecutionException e) {
					// TODO Auto-generated catch block
					db.close();
					e.printStackTrace();
				}*/

				/*}
			else{

				Toast.makeText(getApplicationContext(), "No Active Internet Connection! \n\nPlease check your Internet Connectivity & Try Again", Toast.LENGTH_SHORT).show();
			}*/
	}


	 public static boolean deleteFolderFiles(File path)
	 {

	/*  // Check if file is directory/folder
	  if(file.isDirectory())
	  {
	  // Get all files in the folder
	  File[] files=file.listFiles();

	   for(int i=0;i<files.length;i++)
	   {

	   // Delete each file in the folder
	 //  `	(files[i]);
		   file.delete();
	   }

	  // Delete the folder


	  }*/

		 if( path.exists() )
		 {
	            File[] files = path.listFiles();
	            for(int i=0; i<files.length; i++)
	            {
	                if(files[i].isDirectory())
	                {
	                	deleteFolderFiles(files[i]);
	                }
	                else
	                {
	                    files[i].delete();
	                }
	            }
	      }
	        return(path.delete());

	 }
	public void showSyncSuccess()
	{
		AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(SyncMaster.this);
		alertDialogSyncOK.setTitle(getText(R.string.genTermInformation));
		alertDialogSyncOK.setCancelable(false);
		if(whereTo.contentEquals("WarehouseMap"))
		{
			alertDialogSyncOK.setMessage(getText(R.string.SubmitDistrbtr));
		}
		else if(StoreSelection.flgChangeRouteOrDayEnd==3)
		{
			alertDialogSyncOK.setMessage(getText(R.string.syncAlertStoreQuotationOKMsg));
		}
		else
		{
			alertDialogSyncOK.setMessage(getText(R.string.syncAlertOKMsg));
		}
		alertDialogSyncOK.setNeutralButton(getText(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
		    {
					public void onClick(DialogInterface dialog, int which)
					{

						//db.deleteAllSubmitDataToServer(4);
						CommonInfo.AnyVisit=1;
						dialog.dismiss();

						int flag=0;
						String[] imageToBeDeletedFromSdCard=db.deletFromSDcCardPhotoValidationBasedSstat("4");
			    		if(!imageToBeDeletedFromSdCard[0].equals("No Data"))
			    		{
				    			for(int i=0;i<imageToBeDeletedFromSdCard.length;i++)
				    		     {
				    				flag=1;

				    				//String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageToBeDeletedFromSdCard[i].toString().trim();
									 String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageToBeDeletedFromSdCard[i].toString().trim();

									 File fdelete = new File(file_dj_path);
							        if (fdelete.exists())
							        {
							            if (fdelete.delete())
							            {
							                Log.e("-->", "file Deleted :" + file_dj_path);
							                callBroadCast();
							            }
							            else
							            {
							                Log.e("-->", "file not Deleted :" + file_dj_path);
							            }
							        }
				    			}
			    		}

						flag=0;
						String[] imageToBeDeletedNewStoreFromSdCard=db.deletFromSDcCardPhotoNewStoreValidationBasedSstat("4");
						if(!imageToBeDeletedNewStoreFromSdCard[0].equals("No Data"))
						{
							for(int i=0;i<imageToBeDeletedNewStoreFromSdCard.length;i++)
							{
								flag=1;

								String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageToBeDeletedNewStoreFromSdCard[i].toString().trim();

								File fdelete = new File(file_dj_path);
								if (fdelete.exists())
								{
									if (fdelete.delete())
									{
										//Log.e("-->", "file Deleted :" + file_dj_path);
										callBroadCast();
									}
									else
									{
										//Log.e("-->", "file not Deleted :" + file_dj_path);
									}
								}
							}
						}










						// finishing activity & stepping back
						if(whereTo.contentEquals("WarehouseMap"))
						{
							Intent submitStoreIntent = new Intent(SyncMaster.this, AllButtonActivity.class);
							startActivity(submitStoreIntent);
							finish();
						}
						else if(whereTo.contentEquals("11"))
						{
							int chkSct=0;
							/*db.open();
							chkSct=db.getExistingPicNosOnRemStoreOnChangeRoute();
							db.close();*/
							if(chkSct>0)
							{
								whereTo = "";
								//sysncStart();

								/*File dirORIGimg = new File(Environment.getExternalStorageDirectory(),DATASUBDIRECTORY);
								deleteFolderFiles(dirORIGimg);*/


							}
							else
							{
								whereTo = "";

								/*File dirORIGimg = new File(Environment.getExternalStorageDirectory(),DATASUBDIRECTORY);
								deleteFolderFiles(dirORIGimg);*/


								db.open();
								if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
								{
									db.reTruncateRouteTbl();
									//db.reTruncateRouteMstrTbl();
								}
								db.reCreateDB();
								db.close();

								Intent submitStoreIntent = new Intent(SyncMaster.this, SplashScreen.class);
								startActivity(submitStoreIntent);
								finish();

								/*Intent i = new Intent(SyncMaster.this, WebViewActivity.class);
								i.putExtra("comeFrom","StockIn");
								startActivity(i);
								finish();*/

								//if(whereTo.contentEquals("11"))


							}

						}

						else
						{
						Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
						startActivity(submitStoreIntent);
						finish();
						}
						//finish();
						//SyncMaster.this.finish();
					}
				});
		alertDialogSyncOK.setIcon(R.drawable.info_ico);

		AlertDialog alert = alertDialogSyncOK.create();
		alert.show();
		// alertDialogLowbatt.show();
	}

	//
	public void delXML(String delPath)
	{
		//System.out.println("Deleting..: " + delPath);
		File file = new File(delPath);
	    file.delete();
	    File file1 = new File(delPath.toString().replace(".xml", ".zip"));
	     file1.delete();
	}
	//
	//
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





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync_master);

		_activity = this;
		//System.out.println("Induwati 0");
		Intent syncIntent = getIntent();
		xmlForWeb[0] = syncIntent.getStringExtra("xmlPathForSync");
		zipFileName = syncIntent.getStringExtra("OrigZipFileName");
		whereTo = syncIntent.getStringExtra("whereTo");

		try
		{
			try
			{
			 task1 = new SyncImageData(SyncMaster.this);
			 task1.execute();

				if (timerForDataSubmission != null) {
					timerForDataSubmission.cancel();
					timerForDataSubmission = null;
				}

				timerForDataSubmission = new Timer();
				myTimerTaskForDataSubmission = new MyTimerTaskForDataSubmission();
				timerForDataSubmission.schedule(myTimerTaskForDataSubmission, 300000);
			}
			catch(Exception e)
			{

			}

		    // task = new SyncPROdata(SyncMaster.this);
			// task.execute();

				/*if(whereTo.equals("Regular"))
				{
				  if (timer!=null)
				      {
				       timer.cancel();
				       timer = null;
				      }
				      timer = new Timer();
				      myTimerTask = new MyTimerTask();

				      timer.schedule(myTimerTask, 30000);
				      if(timer2!=null)
				      {
				    	  timer2.cancel();
					       timer2 = null;
				      }

				}*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*class MyTimerTask extends TimerTask {

	    @Override
	    public void run() {

	     runOnUiThread(new Runnable(){


	      @Override
	      public void run() {

	    	  if(task.getStatus()==AsyncTask.Status.RUNNING)
	    	  {
	    		//last...........

	    		  task.cancel(true);
	    		  System.out.println("cancleeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
	    		  pDialogGetStores.dismiss();

	    		  //  Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
				//	startActivity(submitStoreIntent);
				//	finish();


	    		//  pDialogGetStores.dismiss();
	    		//  progressMsg="Internet is slow,submitting again";
	    		// new SyncPROdata(SyncMaster.this).execute();
	    		  AlertDialog.Builder alertDialogSyncOKk = new AlertDialog.Builder(SyncMaster.this);
		    		 alertDialogSyncOKk.setTitle("Internet issue");
		    		 alertDialogSyncOKk.setCancelable(false);
		    		 alertDialogSyncOKk.setMessage(getText(R.string.syncAlertErrMsggg));
		    		 alertDialogSyncOKk.setNeutralButton("OK",
		    					new DialogInterface.OnClickListener() {
		    						public void onClick(DialogInterface dialog, int which) {

		    					dialog.dismiss();
		    					// progressMsg="Internet is slow,submitting again";
		    			    	//	new SyncPROdata(SyncMaster.this).execute();
		    					ProductOrderSearch pd=new ProductOrderSearch();
	    						Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
	    						startActivity(submitStoreIntent);
	    						finish();


		    						}
		    				});
		    		 alertDialogSyncOKk.setIcon(R.drawable.error_info_ico);

		    			AlertDialog alert = alertDialogSyncOKk.create();
		    			alert.show();
	    	  }

	      }});
	    }

	   }
	class MyTimerTask2 extends TimerTask {

	    @Override
	    public void run() {

	     runOnUiThread(new Runnable(){


	      @Override
	      public void run() {

	    	  if(task.getStatus()==AsyncTask.Status.RUNNING)

	    	  {

	    		 task.cancel(true);
	    		 pDialogGetStores.dismiss();
	    		  //  Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
				//		startActivity(submitStoreIntent);
				//		finish();



	    		 AlertDialog.Builder alertDialogSyncOKk = new AlertDialog.Builder(SyncMaster.this);
	    		 alertDialogSyncOKk.setTitle("Information");
	    		 alertDialogSyncOKk.setCancelable(false);
	    		 alertDialogSyncOKk.setMessage(getText(R.string.syncAlertErrMsggg));
	    		 alertDialogSyncOKk.setNeutralButton("OK",
	    					new DialogInterface.OnClickListener() {
	    						public void onClick(DialogInterface dialog, int which) {

	    						dialog.dismiss();

	    					//	db.open();
	    						//System.out.println("Indubati flgChangeRouteOrDayEnd :"+StoreSelection_Old.flgChangeRouteOrDayEnd);
	    						if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
	    						{
	    							db.reTruncateRouteTbl();
	    						}


	    					//	db.reCreateDB();
	    					//	db.close();
	    						ProductOrderSearch pd=new ProductOrderSearch();
	    						Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
	    						startActivity(submitStoreIntent);
	    						finish();
	    						destroyNcleanup(1);
	    						imgs = null;
	    						uComments.clear();

	    					//	finish();


	    						}
	    					});
	    		alertDialogSyncOKk.setIcon(R.drawable.info_ico);

	    			AlertDialog alert = alertDialogSyncOKk.create();
	    			alert.show();

	    		//  progressMsg="Internet is slow,submitting againsssssssssss";
	    		//  new SyncPROdata(SyncMaster.this).execute();
	    	  }

	      }});
	    }

	   }*/

	private class SyncImageData extends AsyncTask<Void, Void, Void>
	{
		 String[] fp2s;
		 String[] NoOfOutletID;

		public SyncImageData(SyncMaster activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		 protected void onPreExecute()
		  {
			    super.onPreExecute();

			    dbengine.open();
			    routeID=dbengine.GetActiveRouteID();
				dbengine.close();


			    pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			  //  pDialogGetStores.setMessage("Uploading Data...");
			    if(StoreSelection.flgChangeRouteOrDayEnd==1)
				{
				 pDialogGetStores.setMessage(getResources().getString(R.string.txtEndingDay));
				}else if(StoreSelection.flgChangeRouteOrDayEnd==2)
				{
				 pDialogGetStores.setMessage(getResources().getString(R.string.txtChangeRoute));
				}else if(StoreSelection.flgChangeRouteOrDayEnd==3)
				{
					 pDialogGetStores.setMessage(getResources().getString(R.string.txtSubmitQuoteDetail));
					}
				else if(DayStartActivity.flgDaySartWorking==1)
				{
					pDialogGetStores.setMessage(getResources().getString(R.string.submittingDayStart));

				}
				else
				{
				pDialogGetStores.setMessage(getResources().getString(R.string.SubmittingOrderDetails));
				}
				pDialogGetStores.setIndeterminate(false);
				pDialogGetStores.setCancelable(false);
				pDialogGetStores.setCanceledOnTouchOutside(false);
				pDialogGetStores.show();


				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				{

				}
				else
				{
					// Locate the image folder in your SD Card
					fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
					// Create a new folder if no folder named SDImageTutorial exist
					fileintial.mkdirs();


					/*// Locate the image folder in your SD Card
					fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
					// Create a new folder if no folder named SDImageTutorial exist
					fileintial.mkdirs();*/
				}


				if (fileintial.isDirectory())
				   {
					   listFile = fileintial.listFiles();
				   }





	        }

			@Override
			protected Void doInBackground(Void... params)
			{


				// Sync POS Images

				  try
			        {

					  try
						{
							db.open();
							NoOfOutletID = db.getAllStoreIDIntblStoreMaterialPhotoDetail();
							db.close();

						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							db.close();
							e.printStackTrace();
						}
				for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
				{
					db.open();
					int NoOfImages = db.getExistingPicNos(NoOfOutletID[chkCountstore].toString());
					String[] NoOfImgsPath = db.getImgsPath(NoOfOutletID[chkCountstore].toString());
					db.close();

					fp2s = new String[2];

					for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
					{
						fp2s[0] = NoOfImgsPath[syCOUNT];
						fp2s[1] = NoOfOutletID[chkCountstore];

						// New Way

						fnameIMG = fp2s[0];
						UploadingImageName=fp2s[0];


						String stID = fp2s[1];
						String currentImageFileName=fnameIMG;

						boolean isImageExist=false;
						for (int i = 0; i < listFile.length; i++)
						{
							FilePathStrings = listFile[i].getAbsolutePath();
							if(listFile[i].getName().equals(fnameIMG))
							{
								fnameIMG=listFile[i].getAbsolutePath();
								isImageExist=true;
								break;
							}
						}
						if(isImageExist)
						{
							/* Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
				             ByteArrayOutputStream stream = new ByteArrayOutputStream();

				             String image_str=  BitMapToString(bmp);
				             ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
*/
							ByteArrayOutputStream stream = new ByteArrayOutputStream();

							String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
							ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

				        try
				        {
							stream.flush();
						}
				        catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				        try
				        {
							stream.close();
						}
				        catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				        long syncTIMESTAMP = System.currentTimeMillis();
				        Date datefromat = new Date(syncTIMESTAMP);
						SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
					    String onlyDate=df.format(datefromat);


				        nameValuePairs.add(new BasicNameValuePair("image", image_str));
				        nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
				        nameValuePairs.add(new BasicNameValuePair("comment","NA"));
				        nameValuePairs.add(new BasicNameValuePair("storeID",stID));
				        nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
				        nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

				        try
				        {

				        	  HttpParams httpParams = new BasicHttpParams();
				              HttpConnectionParams.setSoTimeout(httpParams, 0);
				        	  HttpClient httpclient = new DefaultHttpClient(httpParams);
				              HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


				            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				            HttpResponse response = httpclient.execute(httppost);
				            String the_string_response = convertResponseToString(response);

				            System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

				          //  if(serverResponseCode == 200)
				            if(the_string_response.equals("Abhinav"))
				               {

				            	 System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
				            	 db.updateImageRecordsSyncdforPOSMaterial(UploadingImageName.toString().trim());
				            	// String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
								   String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

								   File fdelete = new File(file_dj_path);
							        if (fdelete.exists())
							        {
							            if (fdelete.delete())
							            {
							                Log.e("-->", "file Deleted :" + file_dj_path);
							                callBroadCast();
							            }
							            else
							            {
							                Log.e("-->", "file not Deleted :" + file_dj_path);
							            }
							        }
				            	/* File file = new File(UploadingImageName.toString().trim());
				         	    file.delete();*/

				               }

				        }catch(Exception e)
				        {
				            IMGsyOK = 1;

				        }
						}


					}


				}
			 }
				catch(Exception e)
		        {
		             IMGsyOK = 1;

		        }


				  // Sync Return Images

				  try
				  {

					  try
						{

							db.open();
							NoOfOutletID = db.getAllStoreIDIntblStoreReturnPhotoDetail();
							db.close();

						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							db.close();
							e.printStackTrace();
						}

						for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
						{
							db.open();
							int NoOfImages = db.getExistingPicNosForReturn(NoOfOutletID[chkCountstore].toString());
							String[] NoOfImgsPath = db.getImgsPathForReturn(NoOfOutletID[chkCountstore].toString());
							db.close();

							fp2s = new String[2];

							for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
							{
								fp2s[0] = NoOfImgsPath[syCOUNT];
								fp2s[1] = NoOfOutletID[chkCountstore];

								// New Way

								fnameIMG = fp2s[0];
								UploadingImageName=fp2s[0];


								String stID = fp2s[1];
								String currentImageFileName=fnameIMG;

								boolean isImageExist=false;
								for (int i = 0; i < listFile.length; i++)
								{
									FilePathStrings = listFile[i].getAbsolutePath();
									if(listFile[i].getName().equals(fnameIMG))
									{
										fnameIMG=listFile[i].getAbsolutePath();
										isImageExist=true;
										break;
									}
								}
								if(isImageExist)
								{
									/* Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
						             ByteArrayOutputStream stream = new ByteArrayOutputStream();

						             String image_str=  BitMapToString(bmp);
						             ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
*/
									ByteArrayOutputStream stream = new ByteArrayOutputStream();

									String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
									ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

						        try
						        {
									stream.flush();
								}
						        catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						        try
						        {
									stream.close();
								}
						        catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

						        long syncTIMESTAMP = System.currentTimeMillis();
						        Date datefromat = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
							    String onlyDate=df.format(datefromat);


						        nameValuePairs.add(new BasicNameValuePair("image", image_str));
						        nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
						        nameValuePairs.add(new BasicNameValuePair("comment","NA"));
						        nameValuePairs.add(new BasicNameValuePair("storeID",stID));
						        nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
						        nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

						        try
						        {

						        	  HttpParams httpParams = new BasicHttpParams();
						              HttpConnectionParams.setSoTimeout(httpParams, 0);
						        	  HttpClient httpclient = new DefaultHttpClient(httpParams);
						              HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


						            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
						            HttpResponse response = httpclient.execute(httppost);
						            String the_string_response = convertResponseToString(response);

						            System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

						          //  if(serverResponseCode == 200)
						            if(the_string_response.equals("Abhinav"))
						               {

						            	 System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
						            	 db.updateImageRecordsSyncdForReturn(UploadingImageName.toString().trim());
						            	// String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
										   String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

										   File fdelete = new File(file_dj_path);
									        if (fdelete.exists())
									        {
									            if (fdelete.delete())
									            {
									                Log.e("-->", "file Deleted :" + file_dj_path);
									                callBroadCast();
									            }
									            else
									            {
									                Log.e("-->", "file not Deleted :" + file_dj_path);
									            }
									        }
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
						               }

						        }catch(Exception e)
						        {
						            IMGsyOK = 1;

						        }
								}


							}


						}

				  }
				  catch(Exception e)
			        {
			             IMGsyOK = 1;

			        }


				try
				{

					try
					{

						db.open();
						NoOfOutletID = db.getAllStoreClosePhotoDetail();
						db.close();

					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						db.close();
						e.printStackTrace();
					}

					for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
					{
						db.open();
						int NoOfImages = db.getExistingPicNosForStoreClose(NoOfOutletID[chkCountstore].toString());
						String[] NoOfImgsPath = db.getImgsPathForStoreClose(NoOfOutletID[chkCountstore].toString());
						db.close();

						fp2s = new String[2];

						for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
						{
							fp2s[0] = NoOfImgsPath[syCOUNT];
							fp2s[1] = NoOfOutletID[chkCountstore];

							// New Way

							fnameIMG = fp2s[0];
							UploadingImageName=fp2s[0];


							String stID = fp2s[1];
							String currentImageFileName=fnameIMG;

							boolean isImageExist=false;
							for (int i = 0; i < listFile.length; i++)
							{
								FilePathStrings = listFile[i].getAbsolutePath();
								if(listFile[i].getName().equals(fnameIMG))
								{
									fnameIMG=listFile[i].getAbsolutePath();
									isImageExist=true;
									break;
								}
							}
							if(isImageExist)
							{/*
								Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
								ByteArrayOutputStream stream = new ByteArrayOutputStream();

								String image_str=  BitMapToString(bmp);
								ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

*/
								ByteArrayOutputStream stream = new ByteArrayOutputStream();

								String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
								ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
								try
								{
									stream.flush();
								}
								catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								try
								{
									stream.close();
								}
								catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								long syncTIMESTAMP = System.currentTimeMillis();
								Date datefromat = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
								String onlyDate=df.format(datefromat);


								nameValuePairs.add(new BasicNameValuePair("image", image_str));
								nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
								nameValuePairs.add(new BasicNameValuePair("comment","NA"));
								nameValuePairs.add(new BasicNameValuePair("storeID",stID));
								nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
								nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

								try
								{

									HttpParams httpParams = new BasicHttpParams();
									HttpConnectionParams.setSoTimeout(httpParams, 0);
									HttpClient httpclient = new DefaultHttpClient(httpParams);
									HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


									httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
									HttpResponse response = httpclient.execute(httppost);
									String the_string_response = convertResponseToString(response);

									System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

									//  if(serverResponseCode == 200)
									if(the_string_response.equals("Abhinav"))
									{

										System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
										db.updateImageRecordsSyncdForStoreClose(UploadingImageName.toString().trim());
										// String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
										String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

										File fdelete = new File(file_dj_path);
										if (fdelete.exists())
										{
											if (fdelete.delete())
											{
												//Log.e("-->", "file Deleted :" + file_dj_path);
												callBroadCast();
											}
											else
											{
												//Log.e("-->", "file not Deleted :" + file_dj_path);
											}
										}
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
									}

								}catch(Exception e)
								{
									IMGsyOK = 1;

								}
							}


						}


					}

				}
				catch(Exception e)
				{
					IMGsyOK = 1;

				}


				// Sync Add New Stores Images

				try
				{

					try
					{

						db.open();
						NoOfOutletID = db.getAllStoreIDIntblNewAddedStorePhotoDetail();
						db.close();

					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						db.close();
						e.printStackTrace();
					}

					for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
					{
						db.open();
						int NoOfImages = db.getExistingPicNosForNewAddedStore(NoOfOutletID[chkCountstore].toString());
						String[] NoOfImgsPath = db.getImgsPathForNewAddedStore(NoOfOutletID[chkCountstore].toString());
						db.close();

						fp2s = new String[2];

						for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
						{
							fp2s[0] = NoOfImgsPath[syCOUNT];
							fp2s[1] = NoOfOutletID[chkCountstore];

							// New Way

							fnameIMG = fp2s[0];
							UploadingImageName=fp2s[0];


							String stID = fp2s[1];
							String currentImageFileName=fnameIMG;

							boolean isImageExist=false;
							for (int i = 0; i < listFile.length; i++)
							{
								FilePathStrings = listFile[i].getAbsolutePath();
								if(listFile[i].getName().equals(fnameIMG))
								{
									fnameIMG=listFile[i].getAbsolutePath();
									isImageExist=true;
									break;
								}
							}
							if(isImageExist)
							{
							/*	Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
								ByteArrayOutputStream stream = new ByteArrayOutputStream();

								String image_str=  BitMapToString(bmp);
								ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
*/
								ByteArrayOutputStream stream = new ByteArrayOutputStream();

								String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
								ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

								try
								{
									stream.flush();
								}
								catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								try
								{
									stream.close();
								}
								catch (IOException e1)
								{
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								long syncTIMESTAMP = System.currentTimeMillis();
								Date datefromat = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
								String onlyDate=df.format(datefromat);


								nameValuePairs.add(new BasicNameValuePair("image", image_str));
								nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
								nameValuePairs.add(new BasicNameValuePair("comment","NA"));
								nameValuePairs.add(new BasicNameValuePair("storeID",stID));
								nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
								nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

								try
								{

									HttpParams httpParams = new BasicHttpParams();
									HttpConnectionParams.setSoTimeout(httpParams, 0);
									HttpClient httpclient = new DefaultHttpClient(httpParams);
									HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


									httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
									HttpResponse response = httpclient.execute(httppost);
									String the_string_response = convertResponseToString(response);

									System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

									//  if(serverResponseCode == 200)
									if(the_string_response.equals("Abhinav"))
									{

										System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
										db.updateImageRecordsSyncdForNewAddedStore(UploadingImageName.toString().trim());
										// String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
										String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

										File fdelete = new File(file_dj_path);
										if (fdelete.exists())
										{
											if (fdelete.delete())
											{
												//Log.e("-->", "file Deleted :" + file_dj_path);
												callBroadCast();
											}
											else
											{
												//Log.e("-->", "file not Deleted :" + file_dj_path);
											}
										}
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
									}

								}catch(Exception e)
								{
									IMGsyOK = 1;

								}
							}


						}


					}

				}
				catch(Exception e)
				{
					IMGsyOK = 1;

				}



				// Sync Signature And selfi Images

				try
				{

					try
					{

						db.open();
						NoOfOutletID = db.getAllDSRSignatureAndSelfi();
						db.close();

					} catch (Exception e)
					{
						// TODO Auto-generated catch block
						db.close();
						e.printStackTrace();
					}
if(NoOfOutletID.length>0)
{
	for(int chkCountstore=0; chkCountstore < NoOfOutletID.length;chkCountstore++)
	{
		db.open();
		int NoOfImages = db.getExistingPicNosForSignatureAndSelfi();
		String[] NoOfImgsPath = db.getImgsPathForSignatureAndSelfi();
		db.close();

		fp2s = new String[2];

		for(int syCOUNT = 0; syCOUNT < NoOfImages; syCOUNT++)
		{
			fp2s[0] = NoOfImgsPath[syCOUNT];
			fp2s[1] = NoOfOutletID[chkCountstore];

			// New Way

			fnameIMG = fp2s[0];
			UploadingImageName=fp2s[0];


			String stID = fp2s[1];
			String currentImageFileName=fnameIMG;

			boolean isImageExist=false;
			for (int i = 0; i < listFile.length; i++)
			{
				FilePathStrings = listFile[i].getAbsolutePath();
				if(listFile[i].getName().equals(fnameIMG))
				{
					fnameIMG=listFile[i].getAbsolutePath();
					isImageExist=true;
					break;
				}
			}
			if(isImageExist)
			{
				/*Bitmap bmp = BitmapFactory.decodeFile(fnameIMG);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();

				String image_str=  BitMapToStringDSSelfiAndSignature(bmp);
				ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();
*/
				ByteArrayOutputStream stream = new ByteArrayOutputStream();

				String image_str= compressImage(fnameIMG);// BitMapToString(bmp);
				ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

				try
				{
					stream.flush();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try
				{
					stream.close();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				long syncTIMESTAMP = System.currentTimeMillis();
				Date datefromat = new Date(syncTIMESTAMP);
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
				String onlyDate=df.format(datefromat);


				nameValuePairs.add(new BasicNameValuePair("image", image_str));
				nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
				nameValuePairs.add(new BasicNameValuePair("comment","NA"));
				nameValuePairs.add(new BasicNameValuePair("storeID",stID));
				nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
				nameValuePairs.add(new BasicNameValuePair("routeID",routeID));

				try
				{

					HttpParams httpParams = new BasicHttpParams();
					HttpConnectionParams.setSoTimeout(httpParams, 0);
					HttpClient httpclient = new DefaultHttpClient(httpParams);
					HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


					httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					HttpResponse response = httpclient.execute(httppost);
					String the_string_response = convertResponseToString(response);

					System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

					//  if(serverResponseCode == 200)
					if(the_string_response.equals("Abhinav"))
					{

						System.out.println("Sunil Doing Testing Response after sending Image inside if" + the_string_response);
						db.updateImageRecordsSyncdForDSRRegistrationAndSelfi();
						// String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+UploadingImageName.toString().trim();
						String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +UploadingImageName.toString().trim();

						File fdelete = new File(file_dj_path);
						if (fdelete.exists())
						{
							if (fdelete.delete())
							{
								Log.e("-->", "file Deleted :" + file_dj_path);
								callBroadCast();
							}
							else
							{
								Log.e("-->", "file not Deleted :" + file_dj_path);
							}
						}
						            	/* File file = new File(UploadingImageName.toString().trim());
							         	    file.delete();  */
					}

				}catch(Exception e)
				{
					IMGsyOK = 1;

				}
			}


		}


	}
}


				}
				catch(Exception e)
				{
					IMGsyOK = 1;

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
				 if(pDialogGetStores.isShowing())
			      {
			    	   pDialogGetStores.dismiss();
				  }

				 if(IMGsyOK == 1)
					{
						IMGsyOK = 0;

						if (myTimerTaskForDataSubmission != null) {
							myTimerTaskForDataSubmission.cancel();
							myTimerTaskForDataSubmission=null;
						}
						if (timerForDataSubmission!=null)
						{
							timerForDataSubmission.cancel();
							timerForDataSubmission = null;
						}
						showSyncErrorStart();
					}
					else
					{
						db.open();

			    		db.updateImageRecordsSyncd();
			    		db.close();

						//showSyncSuccess();

						//showSyncSuccessStart();


						try
						{
							task2 = new SyncXMLfileData(SyncMaster.this);
							task2.execute();
						}
						catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

			}
		}

	public String BitMapToString(Bitmap bitmap)
	{
		int h1=bitmap.getHeight();
		int w1=bitmap.getWidth();

		if(w1 > 768 || h1 > 1024)
		{
			bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);
		}
		else
		{

			bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
		}

		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
		byte [] arr=baos.toByteArray();
		String result=Base64.encodeToString(arr, Base64.DEFAULT);
		return result;
	}

	/*public String BitMapToString(Bitmap bitmap)
	{
		int h1=bitmap.getHeight();
		int w1=bitmap.getWidth();
		h1=h1/8;
		w1=w1/8;
		bitmap=Bitmap.createScaledBitmap(bitmap, w1, h1, true);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
  }*/
	/*public String BitMapToStringDSSelfiAndSignature(Bitmap bitmap)
	{
		int h1=bitmap.getHeight();
		int w1=bitmap.getWidth();
		h1=h1/2;
		w1=w1/2;
		bitmap=Bitmap.createScaledBitmap(bitmap, w1, h1, true);

		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
		byte [] arr=baos.toByteArray();
		String result=Base64.encodeToString(arr, Base64.DEFAULT);
		return result;
	}*/

	public String BitMapToStringDSSelfiAndSignature(Bitmap bitmap)
	{
		int h1=bitmap.getHeight();
		int w1=bitmap.getWidth();

		if(w1 > 768 || h1 > 1024){
			bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);

		}


		else {

			bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
		}

		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
		byte [] arr=baos.toByteArray();
		String result=Base64.encodeToString(arr, Base64.DEFAULT);
		return result;
	}


	private class SyncXMLfileData extends AsyncTask<Void, Void, Integer>
	{



		public SyncXMLfileData(SyncMaster activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		 protected void onPreExecute()
		  {
			 super.onPreExecute();


			 File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

			 if (!OrderXMLFolder.exists())
				{
					OrderXMLFolder.mkdirs();
				}

			    pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			 if(whereTo.contentEquals("WarehouseMap"))
          {
              pDialogGetStores.setMessage(getResources().getString(R.string.SubmitDistrbtrMapDetail));
          }
			 else if(DialogActivity_MarketVisit.flgJointWorking==1)
			 {
				 pDialogGetStores.setMessage("Submitting Joint Working Details...");
				 DialogActivity_MarketVisit.flgJointWorking=0;
			 }
			    else if(StoreSelection.flgChangeRouteOrDayEnd==1)
				{
				 pDialogGetStores.setMessage(getResources().getString(R.string.txtEndingDay));
				}else if(StoreSelection.flgChangeRouteOrDayEnd==2)
				{
				 pDialogGetStores.setMessage(getResources().getString(R.string.txtChangeRoute));
				}
				else if(StoreSelection.flgChangeRouteOrDayEnd==3)
				{
					 pDialogGetStores.setMessage(getResources().getString(R.string.txtSubmitQuoteDetail));
					}

				else if(DayStartActivity.flgDaySartWorking==1)
				{
					pDialogGetStores.setMessage(getResources().getString(R.string.submittingDayStart));

				}
				else
				{
				pDialogGetStores.setMessage(getResources().getString(R.string.SubmittingOrderDetails));
				}
				pDialogGetStores.setIndeterminate(false);
				pDialogGetStores.setCancelable(false);
				pDialogGetStores.setCanceledOnTouchOutside(false);
				pDialogGetStores.show();

	        }

		 @Override
	        protected Integer doInBackground(Void... params)
		 {



		     try {

		     String xmlfileNames = dbengine.fnGetXMLFile("3","1");

		      int index=0;
				 if(!xmlfileNames.equals("")) {
					 String[] xmlfileArray = xmlfileNames.split(Pattern.quote("^"));
					 for (int i = 0; i < xmlfileArray.length; i++) {
						 System.out.println("index" + index);
						 xmlFileName = xmlfileArray[i];


//
						 //String newzipfile = Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + xmlFileName + ".zip";
						 //  xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + xmlFileName + ".xml";

						 String newzipfile = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip";
						 xmlForWeb[0] = Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml";


						 try {
							 zip(xmlForWeb, newzipfile);
						 } catch (Exception e1) {
							 // TODO Auto-generated catch block
							 e1.printStackTrace();
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

						 // It is for Testing Purpose
						 //String urlString = "http://115.124.126.184/ReadXML_PragaSFAForTest/default.aspx?CLIENTFILENAME=" + zipFileName;

						 // It is for Live Purpose
						 // String urlString = "http://115.124.126.184/ReadXML_PragaSFA/default.aspx?CLIENTFILENAME=" + zipFileName;


						// String urlString = CommonInfo.OrderSyncPath.trim() + "?CLIENTFILENAME=" + xmlFileName+".xml";

						 String filetype="0";
						 String urlString="0";
						 filetype=dbengine.getfiletype(xmlFileName);
						 if(filetype.equals("1"))
						 {
							 urlString = CommonInfo.OrderSyncPath.trim() + "?CLIENTFILENAME=" + xmlFileName + ".xml";
						 }
						 else if(filetype.equals("2"))
						 {
							 urlString = CommonInfo.OrderSyncPathWarehouseMap.trim() + "?CLIENTFILENAME=" + xmlFileName + ".xml";
						 }
						 else if(filetype.equals("4"))
						 {
							// urlString = CommonInfo.NewStoreSyncPath.trim() + "?CLIENTFILENAME=" + xmlFileName + ".xml";
						 }
						 else if(filetype.equals("6"))
						 {
							 //urlString = CommonInfo.OrderSyncPathDistributorTarget.trim() + "?CLIENTFILENAME=" + xmlFileName + ".xml";
						 }
						 else
						 {
							 urlString = CommonInfo.OrderSyncPath.trim() + "?CLIENTFILENAME=" + xmlFileName + ".xml";
						 }



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
							 conn.setRequestProperty("zipFileName", xmlFileName);

							 dos = new DataOutputStream(conn.getOutputStream());

							 dos.writeBytes(twoHyphens + boundary + lineEnd);
							 dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
									 + xmlFileName + "\"" + lineEnd);

							 dos.writeBytes(lineEnd);

							 // create a buffer of  maximum size
							 bytesAvailable = fileInputStream.available();

							 bufferSize = Math.min(bytesAvailable, maxBufferSize);
							 buffer = new byte[bufferSize];

							 // read file and write it into form...
							 bytesRead = fileInputStream.read(buffer, 0, bufferSize);

							 while (bytesRead > 0) {

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

							 Log.i("uploadFile", "HTTP Response is : "
									 + serverResponseMessage + ": " + serverResponseCode);

							 if (serverResponseCode == 200) {

								 dbengine.upDateTblXmlFile(xmlFileName);
								 dbengine.deleteXmlTable("4");
		                     /*dbengine.upDatetbl_allAnswermstr("3");
		                     dbengine.upDatetbl_DynamcDataAnswer("3");*/


								 deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".xml");
								 deleteViewdXml(CommonInfo.OrderXMLFolder + "/" + xmlFileName + ".zip");
								 System.out.println("ShivamDELETE" + xmlFileName);
								 runOnUiThread(new Runnable() {
									 public void run() {


										 try {


										 } catch (Exception e) {
											 // TODO Auto-generated catch block
											 e.printStackTrace();
										 }


									 }
								 });
							 }

							 //close the streams //
							 fileInputStream.close();
							 dos.flush();
							 dos.close();
							 index++;
						 } catch (MalformedURLException ex) {

							 pDialogGetStores.dismiss();
							 ex.printStackTrace();

	               /* runOnUiThread(new Runnable() {
	                    public void run() {
	                    // messageText.setText("MalformedURLException Exception : check script url.");
	                        Toast.makeText(SyncMaster.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
	                    }
	                });*/

							 // Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
						 } catch (Exception e) {

							 pDialogGetStores.dismiss();

						 }

						 // pDialogGetInvoiceForDay.dismiss();


					 }
				 }
		     else
				 {
					 serverResponseCode=200;
				 }
			 }
	     catch (Exception e) {

	      e.printStackTrace();
	     }
	     return serverResponseCode;
	     }

		 @Override
	        protected void onCancelled()
		    {
	            Log.i("SyncMaster", "Sync Cancelled");
	        }

		 @Override
	        protected void onPostExecute(Integer result)
		     {
			 super.onPostExecute(result);
			 if(!isFinishing())
			    {

			        	Log.i("SyncMaster", "Sync cycle completed");


			        	  if(pDialogGetStores.isShowing())
					      {
					    	   pDialogGetStores.dismiss();
						  }

					if (timerForDataSubmission!=null)
					{
						timerForDataSubmission.cancel();
						timerForDataSubmission = null;
					}
					if (myTimerTaskForDataSubmission != null) {
						myTimerTaskForDataSubmission.cancel();
						myTimerTaskForDataSubmission=null;
					}
			        	if(result!=200)
			        	{







							if (timer!=null)
							{
								timer.cancel();
								timer = null;
							}
							if(timer2!=null)
							{
								timer2.cancel();
								timer2 = null;
							}





							if(whereTo.contentEquals("11"))
								{
			            			showSyncError();
								}
							else if(whereTo.contentEquals("DayStart"))
							{
								showSyncError();
							}
			            		else
			            		{

			            			Intent submitStoreIntent = new Intent(SyncMaster.this, AllButtonActivity.class);
									startActivity(submitStoreIntent);
									finish();
			            		}

			        	}
			        	else
			        	{
							if(pDialogGetStores.isShowing())
							{
								pDialogGetStores.dismiss();
							}

							if (timerForDataSubmission!=null)
							{
								timerForDataSubmission.cancel();
								timerForDataSubmission = null;
							}
							if (myTimerTaskForDataSubmission != null) {
								myTimerTaskForDataSubmission.cancel();
								myTimerTaskForDataSubmission=null;
							}




							if (timer!=null)
							{
								timer.cancel();
								timer = null;
							}
							if(timer2!=null)
							{
								timer2.cancel();
								timer2 = null;
							}

				/*	int Sstat=6;
					db.open();
					db.updateRecordsSyncd(Sstat);		// update syncd' records
					db.close();*/
							db.UpdateStoreVisitWiseTablesAfterSync(4);




								SyncStockData task1=new SyncStockData(SyncMaster.this);

									task1.execute();


		            	}




			        	}}



	}
	class MyTimerTaskForDataSubmission extends TimerTask
	{

		@Override
		public void run()
		{

			SyncMaster.this.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					if (timerForDataSubmission != null) {
						timerForDataSubmission.cancel();
						timerForDataSubmission = null;
					}
					if(task1!=null){
						if(task1.getStatus()==AsyncTask.Status.RUNNING)
						{
							task1.cancel(true);

						}
					}
					if(task2!=null) {
						if (task2.getStatus() == AsyncTask.Status.RUNNING) {
							task2.cancel(true);

						}
					}

					if(pDialogGetStores.isShowing())
					{
						pDialogGetStores.dismiss();
					}

                    if(DayStartActivity.flgDaySartWorking==1)
                    {
                        Intent submitStoreIntent = new Intent(SyncMaster.this, AllButtonActivity.class);
                        startActivity(submitStoreIntent);
                        finish();
                    }
                    else
                    {
                        Intent submitStoreIntent = new Intent(SyncMaster.this, LauncherActivity.class);
                        startActivity(submitStoreIntent);
                        finish();
                    }

				}});
		}

	}
	 public void deleteViewdXml(String file_dj_path)
	   {
	    File dir=   Environment.getExternalStorageDirectory();
	       File fdelete=new File(dir,file_dj_path);
	   // File fdelete = new File(file_dj_path);
	          if (fdelete.exists()) {
	              if (fdelete.delete()) {
	                  Log.e("-->", "file Deleted :" + file_dj_path);
	                  callBroadCast();
	              } else {
	                  Log.e("-->", "file not Deleted :" + file_dj_path);
	              }
	          }


	   }


	 public void callBroadCast() {
         if (Build.VERSION.SDK_INT >= 14) {
             Log.e("-->", " >= 14");
             MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                 public void onScanCompleted(String path, Uri uri) {

                 }
             });
         } else {
             Log.e("-->", " < 14");
             sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                     Uri.parse("file://" + Environment.getExternalStorageDirectory())));
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




	private class SyncStockData extends AsyncTask<Void, Void, Void>
	{

		boolean serviceException=false;


		public SyncStockData(SyncMaster activity)
		{
			pDialogGetStores = new ProgressDialog(activity);

		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			//  pDialogGetStores.setMessage("Uploading Data...");


				if(DayStartActivity.flgDaySartWorking==1)
		           {
			pDialogGetStores.setMessage(getResources().getString(R.string.submittingDayStart));

		           }
		           else
				{
					pDialogGetStores.setMessage(getResources().getString(R.string.txtGetStockData));
				}
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();

		}

		@Override
		protected Void doInBackground(Void... params)
		{
			ServiceWorker newservice=new ServiceWorker();

			/*newservice=newservice.fnGetVanStockData(getApplicationContext(),CommonInfo.imei);
			if(newservice.flagExecutedServiceSuccesfully!=39)
			{
				serviceException=true;

			}*/

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
			if(pDialogGetStores.isShowing())
			{
				pDialogGetStores.dismiss();
			}
			if(serviceException)
			{
				showSyncError();
			}
			else
			{
				if(whereTo.contentEquals("11"))
				{
					int Sstat=4;
					db.open();
					db.updateRecordsSyncd(Sstat);		// update syncd' records
					db.close();
					Date pdaDate=new Date();
					SimpleDateFormat	sdfPDaDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
					String getPDADate = sdfPDaDate.format(pdaDate).toString().trim();

					db.open();
					String getServerDate=db.fnGetServerDate();
					db.reCreateDB();
					db.close();

					if(!getPDADate.equals(""))
					{
						if(StoreSelection.flgChangeRouteOrDayEnd==1)
						{
							db.deleteViewAddedStore();
						}
						if(getServerDate.equals(getPDADate))
						{

											/*try
							        		{
							        			task3 = new SyncTextFileTaster(SyncMaster.this);
							        			task3.execute();
							        		}
							        		catch (Exception e)
							        		{
						        			// TODO Auto-generated catch block
							        			e.printStackTrace();
							        		}*/

							Intent submitStoreIntent = new Intent(SyncMaster.this, AllButtonActivity.class);
							startActivity(submitStoreIntent);
							finish();


						}
						else
						{
							Intent submitStoreIntent = new Intent(SyncMaster.this, SplashScreen.class);
							startActivity(submitStoreIntent);
							finish();
						}
					}
					else
					{
						showSyncSuccess();
					}



				}
				else if(whereTo.contentEquals("DayStart"))
				{
					Intent intent=new Intent(SyncMaster.this,DSR_Registration.class);
					intent.putExtra("IntentFrom", "SPLASH");
					startActivity(intent);
					finish();;

				}
				else
				{
				/*	int Sstat=6;
					db.open();
					db.updateRecordsSyncd(Sstat);		// update syncd' records
					db.close();*/
					//db.UpdateStoreVisitWiseTablesAfterSync(4);
					showSyncSuccess();


				}
			}

		}
	}
	public String compressImage(String imageUri) {
		String filePath = imageUri;//getRealPathFromURI(imageUri);
		Bitmap scaledBitmap=null;
		BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

		float maxHeight = 1024.0f;
		float maxWidth = 768.0f;
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

//      setting inSampleSize value allows to load a scaled down version of the original image

		options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
		options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[768*1024];

		//bmp
  /*try {
//          load the bitmap from its path


  } catch (OutOfMemoryError exception) {
   exception.printStackTrace();

  }
*/


  /*if(actualWidth > 768 || h1 > 1024)
  {
   bitmap=Bitmap.createScaledBitmap(bitmap,1024,768,true);
  }
  else
  {

   bitmap=Bitmap.createScaledBitmap(bitmap,w1,h1,true);
  }*/
		//Bitmap bitmap=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
		//  bmp =BitmapFactory.decodeFile(filePath, options);//Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);;//

		try {
//          load the bitmap from its path
			bmp = BitmapFactory.decodeFile(filePath, options);

			//bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
			//scaledBitmap=Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
			// bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();

		}

		//Bitmap scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
		//bmp=Bitmap.createScaledBitmap(bmp,actualWidth,actualHeight,true);
		ByteArrayOutputStream baos=new  ByteArrayOutputStream();
		//scaledBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
		bmp.compress(Bitmap.CompressFormat.JPEG,100, baos);

		byte [] arr=baos.toByteArray();
		String result=Base64.encodeToString(arr, Base64.DEFAULT);
		return result;

  /*try {
//          load the bitmap from its path
   bmp = BitmapFactory.decodeFile(filePath, options);
  } catch (OutOfMemoryError exception) {
   exception.printStackTrace();

  }
  try {
   scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
  } catch (OutOfMemoryError exception) {
   exception.printStackTrace();
  }*/

  /*float ratioX = actualWidth / (float) options.outWidth;
  float ratioY = actualHeight / (float) options.outHeight;
  float middleX = actualWidth / 2.0f;
  float middleY = actualHeight / 2.0f;*/


		//return filename;

	}
	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;
		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}

}

