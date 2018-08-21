package project.astix.com.balajisosfadirectsales;

/**
 * Created by Sunil on 10/25/2017.
 */


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.astix.Common.CommonInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class WebViewActivity extends BaseActivity
{


     String ImageUrl;


    PRJDatabase dbengine= new PRJDatabase(this);


    ProgressDialog progressDialog;

    public	Timer timer;
    public	MyTimerTask myTimerTask;

    public int Retry=0;

    public WebView webView;

    public TextView txtVw_name;
    public TextView txtVw_contact;

    public String imei;
    public String comeFrom="";

    String StockDate="";
    String PersonNodeId="";
    String PersonNodeType="";





    public void customHeader()
    {
        TextView tv_heading=(TextView) findViewById(R.id.tv_heading);
        //tv_heading.setText(getText(R.string.DataReport));
        if(comeFrom.equals("ManageParty"))
        {
          //  tv_heading.setText(getText(R.string.ManagePartyMsg));
        }
        else if(comeFrom.equals("StockOut"))
        {
            tv_heading.setText(getText(R.string.ReadyStockOut));
        }
        else if(comeFrom.equals("StockIn"))
        {
            tv_heading.setText(getText(R.string.ReadyStockIn));
        }
        ImageView imgVw_next=(ImageView) findViewById(R.id.imgVw_next);
        imgVw_next.setVisibility(View.GONE);
        ImageView imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        imgVw_back.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if(comeFrom.equals("StockIn"))
                {
                    Intent submitStoreIntent = new Intent(WebViewActivity.this, SplashScreen.class);
                    startActivity(submitStoreIntent);
                    finish();
                }
                else
                {
                    finish();
                }
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = tManager.getDeviceId();


        if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
        {
            imei = tManager.getDeviceId();
            CommonInfo.imei=imei;
        }
        else
        {
            imei= CommonInfo.imei.trim();
        }

        long  syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        StockDate = df.format(dateobj);

        String SONodeIdAndNodeType= dbengine.fngetSalesPersonMstrData();

        PersonNodeId=SONodeIdAndNodeType.split(Pattern.quote("^"))[0];
        PersonNodeType=SONodeIdAndNodeType.split(Pattern.quote("^"))[1];


        /*String UserNameandContact=dbengine.fnGetUserNameContact();

        String userName=UserNameandContact.split(Pattern.quote("^"))[0];
        String ContactNo=UserNameandContact.split(Pattern.quote("^"))[1];

        txtVw_name=(TextView) findViewById(R.id.txtVw_name);
        txtVw_name.setText(userName);
        txtVw_contact=(TextView) findViewById(R.id.txtVw_contact);
        txtVw_contact.setText(ContactNo);*/

        if(Retry==0);
        {
            Intent intentData=getIntent();
            comeFrom=intentData.getStringExtra("comeFrom");
             if(comeFrom.equals("StockOut"))
              {
                 // StockDate=intentData.getStringExtra("StockDate");
                //  String salesmanIdIntent=intentData.getStringExtra("salesmanId");

                //  PersonNodeId=salesmanIdIntent.split(Pattern.quote("^"))[0];
                //  PersonNodeType=salesmanIdIntent.split(Pattern.quote("^"))[1];
                 // CommonInfo.FlgStockOutDone=1;
              }
             else if(comeFrom.equals("StockIn"))
             {
                // StockDate=intentData.getStringExtra("StockDate");
                // String salesmanIdIntent=intentData.getStringExtra("salesmanId");

                // PersonNodeId=salesmanIdIntent.split(Pattern.quote("^"))[0];
               //  PersonNodeType=salesmanIdIntent.split(Pattern.quote("^"))[1];
                // CommonInfo.FlgStockInDone=1;
             }

        }
        customHeader();
        if(isOnline())
        {

            if (timer!=null)
            {
                timer.cancel();
                timer = null;
            }

            timer = new Timer();
            myTimerTask = new MyTimerTask();

            timer.schedule(myTimerTask,30000);

            try
            {
                progressDialog = new ProgressDialog(WebViewActivity.this);
                progressDialog.setMessage("Please Wait...");
                progressDialog.setCancelable(false);



                int ApplicationID= CommonInfo.Application_TypeID;

               // ImageUrl=ImageUrl+"?nid="+nid+"&ntype="+ntype;
               // http://103.20.212.194/ManageOrder/frmStockIn.aspx?PersonNodeId=1&PersonNodeType=10&StockDate=21-Feb-2018&FlgPDA=1

                if(comeFrom.equals("ManageParty"))
                {
                   //  ImageUrl= CommonInfo.WebManagePartyUrl.trim();
                   // ImageUrl=ImageUrl+"?imei="+imei;
                }
                else if(comeFrom.equals("StockOut"))
                {
                    ImageUrl= CommonInfo.WebStockOutUrl.trim();
                    ImageUrl=ImageUrl+"?PersonNodeId="+PersonNodeId+"&PersonNodeType="+PersonNodeType+"&StockDate="+StockDate+"&FlgPDA="+1;
                }
                else if(comeFrom.equals("StockIn"))
                {
                    ImageUrl= CommonInfo.WebStockInUrl.trim();
                    ImageUrl=ImageUrl+"?PersonNodeId="+PersonNodeId+"&PersonNodeType="+PersonNodeType+"&StockDate="+StockDate+"&FlgPDA="+1;
                }
                else
                {

                }



                webView=(WebView) findViewById(R.id.webView);
                webView.setWebViewClient(new MyBrowser(progressDialog));
                webView.getSettings().setLoadsImagesAutomatically(true);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.loadUrl(ImageUrl);

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



    class MyTimerTask extends TimerTask
    {

        @Override
        public void run()
        {

            runOnUiThread(new Runnable()
            {

                @Override
                public void run()
                {

                    if(progressDialog.isShowing())
                    {
                        //  progressDialog.cancel();
                        //  webView.setVisibility(View.GONE);
                        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(WebViewActivity.this);
                        alertDialogNoConn.setTitle("Internet issue");
                        //alertDialogNoConn.setMessage(getText(R.string.syncAlertErrMsggg));
                        alertDialogNoConn.setMessage(getText(R.string.internetslowMsggg));
                        alertDialogNoConn.setCancelable(false);
                        alertDialogNoConn.setPositiveButton("OK",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.dismiss();

                                    }
                                });
                        alertDialogNoConn.setNegativeButton("Abort/Cancle",
                                new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        progressDialog.cancel();
                                        dialog.dismiss();
                                        Retry=1;
                                        Bundle bundle=new Bundle();
                                        onCreate(bundle);

                                    }
                                });
                        alertDialogNoConn.setIcon(R.drawable.error_info_ico);
                        AlertDialog alert = alertDialogNoConn.create();
                        alert.show();

                    }



                }});
        }

    }


}

