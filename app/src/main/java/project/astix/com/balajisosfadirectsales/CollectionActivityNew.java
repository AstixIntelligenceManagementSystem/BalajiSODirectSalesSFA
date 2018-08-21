package project.astix.com.balajisosfadirectsales;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.astix.Common.CommonInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

public class CollectionActivityNew extends BaseActivity  implements DatePickerDialog.OnDateSetListener, LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,InterfaceClass
{
/* For Location Srvices Start*/
public String VisitTimeInSideStore="NA";
    public  int flgRestartOrderReview=0;
    public  int flgStoreOrderOrderReview=0;

String fusedData;
    String mLastUpdateTime;
    int countSubmitClicked=0;
Location mCurrentLocation;
    GoogleApiClient mGoogleApiClient;
LocationRequest mLocationRequest;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    PowerManager pm;
    LocationListener locationListener;
    double latitude;
    double longitude;
    public LocationManager locationManager;
    public Location location;
    // PowerManager.WakeLock wl;
    public ProgressDialog pDialog2STANDBY;

    public int butClickForGPS=0;
    public String FusedLocationLatitude="0";
    public String FusedLocationLongitude="0";
    public String FusedLocationProvider="";
    public String FusedLocationAccuracy="0";
    public String GPSLocationLatitude="0";
    public String GPSLocationLongitude="0";
    public String GPSLocationProvider="";
    public String GPSLocationAccuracy="0";
    public String NetworkLocationLatitude="0";
    public String NetworkLocationLongitude="0";
    public String NetworkLocationProvider="";
    public String NetworkLocationAccuracy="0";
    public AppLocationService appLocationService;
    public CoundownClass2 countDownTimer2;
    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;


    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private static final long MIN_TIME_BW_UPDATES = 1000  * 1; //1 second
    private final long startTime = 30000;
    private final long interval = 200;

    public  int flgLocationServicesOnOffOrderReview=0;
    public  int flgGPSOnOffOrderReview=0;
    public  int flgNetworkOnOffOrderReview=0;
    public  int flgFusedOnOffOrderReview=0;
    public  int flgInternetOnOffWhileLocationTrackingOrderReview=0;

    public int powerCheck=0;
    public  PowerManager.WakeLock wl;
/* Fro Location Services Ends*/

    DatabaseAssistant DA = new DatabaseAssistant(this);
    public int flgTransferStatus=0;
    public String strFinalAllotedInvoiceIds="NA";
    public String newfullFileName;
    public String StoreVisitCode="NA";
    public String CollectionCode="NA";
    public String strGlobalInvoiceNumber="NA";
    public String TmpInvoiceCodePDA="NA";
    public int chkflgInvoiceAlreadyGenerated=0;
public CheckBox cb_collection;
LinearLayout lnCollection,ll_collectionMandatory;
    public int flgVisitCollectionMarkedStatus=0;
    TextView totaltextview,dateTextViewFirst,dateTextViewSecond,dateTextViewThird,pymtModeTextView,AmountTextview,chequeNoTextview,DateLabelTextview,BankLabelTextview;

    TextView BankSpinnerSecond,BankSpinnerThird;
Double OverAllAmountCollected=0.0;
    TextView paymentModeSpinnerFirst,paymentModeSpinnerSecond,paymentModeSpinnerThird;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    View convertView;
    ListView listviewPaymentModeFirst,listviewPaymentModeSecond,listviewBankFirst,listviewBankSecond,listviewBankThird;
    ArrayAdapter<String> adapterPaymentModeFirst,adapterPaymentModeSecond,adapterBankFirst,adapterBankSecond,adapterBankThird;
    String[] pymtModeFirstList,pymtModeSecondList,bankfirstList,bankSecondList;
    String[] pymtModeThirdList,bankThirdList;
    LinkedHashMap<String, String> hashmapPymtMdFirst,hashmapPymtMdSecond,hashmapBank,linkedHmapBankID,linkedHmapPaymentModeID;
    EditText inputSearch;
    PRJDatabase dbengine = new PRJDatabase(this);
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day;
    boolean calFirst=false;
    boolean calSecond=false;
    boolean calThird=false;
    EditText amountEdittextFirst,amountEdittextSecond,amountEdittextThird,checqueNoEdittextSecond,checqueNoEdittextThird;


    Button Done_btn;
    String storeIDGlobal;
    String flagNew_orallDataFromDatabase,PaymentModeUpdate,PaymentModeSecondUpdate,AmountUpdate,ChequeNoUpdte,DateUpdate;
    String BankNameUpdate,BankNameSecondUpdate="0",BankNameThirdUpdate="0";
    public String storeID;
    public int flgFromPlace=0;
    public String imei;

    public String date;
    public String pickerDate;
    public String SN;
    public String strGlobalOrderID="0";
    int flgOrderType=0;
    TextView tv_outstandingvalue,tv_MinCollectionvalue,tv_cntInvoceValue,tv_totOutstandingValue;

    HashMap<String,Integer> hmapDistPrdctStockCount =new HashMap<String,Integer>();
    HashMap<String,String> hmapPrdctIdOutofStock=new HashMap<String,String> ();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_activity);

        StoreSelection.flgChangeRouteOrDayEnd=0;
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);

        boolean isGPSok = false;
        boolean isNWok=false;
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isGPSok)
        {
            isGPSok = false;
        }
        if(!isNWok)
        {
            isNWok = false;
        }
        if(!isGPSok && !isNWok)
        {
            try
            {
                showSettingsAlert();
            }
            catch(Exception e)
            {

            }

            isGPSok = false;
            isNWok=false;
        }
        VisitTimeInSideStore=dbengine.fnGetDateTimeString();
        if(powerCheck==0)
        {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
            wl.acquire();
        }

        hashmapBank = dbengine.fnGettblBankMaster();

        getDataFromIntent();

        TextviewInitialization();

        EdittextInitialization();

        DropDownInitialization();

        CalendarInitialization();

         ButtonInialization();


        chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
        if(chkflgInvoiceAlreadyGenerated==1)
        {
            TmpInvoiceCodePDA=dbengine.fnGetExistingInvoiceNumber(storeID);
        }


        cb_collection=(CheckBox)findViewById(R.id.cb_collection);
        tv_outstandingvalue=(TextView) findViewById(R.id.tv_outstandingvalue);
        Double outstandingvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
        outstandingvalue=Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));
        //String.format("%.2f", outstandingvalue)
        tv_outstandingvalue.setText(" : "+String.format("%.2f", outstandingvalue));

        tv_MinCollectionvalue=(TextView) findViewById(R.id.tv_MinCollectionvalue);
        Double MinCollectionvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
        MinCollectionvalue=Double.parseDouble(new DecimalFormat("##.##").format(MinCollectionvalue));
        tv_MinCollectionvalue.setText(""+String.format("%.2f", MinCollectionvalue));

        tv_cntInvoceValue=(TextView) findViewById(R.id.tv_cntInvoceValue);
        Double cntInvoceValue=dbengine.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
        cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));
        tv_cntInvoceValue.setText(" : "+String.format("%.2f", cntInvoceValue));

        tv_totOutstandingValue=(TextView) findViewById(R.id.tv_totOutstandingValue);
        Double totOutstandingValue=outstandingvalue+cntInvoceValue;
        totOutstandingValue=Double.parseDouble(new DecimalFormat("##.##").format(totOutstandingValue));
        tv_totOutstandingValue.setText(" : "+String.format("%.2f", totOutstandingValue));

        linkedHmapPaymentModeID=	dbengine.fnGetPaymentMode();
        linkedHmapBankID=	dbengine.fnGetBankIdData();
        fetchData();

        lnCollection=(LinearLayout)findViewById(R.id.lnCollection);
        ll_collectionMandatory=(LinearLayout)findViewById(R.id.ll_collectionMandatory);
        flgVisitCollectionMarkedStatus=dbengine.checkflgVisitCollectionMarkedStatus(storeID,TmpInvoiceCodePDA);
        lnCollection.setVisibility(View.GONE);
        ll_collectionMandatory.setVisibility(View.GONE);

        if(outstandingvalue>0.0 && cntInvoceValue>0.0)
        {
            ll_collectionMandatory.setVisibility(View.GONE);
            lnCollection.setVisibility(View.VISIBLE);
        }
        else if(outstandingvalue==0.0 && cntInvoceValue>0.0)
        {
            ll_collectionMandatory.setVisibility(View.VISIBLE);
            lnCollection.setVisibility(View.VISIBLE);
        }
        else if(outstandingvalue>0.0 && cntInvoceValue==0.0)
        {
            ll_collectionMandatory.setVisibility(View.GONE);
            lnCollection.setVisibility(View.VISIBLE);
        }


        if(flgVisitCollectionMarkedStatus==1)
        {

            cb_collection.setChecked(true);
        }
        cb_collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_collection.isChecked()==true)
                {
                    lnCollection.setVisibility(View.INVISIBLE);
                    flgVisitCollectionMarkedStatus=1;
                    dbengine.fnUpdateflgVisitCollectionMarkedStatus(storeID,StoreVisitCode,TmpInvoiceCodePDA,flgVisitCollectionMarkedStatus);
                }
                if(cb_collection.isChecked()==false)
                {
                    lnCollection.setVisibility(View.VISIBLE);
                    flgVisitCollectionMarkedStatus=0;
                    dbengine.fnUpdateflgVisitCollectionMarkedStatus(storeID,StoreVisitCode,TmpInvoiceCodePDA,flgVisitCollectionMarkedStatus);
                }
            }
        });



    }

    public void fetchData()
    {
        try
        {
            String  DataFromDatabase=	dbengine.fnRetrieveCollectionDataBasedOnStoreID(StoreVisitCode,storeIDGlobal,strGlobalOrderID,TmpInvoiceCodePDA);
            if(!DataFromDatabase.equals("0"))
            {
                if(DataFromDatabase.contains("$")){
                    String[] collectionData=DataFromDatabase.split(Pattern.quote("$"));
                    for(int i=0;i<=collectionData.length;i++){
                        String collectionDataString=  collectionData[i];
                        SetDataToLayout(collectionDataString);
                    }
                }
                else{
                    SetDataToLayout(DataFromDatabase);
                }

               /* if(!DataFromDatabase.split(Pattern.quote("^"))[0].equals("0"))
                {
                    amountEdittextFirst.setText(DataFromDatabase.split(Pattern.quote("^"))[0]);
                }
                else
                {
                    amountEdittextFirst.setText("");
                }
                if(!DataFromDatabase.split(Pattern.quote("^"))[1].equals("0")) {
                    amountEdittextSecond.setText(DataFromDatabase.split(Pattern.quote("^"))[1]);
                }
                else
                {
                    amountEdittextSecond.setText("");
                }

                if(!DataFromDatabase.split(Pattern.quote("^"))[2].equals("0")) {
                    checqueNoEdittextSecond.setText(DataFromDatabase.split(Pattern.quote("^"))[2]);
                }
                else
                {
                    checqueNoEdittextSecond.setText("");
                }
                dateTextViewSecond.setText(DataFromDatabase.split(Pattern.quote("^"))[3]);
                BankNameUpdate = DataFromDatabase.split(Pattern.quote("^"))[4];
                if (BankNameUpdate.equals("0"))
                {
                    BankSpinnerSecond.setText(getResources().getString(R.string.txtSelect));
                } else
                {
                    BankSpinnerSecond.setText(linkedHmapBankID.get(BankNameUpdate));

                }

                if(!DataFromDatabase.split(Pattern.quote("^"))[5].equals("0")) {
                    amountEdittextThird.setText(DataFromDatabase.split(Pattern.quote("^"))[5]);
                }
                else
                {
                    amountEdittextThird.setText("");
                }

                if(!DataFromDatabase.split(Pattern.quote("^"))[6].equals("0")) {
                    checqueNoEdittextThird.setText(DataFromDatabase.split(Pattern.quote("^"))[6]);
                }
                else
                {
                    checqueNoEdittextThird.setText("");
                }
                dateTextViewThird.setText(DataFromDatabase.split(Pattern.quote("^"))[7]);
                BankNameUpdate = DataFromDatabase.split(Pattern.quote("^"))[8];
                if (BankNameUpdate.equals("0")) {
                    BankSpinnerThird.setText(getResources().getString(R.string.txtSelect));
                } else {
                    BankSpinnerThird.setText(linkedHmapBankID.get(BankNameUpdate));

                }*/

            }


        }
        catch(Exception e)
        {

        }
    }

    private void getDataFromIntent()
    {


        Intent passedvals = getIntent();

        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate = passedvals.getStringExtra("pickerDate");
        SN = passedvals.getStringExtra("SN");
        flgOrderType = passedvals.getIntExtra("flgOrderType",0);
        strGlobalOrderID=passedvals.getStringExtra("OrderPDAID");
        flgFromPlace=Integer.parseInt(passedvals.getStringExtra("FromPlace"));

        storeIDGlobal=storeID;
        StoreVisitCode=dbengine.fnGetStoreVisitCode(storeID);
    }
    public void EdittextInitialization()
    {

        amountEdittextFirst=(EditText) findViewById(R.id.amountEdittextFirst);
        amountEdittextSecond=(EditText) findViewById(R.id.amountEdittextSecond);
        amountEdittextThird=(EditText) findViewById(R.id.amountEdittextThird);

        checqueNoEdittextSecond=(EditText) findViewById(R.id.checqueNoEdittextSecond);
        checqueNoEdittextThird=(EditText) findViewById(R.id.checqueNoEdittextThird);
        amountEdittextFirst.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                double amntFirst=0.00;
                double amntSecond=0.00;
                double amntThird=0.00;
                NumberFormat nf = NumberFormat.getInstance();
                if(!amountEdittextFirst.getText().toString().trim().equals(""))
                {
                   // amntFirst= Double.parseDouble(amountEdittextFirst.getText().toString().trim());
                    try
                    {
                        amntFirst = nf.parse(amountEdittextFirst.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                      System.out.print("i m in errro");
                    }

                }
                if(!amountEdittextSecond.getText().toString().trim().equals(""))
                {
                    //amntSecond= Double.parseDouble(amountEdittextSecond.getText().toString().trim());
                    try
                    {
                        amntSecond = nf.parse(amountEdittextSecond.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }
                }
                if(!amountEdittextThird.getText().toString().trim().equals(""))
                {
                    //amntThird= Double.parseDouble(amountEdittextThird.getText().toString().trim());
                    try
                    {
                        amntThird = nf.parse(amountEdittextThird.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }
                }


                //String Total=String.valueOf(amntFirst+amntSecond);
                totaltextview.setText(String.format("%.2f", amntFirst+amntSecond+amntThird));
                OverAllAmountCollected= amntFirst+amntSecond+amntThird;

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

        amountEdittextSecond.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                double amntFirst=0.00;
                double amntSecond=0.00;
                double amntThird=0.00;
                NumberFormat nf = NumberFormat.getInstance();
                if(!amountEdittextSecond.getText().toString().trim().equals(""))
                {
                   // amntSecond= Double.parseDouble(amountEdittextSecond.getText().toString().trim());
                    try
                    {
                        amntSecond = nf.parse(amountEdittextSecond.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }
                }
                if(!amountEdittextFirst.getText().toString().trim().equals(""))
                {
                   // amntFirst= Double.parseDouble(amountEdittextFirst.getText().toString().trim());
                    try
                    {
                        amntFirst = nf.parse(amountEdittextFirst.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }

                }

                if(!amountEdittextThird.getText().toString().trim().equals(""))
                {
                   // amntThird= Double.parseDouble(amountEdittextThird.getText().toString().trim());
                    try
                    {
                        amntThird = nf.parse(amountEdittextThird.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }
                }


                //String Total=String.valueOf(amntFirst+amntSecond);
                totaltextview.setText(String.format("%.2f", amntFirst+amntSecond+amntThird));
                OverAllAmountCollected= amntFirst+amntSecond+amntThird;
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });


        amountEdittextThird.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                double amntFirst=0.00;
                double amntSecond=0.00;
                double amntThird=0.00;
                NumberFormat nf = NumberFormat.getInstance();
                if(!amountEdittextSecond.getText().toString().trim().equals(""))
                {
                    //amntSecond= Double.parseDouble(amountEdittextSecond.getText().toString().trim());
                    try
                    {
                        amntSecond = nf.parse(amountEdittextSecond.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }
                }
                if(!amountEdittextFirst.getText().toString().trim().equals(""))
                {
                    //amntFirst= Double.parseDouble(amountEdittextFirst.getText().toString().trim());
                    try
                    {
                        amntFirst = nf.parse(amountEdittextFirst.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }

                }

                if(!amountEdittextThird.getText().toString().trim().equals(""))
                {
                    //amntThird= Double.parseDouble(amountEdittextThird.getText().toString().trim());
                    try
                    {
                        amntThird = nf.parse(amountEdittextThird.getText().toString().trim()).doubleValue();
                    }
                    catch(ParseException e)
                    {
                        System.out.print("i m in errro");
                    }
                }


                //String Total=String.valueOf(amntFirst+amntSecond);
                totaltextview.setText(String.format("%.2f", amntFirst+amntSecond+amntThird));
                OverAllAmountCollected= amntFirst+amntSecond+amntThird;
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });

    }




    public void DropDownInitialization()
    {
        paymentModeSpinnerFirst=	(TextView) findViewById(R.id.paymentModeSpinnerFirst);
        paymentModeSpinnerSecond=	(TextView) findViewById(R.id.paymentModeSpinnerSecond);
        paymentModeSpinnerThird=	(TextView) findViewById(R.id.paymentModeSpinnerThird);

        BankSpinnerSecond=	(TextView) findViewById(R.id.BankSpinnerSecond);
        BankSpinnerThird=	(TextView) findViewById(R.id.BankSpinnerThird);



        paymentModeSpinnerFirst.setText("Cash");
        amountEdittextFirst.setEnabled(true);

        paymentModeSpinnerSecond.setText("Cheque/DD");
        amountEdittextSecond.setEnabled(true);

       // paymentModeSpinnerThird.setText("Electronic");
        paymentModeSpinnerThird.setText("Mobile");
        amountEdittextThird.setEnabled(true);


        if(paymentModeSpinnerFirst.equals("Cash"))
        {
            amountEdittextFirst.setEnabled(true);
            dateTextViewFirst.setBackgroundResource(R.drawable.outside_boundry_gray);
            BankNameUpdate="0";
            dateTextViewFirst.setEnabled(false);
            dateTextViewFirst.setText("");
        }

        if(paymentModeSpinnerSecond.equals("Cheque/DD"))
        {
            dateTextViewSecond.setBackgroundResource(R.drawable.outside_boundry);
            checqueNoEdittextSecond.setBackgroundResource(R.drawable.edittex_with_white_background);
            BankSpinnerSecond.setBackgroundResource(R.drawable.spinner_background_with_rectangle);
            amountEdittextSecond.setEnabled(true);
            checqueNoEdittextSecond.setEnabled(true);
            dateTextViewSecond.setEnabled(true);
            BankSpinnerSecond.setEnabled(true);
        }

        /*if(paymentModeSpinnerThird.equals("Electronic"))
        {
            paymentModeSpinnerThird.setText("Electronic");
            dateTextViewThird.setBackgroundResource(R.drawable.outside_boundry);
            checqueNoEdittextThird.setBackgroundResource(R.drawable.edittex_with_white_background);
            BankSpinnerThird.setBackgroundResource(R.drawable.spinner_background_with_rectangle);
           amountEdittextThird.setEnabled(true);
            checqueNoEdittextThird.setEnabled(true);
            dateTextViewThird.setEnabled(true);
            BankSpinnerThird.setEnabled(true);
        }*/

        if(paymentModeSpinnerThird.equals("Mobile"))
        {
            paymentModeSpinnerThird.setText("Mobile");
            dateTextViewThird.setBackgroundResource(R.drawable.outside_boundry_gray);
            checqueNoEdittextThird.setBackgroundResource(R.drawable.edittext_with_gray_background);//.edittex_with_white_background);
            BankSpinnerThird.setBackgroundResource(R.drawable.spinner_background_with_rectangle);
            amountEdittextThird.setEnabled(true);
            checqueNoEdittextThird.setEnabled(false);
            dateTextViewThird.setEnabled(false);
            BankSpinnerThird.setEnabled(false);
            dateTextViewThird.setText("");



        }







        BankSpinnerSecond.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View arg0) {
                if (!amountEdittextSecond.getText().toString().trim().equals("") && !checqueNoEdittextSecond.getText().toString().trim().equals("") && !dateTextViewSecond.getText().toString().trim().equals("21-mar-16"))
                {
                    BankLabelTextview.setError(null);
                    BankLabelTextview.clearFocus();


                    alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = (View) inflater.inflate(R.layout.activity_list,
                            null);

                    listviewBankSecond = (ListView) convertView.findViewById(R.id.list_view);

                    int index = 0;
                    if (hashmapBank != null) {
                        bankSecondList = new String[hashmapBank.size() + 1];
                        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(
                                hashmapBank);
                        Set set2 = map.entrySet();
                        Iterator iterator = set2.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry me2 = (Map.Entry) iterator.next();
                            if (index == 0) {
                                bankSecondList[index] = "Select";

                                index = index + 1;

                                bankSecondList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            } else {
                                bankSecondList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            }

                        }

                    }
                    adapterBankSecond = new ArrayAdapter<String>(CollectionActivityNew.this,
                            R.layout.list_item, R.id.product_name, bankSecondList);

                    listviewBankSecond.setAdapter(adapterBankSecond);
                    inputSearch = (EditText) convertView
                            .findViewById(R.id.inputSearch);
                    inputSearch.setVisibility(View.VISIBLE);
                    alertDialog.setView(convertView);
                    alertDialog.setTitle("Bank");
                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                            // TODO Auto-generated method stub
                            adapterBankSecond.getFilter().filter(arg0.toString().trim());

                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1,
                                                      int arg2, int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                    listviewBankSecond.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            String abc = listviewBankSecond.getItemAtPosition(arg2)
                                    .toString().trim();
                            BankSpinnerSecond.setText(abc);
                            inputSearch.setText("");

                            ad.dismiss();

                            if (abc.equals("Select")) {


                            } else {
                            }
                        }
                    });
                    ad = alertDialog.show();


                }
                else
                {
                    if(amountEdittextSecond.getText().toString().trim().equals(""))
                    {
                       // allMessageAlert("Please Enter the Amount.");
                        showAlertSingleButtonError("Please Enter the Amount.");
                    }
                    else if(checqueNoEdittextSecond.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    }
                    else if(dateTextViewSecond.getText().toString().trim().equals("21-mar-16"))
                    {
                        showAlertSingleButtonError("Please Select the Date.");
                    }
                    else
                    {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo. or Select Date");
                    }


                }
            }

        });



        BankSpinnerThird.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0)
            {
                if (!amountEdittextThird.getText().toString().trim().equals("") && !checqueNoEdittextThird.getText().toString().trim().equals("") && !dateTextViewThird.getText().toString().trim().equals("21-mar-16"))
                {
                    BankLabelTextview.setError(null);
                    BankLabelTextview.clearFocus();


                    alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
                    LayoutInflater inflater = getLayoutInflater();
                    convertView = (View) inflater.inflate(R.layout.activity_list,
                            null);

                    listviewBankThird = (ListView) convertView.findViewById(R.id.list_view);

                    int index = 0;
                    if (hashmapBank != null) {
                        bankThirdList = new String[hashmapBank.size() + 1];
                        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(
                                hashmapBank);
                        Set set2 = map.entrySet();
                        Iterator iterator = set2.iterator();
                        while (iterator.hasNext()) {
                            Map.Entry me2 = (Map.Entry) iterator.next();
                            if (index == 0) {
                                bankThirdList[index] = "Select";

                                index = index + 1;

                                bankThirdList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            } else {
                                bankThirdList[index] = me2.getKey().toString().trim();

                                index = index + 1;
                            }

                        }

                    }
                    adapterBankThird = new ArrayAdapter<String>(CollectionActivityNew.this,
                            R.layout.list_item, R.id.product_name, bankThirdList);

                    listviewBankThird.setAdapter(adapterBankThird);
                    inputSearch = (EditText) convertView
                            .findViewById(R.id.inputSearch);
                    inputSearch.setVisibility(View.VISIBLE);
                    alertDialog.setView(convertView);
                    alertDialog.setTitle("Bank");
                    inputSearch.addTextChangedListener(new TextWatcher() {

                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                            // TODO Auto-generated method stub
                            adapterBankThird.getFilter().filter(arg0.toString().trim());

                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1,
                                                      int arg2, int arg3) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                            // TODO Auto-generated method stub

                        }
                    });
                    listviewBankThird.setOnItemClickListener(new OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1,
                                                int arg2, long arg3) {
                            String abc = listviewBankThird.getItemAtPosition(arg2).toString().trim();
                            BankSpinnerThird.setText(abc);
                            inputSearch.setText("");

                            ad.dismiss();

                            if (abc.equals("Select")) {


                            } else {
                            }
                        }
                    });
                    ad = alertDialog.show();

                }
                else
                {
                    if(amountEdittextThird.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter the Amount.");
                    }
                    else if(checqueNoEdittextThird.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    }
                    else if(dateTextViewThird.getText().toString().trim().equals("21-mar-16"))
                    {
                        showAlertSingleButtonError("Please Select the Date.");
                    }
                    else
                    {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo. or Select Date");
                    }

                }


            }
        });

    }
    public void CalendarInitialization()
    {

        dateTextViewSecond=(TextView) findViewById(R.id.dateTextViewSecond);
        dateTextViewSecond.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0)
            {
                if (!amountEdittextSecond.getText().toString().trim().equals("") && !checqueNoEdittextSecond.getText().toString().trim().equals("")) {
                    DateLabelTextview.setError(null);
                    DateLabelTextview.clearFocus();

                    calSecond = true;
                    calendar = Calendar.getInstance();

                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH);
                    Day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = DatePickerDialog.newInstance(
                            CollectionActivityNew.this, Year, Month, Day);

                    datePickerDialog.setThemeDark(false);

                    datePickerDialog.showYearPickerFirst(false);

                    Calendar calendarForSetDate = Calendar.getInstance();
                    calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                    // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.setAccentColor(Color.parseColor("#5c5696"));

                    datePickerDialog.setTitle("SELECT DATE");
                    datePickerDialog.setMinDate(calendarForSetDate);
				/*
				 * Calendar calendar = Calendar.getInstance();
				 * calendar.setTimeInMillis
				 * (System.currentTimeMillis()+24*60*60*1000);
				 */
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                }
                else
                {
                    if(amountEdittextSecond.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter the Amount.");
                    }
                    else if(checqueNoEdittextSecond.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    }
                     else
                    {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo.");
                    }
                }

            }
        });


        dateTextViewThird=(TextView) findViewById(R.id.dateTextViewThird);
        dateTextViewThird.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0)
            {
                if (!amountEdittextThird.getText().toString().trim().equals("") && !checqueNoEdittextThird.getText().toString().trim().equals(""))
                {
                    DateLabelTextview.setError(null);
                    DateLabelTextview.clearFocus();

                    calThird = true;
                    calendar = Calendar.getInstance();

                    Year = calendar.get(Calendar.YEAR);
                    Month = calendar.get(Calendar.MONTH);
                    Day = calendar.get(Calendar.DAY_OF_MONTH);
                    datePickerDialog = DatePickerDialog.newInstance(
                            CollectionActivityNew.this, Year, Month, Day);

                    datePickerDialog.setThemeDark(false);

                    datePickerDialog.showYearPickerFirst(false);

                    Calendar calendarForSetDate = Calendar.getInstance();
                    calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                    // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.setAccentColor(Color.parseColor("#5c5696"));

                    datePickerDialog.setTitle("SELECT DATE");
                    datePickerDialog.setMinDate(calendarForSetDate);
				/*
				 * Calendar calendar = Calendar.getInstance();
				 * calendar.setTimeInMillis
				 * (System.currentTimeMillis()+24*60*60*1000);
				 */
                    // YOU can set min or max date using this code
                    // datePickerDialog.setMaxDate(Calendar.getInstance());
                    // datePickerDialog.setMinDate(calendar);

                    datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                }
                else
                {
                    if(amountEdittextThird.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter the Amount.");
                    }
                    else if(checqueNoEdittextThird.getText().toString().trim().equals(""))
                    {
                        showAlertSingleButtonError("Please Enter ChequeNo/RefNo.");
                    }
                   else
                    {
                        showAlertSingleButtonError("Please Enter the Amount or ChequeNo/RefNo.");
                    }

                }


            }
        });





    }



    public void TextviewInitialization()
    {
        pymtModeTextView=(TextView) findViewById(R.id.pymtModeTextView);
        AmountTextview=(TextView) findViewById(R.id.AmountTextview);
        chequeNoTextview=(TextView) findViewById(R.id.chequeNoTextview);
        DateLabelTextview=(TextView) findViewById(R.id.DateLabelTextview);
        BankLabelTextview=(TextView) findViewById(R.id.BankLabelTextview);
        totaltextview=(TextView) findViewById(R.id.totaltextview);

    }


    public void ButtonInialization() {
        ImageView btn_bck=(ImageView) findViewById(R.id.btn_bck);
        btn_bck.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
                alertDialog.setTitle("Information");

                alertDialog.setCancelable(false);
                alertDialog.setMessage("Have you saved your data before going back ");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                        Intent ide=new Intent(CollectionActivityNew.this,OrderReview.class);
                        ide.putExtra("SN", SN);
                        ide.putExtra("storeID", storeID);
                        ide.putExtra("imei", imei);
                        ide.putExtra("userdate", date);
                        ide.putExtra("pickerDate", pickerDate);
                        ide.putExtra("flgOrderType", flgOrderType);
                        startActivity(ide);
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();


                    }
                });

                // Showing Alert Message
                alertDialog.show();*/
               if(flgFromPlace==2) {
                   Intent ide = new Intent(CollectionActivityNew.this, ProductOrderReview.class);
                   ide.putExtra("SN", SN);
                   ide.putExtra("storeID", storeID);
                   ide.putExtra("imei", imei);
                   ide.putExtra("userdate", date);
                   ide.putExtra("pickerDate", pickerDate);
                   ide.putExtra("flgOrderType", flgOrderType);

                   startActivity(ide);
                   finish();
               }
                if(flgFromPlace==1) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductInvoiceReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);

                    startActivity(ide);
                    finish();
                }
            }
        });

        Done_btn=(Button) findViewById(R.id.Done_btn);
        Done_btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
/*
               if(flgVisitCollectionMarkedStatus==1)
               {

                       saveDataToDatabase();

               }
               else {*/

                   if (validate() && validateCollectionAmt()) {

                       AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                               CollectionActivityNew.this);
                       alertDialog.setTitle("Information");

                       alertDialog.setCancelable(false);
                       alertDialog.setMessage("Do you want to submit visit data ");
                       alertDialog.setPositiveButton("Yes",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog,
                                                       int which) {
                                       dialog.dismiss();

                                       saveDataToDatabase();
                                      /* FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                                       task.execute();*/


                                       butClickForGPS=3;
                                       dbengine.open();
                                       if ((dbengine.PrevLocChk(storeID.trim(),StoreVisitCode)) )
                                       {
                                           dbengine.close();

                                           FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                                           task.execute();
                                       }
                                       else
                                       {
                                           dbengine.close();
                                          /* appLocationService=new AppLocationService();

								*//* pm = (PowerManager) getSystemService(POWER_SERVICE);
								 *//**//*  wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
							                | PowerManager.ACQUIRE_CAUSES_WAKEUP
							                | PowerManager.ON_AFTER_RELEASE, "INFO");
							        wl.acquire();*//*


                                           pDialog2STANDBY=ProgressDialog.show(CollectionActivityNew.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
                                           pDialog2STANDBY.setIndeterminate(true);

                                           pDialog2STANDBY.setCancelable(false);
                                           pDialog2STANDBY.show();

                                           if(isGooglePlayServicesAvailable()) {
                                               createLocationRequest();

                                               mGoogleApiClient = new GoogleApiClient.Builder(CollectionActivityNew.this)
                                                       .addApi(LocationServices.API)
                                                       .addConnectionCallbacks(CollectionActivityNew.this)
                                                       .addOnConnectionFailedListener(CollectionActivityNew.this)
                                                       .build();
                                               mGoogleApiClient.connect();
                                           }
                                           //startService(new Intent(DynamicActivity.this, AppLocationService.class));
                                           startService(new Intent(CollectionActivityNew.this, AppLocationService.class));
                                           Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
                                           Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
                                           countDownTimer2 = new CoundownClass2(startTime, interval);
                                           countDownTimer2.start();

*/
                                           LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
                                           llaaa.locationRetrievingAndDistanceCalculating(CollectionActivityNew.this,false,20);


                                       }



                                   }
                               });
                       alertDialog.setNegativeButton("No",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog,
                                                       int which) {
                                       dialog.dismiss();
                                   }
                               });

                       // Showing Alert Message
                       alertDialog.show();


                   }
              // }
            }
        });


    }

    public void saveDataToDatabase()
    {
        String paymentModeFirstString="Cash";
        String AmountFirstString="0";
        String ChequeNoFirstString="0";
        String DateFirstString="0";
        String BankFirstString="0";

        String paymentModeSecondString="Cheque/DD";
        String AmountSecondString="0";
        String ChequeNoSecondString="0";
        String DateSecondString="0";
        String BankSecondString="0";

        String paymentModeThirdString="Electronic";
        String AmountThirdString="0";
        String ChequeNoThirdString="0";
        String DateThirdString="0";
        String BankThirdString="0";


        // First row data
        if(!TextUtils.isEmpty(amountEdittextFirst.getText().toString()))
        {
            AmountFirstString =amountEdittextFirst.getText().toString().trim();
        }


        // Second row data
        if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()))
        {
            AmountSecondString =amountEdittextSecond.getText().toString().trim();
        }
        if(!checqueNoEdittextSecond.getText().toString().trim().equals("")){
            ChequeNoSecondString =checqueNoEdittextSecond	.getText().toString().trim();
        }
        if(!dateTextViewSecond.getText().toString().trim().equals("21-mar-2016"))
        {
            DateSecondString =dateTextViewSecond.getText().toString().trim();
        }

        if(dateTextViewSecond.getText().toString().trim().equals("21-mar-2016"))
        {
            DateSecondString ="0";
        }
        if (hashmapBank != null)
        {
            if(hashmapBank.containsKey(BankSpinnerSecond.getText().toString().trim()))
            {
                BankSecondString = hashmapBank.get(BankSpinnerSecond.getText().toString().trim());
            }
            else
            {
                BankSecondString="0";
            }


        }
        else
        {
            BankSecondString = BankNameSecondUpdate;
        }



        // Third row data
        if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()))
        {
            AmountThirdString =amountEdittextThird.getText().toString().trim();
        }

      /*  if(!checqueNoEdittextThird.getText().toString().trim().equals("")){
            ChequeNoThirdString =checqueNoEdittextThird.getText().toString().trim();
        }
        if(!dateTextViewThird.getText().toString().trim().equals("21-mar-2016")){
            DateThirdString =dateTextViewThird.getText().toString().trim();
        }

        if(dateTextViewThird.getText().toString().trim().equals("21-mar-2016")){
            DateThirdString ="0";
        }

        if (hashmapBank != null)
        {
            if(hashmapBank.containsKey(BankSpinnerThird.getText().toString().trim()))
            {
                BankThirdString = hashmapBank.get(BankSpinnerThird.getText().toString().trim());
            }
            else
            {
                BankThirdString="0";
            }


        }
        else
        {
            BankThirdString = BankNameThirdUpdate;
        }*/


        if(!TextUtils.isEmpty(amountEdittextFirst.getText().toString()) || !TextUtils.isEmpty(amountEdittextSecond.getText().toString()) || !TextUtils.isEmpty(amountEdittextThird.getText().toString()))
        {
            CollectionCode=getCollectionCode();
            dbengine.open();
            dbengine.deleteWhereStoreId(storeIDGlobal,strGlobalOrderID,TmpInvoiceCodePDA);

            if(!AmountFirstString.equals("0")){
                dbengine.savetblAllCollectionData(StoreVisitCode,storeIDGlobal, paymentModeFirstString,"1", AmountFirstString,ChequeNoFirstString, "0", BankFirstString,TmpInvoiceCodePDA,CollectionCode);

            }
            if(!AmountSecondString.equals("0")){
                dbengine.savetblAllCollectionData(StoreVisitCode,storeIDGlobal, paymentModeSecondString,"2", AmountSecondString,ChequeNoSecondString, DateSecondString, BankSecondString,TmpInvoiceCodePDA,CollectionCode);

            }
            if(!AmountThirdString.equals("0")){
                dbengine.savetblAllCollectionData(StoreVisitCode,storeIDGlobal, paymentModeThirdString,"4", AmountThirdString,ChequeNoThirdString, DateThirdString, BankThirdString,TmpInvoiceCodePDA,CollectionCode);
            }
           /* dbengine.savetblAllCollectionData(storeIDGlobal, paymentModeFirstString, AmountFirstString,
                    ChequeNoFirstString, DateFirstString, BankFirstString,
                    paymentModeSecondString, AmountSecondString,
                    ChequeNoSecondString, DateSecondString, BankSecondString,
                    paymentModeThirdString, AmountThirdString,
                    ChequeNoThirdString, DateThirdString, BankThirdString,strGlobalOrderID);*/


            dbengine.close();
        }
        else
        {

        }





      /*  Intent ide=new Intent(CollectionActivityNew.this,ProductOrderReview.class);
        ide.putExtra("SN", SN);
        ide.putExtra("storeID", storeID);
        ide.putExtra("imei", imei);
        ide.putExtra("userdate", date);
        ide.putExtra("pickerDate", pickerDate);
        ide.putExtra("flgOrderType", flgOrderType);
        ide.putExtra("OrderPDAID", strGlobalOrderID);


        startActivity(ide);
        finish();*/



    }


   /* private void allMessageAlert(String message) {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialogNoConn.setTitle("Information");
        alertDialogNoConn.setMessage(message);
        alertDialogNoConn.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();

                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }*/


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
                          int dayOfMonth) {
        String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
                "Aug", "Sep", "Oct", "Nov", "Dec" };
        String mon = MONTHS[monthOfYear];
        if(calFirst){
            dateTextViewFirst.setText(dayOfMonth + "-" + mon + "-" + year);
            calFirst=false;
            calSecond=false;

        }

        if(calSecond){
            dateTextViewSecond.setText(dayOfMonth + "-" + mon + "-" + year);
            calFirst=false;
            calSecond=false;
            calThird=false;

        }
        if(calThird){
            dateTextViewThird.setText(dayOfMonth + "-" + mon + "-" + year);
            calFirst=false;
            calSecond=false;
            calThird=false;

        }


    }
    private boolean validateCollectionAmt()
    {
       Double  OverAllAmountCollectedLimit=dbengine.fetch_Store_MaxCollectionAmount(storeID,TmpInvoiceCodePDA);
        OverAllAmountCollectedLimit=Double.parseDouble(new DecimalFormat("##.##").format(OverAllAmountCollectedLimit));
        OverAllAmountCollected=Double.parseDouble(new DecimalFormat("##.##").format(OverAllAmountCollected));

        Double MinCollectionvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
        MinCollectionvalue=Double.parseDouble(new DecimalFormat("##.##").format(MinCollectionvalue));
        if(ll_collectionMandatory.getVisibility()==View.VISIBLE && cb_collection.isChecked()==false && lnCollection.getVisibility()==View.VISIBLE && OverAllAmountCollected==0.0)
        {
            showAlertSingleButtonError("If there is no Collection for today then  please Tick on 'No Collection Today' before click on Submit");

            //Collected amount is less than the minimum collection amount , current invoice cannot be made.Click CANCEL & EXIT to close Invoice and exit current visit, Click on UPDATE PAYMENT to update Collection amount

            return false;
        }
        else
        {
            if(Math.ceil(OverAllAmountCollected) < Math.ceil(MinCollectionvalue))
            {
                // showAlertSingleButtonError("Collection Amount can not be less then "+MinCollectionvalue);
                showAlertSingleAfterCostumValidationForAmountCollection("Collected amount is less than the minimum collection amount , current invoice cannot be made.\nClick CANCEL & EXIT to close Invoice and exit current visit. \nClick on UPDATE PAYMENT to update Collection amount.");
                return false;
            }
            else if(Math.ceil(OverAllAmountCollected)>=Math.ceil(MinCollectionvalue) && Math.ceil(OverAllAmountCollected)<=Math.ceil(OverAllAmountCollectedLimit))
            {
                return true;
            }
            else
            {
                // showAlertSingleButtonError("Collection Amount can not be greater then "+OverAllAmountCollectedLimit);
                // showAlertSingleAfterCostumValidationForAmountCollection("Collection amount exceeds then required and current Invoice can not be made, Click Cancel & Exit to close Invoice and Exit current visit, Click on Update Payment to update Collection amount.");

                showAlertSingleAfterCostumValidationForAmountCollection("Collection amount can not be greater then total outstandings, current invoice cannot be made.\nClick CANCEL & EXIT to close Invoice and exit current visit. \nClick on UPDATE PAYMENT to update Collection amount.");

                //Collected amount is less than the minimum collection amount , current invoice cannot be made.Click CANCEL & EXIT to close Invoice and exit current visit, Click on UPDATE PAYMENT to update Collection amount

                return false;
            }

        }



    }
    private boolean validate()
    {

        // Start Second Row
        if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && checqueNoEdittextSecond.getText().toString().trim().equals(""))
            {
                checqueNoEdittextSecond.clearFocus();
                checqueNoEdittextSecond.requestFocus();
                String estring = "RefNo/chequeNo/TrnNo is Empty";
                ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
                SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                checqueNoEdittextSecond.setError(ssbuilder);

                return false;
            }
        else if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals(""))
           {
               showAlertSingleButtonError("Date is Empty");
                 return false;
           }
          else  if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals(""))
           {
               showAlertSingleButtonError("Please Select Bank.");
                 return false;
           }
           else if(!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && amountEdittextSecond.getText().toString().trim().equals(""))
           {
                 amountEdittextSecond.clearFocus();
                 amountEdittextSecond.requestFocus();

                 String estring = "Amount is Empty";
                 ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
                 SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
                 ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
                 amountEdittextSecond.setError(ssbuilder);

                 return false;
                        }
            else if(!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals(""))
            {
                showAlertSingleButtonError("Date is Empty");
                 return false;
            }
            else  if(!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && BankSpinnerSecond.getText().toString().trim().equals("Select"))
            {
                showAlertSingleButtonError("Please Select Bank.");
                return false;
            }
            // Second row end
        // Start Second Row
        if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && checqueNoEdittextSecond.getText().toString().trim().equals(""))
        {
            checqueNoEdittextSecond.clearFocus();
            checqueNoEdittextSecond.requestFocus();
            String estring = "RefNo/chequeNo/TrnNo is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            checqueNoEdittextSecond.setError(ssbuilder);

            return false;
        }
        else if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Date is Empty");
            return false;
        }
        else  if(!TextUtils.isEmpty(amountEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        else if(!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && amountEdittextSecond.getText().toString().trim().equals(""))
        {
            amountEdittextSecond.clearFocus();
            amountEdittextSecond.requestFocus();

            String estring = "Amount is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            amountEdittextSecond.setError(ssbuilder);

            return false;
        }
        else if(!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && dateTextViewSecond.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Date is Empty");
            return false;
        }
        else  if(!TextUtils.isEmpty(checqueNoEdittextSecond.getText().toString()) && BankSpinnerSecond.getText().toString().trim().equals("Select"))
        {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        // Second row end

        // Third Second Row
       /* else if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()) && checqueNoEdittextThird.getText().toString().trim().equals(""))
        {
            checqueNoEdittextThird.clearFocus();
            checqueNoEdittextThird.requestFocus();
            String estring = "RefNo/chequeNo/TrnNo is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            checqueNoEdittextThird.setError(ssbuilder);

            return false;
        }
        else if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()) && dateTextViewThird.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Date is Empty");
            return false;
        }
        else  if(!TextUtils.isEmpty(amountEdittextThird.getText().toString()) && dateTextViewThird.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }
        else if(!TextUtils.isEmpty(checqueNoEdittextThird.getText().toString()) && amountEdittextThird.getText().toString().trim().equals(""))//
        {
            amountEdittextThird.clearFocus();
            amountEdittextThird.requestFocus();

            String estring = "Amount is Empty";
            ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.parseColor("#ffffff"));
            SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
            ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
            amountEdittextThird.setError(ssbuilder);

            return false;
        }*/
        /*else if(!TextUtils.isEmpty(checqueNoEdittextThird.getText().toString()) && dateTextViewThird.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonError("Date is Empty");
            return false;
        }
        else  if(!TextUtils.isEmpty(checqueNoEdittextThird.getText().toString()) && BankSpinnerThird.getText().toString().trim().equals("Select"))
        {
            showAlertSingleButtonError("Please Select Bank.");
            return false;
        }*/
         else
            {
                return true;
            }


    }

    public void showAlertSingleAfterCostumValidationForAmountCollection(String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialog.setTitle("Validation");
        alertDialog.setIcon(R.drawable.error_ico);
        alertDialog.setCancelable(false);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("Update Payment", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });
        alertDialog.setNegativeButton("Cancel & Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
                /*Intent ide=new Intent(CollectionActivityNew.this,ProductOrderReview.class);
                ide.putExtra("SN", SN);
                ide.putExtra("storeID", storeID);
                ide.putExtra("imei", imei);
                ide.putExtra("userdate", date);
                ide.putExtra("pickerDate", pickerDate);
                ide.putExtra("flgOrderType", flgOrderType);
                ide.putExtra("OrderPDAID", strGlobalOrderID);

                startActivity(ide);
                finish();*/

                if(flgFromPlace==2) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductOrderReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);
                    ide.putExtra("OrderPDAID", strGlobalOrderID);
                    startActivity(ide);
                    finish();
                }
                if(flgFromPlace==1) {
                    Intent ide = new Intent(CollectionActivityNew.this, ProductInvoiceReview.class);
                    ide.putExtra("SN", SN);
                    ide.putExtra("storeID", storeID);
                    ide.putExtra("imei", imei);
                    ide.putExtra("userdate", date);
                    ide.putExtra("pickerDate", pickerDate);
                    ide.putExtra("flgOrderType", flgOrderType);
                    ide.putExtra("OrderPDAID", strGlobalOrderID);
                    startActivity(ide);
                    finish();
                }
            }
        });

        alertDialog.show();

    }

    public void SetDataToLayout(String collectionDataString){
        String paymentMode=collectionDataString.split(Pattern.quote("^"))[0];
        String PaymentModeID=collectionDataString.split(Pattern.quote("^"))[1];
        String Amount=collectionDataString.split(Pattern.quote("^"))[2];
        String RefNoChequeNoTrnNo=collectionDataString.split(Pattern.quote("^"))[3];
        String Date=collectionDataString.split(Pattern.quote("^"))[4];
        String BankID=collectionDataString.split(Pattern.quote("^"))[5];
        //paymentMode 1 means Cash
        if(PaymentModeID.equals("1")){
            amountEdittextFirst.setText(Amount);
        }
        //paymentMode 2 means Check/DD
        if(PaymentModeID.equals("2")){
            amountEdittextSecond.setText(Amount);
            checqueNoEdittextSecond.setText(RefNoChequeNoTrnNo);
            dateTextViewSecond.setText(Date);
            if (BankID.equals("0"))
            {
                BankSpinnerSecond.setText(getResources().getString(R.string.txtSelect));
            } else
            {
                BankSpinnerSecond.setText(linkedHmapBankID.get(BankID));

            }
        }
        //paymentMode 4 means Electronics
        if(PaymentModeID.equals("4")){
            amountEdittextThird.setText(Amount);
           /* checqueNoEdittextThird.setText(RefNoChequeNoTrnNo);
            dateTextViewThird.setText(Date);
            if (BankID.equals("0"))
            {
                BankSpinnerThird.setText(getResources().getString(R.string.txtSelect));
            } else
            {
                BankSpinnerThird.setText(linkedHmapBankID.get(BankID));

            }*/
            checqueNoEdittextThird.setText("");
            dateTextViewThird.setText("");
            BankID="0";
            if (BankID.equals("0"))
            {
                BankSpinnerThird.setText(getResources().getString(R.string.txtSelect));
            } else
            {
                BankSpinnerThird.setText(linkedHmapBankID.get(BankID));

            }
        }

    }

    public String genCollectionCode()
    {
        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String VisitStartTS = df.format(dateobj);
        String cxz;
        cxz = UUID.randomUUID().toString();


        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        String IMEIid =  CommonInfo.imei.substring(9);
        cxz = "Collection" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;

    }


    public String  getCollectionCode()
    {
        int StoreCurrentOutsStat=dbengine.fnGetCollectionOutSstat(storeID);
        if(StoreCurrentOutsStat!=1)
        {

            CollectionCode=genCollectionCode();

        }
        else
        {
            CollectionCode=dbengine.fnGetStoreCollectionCode(storeID);
        }
        return CollectionCode;
    }

    @Override
    public void onLocationChanged(Location args0) {
        // TODO Auto-generated method stub
        mCurrentLocation = args0;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();

    }

    @Override
    public void testFunctionOne(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state) {

        if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy)))
        {

            fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
            UpdateLocationAndProductAllData();
        }
        else
        {countSubmitClicked++;
            if(countSubmitClicked==1)
            {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);

                // Setting Dialog Title
                alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
                alertDialog.setIcon(R.drawable.error_info_ico);
                alertDialog.setCancelable(false);
                // Setting Dialog Message
                alertDialog.setMessage(CollectionActivityNew.this.getResources().getString(R.string.AlertSameLoc));

                // On pressing Settings button
                alertDialog.setPositiveButton(CollectionActivityNew.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        countSubmitClicked++;
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                });

                // Showing Alert Message
                alertDialog.show();



            }
            else
            {
                UpdateLocationAndProductAllData();
            }


        }

    }


    private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


        ProgressDialog pDialogGetStores;
        public FullSyncDataNow(CollectionActivityNew activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(CollectionActivityNew.this.getResources().getString(R.string.SubmittingOrderDetails));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();


        }

        @Override

        protected Void doInBackground(Void... params) {

            int Outstat=1;
           flgTransferStatus=1;
            dbengine.open();
            dbengine.fnUpdateflgTransferStatusInInvoiceHeader(storeID,StoreVisitCode,TmpInvoiceCodePDA,flgTransferStatus);
            dbengine.close();
            //InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
            //TransactionTableDataDeleteAndSaving(Outstat);
            Double cntInvoceValue=dbengine.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
            cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

            Outstat=3;


            if(Outstat==3) {
                if (cntInvoceValue > 0.0) {
                    if(strFinalAllotedInvoiceIds.equals("NA"))
                    {

                        dbengine.fnTransferDataFromTempToPermanent(storeID,StoreVisitCode,TmpInvoiceCodePDA);

                        int chkflgTransferStatus=dbengine.fnCheckflgTransferStatus(storeID,StoreVisitCode,TmpInvoiceCodePDA);
                        if(chkflgTransferStatus==2)
                        {
                            dbengine.deleteOldStoreInvoice(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
                            dbengine.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
                            dbengine.UpdateStoreVisitMStrTable(storeID,3,StoreVisitCode);



                        }
                        else
                        {
                            dbengine.deleteMasterTblFromParmanentInvoiceTables(storeID,TmpInvoiceCodePDA);
                        }

                    }

                }
            }

            dbengine.UpdateStoreVisitWiseTables(storeID, 3,StoreVisitCode,TmpInvoiceCodePDA);
            long  syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            String StampEndsTime = df.format(dateobj);


            dbengine.open();
            dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
            dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"3",strGlobalOrderID,TmpInvoiceCodePDA);
            dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"3",strGlobalOrderID,TmpInvoiceCodePDA);
            dbengine.UpdateStoreFlag(storeID.trim(), 3);
            dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 3,strGlobalOrderID,TmpInvoiceCodePDA);




            //dbengine.UpdateStoreReturnphotoFlag(storeID.trim(), 5);

            dbengine.close();

            Double outstandingvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
            outstandingvalue=Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));
            dbengine.updateOutstandingOfStore(storeID,0.0);

            Double CollectionAmtAgainstStore=dbengine.fnTotCollectionAmtAgainstStore(storeID.trim(),TmpInvoiceCodePDA,StoreVisitCode);

            dbengine.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0,StoreVisitCode);
            if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,TmpInvoiceCodePDA)==0)
            {
                String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
                if(!strDefaultPaymentStageForStore.equals(""))
                {
                    dbengine.open();
                    dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"3",TmpInvoiceCodePDA);
                    dbengine.close();
                }
            }

            dbengine.open();
            String presentRoute=dbengine.GetActiveRouteID();
            dbengine.close();


			/*long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);*/
            SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

            newfullFileName=imei+"."+presentRoute+"."+df1.format(dateobj);




            try {


                File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                if (!OrderXMLFolder.exists())
                {
                    OrderXMLFolder.mkdirs();

                }
                String routeID=dbengine.GetActiveRouteIDSunil();

                DA.open();
                DA.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);
                DA.close();

                dbengine.savetbl_XMLfiles(newfullFileName, "3","1");

                dbengine.UpdateXMLCreatedFilesTablesFlag(5);
            } catch (Exception e) {

                e.printStackTrace();
                if(pDialogGetStores.isShowing())
                {
                    pDialogGetStores.dismiss();
                }
            }
            return null;
        }
        @Override
        protected void onCancelled() {

        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }
            try
            {
                StoreSelection.flgChangeRouteOrDayEnd=0;
                DayStartActivity.flgDaySartWorking=0;
                Intent syncIntent = new Intent(CollectionActivityNew.this, SyncMaster.class);
                //syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + newfullFileName + ".xml");
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", "Regular");
                startActivity(syncIntent);
                finish();
            } catch (Exception e) {

                e.printStackTrace();
            }

        }
    }


    public class CoundownClass2 extends CountDownTimer {

        public CoundownClass2(long startTime, long interval) {
            super(startTime, interval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            String GpsLat="0";
            String GpsLong="0";
            String GpsAccuracy="0";
            String GpsAddress="0";
            if(isGPSEnabled)
            {

                Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
                if(nwLocation!=null){
                    double lattitude=nwLocation.getLatitude();
                    double longitude=nwLocation.getLongitude();
                    double accuracy= nwLocation.getAccuracy();
                    GpsLat=""+lattitude;
                    GpsLong=""+longitude;
                    GpsAccuracy=""+accuracy;

                    GPSLocationLatitude=""+lattitude;
                    GPSLocationLongitude=""+longitude;
                    GPSLocationProvider="GPS";
                    GPSLocationAccuracy=""+accuracy;
                    System.out.println("LOCATION(GPS)  LATTITUDE: " +lattitude + "LONGITUDE:" + longitude+ "accuracy:" + accuracy);
                    //text2.setText(" LOCATION(GPS) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude+ "\naccuracy:" + accuracy);
                    //Toast.makeText(getApplicationContext(), " LOCATION(NW) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude+ "\naccuracy:" + accuracy, Toast.LENGTH_LONG).show();
                }
            }

            Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
            String NetwLat="0";
            String NetwLong="0";
            String NetwAccuracy="0";
            String NetwAddress="0";
            if(gpsLocation!=null){
                double lattitude1=gpsLocation.getLatitude();
                double longitude1=gpsLocation.getLongitude();
                double accuracy1= gpsLocation.getAccuracy();
                NetwLat=""+lattitude1;
                NetwLong=""+longitude1;
                NetwAccuracy=""+accuracy1;

                NetworkLocationLatitude=""+lattitude1;
                NetworkLocationLongitude=""+longitude1;
                NetworkLocationProvider="Network";
                NetworkLocationAccuracy=""+accuracy1;
                System.out.println("LOCATION(N/W)  LATTITUDE: " +lattitude1 + "LONGITUDE:" + longitude1+ "accuracy:" + accuracy1);
                // Toast.makeText(this, " LOCATION(NW) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude, Toast.LENGTH_LONG).show();
                //text1.setText(" LOCATION(N/W) \n LATTITUDE: " +lattitude1 + "\nLONGITUDE:" + longitude1+ "\naccuracy:" + accuracy1);

            }
					 /* TextView accurcy=(TextView) findViewById(R.id.Acuracy);
					  accurcy.setText("GPS:"+GPSLocationAccuracy+"\n"+"NETWORK"+NetworkLocationAccuracy+"\n"+"FUSED"+fusedData);*/

            System.out.println("LOCATION Fused"+fusedData);
            String FusedLat="0";
            String FusedLong="0";
            String FusedAccuracy="0";
            String FusedAddress="0";

            if(!FusedLocationProvider.equals(""))
            {
                fnAccurateProvider="Fused";
                fnLati=FusedLocationLatitude;
                fnLongi=FusedLocationLongitude;
                fnAccuracy= Double.parseDouble(FusedLocationAccuracy);

                FusedLat=FusedLocationLatitude;
                FusedLong=FusedLocationLongitude;
                FusedAccuracy=FusedLocationAccuracy;
            }




            appLocationService.KillServiceLoc(appLocationService,locationManager);
            try {
                if(mGoogleApiClient!=null && mGoogleApiClient.isConnected())
                {
                    stopLocationUpdates();
                    mGoogleApiClient.disconnect();
                }
            }
            catch (Exception e){

            }




            fnAccurateProvider="";
            fnLati="0";
            fnLongi="0";
            fnAccuracy=0.0;

            if(!FusedLocationProvider.equals(""))
            {
                fnAccurateProvider="Fused";
                fnLati=FusedLocationLatitude;
                fnLongi=FusedLocationLongitude;
                fnAccuracy= Double.parseDouble(FusedLocationAccuracy);
            }

            if(!fnAccurateProvider.equals(""))
            {
                if(!GPSLocationProvider.equals(""))
                {
                    if(Double.parseDouble(GPSLocationAccuracy)<=fnAccuracy)
                    {
                        fnAccurateProvider="Gps";
                        fnLati=GPSLocationLatitude;
                        fnLongi=GPSLocationLongitude;
                        fnAccuracy= Double.parseDouble(GPSLocationAccuracy);
                    }
                }
            }
            else
            {
                if(!GPSLocationProvider.equals(""))
                {
                    fnAccurateProvider="Gps";
                    fnLati=GPSLocationLatitude;
                    fnLongi=GPSLocationLongitude;
                    fnAccuracy= Double.parseDouble(GPSLocationAccuracy);
                }
            }

            if(!fnAccurateProvider.equals(""))
            {
                if(!NetworkLocationProvider.equals(""))
                {
                    if(Double.parseDouble(NetworkLocationAccuracy)<=fnAccuracy)
                    {
                        fnAccurateProvider="Network";
                        fnLati=NetworkLocationLatitude;
                        fnLongi=NetworkLocationLongitude;
                        fnAccuracy= Double.parseDouble(NetworkLocationAccuracy);
                    }
                }
            }
            else
            {
                if(!NetworkLocationProvider.equals(""))
                {
                    fnAccurateProvider="Network";
                    fnLati=NetworkLocationLatitude;
                    fnLongi=NetworkLocationLongitude;
                    fnAccuracy= Double.parseDouble(NetworkLocationAccuracy);
                }
            }
            // fnAccurateProvider="";
            if(fnAccurateProvider.equals(""))
            {
                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }
                //alert ... try again nothing found // return back

                // Toast.makeText(getApplicationContext(), "Please try again, No Fused,GPS or Network found.", Toast.LENGTH_LONG).show();

                showAlertForEveryOne(CollectionActivityNew.this.getResources().getString(R.string.AlertTryAgain));
            }
            else
            {


                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }
                if(!GpsLat.equals("0") )
                {
                    fnCreateLastKnownGPSLoction(GpsLat,GpsLong,GpsAccuracy);
                }

                if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy)))
                {

                    fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
                    UpdateLocationAndProductAllData();
                }
                else
                {countSubmitClicked++;
                    if(countSubmitClicked==1)
                    {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CollectionActivityNew.this);

                        // Setting Dialog Title
                        alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
                        alertDialog.setIcon(R.drawable.error_info_ico);
                        alertDialog.setCancelable(false);
                        // Setting Dialog Message
                        alertDialog.setMessage(CollectionActivityNew.this.getResources().getString(R.string.AlertSameLoc));

                        // On pressing Settings button
                        alertDialog.setPositiveButton(CollectionActivityNew.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                countSubmitClicked++;
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();



                    }
                    else
                    {
                        UpdateLocationAndProductAllData();
                    }


                }

            }

        }

    }
    public void showAlertForEveryOne(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(CollectionActivityNew.this);
        alertDialogNoConn.setTitle(getText(R.string.genTermNoDataConnection));
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(CollectionActivityNew.this.getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                FusedLocationLatitude="0";
                FusedLocationLongitude="0";
                FusedLocationProvider="0";
                FusedLocationAccuracy="0";

                GPSLocationLatitude="0";
                GPSLocationLongitude="0";
                GPSLocationProvider="0";
                GPSLocationAccuracy="0";

                NetworkLocationLatitude="0";
                NetworkLocationLongitude="0";
                NetworkLocationProvider="0";
                NetworkLocationAccuracy="0";


                String GpsLat="0";
                String GpsLong="0";
                String GpsAccuracy="0";
                String GpsAddress="0";
                String NetwLat="0";
                String  NetwLong="0";
                String NetwAccuracy="0";
                String NetwAddress="0";
                String  FusedLat="0";
                String FusedLong="0";
                String FusedAccuracy="0";
                String FusedAddress="0";
                checkHighAccuracyLocationMode(CollectionActivityNew.this);
                dbengine.open();
                dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview,StoreVisitCode,VisitTimeInSideStore);


                if(butClickForGPS==3)
                {
                    butClickForGPS=0;
                    try
                    {
                        FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                        task.execute();
                    }
                    catch (Exception e) {
                        // TODO Autouuid-generated catch block
                        e.printStackTrace();
                        //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
                    }

                }

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOffOrderReview=0;
        flgGPSOnOffOrderReview=0;
        flgNetworkOnOffOrderReview=0;
        flgFusedOnOffOrderReview=0;
        flgInternetOnOffWhileLocationTrackingOrderReview=0;

        if(isGooglePlayServicesAvailable())
        {
            flgFusedOnOffOrderReview=1;
        }
        if(isOnline())
        {
            flgInternetOnOffWhileLocationTrackingOrderReview=1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY){
                    flgLocationServicesOnOffOrderReview=1;
                    flgGPSOnOffOrderReview=1;
                    flgNetworkOnOffOrderReview=1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING){
                    flgLocationServicesOnOffOrderReview=1;
                    flgGPSOnOffOrderReview=0;
                    flgNetworkOnOffOrderReview=1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY){
                    flgLocationServicesOnOffOrderReview=1;
                    flgGPSOnOffOrderReview=1;
                    flgNetworkOnOffOrderReview=0;
                    //flgFusedOnOff=0;
                }
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            //Lower than API 19
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);


            if (TextUtils.isEmpty(locationProviders)) {
                locationMode = Settings.Secure.LOCATION_MODE_OFF;

                flgLocationServicesOnOffOrderReview = 0;
                flgGPSOnOffOrderReview = 0;
                flgNetworkOnOffOrderReview = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOffOrderReview = 1;
                flgGPSOnOffOrderReview = 1;
                flgNetworkOnOffOrderReview = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 1;
                    flgNetworkOnOffOrderReview = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOffOrderReview = 1;
                    flgGPSOnOffOrderReview = 0;
                    flgNetworkOnOffOrderReview = 1;
                    //flgFusedOnOff = 0;
                }
            }
        }

    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    public void onConnectionFailed(ConnectionResult arg0)
    {
        // TODO Auto-generated method stub
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }

    @Override
    public void onConnected(Bundle arg0)
    {
        // TODO Auto-generated method stub
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);

        startLocationUpdates();
    }

	/*	public void hideSoftKeyboard(View view){
			  InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}

		public void showSoftKeyboard(View view){
		    if(view.requestFocus()){
		        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
		    }

		}*/

    protected void startLocationUpdates()
    {
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int arg0)
    {
        // TODO Auto-generated method stub
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);

    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    private void updateUI() {
        Location loc =mCurrentLocation;
        if (null != mCurrentLocation) {
            String lat = String.valueOf(mCurrentLocation.getLatitude());
            String lng = String.valueOf(mCurrentLocation.getLongitude());

            FusedLocationLatitude=lat;
            FusedLocationLongitude=lng;
            FusedLocationProvider=mCurrentLocation.getProvider();
            FusedLocationAccuracy=""+mCurrentLocation.getAccuracy();
            fusedData="At Time: " + mLastUpdateTime  +
                    "Latitude: " + lat  +
                    "Longitude: " + lng  +
                    "Accuracy: " + mCurrentLocation.getAccuracy() +
                    "Provider: " + mCurrentLocation.getProvider();

        } else {

        }
    }

    public void fnCreateLastKnownGPSLoction(String chekLastGPSLat,String chekLastGPSLong,String chekLastGpsAccuracy)
    {

        try {

            JSONArray jArray=new JSONArray();
            JSONObject jsonObjMain=new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put( "chekLastGPSLat",chekLastGPSLat);
            jOnew.put( "chekLastGPSLong",chekLastGPSLong);
            jOnew.put( "chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.AppLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="GPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+CommonInfo.AppLatLngJsonFile+"/"+txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("GPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{

        }
    }
    public boolean checkLastFinalLoctionIsRepeated(String currentLat,String currentLong,String currentAccuracy){
        boolean repeatedLoction=false;

        try {

            String chekLastGPSLat="0";
            String chekLastGPSLong="0";
            String chekLastGpsAccuracy="0";
            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;

            // If file does not exists, then create it
            if (file.exists()) {
                StringBuffer buffer=new StringBuffer();
                String myjson_stampiGPSLastLocation="";
                StringBuffer sb = new StringBuffer();
                BufferedReader br = null;

                try {
                    br = new BufferedReader(new FileReader(file));

                    String temp;
                    while ((temp = br.readLine()) != null)
                        sb.append(temp);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        br.close(); // stop reading
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                myjson_stampiGPSLastLocation=sb.toString();

                JSONObject jsonObjGPSLast = new JSONObject(myjson_stampiGPSLastLocation);
                JSONArray jsonObjGPSLastInneralues = jsonObjGPSLast.getJSONArray("GPSLastLocationDetils");

                String StringjsonGPSLastnew = jsonObjGPSLastInneralues.getString(0);
                JSONObject jsonObjGPSLastnewwewe = new JSONObject(StringjsonGPSLastnew);

                chekLastGPSLat=jsonObjGPSLastnewwewe.getString("chekLastGPSLat");
                chekLastGPSLong=jsonObjGPSLastnewwewe.getString("chekLastGPSLong");
                chekLastGpsAccuracy=jsonObjGPSLastnewwewe.getString("chekLastGpsAccuracy");

                if(currentLat!=null )
                {
                    if(currentLat.equals(chekLastGPSLat) && currentLong.equals(chekLastGPSLong) && currentAccuracy.equals(chekLastGpsAccuracy))
                    {
                        repeatedLoction=true;
                    }
                }
            }
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repeatedLoction;

    }
    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat,String chekLastGPSLong,String chekLastGpsAccuracy)
    {

        try {

            JSONArray jArray=new JSONArray();
            JSONObject jsonObjMain=new JSONObject();


            JSONObject jOnew = new JSONObject();
            jOnew.put( "chekLastGPSLat",chekLastGPSLat);
            jOnew.put( "chekLastGPSLong",chekLastGPSLong);
            jOnew.put( "chekLastGpsAccuracy", chekLastGpsAccuracy);


            jArray.put(jOnew);
            jsonObjMain.put("GPSLastLocationDetils", jArray);

            File jsonTxtFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.FinalLatLngJsonFile);
            if (!jsonTxtFolder.exists())
            {
                jsonTxtFolder.mkdirs();

            }
            String txtFileNamenew="FinalGPSLastLocation.txt";
            File file = new File(jsonTxtFolder,txtFileNamenew);
            String fpath = Environment.getExternalStorageDirectory()+"/"+CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;


            // If file does not exists, then create it
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


            FileWriter fw;
            try {
                fw = new FileWriter(file.getAbsoluteFile());

                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(jsonObjMain.toString());

                bw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
				 /*  file=contextcopy.getFilesDir();
				//fileOutputStream=contextcopy.openFileOutput("FinalGPSLastLocation.txt", Context.MODE_PRIVATE);
				fileOutputStream.write(jsonObjMain.toString().getBytes());
				fileOutputStream.close();*/
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{

        }
    }
    public void UpdateLocationAndProductAllData()
    {
        checkHighAccuracyLocationMode(CollectionActivityNew.this);
        dbengine.open();
        dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview,StoreVisitCode,VisitTimeInSideStore);


        dbengine.close();

        if(butClickForGPS==3)
        {
            butClickForGPS=0;
            try
            {
                FullSyncDataNow task = new FullSyncDataNow(CollectionActivityNew.this);
                task.execute();
            }
            catch (Exception e) {
                // TODO Autouuid-generated catch block
                e.printStackTrace();
                //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
            }
        }

    }

    public void showSettingsAlert()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(CollectionActivityNew.this.getResources().getString(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialog.setPositiveButton(CollectionActivityNew.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
