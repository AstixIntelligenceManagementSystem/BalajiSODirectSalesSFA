package project.astix.com.balajisosfadirectsales;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import java.util.UUID;
import java.util.regex.Pattern;


public class DistributorCheckInFirstActivity extends BaseActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,OnMapReadyCallback
{
    LinkedHashMap<String,String> hmapDistributorListWithRemapFlg=new LinkedHashMap<String,String>();
    LinkedHashMap<String,String> hmapDistributorListWithNodeIDType=new LinkedHashMap<String,String>();
    String gblSelectedDistributor="Select Distributor";
    String UniqueDistribtrMapId;
    public LocationManager locationManager;
    public AppLocationService appLocationService;
    public PowerManager pm;
    public	 PowerManager.WakeLock wl;
    public ProgressDialog pDialog2STANDBY;

    public CoundownClass countDownTimer;
    DatabaseAssistantDistributorMap DA=new DatabaseAssistantDistributorMap(this);
    public String newfullFileName;
    private final long startTime = 15000;
    private final long interval = 200;

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;

    LocationRequest mLocationRequest;
    public Location location;

    public String FusedLocationLatitudeWithFirstAttempt="0";
    public String FusedLocationLongitudeWithFirstAttempt="0";
    public String FusedLocationAccuracyWithFirstAttempt="0";
    public String AllProvidersLocation="";
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

    String fusedData;
    public boolean isGPSEnabled = false;
    public   boolean isNetworkEnabled = false;

    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;

    PRJDatabase helperDb;

    public String AccuracyFromLauncher="NA";
    String AddressFromLauncher="NA";
    String CityFromLauncher="NA";
    String PincodeFromLauncher="NA";
    String StateFromLauncher="NA";

    public String ProviderFromLauncher="NA";
    public String GpsLatFromLauncher="NA";
    public String GpsLongFromLauncher="NA";
    public String GpsAccuracyFromLauncher="NA";
    public String NetworkLatFromLauncher="NA";
    public String NetworkLongFromLauncher="NA";
    public String NetworkAccuracyFromLauncher="NA";
    public String FusedLatFromLauncher="NA";
    public String FusedLongFromLauncher="NA";
    public String FusedAccuracyFromLauncher="NA";

    public String fnAddressFromLauncher="NA";
    public String AllProvidersLocationFromLauncher="NA";
    public String GpsAddressFromLauncher="NA";
    public String NetwAddressFromLauncher="NA";
    public String FusedAddressFromLauncher="NA";
    public String FusedLocationLatitudeWithFirstAttemptFromLauncher="NA";
    public String FusedLocationLongitudeWithFirstAttemptFromLauncher="NA";
    public String FusedLocationAccuracyWithFirstAttemptFromLauncher="NA";

    public static int flgLocationServicesOnOff=0;
    public static int flgGPSOnOff=0;
    public static int flgNetworkOnOff=0;
    public static int flgFusedOnOff=0;
    public static int flgInternetOnOffWhileLocationTracking=0;
    public static int flgRestart=0;
    public static int flgStoreOrder=0;

    public static String address,pincode,city,state,latitudeToSave,longitudeToSave,accuracyToSave;
    int countSubmitClicked=0;
    int refreshCount=0;
    String imei, fDate;
    LinearLayout ll_locationDetails,ll_map,ll_parentLayout,ll_showMap,ll_refresh;
    RadioGroup rg_yes_no;
    RadioButton rb_yes,rb_no;
    TextView txt_rfrshCmnt,tv_MapLocationCorrectText;
    EditText edit_gstYesVal;
    Button btn_submit,btn_refresh;
    ImageView img_back_Btn;
    Spinner spinner_for_filter;
    RadioButton rb_gstYes,rb_gstNo,rb_gstPending;
    FragmentManager manager;
    FragmentTransaction fragTrans;
    MapFragment mapFrag;

    String LattitudeFromLauncher="NA";
    String LongitudeFromLauncher="NA";
    String StoreName="NA";

    String[] Distribtr_list;

    LinearLayout ll_address_section,ll_local_area,ll_gstDetails;
    EditText etLocalArea, etPinCode, etCity, etState;

    TextView tvLocalArea,tvPinCode,tvCity,tvState,txt_gst;

    String DbrNodeId,DbrNodeType,DbrName;
    int flgReMap=0;
    int DistribtrId_Global=0;
    ArrayList<String> DbrArray=new ArrayList<String>();
    public int DistributorNodeType_Global=0;

    public static String VisitStartTS="NA";
    String flgGSTCapture="NA";
    AlertDialog GPSONOFFAlert=null;


    //changes
    boolean checkDistributionLoc=true;
    String latitudeFetched="NA",longitudeFetched="NA";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_map);

        refreshCount=0;
        helperDb=new PRJDatabase(DistributorCheckInFirstActivity.this);
        locationManager=(LocationManager) this.getSystemService(LOCATION_SERVICE);
        ll_locationDetails= (LinearLayout) findViewById(R.id.ll_locationDetails);
        ll_parentLayout= (LinearLayout) findViewById(R.id.ll_parentLayout);
        ll_map= (LinearLayout) findViewById(R.id.ll_map);
        ll_refresh= (LinearLayout) findViewById(R.id.ll_refresh);
        rg_yes_no= (RadioGroup) findViewById(R.id.rg_yes_no);
        rb_yes = (RadioButton) findViewById(R.id.rb_yes);
        rb_no = (RadioButton) findViewById(R.id.rb_no);
        txt_rfrshCmnt= (TextView) findViewById(R.id.txt_rfrshCmnt);

        TextView bardd= (TextView) findViewById(R.id.bardd);
        bardd.setText(R.string.DistributorLocation);

        tv_MapLocationCorrectText=(TextView) findViewById(R.id.tv_MapLocationCorrectText);
        btn_refresh= (Button) findViewById(R.id.btn_refresh);



        edit_gstYesVal= (EditText) findViewById(R.id.edit_gstYesVal);
        rb_gstYes= (RadioButton) findViewById(R.id.rb_gstYes);
        rb_gstNo= (RadioButton) findViewById(R.id.rb_gstNo);
        rb_gstPending= (RadioButton) findViewById(R.id.rb_gstPending);
        spinner_for_filter= (Spinner) findViewById(R.id.spinner_for_filter);
        btn_submit= (Button) findViewById(R.id.btn_submit);
        btn_submit.setText(getText(R.string.txtSubmit));
        ll_gstDetails= (LinearLayout) findViewById(R.id.ll_gstDetails);
        img_back_Btn= (ImageView) findViewById(R.id.img_back_Btn);
        txt_gst= (TextView) findViewById(R.id.txt_gst);

        ll_showMap= (LinearLayout) findViewById(R.id.ll_showMap);

        address="";
        pincode="";
        city="";
        state="";

        Intent i=getIntent();
        imei=i.getStringExtra("imei");
        fDate=i.getStringExtra("fDate");

        if(imei==null)
        {
            imei=CommonInfo.imei;
        }
        if(fDate==null)
        {
            Date date1 = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
            fDate = sdf.format(date1).toString().trim();
        }


        SpannableStringBuilder text_Value3=textWithMandatory(txt_gst.getText().toString());
        txt_gst.setText(text_Value3);

       inflateAddressLayout();
        GstRadioBtns();
        fnGetDistributorList();
        SubmitBtnWorking();
        BackBtnWorking();
        // locationRetreivingAndSetToMap();




        //  rl_sectionQuest=(RelativeLayout) findViewById(R.id.rl_sectionQuest);

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
        btn_refresh.setOnClickListener(new View.OnClickListener() {
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

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(GPSONOFFAlert!=null && GPSONOFFAlert.isShowing())
        {
            GPSONOFFAlert.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(pDialog2STANDBY!=null )
        {
            if(pDialog2STANDBY.isShowing() )
            {

            }
            else
            {
                if(countSubmitClicked==1)
                {
                    locationRetreivingAndSetToMap();
                }
                if(countSubmitClicked==0)
                {
                    locationRetreivingAndSetToMap();
                }
            }

        }
        else
        {
            if(countSubmitClicked==1)
            {

                locationRetreivingAndSetToMap();

            }
            if(countSubmitClicked==0)
            {
                locationRetreivingAndSetToMap();
            }

        }

    }

    public void GstRadioBtns()
    {
        rb_gstYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_gstYesVal.setVisibility(View.VISIBLE);
                }
            }
        });

        rb_gstNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_gstYesVal.setVisibility(View.GONE);
                }
            }
        });

        rb_gstPending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    edit_gstYesVal.setVisibility(View.GONE);
                }
            }
        });
    }

    void inflateAddressLayout()
    {
        View viewLoc=getLayoutInflater().inflate(R.layout.location_details_layout_distributor, null);
        ll_locationDetails.addView(viewLoc);

        ll_address_section=(LinearLayout) viewLoc.findViewById(R.id.ll_address_section);
        ll_address_section.setTag("CompleteAddress");

        ll_local_area= (LinearLayout) viewLoc.findViewById(R.id.ll_local_area);
        etLocalArea= (EditText) viewLoc.findViewById(R.id.etLocalArea);
        etPinCode= (EditText) viewLoc.findViewById(R.id.etPinCode);
        etCity= (EditText) viewLoc.findViewById(R.id.etCity);
        etState= (EditText) viewLoc.findViewById(R.id.etState);

          tvLocalArea= (TextView) viewLoc.findViewById(R.id.tvLocalArea);
         tvPinCode= (TextView) viewLoc.findViewById(R.id.tvPinCode);
          tvCity= (TextView) viewLoc.findViewById(R.id.tvCity);
         tvState= (TextView) viewLoc.findViewById(R.id.tvState);

      /*  if(tvLocalArea!=null)
        {
            String abcd=tvLocalArea.getText().toString();

            SpannableStringBuilder text_Value=textWithMandatory(abcd);
            tvLocalArea.setText(text_Value);
        }*/
            SpannableStringBuilder text_Value=textWithMandatory(tvLocalArea.getText().toString());
             tvLocalArea.setText(text_Value);

          SpannableStringBuilder text_Value1=textWithMandatory(tvPinCode.getText().toString());
          tvPinCode.setText(text_Value1);

          SpannableStringBuilder text_Value2=textWithMandatory(tvCity.getText().toString());
          tvCity.setText(text_Value2);

         SpannableStringBuilder text_Value3=textWithMandatory(tvState.getText().toString());
         tvState.setText(text_Value3);

    }

    public SpannableStringBuilder textWithMandatory(String text_Value)
    {
        String simple = text_Value;
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //text.setText(builder);

        return builder;

    }

    public void fnGetDistributorList()
    {
        helperDb.open();

        Distribtr_list=helperDb.getDistributorDataMstr();
        helperDb.close();
        for(int i=0;i<Distribtr_list.length;i++)
        {
            String value=Distribtr_list[i];
            DbrNodeId=value.split(Pattern.quote("^"))[0];
            DbrNodeType=value.split(Pattern.quote("^"))[1];
            DbrName=value.split(Pattern.quote("^"))[2];
            flgReMap=Integer.parseInt(value.split(Pattern.quote("^"))[3]);
            hmapDistributorListWithRemapFlg.put(DbrName,""+flgReMap);
            hmapDistributorListWithNodeIDType.put(DbrName,DbrNodeId+"^"+DbrNodeType);
            DbrArray.add(DbrName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DistributorCheckInFirstActivity.this,R.layout.initial_spinner_text,DbrArray);
        adapter.setDropDownViewResource(R.layout.spina);
        spinner_for_filter.setAdapter(adapter);

        if(!DbrArray.isEmpty())
        {
            if(DbrArray.size() == 2) //means only 1 distributor is their
            {
                DistribtrId_Global= Integer.parseInt(Distribtr_list[1].split(Pattern.quote("^"))[0]);
                DistributorNodeType_Global= Integer.parseInt(Distribtr_list[1].split(Pattern.quote("^"))[1]);

                spinner_for_filter.setSelection(1);
                ll_parentLayout.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);

            }
            else
            {
                spinner_for_filter.setSelection(0);
                ll_parentLayout.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.VISIBLE);
            }
        }

        spinner_for_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                TextView tv =(TextView) view;
                String text=tv.getText().toString();
                gblSelectedDistributor=text;
                String glbDRNodeIDType=hmapDistributorListWithNodeIDType.get(text);
                DistribtrId_Global= Integer.parseInt(glbDRNodeIDType.split(Pattern.quote("^"))[0]);
                DistributorNodeType_Global= Integer.parseInt(glbDRNodeIDType.split(Pattern.quote("^"))[1]);
                if(text.equals("Select Distributor"))
                {
                    ll_parentLayout.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.GONE);
                }
                else
                {
                    ll_parentLayout.setVisibility(View.VISIBLE);

                    if(hmapDistributorListWithRemapFlg.size()>0)
                    {
                        if(hmapDistributorListWithRemapFlg.containsKey(text))
                        {
                           // if(Integer.parseInt(hmapDistributorListWithRemapFlg.get(text))==0)
                            if(false)
                            {
                                btn_submit.setVisibility(View.GONE);

                                etLocalArea.setEnabled(false);
                                etPinCode.setEnabled(false);
                                etCity.setEnabled(false);
                                etState.setEnabled(false);


                                //Show Alert Distributor is Already Mapped then close


                                AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(DistributorCheckInFirstActivity.this);
                                alertDialogSubmitConfirm.setTitle("Information");
                                alertDialogSubmitConfirm.setMessage("Distributor is Already Mapped");
                                alertDialogSubmitConfirm.setCancelable(false);

                                alertDialogSubmitConfirm.setNeutralButton("OK", new DialogInterface.OnClickListener()
                                {
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.dismiss();
                                    }
                                });

                                alertDialogSubmitConfirm.show();


                            }
                            else
                            {
                                btn_submit.setVisibility(View.VISIBLE);

                                etLocalArea.setEnabled(true);
                                etPinCode.setEnabled(true);
                                etCity.setEnabled(true);
                                etState.setEnabled(true);


                            }
                        }
                    }

                    // btn_submit.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
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
            Marker locationMarker=googleMap.addMarker(marker);
            locationMarker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(LattitudeFromLauncher), Double.parseDouble(LongitudeFromLauncher)), 15));
        }
        else if(!latitudeFetched.equals("NA") && !longitudeFetched.equals("NA"))
        {
            googleMap.clear();
            try
            {
                googleMap.setMyLocationEnabled(false);
            }
            catch(SecurityException e)
            {}

            MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(latitudeFetched), Double.parseDouble(longitudeFetched)));
            Marker locationMarker=googleMap.addMarker(marker);
            locationMarker.showInfoWindow();
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(latitudeFetched), Double.parseDouble(longitudeFetched)), 15));
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

    public void locationRetreivingAndSetToMap()
    {
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
            {

            }
            isGPSokCheckInResume = false;
            isNWokCheckInResume=false;
        }
        else
        {
            checkDistributionLoc=helperDb.checkDistributorLocatioExists(String.valueOf(DistribtrId_Global));

            if(!checkDistributionLoc)
            {
                locationRetrievingAndDistanceCalculating();
            }
            else
            {
                String LatLong=helperDb.getLatLongForDistrbtrMap(String.valueOf(DistribtrId_Global));
                latitudeFetched=LatLong.split(Pattern.quote("^"))[0];
                longitudeFetched=LatLong.split(Pattern.quote("^"))[1];

                ll_map.setVisibility(View.VISIBLE);
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DistributorCheckInFirstActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);
            }
        }
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialogGps = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialogGps.setTitle("Information");
        alertDialogGps.setIcon(R.drawable.error_info_ico);
        alertDialogGps.setCancelable(false);
        // Setting Dialog Message
        alertDialogGps.setMessage("GPS is not enabled. \nPlease select all settings on the next page!");

        // On pressing Settings button
        alertDialogGps.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        GPSONOFFAlert=alertDialogGps.create();
        GPSONOFFAlert.show();
    }

    public void locationRetrievingAndDistanceCalculating()
    {

        appLocationService = new AppLocationService();

        pm = (PowerManager) getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "INFO");
        wl.acquire();


        pDialog2STANDBY = ProgressDialog.show(DistributorCheckInFirstActivity.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.rtrvng_loc), true);
        pDialog2STANDBY.setIndeterminate(true);

        pDialog2STANDBY.setCancelable(false);
        pDialog2STANDBY.show();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            mGoogleApiClient = new GoogleApiClient.Builder(DistributorCheckInFirstActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(DistributorCheckInFirstActivity.this)
                    .addOnConnectionFailedListener(DistributorCheckInFirstActivity.this)
                    .build();
            mGoogleApiClient.connect();
        }
        //startService(new Intent(DynamicActivity.this, AppLocationService.class));
        startService(new Intent(DistributorCheckInFirstActivity.this, AppLocationService.class));
        Location nwLocation = appLocationService.getLocation(locationManager, LocationManager.GPS_PROVIDER, location);
        Location gpsLocation = appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER, location);
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

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
        startLocationUpdates();
    }

    protected void startLocationUpdates()
    {
        try
        {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates( mGoogleApiClient, mLocationRequest, this);
   }
        catch (SecurityException e)
        {

        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
    }

    @Override
    public void onLocationChanged(Location args0) {
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

    public class CoundownClass extends CountDownTimer
    {

        public CoundownClass(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish()
        {
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
									 /* TextView accurcy=(TextView) findViewById(R.id.Acuracy);
									  accurcy.setText("GPS:"+GPSLocationAccuracy+"\n"+"NETWORK"+NetworkLocationAccuracy+"\n"+"FUSED"+fusedData);*/

           // System.out.println("LOCATION Fused"+fusedData);

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
            checkHighAccuracyLocationMode(DistributorCheckInFirstActivity.this);
            // fnAccurateProvider="";
            if(fnAccurateProvider.equals(""))
            {
                helperDb.open();
                helperDb.deleteLocationTable();
                helperDb.saveTblLocationDetails("NA", "NA", "NA","NA","NA","NA","NA","NA", "NA", "NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA");
                helperDb.close();

                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }

                ll_map.setVisibility(View.VISIBLE);
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DistributorCheckInFirstActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);

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
                //surbhi
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
                }

                if(pDialog2STANDBY.isShowing())
                {
                    pDialog2STANDBY.dismiss();
                }
                if(!GpsLat.equals("0") )
                {
                    fnCreateLastKnownGPSLoction(GpsLat,GpsLong,GpsAccuracy);
                }

                LattitudeFromLauncher=fnLati;
                LongitudeFromLauncher=fnLongi;
                AccuracyFromLauncher= String.valueOf(fnAccuracy);
                ProviderFromLauncher = fnAccurateProvider;
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


                ll_map.setVisibility(View.VISIBLE);
                manager= getFragmentManager();
                mapFrag = (MapFragment)manager.findFragmentById(R.id.map);
                mapFrag.getView().setVisibility(View.VISIBLE);
                mapFrag.getMapAsync(DistributorCheckInFirstActivity.this);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.show(mapFrag);


                helperDb.open();
                helperDb.deleteLocationTable();
                helperDb.saveTblLocationDetails(fnLati, fnLongi, String.valueOf(fnAccuracy), addr, city, zipcode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt);
                helperDb.close();
                //        if(!checkLastFinalLoctionIsRepeated("28.4873276","77.1045244","22.201"))
                if(!checkLastFinalLoctionIsRepeated(LattitudeFromLauncher,LongitudeFromLauncher,AccuracyFromLauncher))
                {
                    fnCreateLastKnownFinalLocation(LattitudeFromLauncher,LongitudeFromLauncher,AccuracyFromLauncher);
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DistributorCheckInFirstActivity.this);

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

               /* if(countSubmitClicked>0)
                {
                    if(!checkLastFinalLoctionIsRepeated(LattitudeFromLauncher,LongitudeFromLauncher,AccuracyFromLauncher))
                    {
                        fnCreateLastKnownFinalLocation(LattitudeFromLauncher,LongitudeFromLauncher,AccuracyFromLauncher);
                    }

                    showSubmitConfirm();
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
            // TODO Auto-generated method stub

        }}


    public boolean checkLastFinalLoctionIsRepeated(String currentLat, String currentLong, String currentAccuracy){
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

    public void fnCreateLastKnownGPSLoction(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy)
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


    public void fnCreateLastKnownFinalLocation(String chekLastGPSLat, String chekLastGPSLong, String chekLastGpsAccuracy)
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

    public String getAddressOfProviders(String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(DistributorCheckInFirstActivity.this, Locale.ENGLISH);



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
		      /* //String address = addresses.get(0).getAddressLine(0);
		       String address = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
		       String city = addresses.get(0).getLocality();
		       String state = addresses.get(0).getAdminArea();
		       String country = addresses.get(0).getCountryName();
		       String postalCode = addresses.get(0).getPostalCode();
		       String knownName = addresses.get(0).getFeatureName();
		       FULLADDRESS=address+","+city+","+state+","+country+","+postalCode;
		      Toast.makeText(contextcopy, "ADDRESS"+address+"city:"+city+"state:"+state+"country:"+country+"postalCode:"+postalCode, Toast.LENGTH_LONG).show();*/

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

    public String getAddressForDynamic(String latti, String longi){


        String areaToMerge="NA";
        Address addressTemp=null;
        String addr="NA";
        String zipcode="NA";
        String city="NA";
        String state="NA";
        String fullAddress="";
        StringBuilder FULLADDRESS3 =new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(DistributorCheckInFirstActivity.this, Locale.ENGLISH);

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


                for(int i=0 ;i<addresses.size();i++){
                    addressTemp = addresses.get(i);
                    if(addressTemp.getPostalCode()!=null){
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

                    address=  getAddressNewWay(addresses.get(0).getAddressLine(0),city,state,zipcode,countryname);
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
            return fullAddress=address+"^"+zipcode+"^"+city+"^"+state;
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

    void SubmitBtnWorking()
    {
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (validate())
                {
                    saveDataToDatabase();

                }
            }
        });
    }


    private boolean validate()
    {
        String spinner_text=spinner_for_filter.getSelectedItem().toString().trim();

        if(spinner_text.equals("Select Distributor"))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseSelectDistributor));
            return false;
        }
     /* else if(TextUtils.isEmpty(etLocalArea.getText().toString()) ||
                etLocalArea.getText().toString().trim().equals(null) ||
                etLocalArea.getText().toString().trim().equals("NA")||
                etLocalArea.getText().toString().trim().equals("0")||
                etLocalArea.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseFillAddress));
            return false;
        }
        else if(etLocalArea.getText().toString().trim().length()<5)
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseFillProperAddress));
            return false;
        }
        else if(TextUtils.isEmpty(etPinCode.getText().toString()) ||
                etPinCode.getText().toString().trim().equals(null) ||
                etPinCode.getText().toString().trim().equals("0")||
                etPinCode.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseFillPincode));
            return false;
        }
        else if(etPinCode.getText().toString().trim().length()<6)
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseFillPincodeAllDigits));
            return false;
        }
        else if(TextUtils.isEmpty(etCity.getText().toString()) ||
                etCity.getText().toString().trim().equals(null) ||
                etCity.getText().toString().trim().equals("NA")||
                etCity.getText().toString().trim().equals("0")||
                etCity.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseFillCity));
            return false;
        }
        else if(TextUtils.isEmpty(etState.getText().toString()) ||
                etState.getText().toString().trim().equals(null) ||
                etState.getText().toString().trim().equals("NA")||
                etState.getText().toString().trim().equals("0")||
                etState.getText().toString().trim().equals(""))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.PleaseFillState));
            return false;
        }*/
        else
        {
            return true;
        }
    }



    public String genTempID()
    {
        //store ID generation <x>

        String cxz;
        cxz = UUID.randomUUID().toString();

        StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

        String val1 = tokens.nextToken().trim();
        String val2 = tokens.nextToken().trim();
        String val3 = tokens.nextToken().trim();
        String val4 = tokens.nextToken().trim();
        cxz = tokens.nextToken().trim();

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tManager.getDeviceId();
        String IMEIid =  imei.substring(9);

        cxz = IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();

        return cxz;
        //-_
    }

    public void saveDataToDatabase()
    {
        UniqueDistribtrMapId=genTempID();

        String flgGSTCompliance = "NA";
        String GSTNumber = "NA";

       /* if (ll_gstDetails.getVisibility() == View.VISIBLE)
        {
            flgGSTCapture = "1";
        }
        else if (ll_gstDetails.getVisibility() == View.GONE)
        {
            flgGSTCapture = "0";
        }

        if (rb_gstYes.isChecked())
        {
            flgGSTCompliance = "1";
            if (!edit_gstYesVal.getText().toString().trim().equals(null) ||
                    !edit_gstYesVal.getText().toString().trim().equals(""))
            {
                GSTNumber = edit_gstYesVal.getText().toString().trim();
            }
        }
        else if (rb_gstNo.isChecked())
        {
            flgGSTCompliance = "0";
        }
        else if (rb_gstPending.isChecked())
        {
            flgGSTCompliance = "2";
        }*/

        String allLoctionDetails=  helperDb.getLocationDetails();

        String SOLattitudeFromLauncher="NA";
        String SOLongitudeFromLauncher="NA";
        String SOAccuracyFromLauncer="NA";

        String SOProviderFromLauncher="NA";

        String SOGpsLatFromLauncher="NA";
        String SOGpsLongFromLauncher="NA";
        String SOGpsAccuracyFromLauncher="NA";

        String SONetworkLatFromLauncher="NA";
        String SONetworkLongFromLauncher="NA";
        String SONetworkAccuracyFromLauncher="NA";

        String SOFusedLatFromLauncher="NA";
        String SOFusedLongFromLauncher="NA";
        String SOFusedAccuracyFromLauncher="NA";

        String SOAllProvidersLocationFromLauncher="NA";

        String SOGpsAddressFromLauncher="NA";
        String SONetwAddressFromLauncher="NA";
        String SOFusedAddressFromLauncher="NA";

        String SOFusedLocationLatitudeWithFirstAttemptFromLauncher="NA";
        String SOFusedLocationLongitudeWithFirstAttemptFromLauncher="NA";
        String SOFusedLocationAccuracyWithFirstAttemptFromLauncher="NA";

        if(!allLoctionDetails.equals("0"))
        {
            SOLattitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[0];
            SOLongitudeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[1];
            SOAccuracyFromLauncer = allLoctionDetails.split(Pattern.quote("^"))[2];

            AddressFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[3];
            CityFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[4];
            PincodeFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[5];
            StateFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[6];

            SOProviderFromLauncher = allLoctionDetails.split(Pattern.quote("^"))[7]; //finalProvider

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

        String finalAddress=etLocalArea.getText().toString().trim();
        String finalPinCode=etPinCode.getText().toString().trim();
        String finalCity=etCity.getText().toString().trim();
        String finalState=etState.getText().toString().trim();

       /* helperDb.open();
        helperDb.savetblDistributorMappingData(UniqueDistribtrMapId,""+DistribtrId_Global,""+DistributorNodeType_Global,flgGSTCapture,flgGSTCompliance,
                GSTNumber,finalAddress,finalPinCode,finalCity,finalState,SOLattitudeFromLauncher,SOLongitudeFromLauncher,
                SOAccuracyFromLauncer,"0",SOProviderFromLauncher,SOAllProvidersLocationFromLauncher,fnAddressFromLauncher,
                SOGpsLatFromLauncher,SOGpsLongFromLauncher,SOGpsAccuracyFromLauncher,SOGpsAddressFromLauncher,
                SONetworkLatFromLauncher,SONetworkLongFromLauncher,SONetworkAccuracyFromLauncher,SONetwAddressFromLauncher,
                SOFusedLatFromLauncher,SOFusedLongFromLauncher,SOFusedAccuracyFromLauncher,SOFusedAddressFromLauncher,
                SOFusedLocationLatitudeWithFirstAttemptFromLauncher,SOFusedLocationLongitudeWithFirstAttemptFromLauncher,
                SOFusedLocationAccuracyWithFirstAttemptFromLauncher,3,flgLocationServicesOnOff,flgGPSOnOff,flgNetworkOnOff,flgFusedOnOff,flgInternetOnOffWhileLocationTracking,flgRestart);

        helperDb.close();*/
        try
        {
           // FullSyncDataNow task = new FullSyncDataNow(DistributorCheckInFirstActivity.this);
           // task.execute();

            //Nitika
     /*       Intent intent=new Intent(DistributorCheckInFirstActivity.this,DistributorCheckInSecondActivity.class);
            intent.putExtra("imei", imei);
            intent.putExtra("fDate", fDate);
            startActivity(intent);
            finish();*/




        }
        catch (Exception e)
        {
            // TODO Autouuid-generated catch block
            e.printStackTrace();
            //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
        }
    }

    public void BackBtnWorking()
    {
        img_back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(DistributorCheckInFirstActivity.this);
                alertDialogSubmitConfirm.setTitle(getText(R.string.genTermInformation));
                alertDialogSubmitConfirm.setMessage(getText(R.string.BackButConfirm));
                alertDialogSubmitConfirm.setCancelable(false);

                alertDialogSubmitConfirm.setNeutralButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        Intent i=new Intent(DistributorCheckInFirstActivity.this,AllButtonActivity.class);
                        startActivity(i);
                        finish();
                    }
                });

                alertDialogSubmitConfirm.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

                AlertDialog alert = alertDialogSubmitConfirm.create();

                alert.show();

            }
        });
    }






}
