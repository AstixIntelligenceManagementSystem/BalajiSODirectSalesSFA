package project.astix.com.balajisosfadirectsales;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Pattern;

public class ProductInvoiceReview extends BaseActivity implements OnItemSelectedListener, OnClickListener, OnFocusChangeListener, LocationListener,GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,InterfaceClass{
public int flgDrctslsIndrctSls=1;
	LinkedHashMap<String,String> hampGetLastProductExecution=new LinkedHashMap<String,String>();
	HashMap<String, String> hmapProductExtraOrder=new HashMap<String, String>();
	TextView txt_Lststock;
	public EditText ed_extraQty;
	String lastStockDate="";
	LinkedHashMap<String,String> hmapFetchPDASavedData=new LinkedHashMap<>();
	HashMap<String, String> hmapProductIdLastStock=new HashMap<String, String>();
	private static final String TAG = "LocationActivity";
	private static final long INTERVAL = 1000 * 10;
	private static final long FASTEST_INTERVAL = 1000 * 5;
	private static final long MIN_TIME_BW_UPDATES = 1000  * 1; //1 second
	private final long startTime = 30000;
	private final long interval = 200;
	public int flgTransferStatus=0;
	public String VisitTimeInSideStore="NA";
	public String StoreVisitCode="NA";
	//public TextView tvRateHeading;
	public String VisitTypeStatus="1";
	public int PriceApplyDiscountLevelType=0;
	public Double cutoffvalue=0.0;
	public String TmpInvoiceCodePDA="NA";
	public String strFinalAllotedInvoiceIds="NA";
	public int chkflgInvoiceAlreadyGenerated=0;
	public boolean flgAllTotalOrderValueCrossesCutOff=false;
	public  int flgLocationServicesOnOffOrderReview=0;
	public  int flgGPSOnOffOrderReview=0;
	public  int flgNetworkOnOffOrderReview=0;
	public  int flgFusedOnOffOrderReview=0;
	public  int flgInternetOnOffWhileLocationTrackingOrderReview=0;
	public  int flgRestartOrderReview=0;
	public  int flgStoreOrderOrderReview=0;
	public int powerCheck=0;
	public  PowerManager.WakeLock wl;
		public int StoreCurrentStoreType=0;
		public String Noti_text="Null";
		public int MsgServerID=0;
		public  String[] arrSchId;
		public String SchemeDesc="NA";
		public TextView spinner_product;
		public String ProductIdOnClickedEdit;
		public String CtaegoryIddOfClickedView;
		public String condtnOddEven;
		public String storeID;
		public String imei;
		public String date;
		public String pickerDate;
		public String SN;
		//public ProgressDialog pDialogSync;
		 public String productID;
		public String strGlobalOrderID="0";
		public LinearLayout ll_prdct_detal;
		//Invoice TextView
		public TextView tv_NetInvValue;
		public TextView tvTAmt;
		public TextView tvDis;
		public TextView tv_GrossInvVal;
		public TextView tvFtotal;
		public TextView tvAddDisc;
		public TextView tv_NetInvAfterDiscount;
		public TextView tvPreAmtOutstandingVALNew;
		public TextView tvAmtPrevDueVAL;
		public EditText etAmtCollVAL;
		public TextView tvAmtOutstandingVAL;
		public TextView tvCredAmtVAL;
	public TextView tvTotalAmtCollected;
		public TextView tvINafterCredVAL;
		public TextView textView1_CredAmtVAL_new;
		public TextView tvNoOfCouponValue;
		public TextView txttvCouponAmountValue;
		public String lastKnownLocLatitude="";
		public String lastKnownLocLongitude="";
		public String accuracy="0";
		public String locationProvider="Default";
		public double glbNetOrderLevelPercentDiscount=0.00;
		public double glbNetOderLevelFlatDiscount=0.00;
		public double glbGrossOrderLevelPercentDiscount=0.00;
		public double glbGrossOrderLevelFlatDiscount=0.00;
		public String newfullFileName;
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
	 public EditText   ed_search;
		public ImageView  btn_go;
	public String[] prductId;
		 public int flgApplyFreeProductSelection=0;
		 public int isbtnExceptionVisible=0;
		 public LocationManager locationManager;
		  public Location location;
		 // PowerManager.WakeLock wl;
		  public ProgressDialog pDialog2STANDBY;
		 public int flgProDataCalculation=1;
		 public int StoreCatNodeId=0;
		 public ListView lvProduct;
		 public EditText inputSearch;
	TextView txt_TotalLineCount_Value,txt_TotalInvoiceCount_Value;
	//hmapProductStock= key =ProductID         value=ProductSelectedUOMId
	HashMap<String, String> hmapProductSelectedUOMId=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=LineValBfrTxAftrDscnt
	HashMap<String, String> hmapLineValBfrTxAftrDscnt=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=LineValAftrTxAftrDscnt
	HashMap<String, String> hmapLineValAftrTxAftrDscnt=new HashMap<String, String>();
	LinkedHashMap<String,String> hmapPerBaseQty;
	//nitika
	CustomKeyboard mCustomKeyboardNum,mCustomKeyboardNumWithoutDecimal;
	HashMap<String,Integer> hmapDistPrdctStockCount =new HashMap<String,Integer>();
	HashMap<String,String> hmapPrdctIdOutofStock=new HashMap<String,String> ();
	String distID="";
	ArrayList<String> productFullFilledSlabGlobal=new ArrayList<String>();
	int countSubmitClicked=0;
	LocationRequest mLocationRequest;
		String defaultValForAlert;
		boolean disValClkdOpenAlert=false;
		String progressTitle;
		 boolean alertOpens=false;
		 int flagClkdButton=0;
		 String spinnerCategorySelected;
		 String spinnerCategoryIdSelected;
		 Location lastKnownLoc;
		 int countParentView;
		 String alrtSlctPrdctNameId,alrtSpnr_EditText_Value;
		 String alrtPrdctId;
		 String productIdOnLastEditTextVal="0";
		 String alrtValueSpnr;
		int alrtObjectTypeFlag=1; //1=spinner,edittext=0;
		// Decimal Format
		Locale locale  = new Locale("en", "UK");
		String pattern = "###.##";
		DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
		int CheckIfStoreExistInStoreProdcutPurchaseDetails=0;
		int CheckIfStoreExistInStoreProdcutInvoiceDetails=0;
		ProgressDialog mProgressDialog;
	//****************************************************************
		//Field
		String fusedData;
		 String mLastUpdateTime;
		Location mCurrentLocation;
		 GoogleApiClient mGoogleApiClient;
		EditText ed_LastEditextFocusd;
		 View viewProduct;
		TextView txtVw_schemeApld;
		 EditText alrtcrntEditTextValue;
		 TextView alrtPrvsPrdctSlctd;
		 Spinner alrtPrvsSpnrSlctd;
		Spinner spinner_category;
		ImageView btn_bck;
		TextView txtVwRate;
		TextView textViewFreeQty,textViewRate,textViewDiscount;
		LinearLayout ll_scheme_detail;
		int isReturnClkd=0;
	//****************************************************************
		//Arrays, HashMap
		View[] hide_View;
		 String[] categoryNames;
		LinkedHashMap<String, String> hmapctgry_details=new LinkedHashMap<String, String>();
		HashMap<String, String> hmapProductToBeFree=new HashMap<String, String>();    //Not is use
		ArrayList<HashMap<String, String>> arrLstHmapPrdct=new ArrayList<HashMap<String,String>>();
		//hmapSchemeIDandDescr= key=SchId,val=SchDescr
		HashMap<String, String> hmapSchemeIDandDescr=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=CategoryID
		HashMap<String, String> hmapCtgryPrdctDetail=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=Volume^Rate^TaxAmount
		HashMap<String, String> hmapPrdctVolRatTax=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=OrderQty
		HashMap<String, String> hmapPrdctOdrQty=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=ProductSample
		HashMap<String, String> hmapPrdctSmpl=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=ProductFreeQty
		HashMap<String, String> hmapPrdctFreeQty=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=ProductName
		HashMap<String, String> hmapPrdctIdPrdctName=new HashMap<String, String>();
		HashMap<String, String> hmapPrdctIdPrdctNameVisible=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=ProductDiscount
		HashMap<String, String> hmapPrdctIdPrdctDscnt=new HashMap<String, String>();
		//hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied Schemes,Slabs and other details
		HashMap<String, String> hmapProductRelatedSchemesList=new HashMap<String, String>();
		 // hmapSchemeIdStoreID= key=SchemeId value StoreId
		 HashMap<String, String> hmapSchemeIdStoreID=new HashMap<String, String>();
		 // hmapSchmSlabIdSchmID key=SchemeSlabId value = SchemeID
		 HashMap<String, String> hmapSchmSlabIdSchmID=new HashMap<String, String>();
		   //HashMap<String, String> hmapSchemeStoreID=new HashMap<String, String>();
		 // hmaSchemeSlabIdSlabDes key=SchemeSlabId value=SchemeSlab Description
		 HashMap<String, String> hmapSchemeSlabIdSlabDes=new HashMap<String, String>();
		 // hmapSchemeSlabIdBenifitDes key=SchemeSlabId value = BenifitDescription
		 HashMap<String, String> hmapSchemeSlabIdBenifitDes=new HashMap<String, String>();
		 // hmapProductIdOrdrVal key=Product Id value=ProductOrderVal
		 HashMap<String, String> hmapProductIdOrdrVal =new HashMap<String, String>();
		 // hmapProductIdStock key = ProductID value= Stock
		 HashMap<String, String> hmapProductIdStock=new HashMap<String, String>();
	// hmapProductIdStock key = ProductID value= flgPriceAva
	HashMap<String, String> hmapProductflgPriceAva=new HashMap<String, String>();
		 ArrayList<HashMap<String, String>> arrayListSchemeSlabDteail=new ArrayList<HashMap<String,String>>();
		 //hmapSchmeSlabIdSchemeId= key =SchemeSlabId         value=SchemeID
		   HashMap<String, String> hmapSchmeSlabIdSchemeId=new HashMap<String, String>();
		   //hmapSchmeSlabIdSlabDes= key =SchemeSlabId         value=SchemeSlabDes
		   HashMap<String, String> hmapSchmeSlabIdSlabDes=new HashMap<String, String>();
		   //hmapSchmeSlabIdBenifitDes= key = SchemeSlabId        value=BenifitDescription
		   HashMap<String, String> hmapSchmeSlabIdBenifitDes=new HashMap<String, String>();
		 //hmapProductRetailerMarginPercentage= key =ProductID         value=RetailerMarginPercentage
		   HashMap<String, String> hmapProductRetailerMarginPercentage=new HashMap<String, String>();
		 //hmapProductVatTaxPerventage= key =ProductID         value=VatTaxPercentage
		   HashMap<String, String> hmapProductVatTaxPerventage=new HashMap<String, String>();
		 //hmapProductVatTaxPerventage= key =ProductID         value=ProductMRP
		   HashMap<String, String> hmapProductMRP=new HashMap<String, String>();
		 //hmapProductVolumePer= key =ProductID         value=Per
		   HashMap<String, String> hmapProductVolumePer=new HashMap<String, String>();
		   //hmapProductDiscountPercentageGive= key =ProductID         value=DiscountPercentageGivenOnProduct
		   HashMap<String, String> hmapProductDiscountPercentageGive=new HashMap<String, String>();
		 //hmapProductVolumePer= key =ProductID         value=TaxValue
		   HashMap<String, String> hmapProductTaxValue=new HashMap<String, String>();
		   //hmapProductVolumePer= key =ProductID         value=TaxValue
		   HashMap<String, String> hmapProductViewTag=new HashMap<String, String>();
		 //hmapProductVolumePer= key =ProductID         value=LODQty
		   HashMap<String, String> hmapProductLODQty=new HashMap<String, String>();
			 //hmapProductStandardRate= key =ProductID         value=StandardRate
		   HashMap<String, String> hmapProductStandardRate=new HashMap<String, String>();
		   //hmapProductStandardRateBeforeTax= key =ProductID         value=StandardRateBeforeTax
		   HashMap<String, String> hmapProductStandardRateBeforeTax=new HashMap<String, String>();
		   //hmapProductStandardTax= key =ProductID         value=StandardTax
		   HashMap<String, String> hmapProductStandardTax=new HashMap<String, String>();
		   LinkedHashMap<String, String> hmapFilterProductList=new LinkedHashMap<String, String>();
		HashMap<String, HashMap<String, HashMap<String, String>>> hmapPrdtAppliedSchIdsAppliedSlabIdsDefination;
		  //hmapMinDlvrQty= key =ProductID         value=MinQty
		   LinkedHashMap<String, Integer> hmapMinDlvrQty=new LinkedHashMap<String, Integer>();
		   //hmapMinDlvrQty= key =ProductID         value=QPBT
		   LinkedHashMap<String, String> hmapMinDlvrQtyQPBT=new LinkedHashMap<String, String>();
		   LinkedHashMap<String, String> hmapMinDlvrQtyQPAT=new LinkedHashMap<String, String>();
	LinkedHashMap<String,String> hmapPerUnitName;
		 
		//Database
		   //
		   //hmapMinDlvrQty= key =ProductID         value=QPTaxAmount
		   LinkedHashMap<String, String> hmapMinDlvrQtyQPTaxAmount=new LinkedHashMap<String, String>();
	//hmapProductStock= key =ProductID         value=flgWholeSellApplicable
	HashMap<String, String> hmapflgWholeSellApplicable=new HashMap<String, String>();
		 
		 //Common Controls Box
	//hmapProductStock= key =ProductID         value=flgPriceRangeWholeSellApplicable
	HashMap<String, String> hmapPriceRangeWholeSellApplicable=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=StandardRateWholeSale
	HashMap<String, String> hmapStandardRateWholeSale=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=StandardRateBeforeTaxWholeSell
	HashMap<String, String> hmapStandardRateBeforeTaxWholeSell=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=StandardTaxWholeSale
	HashMap<String, String> hmapStandardTaxWholeSale=new HashMap<String, String>();
	//hmapMinDlvrQty= key =ProductID         value=ProductListOnWhichWholePriceNeedsToApplyIfRequired
	LinkedHashMap<String, String> hmapProductListOnWhichWholePriceNeedsToApplyIfRequired=new LinkedHashMap<String, String>();
		 ArrayList<String> arredtboc_OderQuantityFinalSchemesToApply;
		PRJDatabase dbengine = new PRJDatabase(this);
		DatabaseAssistant DA = new DatabaseAssistant(this);
		 EditText edtBox;
		 TextView viewBox;
		 String viewCurrentBoxValue="";
		 ImageView img_return;
		  boolean isGPSEnabled = false;
		  boolean isNetworkEnabled = false;
		  PowerManager pm;
		  LocationListener locationListener;
		  double latitude;
		  double longitude;
		 View convertView;
		 ArrayAdapter<String> adapter;
		 AlertDialog ad;
		 String[] products;
		private boolean alrtStopResult = false;

		 public void showSettingsAlert()
		 {
			  AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
			     
			        // Setting Dialog Title
			        alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
			        alertDialog.setIcon(R.drawable.error_info_ico);
			        alertDialog.setCancelable(false);
			        // Setting Dialog Message
			        alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.genTermGPSDisablePleaseEnable));
			 
			        // On pressing Settings button
			        alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {
			             Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			             startActivity(intent);
			            }
			        });
			 
			        // Showing Alert Message
			        alertDialog.show();
			 }
		 @Override
			protected void onResume() 
			{
				// TODO Auto-generated method stub
				super.onResume();
				
				
				
				
				
			/*	dbengine.open();
				String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
				dbengine.close();
				System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
				if(!Noti_textWithMsgServerID.equals("Null"))
				{
				StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");
				
				MsgServerID= Integer.parseInt(token.nextToken().trim());
				Noti_text= token.nextToken().trim();
				
				
				if(Noti_text.equals("") || Noti_text.equals("Null"))
				{
					
				}
				else
				{
					AlertDialog.Builder alertDialogSaveOK = new AlertDialog.Builder(ProductList.this);
					  alertDialogSaveOK.setTitle("Notification");
					  
					  alertDialogSaveOK.setMessage(Noti_text);
					  alertDialogSaveOK.setNeutralButton("OK",
					    new DialogInterface.OnClickListener() {
					     public void onClick(DialogInterface dialog, int which) {

					    	 long syncTIMESTAMP = System.currentTimeMillis();
								Date dateobj = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat(
										"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
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
				*/
				
				
				/*boolean isGPSok = false;
				isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				
		          if(!isGPSok)
		          {
		        	  try
		 			 {
		 			showSettingsAlert();
		 			 }
		 			 catch(Exception e)
		 			 {
		 				 
		 			 }
					isGPSok = false;
				   }*/
		          
		         
		     
		  		
			}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK)
		{

			mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
			mCustomKeyboardNum.hideCustomKeyboard();

			return false;


//			return true;
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
		protected void onCreate(Bundle savedInstanceState) 
		{
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_product_list_review);
			 //Toast.makeText(OrderReview.this, "OrderReview Page is called", Toast.LENGTH_SHORT).show();
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
			initializeFields();
			if(powerCheck==0)
			{
				PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
				wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
				wl.acquire();
			}
			/*LocalBroadcastManager.getInstance(ProductList.this).registerReceiver(
			mLocationReceiver, new IntentFilter("GPSLocationUpdates"));*/
		}
		
		public String[] changeHmapToArrayKey(HashMap hmap)
		{
			String[] stringArray=new String[hmap.size()];
			int index=0;
			if(hmap!=null)
	    	{
	            Set set2 = hmap.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext()) 
	            {
	            	 Entry me2 = (Entry)iterator.next();
	            	 stringArray[index]=me2.getKey().toString();
	                 index=index+1;
	            }
	    	}
			return stringArray;
		}
		
		public String[] changeHmapToArrayValue(HashMap hmap)
		{
			String[] stringArray=new String[hmap.size()];
			int index=0;
			if(hmap!=null)
	    	{
	            Set set2 = hmap.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext()) {
	            	 Entry me2 = (Entry)iterator.next();
	            	 stringArray[index]=me2.getValue().toString();
	            	 System.out.println("Betu Slab = "+stringArray[index]);
	                 index=index+1;
	            }
	    	}
			return stringArray;
		}
		
		@Override
		protected void onStop() {
			// TODO Auto-generated method stub
			super.onStop();
			
		}
		
		
		public void onDestroy()
		{
			   super.onDestroy();
			wl.release();
			  // LocalBroadcastManager.getInstance(ProductList.this).unregisterReceiver(mLocationReceiver);
			   
			  }
		

		 
		 public void CustomAlertBoxForSchemeDetails()
		 {
			 
			  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	         
			  ScrollView scroll = new ScrollView(this);
			 
			  scroll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

			
		        LinearLayout layout = new LinearLayout(this);
		        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		        layout.setOrientation(LinearLayout.VERTICAL);
		        layout.setLayoutParams(parms);
	            layout.setGravity(Gravity.CLIP_VERTICAL);
		        layout.setPadding(2,2,2,0);
		        layout.setBackgroundColor(Color.WHITE);

		        TextView tv = new TextView(this);
		        tv.setText(ProductInvoiceReview.this.getResources().getString(R.string.genTermNoDataConnection));
		        tv.setPadding(40, 10, 40, 10);
		        tv.setBackgroundColor(Color.parseColor("#486FA8"));
		        tv.setGravity(Gravity.CENTER);
		        tv.setTextSize(20);
		        tv.setTextColor(Color.parseColor("#ffffff"));

		       
		        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		        tv1Params.bottomMargin = 5;
		        
		        
		      
		        
		        
		        for(int i=0;i<arrSchId.length;i++)
			        {
			        	
			        	LinearLayout ChildViewDynamic = new LinearLayout(ProductInvoiceReview.this);
				    	ChildViewDynamic.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,25f));
				    	ChildViewDynamic.setOrientation(LinearLayout.VERTICAL);
				    	ChildViewDynamic.setWeightSum(25f);
				    	
				    	 TextView tv1 = new TextView(this);
					     tv1.setTextColor(Color.BLACK);
					     tv1.setBackgroundColor(Color.parseColor("#FFFEFC"));
					     SchemeDesc=hmapSchemeIDandDescr.get(arrSchId[i]);
					   //tv1.setText("Scheme Name :"+SchemeDesc);
					     tv1.setTextColor(Color.parseColor("#303F9F"));
					     tv1.setTypeface(null, Typeface.BOLD);
					    String mystring="Scheme Name :"+SchemeDesc;
					    SpannableString content = new SpannableString(mystring);
					     content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
					     tv1.setText(content);
					     
					     ChildViewDynamic.addView(tv1,tv1Params);
					    
					     
					     
					     String AllSchemeSlabID[]=dbengine.fnGetAllSchSlabbasedOnSchemeID(arrSchId[i]);
					    
					     // below two line for Testing,  please comment below two line for live
					    // hmapSchemeSlabIdSlabDes.put("62", "Buy [ 12 units from (Series - [ Go Fresh Cream, Go UHT Milk, Gowardhan Milk rich, Gowardhan Skim Milk Powder, GO Badam Milk, GO Butter Milk, Gowardhan Butter) AND 1 lines among these (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 Total Net value ] OR [5 Kg volume of Products from (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 value of Products from (Series - [ Go UHT Milk, GO Butter Milk)]");
					    // hmapSchemeSlabIdSlabDes.put("63", "Buy [ 12 units from (Series - [ Go Fresh Cream, Go UHT Milk, Gowardhan Milk rich, Gowardhan Skim Milk Powder, GO Badam Milk, GO Butter Milk, Gowardhan Butter) AND 1 lines among these (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 Total Net value ] OR [5 Kg volume of Products from (Series - [ Go UHT Milk, GO Butter Milk) AND Rs 2000 value of Products from (Series - [ Go UHT Milk, GO Butter Milk)]");
					     
					     
					    // hmapSchmeSlabIdSlabDes
					     
					    // hmapSchemeSlabIdBenifitDes.put("62", "GET [ 2 units from Same Product - [Exceptions -  3 units for Old Buyer] AND  Coupon No. 0002 AND 2% discount on Invoice value  - [Exceptions -  3% for Old Buyer, 4% for New Buyer]]");
					    // hmapSchemeSlabIdBenifitDes.put("63", "GET [ 2 units from Same Product - [Exceptions -  3 units for Old Buyer] AND  Coupon No. 0002 AND 2% discount on Invoice value  - [Exceptions -  3% for Old Buyer, 4% for New Buyer]]");
						    
					     int k=0;
					   for(int j=0;j<AllSchemeSlabID.length;j++)   // change 3 into SchmSlabId.length which i got hmapSchmSlabIdSchmID (Length of SchmSlabId)
					   {
						   
						    k=j+1;
						    
						   // System.out.println("List of all SchemeSlabID :"+AllSchemeSlabID[j]);
						   
						    TextView tv2 = new TextView(this);
						    tv2.setTextColor(Color.BLACK);
						    tv2.setBackgroundColor(Color.parseColor("#FFFEFC"));
						    tv2.setText("Slab "+k+"  :"+hmapSchmeSlabIdSlabDes.get(AllSchemeSlabID[j])); // It is for Live
						  //  tv2.setText("Slab "+k+"  :"+hmapSchemeSlabIdSlabDes.get("62"));  // It is for Testing
						    tv2.setTextColor(Color.parseColor("#E65100"));
						    
						    ChildViewDynamic.addView(tv2,tv1Params);
						    
						   
						    
						    TextView tv3 = new TextView(this);
						    tv3.setTextColor(Color.BLACK);
						    tv3.setBackgroundColor(Color.parseColor("#FFFEFC"));
						    tv3.setText("Benefit :"+hmapSchmeSlabIdBenifitDes.get(AllSchemeSlabID[j]));  // It is for Live
						   // tv3.setText("Benifit :"+hmapSchemeSlabIdBenifitDes.get("62"));   // It is for Testing
						    tv3.setTextColor(Color.parseColor("#3BA1B3"));
						    
						   
						    ChildViewDynamic.addView(tv3,tv1Params);
						    
						  
						    
					   }
			        
			           
				    
				    
			           layout.addView(ChildViewDynamic,tv1Params);
			        }
		        
		        scroll.addView(layout);
		       
		       
		        
		       
		      
		        alertDialogBuilder.setView(scroll);
		        alertDialogBuilder.setCustomTitle(tv);
	            alertDialogBuilder.setIcon(R.drawable.info_ico);
		        alertDialogBuilder.setCancelable(false);

		      

		        // Setting Positive "Yes" Button
		        alertDialogBuilder.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	 dialog.cancel();
		              }
		        });

		        AlertDialog alertDialog = alertDialogBuilder.create();
		       

		        try {
		            alertDialog.show();
		        } catch (Exception e) {
		            // WindowManager$BadTokenException will be caught and the app would
		            // not display the 'Force Close' message
		            e.printStackTrace();
		        }
		 
			 
	      
			
		 }
public void loadPurchaseProductDefault()
{
		        	hmapFilterProductList.clear();
		        	hmapPrdctIdPrdctNameVisible.clear();
		        	
		        	ll_prdct_detal.removeAllViews();

			       hmapFilterProductList=dbengine.getFileredOrderReviewProductListMap(storeID,TmpInvoiceCodePDA,flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
			       System.out.println("hmapFilterProductListCount :-"+ hmapFilterProductList.size());
		       
		       			Iterator it11new = hmapPrdctIdPrdctName.entrySet().iterator();
		       			String crntPID="0";
		       			int cntPsize=0;

					    createProductPrepopulateDetail(CheckIfStoreExistInStoreProdcutPurchaseDetails);
					    setInvoiceTableView();
	orderBookingTotalCalc();
}

		public void	initializeFields() {

			getDataFromIntent();
			//StoreVisitCode=dbengine.fnGetStoreVisitCode(storeID);
			//nitika
			mCustomKeyboardNum= new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num );
			mCustomKeyboardNumWithoutDecimal= new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal );
			txt_Lststock= (TextView) findViewById(R.id.txt_Lststock);
			if(!TextUtils.isEmpty(lastStockDate))
			{
				txt_Lststock.setText("Stk On "+lastStockDate);
			}
			else
			{
				txt_Lststock.setText("Last Stk NA");
			}

			txt_TotalLineCount_Value=(TextView) findViewById(R.id.txt_TotalLineCount_Value);

			txt_TotalInvoiceCount_Value=(TextView) findViewById(R.id.txt_TotalInvoiceCount_Value);


			//	spinner_product=(TextView) findViewById(R.id.spinner_product);
			//tvRateHeading=(TextView) findViewById(R.id.tvRateHeading);
			ed_search=(EditText) findViewById(R.id.ed_search);
			  btn_go=(ImageView) findViewById(R.id.btn_go);
			txtVw_schemeApld=(TextView) findViewById(R.id.txtVw_schemeApld);
			txtVw_schemeApld.setText("");
			txtVw_schemeApld.setTag("0");
			 
			//productIdOnLastEditText
			
			btn_go.setOnClickListener(new OnClickListener() {
				   
				   @Override
				   public void onClick(View v) {}

				  
				  });

			TextView tv_InvOrderHeader=(TextView) findViewById(R.id.lbl_InvOrderHeader) ;
			tv_InvOrderHeader.setText(R.string.OQty);
			Button btn_OrderReview=(Button) findViewById(R.id.btn_OrderReview);
			btn_OrderReview.setVisibility(View.GONE);
			btn_OrderReview.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent storeOrderReviewIntent = new Intent(ProductInvoiceReview.this, ProductOrderReview.class);
					storeOrderReviewIntent.putExtra("storeID", storeID);
					storeOrderReviewIntent.putExtra("SN", SN);
					storeOrderReviewIntent.putExtra("bck", 1);
					storeOrderReviewIntent.putExtra("imei", imei);
					storeOrderReviewIntent.putExtra("userdate", date);
					storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
					storeOrderReviewIntent.putExtra("OrderPDAID", strGlobalOrderID);
					startActivity(storeOrderReviewIntent);
					finish();

				}
			});
			TextView txt_RefreshOdrTot=(TextView) findViewById(R.id.txt_RefreshOdrTot);
			txt_RefreshOdrTot.setOnClickListener(new OnClickListener()
			  {
				
					@Override
					public void onClick(View v)
					{


						 orderBookingTotalCalc();
						
					}
			 });

			Double outstandingvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
			outstandingvalue=Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));

			Double CollectionAmt=dbengine.fnTotCollectionAmtAgainstStore(storeID,TmpInvoiceCodePDA,StoreVisitCode);
			CollectionAmt=Double.parseDouble(new DecimalFormat("##.##").format(CollectionAmt));

			Double cntInvoceValue=dbengine.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
			cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));
			final Button btn_AmountCollect=(Button) findViewById(R.id.btn_collectAmount);
			btn_AmountCollect.setVisibility(View.GONE);


			final Button btn_Submit=(Button) findViewById(R.id.btn_sbmt);
			final Button btn_Print=(Button) findViewById(R.id.btn_print);
			btn_Submit.setTag("0_0");
			btn_Print.setTag("2_2");

			btn_Submit.setVisibility(View.GONE);
			btn_Print.setVisibility(View.GONE);

			final Button  btn_Save=(Button)findViewById(R.id.btn_save);
			btn_Save.setTag("0_0");


			btn_Save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(ed_LastEditextFocusd!=null)
					{
						String tag=ed_LastEditextFocusd.getTag().toString();
						if(tag.contains("etOrderQty"))
						{
							if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
							{

								int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
								int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

								if (originalNetQntty>totalStockLeft)
								{

									alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,7);
								}
								else
								{

									if(mProgressDialog!=null) {
										if (mProgressDialog.isShowing() == true) {
											mProgressDialog.dismiss();
										}
									}

									orderBookingTotalCalc();
									nextStepAfterRetailerCreditBal(7);
								}

							}
							else
							{

								orderBookingTotalCalc();
								nextStepAfterRetailerCreditBal(7);
							}
						}


						else
						{
							nextStepAfterRetailerCreditBal(7);
						}



					}
					else
					{
						nextStepAfterRetailerCreditBal(7);
					}



				}
			});


			final Button btn_NextToCollection=(Button) findViewById(R.id.btn_NextToCollection);
			if(outstandingvalue==0.0 && cntInvoceValue==0.0)
			{
				btn_NextToCollection.setVisibility(View.GONE);

				dbengine.open();
				dbengine.deleteWhereStoreId(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
				dbengine.close();
				btn_Submit.setVisibility(View.VISIBLE);
				btn_Print.setVisibility(View.GONE);
			}
			else
			{
				btn_NextToCollection.setVisibility(View.VISIBLE);
				btn_Submit.setVisibility(View.GONE);
				btn_Print.setVisibility(View.GONE);
			}




			btn_NextToCollection.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(ed_LastEditextFocusd!=null)
					{
						String tag=ed_LastEditextFocusd.getTag().toString();
						if(tag.contains("etOrderQty"))
						{
							if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
							{

								int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
								int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

								if (originalNetQntty>totalStockLeft)
								{

									alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,7);
								}
								else
								{

									if(mProgressDialog!=null) {
										if (mProgressDialog.isShowing() == true) {
											mProgressDialog.dismiss();
										}
									}

									orderBookingTotalCalc();
									nextStepAfterRetailerCreditBal(7);
								}

							}
							else
							{

								orderBookingTotalCalc();
								nextStepAfterRetailerCreditBal(7);
							}
						}


						else
						{
							nextStepAfterRetailerCreditBal(7);
						}



					}
					else
					{
						nextStepAfterRetailerCreditBal(7);
					}



				}
			});

			btn_AmountCollect.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(ed_LastEditextFocusd!=null)
					{
						String tag=ed_LastEditextFocusd.getTag().toString();
						if(tag.contains("etOrderQty"))
						{
							if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
							{

								int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
								int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

								if (originalNetQntty>totalStockLeft)
								{

									alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,5);
								}
								else
								{

									if(mProgressDialog!=null) {
										if (mProgressDialog.isShowing() == true) {
											mProgressDialog.dismiss();
										}
									}

									orderBookingTotalCalc();
									nextStepAfterRetailerCreditBal(5);
								}

							}
							else
							{

								orderBookingTotalCalc();
								nextStepAfterRetailerCreditBal(5);
							}
						}


						else
						{
							nextStepAfterRetailerCreditBal(5);
						}



					}
					else
					{
						nextStepAfterRetailerCreditBal(5);
					}



				}
			});
			
			

			
			
			final Button btn_Cancel=(Button) findViewById(R.id.btn_Cancel);
			btn_Cancel.setOnClickListener(new OnClickListener() {
			    //  wer
			      @Override
			      public void onClick(View v) 
			      {
			       // TODO Auto-generated method stub
			    	  
			    	  
			    	  
			    	   long StartClickTime = System.currentTimeMillis();
						Date dateobj1 = new Date(StartClickTime);
						SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
						String StartClickTimeFinal = df1.format(dateobj1);
						
						
						
						
						String fileName=imei+"_"+storeID;
						
						File file = new File("/sdcard/"+CommonInfo.TextFileFolder+"/"+fileName);
						
						if (!file.exists()) 
						{
							try  
							{
								file.createNewFile();
							} 
							catch (IOException e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						CommonInfo.fileContent=CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Cancel Button Click on Product List"+StartClickTimeFinal;
						
						
						FileWriter fw;
						try 
						{
							fw = new FileWriter(file.getAbsoluteFile());
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(CommonInfo.fileContent);
							bw.close();
							
							dbengine.open();
							dbengine.savetblMessageTextFileContainer(fileName,0);
							dbengine.close();
							
							
						}
						catch (IOException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			     
			     AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(ProductInvoiceReview.this);
			     alertDialogSyncError.setTitle(getText(R.string.genTermNoDataConnection));
			     alertDialogSyncError.setCancelable(false);  // try submitting the details from outside the door
			     alertDialogSyncError.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.AlertCancelOrder));
			     alertDialogSyncError.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton),
			       new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which)
			        {
			        
			        	int flag=0;
			    		String[] imageToBeDeletedFromSdCard=dbengine.deletFromSDcCardPhotoValidation(storeID.trim());
			    		if(!imageToBeDeletedFromSdCard[0].equals("No Data"))
			    		{
				    			for(int i=0;i<imageToBeDeletedFromSdCard.length;i++)
				    		     {
				    				flag=1;
				    				//String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageToBeDeletedFromSdCard[i].toString().trim();
									 String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageToBeDeletedFromSdCard[i].toString().trim();

									 File fdelete = new File(file_dj_path);
							        if (fdelete.exists()) {
							            if (fdelete.delete()) {
							                Log.e("-->", "file Deleted :" + file_dj_path);
							                callBroadCast();
							            } else {
							                Log.e("-->", "file not Deleted :" + file_dj_path);
							            }
							        }
				    			}
			    		
			    			
			    		}
			    		
			    		dbengine.deleteProductBenifitSlabApplieddeleteProductBenifitSlabApplied(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
			    		dbengine.deleteAllStoreAlertValueProduct(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
			    		dbengine.open();
			    		dbengine.UpdateStoreFlag(storeID.trim(), 0);
			    		dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 0,strGlobalOrderID,TmpInvoiceCodePDA);
			    		dbengine.deleteStoreTblsRecordsInCaseCancelOrderInOrderBooking(storeID.trim(),flag,strGlobalOrderID,TmpInvoiceCodePDA);
			    		dbengine.close();
			    		dbengine.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0,StoreVisitCode);
			    		
			    		
			        	Intent storeSaveIntent = new Intent(ProductInvoiceReview.this, LauncherActivity.class);
			     		startActivity(storeSaveIntent);
			     		finish();
			        }
			       
			     });
			     alertDialogSyncError.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogCancelButton),
			       new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {

			         
			         dialog.dismiss();
			         
			        }
			       });
			     alertDialogSyncError.setIcon(R.drawable.info_ico);
			     
			     AlertDialog alert = alertDialogSyncError.create();
			     alert.show();
			     
			    
			    
			      }
			     });
			
			

		


			/*btn_Submit.setEnabled(false);
			btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));*/





			if(outstandingvalue<0.0)
			{
				btn_Submit.setEnabled(true);
				btn_Print.setEnabled(true);
				//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
			}
			else if(outstandingvalue==0.0)
			{
				btn_Submit.setEnabled(true);
				btn_Print.setEnabled(true);
				//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
			}
			else if(Math.ceil(CollectionAmt)>Math.ceil(outstandingvalue+cntInvoceValue))
			{
				btn_Submit.setEnabled(false);
				btn_Print.setEnabled(false);
				btn_Print.setBackgroundColor(Color.parseColor("#D3D3D3"));
				btn_Submit.setBackgroundColor(Color.parseColor("#D3D3D3"));
				//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
			}
			else if(outstandingvalue>0.0 && CollectionAmt==0.0)
			{
				btn_Submit.setEnabled(false);
				btn_Submit.setBackgroundColor(Color.parseColor("#D3D3D3"));
				btn_Print.setEnabled(false);
				btn_Print.setBackgroundColor(Color.parseColor("#D3D3D3"));
				//btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
			}
			else if(Math.ceil(outstandingvalue+ cntInvoceValue)==Math.ceil(CollectionAmt))
			{
				btn_Submit.setEnabled(true);
				btn_AmountCollect.setBackgroundColor(Color.parseColor("#FFFF33"));
			}
			else if(outstandingvalue>0.0 && CollectionAmt>0.0)
			{
				btn_Submit.setEnabled(true);
				btn_AmountCollect.setBackgroundColor(Color.parseColor("#2E7D32"));
				btn_Print.setEnabled(true);
				btn_Print.setBackgroundColor(Color.parseColor("#2E7D32"));
			}

			  btn_Submit.setOnClickListener(new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) 
			   {
			    // TODO Auto-generated method stub


				   if(ed_LastEditextFocusd!=null)
				   {
					   String tag=ed_LastEditextFocusd.getTag().toString();
					   if(tag.contains("etOrderQty"))
					   {
						   if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit)) {
							   int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							   int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							   if (originalNetQntty>totalStockLeft)
							   {

								   alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
							   }
							   else
							   {

								   if(dbengine.isFlgCrediBalSubmitted(storeID))
								   {
									   nextStepAfterRetailerCreditBal(2);
								   }
								   else
								   {
									   alertForRetailerCreditLimit(2);
								   }
							   }
						   }
						   else
						   {
							   if(dbengine.isFlgCrediBalSubmitted(storeID))
							   {
								   nextStepAfterRetailerCreditBal(2);
							   }
							   else
							   {
								   alertForRetailerCreditLimit(2);
							   }
						   }
					   }
					   else
					   {
						   if(dbengine.isFlgCrediBalSubmitted(storeID))
						   {
							   nextStepAfterRetailerCreditBal(2);
						   }
						   else
						   {
							   alertForRetailerCreditLimit(2);
						   }
					   }
				   }
				   else
				   {
					   if(dbengine.isFlgCrediBalSubmitted(storeID))
					   {
						   nextStepAfterRetailerCreditBal(2);
					   }
					   else
					   {
						   alertForRetailerCreditLimit(2);
					   }
				   }
			   }
			  });
			  
			  img_return=(ImageView) findViewById(R.id.img_return);
			  img_return.setOnClickListener(new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) {
				   if(ed_LastEditextFocusd!=null)
				   {
					   String tag=ed_LastEditextFocusd.getTag().toString();
					   if(tag.contains("etOrderQty"))
					   {
						   if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit)) {

							   int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							   int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);
							   if (originalNetQntty>totalStockLeft)
							   {

								   alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
							   }
							   else
							   {

								   if(dbengine.isFlgCrediBalSubmitted(storeID))
								   {
									   nextStepAfterRetailerCreditBal(3);
								   }
								   else
								   {
									   alertForRetailerCreditLimit(3);
								   }
							   }
						   }
						   else
						   {
							   if(dbengine.isFlgCrediBalSubmitted(storeID))
							   {
								   nextStepAfterRetailerCreditBal(3);
							   }
							   else
							   {
								   alertForRetailerCreditLimit(3);
							   }
						   }
					   }
					   else
					   {
						   if(dbengine.isFlgCrediBalSubmitted(storeID))
						   {
							   nextStepAfterRetailerCreditBal(3);
						   }
						   else
						   {
							   alertForRetailerCreditLimit(3);
						   }
					   }



				   }
				   else
				   {
					   if(dbengine.isFlgCrediBalSubmitted(storeID))
					   {

						   nextStepAfterRetailerCreditBal(3);

					   }
					   else
					   {

						   alertForRetailerCreditLimit(3);
					   }
				   }



				  }
			  });
			 /* spinner_category=(Spinner) findViewById(R.id.spinner_category);
			  spinner_category.setTag("0_0");
			  spinner_category.setOnFocusChangeListener(this);
			  */
			  btn_bck=(ImageView) findViewById(R.id.btn_bck);
			  btn_bck.setTag("0_0");
			 // btn_bck.setOnFocusChangeListener(this);
			  
			  ll_prdct_detal=(LinearLayout) findViewById(R.id.ll_prdct_detal);
			  ll_scheme_detail=(LinearLayout) findViewById(R.id.ll_scheme_detail);
			  
			/*  txtVw_schemeApld.setOnClickListener(new OnClickListener()
			    {
			     
			     @Override
			        public void onClick(View v) 
			         {
					         // TODO Auto-generated method stub
					         if(v.getId()==R.id.txtVw_schemeApld)
					          {
					        	 if(!v.getTag().equals("null"))
					        	 {
						          if(!v.getTag().toString().equals("0"))
							          {
							           arrSchId=v.getTag().toString().split(Pattern.quote("^"));
							           CustomAlertBoxForSchemeDetails();
							           *//*for(int i=0;i<arrSchId.length;i++)
							           {
							            
							           }*//*
							          }
					        	 }
					          }
			         }
			     });*/
			  //ll_scheme_detail=(LinearLayout) findViewById(R.id.ll_scheme_detail);
			  
			  btn_bck.setOnClickListener(new OnClickListener() {
			   
			   @Override
			   public void onClick(View v) {

				   if(ed_LastEditextFocusd!=null)
				   {
					   String tag=ed_LastEditextFocusd.getTag().toString();
					   if(tag.contains("etOrderQty"))
					   {
						   if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit)) {

							   int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							   int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							   if (originalNetQntty>totalStockLeft)
							   {

								   alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
							   }
							   else
							   {

								   if(dbengine.isFlgCrediBalSubmitted(storeID))
								   {

									   nextStepAfterRetailerCreditBal(4);

								   }
								   else
								   {

									   alertForRetailerCreditLimit(4);
								   }
							   }

						   }
						   else
						   {
							   if(dbengine.isFlgCrediBalSubmitted(storeID))
							   {

								   nextStepAfterRetailerCreditBal(4);

							   }
							   else
							   {

								   alertForRetailerCreditLimit(4);
							   }
						   }
					   }


					   else
					   {
						   if(dbengine.isFlgCrediBalSubmitted(storeID))
						   {

							   nextStepAfterRetailerCreditBal(4);

						   }
						   else
						   {

							   alertForRetailerCreditLimit(4);
						   }
					   }



				   }
				   else
				   {
					   if(dbengine.isFlgCrediBalSubmitted(storeID))
					   {

						   nextStepAfterRetailerCreditBal(4);

					   }
					   else
					   {
						   alertForRetailerCreditLimit(4);
					   }
				   }
			   }
			  });
			 // spinner_category.setOnItemSelectedListener(this);

			  new GetData().execute();

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

		private void getDataFromIntent() {
			  
			  
			  Intent passedvals = getIntent();

			  storeID = passedvals.getStringExtra("storeID");
			  imei = passedvals.getStringExtra("imei");
			  date = passedvals.getStringExtra("userdate");
			  pickerDate = passedvals.getStringExtra("pickerDate");
			  SN = passedvals.getStringExtra("SN");
			StoreVisitCode=dbengine.fnGetStoreVisitCode(storeID);
			  dbengine.open();
				 StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(storeID));
					dbengine.close();
			  //dbengine.fnProductWiseAppliedScehmeSlabDetails(storeIdProd);
			  hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();
			  //hmapPrdtAppliedSchIdsAppliedSlabIdsDefination=dbengine.fnProductWiseAppliedScehmeSlabDetails(StoreID);

			//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number

			if(CommonInfo.FlgDSRSO==1)
			{
				String SOCoverageAreaIDAndType=dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();
				flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[0]),Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[1]));
			}
			else
			{
				flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
			}
			if(flgDrctslsIndrctSls==0)
			{
				chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,flgDrctslsIndrctSls);
				if(chkflgInvoiceAlreadyGenerated==1)
				{
					TmpInvoiceCodePDA=dbengine.fnGetInvoiceCodePDA(storeID,StoreVisitCode);

				}
				if(dbengine.fnCheckForNewInvoiceOrPreviousValueFromPermanentTable(storeID,StoreVisitCode)==1)
				{
					TmpInvoiceCodePDA=dbengine.fnGetInvoiceCodePDAFromPermanentTable(storeID,StoreVisitCode);
				}
			}
			else {
				chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,flgDrctslsIndrctSls);
				if (chkflgInvoiceAlreadyGenerated == 1) {
					TmpInvoiceCodePDA = dbengine.fnGetInvoiceCodePDA(storeID, StoreVisitCode);

				} else {


					TmpInvoiceCodePDA = genTempInvoiceCodePDA();//dbengine.fnGettblInvoiceCaption(storeID);
				}
			}
			CheckIfStoreExistInStoreProdcutPurchaseDetails=dbengine.fnCheckIfStoreExistInStoreProdcutPurchaseDetails(storeID,TmpInvoiceCodePDA,flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
			CheckIfStoreExistInStoreProdcutInvoiceDetails=dbengine.fnCheckIfStoreExistInStoreProdcutInvoiceDetails(storeID,TmpInvoiceCodePDA);
			strGlobalOrderID=TmpInvoiceCodePDA;
			/*if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1 || CheckIfStoreExistInStoreProdcutInvoiceDetails==1)
            {
                strGlobalOrderID=dbengine.fngetOrderIDAganistStore(storeID,TmpInvoiceCodePDA);
            }
            else
            {
                strGlobalOrderID= genOutOrderID();
            }*/
		}


		private void getProductData() {
			// CategoryID,ProductID,ProductShortName,ProductRLP,Date/Qty)

			arrLstHmapPrdct=dbengine.fetch_catgry_prdctsData(storeID,StoreCurrentStoreType);
			//hmapSchemeIDandDescr=dbengine.fnSchemeIDandDescr();
			lastStockDate=dbengine.fnGetLastStockDate(storeID);
			hmapProductIdLastStock=dbengine.fnGetLastStockByDMS_Or_SFA(storeID);
			hampGetLastProductExecution=dbengine.fnGetHampGetLastProductExecution(storeID);
			HashMap<String, String>  hmapTempProd=dbengine.FetchLODqty(storeID);
			if(arrLstHmapPrdct.size()>0)
			{
				hmapCtgryPrdctDetail=arrLstHmapPrdct.get(0);
				hmapPrdctVolRatTax=arrLstHmapPrdct.get(1);
				hmapPrdctOdrQty=arrLstHmapPrdct.get(2);
				hmapPrdctSmpl=arrLstHmapPrdct.get(3);
				hmapPrdctFreeQty=arrLstHmapPrdct.get(4);
				hmapPrdctIdPrdctName=arrLstHmapPrdct.get(5);
				hmapPrdctIdPrdctDscnt=arrLstHmapPrdct.get(6);
				hmapProductRetailerMarginPercentage=arrLstHmapPrdct.get(7);
				hmapProductVatTaxPerventage=arrLstHmapPrdct.get(8);
				hmapProductMRP=arrLstHmapPrdct.get(9);
				hmapProductDiscountPercentageGive=arrLstHmapPrdct.get(10);
				hmapProductVolumePer=arrLstHmapPrdct.get(11);
				hmapProductTaxValue=arrLstHmapPrdct.get(12);
				hmapProductLODQty=arrLstHmapPrdct.get(13);
				hmapProductIdOrdrVal=arrLstHmapPrdct.get(14);

				hmapProductStandardRate=arrLstHmapPrdct.get(15);

				hmapProductStandardRateBeforeTax=arrLstHmapPrdct.get(16);

				hmapProductStandardTax=arrLstHmapPrdct.get(17);
				hmapProductIdStock=arrLstHmapPrdct.get(18);
				hmapProductflgPriceAva=arrLstHmapPrdct.get(19);
				hmapflgWholeSellApplicable=arrLstHmapPrdct.get(20);
				hmapPriceRangeWholeSellApplicable=arrLstHmapPrdct.get(21);
				hmapStandardRateWholeSale=arrLstHmapPrdct.get(22);
				hmapStandardRateBeforeTaxWholeSell=arrLstHmapPrdct.get(23);
				hmapStandardTaxWholeSale=arrLstHmapPrdct.get(24);

				hmapProductSelectedUOMId=arrLstHmapPrdct.get(25);
				hmapLineValBfrTxAftrDscnt=arrLstHmapPrdct.get(26);
				hmapLineValAftrTxAftrDscnt=arrLstHmapPrdct.get(27);
				hmapProductExtraOrder= arrLstHmapPrdct.get(28);


				Iterator it = hmapProductLODQty.entrySet().iterator();
				while (it.hasNext())
				{
					Entry pair = (Entry)it.next();
					if(hmapTempProd.containsKey(pair.getKey().toString()))
					{
						hmapProductLODQty.put(pair.getKey().toString(), hmapTempProd.get(pair.getKey().toString()));
					}

				}

				Iterator it11 = hmapPrdctIdPrdctName.entrySet().iterator();
				int pSize=hmapPrdctIdPrdctName.size();
				products=new String[pSize];
				int cntPsize=0;
				while (it11.hasNext())
				{
					Entry pair = (Entry)it11.next();

					products[cntPsize]=pair.getValue().toString();
					cntPsize++;

				}


			}



		}
		/*private void getSchemeSlabDetails() {
			  
			  arrayListSchemeSlabDteail=dbengine.fnctnSchemeSlabIdSchmVal();
			  
			  hmapSchemeIdStoreID=dbengine.fnctnSchemeStoreID(storeID);
			 // hmapSchemeStoreID=dbengine.fnctnSchemeStoreID(storeID);
			  if(arrayListSchemeSlabDteail!=null && arrayListSchemeSlabDteail.size()>0)
			  {
			    hmapSchmeSlabIdSchemeId=arrayListSchemeSlabDteail.get(0);

			   
			    hmapSchmeSlabIdSlabDes=arrayListSchemeSlabDteail.get(1);
			    
			    hmapSchmeSlabIdBenifitDes=arrayListSchemeSlabDteail.get(2);
			  }
			 }*/
		private void getCutOffDetailsForProduct()
		{
			PriceApplyDiscountLevelType=dbengine.fnGettblPriceApplyDiscountLevelType(storeID);
			if(PriceApplyDiscountLevelType==0) {


			}
			else
			{
				cutoffvalue=dbengine.fnGettblPriceApplycutoffvalue(storeID);
			}
		}
		private void getCategoryDetail() {
			
			hmapctgry_details=dbengine.fetch_Category_List();
			
			int index=0;
			if(hmapctgry_details!=null)
	    	{
				categoryNames=new String[hmapctgry_details.size()];
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details); 
	            Set set2 = map.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext()) {
	            	 Entry me2 = (Entry)iterator.next();
	            	 categoryNames[index]=me2.getKey().toString();
	                 index=index+1;
	            }
	    	}
	    	
			
		}
		
		
		/*public void createDynamicProduct(String productIdDynamic,int CheckIfStoreExistInStoreProdcutPurchaseDetails,String ProductValuesToFill)
		{



	          hide_View=new View[hmapCtgryPrdctDetail.size()];
	          prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);

	         
	          String arrStorePurcaseProducts=null;//=dbengine.fnGetProductPurchaseList(StoreID);

	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)

	           {

	            //arrStorePurcaseProducts=dbengine.fnGetProductPurchaseList(storeID);

	         

	           }

	           LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	          

	          
	           

	        //    countParentView=position;

	            viewProduct=inflater.inflate(R.layout.list_item_card_del_review,null);

	          

	            //ProdID,Stock,OrderQty,OrderVal,FreeQty,DisVal,SampleQuantity,ProductPrice

	           

	           

	           *//*if(position%2==0)

	           {

	            viewProduct.setBackgroundResource(R.drawable.card_background);

	           }*//*

	          

	           // hmapCtgryPrdctDetail.get(prductId[position]) = categoryId

	           viewProduct.setTag(hmapCtgryPrdctDetail.get(productIdDynamic)+"_"+productIdDynamic);

	        //   hide_View[position]=viewProduct;

	           hmapProductViewTag.put(viewProduct.getTag().toString(), "even");
	          ImageView imgDel=(ImageView) viewProduct.findViewById(R.id.imgDel);
	          imgDel.setTag(hmapCtgryPrdctDetail.get(productIdDynamic)+"_"+productIdDynamic);

	           LinearLayout ll_sample = (LinearLayout) viewProduct.findViewById(R.id.ll_sample);

	          //tvProdctName

	           TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);

	          

	           tv_product_name.setTag("tvProductName"+"_"+productIdDynamic);

	           tv_product_name.setText(hmapPrdctIdPrdctName.get(productIdDynamic));
	           EditText et_ProductMDQ=(EditText) viewProduct.findViewById(R.id.et_ProductMDQ);
	           if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
		          {
		        	  if(hmapMinDlvrQty.containsKey(productIdDynamic))
			           {
			        	   et_ProductMDQ.setText(String.valueOf(hmapMinDlvrQty.get(productIdDynamic)));
			        	   SpannableStringBuilder text_Value=textWithMandatory(tv_product_name.getText().toString());
				           tv_product_name.setText(text_Value);
			           }  
		          }
	           final ImageView btnExcptn=(ImageView) viewProduct.findViewById(R.id.btnExcptn);

	          

	           btnExcptn.setTag("btnException"+"_"+productIdDynamic);


	           

	          final EditText txtVwRate=(EditText) viewProduct.findViewById(R.id.txtVwRate);

	       

	       

	          

	           txtVwRate.setTag("tvRate"+"_"+productIdDynamic);

	          

	           String value=hmapPrdctVolRatTax.get(productIdDynamic).toString();

	           StringTokenizer tokens=new StringTokenizer(value,"^");

	           //Volume^Rate^TaxAmount

	           String volume = tokens.nextElement().toString();

	              String rate = tokens.nextElement().toString();

	              String taxAmount = tokens.nextElement().toString();
	              Double rateValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapProductStandardRateBeforeTax.get(productIdDynamic))));
	              txtVwRate.setText(""+rateValBeforeTax);

	              if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
		          {
		        	  if(hmapMinDlvrQty.containsKey(productIdDynamic))
			           {
		        		  rateValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapMinDlvrQtyQPBT.get(productIdDynamic))));
		        		  txtVwRate.setText(""+rateValBeforeTax);
			           }  
		          }
		         
	           

	           final EditText et_Stock=(EditText) viewProduct.findViewById(R.id.et_Stock);

	           et_Stock.setTag("etStock"+"_"+productIdDynamic);

	           et_Stock.setOnFocusChangeListener(this);

	          

	           final EditText et_SampleQTY=(EditText) viewProduct.findViewById(R.id.et_SampleQTY);

	           et_SampleQTY.setTag("etSampleQty"+"_"+productIdDynamic);

	           et_SampleQTY.setOnFocusChangeListener(this);

	          

	           final EditText et_OrderQty=(EditText) viewProduct.findViewById(R.id.et_OrderQty);

	           et_OrderQty.setTag("etOrderQty"+"_"+productIdDynamic);

	          

	           et_OrderQty.setOnFocusChangeListener(this);

	          

	           et_OrderQty.addTextChangedListener(new TextWatcher() {

	                       

	                        @Override

	                        public void onTextChanged(CharSequence s, int start, int before, int count) {

	                                        // TODO Auto-generated method stub

	                        	System.out.println("EditValue onTextchange : "+s.toString());

	                        }

	                       

	                        @Override

	                        public void beforeTextChanged(CharSequence s, int start, int count,

	                                                        int after) {

	                                        // TODO Auto-generated method stub
	                        		System.out.println("EditValue before : "+s.toString());
	                                       

	                        }

	                       

	                        @Override

	                        public void afterTextChanged(Editable s) 
	                        {

	                                       
	                        	productIdOnLastEditTextVal=s.toString();
	                        	
	                        	System.out.println("EditValue after : "+s.toString());
	                        		if(!viewCurrentBoxValue.equals(s.toString()))
	                        		{
	                        			if(btnExcptn.getVisibility()==View.VISIBLE)
	                            		{
	                            	
	                            			btnExcptn.setVisibility(View.INVISIBLE);
	                                			
	                                		
	                            			
	                            		}
	                        		}
	                        		else
	                        		{
	                        			if(isbtnExceptionVisible==1)
	                        			{
	                        					
	                        				btnExcptn.setVisibility(View.VISIBLE);
	                        			}
	                        		}
	                        		
	                    		
	                    		
	                    		

	                        }

	        });

	          
	           imgDel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				final String productIdToDel=	v.getTag().toString().split(Pattern.quote("_"))[1];
				final String vtag=v.getTag().toString();
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderReview.this);
					 
			        // Setting Dialog Title
			        alertDialog.setTitle("Confirm Delete...");
			 
			        // Setting Dialog Message
			        alertDialog.setMessage("Are you sure you want remove this product?");
			 
			        // Setting Icon to Dialog
			        alertDialog.setIcon(R.drawable.delete);
			 
			        // Setting Positive "Yes" Button
			        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {
			 
			            // Write your code here to invoke YES event
			            	hmapPrdctIdPrdctNameVisible.remove(productIdToDel);
			            	
			            	View namebar = (LinearLayout) (ll_prdct_detal).findViewWithTag(vtag);
			            	
			            	//If romoved from list, then remove it from the return  page also and code starts here
			            	
			            	String getPIdToremove=vtag.split("_")[1];
			            	try
			            	{
			            		 
			            		dbengine.fnDeleteProductDetailsFromReturnTables(storeID, getPIdToremove, strGlobalOrderID);
			            		dbengine.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsactionSingleProduct(storeID,strGlobalOrderID,getPIdToremove);
			            		hmapPrdctOdrQty.put(getPIdToremove, "0");
			            		hmapPrdctIdPrdctDscnt.put(getPIdToremove, "0");
			            		hmapProductDiscountPercentageGive.put(getPIdToremove, "0");
			            		hmapProductIdOrdrVal.put(getPIdToremove, "0");
			            		hmapProductIdStock.put(getPIdToremove, "0");
			            	}
			            	catch(Exception ex)
			            	{
			            	String abi=ex.toString();
			            	}
			            	//If romoved from list, then remove it from the return  page also and code ends here
			            	
			            	((LinearLayout) namebar.getParent()).removeView(namebar);
			            	 if(hmapPrdctIdPrdctNameVisible.size()>0)
			  	           {
			  	        	   createProductRowColor(); 
			  	           }
			            	
			            	 //ll_prdct_detal.removeViewInLayout((LinearLayout) (ll_prdct_detal).findViewWithTag(vtag));
			            	 
			            }
			        });
			 
			        // Setting Negative "NO" Button
			        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            // Write your code here to invoke NO event
			            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
			            dialog.cancel();
			            }
			        });
			 
			        // Showing Alert Message
			        alertDialog.show();
					
				}
			});
	           

	          final TextView tv_Orderval=(TextView) viewProduct.findViewById(R.id.tv_Orderval);

	           tv_Orderval.setTag("tvOrderVal"+"_"+productIdDynamic);

	       

	                                       

	           

	           EditText tv_FreeQty=(EditText) viewProduct.findViewById(R.id.tv_FreeQty);

	           tv_FreeQty.setTag("tvFreeQty"+"_"+productIdDynamic);

	         

	           

	           TextView tv_DisVal=(TextView) viewProduct.findViewById(R.id.tv_DisVal);

	           tv_DisVal.setTag("tvDiscountVal"+"_"+productIdDynamic);

	           tv_DisVal.setText(hmapPrdctIdPrdctDscnt.get(productIdDynamic));

	       

	          

	         

	           

	           EditText tvLODqty=(EditText) viewProduct.findViewById(R.id.tvLODqty);

	           tvLODqty.setTag("tvLODQuantity"+"_"+productIdDynamic);

	           tvLODqty.setText(hmapProductLODQty.get(productIdDynamic));

	          

	           final EditText et_ProductMRP=(EditText) viewProduct.findViewById(R.id.et_ProductMRP);

	           et_ProductMRP.setTag("etProductMRP"+"_"+productIdDynamic);

	           et_ProductMRP.setOnFocusChangeListener(this);

	           et_ProductMRP.setText(hmapProductMRP.get(productIdDynamic));

	       

	          

	         

	                                          

	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)

	           {

	                          

	                et_Stock.setText(ProductValuesToFill.split(Pattern.quote("^"))[1]);

	                et_OrderQty.setText(ProductValuesToFill.split(Pattern.quote("^"))[2]);

	                tv_Orderval.setText(ProductValuesToFill.split(Pattern.quote("^"))[3]);

	                tv_FreeQty.setText(ProductValuesToFill.split(Pattern.quote("^"))[4]);

	                tv_DisVal.setText(ProductValuesToFill.split(Pattern.quote("^"))[5]);

	                et_SampleQTY.setText(ProductValuesToFill.split(Pattern.quote("^"))[6]);

	               

	                if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[6])==0)

	                {

	                        et_SampleQTY.setText("");

	                        et_SampleQTY.setHint("Smpl.Qty");

	                }

	               

	                if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[2])==0)

	                {

	                        et_OrderQty.setText("");

	                        et_OrderQty.setHint("O.Qty");

	                }

	                if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[1])==0)

	                {

	                        et_Stock.setText("");

	                        et_Stock.setHint("Stock.Qty");

	                }

	               

	                hmapProductIdStock.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[1]);

	                hmapPrdctOdrQty.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[2]);

	                hmapProductIdOrdrVal.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[3]);

	                hmapPrdctFreeQty.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[4]);

	                hmapPrdctIdPrdctDscnt.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[5]);

	                hmapPrdctSmpl.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[6]);

	               

	                

	           }

	           et_OrderQty.setOnFocusChangeListener(this);

	          // txtVwRate.setOnClickListener(this);

	          // tv_product_name.setOnClickListener(this);

	           et_Stock.setOnClickListener(this);

	           et_OrderQty.setOnClickListener(this);

	           tv_Orderval.setOnClickListener(this);

	          // tv_FreeQty.setOnClickListener(this);

	           //tv_DisVal.setOnClickListener(this);

	           et_SampleQTY.setOnClickListener(this);

	          

	           et_SampleQTY.addTextChangedListener(new TextWatcher() {

	                       

	                        @Override

	                        public void onTextChanged(CharSequence s, int start, int before, int count) {

	                                        // TODO Auto-generated method stub

	                                       

	                        }

	                       

	                        @Override

	                        public void beforeTextChanged(CharSequence s, int start, int count,

	                                                        int after) {

	                                        // TODO Auto-generated method stub

	                                       

	                        }

	                       

	                        @Override

	                        public void afterTextChanged(Editable s) {

	                                        // TODO Auto-generated method stub

	                                       

	                                        int getPIDTag=Integer.parseInt(et_SampleQTY.getTag().toString().split("_")[1].toString());

	                                        if(!et_SampleQTY.getText().equals(""))

	                                        {             

	                                                        int boxSample=0;

	                                                        if(et_SampleQTY.getText().toString().equals("") || et_SampleQTY.getText().equals("0") || et_SampleQTY.getText().toString().trim().toLowerCase().equals("Smpl.Qty"))

	                                                        {

	                                                                        boxSample=0;

	                                                        }

	                                                        else

	                                                        {

	                                                                        boxSample         =Integer.parseInt(et_SampleQTY.getText().toString());

	                                                        }

	                                                        hmapPrdctSmpl.put(""+getPIDTag, ""+boxSample);

	                                        }

	                                        else

	                                        {

	                                                        et_SampleQTY.setText("0");

	                                                        hmapPrdctSmpl.put(""+getPIDTag, "0");

	                                        }

	                                       

	                        }

	        });

	           et_Stock.addTextChangedListener(new TextWatcher() {

	                       

	                        @Override

	                        public void onTextChanged(CharSequence s, int start, int before, int count) {

	                                        // TODO Auto-generated method stub

	                                       

	                        }

	                       

	                        @Override

	                        public void beforeTextChanged(CharSequence s, int start, int count,

	                                                        int after) {

	                                        // TODO Auto-generated method stub

	                                       

	                        }

	                       

	                        @Override

	                        public void afterTextChanged(Editable s) {

	                                        int getPIDTag=Integer.parseInt(et_Stock.getTag().toString().split("_")[1].toString());

	                                        if(!et_Stock.getText().equals(""))

	                                        {             

	                                                        int boxStock=0;

	                                                        if(et_Stock.getText().toString().equals("") || et_Stock.getText().equals("0") || et_Stock.getText().toString().trim().toLowerCase().equals("Stock"))

	                                                        {

	                                                                        boxStock=0;

	                                                        }

	                                                        else

	                                                        {

	                                                                        boxStock             =Integer.parseInt(et_Stock.getText().toString());

	                                                        }

	                                                        hmapProductIdStock.put(""+getPIDTag, ""+boxStock);

	                                        }

	                                        else

	                                        {

	                                                        et_Stock.setText("0");

	                                                        hmapProductIdStock.put(""+getPIDTag, "0");

	                                        }

	                                       

	                        }

	        });

	          

	           et_OrderQty.addTextChangedListener(new TextWatcher() {

	                        @Override

	                        public void onTextChanged(CharSequence s, int start, int before, int count) {

	                                        // TODO Auto-generated method stub

	                                        // TODO Auto-generated method stub

	                                       

	                        }

	                       

	                        @Override

	                        public void beforeTextChanged(CharSequence s, int start, int count,

	                                                        int after) {

	                                        // TODO Auto-generated method stub

	                                       

	                        }

	                       

	                        @Override

	                        public void afterTextChanged(Editable arg0) {

	                                        int getPIDTag=Integer.parseInt(et_OrderQty.getTag().toString().split("_")[1].toString());

	                                        if(!et_OrderQty.getText().equals(""))

	                                        {

	                                                        *//*String strPrate=((TextView)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).getText().toString();

	                                                        if(strPrate.equals(""))

	                                                        {

	                                                                        strPrate="0.00";

	                                                        }*//*

	                                                        int boxQty=0;

	                                                        if(et_OrderQty.getText().toString().equals("") || et_OrderQty.getText().equals("0") || et_OrderQty.getText().equals("Q.Qty") || et_OrderQty.getText().equals("Q . Qty"))

	                                                        {

	                                                                        boxQty=0;

	                                                        }

	                                                        else

	                                                        {

	                                                                        boxQty =Integer.parseInt(et_OrderQty.getText().toString());

	                                                        }

	                                                       

	                                                        *//*Double calculatedOrderValue=Double.parseDouble(""+((TextView)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).getText()) * Double.parseDouble(""+boxQty);

	                                                        //Get the Percentage of Now Over Value like Double calculatedDiscount =Rate*Order*BenifitAssignedValue/100

	                                                        //Order Value TextBox=Order Value TextBox+calculatedDiscount

	                                                        calculatedOrderValue=Double.parseDouble(new DecimalFormat("##.##").format(calculatedOrderValue));

	                                                        ((TextView)ll_prdct_detal.findViewWithTag("tvOrderVal_"+getPIDTag)).setText(""+calculatedOrderValue);

	                                                        hmapProductIdOrdrVal.put(""+getPIDTag, ""+calculatedOrderValue);*//*

	                                                        hmapPrdctOdrQty.put(""+getPIDTag, ""+boxQty);

	                                        }

	                                        else

	                                        {

	                                                        et_OrderQty.setText("0");

	                                                        et_OrderQty.setHint("O.Qty");

	                                                        ((TextView)ll_prdct_detal.findViewWithTag("tvOrderVal_"+getPIDTag)).setText("0.00");

	                                                        hmapPrdctOdrQty.put(""+getPIDTag, "0");

	                                                       

	                                        }

	                                       

	                        }

	        });

	          

	         

	          

	          ll_prdct_detal.addView(viewProduct);

	           

	          



	        
		}*/


	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
	}

	public void createDynamicProduct(String productIdDynamic, int CheckIfStoreExistInStoreProdcutPurchaseDetails, String ProductValuesToFill)
	{



		hide_View=new View[hmapCtgryPrdctDetail.size()];
		prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);


		String arrStorePurcaseProducts=null;//=dbengine.fnGetProductPurchaseList(StoreID);

		if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)

		{

			//arrStorePurcaseProducts=dbengine.fnGetProductPurchaseList(storeID);



		}

		LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);






		//    countParentView=position;

		//viewProduct=inflater.inflate(R.layout.list_item_card,null);
		viewProduct=inflater.inflate(R.layout.list_item_card_del_review,null);


		//ProdID,Stock,OrderQty,OrderVal,FreeQty,DisVal,SampleQuantity,ProductPrice





	           /*if(position%2==0)

	           {

	            viewProduct.setBackgroundResource(R.drawable.card_background);

	           }*/



		// hmapCtgryPrdctDetail.get(prductId[position]) = categoryId

		viewProduct.setTag(hmapCtgryPrdctDetail.get(productIdDynamic)+"_"+productIdDynamic);

		//   hide_View[position]=viewProduct;

		hmapProductViewTag.put(viewProduct.getTag().toString(), "even");
	          ImageView imgDel=(ImageView) viewProduct.findViewById(R.id.imgDel);
	          imgDel.setTag(hmapCtgryPrdctDetail.get(productIdDynamic)+"_"+productIdDynamic);
        imgDel.setVisibility(View.GONE);

		LinearLayout ll_sample = (LinearLayout) viewProduct.findViewById(R.id.ll_sample);

		//tvProdctName

		TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);

		tv_product_name.setTag("tvProductName"+"_"+productIdDynamic);
        if(hmapDistPrdctStockCount!=null && hmapDistPrdctStockCount.size()>0)
        {
            if(hmapDistPrdctStockCount.containsKey(productIdDynamic))
            {
                tv_product_name.setText(""+hmapPrdctIdPrdctName.get(productIdDynamic)+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")");//
            }
            else {
                tv_product_name.setText(hmapPrdctIdPrdctName.get(productIdDynamic));
            }
        }
        else
        {
            tv_product_name.setText(hmapPrdctIdPrdctName.get(productIdDynamic));
        }





		TextView et_ProductMDQ=(TextView) viewProduct.findViewById(R.id.et_ProductMDQ);
		if(hmapPerUnitName!=null && hmapPerUnitName.size()>0)
		{
			if(hmapPerUnitName.containsKey(productIdDynamic))
			{
				et_ProductMDQ.setText(hmapPerUnitName.get(productIdDynamic));
			}
		}


		/*TextView et_ProductUnitQty=(TextView) viewProduct.findViewById(R.id.tv_UnitQty);
		et_ProductUnitQty.setTag("etProductUnitQty"+"_"+productIdDynamic);*/
			/*if(hmapPerBaseQty!=null && hmapPerBaseQty.size()>0)
			{
				if(hmapPerBaseQty.containsKey(productIdDynamic))
				{
					et_ProductUnitQty.setText(hmapPerBaseQty.get(productIdDynamic));
				}
			}*/

		/*if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
		{
			if(hmapMinDlvrQty.containsKey(productIdDynamic))
			{
				et_ProductMDQ.setText(String.valueOf(hmapMinDlvrQty.get(productIdDynamic)));
				SpannableStringBuilder text_Value=textWithMandatory(tv_product_name.getText().toString());
				tv_product_name.setText(text_Value);
			}
		}
*/



		final ImageView btnExcptn=(ImageView) viewProduct.findViewById(R.id.btnExcptn);



		btnExcptn.setTag("btnException"+"_"+productIdDynamic);




		final EditText txtVwRate=(EditText) viewProduct.findViewById(R.id.txtVwRate);


		txtVwRate.setTag("tvRate"+"_"+productIdDynamic);
		txtVwRate.setEnabled(false);

		String value=hmapPrdctVolRatTax.get(productIdDynamic).toString();

		StringTokenizer tokens=new StringTokenizer(value,"^");

		//Volume^Rate^TaxAmount

		String volume = tokens.nextElement().toString();

		String rate = tokens.nextElement().toString();

		String taxAmount = tokens.nextElement().toString();

		Double rateValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapProductStandardRateBeforeTax.get(productIdDynamic))));
		//Double rateValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapProductStandardRate.get(productIdDynamic))));
		if(hmapProductStandardRate.get(productIdDynamic).equals("-99.0") || hmapProductStandardRate.get(productIdDynamic).equals("-99.00") || hmapProductStandardRate.get(productIdDynamic).equals("-99"))
		{
			txtVwRate.setText("");
			txtVwRate.setBackgroundResource(R.drawable.edit_text_bg);
			txtVwRate.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Rate));

		}
		else if(rateValBeforeTax.equals("-99.0") || rateValBeforeTax.equals("-99.00") || rateValBeforeTax.equals("-99"))
		{
			txtVwRate.setText("");
			txtVwRate.setBackgroundResource(R.drawable.edit_text_bg);
			txtVwRate.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Rate));
		}
		else {
			txtVwRate.setText("" + rateValBeforeTax);
		}

		if(hmapProductflgPriceAva.get(productIdDynamic).equals("1"))
		{
			txtVwRate.setEnabled(false);
			//txtVwRate.setEnabled(true);
		}
		if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
		{
			if(hmapMinDlvrQty.containsKey(productIdDynamic))
			{
				rateValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapMinDlvrQtyQPBT.get(productIdDynamic))));
				txtVwRate.setText(""+rateValBeforeTax);
			}
		}





		final EditText et_Stock=(EditText) viewProduct.findViewById(R.id.et_Stock);
        et_Stock.setEnabled(false);
		final EditText et_LstStock=(EditText) viewProduct.findViewById(R.id.et_LstStock);
		if(hmapProductIdLastStock.containsKey(productIdDynamic))
		{
			et_LstStock.setText(""+hmapProductIdLastStock.get(productIdDynamic));
		}
		else
		{
			et_LstStock.setText("");
		}
		et_Stock.setTag("etStock"+"_"+productIdDynamic);
		final EditText et_StoreSale=(EditText) viewProduct.findViewById(R.id.et_StoreSale);
		et_StoreSale.setTag("etStoreSale" + "_" + productIdDynamic);
/*if(flgOrderType==1)
		{*/
		et_Stock.setBackgroundResource(R.drawable.edit_text_bg_gst_disable);
		if(hmapFetchPDASavedData!=null && hmapFetchPDASavedData.containsKey(productIdDynamic))
		{

			et_Stock.setText(hmapFetchPDASavedData.get(productIdDynamic));


			hmapProductIdStock.put(productIdDynamic,hmapFetchPDASavedData.get(productIdDynamic));


		}
		else
		{
			hmapProductIdStock.put(productIdDynamic,"0");
		}
		/*}
		else
		{
			//ashwani sir said to mke it disable
			et_Stock.setEnabled(true);
			et_Stock.setBackgroundResource(R.drawable.edit_text_bg_stock);
			et_Stock.setTextColor(getResources().getColor(R.color.black));
			et_Stock.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.StockQty));

		}*/



		final EditText et_SampleQTY=(EditText) viewProduct.findViewById(R.id.et_SampleQTY);

		et_SampleQTY.setTag("etSampleQty"+"_"+productIdDynamic);





		final EditText et_OrderQty=(EditText) viewProduct.findViewById(R.id.et_OrderQty);

		et_OrderQty.setTag("etOrderQty"+"_"+productIdDynamic);

		et_OrderQty.setEnabled(false);







	          imgDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				final String productIdToDel=	v.getTag().toString().split(Pattern.quote("_"))[1];
				final String vtag=v.getTag().toString();
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

			        // Setting Dialog Title
			        alertDialog.setTitle(ProductInvoiceReview.this.getResources().getString(R.string.ConfirmDel));

			        // Setting Dialog Message
			        alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.RemoveProdct));

			        // Setting Icon to Dialog
			        alertDialog.setIcon(R.drawable.delete);

			        // Setting Positive "Yes" Button
			        alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog,int which) {

			            // Write your code here to invoke YES event
			            	hmapPrdctIdPrdctNameVisible.remove(productIdToDel);

							if (hmapDistPrdctStockCount.containsKey(productIdToDel)) {
								if(hmapPrdctIdOutofStock!=null && hmapPrdctIdOutofStock.size()>0)
								{
									if(hmapPrdctIdOutofStock.containsKey(productIdToDel))
									{
										int lastOrgnlQntty=Integer.parseInt(hmapPrdctIdOutofStock.get(productIdToDel));


										//hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

										//int updatedStock=lastOrgnlQntty-originalQntty;
										int netStockLeft=hmapDistPrdctStockCount.get(productIdToDel)+lastOrgnlQntty;
										hmapDistPrdctStockCount.put(productIdToDel,netStockLeft);



										hmapPrdctIdOutofStock.remove(productIdToDel);
										dbengine.deleteExistStockTable(distID,strGlobalOrderID,productIdToDel);

									}

								}



							}

			            	View namebar = (LinearLayout) (ll_prdct_detal).findViewWithTag(vtag);

			            	//If romoved from list, then remove it from the return  page also and code starts here

			            	String getPIdToremove=vtag.split("_")[1];
			            	try
			            	{
			            		dbengine.fnDeleteProductDetailsFromReturnTables(storeID, getPIdToremove, strGlobalOrderID,TmpInvoiceCodePDA);
								hmapPrdctOdrQty.put(""+getPIdToremove,"0");
								hmapPrdctFreeQty.put(""+getPIdToremove,"0");

								hmapProductIdStock.put(""+getPIdToremove,"0");
			            	}
			            	catch(Exception ex)
			            	{

			            	}
			            	//If romoved from list, then remove it from the return  page also and code ends here

			            	((LinearLayout) namebar.getParent()).removeView(namebar);
			            	 if(hmapPrdctIdPrdctNameVisible.size()>0)
			  	           {
			  	        	   createProductRowColor();
			  	           }

			            	 //ll_prdct_detal.removeViewInLayout((LinearLayout) (ll_prdct_detal).findViewWithTag(vtag));

			            }
			        });

			        // Setting Negative "NO" Button
			        alertDialog.setNegativeButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
			            public void onClick(DialogInterface dialog, int which) {
			            // Write your code here to invoke NO event
			           // Toast.makeText(getApplicationContext(), OrderReview.this.getResources().getString(R.string.YouclickedonNO), Toast.LENGTH_SHORT).show();
			            dialog.cancel();
			            }
			        });

			        // Showing Alert Message
			        alertDialog.show();

				}
			});


		final TextView tv_Orderval=(TextView) viewProduct.findViewById(R.id.tv_Orderval);

		tv_Orderval.setTag("tvOrderVal"+"_"+productIdDynamic);







		EditText tv_FreeQty=(EditText) viewProduct.findViewById(R.id.tv_FreeQty);

		tv_FreeQty.setTag("tvFreeQty"+"_"+productIdDynamic);





		TextView tv_DisVal=(TextView) viewProduct.findViewById(R.id.tv_DisVal);

		tv_DisVal.setTag("tvDiscountVal"+"_"+productIdDynamic);

		tv_DisVal.setText(hmapPrdctIdPrdctDscnt.get(productIdDynamic));









		EditText tvLODqty=(EditText) viewProduct.findViewById(R.id.tvLODqty);

		tvLODqty.setTag("tvLODQuantity"+"_"+productIdDynamic);

		tvLODqty.setText(hmapProductLODQty.get(productIdDynamic));



		final EditText et_ProductMRP=(EditText) viewProduct.findViewById(R.id.et_ProductMRP);

		et_ProductMRP.setTag("etProductMRP"+"_"+productIdDynamic);

	//	et_ProductMRP.setOnFocusChangeListener(this);


		if(hmapProductMRP.get(productIdDynamic).equals("-99.0") || hmapProductMRP.get(productIdDynamic).equals("-99.00") || hmapProductMRP.get(productIdDynamic).equals("-99"))
		{
			et_ProductMRP.setText("");
		}
		else if((""+rateValBeforeTax).equals("-99.0") || (""+rateValBeforeTax).equals("-99.00") || (""+rateValBeforeTax).equals("-99"))
		{
			et_ProductMRP.setText("");
		}
		else {
			Double rateOFProductMRP=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapProductMRP.get(productIdDynamic))));
			et_ProductMRP.setText(""+rateOFProductMRP);
		}

		// et_ProductMRP.setText(hmapProductMRP.get(productIdDynamic));









		if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)

		{



			//et_Stock.setText(ProductValuesToFill.split(Pattern.quote("^"))[1]);

			et_OrderQty.setText(ProductValuesToFill.split(Pattern.quote("^"))[2]);

			tv_Orderval.setText(ProductValuesToFill.split(Pattern.quote("^"))[3]);

			tv_FreeQty.setText(ProductValuesToFill.split(Pattern.quote("^"))[4]);

			tv_DisVal.setText(ProductValuesToFill.split(Pattern.quote("^"))[5]);

			et_SampleQTY.setText(ProductValuesToFill.split(Pattern.quote("^"))[6]);
			txtVwRate.setText(ProductValuesToFill.split(Pattern.quote("^"))[7]);
			//hmapProductStandardRate.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[7]);

			if(hmapProductStandardRate.get(productIdDynamic).equals("-99.0") || hmapProductStandardRate.get(productIdDynamic).equals("-99.00") || hmapProductStandardRate.get(productIdDynamic).equals("-99"))
			{
				txtVwRate.setText("");
				txtVwRate.setBackgroundResource(R.drawable.edit_text_bg);
				txtVwRate.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Rate));

			}
			else {
				Double rateOFProduct=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(ProductValuesToFill.split(Pattern.quote("^"))[7])));
				txtVwRate.setText(""+rateOFProduct);
			}

			if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[6])==0)

			{

				et_SampleQTY.setText("");

				et_SampleQTY.setHint(ProductInvoiceReview.this.getResources().getString(R.string.SmplQty));

			}



			if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[2])==0)

			{

				et_OrderQty.setText("");

				et_OrderQty.setHint(ProductInvoiceReview.this.getResources().getString(R.string.OQty));

			}

			/*if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[1])==0)

			{

				et_Stock.setText("");

				et_Stock.setHint(ProductOrderReview.this.getResources().getString(R.string.StockQty));

			}*/



			//hmapProductIdStock.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[1]);

			hmapPrdctOdrQty.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[2]);

			hmapProductIdOrdrVal.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[3]);

			hmapPrdctFreeQty.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[4]);

			hmapPrdctIdPrdctDscnt.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[5]);

			hmapPrdctSmpl.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[6]);


			hmapProductSelectedUOMId.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[8]);
			hmapLineValBfrTxAftrDscnt.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[9]);
			hmapLineValAftrTxAftrDscnt.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[10]);
			hmapProductExtraOrder.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[11]);

		}








		ll_prdct_detal.addView(viewProduct);





		//txtVwRate.setOnFocusChangeListener(this);
		//et_OrderQty.setOnFocusChangeListener(this);

		// txtVwRate.setOnClickListener(this);

		// tv_product_name.setOnClickListener(this);

		//et_Stock.setOnClickListener(this);

		//et_OrderQty.setOnClickListener(this);

		//tv_Orderval.setOnClickListener(this);

		// tv_FreeQty.setOnClickListener(this);

		//tv_DisVal.setOnClickListener(this);

	//	et_SampleQTY.setOnClickListener(this);


/*

		et_SampleQTY.addTextChangedListener(new TextWatcher() {



			@Override

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				// TODO Auto-generated method stub



			}



			@Override

			public void beforeTextChanged(CharSequence s, int start, int count,

										  int after) {

				// TODO Auto-generated method stub



			}



			@Override

			public void afterTextChanged(Editable s) {

				// TODO Auto-generated method stub



				int getPIDTag=Integer.parseInt(et_SampleQTY.getTag().toString().split("_")[1].toString());

				if(!et_SampleQTY.getText().equals(""))

				{

					int boxSample=0;

					if(et_SampleQTY.getText().toString().equals("") || et_SampleQTY.getText().equals("0") || et_SampleQTY.getText().toString().trim().toLowerCase().equals("Smpl.Qty"))

					{

						boxSample=0;

					}

					else

					{

						boxSample         =Integer.parseInt(et_SampleQTY.getText().toString());

					}

					hmapPrdctSmpl.put(""+getPIDTag, ""+boxSample);

				}

				else

				{

					et_SampleQTY.setText("0");

					hmapPrdctSmpl.put(""+getPIDTag, "0");

				}



			}

		});

		et_Stock.addTextChangedListener(new TextWatcher() {



			@Override

			public void onTextChanged(CharSequence s, int start, int before, int count) {

				// TODO Auto-generated method stub



			}



			@Override

			public void beforeTextChanged(CharSequence s, int start, int count,

										  int after) {

				// TODO Auto-generated method stub



			}



			@Override

			public void afterTextChanged(Editable s) {

				int getPIDTag=Integer.parseInt(et_Stock.getTag().toString().split("_")[1].toString());

				if(!et_Stock.getText().equals(""))

				{

					int boxStock=0;

					if(et_Stock.getText().toString().equals("") || et_Stock.getText().equals("0") || et_Stock.getText().toString().trim().toLowerCase().equals("Stock"))

					{

						boxStock=0;

					}

					else

					{

						boxStock             =Integer.parseInt(et_Stock.getText().toString());

					}

					hmapProductIdStock.put(""+getPIDTag, ""+boxStock);

				}

				else

				{

					et_Stock.setText("0");

					hmapProductIdStock.put(""+getPIDTag, "0");

				}



			}

		});



		txtVwRate.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				int getPIDTag=Integer.parseInt(txtVwRate.getTag().toString().split("_")[1].toString());



				if(TextUtils.isEmpty(txtVwRate.getText().toString().trim())) {
					String productiD = String.valueOf(getPIDTag);
					if (hmapProductflgPriceAva.get(productiD).equals("1")) {
						EditText ediTextOrder = (EditText) ll_prdct_detal.findViewWithTag("etOrderQty" + "_" + productiD);
						if (ediTextOrder != null) {

							ediTextOrder.setText("");
						}


					}
				}

				if(!txtVwRate.getText().equals(""))
				{
					String boxQty="-99";

					if(txtVwRate.getText().toString().equals("") || txtVwRate.getText().equals("0") || txtVwRate.getText().equals("Rate") || txtVwRate.getText().equals("Rate") || txtVwRate.getText().equals("0.0") || txtVwRate.getText().equals("0.00"))
					{

						boxQty="-99";

					}
					else
					{
						boxQty =txtVwRate.getText().toString();
					}
					//	hmapProductStandardRate.put(""+getPIDTag, ""+boxQty);
					if(Integer.parseInt(hmapPrdctOdrQty.get(""+getPIDTag))>0)
					{
						((EditText)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).setSelected(true);
						((EditText)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).requestFocus();
					}

				}
				else
				{

					//txtVwRate.setText("");

					txtVwRate.setHint(OrderReview.this.getResources().getString(R.string.Rate));



					hmapProductStandardRate.put(""+getPIDTag, "-99");



				}

			}
		});
*/

	/*	et_OrderQty.addTextChangedListener(new TextWatcher()
		{

			@Override

			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub
			}



			@Override

			public void beforeTextChanged(CharSequence s, int start, int count,int after)
			{
				// TODO Auto-generated method stub
			}



			@Override

			public void afterTextChanged(Editable s)
			{
				productIdOnLastEditTextVal=s.toString();
				String tagOrder=et_OrderQty.getTag().toString();
				String productIdOfTag=tagOrder.split(Pattern.quote("_"))[1];

				if(!TextUtils.isEmpty(et_OrderQty.getText().toString().trim()))
				{
					if(hmapProductflgPriceAva.get(productIdOfTag).equals("1"))
					{
						EditText ediTextRate= (EditText) ll_prdct_detal.findViewWithTag("tvRate"+"_"+productIdOfTag);
						if(ediTextRate!=null)
						{
							if(TextUtils.isEmpty(ediTextRate.getText().toString().trim()))
							{
								et_OrderQty.clearFocus();
								ediTextRate.requestFocus();
								//	ediTextRate.setCursorVisible(true);
								ediTextRate.setError(ProductOrderReview.this.getResources().getString(R.string.PleasefillproperRatefirst));
								et_OrderQty.setText("");
							}
							else if(Double.parseDouble(ediTextRate.getText().toString())<0)
							{
								et_OrderQty.clearFocus();
								ediTextRate.requestFocus();
								//ediTextRate.setCursorVisible(true);
								ediTextRate.setError(ProductOrderReview.this.getResources().getString(R.string.PleasefillproperRatefirst));
								et_OrderQty.setText("");
							}

						}

					}
				}




				if(!viewCurrentBoxValue.equals(s.toString()))
				{
					if(btnExcptn.getVisibility()==View.VISIBLE)
					{

						btnExcptn.setVisibility(View.INVISIBLE);
					}
				}
				else
				{
					if(isbtnExceptionVisible==1)
					{

						btnExcptn.setVisibility(View.VISIBLE);
					}
				}


				int getPIDTag=Integer.parseInt(et_OrderQty.getTag().toString().split("_")[1].toString());

				if(!et_OrderQty.getText().equals(""))
				{
					int boxQty=0;

					if(et_OrderQty.getText().toString().equals("") || et_OrderQty.getText().equals("0") || et_OrderQty.getText().equals("Q.Qty") || et_OrderQty.getText().equals("Q . Qty"))
					{

						boxQty=0;

					}
					else
					{
						boxQty =Integer.parseInt(et_OrderQty.getText().toString());
					}
					hmapPrdctOdrQty.put(""+getPIDTag, ""+boxQty);


				}
				else
				{

					et_OrderQty.setText("0");

					et_OrderQty.setHint(ProductOrderReview.this.getResources().getString(R.string.OQty));

					((TextView)ll_prdct_detal.findViewWithTag("tvOrderVal_"+getPIDTag)).setText("0.00");

					hmapPrdctOdrQty.put(""+getPIDTag, "0");



				}



			}

		});
*/



	}


		public void createProductPrepopulateDetail(int CheckIfStoreExistInStoreProdcutPurchaseDetails) {
		
			System.out.println("Abhinav Nitish Ankit New :"+CheckIfStoreExistInStoreProdcutPurchaseDetails);
			
	          hide_View=new View[hmapCtgryPrdctDetail.size()];
	          prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);
	          if(prductId.length>0)
	          {
	           String[] arrStorePurcaseProducts=null;//=dbengine.fnGetProductPurchaseList(StoreID);
	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)
	           {
	            arrStorePurcaseProducts=dbengine.fnGetProductPurchaseList(storeID,strGlobalOrderID,TmpInvoiceCodePDA,flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
	            System.out.println("Abhinav Nitish Ankit New Val :"+arrStorePurcaseProducts.length);
	            
	            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		           System.out.println("Abhinav Nitish Ankit New Val :"+hmapCtgryPrdctDetail.size());
		           for(int position=0;position<arrStorePurcaseProducts.length;position++)
		           {
			            countParentView=position;
			            viewProduct=inflater.inflate(R.layout.list_item_card,null);

			            if(position%2==0)
			           {
			            //viewProduct.setBackgroundResource(R.drawable.card_background);
			           }
			            
			            if(!hmapPrdctIdPrdctNameVisible.containsKey(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[0]))
						   {
							   createDynamicProduct(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[0], 1, arrStorePurcaseProducts[position].toString());
						       hmapPrdctIdPrdctNameVisible.put(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[0], hmapPrdctIdPrdctName.get(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[0]));
						   }
				   
		           }
		           
		           if(hmapPrdctIdPrdctNameVisible.size()>0)
		           {
		        	   createProductRowColor();
					   int InvoiceReviewLineCount=dbengine.fetchtblInvoiceReviewLineCount(storeID,TmpInvoiceCodePDA);
					   txt_TotalLineCount_Value.setText(""+InvoiceReviewLineCount);
					   int TotalInvoiceCount=dbengine.fetchtblInvoiceReviewCount(storeID,TmpInvoiceCodePDA);
					   txt_TotalInvoiceCount_Value.setText(""+TotalInvoiceCount);

		           }
	           }
	           
	         }
	          
	          
	          
		}
		@SuppressLint("ResourceAsColor")
		public void createProductRowColor() {
			// LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			  
			   for(int position=0;position<hmapPrdctIdPrdctNameVisible.size();position++)
			   	{
				  // viewProduct=inflater.inflate(R.layout.list_item_card,null);
				    if(position%2==0)
					{
				    	ll_prdct_detal.getChildAt(position).setBackgroundResource(R.drawable.card_background);
					}
				    else
				    {
				    	//ll_prdct_detal.getChildAt(position).setBackgroundColor(Color.parseColor("#ffffff"));
					//	ll_prdct_detal.getChildAt(position).setBackgroundColor(Color.parseColor("#F2F2F2"));
						ll_prdct_detal.getChildAt(position).setBackgroundResource(R.drawable.card_background_white);

					}
				}
		}
		public void createProductDetail(int CheckIfStoreExistInStoreProdcutPurchaseDetails) {}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,long id) 
		   {
		
				spinnerCategorySelected = spinner_category.getSelectedItem().toString();
				//txtVw_schemeApld.setText("");
				
				txtVw_schemeApld.setText("");
				txtVw_schemeApld.setTag("0");
				
				filterProduct(spinnerCategorySelected);
			}
			
			
		

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	private void filterProduct(String slctdProduct)
	{
		
		spinnerCategoryIdSelected=hmapctgry_details.get(slctdProduct);
			
		 int currentPos=1;
		 /*txtVw_schemeApld.setTextColor(Color.parseColor("#3f51b5"));
		 SpannableString content = new SpannableString("");
		 content.setSpan(new UnderlineSpan(), 0, content.length(), 0);*/
		 txtVw_schemeApld.setText("");
		 txtVw_schemeApld.setTag("0");
	for(int posHideVsbl=0;posHideVsbl<hmapCtgryPrdctDetail.size();posHideVsbl++)
		{
			if(slctdProduct.equals("All"))
					{
				
							if(hide_View[posHideVsbl].getVisibility()==View.GONE)
								{
									hide_View[posHideVsbl].setVisibility(View.VISIBLE);
								}
							if(currentPos%2==0)
							{
								hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_even);
								hide_View[posHideVsbl].setTag(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl]+"_"+"even");
								 hmapProductViewTag.put(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl], "even");
							}
					 		else
							{
					 			hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_odd);
					 			hide_View[posHideVsbl].setTag(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl]+"_"+"odd");
					 			 hmapProductViewTag.put(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl], "odd");
							}
							currentPos++;
					}
					
			else{
					if(((hide_View[posHideVsbl].getTag().toString()).split(Pattern.quote("_"))[0]).equals(spinnerCategoryIdSelected))
						{
						
							if(hide_View[posHideVsbl].getVisibility()==View.GONE)
								{
									hide_View[posHideVsbl].setVisibility(View.VISIBLE);
								}
							
							if(currentPos%2==0)
							{
								hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_even);
								hide_View[posHideVsbl].setTag(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl]+"_"+"even");
								 hmapProductViewTag.put(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl], "even");
							}
							else
								
								{
								hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_odd);
								hide_View[posHideVsbl].setTag(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl]+"_"+"odd");
								 hmapProductViewTag.put(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl], "odd");
								}
							currentPos++;
						}
					else
						{
							hide_View[posHideVsbl].setVisibility(View.GONE);
						}
				}
		}		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub


		txtVw_schemeApld.setText("No Scheme Applicable");
		txtVw_schemeApld.setTag("0");

		EditText et_ValueOnFocuslostnew=null;
		String ProductIdOnClickedControl=v.getTag().toString().split("_")[1];
		if(!hasFocus)
		{
			isbtnExceptionVisible=0;

			View viewRow=(View) v.getParent();

			//hideSoftKeyboard(v);
			if(v instanceof EditText)
			{


				//ProductIdOnClickedEdit
				EditText et_ValueOnFocuslost=(EditText) v;
				//et_ValueOnFocuslost.setCursorVisible(false);
				et_ValueOnFocuslostnew=et_ValueOnFocuslost;
				ProductIdOnClickedEdit=et_ValueOnFocuslost.getTag().toString().split(Pattern.quote("_"))[1];
				CtaegoryIddOfClickedView=hmapCtgryPrdctDetail.get(ProductIdOnClickedEdit);
				condtnOddEven=hmapProductViewTag.get(CtaegoryIddOfClickedView+"_"+ProductIdOnClickedEdit);
				// View viewOldBackgound;

				if(v.getId()==R.id.et_Stock)
				{
					et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Stock));

				}//txtVwRate
				if(v.getId()==R.id.txtVwRate)
				{
					et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Rate));
					et_ValueOnFocuslost.setError(null);
					if (hmapProductflgPriceAva.get(ProductIdOnClickedEdit).equals("1"))
					{
						EditText ediTextOrder = (EditText) ll_prdct_detal.findViewWithTag("etOrderQty" + "_" + ProductIdOnClickedEdit);
						if(TextUtils.isEmpty(et_ValueOnFocuslost.getText().toString().trim()))
						{
							if (ediTextOrder != null) {

								ediTextOrder.setText("");
							}
						}



					}

				}
				if(v.getId()==R.id.et_SampleQTY)
				{
					et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.SmplQty));

				}
				if(v.getId()==R.id.et_OrderQty)
				{
					et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.OQty));
				}
				if(!viewCurrentBoxValue.equals(et_ValueOnFocuslost.getText().toString().trim()))
				{
					if(v.getId()==R.id.et_Stock)
					{
						et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Stock));

					}

					if(v.getId()==R.id.txtVwRate)
					{
						et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.Rate));

						EditText temped=(EditText) ll_prdct_detal.findViewWithTag("etOrderQty_"+et_ValueOnFocuslost.getTag().toString().split(Pattern.quote("_"))[1]);
						EditText temprt=(EditText) ll_prdct_detal.findViewWithTag("tvRate_"+et_ValueOnFocuslost.getTag().toString().split(Pattern.quote("_"))[1]);


					}
					if(v.getId()==R.id.et_SampleQTY)
					{
						et_ValueOnFocuslost.setHint(ProductInvoiceReview.this.getResources().getString(R.string.SmplQty));
						/*hmapPrdctSmpl.remove(ProductIdOnClickedControl);
						hmapPrdctSmpl.put(ProductIdOnClickedControl, et_ValueOnFocuslost.getText().toString().trim());*/
					}
					if(v.getId()==R.id.et_OrderQty)
					{

						if(hmapPrdctOdrQty!=null && hmapPrdctOdrQty.containsKey(ProductIdOnClickedEdit))
						{
							int originalNetQntty=Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit));
							int totalStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);
							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,et_ValueOnFocuslost,et_ValueOnFocuslost,-1);
							}
						}

						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit))>0)
						{
							if((!hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("") && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99")) && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99.00") && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99.0"))
							{

							}
							else
							{
								EditText temprt=(EditText) ll_prdct_detal.findViewWithTag("tvRate_"+et_ValueOnFocuslostnew.getTag().toString().split(Pattern.quote("_"))[1]);
								temprt.setSelected(true);
								temprt.requestFocus();
								temprt.setCursorVisible(true);
							}
						}




					}


				}
			}

		}

		else
		{


			txtVw_schemeApld.setText("No Scheme Applicable");
			txtVw_schemeApld.setTag("0");

			if(v instanceof EditText)
			{
				//showSoftKeyboard(v);


				EditText edtBox=(EditText) v;

				if (Build.VERSION.SDK_INT >= 11) {
					edtBox.setRawInputType(InputType.TYPE_CLASS_NUMBER);
					edtBox.setTextIsSelectable(true);
				} else {
					edtBox.setRawInputType(InputType.TYPE_NULL);
					edtBox.setFocusable(true);
				}
				//edtBox.setInputType(InputType.TYPE_NULL);

				mCustomKeyboardNumWithoutDecimal.hideCustomKeyboardNum(v);
				mCustomKeyboardNum.hideCustomKeyboardNum(v);



				//ProductIdOnClickedEdit
				ProductIdOnClickedEdit=edtBox.getTag().toString().split(Pattern.quote("_"))[1];
				ed_LastEditextFocusd=edtBox;
				CtaegoryIddOfClickedView=hmapCtgryPrdctDetail.get(ProductIdOnClickedEdit);
				condtnOddEven=hmapProductViewTag.get(CtaegoryIddOfClickedView+"_"+ProductIdOnClickedEdit);
				// View viewParent;
				if(condtnOddEven.equals("even"))
				{
					// viewParent=ll_prdct_detal.findViewWithTag(CtaegoryIddOfClickedView+"_"+ProductIdOnClickedEdit+"_"+"even");
				}
				else
				{
					// viewParent=ll_prdct_detal.findViewWithTag(CtaegoryIddOfClickedView+"_"+ProductIdOnClickedEdit+"_"+"odd");
				}
				// viewParent.setBackgroundResource(R.drawable.edit_text_diable_bg_clicked);
				if(v.getId()==R.id.et_OrderQty)
				{

					mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
					mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

					edtBox.setHint("");
					viewCurrentBoxValue=edtBox.getText().toString().trim();
					if(((ImageView) ll_prdct_detal.findViewWithTag("btnException_"+(ProductIdOnClickedEdit))).getVisibility()==View.VISIBLE)
					{
						isbtnExceptionVisible=1;
					}

				}

				if(v.getId()==R.id.et_SampleQTY)
				{
					mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
					mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

					edtBox.setHint("");



				}
				if(v.getId()==R.id.et_Stock)
				{
					mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
					mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

					edtBox.setHint("");
				}
				if(v.getId()==R.id.txtVwRate)
				{
					mCustomKeyboardNum.registerEditText(edtBox);
					mCustomKeyboardNum.showCustomKeyboard(v);

					edtBox.setHint("");
				}
			}

			if(hmapProductRelatedSchemesList.size()>0)
			{
				if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl))
				{
					fnUpdateSchemeNameOnScehmeControl(ProductIdOnClickedControl);
				}
				else
				{
					//SchemeNameOnScehmeControl="No Scheme Applicable";
					txtVw_schemeApld.setText("No Scheme Applicable");
					txtVw_schemeApld.setTag("0");
				}
			}
			else
			{
				//SchemeNameOnScehmeControl="No Scheme Applicable";
				txtVw_schemeApld.setText("No Scheme Applicable");
				txtVw_schemeApld.setTag("0");
			}




		}

		/*if(v.getId()==R.id.et_OrderQty)
		{
			*//*if(flgnwstausforfocus==false) {
				EditText temped = (EditText) ll_prdct_detal.findViewWithTag("etOrderQty_" + ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[1]);
				if (temped.getText().length() != 0) {

					if ((!hmapProductStandardRate.get(ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[1]).equals("") && !hmapProductStandardRate.get(ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[1]).equals("-99")) && !hmapProductStandardRate.get(ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[1]).equals("-99.00") && !hmapProductStandardRate.get(ed_LastEditextFocusd.getTag().toString().split(Pattern.quote("_"))[1]).equals("-99.0")) {
						orderBookingTotalCalc();
					} else {
						flgnwstausforfocus = true;
					*//**//*ed_LastEditextFocusd.setSelected(true);
					ed_LastEditextFocusd.requestFocus();*//**//*
					}
				}
			}*//*
		}
*/


		orderBookingTotalCalc();


	}

	@Override
	public void onClick(View v) {
	 

	  String ProductIdOnClickedControl=v.getTag().toString().split(Pattern.quote("_"))[1];
	  if(hmapProductRelatedSchemesList.size()>0)
	  {
	   if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl))
	   {
		   
		   fnUpdateSchemeNameOnScehmeControl(ProductIdOnClickedControl);
	   }
	   else
	   {
	    //SchemeNameOnScehmeControl="No Scheme Applicable";
	    txtVw_schemeApld.setText("No Scheme Applicable");
	    txtVw_schemeApld.setTag("0");
	   }
	  }
	  else
	  {
	   //SchemeNameOnScehmeControl="No Scheme Applicable";
	   txtVw_schemeApld.setText("No Scheme Applicable");
	   txtVw_schemeApld.setTag("0");
	  }
	  
	  if(v instanceof EditText)
	  {
	   System.out.println("Abhinav Edit Clkd");
	   edtBox=(EditText) v;
	   if(v.getId()==R.id.et_OrderQty)
	   {
		   mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
		   mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

		   edtBox.setHint("");
	    viewCurrentBoxValue=edtBox.getText().toString().trim();
	    
	    
	   }
	   
	   if(v.getId()==R.id.et_SampleQTY)
	   {
		   mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
		   mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

		   edtBox.setHint("");
	   
	    
	    
	   }
	   if(v.getId()==R.id.et_Stock)
	   {
		   mCustomKeyboardNumWithoutDecimal.registerEditText(edtBox);
		   mCustomKeyboardNumWithoutDecimal.showCustomKeyboard(v);

		   edtBox.setHint("");
	   
	    
	    
	   }
	  }
	}

	private void customAlert(ArrayList<HashMap<String, String>> arrLstHmap ,ArrayList<String[]> listfreeProductQty,final int fieldFlag,final ArrayList<String> listSchemeAllData,final String alrtProductIdOnClicked) {
		
		alertOpens=true;
		//	 HashMap<String, String> hmapProductNameId
			final Dialog dialog = new Dialog(ProductInvoiceReview.this);

			//setting custom layout to dialog
			dialog.setContentView(R.layout.custom_alert);
			dialog.setTitle(ProductInvoiceReview.this.getResources().getString(R.string.FreeOffer));
			
			LinearLayout ll_radio_spinner=(LinearLayout) dialog.findViewById(R.id.ll_radio_spinner);
			//adding text dynamically
			
			
		
			 
			
			 final int imgUnChckdbtn = R.drawable.unchekedradiobutton;
			 final int imgChckdbtn = R.drawable.checkedradiobutton;
			LinearLayout ll_prdctScheme;
		String titleAlrtVal = null;
			String spnr_EditText_Value;
			EditText crntEditTextValue;
			boolean completedAlrt=false;
			 String alrtbodyToShow = null; 
			int count=0;
			boolean benSubBucketType7or10=false;
			
			// key = productId   value=qtyselected+"~"+scheId+"~"+..............
			 final HashMap<String, ArrayList<String>> hashMapSelectionFreeQty=new HashMap<String, ArrayList<String>>();
			 
			final String[] arrayStringSpinner=new String[arrLstHmap.size()];
			final String[] arrayProduct=new String[arrLstHmap.size()];
			
			
			final TextView[] alrtPrvsPrdctSlctd=new TextView[arrLstHmap.size()];
			final Spinner[] alrtPrvsSpnrSlctd=new Spinner[arrLstHmap.size()];
			
			final String schemAllStringForBen7or10=listSchemeAllData.get(0);
			
			final int alrtBenSubBucketTypeFor7or10 =Integer.parseInt(schemAllStringForBen7or10.split(Pattern.quote("~"))[7].toString());
			final int alrtschSlabSubBucketType =Integer.parseInt(schemAllStringForBen7or10.split(Pattern.quote("~"))[5].toString());
			for(int i=0;i<arrLstHmap.size();i++)
			{
				LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View viewScheme=inflater.inflate(R.layout.alrt_scheme,null);
				 String[] arraySpnrVal=listfreeProductQty.get(i);
				ll_prdctScheme=(LinearLayout) viewScheme.findViewById(R.id.ll_prdctScheme);
				final TextView txtVw_scheme=(TextView) viewScheme.findViewById(R.id.txtVw_scheme);
				
				final String schemAllString=listSchemeAllData.get(i);
				String schemeIdFromAllVal=(schemAllString.split(Pattern.quote("~"))[0].toString());
				final	int alrtBenSubBucketType =Integer.parseInt(schemAllString.split(Pattern.quote("~"))[7].toString());
				String descrptionBenType="";
				
				//alrtschSlabSubBucketType
				//1. Product Quantity
				//5. Product Volume
				//2. Invoice Value
				//3. Product Lines
				//4. Product Value
				
				
				//BenSubBucketType
				//1. Free Other Product =
				//2. Discount in Percentage with other product
				//3. Discount in Amount with other product
				//4. Coupons
				//5. Free Same Product
				//6. Discount in Percentage with same product
				//7. Discount in Amount with same product
				//8. Percentage On Invoice
				//9.  Amount On Invoice
				//10. PEr Case
				//alrtbodyToShow
				//titleAlrtVal
				//arraySpnrVal[0]
				if(alrtschSlabSubBucketType==1 || alrtschSlabSubBucketType==5 || alrtschSlabSubBucketType==4)
				{
					if(alrtBenSubBucketType==1 || alrtBenSubBucketType==5)
					{
						benSubBucketType7or10=true;
						titleAlrtVal=ProductInvoiceReview.this.getResources().getString(R.string.DefaultFreeQty)+arraySpnrVal[0];

						alrtbodyToShow=ProductInvoiceReview.this.getResources().getString(R.string.SelectActualFreeQty);
			
					}
					
				else if(alrtBenSubBucketType==3 || alrtBenSubBucketType==7)
					{
						benSubBucketType7or10=true;
						

						titleAlrtVal=ProductInvoiceReview.this.getResources().getString(R.string.DefaultDiscValue)+arraySpnrVal[0];

						alrtbodyToShow=ProductInvoiceReview.this.getResources().getString(R.string.SelectActualValue);
			
					}
					
				else if(alrtBenSubBucketType==2 || alrtBenSubBucketType==6)
				{
					benSubBucketType7or10=true;
					

					titleAlrtVal=ProductInvoiceReview.this.getResources().getString(R.string.DefaultDisc)+arraySpnrVal[0];

					alrtbodyToShow=ProductInvoiceReview.this.getResources().getString(R.string.SelectActualDisc);
		
				}
				else if(alrtBenSubBucketType==10)
				{
					
					
					
						benSubBucketType7or10=true;
						titleAlrtVal=ProductInvoiceReview.this.getResources().getString(R.string.DefaultFreeVal)+arraySpnrVal[0];

						alrtbodyToShow=ProductInvoiceReview.this.getResources().getString(R.string.SelectActualValue);
					
				}
				}
				
				else
				{
					benSubBucketType7or10=true;
					titleAlrtVal=ProductInvoiceReview.this.getResources().getString(R.string.DefaultVal)+arraySpnrVal[0];

					alrtbodyToShow=ProductInvoiceReview.this.getResources().getString(R.string.SelectActualValue);
				}
				
			
				
				txtVw_scheme.setTag(i);
				boolean ifChckdRadio=false;
				 final HashMap<String, String> hmapProductNameId=arrLstHmap.get(i);
				
				 txtVw_scheme.setText(titleAlrtVal);
				String hMapScheme="hMapScheme";
				if(benSubBucketType7or10)
				{

					
					
			
					String[] productGivenDiscount=dbengine.getValOfSchemeAlrt(storeID,alrtProductIdOnClicked,schemAllString.split(Pattern.quote("~"))[1].toString(),strGlobalOrderID,TmpInvoiceCodePDA);
				
					LayoutInflater inflater2=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					final View viewProduct=inflater2.inflate(R.layout.row_free_prodct_qty,null);
					viewProduct.setTag(alrtProductIdOnClicked);
					 final TextView tv_prdct_name=(TextView) viewProduct.findViewById(R.id.tv_prdct_name);
					 tv_prdct_name.setTag(i);
					tv_prdct_name.setText(alrtbodyToShow);
				//	tv_prdct_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
				
					
					 final EditText editText_Qty=(EditText) viewProduct.findViewById(R.id.editText_Qty);
					 final Spinner spnrMinMax=(Spinner) viewProduct.findViewById(R.id.spnr_Qty);
					
					if(fieldFlag==1)
					{
						if(spnrMinMax.getVisibility()==View.GONE)
						{
							spnrMinMax.setVisibility(View.VISIBLE);
							editText_Qty.setVisibility(View.GONE);
							
						}
						spnrMinMax.setVisibility(View.VISIBLE);
						final ArrayAdapter adapterProduct=new ArrayAdapter(this, android.R.layout.simple_spinner_item,arraySpnrVal);
					 adapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					 spnrMinMax.setAdapter(adapterProduct);
					 if(!productGivenDiscount[0].equals("No Data"))
					 {
						 spnrMinMax.setSelection(Integer.valueOf(productGivenDiscount[1].toString()));
					 }
					
					 
					 //dbengine.insertSchemeAlrtVal(storeID,arrayProductIdToDefault[0],"0",hmapPrdctIdPrdctName.get(arrayProductIdToDefault[0]));
					 
					 spnrMinMax.setEnabled(true);
						
					 spnrMinMax.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> parent,
									View view, int position, long id) {
								
								String strngPrdctCompltValue=spnrMinMax.getSelectedItem().toString();
								
								arrayStringSpinner[Integer.parseInt(tv_prdct_name.getTag().toString())]=strngPrdctCompltValue;
								
								for (Entry<String, String> entry : hmapProductNameId.entrySet())
								{
									
										dbengine.insertSchemeAlrtVal(storeID, entry.getValue(),strngPrdctCompltValue , tv_prdct_name.getText().toString(),String.valueOf(adapterProduct.getPosition(strngPrdctCompltValue)),schemAllStringForBen7or10.split(Pattern.quote("~"))[1].toString(),schemAllStringForBen7or10.split(Pattern.quote("~"))[0].toString(),strGlobalOrderID,TmpInvoiceCodePDA);
									
									
								}
									
								
								
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								// TODO Auto-generated method stub
								
							}
						});
				}
					
					else
					{
						editText_Qty.setVisibility(View.VISIBLE);
						spnrMinMax.setVisibility(View.GONE);
						 editText_Qty.setEnabled(false);
					}
					 
				count++;
				
				
			
				
			
				
				ll_prdctScheme.addView(viewProduct);
				
				}
				
				ll_radio_spinner.addView(viewScheme);
			}
				
			
			
			//adding button click event
			Button dismissButton = (Button) dialog.findViewById(R.id.btnDisplay);
			dismissButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

						 orderBookingTotalCalc();
						//saveFreeProductDataWithSchemeToDatabase(hashMapSelectionFreeQty,alrtProductIdOnClicked);
						
						dialog.dismiss();
						alertOpens=false;
						
					
					
				
					
				}
			});
			dialog.setCanceledOnTouchOutside(false);
			dialog.setCancelable(false);
			
			dialog.show();
			
			
			
		}

	private void setInvoiceTableView() {
		
		 LayoutInflater inflater = getLayoutInflater();
		 final View row123 = (View)inflater.inflate(R.layout.activity_detail_scheme, ll_scheme_detail , false);
		
			decimalFormat.applyPattern(pattern);
			//tvCurrentProdName = (TextView) findViewById(R.id.textView1_schemeVAL1111);

		//tv_NetInvAfterDiscount
		tvCredAmtVAL =  (TextView) row123.findViewById(R.id.textView1_CredAmtVAL);
			tvINafterCredVAL =  (TextView) row123.findViewById(R.id.textView1_INafterCredVAL);
			textView1_CredAmtVAL_new = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL_new);

		tvTotalAmtCollected=(TextView) row123.findViewById(R.id.tvTotalAmtCollected);
			tv_NetInvValue = (TextView)row123.findViewById(R.id.tv_NetInvValue);
			tvTAmt = (TextView)row123.findViewById(R.id.textView1_v2);
			tvDis = (TextView)row123.findViewById(R.id.textView1_v3);
			tv_GrossInvVal = (TextView)row123.findViewById(R.id.tv_GrossInvVal);
			tvFtotal = (TextView)row123.findViewById(R.id.textView1_v5);
			tvAddDisc =  (TextView)row123.findViewById(R.id.textView1_AdditionalDiscountVAL);
			tv_NetInvAfterDiscount =  (TextView)row123.findViewById(R.id.tv_NetInvAfterDiscount);
			
			tvAmtPrevDueVAL =  (TextView)row123.findViewById(R.id.tvAmtPrevDueVAL);
			tvAmtOutstandingVAL =  (TextView)row123.findViewById(R.id.tvAmtOutstandingVAL);
			etAmtCollVAL = (EditText)row123.findViewById(R.id.etAmtCollVAL);
			
			tvNoOfCouponValue = (EditText)row123.findViewById(R.id.tvNoOfCouponValue);
			 txttvCouponAmountValue = (EditText)row123.findViewById(R.id.tvCouponAmountValue);

		tvPreAmtOutstandingVALNew=(TextView)row123.findViewById(R.id.tvPreAmtOutstandingVALNew);
		 ll_scheme_detail.addView(row123);


		Double outstandingvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
		tvPreAmtOutstandingVALNew.setText(""+outstandingvalue);

		Double TotalAmtCollected=dbengine.fnTotCollectionAmtAgainstStore(storeID,TmpInvoiceCodePDA,StoreVisitCode);
		tvTotalAmtCollected.setText(""+TotalAmtCollected);
		 orderBookingTotalCalc();
		
	}


	/*public void orderBookingTotalCalc()
	{
		Double StandardRate=0.00;
		Double StandardRateBeforeTax=0.00;
		Double StandardTax=0.00;
		Double ActualRateAfterDiscountBeforeTax=0.00;
		Double DiscountAmount=0.00;
		Double ActualTax=0.00;
		Double ActualRateAfterDiscountAfterTax=0.00;

		String PrdMaxValuePercentageDiscount="";
		String PrdMaxValueFlatDiscount="";

		Double TotalFreeQTY=0.00;
		Double TotalProductLevelDiscount=0.00;
		Double TotalOrderValBeforeTax=0.00;
		Double TotAdditionaDiscount=0.00;
		Double TotOderValueAfterAdditionaDiscount=0.00;
		Double TotTaxAmount=0.00;
		Double TotOderValueAfterTax=0.00;

		int prdListCount =hmapPrdctIdPrdctNameVisible.size();

		for (int index=0; index < prdListCount; index++){
			View vRow = ll_prdct_detal.getChildAt(index);

			int PCateIdDetails=Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
			String ProductID=((TextView)(vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];

			if(hmapPrdctOdrQty.containsKey(ProductID))
			{
				((TextView)(vRow).findViewById(R.id.tv_FreeQty)).setText(hmapPrdctFreeQty.get(ProductID).toString());
				TotalFreeQTY=TotalFreeQTY+Integer.parseInt(hmapPrdctFreeQty.get(ProductID));
				hmapProductTaxValue.put(ProductID, "0.00");
				hmapMinDlvrQtyQPTaxAmount.put(ProductID, "0.00");
				((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText("0.00");
				if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>0)
				{
						*//*StandardRate=Double.parseDouble(hmapProductMRP.get(ProductID))/((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);*//*
					if(Integer.parseInt(hmapProductflgPriceAva.get(ProductID))>0)
					{
						StandardRate=Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
					}
					else
					{
						StandardRate=Double.parseDouble(hmapProductStandardRate.get(ProductID));
						StandardRateBeforeTax=Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID));
						StandardTax=Double.parseDouble(hmapProductStandardTax.get(ProductID));

					}

					if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
					{
						if(hmapMinDlvrQty.containsKey(ProductID))
						{
							if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
							{
								StandardRateBeforeTax=Double.parseDouble(hmapMinDlvrQtyQPBT.get(ProductID));
								StandardTax=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));
							}
						}
					}



					PrdMaxValuePercentageDiscount=dbengine.fnctnGetHighestDiscountPercentge(ProductID, storeID);
					PrdMaxValueFlatDiscount=dbengine.fnctnGetHighestDiscountAmount(ProductID, storeID);
					int BenifitRowIdPercentageDiscount=0;
					int BenifitRowIdFlatDiscount=0;
					//Double per=Double.parseDouble(hmapProductVolumePer.get(ProductID));

					Double per;
					String perProduct=dbengine.fnctnGetfreePerUnitVol(ProductID, storeID);
					if(perProduct.equals(""))
					{
						per=0.0;
					}
					else
					{
						per=Double.parseDouble((perProduct.split(Pattern.quote("^"))[0]));
					}

					String value=hmapPrdctVolRatTax.get(ProductID).toString();
					StringTokenizer tokens=new StringTokenizer(value,Pattern.quote("^"));
					//Volume^Rate^TaxAmount
					String prdVolume = tokens.nextElement().toString();

					StandardRate=Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));
					//		((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(hmapProductStandardRateBeforeTax.get(ProductID));
					if(PrdMaxValuePercentageDiscount.equals(""))
					{
						PrdMaxValuePercentageDiscount="0.00";
					}
					else
					{
						BenifitRowIdPercentageDiscount=Integer.parseInt(PrdMaxValuePercentageDiscount.split(Pattern.quote("^"))[1]);
						PrdMaxValuePercentageDiscount=PrdMaxValuePercentageDiscount.split(Pattern.quote("^"))[0];
					}
					if(PrdMaxValueFlatDiscount.equals(""))
					{
						PrdMaxValueFlatDiscount="0.00";
					}else
					{

						if(per.intValue()!=0)
						{
							//volume
					    		*//*int prdQty=Integer.parseInt(hmapPrdctOdrQty.get(ProductID));
					    		Double perVoume=per;
					    		Double productSingleUnitVolume=Double.parseDouble(prdVolume);
					    		Double prodPuchasedQtyBasedVolume=productSingleUnitVolume*prdQty;
					    		int prdNoOfVolumeCount=(prodPuchasedQtyBasedVolume.intValue()/perVoume.intValue());*//*

							BenifitRowIdFlatDiscount=Integer.parseInt(PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[1]);
							PrdMaxValueFlatDiscount=PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[0];
						    	*//*int finalValue;
						    	if(prdNoOfVolumeCount!=0)
						    	{
						    		finalValue=Integer.parseInt(PrdMaxValueFlatDiscount);//prdNoOfVolumeCount*
						    	}
						    	else
						    	{
						    		finalValue=0;
						    	}

						    	if(prdNoOfVolumeCount!=0)
						    	{
						    		finalValue=Integer.parseInt(PrdMaxValueFlatDiscount);
						    	}
						    	else
						    	{
						    		finalValue=prdNoOfVolumeCount*Integer.parseInt(PrdMaxValueFlatDiscount);
						    	}

						    	PrdMaxValueFlatDiscount=""+finalValue;*//*
						}
						else
						{
							BenifitRowIdFlatDiscount=Integer.parseInt(PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[1]);
							PrdMaxValueFlatDiscount=PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[0];
						}
					}
					if(!PrdMaxValueFlatDiscount.equals("0.00") || !PrdMaxValuePercentageDiscount.equals("0.00"))
					{
						if(Double.parseDouble(PrdMaxValuePercentageDiscount)>=Double.parseDouble(PrdMaxValueFlatDiscount))
						{
							//If Percentage Discount is greater the FlatAmt Code Starts Here
							//If Percentage Discount is greater the FlatAmt Code Starts Here
							ActualRateAfterDiscountBeforeTax=StandardRateBeforeTax-(StandardRateBeforeTax*(((Double.parseDouble(PrdMaxValuePercentageDiscount)/100))));
							DiscountAmount=StandardRateBeforeTax-ActualRateAfterDiscountBeforeTax;
							ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
							ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax+ActualTax;//ActualRateAfterDiscountBeforeTax*((Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));

							Double DiscAmtOnPreQtyBasic=DiscountAmount*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

							Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
							DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
							hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
							TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
							TotTaxAmount=TotTaxAmount+(ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

							Double TaxValue=ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
							hmapProductTaxValue.put(ProductID, ""+TaxValue);

							if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
							{
								hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
							}

							Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
							OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
							hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
							TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));//

							TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;

							//If Percentage Discount is greater the FlatAmt Code Ends Here
						}
						else
						{
							//If Flat Amount is greater the Percentage Code Starts Here
							ActualRateAfterDiscountBeforeTax=(StandardRateBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)))-Double.parseDouble(PrdMaxValueFlatDiscount);
							DiscountAmount=Double.parseDouble(PrdMaxValueFlatDiscount);
							ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
							ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax*(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
							Double DiscAmtOnPreQtyBasic=0.00;
							if(per.intValue()>0)
							{
								DiscAmtOnPreQtyBasic=DiscountAmount;/*//*((Double.parseDouble(hmapPrdctOdrQty.get(ProductID))*Double.parseDouble(prdVolume))/per);
							}
							else
							{
								DiscAmtOnPreQtyBasic=DiscountAmount;/*//*((Double.parseDouble(hmapPrdctOdrQty.get(ProductID))));
							}
							Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
							DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
							hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
							TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
							TotTaxAmount=TotTaxAmount+(ActualTax);/*//* Double.parseDouble(hmapPrdctOdrQty.get(ProductID)

							Double TaxValue=ActualTax;/*//* Double.parseDouble(hmapPrdctOdrQty.get(ProductID))
							TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
							hmapProductTaxValue.put(ProductID, ""+TaxValue);
							if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
							{
								hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
							}
							Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax;/*//*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
							OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
							hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
							TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax);/*//*Double.parseDouble(hmapPrdctOdrQty.get(ProductID))
							TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;
							//If Flat Amount is greater the Percentage Code Ends Here
						}
					}
					else
					{
						//If No Percentage Discount or Flat Discount is Applicable Code Starts Here
						ActualRateAfterDiscountBeforeTax=StandardRateBeforeTax;
						DiscountAmount=0.00;
						ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
						ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax*(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));

						Double DiscAmtOnPreQtyBasic=DiscountAmount*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

						Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
						DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
						((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
						hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);

						TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
						TotTaxAmount=TotTaxAmount+(ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

						Double TaxValue=ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
						hmapProductTaxValue.put(ProductID, ""+TaxValue);
						if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
						{
							hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
						}
						Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
						OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
						((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
						hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
						TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));
						TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;
						//If No Percentage Discount or Flat Discount is Applicable Code Ends Here
					}

				}
			}

		}
		//Now the its Time to Show the OverAll Summary Code Starts Here

		tvFtotal.setText((""+ TotalFreeQTY).trim());

		TotalProductLevelDiscount=Double.parseDouble(new DecimalFormat("##.##").format(TotalProductLevelDiscount));
		tvDis.setText((""+ TotalProductLevelDiscount).trim());

		TotalOrderValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(TotalOrderValBeforeTax));
		tv_NetInvValue.setText((""+ TotalOrderValBeforeTax).trim());

		String percentBenifitMax=dbengine.fnctnGetMaxAssignedBen8DscntApld1(storeID,strGlobalOrderID);
		Double percentMax=0.00;
		Double percentMaxGross=0.0;
		Double amountMaxGross=0.0;

		String amountBenfitMaxGross=dbengine.fnctnGetMaxAssignedBen9DscntApld2(storeID,strGlobalOrderID);
		String percentBenifitMaxGross=dbengine.fnctnGetMaxAssignedBen8DscntApld2(storeID,strGlobalOrderID);

		if(percentBenifitMaxGross.equals(""))
		{
			percentMaxGross=0.0;
		}
		else
		{
			percentMaxGross=Double.parseDouble(percentBenifitMaxGross.split(Pattern.quote("^"))[0]);
		}
		if(percentBenifitMax.equals("") )
		{
			percentMax=0.00;
		}
		else
		{
			percentMax=Double.parseDouble(percentBenifitMax.split(Pattern.quote("^"))[0]);
		}

		String amountBenifitMax=dbengine.fnctnGetMaxAssignedBen9DscntApld1(storeID,strGlobalOrderID);
		Double amountMax=0.00;
		if(percentBenifitMax.equals(""))
		{
			amountMax=0.0;
		}
		else
		{
			amountMax=Double.parseDouble(amountBenifitMax.split(Pattern.quote("^"))[0]);
		}


		tvAddDisc.setText(""+ "0.00");

		tv_NetInvAfterDiscount.setText(""+ TotalOrderValBeforeTax);

		TotTaxAmount=Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
		tvTAmt.setText(""+ TotTaxAmount);

		Double totalGrossVALMaxPercentage=TotalOrderValBeforeTax-TotalOrderValBeforeTax*(percentMaxGross/100);
		Double totalGrossrVALMaxAmount=TotalOrderValBeforeTax-amountMaxGross;
		Double totalGrossVALAfterDiscount = 0.0;
		if(totalGrossVALMaxPercentage!=totalGrossrVALMaxAmount)
		{
			totalGrossVALAfterDiscount=Math.min(totalGrossrVALMaxAmount, totalGrossVALMaxPercentage);
		}
		else
		{
			totalGrossVALAfterDiscount=totalGrossrVALMaxAmount;
		}

		if(totalGrossVALAfterDiscount==totalGrossrVALMaxAmount && totalGrossrVALMaxAmount!=0.0)
		{
			dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(amountBenfitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID);
		}
		else if(totalGrossVALAfterDiscount==totalGrossVALMaxPercentage && percentMaxGross!=0.0)
		{
			dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(percentBenifitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID);
		}

		Double GrossInvValue=totalGrossVALAfterDiscount + TotTaxAmount;
		GrossInvValue=Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
		tv_GrossInvVal.setText(""+GrossInvValue);
		//Now the its Time to Show the OverAll Summary Code Starts Here
	}*/
	public Double fnOnlyCalculateWholeSaleValue(int flgPriceApplyDiscountLevelType) {
		Double StandardRate = 0.00;
		Double StandardRateBeforeTax = 0.00;
		Double StandardTax = 0.00;
		Double ActualRateAfterDiscountBeforeTax = 0.00;
		Double DiscountAmount = 0.00;
		Double ActualTax = 0.00;
		Double ActualRateAfterDiscountAfterTax = 0.00;

		String PrdMaxValuePercentageDiscount = "";
		String PrdMaxValueFlatDiscount = "";

		Double TotalFreeQTY = 0.00;
		Double TotalProductLevelDiscount = 0.00;
		Double TotalOrderValBeforeTax = 0.00;
		Double TotAdditionaDiscount = 0.00;
		Double TotOderValueAfterAdditionaDiscount = 0.00;
		Double TotTaxAmount = 0.00;
		Double TotOderValueAfterTax = 0.00;

		int flgWholeSellApplicable = 0;
		Double PriceRangeWholeSellApplicable = 0.00;

		int prdListCount = hmapPrdctIdPrdctNameVisible.size();

		for (int index = 0; index < prdListCount; index++) {
			View vRow = ll_prdct_detal.getChildAt(index);

			int PCateIdDetails = Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
			String ProductID = ((TextView) (vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];
			//((TextView) (vRow).findViewById(R.id.txtVwRate)).setText("" + hmapStandardRateWholeSale.get(ProductID).toString());
			if (hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.containsKey(ProductID)) {
				if (hmapPrdctOdrQty.containsKey(ProductID)) {
					flgWholeSellApplicable = Integer.parseInt(hmapflgWholeSellApplicable.get(ProductID));
					PriceRangeWholeSellApplicable = Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID));
					hmapPrdctFreeQty.put(ProductID, "0");
					((TextView) (vRow).findViewById(R.id.tv_FreeQty)).setText(hmapPrdctFreeQty.get(ProductID).toString());
					TotalFreeQTY = TotalFreeQTY + Integer.parseInt(hmapPrdctFreeQty.get(ProductID));
					/*hmapProductTaxValue.put(ProductID, "0.00");
					hmapMinDlvrQtyQPTaxAmount.put(ProductID, "0.00");*/

					if (Integer.parseInt(hmapPrdctOdrQty.get(ProductID)) > 0) {

						if (flgPriceApplyDiscountLevelType == 1) {
							if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
								StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
								StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
								StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);

							} else {
								StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));
								StandardRateBeforeTax = Double.parseDouble(hmapStandardRateBeforeTaxWholeSell.get(ProductID));
								StandardTax = Double.parseDouble(hmapStandardTaxWholeSale.get(ProductID));

							}

						}
						if (flgPriceApplyDiscountLevelType == 2) {
							if (hmapflgWholeSellApplicable.containsKey(ProductID)) {
								if (Double.parseDouble(hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.get(ProductID)) >= Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID))) {
									if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
										StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
										StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
										StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);

									} else {
										StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));
										StandardRateBeforeTax = Double.parseDouble(hmapStandardRateBeforeTaxWholeSell.get(ProductID));
										StandardTax = Double.parseDouble(hmapStandardTaxWholeSale.get(ProductID));


									}
								} else {
									if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
										StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
										StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
										StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
									} else {
										StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));
										StandardRateBeforeTax = Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID));
										StandardTax = Double.parseDouble(hmapProductStandardTax.get(ProductID));

									}
								}
							} else {
								if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
									StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
									StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
									StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
								} else {
									StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));
									StandardRateBeforeTax = Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID));
									StandardTax = Double.parseDouble(hmapProductStandardTax.get(ProductID));

								}
							}
						}


						String value = hmapPrdctVolRatTax.get(ProductID).toString();
						StringTokenizer tokens = new StringTokenizer(value, Pattern.quote("^"));
						//Volume^Rate^TaxAmount
						String prdVolume = tokens.nextElement().toString();

						StandardRate = Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));

						//If No Percentage Discount or Flat Discount is Applicable Code Starts Here
						ActualRateAfterDiscountBeforeTax = StandardRateBeforeTax;
						DiscountAmount = 0.00;
						ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
						ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

						Double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

						Double DiscAmtOnPreQtyBasicToDisplay = 0.00;//DiscAmtOnPreQtyBasic;
						DiscAmtOnPreQtyBasicToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));


						TotalProductLevelDiscount = TotalProductLevelDiscount + DiscAmtOnPreQtyBasic;
						TotTaxAmount = TotTaxAmount + (ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

						Double TaxValue = ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						TaxValue = Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));


						Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

						Double OrderValPrdQtyBasisToDisplay = OrderValPrdQtyBasis;
						OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));

						TotalOrderValBeforeTax = TotalOrderValBeforeTax + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

						TotOderValueAfterTax = TotOderValueAfterTax + OrderValPrdQtyBasis;
						//If No Percentage Discount or Flat Discount is Applicable Code Ends Here
						//}

					}

				}
			}


		}
		//Now the its Time to Show the OverAll Summary Code Starts Here


		TotalProductLevelDiscount = Double.parseDouble(new DecimalFormat("##.##").format(TotalProductLevelDiscount));
		TotalOrderValBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(TotalOrderValBeforeTax));

		TotTaxAmount = Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
		Double totalGrossVALAfterDiscount = TotalOrderValBeforeTax;//0.0;

		Double GrossInvValue = TotOderValueAfterTax;// + TotTaxAmount;
		GrossInvValue = Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
		//Now the its Time to Show the OverAll Summary Code Starts Here
		return  GrossInvValue;
	}

	public void orderBookingTotalCalc()
	{

		Double StandardRate=0.00;
		Double StandardRateBeforeTax=0.00;
		Double StandardTax=0.00;
		Double ActualRateAfterDiscountBeforeTax=0.00;
		Double DiscountAmount=0.00;
		Double ActualTax=0.00;
		Double ActualRateAfterDiscountAfterTax=0.00;

		String PrdMaxValuePercentageDiscount="";
		String PrdMaxValueFlatDiscount="";

		Double TotalFreeQTY=0.00;
		Double TotalProductLevelDiscount=0.00;
		Double TotalOrderValBeforeTax=0.00;
		Double TotAdditionaDiscount=0.00;
		Double TotOderValueAfterAdditionaDiscount=0.00;
		Double TotTaxAmount=0.00;
		Double TotOderValueAfterTax=0.00;

		int flgWholeSellApplicable=0;
		Double PriceRangeWholeSellApplicable=0.00;

		int prdListCount =hmapPrdctIdPrdctNameVisible.size();
		//tvRateHeading.setText("Rate-S");
		for (int index=0; index < prdListCount; index++){
			View vRow = ll_prdct_detal.getChildAt(index);

			int PCateIdDetails=Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
			String ProductID=((TextView)(vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];

			int LastStockQty=0;
			int LastExecutionQty=0;
			int CurrentStockQty=0;
			if(hmapProductIdLastStock.containsKey(ProductID))
			{
				LastStockQty=Integer.parseInt(hmapProductIdLastStock.get(ProductID));
			}
			if(hampGetLastProductExecution.containsKey(ProductID))
			{
				LastExecutionQty=Integer.parseInt(hampGetLastProductExecution.get(ProductID));
			}
			if(hmapProductIdStock.containsKey(ProductID))
			{
				CurrentStockQty=Integer.parseInt(hmapProductIdStock.get(ProductID));
			}
			int PStoreSale=((LastStockQty+LastExecutionQty)-CurrentStockQty);
			if(PStoreSale<0)
			{
				PStoreSale=0;
			}
			((EditText) (vRow).findViewById(R.id.et_StoreSale)).setText("" +PStoreSale );
			((EditText) (vRow).findViewById(R.id.et_StoreSale)).setEnabled(false);


			if(hmapPrdctOdrQty.containsKey(ProductID))
			{


				flgWholeSellApplicable=Integer.parseInt(hmapflgWholeSellApplicable.get(ProductID));
				PriceRangeWholeSellApplicable=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID));

				((TextView)(vRow).findViewById(R.id.tv_FreeQty)).setText(hmapPrdctFreeQty.get(ProductID).toString());
				TotalFreeQTY=TotalFreeQTY+Integer.parseInt(hmapPrdctFreeQty.get(ProductID));
				//hmapProductTaxValue.put(ProductID, "0.00");
				//hmapMinDlvrQtyQPTaxAmount.put(ProductID, "0.00");
				((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText("0.00");
				//((TextView)(vRow).findViewById(R.id.tv_UnitQty)).setText("0.00");
				if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>0)
				{
					Double OverAllPerBaseQty=Double.parseDouble (hmapPrdctOdrQty.get(ProductID))/Double.parseDouble (hmapPerBaseQty.get(ProductID));
					OverAllPerBaseQty=Double.parseDouble(new DecimalFormat("##.##").format(OverAllPerBaseQty));
				//	((TextView)(vRow).findViewById(R.id.tv_UnitQty)).setText(""+OverAllPerBaseQty);

						/*StandardRate=Double.parseDouble(hmapProductMRP.get(ProductID))/((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);*/
					((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapProductStandardRate.get(ProductID));
						if(PriceApplyDiscountLevelType!=0)
					{


						Double ProductOrderValueBeforeWholeSaleBeforeTax=Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID))*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						ProductOrderValueBeforeWholeSaleBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ProductOrderValueBeforeWholeSaleBeforeTax));
						hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.put(ProductID,""+ProductOrderValueBeforeWholeSaleBeforeTax);
					}
					if(Integer.parseInt(hmapProductflgPriceAva.get(ProductID))>0)
					{
						StandardRate=Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
					}
					else
					{
						StandardRate=Double.parseDouble(hmapProductStandardRate.get(ProductID));
						StandardRateBeforeTax=Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID));
						StandardTax=Double.parseDouble(hmapProductStandardTax.get(ProductID));

					}

					if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
					{
						if(hmapMinDlvrQty.containsKey(ProductID))
						{
							if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
							{
								StandardRateBeforeTax=Double.parseDouble(hmapMinDlvrQtyQPBT.get(ProductID));
								StandardTax=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));
							}
						}
					}



					PrdMaxValuePercentageDiscount=dbengine.fnctnGetHighestDiscountPercentge(ProductID, storeID,TmpInvoiceCodePDA);
					PrdMaxValueFlatDiscount=dbengine.fnctnGetHighestDiscountAmount(ProductID, storeID,TmpInvoiceCodePDA);
					int BenifitRowIdPercentageDiscount=0;
					int BenifitRowIdFlatDiscount=0;
					//Double per=Double.parseDouble(hmapProductVolumePer.get(ProductID));

					Double per;
					String perProduct=dbengine.fnctnGetfreePerUnitVol(ProductID, storeID,TmpInvoiceCodePDA);
					if(perProduct.equals(""))
					{
						per=0.0;
					}
					else
					{
						per=Double.parseDouble((perProduct.split(Pattern.quote("^"))[0]));
					}

					String value=hmapPrdctVolRatTax.get(ProductID).toString();
					StringTokenizer tokens=new StringTokenizer(value,Pattern.quote("^"));
					//Volume^Rate^TaxAmount
					String prdVolume = tokens.nextElement().toString();

					StandardRate=Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));
					//		((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(hmapProductStandardRateBeforeTax.get(ProductID));
					if(PrdMaxValuePercentageDiscount.equals(""))
					{
						PrdMaxValuePercentageDiscount="0.00";
					}
					else
					{
						BenifitRowIdPercentageDiscount=Integer.parseInt(PrdMaxValuePercentageDiscount.split(Pattern.quote("^"))[1]);
						PrdMaxValuePercentageDiscount=PrdMaxValuePercentageDiscount.split(Pattern.quote("^"))[0];
					}
					if(PrdMaxValueFlatDiscount.equals(""))
					{
						PrdMaxValueFlatDiscount="0.00";
					}else
					{

						if(per.intValue()!=0)
						{
							//volume
					    		/*int prdQty=Integer.parseInt(hmapPrdctOdrQty.get(ProductID));
					    		Double perVoume=per;
					    		Double productSingleUnitVolume=Double.parseDouble(prdVolume);
					    		Double prodPuchasedQtyBasedVolume=productSingleUnitVolume*prdQty;
					    		int prdNoOfVolumeCount=(prodPuchasedQtyBasedVolume.intValue()/perVoume.intValue());*/

							BenifitRowIdFlatDiscount=Integer.parseInt(PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[1]);
							PrdMaxValueFlatDiscount=PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[0];
						    	/*int finalValue;
						    	if(prdNoOfVolumeCount!=0)
						    	{
						    		finalValue=Integer.parseInt(PrdMaxValueFlatDiscount);//prdNoOfVolumeCount*
						    	}
						    	else
						    	{
						    		finalValue=0;
						    	}

						    	if(prdNoOfVolumeCount!=0)
						    	{
						    		finalValue=Integer.parseInt(PrdMaxValueFlatDiscount);
						    	}
						    	else
						    	{
						    		finalValue=prdNoOfVolumeCount*Integer.parseInt(PrdMaxValueFlatDiscount);
						    	}

						    	PrdMaxValueFlatDiscount=""+finalValue;*/
						}
						else
						{
							BenifitRowIdFlatDiscount=Integer.parseInt(PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[1]);
							PrdMaxValueFlatDiscount=PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[0];
						}
					}
					if(!PrdMaxValueFlatDiscount.equals("0.00") || !PrdMaxValuePercentageDiscount.equals("0.00"))
					{
						if(Double.parseDouble(PrdMaxValuePercentageDiscount)>=Double.parseDouble(PrdMaxValueFlatDiscount))
						{
							//If Percentage Discount is greater the FlatAmt Code Starts Here
							ActualRateAfterDiscountBeforeTax=StandardRateBeforeTax-(StandardRateBeforeTax*(((Double.parseDouble(PrdMaxValuePercentageDiscount)/100))));
							DiscountAmount=StandardRateBeforeTax-ActualRateAfterDiscountBeforeTax;
							ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
							ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax+ActualTax;//ActualRateAfterDiscountBeforeTax*((Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));

							Double DiscAmtOnPreQtyBasic=DiscountAmount*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

							Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
							DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
							hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
							TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
							TotTaxAmount=TotTaxAmount+(ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

							Double TaxValue=ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
							hmapProductTaxValue.put(ProductID, ""+TaxValue);

							if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
							{
								hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
							}

							Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
							OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
							hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
							TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));//

							TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;

							//If Percentage Discount is greater the FlatAmt Code Ends Here
						}
						else
						{
							//If Flat Amount is greater the Percentage Code Starts Here
							ActualRateAfterDiscountBeforeTax=(StandardRateBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)))-Double.parseDouble(PrdMaxValueFlatDiscount);
							DiscountAmount=Double.parseDouble(PrdMaxValueFlatDiscount);
							ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
							ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax*(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
							Double DiscAmtOnPreQtyBasic=0.00;
							if(per.intValue()>0)
							{
								DiscAmtOnPreQtyBasic=DiscountAmount;//*((Double.parseDouble(hmapPrdctOdrQty.get(ProductID))*Double.parseDouble(prdVolume))/per);
							}
							else
							{
								DiscAmtOnPreQtyBasic=DiscountAmount;//*((Double.parseDouble(hmapPrdctOdrQty.get(ProductID))));
							}
							Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
							DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
							hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
							TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
							TotTaxAmount=TotTaxAmount+(ActualTax);//* Double.parseDouble(hmapPrdctOdrQty.get(ProductID)

							Double TaxValue=ActualTax;//* Double.parseDouble(hmapPrdctOdrQty.get(ProductID))
							TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
							hmapProductTaxValue.put(ProductID, ""+TaxValue);
							if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
							{
								hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
							}
							Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax;//*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
							OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
							((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
							hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
							TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax);//*Double.parseDouble(hmapPrdctOdrQty.get(ProductID))
							TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;
							//If Flat Amount is greater the Percentage Code Ends Here
						}
					}
					else
					{
						//If No Percentage Discount or Flat Discount is Applicable Code Starts Here
						ActualRateAfterDiscountBeforeTax=StandardRateBeforeTax;
						DiscountAmount=0.00;
						ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
						ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax*(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));

						Double DiscAmtOnPreQtyBasic=DiscountAmount*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

						Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
						DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
						((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
						hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);

						TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
						TotTaxAmount=TotTaxAmount+(ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

						Double TaxValue=ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
						hmapProductTaxValue.put(ProductID, ""+TaxValue);
						if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
						{
							hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
						}
						Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
						OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
						((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
						hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
						TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));
						TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;
						//If No Percentage Discount or Flat Discount is Applicable Code Ends Here
					}

				}

			}

		}
		//Now the its Time to Show the OverAll Summary Code Starts Here

		tvFtotal.setText((""+ TotalFreeQTY).trim());

		TotalProductLevelDiscount=Double.parseDouble(new DecimalFormat("##.##").format(TotalProductLevelDiscount));
		tvDis.setText((""+ TotalProductLevelDiscount).trim());

		TotalOrderValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(TotalOrderValBeforeTax));
		tv_NetInvValue.setText((""+ TotalOrderValBeforeTax).trim());

		String percentBenifitMax=dbengine.fnctnGetMaxAssignedBen8DscntApld1(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
		Double percentMax=0.00;
		Double percentMaxGross=0.0;
		Double amountMaxGross=0.0;

		String amountBenfitMaxGross=dbengine.fnctnGetMaxAssignedBen9DscntApld2(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
		String percentBenifitMaxGross=dbengine.fnctnGetMaxAssignedBen8DscntApld2(storeID,strGlobalOrderID,TmpInvoiceCodePDA);

		if(percentBenifitMaxGross.equals(""))
		{
			percentMaxGross=0.0;
		}
		else
		{
			percentMaxGross=Double.parseDouble(percentBenifitMaxGross.split(Pattern.quote("^"))[0]);
		}
		if(percentBenifitMax.equals("") )
		{
			percentMax=0.00;
		}
		else
		{
			percentMax=Double.parseDouble(percentBenifitMax.split(Pattern.quote("^"))[0]);
		}

		String amountBenifitMax=dbengine.fnctnGetMaxAssignedBen9DscntApld1(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
		Double amountMax=0.00;
		if(percentBenifitMax.equals(""))
		{
			amountMax=0.0;
		}
		else
		{
			amountMax=Double.parseDouble(amountBenifitMax.split(Pattern.quote("^"))[0]);
		}


		tvAddDisc.setText(""+ "0.00");

		tv_NetInvAfterDiscount.setText(""+ TotalOrderValBeforeTax);

		TotTaxAmount=Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
		tvTAmt.setText(""+ TotTaxAmount);

		Double totalGrossVALMaxPercentage=TotalOrderValBeforeTax-TotalOrderValBeforeTax*(percentMaxGross/100);
		Double totalGrossrVALMaxAmount=TotalOrderValBeforeTax-amountMaxGross;
		Double totalGrossVALAfterDiscount = 0.0;
		if(totalGrossVALMaxPercentage!=totalGrossrVALMaxAmount)
		{
			totalGrossVALAfterDiscount=Math.min(totalGrossrVALMaxAmount, totalGrossVALMaxPercentage);
		}
		else
		{
			totalGrossVALAfterDiscount=totalGrossrVALMaxAmount;
		}

		if(totalGrossVALAfterDiscount==totalGrossrVALMaxAmount && totalGrossrVALMaxAmount!=0.0)
		{
			dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(amountBenfitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID,TmpInvoiceCodePDA);
		}
		else if(totalGrossVALAfterDiscount==totalGrossVALMaxPercentage && percentMaxGross!=0.0)
		{
			dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(percentBenifitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID,TmpInvoiceCodePDA);
		}

		Double GrossInvValue=totalGrossVALAfterDiscount + TotTaxAmount;
		GrossInvValue=Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));

		Double CollectionAmt=dbengine.fnTotCollectionAmtAgainstStore(storeID,TmpInvoiceCodePDA,StoreVisitCode);
		CollectionAmt=Double.parseDouble(new DecimalFormat("##.##").format(CollectionAmt));
		if(GrossInvValue>0.0)
		{
			VisitTypeStatus="2";
		}
		if(CollectionAmt>0.0)
		{
			VisitTypeStatus="3";
		}
		if(CollectionAmt>0.0 && GrossInvValue>0.0)
		{
			VisitTypeStatus="4";
		}
		dbengine.updateVisitTypeStatusOfStore(storeID,VisitTypeStatus,StoreVisitCode);
		tv_GrossInvVal.setText(""+GrossInvValue);
		/*Double TotOrderValuebasedonWholeSale=fnOnlyCalculateWholeSaleValue(PriceApplyDiscountLevelType);
		if(PriceApplyDiscountLevelType!=0) {
			if(PriceApplyDiscountLevelType==1) {// Invoice Level Whole Sell Price Applicable but Not At Invoice Level
				//if (flgWholeSellApplicable != 0) {
				if (TotOrderValuebasedonWholeSale >= cutoffvalue) {
					tvRateHeading.setText("Rate-W");
					flgAllTotalOrderValueCrossesCutOff=true;
					CalculateInvoiceLevelOrProductLevelWholeSaleValue(PriceApplyDiscountLevelType);
				}
				//}
			}
			if(PriceApplyDiscountLevelType==2) {//Product Level Whole Sell Price Applicable but Not At Invoice Level
				CalculateInvoiceLevelOrProductLevelWholeSaleValue(PriceApplyDiscountLevelType);
			}
		}*/
		//Now the its Time to Show the OverAll Summary Code Starts Here
	}


	public void CalculateInvoiceLevelOrProductLevelWholeSaleValue(int flgPriceApplyDiscountLevelType)
	{
		Double StandardRate = 0.00;
		Double StandardRateBeforeTax = 0.00;
		Double StandardTax = 0.00;
		Double ActualRateAfterDiscountBeforeTax = 0.00;
		Double DiscountAmount = 0.00;
		Double ActualTax = 0.00;
		Double ActualRateAfterDiscountAfterTax = 0.00;

		String PrdMaxValuePercentageDiscount = "";
		String PrdMaxValueFlatDiscount = "";

		Double TotalFreeQTY = 0.00;
		Double TotalProductLevelDiscount = 0.00;
		Double TotalOrderValBeforeTax = 0.00;
		Double TotAdditionaDiscount = 0.00;
		Double TotOderValueAfterAdditionaDiscount = 0.00;
		Double TotTaxAmount = 0.00;
		Double TotOderValueAfterTax = 0.00;

		int flgWholeSellApplicable = 0;
		Double PriceRangeWholeSellApplicable = 0.00;

		int prdListCount = hmapPrdctIdPrdctNameVisible.size();

		for (int index = 0; index < prdListCount; index++) {
			View vRow = ll_prdct_detal.getChildAt(index);

			int PCateIdDetails = Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
			String ProductID = ((TextView) (vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];
			((TextView) (vRow).findViewById(R.id.txtVwRate)).setText("" + hmapStandardRateWholeSale.get(ProductID).toString());
			if (hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.containsKey(ProductID)) {
				if (hmapPrdctOdrQty.containsKey(ProductID)) {
					flgWholeSellApplicable = Integer.parseInt(hmapflgWholeSellApplicable.get(ProductID));
					PriceRangeWholeSellApplicable = Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID));
					hmapPrdctFreeQty.put(ProductID, "0");
					((TextView) (vRow).findViewById(R.id.tv_FreeQty)).setText(hmapPrdctFreeQty.get(ProductID).toString());
					TotalFreeQTY = TotalFreeQTY + Integer.parseInt(hmapPrdctFreeQty.get(ProductID));
					/*hmapProductTaxValue.put(ProductID, "0.00");
					hmapMinDlvrQtyQPTaxAmount.put(ProductID, "0.00");*/
					((TextView) (vRow).findViewById(R.id.tv_Orderval)).setText("0.00");
					if (Integer.parseInt(hmapPrdctOdrQty.get(ProductID)) > 0) {
						/*StandardRate=Double.parseDouble(hmapProductMRP.get(ProductID))/((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);*/
						if (flgPriceApplyDiscountLevelType == 1) {
							if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
								StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
								StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
								StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
								//	((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
							} else {
								StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));
								StandardRateBeforeTax = Double.parseDouble(hmapStandardRateBeforeTaxWholeSell.get(ProductID));
								StandardTax = Double.parseDouble(hmapStandardTaxWholeSale.get(ProductID));
								//((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
							}

						}
						if (flgPriceApplyDiscountLevelType == 2) {
							if (hmapflgWholeSellApplicable.containsKey(ProductID)) {
								if (Double.parseDouble(hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.get(ProductID)) >= Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID))) {
									if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
										StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
										StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
										StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
										//((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
									} else {
										StandardRate = Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));
										StandardRateBeforeTax = Double.parseDouble(hmapStandardRateBeforeTaxWholeSell.get(ProductID));
										StandardTax = Double.parseDouble(hmapStandardTaxWholeSale.get(ProductID));
										//	((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());

									}
								} else {
									if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
										StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
										StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
										StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
									} else {
										StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));
										StandardRateBeforeTax = Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID));
										StandardTax = Double.parseDouble(hmapProductStandardTax.get(ProductID));

									}
								}
							} else {
								if (Integer.parseInt(hmapProductflgPriceAva.get(ProductID)) > 0) {
									StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
									StandardRateBeforeTax = StandardRate / (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));
									StandardTax = StandardRateBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
								} else {
									StandardRate = Double.parseDouble(hmapProductStandardRate.get(ProductID));
									StandardRateBeforeTax = Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID));
									StandardTax = Double.parseDouble(hmapProductStandardTax.get(ProductID));

								}
							}
						}

			/*if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
			{
				if(hmapMinDlvrQty.containsKey(ProductID))
				{
					if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
					{
						StandardRateBeforeTax=Double.parseDouble(hmapMinDlvrQtyQPBT.get(ProductID));
						StandardTax=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));
					}
				}
			}*/



			/*PrdMaxValuePercentageDiscount=dbengine.fnctnGetHighestDiscountPercentge(ProductID, storeID);
			PrdMaxValueFlatDiscount=dbengine.fnctnGetHighestDiscountAmount(ProductID, storeID);
			int BenifitRowIdPercentageDiscount=0;
			int BenifitRowIdFlatDiscount=0;
			//Double per=Double.parseDouble(hmapProductVolumePer.get(ProductID));

			Double per;
			String perProduct=dbengine.fnctnGetfreePerUnitVol(ProductID, storeID);
			if(perProduct.equals(""))
			{
				per=0.0;
			}
			else
			{
				per=Double.parseDouble((perProduct.split(Pattern.quote("^"))[0]));
			}*/

						String value = hmapPrdctVolRatTax.get(ProductID).toString();
						StringTokenizer tokens = new StringTokenizer(value, Pattern.quote("^"));
						//Volume^Rate^TaxAmount
						String prdVolume = tokens.nextElement().toString();

						StandardRate = Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));


						//		((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(hmapProductStandardRateBeforeTax.get(ProductID));
			/*if(PrdMaxValuePercentageDiscount.equals(""))
			{
				PrdMaxValuePercentageDiscount="0.00";
			}
			else
			{
				BenifitRowIdPercentageDiscount=Integer.parseInt(PrdMaxValuePercentageDiscount.split(Pattern.quote("^"))[1]);
				PrdMaxValuePercentageDiscount=PrdMaxValuePercentageDiscount.split(Pattern.quote("^"))[0];
			}
			if(PrdMaxValueFlatDiscount.equals(""))
			{
				PrdMaxValueFlatDiscount="0.00";
			}else
			{

				if(per.intValue()!=0)
				{
					//volume
					    		*//*int prdQty=Integer.parseInt(hmapPrdctOdrQty.get(ProductID));
					    		Double perVoume=per;
					    		Double productSingleUnitVolume=Double.parseDouble(prdVolume);
					    		Double prodPuchasedQtyBasedVolume=productSingleUnitVolume*prdQty;
					    		int prdNoOfVolumeCount=(prodPuchasedQtyBasedVolume.intValue()/perVoume.intValue());*//*

					BenifitRowIdFlatDiscount=Integer.parseInt(PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[1]);
					PrdMaxValueFlatDiscount=PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[0];
						    	*//*int finalValue;
						    	if(prdNoOfVolumeCount!=0)
						    	{
						    		finalValue=Integer.parseInt(PrdMaxValueFlatDiscount);//prdNoOfVolumeCount*
						    	}
						    	else
						    	{
						    		finalValue=0;
						    	}

						    	if(prdNoOfVolumeCount!=0)
						    	{
						    		finalValue=Integer.parseInt(PrdMaxValueFlatDiscount);
						    	}
						    	else
						    	{
						    		finalValue=prdNoOfVolumeCount*Integer.parseInt(PrdMaxValueFlatDiscount);
						    	}

						    	PrdMaxValueFlatDiscount=""+finalValue;*//*
				}
				else
				{
					BenifitRowIdFlatDiscount=Integer.parseInt(PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[1]);
					PrdMaxValueFlatDiscount=PrdMaxValueFlatDiscount.split(Pattern.quote("^"))[0];
				}
			}*/
			/*if(!PrdMaxValueFlatDiscount.equals("0.00") || !PrdMaxValuePercentageDiscount.equals("0.00"))
			{
				if(Double.parseDouble(PrdMaxValuePercentageDiscount)>=Double.parseDouble(PrdMaxValueFlatDiscount))
				{
					//If Percentage Discount is greater the FlatAmt Code Starts Here
					ActualRateAfterDiscountBeforeTax=StandardRateBeforeTax-(StandardRateBeforeTax*(((Double.parseDouble(PrdMaxValuePercentageDiscount)/100))));
					DiscountAmount=StandardRateBeforeTax-ActualRateAfterDiscountBeforeTax;
					ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
					ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax+ActualTax;//ActualRateAfterDiscountBeforeTax*((Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));

					Double DiscAmtOnPreQtyBasic=DiscountAmount*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

					Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
					DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
					((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
					hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
					TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
					TotTaxAmount=TotTaxAmount+(ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

					Double TaxValue=ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
					TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
					hmapProductTaxValue.put(ProductID, ""+TaxValue);

					if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
					{
						hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
					}

					Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
					Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
					OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
					((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
					hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
					TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));//

					TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;

					//If Percentage Discount is greater the FlatAmt Code Ends Here
				}
				else
				{
					//If Flat Amount is greater the Percentage Code Starts Here
					ActualRateAfterDiscountBeforeTax=(StandardRateBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)))-Double.parseDouble(PrdMaxValueFlatDiscount);
					DiscountAmount=Double.parseDouble(PrdMaxValueFlatDiscount);
					ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
					ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax*(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
					Double DiscAmtOnPreQtyBasic=0.00;
					if(per.intValue()>0)
					{
						DiscAmtOnPreQtyBasic=DiscountAmount;/*//*((Double.parseDouble(hmapPrdctOdrQty.get(ProductID))*Double.parseDouble(prdVolume))/per);
					}
					else
					{
						DiscAmtOnPreQtyBasic=DiscountAmount;/*//*((Double.parseDouble(hmapPrdctOdrQty.get(ProductID))));
					}
					Double DiscAmtOnPreQtyBasicToDisplay=DiscAmtOnPreQtyBasic;
					DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
					((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
					hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
					TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
					TotTaxAmount=TotTaxAmount+(ActualTax);/*//* Double.parseDouble(hmapPrdctOdrQty.get(ProductID)

					Double TaxValue=ActualTax;/*//* Double.parseDouble(hmapPrdctOdrQty.get(ProductID))
					TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
					hmapProductTaxValue.put(ProductID, ""+TaxValue);
					if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
					{
						hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
					}
					Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax;/*//*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
					Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
					OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
					((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
					hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
					TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax);/*//*Double.parseDouble(hmapPrdctOdrQty.get(ProductID))
					TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;
					//If Flat Amount is greater the Percentage Code Ends Here
				}
			}
			else
			{*/
						//If No Percentage Discount or Flat Discount is Applicable Code Starts Here
						ActualRateAfterDiscountBeforeTax = StandardRateBeforeTax;
						DiscountAmount = 0.00;
						ActualTax = ActualRateAfterDiscountBeforeTax * (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100);
						ActualRateAfterDiscountAfterTax = ActualRateAfterDiscountBeforeTax * (1 + (Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID)) / 100));

						Double DiscAmtOnPreQtyBasic = DiscountAmount * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

						Double DiscAmtOnPreQtyBasicToDisplay = 0.00;//DiscAmtOnPreQtyBasic;
						DiscAmtOnPreQtyBasicToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
						((TextView) (vRow).findViewById(R.id.tv_DisVal)).setText("" + DiscAmtOnPreQtyBasicToDisplay);
						hmapPrdctIdPrdctDscnt.put(ProductID, "" + DiscAmtOnPreQtyBasicToDisplay);

						TotalProductLevelDiscount = TotalProductLevelDiscount + DiscAmtOnPreQtyBasic;
						TotTaxAmount = TotTaxAmount + (ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

						Double TaxValue = ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						TaxValue = Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
						hmapProductTaxValue.put(ProductID, "" + TaxValue);
				/*if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
				{
					hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
				}*/
						Double OrderValPrdQtyBasis = ActualRateAfterDiscountAfterTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
						hmapLineValAftrTxAftrDscnt.put(ProductID, "" + OrderValPrdQtyBasis);
						Double OrderValPrdQtyBasisToDisplay = OrderValPrdQtyBasis;
						OrderValPrdQtyBasisToDisplay = Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
						((TextView) (vRow).findViewById(R.id.tv_Orderval)).setText("" + OrderValPrdQtyBasisToDisplay);
						hmapProductIdOrdrVal.put(ProductID, "" + OrderValPrdQtyBasis);
						TotalOrderValBeforeTax = TotalOrderValBeforeTax + (ActualRateAfterDiscountBeforeTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));
						hmapLineValBfrTxAftrDscnt.put(ProductID, "" + ActualRateAfterDiscountBeforeTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));
						TotOderValueAfterTax = TotOderValueAfterTax + OrderValPrdQtyBasis;
						//If No Percentage Discount or Flat Discount is Applicable Code Ends Here
						//}

					}

				}
			}


		}
		//Now the its Time to Show the OverAll Summary Code Starts Here

		tvFtotal.setText(("" + TotalFreeQTY).trim());

		TotalProductLevelDiscount = Double.parseDouble(new DecimalFormat("##.##").format(TotalProductLevelDiscount));
		tvDis.setText(("" + TotalProductLevelDiscount).trim());

		TotalOrderValBeforeTax = Double.parseDouble(new DecimalFormat("##.##").format(TotalOrderValBeforeTax));
		tv_NetInvValue.setText(("" + TotalOrderValBeforeTax).trim());

		/*String percentBenifitMax=dbengine.fnctnGetMaxAssignedBen8DscntApld1(storeID,strGlobalOrderID);
		Double percentMax=0.00;
		Double percentMaxGross=0.0;
		Double amountMaxGross=0.0;

		String amountBenfitMaxGross=dbengine.fnctnGetMaxAssignedBen9DscntApld2(storeID,strGlobalOrderID);
		String percentBenifitMaxGross=dbengine.fnctnGetMaxAssignedBen8DscntApld2(storeID,strGlobalOrderID);

		if(percentBenifitMaxGross.equals(""))
		{
			percentMaxGross=0.0;
		}
		else
		{
			percentMaxGross=Double.parseDouble(percentBenifitMaxGross.split(Pattern.quote("^"))[0]);
		}
		if(percentBenifitMax.equals("") )
		{
			percentMax=0.00;
		}
		else
		{
			percentMax=Double.parseDouble(percentBenifitMax.split(Pattern.quote("^"))[0]);
		}

		String amountBenifitMax=dbengine.fnctnGetMaxAssignedBen9DscntApld1(storeID,strGlobalOrderID);
		Double amountMax=0.00;
		if(percentBenifitMax.equals(""))
		{
			amountMax=0.0;
		}
		else
		{
			amountMax=Double.parseDouble(amountBenifitMax.split(Pattern.quote("^"))[0]);
		}

*/
		tvAddDisc.setText("" + "0.00");

		tv_NetInvAfterDiscount.setText("" + TotalOrderValBeforeTax);

		TotTaxAmount = Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
		tvTAmt.setText("" + TotTaxAmount);
		Double totalGrossVALAfterDiscount = TotalOrderValBeforeTax;//0.0;
		/*Double totalGrossVALMaxPercentage=TotalOrderValBeforeTax-TotalOrderValBeforeTax*(percentMaxGross/100);
		Double totalGrossrVALMaxAmount=TotalOrderValBeforeTax-amountMaxGross;

		if(totalGrossVALMaxPercentage!=totalGrossrVALMaxAmount)
		{
			totalGrossVALAfterDiscount=Math.min(totalGrossrVALMaxAmount, totalGrossVALMaxPercentage);
		}
		else
		{
			totalGrossVALAfterDiscount=totalGrossrVALMaxAmount;
		}

		if(totalGrossVALAfterDiscount==totalGrossrVALMaxAmount && totalGrossrVALMaxAmount!=0.0)
		{
			dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(amountBenfitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID);
		}
		else if(totalGrossVALAfterDiscount==totalGrossVALMaxPercentage && percentMaxGross!=0.0)
		{
			dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(percentBenifitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID);
		}*/

		Double GrossInvValue = totalGrossVALAfterDiscount + TotTaxAmount;
		GrossInvValue = Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
		tv_GrossInvVal.setText("" + TotOderValueAfterTax);
		//tvAfterTaxValue.setText("" + TotOderValueAfterTax);
		//Now the its Time to Show the OverAll Summary Code Starts Here
	}
	public void fnUpdateSchemeNameOnScehmeControl(String ProductIdOnClickedControl)
	{
		txtVw_schemeApld.setText("");
		txtVw_schemeApld.setTag("0");

		txtVw_schemeApld.setVisibility(View.GONE);
	}


	public void fnSaveFilledDataToDatabase(int valBtnClickedFrom)
	{
	 
	 //valBtnClickedFrom=Save/Save And Exit/Submit
		
		//Declare  Outstat=0;  // Outstat=1 (Save,SaveExit) , Outstat=3(Submit)
		
		

	 if(valBtnClickedFrom==3)//Clicked By Btn Submitt
	 {
	     //Send Data for Sync
		
		 // Changes By Sunil 
		   AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductInvoiceReview.this);
			alertDialogSubmitConfirm.setTitle(getText(R.string.genTermNoDataConnection));
			alertDialogSubmitConfirm.setMessage(getText(R.string.submitConfirmAlert));
			alertDialogSubmitConfirm.setCancelable(false);
			
			alertDialogSubmitConfirm.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which)
						{
							 butClickForGPS=3;
							 dbengine.open();
							 if ((dbengine.PrevLocChk(storeID.trim(),StoreVisitCode)) )
								{
								 dbengine.close();

									FullSyncDataNow task = new FullSyncDataNow(ProductInvoiceReview.this);
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
							       
							        
							        pDialog2STANDBY=ProgressDialog.show(ProductInvoiceReview.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
									   pDialog2STANDBY.setIndeterminate(true);
										
										pDialog2STANDBY.setCancelable(false);
										pDialog2STANDBY.show();
										
								if(isGooglePlayServicesAvailable()) {
									 createLocationRequest();
									 
									 mGoogleApiClient = new GoogleApiClient.Builder(ProductInvoiceReview.this)
								     .addApi(LocationServices.API)
								     .addConnectionCallbacks(ProductInvoiceReview.this)
								     .addOnConnectionFailedListener(ProductInvoiceReview.this)
								     .build();
									 mGoogleApiClient.connect();
							      }
								//startService(new Intent(DynamicActivity.this, AppLocationService.class));
								startService(new Intent(ProductInvoiceReview.this, AppLocationService.class));
								Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
								Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
								 countDownTimer2 = new CoundownClass2(startTime, interval);
						         countDownTimer2.start();

								*/

								 LocationRetreivingGlobal llaaa=new LocationRetreivingGlobal();
								 llaaa.locationRetrievingAndDistanceCalculating(ProductInvoiceReview.this,false,20);

							 }
							
							// storeSubmit.setEnabled(false);
							//storeSave4Later.setEnabled(false);
							//storeSaveContinue4Later.setEnabled(false);
							/* int Outstat=3;
							TransactionTableDataDeleteAndSaving(Outstat);
							InvoiceTableDataDeleteAndSaving(Outstat);
						 
						    long  syncTIMESTAMP = System.currentTimeMillis();
							Date dateobj = new Date(syncTIMESTAMP);
							SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
							String StampEndsTime = df.format(dateobj);
							
							
							dbengine.open();
							dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
							dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"3");
							
							dbengine.UpdateStoreFlag(storeID.trim(), 3);
							//dbengine.deleteStoreRecordFromtblStoreSchemeFreeProQtyOtherDetailsOnceSubmitted(fStoreID);
							dbengine.close();*/
							
							//new FullSyncDataNow().execute();
							
						}
					});
			
			alertDialogSubmitConfirm.setNegativeButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			
			alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);
			
			AlertDialog alert = alertDialogSubmitConfirm.create();
			
			alert.show();
			

		    
		 
	 }
	 if(valBtnClickedFrom==1)//Clicked By Btn Save
	 {
	        //Change Ostat Val=2
		 int Outstat=1;
		 InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
		    TransactionTableDataDeleteAndSaving(Outstat);

			dbengine.open();
			dbengine.UpdateStoreFlag(storeID.trim(), 1);
			dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1,strGlobalOrderID,TmpInvoiceCodePDA);
			dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"1",strGlobalOrderID,TmpInvoiceCodePDA);
			dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"1",strGlobalOrderID,TmpInvoiceCodePDA);
			
		    long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = df.format(dateobj);
			
			
			dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
			dbengine.close();
			dbengine.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0,StoreVisitCode);
			if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,TmpInvoiceCodePDA)==0)
			{
				String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
				if(!strDefaultPaymentStageForStore.equals(""))
				{
					dbengine.open();
					dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"1",TmpInvoiceCodePDA);
					dbengine.close();
				}
			}
	 }
	 if(valBtnClickedFrom==2)//Clicked By Btn Save and Exit
	 {
	        //Go to Store List Page
	       //Change Ostat Val=2
		 
		    //change by Sunil
		   int Outstat=1;
		 InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
		    TransactionTableDataDeleteAndSaving(Outstat);

			dbengine.open();
			dbengine.UpdateStoreFlag(storeID.trim(), 1);
			dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1,strGlobalOrderID,TmpInvoiceCodePDA);
			dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"1",strGlobalOrderID,TmpInvoiceCodePDA);
			dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"1",strGlobalOrderID,TmpInvoiceCodePDA);
			
			 long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = df.format(dateobj);
				
				
				dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
			dbengine.close();
			if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,TmpInvoiceCodePDA)==0)
			{
				String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
				if(!strDefaultPaymentStageForStore.equals(""))
				{
					dbengine.open();
					dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"1",TmpInvoiceCodePDA);
					dbengine.close();
				}
			}

		 dbengine.open();
		 String presentRoute=dbengine.GetActiveRouteID();
		 dbengine.close();

		 Intent trans2storeList = new Intent(ProductInvoiceReview.this, StoreSelection.class);
		 trans2storeList.putExtra("imei", imei);
		 trans2storeList.putExtra("userDate", date);
		 trans2storeList.putExtra("pickerDate", pickerDate);
		 trans2storeList.putExtra("rID", presentRoute);
		 startActivity(trans2storeList);
		 finish();
		   /* Intent storeSaveIntent = new Intent(OrderReview.this, LauncherActivity.class);
			startActivity(storeSaveIntent);
			finish();*/
		 
	 }


		if(valBtnClickedFrom==4)//Next Button Click For Collection
		{
					/*butClickForGPS=8;
					dbengine.open();
					if ((dbengine.PrevLocChk(storeID.trim(),StoreVisitCode)) )
					{
						dbengine.close();*/
						Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, CollectionActivityNew.class);   //
						AmtCollectIntent.putExtra("storeID", storeID);
						AmtCollectIntent.putExtra("imei", imei);
						AmtCollectIntent.putExtra("userdate", date);
						AmtCollectIntent.putExtra("pickerDate", pickerDate);
						AmtCollectIntent.putExtra("SN", SN);
						AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
						AmtCollectIntent.putExtra("FromPlace", "1");
						startActivity(AmtCollectIntent);
						finish();
					/*}
					else
					{
						dbengine.close();
						appLocationService=new AppLocationService();
						pDialog2STANDBY=ProgressDialog.show(ProductOrderReview.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
						pDialog2STANDBY.setIndeterminate(true);
						pDialog2STANDBY.setCancelable(false);
						pDialog2STANDBY.show();
						if(isGooglePlayServicesAvailable()) {
							createLocationRequest();

							mGoogleApiClient = new GoogleApiClient.Builder(ProductOrderReview.this)
									.addApi(LocationServices.API)
									.addConnectionCallbacks(ProductOrderReview.this)
									.addOnConnectionFailedListener(ProductOrderReview.this)
									.build();
							mGoogleApiClient.connect();
						}
						//startService(new Intent(DynamicActivity.this, AppLocationService.class));
						startService(new Intent(ProductOrderReview.this, AppLocationService.class));
						Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
						Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
						countDownTimer2 = new CoundownClass2(startTime, interval);
						countDownTimer2.start();




					}*/






		}
	}



	public void TransactionTableDataDeleteAndSaving(int Outstat)
	{


		dbengine.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
		/* if (lastKnownLoc == null)
	     {
			 lastKnownLocLatitude=String.valueOf("0.00000");
	    	 lastKnownLocLongitude=String.valueOf("0.00000");
	    	 accuracy=String.valueOf("0");
	    	 locationProvider="Default";
	     }
		 dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(lastKnownLocLatitude), String.valueOf(lastKnownLocLongitude), "" + accuracy,locationProvider);
		 */
		int childcount =hmapPrdctIdPrdctName.size();// hmapPrdctIdPrdctNameVisible.size();

		for (Entry<String, String> entry:hmapPrdctIdPrdctName.entrySet() )
		{
			//  View vRow = ll_prdct_detal.getChildAt(index);

			int PCateId=Integer.parseInt(hmapCtgryPrdctDetail.get(entry.getKey()));
			String PName =entry.getValue();
			String ProductID=entry.getKey();
			String ProductStock =hmapProductIdStock.get(ProductID);
			int ProductExtraOrder=Integer.parseInt(hmapProductExtraOrder.get(ProductID));
			double TaxRate=0.00;
			double TaxValue=0.00;
			if(ProductStock.equals(""))
			{
				ProductStock="0";
			}
			String SampleQTY =hmapPrdctSmpl.get(ProductID);
			if(SampleQTY.equals(""))
			{
				SampleQTY="0";
			}
			String OrderQTY =hmapPrdctOdrQty.get(ProductID);

			if(OrderQTY.equals(""))
			{
				OrderQTY="0";

			}
			String OrderValue="0";
			if(Integer.parseInt(OrderQTY)>0)
			{
				OrderValue =hmapProductIdOrdrVal.get(ProductID);// ((TextView)(vRow).findViewById(R.id.tv_Orderval)).getText().toString();
				if(OrderValue.equals(""))
				{
					OrderValue="0";
				}
			}

			String OrderFreeQty =hmapPrdctFreeQty.get(ProductID);
			if(OrderFreeQty.equals(""))
			{
				OrderFreeQty="0";
			}

			String OrderDisVal= "0";
			if(Integer.parseInt(OrderQTY)>0) {
				// hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
				OrderDisVal= hmapPrdctIdPrdctDscnt.get(ProductID);
				if (OrderDisVal.equals("0.00")) {
					OrderDisVal = "0";
				}
			}
			String PRate="0.00";
			int flgIsQuoteRateApplied=0;
			if(PriceApplyDiscountLevelType==0) {
				PRate=hmapProductStandardRate.get(ProductID);
				TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
				TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));

				if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
				{
					if(hmapMinDlvrQty.containsKey(ProductID))
					{
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
						{
							flgIsQuoteRateApplied=1;
							PRate=hmapMinDlvrQtyQPAT.get(ProductID);
							TaxValue=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));

						}

					}
				}
			}
			if(PriceApplyDiscountLevelType==1) {
				if(flgAllTotalOrderValueCrossesCutOff==true)
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
				else
				{
					PRate=hmapProductStandardRate.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}
			if(PriceApplyDiscountLevelType==2) {
				if(Double.parseDouble(OrderValue)>=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID)))
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}
			 	/*PRate=hmapProductStandardRate.get(ProductID);
		       TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
		       TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
		       int flgIsQuoteRateApplied=0;
		       if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
				{
					if(hmapMinDlvrQty.containsKey(ProductID))
					{
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
						{
							flgIsQuoteRateApplied=1;
							PRate=hmapMinDlvrQtyQPAT.get(ProductID);
							TaxValue=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));

						}

					}
				}*/
			// String TransDate=date;
			if(Integer.valueOf(OrderFreeQty)>0 || Integer.valueOf(SampleQTY)>0 || Integer.valueOf(OrderQTY)>0 || Integer.valueOf(OrderValue)>0 || Integer.valueOf(OrderDisVal)>0 || Integer.valueOf(ProductStock)>0 || ProductExtraOrder>0)
			{
				dbengine.open();
				StoreCatNodeId=dbengine.fnGetStoreCatNodeId(storeID);
				int flgRuleTaxVal=1;
				dbengine.fnsaveStoreTempOrderEntryDetails(TmpInvoiceCodePDA,storeID,""+PCateId,ProductID,Double.parseDouble(PRate),TaxRate,flgRuleTaxVal,Integer.parseInt(OrderQTY),Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)),Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)),Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)),Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]),Double.parseDouble(OrderDisVal),Integer.parseInt(SampleQTY),PName,TaxValue,strGlobalOrderID,flgIsQuoteRateApplied,PriceApplyDiscountLevelType,distID,Outstat,ProductExtraOrder);
				dbengine.close();
			}



		}
		flgAllTotalOrderValueCrossesCutOff=false;
	}



	public void InvoiceTableDataDeleteAndSaving(int Outstat,int flgTransferStatus)
	{
		Double INval;

		if(!tv_GrossInvVal.getText().toString().isEmpty()){


			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}


	    dbengine.deleteOldStoreInvoice(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
		
		Double TBtaxDis;
		Double TAmt;
		Double Dis;


		Double AddDis;
		Double InvAfterDis;
		
		Double INvalCreditAmt;
		Double INvalInvoiceAfterCreditAmt;
		
		Double INvalInvoiceOrginal=0.00;
		
		
		int Ftotal;
		
		
		
		
		if(!tv_NetInvValue.getText().toString().isEmpty()){
			TBtaxDis = Double.parseDouble(tv_NetInvValue.getText().toString().trim());
		}
		else{
			TBtaxDis = 0.00;
		}
		if(!tvTAmt.getText().toString().isEmpty()){
			TAmt = Double.parseDouble(tvTAmt.getText().toString().trim());
		}
		else{
			TAmt = 0.00;
		}
		if(!tvDis.getText().toString().isEmpty()){
			Dis = Double.parseDouble(tvDis.getText().toString().trim());
		}
		else{
			Dis = 0.00;
		}
		/*if(!tv_GrossInvVal.getText().toString().isEmpty()){
			
		*//*	if(Dis!=0.00)
			{
				INval = Double.parseDouble(tvINval.getText().toString().trim())-Dis;
			}
			else
			{
				INval = Double.parseDouble(tvINval.getText().toString().trim());
			}*//*
			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}*/
		if(!tvFtotal.getText().toString().isEmpty()){
			Double FtotalValue=Double.parseDouble(tvFtotal.getText().toString().trim());
			Ftotal =FtotalValue.intValue();
		}
		else{
			Ftotal = 0;
		}
		
		if(!tv_NetInvAfterDiscount.getText().toString().isEmpty()){
			InvAfterDis = Double.parseDouble(tv_NetInvAfterDiscount.getText().toString().trim());
		}
		else{
			InvAfterDis = 0.00;
		}
		if(!tvAddDisc.getText().toString().isEmpty()){
			AddDis = Double.parseDouble(tvAddDisc.getText().toString().trim());
		}
		else{
			AddDis = 0.00;
		}
		
		
		Double AmtPrevDueVA=0.00;
		Double AmtCollVA=0.00;
		Double AmtOutstandingVAL=0.00;
		if(!tvAmtPrevDueVAL.getText().toString().isEmpty()){
			AmtPrevDueVA = Double.parseDouble(tvAmtPrevDueVAL.getText().toString().trim());
		}
		else{
			AmtPrevDueVA = 0.00;
		}
		if(!etAmtCollVAL.getText().toString().isEmpty()){
			AmtCollVA = Double.parseDouble(etAmtCollVAL.getText().toString().trim());
		}
		else{
			AmtCollVA = 0.00;
		}
		
		if(!tvAmtOutstandingVAL.getText().toString().isEmpty()){
			AmtOutstandingVAL = Double.parseDouble(tvAmtOutstandingVAL.getText().toString().trim());
		}
		else{
			AmtOutstandingVAL = 0.00;
		}
		
		int NoOfCouponValue=0;
		/*if(!txttvNoOfCouponValue.getText().toString().isEmpty()){
			NoOfCouponValue = Integer.parseInt(txttvNoOfCouponValue.getText().toString().trim());
		}
		else{
			NoOfCouponValue = 0;
		}
		*/
		Double TotalCoupunAmount=0.00;
		if(!txttvCouponAmountValue.getText().toString().isEmpty()){
			TotalCoupunAmount = Double.parseDouble(txttvCouponAmountValue.getText().toString().trim());
		}
		else{
			TotalCoupunAmount = 0.00;
		}

		int flgRuleTaxVal=1;
		int flgTransType=1;
		dbengine.open();
		//dbengine.saveStoreInvoice(imei,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, AmtPrevDueVA, AmtCollVA, AmtOutstandingVAL, NoOfCouponValue, TotalCoupunAmount,Outstat,strGlobalOrderID,TmpInvoiceCodePDA,strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);

		dbengine.saveStoreTempInvoice(StoreVisitCode,TmpInvoiceCodePDA,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis,  NoOfCouponValue, TotalCoupunAmount,pickerDate,flgTransType,PriceApplyDiscountLevelType,flgRuleTaxVal,Outstat,flgTransferStatus);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
		dbengine.close();
		

		 	
	}

	public void SyncNow()
	{

		dbengine.open();
		String presentRoute=dbengine.GetActiveRouteID();
		dbengine.close();


		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

		String newfullFileName=imei+"."+presentRoute+"."+ df.format(dateobj);




		try
		{

			 File OrderXMLFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.OrderXMLFolder);

			 if (!OrderXMLFolder.exists())
				{
					OrderXMLFolder.mkdirs();

				}

			 String routeID=dbengine.GetActiveRouteIDSunil();

			DA.open();
			DA.export(CommonInfo.DATABASE_NAME, newfullFileName,routeID);
			DA.close();



			Intent syncIntent = new Intent(ProductInvoiceReview.this, SyncMaster.class);
			//syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + newfullFileName + ".xml");
			syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");

			syncIntent.putExtra("OrigZipFileName", newfullFileName);
			syncIntent.putExtra("whereTo", "Regular");
			startActivity(syncIntent);


		} catch (IOException e) {

			e.printStackTrace();
		}

	}



		 public String genOutOrderID()
			{
				//store ID generation <x>
				long syncTIMESTAMP = System.currentTimeMillis();
				Date dateobj = new Date(syncTIMESTAMP);
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
				String VisitStartTS = df.format(dateobj);
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

						/*TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String imei = tManager.getDeviceId();*/
						String IMEIid =  CommonInfo.imei.substring(9);
						//cxz = IMEIid +"-"+cxz;
						cxz = "OrdID" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();
						//System.out.println("cxz: "+cxz);

						return cxz;
						//-_
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

		public void showAlertForEveryOne(String msg)
		{
				AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductInvoiceReview.this);
				alertDialogNoConn.setTitle(getText(R.string.genTermNoDataConnection));
				alertDialogNoConn.setMessage(msg);
				alertDialogNoConn.setCancelable(false);
				alertDialogNoConn.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
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
								 checkHighAccuracyLocationMode(ProductInvoiceReview.this);
								 dbengine.open();
								 dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview,StoreVisitCode,VisitTimeInSideStore);


								 dbengine.close();

								 if(butClickForGPS==1)
								 {
									 butClickForGPS=0;

									 orderBookingTotalCalc();
									 if(!alertOpens)
									 {
										 progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);

										 new SaveData().execute("1");
									 }


								 }
								 else  if(butClickForGPS==2)
								 {
									 butClickForGPS=0;


									 orderBookingTotalCalc();
									 if(!alertOpens)
									 {
										 progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSaveExit);
										 new SaveData().execute("2");
									 }

								 }
								 else  if(butClickForGPS==3)
								 {
									 butClickForGPS=0;
									 try
									 {
										 FullSyncDataNow task = new FullSyncDataNow(ProductInvoiceReview.this);
										 task.execute();
									 }
									 catch (Exception e) {
										 // TODO Autouuid-generated catch block
										 e.printStackTrace();
										 //System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
									 }
								 }

								 else  if(butClickForGPS==6)
								 {
									 butClickForGPS=0;


									 orderBookingTotalCalc();
									 if(!alertOpens)
									 {
										 progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileReview);

										 new SaveData().execute("6");
									 }

								 }

							 }
						});
				alertDialogNoConn.setIcon(R.drawable.info_ico);
				AlertDialog alert = alertDialogNoConn.create();
				alert.show();
		}

		@Override
		public void onLocationChanged(Location args0) {
			// TODO Auto-generated method stub
			 mCurrentLocation = args0;
	         mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
	         updateUI();

		}

		 public SpannableStringBuilder textWithMandatory(String text_Value)

		 	{
		 		String simple = text_Value;
		 		String colored = "**";
		 		SpannableStringBuilder builder = new SpannableStringBuilder();

		 		builder.append(simple);
		 		int start = builder.length();
		 		builder.append(colored);
		 		int end = builder.length();

		 		builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
		 		                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		 		//text.setText(builder);

		 		return builder;

		 	}

	     public Double getAccAsignValue(int schSlabSubBucketType,int chkflgProDataCalculation,double BenSubBucketValue,double schSlabSubBucketValue,int totalProductQty,int totalProductLine,double totalProductValue,double totalProductVol,double totalInvoice,double per,String productIdForFree,boolean isLastPrdct)
	     {
	    	 Double accAsignVal=0.0;
	    	//1. Product Quantity
				//5. Product Volume
				//2. Invoice Value
				//3. Product Lines
				//4. Product Value

	    	 //Product Quantity
				if(schSlabSubBucketType==1)
				{
					if(chkflgProDataCalculation==1)
					{

						if(isLastPrdct)
						{
							accAsignVal=BenSubBucketValue*(totalProductQty/schSlabSubBucketValue);
							accAsignVal=  Double.valueOf(Math.abs(accAsignVal.intValue())) ;
						}
						//accAsignVal=Double.valueOf(Double.valueOf(hmapPrdctOdrQty.get(productIdForFree))*accAsignVal/totalProductQty);

					}
					else
					{
						if(isLastPrdct)
						{
							accAsignVal=BenSubBucketValue;
						}

					}

				}
				//Invoice Value
				else if(schSlabSubBucketType==2)
				{

					if(chkflgProDataCalculation==1)
					{
						accAsignVal=BenSubBucketValue*(totalInvoice/schSlabSubBucketValue);

					}
					else
					{
						accAsignVal=BenSubBucketValue;
					}
				}
				//Product Lines
				else if(schSlabSubBucketType==3)
				{
					if(chkflgProDataCalculation==1)
					{
						accAsignVal=BenSubBucketValue*(totalProductLine/schSlabSubBucketValue);
						accAsignVal=Double.valueOf(Double.valueOf(productFullFilledSlabGlobal.size())*accAsignVal/totalProductLine);
					}
					else
					{
						accAsignVal=BenSubBucketValue;
					}
				}
				 //Product Value
				else if(schSlabSubBucketType==4)
				{
					if(chkflgProDataCalculation==1)
					{
						accAsignVal=BenSubBucketValue*(totalProductValue/schSlabSubBucketValue);

						//product value

						Double prodRate= Double.parseDouble(hmapPrdctVolRatTax.get(productIdForFree).split(Pattern.quote("^"))[1]);
						Double oderRateOfCurrentMapedProduct=prodRate *Double.valueOf(hmapPrdctOdrQty.get(productIdForFree)) ;
						//oderRateOnProduct=oderRateOnProduct + oderRateOfCurrentMapedProduct;
						double singleProductVal=oderRateOfCurrentMapedProduct;

						if(per>0.0)
						{
							singleProductVal=singleProductVal/per;
						}
						accAsignVal=Double.valueOf(Double.valueOf(singleProductVal)*(accAsignVal/totalProductValue));


					}
					else
					{

						accAsignVal=BenSubBucketValue;
					}

				}
				// Product Volume
				else if(schSlabSubBucketType==5)
				{
					if(chkflgProDataCalculation==1)
					{

						accAsignVal=BenSubBucketValue*(totalProductVol/(schSlabSubBucketValue*1000));
						// product volume
						Double prodVolume= Double.parseDouble(hmapPrdctVolRatTax.get(productIdForFree).split(Pattern.quote("^"))[0]);
						Double oderVolumeOfCurrentMapedProduct=prodVolume * Double.valueOf(hmapPrdctOdrQty.get(productIdForFree)) ;
						Double singleProductVol=oderVolumeOfCurrentMapedProduct;
						if(per>0.0)
						{
							singleProductVol=singleProductVol/per;
						}
						accAsignVal=Double.valueOf(Double.valueOf(singleProductVol)*accAsignVal/totalProductVol);

					}
					else
					{
						accAsignVal=BenSubBucketValue;
					}
				}
				 return accAsignVal;
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

	public void UpdateLocationAndProductAllData()
	{
		checkHighAccuracyLocationMode(ProductInvoiceReview.this);
		dbengine.open();
		dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview,StoreVisitCode,VisitTimeInSideStore);


		dbengine.close();

		if(butClickForGPS==1)
		{
			butClickForGPS=0;

			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);

				new SaveData().execute("1");
			}


		}
		else  if(butClickForGPS==2)
		{
			butClickForGPS=0;


			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSaveExit);

				new SaveData().execute("2");
			}

		}
		else  if(butClickForGPS==3)
		{
			butClickForGPS=0;
			try
			{
				FullSyncDataNow task = new FullSyncDataNow(ProductInvoiceReview.this);
				task.execute();
			}
			catch (Exception e) {
				// TODO Autouuid-generated catch block
				e.printStackTrace();
				//System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
			}
		}

		else  if(butClickForGPS==6)
		{
			butClickForGPS=0;


			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileReview);

				new SaveData().execute("6");
			}

		}
		else  if(butClickForGPS==8)
		{
			butClickForGPS=0;


			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileReview);

				new SaveData().execute("1~7");
			}

		}
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

	public void alertForOrderExceedStock(final String productOIDClkd, final EditText edOrderCurrent, final EditText edOrderCurrentLast, final int flagClkdButton)
	{

		final Dialog listDialog = new Dialog(ProductInvoiceReview.this);
		listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		listDialog.setContentView(R.layout.custom_stock_alert);
		listDialog.setCanceledOnTouchOutside(false);
		listDialog.setCancelable(false);
		WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
		parms.gravity =Gravity.CENTER;
		parms.width=WindowManager.LayoutParams.MATCH_PARENT;
		//there are a lot of settings, for dialog, check them all out!
		parms.dimAmount = (float) 0.5;


		final int avilabQty=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

		TextView order_detail=(TextView) listDialog.findViewById(R.id.order_detail);

		order_detail.setText(hmapPrdctIdPrdctName.get(ProductIdOnClickedEdit)+"\n\n"+ProductInvoiceReview.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductInvoiceReview.this.getResources().getString(R.string.RqrdQty)+hmapPrdctOdrQty.get(productOIDClkd)+"\n"+getText(R.string.order_exceeds_stock));
		Button btn_done= (Button) listDialog.findViewById(R.id.btn_done);
		final EditText editText_prdctQty= (EditText) listDialog.findViewById(R.id.editText_prdctQty);
		editText_prdctQty.setText(""+avilabQty);
		editText_prdctQty.setVisibility(View.GONE);
		ed_extraQty= (EditText) listDialog.findViewById(R.id.ed_extraQty);
		ed_extraQty.setVisibility(View.GONE);
		/*editText_prdctQty.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void afterTextChanged(Editable editable) {
				if(!TextUtils.isEmpty(editText_prdctQty.getText().toString()))
				{
					int prdctQty=Integer.parseInt(editText_prdctQty.getText().toString());
						if(prdctQty>avilabQty)
						{
							editText_prdctQty.setText("");
							editText_prdctQty.setError("Qty cannot exceed available qty");
						}
				}
			}
		});*/

		btn_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listDialog.dismiss();
				hmapPrdctOdrQty.put(productOIDClkd,"0");
				hmapProductExtraOrder.put(productOIDClkd,"0");
				edOrderCurrentLast.setText("");
				/*if(!TextUtils.isEmpty(ed_extraQty.getText().toString().trim()))
				{
					hmapProductExtraOrder.put(productOIDClkd,ed_extraQty.getText().toString().trim());
				}
				else
				{
					hmapProductExtraOrder.put(productOIDClkd,"0");
				}

				if(!TextUtils.isEmpty(editText_prdctQty.getText().toString().trim()))
				{
					hmapPrdctOdrQty.put(productOIDClkd,editText_prdctQty.getText().toString());
					edOrderCurrentLast.setText(editText_prdctQty.getText().toString());
				}
				else
				{
					hmapPrdctOdrQty.put(productOIDClkd,"0");
				}
*/
				edOrderCurrent.clearFocus();
				edOrderCurrentLast.requestFocus();
				//alertForOrderExceedStock(productOIDClkd,edOrderCurrent,edOrderCurrentLast,-1);

			}
		});




		//now that the dialog is set up, it's time to show it
		listDialog.show();

	}
	public void alertForRetailerCreditLimit(final int btnClkd)
	{
		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductInvoiceReview.this);
		alertDialogSubmitConfirm.setTitle(getText(R.string.genTermNoDataConnection));
		alertDialogSubmitConfirm.setMessage(getText(R.string.credit_retailer_balance));
		alertDialogSubmitConfirm.setCancelable(false);

		alertDialogSubmitConfirm.setNeutralButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				dbengine.updateFlgCrediBal(storeID,1);

				nextStepAfterRetailerCreditBal(btnClkd);


			}
		});

		alertDialogSubmitConfirm.setNegativeButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method
				dbengine.updateFlgCrediBal(storeID,0);

				nextStepAfterRetailerCreditBal(btnClkd);


				dialog.dismiss();
			}
		});

		alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);

		AlertDialog alert = alertDialogSubmitConfirm.create();

		alert.show();


	}

	public void nextStepAfterRetailerCreditBal(int btnClkd)
	{
		/*if(chkflgInvoiceAlreadyGenerated==0) {
			chkflgInvoiceAlreadyGenerated=1;
			dbengine.updatetblInvoiceCaption(storeID);

		}*/


		if(btnClkd==0) // save
		{
			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"Save Button Click on Product List"+StartClickTimeFinal);
			File file = new File("/sdcard/"+CommonInfo.TextFileFolder+"/"+fileName);

			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}


			CommonInfo.fileContent=CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Save Button Click on Product List"+StartClickTimeFinal;


			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				dbengine.open();
				dbengine.savetblMessageTextFileContainer(fileName,0);
				dbengine.close();


			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}




			butClickForGPS=1;
			flagClkdButton=5;


			orderBookingTotalCalc();
			progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);

			new SaveData().execute("1");

		}

		else if(btnClkd==1) // btn save&Exit clkd
		{
			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal);
			File file = new File("/sdcard/"+CommonInfo.TextFileFolder+"/"+fileName);

			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}


			CommonInfo.fileContent=CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal;


			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				dbengine.open();
				dbengine.savetblMessageTextFileContainer(fileName,0);
				dbengine.close();


			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			butClickForGPS=2;
			flagClkdButton=2;
			dbengine.open();
			dbengine.updateflgFromWhereSubmitStatusAgainstStore(storeID, 2,StoreVisitCode);
			if ((dbengine.PrevLocChk(storeID.trim(),StoreVisitCode)) )
			{
				dbengine.close();


				orderBookingTotalCalc();
				if(!alertOpens)
				{
					progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSaveExit);
					locationManager=(LocationManager) ProductInvoiceReview.this.getSystemService(LOCATION_SERVICE);

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
					else
					{
						new SaveData().execute("2");
					}
				}
			}
			else
			{
				dbengine.close();
				appLocationService=new AppLocationService();



				pDialog2STANDBY=ProgressDialog.show(ProductInvoiceReview.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
				pDialog2STANDBY.setIndeterminate(true);

				pDialog2STANDBY.setCancelable(false);
				pDialog2STANDBY.show();

				if(isGooglePlayServicesAvailable()) {
					createLocationRequest();

					mGoogleApiClient = new GoogleApiClient.Builder(ProductInvoiceReview.this)
							.addApi(LocationServices.API)
							.addConnectionCallbacks(ProductInvoiceReview.this)
							.addOnConnectionFailedListener(ProductInvoiceReview.this)
							.build();
					mGoogleApiClient.connect();
				}
				//startService(new Intent(DynamicActivity.this, AppLocationService.class));
				startService(new Intent(ProductInvoiceReview.this, AppLocationService.class));
				Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
				Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
				countDownTimer2 = new CoundownClass2(startTime, interval);
				countDownTimer2.start();


			}

		}

		else if(btnClkd==2) // btn submit clkd
		{

			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);

			String fileName=imei+"_"+storeID;

			File file = new File("/sdcard/"+CommonInfo.TextFileFolder+"/"+fileName);
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			CommonInfo.fileContent=CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Submit Button Click on Product List"+StartClickTimeFinal;

			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				dbengine.open();
				dbengine.savetblMessageTextFileContainer(fileName,0);
				dbengine.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			flagClkdButton=3;
			dbengine.open();
			dbengine.updateflgFromWhereSubmitStatusAgainstStore(storeID, 2,StoreVisitCode);
			dbengine.close();

			orderBookingTotalCalc();
			if(!alertOpens)
			{
				boolean isGPSEnabled2 = false;
				boolean isNetworkEnabled2=false;
				isGPSEnabled2 = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				isNetworkEnabled2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if(!isGPSEnabled2)
				{
					isGPSEnabled2 = false;
				}
				if(!isNetworkEnabled2)
				{
					isNetworkEnabled2 = false;
				}
				if(!isGPSEnabled2 && !isNetworkEnabled2)
				{
					try
					{
						showSettingsAlert();
					}
					catch(Exception e)
					{

					}

					isGPSEnabled2 = false;
					isNetworkEnabled2=false;
				}
				else{
					fnSaveFilledDataToDatabase(3);
				}
			}
		}
		else if(btnClkd==3) // btn return clkd
		{
			boolean isStockAvilable=false;
			for (Entry<String, String> entry : hmapProductIdStock.entrySet())
			{
				if(!entry.getValue().equals("0") )
				{
					isStockAvilable=true;
					break;
				}

			}
			if(isStockAvilable)
			{
				flagClkdButton=1;

				orderBookingTotalCalc();
				if(!alertOpens)
				{
					progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.genTermInformation);
					new SaveData().execute("1~3");
				}
			}

			else
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

				// Setting Dialog Title
				alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
				alertDialog.setIcon(R.drawable.error_info_ico);
				alertDialog.setCancelable(false);
				// Setting Dialog Message
				alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.NoStocks));

				// On pressing Settings button
				alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
					}
				});

				// Showing Alert Message
				alertDialog.show();

			}
				      /*
			   boolean isStockAvilable=false;
			    for (Entry<String, String> entry : hmapProductIdStock.entrySet())
			    {
			     if(!entry.getValue().equals("0") )
			     {
			      isStockAvilable=true;
			      break;
			     }

			    }
			    if(isStockAvilable)
			    {
			       new SaveData().execute("1");
			      Intent fireBackDetPg=new Intent(ProductList.this,ReturnActivity.class);
			           fireBackDetPg.putExtra("storeID", storeID);
			           fireBackDetPg.putExtra("SN", SN);
			           fireBackDetPg.putExtra("bck", 1);
			           fireBackDetPg.putExtra("imei", imei);
			           fireBackDetPg.putExtra("date", date);
			           fireBackDetPg.putExtra("pickerDate", pickerDate);
			           startActivity(fireBackDetPg);
			           finish();
			    }

			    else
			    {
			     Toast.makeText(ProductList.this, "There are no Stocks to Return Product", Toast.LENGTH_SHORT).show();
			    }
			   */
		}

		else if(btnClkd==4) // back button clkd
		{
			flagClkdButton=4;

			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileWeSave);

				new SaveData().execute("1~2");



			}
		}

		else if(btnClkd==5)// Collection Amount
		{
			progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);
			new SaveData().execute("1~5");
		}
		else if(btnClkd==6)    //Delivery_Details_Activity
		{
			progressTitle=ProductInvoiceReview.this.getResources().getString(R.string.WhileSave);
			new SaveData().execute("1~6");
		}
		/*else if(btnClkd==7)    //Delivery_Details_Activity
		{
			progressTitle=ProductOrderReview.this.getResources().getString(R.string.WhileSave);
			new SaveData().execute("1~7");
		}*/
		else if(btnClkd==7) // btn submit clkd
		{

			/*long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);

			String fileName=imei+"_"+storeID;

			File file = new File("/sdcard/"+CommonInfo.TextFileFolder+"/"+fileName);
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			CommonInfo.fileContent=CommonInfo.fileContent+"     "+imei+"_"+storeID+"_"+"Submit Button Click on Product List"+StartClickTimeFinal;

			FileWriter fw;
			try
			{
				fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(CommonInfo.fileContent);
				bw.close();

				dbengine.open();
				dbengine.savetblMessageTextFileContainer(fileName,0);
				dbengine.close();
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/

			orderBookingTotalCalc();
			/*if(!alertOpens)
			{
				boolean isGPSEnabled2 = false;
				boolean isNetworkEnabled2=false;
				isGPSEnabled2 = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
				isNetworkEnabled2 = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if(!isGPSEnabled2)
				{
					isGPSEnabled2 = false;
				}
				if(!isNetworkEnabled2)
				{
					isNetworkEnabled2 = false;
				}
				if(!isGPSEnabled2 && !isNetworkEnabled2)
				{
					try
					{
						showSettingsAlert();
					}
					catch(Exception e)
					{

					}

					isGPSEnabled2 = false;
					isNetworkEnabled2=false;
				}
				else{*/
					fnSaveFilledDataToDatabase(4);
				/*}
			}*/
		}

	}

	public String genTempInvoiceCodePDA()
	{
		//store ID generation <x>
		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
		String VisitStartTS = df.format(dateobj);
		String cxz;
		cxz = UUID.randomUUID().toString();
						/*cxz.split("^([^-]*,[^-]*,[^-]*,[^-]*),(.*)$");*/

		StringTokenizer tokens = new StringTokenizer(String.valueOf(cxz), "-");

		String val1 = tokens.nextToken().trim();
		String val2 = tokens.nextToken().trim();
		String val3 = tokens.nextToken().trim();
		String val4 = tokens.nextToken().trim();
		cxz = tokens.nextToken().trim();



						/*TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
						String imei = tManager.getDeviceId();*/
		String IMEIid =  CommonInfo.imei.substring(9);
		//cxz = IMEIid +"-"+cxz;
		cxz = "TmpInvoiceCodePDA" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


		return cxz;
		//-_
	}

	public void fnVisitFinalInvoiceDetailsSaving(int Outstat,String FinalInvoiceNumberGenerated)
	{


		int childcount =hmapPrdctIdPrdctName.size();

		for (Entry<String, String> entry:hmapPrdctIdPrdctName.entrySet() )
		{
			//  View vRow = ll_prdct_detal.getChildAt(index);

			int PCateId=Integer.parseInt(hmapCtgryPrdctDetail.get(entry.getKey()));
			String PName =entry.getValue();
			String ProductID=entry.getKey();
			String ProductStock =hmapProductIdStock.get(ProductID);
			double TaxRate=0.00;
			double TaxValue=0.00;
			if(ProductStock.equals(""))
			{
				ProductStock="0";
			}
			String SampleQTY =hmapPrdctSmpl.get(ProductID);
			if(SampleQTY.equals(""))
			{
				SampleQTY="0";
			}
			String OrderQTY =hmapPrdctOdrQty.get(ProductID);

			if(OrderQTY.equals(""))
			{
				OrderQTY="0";

			}
			String OrderValue="0";
			if(Integer.parseInt(OrderQTY)>0)
			{
				OrderValue =hmapProductIdOrdrVal.get(ProductID);// ((TextView)(vRow).findViewById(R.id.tv_Orderval)).getText().toString();
				if(OrderValue.equals(""))
				{
					OrderValue="0";
				}
			}

			String OrderFreeQty =hmapPrdctFreeQty.get(ProductID);
			if(OrderFreeQty.equals(""))
			{
				OrderFreeQty="0";
			}

			String OrderDisVal= "0";
			if(Integer.parseInt(OrderQTY)>0) {
				// hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);
				OrderDisVal= hmapPrdctIdPrdctDscnt.get(ProductID);
				if (OrderDisVal.equals("0.00")) {
					OrderDisVal = "0";
				}
			}
			String PRate="0.00";
			int flgIsQuoteRateApplied=0;
			if(PriceApplyDiscountLevelType==0) {
				PRate=hmapProductStandardRate.get(ProductID);
				TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
				TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));

				if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
				{
					if(hmapMinDlvrQty.containsKey(ProductID))
					{
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
						{
							flgIsQuoteRateApplied=1;
							PRate=hmapMinDlvrQtyQPAT.get(ProductID);
							TaxValue=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));

						}

					}
				}
			}
			if(PriceApplyDiscountLevelType==1) {
				if(flgAllTotalOrderValueCrossesCutOff==true)
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
				else
				{
					PRate=hmapProductStandardRate.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}
			if(PriceApplyDiscountLevelType==2) {
				if(Double.parseDouble(OrderValue)>=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID)))
				{
					PRate=hmapStandardRateWholeSale.get(ProductID);
					TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
					TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
				}
			}
			 	/*PRate=hmapProductStandardRate.get(ProductID);
		       TaxRate=Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID));
		       TaxValue=Double.parseDouble(hmapProductTaxValue.get(ProductID));
		       int flgIsQuoteRateApplied=0;
		       if(hmapMinDlvrQty!=null && hmapMinDlvrQty.size()>0)
				{
					if(hmapMinDlvrQty.containsKey(ProductID))
					{
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>(hmapMinDlvrQty.get(ProductID)-1))
						{
							flgIsQuoteRateApplied=1;
							PRate=hmapMinDlvrQtyQPAT.get(ProductID);
							TaxValue=Double.parseDouble(hmapMinDlvrQtyQPTaxAmount.get(ProductID));

						}

					}
				}*/
			// String TransDate=date;
			if(Integer.valueOf(OrderFreeQty)>0 || Integer.valueOf(SampleQTY)>0 || Integer.valueOf(OrderQTY)>0 || Integer.valueOf(OrderValue)>0 || Integer.valueOf(OrderDisVal)>0 || Integer.valueOf(ProductStock)>0)
			{
				dbengine.open();
				StoreCatNodeId=dbengine.fnGetStoreCatNodeId(storeID);
				int flgRuleTaxVal=1;
				dbengine.saveStoreFinalInvoiceDetails(TmpInvoiceCodePDA,storeID,""+PCateId,ProductID,Double.parseDouble(PRate),TaxRate,flgRuleTaxVal,Integer.parseInt(OrderQTY),Integer.parseInt(hmapProductSelectedUOMId.get(ProductID)),Double.parseDouble(hmapLineValBfrTxAftrDscnt.get(ProductID)),Double.parseDouble(hmapLineValAftrTxAftrDscnt.get(ProductID)),Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]),Double.parseDouble(OrderDisVal),Integer.parseInt(SampleQTY),PName,TaxValue,strGlobalOrderID,flgIsQuoteRateApplied,PriceApplyDiscountLevelType,distID,Outstat,FinalInvoiceNumberGenerated);
				dbengine.close();
			}



		}
		flgAllTotalOrderValueCrossesCutOff=false;
	}

	public void fnVisitFinalInvoiceHeaderSaving(int Outstat,String FinalInvoiceNumberGenerated)
	{
		Double INval;

		if(!tv_GrossInvVal.getText().toString().isEmpty()){


			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}



		Double TBtaxDis;
		Double TAmt;
		Double Dis;


		Double AddDis;
		Double InvAfterDis;

		Double INvalCreditAmt;
		Double INvalInvoiceAfterCreditAmt;

		Double INvalInvoiceOrginal=0.00;


		int Ftotal;




		if(!tv_NetInvValue.getText().toString().isEmpty()){
			TBtaxDis = Double.parseDouble(tv_NetInvValue.getText().toString().trim());
		}
		else{
			TBtaxDis = 0.00;
		}
		if(!tvTAmt.getText().toString().isEmpty()){
			TAmt = Double.parseDouble(tvTAmt.getText().toString().trim());
		}
		else{
			TAmt = 0.00;
		}
		if(!tvDis.getText().toString().isEmpty()){
			Dis = Double.parseDouble(tvDis.getText().toString().trim());
		}
		else{
			Dis = 0.00;
		}
		/*if(!tv_GrossInvVal.getText().toString().isEmpty()){

		*//*	if(Dis!=0.00)
			{
				INval = Double.parseDouble(tvINval.getText().toString().trim())-Dis;
			}
			else
			{
				INval = Double.parseDouble(tvINval.getText().toString().trim());
			}*//*
			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}*/
		if(!tvFtotal.getText().toString().isEmpty()){
			Double FtotalValue=Double.parseDouble(tvFtotal.getText().toString().trim());
			Ftotal =FtotalValue.intValue();
		}
		else{
			Ftotal = 0;
		}

		if(!tv_NetInvAfterDiscount.getText().toString().isEmpty()){
			InvAfterDis = Double.parseDouble(tv_NetInvAfterDiscount.getText().toString().trim());
		}
		else{
			InvAfterDis = 0.00;
		}
		if(!tvAddDisc.getText().toString().isEmpty()){
			AddDis = Double.parseDouble(tvAddDisc.getText().toString().trim());
		}
		else{
			AddDis = 0.00;
		}


		Double AmtPrevDueVA=0.00;
		Double AmtCollVA=0.00;
		Double AmtOutstandingVAL=0.00;
		if(!tvAmtPrevDueVAL.getText().toString().isEmpty()){
			AmtPrevDueVA = Double.parseDouble(tvAmtPrevDueVAL.getText().toString().trim());
		}
		else{
			AmtPrevDueVA = 0.00;
		}
		if(!etAmtCollVAL.getText().toString().isEmpty()){
			AmtCollVA = Double.parseDouble(etAmtCollVAL.getText().toString().trim());
		}
		else{
			AmtCollVA = 0.00;
		}

		if(!tvAmtOutstandingVAL.getText().toString().isEmpty()){
			AmtOutstandingVAL = Double.parseDouble(tvAmtOutstandingVAL.getText().toString().trim());
		}
		else{
			AmtOutstandingVAL = 0.00;
		}

		int NoOfCouponValue=0;
		/*if(!txttvNoOfCouponValue.getText().toString().isEmpty()){
			NoOfCouponValue = Integer.parseInt(txttvNoOfCouponValue.getText().toString().trim());
		}
		else{
			NoOfCouponValue = 0;
		}
		*/
		Double TotalCoupunAmount=0.00;
		if(!txttvCouponAmountValue.getText().toString().isEmpty()){
			TotalCoupunAmount = Double.parseDouble(txttvCouponAmountValue.getText().toString().trim());
		}
		else{
			TotalCoupunAmount = 0.00;
		}

		int flgRuleTaxVal=1;
		int flgTransType=1;
		dbengine.open();
		//dbengine.saveStoreInvoice(imei,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, AmtPrevDueVA, AmtCollVA, AmtOutstandingVAL, NoOfCouponValue, TotalCoupunAmount,Outstat,strGlobalOrderID,TmpInvoiceCodePDA,strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);

		dbengine.fnsaveStoreFinalInvoiceSummaryEntry(StoreVisitCode,TmpInvoiceCodePDA,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis,  NoOfCouponValue, TotalCoupunAmount,pickerDate,flgTransType,PriceApplyDiscountLevelType,flgRuleTaxVal,Outstat,FinalInvoiceNumberGenerated);//strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
		dbengine.close();



	}

	@Override
	public void testFunctionOne(String fnLati, String fnLongi, String finalAccuracy, String fnAccurateProvider, String GpsLat, String GpsLong, String GpsAccuracy, String NetwLat, String NetwLong, String NetwAccuracy, String FusedLat, String FusedLong, String FusedAccuracy, String AllProvidersLocation, String GpsAddress, String NetwAddress, String FusedAddress, String FusedLocationLatitudeWithFirstAttempt, String FusedLocationLongitudeWithFirstAttempt, String FusedLocationAccuracyWithFirstAttempt, int flgLocationServicesOnOff, int flgGPSOnOff, int flgNetworkOnOff, int flgFusedOnOff, int flgInternetOnOffWhileLocationTracking, String address, String pincode, String city, String state) {
		if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy)))
		{

			fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
			UpdateLocationAndProductAllData();
		}
		else
		{
			countSubmitClicked++;
			if(countSubmitClicked==1)
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

				// Setting Dialog Title
				alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
				alertDialog.setIcon(R.drawable.error_info_ico);
				alertDialog.setCancelable(false);
				// Setting Dialog Message
				alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.AlertSameLoc));

				// On pressing Settings button
				alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
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
		public FullSyncDataNow(ProductInvoiceReview activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.SubmittingInvoiceOrderDetails));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();


		}

		@Override

		protected Void doInBackground(Void... params) {

			int Outstat=1;
			flgTransferStatus=1;
			InvoiceTableDataDeleteAndSaving(Outstat,flgTransferStatus);
				TransactionTableDataDeleteAndSaving(Outstat);
			Double cntInvoceValue=dbengine.fetch_Store_InvValAmount(storeID,TmpInvoiceCodePDA);
			cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

			 Outstat=3;


			if(Outstat==3) {
				if (cntInvoceValue > 0.0) {
					if(strFinalAllotedInvoiceIds.equals("NA"))
					{

						/*strFinalAllotedInvoiceIds=dbengine.fnGetFinalAllotedInvoiceIds(storeID);
						dbengine.updateFinalAllotedInvoiceIdstblInvoiceCaption();

						fnVisitFinalInvoiceHeaderSaving(Outstat,strFinalAllotedInvoiceIds);
						fnVisitFinalInvoiceDetailsSaving(Outstat,strFinalAllotedInvoiceIds);
						dbengine.UpdateStoreVisitMStrTable(storeID,3,StoreVisitCode);


						dbengine.UpdateStoreVisitWiseTables(storeID, 3,StoreVisitCode,TmpInvoiceCodePDA);

						dbengine.deleteOldStoreInvoice(storeID,strGlobalOrderID,TmpInvoiceCodePDA);
						dbengine.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID,strGlobalOrderID,TmpInvoiceCodePDA);*/
						dbengine.fnTransferDataFromTempToPermanent(storeID,StoreVisitCode,TmpInvoiceCodePDA,flgDrctslsIndrctSls);

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
				/*dbengine.open();
				dbengine.UpdatetblStoreClosedPhotoDetail(storeID.trim(), 5);

				dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 5,strGlobalOrderID,TmpInvoiceCodePDA);
				dbengine.UpdateStoreMaterialphotoFlag(storeID.trim(), 5);
				dbengine.UpdateStoreReturnphotoFlag(storeID.trim(), 5);
				dbengine.UpdateNewAddedStorephotoFlag(storeID.trim(), 5);
				//dbengine.UpdateStoreImage(storeID.trim(), 5);



				dbengine.close();
				dbengine.UpdateStoreVisitMStrTable(storeID,3,StoreVisitCode);
				dbengine.UpdateStoreVisitWiseTables(storeID.trim(), 5,StoreVisitCode,TmpInvoiceCodePDA);
				VisitTypeStatus="0";
				dbengine.updateVisitTypeStatusOfStore(storeID,VisitTypeStatus,StoreVisitCode);
				if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,TmpInvoiceCodePDA)==0)
				{
					String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
					if(!strDefaultPaymentStageForStore.equals(""))
					{
						dbengine.open();
						dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"4",TmpInvoiceCodePDA);
						dbengine.close();
					}
				}*/
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
				Intent syncIntent = new Intent(ProductInvoiceReview.this, SyncMaster.class);
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

		public class GetData extends AsyncTask<Void, Void, Void>
		 {

		   @Override
		         protected void onPreExecute() {
		             super.onPreExecute();

		             mProgressDialog = new ProgressDialog(ProductInvoiceReview.this);
		             mProgressDialog.setTitle(ProductInvoiceReview.this.getResources().getString(R.string.genTermPleaseWaitNew));
		             mProgressDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.Loading));
		             mProgressDialog.setIndeterminate(true);
		             mProgressDialog.setCancelable(false);
		            mProgressDialog.show();
		         }

		  @Override
		  protected Void doInBackground(Void... params)
		  {
		   hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();



			  //chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
			  if(CommonInfo.FlgDSRSO==1)
			  {
				  String SOCoverageAreaIDAndType=dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();
				  flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[0]),Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[1]));
			  }
			  else
			  {
				  flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
			  }
			  if(flgDrctslsIndrctSls==0)
			  {
				  chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,flgDrctslsIndrctSls);
				  if(chkflgInvoiceAlreadyGenerated==1)
				  {
					  TmpInvoiceCodePDA=dbengine.fnGetInvoiceCodePDA(storeID,StoreVisitCode);

				  }
				   if(dbengine.fnCheckForNewInvoiceOrPreviousValueFromPermanentTable(storeID,StoreVisitCode)==1)
				  {
					  TmpInvoiceCodePDA=dbengine.fnGetInvoiceCodePDAFromPermanentTable(storeID,StoreVisitCode);
				  }
				  else
				  {
					  TmpInvoiceCodePDA = genTempInvoiceCodePDA();
				  }
			  }
			  else {
				  chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,flgDrctslsIndrctSls);
				  if (chkflgInvoiceAlreadyGenerated == 1) {
					  TmpInvoiceCodePDA = dbengine.fnGetInvoiceCodePDA(storeID, StoreVisitCode);

				  } else {


					  TmpInvoiceCodePDA = genTempInvoiceCodePDA();//dbengine.fnGettblInvoiceCaption(storeID);
				  }
			  }
			  /* if(chkflgInvoiceAlreadyGenerated==1)
			  {
				  TmpInvoiceCodePDA=dbengine.fnGetInvoiceCodePDA(storeID,StoreVisitCode);
			  }
			  else
			  {
				  TmpInvoiceCodePDA=genTempInvoiceCodePDA();
			  }*/
			  CheckIfStoreExistInStoreProdcutPurchaseDetails=dbengine.fnCheckIfStoreExistInStoreProdcutPurchaseDetails(storeID,TmpInvoiceCodePDA,flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
			  CheckIfStoreExistInStoreProdcutInvoiceDetails=dbengine.fnCheckIfStoreExistInStoreProdcutInvoiceDetails(storeID,TmpInvoiceCodePDA);
			  strGlobalOrderID=TmpInvoiceCodePDA;
			 /* if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1 || CheckIfStoreExistInStoreProdcutInvoiceDetails==1)
			  {
				  strGlobalOrderID=dbengine.fngetOrderIDAganistStore(storeID,TmpInvoiceCodePDA);
			  }
			  else
			  {
				  strGlobalOrderID= genOutOrderID();
			  }*/
		   getCategoryDetail();

		   getProductData();

			  getCutOffDetailsForProduct();
			  dbengine.open();
			  hmapFetchPDASavedData=dbengine.fetchActualVisitData(storeID);

			  dbengine.close();

			  if(hmapFetchPDASavedData!=null && hmapFetchPDASavedData.size()>0)
			  {

				  hmapProductIdStock.putAll(hmapFetchPDASavedData);

			  }
			  distID=dbengine.getDisId(storeID);
			  hmapDistPrdctStockCount=dbengine.fnGetFinalInvoiceQtyProductWise(flgDrctslsIndrctSls);
			  hmapPerUnitName=dbengine.getPerUnitName();
			  hmapPerBaseQty=dbengine.getPerBaseQty();
		   return null;
		  }
		   @Override
		         protected void onPostExecute(Void args) {
			   loadPurchaseProductDefault();
		       mProgressDialog.dismiss();

		  }

		 }

		public class SaveData extends AsyncTask<String, String, Void>
		 {

		   @Override
		         protected void onPreExecute() {
		             super.onPreExecute();
		             //Text need to e changed according to btn Click


		             if(mProgressDialog.isShowing()==false)
		             {
		             mProgressDialog = new ProgressDialog(ProductInvoiceReview.this);
		             mProgressDialog.setTitle(ProductInvoiceReview.this.getResources().getString(R.string.genTermPleaseWaitNew));
		             mProgressDialog.setMessage(progressTitle);
		             mProgressDialog.setIndeterminate(true);
		             mProgressDialog.setCancelable(false);
		            mProgressDialog.show();
		             }
		         }

		  @Override
		  protected Void doInBackground(String... params)
		  {
			  String executedData=params[0];

			  int btnClkd;
			  if(executedData.contains("~"))
			  {
				  btnClkd=Integer.parseInt(executedData.split(Pattern.quote("~"))[0]);
				  isReturnClkd=Integer.parseInt(executedData.split(Pattern.quote("~"))[1]);
			  }

			  else
			  {
				  btnClkd=Integer.parseInt(executedData);
			  }


			  fnSaveFilledDataToDatabase(btnClkd);
		   return null;
		  }
		   @Override
		         protected void onPostExecute(Void args) {
		   if(mProgressDialog!=null) {
			   if (mProgressDialog.isShowing() == true) {
				   mProgressDialog.dismiss();
			   }
		   }
			   long syncTIMESTAMP = System.currentTimeMillis();
				Date dateobj = new Date(syncTIMESTAMP);
				SimpleDateFormat df = new SimpleDateFormat(
						"dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
				String startTS = df.format(dateobj);
				dbengine.open();
				dbengine.UpdateStoreEndVisit(storeID,startTS);
				dbengine.close();
		     if(isReturnClkd==3)
		     {
		    	   Intent fireBackDetPg=new Intent(ProductInvoiceReview.this,ReturnActivity.class);
		           fireBackDetPg.putExtra("storeID", storeID);
		           fireBackDetPg.putExtra("SN", SN);
		           fireBackDetPg.putExtra("bck", 1);
		           fireBackDetPg.putExtra("imei", imei);
		           fireBackDetPg.putExtra("userdate", date);
		           fireBackDetPg.putExtra("pickerDate", pickerDate);
		           fireBackDetPg.putExtra("OrderPDAID", strGlobalOrderID);
		           fireBackDetPg.putExtra("flgPageToRedirect", "2");
		          // fireBackDetPg.putExtra("rID", routeID);

		           startActivity(fireBackDetPg);
		           finish();
		     }

		     else if(isReturnClkd==2)
		     {
		    	 //Intent fireBackDetPg=new Intent(ProductOrderSearch.this,POSMaterialActivity.class);
		    	Intent fireBackDetPg=new Intent(ProductInvoiceReview.this,ProductOrderEntry.class);
			    fireBackDetPg.putExtra("storeID", storeID);
			    fireBackDetPg.putExtra("SN", SN);
			    fireBackDetPg.putExtra("bck", 1);
			    fireBackDetPg.putExtra("imei", imei);
			    fireBackDetPg.putExtra("userdate", date);
			    fireBackDetPg.putExtra("pickerDate", pickerDate);
			    //fireBackDetPg.putExtra("rID", routeID);
			    startActivity(fireBackDetPg);
			    finish();
		     }
			 else if(isReturnClkd==5)
			 {
				 Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, CollectionActivityNew.class);
				 AmtCollectIntent.putExtra("storeID", storeID);
				 AmtCollectIntent.putExtra("imei", imei);
				 AmtCollectIntent.putExtra("userdate", date);
				 AmtCollectIntent.putExtra("pickerDate", pickerDate);
				 AmtCollectIntent.putExtra("SN", SN);
				 AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
				 AmtCollectIntent.putExtra("FromPlace", "1");
				// AmtCollectIntent.putExtra("flgOrderType",flgOrderType);
				 startActivity(AmtCollectIntent);
				 finish();
			 }
			 else if(isReturnClkd==6)
			 {
				 Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, Delivery_Details_Activity.class);   //
				 AmtCollectIntent.putExtra("storeID", storeID);
				 AmtCollectIntent.putExtra("imei", imei);
				 AmtCollectIntent.putExtra("userdate", date);
				 AmtCollectIntent.putExtra("pickerDate", pickerDate);
				 AmtCollectIntent.putExtra("SN", SN);
				 AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
				// AmtCollectIntent.putExtra("flgOrderType",flgOrderType);
				 startActivity(AmtCollectIntent);
				 finish();
			 }
			 else if(isReturnClkd==7)
			 {
				 Intent AmtCollectIntent = new Intent(ProductInvoiceReview.this, CollectionActivityNew.class);   //
				 AmtCollectIntent.putExtra("storeID", storeID);
				 AmtCollectIntent.putExtra("imei", imei);
				 AmtCollectIntent.putExtra("userdate", date);
				 AmtCollectIntent.putExtra("pickerDate", pickerDate);
				 AmtCollectIntent.putExtra("SN", SN);
				 AmtCollectIntent.putExtra("OrderPDAID", strGlobalOrderID);
				 AmtCollectIntent.putExtra("FromPlace", "1");
				 // AmtCollectIntent.putExtra("flgOrderType",flgOrderType);
				 startActivity(AmtCollectIntent);
				 finish();
			 }
		   }


		 }

		 public class standBYtask extends AsyncTask<Void, Void, Void>
			{

				@Override
				protected Void doInBackground(Void... params)
				{
					// TODO Auto-generated method stub

					// Thread ttt = new Thread();
					return null;
				}

				@Override
				protected void onPostExecute(Void result)
				{
					// TODO Auto-generated method stub
					super.onPostExecute(result);
				}

				@Override
				protected void onPreExecute()
				{
					// TODO Auto-generated method stub
					super.onPreExecute();
				}

			}

		public class CoundownClass2 extends CountDownTimer{

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

					showAlertForEveryOne(ProductInvoiceReview.this.getResources().getString(R.string.AlertTryAgain));
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
							AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductInvoiceReview.this);

							// Setting Dialog Title
							alertDialog.setTitle(getText(R.string.genTermNoDataConnection));
							alertDialog.setIcon(R.drawable.error_info_ico);
							alertDialog.setCancelable(false);
							// Setting Dialog Message
							alertDialog.setMessage(ProductInvoiceReview.this.getResources().getString(R.string.AlertSameLoc));

							// On pressing Settings button
							alertDialog.setPositiveButton(ProductInvoiceReview.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
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

}