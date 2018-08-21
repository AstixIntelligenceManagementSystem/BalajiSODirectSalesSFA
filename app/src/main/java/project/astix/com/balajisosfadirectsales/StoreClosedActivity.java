package project.astix.com.balajisosfadirectsales;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.*;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Map;
import java.util.regex.Pattern;

public class StoreClosedActivity extends BaseActivity implements LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,DeletePic{

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    public static int flgLocationServicesOnOff=0;
    public static int flgGPSOnOff=0;
    public static int flgNetworkOnOff=0;
    public static int flgFusedOnOff=0;
    public static int flgInternetOnOffWhileLocationTracking=0;
    public static String address,pincode,city,state;
    private final LinkedHashMap<String ,ArrayList<String>> hmapCtgryPhotoSection=new LinkedHashMap<>();
    private final LinkedHashMap<String ,Integer> hmapCtgry_Imageposition=new LinkedHashMap<>();
    private final LinkedHashMap<String ,ImageAdapter> hmapImageAdapter=new LinkedHashMap<>();
    private final LinkedHashMap<String ,String> hmapPhotoDetailsForSaving=new LinkedHashMap<>();
    private final ArrayList<Object> arrImageData=new ArrayList<>();
    private final long startTime = 15000;
    private final long interval = 200;
    public String StoreVisitCode="NA";
    public LocationManager locationManager;
    public AppLocationService appLocationService;
    public PowerManager pm;
    public	 PowerManager.WakeLock wl;
    public ProgressDialog pDialog2STANDBY;
    public CoundownClass countDownTimer;
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
    public boolean isGPSEnabled = false;
    public   boolean isNetworkEnabled = false;
    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;
    public String AccuracyFromLauncher="NA";
    public String ProviderFromLauncher="NA";
    public String storeID;
    public String selStoreName;
    public String imei;
    public String date;
    public String pickerDate;
    TextView drpdwn_closeReason;
    EditText et_OtherReason;
    ImageView btn_bck;
    Button btn_clickPic,btn_save;
    ExpandableHeightGridView expnd_GridView;
    LinkedHashMap<String,String> hmap_StoreCloseReasons;
    LinkedHashMap<String,String> hmap_ReasonsDescID = new LinkedHashMap<>();
    ArrayList<String> list_Reasons=new ArrayList<>();
    AlertDialog GPSONOFFAlert=null;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    LocationRequest mLocationRequest;
    String fusedData;
    int countSubmitClicked=0;
    String LattitudeFromLauncher="NA";
    String LongitudeFromLauncher="NA";
    PRJDatabase helperDb=new PRJDatabase(StoreClosedActivity.this);
    ImageAdapter adapterImage=null;
    boolean flgListEmpty=false;
    ArrayList<String> list_ImgName;
    private Dialog dialog;
    private Uri uriSavedImage;
    private ImageView flashImage;
    private float mDist=0;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture,cancelCam, switchCamera;
    private boolean isLighOn = false;
    private String imageName;
    private LinearLayout cameraPreview;
    private final View.OnClickListener captrureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            cancelCam.setEnabled(false);
            flashImage.setEnabled(false);
            if(cameraPreview!=null)
            {
                cameraPreview.setEnabled(false);
            }

            if(mCamera!=null)
            {
                mCamera.takePicture(null, null, mPicture);
            }
            else
            {
                dialog.dismiss();
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    };
    private boolean cameraFront = false;
    private int picAddPosition=0;
    private int removePicPositin=0;
    private String clickedTagPhoto="";

    private static File getOutputMediaFile()
    {
        //make a new file directory inside the "sdcard" folder
        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder;
        File mediaStorageDir = new File(file_dj_path);
        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
       // mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        //mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" +CommonInfo.imei+"$"+ timeStamp + ".jpg");
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" +CommonInfo.imei+timeStamp + ".jpg");
        return mediaFile;
    }

    private static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_closed);



        Intent passedvals = getIntent();
        selStoreName = passedvals.getStringExtra("SN");
        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate= passedvals.getStringExtra("pickerDate");

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = tManager.getDeviceId();
        if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
        {
            CommonInfo.imei=imei;
        }
        else
        {
            imei=CommonInfo.imei.trim();
        }


        long syncTIMESTAMP = System.currentTimeMillis();
        Date dateobj = new Date(syncTIMESTAMP);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
        String startTS = df.format(dateobj);
        StoreVisitCode=helperDb.fnGetStoreVisitCode(storeID);
        helperDb.open();
        helperDb.UpdateStoreStartVisit(storeID,startTS);
        helperDb.close();


        btn_clickPic= (Button) findViewById(R.id.btn_clickPic);
        et_OtherReason = (EditText) findViewById(R.id.et_OtherReason);
        et_OtherReason.setVisibility(View.GONE);

        expnd_GridView= (ExpandableHeightGridView) findViewById(R.id.expnd_GridView);
        expnd_GridView.setExpanded(true);

        btn_bck= (ImageView) findViewById(R.id.btn_bck);
        btn_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               bckBtnAlert();
            }
        });

        drpdwn_closeReason = (TextView) findViewById(R.id.drpdwn_closeReason);
        drpdwn_closeReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAlertStoreList(getResources().getString(R.string.reason));
            }
        });

        btn_save= (Button) findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBtnAlert();
            }
        });

        locationFetching();

        getDataFromDatabase();
        fetchPhotoDetails();

        clickImage();
    }

    void getDataFromDatabase()
    {
        hmap_StoreCloseReasons=helperDb.getStoreClosedReasons();
        list_ImgName = helperDb.getStoreClosedImgNameByStoreId(storeID,StoreVisitCode);

        if(hmap_StoreCloseReasons != null && hmap_StoreCloseReasons.size()>0)
        {
            for(Map.Entry<String,String> entry:hmap_StoreCloseReasons.entrySet())
            {
                hmap_ReasonsDescID.put(entry.getValue(),entry.getKey());
            }
        }

        String fetchedValue=helperDb.getOtherReason(storeID,StoreVisitCode);

        String selectedDrpDwnValue="NA",selectedTextID="00";
        selectedTextID=fetchedValue.split(Pattern.quote("^"))[0];
        selectedDrpDwnValue=fetchedValue.split(Pattern.quote("^"))[1];

        if(selectedTextID.equals("-99"))
        {
            et_OtherReason.setVisibility(View.VISIBLE);
            drpdwn_closeReason.setText(hmap_StoreCloseReasons.get("-99"));
            et_OtherReason.setText(selectedDrpDwnValue);
        }
        else
        {
            drpdwn_closeReason.setText(hmap_StoreCloseReasons.get(selectedTextID));
        }

        btn_clickPic.setTag(storeID+"_"+selectedTextID);
        clickedTagPhoto=storeID+"_"+selectedTextID;
    }

    void bckBtnAlert()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(StoreClosedActivity.this);
        alert.setMessage(getText(R.string.BackButConfirm));
        alert.setTitle(getText(R.string.genTermInformation));
        alert.setPositiveButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                long  syncTIMESTAMP = System.currentTimeMillis();
                Date dateobj = new Date(syncTIMESTAMP);
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                String StampEndsTime = df.format(dateobj);
                helperDb.open();
                helperDb.deleteStorecloseLocationTableBasedOnStoreID(storeID,StoreVisitCode);
                helperDb.UpdateStoreEndVisit(storeID, StampEndsTime);
                helperDb.UpdateStoreFlag(storeID.trim(), 3);
                helperDb.close();



                Intent nxtP4=new Intent(StoreClosedActivity.this,LastVisitDetails.class);
                nxtP4.putExtra("storeID", storeID);
                nxtP4.putExtra("SN", selStoreName);
                nxtP4.putExtra("imei", imei);
                nxtP4.putExtra("userdate", date);
                nxtP4.putExtra("pickerDate", pickerDate);
                nxtP4.putExtra("bck", 1);
                startActivity(nxtP4);
                finish();
            }
        });

        alert.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog=alert.create();
        dialog.show();

    }

    void saveBtnAlert()
    {
        AlertDialog.Builder alert=new AlertDialog.Builder(StoreClosedActivity.this);
        alert.setMessage(getText(R.string.alertAskSaveMsg));
        alert.setTitle(getText(R.string.genTermInformation));
        alert.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(savingDataAndValidate())
                {
                    long syncTIMESTAMP = System.currentTimeMillis();
                    Date dateobj = new Date(syncTIMESTAMP);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                    String startTS = df.format(dateobj);
                    helperDb.open();
                    helperDb.UpdateStoreEndVisit(storeID,startTS);
                    helperDb.UpdateStoreStoreClose(storeID,1,StoreVisitCode);
                    helperDb.UpdateStoreSstat(storeID,3,StoreVisitCode);
                    helperDb.close();

                    if (hmapPhotoDetailsForSaving != null && hmapPhotoDetailsForSaving.size() > 0)
                    {
                        String drpdwnSelectedValue = "NA";
                        String drpdwnSelectedID="NA";

                        if (!hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("00")) //select
                        {
                            if (hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("-99")) //other
                            {
                                drpdwnSelectedValue = et_OtherReason.getText().toString();
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                            else
                            {
                                drpdwnSelectedValue = "NA";
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                        }
                        else
                        {
                            drpdwnSelectedValue="NA";
                            drpdwnSelectedID="00";
                        }

                        helperDb.inserttblStoreCloseReasonSaving(storeID,drpdwnSelectedID,drpdwnSelectedValue,3,StoreVisitCode);

                        for (Map.Entry<String, String> entry : hmapPhotoDetailsForSaving.entrySet())
                        {
                            String photoName = entry.getKey();
                            String photoPath = entry.getValue().split(Pattern.quote("~"))[1];
                            String clickedDateTime = entry.getValue().split(Pattern.quote("~"))[2];

                            helperDb.inserttblStoreClosedPhotoDetail(storeID, clickedDateTime, photoName, photoPath, 3,StoreVisitCode);
                            System.out.println("SAVING..."+storeID+"-"+clickedDateTime+"-"+photoName+"-"+photoPath);
                        }

                        Intent in = new Intent(StoreClosedActivity.this, StoreSelection.class);
                        in.putExtra("imei", imei);
                        in.putExtra("userDate", date);
                        in.putExtra("pickerDate", pickerDate);
                        startActivity(in);
                        finish();
                    }
                    else
                    {
                        String drpdwnSelectedValue = "NA";
                        String drpdwnSelectedID="NA";

                        if (!hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("00"))
                        {
                            if (hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("-99"))
                            {
                                drpdwnSelectedValue = et_OtherReason.getText().toString();
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                            else
                            {
                                drpdwnSelectedValue = "NA";
                                drpdwnSelectedID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
                            }
                        }
                        else
                        {
                            drpdwnSelectedValue="NA";
                            drpdwnSelectedID="00";
                        }

                        helperDb.upDateCloseStoreReason(storeID,drpdwnSelectedID,drpdwnSelectedValue,StoreVisitCode);
                        System.out.println("SAVING UPDATE..."+storeID+"-"+drpdwnSelectedID+"-"+drpdwnSelectedValue);

                        Intent in = new Intent(StoreClosedActivity.this, StoreSelection.class);
                        in.putExtra("imei", imei);
                        in.putExtra("userDate", date);
                        in.putExtra("pickerDate", pickerDate);

                        startActivity(in);
                        finish();
                    }
                }
            }
        });

        alert.setNegativeButton(getText(R.string.AlertDialogCancelButton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog=alert.create();
        dialog.show();

    }

    public void customAlertStoreList(String sectionHeader)
    {

        final Dialog listDialog = new Dialog(StoreClosedActivity.this);
        listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        listDialog.setContentView(R.layout.list_store_close);
        listDialog.setCanceledOnTouchOutside(false);
        listDialog.setCancelable(false);
        WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
        parms.gravity = Gravity.CENTER;
        parms.dimAmount = (float) 0.5;

        TextView txt_header = (TextView) listDialog.findViewById(R.id.txt_header);
        txt_header.setText(sectionHeader);

        TextView txtVwCncl = (TextView) listDialog.findViewById(R.id.txtVwCncl);

        final LinearLayout ll_parentRows = (LinearLayout) listDialog.findViewById(R.id.ll_parentRows);
        if( hmap_StoreCloseReasons != null && hmap_StoreCloseReasons.size()>0)
        {
            for (Map.Entry<String,String> entry:hmap_StoreCloseReasons.entrySet())
            {
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.text_view_ques, null);

                final TextView txtVw_ques = (TextView) convertView.findViewById(R.id.txtVw_ques);
                txtVw_ques.setText(entry.getValue());
                txtVw_ques.setTag(entry.getKey());

                txtVw_ques.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDialog.dismiss();
                        if (hmap_ReasonsDescID.get(txtVw_ques.getText().toString()).equals("-99"))
                        {
                            drpdwn_closeReason.setText(txtVw_ques.getText());
                            et_OtherReason.setVisibility(View.VISIBLE);
                        } else {
                            drpdwn_closeReason.setText(txtVw_ques.getText());
                            et_OtherReason.setVisibility(View.GONE);
                        }
                    }
                });

                ll_parentRows.addView(convertView);
            }
        }

        txtVwCncl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listDialog.dismiss();
            }
        });

        listDialog.show();

    }

    void locationFetching() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

        boolean isGPSokCheckInResume = false;
        boolean isNWokCheckInResume = false;
        isGPSokCheckInResume = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNWokCheckInResume = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSokCheckInResume && !isNWokCheckInResume) {
            try {
                showSettingsAlert();
            } catch (Exception e) {

            }
            isGPSokCheckInResume = false;
            isNWokCheckInResume = false;
        } else {
            locationRetrievingAndDistanceCalculating();
        }
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialogGps = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialogGps.setTitle(getText(R.string.genTermInformation));
        alertDialogGps.setIcon(R.drawable.error_info_ico);
        alertDialogGps.setCancelable(false);
        // Setting Dialog Message
        alertDialogGps.setMessage(getText(R.string.genTermGPSDisablePleaseEnable));

        // On pressing Settings button
        alertDialogGps.setPositiveButton(getText(R.string.syncAlertOKMsg), new DialogInterface.OnClickListener() {
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

        pDialog2STANDBY = ProgressDialog.show(StoreClosedActivity.this, getText(R.string.genTermPleaseWaitNew), getText(R.string.rtrvng_loc), true);
        pDialog2STANDBY.setIndeterminate(true);

        pDialog2STANDBY.setCancelable(false);
        pDialog2STANDBY.show();

        if (isGooglePlayServicesAvailable()) {
            createLocationRequest();

            mGoogleApiClient = new GoogleApiClient.Builder(StoreClosedActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(StoreClosedActivity.this)
                    .addOnConnectionFailedListener(StoreClosedActivity.this)
                    .build();
            mGoogleApiClient.connect();
        }

        //startService(new Intent(DynamicActivity.this, AppLocationService.class));
        startService(new Intent(StoreClosedActivity.this, AppLocationService.class));
        Location nwLocation = appLocationService.getLocation(locationManager, LocationManager.GPS_PROVIDER, location);
        Location gpsLocation = appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER, location);
        countDownTimer = new CoundownClass(startTime, interval);
        countDownTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(GPSONOFFAlert!=null && GPSONOFFAlert.isShowing())
        {
            GPSONOFFAlert.dismiss();
        }
        if(wl!=null)
        {
            wl.release();
        }
        if(mCamera!=null)
        {
            mCamera.release();
            mCamera=null;
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.dismiss();

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

    /*public String getAddressOfProviders(String latti, String longi){

        StringBuilder FULLADDRESS2 =new StringBuilder();
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(StoreClosedActivity.this, Locale.getDefault());

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
        geocoder = new Geocoder(StoreClosedActivity.this, Locale.ENGLISH);



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
            Geocoder geocoder = new Geocoder(StoreClosedActivity.this, Locale.ENGLISH);

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

              /*  NewStoreForm recFragment = (NewStoreForm)getFragmentManager().findFragmentByTag("NewStoreFragment");
                if(null != recFragment)
                {
                    recFragment.setFreshAddress();
                }*/
            }
            else{FULLADDRESS3.append("NA");}
        } catch (IOException e) {
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

    void clickImage()
    {
        /*String selectedTextID="00";

        selectedTextID=hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString());
        */
        if(list_ImgName == null || list_ImgName.size()==0)
        {
            adapterImage = new ImageAdapter(StoreClosedActivity.this);
            expnd_GridView.setAdapter(adapterImage);
            hmapImageAdapter.put(clickedTagPhoto,adapterImage);
        }

        btn_clickPic.setTag(clickedTagPhoto);
        btn_clickPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                clickedTagPhoto=v.getTag().toString();
                openCustomCamara();
            }
        });
    }

    boolean savingDataAndValidate()
    {
        ImageAdapter adapterImage=hmapImageAdapter.get(clickedTagPhoto);
        if(adapterImage.getCount()==0)
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.alertClickPic));
            return false;
        }
       /* else if(flgListEmpty)
        {
            showAlertSingleButtonInfo("Please click atleast one pic of the store");
            return false;
        }*/
        else if((hmap_ReasonsDescID != null && hmap_ReasonsDescID.size()>0) && hmap_ReasonsDescID.get(drpdwn_closeReason.getText().toString()).equals("-99") && TextUtils.isEmpty(et_OtherReason.getText()))
        {
            showAlertSingleButtonInfo(getResources().getString(R.string.alertOtherReason));
            return false;
        }
        else
        {
            return true;
        }
    }

    void fetchPhotoDetails()
    {
        if (list_ImgName != null && list_ImgName.size() > 0)
        {
            adapterImage = new ImageAdapter(StoreClosedActivity.this);
            expnd_GridView.setAdapter(adapterImage);
            hmapImageAdapter.put(clickedTagPhoto,adapterImage);

            hmapCtgryPhotoSection.put(clickedTagPhoto, list_ImgName);

            int picPosition=0;
            for (int i=0;i<list_ImgName.size();i++)
            {
                String imgName=list_ImgName.get(i);
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imgName;
                File fImageShow = new File(file_dj_path);
                if (fImageShow.exists())
                {
                    Bitmap bmp = decodeSampledBitmapFromFile(fImageShow.getAbsolutePath(), 80, 80);
                    adapterImage.add(i,bmp,imgName+"^"+clickedTagPhoto);

                    hmapCtgry_Imageposition.put(clickedTagPhoto,picPosition);
                    picPosition++;
                }
            }
        }
    }

    //custom camera
    private void openCustomCamara()
    {
        if(dialog!=null)
        {
            if(!dialog.isShowing())
            {
                openCamera();
            }
        }
        else
        {
            openCamera();
        }
    }

    private void handleZoom(MotionEvent event, Camera.Parameters params)
    {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            // zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            // zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;
        params.setZoom(zoom);
        mCamera.setParameters(params);
    }

    private void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null
                && supportedFocusModes
                .contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }

    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;

                if (pictureFile == null) {
                    return;
                }
                try
                {
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    arrImageData.add(0,pictureFile);
                    arrImageData.add(1,pictureFile.getName());
                    dialog.dismiss();
                    if(pictureFile!=null)
                    {
                        File file=pictureFile;
                        System.out.println("File +++"+pictureFile);
                        imageName=pictureFile.getName();
                        Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 80, 80);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        uriSavedImage = Uri.fromFile(pictureFile);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        byte[] byteArray = stream.toByteArray();

                        // Convert ByteArray to Bitmap::\
                        //
                        long syncTIMESTAMP = System.currentTimeMillis();
                        Date dateobj = new Date(syncTIMESTAMP);
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
                        String clkdTime = df.format(dateobj);

                        String valueOfKey=clickedTagPhoto+"~"+AddNewStore_DynamicSectionWiseSO.selStoreID+"~"+uriSavedImage.toString()+"~"+clkdTime+"~"+"1";

                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

                        setSavedImageToScrollView(bitmap, imageName,valueOfKey,clickedTagPhoto);
                        System.out.println("merch data..."+imageName+"~~"+valueOfKey+"~~"+clickedTagPhoto);
                    }
//Show dialog here
//...
//Hide dialog here

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }

                //refresh camera to continue preview--------------------------------------------------------------
                //	mPreview.refreshCamera(mCamera);
                //if want to release camera
                if(mCamera!=null){
                    mCamera.release();
                    mCamera=null;
                }
            }
        };
        return picture;
    }

    private boolean hasCamera(Context context) {
        //check if the device has camera
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }

    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }

    private void openCamera()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        arrImageData.clear();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(StoreClosedActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //dialog.setTitle("Calculation");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_main);
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();

        parms.height= parms.MATCH_PARENT;
        parms.width=parms.MATCH_PARENT;
        cameraPreview = (LinearLayout)dialog. findViewById(R.id.camera_preview);

        mPreview = new CameraPreview(StoreClosedActivity.this, mCamera);
        cameraPreview.addView(mPreview);
        //onResume code
        if (!hasCamera(StoreClosedActivity.this)) {
            Toast toast = Toast.makeText(StoreClosedActivity.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();
        }

        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(StoreClosedActivity.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }

            //mCamera = Camera.open(findBackFacingCamera());

			/*if(mCamera!=null){
				mCamera.release();
				mCamera=null;
			}*/
            mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			/*if(mCamera==null){
				mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
			}*/

            boolean isParameterSet=false;
            try {
                Camera.Parameters params= mCamera.getParameters();


                List<Camera.Size> sizes = params.getSupportedPictureSizes();
                Camera.Size size = sizes.get(0);
                //Camera.Size size1 = sizes.get(0);
                for(int i=0;i<sizes.size();i++)
                {

                    if(sizes.get(i).width > size.width)
                        size = sizes.get(i);
                }

                //System.out.println(size.width + "mm" + size.height);

                params.setPictureSize(size.width, size.height);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                //	params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);

                //	params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                isLighOn = false;
                int minExpCom=params.getMinExposureCompensation();
                int maxExpCom=params.getMaxExposureCompensation();

                if( maxExpCom > 4 && minExpCom < 4)
                {
                    params.setExposureCompensation(4);
                }
                else
                {
                    params.setExposureCompensation(0);
                }

                params.setAutoExposureLock(false);
                params.setAutoWhiteBalanceLock(false);
                //String supportedIsoValues = params.get("iso-values");
                // String newVAlue = params.get("iso");
                //  params.set("iso","1600");
                params.setColorEffect("none");
                params.set("scene-mode","auto");
                params.setPictureFormat(ImageFormat.JPEG);
                params.setJpegQuality(70);
                params.setRotation(90);

                mCamera.setParameters(params);
                isParameterSet=true;
            }
            catch (Exception e)
            {

            }
            if(!isParameterSet)
            {
                Camera.Parameters params2 = mCamera.getParameters();
                params2.setPictureFormat(ImageFormat.JPEG);
                params2.setJpegQuality(70);
                params2.setRotation(90);

                mCamera.setParameters(params2);
            }

            setCameraDisplayOrientation(StoreClosedActivity.this, Camera.CameraInfo.CAMERA_FACING_BACK,mCamera);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }

        capture = (Button)dialog.  findViewById(R.id.button_capture);

        flashImage= (ImageView)dialog.  findViewById(R.id.flashImage);
        flashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLighOn)
                {
                    // turn off flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(params);
                    flashImage.setImageResource(R.drawable.flash_off);
                    isLighOn=false;
                }
                else
                {
                    // turn on flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

                    flashImage.setImageResource(R.drawable.flash_on);
                    mCamera.setParameters(params);

                    isLighOn=true;
                }
            }
        });

        final Button cancleCamera= (Button)dialog.  findViewById(R.id.cancleCamera);
        cancelCam=cancleCamera;
        cancleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                v.setEnabled(false);
                capture.setEnabled(false);
                cameraPreview.setEnabled(false);
                flashImage.setEnabled(false);

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;
                dialog.dismiss();
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });

        capture.setOnClickListener(captrureListener);

        cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Get the pointer ID
                Camera.Parameters params = mCamera.getParameters();
                int action = event.getAction();

                if (event.getPointerCount() > 1) {
                    // handle multi-touch events
                    if (action == MotionEvent.ACTION_POINTER_DOWN) {
                        mDist = getFingerSpacing(event);
                    } else if (action == MotionEvent.ACTION_MOVE
                            && params.isZoomSupported()) {
                        mCamera.cancelAutoFocus();
                        handleZoom(event, params);
                    }
                } else {
                    // handle single touch events
                    if (action == MotionEvent.ACTION_UP) {
                        handleFocus(event, params);
                    }
                }
                return true;
            }
        });

        dialog.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    private void setSavedImageToScrollView(Bitmap bitmap, String imageName, String valueOfKey, String clickedTagPhoto)
    {
        if(hmapCtgry_Imageposition!=null && hmapCtgry_Imageposition.size()>0)
        {
            if(hmapCtgry_Imageposition.containsKey(clickedTagPhoto))
            {
                picAddPosition= hmapCtgry_Imageposition.get(clickedTagPhoto);
                if(picAddPosition == -1)
                {
                    flgListEmpty=false;
                    picAddPosition=0;
                }
            }
            else
            {
                picAddPosition=0;
            }
        }
        else
        {
            picAddPosition=0;
        }

        removePicPositin=picAddPosition;
        ArrayList<String> listClkdPic=new ArrayList<String>();
        if(hmapCtgryPhotoSection!=null && hmapCtgryPhotoSection.containsKey(clickedTagPhoto))
        {
            listClkdPic=hmapCtgryPhotoSection.get(clickedTagPhoto);
        }

        listClkdPic.add(imageName);
        hmapCtgryPhotoSection.put(clickedTagPhoto,listClkdPic);
        ImageAdapter adapterImage=hmapImageAdapter.get(clickedTagPhoto);
        adapterImage.add(picAddPosition,bitmap,imageName+"^"+clickedTagPhoto);
        System.out.println("REMOVE AND PIC ADD..."+removePicPositin+"__"+picAddPosition);
        System.out.println("Picture Adapter"+picAddPosition);
        picAddPosition++;
        hmapCtgry_Imageposition.put(clickedTagPhoto,picAddPosition);

        String photoPath=valueOfKey.split(Pattern.quote("~"))[2];
        String clickedDateTime=valueOfKey.split(Pattern.quote("~"))[3];

        //key- imagName
        //value- businessId^CatID^TypeID^PhotoPath^ClikcedDatetime^PhotoTypeFlag^StackNo

        hmapPhotoDetailsForSaving.put(imageName,clickedTagPhoto+"~"+photoPath+"~"+clickedDateTime);
        System.out.println("Hmap Photo..."+imageName+"^"+clickedTagPhoto+"^"+photoPath+"^"+clickedDateTime);

    }

    @Override
    public void delPic(Bitmap bmp, String imageNameToDel)
    {
        String  imageNameToDelVal=imageNameToDel.split(Pattern.quote("^"))[0];
        String tagPhoto=imageNameToDel.split(Pattern.quote("^"))[1];

        picAddPosition= hmapCtgry_Imageposition.get(tagPhoto);
        if(picAddPosition>1)
        {
            removePicPositin=picAddPosition-1;
        }
        else
        {
            removePicPositin=picAddPosition;
        }

        removePicPositin=removePicPositin-1;
        picAddPosition=picAddPosition-1;
        System.out.println("REMOVE AND PIC ADD DEL..."+removePicPositin+"__"+picAddPosition);
        hmapCtgry_Imageposition.put(tagPhoto,picAddPosition);
        //	String photoToBeDletedFromPath=dbengine.getPdaPhotoPath(imageNameToDel);

        // dbengine.updatePhotoValidation("0", imageNameToDel);

        ArrayList<String> listClkdPic=new ArrayList<String>();
        if(hmapCtgryPhotoSection!=null && hmapCtgryPhotoSection.containsKey(tagPhoto))
        {
            listClkdPic=hmapCtgryPhotoSection.get(tagPhoto);
        }

        if(listClkdPic.contains(imageNameToDelVal))
        {
            listClkdPic.remove(imageNameToDelVal);
            ImageAdapter adapterImage=hmapImageAdapter.get(tagPhoto);
            adapterImage.remove(bmp);
            hmapCtgryPhotoSection.put(tagPhoto,listClkdPic);
            if(listClkdPic.size()<1)
            {
                hmapCtgryPhotoSection.remove(tagPhoto);
            }
        }
        if(listClkdPic.size()==0)
        {
            flgListEmpty=true;
        }
        if(hmapPhotoDetailsForSaving.containsKey(imageNameToDelVal))
        {
            hmapPhotoDetailsForSaving.remove(imageNameToDelVal);
        }
        //  String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageNameToDel;
        String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageNameToDelVal;
        helperDb.validateAndDelStoreClosePic(storeID,imageNameToDelVal,StoreVisitCode);
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

    @Override
    public void getProductPhotoDetail(String productIdTag) {
    }

    private void callBroadCast() {
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

            Location gpsLocation=appLocationService.getLocation(locationManager, LocationManager.NETWORK_PROVIDER,location);
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
            checkHighAccuracyLocationMode(StoreClosedActivity.this);
            // fnAccurateProvider="";
            if(fnAccurateProvider.equals(""))
            {
                helperDb.open();
                helperDb.deleteStorecloseLocationTableBasedOnStoreID(storeID,StoreVisitCode);
                helperDb.saveTblStorecloseLocationDetails(storeID,"NA", "NA", "NA","NA","NA","NA","NA","NA", "NA", "NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA","NA",3,StoreVisitCode);
                System.out.println("LOCATION NA....");
                helperDb.close();

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

                helperDb.open();
                helperDb.deleteStorecloseLocationTableBasedOnStoreID(storeID,StoreVisitCode);
                helperDb.saveTblStorecloseLocationDetails(storeID,fnLati, fnLongi, String.valueOf(fnAccuracy), addr, city, zipcode, state,fnAccurateProvider,GpsLat,GpsLong,GpsAccuracy,NetwLat,NetwLong,NetwAccuracy,FusedLat,FusedLong,FusedAccuracy,AllProvidersLocation,GpsAddress,NetwAddress,FusedAddress,FusedLocationLatitudeWithFirstAttempt,FusedLocationLongitudeWithFirstAttempt,FusedLocationAccuracyWithFirstAttempt,3,StoreVisitCode);
                System.out.println("LOCATION..."+fnLati+"--"+fnLongi+"--"+String.valueOf(fnAccuracy)+"--"+addr+"--"+city+"--"+zipcode+"--"+state+"--"+fnAccurateProvider+"--"+GpsLat+"--"+GpsLong+"--"+GpsAccuracy+"--"+NetwLat+"--"+NetwLong+"--"+NetwAccuracy+"--"+FusedLat+"--"+FusedLong+"--"+FusedAccuracy+"--"+AllProvidersLocation+"--"+GpsAddress+"--"+NetwAddress+"--"+FusedAddress+"--"+FusedLocationLatitudeWithFirstAttempt+"--"+FusedLocationLongitudeWithFirstAttempt+"--"+FusedLocationAccuracyWithFirstAttempt);
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
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StoreClosedActivity.this);

                        // Setting Dialog Title
                        alertDialog.setTitle(getText(R.string.genTermInformation));
                        alertDialog.setIcon(R.drawable.error_info_ico);
                        alertDialog.setCancelable(false);
                        // Setting Dialog Message
                        alertDialog.setMessage(getText(R.string.AlertSameLoc));

                        // On pressing Settings button
                        alertDialog.setPositiveButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
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

        }
    }


}