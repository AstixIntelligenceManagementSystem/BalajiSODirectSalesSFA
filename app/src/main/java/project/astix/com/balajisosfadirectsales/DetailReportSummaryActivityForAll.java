package project.astix.com.balajisosfadirectsales;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonInfo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class DetailReportSummaryActivityForAll extends BaseActivity
{

    public Date currDate;
    public SimpleDateFormat currDateFormat;
    public String userDate;
    boolean serviceException=false;

    int slctdCoverageAreaNodeID=0,slctdCoverageAreaNodeType=0,slctdDSrSalesmanNodeId=0,slctdDSrSalesmanNodeType=0;
    public int chkFlgForErrorToCloseApp=0;
    ServiceWorker newservice = new ServiceWorker();
    public ProgressDialog pDialogGetStores;
    SharedPreferences sharedPref;
    LinkedHashMap<String, String> hmapdsrIdAndDescr_details=new LinkedHashMap<String, String>();
    String[] drsNames;
    public String	SelectedDSRValue="";
    Dialog dialog;
    ImageView img_side_popUp;
    TableLayout tbl_inflate;
    LinkedHashMap<String,String> hmapSummaryData =new LinkedHashMap<>();

    LinkedHashMap<String, LinkedHashMap<String, String>> hmapSummaryDataNew=new LinkedHashMap<String, LinkedHashMap<String, String>>();

    String date_value="";
    String imei="";
    String rID;
    String pickerDate="";
    TextView actualCalls;

    public String back="0";

    PRJDatabase dbengine = new PRJDatabase(this);
    public TableLayout tl2;
    public int bck = 0;

    public String Noti_text="Null";
    public int MsgServerID=0;

    Locale locale  = new Locale("en", "UK");
    String pattern = "###.##";
    DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
    public String fDate;
    public String[] AllDataContainer;


int SalesmanNodeId,SalesmanNodeType,flgDataScope=0;

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
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


    private void getDSRDetail() throws IOException
    {

        int check=dbengine.countDataIntblNoVisitReasonMaster();

        hmapdsrIdAndDescr_details=dbengine.fetch_DSRCoverage_List();

        int index=0;
        if(hmapdsrIdAndDescr_details!=null)
        {
            drsNames=new String[hmapdsrIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapdsrIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator.next();
                drsNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        dbengine.open();
        String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
        dbengine.close();
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
                AlertDialog.Builder alertDialogSaveOK = new AlertDialog.Builder(DetailReportSummaryActivityForAll.this);
                alertDialogSaveOK.setTitle("Notification");

                alertDialogSaveOK.setMessage(Noti_text);
                alertDialogSaveOK.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                long syncTIMESTAMP = System.currentTimeMillis();
                                Date dateobj = new Date(syncTIMESTAMP);
                                SimpleDateFormat df = new SimpleDateFormat(
                                        "dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                                String Noti_ReadDateTime = df.format(dateobj);
                                dbengine.open();

                                dbengine.updatetblNotificationMstr(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
                                dbengine.close();
                                dialog.dismiss();

                            }
                        });
                alertDialogSaveOK.setIcon(R.drawable.info_ico);
                //alertDialogSaveOK.setIcon(R.drawable.error_info_ico);

                AlertDialog alert = alertDialogSaveOK.create();
                alert.show();

            }
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_summary);

        currDate = new Date();
        currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

        userDate = currDateFormat.format(currDate).toString();

        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);
        if(sharedPref.contains("CoverageAreaNodeID"))
        {
            if(sharedPref.getInt("CoverageAreaNodeID",0)!=0)
            {
                CommonInfo.CoverageAreaNodeID=sharedPref.getInt("CoverageAreaNodeID",0);
                CommonInfo.CoverageAreaNodeType=sharedPref.getInt("CoverageAreaNodeType",0);
            }
        }
        if(sharedPref.contains("SalesmanNodeId"))
        {
            if(sharedPref.getInt("SalesmanNodeId",0)!=0)
            {
                CommonInfo.SalesmanNodeId=sharedPref.getInt("SalesmanNodeId",0);
                CommonInfo.SalesmanNodeType=sharedPref.getInt("SalesmanNodeType",0);
            }
        }
        if(sharedPref.contains("flgDataScope"))
        {
            if(sharedPref.getInt("flgDataScope",0)!=0)
            {
                CommonInfo.flgDataScope=sharedPref.getInt("flgDataScope",0);

            }
        }
        if(sharedPref.contains("flgDSRSO"))
        {
            if(sharedPref.getInt("flgDSRSO",0)!=0)
            {
                CommonInfo.FlgDSRSO=sharedPref.getInt("flgDSRSO",0);

            }
        }

        tbl_inflate= (TableLayout) findViewById(R.id.tbl_inflate);
        Intent extras = getIntent();

        bck = extras.getIntExtra("bck", 0);


        if(extras !=null)
        {
            date_value=extras.getStringExtra("userDate");
            pickerDate= extras.getStringExtra("pickerDate");
            imei=extras.getStringExtra("imei");
            rID=extras.getStringExtra("rID");
            back=extras.getStringExtra("back");

        }

        try {
            getDSRDetail();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        SalesmanNodeId= CommonInfo.SalesmanNodeId;
        SalesmanNodeType= CommonInfo.SalesmanNodeType;
        flgDataScope= CommonInfo.flgDataScope;


        TextView  dsrName=(TextView)findViewById(R.id.dsrName);
        if(flgDataScope==1)
        {
            dsrName.setText("My Report");
        }
        else  if(flgDataScope==2)
        {
            try {
                String CoverageArea = dbengine.fetch_DSRCoverage_Name(CommonInfo.SalesmanNodeId, CommonInfo.SalesmanNodeType);
                dsrName.setText(CoverageArea);
            }
            catch(Exception e)
            {

            }
        }
        else if(flgDataScope==3)
        {
            dsrName.setText("Full Territory");
        }
        /*else if(flgDataScope==4)
        {try {
            String DistributorName = dbengine.getDistributorName(CommonInfo.SalesmanNodeId, CommonInfo.SalesmanNodeType);
            dsrName.setText(DistributorName);
        }
        catch(Exception e)
        {

        }
           // dsrName.setText("Full Territory");
        }*/
        else
        {
            dsrName.setText("Report");
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
            imei= CommonInfo.imei.trim();
        }

        Date date1=new Date();
        SimpleDateFormat	sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        fDate = sdf.format(date1).toString().trim();


        TextView txtSalessumuryDate=(TextView)findViewById(R.id.txtSalessumuryDate);
        txtSalessumuryDate.setText("Summary as on :"+fDate);



        setUpVariable();

        if(isOnline())
        {

            try
            {
                GetSummaryForDay task = new GetSummaryForDay(DetailReportSummaryActivityForAll.this);
                task.execute();
            }
            catch (Exception e)
            {
                // TODO Autouuid-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(DetailReportSummaryActivityForAll.this, "Your device has no Data Connection. \n Please ensure Internet is accessible to Continue.", Toast.LENGTH_SHORT).show();
        }






    }
    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }
    public void getDataFromDatabase()
    {
        //String[] tblRowCount=dbengine.fetchTblRowSummary();

        hmapSummaryDataNew=dbengine.fetchTblRowSummary();

        //System.out.println("CountNew " +tblRowCount.length);
        //LinkedHashMap<String, LinkedHashMap<String, String>> hmapSummaryDataNew=new LinkedHashMap<String, LinkedHashMap<String, String>>();

        int a=1;
        for (Map.Entry<String, LinkedHashMap<String, String>> entry : hmapSummaryDataNew.entrySet())
        {
            String key = entry.getKey();
            LinkedHashMap<String, String> ab = entry.getValue();

            if(a==0) {
                LinearLayout addSpace = new LinearLayout(DetailReportSummaryActivityForAll.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 20);
                addSpace.setLayoutParams(lp);
                tbl_inflate.addView(addSpace);
            }
            a=0;

            for (Map.Entry<String, String> entry1 : ab.entrySet())
            {

                String key1 = entry1.getKey();

                String value = entry1.getValue();


                String TodaysSummary=value.split(Pattern.quote("^"))[0];
                String MTDSummary=value.split(Pattern.quote("^"))[1];
                String ColorCode=value.split(Pattern.quote("^"))[2];

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.summary_row_inflate, null);



                TextView measure_val=(TextView) view.findViewById(R.id.measure_val);

                TextView txtValueAchievedToday=(TextView) view.findViewById(R.id.txtValueAchievedToday);
                TextView txtValueAchievedMTD=(TextView) view.findViewById(R.id.txtValueAchievedMTD);

                measure_val.setText(key1);
                txtValueAchievedToday.setText(TodaysSummary);
                txtValueAchievedMTD.setText(MTDSummary);

                view.setBackgroundColor(Color.parseColor(ColorCode));


                tbl_inflate.addView(view);


            }



        }



        //System.out.println("loop"+ tblRowCount.length);




    }



    private class GetSummaryForDay extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog pDialogGetStores;//=new ProgressDialog(DetailReport_Activity.this);
        public GetSummaryForDay(DetailReportSummaryActivityForAll activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            dbengine.open();
            dbengine.truncateAllSummaryDayDataTable();
            dbengine.close();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(getText(R.string.genTermRetrivingSummary));
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

                newservice = newservice.getfnCallspPDAGetDayAndMTDSummary(getApplicationContext(), fDate , imei,SalesmanNodeId,SalesmanNodeType,flgDataScope);

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

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }
            dbengine.open();
//        AllDataContainer= dbengine.fetchAllDataFromtblAllSummary();
            getDataFromDatabase();

            System.out.println("database Data");
            dbengine.close();


//        intializeFields();

        }
    }
/*
public void intializeFields(){

	tbl_inflate= (TableLayout) findViewById(R.id.tbl_inflate);
}
*/

    public void setUpVariable()
    {
        img_side_popUp=(ImageView) findViewById(R.id.img_side_popUp);
        img_side_popUp.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                open_pop_up();
            }
        });

       /* Button btn_Target_Achieved_Report = (Button) findViewById(R.id.btn_Target_Achieved_Report);
        btn_Target_Achieved_Report.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, TargetVsAchievedActivityForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                intent.putExtra("Pagefrom", "2");
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });*/


        ImageView but_back=(ImageView)findViewById(R.id.backbutton);
        but_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent ide=new Intent(DetailReportSummaryActivityForAll.this,AllButtonActivity.class);
                 ide.putExtra("userDate", date_value);
                ide.putExtra("pickerDate", pickerDate);
                ide.putExtra("imei", imei);
                ide.putExtra("rID", rID);
                //startActivity(ide);
                startActivity(ide);
                finish();


            }
        });

        Button btn_sku_wise = (Button) findViewById(R.id.btn_sku_wise);
        btn_sku_wise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, SKUWiseSummaryReport_ByTabForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });

        Button btn_store_wise = (Button) findViewById(R.id.btn_store_wise);
        btn_store_wise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, StoreWiseSummaryReport_ByTabForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });

        Button btn_str_sku_wise = (Button) findViewById(R.id.btn_str_sku_wise);
        btn_str_sku_wise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, StoreAndSKUWiseSummaryReport_ByTabForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });

        Button btn_mtd_sku_wise = (Button) findViewById(R.id.btn_mtd_sku_wise);
        btn_mtd_sku_wise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, SKUWiseSummaryReportMTDForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });

        Button btn_mtd_store_wise = (Button) findViewById(R.id.btn_mtd_store_wise);
        btn_mtd_store_wise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, StoreWiseSummaryReportMTDForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });

        Button btn_mtd_str_sku_wise = (Button) findViewById(R.id.btn_mtd_str_sku_wise);
        btn_mtd_str_sku_wise.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailReportSummaryActivityForAll.this, StoreAndSKUWiseSummaryReportMTDForAll.class);
                intent.putExtra("imei", imei);
                intent.putExtra("userDate", date_value);
                intent.putExtra("pickerDate", pickerDate);
                intent.putExtra("rID", rID);
                DetailReportSummaryActivityForAll.this.startActivity(intent);
                finish();

            }
        });

    }

    private class GetStoreAllData extends AsyncTask<Void, Void, Void> {

        public GetStoreAllData(DetailReportSummaryActivityForAll activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialogGetStores.setTitle(getText(R.string.PleaseWaitMsg));
            pDialogGetStores.setMessage(getText(R.string.RetrivingDataMsg));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                int DatabaseVersion = dbengine.DATABASE_VERSION;
                int ApplicationID = dbengine.Application_TypeID;
                //newservice = newservice.getAvailableAndUpdatedVersionOfApp(getApplicationContext(), imei,fDate,DatabaseVersion,ApplicationID);

                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(1);
                for(int mm = 1; mm<5; mm++)
                {
                    if(mm==1)
                    {
                       /* newservice = newservice.getSOSummary(getApplicationContext(), imei, fDate, DatabaseVersion, ApplicationID);
                        if (!newservice.director.toString().trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }*/
                    }
                    if(mm==2)
                    {
                        newservice = newservice.getStoreAllDetails(getApplicationContext(), imei, fDate, DatabaseVersion, ApplicationID);
                        if (!newservice.director.toString().trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }


                    }
                    if(mm==3)
                    {
                       /* newservice = newservice.callfnSingleCallAllWebServiceSO(getApplicationContext(),ApplicationID,imei);
                        if (!newservice.director.toString().trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }*/

                    }
                    if(mm==4)
                    {
                        newservice = newservice.getQuotationDataFromServer(getApplicationContext(), fDate, imei, "0");
                        if (!newservice.director.toString().trim().equals("1")) {
                            if (chkFlgForErrorToCloseApp == 0) {
                                chkFlgForErrorToCloseApp = 1;
                                break;
                            }

                        }

                    }

                }






            } catch (Exception e) {
            } finally {
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
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

            if (chkFlgForErrorToCloseApp == 1)   // if Webservice showing exception or not excute complete properly
            {
                chkFlgForErrorToCloseApp = 0;
                SharedPreferences sharedPreferences=getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor ed;
                if(sharedPreferences.contains("ServerDate"))
                {
                    ed = sharedPreferences.edit();
                    ed.putString("ServerDate", "0");
                    ed.commit();
                }


                showAlertSingleButtonInfo("Error while retrieving data.");
            }
            else
            {
                dbengine.fnInsertOrUpdate_tblAllServicesCalledSuccessfull(0);
                Intent i=new Intent(DetailReportSummaryActivityForAll.this,StorelistActivity.class);
                startActivity(i);
                finish();

            }
        }

    }

    protected void open_pop_up()
    {
        dialog = new Dialog(DetailReportSummaryActivityForAll.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.selection_header_custom);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.side_dialog_animation;
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();
        parms.gravity = Gravity.TOP | Gravity.LEFT;
        parms.height=parms.MATCH_PARENT;
        parms.dimAmount = (float) 0.5;


        final Button btnDSRTrack = (Button) dialog.findViewById(R.id.btnDSRTrack);
        btnDSRTrack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                /*Intent intent=new Intent(DetailReportSummaryActivityForAll.this,WebViewDSRTrackerActivity.class);
                startActivity(intent);*/
                openDSRTrackerAlert();
            }
        });


        final Button btnExecution = (Button) dialog.findViewById(R.id.btnExecution);
        btnExecution.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GetInvoiceForDay task = new GetInvoiceForDay(DetailReportSummaryActivityForAll.this);
                task.execute();
            }
        });

        final   Button butHome = (Button) dialog.findViewById(R.id.butHome);
        butHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(DetailReportSummaryActivityForAll.this,AllButtonActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final   Button butn_Change_dsr = (Button) dialog.findViewById(R.id.butn_Change_dsr);
        butn_Change_dsr.setVisibility(View.GONE);

        /*final   Button btnDistributorMap = (Button) dialog.findViewById(R.id.btnDistributorMap);
        btnDistributorMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(DetailReportSummaryActivityForAll.this,DistributorMapActivity.class);
                startActivity(intent);
               // finish();
            }
        });

        final   Button btnDistributorStock = (Button) dialog.findViewById(R.id.btnDistributorStock);
        btnDistributorStock.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int CstmrNodeId=0,CstomrNodeType= 0;

                //changes
                if(imei==null)
                {
                    imei= CommonInfo.imei;
                }
                if(fDate==null)
                {
                    Date date1 = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    fDate = sdf.format(date1).toString().trim();
                }
                Intent i=new Intent(DetailReportSummaryActivityForAll.this,DistributorEntryActivity.class);
                i.putExtra("imei", imei);
                i.putExtra("CstmrNodeId", CstmrNodeId);
                i.putExtra("CstomrNodeType", CstomrNodeType);
                i.putExtra("fDate", fDate);
                startActivity(i);
              //  finish();
            }
        });
*/

        final   Button butMarketVisit = (Button) dialog.findViewById(R.id.butMarketVisit);
        butMarketVisit.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               /* Intent intent = new Intent(DetailReportSummaryActivityForAll.this, AllButtonActivity.class);
                startActivity(intent);
                finish();*/
                int checkDataNotSync = dbengine.CheckUserDoneGetStoreOrNot();
                Date date1 = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                fDate = sdf.format(date1).toString().trim();
                if (checkDataNotSync == 1)
                {
                    dbengine.open();
                    String rID = dbengine.GetActiveRouteID();
                    dbengine.close();

                    // Date date=new Date();
                    sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                    String fDateNew = sdf.format(date1).toString();
                    //fDate = passDate.trim().toString();


                    // In Splash Screen SP, we are sending this Format "dd-MMM-yyyy"
                    // But InLauncher Screen SP, we are sending this Format "dd-MM-yyyy"


                    Intent storeIntent = new Intent(DetailReportSummaryActivityForAll.this, StoreSelection.class);
                    storeIntent.putExtra("imei", imei);
                    storeIntent.putExtra("userDate", fDate);
                    storeIntent.putExtra("pickerDate", fDateNew);
                    storeIntent.putExtra("rID", rID);
                    startActivity(storeIntent);
                    finish();

                }
                else
                {
                   /* Intent i=new Intent(DetailReportSummaryActivityForAll.this,AllButtonActivity.class);
                    startActivity(i);
                    finish();*/

                    openMarketVisitAlert();
                }
            }
        });




       /* final   Button butn_store_validation = (Button) dialog.findViewById(R.id.butn_store_validation);
        butn_store_validation.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               *//* Intent intent = new Intent(DetailReportSummaryActivityForAll.this, StorelistActivity.class);
                startActivity(intent);
                finish();*//*
               GetStoreAllData getStoreAllDataAsync= new GetStoreAllData(DetailReportSummaryActivityForAll.this);
                getStoreAllDataAsync.execute();
            }
        });*/

		/* final Button btnTargetVsAchieved=(Button) dialog.findViewById(R.id.btnTargetVsAchieved);

		 btnTargetVsAchieved.setOnClickListener(new OnClickListener() {

			 @Override
			 public void onClick(View arg0) {
				 // TODO Auto-generated method stub
				 Intent intent = new Intent(StoreSelection.this, TargetVsAchievedActivity.class);
				 intent.putExtra("imei", imei);
				 intent.putExtra("userDate", userDate);
				 intent.putExtra("pickerDate", pickerDate);
				 intent.putExtra("rID", rID);
				 intent.putExtra("Pagefrom", "1");
				 //intent.putExtra("back", "0");
				 startActivity(intent);
				 finish();


			 }
		 });*/


		/* btnewAddedStore.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					 Intent intent = new Intent(StoreSelection.this, ViewAddedStore.class);
						intent.putExtra("imei", imei);
						intent.putExtra("userDate", userDate);
						intent.putExtra("pickerDate", pickerDate);
						intent.putExtra("rID", rID);
						//intent.putExtra("back", "0");
						startActivity(intent);
						finish();

				}
			});*/
        final Button btnVersion = (Button) dialog.findViewById(R.id.btnVersion);
        btnVersion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub



                btnVersion.setBackgroundColor(Color.GREEN);
                dialog.dismiss();
            }
        });

        dbengine.open();
        String ApplicationVersion=dbengine.AppVersionID;
        dbengine.close();
        btnVersion.setText("Version No-V"+ApplicationVersion);

        // Version No-V12

        final Button but_SalesSummray = (Button) dialog.findViewById(R.id.btnSalesSummary);
        but_SalesSummray.setVisibility(View.GONE);
















        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    void openMarketVisitAlert()
    {
        final android.support.v7.app.AlertDialog.Builder alert=new android.support.v7.app.AlertDialog.Builder(DetailReportSummaryActivityForAll.this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.market_visit_alert, null);
        alert.setView(view);

        alert.setCancelable(false);

        final RadioButton rb_myVisit= (RadioButton) view.findViewById(R.id.rb_myVisit);
        final RadioButton rb_dsrVisit= (RadioButton) view.findViewById(R.id.rb_dsrVisit);
        final RadioButton rb_jointWorking= (RadioButton) view.findViewById(R.id.rb_jointWorking);
        final Spinner spinner_dsrVisit= (Spinner) view.findViewById(R.id.spinner_dsrVisit);
        final Spinner spinner_jointWorking= (Spinner) view.findViewById(R.id.spinner_jointWorking);
        Button btn_proceed= (Button) view.findViewById(R.id.btn_proceed);
        Button btn_cancel= (Button) view.findViewById(R.id.btn_cancel);

        final android.support.v7.app.AlertDialog dialog=alert.create();
        dialog.show();

        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_proceed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if(rb_myVisit.isChecked())
                {
                    /*String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    CommonInfo.PersonNodeID=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    CommonInfo.PersonNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);*/


                    /*String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    int tempSalesmanNodeId=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    int tempSalesmanNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                    shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                    flgDataScopeSharedPref(1);

                    shardPrefForCoverageArea(0,0);
                    flgDSRSOSharedPref(1);
                    Intent i=new Intent(DetailReportSummaryActivityForAll.this,LauncherActivity.class);
                    startActivity(i);
                    finish();*/
                    dbengine.open();

                    rID= dbengine.GetActiveRouteIDCrntDSR(0,0);
                    dbengine.close();
                    CommonInfo.CoverageAreaNodeID=0;
                    CommonInfo.CoverageAreaNodeType=0;
                    CommonInfo.FlgDSRSO=1;

                    shardPrefForCoverageArea(0,0);
                    if(rID.equals("0"))
                    {

                    }

                    if(dbengine.isDataAlreadyExist(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType))
                    {
                        shardPrefForCoverageArea(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType);

                        shardPrefForSalesman(slctdDSrSalesmanNodeId,slctdDSrSalesmanNodeType);

                        flgDataScopeSharedPref(1);
                        flgDSRSOSharedPref(1);
                        Intent intent=new Intent(DetailReportSummaryActivityForAll.this,StoreSelection.class);
                        intent.putExtra("imei", imei);
                        intent.putExtra("userDate", userDate);
                        intent.putExtra("pickerDate", fDate);
                        intent.putExtra("rID", rID);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        if(dbengine.isDBOpen())
                        {
                            dbengine.close();
                        }


                        new GetStoresForDay(DetailReportSummaryActivityForAll.this).execute();
                    }
                }
                else if(rb_dsrVisit.isChecked())
                {
                    if(!SelectedDSRValue.equals("") && !SelectedDSRValue.equals("Select DSM") && !SelectedDSRValue.equals("No DSM") )
                    {

                        /*String DSRNodeIdAndNodeType= dbengine.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        int tempCoverageAreaNodeID=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempCoverageAreaNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForCoverageArea(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                        flgDSRSOSharedPref(2);


                        String DSRPersonNodeIdAndNodeType= dbengine.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        int tempSalesmanNodeId=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempSalesmanNodeType=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                        flgDataScopeSharedPref(2);
                        Intent i = new Intent(DetailReportSummaryActivityForAll.this, LauncherActivity.class);
                        startActivity(i);
                        finish();*/

                        String DSRNodeIdAndNodeType= dbengine.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        slctdCoverageAreaNodeID=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        slctdCoverageAreaNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                        CommonInfo.CoverageAreaNodeID=slctdCoverageAreaNodeID;
                        CommonInfo.CoverageAreaNodeType=slctdCoverageAreaNodeType;
                        CommonInfo.FlgDSRSO=2;

                        String DSRPersonNodeIdAndNodeType= dbengine.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        slctdDSrSalesmanNodeId=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        slctdDSrSalesmanNodeType=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                        dbengine.open();

                        rID= dbengine.GetActiveRouteIDCrntDSR(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType);
                        dbengine.close();


                        if(rID.equals("0"))
                        {

                        }

                        if(dbengine.isDataAlreadyExist(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType))
                        {
                            shardPrefForCoverageArea(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType);

                            shardPrefForSalesman(slctdDSrSalesmanNodeId,slctdDSrSalesmanNodeType);

                            flgDataScopeSharedPref(2);
                            flgDSRSOSharedPref(2);
                            Intent intent=new Intent(DetailReportSummaryActivityForAll.this,StoreSelection.class);
                            intent.putExtra("imei", imei);
                            intent.putExtra("userDate", userDate);
                            intent.putExtra("pickerDate", fDate);
                            intent.putExtra("rID", rID);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            if(dbengine.isDBOpen())
                            {
                                dbengine.close();
                            }


                            new GetStoresForDay(DetailReportSummaryActivityForAll.this).execute();
                        }
                    }
                    else
                    {
                        showAlertForEveryOne("Please select DSM to Proceeds.");
                    }
                }
                else if(rb_jointWorking.isChecked())
                {
                    if(!SelectedDSRValue.equals("") && !SelectedDSRValue.equals("Select DSM") && !SelectedDSRValue.equals("No DSM") )
                    {
                        // Find GPS
                       /* String DSRNodeIdAndNodeType= dbengine.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        CommonInfo.CoverageAreaNodeID=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        CommonInfo.CoverageAreaNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        CommonInfo.FlgDSRSO=2;


                        String DSRPersonNodeIdAndNodeType= dbengine.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        CommonInfo.SalesmanNodeId=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        CommonInfo.SalesmanNodeType=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        CommonInfo.flgDataScope=2;
                        Intent i = new Intent(AllButtonActivity.this, LauncherActivity.class);
                        startActivity(i);
                        finish();*/
                    }
                    else
                    {
                        showAlertForEveryOne("Please select DSM to Proceeds.");
                    }
                }
                else
                {
                    showAlertForEveryOne("Please select atleast one option to Proceeds.");
                }
            }
        });

        rb_myVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_myVisit.isChecked())
                {
                    rb_dsrVisit.setChecked(false);
                    rb_jointWorking.setChecked(false);
                    spinner_dsrVisit.setVisibility(View.GONE);
                }
            }
        });

        rb_dsrVisit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_dsrVisit.isChecked())
                {
                    rb_myVisit.setChecked(false);
                    rb_jointWorking.setChecked(false);
                    ArrayAdapter adapterCategory=new ArrayAdapter(DetailReportSummaryActivityForAll.this, android.R.layout.simple_spinner_item,drsNames);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_dsrVisit.setAdapter(adapterCategory);
                    spinner_dsrVisit.setVisibility(View.VISIBLE);

                    spinner_dsrVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                        {
                            // TODO Auto-generated method stub
                            SelectedDSRValue = spinner_dsrVisit.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0)
                        {
                            // TODO Auto-generated method stub

                        }
                    });

                }
            }
        });

        rb_jointWorking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_jointWorking.isChecked())
                {
                    rb_myVisit.setChecked(false);
                    rb_dsrVisit.setChecked(false);
                    ArrayAdapter adapterCategory=new ArrayAdapter(DetailReportSummaryActivityForAll.this, android.R.layout.simple_spinner_item,drsNames);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_jointWorking.setAdapter(adapterCategory);
                    spinner_jointWorking.setVisibility(View.VISIBLE);

                    spinner_jointWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                        {
                            // TODO Auto-generated method stub
                            SelectedDSRValue = spinner_jointWorking.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0)
                        {
                            // TODO Auto-generated method stub

                        }
                    });

                }
            }
        });

        dialog.show();
    }

    public void showAlertForEveryOne(String msg)
    {
        //AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
        android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(DetailReportSummaryActivityForAll.this);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                //finish();
            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        android.support.v7.app.AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    private class GetInvoiceForDay extends AsyncTask<Void, Void, Void>
    {
        ServiceWorker newservice = new ServiceWorker();
        String rID="0";
        int chkFlgForErrorToCloseApp=0;

        ProgressDialog pDialogGetInvoiceForDay;

        public GetInvoiceForDay(DetailReportSummaryActivityForAll activity)
        {
            pDialogGetInvoiceForDay = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            pDialogGetInvoiceForDay.setTitle(getText(R.string.PleaseWaitMsg));
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

            Date currDate = new Date();
            SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

            String currSysDate = currDateFormat.format(currDate).toString();

            Intent storeIntent = new Intent(DetailReportSummaryActivityForAll.this, InvoiceStoreSelection.class);
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
                android.support.v7.app.AlertDialog.Builder alertDialogNoConn = new android.support.v7.app.AlertDialog.Builder(DetailReportSummaryActivityForAll.this);
                alertDialogNoConn.setTitle("Information");
                alertDialogNoConn.setMessage("There is no Invoice Pending");
                alertDialogNoConn.setCancelable(false);
                alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.dismiss();
                                // but_Invoice.setEnabled(true);
                                chkFlgForErrorToCloseApp=0;
                            }
                        });
                alertDialogNoConn.setIcon(R.drawable.info_ico);
                android.support.v7.app.AlertDialog alert = alertDialogNoConn.create();
                alert.show();
                return;

            }
        }
    }
    public void shardPrefForCoverageArea(int coverageAreaNodeID,int coverageAreaNodeType) {




        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("CoverageAreaNodeID", coverageAreaNodeID);
        editor.putInt("CoverageAreaNodeType", coverageAreaNodeType);


        editor.commit();

    }


    public void shardPrefForSalesman(int salesmanNodeId,int salesmanNodeType) {




        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("SalesmanNodeId", salesmanNodeId);
        editor.putInt("SalesmanNodeType", salesmanNodeType);

        editor.commit();

    }

    public void flgDataScopeSharedPref(int _flgDataScope)
    {
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("flgDataScope", _flgDataScope);
        editor.commit();


    }
    public void flgDSRSOSharedPref(int _flgDSRSO)
    {
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("flgDSRSO", _flgDSRSO);
        editor.commit();


    }

    void openDSRTrackerAlert()
    {
        final android.support.v7.app.AlertDialog.Builder alert=new android.support.v7.app.AlertDialog.Builder(DetailReportSummaryActivityForAll.this);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dsr_tracker_alert, null);
        alert.setView(view);

        alert.setCancelable(false);

        final RadioButton rb_dataReport= (RadioButton) view.findViewById(R.id.rb_dataReport);
        final RadioButton rb_onMap= (RadioButton) view.findViewById(R.id.rb_onMap);


        Button btn_proceed= (Button) view.findViewById(R.id.btn_proceed);
        Button btn_cancel= (Button) view.findViewById(R.id.btn_cancel);

        final android.support.v7.app.AlertDialog dialog=alert.create();
        dialog.show();

        btn_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_proceed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                if(rb_dataReport.isChecked())
                {
                    Intent i=new Intent(DetailReportSummaryActivityForAll.this,WebViewDSRDataReportActivity.class);
                    startActivity(i);

                }
                else if(rb_onMap.isChecked())
                {
                    Intent i = new Intent(DetailReportSummaryActivityForAll.this, WebViewDSRTrackerActivity.class);
                    startActivity(i);

                }

                else
                {
                    showAlertForEveryOne("Please select atleast one option to Proceeds.");
                }
            }
        });

        rb_dataReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_dataReport.isChecked())
                {
                    rb_onMap.setChecked(false);

                }
            }
        });

        rb_onMap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_onMap.isChecked())
                {
                    rb_dataReport.setChecked(false);

                }
            }
        });



        dialog.show();
    }
    private class GetStoresForDay extends AsyncTask<Void, Void, Void>
    {


        public GetStoresForDay(DetailReportSummaryActivityForAll activity)
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

					/*showAlertBox("Your Phone  Date is not Up to date.Please Correct the Date.");
					dbengine.open();
					dbengine.maintainPDADate();
					dbengine.reCreateDB();
					dbengine.close();
					return;*/
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


            pDialogGetStores.setTitle(getText(R.string.PleaseWaitMsg));
            pDialogGetStores.setMessage(getText(R.string.RetrivingDataMsg));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();
            if(dbengine.isDBOpen())
            {
                dbengine.close();
            }

        }

        @Override
        protected Void doInBackground(Void... args)
        {
            ServiceWorker newservice = new ServiceWorker();

            try
            {
                for(int mm = 1; mm < 38  ; mm++)
                {
                    System.out.println("bywww "+mm);
                    if(mm==1)
                    {




                       /* newservice = newservice.getallStores(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==2)
                    {

                        /*newservice = newservice.getallProduct(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=2)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==3)
                    {

                       /* newservice = newservice.getCategory(getApplicationContext(), imei);
                        if(newservice.flagExecutedServiceSuccesfully!=3)
                        {
                            serviceException=true;
                            break;
                        }*/

                    }
                    if(mm==4)
                    {

                       /* Date currDateNew = new Date();
                        SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                        String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
                        newservice = newservice.getAllNewSchemeStructure(getApplicationContext(), currSysDateNew, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=4)
                        {
                            serviceException=true;
                            break;
                        }*/

                    }
                    if(mm==5)
                    {

                      /*  Date currDateNew = new Date();
                        SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                        String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
                        newservice = newservice.getallPDASchAppListForSecondPage(getApplicationContext(), currSysDateNew, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=5)
                        {
                            serviceException=true;
                            break;
                        }*/
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
                        //newservice = newservice.getfnGetStoreWiseTarget(getApplicationContext(), fDate, imei, rID);
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
                        /*newservice = newservice.GetPDAIsSchemeApplicable(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=21)
                        {
                            serviceException=true;
                            break;
                        }*/

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
                        /*newservice = newservice.GetLODDetailsOnLastSalesSummary(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=29)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }

                    if(mm==31)
                    {
                        /*newservice = newservice.GetCallspForPDAGetLastVisitDate(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=31)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==32)
                    {
                        /*newservice = newservice.GetCallspForPDAGetLastOrderDate(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=32)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==33)
                    {
                        /*newservice = newservice.GetCallspForPDAGetLastVisitDetails(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=33)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==34)
                    {
                        /*newservice = newservice.GetCallspForPDAGetLastOrderDetails(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=34)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==35)
                    {
                        /*newservice = newservice.GetCallspForPDAGetLastOrderDetails_TotalValues(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=35)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==36)
                    {
                       /* newservice = newservice.GetCallspForPDAGetExecutionSummary(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=36)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }

                    if(mm==37)
                    {
                        /*newservice = newservice.getQuotationDataFromServer(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=37)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }

				/*if(mm==38)
				{
					newservice = newservice.fnGetStoreListWithPaymentAddressMR(getApplicationContext(), fDate, imei, rID);

				}*/

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
                    //but_GetStore.setEnabled(true);
                    showAlertException("Error.....","Error while Retrieving Data!\n Please Retry");
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
                //dbengine.close();
                shardPrefForCoverageArea(slctdCoverageAreaNodeID,slctdCoverageAreaNodeType);

                shardPrefForSalesman(slctdDSrSalesmanNodeId,slctdDSrSalesmanNodeType);

                flgDataScopeSharedPref(2);
                flgDSRSOSharedPref(2);
                //onCreate(new Bundle());

                if(dbengine.isDBOpen())
                {
                    dbengine.close();
                }


                Intent storeIntent = new Intent(DetailReportSummaryActivityForAll.this, LauncherActivity.class);
                storeIntent.putExtra("imei", imei);
                storeIntent.putExtra("userDate", userDate);
                storeIntent.putExtra("pickerDate", fDate);
                storeIntent.putExtra("rID", rID);


                startActivity(storeIntent);
                finish();

            }

        }
    }

    public void showAlertException(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailReportSummaryActivityForAll.this);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                new GetStoresForDay(DetailReportSummaryActivityForAll.this).execute();
                dialog.dismiss();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.dismiss();
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}