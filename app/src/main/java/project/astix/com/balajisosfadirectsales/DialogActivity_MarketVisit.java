package project.astix.com.balajisosfadirectsales;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.astix.Common.CommonInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;


public class DialogActivity_MarketVisit extends BaseActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener
{
    int flgDrctslsIndrctSls=0;
    int flgOwnRouteClick=0;
    String whereTo = "11";
    ProgressDialog pDialog2;
    DatabaseAssistant DASFA = new DatabaseAssistant(this);
    int countSubmitClicked=0;
    static int flgJointWorking = 0;
    int whatTask = 0;
    public long syncTIMESTAMP;
    public static int flgLocationServicesOnOff=0;
    public static int flgGPSOnOff=0;
    public static int flgNetworkOnOff=0;
    public static int flgFusedOnOff=0;
    public static int flgInternetOnOffWhileLocationTracking=0;
    public static int flgRestart=0;
    public static int flgStoreOrder=0;
    public String serviceExceptionCode="";
    private int chkFlgForErrorToCloseApp=0;
    private ProgressDialog pDialogGetStores;
    private boolean serviceException=false;

    private SimpleDateFormat sdf;
    private String passDate;



    private Date currDate;
    private SimpleDateFormat currDateFormat;
    private String currSysDate;


    private String PageFrom="0";
    private String rID;
    private String imei;
    private final LinkedHashMap<String, String> hmapOutletListForNearUpdated= new LinkedHashMap<>();
    private LinkedHashMap<String, String> hmapOutletListForNear= new LinkedHashMap<>();
    private String fusedData;
    private String fnAccurateProvider="";
    private String fnLati="0";
    private String fnLongi="0";
    private Double fnAccuracy=0.0;
    private String fDate;

    private String FusedLocationLatitudeWithFirstAttempt="0";
    private String FusedLocationLongitudeWithFirstAttempt="0";
    private String FusedLocationAccuracyWithFirstAttempt="0";
    private String AllProvidersLocation="";
    private final String FusedLocationLatitude="0";
    private final String FusedLocationLongitude="0";
    private final String FusedLocationProvider="";
    private final String FusedLocationAccuracy="0";

    private String GPSLocationLatitude="0";
    private String GPSLocationLongitude="0";
    private String GPSLocationProvider="";
    private String GPSLocationAccuracy="0";

    private String NetworkLocationLatitude="0";
    private String NetworkLocationLongitude="0";
    private String NetworkLocationProvider="";
    private String NetworkLocationAccuracy="0";
    private boolean isGPSEnabled = false;
    private LocationRequest mLocationRequest;
    private Location location;
    private CoundownClass countDownTimer;

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private SharedPreferences sharedPref;
    private android.app.AlertDialog GPSONOFFAlert=null;
    private AppLocationService appLocationService;


    private final PRJDatabase dbengine = new PRJDatabase(this);


    private String SelectedDSRValue="";
    private LocationManager locationManager;

    private PowerManager pm;

    private ProgressDialog pDialog2STANDBY;





    public static String address,pincode,city,state,latitudeToSave,longitudeToSave,accuracyToSave;

    private PowerManager.WakeLock wl;

    private LinkedHashMap<String, String> hmapdsrIdAndDescr_details= new LinkedHashMap<>();
    private String[] drsNames;

    @Override
    protected void onStop() {
        super.onStop();
        if(GPSONOFFAlert!=null && GPSONOFFAlert.isShowing())
        {
            GPSONOFFAlert.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);

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

      /*  TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
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
*/
        Date date1=new Date();
        sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        passDate = sdf.format(date1);
        fDate = passDate.trim();

        Intent getStorei = getIntent();
        if(getStorei !=null)
        {
            PageFrom = getStorei.getStringExtra("PageFrom").trim();
            imei=getStorei.getStringExtra("imei").trim();

        }

        currDate = new Date();
        currDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

        currSysDate = currDateFormat.format(currDate);

        shardPrefForSalesman(0,0);

        flgDataScopeSharedPref(0);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = (int) (metrics.widthPixels * 0.80);

        setContentView(R.layout.market_visit_alert);

        getWindow().setLayout(screenWidth, WindowManager.LayoutParams.WRAP_CONTENT);
        setFinishOnTouchOutside(false);


        final RadioButton rb_myVisit= (RadioButton) findViewById(R.id.rb_myVisit);
        final RadioButton rb_dsrVisit= (RadioButton) findViewById(R.id.rb_dsrVisit);
        final RadioButton rb_jointWorking= (RadioButton) findViewById(R.id.rb_jointWorking);
        if(PageFrom.equals("1"))
        {
            rb_jointWorking.setVisibility(View.GONE);
        }
        else
        {
            rb_jointWorking.setVisibility(View.VISIBLE);
        }


        final Spinner spinner_dsrVisit= (Spinner) findViewById(R.id.spinner_dsrVisit);
        final Spinner spinner_jointWorking= (Spinner) findViewById(R.id.spinner_jointWorking);

        Button btn_proceed= (Button) findViewById(R.id.btn_proceed);
        Button btn_cancel= (Button) findViewById(R.id.btn_cancel);

        try
        {
            getDSRDetail();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(rb_myVisit.isChecked())
                {
                    /*String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    CommonInfo.PersonNodeID=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    CommonInfo.PersonNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                    String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    int tempSalesmanNodeId=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    int tempSalesmanNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                    shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                    flgDataScopeSharedPref(1);

                    shardPrefForCoverageArea(0,0);
                    flgDSRSOSharedPref(1);*/

                   /* Intent i=new Intent(DialogActivity_MarketVisit.this,LauncherActivity.class);
                    startActivity(i);
                    finish();*/
                    int routeExist=dbengine.fnGetRouteExistOrNot(0,0);
                    if(routeExist==0)
                    {
                        showAlertForEveryOne("There are no Routes Available for You.");
                        return;
                    }

                    dbengine.open();

                    rID= dbengine.GetActiveRouteIDCrntDSR(0,0);
                    dbengine.close();
                    CommonInfo.CoverageAreaNodeID=0;
                    CommonInfo.CoverageAreaNodeType=0;
                    CommonInfo.FlgDSRSO=1;

                    shardPrefForCoverageArea(0,0);
                    flgDSRSOSharedPref(1);

                    if(dbengine.isDataAlreadyExist(0,0))
                    {
                        Date date1 = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        fDate = sdf.format(date1).trim();

                        // Date date=new Date();
                        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                        String fDateNew = sdf1.format(date1);
                        //fDate = passDate.trim().toString();


                        Intent storeIntent = new Intent(DialogActivity_MarketVisit.this, StoreSelection.class);
                        storeIntent.putExtra("imei", imei);
                        storeIntent.putExtra("userDate", fDate);
                        storeIntent.putExtra("pickerDate", fDateNew);
                        storeIntent.putExtra("rID", rID);
                        startActivity(storeIntent);
                        finish();
                    }
                    else
                    {
                        flgOwnRouteClick=1;
                        marketVisitGetRoutesClick();
                    }

                }
                else if(rb_dsrVisit.isChecked())
                {
                    if(!SelectedDSRValue.equals("") && !SelectedDSRValue.equals("Select Salesman") && !SelectedDSRValue.equals("No DSR") )
                    {
                        String DSRNodeIdAndNodeType= dbengine.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        int tempCoverageAreaNodeID=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempCoverageAreaNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                        CommonInfo.CoverageAreaNodeID=tempCoverageAreaNodeID;
                        CommonInfo.CoverageAreaNodeType=tempCoverageAreaNodeType;
                        CommonInfo.FlgDSRSO=2;

                        dbengine.open();

                        rID= dbengine.GetActiveRouteIDCrntDSR(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                        dbengine.updateActiveRoute(rID, 1);
                        dbengine.close();
                        shardPrefForCoverageArea(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                        flgDSRSOSharedPref(2);

                        String DSRPersonNodeIdAndNodeType= dbengine.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        int tempSalesmanNodeId=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempSalesmanNodeType=Integer.parseInt(DSRPersonNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                        flgDataScopeSharedPref(2);


                        if(dbengine.isDataAlreadyExist(tempCoverageAreaNodeID,tempCoverageAreaNodeType))
                        {
                            dbengine.open();
                            rID= dbengine.GetActiveRouteIDCrntDSR(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                            dbengine.close();

                            Date date1 = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            fDate = sdf.format(date1).trim();

                            // Date date=new Date();
                            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
                            String fDateNew = sdf1.format(date1);
                            //fDate = passDate.trim().toString();


                            Intent storeIntent = new Intent(DialogActivity_MarketVisit.this, StoreSelection.class);
                            storeIntent.putExtra("imei", imei);
                            storeIntent.putExtra("userDate", fDate);
                            storeIntent.putExtra("pickerDate", fDateNew);
                            storeIntent.putExtra("rID", rID);
                            startActivity(storeIntent);
                            finish();


                        }
                        else
                        {


                            String dsrRouteID=hmapdsrIdAndDescr_details.get(SelectedDSRValue);
                            flgOwnRouteClick=1;
                            marketVisitGetRoutesClick();

                            /*Intent i = new Intent(DialogActivity_MarketVisit.this, StoreSelection.class);

                            i.putExtra("imei", imei);
                            i.putExtra("userDate", currSysDate);
                            i.putExtra("pickerDate", fDate);
                           // i.putExtra("rID", rID);

                          //  i.putExtra("RouteID",dsrRouteID);
                            startActivity(i);
                            finish();*/
                        }

                    }
                    else
                    {
                       showAlertSingleButtonError(getResources().getString(R.string.selectDSRProceeds));
                    }
                }
                else if(rb_jointWorking.isChecked())
                {
                   if(!SelectedDSRValue.equals("") && !SelectedDSRValue.equals("Select Salesman") && !SelectedDSRValue.equals("No DSM") )
                    {
                        String DSRNodeIdAndNodeType= dbengine.fnGetDSRNodeIdAndNodeType(SelectedDSRValue);
                        int tempCoverageAreaNodeID=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempCoverageAreaNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForCoverageArea(tempCoverageAreaNodeID,tempCoverageAreaNodeType);
                        flgDSRSOSharedPref(4);

                        // Find GPS
                        //surbhi loc code
                        boolean isGPSokCheckInResume = false;
                        boolean isNWokCheckInResume=false;
                        isGPSokCheckInResume = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        isNWokCheckInResume = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                        if(!isGPSokCheckInResume && !isNWokCheckInResume)
                        {
                            try
                            {
                                showSettingsAlert();
                            }
                            catch(Exception e)
                            {}
                            isGPSokCheckInResume = false;
                            isNWokCheckInResume=false;
                        }
                        else
                        {
                            locationRetrievingAndDistanceCalculating();
                        }
                    }
                    else
                    {
                        showAlertForEveryOne(getResources().getString(R.string.selectDSMProceeds));
                    }
                }
                else
                {
                    showAlertSingleButtonError(getResources().getString(R.string.selectOptionProceeds));
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
                    spinner_jointWorking.setVisibility(View.GONE);
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
                    spinner_jointWorking.setVisibility(View.GONE);

                    ArrayAdapter adapterCategory=new ArrayAdapter(DialogActivity_MarketVisit.this, android.R.layout.simple_spinner_item,drsNames);
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
                    spinner_dsrVisit.setVisibility(View.GONE);

                    ArrayAdapter adapterCategory=new ArrayAdapter(DialogActivity_MarketVisit.this, android.R.layout.simple_spinner_item,drsNames);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_jointWorking.setAdapter(adapterCategory);
                    spinner_jointWorking.setVisibility(View.VISIBLE);

                    spinner_jointWorking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                        {
                            SelectedDSRValue = spinner_jointWorking.getSelectedItem().toString();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0)
                        {
                        }

                    });
                }
            }
        });

    }

    private void showSettingsAlert(){
        android.app.AlertDialog.Builder alertDialogGps = new android.app.AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialogGps.setTitle(getResources().getString(R.string.genTermInformation));
        alertDialogGps.setIcon(R.drawable.error_info_ico);
        alertDialogGps.setCancelable(false);
        // Setting Dialog Message
        alertDialogGps.setMessage(getResources().getString(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialogGps.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        GPSONOFFAlert=alertDialogGps.create();
        GPSONOFFAlert.show();
    }

    private void getDSRDetail() throws IOException
    {
        int check=dbengine.countDataIntblNoVisitReasonMaster();

        //hmapdsrIdAndDescr_details=dbengine.fetch_MarktVisitDSM_List();
        hmapdsrIdAndDescr_details=dbengine.fetch_DSRCoverage_List();

        int index=0;
        if(hmapdsrIdAndDescr_details!=null)
        {
            drsNames=new String[hmapdsrIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<>(hmapdsrIdAndDescr_details);
            Set set2 = map.entrySet();
            for (Object aSet2 : set2) {
                Map.Entry me2 = (Map.Entry) aSet2;
                drsNames[index] = me2.getKey().toString();
                index = index + 1;
            }
        }
    }

    private void showAlertForEveryOne(String msg)
    {
        //AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DialogActivity_MarketVisit.this);

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
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }



    private void shardPrefForCoverageArea(int coverageAreaNodeID, int coverageAreaNodeType)
    {


        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("CoverageAreaNodeID", coverageAreaNodeID);
        editor.putInt("CoverageAreaNodeType", coverageAreaNodeType);


        editor.commit();

    }
    private void shardPrefForSalesman(int salesmanNodeId, int salesmanNodeType)
    {




        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("SalesmanNodeId", salesmanNodeId);
        editor.putInt("SalesmanNodeType", salesmanNodeType);

        editor.commit();

    }


    private void flgDataScopeSharedPref(int _flgDataScope)
    {
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("flgDataScope", _flgDataScope);
        editor.commit();


    }
    private void flgDSRSOSharedPref(int _flgDSRSO)
    {
        SharedPreferences.Editor editor = sharedPref.edit();


        editor.putInt("flgDSRSO", _flgDSRSO);
        editor.commit();


    }

    private void locationRetrievingAndDistanceCalculating()
    {
        appLocationService = new AppLocationService();

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");
        wl.acquire();


        pDialog2STANDBY = ProgressDialog.show(DialogActivity_MarketVisit.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.rtrvng_loc), true);
        pDialog2STANDBY.setIndeterminate(true);

        pDialog2STANDBY.setCancelable(false);
        pDialog2STANDBY.show();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            mGoogleApiClient = new GoogleApiClient.Builder(DialogActivity_MarketVisit.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(DialogActivity_MarketVisit.this)
                    .addOnConnectionFailedListener(DialogActivity_MarketVisit.this)
                    .build();
            mGoogleApiClient.connect();
        }
        //startService(new Intent(DynamicActivity.this, AppLocationService.class));


        startService(new Intent(DialogActivity_MarketVisit.this, AppLocationService.class));
        Location nwLocation = appLocationService.getLocation(locationManager, LocationManager.GPS_PROVIDER, location);
        Location gpsLocation = appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER, location);
        long interval = 200;
        long startTime = 15000;
        countDownTimer = new CoundownClass(startTime, interval);
        countDownTimer.start();

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

    @Override
    public void onLocationChanged(Location location)
    {
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        //updateUI();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
        startLocationUpdates();
    }
    @Override
    public void onConnectionSuspended(int i) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }
    private void startLocationUpdates()
    {
        try {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, this);
        }
        catch(SecurityException e)
        {

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public class CoundownClass extends CountDownTimer
    {

        public CoundownClass(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {


            AllProvidersLocation="";
            /*Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
            Date currentLocalTime = cal.getTime();
            DateFormat date = new SimpleDateFormat("HH:mm a");
                // you can get seconds by adding  "...:ss" to it
            date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));

            String localTime = date.format(currentLocalTime);*/

            DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
            Calendar cal = Calendar.getInstance();
            String DateTime = dateFormat.format(cal.getTime());

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            String GpsLat="0";
            String GpsLong="0";
            String GpsAccuracy="0";
            String GpsAddress="0";
            if(isGPSEnabled)
            {
                Location nwLocation=appLocationService.getLocation(locationManager, LocationManager.GPS_PROVIDER,location);

                if(nwLocation!=null)
                {
                    double lattitude=nwLocation.getLatitude();
                    double longitude=nwLocation.getLongitude();
                    double accuracy= nwLocation.getAccuracy();
                    GpsLat=""+lattitude;
                    GpsLong=""+longitude;
                    GpsAccuracy=""+accuracy;
                    if(isOnline())
                    {
                        GpsAddress=getAddressOfProviders(GpsLat, GpsLong);
                    }
                    else
                    {
                        GpsAddress="NA";
                    }

                    GPSLocationLatitude=""+lattitude;
                    GPSLocationLongitude=""+longitude;
                    GPSLocationProvider="GPS";
                    GPSLocationAccuracy=""+accuracy;
                    AllProvidersLocation="GPS=Lat:"+lattitude+"Long:"+longitude+"Acc:"+accuracy;

                }
            }

            Location gpsLocation=appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER,location);
            String NetwLat="0";
            String NetwLong="0";
            String NetwAccuracy="0";
            String NetwAddress="0";
            if(gpsLocation!=null)
            {
                double lattitude1=gpsLocation.getLatitude();
                double longitude1=gpsLocation.getLongitude();
                double accuracy1= gpsLocation.getAccuracy();

                NetwLat=""+lattitude1;
                NetwLong=""+longitude1;
                NetwAccuracy=""+accuracy1;
                if(isOnline())
                {
                    NetwAddress=getAddressOfProviders(NetwLat, NetwLong);
                }
                else
                {
                    NetwAddress="NA";
                }

                NetworkLocationLatitude=""+lattitude1;
                NetworkLocationLongitude=""+longitude1;
                NetworkLocationProvider="Network";
                NetworkLocationAccuracy=""+accuracy1;
                if(!AllProvidersLocation.equals(""))
                {
                    AllProvidersLocation=AllProvidersLocation+"$Network=Lat:"+lattitude1+"Long:"+longitude1+"Acc:"+accuracy1;
                }
                else
                {
                    AllProvidersLocation="Network=Lat:"+lattitude1+"Long:"+longitude1+"Acc:"+accuracy1;
                }


            }

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
                FusedLocationLatitudeWithFirstAttempt=FusedLocationLatitude;
                FusedLocationLongitudeWithFirstAttempt=FusedLocationLongitude;
                FusedLocationAccuracyWithFirstAttempt=FusedLocationAccuracy;


                if(isOnline())
                {
                    FusedAddress=getAddressOfProviders(FusedLat, FusedLong);
                }
                else
                {
                    FusedAddress="NA";
                }

                if(!AllProvidersLocation.equals(""))
                {
                    AllProvidersLocation=AllProvidersLocation+"$Fused=Lat:"+FusedLocationLatitude+"Long:"+FusedLocationLongitude+"Acc:"+fnAccuracy;
                }
                else
                {
                    AllProvidersLocation="Fused=Lat:"+FusedLocationLatitude+"Long:"+FusedLocationLongitude+"Acc:"+fnAccuracy;
                }
            }


            appLocationService.KillServiceLoc(appLocationService, locationManager);
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
                    if(Double.parseDouble(GPSLocationAccuracy)<fnAccuracy)
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
                    if(Double.parseDouble(NetworkLocationAccuracy)<fnAccuracy)
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
            checkHighAccuracyLocationMode(DialogActivity_MarketVisit.this);
            // fnAccurateProvider="";
            if(fnAccurateProvider.equals(""))
            {

                String PersonNodeIDAndPersonNodeType=dbengine.FetchPersonNodeIDAndPersonNodeType(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)),String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)));

                String userName=PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[0];
                String ContactNo=PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[1];


                dbengine.open();
                dbengine.deleteDsrLocationDetails(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)),String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)));

                dbengine.savetblDsrLocationData(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)),String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)),PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[0],PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[1],"NA","NA","NA","NA","NA", "NA", "NA","NA",
                        "NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA",
                        "NA","NA","NA","NA","NA","NA");

                dbengine.close();

                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }


                countSubmitClicked=2;
            }
            else
            {
                String FullAddress="0";
                if(isOnline())
                {
                    FullAddress=   getAddressForDynamic(fnLati, fnLongi);
                }
                else
                {
                    FullAddress="NA";
                }
                String addr="NA";
                String zipcode="NA";
                String city="NA";
                String state="NA";


                if(!FullAddress.equals("NA"))
                {
                    addr = FullAddress.split(Pattern.quote("^"))[0];
                    zipcode = FullAddress.split(Pattern.quote("^"))[1];
                    city = FullAddress.split(Pattern.quote("^"))[2];
                    state = FullAddress.split(Pattern.quote("^"))[3];
                }
              /*  //surbhi
                if(!addr.equals("NA"))
                {
                    etLocalArea.setText(addr);
                }
                if(!zipcode.equals("NA"))
                {
                    etPinCode.setText(zipcode);
                }
                if(!city.equals("NA"))
                {
                    etCity.setText(city);
                }
                if(!state.equals("NA"))
                {
                    etState.setText(state);
                }*/

                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }
                if(!GpsLat.equals("0") )
                {
                    fnCreateLastKnownGPSLoction(GpsLat,GpsLong,GpsAccuracy);
                }

              /*  LattitudeFromLauncher=fnLati;
                LongitudeFromLauncher=fnLongi;
                AccuracyFromLauncher= String.valueOf(fnAccuracy);
                ProviderFromLauncher = fnAccurateProvider;*/
/*
                GpsLatFromLauncher = GpsLat;
                GpsLongFromLauncher = GpsLong;
                GpsAccuracyFromLauncher = GpsAccuracy;

                NetworkLatFromLauncher = NetwLat;
                NetworkLongFromLauncher = NetwLong;
                NetworkAccuracyFromLauncher = NetwAccuracy;

                FusedLatFromLauncher = FusedLat;
                FusedLongFromLauncher = FusedLong;
                FusedAccuracyFromLauncher =FusedAccuracy;

                AllProvidersLocationFromLauncher = AllProvidersLocation;
                GpsAddressFromLauncher = GpsAddress;
                NetwAddressFromLauncher = NetwAddress;
                FusedAddressFromLauncher = FusedAddress;

                FusedLocationLatitudeWithFirstAttemptFromLauncher = FusedLocationLatitudeWithFirstAttempt;
                FusedLocationLongitudeWithFirstAttemptFromLauncher = FusedLocationLongitudeWithFirstAttempt;
                FusedLocationAccuracyWithFirstAttemptFromLauncher = FusedLocationAccuracyWithFirstAttempt;*/
                //LLLLL


               /* ll_map.setVisibility(View.VISIBLE);
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(
                        R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DistributorMapActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);
*/

                dbengine.open();
                dbengine.deleteDsrLocationDetails(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)),String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)));

                //surbhi

                if(GpsAddress.equals(""))
                {
                    GpsAddress="NA";
                }
                if(NetwAddress.equals(""))
                {
                    NetwAddress="NA";
                }
                if(FusedAddress.equals(""))
                {
                    FusedAddress="NA";
                }
                String PersonNodeIDAndPersonNodeType=dbengine.FetchPersonNodeIDAndPersonNodeType(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)),String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)));

                String userName=PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[0];
                String ContactNo=PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[1];

                dbengine.savetblDsrLocationData(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)),String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)),
                        PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[0],PersonNodeIDAndPersonNodeType.split(Pattern.quote("^"))[1],
                        DateTime, addr,zipcode, city, state,fnLati, fnLongi,String.valueOf(fnAccuracy),
                        fnAccurateProvider,AllProvidersLocation,GpsLat,GpsLong,GpsAccuracy,GpsAddress,
                        NetwLat,NetwLong,NetwAccuracy,NetwAddress,FusedLat, FusedLong,FusedAccuracy,FusedAddress,
                        FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);

             /*   System.out.println(String.valueOf(String.valueOf(sharedPref.getInt("CoverageAreaNodeID",0)))+"--"+String.valueOf(sharedPref.getInt("CoverageAreaNodeType",0)+"--"+
                        DateTime+"--"+ addr+"--"+zipcode+"--"+ city+"--"+ state+"--"+fnLati+"--"+ fnLongi+"--"+String.valueOf(fnAccuracy)
                        +"--"+fnAccurateProvider+"--"+AllProvidersLocation+"--"+GpsLat+"--"+GpsLong+"--"+GpsAccuracy+"--"+GpsAddress+"--"+
                        NetwLat+"--"+NetwLong+"--"+NetwAccuracy+"--"+NetwAddress+"--"+FusedLat+"--"+ FusedLong+"--"+FusedAccuracy+"--"+FusedAddress+"--"+
                        FusedLocationLatitudeWithFirstAttempt+"--"+FusedLocationLongitudeWithFirstAttempt+"--"+FusedLocationAccuracyWithFirstAttempt);
*/
                dbengine.close();


                whatTask = 2;
                if(isOnline())
                {
                    try {
                        flgJointWorking=1;
                        new bgTasker().execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        //System.out.println(e);
                    }
                }
                else
                {
                    try
                    {

                        syncTIMESTAMP = System.currentTimeMillis();
                        Date dateobj = new Date(syncTIMESTAMP);


                        dbengine.open();
                        String presentRoute=dbengine.GetActiveRouteID();
                        dbengine.close();
                        //syncTIMESTAMP = System.currentTimeMillis();
                        //Date dateobj = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss",Locale.ENGLISH);
                        //fullFileName1 = df.format(dateobj);
                        String newfullFileName=imei+"."+presentRoute+"."+ df.format(dateobj);



                        File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                        if (!OrderXMLFolder.exists())
                        {
                            OrderXMLFolder.mkdirs();
                        }

                        String routeID=dbengine.GetActiveRouteIDSunil();

                        DASFA.open();
                        DASFA.export(dbengine.DATABASE_NAME, newfullFileName,routeID);


                        DASFA.close();

                        dbengine.savetbl_XMLfiles(newfullFileName, "3","1");

                    }
                    catch(Exception e)
                    {
                    }

                    showNoConnAlert();


                }

                //        if(!checkLastFinalLoctionIsRepeated("28.4873276","77.1045244","22.201"))
               /* if(!checkLastFinalLoctionIsRepeated(fnLati,fnLongi,String.valueOf(fnAccuracy)))
                {
                    fnCreateLastKnownFinalLocation(fnLati,fnLongi,String.valueOf(fnAccuracy));
                    countSubmitClicked=2;
                }
                else
                {
                    if(countSubmitClicked == 1)
                    {
                        countSubmitClicked=2;
                    }
                    if(countSubmitClicked == 0)
                    {
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(AllButtonActivity.this);

                        // Setting Dialog Title
                        alertDialog.setTitle("Information");
                        alertDialog.setIcon(R.drawable.error_info_ico);
                        alertDialog.setCancelable(false);
                        // Setting Dialog Message
                        alertDialog.setMessage("Your current location is same as previous, so please turn off your location services then turn on, it back again.");

                        // On pressing Settings button
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                countSubmitClicked++;
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        });

                        // Showing Alert Message
                        alertDialog.show();

                    }
                }*/

                GpsLat="0";
                GpsLong="0";
                GpsAccuracy="0";
                GpsAddress="0";
                NetwLat="0";
                NetwLong="0";
                NetwAccuracy="0";
                NetwAddress="0";
                FusedLat="0";
                FusedLong="0";
                FusedAccuracy="0";
                FusedAddress="0";

                //code here
            }
        }

        @Override
        public void onTick(long arg0) {

        }}

    public void checkHighAccuracyLocationMode(Context context) {
        int locationMode = 0;
        String locationProviders;

        flgLocationServicesOnOff=0;
        flgGPSOnOff=0;
        flgNetworkOnOff=0;
        flgFusedOnOff=0;
        flgInternetOnOffWhileLocationTracking=0;

        if(isGooglePlayServicesAvailable())
        {
            flgFusedOnOff=1;
        }
        if(isOnline())
        {
            flgInternetOnOffWhileLocationTracking=1;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            //Equal or higher than API 19/KitKat
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
                if (locationMode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=1;
                    flgNetworkOnOff=1;
                    //flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=0;
                    flgNetworkOnOff=1;
                    // flgFusedOnOff=1;
                }
                if (locationMode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY){
                    flgLocationServicesOnOff=1;
                    flgGPSOnOff=1;
                    flgNetworkOnOff=0;
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

                flgLocationServicesOnOff = 0;
                flgGPSOnOff = 0;
                flgNetworkOnOff = 0;
                // flgFusedOnOff = 0;
            }
            if (locationProviders.contains(LocationManager.GPS_PROVIDER) && locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                flgLocationServicesOnOff = 1;
                flgGPSOnOff = 1;
                flgNetworkOnOff = 1;
                //flgFusedOnOff = 0;
            } else {
                if (locationProviders.contains(LocationManager.GPS_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 1;
                    flgNetworkOnOff = 0;
                    // flgFusedOnOff = 0;
                }
                if (locationProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                    flgLocationServicesOnOff = 1;
                    flgGPSOnOff = 0;
                    flgNetworkOnOff = 1;
                    //flgFusedOnOff = 0;
                }
            }
        }

    }

   /* public String getAddressOfProviders(String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());



        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);

            if (addresses == null || addresses.size()  == 0)
            {
                FULLADDRESS2=  FULLADDRESS2.append("NA");
            }
            else
            {
                for(Address address : addresses) {
                    //  String outputAddress = "";
                    for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        if(i==1)
                        {
                            FULLADDRESS2.append(address.getAddressLine(i));
                        }
                        else if(i==2)
                        {
                            FULLADDRESS2.append(",").append(address.getAddressLine(i));
                        }
                    }
                }
		      *//* //String address = addresses.get(0).getAddressLine(0);
		       String address = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
		       String city = addresses.get(0).getLocality();
		       String state = addresses.get(0).getAdminArea();
		       String country = addresses.get(0).getCountryName();
		       String postalCode = addresses.get(0).getPostalCode();
		       String knownName = addresses.get(0).getFeatureName();
		       FULLADDRESS=address+","+city+","+state+","+country+","+postalCode;
		      Toast.makeText(contextcopy, "ADDRESS"+address+"city:"+city+"state:"+state+"country:"+country+"postalCode:"+postalCode, Toast.LENGTH_LONG).show();*//*

            }

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        return FULLADDRESS2.toString();

    }*/

    private String getAddressOfProviders(String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(DialogActivity_MarketVisit.this, Locale.ENGLISH);



        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);

            if (addresses == null || addresses.size()  == 0 || addresses.get(0).getAddressLine(0)==null)
            {
                FULLADDRESS2=  FULLADDRESS2.append("NA");
            }
            else
            {
                FULLADDRESS2 =FULLADDRESS2.append(addresses.get(0).getAddressLine(0));
            }

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Here 1 represent max location result to returned, by documents it recommended 1 to 5


        return FULLADDRESS2.toString();

    }

    private void stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);




    }

    private String getAddressForDynamic(String latti, String longi){


        String areaToMerge="NA";
        Address address=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);
            if (addresses != null && addresses.size() > 0){
                if(addresses.get(0).getAddressLine(1)!=null){
                    addr=addresses.get(0).getAddressLine(1);
                }

                if(addresses.get(0).getLocality()!=null){
                    city=addresses.get(0).getLocality();
                }

                if(addresses.get(0).getAdminArea()!=null){
                    state=addresses.get(0).getAdminArea();
                }


                for(int i=0 ;i<addresses.size();i++){
                    address = addresses.get(i);
                    if(address.getPostalCode()!=null){
                        zipcode=address.getPostalCode();
                        break;
                    }




                }

                if(addresses.get(0).getAddressLine(0)!=null && addr.equals("NA")){
                    String countryname="NA";
                    if(addresses.get(0).getCountryName()!=null){
                        countryname=addresses.get(0).getCountryName();
                    }

                    addr=  getAddressNewWay(addresses.get(0).getAddressLine(0),city,state,zipcode,countryname);
                }

            }
            else{FULLADDRESS3.append("NA");}
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            return addr+"^"+zipcode+"^"+city+"^"+state;
        }
    }

    private String getAddressNewWay(String ZeroIndexAddress, String city, String State, String pincode, String country){
        String editedAddress=ZeroIndexAddress;
        if(editedAddress.contains(city)){
            editedAddress= editedAddress.replace(city,"");

        }
        if(editedAddress.contains(State)){
            editedAddress=editedAddress.replace(State,"");

        }
        if(editedAddress.contains(pincode)){
            editedAddress= editedAddress.replace(pincode,"");

        }
        if(editedAddress.contains(country)){
            editedAddress=editedAddress.replace(country,"");

        }
        if(editedAddress.contains(",")){
            editedAddress=editedAddress.replace(","," ");

        }

        return editedAddress;
    }

    private void fnCreateLastKnownGPSLoction(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy)
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
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.AppLatLngJsonFile+"/"+txtFileNamenew;


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

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


private void marketVisitGetRoutesClick(){
    if(isOnline())
    {
        if(isOnline())
        {

            Boolean isRouteAvailable=dbengine.checkIfRouteExist();
            if(isRouteAvailable)
            {
                SyncStockData task = new SyncStockData(DialogActivity_MarketVisit.this);
                task.execute();
            }
            else
            {
                showAlertSingleButtonError(getResources().getString(R.string.NoRouteAvailable));
                return;
            }
        }
        else
        {
            showAlertSingleButtonError(getResources().getString(R.string.NoDataConnectionFullMsg));
        }
    }
    else
    {
        showNoConnAlert();
    }

}


    private class SyncStockData extends AsyncTask<Void, Void, Void>
    {

        int flgStockOut=0;



        public SyncStockData(DialogActivity_MarketVisit activity)
        {

            serviceException=false;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();




            dbengine.open();
            String getPDADate=dbengine.fnGetPdaDate();
            String getServerDate=dbengine.fnGetServerDate();



            dbengine.close();

            if(!getPDADate.equals(""))  // || !getPDADate.equals("NA") || !getPDADate.equals("Null") || !getPDADate.equals("NULL")
            {
                if(!getServerDate.equals(getPDADate))
                {

                    showAlertSingleButtonInfo(getResources().getString(R.string.txtErrorPhnDate));

                    dbengine.open();
                    dbengine.maintainPDADate();
                    dbengine.reCreateDB();
                    dbengine.close();
                    return;
                }
            }






            dbengine.open();
            rID=dbengine.GetActiveRouteID();

            if(rID.equals("0"))
            {
                rID=dbengine.GetNotActiveRouteID();
            }
            dbengine.updateActiveRoute(rID, 1);

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

            showProgress(getResources().getString(R.string.RetrivingDataMsg));
        }

        @Override
        protected Void doInBackground(Void... params)
        {

            // ServiceWorker newservice=new ServiceWorker();
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
            for(int mm = 1; mm<44; mm++) {
                if (mm == 1) {
                    newservice = newservice.fnGetStockUploadedStatus(getApplicationContext(), fDate, imei);

                    if (!newservice.director.toString().trim().equals("1")) {
                        serviceException = true;

                    }
                }
                if (mm == 7) {
                    newservice = newservice.fnGetVanStockData(getApplicationContext(), CommonInfo.imei);
                    if (!newservice.director.toString().trim().equals("1")) {
                        serviceException = true;
                    }

                }
                if(mm==2)
                {

                    newservice = newservice.getallProduct(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=2)
                    {
                        serviceException=true;
                        break;
                    }
                }
                if(mm==3)
                {

                    newservice = newservice.getCategory(getApplicationContext(), imei);
                    if(newservice.flagExecutedServiceSuccesfully!=3)
                    {
                        serviceExceptionCode=" for Category and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }

                }
                if(mm==8)
                {
                    newservice = newservice.getfnGetStoreWiseTarget(getApplicationContext(), fDate, imei, rID,RouteType);
                }
                if(mm==9)
                {
                    newservice = newservice.fnGetPDACollectionMaster(getApplicationContext(), fDate, imei, rID);
                    if(newservice.flagExecutedServiceSuccesfully!=40)
                    {
                        serviceExceptionCode=" for Collection and Error Code is : "+newservice.exceptionCode;
                        System.out.println("GRLTyre = "+mm);
                        serviceException=true;
                        break;
                    }
                }
                if(mm==21)
                {
                    newservice = newservice.GetPDAIsSchemeApplicable(getApplicationContext(), fDate, imei, rID,RouteType);
                      /*  if(newservice.flagExecutedServiceSuccesfully!=21)
                        {
                            serviceException=true;
                            break;
                        }*/

                }
                if(mm==29)
                {
                    newservice = newservice.GetLODDetailsOnLastSalesSummary(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=29)
                    {
                        serviceExceptionCode=" for last summary and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }

                if(mm==31)
                {
                    newservice = newservice.GetCallspForPDAGetLastVisitDate(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=31)
                    {

                        serviceExceptionCode=" for last visit and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }
                if(mm==32)
                {
                    newservice = newservice.GetCallspForPDAGetLastOrderDate(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=32)
                    {
                        serviceExceptionCode=" for last order's and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }
                if(mm==33)
                {
                    newservice = newservice.GetCallspForPDAGetLastVisitDetails(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=33)
                    {
                        serviceExceptionCode=" for last visit's and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }
                if(mm==34)
                {
                    newservice = newservice.GetCallspForPDAGetLastOrderDetails(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=34)
                    {
                        serviceExceptionCode=" for last order detials and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }
                if(mm==35)
                {
                    newservice = newservice.GetCallspForPDAGetLastOrderDetails_TotalValues(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=35)
                    {
                        serviceExceptionCode=" for last order total values and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }
                if(mm==36)
                {
                    newservice = newservice.GetCallspForPDAGetExecutionSummary(getApplicationContext(), fDate, imei, rID,RouteType);
                    if(newservice.flagExecutedServiceSuccesfully!=36)
                    {
                        serviceExceptionCode=" for execution summary and Error Code is : "+newservice.exceptionCode;
                        serviceException=true;
                        break;
                    }
                }

                if(mm==37)
                {
                     /*   newservice = newservice.getQuotationDataFromServer(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=37)
                        {
                            serviceException=true;
                            break;
                        }*/
                }

                if(mm==38)
                {
                      newservice=newservice.fnGetDistStockData(getApplicationContext(),imei);
                        if(newservice.flagExecutedServiceSuccesfully!=38)
                        {
                            serviceException=true;
                            break;
                        }

                }


                if(mm==39)
                {
                      /* newservice=newservice.fnGetVanStockData(getApplicationContext(),imei);
                        if(newservice.flagExecutedServiceSuccesfully!=39)
                        {
                            serviceException=true;
                            break;
                        }*/
                    newservice = newservice.getProductListLastVisitStockOrOrderMstr(getApplicationContext(), fDate, imei, rID);
                    if(newservice.flagExecutedServiceSuccesfully!=1)
                    {
                        serviceException=true;
                        break;
                    }
                }

                if(mm==40)
                {

                    newservice = newservice.getStoreWiseOutStandings(getApplicationContext(), fDate, imei, rID,RouteType);
                       /* if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceException=true;
                            break;
                        }*/
                }

                if(mm==41)
                {

                    newservice = newservice.getInvoiceCaption(getApplicationContext(), fDate, imei, rID,RouteType);

                }
                if(mm==43)
                {
                    newservice = newservice.getProductListLastVisitStockOrOrderMstr(getApplicationContext(), fDate, imei, rID);
                    if(newservice.flagExecutedServiceSuccesfully!=1)
                    {
                        serviceException=true;
                        break;
                    }
                }


            }


            return null;
        }



        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
           // dismissProgress();

            flgStockOut= dbengine.fetchtblStockUploadedStatus();
            if( CommonInfo.FlgDSRSO==2)
            {
                flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
            }
            else
            {
                String SOCoverageAreaIDAndType=dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();
                flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[0]),Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[1]));
            }
            //  flgStockOut=1;
            if(serviceException)
            {
                dismissProgress();
                serviceException=false;
                showAlertException( getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrorRetrievingData));
                //Toast.makeText(AllButtonActivity.this,"Please fill Stock out first for starting your market visit.",Toast.LENGTH_SHORT).show();
                //showSyncError();
            }
            else if((flgStockOut==0 && flgOwnRouteClick==1) && (flgDrctslsIndrctSls==1))
            {
                dismissProgress();
                flgOwnRouteClick=0;
                showAlertStockOut(getResources().getString(R.string.genTermNoDataConnection),getResources().getString(R.string.AlertVANStockStockOut)); // message change by Avinash Sir on 3 Aug 2018 on Paras SO SFA
                //   Toast.makeText(AllButtonActivity.this,"Error while retrieving data.",Toast.LENGTH_SHORT).show();
            }
           else if((dbengine.flgConfirmedWareHouse()==0 && flgOwnRouteClick==1) && (flgDrctslsIndrctSls==1))
           // else if((dbengine.flgConfirmedWareHouse()==0 && flgOwnRouteClick==1) )
            {
                dismissProgress();
                flgOwnRouteClick=0;
                showAlertStockOut(getResources().getString(R.string.genTermNoDataConnection),getResources().getString(R.string.AlertVANStockStockValidate));
            }
            else
            {
                GetStoresForDay task = new GetStoresForDay(DialogActivity_MarketVisit.this);
                task.execute();
            }

        }
    }
    public void showAlertStockOut(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DialogActivity_MarketVisit.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
    private class GetStoresForDay extends AsyncTask<Void, Void, Void>
    {


        public GetStoresForDay(DialogActivity_MarketVisit activity)
        {

        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();




            // Base class method for Creating ProgressDialog
            //showProgress(getResources().getString(R.string.RetrivingDataMsg));


        }

        @Override
        protected Void doInBackground(Void... args)
        {


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
                for(int mm = 1; mm < 44  ; mm++)
                {
                    System.out.println("Excecuted function : "+mm);
                    if(mm==1)
                    {

                       /* newservice = newservice.getallStores(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }
                    if(mm==2)
                    {

                      /*  newservice = newservice.getallProduct(getApplicationContext(), fDate, imei, rID,RouteType);
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
                            serviceExceptionCode=" for Category and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }

                    }
                    if(mm==4)
                    {

                      /*  Date currDateNew = new Date();
                        SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                        String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
                        newservice = newservice.getAllNewSchemeStructure(getApplicationContext(), currSysDateNew, imei, rID,RouteType);
                      *//*  if(newservice.flagExecutedServiceSuccesfully!=4)
                        {
                            serviceException=true;
                            break;
                        }*/

                    }
                    if(mm==5)
                    {

                       /* Date currDateNew = new Date();
                        SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                        String currSysDateNew = currDateFormatNew.format(currDateNew).toString();
                        newservice = newservice.getallPDASchAppListForSecondPage(getApplicationContext(), currSysDateNew, imei, rID,RouteType);
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
                        newservice = newservice.getfnGetStoreWiseTarget(getApplicationContext(), fDate, imei, rID,RouteType);
                    }
                    if(mm==9)
                    {
                        newservice = newservice.fnGetPDACollectionMaster(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=40)
                        {
                            serviceExceptionCode=" for Collection and Error Code is : "+newservice.exceptionCode;
                            System.out.println("GRLTyre = "+mm);
                            serviceException=true;
                            break;
                        }
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
                      /*  if(newservice.flagExecutedServiceSuccesfully!=21)
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
                        newservice = newservice.GetLODDetailsOnLastSalesSummary(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=29)
                        {
                            serviceExceptionCode=" for last summary and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }

                    if(mm==31)
                    {
                        newservice = newservice.GetCallspForPDAGetLastVisitDate(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=31)
                        {

                            serviceExceptionCode=" for last visit and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }
                    if(mm==32)
                    {
                        newservice = newservice.GetCallspForPDAGetLastOrderDate(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=32)
                        {
                            serviceExceptionCode=" for last order's and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }
                    if(mm==33)
                    {
                        newservice = newservice.GetCallspForPDAGetLastVisitDetails(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=33)
                        {
                            serviceExceptionCode=" for last visit's and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }
                    if(mm==34)
                    {
                        newservice = newservice.GetCallspForPDAGetLastOrderDetails(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=34)
                        {
                            serviceExceptionCode=" for last order detials and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }
                    if(mm==35)
                    {
                        newservice = newservice.GetCallspForPDAGetLastOrderDetails_TotalValues(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=35)
                        {
                            serviceExceptionCode=" for last order total values and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }
                    if(mm==36)
                    {
                        newservice = newservice.GetCallspForPDAGetExecutionSummary(getApplicationContext(), fDate, imei, rID,RouteType);
                        if(newservice.flagExecutedServiceSuccesfully!=36)
                        {
                            serviceExceptionCode=" for execution summary and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }

                    if(mm==37)
                    {
                     /*   newservice = newservice.getQuotationDataFromServer(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=37)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }

                    if(mm==38)
                    {
                 /*      newservice=newservice.fnGetDistStockData(getApplicationContext(),imei);
                        if(newservice.flagExecutedServiceSuccesfully!=38)
                        {
                            serviceException=true;
                            break;
                        }
*/
                    }


                    if(mm==39)
                    {
                      /* newservice=newservice.fnGetVanStockData(getApplicationContext(),imei);
                        if(newservice.flagExecutedServiceSuccesfully!=39)
                        {
                            serviceException=true;
                            break;
                        }*/
                        newservice = newservice.getProductListLastVisitStockOrOrderMstr(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceException=true;
                            break;
                        }
                    }

                    if(mm==40)
                    {

                        newservice = newservice.getStoreWiseOutStandings(getApplicationContext(), fDate, imei, rID,RouteType);
                       /* if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceException=true;
                            break;
                        }*/
                    }

                    if(mm==41)
                    {

                        newservice = newservice.getInvoiceCaption(getApplicationContext(), fDate, imei, rID,RouteType);

                    }
                    if(mm==42)
                    {

                        newservice = newservice.getallStores(getApplicationContext(), fDate, imei, rID,RouteType,1);
                        if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceExceptionCode=" for getting all stores and Error Code is : "+newservice.exceptionCode;
                            serviceException=true;
                            break;
                        }
                    }
                    if(mm==43)
                    {
                        newservice = newservice.getProductListLastVisitStockOrOrderMstr(getApplicationContext(), fDate, imei, rID);
                        if(newservice.flagExecutedServiceSuccesfully!=1)
                        {
                            serviceException=true;
                            break;
                        }
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


            dismissProgress();   // Base class method for dismissing ProgressDialog

            if(serviceException)
            {
                try
                {
                    serviceException=false;
                    showAlertException(getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrorRetrievingData)+serviceExceptionCode);
                }

                catch(Exception e)
                {

                }
              /*  dbengine.open();

               // dbengine.maintainPDADate();
               // dbengine.dropRoutesTBL();
               // dbengine.reCreateDB();

                dbengine.close();*/
            }
            else
            {
                Intent storeIntent = new Intent(DialogActivity_MarketVisit.this, StoreSelection.class);
                storeIntent.putExtra("imei", imei);
                storeIntent.putExtra("userDate", currSysDate);
                storeIntent.putExtra("pickerDate", fDate);
                storeIntent.putExtra("rID", rID);
                startActivity(storeIntent);
                finish();

            }

        }
    }

    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DialogActivity_MarketVisit.this);
        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setMessage(R.string.NoDataConnectionFullMsg);
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }

    private void showAlertBox(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DialogActivity_MarketVisit.this);
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


    private void showNewVersionAvailableAlert()
    {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
        r.play();
        //AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(new ContextThemeWrapper(LauncherActivity.this, R.style.Dialog));
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DialogActivity_MarketVisit.this);

        alertDialogNoConn.setTitle(R.string.AlertDialogHeaderMsg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setMessage(getText(R.string.NewVersionMsg));
        alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                GetUpdateInfo task = new GetUpdateInfo(DialogActivity_MarketVisit.this);
                task.execute();
                dialog.dismiss();
            }
        });

        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();

    }
    private class GetUpdateInfo extends AsyncTask<Void, Void, Void>
    {

        final ProgressDialog pDialogGetStores;
        GetUpdateInfo(DialogActivity_MarketVisit activity)
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
            URL url = new URL(CommonInfo.VersionDownloadPath.trim()+ CommonInfo.VersionDownloadAPKName);
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
            File file2 = new File(PATH+ CommonInfo.VersionDownloadAPKName);
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

    public void showAlertException(String title,String msg)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DialogActivity_MarketVisit.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg + serviceExceptionCode);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.txtRetry), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {
                new SyncStockData(DialogActivity_MarketVisit.this).execute();
                dialog.dismiss();
            }
        });

        alertDialog.setNegativeButton(getResources().getString(R.string.txtCancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
    private class bgTasker extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialog2 = ProgressDialog.show(DialogActivity_MarketVisit.this,getText(R.string.PleaseWaitMsg),getText(R.string.genTermProcessingRequest), true);
            pDialog2.setIndeterminate(true);
            pDialog2.setCancelable(false);
            pDialog2.show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {





                if (whatTask == 2)
                {
                    whatTask = 0;
                    // stores with Sstat = 1 !


                    pDialog2.dismiss();
                    dbengine.open();

                    //dbengine.updateActiveRoute(rID, 0);
                    dbengine.close();
                    // sync here


                    SyncNow();


                }else if (whatTask == 3) {
                    // sync rest
                    whatTask = 0;

                    pDialog2.dismiss();
                    //dbengine.open();
                    //String rID=dbengine.GetActiveRouteID();
                    //dbengine.updateActiveRoute(rID, 0);
                    //dbengine.close();
                    // sync here

                    SyncNow();


                    /*
                     * dbengine.open(); dbengine.reCreateDB(); dbengine.close();
                     */
                }else if (whatTask == 1) {
                    // clear all
                    whatTask = 0;

                    SyncNow();

                    dbengine.open();
                    //String rID=dbengine.GetActiveRouteID();
                    //dbengine.updateActiveRoute(rID, 0);
                    dbengine.reCreateDB();

                    dbengine.close();
                }/*else if (whatTask == 0)
				{
					try {
						new FullSyncDataNow().execute().get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/


            } catch (Exception e) {
                Log.i("bgTasker", "bgTasker Execution Failed!", e);

            }

            finally {

                Log.i("bgTasker", "bgTasker Execution Completed...");

            }

            return null;
        }



        @Override
        protected void onCancelled() {
            Log.i("bgTasker", "bgTasker Execution Cancelled");
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.i("bgTasker", "bgTasker Execution cycle completed");
            pDialog2.dismiss();
            whatTask = 0;

        }
    }
    public void SyncNow()
    {

        syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);


        dbengine.open();
        String presentRoute=dbengine.GetActiveRouteID();
        dbengine.close();
        //syncTIMESTAMP = System.currentTimeMillis();
        //Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss",Locale.ENGLISH);
        //fullFileName1 = df.format(dateobj);
        String newfullFileName=imei+"."+presentRoute+"."+ df.format(dateobj);



        try
        {

            File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

            if (!OrderXMLFolder.exists())
            {
                OrderXMLFolder.mkdirs();
            }

            String routeID=dbengine.GetActiveRouteIDSunil();

            DASFA.open();
            DASFA.export(dbengine.DATABASE_NAME, newfullFileName,routeID);


            DASFA.close();

            dbengine.savetbl_XMLfiles(newfullFileName, "3","1");
            dbengine.open();
            dbengine.fnSettblDsrLocationDetails();

            dbengine.close();

           // flgChangeRouteOrDayEnd=valDayEndOrChangeRoute;

            if(isOnline())
            {
                Intent syncIntent = new Intent(DialogActivity_MarketVisit.this, SyncMaster.class);
                syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                syncIntent.putExtra("OrigZipFileName", newfullFileName);
                syncIntent.putExtra("whereTo", whereTo);
                startActivity(syncIntent);
                finish();
            }
            else
            {
                showNoConnAlert();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
