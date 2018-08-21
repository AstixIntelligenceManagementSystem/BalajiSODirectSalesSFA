package project.astix.com.balajisosfadirectsales;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.Log;

import com.astix.Common.CommonInfo;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class ImageUploadFromFolderAsyncTask extends AsyncTask<Void,Void,Boolean>
{
    int serverResponseCode = 0;
    int responseCode=0;
    PRJDatabase dbengine;
    String the_string_response="0";
    InputStream inputStream;
    // public String imei=null;
    // public String fDate=null;
    private TaskListner mTaskListner=null;
    boolean isErrorExist=false;

    private Context mContext;

    public ImageUploadFromFolderAsyncTask(Context context)
    {
        mContext = context;
        dbengine = new PRJDatabase(mContext);
        //this.imei=imei;
        // this.fDate=fDate;
        mTaskListner=(TaskListner)context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... args)
    {




        try
        {



            File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);

            // check number of files in folder
            String [] AllFilesName= checkNumberOfFiles(del);


            if(AllFilesName.length>0)
            {


                for(int vdo=0;vdo<AllFilesName.length;vdo++)
                {
                    String fileUri=  AllFilesName[vdo];


                    String f1=Environment.getExternalStorageDirectory().getPath()+ "/" + CommonInfo.ImagesFolder + "/" +fileUri;
                    // System.out.println("Sunil Again each file full path"+f1);
                    try
                    {
                        responseCode= upLoad2Server(f1,fileUri);
                    }
                    catch (Exception e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    //if(responseCode!=200)
                    if(!the_string_response.equals("Abhinav"))
                    {
                        break;
                    }

                }

            }
            else
            {
                responseCode=200;
            }







        } catch (Exception e)
        {
            isErrorExist=true;
            e.printStackTrace();

        }

        return isErrorExist;

    }


    @Override
    protected void onPostExecute(Boolean isErrorExist)
    {
        if(dbengine.fnCheckForPendingXMLFilesInTable()==1)
        {
            new XMLFileUploadAsyncTask(mContext).execute();
        }
        else if(checkXMLFilesInFolder()>0)
        {
            new XMLFileUploadFromFolderAsyncTask(mContext).execute();
        }
        else
        {
            mTaskListner.onTaskFinish(isErrorExist,2);
        }
    }


    public  int upLoad2Server(String sourceFileUri,String fileUri)
    {
        String currentImageFileName=fileUri;

        Bitmap bmp = BitmapFactory.decodeFile(sourceFileUri);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        String image_str=  BitMapToString(bmp);
        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();


        try
        {
            stream.flush();
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            isErrorExist=true;
        }
        try
        {
            stream.close();
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            isErrorExist=true;
        }

        long syncTIMESTAMP = System.currentTimeMillis();
        Date datefromat = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS", Locale.ENGLISH);
        String onlyDate=df.format(datefromat);


        nameValuePairs.add(new BasicNameValuePair("image", image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName",currentImageFileName));
        nameValuePairs.add(new BasicNameValuePair("comment","NA"));
        nameValuePairs.add(new BasicNameValuePair("storeID","0"));
        nameValuePairs.add(new BasicNameValuePair("date",onlyDate));
        nameValuePairs.add(new BasicNameValuePair("routeID","0"));

        try
        {

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, 0);
            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath.trim());


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            the_string_response="0";
            the_string_response = convertResponseToString(response);

            System.out.println("Sunil Doing Testing Response after sending Image" + the_string_response);

            //  if(serverResponseCode == 200)
            if(the_string_response.equals("Abhinav"))
            {

                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +currentImageFileName.toString().trim();

                File fdelete = new File(file_dj_path);
                if (fdelete.exists())
                {
                    if (fdelete.delete())
                    {
                        callBroadCast();
                    }

                }

            }

        }catch(Exception e)
        {
            isErrorExist=true;

        }
        return serverResponseCode;
    }

    public int checkXMLFilesInFolder()
    {
        int totalFiles=0;
        File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        String [] AllFilesName= checkNumberOfFiles(del);

        if(AllFilesName.length>0)
        {
            totalFiles=AllFilesName.length;
        }
        return totalFiles;
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


    public void uploadImage(String sourceFileUri,String fileName,String tempIdImage) throws IOException
    {
        BitmapFactory.Options IMGoptions01 = new BitmapFactory.Options();
        IMGoptions01.inDither=false;
        IMGoptions01.inPurgeable=true;
        IMGoptions01.inInputShareable=true;
        IMGoptions01.inTempStorage = new byte[16*1024];

        Bitmap bitmap = BitmapFactory.decodeFile(Uri.parse(sourceFileUri).getPath(),IMGoptions01);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream); //compress to which format you want.

        String image_str = BitMapToString(bitmap);
        ArrayList<NameValuePair> nameValuePairs = new  ArrayList<NameValuePair>();

        stream.flush();
        stream.close();

        bitmap.recycle();
        nameValuePairs.add(new BasicNameValuePair("image",image_str));
        nameValuePairs.add(new BasicNameValuePair("FileName", fileName));
        nameValuePairs.add(new BasicNameValuePair("TempID", tempIdImage));
        try
        {

            HttpParams httpParams = new BasicHttpParams();
            int some_reasonable_timeout = (int) (30 * DateUtils.SECOND_IN_MILLIS);


            HttpConnectionParams.setSoTimeout(httpParams, some_reasonable_timeout + 2000);


            HttpClient httpclient = new DefaultHttpClient(httpParams);
            HttpPost httppost = new HttpPost(CommonInfo.ImageSyncPath);



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);

            String the_string_response = convertResponseToString(response);
            if(the_string_response.equals("Abhinav"))
            {
                dbengine.updateSSttImage(fileName, 4);
                dbengine.fndeleteSbumittedStoreImagesOfSotre(4);

                String file_dj_path = Environment.getExternalStorageDirectory() + "/"+CommonInfo.ImagesFolder+"/"+fileName;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }

            }

        }catch(Exception e)
        {

            System.out.println(e);
            //	IMGsyOK = 1;

        }
    }
    public void callBroadCast()
    {
        if (Build.VERSION.SDK_INT >= 14)
        {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(mContext, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {

            mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }
    public String convertResponseToString(HttpResponse response) throws IllegalStateException, IOException
    {

        String res = "";
        StringBuffer buffer = new StringBuffer();
        inputStream = response.getEntity().getContent();
        int contentLength = (int) response.getEntity().getContentLength(); //getting content length…..
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
                    buffer.append(new String(data, 0, len)); //converting to string and appending  to stringbuffer…..
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                inputStream.close(); // closing the stream…..
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            res = buffer.toString();     // converting stringbuffer to string…..

            //System.out.println("Result : " + res);
            //Toast.makeText(MainActivity.this, "Result : " + res, Toast.LENGTH_LONG).show();
            ////System.out.println("Response => " +  EntityUtils.toString(response.getEntity()));
        }
        return res;
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
        String result= Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }
}


