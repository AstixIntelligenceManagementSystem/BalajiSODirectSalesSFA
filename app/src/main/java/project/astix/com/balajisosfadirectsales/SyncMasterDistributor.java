package project.astix.com.balajisosfadirectsales;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.astix.Common.CommonInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SyncMasterDistributor extends Activity
{

    // New Sync way
    public Timer timerForDataSubmission;
    public	MyTimerTaskForDataSubmission myTimerTaskForDataSubmission;


    SyncImageData task1;
    SyncXMLfileData task2;

    private String FilePathStrings;
    public String fnameIMG;

    public String UploadingImageName;

    private File[] listFile;
    public  File fileintial;
    //  public String routeID="0";
    String xmlFileName;
    int serverResponseCode = 0;




    Timer timer,timer2;
    String progressMsg;

    public ProgressDialog pDialogGetStores;


    public String[] xmlForWeb = new String[1];


    HttpEntity resEntity;
    private SyncMasterDistributor _activity;



    public int syncFLAG = 0;
    public int res_code;
    public String zipFileName;
    ProgressDialog PDpicTasker;
    public String whereTo;
    public int IMGsyOK = 0;
    ProgressDialog pDialog2;
    InputStream inputStream;




    public  File dir;

    PRJDatabase db = new PRJDatabase(this);
    PRJDatabase dbengine = new PRJDatabase(this);
    class MyTimerTaskForDataSubmission extends TimerTask
    {

        @Override
        public void run()
        {

            SyncMasterDistributor.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (timerForDataSubmission != null) {
                        timerForDataSubmission.cancel();
                        timerForDataSubmission = null;
                    }
                    if(task1!=null){
                        if(task1.getStatus()== AsyncTask.Status.RUNNING)
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


                    Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, AllButtonActivity.class);
                    startActivity(submitStoreIntent);
                    finish();
                }});
        }

    }
    public void showSyncError()
    {
        AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(SyncMasterDistributor.this);
        alertDialogSyncError.setTitle("Sync Error!");
        alertDialogSyncError.setCancelable(false);

        if(whereTo.contentEquals("11"))
        {
            alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsgDayEndOrChangeRoute));
        }
        else
        {
            alertDialogSyncError.setMessage(getText(R.string.syncAlertErrMsg));
        }
        alertDialogSyncError.setNeutralButton("OK",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, AllButtonActivity.class);
                startActivity(submitStoreIntent);
                finish();

            }
        });
        alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

        AlertDialog alert = alertDialogSyncError.create();
        alert.show();

    }
    public void showSyncErrorStart()
    {
        AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(SyncMasterDistributor.this);
        alertDialogSyncError.setTitle("Sync Error!");
        alertDialogSyncError.setCancelable(false);
        alertDialogSyncError.setMessage("Sync Error! \n\n Please check your Internet Connectivity & Try Again!");
        alertDialogSyncError.setNeutralButton("OK",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, AllButtonActivity.class);
                startActivity(submitStoreIntent);
                finish();
            }
        });
        alertDialogSyncError.setIcon(R.drawable.sync_error_ico);

        AlertDialog alert = alertDialogSyncError.create();
        alert.show();

    }
    public void showSyncSuccessStart()
    {
        AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(SyncMasterDistributor.this);
        alertDialogSyncOK.setTitle("Information");
        alertDialogSyncOK.setCancelable(false);
       /* if(StoreSelection.flgChangeRouteOrDayEnd==3)
        {
            alertDialogSyncOK.setMessage(getText(R.string.syncAlertStoreQuotationOKMsg));
        }
        else
        {*/
        alertDialogSyncOK.setMessage(getText(R.string.syncAlertOKMsg));
        // }

        alertDialogSyncOK.setNeutralButton("OK",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                        dialog.dismiss();

                        //  db.open();
                        //System.out.println("Indubati flgChangeRouteOrDayEnd :"+StoreSelection_Old.flgChangeRouteOrDayEnd);
					/*if(StoreSelection.flgChangeRouteOrDayEnd==1 || StoreSelection.flgChangeRouteOrDayEnd==2)
					{
						db.reTruncateRouteTbl();
					}*/


                        //  db.reCreateDB();
                        //  db.close();

                        Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, AllButtonActivity.class);
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








    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
    {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
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
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();


        }
        return res;
    }

    public void sysncStart()
    {


        String[] fp2s; // = "/mnt/sdcard/NMPphotos/1539_24-05-2013_1.jpg";

        try {
            //db.open();
            //String[] sySTidS = db.getStoreIDTblSelectedStoreIDinChangeRouteCase();
            //String date= db.GetPickerDate();
            // db.close();

            showSyncSuccessStart();



        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            db.close();
            e.printStackTrace();
        }
    }


    public static boolean deleteFolderFiles(File path)
    {

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
        AlertDialog.Builder alertDialogSyncOK = new AlertDialog.Builder(SyncMasterDistributor.this);
        alertDialogSyncOK.setTitle("Information");
        alertDialogSyncOK.setCancelable(false);


        alertDialogSyncOK.setMessage("Distributor Mapping submission was successfull.");

        alertDialogSyncOK.setNeutralButton("OK",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {


                CommonInfo.AnyVisit=1;
                dialog.dismiss();

                int flag=0;
                String[] imageToBeDeletedFromSdCard=db.deletFromSDcCardPhotoValidationBasedSstat("4");
                if(!imageToBeDeletedFromSdCard[0].equals("No Data"))
                {
                    for(int i=0;i<imageToBeDeletedFromSdCard.length;i++)
                    {
                        flag=1;

                        String file_dj_path = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.ImagesFolder+"/"+imageToBeDeletedFromSdCard[i].toString().trim();
                        File fdelete = new File(file_dj_path);
                        if (fdelete.exists())
                        {
                            if (fdelete.delete())
                            {

                                callBroadCast();
                            }
                            else
                            {

                            }
                        }
                    }
                    //dbengine.fndeleteSbumittedStoreImagesOfSotre(4);
                }




               /* if(whereTo.contentEquals("11"))
                {

                        Intent submitStoreIntent = new Intent(SyncMaster.this, Splash_Activity.class);
                        startActivity(submitStoreIntent);
                        finish();


                }

                else
                {*/
                Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, DistributorMapActivity.class);
                submitStoreIntent.putExtra("FROM", "AddNewStore_DynamicSectionWise");
                startActivity(submitStoreIntent);
                finish();
                // }

            }
        });
        alertDialogSyncOK.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSyncOK.create();
        alert.show();

    }

    //
    public void delXML(String delPath)
    {
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_master_distributor);

        _activity = this;

        Intent syncIntent = getIntent();
        xmlForWeb[0] = syncIntent.getStringExtra("xmlPathForSync");
        zipFileName = syncIntent.getStringExtra("OrigZipFileName");
        whereTo = syncIntent.getStringExtra("whereTo");



        try
        {

            task1 = new SyncImageData(SyncMasterDistributor.this);
            task1.execute();

            if (timerForDataSubmission != null) {
                timerForDataSubmission.cancel();
                timerForDataSubmission = null;
            }

            timerForDataSubmission = new Timer();
            myTimerTaskForDataSubmission = new MyTimerTaskForDataSubmission();
            timerForDataSubmission.schedule(myTimerTaskForDataSubmission, 120000);


        }
        catch(Exception e)
        {

        }



    }



    private class SyncImageData extends AsyncTask<Void, Void, Void>
    {
        String[] fp2s;
        String[] NoOfOutletID;

        public SyncImageData(SyncMasterDistributor activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStores.setMessage("Submitting Images...");

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
                fileintial = new File(Environment.getExternalStorageDirectory()
                        + File.separator + CommonInfo.ImagesFolder);
                // Create a new folder if no folder named SDImageTutorial exist
                fileintial.mkdirs();
            }


            if (fileintial.isDirectory())
            {
                listFile = fileintial.listFiles();
            }





        }

        @Override
        protected Void doInBackground(Void... params)
        {








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

                try
                {
                    task2 = new SyncXMLfileData(SyncMasterDistributor.this);
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

  /*  public String BitMapToString(Bitmap bitmap)
    {
        int h1=bitmap.getHeight();
        int w1=bitmap.getWidth();
        h1=h1/8;
        w1=w1/8;
        bitmap=Bitmap.createScaledBitmap(bitmap, w1, h1, true);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] arr=baos.toByteArray();
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }*/
  public String BitMapToString(Bitmap bitmap)
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



        public SyncXMLfileData(SyncMasterDistributor activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            File MeijiIndirectSFAxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.DistributorMapXMLFolder);

            if (!MeijiIndirectSFAxmlFolder.exists())
            {
                MeijiIndirectSFAxmlFolder.mkdirs();
            }

            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));

            pDialogGetStores.setMessage("Submitting Details...");

            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();

        }

        @Override
        protected Integer doInBackground(Void... params)
        {



            try {

                String xmlfileNames = dbengine.fnGetXMLFile("3","2");

                int index=0;
                if(!xmlfileNames.equals(""))
                {
                    String[] xmlfileArray= xmlfileNames.split(Pattern.quote("^"));

                    for(int i=0;i<xmlfileArray.length;i++){
                        System.out.println("index"+index);
                        xmlFileName=xmlfileArray[i];



//



                        String newzipfile = Environment.getExternalStorageDirectory() + "/"+ CommonInfo.DistributorMapXMLFolder+"/" + xmlFileName + ".zip";
                        xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/"+ CommonInfo.DistributorMapXMLFolder+"/" + xmlFileName + ".xml";
                        try
                        {
                            zip(xmlForWeb, newzipfile);
                        }
                        catch (Exception e1)
                        {
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
                        int maxBufferSize = 1 * 1024 *1024;

                        File file2send = new File(newzipfile);

                        // It is for Testing Purpose
                        //String urlString = "http://115.124.126.184/ReadXML_PragaSFAForTest/default.aspx?CLIENTFILENAME=" + zipFileName;

                        // It is for Live Purpose
                        // String urlString = "http://115.124.126.184/ReadXML_PragaSFA/default.aspx?CLIENTFILENAME=" + zipFileName;



                        String urlString = CommonInfo.OrderSyncPathDistributorMap.trim()+"?CLIENTFILENAME=" + xmlFileName+".zip";



                        try
                        {

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

                            Log.i("uploadFile", "HTTP Response is : "
                                    + serverResponseMessage + ": " + serverResponseCode);

                            if(serverResponseCode == 200)
                            {

                                dbengine.upDateTblXmlFile(xmlFileName);
                                dbengine.deleteXmlTable("4");
		                     /*dbengine.upDatetbl_allAnswermstr("3");
		                     dbengine.upDatetbl_DynamcDataAnswer("3");*/





                                deleteViewdXml(CommonInfo.OrderXMLFolder+"/"+xmlFileName+".xml");
                                deleteViewdXml(CommonInfo.OrderXMLFolder+"/"+xmlFileName+".zip");


                            }

                            //close the streams //
                            fileInputStream.close();
                            dos.flush();
                            dos.close();
                            index++;
                        } catch (MalformedURLException ex)
                        {

                            if(pDialogGetStores.isShowing())
                            {
                                pDialogGetStores.dismiss();
                            }
                            ex.printStackTrace();


                        } catch (Exception e)
                        {

                            if(pDialogGetStores.isShowing())
                            {
                                pDialogGetStores.dismiss();
                            }

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

                if (myTimerTaskForDataSubmission != null) {
                    myTimerTaskForDataSubmission.cancel();
                    myTimerTaskForDataSubmission=null;
                }
                if(result!=200)
                {
                    if (timerForDataSubmission!=null)
                    {
                        timerForDataSubmission.cancel();
                        timerForDataSubmission = null;
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


                   /* if(whereTo.contentEquals("11"))
                    {
                        showSyncError();
                    }
                    else
                    {
*/
                    Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, DistributorMapActivity.class);
                    startActivity(submitStoreIntent);
                    finish();
                    // }

                }
                else
                {
                    if (timerForDataSubmission!=null)
                    {
                        timerForDataSubmission.cancel();
                        timerForDataSubmission = null;
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

                    //dbengine.fndeleteSbumittedStoreList(4);
                    showSyncSuccess();



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



                Intent submitStoreIntent = new Intent(SyncMasterDistributor.this, AllButtonActivity.class);
                startActivity(submitStoreIntent);
                finish();

            }

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



}

