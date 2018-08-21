package project.astix.com.balajisosfadirectsales;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.WindowManager;

import com.astix.Common.CommonInfo;


public class InvoiceSyncMaster extends Activity
{

	
	public String imei;
	public String StoreName;
	public String currSysDate;
	public String pickerDate; 
	
	public String[] xmlForWeb = new String[1];
	
	//public TextView chkString;
	HttpEntity resEntity;
	private InvoiceSyncMaster _activity;
	
	private static final String DATASUBDIRECTORY = "NMPphotos";
	
	
	public int syncFLAG = 0;
	public int res_code;
	public String zipFileName;
	ProgressDialog PDpicTasker;
	public String whereTo;
	public int IMGsyOK = 0;
	 
	//ProgressDialog pDialog2;
	InputStream inputStream;
	ArrayList mSelectedItems = new ArrayList();
	ArrayList mSelectedItemsConfornInvoiceOrders= new ArrayList();
	
	 ArrayList<String> stIDs;
		ArrayList<String> stNames;
	PRJDatabase db = new PRJDatabase(this);
	
	public void showSyncError()
	{
		AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(InvoiceSyncMaster.this);
		alertDialogSyncError.setTitle(getText(R.string.genTermSyncErrornew));
		alertDialogSyncError.setCancelable(false);
		/*alertDialogSyncError
				.setMessage("Sync was not successful! Please try again.");*/
		alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsg));
		alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						
						dialog.dismiss();
						Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, InvoiceStoreSelection.class);
						submitStoreIntent.putExtra("imei", imei);
						submitStoreIntent.putExtra("currSysDate", currSysDate);
						submitStoreIntent.putExtra("pickerDate", pickerDate);
						startActivity(submitStoreIntent);
						finish();
						//SyncMaster.this.finish();
					}
				});
		alertDialogSyncError.setIcon(R.drawable.sync_error_ico);
		
		AlertDialog alert = alertDialogSyncError.create();
		alert.show();
		// alertDialogLowbatt.show();
	}
	public void showSyncErrorStart() {
		AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(InvoiceSyncMaster.this);
		alertDialogSyncError.setTitle(getText(R.string.genTermSyncErrornew));
		alertDialogSyncError.setCancelable(false);
		alertDialogSyncError.setMessage(getText(R.string.genTermSyncErrorFullMsg));
		alertDialogSyncError.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						
						dialog.dismiss();
						
						/*destroyNcleanup(1);
						imgs = null;
						uComments.clear();*/
						
						//finish();
					}
				});
		alertDialogSyncError.setIcon(R.drawable.sync_error_ico);
		
		AlertDialog alert = alertDialogSyncError.create();
		alert.show();
		// alertDialogLowbatt.show();
	}
	public void showSyncSuccessStart() {
		AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(InvoiceSyncMaster.this);
		alertDialogSyncOK.setTitle(getText(R.string.genTermInformation));
		alertDialogSyncOK.setCancelable(false);
		alertDialogSyncOK.setMessage(getText(R.string.syncAlertInvoiceOKMsg));
		alertDialogSyncOK.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					
					dialog.dismiss();
					
					//db.open();
					//System.out.println("Indubati flgChangeRouteOrDayEnd :"+StoreSelection_Old.flgChangeRouteOrDayEnd);
					/*if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
					{
						db.reTruncateRouteTbl();
					}*/
					
					
					//db.reCreateDB();
					//db.close();
					
					Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, InvoiceStoreSelection.class);
					submitStoreIntent.putExtra("imei", imei);
					submitStoreIntent.putExtra("currSysDate", currSysDate);
					submitStoreIntent.putExtra("pickerDate", pickerDate);
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
private class SyncImgTasker extends AsyncTask<String, Void, Void> {
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			PDpicTasker = ProgressDialog.show(InvoiceSyncMaster.this, null, null);
			//PDpicTasker.setContentView(R.layout.loader);
			PDpicTasker.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	       
		}

		@Override
		protected Void doInBackground(String... params)
		{
			String fnameIMG = params[0];
			String imgComment = params[1];
			String stID = params[2];
			String date2S = params[3];
			String actRouteID = params[4];
			
			//System.out.println("File to be sync'ed: "+fnameIMG);
	
	   try 
	   {
		   BitmapFactory.Options IMGoptions01 = new BitmapFactory.Options();
			IMGoptions01.inDither=false;
			IMGoptions01.inPurgeable=true;
			IMGoptions01.inInputShareable=true;
			IMGoptions01.inTempStorage = new byte[16*1024];
			
			//finalBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(fnameIMG,IMGoptions01), 640, 480, false);
			
			Bitmap bitmap = BitmapFactory.decodeFile(fnameIMG,IMGoptions01);
			
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
	        bitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream); //compress to which format you want.
			
			//b is the Bitmap    
			//int bytes = bitmap.getWidth()*bitmap.getHeight()*4; //calculate how many bytes our image consists of. Use a different value than 4 if you don't use 32bit images.

			//ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
			//bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer
			//byte [] byte_arr = buffer.array();
			
			
	        byte [] byte_arr = stream.toByteArray();
	        String image_str = Base64.encodeBytes(byte_arr);
	        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

	        ////System.out.println("image_str: "+image_str);
	        
	        stream.flush();
	        stream.close();
	        //buffer.clear();
	        //buffer = null;
	        bitmap.recycle();
	        
	        nameValuePairs.add(new BasicNameValuePair("image",image_str));
	        
	        //System.out.println("fnameIMG (BEFORE split): "+fnameIMG);
			StringTokenizer tokens = new StringTokenizer(String.valueOf(fnameIMG), "/");
			
			String val1 = tokens.nextToken().trim();
			val1 = tokens.nextToken().trim();
			val1 = tokens.nextToken().trim();
			//val1 = tokens.nextToken().trim();
			String fn2s = tokens.nextToken().trim();
			
			//System.out.println("fnameIMG (AFTER split): "+fn2s);
			
			nameValuePairs.add(new BasicNameValuePair("FileName", fn2s));
	        nameValuePairs.add(new BasicNameValuePair("comment", imgComment));
	        nameValuePairs.add(new BasicNameValuePair("storeID", stID));
	        nameValuePairs.add(new BasicNameValuePair("date", date2S));
	        nameValuePairs.add(new BasicNameValuePair("routeID", actRouteID));
	        

	        try{
	        	
	        	  HttpParams httpParams = new BasicHttpParams();
	              int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);
	              
	             // HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);
	              
	              HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout+2000);
	        	
	        	
	            HttpClient httpclient = new DefaultHttpClient(httpParams);
	           HttpPost httppost = new HttpPost("http://122.160.87.77/neomilkproduct/PDA/FrmStoreImageSync.aspx");
	           
	         //  http://103.16.141.16/neomilkproduct/PDA/FrmStoreImageSync.aspx
	          
	            // It is Live Path
	       //  HttpPost httppost = new HttpPost("http://103.16.141.16/neomilkproduct/PDA/FrmStoreImageSync.aspx");
	      
	         
	       // HttpPost httppost = new HttpPost("http://192.168.1.230/NEOSyncPDAImages/DefaultTT.aspx");
	        
	         //post.addHeader("zipFileName", zipFileName);
	            //httppost.addHeader("image",image_str);
	            
	           /* File data2send = new File();
	            
	            //File data2send = new File(image_str);
	            FileEntity fEntity = new FileEntity(data2send, "binary/octet-stream");
	            httppost.setEntity(fEntity);*/
	           
	           /* StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	            StrictMode.setThreadPolicy(policy);*/
	            
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            String the_string_response = convertResponseToString(response);
	            //Toast.makeText(MainActivity.this, "Response " + the_string_response, Toast.LENGTH_LONG).show();
	            //System.out.println("Response " + the_string_response);
	            
	        }catch(Exception e){
	              //Toast.makeText(MainActivity.this, "ERROR " + e.getMessage(), Toast.LENGTH_LONG).show();
	        	
	        	IMGsyOK = 1;
	        	//System.out.println("ERROR " + e.getMessage());
	             //System.out.println("Error in http connection "+e.toString());
	        }
	        
	        if(IMGsyOK == 0){
	        	/*db.open();
	        	db.PicRecordPath2del(fnameIMG);
	        	db.close();*/
	        }
				
		}
	   catch (Exception e) {
				Log.i("SvcMgr", "Service Execution Failed!", e);

			}

			finally {

				Log.i("SvcMgr", "Service Execution Completed...");

			}
			
			return null;
		}

		@Override
		protected void onCancelled() {
			Log.i("SvcMgr", "Service Execution Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			PDpicTasker.dismiss();
			
			Log.i("SvcMgr", "Service Execution cycle completed");
	
		}
	}
	
	public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException{
		 
        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length�..
        //System.out.println("contentLength : " + contentLength);
        //Toast.makeText(MainActivity.this, "contentLength : " + contentLength, Toast.LENGTH_LONG).show();
        if (contentLength < 0){
        }
        else{
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
					
					
					
				} catch (Exception e) {
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
		 
		 if( path.exists() ) {
	            File[] files = path.listFiles();
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                	deleteFolderFiles(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	        return(path.delete());
	 
	 }
	public void showSyncSuccess() {
		AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(
				InvoiceSyncMaster.this);
		alertDialogSyncOK.setTitle(getText(R.string.genTermInformation));
		alertDialogSyncOK.setCancelable(false);
		/*alertDialogSyncOK
				.setMessage("Sync was successful!");*/
		alertDialogSyncOK.setMessage(getText(R.string.syncAlertInvoiceOKMsg));
		alertDialogSyncOK.setNeutralButton(getText(R.string.AlertDialogOkButton),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						
						dialog.dismiss();
						// finishing activity & stepping back
						/*if(whereTo.contentEquals("11"))
						{
							
							
								
								Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, LauncherActivity.class);
								submitStoreIntent.putExtra("imei", imei);
								//submitStoreIntent.putExtra("currSysDate", currSysDate);
								//submitStoreIntent.putExtra("pickerDate", pickerDate);
								startActivity(submitStoreIntent);
								finish();
							
						
						}
						
						else{*/
						Intent submitStoreIntent = new Intent(InvoiceSyncMaster.this, InvoiceStoreSelection.class);
						submitStoreIntent.putExtra("imei", imei);
						submitStoreIntent.putExtra("currSysDate", currSysDate);
						submitStoreIntent.putExtra("pickerDate", pickerDate);
						startActivity(submitStoreIntent);
						finish();
						//}
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
	public static void zip(String[] files, String zipFile) throws IOException{
	    BufferedInputStream origin = null;
	    final int BUFFER_SIZE = 2048;

	    ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
	    try { 
	        byte data[] = new byte[BUFFER_SIZE];

	        for (int i = 0; i < files.length; i++) {
	            FileInputStream fi = new FileInputStream(files[i]);    
	            origin = new BufferedInputStream(fi, BUFFER_SIZE);
	            try {
	                ZipEntry entry = new ZipEntry(files[i].substring(files[i].lastIndexOf("/") + 1));
	                out.putNextEntry(entry);
	                int count;
	                while ((count = origin.read(data, 0, BUFFER_SIZE)) != -1) {
	                    out.write(data, 0, count);
	                }
	            }
	            finally {
	                origin.close();
	            }
	        }
	    }
	    
	    finally {
	        out.close();
	    }
	    
	    
	   
	    
	}
	//
	
	private class SyncPROdata extends AsyncTask<Void, Void, Void> {

	
		ProgressDialog pDialogGetStores;
		public SyncPROdata(InvoiceSyncMaster activity) 
		{
			pDialogGetStores = new ProgressDialog(activity);
		}
		
		
		@Override
		 protected void onPreExecute()
		{
			 super.onPreExecute();
	            Log.i("SyncMaster","Ready to Sync Data");
	           
	           /* pDialog2 = ProgressDialog.show(_activity,"Please wait...", "Data Sync in Progress...", true);
				pDialog2.setIndeterminate(true);
				//pDialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				
				//pDialog2.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		        pDialog2.setCancelable(false);
		        pDialog2.show();*/
		        
	        	/*progressDialog = ProgressDialog.show(_activity, null, null);
	        	progressDialog.setContentView(R.layout.loader);
	            progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
	            */
	            
	            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
				pDialogGetStores.setMessage(getText(R.string.SubmittingDelDetails));
				pDialogGetStores.setIndeterminate(false);
				pDialogGetStores.setCancelable(false);
				pDialogGetStores.setCanceledOnTouchOutside(false);
				pDialogGetStores.show();
	            
	        }
		 
		 @Override
	        protected Void doInBackground(Void... params) 
		    {
	          
			/* try 
			 {
				Thread.currentThread().sleep(2000);
			 }
			 catch (InterruptedException e) 
			 {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }*/
			 
			 
			 			// ZIPPING XML FILE HERE
			 //String newzipfile = Environment.getExternalStorageDirectory() + "/TJUKSFAInvoicexml/" + zipFileName + ".zip";
				String newzipfile = Environment.getExternalStorageDirectory() + "/"+CommonInfo.InvoiceXMLFolder+"/" + zipFileName + ".zip";

				try {
				zip(xmlForWeb,newzipfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			 
		        File file2send = new File(newzipfile);
		        
		        
		        // It is for Testing Purpose
		     //  String urlString = "http://115.124.126.184/ReadXML_PragaInvoiceTestSFA/Default.aspx?CLIENTFILENAME=" + zipFileName;  
				    
		        
		      
		    // It is for Live
		   // String urlString = "http://115.124.126.184/ReadXMLForParagSFA_Invoicing/Default.aspx?CLIENTFILENAME=" + zipFileName;
			
		     
		       String urlString = CommonInfo.InvoiceSyncPath+"?CLIENTFILENAME=" + zipFileName;
				
		       
		    
		        HttpParams httpParams = new BasicHttpParams();
                int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);
                
               // HttpConnectionParams.setConnectionTimeout(httpParams, some_reasonable_timeout);
                
                HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout+2000);
                
            	HttpClient client = new DefaultHttpClient(httpParams);
                HttpPost post = new HttpPost(urlString);
                
                ////System.out.println("SYNC'ing USING METHOD: " + post.getMethod().toString());
			 try {
	            	
	            	//publishProgress("Sync in Progress...");
				 	
	              /* MultipartEntity rEntity = new MultipartEntity();
	               rEntity.addPart();*/
				 
	               //OLD FILE-ENTITY MECHANISM >
	                FileEntity fEntity = new FileEntity(file2send, "binary/octet-stream");
	              
	                //NEW (v1.5) INPUTSTREAM-ENTITY MECHANISM >
	                //InputStreamEntity fEntity = new InputStreamEntity(new FileInputStream(newzipfile), -1);
	               // fEntity.setContentType("application/zip");
	                
	                post.setEntity(fEntity);
	                post.addHeader("zipFileName", zipFileName);
	                //post.setHeader("zipFileName", zipFileName);
	                ////System.out.println(post.containsHeader("zipFileName"));
	                ////System.out.println(zipFileName);
	                
	                HttpResponse response = client.execute(post);
	                resEntity = response.getEntity();
	              
	                res_code = response.getStatusLine().getStatusCode();	
	                
	                System.out.println("Mobile Execution :"+res_code);
	                // http response code >> chk for response and update syncd' records..
	                final String response_str = EntityUtils.toString(resEntity);
	                if (resEntity != null) {		// **** check for response >> if OK >> update records in db as "synced" 0->1  || ELSE toast= sync error...
	                    Log.i("RESPONSE",response_str);
	                    runOnUiThread(new Runnable(){
	                           public void run() {
	                                try {
	                                	System.out.println("After Sync Successful res_code: " + res_code);
	                                	
	                                	if(res_code==200){
	                                		syncFLAG = 1;
	                                		System.out.println("After Sync Successful res_code: if " + res_code);
	                                		/*db.open();
	                                		System.out.println("After Sync Successful mSelectedItems.size(): " + mSelectedItems.size());
	                                		
	                                		for (int nosSelected = 0; nosSelected <= mSelectedItems.size() - 1; nosSelected++) 
	                						{
	                							String valSN = (String) mSelectedItems.get(nosSelected);
	                							int valID = stNames.indexOf(valSN);
	                							String stIDneeded = stIDs.get(valID);
	                							
	                							System.out.println("After Sync Successful StoreID: " + stIDneeded);
	                							System.out.println("After Sync Successful valSN: " + valSN);
	                							db.UpdateInvoiceButtonCancelStoreSynFlag(stIDneeded, 4,1);

	                							
	                						}
	                                		db.close();*/
	                                		db.open();
	                                		if(whereTo.contentEquals("7"))
	                                		{
	                                			db.updateInvoiceButtonRecordsSyncd("7");
	                                		}
	                                		else if(whereTo.contentEquals("9"))
	                                		{
	                                		
	                                		db.updateInvoiceButtonRecordsSyncd("9");		// update syncd' records
	                                		
	                                		}
	                                		else if(whereTo.contentEquals("11"))
	                                		{
	                                		
	                                		db.updateInvoiceButtonRecordsSyncd("3");		// update syncd' records
	                                		
	                                		}
	                                		db.close();
	                                		//delete recently synced xml (not zip)
	                                		delXML(xmlForWeb[0].toString());
	                                		//System.out.println("inside runonUIthread() - Sync OK");
	                                	}
	                                	else {}
	                                	
	                                 //Toast.makeText(getApplicationContext(),"Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
	                                	//System.out.println("inside runonUIthread() - Sync executed");
	                               } catch (Exception e) {
	                            	   
	                                   e.printStackTrace();
	                                   //showSyncError();
	                                   
	                               }
	                              }
	                       });
	                }

	            } catch (Exception e) {
	                Log.i("SyncMaster", "Sync Failed!", e);
	                //return "Finished with failure";
	                //showSyncError();
	                
	            }

			// Log.i("SyncMaster", "Sync Completed...");
	           // return "Sync Completed...";
			 /*progressDialog2.dismiss();*/
			 finally {
				 
				 Log.i("SyncMaster", "Sync Completed...");
				 client.getConnectionManager().shutdown();
			 }
			 return null;
	        }

		 @Override
	        protected void onCancelled() {
	            Log.i("SyncMaster", "Sync Cancelled");
	        }
	        
		 @Override
	        protected void onPostExecute(Void result) {
			 super.onPostExecute(result);    
	        	Log.i("SyncMaster", "Sync cycle completed");
	        	
	        	/*progressDialog.dismiss();*/
	        	//pDialog2.dismiss();
	        	 if(pDialogGetStores.isShowing()) 
			      {
			    	   pDialogGetStores.dismiss();
				  }
			
	        	
	        	if(syncFLAG == 0)
	        	{
	        		showSyncError();
	        	}
	        	else
	        	{
	        		/*db.open();
	        		if(whereTo.contentEquals("11"))
            		{
            			db.updateInvoiceButtonRecordsSyncd("7");
            		}
            		else
            		{
            		
            		db.updateInvoiceButtonRecordsSyncd("3");		// update syncd' records
            		
            		}		// update syncd' records
            		db.close();*/
	        		showSyncSuccess();
	        	}
	        }

	        /*protected void onProgressUpdate(String... values) {
	           // super.onProgressUpdate(values);
	        }*/

	}
	
	
	/*//lockdown_KEYS STARTS
	private Runnable mUpdateUiMsg = new Runnable() {
        public void run() {
            getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
         }
    };
	@Override
	 public void onAttachedToWindow() {
	  // TODO Auto-generated method stub
	     super.onAttachedToWindow();  
	     Handler lockdownhandler = new android.os.Handler();
	     
	     lockdownhandler.postDelayed(mUpdateUiMsg, 100);
	 }


	 public boolean onKeyDown(int keyCode, KeyEvent event) {
	  // TODO Auto-generated method stub
	  if(keyCode==KeyEvent.KEYCODE_BACK){
	   return true;
	  }
	  if(keyCode==KeyEvent.KEYCODE_HOME){
	   return true;
	  }
	  if(keyCode==KeyEvent.KEYCODE_MENU){
		  return true;
	  }
	  if(keyCode==KeyEvent.KEYCODE_SEARCH){
		  return true;
	  }

	  return super.onKeyDown(keyCode, event);
	 }
	 
	//lockdown ENDS
*/	    
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
		
		mSelectedItems = syncIntent.getStringArrayListExtra("mSelectedItems");
		//mSelectedItemsConfornInvoiceOrders = syncIntent.getStringArrayListExtra("mSelectedItemsConfornInvoiceOrders");
		imei = syncIntent.getStringExtra("imei");
		
		currSysDate = syncIntent.getStringExtra("currSysDate");
		pickerDate = syncIntent.getStringExtra("pickerDate");
		
		System.out.println("Induwati whereTo :"+whereTo);
		//System.out.println("XML path: " + xmlForWeb);
		
		/*chkString = (TextView)findViewById(R.id.textview1_testString);
		chkString.setText(xmlForWeb);*/
		try {
			//new SyncPROdata().execute().get();
			SyncPROdata task = new SyncPROdata(InvoiceSyncMaster.this);
			 task.execute();
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_launcher, menu);
		return true;
	}*/

}
