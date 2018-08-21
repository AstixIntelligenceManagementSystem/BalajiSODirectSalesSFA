package project.astix.com.balajisosfadirectsales;


import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.astix.Common.CommonInfo;

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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class XMLFileUploadFromFolderAsyncTask extends AsyncTask<Void,Void,Boolean>
{
    PRJDatabase dbengine;

    InputStream inputStream;
    // public String imei=null;
    // public String fDate=null;
    private TaskListner mTaskListner=null;
    public boolean serviceException=false;
    String xmlFileName;
    public String[] xmlForWeb = new String[1];
    int responseCode = 0;
    private Context mContext;

    boolean isErrorExist=false;
    int serverResponseCode = 0;

    public XMLFileUploadFromFolderAsyncTask(Context context)
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
        File LTFoodXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

        if (!LTFoodXMLFolder.exists())
        {
            LTFoodXMLFolder.mkdirs();
        }
    }

    @Override
    protected Boolean doInBackground(Void... args)
    {




        try
        {

            File del = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            // check number of files in folder
            String [] AllFilesName= checkNumberOfFiles(del);


            if(AllFilesName.length>0)
            {

                for(int vdo=0;vdo<AllFilesName.length;vdo++)
                {
                    String fileUri=  AllFilesName[vdo];

                    if(fileUri.contains(".zip"))
                    {
                        File file = new File(Environment.getExternalStorageDirectory().getPath()+ "/" + CommonInfo.OrderXMLFolder + "/" +fileUri);
                        file.delete();
                    }
                    else
                    {
                        String f1=Environment.getExternalStorageDirectory().getPath()+ "/" + CommonInfo.OrderXMLFolder + "/" +fileUri;
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
                    }
                    if(responseCode!=200)
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
        mTaskListner.onTaskFinish(isErrorExist,4);
    }



    public  int upLoad2Server(String sourceFileUri,String fileUri)
    {

        fileUri=fileUri.replace(".xml", "");

        String fileName = fileUri;
        String zipFileName=fileUri;

        String newzipfile = Environment.getExternalStorageDirectory() + "/"+CommonInfo.OrderXMLFolder+"/" + fileName + ".zip";

        sourceFileUri=newzipfile;

        xmlForWeb[0]=         Environment.getExternalStorageDirectory() + "/"+CommonInfo.OrderXMLFolder+"/" + fileName + ".xml";


        try
        {
            zip(xmlForWeb,newzipfile);
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
        int maxBufferSize = 1 * 1024 * 1024;


        File file2send = new File(newzipfile);

        String urlString = CommonInfo.OrderSyncPath.trim()+"?CLIENTFILENAME=" + zipFileName;

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

            //Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

            if(serverResponseCode == 200)
            {
                //syncFLAG = 1;


                dbengine.upDateTblXmlFile(fileName);
                delXML(xmlForWeb[0].toString());


            }
            else
            {
               // syncFLAG = 0;
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex)
        {
            ex.printStackTrace();
            isErrorExist=true;
        } catch (Exception e)
        {
            e.printStackTrace();
            isErrorExist=true;
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

    public void deleteViewdXml(String file_dj_path)
    {
        File dir=   Environment.getExternalStorageDirectory();
        File fdelete=new File(dir,file_dj_path);

        if (fdelete.exists()) {
            if (fdelete.delete()) {
                Log.e("-->", "file Deleted :" + file_dj_path);
                callBroadCast();
            } else {
                Log.e("-->", "file not Deleted :" + file_dj_path);
            }
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
}



