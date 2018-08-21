package project.astix.com.balajisosfadirectsales;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Sunil on 10/9/2017.
 */


public class AddNewStore_DynamicSectionWiseSO extends FragmentActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,SearchListCommunicator,OnMapReadyCallback,CategoryCommunicatorCityState
{
    String getActiveRouteId="";
    public static int flgRuleTaxVal, flgTransType;
    public static String distID="0";
    public String fnlStoreType="0",fnlOwnerName="NA",fnlMobileNumber="NA",fnlAddressName="NA",fnSalesPersonName="NA",fnSalesPersonContactNo="NA",fnStoreCatType="NA";
    public static String activityFrom="";
    public static int flgLocationServicesOnOff=0;
    public static int flgGPSOnOff=0;
    public static int flgNetworkOnOff=0;
    public static int flgFusedOnOff=0;
    public static int flgInternetOnOffWhileLocationTracking=0;
    public static int flgRestart=0;
    public static int flgStoreOrder=0;


    public static  int flgUpdateSomeNewStoreFlags=0;


    public static String fnAddressFromLauncher="NA";
    public static String SOProviderFromLauncher="NA";
    public static String SOGpsLatFromLauncher="NA";
    public static String SOGpsLongFromLauncher="NA";
    public static String SOGpsAccuracyFromLauncher="NA";
    public static String SONetworkLatFromLauncher="NA";
    public static String SONetworkLongFromLauncher="NA";
    public static String SONetworkAccuracyFromLauncher="NA";
    public static String SOFusedLatFromLauncher="NA";
    public static String SOFusedLongFromLauncher="NA";
    public static String SOFusedAccuracyFromLauncher="NA";

    public static String SOfnAddressFromLauncher="NA";
    public static String SOAllProvidersLocationFromLauncher="NA";
    public static String SOGpsAddressFromLauncher="NA";
    public static String SONetwAddressFromLauncher="NA";
    public static String SOFusedAddressFromLauncher="NA";
    public static String SOFusedLocationLatitudeWithFirstAttemptFromLauncher="NA";
    public static String SOFusedLocationLongitudeWithFirstAttemptFromLauncher="NA";
    public static String SOFusedLocationAccuracyWithFirstAttemptFromLauncher="NA";



    public static String prvsStoreId="";
    public String FusedLocationLatitudeWithFirstAttempt="0";
    public String FusedLocationLongitudeWithFirstAttempt="0";
    public String FusedLocationAccuracyWithFirstAttempt="0";

    public int flgOldNewStore=0;
    public int flgSelfStore=0;
    public String newfullFileName;
    String allValuesOfPaymentStageID="0";
    public String QuestIDForOutChannel="0";
    int countSubmitClicked=0;
    public String AllProvidersLocation="";
    // public GetUpdateSchemeForNewStore  taskUpdateScheme=null;

   /* @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            imageF_savedInstance = new File(savedInstanceState.getString("imageClkdPath"));

            imageName_savedInstance = savedInstanceState.getString("imageName");
            clickedTagPhoto_savedInstance = savedInstanceState.getString("clickedTagPhoto");
            uriSavedImage_savedInstance = Uri.parse(savedInstanceState.getString("uriSavedImage"));
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    LinkedHashMap<String, String> hmapStoresDSRImageList=new LinkedHashMap<String, String>();


    public int flgHasQuote=0;
    public static String address,pincode,city,state,latitudeToSave,longitudeToSave,accuracyToSave;
    public int flgAllowQuotation=1;
    public int flgSubmitFromQuotation=0;
    public CoundownClass countDownTimer;



    private final long startTime = 15000;
    private final long interval = 200;

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    LocationRequest mLocationRequest;

    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;

    public String OutletID="NA";
    public String StoreName="NA";

    public static String storeNameToShow="";
    public String StoreName_tag="NA";
    public static int StoreTypeTradeChannel=0;



    String fusedData;

    public int checkSecondTaskStatus=0;
    public static int ServiceWorkerDataComingFlag=0;
    public static String ServiceWorkerStoreID="";
    public static String ServiceWorkerResultSet="";


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
    TextView txtAddress,txtAccuracy,txtLong,txtLat,txt_internetProb;
    RelativeLayout rl_sectionMap,rl_sectionQuest;

    // public	GetAddingStoreInfo task=null;
    public Timer timer;
    // public	MyTimerTask myTimerTask;

    public ProgressDialog pDialogGetStores;
    String VisitEndTS;
    public int chkFlgForErrorToCloseApp=0;
    String CustomStringForServiceWorker="";

    String CustomStoreID="NA";
    LocationVo locVo;

    public LocationManager locationManager;
    public static int battLevel;

    public float Current_acc;
    MapFragment mapFrag;
    GoogleMap googleMap;

    Button btnSubmit;
    public Location location;
    public ProgressDialog pDialog2STANDBY;

    public LocationListener locationListener;
    public double latitude; // latitude
    public double longitude; // longitude

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private boolean mIsServiceStarted = false;
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000  * 1; // 1 second

    private static final long MIN_TIME_BW_UPDATESNew = 100  * 1; // 1 second

    public boolean isGPSEnabled = false;
    public   boolean isNetworkEnabled = false;
    public   PowerManager pm;
    public	 PowerManager.WakeLock wl;
    public float acc=0F;
    String locProvider="None";
    public String lastKnownLocLatitude="";
    public String lastKnownLocLongitude="";
    public String accuracy="0";
    public String locationProvider="Default";

    public ProgressDialog pDialogSync;



    LinearLayout ll_allData,ll_locmsg;
    AddressResultReceiver mResultReceiver;
    public static String fetchAddress="";


    NewStoreFormSO newStore_Fragment;
    FragmentManager manager;
    FragmentTransaction fragTrans;
    ImageView img_next;
    TextView txt_Next;
    TextView txt_Activityname;
    ImageView img_reject;
    TextView txt_reject;

    ImageView img_revisit;
    TextView txt_revisit;


    public static LinkedHashMap<String, ArrayList<String>> hmapSection_key=new LinkedHashMap<String, ArrayList<String>>();
    public static LinkedHashMap<String, String> hmapDpndtQustGrpId=new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> hmapQuesIdandGetAnsCntrlType=new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, String> hmapQuesIdValues=new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, ArrayList<String>> hmapQuesGropKeySection=new LinkedHashMap<String, ArrayList<String>>();
    public static LinkedHashMap<String, String> hmapGroupId_Desc=new LinkedHashMap<String, String>();
    public static LinkedHashMap<String, ArrayList<String>> hmapSctnId_GrpId=new LinkedHashMap<String, ArrayList<String>>();
    // public static LinkedHashMap<String, String> hmapOptionId_OptionValue=new LinkedHashMap<String, String>();
    LinearLayout ll_next,ll_back,ll_save_Exit,ll_map,ll_refresh,ll_reject,ll_revisit;
    int refreshCount=0;
    RadioGroup rg_yes_no;
    RadioButton rb_yes,rb_no;
    Button btn_refresh;
    TextView txt_rfrshCmnt;

    ImageView img_exit;
    PRJDatabase helperDb;

    public static String date_value="";
    public static String rID;
    public  int sectionToShowHide=1;

    public static String pickerDate="";
    public static String imei;

    public static String VisitStartTS="NA";
    public static String selStoreID="0";


    public static int CoverageAreaID=0;
    public static int RouteNodeID=0;
    public static String StoreCategoryType="0-0-0";
    public static int StoreSectionCount=0;




    public static String FLAG_NEW_UPDATE="";
    String LattitudeFromLauncher="NA";
    String   LongitudeFromLauncher="NA";

    String SOLattitudeFromLauncher="NA";
    String   SOLongitudeFromLauncher="NA";
    String SOAccuracyFromLauncer="NA";

    public String SOStoreVisitStartTime="NA";
    public String   SOStoreVisitEndTime="NA";




    String AccuracyFromLauncher="NA";
    String   AddressFromLauncher="NA";
    String CityFromLauncher="NA";
    String   PincodeFromLauncher="NA";
    String StateFromLauncher="NA";

    MyReceiver myReceiver;

   PRJDatabase dbengineSO=new PRJDatabase(this);
    DatabaseAssistantSO DA=new DatabaseAssistantSO(this);

    public static String  VisitStartTimeOfNewStore="NA";

    public static int IsStoreDataCompleteSaved=0;
    public static int flgApproveOrRejectOrNoActionOrReVisit=0;
    public static int flgStoreVisitMode=0;


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
       /* if (mIsServiceStarted) {
            mIsServiceStarted = false;
            stopService(new Intent(AddNewStore_DynamicSectionWiseSO.this, LocationUpdateService.class));
        }*/
        this.unregisterReceiver(this.mBatInfoReceiver);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            return true;
        }
        if(keyCode==KeyEvent.KEYCODE_HOME)
        {
            // finish();
            return true;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newstoredynamicsectionwise_so);
        prvsStoreId="";
        flgStoreVisitMode= CommonInfo.flgSOOnlineOffLine;
        refreshCount=0;
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        helperDb=new PRJDatabase(AddNewStore_DynamicSectionWiseSO.this);
        locVo=new LocationVo();
        address="";
        pincode="";
        city="";
        state="";
        latitudeToSave="";
        longitudeToSave="";
        accuracyToSave="";
        FLAG_NEW_UPDATE="";
        boolean isGPSok = false;
        boolean isNWok=false;
        VisitStartTS="";
        isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        long  syncTIMESTAMP1 = System.currentTimeMillis();
        Date dateobj1 = new Date(syncTIMESTAMP1);
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        VisitStartTS = df1.format(dateobj1);
        int flgCheckStoreAlreadySOLAtLong=0;

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
                // showSettingsAlert();
            }
            catch(Exception e)
            {

            }

            isGPSok = false;
            isNWok=false;
        }
        storeNameToShow="";
        this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        Intent extras = getIntent();
        FLAG_NEW_UPDATE=  extras.getStringExtra("FLAG_NEW_UPDATE");
        txt_Activityname=(TextView) findViewById(R.id.txt_Activityname);
        activityFrom=extras.getStringExtra("activityFrom");
        if(FLAG_NEW_UPDATE.equals("UPDATE"))
        {

            selStoreID=  extras.getStringExtra("StoreID");
            StoreName=    extras.getStringExtra("StoreName");
            CoverageAreaID=Integer.parseInt(extras.getStringExtra("CoverageAreaID"));
            RouteNodeID=Integer.parseInt(extras.getStringExtra("RouteNodeID"));
            StoreCategoryType=    extras.getStringExtra("StoreCategoryType");
            StoreSectionCount=Integer.parseInt(extras.getStringExtra("StoreSectionCount"));
      storeNameToShow=StoreName;
        }
        else
        {
            txt_Activityname.setText("Add New Outlet");
            selStoreID=genTempID();

            CoverageAreaID=Integer.parseInt(extras.getStringExtra("CoverageAreaID"));
            RouteNodeID=Integer.parseInt(extras.getStringExtra("RouteNodeID"));
            StoreCategoryType=    extras.getStringExtra("StoreCategoryType");
            StoreSectionCount=Integer.parseInt(extras.getStringExtra("StoreSectionCount"));

        }


        if(extras !=null)
        {


            date_value="28-Jun-2017";
            pickerDate= "28-Jun-2017";
            //imei="123";
            rID="1";
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

        checkHighAccuracyLocationMode(AddNewStore_DynamicSectionWiseSO.this);

        helperDb.open();
        String allLoctionDetails=  helperDb.getLocationDetailsAddNewStoreSO();
        helperDb.close();
        prvsStoreId=helperDb.getPreviousStoreId();
        if(helperDb.fnCheckIfStoreIDExistsIn_tblStoreDeatils(selStoreID)==0)
        {
            flgOldNewStore=1;
            SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
            SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
            SOAccuracyFromLauncer = allLoctionDetails.split(Pattern.quote("^"))[2];
            AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];


            SOProviderFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[7];
            SOGpsLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[8];
            SOGpsLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[9];
            SOGpsAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[10];
            SONetworkLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[11];
            SONetworkLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[12];
            SONetworkAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[13];
            SOFusedLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[14];
            SOFusedLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[15];
            SOFusedAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[16];

            SOAllProvidersLocationFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[17];
            SOGpsAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[18];
            SONetwAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[19];
            SOFusedAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[20];
            SOFusedLocationLatitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[21];
            SOFusedLocationLongitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[22];
            SOFusedLocationAccuracyWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[23];

            SOStoreVisitStartTime="NA";
            SOStoreVisitEndTime="NA";
            LattitudeFromLauncher="NA";
            LongitudeFromLauncher="NA";
            AccuracyFromLauncher="NA";
            allValuesOfPaymentStageID="0";

            if(SOStoreVisitStartTime.equals("NA"))
            {

                flgUpdateSomeNewStoreFlags=1;
                String Time = fnGenerateDateTime();

                VisitStartTimeOfNewStore=Time;
                SOStoreVisitStartTime=VisitStartTimeOfNewStore;

            }
           /* myReceiver = new MyReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationUpdateService.MY_ACTION);
            registerReceiver(myReceiver, intentFilter);
            startService(new Intent(this, LocationUpdateService.class));
            mIsServiceStarted = true;*/

        }
        else
        {


            ArrayList<String> arrBasisDetailsAgainstStore=    helperDb.fnGetDetails_tblStoreDeatils(selStoreID,StoreName);


            allValuesOfPaymentStageID =  arrBasisDetailsAgainstStore.get(1);//bydefalt "0"
            AddressFromLauncher = arrBasisDetailsAgainstStore.get(2);////bydefalt "NA"
            CityFromLauncher =  arrBasisDetailsAgainstStore.get(3);
            PincodeFromLauncher =  arrBasisDetailsAgainstStore.get(4);
            StateFromLauncher =  arrBasisDetailsAgainstStore.get(5);
            LattitudeFromLauncher = arrBasisDetailsAgainstStore.get(6);
            LongitudeFromLauncher = arrBasisDetailsAgainstStore.get(7);
            AccuracyFromLauncher = arrBasisDetailsAgainstStore.get(8);
            SOLattitudeFromLauncher=arrBasisDetailsAgainstStore.get(9);
            SOLongitudeFromLauncher=arrBasisDetailsAgainstStore.get(10);
            IsStoreDataCompleteSaved=Integer.parseInt(arrBasisDetailsAgainstStore.get(11));

            SOStoreVisitStartTime=arrBasisDetailsAgainstStore.get(12);
            SOStoreVisitEndTime=arrBasisDetailsAgainstStore.get(13);
            flgOldNewStore=Integer.parseInt(arrBasisDetailsAgainstStore.get(14));
            SOAccuracyFromLauncer=arrBasisDetailsAgainstStore.get(15);

            flgSelfStore=Integer.parseInt(arrBasisDetailsAgainstStore.get(16));
            //  SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
            //SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
            if(SOLattitudeFromLauncher.equals("NA"))
            {
                flgCheckStoreAlreadySOLAtLong=1;
                if(!allLoctionDetails.equals("0"))
                {
                    SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
                    SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
                    SOAccuracyFromLauncer = allLoctionDetails.split(Pattern.quote("^"))[2];



                    SOProviderFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[7];
                    SOGpsLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[8];
                    SOGpsLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[9];
                    SOGpsAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[10];
                    SONetworkLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[11];
                    SONetworkLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[12];
                    SONetworkAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[13];
                    SOFusedLatFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[14];
                    SOFusedLongFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[15];
                    SOFusedAccuracyFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[16];

                    SOAllProvidersLocationFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[17];
                    SOGpsAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[18];
                    SONetwAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[19];
                    SOFusedAddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[20];
                    SOFusedLocationLatitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[21];
                    SOFusedLocationLongitudeWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[22];
                    SOFusedLocationAccuracyWithFirstAttemptFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[23];

                }

            }
            if(SOStoreVisitStartTime.equals("NA"))
            {
                flgCheckStoreAlreadySOLAtLong=1;

                flgUpdateSomeNewStoreFlags=1;
                String Time = fnGenerateDateTime();


                dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,0);


                VisitStartTimeOfNewStore=Time;
                SOStoreVisitStartTime=VisitStartTimeOfNewStore;


                if(CommonInfo.flgSOOnlineOffLine==1) {
                    if (IsStoreDataCompleteSaved == 0) {
                        myReceiver = new MyReceiver();
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction(LocationUpdateService.MY_ACTION);
                        registerReceiver(myReceiver, intentFilter);
                        startService(new Intent(this, LocationUpdateService.class));
                        mIsServiceStarted = true;
                    }
                }

            }



            if(SOStoreVisitStartTime.equals("NA"))
            {


                String Time =fnGenerateDateTime();


                dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,1);
            }

        }

        address=AddressFromLauncher;
        pincode=PincodeFromLauncher;
        city=CityFromLauncher;
        state=StateFromLauncher;
        latitudeToSave=SOLattitudeFromLauncher;
        longitudeToSave=SOLongitudeFromLauncher;
        accuracyToSave=SOAccuracyFromLauncer;

		/*  PackageManager m = getPackageManager();
		    if (m.hasSystemFeature(PackageManager.FEATURE_LOCATION)) {

		    	m.setApplicationEnabledSetting(PackageManager.FEATURE_LOCATION,PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 0);
		    }*/
        mResultReceiver = new AddressResultReceiver(new Handler());
        //   ll_allData=(LinearLayout) findViewById(R.id.ll_allData);

        ll_back=(LinearLayout) findViewById(R.id.ll_back);
        ll_next=(LinearLayout) findViewById(R.id.ll_next);

        ll_save_Exit=(LinearLayout) findViewById(R.id.ll_save_Exit);


        ll_revisit=(LinearLayout) findViewById(R.id.ll_revisit);

        ll_reject=(LinearLayout) findViewById(R.id.ll_reject);


        img_next=(ImageView) findViewById(R.id.img_next);
        img_exit=(ImageView) findViewById(R.id.img_exit);



        txt_Next=(TextView) findViewById(R.id.txt_Next);
        ll_map=(LinearLayout) findViewById(R.id.ll_map);
        rg_yes_no= (RadioGroup) findViewById(R.id.rg_yes_no);
        TextView tv_MapLocationCorrectText=(TextView) findViewById(R.id.tv_MapLocationCorrectText);
        ll_refresh= (LinearLayout) findViewById(R.id.ll_refresh);

        btn_refresh= (Button) findViewById(R.id.btn_refresh);
        txt_rfrshCmnt= (TextView) findViewById(R.id.txt_rfrshCmnt);


        if(CommonInfo.flgSOOnlineOffLine==0)
        {
            rg_yes_no.setVisibility(View.GONE);
            tv_MapLocationCorrectText.setVisibility(View.GONE);
            ll_refresh.setVisibility(View.GONE);
            btn_refresh.setVisibility(View.GONE);
            txt_rfrshCmnt.setVisibility(View.GONE);
        }
        rb_yes= (RadioButton) findViewById(R.id.rb_yes);
        rb_no=(RadioButton)findViewById(R.id.rb_no);


        //  rl_sectionQuest=(RelativeLayout) findViewById(R.id.rl_sectionQuest);


        fillHmapData();
        addFragment();
        rg_yes_no.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i!=-1)
                {
                    RadioButton    radioButtonVal = (RadioButton) radioGroup.findViewById(i);
                    if(radioButtonVal.getId()==R.id.rb_yes)
                    {
                        ll_refresh.setVisibility(View.GONE);

                    }
                    else if(radioButtonVal.getId()==R.id.rb_no)
                    {
                        ll_refresh.setVisibility(View.VISIBLE);


                    }
                }

            }
        });
        btn_refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


               /* if (mIsServiceStarted)
                {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/
                boolean isGPSok = false;
                boolean isNWok=false;
                isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isNWok = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

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
                else
                {

                    manager= getFragmentManager();
                    mapFrag = (MapFragment)manager.findFragmentById(
                            R.id.map);
                    // mapFrag.getMapAsync(AddNewStore_DynamicSectionWise.this);
                    mapFrag.getView().setVisibility(View.GONE);
                   /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.hide(mapFrag);*/
                    locationRetrievingAndDistanceCalculating();
                }


                refreshCount++;
                if(refreshCount==1)
                {
                    txt_rfrshCmnt.setText(getString(R.string.second_msg_for_map));
                }
                else if(refreshCount==2)
                {
                    txt_rfrshCmnt.setText(getString(R.string.third_msg_for_map));
                    btn_refresh.setVisibility(View.GONE);
                }
                rg_yes_no.clearCheck();
                ll_refresh.setVisibility(View.GONE);

            }
        });

        img_exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
               /* if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/
                getSectionNextOrBack(4, sectionToShowHide);

                String Time =fnGenerateDateTime();
                dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,1);

                NewStoreFormSO recFragment = (NewStoreFormSO) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;

            }
        });

        ll_save_Exit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                getSectionNextOrBack(2, sectionToShowHide);
                NewStoreFormSO recFragment = (NewStoreFormSO) getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName = recFragment.currentStoreName;

                String Time =fnGenerateDateTime();
                dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,1);


               /* if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/

                IsStoreDataCompleteSaved=1;
                flgApproveOrRejectOrNoActionOrReVisit=0;



            }
        });
      /*  btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showSubmitConfirm();

            }
        });*/

        ll_reject.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(recFragment!=null)
                {
                    StoreName=recFragment.currentStoreName;
                    if(recFragment.validate() && recFragment.validateProperAddressDetails())// && recFragment.validatePaymentStageID()
                    {
                        IsStoreDataCompleteSaved=2;
                        flgApproveOrRejectOrNoActionOrReVisit=2;
                        showSubmitConfirm();
                    }
                }
            }
        });


        ll_revisit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(recFragment!=null)
                {
                    StoreName=recFragment.currentStoreName;
                    // if(recFragment.validate() && recFragment.validatePaymentStageID())
                    if(recFragment.validate() && recFragment.validateProperAddressDetails())
                    {
                        IsStoreDataCompleteSaved=2;
                        flgApproveOrRejectOrNoActionOrReVisit=3;
                        showSubmitConfirm();
                    }
                }
            }
        });



        ll_next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                hideSoftKeyboard(v);


               /* if(ll_reject.getVisibility()==View.VISIBLE)
                {
                    ll_reject.setVisibility(View.GONE);
                }

                if(ll_revisit.getVisibility()==View.VISIBLE)
                {
                    ll_revisit.setVisibility(View.GONE);
                }

                if(ll_map.getVisibility()==View.VISIBLE)
                {
                    ll_map.setVisibility(View.GONE);
                }
*/

                String Time =fnGenerateDateTime();
                dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,1);
                if(sectionToShowHide<hmapSctnId_GrpId.size()-1)
                {
                    boolean isNextMoved=getSectionNextOrBack(0,sectionToShowHide );
                    if(isNextMoved)
                    {
                        if(ll_reject.getVisibility()==View.VISIBLE)
                        {
                            ll_reject.setVisibility(View.GONE);
                        }

                        if(ll_revisit.getVisibility()==View.VISIBLE)
                        {
                            ll_revisit.setVisibility(View.GONE);
                        }

                        if(ll_map.getVisibility()==View.VISIBLE)
                        {
                            ll_map.setVisibility(View.GONE);
                        }

                        sectionToShowHide++;
                        NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                        StoreName=recFragment.currentStoreName;
                        if(sectionToShowHide!=1 )
                        {
                            if(ll_back.getVisibility()==View.INVISIBLE)
                            {
                                ll_back.setVisibility(View.VISIBLE);
                            }


                        }
                    }

                }
                else if(sectionToShowHide>hmapSctnId_GrpId.size()-1)
                {

                    //NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                    NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                    if(recFragment!=null)
                    {
                        StoreName=recFragment.currentStoreName;
                        if(recFragment.validate() && recFragment.validateProperAddressDetails())// && recFragment.validatePaymentStageID()
                        {

                            IsStoreDataCompleteSaved=2;
                            if(flgSelfStore==1) {
                                flgApproveOrRejectOrNoActionOrReVisit=0;
                            }
                            else {
                                flgApproveOrRejectOrNoActionOrReVisit = 1;
                            }
                            showSubmitConfirm();
                        }
                    }

                 /*   if(recFragment.validate() && recFragment.validatePaymentStageID())
                    {

                       // getSectionNextOrBack(5,sectionToShowHide );


                    }*/




                }

                else
                {
                    boolean isNextMoved=getSectionNextOrBack(0,sectionToShowHide );
                    if(isNextMoved)
                    {
                        if(ll_map.getVisibility()==View.VISIBLE)
                        {
                            ll_map.setVisibility(View.GONE);
                        }
                        if(sectionToShowHide==hmapSctnId_GrpId.size()-1)
                        {
                            sectionToShowHide++;
                            img_next.setImageResource(R.drawable.done);
                            txt_Next.setText("Done");
                            if(FLAG_NEW_UPDATE.equals("UPDATE"))
                            {
                                if(flgSelfStore==1) {

                                }
                                else if(flgSelfStore==0) {

                                    if(flgOldNewStore==0) {
                                        if (ll_reject.getVisibility() == View.GONE) {
                                            ll_reject.setVisibility(View.VISIBLE);
                                        }

                                        if (ll_revisit.getVisibility() == View.GONE) {
                                            ll_revisit.setVisibility(View.VISIBLE);
                                        }
                                        txt_Next.setText("Approve");
                                    }
                                }

                            }


                        }
                        NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                        StoreName=recFragment.currentStoreName;
                        if(ll_back.getVisibility()==View.INVISIBLE)
                        {
                            ll_back.setVisibility(View.VISIBLE);
                        }


                    }
                }


            }
        });

        ll_back.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sectionToShowHide--;
                getSectionNextOrBack(1,sectionToShowHide );
                NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                StoreName=recFragment.currentStoreName;

                if(ll_reject.getVisibility()==View.VISIBLE)
                {
                    ll_reject.setVisibility(View.GONE);
                }

                if(ll_revisit.getVisibility()==View.VISIBLE)
                {
                    ll_revisit.setVisibility(View.GONE);
                }


                if(sectionToShowHide==1)
                {
                    if(ll_back.getVisibility()==View.VISIBLE)
                    {
                        ll_back.setVisibility(View.INVISIBLE);
                        if(ll_map.getVisibility()==View.GONE)
                        {
                            ll_map.setVisibility(View.VISIBLE);
                        }
                    }

                }
                if(sectionToShowHide<hmapSctnId_GrpId.size())
                {
                    img_next.setImageResource(R.drawable.next);
                    txt_Next.setText("Next");
                }
                String Time =fnGenerateDateTime();
                dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,1);
            }
        });
        ll_map.setVisibility(View.VISIBLE);
        manager= getFragmentManager();
        mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
        mapFrag.getMapAsync(AddNewStore_DynamicSectionWiseSO.this);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(mapFrag);


        if(flgCheckStoreAlreadySOLAtLong==1)
        {
            flgCheckStoreAlreadySOLAtLong=0;
           /* if(SOLattitudeFromLauncher.equals("NA"))
            {
                fnCreateLastKnownFinalLocation(SOLattitudeFromLauncher,SOLongitudeFromLauncher,AccuracyFromLauncher);
            }
            else
            {

            }*/
            if(!checkLastFinalLoctionIsRepeated(SOLattitudeFromLauncher,SOLongitudeFromLauncher,SOAccuracyFromLauncer))
            {

                fnCreateLastKnownFinalLocation(SOLattitudeFromLauncher,SOLongitudeFromLauncher,SOAccuracyFromLauncer);

            }
            else
            {
                if(StorelistActivity.modeOfVisit==1)
                {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddNewStore_DynamicSectionWiseSO.this);

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



            }
        }
        if(sectionToShowHide==hmapSctnId_GrpId.size())
        {

            img_next.setImageResource(R.drawable.done);
            txt_Next.setText("Done");
            if(FLAG_NEW_UPDATE.equals("UPDATE"))
            {
                if(flgSelfStore==1) {

                }
                else if(flgSelfStore==0) {

                    if(flgOldNewStore==0) {
                        if (ll_reject.getVisibility() == View.GONE) {
                            ll_reject.setVisibility(View.VISIBLE);
                        }

                        if (ll_revisit.getVisibility() == View.GONE) {
                            ll_revisit.setVisibility(View.VISIBLE);
                        }
                        txt_Next.setText("Approve");
                    }
                }

            }


        }

    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            battLevel = intent.getIntExtra("level", 0);

        }
    };


    public boolean getSectionNextOrBack(int isNextPressed,int sectionToShowOrHide)
    {
        boolean isNextMoved=false;
        NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            isNextMoved=recFragment.nextOrBackSection(isNextPressed,sectionToShowOrHide);
        }
        return isNextMoved;
    }
    private void addFragment() {
        newStore_Fragment=new NewStoreFormSO();
        manager=getFragmentManager();
        fragTrans=manager.beginTransaction();
        fragTrans.replace(R.id.fragmentForm, newStore_Fragment,"NewStoreFragment");

        fragTrans.commit();

    }
    private void fillHmapData() {
        //QuestID,QuestCode,QuestDesc,QuestType,AnsControlType,AnsControlInputTypeID,AnsControlInputTypeMaxLength,AnsMustRequiredFlg,QuestBundleFlg,ApplicationTypeID,Sequence,AnsControlInputTypeMinLength,AnsHint,QuestBundleGroupId
        //hmapQuesIdValues.put("1^2", "1^")

        hmapQuesIdValues=helperDb.fnGetQuestionMstr(StoreSectionCount);
        hmapQuesGropKeySection=helperDb.fnGetQuestionMstrKey();
        hmapGroupId_Desc=helperDb.getGroupDescription();
        hmapSctnId_GrpId=helperDb.fnGetGroupIdMpdWdSectionId(StoreSectionCount);
        hmapDpndtQustGrpId=helperDb.fnGetDependentQuestionMstr();
        hmapSection_key=helperDb.fnGetSection_Key();
        //   hmapOptionId_OptionValue=helperDb.fnGetOptionId_OptionValue();
        QuestIDForOutChannel=helperDb.fnGetQuestIDForOutChannelFromQuestionMstr();

    }
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Information");
        alertDialog.setIcon(R.drawable.error_info_ico);
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. \nPlease select all settings on the next page!");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWiseSO.this);
        alertDialogNoConn.setTitle("No Data Connection!");
        alertDialogNoConn.setMessage("Your device has no Data Connection! Please ensure Internet is accessible to Continue.");
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        /*if(isMyServiceRunning())
                  		{
                        stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
                  		}
                        finish();*/
                        //finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
        // alertDialogLowbatt.show();
    }

    public void showSubmitConfirm()
    {

        AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(AddNewStore_DynamicSectionWiseSO.this);
        alertDialogSubmitConfirm.setTitle("Information");
        alertDialogSubmitConfirm.setMessage("Do you want to Submit Store's Information? \n\n");
        alertDialogSubmitConfirm.setCancelable(false);

        alertDialogSubmitConfirm.setNeutralButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                sectionToShowHide--;
							/*if(isOnline())
							{*/
                dialog.dismiss();
                //submitButtonFunctionality();

			        		/*}
							else
							{dialog.dismiss();
			        			showNoConnAlert();
			        		}*/








                LinkedHashMap<String, String> hmapStoreQuestAnsNew = new LinkedHashMap<String, String>();
                LinkedHashMap<String, String> hmapStoreAddress = new LinkedHashMap<String, String>();
                NewStoreFormSO recFragment = (NewStoreFormSO) getFragmentManager().findFragmentByTag("NewStoreFragment");
                if (null != recFragment) {
                    recFragment.saveDynamicQuesAns(true);
                    StoreName=recFragment.currentStoreName;
                   // hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
                   // hmapStoreAddress=recFragment.hmapAddress;


                   // recFragment.saveDynamicQuesAns(true);
                        hmapStoreQuestAnsNew = recFragment.hmapAnsValues;
                        hmapStoreAddress=recFragment.hmapAddress;
                        fnlStoreType=recFragment.currentStoreType;
                        fnlOwnerName=recFragment.currentOwnerName;
                        fnlMobileNumber=recFragment.currentMobileNumber;
                        fnlAddressName=recFragment.currentAddressName;
                        fnSalesPersonName=recFragment.currentSalesPersonName;
                        fnSalesPersonContactNo=recFragment.currentSalesPersonContactNo;
                        fnStoreCatType=recFragment.currentStoreCatType;
                        if(!TextUtils.isEmpty(recFragment.distBId))
                        {
                            distID=recFragment.distBId;
                        }


                }
                // int ansValForBSgmntId = helperDb.fnGetAnsValID(hmapStoreQuestAnsNew.get(QuestIDForOutChannel));
                //  int BusinessSegmentID = helperDb.fnGetBusinessSegmentIDAgainstStoreType(ansValForBSgmntId);


                helperDb.fnsaveOutletQuestAnsMstrSectionWise(hmapStoreQuestAnsNew, 0, selStoreID,StoreCategoryType);

                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);


                String VisitEndFinalTS = df.format(datefromat);
                String VisitDate = dfDate.format(datefromat);
                // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
                int ApplicationID = CommonInfo.Application_TypeID;


                String allValuesOfPaymentStageID = helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);

                if(SOProviderFromLauncher.equals("Fused"))
                {
                    fnAddressFromLauncher=SOFusedAddressFromLauncher;
                }
                else if(SOProviderFromLauncher.equals("Gps"))
                {
                    fnAddressFromLauncher=SOGpsAddressFromLauncher;
                }
                else if(SOProviderFromLauncher.equals("Network"))
                {
                    fnAddressFromLauncher=SONetwAddressFromLauncher;
                }


                helperDb.saveLatLngToTxtFile(selStoreID,SOLattitudeFromLauncher, SOLongitudeFromLauncher,AccuracyFromLauncher,SOProviderFromLauncher,SOGpsLatFromLauncher,SOGpsLongFromLauncher,SOGpsAccuracyFromLauncher,SONetworkLatFromLauncher,SONetworkLongFromLauncher,SONetworkAccuracyFromLauncher,SOFusedLatFromLauncher,SOFusedLongFromLauncher,SOFusedAccuracyFromLauncher,3,"0",fnAddressFromLauncher,SOAllProvidersLocationFromLauncher,SOGpsAddressFromLauncher,SONetwAddressFromLauncher,SOFusedAddressFromLauncher,SOFusedLocationLatitudeWithFirstAttemptFromLauncher
                        ,SOFusedLocationLongitudeWithFirstAttemptFromLauncher,SOFusedLocationAccuracyWithFirstAttemptFromLauncher);
                helperDb.fnInsertOrUpdate_tblStoreDeatils(selStoreID, StoreName, SOLattitudeFromLauncher, SOLongitudeFromLauncher, VisitStartTimeOfNewStore, VisitEndFinalTS, SOProviderFromLauncher, SOAccuracyFromLauncer, "" + battLevel, IsStoreDataCompleteSaved, allValuesOfPaymentStageID, 1, hmapStoreAddress.get("0"), hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"), 3,flgApproveOrRejectOrNoActionOrReVisit,flgStoreVisitMode,StoreCategoryType,StoreSectionCount,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart,flgStoreOrder,flgUpdateSomeNewStoreFlags, hmapStoreAddress.get("4"), hmapStoreAddress.get("5"), hmapStoreAddress.get("6"), hmapStoreAddress.get("7"), hmapStoreAddress.get("8"), hmapStoreAddress.get("9"));

                //String StoreID,String ActualLatitude,String ActualLongitude,String VisitStartTS,String VisitEndTS,String LocProvider,String Accuracy,String BateryLeftStatus,int IsStoreDataCompleteSaved,String PaymentStage,int flgLocationTrackEnabled,String StoreAddress,String StoreCity,String StorePinCode,String StoreState,int Sstat)

                if (FLAG_NEW_UPDATE.equals("UPDATE")) {
                    int CurrentCoverageIDOfStore=helperDb.fetch_GetCoverageAreaIDAgsinstStoreID(selStoreID);
                    // helperDb.fnInsertOrUpdate_tblDSRSummaryDetials(CurrentCoverageIDOfStore,1);
                    helperDb.open();
                    helperDb.UpdateStoreReturnphotoFlagSO(selStoreID, StoreName);
                    helperDb.close();
                } else {
                    // helperDb.fnInsertOrUpdate_tblDSRSummaryDetials(0,0);
                    String storeCountDeatails=helperDb.getTodatAndTotalStores();
                    int  totaltarget = Integer.parseInt(storeCountDeatails.split(Pattern.quote("^"))[0]);
                    int todayTarget = Integer.parseInt(storeCountDeatails.split(Pattern.quote("^"))[1]);

                    helperDb.open();
                    helperDb.deletetblStoreCountDetails();
                    totaltarget=totaltarget+1;
                    todayTarget=todayTarget+1;
                    helperDb.saveTblStoreCountDetails(String.valueOf(totaltarget),String.valueOf(todayTarget));


                    //helperDb.saveTblPreAddedStores(selStoreID, StoreName, SOLattitudeFromLauncher, SOLongitudeFromLauncher, VisitDate, 1, 3);
                    helperDb.close();
                    if((!fnlStoreType.equals("0")) && fnlStoreType.contains("-"))
                    {
                        fnlStoreType=fnlStoreType.split(Pattern.quote("-"))[1];
                    }
                    helperDb.open();
                    helperDb.savetblStoreMain(CommonInfo.ActiveRouteSM,selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                            Integer.parseInt(fnlStoreType),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,3,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fnlAddressName,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation,"1","0","NA",0,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart,flgStoreOrder, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"),distID,fnlOwnerName,fnlMobileNumber,fnStoreCatType,flgRuleTaxVal,flgTransType,fnlMobileNumber,fnSalesPersonName,fnSalesPersonContactNo);

                    helperDb.saveSOAPdataStoreListDetailsInNewTable(selStoreID, hmapStoreAddress.get("2"), hmapStoreAddress.get("1"), hmapStoreAddress.get("3"),1);
                    helperDb.close();
                }


                   /* helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                    helperDb.savetblStoreMain("NA", selStoreID, StoreName, "NA", "NA", "NA", "NA", "NA", "NA", "NA", "0", StoreTypeTradeChannel,
                            BusinessSegmentID, 0, 0, 0, "NA", VisitStartTS, imei, "" + battLevel, 1, 1, String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy, "" + fnAccurateProvider, 0, fetchAddress, allValuesOfPaymentStageID, flgHasQuote, flgAllowQuotation, flgSubmitFromQuotation);
*/


               /* if (mIsServiceStarted) {
                    mIsServiceStarted = false;
                    stopService(new Intent(AddNewStore_DynamicSectionWise.this, LocationUpdateService.class));
                }*/

                // alertSubmit();



                try
                {
                    FullSyncDataNow task = new FullSyncDataNow(AddNewStore_DynamicSectionWiseSO.this);
                    task.execute();
                }
                catch (Exception e)
                {
                    // TODO Autouuid-generated catch block
                    e.printStackTrace();
                    //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
                }
										  /*  Intent ide=new Intent(AddNewStore_DynamicSectionWiseSO.this,StoreSelection.class);
											ide.putExtra("userDate", pickerDate);
											ide.putExtra("pickerDate", pickerDate);
											ide.putExtra("imei", imei);
											ide.putExtra("rID", rID);
											AddNewStore_DynamicSectionWise.this.startActivity(ide);
											finish();*/
                // finish();

              /*  if (isOnline()) {
									   *//*getSectionNextOrBack(5,sectionToShowHide );*//*
*//*
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }

                    timer = new Timer();
                    myTimerTask = new MyTimerTask();
                    timer.schedule(myTimerTask, 45000);

                    try {

                      *//**//*  task = new GetAddingStoreInfo(AddNewStore_DynamicSectionWise.this);
                        task.execute();*//**//*
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*//*
                } else {
                    // getSectionNextOrBack(3,sectionToShowHide );


                }*/


            }
        });

        alertDialogSubmitConfirm.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
					/*helperDb.open();
					//helperDb.deleteStoreFormtblStoreMain(StoreID);
					helperDb.close();*/
            }
        });

        alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

        AlertDialog alert = alertDialogSubmitConfirm.create();

        alert.show();

    }

    public String fnGenerateDateTime()
    {


        long syncTIMESTAMP = System.currentTimeMillis();
        Date datefromat = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String Time = df.format(datefromat);

        return  Time;

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
    public void onConnected(Bundle arg0) {
        // TODO Auto-generated method stub
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
        startLocationUpdates();
    }

    protected void startLocationUpdates()
    {
        try {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        catch (SecurityException e)
        {

        }
    }
    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }


    @Override
    public void onLocationChanged(Location args0) {
        // TODO Auto-generated method stub
        mCurrentLocation = args0;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();

    }


    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

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

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        if(FLAG_NEW_UPDATE.equals("NEW"))
        {
            if(!SOLattitudeFromLauncher.equals("NA") && !SOLattitudeFromLauncher.equals("0.0"))
            {
                googleMap.clear();
                try
                {
                    googleMap.setMyLocationEnabled(false);
                }
                catch(SecurityException e)
                {

                }


                MarkerOptions markerSO = new MarkerOptions().position(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)));
                markerSO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                //markerSO.title("Your Point");
                Marker SOlocationMarker=googleMap.addMarker(markerSO);
                SOlocationMarker.showInfoWindow();

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)), 15));


               /* MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)));
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                marker.title(StoreName +": By DSR");

                Marker locationMarker=googleMap.addMarker(marker);
                locationMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
                */
               /* if(CommonInfo.flgSOOnlineOffLine==1) {
                    if(!SOLattitudeFromLauncher.equals("NA"))
                    {
                      *//*  MarkerOptions markerSO = new MarkerOptions().position(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)));
                        markerSO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        markerSO.title("Your Point");
                        Marker SOlocationMarker=googleMap.addMarker(markerSO);
                        SOlocationMarker.showInfoWindow();

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)), 15));

*//*

                    }
                }
*/



            }

            else
            {
                if(refreshCount==2)
                {
                    txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                    btn_refresh.setVisibility(View.GONE);
                }
                try
                {
                    googleMap.setMyLocationEnabled(false);
                }
                catch(SecurityException e)
                {

                }
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker markerSO) {

                        markerSO.setTitle(StoreName);
                    }
                });

            }
        }
        else
        {
            if(!LattitudeFromLauncher.equals("NA") && !LattitudeFromLauncher.equals("0.0"))
            {
                googleMap.clear();
                try
                {
                    googleMap.setMyLocationEnabled(false);
                }
                catch(SecurityException e)
                {

                }
                MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)));
                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                String StoreCategoryType=dbengineSO.getChannelGroupIdOptIdForAddingStore();
                if(StoreCategoryType.equals("0-3-80"))
                {
                    marker.title(StoreName +": By Merchandiser");
                }
                else {
                    marker.title(StoreName + ": By DSR");
                }
                Marker locationMarker=googleMap.addMarker(marker);
                locationMarker.showInfoWindow();
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));

                if(CommonInfo.flgSOOnlineOffLine==1) {
                    if(!SOLattitudeFromLauncher.equals("NA"))
                    {
                        MarkerOptions markerSO = new MarkerOptions().position(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)));
                        markerSO.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                        markerSO.title("Your Point");
                        Marker SOlocationMarker=googleMap.addMarker(markerSO);
                        SOlocationMarker.showInfoWindow();

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(SOLattitudeFromLauncher), Double.parseDouble(SOLongitudeFromLauncher)), 15));



                    }
                }




            }

            else
            {
                if(refreshCount==2)
                {
                    txt_rfrshCmnt.setText(getString(R.string.loc_not_found));
                    btn_refresh.setVisibility(View.GONE);
                }
                try
                {
                    googleMap.setMyLocationEnabled(false);
                }
                catch(SecurityException e)
                {

                }
                googleMap.moveCamera(CameraUpdateFactory.zoomIn());
                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        marker.setTitle(StoreName);
                    }
                });

            }
        }


    }




    public  boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }

    public void showAlertForEveryOne(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(AddNewStore_DynamicSectionWiseSO.this);
        alertDialogNoConn.setTitle("Information");
        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton("Ok",new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                SimpleDateFormat dfDate = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);


                String VisitEndFinalTS = df.format(datefromat);
                if(!TextUtils.isEmpty(prvsStoreId.trim()))
                {
                    helperDb.updateMsgToRestartPopUpShown(prvsStoreId,VisitEndFinalTS);
                }
                else
                {

                    helperDb.updateMsgToRestartPopUpShown(selStoreID,VisitEndFinalTS);
                }
                finish();
                // finish();
            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }





    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }

    /*private class GetAddingStoreInfo extends AsyncTask<Void, Void, Void>
    {
        ServiceWorker getRouteservice = new ServiceWorker();


        public GetAddingStoreInfo(AddNewStore_DynamicSectionWise activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            long  syncTIMESTAMP = System.currentTimeMillis();
            Date dateobj = new Date(syncTIMESTAMP);
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            VisitEndTS = df.format(dateobj);

            System.out.println("Im  Testing H2");


            pDialogGetStores.setTitle("Please Wait");
            pDialogGetStores.setMessage("");
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
                for(int mm = 1; mm<2; mm++)
                {
                    if(mm==1)
                    {


                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date datefromat = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                        String VisitStartTS=df.format(datefromat);

                        int ApplicationID=CommonInfo.Application_TypeID;
										   *//*helperDb.inserttblOutletMstr(OutletID,VisitStartTS,String.valueOf(lastKnownLocLatitude),
												String.valueOf(lastKnownLocLongitude),"" + accuracy,locationProvider,battLevel + "%",StoreName,
												imei,0,3,ApplicationID);*//*

                        String NewStoreOutletPaymentDetails=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                        JSONArray ArrOutletPaymentDetails = new JSONArray();
                        JSONObject OutletPaymentDetails = new JSONObject();
                        OutletPaymentDetails.put("PymtStageId", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[0]);
                        OutletPaymentDetails.put("Percentage", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[1]);
                        OutletPaymentDetails.put("CreditDays", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[2]);
                        OutletPaymentDetails.put("CreditLimit", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[3]);
                        OutletPaymentDetails.put("PymtMode", NewStoreOutletPaymentDetails.trim().split(Pattern.quote("~"))[4]);
                        ArrOutletPaymentDetails.put(OutletPaymentDetails);







                        JSONArray ArrOutletGeneralInfoTable = new JSONArray();
                        JSONObject OutletInfo = new JSONObject();

                        OutletInfo.put("OutletID", selStoreID.trim());
                        OutletInfo.put("VisitStartTS", VisitStartTS.trim());
                        OutletInfo.put("VisitEndTS", VisitStartTS.trim());
                        OutletInfo.put("AppVersion", ApplicationID);
                        OutletInfo.put("ActualLatitude", String.valueOf(fnLati));
                        OutletInfo.put("ActualLongitude", String.valueOf(fnLongi));


                        OutletInfo.put("LocProvider", fnAccurateProvider);
                        OutletInfo.put("Accuracy", fnAccuracy);
                        OutletInfo.put("BateryLeftStatus", battLevel);
                        OutletInfo.put("StoreName", StoreName.trim());
                        OutletInfo.put("imei", imei.trim());
                        OutletInfo.put("ISNewStore", 1);
                        OutletInfo.put("IsNewStoreDataCompleteSaved", 0);

                        OutletInfo.put("Sstat", 0);
                        OutletInfo.put("StoreMapAddress", fetchAddress);


                        ArrOutletGeneralInfoTable.put(OutletInfo);

                        JSONArray ArrAnsAndQuestionTable = new JSONArray();

                        LinkedHashMap<String, String> hmapStoreQuestAnsNew=new LinkedHashMap<String, String>();


                        NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                        if(null != recFragment)
                        {
                            hmapStoreQuestAnsNew=recFragment.hmapAnsValues;

                        }

                        try
                        {
                            if(hmapStoreQuestAnsNew.size()>0)
                            {
                                for(Entry<String, String> entry:hmapStoreQuestAnsNew.entrySet())
                                {
                                    JSONObject post_dict = new JSONObject();
                                    String keyhmap=entry.getKey();
                                    String valuehmap=entry.getValue();
                                    post_dict.put("OutletID" , selStoreID);

											        	 *//*if(Integer.parseInt(outletsDetailValue.get(1))==15)
											        	 {
											        		 StoreTypeTradeChannel= helperDb.fnGetAnsValFromOptionID(Integer.parseInt(outletsDetailValue.get(3)));//Integer.parseInt(outletsDetailValue.get(3));
											        	 }*//*
                                    post_dict.put("QuestionId" , keyhmap.split(Pattern.quote("^"))[0]);
                                    post_dict.put("AnsCtronlType", keyhmap.split(Pattern.quote("^"))[1]);
                                    post_dict.put("QuestGroupId", keyhmap.split(Pattern.quote("^"))[2]);
                                    post_dict.put("Value", valuehmap);

                                    *//*String optionValue="0";
                                    if(valuehmap.trim().indexOf("^")!=-1)
                                    {
                                        String[] diffrentOptionIdSelected=valuehmap.trim().split(Pattern.quote("^"));
                                        for(int j=0;j<diffrentOptionIdSelected.length;j++)
                                        {
                                            String curntOptionID=diffrentOptionIdSelected[j];
                                            String filterOptID="0";
                                            if(curntOptionID.trim().indexOf("~")!=-1)
                                            {
                                                filterOptID=curntOptionID.trim().split(Pattern.quote("~"))[0];
                                            }
                                            else
                                            {
                                                filterOptID=curntOptionID;
                                            }

                                            if(hmapOptionId_OptionValue.containsKey(keyhmap.split(Pattern.quote("^"))[0]+"_"+filterOptID))
                                            {
                                                if(j==0)
                                                {
                                                    optionValue=hmapOptionId_OptionValue.get(keyhmap.split(Pattern.quote("^"))[0]+"_"+filterOptID);
                                                }
                                                else
                                                {
                                                    optionValue=optionValue+"^"+hmapOptionId_OptionValue.get(keyhmap.split(Pattern.quote("^"))[0]+"_"+filterOptID);
                                                }
                                            }

                                        }
                                    }
                                    else
                                    {
                                        if(hmapOptionId_OptionValue.containsKey(keyhmap.split(Pattern.quote("^"))[0]+"_"+valuehmap.trim()))
                                        {
                                            optionValue=hmapOptionId_OptionValue.get(keyhmap.split(Pattern.quote("^"))[0]+"_"+valuehmap.trim());
                                        }
                                    }

                                    post_dict.put("optionValue", optionValue);*//*

                                    ArrAnsAndQuestionTable.put(post_dict);
                                }
												  *//*for(int i=0;i<hmapStoreQuestAnsNew.size();i++)
											         {
											        	 ArrayList<String> outletsDetailValue=hmapStoreQuestAnsNew.get(i);
											        	 JSONObject post_dict = new JSONObject();

													        	 if(i==0)
													        	 {
											        		     post_dict.put("OutletID" , outletsDetailValue.get(0));
											        	         }
													        	 //StoreType
													        	 if(Integer.parseInt(outletsDetailValue.get(1))==15)
													        	 {
													        		 StoreTypeTradeChannel= helperDb.fnGetAnsValFromOptionID(Integer.parseInt(outletsDetailValue.get(3)));//Integer.parseInt(outletsDetailValue.get(3));
													        	 }
											        		     post_dict.put("QuestionId" , outletsDetailValue.get(1));
													             post_dict.put("AnsCtronlType", outletsDetailValue.get(2));
													             post_dict.put("Value", outletsDetailValue.get(3));
													             ArrAnsAndQuestionTable.put(post_dict);


											         }*//*
                            }

                            // ArrAnsAndQuestionTable.put(ArrOutletGeneralInfoTable);
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }


                       // getRouteservice = getRouteservice.getNewStoreInfoDynamic(getApplicationContext(),ArrAnsAndQuestionTable,ArrOutletGeneralInfoTable,imei,ArrOutletPaymentDetails);

                        CustomStringForServiceWorker="";
                        if(!getRouteservice.director.toString().trim().equals("1"))
                        {
                            if(chkFlgForErrorToCloseApp==0)
                            {
                                System.out.println("Im  Testing H4");
                                chkFlgForErrorToCloseApp=1;

                            }

                        }
                    }


                }


            }
            catch (Exception e)
            {

            }
            finally
            {

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


            System.out.println("Im  Testing H5 : "+chkFlgForErrorToCloseApp);

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            if(chkFlgForErrorToCloseApp==1)
            {
                chkFlgForErrorToCloseApp=0;


                // helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                if (timer!=null)
                {
                    timer.cancel();
                    timer = null;
                }
                getSectionNextOrBack(3,sectionToShowHide );
                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                String VisitStartTS=df.format(datefromat);
                int ApplicationID=CommonInfo.Application_TypeID;
							   *//*if(selStoreID.equals("0"))
							   {*//*

                helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
                String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                helperDb.open();
               *//* helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                        Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                helperDb.close();
                // }


								*//*Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
								ide.putExtra("userDate", pickerDate);
								ide.putExtra("pickerDate", pickerDate);
								ide.putExtra("imei", imei);
								ide.putExtra("rID", rID);
								AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                finish();

            }
            else
            {
						*//* if(selStoreID=="0")
							 {*//*
                if(isOnline())

                {
                    try
                    {
                        checkSecondTaskStatus=1;
                        taskUpdateScheme = new GetUpdateSchemeForNewStore(AddNewStore_DynamicSectionWise.this);

                        taskUpdateScheme.execute();

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {

                    //helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                    getSectionNextOrBack(3,sectionToShowHide );
                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date datefromat = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                    String VisitStartTS=df.format(datefromat);

                    int ApplicationID=CommonInfo.Application_TypeID;
                    String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                    helperDb.open();
                   *//* helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                    helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                            Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                    helperDb.close();
								*//*
									 Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
								ide.putExtra("userDate", pickerDate);
								ide.putExtra("pickerDate", pickerDate);
								ide.putExtra("imei", imei);
								ide.putExtra("rID", rID);
								AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                    finish();

                }


							*//* }

						 else
						 {

							 Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
						ide.putExtra("userDate", pickerDate);
						ide.putExtra("pickerDate", pickerDate);
						ide.putExtra("imei", imei);
						ide.putExtra("rID", rID);
						AddNewStore_DynamicSectionWise.this.startActivity(ide);
						finish();

						 }*//*

            }




        }
    }*/


   /* private class GetUpdateSchemeForNewStore extends AsyncTask<Void, Void, Void>
    {
        ServiceWorker newservice = new ServiceWorker();


        public GetUpdateSchemeForNewStore(AddNewStore_DynamicSectionWise activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialogGetStores.setTitle("Please Wait");
            pDialogGetStores.setMessage("");
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
                for(int mm = 1; mm<2; mm++)
                {
                    if(mm==1)
                    {

                        Date currDateNew = new Date();
                        SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

                        String currSysDateNew = currDateFormatNew.format(currDateNew).toString();

                      //  newservice = newservice.getAllNewSchemeStructure(getApplicationContext(), currSysDateNew, imei, rID);
                        if(!newservice.director.toString().trim().equals("1"))
                        {
                            if(chkFlgForErrorToCloseApp==0)
                            {
                                chkFlgForErrorToCloseApp=1;

                            }

                        }
                    }
									*//*if(mm==2)
									{

										Date currDateNew = new Date();
										SimpleDateFormat currDateFormatNew = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);

										String currSysDateNew = currDateFormatNew.format(currDateNew).toString();

										newservice = newservice.fnGetStoreListWithPaymentAddressMR(getApplicationContext(), currSysDateNew, imei, rID);
										if(!newservice.director.toString().trim().equals("1"))
										{
											if(chkFlgForErrorToCloseApp==0)
											{
												chkFlgForErrorToCloseApp=1;

											}

										}
									}*//*
                }


            }
            catch (Exception e)
            {

            }
            finally
            {

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
            if (timer!=null)
            {
                timer.cancel();
                timer = null;
            }

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }

            if(chkFlgForErrorToCloseApp==1)
            {
                chkFlgForErrorToCloseApp=0;

                if(ServiceWorkerDataComingFlag==0)
                {
                    // helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                    getSectionNextOrBack(3,sectionToShowHide );
                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date datefromat = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                    String VisitStartTS=df.format(datefromat);
                    // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
                    int ApplicationID=CommonInfo.Application_TypeID;
                    String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                    helperDb.open();
                *//* //   helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
                  //  helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                            Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                    helperDb.close();
                }
                else
                {
                    StringTokenizer tokens = new StringTokenizer(String.valueOf(ServiceWorkerResultSet), "^");

                    String StoreName= tokens.nextToken().toString().trim();
                    String StoreType= tokens.nextToken().toString().trim();
                    String StoreLatitude= tokens.nextToken().toString().trim();
                    String StoreLongitude= tokens.nextToken().toString().trim();
                    String LastVisitDate= tokens.nextToken().toString().trim();
                    String LastTransactionDate= tokens.nextToken().toString().trim();
                    String dateVAL= tokens.nextToken().toString().trim();
                    String AutoIdStore= tokens.nextToken().toString().trim();
                    String Sstat= tokens.nextToken().toString().trim();
                    String IsClose= tokens.nextToken().toString().trim();
                    String IsNextDat= tokens.nextToken().toString().trim();
                    String RouteID= tokens.nextToken().toString().trim();
                    int flgHasQuoteNew= Integer.parseInt(tokens.nextToken().toString().trim());
                    int flgAllowQuotationNew= Integer.parseInt(tokens.nextToken().toString().trim());
                    int flgSubmitFromQuotatioNew=Integer.parseInt(tokens.nextToken().toString().trim());
                    helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
                    String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                    helperDb.open();
                   *//* helperDb.fndeleteNewStoreSalesQuotePaymentDetails(selStoreID);
                    helperDb.saveSOAPdataStoreList(ServiceWorkerStoreID,StoreName,StoreType,Double.parseDouble(StoreLatitude),Double.parseDouble(StoreLongitude),LastVisitDate,LastTransactionDate,
                            dateVAL.toString().trim(),Integer.parseInt(AutoIdStore), Integer.parseInt(Sstat),Integer.parseInt(IsClose),Integer.parseInt(IsNextDat),Integer.parseInt(RouteID),StoreTypeTradeChannel,fetchAddress,allValuesOfPaymentStageID,flgHasQuoteNew,flgAllowQuotationNew,flgSubmitFromQuotatioNew);
                   *//*

                    helperDb.close();
                }




						   *//* Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
							ide.putExtra("userDate", pickerDate);
							ide.putExtra("pickerDate", pickerDate);
							ide.putExtra("imei", imei);
							ide.putExtra("rID", rID);
							AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                finish();



            }
            else
            { if(ServiceWorkerDataComingFlag==0)
            {
                // helperDb.saveOutletQuestAnsMstr(saveOutletQuestAnsMstrVal);
                getSectionNextOrBack(3,sectionToShowHide );
                long syncTIMESTAMP = System.currentTimeMillis();
                Date datefromat = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");


                String VisitStartTS=df.format(datefromat);
                // int DatabaseVersion=CommonInfo.DATABASE_VERSIONID;
                int ApplicationID=CommonInfo.Application_TypeID;
                String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                helperDb.open();
              *//* // helperDb.deletetblstoreMstrOnStoreIDBasis(selStoreID);
               // helperDb.savetblStoreMain("NA",selStoreID,StoreName,"NA","NA","NA","NA","NA","NA","NA","0",StoreTypeTradeChannel,
                        Integer.parseInt("1"),0,0, 0, "NA",VisitStartTS,imei,""+battLevel,1,1,String.valueOf(fnLati),String.valueOf(fnLongi),"" + fnAccuracy,"" + fnAccurateProvider,0,fetchAddress,allValuesOfPaymentStageID,flgHasQuote,flgAllowQuotation,flgSubmitFromQuotation);
*//*
                helperDb.close();
            }
            else
            {
                StringTokenizer tokens = new StringTokenizer(String.valueOf(ServiceWorkerResultSet), "^");

                String StoreName= tokens.nextToken().toString().trim();
                String StoreType= tokens.nextToken().toString().trim();
                String StoreLatitude= tokens.nextToken().toString().trim();
                String StoreLongitude= tokens.nextToken().toString().trim();
                String LastVisitDate= tokens.nextToken().toString().trim();
                String LastTransactionDate= tokens.nextToken().toString().trim();
                String dateVAL= tokens.nextToken().toString().trim();
                String AutoIdStore= tokens.nextToken().toString().trim();
                String Sstat= tokens.nextToken().toString().trim();
                String IsClose= tokens.nextToken().toString().trim();
                String IsNextDat= tokens.nextToken().toString().trim();
                String RouteID= tokens.nextToken().toString().trim();
                int flgHasQuoteNew= Integer.parseInt(tokens.nextToken().toString().trim());
                int flgAllowQuotationNew= Integer.parseInt(tokens.nextToken().toString().trim());
                int flgSubmitFromQuotatioNew=Integer.parseInt(tokens.nextToken().toString().trim());
                helperDb.fnDeletesaveNewOutletFromOutletMstr(selStoreID);
                String allValuesOfPaymentStageID=helperDb.fngettblNewStoreSalesQuotePaymentDetails(selStoreID);
                helperDb.open();
               *//* helperDb.fndeleteNewStoreSalesQuotePaymentDetails(selStoreID);
                helperDb.saveSOAPdataStoreList(ServiceWorkerStoreID,StoreName,StoreType,Double.parseDouble(StoreLatitude),Double.parseDouble(StoreLongitude),LastVisitDate,LastTransactionDate,
                        dateVAL.toString().trim(),Integer.parseInt(AutoIdStore), Integer.parseInt(Sstat),Integer.parseInt(IsClose),Integer.parseInt(IsNextDat),Integer.parseInt(RouteID),StoreTypeTradeChannel,fetchAddress,allValuesOfPaymentStageID,flgHasQuoteNew,flgAllowQuotationNew,flgSubmitFromQuotatioNew);
               *//*

                helperDb.close();
            }




				  *//*  Intent ide=new Intent(AddNewStore_DynamicSectionWise.this,StoreSelection.class);
					ide.putExtra("userDate", pickerDate);
					ide.putExtra("pickerDate", pickerDate);
					ide.putExtra("imei", imei);
					ide.putExtra("rID", rID);
					AddNewStore_DynamicSectionWise.this.startActivity(ide);*//*
                finish();
            }
        }

    }*/

    /* private void initializaeMap(GoogleMap googleMap) {


         googleMap.setMyLocationEnabled(true);
         MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(StoreName);
         Marker locationMarker=googleMap.addMarker(marker);
         locationMarker.showInfoWindow();
         googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15));

         //}
                 *//*else
			    {
			     ft.hide(mapFrag);
			       txt_internetProb.setVisibility(View.VISIBLE);
			    }*//*
        //mapFrag.getView().setVisibility(View.GONE);

        ft.commit();
    }*/
    public String genTempID()
    {
        //store ID generation <x>

        String cxz;
        cxz = UUID.randomUUID().toString();
						/*cxz.split("^([^-]*,[^-]*,[^-]*,[^-]*),(.*)$");*/
        //System.out.println("cxz (BEFORE split): "+cxz);
        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        //System.out.println("cxz (AFTER split): "+cxz);

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tManager.getDeviceId();
        String IMEIid =  imei.substring(9);

        cxz = IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


        return cxz;
        //-_
    }



    public void fetchAddress()
    {


        /*Intent intent = new Intent(this, GeocodeAddressIntentService.class);
        intent.putExtra("Reciever", mResultReceiver);
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        intent.putExtra("Location", location);
        startService(intent);*/
    }

    @Override
    public void selectedCityState(String selectedCategory, Dialog dialog, int flgCityState) {
        NewStoreFormSO recFragment = (NewStoreFormSO) getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            recFragment.selectedCityState(selectedCategory,dialog,flgCityState);
        }
    }

    class AddressResultReceiver extends ResultReceiver
    {

        public AddressResultReceiver(Handler handler) {
            super(handler);
            // TODO Auto-generated constructor stub
        }
        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData) {
            // TODO Auto-generated method stub
            if (resultCode == 1)
            {
                final Address address = resultData.getParcelable("RESULT_ADDRESS");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        String address1 = resultData.getString("RESULT_DATA_KEY");
                        fetchAddress=address1;
                        txtAddress.setText(address1);
                    }
                });
            }
        }
    }

    @Override
    public void selectedOption(String optId, String optionVal,
                               EditText txtVw, ListView listViewOption, String tagVal,
                               Dialog dialog, TextView textView,ArrayList<String> listStoreIDOrigin) {

        NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            recFragment.selectedOption( optId,  optionVal,
                    txtVw,  listViewOption,  tagVal,
                    dialog,  textView,listStoreIDOrigin);
        }

    }
    @Override
    public void selectedStoreMultiple(String optId, String optionVal,
                                      EditText txtVw, ListView listViewOption, String tagVal,
                                      Dialog dialog, TextView textView, LinearLayout ll_SlctdOpt,
                                      ArrayList<String> listSelectedOpt,
                                      ArrayList<String> listSelectedStoreID,ArrayList<String> listSelectedStoreOrigin) {

        NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
        if(null != recFragment)
        {
            recFragment.selectedStoreMultiple( optId,  optionVal,
                    txtVw,  listViewOption,  tagVal,
                    dialog,  textView,  ll_SlctdOpt,
                    listSelectedOpt,
                    listSelectedStoreID,listSelectedStoreOrigin);
        }

    }


    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub

           /* intent.putExtra("Latitude", mCurrentLocation.getLatitude());
            intent.putExtra("Longitude", mCurrentLocation.getLatitude());
            intent.putExtra("Accuracy", mCurrentLocation.getAccuracy());*/
            double lat = arg1.getDoubleExtra("Latitude", 0.0);
            double lng=arg1.getDoubleExtra("Longitude", 0.0);
            float acc=arg1.getFloatExtra("Accuracy",0F);
            if(lat!=0.0)
            {

                SOLattitudeFromLauncher=String.valueOf(lat);
                SOLongitudeFromLauncher=String.valueOf(lng);
                SOAccuracyFromLauncer=String.valueOf(acc);
                latitudeToSave=SOLattitudeFromLauncher;
                longitudeToSave=SOLongitudeFromLauncher;
                accuracyToSave=SOAccuracyFromLauncer;
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(
                        R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(AddNewStore_DynamicSectionWiseSO.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);


            }



        }

    }


    public void alertSubmit()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(
                AddNewStore_DynamicSectionWiseSO.this).create();

        // Setting Dialog Title
        alertDialog.setTitle("Data Sent");

        // Setting Dialog Message
        alertDialog.setMessage("Data has been successfully submitted");

        // Setting Icon to Dialog
        //  alertDialog.setIcon(R.drawable.tick);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                Intent intent = new Intent(AddNewStore_DynamicSectionWiseSO.this, LauncherActivity.class);
                intent.putExtra("FROM", "AddNewStore_DynamicSectionWiseSO");
                startActivity(intent);
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void locationRetrievingAndDistanceCalculating()
    {

        appLocationService = new AppLocationService();

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");
        wl.acquire();


        pDialog2STANDBY = ProgressDialog.show(AddNewStore_DynamicSectionWiseSO.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.rtrvng_loc), true);
        pDialog2STANDBY.setIndeterminate(true);

        pDialog2STANDBY.setCancelable(false);
        pDialog2STANDBY.show();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            mGoogleApiClient = new GoogleApiClient.Builder(AddNewStore_DynamicSectionWiseSO.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(AddNewStore_DynamicSectionWiseSO.this)
                    .addOnConnectionFailedListener(AddNewStore_DynamicSectionWiseSO.this)
                    .build();
            mGoogleApiClient.connect();
        }
        //startService(new Intent(DynamicActivity.this, AppLocationService.class));
        startService(new Intent(AddNewStore_DynamicSectionWiseSO.this, AppLocationService.class));
        Location nwLocation = appLocationService.getLocation(locationManager, LocationManager.GPS_PROVIDER, location);
        Location gpsLocation = appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER, location);
        countDownTimer = new CoundownClass(startTime, interval);
        countDownTimer.start();

    }

    public class CoundownClass extends CountDownTimer {

        public CoundownClass(long startTime, long interval) {
            super(startTime, interval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish()
        {
            AllProvidersLocation="";
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
            // fnAccurateProvider="";

            checkHighAccuracyLocationMode(AddNewStore_DynamicSectionWiseSO.this);

            if(fnAccurateProvider.equals(""))
            {

                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }

                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(
                        R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(AddNewStore_DynamicSectionWiseSO.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);
            }
            else
            {
                if(isOnline())
                {
                    getAddressForDynamic(fnLati,fnLongi);
                }
                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }
                if(!GpsLat.equals("0") )
                {
                    fnCreateLastKnownGPSLoction(GpsLat,GpsLong,GpsAccuracy);
                }
                SOLattitudeFromLauncher=fnLati;
                SOLongitudeFromLauncher=fnLongi;
                SOAccuracyFromLauncer=String.valueOf(fnAccuracy);
                latitudeToSave=fnLati;
                longitudeToSave=fnLongi;

                SOProviderFromLauncher = fnAccurateProvider;
                SOGpsLatFromLauncher = GpsLat;
                SOGpsLongFromLauncher = GpsLong;
                SOGpsAccuracyFromLauncher = GpsAccuracy;
                SONetworkLatFromLauncher = NetwLat;
                SONetworkLongFromLauncher = NetwLong;
                SONetworkAccuracyFromLauncher = NetwAccuracy;
                SOFusedLatFromLauncher = FusedLat;
                SOFusedLongFromLauncher = FusedLong;
                SOFusedAccuracyFromLauncher =FusedAccuracy;

                SOAllProvidersLocationFromLauncher = AllProvidersLocation;
                SOGpsAddressFromLauncher = GpsAddress;
                SONetwAddressFromLauncher = NetwAddress;
                SOFusedAddressFromLauncher = FusedAddress;
                SOFusedLocationLatitudeWithFirstAttemptFromLauncher = FusedLocationLatitudeWithFirstAttempt;
                SOFusedLocationLongitudeWithFirstAttemptFromLauncher = FusedLocationLongitudeWithFirstAttempt;
                SOFusedLocationAccuracyWithFirstAttemptFromLauncher = FusedLocationAccuracyWithFirstAttempt;





                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(
                        R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(AddNewStore_DynamicSectionWiseSO.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);

                if(!checkLastFinalLoctionIsRepeated(SOLattitudeFromLauncher,SOLongitudeFromLauncher,SOAccuracyFromLauncer))
                {
                    fnCreateLastKnownFinalLocation(SOLattitudeFromLauncher,SOLongitudeFromLauncher,SOAccuracyFromLauncer);
                }
                else
                {
                    if(!TextUtils.isEmpty(prvsStoreId))
                    {
                        if(helperDb.isPrvsStoreMsgShownAndRestrtDone(prvsStoreId))
                        {

                            showAlertForEveryOne("Location is same,Please Restart your phone after clicking Ok button.");
                        }
                        else
                        {

                        }
                    }

                    else
                    {
                        //String prvsStoreID,String CrntStoreID,String isSavedOrSubmittedStore,String MsgToRestartPopUpShown,String isRestartDoneByDSR,String Sstat)
                        helperDb.insertRestartStoreInfo(selStoreID,selStoreID,"0","1","0",0,VisitStartTS);
                        showAlertForEveryOne("Location is same,Please Restart your phone after clicking Ok button.");
                    }



                }

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
            // TODO Auto-generated method stub

        }}

    private class FullSyncDataNow extends AsyncTask<Void, Void, Void>
    {


        ProgressDialog pDialogGetStores;
        public FullSyncDataNow(AddNewStore_DynamicSectionWiseSO activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();


            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage("Submitting  Details...");
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


            String Time=fnGenerateDateTime();
            dbengineSO.fnVisitStartOrEndTime(selStoreID,Time,1);

            dbengineSO.open();

            //dbengineSO.UpdateStoreFlag(selStoreID, 3);
            dbengineSO.UpdateStoreFlagSO(selStoreID, 3);
            dbengineSO.UpdateStoreImageTableFlag(selStoreID, 3);

            dbengineSO.close();


            SimpleDateFormat df1 = new SimpleDateFormat("dd.MMM.yyyy.HH.mm.ss", Locale.ENGLISH);

            newfullFileName=imei+"."+ df1.format(dateobj);

            LinkedHashMap<String,String> hmapstlist=new LinkedHashMap<String, String>();

            hmapstlist=dbengineSO.fnGetStList();

            try {


                File LTFoodxmlFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

                if (!LTFoodxmlFolder.exists())
                {
                    LTFoodxmlFolder.mkdirs();

                }


                DA.open();
                DA.export(CommonInfo.DATABASE_NAME, newfullFileName);
                DA.close();



                dbengineSO.savetbl_XMLfiles(newfullFileName, "3","4");
                dbengineSO.open();

                dbengineSO.UpdateStoreFlagSO(selStoreID, 4);
                dbengineSO.UpdateStoreImageTableFlag(selStoreID.trim(), 5);

                dbengineSO.close();
                dbengineSO.fnDeleteUnNeededRecordsFromOtheTables(selStoreID);
                //   dbengineSO.fndeleteSbumittedStoreList(4);
            } catch (Exception e)
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

                if(isOnline())
                {
                    if(activityFrom.equals("StoreSelection")){

                        Intent intent = new Intent(AddNewStore_DynamicSectionWiseSO.this, StoreSelection.class);
                        intent.putExtra("imei", AddNewStore_DynamicSectionWiseSO.imei);
                        intent.putExtra("userDate", AddNewStore_DynamicSectionWiseSO.date_value);
                        intent.putExtra("pickerDate", AddNewStore_DynamicSectionWiseSO.pickerDate);
                        intent.putExtra("rID", RouteNodeID);
                        startActivity(intent);
                        finish();

                    }
                    else {
                        Intent syncIntent = new Intent(AddNewStore_DynamicSectionWiseSO.this, SyncMasterSO.class);
                        syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");
                        syncIntent.putExtra("OrigZipFileName", newfullFileName);
                        syncIntent.putExtra("whereTo", "Regular");
                        startActivity(syncIntent);
                        finish();
                    }

                }
                else
                {


                    /*Intent intent = new Intent(AddNewStore_DynamicSectionWiseSO.this, StorelistActivity.class);
                    intent.putExtra("FROM", "AddNewStore_DynamicSectionWiseSO");
                    startActivity(intent);
                    finish();*/

                    if(activityFrom.equals("StoreSelection")){

                        Intent intent = new Intent(AddNewStore_DynamicSectionWiseSO.this, StoreSelection.class);
                        intent.putExtra("imei", AddNewStore_DynamicSectionWiseSO.imei);
                        intent.putExtra("userDate", AddNewStore_DynamicSectionWiseSO.date_value);
                        intent.putExtra("pickerDate", AddNewStore_DynamicSectionWiseSO.pickerDate);
                        intent.putExtra("rID", RouteNodeID);
                        startActivity(intent);
                        finish();

                    }
                    else if(activityFrom.equals("AllButtonActivity")){
                   /* Intent intent = new Intent(getActivity(), AllButtonActivity.class);
                    intent.putExtra("imei", AddNewStore_DynamicSectionWiseSO.imei);
                    intent.putExtra("userDate", AddNewStore_DynamicSectionWiseSO.date_value);
                    intent.putExtra("pickerDate", AddNewStore_DynamicSectionWiseSO.pickerDate);
                    intent.putExtra("rID", getActiveRouteId);
                    getActivity().startActivity(intent);
                    getActivity().finish();*/
                        Intent intent =new Intent(AddNewStore_DynamicSectionWiseSO.this,StorelistActivity.class);
                        intent.putExtra("activityFrom", "AllButtonActivity");
                        startActivity(intent);
                        finish();

                    }

                }


            } catch (Exception e) {

                e.printStackTrace();
            }


        }
    }

    public String getAddressForDynamic(String latti,String longi){


        String areaToMerge="NA";
        Address addressTemp=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);

           /* AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];*/
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(latti), Double.parseDouble(longi), 1);
            if (addresses != null && addresses.size() > 0){
                if(addresses.get(0).getAddressLine(1)!=null){
                    addr=addresses.get(0).getAddressLine(1);
                    address=addr;
                }

                if(addresses.get(0).getLocality()!=null){
                    city=addresses.get(0).getLocality();
                    this.city=city;
                }

                if(addresses.get(0).getAdminArea()!=null){
                    state=addresses.get(0).getAdminArea();
                    this.state=state;
                }


                for(int i=0 ;i<addresses.size();i++)
                {
                    addressTemp = addresses.get(i);
                    if(addressTemp.getPostalCode()!=null)
                    {
                        zipcode=addressTemp.getPostalCode();
                        this.pincode=zipcode;
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

                NewStoreFormSO recFragment = (NewStoreFormSO)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(null != recFragment)
                {
                    recFragment.setFreshAddress();
                }
            }
            else{FULLADDRESS3.append("NA");}
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            return fullAddress=addr+"^"+zipcode+"^"+city+"^"+state;
        }
    }

    public String getAddressNewWay(String ZeroIndexAddress,String city,String State,String pincode,String country){
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
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;

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
            String fpath = Environment.getExternalStorageDirectory()+"/"+ CommonInfo.FinalLatLngJsonFile+"/"+txtFileNamenew;


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
    @Override
    protected void onResume() {
        super.onResume();
        boolean isGPSokCheckInResume = false;
        boolean isNWokCheckInResume=false;
        isGPSokCheckInResume = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWokCheckInResume = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(CommonInfo.flgSOOnlineOffLine==1)
        {
            if(!isGPSokCheckInResume && !isNWokCheckInResume)
            {
                try
                {
                    showSettingsAlert();
                }
                catch(Exception e)
                {

                }
                isGPSokCheckInResume = false;
                isNWokCheckInResume=false;
            }
            else
            {


                if(countSubmitClicked==1)
                {

                    locationRetrievingAndDistanceCalculating();
                    countSubmitClicked++;


                }



            }
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
   public String getAddressOfProviders(String latti, String longi){

       StringBuilder FULLADDRESS2 =new StringBuilder();
       Geocoder geocoder;
       List<Address> addresses;
       geocoder = new Geocoder(AddNewStore_DynamicSectionWiseSO.this, Locale.ENGLISH);



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
}

