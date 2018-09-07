package project.astix.com.balajisosfadirectsales;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


import com.astix.Common.CommonInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;



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
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductOrderFilterSearch  extends BaseActivity implements OnItemSelectedListener, OnClickListener, OnFocusChangeListener, LocationListener,GoogleApiClient.ConnectionCallbacks,
GoogleApiClient.OnConnectionFailedListener,CategoryCommunicator
{

	    private static final String TAG = "LocationActivity";
	    private static final long INTERVAL = 1000 * 10;
	    private static final long FASTEST_INTERVAL = 1000 * 5;
		  private static final long MIN_TIME_BW_UPDATES = 1000  * 1; //1 second
	    private final long startTime = 15000;
	    private final long interval = 200;
	public TextView tvRateHeading;
	public String VisitTypeStatus="1";
	public boolean flgAllTotalOrderValueCrossesCutOff=false;
	public TextView tvPreAmtOutstandingVALNew;
	public TextView tvAfterTaxValue;
	public TableLayout tbl1_dyntable_For_ExecutionDetails;
	public TableLayout tbl1_dyntable_For_OrderDetails;
	public  int flgLocationServicesOnOffOrderReview=0;
	public  int flgGPSOnOffOrderReview=0;
	public  int flgNetworkOnOffOrderReview=0;
	public  int flgFusedOnOffOrderReview=0;
	public  int flgInternetOnOffWhileLocationTrackingOrderReview=0;
	public  int flgRestartOrderReview=0;
	public  int flgStoreOrderOrderReview=0;
    public int PriceApplyDiscountLevelType=0;
    public Double cutoffvalue=0.0;
	public int powerCheck=0;
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
		public ProgressDialog pDialog2STANDBYabhi;
		public String date;
		public String pickerDate;
		public String SN;
		//public ProgressDialog pDialogSync;
		 public String productID;
	public String strFinalAllotedInvoiceIds="NA";
	     public String strGlobalInvoiceNumber="NA";
		public int chkflgInvoiceAlreadyGenerated=0;
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
		public TextView tvAmtPrevDueVAL;
		public EditText etAmtCollVAL;
		public TextView tvAmtOutstandingVAL;
		public TextView tvCredAmtVAL;
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
		  public ProgressDialog pDialog2STANDBY;
		 public int flgProDataCalculation=1;
		 public int StoreCatNodeId=0;
		 public ListView lvProduct;
		 public EditText inputSearch;


	//Nitish DUbey3

	//Nitish DUbey2
//Abhinav Raj
	CustomKeyboard mCustomKeyboardNum,mCustomKeyboardNumWithoutDecimal;
	int countSubmitClicked=0;
	ArrayList<String> productFullFilledSlabGlobal=new ArrayList<String>();
	ImageView img_ctgry;
	String previousSlctdCtgry="";
	LocationRequest mLocationRequest;
	Timer timer;
		//MyTimerTask myTimerTask;
		String defaultValForAlert;
		int progressBarStatus=0;
		Thread myThread;
		boolean disValClkdOpenAlert=false;
		String progressTitle;
		 boolean alertOpens=false;
		 int flagClkdButton=0;
		String distID="";
	HashMap<String,Integer> hmapDistPrdctStockCountText =new HashMap<String,Integer>();
		HashMap<String,Integer> hmapDistPrdctStockCount =new HashMap<String,Integer>();
	HashMap<String,String> hmapPrdctIdOutofStock=new HashMap<String,String> ();
		LinkedHashMap<String,Integer> hmapflgProduct =new LinkedHashMap<String,Integer>();
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
		List<String> categoryNames;
	String defaultCatName_Id="0";
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
		 // hmaSchemeSlabIdSlabDes key=SchemeSlabId value=SchemeSlab Description
		 HashMap<String, String> hmapSchemeSlabIdSlabDes=new HashMap<String, String>();
		   //HashMap<String, String> hmapSchemeStoreID=new HashMap<String, String>();
		 // hmapSchemeSlabIdBenifitDes key=SchemeSlabId value = BenifitDescription
		 HashMap<String, String> hmapSchemeSlabIdBenifitDes=new HashMap<String, String>();
		 // hmapProductIdOrdrVal key=Product Id value=ProductOrderVal
		 HashMap<String, String> hmapProductIdOrdrVal =new HashMap<String, String>();
		 // hmapProductIdStock key = ProductID value= flgPriceAva
		 HashMap<String, String> hmapProductflgPriceAva=new HashMap<String, String>();
	// hmapProductIdStock key = ProductID value= Stock
	HashMap<String, String> hmapProductIdStock=new HashMap<String, String>();
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
		   //hmapMinDlvrQty= key =ProductID         value=MinQty
		   LinkedHashMap<String, Integer> hmapMinDlvrQty=new LinkedHashMap<String, Integer>();
		   //hmapMinDlvrQty= key =ProductID         value=QPBT
		   LinkedHashMap<String, String> hmapMinDlvrQtyQPBT=new LinkedHashMap<String, String>();
		 //hmapMinDlvrQty= key =ProductID         value=QPBT
		   LinkedHashMap<String, String> hmapMinDlvrQtyQPAT=new LinkedHashMap<String, String>();
		   //hmapMinDlvrQty= key =ProductID         value=QPTaxAmount
		   LinkedHashMap<String, String> hmapMinDlvrQtyQPTaxAmount=new LinkedHashMap<String, String>();
		HashMap<String, HashMap<String, HashMap<String, String>>> hmapPrdtAppliedSchIdsAppliedSlabIdsDefination;

		//Database
	//hmapProductStock= key =ProductID         value=flgWholeSellApplicable
	HashMap<String, String> hmapflgWholeSellApplicable=new HashMap<String, String>();
	//hmapProductStock= key =ProductID         value=flgPriceRangeWholeSellApplicable
	HashMap<String, String> hmapPriceRangeWholeSellApplicable=new HashMap<String, String>();

		 //Common Controls Box
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
		  PowerManager.WakeLock wl;
		  LocationListener locationListener;
		  double latitude;
		  double longitude;
		 View convertView;
		 ArrayAdapter<String> adapter;
		 AlertDialog ad;
		 String[] products;
		Runnable myRunnable = new Runnable(){

		     @Override
		     public void run() {

		       runOnUiThread(new Runnable(){

		         @Override
		         public void run() {


		          new CountDownTimer(2000, 1000) {

		              public void onTick(long millisUntilFinished) {
		               if(pDialog2STANDBYabhi != null && pDialog2STANDBYabhi.isShowing())
		               {
		                pDialog2STANDBYabhi.setCancelable(false);

		               }
		              }

		              public void onFinish() {
		               /*try {
		          Thread.sleep(2000);
		         } catch (InterruptedException e) {
		          // TODO Auto-generated catch block
		          e.printStackTrace();
		         }*/
		              new IAmABackgroundTask().execute();
		              // createProductPrepopulateDetailWhileSearch(CheckIfStoreExistInStoreProdcutPurchaseDetails);
		              }
		           }.start();



		               //pDialog2STANDBYabhi.show(ProductOrderFilterSearch.this,getText(R.string.genTermPleaseWaitNew) ,"Loading Data Abhinav", true);
		           //pDialog2STANDBYabhi.getCurrentFocus();
		           // pDialog2STANDBYabhi.show();




		         }

		        });

		     }
		       };
		private Handler mHandler = new Handler();
		private boolean alrtStopResult = false;

		 public void showSettingsAlert()
		 {
			 AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		        // Setting Dialog Title
		        alertDialog.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
		        alertDialog.setIcon(R.drawable.error_info_ico);
		        alertDialog.setCancelable(false);
		        // Setting Dialog Message
		        alertDialog.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermGPSDisablePleaseEnable));

		        // On pressing Settings button
		        alertDialog.setPositiveButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
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





				try
				{

				dbengine.open();
				String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
				dbengine.close();
				//System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
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



					 final AlertDialog builder = new AlertDialog.Builder(ProductOrderFilterSearch.this).create();


						LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				        View openDialog = inflater.inflate(R.layout.custom_dialog, null);
				        openDialog.setBackgroundColor(Color.parseColor("#ffffff"));

				        builder.setCancelable(false);
				     	TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
				     	final TextView msg=(TextView)openDialog. findViewById(R.id.msg);

						final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
						final Button cancel=(Button)openDialog. findViewById(R.id.but_no);

						cancel.setVisibility(View.GONE);
					    header_text.setText(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogHeaderMsg));
					     msg.setText(Noti_text);

					     	ok_but.setText(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton));

							builder.setView(openDialog,0,0,0,0);

					        ok_but.setOnClickListener(new OnClickListener()
					        {

								@Override
								public void onClick(View arg0)
								{

									long syncTIMESTAMP = System.currentTimeMillis();
									Date dateobj = new Date(syncTIMESTAMP);
									SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
									String Noti_ReadDateTime = df.format(dateobj);

									dbengine.open();
									dbengine.updatetblNotificationMstr(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
									dbengine.close();

									try
									{
										dbengine.open();
									    int checkleftNoti=dbengine.countNumberOFNotificationtblNotificationMstr();
									    if(checkleftNoti>0)
									    {
										    	String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
												//System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
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
														  msg.setText(Noti_text);
													}
												}

									    }
										else
										{
											builder.dismiss();
										}

									}
									catch(Exception e)
									{

									}
						            finally
						            {
						            	dbengine.close();

						            }


								}
							});




					     	builder.show();






				}
				}
				}
				catch(Exception e)
				{

				}



				new GetData().execute();
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
			setContentView(R.layout.activity_product_list);
			// Toast.makeText(ProductOrderFilterSearch.this, "ProductOrderFilterSearch Page is called", Toast.LENGTH_SHORT).show();

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
	            	// System.out.println("Betu Slab = "+stringArray[index]);
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
		        tv.setText(getText(R.string.genTermInformation));
		        tv.setPadding(40, 10, 40, 10);
		        tv.setBackgroundColor(Color.parseColor("#486FA8"));
		        tv.setGravity(Gravity.CENTER);
		        tv.setTextSize(20);
		        tv.setTextColor(Color.parseColor("#ffffff"));


		        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		        tv1Params.bottomMargin = 5;





		        for(int i=0;i<arrSchId.length;i++)
			        {

			        	LinearLayout ChildViewDynamic = new LinearLayout(ProductOrderFilterSearch.this);
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
						    tv3.setText("Benifit :"+hmapSchmeSlabIdBenifitDes.get(AllSchemeSlabID[j]));  // It is for Live
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
		        alertDialogBuilder.setPositiveButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
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

		public void initializeFields() {

			mCustomKeyboardNum= new CustomKeyboard(this, R.id.keyboardviewNum, R.xml.num );
			mCustomKeyboardNumWithoutDecimal= new CustomKeyboard(this, R.id.keyboardviewNumDecimal, R.xml.num_without_decimal );

			ImageView executionDetails_butn=(ImageView)findViewById(R.id.txt_execution_Details);
			tvRateHeading=null;//(TextView) findViewById(R.id.tvRateHeading);
			executionDetails_butn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					// TODO Auto-generated method stub

					LayoutInflater layoutInflater = LayoutInflater.from(ProductOrderFilterSearch.this);
					View promptView = layoutInflater.inflate(R.layout.lastsummary_execution, null);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProductOrderFilterSearch.this);


					alertDialogBuilder.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));



					dbengine.open();

					String DateResult[]=dbengine.fetchOrderDateFromtblForPDAGetExecutionSummary(storeID);
					String LastexecutionDetail[]=dbengine.fetchAllDataFromtbltblForPDAGetExecutionSummary(storeID);

					String PrdNameDetail[]=dbengine.fetchPrdNameFromtblForPDAGetExecutionSummary(storeID);

					String ProductIDDetail[]=dbengine.fetchProductIDFromtblForPDAGetExecutionSummary(storeID);


					//System.out.println("Ashish and Anuj LastexecutionDetail : "+LastexecutionDetail.length);
					dbengine.close();

					if(DateResult.length>0)
					{
						TextView FirstDate = (TextView)promptView.findViewById(R.id.FirstDate);
						TextView SecondDate = (TextView)promptView.findViewById(R.id.SecondDate);
						TextView ThirdDate = (TextView)promptView.findViewById(R.id.ThirdDate);

						TextView lastExecution = (TextView)promptView.findViewById(R.id.lastExecution);
						lastExecution.setText(ProductOrderFilterSearch.this.getResources().getString(R.string.lastvisitdetails_last)
								+DateResult.length+ProductOrderFilterSearch.this.getResources().getString(R.string.ExecSummary));





						if(DateResult.length==1)
						{
							FirstDate.setText(""+DateResult[0]);
							SecondDate.setVisibility(View.GONE);
							ThirdDate.setVisibility(View.GONE);
						}
						else if(DateResult.length==2)
						{
							FirstDate.setText(""+DateResult[0]);
							SecondDate.setText(""+DateResult[1]);
							ThirdDate.setVisibility(View.GONE);
						}
						else if(DateResult.length==3)
						{
							FirstDate.setText(""+DateResult[0]);
							SecondDate.setText(""+DateResult[1]);
							ThirdDate.setText(""+DateResult[2]);
						}
					}

					LayoutInflater inflater = getLayoutInflater();

					DisplayMetrics dm = new DisplayMetrics();
					getWindowManager().getDefaultDisplay().getMetrics(dm);
					double x = Math.pow(dm.widthPixels/dm.xdpi,2);
					double y = Math.pow(dm.heightPixels/dm.ydpi,2);
					double screenInches = Math.sqrt(x+y);
					if(LastexecutionDetail.length>0)
					{
						alertDialogBuilder.setView(promptView);




						tbl1_dyntable_For_ExecutionDetails = (TableLayout) promptView.findViewById(R.id.dyntable_For_ExecutionDetails);
						TableRow row1 = (TableRow)inflater.inflate(R.layout.table_execution_head, tbl1_dyntable_For_OrderDetails, false);

						TextView firstDateOrder = (TextView)row1.findViewById(R.id.firstDateOrder);
						TextView firstDateInvoice = (TextView)row1.findViewById(R.id.firstDateInvoice);
						TextView secondDateOrder = (TextView)row1.findViewById(R.id.secondDateOrder);
						TextView secondDateInvoice = (TextView)row1.findViewById(R.id.secondDateInvoice);
						TextView thirdDateOrder = (TextView)row1.findViewById(R.id.thirdDateOrder);
						TextView thirdDateInvoice = (TextView)row1.findViewById(R.id.thirdDateInvoice);
						if(DateResult.length>0)
						{
							if(DateResult.length==1)
							{

								secondDateOrder.setVisibility(View.GONE);
								secondDateInvoice.setVisibility(View.GONE);
								thirdDateOrder.setVisibility(View.GONE);
								thirdDateInvoice.setVisibility(View.GONE);
							}
							else if(DateResult.length==2)
							{
								thirdDateOrder.setVisibility(View.GONE);
								thirdDateInvoice.setVisibility(View.GONE);
							}
						}

						tbl1_dyntable_For_ExecutionDetails.addView(row1);


						for (int current = 0; current <= (PrdNameDetail.length - 1); current++)
						{


							final TableRow row = (TableRow)inflater.inflate(R.layout.table_execution_row, tbl1_dyntable_For_OrderDetails, false);

							TextView tv1 = (TextView)row.findViewById(R.id.skuName);
							TextView tv2 = (TextView)row.findViewById(R.id.firstDateOrder);
							TextView tv3 = (TextView)row.findViewById(R.id.firstDateInvoice);
							TextView tv4 = (TextView)row.findViewById(R.id.secondDateOrder);
							TextView tv5 = (TextView)row.findViewById(R.id.secondDateInvoice);
							TextView tv6 = (TextView)row.findViewById(R.id.thirdDateOrder);
							TextView tv7 = (TextView)row.findViewById(R.id.thirdDateInvoice);

							tv1.setText(PrdNameDetail[current]);

							if(DateResult.length>0)
							{
								if(DateResult.length==1)
								{
									tv4.setVisibility(View.GONE);
									tv5.setVisibility(View.GONE);
									tv6.setVisibility(View.GONE);
									tv7.setVisibility(View.GONE);
									dbengine.open();
									String abc[]=dbengine.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[0],ProductIDDetail[current]);
									dbengine.close();

									//System.out.println("Check Value Number "+abc.length);
									//System.out.println("Check Value Number12 "+DateResult[0]);
									if(abc.length>0)
									{
										StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
										tv2.setText(tokens.nextToken().trim());
										tv3.setText(tokens.nextToken().trim());
									}
									else
									{
										tv2.setText("0");
										tv3.setText("0");
									}
								}
								else if(DateResult.length==2)
								{
									tv6.setVisibility(View.GONE);
									tv7.setVisibility(View.GONE);

									dbengine.open();
									String abc[]=dbengine.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[0],ProductIDDetail[current]);
									dbengine.close();

									//System.out.println("Check Value Number "+abc.length);
									//System.out.println("Check Value Number12 "+DateResult[0]);
									if(abc.length>0)
									{
										StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
										tv2.setText(tokens.nextToken().trim());
										tv3.setText(tokens.nextToken().trim());
									}
									else
									{
										tv2.setText("0");
										tv3.setText("0");
									}

									dbengine.open();
									String abc1[]=dbengine.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[1],ProductIDDetail[current]);
									dbengine.close();


									if(abc1.length>0)
									{
										StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
										tv4.setText(tokens.nextToken().trim());
										tv5.setText(tokens.nextToken().trim());
									}
									else
									{
										tv4.setText("0");
										tv5.setText("0");
									}





								}
								else if(DateResult.length==3)
								{
									dbengine.open();
									String abc[]=dbengine.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[0],ProductIDDetail[current]);
									dbengine.close();


									if(abc.length>0)
									{
										StringTokenizer tokens = new StringTokenizer(String.valueOf(abc[0]), "_");
										tv2.setText(tokens.nextToken().trim());
										tv3.setText(tokens.nextToken().trim());
									}
									else
									{
										tv2.setText("0");
										tv3.setText("0");
									}

									dbengine.open();
									String abc1[]=dbengine.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[1],ProductIDDetail[current]);
									dbengine.close();


									if(abc1.length>0)
									{
										StringTokenizer tokens = new StringTokenizer(String.valueOf(abc1[0]), "_");
										tv4.setText(tokens.nextToken().trim());
										tv5.setText(tokens.nextToken().trim());
									}
									else
									{
										tv4.setText("0");
										tv5.setText("0");
									}

									dbengine.open();
									String abc2[]=dbengine.fetchAllDataNewFromtbltblForPDAGetExecutionSummary(storeID,DateResult[2],ProductIDDetail[current]);
									dbengine.close();


									if(abc2.length>0)
									{
										StringTokenizer tokens = new StringTokenizer(String.valueOf(abc2[0]), "_");
										tv6.setText(tokens.nextToken().trim());
										tv7.setText(tokens.nextToken().trim());
									}
									else
									{
										tv6.setText("0");
										tv7.setText("0");
									}





								}
								else
								{

								}
							}

					/*if(screenInches>6.5)
					{
						tv1.setTextSize(14);
						tv2.setTextSize(14);
						tv3.setTextSize(14);
						tv4.setTextSize(14);
						tv5.setTextSize(14);
						tv6.setTextSize(14);
						tv7.setTextSize(14);
					}
					else
					{

					}*/


					/*StringTokenizer tokens = new StringTokenizer(String.valueOf(LastexecutionDetail[current]), "_");

					tv1.setText(tokens.nextToken().trim());
					tv2.setText(tokens.nextToken().trim());
					tokens.nextToken().trim();
					tv3.setText(tokens.nextToken().trim());*/
					/*tv4.setText(tokens.nextToken().trim());
					tv5.setText(tokens.nextToken().trim());*/
							tbl1_dyntable_For_ExecutionDetails.addView(row);

						}

					}
					else
					{
						alertDialogBuilder.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertExecNoSum));
					}
					alertDialogBuilder
							.setCancelable(false)
							.setPositiveButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {}
							});


					alertDialogBuilder.setIcon(R.drawable.info_ico);
					// create an alert dialog
					AlertDialog alert = alertDialogBuilder.create();
					alert.show();



				}
			});


		//	spinner_product=(TextView) findViewById(R.id.spinner_product);
			img_ctgry= (ImageView) findViewById(R.id.img_ctgry);
			ed_search=(EditText) findViewById(R.id.ed_search);

			ed_search.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {

					mCustomKeyboardNumWithoutDecimal.hideCustomKeyboard();
					mCustomKeyboardNum.hideCustomKeyboard();

					return false;
				}
			});

			  btn_go=(ImageView) findViewById(R.id.btn_go);
			txtVw_schemeApld=(TextView) findViewById(R.id.txtVw_schemeApld);
			txtVw_schemeApld.setText("");
			txtVw_schemeApld.setTag("0");

			//productIdOnLastEditText
			img_ctgry.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					img_ctgry.setEnabled(false);
					customAlertStoreList(categoryNames,"Select Category");
				}
			});
			btn_go.setOnClickListener(new OnClickListener() {

				   @Override
				   public void onClick(View v) {

				    if(!TextUtils.isEmpty(ed_search.getText().toString().trim()))
				    {
				    	/*progressBarCircular.setCancelable(false);
				    	progressBarCircular.setMessage("Searching ...");
				    	progressBarCircular.setProgressStyle(ProgressDialog.STYLE_SPINNER);*/

				    	/*progressBarCircular.setProgress(0);
				    	progressBarCircular.setMax(100);*/
				    	if(!ed_search.getText().toString().trim().equals(""))
						{
							searchProduct(ed_search.getText().toString().trim(),"");

						}


				    }

				    else
				    {

				    }

				   }


				  });


			TextView txt_RefreshOdrTot=(TextView) findViewById(R.id.txt_RefreshOdrTot);
			txt_RefreshOdrTot.setOnClickListener(new OnClickListener()
			  {

					@Override
					public void onClick(View v)
					{
						if(ed_LastEditextFocusd!=null)
						{
							//etOrderQty
							String tag=ed_LastEditextFocusd.getTag().toString();
							if(tag.contains("etOrderQty"))
							{
								if (hmapDistPrdctStockCount.containsKey(ProductIdOnClickedEdit))
								{
									if(hmapPrdctIdOutofStock!=null && hmapPrdctIdOutofStock.size()>0)
									{
										if(hmapPrdctIdOutofStock.containsKey(ProductIdOnClickedEdit))
										{
											int lastOrgnlQntty=Integer.parseInt(hmapPrdctIdOutofStock.get(ProductIdOnClickedEdit));
											int netStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit)+lastOrgnlQntty;
											hmapDistPrdctStockCount.put(ProductIdOnClickedEdit,netStockLeft);

										}
									}
									int originalNetQntty=0;
									if(!TextUtils.isEmpty(ed_LastEditextFocusd.getText().toString()))
									{
										originalNetQntty=Integer.parseInt(ed_LastEditextFocusd.getText().toString());
									}
									int totalStockLeft = hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

									int netStock=totalStockLeft-originalNetQntty;
									hmapDistPrdctStockCount.put(ProductIdOnClickedEdit,netStock);

									/*change*/
									/*TextView tv_VanStock= (TextView) ll_prdct_detal.findViewWithTag("etVanStock"+"_"+ProductIdOnClickedEdit);
									if(tv_VanStock!=null)
									{
										tv_VanStock.setText(""+hmapDistPrdctStockCount.get(ProductIdOnClickedEdit));
									}*/


									if(originalNetQntty!=0)
									{
										hmapPrdctIdOutofStock.put(ProductIdOnClickedEdit,ed_LastEditextFocusd.getText().toString().trim());
									}
									else
									{
										hmapPrdctIdOutofStock.remove(ProductIdOnClickedEdit);
										dbengine.deleteExistStockTable(distID,strGlobalOrderID,ProductIdOnClickedEdit);
									}
									if (originalNetQntty>totalStockLeft)
									{

										alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
									}
									else
									{

									}

								}
							}



							getOrderData(ProductIdOnClickedEdit);

							fnUpdateSchemeNameOnScehmeControl(ProductIdOnClickedEdit);
							orderBookingTotalCalc();


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

						//File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
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

			     AlertDialog.Builder alertDialogSyncError = new AlertDialog.Builder(ProductOrderFilterSearch.this);
			     alertDialogSyncError.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
			     alertDialogSyncError.setCancelable(false);  // try submitting the details from outside the door
			     alertDialogSyncError.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertCancelOrder));
			     alertDialogSyncError.setPositiveButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton),
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

			    		dbengine.deleteProductBenifitSlabApplieddeleteProductBenifitSlabApplied(storeID,strGlobalOrderID,strGlobalInvoiceNumber);
			    		dbengine.deleteAllStoreAlertValueProduct(storeID,strGlobalOrderID,strGlobalInvoiceNumber);
			    		dbengine.open();
			    		dbengine.UpdateStoreFlag(storeID.trim(), 0);
						dbengine.updateRtlrCrdtBal(storeID.trim());
						dbengine.deleteOrderId(strGlobalOrderID);
			    		dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 0,strGlobalOrderID,strGlobalInvoiceNumber);
			    		dbengine.deleteStoreTblsRecordsInCaseCancelOrderInOrderBooking(storeID.trim(),flag,strGlobalOrderID,strGlobalInvoiceNumber);
			    		dbengine.close();

			    		//dbengine.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0);

			        	Intent storeSaveIntent = new Intent(ProductOrderFilterSearch.this, LauncherActivity.class);
			     		startActivity(storeSaveIntent);
			     		finish();
			        }

			     });
			     alertDialogSyncError.setNeutralButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogCancelButton),
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


			final Button btn_orderReview=(Button) findViewById(R.id.btn_orderReview);
			btn_orderReview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {




				    //TODO Auto-generated method stub
					if(validateInviceQty())
					{
						fnCreditAndStockCal(0);
					}





				}
			});
			final Button btn_Save=(Button) findViewById(R.id.btn_save);
		    btn_Save.setTag("0_0");
		    btn_Save.setOnClickListener(new OnClickListener()
		    {

			   @Override
			   public void onClick(View v)
			   {
			    // TODO Auto-generated method stub


				   fnCreditAndStockCal(1);







			   }
			  });

			  final Button btn_SaveExit=(Button) findViewById(R.id.btn_saveExit);
		      btn_SaveExit.setTag("0_0");
			  btn_SaveExit.setOnClickListener(new OnClickListener()
			  {

			   @Override
			   public void onClick(View v) {
			    // TODO Auto-generated method stub

				   fnCreditAndStockCal(2);



			   }
			  });
			  final Button btn_Submit=(Button) findViewById(R.id.btn_sbmt);
			  btn_Submit.setTag("0_0");
			  btn_Submit.setOnClickListener(new OnClickListener() {

			   @Override
			   public void onClick(View v)
			   {
			    // TODO Auto-generated method stub

				   fnCreditAndStockCal(3);



			   }
			  });

			  img_return=(ImageView) findViewById(R.id.img_return);
			  img_return.setOnClickListener(new OnClickListener() {

			   @Override
			   public void onClick(View v) {


				   fnCreditAndStockCal(4);



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

			  txtVw_schemeApld.setOnClickListener(new OnClickListener()
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
							           /*for(int i=0;i<arrSchId.length;i++)
							           {

							           }*/
							          }
					        	 }
					          }
			         }
			     });
			  //ll_scheme_detail=(LinearLayout) findViewById(R.id.ll_scheme_detail);

			  btn_bck.setOnClickListener(new OnClickListener() {

			   @Override
			   public void onClick(View v) {

				   fnCreditAndStockCal(5);


			   }
			  });
			 // spinner_category.setOnItemSelectedListener(this);





			  getDataFromIntent();






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
			if(passedvals.hasExtra("hmapPrdctIdOutofStock"))
			{
				hmapDistPrdctStockCount=(HashMap<String, Integer>) passedvals.getSerializableExtra("hmapDistPrdctStockCount");
				hmapPrdctIdOutofStock=(HashMap<String, String>) passedvals.getSerializableExtra("hmapPrdctIdOutofStock");
			}

			 }

		private void getProductData() {
			// CategoryID,ProductID,ProductShortName,ProductRLP,Date/Qty)
			arrLstHmapPrdct=dbengine.fetch_catgry_prdctsData(storeID,StoreCurrentStoreType);
			hmapSchemeIDandDescr=dbengine.fnSchemeIDandDescr();

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

		private void getSchemeSlabDetails()
		{
            PriceApplyDiscountLevelType=dbengine.fnGettblPriceApplyDiscountLevelType(storeID);
            if(PriceApplyDiscountLevelType==0) {
                arrayListSchemeSlabDteail = dbengine.fnctnSchemeSlabIdSchmVal();

                hmapSchemeIdStoreID = dbengine.fnctnSchemeStoreID(storeID);
                // hmapSchemeStoreID=dbengine.fnctnSchemeStoreID(storeID);
                if (arrayListSchemeSlabDteail != null && arrayListSchemeSlabDteail.size() > 0) {
                    hmapSchmeSlabIdSchemeId = arrayListSchemeSlabDteail.get(0);
                    hmapSchmeSlabIdSlabDes = arrayListSchemeSlabDteail.get(1);
                    hmapSchmeSlabIdBenifitDes = arrayListSchemeSlabDteail.get(2);
                }
            }
            else
            {
                cutoffvalue=dbengine.fnGettblPriceApplycutoffvalue(storeID);
            }
        }

		private void getCategoryDetail()
		{

			hmapctgry_details=dbengine.fetch_Category_List();

			int index=0;
			if(hmapctgry_details!=null)
	    	{
				categoryNames=new ArrayList<String>();
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapctgry_details);
	            Set set2 = map.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext()) {
	            	 Entry me2 = (Entry)iterator.next();
	            	 categoryNames.add(me2.getKey().toString());
					if(index==0)
					{
						defaultCatName_Id=me2.getKey().toString()+"^"+me2.getValue().toString();
					}
	                 index=index+1;
	            }
	    	}


		}

		public void createDynamicProduct(String productIdDynamic,int CheckIfStoreExistInStoreProdcutPurchaseDetails,String ProductValuesToFill)
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

	            viewProduct=inflater.inflate(R.layout.list_item_card,null);



	            //ProdID,Stock,OrderQty,OrderVal,FreeQty,DisVal,SampleQuantity,ProductPrice





	           /*if(position%2==0)

	           {

	            viewProduct.setBackgroundResource(R.drawable.card_background);

	           }*/



	           // hmapCtgryPrdctDetail.get(prductId[position]) = categoryId

	           viewProduct.setTag(hmapCtgryPrdctDetail.get(productIdDynamic)+"_"+productIdDynamic);

	        //   hide_View[position]=viewProduct;

	           hmapProductViewTag.put(viewProduct.getTag().toString(), "even");
	         /* ImageView imgDel=(ImageView) viewProduct.findViewById(R.id.imgDel);
	          imgDel.setTag(hmapCtgryPrdctDetail.get(productIdDynamic)+"_"+productIdDynamic);
*/
	           LinearLayout ll_sample = (LinearLayout) viewProduct.findViewById(R.id.ll_sample);

	          //tvProdctName
//et_LastStock
	           TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);
			tv_product_name.setTag("tvProductName"+"_"+productIdDynamic);
			EditText et_VanStock=null;//=(EditText) viewProduct.findViewById(R.id.et_VanStock);
			et_VanStock.setTag("etVanStock"+"_"+productIdDynamic);

			if(hmapDistPrdctStockCount!=null && hmapDistPrdctStockCount.size()>0)
			{
				if(hmapDistPrdctStockCount.containsKey(productIdDynamic))
				{
					et_VanStock.setText(""+hmapDistPrdctStockCountText.get(productIdDynamic));
					tv_product_name.setText(hmapPrdctIdPrdctName.get(productIdDynamic));//+"( Avl : "+hmapDistPrdctStockCount.get(productIdDynamic)+")"
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

						txtVwRate.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Rate));

					}
					else if((""+rateValBeforeTax).equals("-99.0") || (""+rateValBeforeTax).equals("-99.00") || (""+rateValBeforeTax).equals("-99"))
					{
						txtVwRate.setText("");

						txtVwRate.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Rate));
					}
					else {
						txtVwRate.setText("" + rateValBeforeTax);
					}

			if(hmapProductflgPriceAva.get(productIdDynamic).equals("1")) {
				txtVwRate.setBackgroundResource(R.drawable.edit_text_bg);

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

	           et_Stock.setTag("etStock"+"_"+productIdDynamic);

	          // et_Stock.setOnFocusChangeListener(this);



	           final EditText et_SampleQTY=(EditText) viewProduct.findViewById(R.id.et_SampleQTY);

	           et_SampleQTY.setTag("etSampleQty"+"_"+productIdDynamic);

	         //  et_SampleQTY.setOnFocusChangeListener(this);



	           final EditText et_OrderQty=(EditText) viewProduct.findViewById(R.id.et_OrderQty);

	           et_OrderQty.setTag("etOrderQty"+"_"+productIdDynamic);



	          // et_OrderQty.setOnFocusChangeListener(this);
			if(hmapProductflgPriceAva.get(productIdDynamic).equals("1"))
			{
			//	txtVwRate.setEnabled(true);
				txtVwRate.setEnabled(false);
			}




	    /*       imgDel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
				final String productIdToDel=	v.getTag().toString().split(Pattern.quote("_"))[1];
				final String vtag=v.getTag().toString();
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductOrderFilterSearch.this);

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
			});*/


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

	          // et_ProductMRP.setOnFocusChangeListener(this);


			if(hmapProductMRP.get(productIdDynamic).equals("-99.0") || hmapProductMRP.get(productIdDynamic).equals("-99.00") || hmapProductMRP.get(productIdDynamic).equals("-99"))
			{
				et_ProductMRP.setText("");
			}
			else if((""+rateValBeforeTax).equals("-99.0") || (""+rateValBeforeTax).equals("-99.00") || (""+rateValBeforeTax).equals("-99"))
			{
				et_ProductMRP.setText("");
			}
			else {
				//et_ProductMRP.setText("" + rateValBeforeTax);
				Double rateOFProductMRP=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(hmapProductMRP.get(productIdDynamic))));
				et_ProductMRP.setText(""+rateOFProductMRP);
			}

	          // et_ProductMRP.setText(hmapProductMRP.get(productIdDynamic));









	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)

	           {



	                et_Stock.setText(ProductValuesToFill.split(Pattern.quote("^"))[1]);

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
					   txtVwRate.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Rate));

				   }
				   else {
					   Double rateOFProduct=Double.parseDouble(new DecimalFormat("##.##").format(Double.valueOf(ProductValuesToFill.split(Pattern.quote("^"))[7])));
					   txtVwRate.setText(""+rateOFProduct);
				   }

	                if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[6])==0)

	                {

	                        et_SampleQTY.setText("");

	                        et_SampleQTY.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.SmplQty));

	                }



	                if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[2])==0)

	                {

	                        et_OrderQty.setText("");

	                        et_OrderQty.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.OQty));

	                }

	                if(Integer.parseInt(ProductValuesToFill.split(Pattern.quote("^"))[1])==0)

	                {

	                        et_Stock.setText("");

	                        et_Stock.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.StockQty));

	                }



	                hmapProductIdStock.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[1]);

	                hmapPrdctOdrQty.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[2]);

	                hmapProductIdOrdrVal.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[3]);

	                hmapPrdctFreeQty.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[4]);

	                hmapPrdctIdPrdctDscnt.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[5]);

	                hmapPrdctSmpl.put(productIdDynamic, ProductValuesToFill.split(Pattern.quote("^"))[6]);




	           }





	       /*    et_OrderQty.addTextChangedListener(new TextWatcher() {

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
															if(hmapProductStandardRate.get(""+getPIDTag).equals("-99.0") || hmapProductMRP.get(""+getPIDTag).equals("-99.00") || hmapProductMRP.get(""+getPIDTag).equals("-99"))
															{
																//((EditText)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).setSelected(true);
																((EditText)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).requestFocus();
															}

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


*/




	          ll_prdct_detal.addView(viewProduct);


			et_OrderQty.addTextChangedListener(new TextWatcher()
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
									ediTextRate.setError(ProductOrderFilterSearch.this.getResources().getString(R.string.PleasefillproperRatefirst));
									et_OrderQty.setText("");
								}
								else if(Double.parseDouble(ediTextRate.getText().toString())<0)
								{
									et_OrderQty.clearFocus();
									ediTextRate.requestFocus();
									//ediTextRate.setCursorVisible(true);
									ediTextRate.setError(ProductOrderFilterSearch.this.getResources().getString(R.string.PleasefillproperRatefirst));
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

						et_OrderQty.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.OQty));

						((TextView)ll_prdct_detal.findViewWithTag("tvOrderVal_"+getPIDTag)).setText("0.00");

						hmapPrdctOdrQty.put(""+getPIDTag, "0");



					}



				}

			});




			/*txtVwRate.addTextChangedListener(new TextWatcher() {
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

						txtVwRate.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Rate));



						hmapProductStandardRate.put(""+getPIDTag, "-99");



					}

				}
			});
*/
			//txtVwRate.setOnFocusChangeListener(this);
			et_OrderQty.setOnFocusChangeListener(this);

			// txtVwRate.setOnClickListener(this);

			// tv_product_name.setOnClickListener(this);

			//et_Stock.setOnClickListener(this);

			et_OrderQty.setOnClickListener(this);

			tv_Orderval.setOnClickListener(this);

			// tv_FreeQty.setOnClickListener(this);

			//tv_DisVal.setOnClickListener(this);

//			et_SampleQTY.setOnClickListener(this);



			/*et_SampleQTY.addTextChangedListener(new TextWatcher() {



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


*/


		}

		public void createProductPrepopulateDetailWhileSearch(int CheckIfStoreExistInStoreProdcutPurchaseDetails) {



	          hide_View=new View[hmapCtgryPrdctDetail.size()];
	          prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);
	          if(prductId.length>0)
	          {
	          // String[] arrStorePurcaseProducts=null;//=dbengine.fnGetProductPurchaseList(StoreID);
	          /* if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)
	           {*/
	          //  arrStorePurcaseProducts=dbengine.fnGetProductPurchaseList(storeID,strGlobalOrderID);


	          //  LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	            int position=0;
	            //hmapFilterProductList
		           for(Entry<String, String> entry:hmapFilterProductList.entrySet())
		           {
			            countParentView=position;
			         //   viewProduct=inflater.inflate(R.layout.list_item_card,null);
			            if(position%2==0)
			           {
			            //viewProduct.setBackgroundResource(R.drawable.card_background);
			           }

			            if(!hmapPrdctIdPrdctNameVisible.containsKey(entry.getKey()))
						   {
			            	String strValuesOfProduct=entry.getKey()+"^"+hmapProductIdStock.get(entry.getKey())+"^"+hmapPrdctOdrQty.get(entry.getKey())+"^"+hmapProductIdOrdrVal.get(entry.getKey())+"^"+hmapPrdctFreeQty.get(entry.getKey())+"^"+hmapPrdctIdPrdctDscnt.get(entry.getKey())+"^"+hmapPrdctSmpl.get(entry.getKey())+"^"+hmapProductStandardRate.get(entry.getKey());//hmapPrdctVolRatTax.get(entry.getKey()).split(Pattern.quote("^"))[1];

							   //createDynamicProduct(entry.getKey(), 1, arrStorePurcaseProducts[position].toString());
			            	createDynamicProduct(entry.getKey(), 1, strValuesOfProduct);
						       hmapPrdctIdPrdctNameVisible.put(entry.getKey(), hmapPrdctIdPrdctName.get(entry.getKey()));
						   }
			            position++;
		           }

		           if(hmapPrdctIdPrdctNameVisible.size()>0)
		           {
		        	   orderBookingTotalCalc();
		        	   createProductRowColor();

		           }
	           //}

	         }



		}

		public void createProductPrepopulateDetail(int CheckIfStoreExistInStoreProdcutPurchaseDetails) {


	          hide_View=new View[hmapCtgryPrdctDetail.size()];
	          prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);
	          if(prductId.length>0)
	          {
	           String[] arrStorePurcaseProducts=null;//=dbengine.fnGetProductPurchaseList(StoreID);
	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)
	           {
	            arrStorePurcaseProducts=null;//dbengine.fnGetProductPurchaseList(storeID,strGlobalOrderID,strGlobalInvoiceNumber);


	            LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
						//ll_prdct_detal.getChildAt(position).setBackgroundColor(Color.parseColor("#F2F2F2"));
						ll_prdct_detal.getChildAt(position).setBackgroundResource(R.drawable.card_background_white);

				    }
				}
		}

		public void createProductDetail(int CheckIfStoreExistInStoreProdcutPurchaseDetails) {

			/*
	          System.out.println("Abhinav Nitish Ankit New :"+CheckIfStoreExistInStoreProdcutPurchaseDetails);

	          hide_View=new View[hmapCtgryPrdctDetail.size()];
	          prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);
	          if(prductId.length>0)
	          {
	           String[] arrStorePurcaseProducts=null;//=dbengine.fnGetProductPurchaseList(StoreID);
	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)
	           {
	            arrStorePurcaseProducts=dbengine.fnGetProductPurchaseList(storeID);
	            System.out.println("Abhinav Nitish Ankit New Val :"+arrStorePurcaseProducts.length);
	           }
	           LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	           System.out.println("Abhinav Nitish Ankit New Val :"+hmapCtgryPrdctDetail.size());
	           for(int position=0;position<hmapCtgryPrdctDetail.size();position++)
	           {
	            countParentView=position;
	            viewProduct=inflater.inflate(R.layout.list_item_card,null);
	            if(position%2==0)
	           {
	            //viewProduct.setBackgroundResource(R.drawable.card_background);
	           }
	           viewProduct.setTag(hmapCtgryPrdctDetail.get(prductId[position])+"_"+prductId[position]);
	           hide_View[position]=viewProduct;
	           hmapProductViewTag.put(viewProduct.getTag().toString(), "even");
	           LinearLayout ll_sample = (LinearLayout) viewProduct.findViewById(R.id.ll_sample);
	           TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);
	           tv_product_name.setTag("tvProductName"+"_"+prductId[position]);
	           tv_product_name.setText(hmapPrdctIdPrdctName.get(prductId[position]));
	           final ImageView btnExcptn=(ImageView) viewProduct.findViewById(R.id.btnExcptn);
	           btnExcptn.setTag("btnException"+"_"+prductId[position]);
	           final EditText txtVwRate=(EditText) viewProduct.findViewById(R.id.txtVwRate);
	           txtVwRate.setTag("tvRate"+"_"+prductId[position]);
	           String value=hmapPrdctVolRatTax.get(prductId[position]).toString();
	           StringTokenizer tokens=new StringTokenizer(value,"^");
	           String volume = tokens.nextElement().toString();
	              String rate = tokens.nextElement().toString();
	              String taxAmount = tokens.nextElement().toString();
	              txtVwRate.setText(rate);
	              final EditText et_Stock=(EditText) viewProduct.findViewById(R.id.et_Stock);
	              et_Stock.setTag("etStock"+"_"+prductId[position]);
	           et_Stock.setOnFocusChangeListener(this);
	           final EditText et_SampleQTY=(EditText) viewProduct.findViewById(R.id.et_SampleQTY);
	           et_SampleQTY.setTag("etSampleQty"+"_"+prductId[position]);
	           et_SampleQTY.setOnFocusChangeListener(this);
	           final EditText et_OrderQty=(EditText) viewProduct.findViewById(R.id.et_OrderQty);
	           et_OrderQty.setTag("etOrderQty"+"_"+prductId[position]);
	           et_OrderQty.setOnFocusChangeListener(this);
	           et_OrderQty.addTextChangedListener(new TextWatcher() {
	        	   @Override
	        	   public void onTextChanged(CharSequence s, int start, int before, int count) {

	                        }
	                        @Override
	                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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





	          final TextView tv_Orderval=(TextView) viewProduct.findViewById(R.id.tv_Orderval);

	           tv_Orderval.setTag("tvOrderVal"+"_"+prductId[position]);







	           EditText tv_FreeQty=(EditText) viewProduct.findViewById(R.id.tv_FreeQty);

	           tv_FreeQty.setTag("tvFreeQty"+"_"+prductId[position]);





	           TextView tv_DisVal=(TextView) viewProduct.findViewById(R.id.tv_DisVal);

	           tv_DisVal.setTag("tvDiscountVal"+"_"+prductId[position]);

	           tv_DisVal.setText(hmapPrdctIdPrdctDscnt.get(prductId[position]));









	           EditText tvLODqty=(EditText) viewProduct.findViewById(R.id.tvLODqty);

	           tvLODqty.setTag("tvLODQuantity"+"_"+prductId[position]);

	           tvLODqty.setText(hmapProductLODQty.get(prductId[position]));



	           final EditText et_ProductMRP=(EditText) viewProduct.findViewById(R.id.et_ProductMRP);

	           et_ProductMRP.setTag("etProductMRP"+"_"+prductId[position]);

	           et_ProductMRP.setOnFocusChangeListener(this);

	           et_ProductMRP.setText(hmapProductMRP.get(prductId[position]));









	           if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1)

	           {



	                et_Stock.setText(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[1]);

	                et_OrderQty.setText(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[2]);

	                tv_Orderval.setText(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[3]);

	                tv_FreeQty.setText(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[4]);

	                tv_DisVal.setText(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[5]);

	                et_SampleQTY.setText(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[6]);



	                if(Integer.parseInt(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[6])==0)

	                {

	                        et_SampleQTY.setText("");

	                        et_SampleQTY.setHint("Smpl.Qty");

	                }



	                if(Integer.parseInt(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[2])==0)

	                {

	                        et_OrderQty.setText("");

	                        et_OrderQty.setHint("O.Qty");

	                }

	                if(Integer.parseInt(arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[1])==0)

	                {

	                        et_Stock.setText("");

	                        et_Stock.setHint("Stock.Qty");

	                }



	                hmapProductIdStock.put(prductId[position], arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[1]);

	                hmapPrdctOdrQty.put(prductId[position], arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[2]);

	                hmapProductIdOrdrVal.put(prductId[position], arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[3]);

	                hmapPrdctFreeQty.put(prductId[position], arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[4]);

	                hmapPrdctIdPrdctDscnt.put(prductId[position], arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[5]);

	                hmapPrdctSmpl.put(prductId[position], arrStorePurcaseProducts[position].toString().split(Pattern.quote("^"))[6]);





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

	                                                        String strPrate=((TextView)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).getText().toString();

	                                                        if(strPrate.equals(""))

	                                                        {

	                                                                        strPrate="0.00";

	                                                        }

	                                                        int boxQty=0;

	                                                        if(et_OrderQty.getText().toString().equals("") || et_OrderQty.getText().equals("0") || et_OrderQty.getText().equals("Q.Qty") || et_OrderQty.getText().equals("Q . Qty"))

	                                                        {

	                                                                        boxQty=0;

	                                                        }

	                                                        else

	                                                        {

	                                                                        boxQty =Integer.parseInt(et_OrderQty.getText().toString());

	                                                        }



	                                                        Double calculatedOrderValue=Double.parseDouble(""+((TextView)ll_prdct_detal.findViewWithTag("tvRate_"+getPIDTag)).getText()) * Double.parseDouble(""+boxQty);

	                                                        //Get the Percentage of Now Over Value like Double calculatedDiscount =Rate*Order*BenifitAssignedValue/100

	                                                        //Order Value TextBox=Order Value TextBox+calculatedDiscount

	                                                        calculatedOrderValue=Double.parseDouble(new DecimalFormat("##.##").format(calculatedOrderValue));

	                                                        ((TextView)ll_prdct_detal.findViewWithTag("tvOrderVal_"+getPIDTag)).setText(""+calculatedOrderValue);

	                                                        hmapProductIdOrdrVal.put(""+getPIDTag, ""+calculatedOrderValue);

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

	           }

	          }



	        */}

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
			if(slctdProduct.toLowerCase().equals("All".toLowerCase()))
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
				 if(condtnOddEven.equals("even"))
				 {
					// viewOldBackgound=ll_prdct_detal.findViewWithTag(CtaegoryIddOfClickedView+"_"+ProductIdOnClickedEdit+"_"+"even");
					// viewOldBackgound.setBackgroundResource(R.drawable.card_background_even);
				 }
				 else
				 {
					 //viewOldBackgound=ll_prdct_detal.findViewWithTag(CtaegoryIddOfClickedView+"_"+ProductIdOnClickedEdit+"_"+"odd");

					 //viewOldBackgound.setBackgroundResource(R.drawable.card_background_odd);
				 }

				if(v.getId()==R.id.et_Stock)
				{
					et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Stock));

				}//txtVwRate
				if(v.getId()==R.id.txtVwRate)
				{
					et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Rate));
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
					et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.SmplQty));

				}
				if(v.getId()==R.id.et_OrderQty)
				{
					et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.OQty));
				}
				if(!viewCurrentBoxValue.equals(et_ValueOnFocuslost.getText().toString().trim()))
				{
					if(v.getId()==R.id.et_Stock)
					{
						et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Stock));

					}

					if(v.getId()==R.id.txtVwRate)
					{
						et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.Rate));

						EditText temped=(EditText) ll_prdct_detal.findViewWithTag("etOrderQty_"+et_ValueOnFocuslost.getTag().toString().split(Pattern.quote("_"))[1]);
						EditText temprt=(EditText) ll_prdct_detal.findViewWithTag("tvRate_"+et_ValueOnFocuslost.getTag().toString().split(Pattern.quote("_"))[1]);


					}
					if(v.getId()==R.id.et_SampleQTY)
					{
						et_ValueOnFocuslost.setHint(ProductOrderFilterSearch.this.getResources().getString(R.string.SmplQty));
						/*hmapPrdctSmpl.remove(ProductIdOnClickedControl);
						hmapPrdctSmpl.put(ProductIdOnClickedControl, et_ValueOnFocuslost.getText().toString().trim());*/
					}
					if(v.getId()==R.id.et_OrderQty)
					{

						if (hmapDistPrdctStockCount.containsKey(ProductIdOnClickedEdit))
						{
							if(hmapPrdctIdOutofStock!=null && hmapPrdctIdOutofStock.size()>0)
							{
								if(hmapPrdctIdOutofStock.containsKey(ProductIdOnClickedEdit))
								{
									int lastOrgnlQntty=Integer.parseInt(hmapPrdctIdOutofStock.get(ProductIdOnClickedEdit));
									int netStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit)+lastOrgnlQntty;
									hmapDistPrdctStockCount.put(ProductIdOnClickedEdit,netStockLeft);

								}
							}
							int originalNetQntty=0;
							if(!TextUtils.isEmpty(ed_LastEditextFocusd.getText().toString()))
							{
								originalNetQntty=Integer.parseInt(et_ValueOnFocuslost.getText().toString());
							}
							int totalStockLeft = hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

							int netStock=totalStockLeft-originalNetQntty;
							hmapDistPrdctStockCount.put(ProductIdOnClickedEdit,netStock);

									/*change*/
						/*	TextView tv_VanStock= (TextView) ll_prdct_detal.findViewWithTag("etVanStock"+"_"+ProductIdOnClickedEdit);
							if(tv_VanStock!=null)
							{
								tv_VanStock.setText(""+hmapDistPrdctStockCount.get(ProductIdOnClickedEdit));
							}*/


							if(originalNetQntty!=0)
							{
								hmapPrdctIdOutofStock.put(ProductIdOnClickedEdit,et_ValueOnFocuslost.getText().toString().trim());
							}
							else
							{
								hmapPrdctIdOutofStock.remove(ProductIdOnClickedEdit);
								dbengine.deleteExistStockTable(distID,strGlobalOrderID,ProductIdOnClickedEdit);
							}
							if (originalNetQntty>totalStockLeft)
							{

								alertForOrderExceedStock(ProductIdOnClickedEdit,et_ValueOnFocuslost,et_ValueOnFocuslost,-1);
							}
							else
							{

							}

						}
						if(Integer.parseInt(hmapPrdctOdrQty.get(ProductIdOnClickedEdit))>0)
					{
						if((!hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("") && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99")) && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99.00") && !hmapProductStandardRate.get(ProductIdOnClickedEdit).equals("-99.0"))
						{
							if(PriceApplyDiscountLevelType==0)
							{
								getOrderData(ProductIdOnClickedEdit);
							}
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

	private void getOrderData(String ProductIdOnClickedControl123)
	 {


		isbtnExceptionVisible=0;

		if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl123))
		{
			/*String[] OldProds=dbengine.fnGetProductsAgainstBenifitTable(storeID, ProductIdOnClickedControl);
		      for(int i=0;i<OldProds.length;i++)
		      {
		       hmapPrdctIdPrdctDscnt.put(OldProds[i], "0.00");
		       ((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+OldProds[i])).setText("0.00");
		      }
		      */
			//fnUpdateSchemeNameOnScehmeControl(ProductIdOnClickedControl123);
			String SchIdsCompleteSchemeIdListOnProductID=hmapProductRelatedSchemesList.get(ProductIdOnClickedControl123);
			fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(SchIdsCompleteSchemeIdListOnProductID,ProductIdOnClickedControl123);

		}

		else if(dbengine.isFreeProductIdExist(Integer.parseInt(ProductIdOnClickedControl123)))
		{
			String productIdAgaingtFreeProductId=dbengine.getFreeProductIdAgainstFreeProductId(Integer.parseInt(ProductIdOnClickedControl123));
			String SchIdsCompleteSchemeIdListOnProductID=hmapProductRelatedSchemesList.get(productIdAgaingtFreeProductId);
			fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(SchIdsCompleteSchemeIdListOnProductID,productIdAgaingtFreeProductId);
		}


	}

	/*public void fnAssignSchemeIDsAppliedOverProductAfterValueChange(String ProductIdOnClicked)
	{
		HashMap<String, ArrayList<String>> noAlrtHshMaptoSaveData=new HashMap<String, ArrayList<String>>();
		ArrayList<String> noAlrtStringSchemeIdWthAllVal=new ArrayList<String>();
		ArrayList<String> stringSchemeIdWthAllVal=new ArrayList<String>();
		ArrayList<HashMap<String, String>> listArrayHashmapProduct=new ArrayList<HashMap<String, String>>();
		ArrayList<String[]> listArrayFreePrdctQty=new ArrayList<String[]>();

		if(arredtboc_OderQuantityFinalSchemesToApply.size()>0)
		{
			for(String strListMpdWdPrdct:arredtboc_OderQuantityFinalSchemesToApply)
			{
				//schId+"^"+schSlabId+"^"+schSlbBuckId+"^"+schSlabSubBucketValue+"^"+schSubBucketValType+"^"
				//+schSlabSubBucketType+"^"+ProductIdOnClicked +"^"+valForVolumetQTYToMultiply+"^"
				//+schSlbSubRowID+"^"+SchTypeId+"^"+totalProductQnty+"^"+totalInvoice+"^"
				//+totalProductLine+"^"+totalProductVal+totalProductVol;
				int schId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[0]);
				int schSlabId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[1]);
				int schSlbBuckId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[2]);
				Double schSlabSubBucketValue=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[3]);
				int schSubBucketValType=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[4]);
				int schSlabSubBucketType=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[5]);
				int Pid=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[6]);
				int toMultiply=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[7]);
				int schSlbSubRowID=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[8]);
				int SchTypeId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[9]);
				int totalProductQty=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[10]);
				double totalInvoice=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[11]);
				int totalProductLine=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[12]);
				double totalProductVal=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[13]);
				double totalProductVol=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[14]);

				if(hmapSchemeIdStoreID.containsKey(""+schId))
				{
					String[] arrProductIDBenifitsListOnPurchase=dbengine.fectProductIDBenifitsListOnPurchase(schId,schSlabId,schSlbBuckId);
					// RowID AS BenifitRowID,BenSubBucketType,BenDiscApplied,CouponCode,BenSubBucketValue,Per, UOM,ProRata
					if(arrProductIDBenifitsListOnPurchase!=null && arrProductIDBenifitsListOnPurchase.length>0)
					{
						for(String strProductIDBenifitsListOnPurchase:arrProductIDBenifitsListOnPurchase )
						{
							int BenifitRowID=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[0]);
							int BenSubBucketType=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[1]);
							int BenDiscApplied=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[2]);

							// MinValueQty of free product
							//String CouponCode=strProductIDBenifitsListOnPurchase.split("^")[0];
							Double BenSubBucketValue=Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[4]);
							Double Per=Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[5]);
							Double UOM=Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[6]);
							int chkflgProDataCalculation=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[7]);

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
							//10. PerVolume Discount


							if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3 ||BenSubBucketType==5 ||BenSubBucketType==6 || BenSubBucketType==7) //1. Free Other Product 2. Discount in Percentage with other product 3. Discount in Amount with other product
							{
								HashMap<String, String> arrProductIDMappedInSchSlbSubBukBenifits=new HashMap<String, String>();
								//productFullFilledSlabGlobal;
								//BenValue
								int isHaveMoreBenifits=0;

								String[] strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,toMultiply,BenSubBucketValue,BenSubBucketType);
								if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3)
								{
									arrProductIDMappedInSchSlbSubBukBenifits=dbengine.fectProductIDMappedInSchSlbSubBukBenifits(BenifitRowID);
								}
								else
								{

									for(String productIdToFillSlab:productFullFilledSlabGlobal)
									{
										arrProductIDMappedInSchSlbSubBukBenifits.put(hmapPrdctIdPrdctName.get(productIdToFillSlab), productIdToFillSlab);
									}

								}

								defaultValForAlert=strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];
								defaultValForAlert=strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];
								HashMap<String, String> hmapFreeProdID=new HashMap<String, String>();
								HashMap<String, String> hmapFreeProdIDAlrt=new HashMap<String, String>();
								if(arrProductIDMappedInSchSlbSubBukBenifits.size()>0)
								{

									String[] arrBenifitAssignedVal=new String[arrProductIDMappedInSchSlbSubBukBenifits.size()];
									int countAssignVal=0;
								for(Entry<String, String> allPrdctNamePrdctId:arrProductIDMappedInSchSlbSubBukBenifits.entrySet())
									{
									Double accAsignVal=0.0;
									String productIdForFree=allPrdctNamePrdctId.getValue();

									String maxBenifiAssignedValToCalc="";
									String maxBenifiAssignedVal=dbengine.getValOfSchemeAlrtSelected(storeID,""+schId,""+schSlabId,strGlobalOrderID);
									if(Double.parseDouble(maxBenifiAssignedVal)>0)
									{

										maxBenifiAssignedValToCalc=maxBenifiAssignedVal;

									}
									else
									{
										dbengine.deleteAlertValueProduct(storeID,""+schId,strGlobalOrderID);
									}
									boolean defaultSelected=false;
									if(!maxBenifiAssignedValToCalc.equals(""))
									{

										if(Double.parseDouble(maxBenifiAssignedValToCalc)>0)
										{
											accAsignVal=Double.parseDouble(maxBenifiAssignedValToCalc);

										 String[] strBeniftRowIdTest=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,1,BenSubBucketValue,BenSubBucketType);
									for(int i=0;i<strBeniftRowIdTest.length;i++)
									{

										Double benifit=Double.parseDouble(strBeniftRowIdTest[i]);
										if(Double.parseDouble(maxBenifiAssignedValToCalc)==benifit)
										{
											if(countAssignVal==(arrProductIDMappedInSchSlbSubBukBenifits.size()-1))
											{
												accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree,true );

											}
											else
											{
												accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree,false );
											}
										//	accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree);

											defaultSelected=true;
											break;
										}
									}
								}

									}
									if(!defaultSelected)
									{
										if(countAssignVal==(arrProductIDMappedInSchSlbSubBukBenifits.size()-1))
										{
											accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per,productIdForFree,true );

										}
										else
										{
											accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per,productIdForFree,false );
										}

									}

									String productNameValue=allPrdctNamePrdctId.getKey();
									hmapFreeProdIDAlrt.put(productNameValue,productIdForFree);
								hmapFreeProdID.put(productNameValue,productIdForFree);

									if(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail.length>1)
									{
										String subValues=String.valueOf(schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
												BenSubBucketType+"~"+0+"~"+BenSubBucketValue+"~"+0+"~"+0+"~"+
												0+"~"+0+"~"+0+"~"+0.0+"~"+0.0+"~"+schSlbSubRowID+"~"+SchTypeId+"~"+chkflgProDataCalculation);
										stringSchemeIdWthAllVal.add(subValues);
										//listArrayHashmapProduct.add(hmapFreeProdID);
										isHaveMoreBenifits=1;
										arrBenifitAssignedVal[countAssignVal]=(String.valueOf(accAsignVal));
							//			listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);



									}
									else
									{
										isHaveMoreBenifits=0;


										ArrayList<String> noAlrtStringSchemeIdWthAllValTemp=new ArrayList<String>();

										String noAlrtsubValues=String.valueOf(accAsignVal+"~"+schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
												BenSubBucketType+"~"+0+"~"+BenSubBucketValue+"~"+0+"~"+0+"~"+
												0+"~"+0+"~"+0+"~"+0.0+"~"+0.0+"~"+schSlbSubRowID+"~"+SchTypeId);

										noAlrtStringSchemeIdWthAllValTemp.add(noAlrtsubValues);


										//String[] arrayProductId=changeHmapToArrayValue(arrProductIDMappedInSchSlbSubBukBenifits);
										noAlrtHshMaptoSaveData.put(productIdForFree, noAlrtStringSchemeIdWthAllValTemp);

									}
									countAssignVal++;
									}// for loop arrProductIDMappedInSchSlbSubBukBenifits.entrySet() ends here

								if(isHaveMoreBenifits==1)
								{
								if(disValClkdOpenAlert)
								{


									listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
									listArrayHashmapProduct.add(hmapFreeProdIDAlrt);
								}
								else
								{

									listArrayFreePrdctQty.add(arrBenifitAssignedVal);
									listArrayHashmapProduct.add(hmapFreeProdID);
								}
									}
								}//if(arrProductIDMappedInSchSlbSubBukBenifits.size()>0)
								alrtStopResult=false;
							}//if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3) ends here
							if(BenSubBucketType==10) //10. Free pr Unit Volume
							{

								HashMap<String, String> hmapMultiplePuschasedProductVolumeAndValue=new HashMap<String, String>();
								HashMap<String, String> arrProductIDMappedInSchSlbSubBukBenifits=new HashMap<String, String>();
								LinkedHashMap<String, String> hmapFreeProdID=new LinkedHashMap<String, String>();
								dbengine.open();

								*//*String productNameValue=hmapPrdctIdPrdctName.get(ProductIdOnClicked);
								arrProductIDMappedInSchSlbSubBukBenifits.put(productNameValue,ProductIdOnClicked);*//*




								  String[] strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,toMultiply,BenSubBucketValue,BenSubBucketType);
									 defaultValForAlert=strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];




								dbengine.close();
								Double AssigendValue=Double.parseDouble(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0]);
								double totVolumeofProducts=0.00;
								double totlCombinedPriceOfProdcuts=0.00;
								if(productFullFilledSlabGlobal.size()>0)
								{
									int countPerVol=0;
									String maxBenifiAssignedVal=null;
									String maxBenifiAssignedValToCalc="";
									int isHaveMoreBenifits=0;
									for(String prdctIdMpdWithScheme:productFullFilledSlabGlobal)
									{


										if(Integer.parseInt(hmapPrdctOdrQty.get(prdctIdMpdWithScheme))>0)
										{


											maxBenifiAssignedVal=dbengine.getValOfSchemeAlrtSelected(storeID,""+schId,""+schSlabId,strGlobalOrderID);
											if(Double.parseDouble(maxBenifiAssignedVal)>0)
											{

												maxBenifiAssignedValToCalc=maxBenifiAssignedVal;

											}
											else
											{
												dbengine.deleteAlertValueProduct(storeID,""+schId,strGlobalOrderID);
											}
											double prdPrice=Double.parseDouble(hmapPrdctVolRatTax.get(prdctIdMpdWithScheme).split(Pattern.quote("^"))[1])*Double.parseDouble(hmapPrdctOdrQty.get(prdctIdMpdWithScheme));
											double prdVol=Double.parseDouble(hmapPrdctVolRatTax.get(prdctIdMpdWithScheme).split(Pattern.quote("^"))[0])*Double.parseDouble(hmapPrdctOdrQty.get(prdctIdMpdWithScheme));
											if(prdVol>=Per)
											{
												countPerVol++;
											}
											hmapMultiplePuschasedProductVolumeAndValue.put(prdctIdMpdWithScheme, hmapPrdctIdPrdctName.get(prdctIdMpdWithScheme)+"^"+prdPrice+"^"+prdVol);
											totlCombinedPriceOfProdcuts=totlCombinedPriceOfProdcuts+prdPrice;
											totVolumeofProducts=totVolumeofProducts+prdVol;


										}


									}
									if(!maxBenifiAssignedValToCalc.equals(""))
									{
										boolean defaultSelected=false;
										if(Double.parseDouble(maxBenifiAssignedValToCalc)>0)
										{
										if(countPerVol==0)
										{
											countPerVol=1;
										}
										 String[] strBeniftRowIdTest=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,1,BenSubBucketValue,BenSubBucketType);
									for(int i=0;i<strBeniftRowIdTest.length;i++)
									{

										Double benifit=Double.parseDouble(strBeniftRowIdTest[i]);
										if(Double.parseDouble(maxBenifiAssignedValToCalc)==benifit)
										{
											AssigendValue=Double.parseDouble(maxBenifiAssignedValToCalc)*toMultiply;
											defaultSelected=true;
											break;
										}
									}
									if(!defaultSelected)
									{
										AssigendValue=Double.parseDouble(maxBenifiAssignedValToCalc);
									}



										}

									}


									if(hmapMultiplePuschasedProductVolumeAndValue.size()>0)
									{
										double totOverAllValueDis=0.00;
										if(schSlabSubBucketType==5)//Vol-Flat Disc on Same Prd
										{
											if(chkflgProDataCalculation==1)
											{
												if(Per.intValue()==0)
										          {
										          totOverAllValueDis=totVolumeofProducts*(AssigendValue/schSlabSubBucketValue);//BenSubBucketValue;BenSubBucketValue;
										          }
										          else
										          {
										           //totOverAllValueDis=(totVolumeofProducts*(AssigendValue/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
										        	  totOverAllValueDis=((totVolumeofProducts/Per)*AssigendValue);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
										          }
											}
											else if(chkflgProDataCalculation==0)
											{
												if(Per.intValue()==0)
												{
												totOverAllValueDis=AssigendValue;
												}
												else
												{

													totOverAllValueDis=(Double.valueOf(totVolumeofProducts/Per).intValue())*AssigendValue;

												}
											}
										}
										if(schSlabSubBucketType==4)//Val-Flat Disc on Same Prd
										{
											if(chkflgProDataCalculation==1)
											{
												if(Per.intValue()==0)
										          {
										          totOverAllValueDis=totlCombinedPriceOfProdcuts*(AssigendValue/schSlabSubBucketValue);
										          }
										          else
										          {
										           //totOverAllValueDis=(totVolumeofProducts*(totlCombinedPriceOfProdcuts/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;(totlCombinedPriceOfProdcuts*BenSubBucketValue)/Per;
										        	  totOverAllValueDis=((totVolumeofProducts/Per)*AssigendValue);//(totVolumeofProducts*(totlCombinedPriceOfProdcuts/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;(totlCombinedPriceOfProdcuts*BenSubBucketValue)/Per;
										          }
											}
											else if(chkflgProDataCalculation==0)
											{
												if(Per.intValue()==0)
												{
												totOverAllValueDis=AssigendValue;
												}
												else
												{
													totOverAllValueDis=(Double.valueOf(totlCombinedPriceOfProdcuts/Per).intValue())*AssigendValue;
												}
											}
										}
										if(schSlabSubBucketType==1)//Quantity Based
										{
											if(Per.intValue()==0)
									          {
									          totOverAllValueDis=totVolumeofProducts*(AssigendValue/schSlabSubBucketValue);//BenSubBucketValue;BenSubBucketValue;
									          }
									          else
									          {
									           //totOverAllValueDis=(totVolumeofProducts*(AssigendValue/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
									        	  totOverAllValueDis=((totVolumeofProducts/Per)*AssigendValue);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
									          }									//totOverAllValueDis=AssigendValue*toMultiply;

										}
										String[] arrPurchasedProductListVolumeAndValue=changeHmapToArrayKey(hmapMultiplePuschasedProductVolumeAndValue);
										String[] arrBenifitAssignedVal=new String[arrPurchasedProductListVolumeAndValue.length];
										HashMap<String, String> hmapFreeProdIDAlrt=new HashMap<String, String>();
										for(int cntPurchasedProductList=0;cntPurchasedProductList<arrPurchasedProductListVolumeAndValue.length;cntPurchasedProductList++)
										{
											double calculatedBenifitAssignedValueSKULevel=0.00;
											double prodValOrVol=0.00;
											double caculateVolOrValOnSchSlabBasis=0.00;

											if(schSlabSubBucketType==5)//Vol-Flat Disc on Same Prd
											{
												if(chkflgProDataCalculation==0)
												{
													if(Per.intValue()!=0)
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														int perProdValOrVol=Double.valueOf(prodValOrVol/Per).intValue();
														int perTotVolumeofProducts=Double.valueOf(totVolumeofProducts/Per).intValue();
														calculatedBenifitAssignedValueSKULevel=((float)perProdValOrVol/(float)perTotVolumeofProducts)*totOverAllValueDis;
													}
													else
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														calculatedBenifitAssignedValueSKULevel=Double.valueOf(prodValOrVol/totVolumeofProducts).intValue()*totOverAllValueDis;
													}

												}
												else
												{
													prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
													calculatedBenifitAssignedValueSKULevel=(prodValOrVol/totVolumeofProducts)*totOverAllValueDis;
												}

											}
											if(schSlabSubBucketType==4)//Val-Flat Disc on Same Prd
											{
												if(chkflgProDataCalculation==0)
												{
													if(Per.intValue()!=0)
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														int perProdValOrVol=Double.valueOf(prodValOrVol/Per).intValue();
														int perTotlCombinedPriceOfProdcuts=Double.valueOf(totlCombinedPriceOfProdcuts/Per).intValue();
														calculatedBenifitAssignedValueSKULevel=((float)perProdValOrVol/(float)perTotlCombinedPriceOfProdcuts)*totOverAllValueDis;
													}
													else
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[1]);
														calculatedBenifitAssignedValueSKULevel=(prodValOrVol/totlCombinedPriceOfProdcuts)*totOverAllValueDis;
													}

												}

											}
											if(schSlabSubBucketType==1)//Quantity Based
											{
												if(chkflgProDataCalculation==0)
												{
													if(Per.intValue()!=0)
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														int perProdValOrVol=Double.valueOf(prodValOrVol/Per).intValue();
														int perTotVolumeofProducts=Double.valueOf(totVolumeofProducts/Per).intValue();
														calculatedBenifitAssignedValueSKULevel=((float)perProdValOrVol/(float)perTotVolumeofProducts)*totOverAllValueDis;
													}
													else
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														calculatedBenifitAssignedValueSKULevel=Double.valueOf(prodValOrVol/totVolumeofProducts).intValue()*totOverAllValueDis;
													}

												}
												else
												{
													prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
													calculatedBenifitAssignedValueSKULevel=(prodValOrVol/totVolumeofProducts)*totOverAllValueDis;
												}
											}
											String productNameValue=hmapPrdctIdPrdctName.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											arrProductIDMappedInSchSlbSubBukBenifits.put(productNameValue,arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											hmapFreeProdIDAlrt.put(productNameValue,arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											hmapFreeProdID.put(productNameValue,arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											if(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail.length>1)
											{
												String subValues=String.valueOf(schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
														BenSubBucketType+"~"+0+"~"+calculatedBenifitAssignedValueSKULevel+"~"+0+"~"+0+"~"+
														0+"~"+0+"~"+0+"~"+Per+"~"+UOM+"~"+schSlbSubRowID+"~"+SchTypeId);
												arrBenifitAssignedVal[cntPurchasedProductList]=String.valueOf(calculatedBenifitAssignedValueSKULevel);
												stringSchemeIdWthAllVal.add(subValues);
												isHaveMoreBenifits=1;
												//listArrayHashmapProduct.add(hmapFreeProdID);

											}
											else
											{
												ArrayList<String> noAlrtStringSchemeIdWthAllValTemp=new ArrayList<String>();

												String noAlrtsubValues=String.valueOf(calculatedBenifitAssignedValueSKULevel+"~"+schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
														BenSubBucketType+"~"+0+"~"+BenSubBucketValue+"~"+0+"~"+0+"~"+
														0+"~"+0+"~"+0+"~"+0.0+"~"+0.0+"~"+schSlbSubRowID+"~"+SchTypeId);

												noAlrtStringSchemeIdWthAllValTemp.add(noAlrtsubValues);


												String[] arrayProductId=changeHmapToArrayValue(arrProductIDMappedInSchSlbSubBukBenifits);
												noAlrtHshMaptoSaveData.put(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList], noAlrtStringSchemeIdWthAllValTemp);
											}


											//listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
										}
										if(isHaveMoreBenifits==1)
										{
										if(disValClkdOpenAlert)
										{

											listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
											listArrayHashmapProduct.add(hmapFreeProdIDAlrt);
										}
										else
										{

											listArrayFreePrdctQty.add(arrBenifitAssignedVal);
											listArrayHashmapProduct.add(hmapFreeProdID);
										}
										}
									}
								}

								//Now get the Free Product EditTextBox ID and  gets its current value
								//Minus the AppliedQty from the present value
							}// if(BensubBucketType==10) ends here
						}//for(String strProductIDBenifitsListOnPurchase:arrProductIDBenifitsListOnPurchase ) ends here

					}//arrProductIDBenifitsListOnPurchase length condition ends here

				}//hmapSchemeIdStoreID.containsKey(""+schId) ends here
			}// for loops ends here for arredtboc_OderQuantityFinalSchemesToApply

			if(noAlrtHshMaptoSaveData.size()>0)
			{
				boolean flagMappedToProduct=false; // if noAlrtHshMaptoSaveData doenot contains arrProductIDMappedInSchSlbSubBukRowId
				if(productFullFilledSlabGlobal!=null && productFullFilledSlabGlobal.size()>0)
				{

						for(int cntProdcutsRowIdCnt=0;cntProdcutsRowIdCnt<productFullFilledSlabGlobal.size();cntProdcutsRowIdCnt++)
						{

							if(noAlrtHshMaptoSaveData.containsKey(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt)))
							{
								flagMappedToProduct=true;
								HashMap<String, ArrayList<String>> noAlrtHshMaptoSaveDataTemp=new HashMap<String, ArrayList<String>>();
								noAlrtHshMaptoSaveDataTemp.put(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt), noAlrtHshMaptoSaveData.get(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt)));
								saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveDataTemp, productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt));
							}
						}

						*//*if(!flagMappedToProduct)
						{
							saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
					}*//*

				}

				else
				{
					saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
				}
			}
			//saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
			if(listArrayHashmapProduct.size()>0)
			{
				//saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
				if(disValClkdOpenAlert)
				{
					disValClkdOpenAlert=false;

					customAlert(listArrayHashmapProduct, listArrayFreePrdctQty,alrtObjectTypeFlag , stringSchemeIdWthAllVal,ProductIdOnClicked);
				}
				else
				{

					String[] arrayProductIdToDefault=changeHmapToArrayValue(listArrayHashmapProduct.get(0));

					for(int abc=0;abc<arrayProductIdToDefault.length;abc++)
					{
						ArrayList<String> arrayListSaveAssigndVal=new ArrayList<String>();
						HashMap<String, ArrayList<String>> alerValWithDefault=new HashMap<String, ArrayList<String>>();


								String defaultVal=(listArrayFreePrdctQty.get(0))[abc].toString();

							String defaultValWithDefltAssigndVal=defaultVal+"~"+stringSchemeIdWthAllVal.get(0).toString();
							String spinnerValSelected= dbengine.getValOfSchemeAlrtSelected(storeID,(stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[1],(stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[0],strGlobalOrderID);
							String[] spinnerPositionSelected=dbengine.getValOfSchemeAlrt(storeID,ProductIdOnClicked,""+(stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[1],strGlobalOrderID);


							arrayListSaveAssigndVal.add(defaultValWithDefltAssigndVal);
							alerValWithDefault.put(arrayProductIdToDefault[abc], arrayListSaveAssigndVal);
							if(defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("10") ||defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("7") || defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("6"))
							{
								saveFreeProductDataWithSchemeToDatabase(alerValWithDefault,arrayProductIdToDefault[abc]);
								// exception sighn
								final ImageView btnExcptnAlrt=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+arrayProductIdToDefault[abc]);
								EditText edOrderText=(EditText)ll_prdct_detal.findViewWithTag("etOrderQty_"+arrayProductIdToDefault[abc]);
								if(edOrderText.getText().equals("") || TextUtils.isEmpty(edOrderText.getText().toString()))
										{
									btnExcptnAlrt.setVisibility(View.INVISIBLE);
										}
								else
								{
									btnExcptnAlrt.setVisibility(View.VISIBLE);
								}



								btnExcptnAlrt.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										disValClkdOpenAlert=true;
										 getOrderData(((btnExcptnAlrt.getTag().toString()).split(Pattern.quote("_")))[1]);

										// orderBookingTotalCalc();

									}
								});


							}
							else
							{
								saveFreeProductDataWithSchemeToDatabase(alerValWithDefault,ProductIdOnClicked);


								final ImageView btnExcptnAlrt=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+ProductIdOnClicked);
								EditText edOrderText=(EditText)ll_prdct_detal.findViewWithTag("etOrderQty_"+ProductIdOnClicked);
								if(edOrderText.getText().equals("") || TextUtils.isEmpty(edOrderText.getText().toString()))
										{
									if(btnExcptnAlrt.getVisibility()==View.VISIBLE)
									{
										btnExcptnAlrt.setVisibility(View.INVISIBLE);
									}

										}
								else
								{

									btnExcptnAlrt.setVisibility(View.VISIBLE);
									btnExcptnAlrt.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {

											disValClkdOpenAlert=true;
											 getOrderData(((btnExcptnAlrt.getTag().toString()).split(Pattern.quote("_")))[1]);

											// orderBookingTotalCalc();

										}
									});
								}


								for(int i=0;i<productFullFilledSlabGlobal.size();i++)
								{
									if(Integer.parseInt( hmapPrdctOdrQty.get(productFullFilledSlabGlobal.get(i)))>0)
									{
										final ImageView btnExcptnAlrtTemp=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+productFullFilledSlabGlobal.get(i));
										EditText edOrderTextTemp=(EditText)ll_prdct_detal.findViewWithTag("etOrderQty_"+productFullFilledSlabGlobal.get(i));

										if(btnExcptnAlrtTemp.getVisibility()==View.INVISIBLE)
										{
											btnExcptnAlrtTemp.setVisibility(View.VISIBLE);
											btnExcptnAlrtTemp.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View v) {

													disValClkdOpenAlert=true;
													 getOrderData(((btnExcptnAlrtTemp.getTag().toString()).split(Pattern.quote("_")))[1]);

													// orderBookingTotalCalc();

												}
											});
										}








									}

								}



							}


												}


				//	}




								}
							}

		}// ends here arredtboc_OderQuantityFinalSchemesToApply length check
	}*/

	public void fnCheckNewSchemeIDsAppliedAfterValueChange(String SchIdsCompleteListOnProductID,String ProductIdOnClicked)
	{
		arredtboc_OderQuantityFinalSchemesToApply=new ArrayList<String>();
	//Example :-1075_1_0_1!1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
	String valForVolumetQTYToMultiply="0";
	productFullFilledSlabGlobal=new ArrayList<String>();
	String[] arrSchIdsListOnProductID=SchIdsCompleteListOnProductID.split("#");
	for(int pSchIdsAppliCount=0;pSchIdsAppliCount<arrSchIdsListOnProductID.length;pSchIdsAppliCount++)
	{
		String schOverviewDetails=arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[0];   //Example :-1075_1_0_1
		String schOverviewOtherDetails=arrSchIdsListOnProductID[pSchIdsAppliCount].split("!")[1]; //Example :-1026$1^1|1^23^1^10^0@1025$1^1|1^22^1^20^0@1022$1^1|1^19^5^5^0@1020$1^1|1^17^3^4^0@1019$1^1|1^16^1^12^0@1018$1^1|1^15^1^10^0@1017$1^1|1^14^1^12^0
		int schId=Integer.parseInt(schOverviewDetails.split("_")[0]);                           //Example :-1075
		int schAppRule=Integer.parseInt(schOverviewDetails.split("_")[1]);                                                                                        //Example :-1
		int schApplicationId=Integer.parseInt(schOverviewDetails.split("_")[2]);                                                              //Example :-0
		int SchTypeId=Integer.parseInt(schOverviewDetails.split("_")[3]);                                                                                           //Example :-1 // 1=Check Combined Skus, 2=Bundle,3=Simple with Check on Individual SKU
		String[] arrschSlbIDsOnSchIdBasis=schOverviewOtherDetails.split("@");                                                                               //Split for multiple slabs Example :-1026$1^1|1^23^1^10^0, 1025$1^1|1^22^1^20^0

		int exitWhenSlabToExit=0;

		if(hmapSchemeIdStoreID.containsKey(""+schId))
		{
			boolean bucketCndtnFullFillisReally=false;
			for(int pSchSlbCount=0;pSchSlbCount<arrschSlbIDsOnSchIdBasis.length;pSchSlbCount++)
			{
				//Exmaple Slab:- 1026$1^1|1^23^1^10^0
				int schSlabId=Integer.parseInt((arrschSlbIDsOnSchIdBasis[pSchSlbCount]).split(Pattern.quote("$"))[0]); //Exmaple Slab ID:- 1026
				String schSlabOtherDetails=arrschSlbIDsOnSchIdBasis[pSchSlbCount].split(Pattern.quote("$"))[1]; //Exmaple Slab OtherDetails:- 1^1|1^23^1^10^0
				String[] arrSchSlabBuckWiseDetails=schSlabOtherDetails.split(Pattern.quote("~")); //Example Split For Multiple Buckets
				for(int pSchSlbBuckCnt=0;pSchSlbBuckCnt<arrSchSlabBuckWiseDetails.length;pSchSlbBuckCnt++)
				{
					String schSlbBuckDetails=arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[0]; // Eaxmple:-1^1
					String schSlbBuckOtherDetails=arrSchSlabBuckWiseDetails[pSchSlbBuckCnt].split(Pattern.quote("|"))[1];  // Eaxmple:-1^23^1^10^0
					int schSlbBuckId=Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[0]);  //Exmaple Slab Bucket ID:- 1
					int schSlbBuckCnt=Integer.parseInt(schSlbBuckDetails.split(Pattern.quote("^"))[1]);            //Example Number of Buckets under this Slab, Count:-1

					String[] arrSubBucketDetails=schSlbBuckOtherDetails.split(Pattern.quote("*"));  //Example Split For Multiple Sub Buckets
					String[] arrMaintainDetailsOfBucketConditionsAgainstBuckId=new String[schSlbBuckCnt];  //Example Length of Buckes in Slab and which condition is true in case of OR
				// variables for calculating total sub bucket
					ArrayList<String> productFullFilledSlab=new ArrayList<String>();
					ArrayList<String> productFullFilledSlabForInvoice=new ArrayList<String>();
					int totalProductQnty=0;
					double totalProductVol=0.0;

					double totalProductVal=0.0;
					int totalProductLine=0;
					double totalInvoice=0.0;

					//product invoice
					for(Entry<String, String> entryProduct:hmapPrdctOdrQty.entrySet())
					{
						if(hmapPrdctOdrQty.containsKey(entryProduct.getKey()))
						{
							if(Integer.parseInt(hmapPrdctOdrQty.get(entryProduct.getKey()))>(0))
							{
								int curntProdQty = Integer.parseInt(entryProduct.getValue()) ;
								String curntProdVolumeRate = hmapPrdctVolRatTax.get(entryProduct.getKey());
							    Double curntProdRate=Double.parseDouble(curntProdVolumeRate.split(Pattern.quote("^"))[1]);

							    Double currentProductOverAllPriceQtywise=curntProdRate * curntProdQty;
							    totalInvoice=totalInvoice+currentProductOverAllPriceQtywise;
							    productFullFilledSlabForInvoice.add(entryProduct.getKey());
							}
						}

					}
					// end product invoice
					//sub bucket starts here
				LinkedHashMap<String, String> hmapSubBucketDetailsData=new LinkedHashMap<String, String>();
					for(int cntSubBucket=0;cntSubBucket<arrSubBucketDetails.length;cntSubBucket++)
					{
						// Eaxmple:-1^23^1^10^0
						int schSlbSubBuckID=Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[0]); //Slab Sub BucketID Eaxmple:-1
						int schSlbSubRowID=Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[1]);  //Slab Sub Bucket RowID Eaxmple:-23
						int schSlabSubBucketType=Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[2]);  ///Slab Sub Bucket Type Eaxmple:-1

						Double schSlabSubBucketValue=Double.parseDouble(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[3]);  ///Slab Sub Bucket Value Eaxmple:-10
						int schSubBucketValType=Integer.parseInt(arrSubBucketDetails[cntSubBucket].split(Pattern.quote("^"))[4]); ///Slab Sub Bucket Value Type Eaxmple:-0


						int totalOderQtyProductsAgainstRowId=0;


						ArrayList<String> arrProductIDMappedInSchSlbSubBukRowId=new ArrayList<String>();



						//String[] productFullFilledSlab=new String[arrProductIDMappedInSchSlbSubBukRowId.length];
						int positionOfProductHavingQntty=0;


						//IF SchTypeID==1 OR SchTypeID==2 OR SchTypeID==3  Code Starts Here To Check the Products

						if(SchTypeId==1 || SchTypeId==2)
						{
							arrProductIDMappedInSchSlbSubBukRowId=dbengine.fectProductIDMappedInSchSlbSubBukRowIdTemp(schSlbSubRowID);
						}
						if(SchTypeId==3)
						{
							arrProductIDMappedInSchSlbSubBukRowId.add(ProductIdOnClicked);
						}

						//IF SchTypeID==1 OR SchTypeID==2 OR SchTypeID==3  Code Ends Here To Check the Products
						//SlabSubBucketValType
						//I           =Invoice Value                  Order Value After Tax
						//G         =Gross Value                     Order Value Before Tax
						//N         =Net Value                                         Order Value After Tax


						if(arrProductIDMappedInSchSlbSubBukRowId.size()>0)
						{


							for(String productMappedWithScheme:arrProductIDMappedInSchSlbSubBukRowId)
							{

								String hmapSubBucketDetailsData_Value=	schId+"^"+schSlabId+"^"+schSlbBuckId+"^"+schSlabSubBucketValue+"^"+schSubBucketValType+"^"+schSlabSubBucketType+"^"+ProductIdOnClicked +"^"+valForVolumetQTYToMultiply+"^"+schSlbSubRowID+"^"+SchTypeId;
								hmapSubBucketDetailsData.put(productMappedWithScheme,hmapSubBucketDetailsData_Value );
								if(hmapPrdctOdrQty.containsKey(productMappedWithScheme))
								{
									if(Integer.parseInt(hmapPrdctOdrQty.get(productMappedWithScheme))>(0))
									{
										//1. Product Quantity

										productFullFilledSlab.add(productMappedWithScheme);// productLine
										int oderQtyOnProd=Integer.parseInt(hmapPrdctOdrQty.get(productMappedWithScheme));
										totalProductQnty=totalProductQnty+oderQtyOnProd;

										// product volume
										Double prodVolume= Double.parseDouble(hmapPrdctVolRatTax.get(productMappedWithScheme).split(Pattern.quote("^"))[0]);
										Double oderVolumeOfCurrentMapedProduct=prodVolume * oderQtyOnProd;
										totalProductVol=totalProductVol + oderVolumeOfCurrentMapedProduct;

										//product value

										Double prodRate= Double.parseDouble(hmapPrdctVolRatTax.get(productMappedWithScheme).split(Pattern.quote("^"))[1]);
										Double oderRateOfCurrentMapedProduct=prodRate * oderQtyOnProd;
										//oderRateOnProduct=oderRateOnProduct + oderRateOfCurrentMapedProduct;
										totalProductVal=totalProductVal+oderRateOfCurrentMapedProduct;


									}
								}


							}// for loops ends here productMappedWithScheme:arrProductIDMappedInSchSlbSubBukRowId


						}// ends if(arrProductIDMappedInSchSlbSubBukRowId.size()>0)



					} //sub bucket ends here

					//schSlabSubBucketType
					//1. Product Quantity
					//5. Product Volume
					//2. Invoice Value
					//3. Product Lines
					//4. Product Value
					boolean bucketCndtnFullFill=true;
					String stringValHmap="";
					String stringValHmapInvoice="";
					ArrayList<String> listStrValHmapForSchm2=new ArrayList<String>();
					if(productFullFilledSlabForInvoice!=null && productFullFilledSlabForInvoice.size()>0)
					{
						for(String productIdFullFilledSlabInvoiceWithQty:productFullFilledSlabForInvoice)
						{
							if(hmapSubBucketDetailsData.containsKey(productIdFullFilledSlabInvoiceWithQty))
							{
								stringValHmapInvoice=hmapSubBucketDetailsData.get(productIdFullFilledSlabInvoiceWithQty);
								String schSlabSubBucketType=stringValHmapInvoice.split(Pattern.quote("^"))[5];
								Double schSlabSubBucketVal=Double.valueOf(stringValHmapInvoice.split(Pattern.quote("^"))[3]);
								if(schSlabSubBucketType.equals("2"))
								{
									if(totalInvoice>=schSlabSubBucketVal)
									{
									  dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabInvoiceWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
										break;
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										stringValHmapInvoice="";
										break;
									}

								}
								else
								{
									stringValHmapInvoice="";
								}
							}

						}
					}

					if(productFullFilledSlab!=null && productFullFilledSlab.size()>0)
					{
						for(String productIdFullFilledSlabWithQty:productFullFilledSlab)
						{
							stringValHmap=hmapSubBucketDetailsData.get(productIdFullFilledSlabWithQty);
							String schSlabSubBucketType=stringValHmap.split(Pattern.quote("^"))[5];
							Double schSlabSubBucketVal=Double.valueOf(stringValHmap.split(Pattern.quote("^"))[3]);
							if(SchTypeId==1 || SchTypeId==3)
							{


								if(schSlabSubBucketType.equals("1"))
								{
									if(totalProductQnty>=schSlabSubBucketVal)
									{
										dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);

									}
									else
									{

										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										stringValHmap="";
										break;
									}

								}
								//Product Line
								 if(schSlabSubBucketType.equals("3"))
								{
									if(productFullFilledSlab.size()>=schSlabSubBucketVal)
									{
										 dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										stringValHmap="";
										break;
									}
								}
								 //product Value
								 if(schSlabSubBucketType.equals("4"))
								{
									if(totalProductVal>=schSlabSubBucketVal)
									{
										 dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										stringValHmap="";
										break;
									}

								}
								 //product volume
								 if(schSlabSubBucketType.equals("5"))
								{
									 if(totalProductVol>=(schSlabSubBucketVal*1000))
									{
										 dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										stringValHmap="";
										break;
									}
								}

							}
							else // scheme typeid=2
							{

								if(schSlabSubBucketType.equals("1"))
								{
									if(Integer.parseInt(hmapPrdctOdrQty.get(productIdFullFilledSlabWithQty))>=schSlabSubBucketVal)
									{
										listStrValHmapForSchm2.add(stringValHmap);
										dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);

									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										listStrValHmapForSchm2.clear();
										break;
									}

								}

								 if(schSlabSubBucketType.equals("3"))
								{
									if(productFullFilledSlab.size()>=schSlabSubBucketVal)
									{
										listStrValHmapForSchm2.add(stringValHmap);
										 dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										listStrValHmapForSchm2.clear();
										break;
									}
								}
								 if(schSlabSubBucketType.equals("4"))
								{
									 Double singleProdRate= Double.parseDouble(hmapPrdctVolRatTax.get(productIdFullFilledSlabWithQty).split(Pattern.quote("^"))[1]);
										Double singlePrdctOderRate=singleProdRate * Integer.parseInt(hmapPrdctOdrQty.get(productIdFullFilledSlabWithQty));


									if(singlePrdctOderRate>=schSlabSubBucketVal)
									{
										listStrValHmapForSchm2.add(stringValHmap);
										 dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										listStrValHmapForSchm2.clear();
										break;
									}

								}
								 if(schSlabSubBucketType.equals("5"))
								{
									 Double singleProdVol= Double.parseDouble(hmapPrdctVolRatTax.get(productIdFullFilledSlabWithQty).split(Pattern.quote("^"))[0]);
										Double singlePrdctOderVol=singleProdVol * Integer.parseInt(hmapPrdctOdrQty.get(productIdFullFilledSlabWithQty));

									if(singlePrdctOderVol>=schSlabSubBucketVal)
									{
										listStrValHmapForSchm2.add(stringValHmap);
										 dbengine.insertProductMappedWithSchemApplied(storeID, productIdFullFilledSlabWithQty,""+schSlabId,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
									}
									else
									{
										dbengine.deleteAlertValueSlab(storeID,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										bucketCndtnFullFill=false;
										listStrValHmapForSchm2.clear();
										break;
									}
								}


							}


						}


					}//	if(productFullFilledSlab!=null && productFullFilledSlab.size()>0) ends here


					if(bucketCndtnFullFill)
					{
						bucketCndtnFullFillisReally=true;
						if(SchTypeId==1 || SchTypeId==3)
						{
							if(!TextUtils.isEmpty(stringValHmap.trim()))
							{
								 productFullFilledSlabGlobal=productFullFilledSlab;

								arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmap+"^"+totalProductQnty+"^"+totalInvoice+"^"+totalProductLine+"^"+totalProductVal+"^"+totalProductVol);
							}
							else if(!TextUtils.isEmpty(stringValHmapInvoice.trim()))
							{
								arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmapInvoice+"^"+totalProductQnty+"^"+totalInvoice+"^"+totalProductLine+"^"+totalProductVal+"^"+totalProductVol);
							}
						}
						else
						{
							if(listStrValHmapForSchm2!=null && listStrValHmapForSchm2.size()>0)
							{
								 productFullFilledSlabGlobal=productFullFilledSlab;

								for(String strVal:listStrValHmapForSchm2)
								{

									arredtboc_OderQuantityFinalSchemesToApply.add(strVal+"^"+totalProductQnty+"^"+totalInvoice+"^"+totalProductLine+"^"+totalProductVal+"^"+totalProductVol);
								}
							}
							if(!TextUtils.isEmpty(stringValHmapInvoice.trim()))
							{
								arredtboc_OderQuantityFinalSchemesToApply.add(stringValHmapInvoice+"^"+totalProductQnty+"^"+totalInvoice+"^"+totalProductLine+"^"+totalProductVal+"^"+totalProductVol);
							}

						}
						break;
					}//if(bucketCndtnFullFill) ends here

				}// bucket ends here

				if(bucketCndtnFullFillisReally)
				{
					break;
				}
			}
		}
	}
	fnAssignSchemeIDsAppliedOverProductAfterValueChange(ProductIdOnClicked);
	}

	public void fnAssignSchemeIDsAppliedOverProductAfterValueChange(String ProductIdOnClicked)
	{
		HashMap<String, ArrayList<String>> noAlrtHshMaptoSaveData=new HashMap<String, ArrayList<String>>();
		ArrayList<String> noAlrtStringSchemeIdWthAllVal=new ArrayList<String>();
		ArrayList<String> stringSchemeIdWthAllVal=new ArrayList<String>();
		ArrayList<HashMap<String, String>> listArrayHashmapProduct=new ArrayList<HashMap<String, String>>();
		ArrayList<String[]> listArrayFreePrdctQty=new ArrayList<String[]>();

		if(arredtboc_OderQuantityFinalSchemesToApply.size()>0)
		{
			for(String strListMpdWdPrdct:arredtboc_OderQuantityFinalSchemesToApply)
			{
				//schId+"^"+schSlabId+"^"+schSlbBuckId+"^"+schSlabSubBucketValue+"^"+schSubBucketValType+"^"
				//+schSlabSubBucketType+"^"+ProductIdOnClicked +"^"+valForVolumetQTYToMultiply+"^"
				//+schSlbSubRowID+"^"+SchTypeId+"^"+totalProductQnty+"^"+totalInvoice+"^"
				//+totalProductLine+"^"+totalProductVal+totalProductVol;
				int schId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[0]);
				int schSlabId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[1]);
				int schSlbBuckId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[2]);
				Double schSlabSubBucketValue=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[3]);
				int schSubBucketValType=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[4]);
				int schSlabSubBucketType=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[5]);
				int Pid=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[6]);
				int toMultiply=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[7]);
				int schSlbSubRowID=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[8]);
				int SchTypeId=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[9]);
				int totalProductQty=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[10]);
				double totalInvoice=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[11]);
				int totalProductLine=Integer.parseInt(strListMpdWdPrdct.split(Pattern.quote("^"))[12]);
				double totalProductVal=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[13]);
				double totalProductVol=Double.parseDouble(strListMpdWdPrdct.split(Pattern.quote("^"))[14]);

				if(hmapSchemeIdStoreID.containsKey(""+schId))
				{
					String[] arrProductIDBenifitsListOnPurchase=dbengine.fectProductIDBenifitsListOnPurchase(schId,schSlabId,schSlbBuckId);
					// RowID AS BenifitRowID,BenSubBucketType,BenDiscApplied,CouponCode,BenSubBucketValue,Per, UOM,ProRata
					if(arrProductIDBenifitsListOnPurchase!=null && arrProductIDBenifitsListOnPurchase.length>0)
					{
						for(String strProductIDBenifitsListOnPurchase:arrProductIDBenifitsListOnPurchase )
						{
							int BenifitRowID=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[0]);
							int BenSubBucketType=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[1]);
							int BenDiscApplied=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[2]);

							// MinValueQty of free product
							//String CouponCode=strProductIDBenifitsListOnPurchase.split("^")[0];
							Double BenSubBucketValue=Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[4]);
							Double Per=Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[5]);
							Double UOM=Double.parseDouble(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[6]);
							int chkflgProDataCalculation=Integer.parseInt(strProductIDBenifitsListOnPurchase.split(Pattern.quote("^"))[7]);

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
							//10. PerVolume Discount


							if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3 ||BenSubBucketType==5 ||BenSubBucketType==6 || BenSubBucketType==7) //1. Free Other Product 2. Discount in Percentage with other product 3. Discount in Amount with other product
							{
								HashMap<String, String> arrProductIDMappedInSchSlbSubBukBenifits=new HashMap<String, String>();
								//productFullFilledSlabGlobal;
								//BenValue
								int isHaveMoreBenifits=0;

								String[] strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,toMultiply,BenSubBucketValue,BenSubBucketType);
								if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3)
								{
									arrProductIDMappedInSchSlbSubBukBenifits=dbengine.fectProductIDMappedInSchSlbSubBukBenifits(BenifitRowID);
								}
								else
								{

									for(String productIdToFillSlab:productFullFilledSlabGlobal)
									{
										arrProductIDMappedInSchSlbSubBukBenifits.put(hmapPrdctIdPrdctName.get(productIdToFillSlab), productIdToFillSlab);
									}

								}

								defaultValForAlert=strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];
								defaultValForAlert=strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];
								HashMap<String, String> hmapFreeProdID=new HashMap<String, String>();
								HashMap<String, String> hmapFreeProdIDAlrt=new HashMap<String, String>();
								if(arrProductIDMappedInSchSlbSubBukBenifits.size()>0)
								{

									String[] arrBenifitAssignedVal=new String[arrProductIDMappedInSchSlbSubBukBenifits.size()];
									int countAssignVal=0;
									for(Entry<String, String> allPrdctNamePrdctId:arrProductIDMappedInSchSlbSubBukBenifits.entrySet())
									{
										Double accAsignVal=0.0;
										String productIdForFree=allPrdctNamePrdctId.getValue();

										String maxBenifiAssignedValToCalc="";
										String maxBenifiAssignedVal=dbengine.getValOfSchemeAlrtSelected(storeID,""+schId,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
										if(Double.parseDouble(maxBenifiAssignedVal)>0)
										{

											maxBenifiAssignedValToCalc=maxBenifiAssignedVal;

										}
										else
										{
											dbengine.deleteAlertValueProduct(storeID,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
										}
										boolean defaultSelected=false;
										if(!maxBenifiAssignedValToCalc.equals(""))
										{

											if(Double.parseDouble(maxBenifiAssignedValToCalc)>0)
											{
												accAsignVal=Double.parseDouble(maxBenifiAssignedValToCalc);

												String[] strBeniftRowIdTest=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,1,BenSubBucketValue,BenSubBucketType);
												for(int i=0;i<strBeniftRowIdTest.length;i++)
												{

													Double benifit=Double.parseDouble(strBeniftRowIdTest[i]);
													if(Double.parseDouble(maxBenifiAssignedValToCalc)==benifit)
													{
														if(countAssignVal==(arrProductIDMappedInSchSlbSubBukBenifits.size()-1))
														{
															accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree,true );

														}
														else
														{
															if(BenSubBucketType==6)
															{
																accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree,true );
															}
															else{
																accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree,false );
															}

														}
														// accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, Double.parseDouble(maxBenifiAssignedVal), schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per ,productIdForFree);

														defaultSelected=true;
														break;
													}
												}
											}

										}
										if(!defaultSelected)
										{
											if(countAssignVal==(arrProductIDMappedInSchSlbSubBukBenifits.size()-1))
											{
												accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per,productIdForFree,true );

											}
											else
											{
												if(BenSubBucketType==6)
												{
													accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per,productIdForFree,true );
												}
												else{
													accAsignVal=getAccAsignValue(schSlabSubBucketType, chkflgProDataCalculation, BenSubBucketValue, schSlabSubBucketValue, totalProductQty, totalProductLine, totalProductVal, totalProductVol, totalInvoice,Per,productIdForFree,false );
												}

											}

										}

										String productNameValue=allPrdctNamePrdctId.getKey();
										hmapFreeProdIDAlrt.put(productNameValue,productIdForFree);
										hmapFreeProdID.put(productNameValue,productIdForFree);

										if(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail.length>1)
										{
											String subValues=String.valueOf(schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
													BenSubBucketType+"~"+0+"~"+BenSubBucketValue+"~"+0+"~"+0+"~"+
													0+"~"+0+"~"+0+"~"+0.0+"~"+0.0+"~"+schSlbSubRowID+"~"+SchTypeId+"~"+chkflgProDataCalculation);
											stringSchemeIdWthAllVal.add(subValues);
											//listArrayHashmapProduct.add(hmapFreeProdID);
											isHaveMoreBenifits=1;
											arrBenifitAssignedVal[countAssignVal]=(String.valueOf(accAsignVal));
											//       listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);



										}
										else
										{
											isHaveMoreBenifits=0;


											ArrayList<String> noAlrtStringSchemeIdWthAllValTemp=new ArrayList<String>();

											String noAlrtsubValues=String.valueOf(accAsignVal+"~"+schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
													BenSubBucketType+"~"+0+"~"+BenSubBucketValue+"~"+0+"~"+0+"~"+
													0+"~"+0+"~"+0+"~"+0.0+"~"+0.0+"~"+schSlbSubRowID+"~"+SchTypeId);

											noAlrtStringSchemeIdWthAllValTemp.add(noAlrtsubValues);


											//String[] arrayProductId=changeHmapToArrayValue(arrProductIDMappedInSchSlbSubBukBenifits);
											noAlrtHshMaptoSaveData.put(productIdForFree, noAlrtStringSchemeIdWthAllValTemp);

										}
										countAssignVal++;
									}// for loop arrProductIDMappedInSchSlbSubBukBenifits.entrySet() ends here

									if(isHaveMoreBenifits==1)
									{
										if(disValClkdOpenAlert)
										{


											listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
											listArrayHashmapProduct.add(hmapFreeProdIDAlrt);
										}
										else
										{

											listArrayFreePrdctQty.add(arrBenifitAssignedVal);
											listArrayHashmapProduct.add(hmapFreeProdID);
										}
									}
								}//if(arrProductIDMappedInSchSlbSubBukBenifits.size()>0)
								alrtStopResult=false;
							}//if(BenSubBucketType==1 || BenSubBucketType==2 || BenSubBucketType==3) ends here
							if(BenSubBucketType==10) //10. Free pr Unit Volume
							{

								HashMap<String, String> hmapMultiplePuschasedProductVolumeAndValue=new HashMap<String, String>();
								HashMap<String, String> arrProductIDMappedInSchSlbSubBukBenifits=new HashMap<String, String>();
								LinkedHashMap<String, String> hmapFreeProdID=new LinkedHashMap<String, String>();
								dbengine.open();

                     /*String productNameValue=hmapPrdctIdPrdctName.get(ProductIdOnClicked);
                     arrProductIDMappedInSchSlbSubBukBenifits.put(productNameValue,ProductIdOnClicked);*/




								String[] strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,toMultiply,BenSubBucketValue,BenSubBucketType);
								defaultValForAlert=strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0];




								dbengine.close();
								Double AssigendValue=Double.parseDouble(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail[0]);
								double totVolumeofProducts=0.00;
								double totlCombinedPriceOfProdcuts=0.00;
								if(productFullFilledSlabGlobal.size()>0)
								{
									int countPerVol=0;
									String maxBenifiAssignedVal=null;
									String maxBenifiAssignedValToCalc="";
									int isHaveMoreBenifits=0;
									for(String prdctIdMpdWithScheme:productFullFilledSlabGlobal)
									{


										if(Integer.parseInt(hmapPrdctOdrQty.get(prdctIdMpdWithScheme))>0)
										{


											maxBenifiAssignedVal=dbengine.getValOfSchemeAlrtSelected(storeID,""+schId,""+schSlabId,strGlobalOrderID,strGlobalInvoiceNumber);
											if(Double.parseDouble(maxBenifiAssignedVal)>0)
											{

												maxBenifiAssignedValToCalc=maxBenifiAssignedVal;

											}
											else
											{
												dbengine.deleteAlertValueProduct(storeID,""+schId,strGlobalOrderID,strGlobalInvoiceNumber);
											}
											double prdPrice=Double.parseDouble(hmapPrdctVolRatTax.get(prdctIdMpdWithScheme).split(Pattern.quote("^"))[1])*Double.parseDouble(hmapPrdctOdrQty.get(prdctIdMpdWithScheme));
											double prdVol=Double.parseDouble(hmapPrdctVolRatTax.get(prdctIdMpdWithScheme).split(Pattern.quote("^"))[0])*Double.parseDouble(hmapPrdctOdrQty.get(prdctIdMpdWithScheme));
											if(prdVol>=Per)
											{
												countPerVol++;
											}
											hmapMultiplePuschasedProductVolumeAndValue.put(prdctIdMpdWithScheme, hmapPrdctIdPrdctName.get(prdctIdMpdWithScheme)+"^"+prdPrice+"^"+prdVol);
											totlCombinedPriceOfProdcuts=totlCombinedPriceOfProdcuts+prdPrice;
											totVolumeofProducts=totVolumeofProducts+prdVol;


										}


									}
									if(!maxBenifiAssignedValToCalc.equals(""))
									{
										boolean defaultSelected=false;
										if(Double.parseDouble(maxBenifiAssignedValToCalc)>0)
										{
											if(countPerVol==0)
											{
												countPerVol=1;
											}
											String[] strBeniftRowIdTest=dbengine.fectStatusIfBeniftRowIdExistsInSchemeSlabBenefitsValueDetailWithoutMultiply(BenifitRowID,1,BenSubBucketValue,BenSubBucketType);
											for(int i=0;i<strBeniftRowIdTest.length;i++)
											{

												Double benifit=Double.parseDouble(strBeniftRowIdTest[i]);
												if(Double.parseDouble(maxBenifiAssignedValToCalc)==benifit)
												{
													AssigendValue=Double.parseDouble(maxBenifiAssignedValToCalc)*toMultiply;
													defaultSelected=true;
													break;
												}
											}
											if(!defaultSelected)
											{
												AssigendValue=Double.parseDouble(maxBenifiAssignedValToCalc);
											}



										}

									}


									if(hmapMultiplePuschasedProductVolumeAndValue.size()>0)
									{
										double totOverAllValueDis=0.00;
										if(schSlabSubBucketType==5)//Vol-Flat Disc on Same Prd
										{
											if(chkflgProDataCalculation==1)
											{
												if(Per.intValue()==0)
												{
													totOverAllValueDis=totVolumeofProducts*(AssigendValue/schSlabSubBucketValue);//BenSubBucketValue;BenSubBucketValue;
												}
												else
												{
													//totOverAllValueDis=(totVolumeofProducts*(AssigendValue/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
													totOverAllValueDis=((totVolumeofProducts/Per)*AssigendValue);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
												}
											}
											else if(chkflgProDataCalculation==0)
											{
												if(Per.intValue()==0)
												{
													totOverAllValueDis=AssigendValue;
												}
												else
												{

													totOverAllValueDis=(Double.valueOf(totVolumeofProducts/Per).intValue())*AssigendValue;

												}
											}
										}
										if(schSlabSubBucketType==4)//Val-Flat Disc on Same Prd
										{
											if(chkflgProDataCalculation==1)
											{
												if(Per.intValue()==0)
												{
													totOverAllValueDis=totlCombinedPriceOfProdcuts*(AssigendValue/schSlabSubBucketValue);
												}
												else
												{
													//totOverAllValueDis=(totVolumeofProducts*(totlCombinedPriceOfProdcuts/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;(totlCombinedPriceOfProdcuts*BenSubBucketValue)/Per;
													totOverAllValueDis=((totVolumeofProducts/Per)*AssigendValue);//(totVolumeofProducts*(totlCombinedPriceOfProdcuts/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;(totlCombinedPriceOfProdcuts*BenSubBucketValue)/Per;
												}
											}
											else if(chkflgProDataCalculation==0)
											{
												if(Per.intValue()==0)
												{
													totOverAllValueDis=AssigendValue;
												}
												else
												{
													totOverAllValueDis=(Double.valueOf(totlCombinedPriceOfProdcuts/Per).intValue())*AssigendValue;
												}
											}
										}
										if(schSlabSubBucketType==1)//Quantity Based
										{
											if(Per.intValue()==0)
											{
												totOverAllValueDis=totVolumeofProducts*(AssigendValue/schSlabSubBucketValue);//BenSubBucketValue;BenSubBucketValue;
											}
											else
											{
												//totOverAllValueDis=(totVolumeofProducts*(AssigendValue/Double.parseDouble(schSlabSubBucketValue))/Per);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
												totOverAllValueDis=((totVolumeofProducts/Per)*AssigendValue);//BenSubBucketValue;(totVolumeofProducts*BenSubBucketValue)/Per;
											}                            //totOverAllValueDis=AssigendValue*toMultiply;

										}
										String[] arrPurchasedProductListVolumeAndValue=changeHmapToArrayKey(hmapMultiplePuschasedProductVolumeAndValue);
										String[] arrBenifitAssignedVal=new String[arrPurchasedProductListVolumeAndValue.length];
										HashMap<String, String> hmapFreeProdIDAlrt=new HashMap<String, String>();
										for(int cntPurchasedProductList=0;cntPurchasedProductList<arrPurchasedProductListVolumeAndValue.length;cntPurchasedProductList++)
										{
											double calculatedBenifitAssignedValueSKULevel=0.00;
											double prodValOrVol=0.00;
											double caculateVolOrValOnSchSlabBasis=0.00;

											if(schSlabSubBucketType==5)//Vol-Flat Disc on Same Prd
											{
												if(chkflgProDataCalculation==0)
												{
													if(Per.intValue()!=0)
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														int perProdValOrVol=Double.valueOf(prodValOrVol/Per).intValue();
														int perTotVolumeofProducts=Double.valueOf(totVolumeofProducts/Per).intValue();
														calculatedBenifitAssignedValueSKULevel=((float)perProdValOrVol/(float)perTotVolumeofProducts)*totOverAllValueDis;
													}
													else
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														calculatedBenifitAssignedValueSKULevel=Double.valueOf(prodValOrVol/totVolumeofProducts).intValue()*totOverAllValueDis;
													}

												}
												else
												{
													prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
													calculatedBenifitAssignedValueSKULevel=(prodValOrVol/totVolumeofProducts)*totOverAllValueDis;
												}

											}
											if(schSlabSubBucketType==4)//Val-Flat Disc on Same Prd
											{
												if(chkflgProDataCalculation==0)
												{
													if(Per.intValue()!=0)
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														int perProdValOrVol=Double.valueOf(prodValOrVol/Per).intValue();
														int perTotlCombinedPriceOfProdcuts=Double.valueOf(totlCombinedPriceOfProdcuts/Per).intValue();
														calculatedBenifitAssignedValueSKULevel=((float)perProdValOrVol/(float)perTotlCombinedPriceOfProdcuts)*totOverAllValueDis;
													}
													else
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[1]);
														calculatedBenifitAssignedValueSKULevel=(prodValOrVol/totlCombinedPriceOfProdcuts)*totOverAllValueDis;
													}

												}

											}
											if(schSlabSubBucketType==1)//Quantity Based
											{
												if(chkflgProDataCalculation==0)
												{
													if(Per.intValue()!=0)
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														int perProdValOrVol=Double.valueOf(prodValOrVol/Per).intValue();
														int perTotVolumeofProducts=Double.valueOf(totVolumeofProducts/Per).intValue();
														calculatedBenifitAssignedValueSKULevel=((float)perProdValOrVol/(float)perTotVolumeofProducts)*totOverAllValueDis;
													}
													else
													{
														prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
														calculatedBenifitAssignedValueSKULevel=Double.valueOf(prodValOrVol/totVolumeofProducts).intValue()*totOverAllValueDis;
													}

												}
												else
												{
													prodValOrVol=Double.parseDouble(hmapMultiplePuschasedProductVolumeAndValue.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]).split(Pattern.quote("^"))[2]);
													calculatedBenifitAssignedValueSKULevel=(prodValOrVol/totVolumeofProducts)*totOverAllValueDis;
												}
											}
											String productNameValue=hmapPrdctIdPrdctName.get(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											arrProductIDMappedInSchSlbSubBukBenifits.put(productNameValue,arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											hmapFreeProdIDAlrt.put(productNameValue,arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											hmapFreeProdID.put(productNameValue,arrPurchasedProductListVolumeAndValue[cntPurchasedProductList]);
											if(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail.length>1)
											{
												String subValues=String.valueOf(schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
														BenSubBucketType+"~"+0+"~"+calculatedBenifitAssignedValueSKULevel+"~"+0+"~"+0+"~"+
														0+"~"+0+"~"+0+"~"+Per+"~"+UOM+"~"+schSlbSubRowID+"~"+SchTypeId);
												arrBenifitAssignedVal[cntPurchasedProductList]=String.valueOf(calculatedBenifitAssignedValueSKULevel);
												stringSchemeIdWthAllVal.add(subValues);
												isHaveMoreBenifits=1;
												//listArrayHashmapProduct.add(hmapFreeProdID);

											}
											else
											{
												ArrayList<String> noAlrtStringSchemeIdWthAllValTemp=new ArrayList<String>();

												String noAlrtsubValues=String.valueOf(calculatedBenifitAssignedValueSKULevel+"~"+schId+"~"+schSlabId+"~"+schSlbBuckId+"~"+schSlabSubBucketValue+"~"+0+"~"+schSlabSubBucketType+"~"+BenifitRowID+"~"+
														BenSubBucketType+"~"+0+"~"+BenSubBucketValue+"~"+0+"~"+0+"~"+
														0+"~"+0+"~"+0+"~"+0.0+"~"+0.0+"~"+schSlbSubRowID+"~"+SchTypeId);

												noAlrtStringSchemeIdWthAllValTemp.add(noAlrtsubValues);


												String[] arrayProductId=changeHmapToArrayValue(arrProductIDMappedInSchSlbSubBukBenifits);
												noAlrtHshMaptoSaveData.put(arrPurchasedProductListVolumeAndValue[cntPurchasedProductList], noAlrtStringSchemeIdWthAllValTemp);
											}


											//listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
										}
										if(isHaveMoreBenifits==1)
										{
											if(disValClkdOpenAlert)
											{

												listArrayFreePrdctQty.add(strBeniftRowIdExistsInSchemeSlabBenefitsValueDetail);
												listArrayHashmapProduct.add(hmapFreeProdIDAlrt);
											}
											else
											{

												listArrayFreePrdctQty.add(arrBenifitAssignedVal);
												listArrayHashmapProduct.add(hmapFreeProdID);
											}
										}
									}
								}

								//Now get the Free Product EditTextBox ID and  gets its current value
								//Minus the AppliedQty from the present value
							}// if(BensubBucketType==10) ends here
						}//for(String strProductIDBenifitsListOnPurchase:arrProductIDBenifitsListOnPurchase ) ends here

					}//arrProductIDBenifitsListOnPurchase length condition ends here

				}//hmapSchemeIdStoreID.containsKey(""+schId) ends here
			}// for loops ends here for arredtboc_OderQuantityFinalSchemesToApply

			if(noAlrtHshMaptoSaveData.size()>0)
			{
				boolean flagMappedToProduct=false; // if noAlrtHshMaptoSaveData doenot contains arrProductIDMappedInSchSlbSubBukRowId
				if(productFullFilledSlabGlobal!=null && productFullFilledSlabGlobal.size()>0)
				{

					for(int cntProdcutsRowIdCnt=0;cntProdcutsRowIdCnt<productFullFilledSlabGlobal.size();cntProdcutsRowIdCnt++)
					{

						if(noAlrtHshMaptoSaveData.containsKey(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt)))
						{
							flagMappedToProduct=true;
							HashMap<String, ArrayList<String>> noAlrtHshMaptoSaveDataTemp=new HashMap<String, ArrayList<String>>();
							noAlrtHshMaptoSaveDataTemp.put(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt), noAlrtHshMaptoSaveData.get(productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt)));
							saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveDataTemp, productFullFilledSlabGlobal.get(cntProdcutsRowIdCnt));
						}
					}

               /*if(!flagMappedToProduct)
               {
                  saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
            }*/

				}

				else
				{
					saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
				}
			}
			//saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
			if(listArrayHashmapProduct.size()>0)
			{
				//saveFreeProductDataWithSchemeToDatabase(noAlrtHshMaptoSaveData, ProductIdOnClicked);
				if(disValClkdOpenAlert)
				{
					disValClkdOpenAlert=false;

					customAlert(listArrayHashmapProduct, listArrayFreePrdctQty,alrtObjectTypeFlag , stringSchemeIdWthAllVal,ProductIdOnClicked);
				}
				else
				{

					String[] arrayProductIdToDefault=changeHmapToArrayValue(listArrayHashmapProduct.get(0));

					for(int abc=0;abc<arrayProductIdToDefault.length;abc++)
					{
						ArrayList<String> arrayListSaveAssigndVal=new ArrayList<String>();
						HashMap<String, ArrayList<String>> alerValWithDefault=new HashMap<String, ArrayList<String>>();


						String defaultVal=(listArrayFreePrdctQty.get(0))[abc].toString();

						String defaultValWithDefltAssigndVal=defaultVal+"~"+stringSchemeIdWthAllVal.get(0).toString();
						String spinnerValSelected= dbengine.getValOfSchemeAlrtSelected(storeID,(stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[1],(stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[0],strGlobalOrderID,strGlobalInvoiceNumber);
						String[] spinnerPositionSelected=dbengine.getValOfSchemeAlrt(storeID,ProductIdOnClicked,""+(stringSchemeIdWthAllVal.get(0)).split(Pattern.quote("~"))[1],strGlobalOrderID,strGlobalInvoiceNumber);


						arrayListSaveAssigndVal.add(defaultValWithDefltAssigndVal);
						alerValWithDefault.put(arrayProductIdToDefault[abc], arrayListSaveAssigndVal);
						if(defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("10") ||defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("7") || defaultValWithDefltAssigndVal.split(Pattern.quote("~"))[8].equals("6"))
						{
							saveFreeProductDataWithSchemeToDatabase(alerValWithDefault,arrayProductIdToDefault[abc]);
							// exception sighn
							final ImageView btnExcptnAlrt=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+arrayProductIdToDefault[abc]);
							EditText edOrderText=(EditText)ll_prdct_detal.findViewWithTag("etOrderQty_"+arrayProductIdToDefault[abc]);
							if(edOrderText.getText().equals("") || TextUtils.isEmpty(edOrderText.getText().toString()))
							{
								btnExcptnAlrt.setVisibility(View.INVISIBLE);
							}
							else
							{
								btnExcptnAlrt.setVisibility(View.VISIBLE);
							}



							btnExcptnAlrt.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {

									disValClkdOpenAlert=true;
									getOrderData(((btnExcptnAlrt.getTag().toString()).split(Pattern.quote("_")))[1]);

									// orderBookingTotalCalc();

								}
							});


						}
						else
						{
							saveFreeProductDataWithSchemeToDatabase(alerValWithDefault,ProductIdOnClicked);


							final ImageView btnExcptnAlrt=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+ProductIdOnClicked);
							EditText edOrderText=(EditText)ll_prdct_detal.findViewWithTag("etOrderQty_"+ProductIdOnClicked);
							if(edOrderText.getText().equals("") || TextUtils.isEmpty(edOrderText.getText().toString()))
							{
								if(btnExcptnAlrt.getVisibility()==View.VISIBLE)
								{
									btnExcptnAlrt.setVisibility(View.INVISIBLE);
								}

							}
							else
							{

								btnExcptnAlrt.setVisibility(View.VISIBLE);
								btnExcptnAlrt.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										disValClkdOpenAlert=true;
										getOrderData(((btnExcptnAlrt.getTag().toString()).split(Pattern.quote("_")))[1]);

										// orderBookingTotalCalc();

									}
								});
							}


							for(int i=0;i<productFullFilledSlabGlobal.size();i++)
							{
								if(Integer.parseInt( hmapPrdctOdrQty.get(productFullFilledSlabGlobal.get(i)))>0)
								{
									final ImageView btnExcptnAlrtTemp=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+productFullFilledSlabGlobal.get(i));
									EditText edOrderTextTemp=(EditText)ll_prdct_detal.findViewWithTag("etOrderQty_"+productFullFilledSlabGlobal.get(i));

									if(btnExcptnAlrtTemp.getVisibility()==View.INVISIBLE)
									{
										btnExcptnAlrtTemp.setVisibility(View.VISIBLE);
										btnExcptnAlrtTemp.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {

												disValClkdOpenAlert=true;
												getOrderData(((btnExcptnAlrtTemp.getTag().toString()).split(Pattern.quote("_")))[1]);

												// orderBookingTotalCalc();

											}
										});
									}








								}

							}



						}


					}


					// }




				}
			}

		}// ends here arredtboc_OderQuantityFinalSchemesToApply length check
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
			final Dialog dialog = new Dialog(ProductOrderFilterSearch.this);

			//setting custom layout to dialog
			dialog.setContentView(R.layout.custom_alert);
			dialog.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.FreeOffer));

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
						titleAlrtVal=ProductOrderFilterSearch.this.getResources().getString(R.string.DefaultFreeQty)+arraySpnrVal[0];

						alrtbodyToShow=ProductOrderFilterSearch.this.getResources().getString(R.string.SelectActualFreeQty);

					}

				else if(alrtBenSubBucketType==3 || alrtBenSubBucketType==7)
					{
						benSubBucketType7or10=true;


						titleAlrtVal=ProductOrderFilterSearch.this.getResources().getString(R.string.DefaultDiscValue)+arraySpnrVal[0];

						alrtbodyToShow=ProductOrderFilterSearch.this.getResources().getString(R.string.SelectActualValue);

					}

				else if(alrtBenSubBucketType==2 || alrtBenSubBucketType==6)
				{
					benSubBucketType7or10=true;


					titleAlrtVal=ProductOrderFilterSearch.this.getResources().getString(R.string.DefaultDisc)+arraySpnrVal[0];

					alrtbodyToShow=ProductOrderFilterSearch.this.getResources().getString(R.string.SelectActualDisc);

				}
				else if(alrtBenSubBucketType==10)
				{



						benSubBucketType7or10=true;
						titleAlrtVal=ProductOrderFilterSearch.this.getResources().getString(R.string.DefaultFreeVal)+arraySpnrVal[0];

						alrtbodyToShow=ProductOrderFilterSearch.this.getResources().getString(R.string.SelectActualValue);

				}
				}

				else
				{
					benSubBucketType7or10=true;
					titleAlrtVal=ProductOrderFilterSearch.this.getResources().getString(R.string.DefaultVal)+arraySpnrVal[0];

					alrtbodyToShow=ProductOrderFilterSearch.this.getResources().getString(R.string.SelectActualValue);
				}



				txtVw_scheme.setTag(i);
				boolean ifChckdRadio=false;
				 final HashMap<String, String> hmapProductNameId=arrLstHmap.get(i);

				 txtVw_scheme.setText(titleAlrtVal);
				String hMapScheme="hMapScheme";
				if(benSubBucketType7or10)
				{




					String[] productGivenDiscount=dbengine.getValOfSchemeAlrt(storeID,alrtProductIdOnClicked,schemAllString.split(Pattern.quote("~"))[1].toString(),strGlobalOrderID,strGlobalInvoiceNumber);

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

										dbengine.insertSchemeAlrtVal(storeID, entry.getValue(),strngPrdctCompltValue , tv_prdct_name.getText().toString(),String.valueOf(adapterProduct.getPosition(strngPrdctCompltValue)),schemAllStringForBen7or10.split(Pattern.quote("~"))[1].toString(),schemAllStringForBen7or10.split(Pattern.quote("~"))[0].toString(),strGlobalOrderID,strGlobalInvoiceNumber);


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
					int i;


			/*		else
					{
						for(i=0;i<arrayProduct.length;i++)
						{

							if(arrayProduct[i]!=null)
							{
							 ArrayList<String> arrayList=new ArrayList<String>();
								for(int k=0;k<arrayStringSpinner.length;k++)
								{
									if(i!=k)
									{
										if(arrayProduct[i].equals(arrayProduct[k]))
										{
											if(i<k)
											{
												arrayList.add(arrayStringSpinner[i]+"~"+(listSchemeAllData.get(i)).toString());
											}
											else
											{
												ArrayList<String> listArray=hashMapSelectionFreeQty.get(arrayProduct[k]);
												//System.out.println("Raj Abhinav ="+arrayProduct[
		//]+" index i="+i+"index k="+k);


												arrayList.add((listArray.get(k)).toString()+"~"+(listSchemeAllData.get(i)).toString());
												//sdsd
											}



										}
									}
									else
									{
										arrayList.add(arrayStringSpinner[i]+"~"+(listSchemeAllData.get(i)).toString());
									}


									hashMapSelectionFreeQty.put(arrayProduct[i], arrayList);
								}


								System.out.println("Raj Abhinav ="+hashMapSelectionFreeQty);
								condition=true;
							}
							else
							{
								condition=false;
								break;
							}
						}
					}*/



						 getOrderData(alrtProductIdOnClicked);
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
		 final View row123 = (View)inflater.inflate(R.layout.activity_detail_productfilter, ll_scheme_detail , false);

			decimalFormat.applyPattern(pattern);
			//tvCurrentProdName = (TextView) findViewById(R.id.textView1_schemeVAL1111);
			tvCredAmtVAL =  (TextView) row123.findViewById(R.id.textView1_CredAmtVAL);
			tvINafterCredVAL =  (TextView) row123.findViewById(R.id.textView1_INafterCredVAL);
			textView1_CredAmtVAL_new = (TextView) row123.findViewById(R.id.textView1_CredAmtVAL_new);


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
		tvAfterTaxValue=(TextView)row123.findViewById(R.id.tvAfterTaxValue);
		 ll_scheme_detail.addView(row123);
		Double outstandingvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
		tvPreAmtOutstandingVALNew.setText(""+outstandingvalue);
		 orderBookingTotalCalc();

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
		tvRateHeading.setText("Rate-S");
		 for (int index=0; index < prdListCount; index++){
			 View vRow = ll_prdct_detal.getChildAt(index);

		       int PCateIdDetails=Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
		       String ProductID=((TextView)(vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];
			 ((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapProductStandardRate.get(ProductID).toString());

		       if(hmapPrdctOdrQty.containsKey(ProductID))
				{
					flgWholeSellApplicable=Integer.parseInt(hmapflgWholeSellApplicable.get(ProductID));
					PriceRangeWholeSellApplicable=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID));

					((TextView)(vRow).findViewById(R.id.tv_FreeQty)).setText(hmapPrdctFreeQty.get(ProductID).toString());
					TotalFreeQTY=TotalFreeQTY+Integer.parseInt(hmapPrdctFreeQty.get(ProductID));
					hmapProductTaxValue.put(ProductID, "0.00");
					hmapMinDlvrQtyQPTaxAmount.put(ProductID, "0.00");
					((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText("0.00");
					if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>0)
					{
						/*StandardRate=Double.parseDouble(hmapProductMRP.get(ProductID))/((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);*/
						//((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapProductStandardRate.get(ProductID).toString());
						if(PriceApplyDiscountLevelType!=0)
						{


							Double ProductOrderValueBeforeWholeSaleBeforeTax=Double.parseDouble(hmapProductStandardRateBeforeTax.get(ProductID))*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
							ProductOrderValueBeforeWholeSaleBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ProductOrderValueBeforeWholeSaleBeforeTax));
							//hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.put(ProductID,""+ProductOrderValueBeforeWholeSaleBeforeTax);
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



						PrdMaxValuePercentageDiscount=dbengine.fnctnGetHighestDiscountPercentge(ProductID, storeID,strGlobalInvoiceNumber);
						PrdMaxValueFlatDiscount=dbengine.fnctnGetHighestDiscountAmount(ProductID, storeID,strGlobalInvoiceNumber);
						int BenifitRowIdPercentageDiscount=0;
						int BenifitRowIdFlatDiscount=0;
						//Double per=Double.parseDouble(hmapProductVolumePer.get(ProductID));

						Double per;
						String perProduct=dbengine.fnctnGetfreePerUnitVol(ProductID, storeID,strGlobalInvoiceNumber);
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
							if(PriceApplyDiscountLevelType!=0)
							{


								hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.put(ProductID,""+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID))));
							}
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

		 String percentBenifitMax=dbengine.fnctnGetMaxAssignedBen8DscntApld1(storeID,strGlobalOrderID,strGlobalInvoiceNumber);
		 Double percentMax=0.00;
		 Double percentMaxGross=0.0;
		 Double amountMaxGross=0.0;

		 String amountBenfitMaxGross=dbengine.fnctnGetMaxAssignedBen9DscntApld2(storeID,strGlobalOrderID,strGlobalInvoiceNumber);
		 String percentBenifitMaxGross=dbengine.fnctnGetMaxAssignedBen8DscntApld2(storeID,strGlobalOrderID,strGlobalInvoiceNumber);

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

		 String amountBenifitMax=dbengine.fnctnGetMaxAssignedBen9DscntApld1(storeID,strGlobalOrderID,strGlobalInvoiceNumber);
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
		   dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(amountBenfitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID,strGlobalInvoiceNumber);
		  }
		  else if(totalGrossVALAfterDiscount==totalGrossVALMaxPercentage && percentMaxGross!=0.0)
		  {
		   dbengine.updatewhatAppliedFlag(1, storeID, Integer.parseInt(percentBenifitMaxGross.split(Pattern.quote("^"))[1]),strGlobalOrderID,strGlobalInvoiceNumber);
		  }

		Double GrossInvValue=totalGrossVALAfterDiscount + TotTaxAmount;
		GrossInvValue=Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
		tv_GrossInvVal.setText(""+GrossInvValue);
		tvAfterTaxValue.setText(""+GrossInvValue);

		Double CollectionAmt=0.00;//dbengine.fnTotCollectionAmtAgainstStore(storeID,strGlobalInvoiceNumber);
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

		//dbengine.updateVisitTypeStatusOfStore(storeID,VisitTypeStatus,StoreVisitCode);

		if(PriceApplyDiscountLevelType!=0) {
			if(PriceApplyDiscountLevelType==1) {// Invoice Level Whole Sell Price Applicable but Not At Invoice Level
				//if (flgWholeSellApplicable != 0) {
					if (GrossInvValue >= cutoffvalue) {
						tvRateHeading.setText("Rate-W");
						flgAllTotalOrderValueCrossesCutOff=true;
						CalculateInvoiceLevelOrProductLevelWholeSaleValue(PriceApplyDiscountLevelType);
					}
				//}
			}
			if(PriceApplyDiscountLevelType==2) {//Product Level Whole Sell Price Applicable but Not At Invoice Level
				CalculateInvoiceLevelOrProductLevelWholeSaleValue(PriceApplyDiscountLevelType);
			}
		}
	//Now the its Time to Show the OverAll Summary Code Starts Here
	}

	public void CalculateInvoiceLevelOrProductLevelWholeSaleValue(int flgPriceApplyDiscountLevelType)
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

		for (int index=0; index < prdListCount; index++){
			View vRow = ll_prdct_detal.getChildAt(index);

			int PCateIdDetails=Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
			String ProductID=((TextView)(vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];
			((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
if(hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.containsKey(ProductID))
{
	if(hmapPrdctOdrQty.containsKey(ProductID))
	{
		flgWholeSellApplicable=Integer.parseInt(hmapflgWholeSellApplicable.get(ProductID));
		PriceRangeWholeSellApplicable=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID));
		hmapPrdctFreeQty.put(ProductID,"0");
		((TextView)(vRow).findViewById(R.id.tv_FreeQty)).setText(hmapPrdctFreeQty.get(ProductID).toString());
		TotalFreeQTY=TotalFreeQTY+Integer.parseInt(hmapPrdctFreeQty.get(ProductID));
		hmapProductTaxValue.put(ProductID, "0.00");
		hmapMinDlvrQtyQPTaxAmount.put(ProductID, "0.00");
		((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText("0.00");
		if(Integer.parseInt(hmapPrdctOdrQty.get(ProductID))>0)
		{
						/*StandardRate=Double.parseDouble(hmapProductMRP.get(ProductID))/((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
						StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
						StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);*/
						if(flgPriceApplyDiscountLevelType==1)
						{
							if(Integer.parseInt(hmapProductflgPriceAva.get(ProductID))>0)
							{
								StandardRate=Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
								StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
								StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
							//	((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
							}
							else
							{
								StandardRate=Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));
								StandardRateBeforeTax=Double.parseDouble(hmapStandardRateBeforeTaxWholeSell.get(ProductID));
								StandardTax=Double.parseDouble(hmapStandardTaxWholeSale.get(ProductID));
								//((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
							}

						}
						if(flgPriceApplyDiscountLevelType==2)
						{
							if(hmapflgWholeSellApplicable.containsKey(ProductID))
							{
								if(Double.parseDouble(hmapProductListOnWhichWholePriceNeedsToApplyIfRequired.get(ProductID))>=Double.parseDouble(hmapPriceRangeWholeSellApplicable.get(ProductID)))
								{
									if(Integer.parseInt(hmapProductflgPriceAva.get(ProductID))>0)
									{
										StandardRate=Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));///((1+(Double.parseDouble(hmapProductRetailerMarginPercentage.get(ProductID))/100)));
										StandardRateBeforeTax=StandardRate/(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));
										StandardTax=StandardRateBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
										//((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());
									}
									else
									{
										StandardRate=Double.parseDouble(hmapStandardRateWholeSale.get(ProductID));
										StandardRateBeforeTax=Double.parseDouble(hmapStandardRateBeforeTaxWholeSell.get(ProductID));
										StandardTax=Double.parseDouble(hmapStandardTaxWholeSale.get(ProductID));
									//	((TextView)(vRow).findViewById(R.id.txtVwRate)).setText(""+hmapStandardRateWholeSale.get(ProductID).toString());

									}
								}
								else
								{
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
							else {
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

			String value=hmapPrdctVolRatTax.get(ProductID).toString();
			StringTokenizer tokens=new StringTokenizer(value,Pattern.quote("^"));
			//Volume^Rate^TaxAmount
			String prdVolume = tokens.nextElement().toString();

			StandardRate=Double.parseDouble(new DecimalFormat("##.##").format(StandardRate));



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
				ActualRateAfterDiscountBeforeTax=StandardRateBeforeTax;
				DiscountAmount=0.00;
				ActualTax=ActualRateAfterDiscountBeforeTax*(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100);
				ActualRateAfterDiscountAfterTax=ActualRateAfterDiscountBeforeTax*(1+(Double.parseDouble(hmapProductVatTaxPerventage.get(ProductID))/100));

				Double DiscAmtOnPreQtyBasic=DiscountAmount*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));

				Double DiscAmtOnPreQtyBasicToDisplay=0.00;//DiscAmtOnPreQtyBasic;
				DiscAmtOnPreQtyBasicToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(DiscAmtOnPreQtyBasicToDisplay));
				((TextView)(vRow).findViewById(R.id.tv_DisVal)).setText(""+DiscAmtOnPreQtyBasicToDisplay);
				hmapPrdctIdPrdctDscnt.put(ProductID,""+DiscAmtOnPreQtyBasicToDisplay);

				TotalProductLevelDiscount=TotalProductLevelDiscount+DiscAmtOnPreQtyBasic;
				TotTaxAmount=TotTaxAmount+(ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));

				Double TaxValue=ActualTax * Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
				TaxValue=Double.parseDouble(new DecimalFormat("##.##").format(TaxValue));
				hmapProductTaxValue.put(ProductID, ""+TaxValue);
				/*if(hmapMinDlvrQtyQPTaxAmount.containsKey(ProductID))
				{
					hmapMinDlvrQtyQPTaxAmount.put(ProductID, ""+TaxValue);
				}*/
				Double OrderValPrdQtyBasis=ActualRateAfterDiscountAfterTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID));
				Double OrderValPrdQtyBasisToDisplay=OrderValPrdQtyBasis;
				OrderValPrdQtyBasisToDisplay=Double.parseDouble(new DecimalFormat("##.##").format(OrderValPrdQtyBasisToDisplay));
				((TextView)(vRow).findViewById(R.id.tv_Orderval)).setText(""+OrderValPrdQtyBasisToDisplay);
				hmapProductIdOrdrVal.put(ProductID, ""+OrderValPrdQtyBasis);
				TotalOrderValBeforeTax=TotalOrderValBeforeTax+(ActualRateAfterDiscountBeforeTax*Double.parseDouble(hmapPrdctOdrQty.get(ProductID)));
				TotOderValueAfterTax=TotOderValueAfterTax+OrderValPrdQtyBasis;
				//If No Percentage Discount or Flat Discount is Applicable Code Ends Here
			//}

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
		tvAddDisc.setText(""+ "0.00");

		tv_NetInvAfterDiscount.setText(""+ TotalOrderValBeforeTax);

		TotTaxAmount=Double.parseDouble(new DecimalFormat("##.##").format(TotTaxAmount));
		tvTAmt.setText(""+ TotTaxAmount);
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

		Double GrossInvValue=totalGrossVALAfterDiscount + TotTaxAmount;
		GrossInvValue=Double.parseDouble(new DecimalFormat("##.##").format(GrossInvValue));
		tv_GrossInvVal.setText(""+GrossInvValue);
		tvAfterTaxValue.setText(""+GrossInvValue);
		//Now the its Time to Show the OverAll Summary Code Starts Here
	}

	public void fnUpdateSchemeNameOnScehmeControl(String ProductIdOnClickedControl)
	{
		String SchemeNamesApplies="No Scheme Applicable";
		String scIds="0";
		if(hmapProductRelatedSchemesList.containsKey(ProductIdOnClickedControl))
		{
			String SchemeOnProduct=hmapProductRelatedSchemesList.get(ProductIdOnClickedControl).toString();
			 String[] arrSchIdsListOnProductID=SchemeOnProduct.split("#");


			 for(int pSchIdsAppliCount=0;pSchIdsAppliCount<arrSchIdsListOnProductID.length;pSchIdsAppliCount++)
			 {
			  String schOverviewDetails=arrSchIdsListOnProductID[pSchIdsAppliCount].split(Pattern.quote("!"))[0];
			  String schId=schOverviewDetails.split(Pattern.quote("_"))[0];
			  if(hmapSchemeIdStoreID.containsKey(schId))
			  {
			   if(SchemeNamesApplies.equals("No Scheme Applicable"))
			   {
			    SchemeNamesApplies=hmapSchemeIDandDescr.get(schId);
			    scIds=schId;
			   }
			   else
			   {
			    SchemeNamesApplies=SchemeNamesApplies+" , "+hmapSchemeIDandDescr.get(schId);
			    scIds=scIds+"^"+schId;
			   }
			  }
			 /* else
			  {
			   SchemeNamesApplies="Not Applicable Here";
			   scIds="0";
			  }*/
			 }
		}


	 txtVw_schemeApld.setTextColor(Color.parseColor("#3f51b5"));
	 SpannableString content = new SpannableString(SchemeNamesApplies);
	 content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
	 txtVw_schemeApld.setText(SchemeNamesApplies);
	 txtVw_schemeApld.setTag(scIds);

	}



	/*public void fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(String CompleteSchemeIdListOnProductID,String ProductIdOnClicked)
	{

	//53_1_0_1!95$1^1|1^25^5^75^0@94$1^1|1^24^5^50^0@93$1^1|1^23^5^35^0@92$1^1|1^22^5^20^0@91$1^1|1^21^5^15^0@90$1^1|1^20^5^10^0@89$1^1|1^19^5^1^0
		String schIdforBen10=(CompleteSchemeIdListOnProductID.split(Pattern.quote("_")))[0].toString();
		String schmTypeId=(CompleteSchemeIdListOnProductID.split(Pattern.quote("_")))[1].toString();

			String[] arrProductRelatedToProject=dbengine.fnGetDistinctProductIdAgainstStoreProduct(storeID,schIdforBen10);
			if(arrProductRelatedToProject.length>0)
			{

				for(int i=0;i<arrProductRelatedToProject.length;i++)
				{
					if(arrProductRelatedToProject[i]!=null)
					{

					//BenSubBucketType,FreeProductID,BenifitAssignedValue,BenifitDiscountApplied,BenifitCouponCode

					int BenSubBucketType=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[0]);
					int FreeProductID=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[1]);
					Double BenifitAssignedValue=Double.parseDouble(""+arrProductRelatedToProject[i].split(Pattern.quote("^"))[2]);//Actually Given Values
					Double BenifitDiscountApplied=Double.parseDouble(""+arrProductRelatedToProject[i].split(Pattern.quote("^"))[3]);//BenifitAssignedValueType on Net Order or Invoice
					String BenifitCouponCode=arrProductRelatedToProject[i].split(Pattern.quote("^"))[4];
					String CurrentSchemeIDOnProduct=arrProductRelatedToProject[i].split(Pattern.quote("^"))[5];
					int schSlbRowId=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[6]);
					int SchTypeId=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[7]);

					String[] AllProductInSchSlab=dbengine.fnGetProductsSchIdSlabRow(storeID,schSlbRowId);


					if(AllProductInSchSlab.length>0)
						{



							if(BenSubBucketType==10)
							{
								for(int mm=0;mm<AllProductInSchSlab.length;mm++)
								{
								//Get the Object of Free Quantity TextBox of FreeProductID
								//Get the value inside the TextBox of FreeProductID
								//TextBox of  FreeProductID=TextBox of FreeProductID-BenifitAssignedValue

								//hmapPrdctFreeQty.put(""+FreeProductID, ""+(Integer.parseInt(hmapPrdctFreeQty.get(""+FreeProductID).toString())-BenifitAssignedValue.intValue()));
								//((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).setText(""+(Integer.parseInt(((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).getText().toString())-BenifitAssignedValue));
								}
								//dbengine.fnDeleteOldSchemeRowIdRecords(schSlbRowId);

								String[] bensubBucket10Product=dbengine.fnctnGetBensubBucket10Column(CurrentSchemeIDOnProduct,storeID);
								if(bensubBucket10Product.length>0)
								{
									for(int index=0;index<bensubBucket10Product.length;index++)
									{hmapPrdctFreeQty.put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0");
							         hmapPrdctFreeQty.put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[1], "0");
							         //hmapPrdctFreeQty.put(bensubBucket10Product[index], "0");
							         hmapProductVolumePer.put(""+((bensubBucket10Product[index]).split(Pattern.quote("^")))[0],"0.00");

							         hmapPrdctIdPrdctDscnt.put(""+((bensubBucket10Product[index]).split(Pattern.quote("^")))[0],"0.00");
							         ((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+((bensubBucket10Product[index]).split(Pattern.quote("^")))[0])).setText("0.00");
							         if(Integer.parseInt(((bensubBucket10Product[index]).split(Pattern.quote("^")))[1].toString())!=0)
							         {
							          ((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+((bensubBucket10Product[index]).split(Pattern.quote("^")))[1].toString())).setText("0");
							         }
							         ((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+((bensubBucket10Product[index]).split(Pattern.quote("^"))[0]))).setText("0");
							         dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,((bensubBucket10Product[index]).split(Pattern.quote("^")))[1]);}


								}


							}
						}







				}
					}



			}



		else
		{
				String[] arrSchmesRelatedToProject=dbengine.fnGetDistinctSchIdsAgainstStoreProduct(storeID,ProductIdOnClicked,Integer.parseInt(schIdforBen10));




			if(arrSchmesRelatedToProject.length>0)
			{

				for(int i=0;i<arrSchmesRelatedToProject.length;i++)
				{
					if(arrSchmesRelatedToProject[i]!=null)
					{

					//BenSubBucketType,FreeProductID,BenifitAssignedValue,BenifitDiscountApplied,BenifitCouponCode

					int BenSubBucketType=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[0]);
					int FreeProductID=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[1]);
					Double BenifitAssignedValue=Double.parseDouble(""+arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[2]);//Actually Given Values
					Double BenifitDiscountApplied=Double.parseDouble(""+arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[3]);//BenifitAssignedValueType on Net Order or Invoice
					String BenifitCouponCode=arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[4];
					String CurrentSchemeIDOnProduct=arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[5];
					int schSlbRowId=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[6]);
					int SchTypeId=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[7]);
					int SchProdID=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[8]);
					String[] AllProductInSchSlab=dbengine.fnGetProductsSchIdSlabRow(storeID,schSlbRowId);

					if(SchTypeId==3)
					{
						hmapPrdctIdPrdctDscnt.put(""+ProductIdOnClicked,"0.00");
						((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+ProductIdOnClicked)).setText("0.00");

						//if(BenSubBucketType==1 || BenSubBucketType==5)
					if(BenSubBucketType==1 || BenSubBucketType==5 ||  BenSubBucketType==6 ||  BenSubBucketType==2)
						{

							//Get the Object of Free Quantity TextBox of FreeProductID
							//Get the value inside the TextBox of FreeProductID
							//TextBox of  FreeProductID=TextBox of FreeProductID-BenifitAssignedValue
							 if(Integer.parseInt(ProductIdOnClicked)==SchProdID)
							{
								if(Integer.parseInt(hmapPrdctFreeQty.get(""+SchProdID).toString())>0)
								{

									hmapPrdctFreeQty.put(""+ProductIdOnClicked, "0");


								}

							}
							dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,""+ProductIdOnClicked);
							//((TextView)ll_prdct_detal.findV	iewWithTag("tvFreeQty_"+FreeProductID)).setText(""+(Integer.parseInt(((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).getText().toString())-BenifitAssignedValue));

						}



					}
					else
					{
						if(AllProductInSchSlab.length>0)
						{
							if(BenSubBucketType==2 || BenSubBucketType==6)
							{
								for(int mm=0;mm<AllProductInSchSlab.length;mm++)
								{
									//hmapProductVolumePer.put(""+AllProductInSchSlab[mm],"0.00");
									hmapPrdctIdPrdctDscnt.put(""+AllProductInSchSlab[mm],"0.00");
									//((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+AllProductInSchSlab[mm])).setText("0.00");

									((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+AllProductInSchSlab[mm])).setText("0.00");


								}
								dbengine.fnDeleteOldSchemeRowIdRecords(storeID,schSlbRowId);
							}

							if(BenSubBucketType==1 || BenSubBucketType==5 )
							{

									for(int mm=0;mm<AllProductInSchSlab.length;mm++)
									{
									//Get the Object of Free Quantity TextBox of FreeProductID
									//Get the value inside the TextBox of FreeProductID
									//TextBox of  FreeProductID=TextBox of FreeProductID-BenifitAssignedValue

										hmapProductVolumePer.put(""+AllProductInSchSlab[mm],"0.00");
										hmapPrdctIdPrdctDscnt.put(""+AllProductInSchSlab[mm],"0.00");
										((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+AllProductInSchSlab[mm])).setText("0.00");
										if(BenSubBucketType==5)
										{
										hmapProductVolumePer.put(""+ProductIdOnClicked,"0.00");
										hmapPrdctIdPrdctDscnt.put(""+ProductIdOnClicked,"0.00");
										((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+ProductIdOnClicked)).setText("0.00");
										}

										if(Integer.parseInt(hmapPrdctFreeQty.get(""+FreeProductID).toString())>0)
										{
											//hmapPrdctFreeQty.put(""+FreeProductID, ""+(Integer.parseInt(hmapPrdctFreeQty.get(""+FreeProductID).toString())-BenifitAssignedValue.intValue()));
											if(BenifitAssignedValue.intValue()>0)
											{
												hmapPrdctFreeQty.put(""+FreeProductID, ""+(Integer.parseInt(hmapPrdctFreeQty.get(""+FreeProductID).toString())-BenifitAssignedValue.intValue()));
											}

										}
										((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).setText(hmapPrdctFreeQty.get(""+FreeProductID).toString());
									//((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).setText(""+(Integer.parseInt(((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).getText().toString())-BenifitAssignedValue));
										dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,AllProductInSchSlab[mm]);
									}
									//dbengine.fnDeleteOldSchemeRowIdRecords(schSlbRowId);



							}

						}
						else
						{}

					}




					//BenSubBucketType
					//1. Free Other Product
					//2. Discount in Percentage with other product
					//3. Discount in Amount with other product
					//4. Coupons
					//5. Free Same Product
					//6. Discount in Percentage with same product
					//7. Discount in Amount with same product
					//8. Percentage On Invoice
					//9.  Amount On Invoice


				}
					}



			}
			else
			{

				//String[] arrChkAllSlabRowIdAgainstSchemeAppliedOverProduct=dbengine.fnGetAllSlabRowIdAgainstSchemeAppliedOverProduct();
			}
		}


		fnCheckNewSchemeIDsAppliedAfterValueChange(CompleteSchemeIdListOnProductID,ProductIdOnClicked);

	}*/

	public void fnDeletePreviousEntriesSchemeIDsAppliedOverProductAfterValueChange(String CompleteSchemeIdListOnProductID,String ProductIdOnClicked)
	{

		//53_1_0_1!95$1^1|1^25^5^75^0@94$1^1|1^24^5^50^0@93$1^1|1^23^5^35^0@92$1^1|1^22^5^20^0@91$1^1|1^21^5^15^0@90$1^1|1^20^5^10^0@89$1^1|1^19^5^1^0#
			String[] werer=CompleteSchemeIdListOnProductID.split(Pattern.quote("#"));
			for(int pos=0;pos<werer.length;pos++)
			{

			String schIdforBen10=(CompleteSchemeIdListOnProductID.split(Pattern.quote("#"))[0].split(Pattern.quote("_")))[0].toString();
			String schmTypeId=(CompleteSchemeIdListOnProductID.split(Pattern.quote("#"))[0].split(Pattern.quote("_")))[1].toString();

				String[] arrProductRelatedToProject=dbengine.fnGetDistinctProductIdAgainstStoreProduct(storeID,schIdforBen10,strGlobalOrderID,strGlobalInvoiceNumber);
				if(arrProductRelatedToProject.length>0)
				{

					for(int i=0;i<arrProductRelatedToProject.length;i++)
					{
						if(arrProductRelatedToProject[i]!=null)
						{

						//BenSubBucketType,FreeProductID,BenifitAssignedValue,BenifitDiscountApplied,BenifitCouponCode

						int BenSubBucketType=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[0]);
						int FreeProductID=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[1]);
						Double BenifitAssignedValue=Double.parseDouble(""+arrProductRelatedToProject[i].split(Pattern.quote("^"))[2]);//Actually Given Values
						Double BenifitDiscountApplied=Double.parseDouble(""+arrProductRelatedToProject[i].split(Pattern.quote("^"))[3]);//BenifitAssignedValueType on Net Order or Invoice
						String BenifitCouponCode=arrProductRelatedToProject[i].split(Pattern.quote("^"))[4];
						String CurrentSchemeIDOnProduct=arrProductRelatedToProject[i].split(Pattern.quote("^"))[5];
						int schSlbRowId=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[6]);
						int SchTypeId=Integer.parseInt(arrProductRelatedToProject[i].split(Pattern.quote("^"))[7]);

						String[] AllProductInSchSlab=dbengine.fnGetProductsSchIdSlabRow(storeID,schSlbRowId,strGlobalOrderID,strGlobalInvoiceNumber);


						if(AllProductInSchSlab.length>0)
							{

								if(BenSubBucketType==10 || BenSubBucketType==7)
								{
									/*for(int mm=0;mm<AllProductInSchSlab.length;mm++)
									{*/
									//Get the Object of Free Quantity TextBox of FreeProductID
									//Get the value inside the TextBox of FreeProductID
									//TextBox of  FreeProductID=TextBox of FreeProductID-BenifitAssignedValue

									//hmapPrdctFreeQty.put(""+FreeProductID, ""+(Integer.parseInt(hmapPrdctFreeQty.get(""+FreeProductID).toString())-BenifitAssignedValue.intValue()));
									//((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).setText(""+(Integer.parseInt(((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+FreeProductID)).getText().toString())-BenifitAssignedValue));
									/*}*/
									//dbengine.fnDeleteOldSchemeRowIdRecords(schSlbRowId);

									String[] bensubBucket10Product=dbengine.fnctnGetBensubBucket10Column(CurrentSchemeIDOnProduct,storeID,strGlobalOrderID,strGlobalInvoiceNumber);
									if(bensubBucket10Product.length>0)
									{
										for(int index=0;index<bensubBucket10Product.length;index++)
										{

											ImageView buttonException=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+(bensubBucket10Product[index]).split(Pattern.quote("^"))[0]);
					                        	{

					                        		if(buttonException.getVisibility()==View.VISIBLE)
					                        		{
					                        			buttonException.setVisibility(View.INVISIBLE);

					                        		}

					                        	}
					                        	if(SchTypeId==3)
					                        	{
					                        		 hmapPrdctFreeQty.put(ProductIdOnClicked, "0");
											         hmapPrdctFreeQty.put(ProductIdOnClicked, "0");
											         //hmapPrdctFreeQty.put(bensubBucket10Product[index], "0");
											         hmapProductVolumePer.put(""+ProductIdOnClicked,"0.00");

											         hmapPrdctIdPrdctDscnt.put(""+ProductIdOnClicked,"0.00");
											         ((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+ProductIdOnClicked)).setText("0.00");
											         if(Integer.parseInt(ProductIdOnClicked)!=0)
											         {
											          ((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+ProductIdOnClicked)).setText("0");
											         }
											         ((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+ProductIdOnClicked)).setText("0");

											         dbengine.deleteProductOldSlab215(storeID, schIdforBen10,strGlobalOrderID,strGlobalInvoiceNumber);
											         dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,ProductIdOnClicked,strGlobalOrderID,strGlobalInvoiceNumber);
					                        	}
					                        	else
					                        	{
					                        		 hmapPrdctFreeQty.put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[0], "0");
											         hmapPrdctFreeQty.put(((bensubBucket10Product[index]).split(Pattern.quote("^")))[1], "0");
											         //hmapPrdctFreeQty.put(bensubBucket10Product[index], "0");
											         hmapProductVolumePer.put(""+((bensubBucket10Product[index]).split(Pattern.quote("^")))[0],"0.00");

											         hmapPrdctIdPrdctDscnt.put(""+((bensubBucket10Product[index]).split(Pattern.quote("^")))[0],"0.00");
											         ((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+((bensubBucket10Product[index]).split(Pattern.quote("^")))[0])).setText("0.00");
											         if(Integer.parseInt(((bensubBucket10Product[index]).split(Pattern.quote("^")))[1].toString())!=0)
											         {
											          ((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+((bensubBucket10Product[index]).split(Pattern.quote("^")))[1].toString())).setText("0");
											         }
											         ((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+((bensubBucket10Product[index]).split(Pattern.quote("^"))[0]))).setText("0");

											         dbengine.deleteProductOldSlab215(storeID, schIdforBen10,strGlobalOrderID,strGlobalInvoiceNumber);
											         dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,((bensubBucket10Product[index]).split(Pattern.quote("^")))[1],strGlobalOrderID,strGlobalInvoiceNumber);
					                        	}


										}


									}


								}
							}







					}
						}



				}



			else
			{
					String arrSchmesRelatedToProject=dbengine.fnGetDistinctSchIdsAgainstStoreProduct(storeID,ProductIdOnClicked,Integer.parseInt(schIdforBen10));




				if(!TextUtils.isEmpty(arrSchmesRelatedToProject))
				{



						//BenSubBucketType,FreeProductID,BenifitAssignedValue,BenifitDiscountApplied,BenifitCouponCode


						//int FreeProductID=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[1]);
						//Double BenifitAssignedValue=Double.parseDouble(""+arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[2]);//Actually Given Values
						//Double BenifitDiscountApplied=Double.parseDouble(""+arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[3]);//BenifitAssignedValueType on Net Order or Invoice
						//String BenifitCouponCode=arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[4];
						//String CurrentSchemeIDOnProduct=arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[5];
						int schSlbRowId=Integer.parseInt(arrSchmesRelatedToProject.split(Pattern.quote("^"))[0]);
						int SchTypeId=Integer.parseInt(arrSchmesRelatedToProject.split(Pattern.quote("^"))[1]);
						//int SchProdID=Integer.parseInt(arrSchmesRelatedToProject[i].split(Pattern.quote("^"))[8]);
						ArrayList<String> AllProductInSchSlab=new ArrayList<String>();
						if(SchTypeId==1 || SchTypeId==2)
						{
							AllProductInSchSlab=dbengine.fnGetProductsSchIdSlabRowList(storeID,schSlbRowId,strGlobalOrderID,strGlobalInvoiceNumber);
						}
						else
						{
							AllProductInSchSlab.add(ProductIdOnClicked);
						}








						//BenSubBucketType
						//1. Free Other Product
						//2. Discount in Percentage with other product
						//3. Discount in Amount with other product
						//4. Coupons
						//5. Free Same Product
						//6. Discount in Percentage with same product
						//7. Discount in Amount with same product
						//8. Percentage On Invoice
						//9.  Amount On Invoice

							((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+ProductIdOnClicked)).setText("0.00");
							ImageView buttonException=(ImageView) ll_prdct_detal.findViewWithTag("btnException_"+ProductIdOnClicked);
			             	{

			             		if(buttonException.getVisibility()==View.VISIBLE)
			             		{
			             			buttonException.setVisibility(View.INVISIBLE);

			             		}

			             	}


			             		for(String prodctMpdWdSchm:AllProductInSchSlab)
			             		{
			             			String freePrdctId_benassVal=dbengine.fnGetBenifitAssignedValue(storeID, Integer.valueOf(prodctMpdWdSchm),Integer.parseInt(schIdforBen10),strGlobalInvoiceNumber );
			             			if(!TextUtils.isEmpty(freePrdctId_benassVal.trim()))
			             			{
			             				String freePrdctId=freePrdctId_benassVal.split(Pattern.quote("^"))[0];
				             			Double besnAssignVal=Double.valueOf(freePrdctId_benassVal.split(Pattern.quote("^"))[1]);
				             			int BenSubBucketType=Integer.parseInt(freePrdctId_benassVal.split(Pattern.quote("^"))[2]);
				             			if(BenSubBucketType==1 || BenSubBucketType==5)
				             			{
				             				hmapPrdctFreeQty.put(""+freePrdctId, ""+(Integer.valueOf(hmapPrdctFreeQty.get(freePrdctId))-Math.abs(besnAssignVal.intValue())));
											((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+freePrdctId)).setText(hmapPrdctFreeQty.get(""+freePrdctId).toString());
				             			}
				             			else if(BenSubBucketType==2 || BenSubBucketType==6)
				             			{
				             				hmapProductDiscountPercentageGive.put(""+freePrdctId, ""+(Double.valueOf(hmapProductDiscountPercentageGive.get(freePrdctId))-besnAssignVal));
											((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+freePrdctId)).setText("0.0");
				             			}
				             			else if(BenSubBucketType==3 || BenSubBucketType==7)
				             			{
				             				hmapPrdctIdPrdctDscnt.put(""+freePrdctId, ""+(Double.valueOf(hmapPrdctIdPrdctDscnt.get(freePrdctId))-besnAssignVal));
											((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+freePrdctId)).setText(hmapPrdctIdPrdctDscnt.get(""+freePrdctId).toString());
				             			}

				             			dbengine.fnDeleteRecordsAllRecordsForClickedProdoductId(storeID,prodctMpdWdSchm,strGlobalOrderID,strGlobalInvoiceNumber);
										dbengine.deleteProductSchemeType3(storeID, prodctMpdWdSchm,strGlobalOrderID,strGlobalInvoiceNumber);
			             			}

			             		}









				}
				else
				{


				}
			}
		}


			fnCheckNewSchemeIDsAppliedAfterValueChange(CompleteSchemeIdListOnProductID,ProductIdOnClicked);

	}

	public void fnSaveFilledDataToDatabase(int valBtnClickedFrom)
	{

	 //valBtnClickedFrom=Save/Save And Exit/Submit

		//Declare  Outstat=0;  // Outstat=1 (Save,SaveExit) , Outstat=3(Submit)



	 if(valBtnClickedFrom==3)//Clicked By Btn Submitt
	 {
	     //Send Data for Sync

		 // Changes By Sunil
		   AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductOrderFilterSearch.this);
			alertDialogSubmitConfirm.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
			alertDialogSubmitConfirm.setMessage(getText(R.string.submitConfirmAlert));
			alertDialogSubmitConfirm.setCancelable(false);

			alertDialogSubmitConfirm.setNeutralButton(getText(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which)
						{
							 butClickForGPS=3;
							 dbengine.open();
							 if ((dbengine.PrevLocChk(storeID.trim(),"abc")) )
								{
								 dbengine.close();
								 try
								    {
									FullSyncDataNow task = new FullSyncDataNow(ProductOrderFilterSearch.this);
									 task.execute();
								    }
									catch (Exception e) {
										// TODO Autouuid-generated catch block
										e.printStackTrace();
										//System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
									}
								}
							 else
							 {
								/* dbengine.close();

									// TODO Auto-generated method stub
									boolean isGPSok = false;
									isGPSok = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

									 if(!isGPSok)
							          {
										showSettingsAlert();
										isGPSok = false;
										 return;
									  }



							       isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
							       isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
								   location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

								   pm = (PowerManager) getSystemService(POWER_SERVICE);
								   wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
							                | PowerManager.ACQUIRE_CAUSES_WAKEUP
							                | PowerManager.ON_AFTER_RELEASE, "INFO");
							        wl.acquire();

							       pDialog2STANDBY=ProgressDialog.show(ProductList.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
								   pDialog2STANDBY.setIndeterminate(true);

									pDialog2STANDBY.setCancelable(false);
									pDialog2STANDBY.show();

									checkSTANDBYAysncTask chkSTANDBY = new checkSTANDBYAysncTask(
											new standBYtask().execute()); // Thread keeping 1 minute time
															// watch

									(new Thread(chkSTANDBY)).start();*/



								 appLocationService=new AppLocationService();

								/* pm = (PowerManager) getSystemService(POWER_SERVICE);
								   wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
							                | PowerManager.ACQUIRE_CAUSES_WAKEUP
							                | PowerManager.ON_AFTER_RELEASE, "INFO");
							        wl.acquire();*/


							        pDialog2STANDBY=ProgressDialog.show(ProductOrderFilterSearch.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
									   pDialog2STANDBY.setIndeterminate(true);

										pDialog2STANDBY.setCancelable(false);
										pDialog2STANDBY.show();

								if(isGooglePlayServicesAvailable()) {
									 createLocationRequest();

									 mGoogleApiClient = new GoogleApiClient.Builder(ProductOrderFilterSearch.this)
								     .addApi(LocationServices.API)
								     .addConnectionCallbacks(ProductOrderFilterSearch.this)
								     .addOnConnectionFailedListener(ProductOrderFilterSearch.this)
								     .build();
									 mGoogleApiClient.connect();
							      }
								//startService(new Intent(DynamicActivity.this, AppLocationService.class));
								startService(new Intent(ProductOrderFilterSearch.this, AppLocationService.class));
								Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
								Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
								 countDownTimer2 = new CoundownClass2(startTime, interval);
						         countDownTimer2.start();




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

			alertDialogSubmitConfirm.setNegativeButton(getText(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

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
		    TransactionTableDataDeleteAndSaving(Outstat);
			InvoiceTableDataDeleteAndSaving(Outstat);
			dbengine.open();
			dbengine.UpdateStoreFlag(storeID.trim(), 1);
			dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1,strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"1",strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"1",strGlobalOrderID,strGlobalInvoiceNumber);


		    long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = df.format(dateobj);


			dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
			dbengine.close();
			//dbengine.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0);
			if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strGlobalInvoiceNumber)==0)
			{
				String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
				if(!strDefaultPaymentStageForStore.equals(""))
				{
					dbengine.open();
					dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"1",strGlobalInvoiceNumber);
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
		    TransactionTableDataDeleteAndSaving(Outstat);
			InvoiceTableDataDeleteAndSaving(Outstat);
			dbengine.open();
			dbengine.UpdateStoreFlag(storeID.trim(), 1);
			dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1,strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"1",strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"1",strGlobalOrderID,strGlobalInvoiceNumber);

			 long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = df.format(dateobj);


				dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
			dbengine.close();
			if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strGlobalInvoiceNumber)==0)
			{
				String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
				if(!strDefaultPaymentStageForStore.equals(""))
				{
					dbengine.open();
					dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"1",strGlobalInvoiceNumber);
					dbengine.close();
				}
			}




		 dbengine.open();
		 String presentRoute=dbengine.GetActiveRouteID();
		 dbengine.close();

		 Intent trans2storeList = new Intent(ProductOrderFilterSearch.this, StoreSelection.class);
		 trans2storeList.putExtra("imei", imei);
		 trans2storeList.putExtra("userDate", date);
		 trans2storeList.putExtra("pickerDate", pickerDate);
		 trans2storeList.putExtra("rID", presentRoute);
		 startActivity(trans2storeList);
		 finish();
		  /*  Intent storeSaveIntent = new Intent(ProductOrderFilterSearch.this, LauncherActivity.class);
			startActivity(storeSaveIntent);
			finish();*/

	 }

	 if(valBtnClickedFrom==6)//Clicked By Btn Save and Exit
	 {
	        //Go to Store List Page
	       //Change Ostat Val=2

		    //change by Sunil
		   int Outstat=1;
		    TransactionTableDataDeleteAndSaving(Outstat);
			InvoiceTableDataDeleteAndSaving(Outstat);
			dbengine.open();
			dbengine.UpdateStoreFlag(storeID.trim(), 1);
			dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 1,strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"1",strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"1",strGlobalOrderID,strGlobalInvoiceNumber);
			dbengine.close();

			if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strGlobalInvoiceNumber)==0)
			{
				String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
				if(!strDefaultPaymentStageForStore.equals(""))
				{
					dbengine.open();
					dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"1",strGlobalInvoiceNumber);
					dbengine.close();
				}
			}
			 long  syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StampEndsTime = df.format(dateobj);



				   	Intent storeOrderReviewIntent = new Intent(ProductOrderFilterSearch.this, OrderReview.class);
				    storeOrderReviewIntent.putExtra("storeID", storeID);
				    storeOrderReviewIntent.putExtra("SN", SN);
				    storeOrderReviewIntent.putExtra("bck", 1);
				    storeOrderReviewIntent.putExtra("imei", imei);
				    storeOrderReviewIntent.putExtra("userdate", date);
				    storeOrderReviewIntent.putExtra("pickerDate", pickerDate);
					storeOrderReviewIntent.putExtra("OrderPDAID", strGlobalOrderID);
		 if(hmapPrdctIdOutofStock!=null && hmapPrdctIdOutofStock.size()>0)
		 {
			 storeOrderReviewIntent.putExtra("hmapDistPrdctStockCount", hmapDistPrdctStockCount);
			 storeOrderReviewIntent.putExtra("hmapPrdctIdOutofStock", hmapPrdctIdOutofStock);
		 }
				    //fireBackDetPg.putExtra("rID", routeID);
					startActivity(storeOrderReviewIntent);
					finish();




	 }

	}

	public void TransactionTableDataDeleteAndSaving(int Outstat)
	{

		 dbengine.deleteStoreRecordFromtblStorePurchaseDetailsFromProductTrsaction(storeID,strGlobalOrderID,strGlobalInvoiceNumber);
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
			       dbengine.fnsaveStoreProdcutPurchaseDetails(imei,storeID,""+PCateId,ProductID,pickerDate,Integer.parseInt(ProductStock),Integer.parseInt(OrderQTY),Double.parseDouble(OrderValue),Integer.parseInt(OrderFreeQty.split(Pattern.quote("."))[0]),Double.parseDouble(OrderDisVal),Integer.parseInt(SampleQTY),PName,Double.parseDouble(PRate),Outstat,TaxRate,TaxValue,StoreCatNodeId,strGlobalOrderID,flgIsQuoteRateApplied,distID,PriceApplyDiscountLevelType,strGlobalInvoiceNumber,strFinalAllotedInvoiceIds);
			       dbengine.close();
		       }



		 }
		flgAllTotalOrderValueCrossesCutOff=false;
	}

	public void InvoiceTableDataDeleteAndSaving(int Outstat)
	{

	    dbengine.deleteOldStoreInvoice(storeID,strGlobalOrderID,strGlobalInvoiceNumber);

		Double TBtaxDis;
		Double TAmt;
		Double Dis;
		Double INval;

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
		if(!tv_GrossInvVal.getText().toString().isEmpty()){

		/*	if(Dis!=0.00)
			{
				INval = Double.parseDouble(tvINval.getText().toString().trim())-Dis;
			}
			else
			{
				INval = Double.parseDouble(tvINval.getText().toString().trim());
			}*/
			INval = Double.parseDouble(tv_GrossInvVal.getText().toString().trim());
		}
		else{
			INval = 0.00;
		}
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


		dbengine.open();
		dbengine.saveStoreInvoice(imei,storeID, pickerDate, TBtaxDis, TAmt, Dis, INval, Ftotal, InvAfterDis, AddDis, AmtPrevDueVA, AmtCollVA, AmtOutstandingVAL, NoOfCouponValue, TotalCoupunAmount,Outstat,strGlobalOrderID,strGlobalInvoiceNumber,strFinalAllotedInvoiceIds);//, INvalCreditAmt, INvalInvoiceAfterCreditAmt, valInvoiceOrginal);
	    dbengine.close();



	}

	public void SyncNow()
	{

		dbengine.open();
		String presentRoute=dbengine.GetActiveRouteID();
		dbengine.close();


		long syncTIMESTAMP = System.currentTimeMillis();
		Date dateobj = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat(imei+"."+presentRoute+"."+ "dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

		String newfullFileName=df.format(dateobj);




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



			Intent syncIntent = new Intent(ProductOrderFilterSearch.this, SyncMaster.class);
			//syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/RSPLSFAXml/" + newfullFileName + ".xml");
			syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");

			syncIntent.putExtra("OrigZipFileName", newfullFileName);
			syncIntent.putExtra("whereTo", "Regular");
			startActivity(syncIntent);


		} catch (IOException e) {

			e.printStackTrace();
		}

	}


		public void saveFreeProductDataWithSchemeToDatabase(HashMap<String, ArrayList<String>> hashMapSelectionFreeQty,String savProductIdOnClicked)
		{
			/*if(chkflgInvoiceAlreadyGenerated==0) {
				chkflgInvoiceAlreadyGenerated=1;
				dbengine.updatetblInvoiceCaption(storeID);


			}*/
			String freeProductID;
			ArrayList<String> listFreeProdctQtyScheme;


			for (Entry<String, ArrayList<String>> entry : hashMapSelectionFreeQty.entrySet())
			{
				freeProductID=entry.getKey();
				listFreeProdctQtyScheme=entry.getValue();

				for(String strFreeProdctQtyScheme: listFreeProdctQtyScheme)
				{
					//[10.0, 41, 60, 1, 500.0, 0, 4, 2, 6, 0, 10.0, 0, 0, 0, 0, 0, 0.0, 0.0, 2]

					String[] arrayAllValues=strFreeProdctQtyScheme.split(Pattern.quote("~"));

					int schemeId=Integer.parseInt(arrayAllValues[1]);

					int schemeSlabId=Integer.parseInt(arrayAllValues[2]);

					int schemeSlabBcktId=Integer.parseInt((arrayAllValues[3]));

					Double schemeSlabSubBcktVal=Double.parseDouble(arrayAllValues[4]);

					int schemeSubBucktValType=Integer.parseInt(arrayAllValues[5]);
					//[10.0, 41, 60, 1, 500.0, 0, 4, 2, 6, 0, 10.0, 0, 0, 0, 0, 0, 0.0, 0.0, 2]
					int schemeSlabSubBucktType=Integer.parseInt(arrayAllValues[6]);

					int benifitRowId=Integer.parseInt(arrayAllValues[7]);

					int benSubBucketType=Integer.parseInt(arrayAllValues[8]);

					int freeProductId=Integer.parseInt(freeProductID);

					Double benifitSubBucketValue=Double.parseDouble(arrayAllValues[10]);

					Double benifitMaxValue=Double.parseDouble(arrayAllValues[11]);

					Double benifitAssignedVal=Double.parseDouble(arrayAllValues[0]);

					Double benifitAssignedValueType=Double.parseDouble(arrayAllValues[13]);

					int benifitDiscountApplied=Integer.parseInt(arrayAllValues[14]);

					String benifitCoupnCode=arrayAllValues[15];

					Double per=Double.parseDouble(arrayAllValues[16]);

					Double UOM=Double.parseDouble(arrayAllValues[17]);
					int schSlbRowId=Integer.parseInt(arrayAllValues[18]);
					int SchTypeId=Integer.parseInt(arrayAllValues[19]);

					int WhatFinallyApplied=1;

					if(benSubBucketType==1 || benSubBucketType==5)//Free Different Product  / Free Same Product
					{

						hmapPrdctFreeQty.put(""+freeProductId,""+benifitAssignedVal.intValue());
						//hmapPrdctFreeQty.put(String.valueOf(freeProductId),((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+freeProductId)).getText().toString());
						WhatFinallyApplied=1;
					}

					if(benSubBucketType==2 || benSubBucketType==6)//Discount in Percentage with other product  / Discount in Percentage with same product
					{

						hmapProductDiscountPercentageGive.put(""+freeProductId,""+benifitAssignedVal.intValue());
						//hmapPrdctFreeQty.put(String.valueOf(freeProductId),((TextView)ll_prdct_detal.findViewWithTag("tvFreeQty_"+freeProductId)).getText().toString());
						WhatFinallyApplied=1;
					}
						//((TextView)ll_prdct_detal.findViewWithTag("tvDiscountVal_"+AllProductInSchSlab[mm])).setText("0.00");

					if(benSubBucketType==10)
					{
						WhatFinallyApplied=1;
						//benifitAssignedVal=benifitSubBucketValue;
						hmapProductVolumePer.put(""+freeProductId, ""+per);
					}

					//BenSubBucketType
					//1. Free Other Product
					//2. Discount in Percentage with other product
					//3. Discount in Amount with other product
					//4. Coupons
					//5. Free Same Product
					//6. Discount in Percentage with same product
					//7. Discount in Amount with same product
					//8. Percentage On Invoice
					//9.  Amount On Invoice
					//10. Volume Based Per KG

					dbengine.fnsavetblStoreProductAppliedSchemesBenifitsRecords(storeID,Integer.parseInt(savProductIdOnClicked), schemeId, schemeSlabId,schemeSlabBcktId, schemeSlabSubBcktVal,schemeSubBucktValType,
							schemeSlabSubBucktType,  benifitRowId,  benSubBucketType,
							freeProductId,  benifitSubBucketValue,  benifitMaxValue,  benifitAssignedVal,  benifitAssignedValueType,  benifitDiscountApplied,  benifitCoupnCode,per,UOM,WhatFinallyApplied,schSlbRowId,SchTypeId,strGlobalOrderID,strGlobalInvoiceNumber);
				}



			}

			//orderBookingTotalCalc();

			if(alertOpens)
			{
				 if(flagClkdButton==1)
			     {
					 flagClkdButton=0;
					 progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.genTermPleaseWaitNew);
		         new SaveData().execute("1~3");
			     }

			     else if(flagClkdButton==4)
			     {
			    	 flagClkdButton=0;
			    	  progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileSave);
					   new SaveData().execute("1~2");
			     }

			     else if(flagClkdButton==2)
			     {
			    	 flagClkdButton=0;
			    	   progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSaveExit);
					   new SaveData().execute("2");
			     }


			     else if(flagClkdButton==3)
			     {
			    	 flagClkdButton=0;
			    	 fnSaveFilledDataToDatabase(3);
			     }

			     else if(flagClkdButton==5)
			     {
			    	 flagClkdButton=0;
			    	 progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileSave);
					   new SaveData().execute("1");
			     }

			     else if(flagClkdButton==6)
			     {
			    	 flagClkdButton=0;
			    	   progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileReview);
					   new SaveData().execute("6");
			     }

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
						cxz = "OrdID" + "-" +IMEIid +"-"+cxz+"-"+VisitStartTS.replace(" ", "").replace(":", "").trim();


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
		public void onConnectionFailed(ConnectionResult arg0) {
			// TODO Auto-generated method stub
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);

		}

		/*public void hideSoftKeyboard(View view){
			  InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			  imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
			}*/

	/*	public void showSoftKeyboard(View view){
		    if(view.requestFocus()){
		        InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
		    }

		}*/

		@Override
		public void onConnected(Bundle arg0) {
			// TODO Auto-generated method stub
			LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, appLocationService);
			startLocationUpdates();
		}


		 /*class checkSTANDBYAysncTask implements Runnable
		    {

				AsyncTask<Void, Void, Void> mAT;

				Context context;

				public checkSTANDBYAysncTask(AsyncTask<Void, Void, Void> at)
				{
					mAT = at;

				}

				@Override
				public void run()
				{

					//mHandler.postDelayed(runnable, 60000); // After 60sec the task in
															// run() of runnable will be
					//mHandler.postDelayed(runnable, 2000); // done

					if(acc<=5.0)
					{
						mHandler.postDelayed(runnable, 2000);
					}
					else
					{
						mHandler.postDelayed(runnable, 20000);
					//}

				//.postDelayed(runnable, 5000);
					//getLocation();
				}

				Handler mHandler = new Handler();
				Runnable runnable = new Runnable() {

					@Override
					public void run()
					{


						//enableGPSifNot();

						//task_STANDBY.cancel(true);
						mAT.cancel(true);


						locationListener = new LocationListener()
						{

							public void onProviderDisabled(String provider)
				                 {

				                 }

							public void onProviderEnabled(String provider)
							{
								// TODO Auto-generated method stub

							}

							public void onStatusChanged(String provider, int status,
									Bundle extras)
							{
								// TODO Auto-generated method stub

							}

							public void onLocationChanged(Location location)
							{

								latitude = location.getLatitude();
								longitude = location.getLongitude();

							}
						};


						if (isGPSEnabled)
						{
							locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,0.0f, locationListener);

							//System.out.println("Suni H2 locationManager :"+locationManager);

							if (locationManager != null)
							{
								location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


								//System.out.println("Suni H3 location :"+location);

								if (location != null)
								{
									latitude = location.getLatitude();
									longitude = location.getLongitude();

									//System.out.println("Suni H4 latitude :"+latitude);
									//System.out.println("Suni H5 longitude :"+longitude);

								}
								else
								{
									locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,0.0f, locationListener);

									//System.out.println("Suni H6 locationManager :"+locationManager);

									if (locationManager != null )
									{
									location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);


									//System.out.println("Suni H7 location :"+location);


									if (location!=null)
									{

											latitude = location.getLatitude();
											longitude = location.getLongitude();

											//System.out.println("Suni H8 latitude :"+latitude);
											//System.out.println("Suni H9 longitude :"+longitude);

									}

									}


								}
							}
							else
							{


								locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,0.0f, locationListener);

								//System.out.println("Suni H10 locationManager :"+locationManager);

								if (locationManager != null )
								{
								location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

								//System.out.println("Suni H11 location :"+location);


								if (location!=null)
								{

										latitude = location.getLatitude();
										longitude = location.getLongitude();

										//System.out.println("Suni H12 latitude :"+latitude);
										//System.out.println("Suni H13 longitude :"+longitude);

								}

								}



								//System.out.println("Suni H3 else :"+locationManager);
							}
						}
						else if (isNetworkEnabled)
						{

							//System.out.println("Suni  H1 G1  isNetworkEnabled :"+isNetworkEnabled);

							locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,0.0f, locationListener);

							//System.out.println("Suni  H1 G2  locationManager :"+locationManager);

							location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

							//System.out.println("Suni  H1 G3  location :"+location);

							if (locationManager != null && location==null)
							{
								location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

								//System.out.println("Suni  H1 G4  location :"+location);

								if (location != null)
								{
									latitude = location.getLatitude();
									longitude = location.getLongitude();


									//System.out.println("Suni  H1 G4  latitude :"+latitude);
									//System.out.println("Suni  H1 G4  longitude :"+longitude);

								}
							}
						}





						if(location==null)
						{
							 lastKnownLocLatitude=String.valueOf("0.00000");
					    	 lastKnownLocLongitude=String.valueOf("0.00000");
					    	 accuracy=String.valueOf("0");
					    	 locationProvider="Default";
						}
						else
						{
							lastKnownLocLatitude=""+location.getLatitude();
							lastKnownLocLongitude=""+location.getLongitude();
							accuracy=""+location.getAccuracy();
							locationProvider=location.getProvider();
						}


						// dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(lastKnownLocLatitude), String.valueOf(lastKnownLocLongitude), "" + accuracy,locationProvider);


							pDialog2STANDBY.dismiss();
							wl.release();


							  if(locationProvider.equals("Default"))
			        		   {
					        	   locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0.0f, locationListener);
									location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
									if (locationManager != null && location != null)
									{
										location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
									}

									if(location==null)
									{
										 lastKnownLocLatitude=String.valueOf("0.00000");
								    	 lastKnownLocLongitude=String.valueOf("0.00000");
								    	 accuracy=String.valueOf("0");
								    	 locationProvider="Default";
									}
									else
									{
										lastKnownLocLatitude=""+location.getLatitude();
										lastKnownLocLongitude=""+location.getLongitude();
										accuracy=""+location.getAccuracy();
										locationProvider=location.getProvider();
									}
			        		   }

							dbengine.open();
							 dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(lastKnownLocLatitude), String.valueOf(lastKnownLocLongitude), "" + accuracy,locationProvider);


							dbengine.close();

							 if(butClickForGPS==1)
	                         {
								 butClickForGPS=0;
								 if(ed_LastEditextFocusd!=null)
								 {
									 if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
										{
											 getOrderData(ProductIdOnClickedEdit);

										}


								 }


								   orderBookingTotalCalc();
								   if(!alertOpens)
								   {
									   progressTitle="While we save your data";
									   new SaveData().execute("1");
								   }


							 }
							 else  if(butClickForGPS==2)
	                         {
								 butClickForGPS=0;

								 if(ed_LastEditextFocusd!=null)
								 {
									 if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
									 {
										 getOrderData(ProductIdOnClickedEdit);
									 }

								 }

								   orderBookingTotalCalc();
								   if(!alertOpens)
								   {
									   progressTitle="While we save your data then exit";
									   new SaveData().execute("2");
								   }

							 }
							 else  if(butClickForGPS==3)
	                         {
								 butClickForGPS=0;
								 try
								    {
									FullSyncDataNow task = new FullSyncDataNow(ProductList.this);
									 task.execute();
								    }
									catch (Exception e) {
										// TODO Autouuid-generated catch block
										e.printStackTrace();
										//System.out.println("onGetStoresForDayCLICK: Exec(). EX: "+e);
									}
							 }
							Intent ide=new Intent(Add_New_Store_NewFormat.this,StoreSelection.class);
							ide.putExtra("userDate", date_value);
							ide.putExtra("pickerDate", pickerDate);
							ide.putExtra("imei", imei);
							ide.putExtra("rID", rID);
							Add_New_Store_NewFormat.this.startActivity(ide);
							finish();






					};
				};
			}
	*/

		protected void startLocationUpdates() {
		    PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
		}

		@Override
		public void onConnectionSuspended(int arg0) {
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
				AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductOrderFilterSearch.this);
				alertDialogNoConn.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
				alertDialogNoConn.setMessage(msg);
				alertDialogNoConn.setCancelable(false);
				alertDialogNoConn.setNeutralButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
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
								 checkHighAccuracyLocationMode(ProductOrderFilterSearch.this);
								 dbengine.open();
								// dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview);


								 dbengine.close();

								 if(butClickForGPS==1)
								 {
									 butClickForGPS=0;
									 if(ed_LastEditextFocusd!=null)
									 {
									 /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
										{*/
										 getOrderData(ProductIdOnClickedEdit);

										/*}*/


									 }


									 orderBookingTotalCalc();
									 if(!alertOpens)
									 {
										 progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileSave);
										 new SaveData().execute("1");
									 }


								 }
								 else  if(butClickForGPS==2)
								 {
									 butClickForGPS=0;

									 if(ed_LastEditextFocusd!=null)
									 {
									 /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
									 {*/
										 getOrderData(ProductIdOnClickedEdit);
									 /*}*/

									 }

									 orderBookingTotalCalc();
									 if(!alertOpens)
									 {
										 progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSaveExit);
										 new SaveData().execute("2");
									 }

								 }
								 else  if(butClickForGPS==3)
								 {
									 butClickForGPS=0;
									 try
									 {
										 FullSyncDataNow task = new FullSyncDataNow(ProductOrderFilterSearch.this);
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

									 if(ed_LastEditextFocusd!=null)
									 {
										 /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
										 {*/
										 getOrderData(ProductIdOnClickedEdit);
										 /*}*/

									 }

									 orderBookingTotalCalc();
									 if(!alertOpens)
									 {
										 progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileReview);
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

		private void allMessageAlert(String message) {
			 AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(ProductOrderFilterSearch.this);
				alertDialogNoConn.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
				alertDialogNoConn.setMessage(message);
				//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
				alertDialogNoConn.setNeutralButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which)
							{
	                     dialog.dismiss();
	                     ed_search.requestFocus();
	                     /*if(isMyServiceRunning())
	               		{
	                     stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
	               		}
	                     finish();*/
	                     //finish();
							}
						});
				alertDialogNoConn.setIcon(R.drawable.info_ico);
				AlertDialog alert = alertDialogNoConn.create();
				alert.show();

		}

		 public void countUp(int start)
		  {

		    if(pDialog2STANDBYabhi != null && pDialog2STANDBYabhi.isShowing())
		       {

		     pDialog2STANDBYabhi.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.MyNameAbhinav));
		     pDialog2STANDBYabhi.setCancelable(true);
		     pDialog2STANDBYabhi.cancel();
		     pDialog2STANDBYabhi.dismiss();

		       }

		    else
		    {
		     countUp(start + 1);
		    }
		  }

		     public void fnAbhinav(int mytimeval)
		     {
		    countUp(1);
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

	public void customAlertStoreList(final List<String> listOption, String sectionHeader)
	{

		final Dialog listDialog = new Dialog(ProductOrderFilterSearch.this);
		listDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		listDialog.setContentView(R.layout.search_list);
		listDialog.setCanceledOnTouchOutside(false);
		listDialog.setCancelable(false);
		WindowManager.LayoutParams parms = listDialog.getWindow().getAttributes();
		parms.gravity =Gravity.CENTER;
		//there are a lot of settings, for dialog, check them all out!
		parms.dimAmount = (float) 0.5;




		TextView txt_section=(TextView) listDialog.findViewById(R.id.txt_section);
		txt_section.setText(sectionHeader);
		TextView txtVwCncl=(TextView) listDialog.findViewById(R.id.txtVwCncl);
		//    TextView txtVwSubmit=(TextView) listDialog.findViewById(R.id.txtVwSubmit);

		final EditText ed_search=(AutoCompleteTextView) listDialog.findViewById(R.id.ed_search);
		ed_search.setVisibility(View.GONE);
		final ListView list_store=(ListView) listDialog.findViewById(R.id.list_store);
		final CardArrayAdapterCategory cardArrayAdapter = new CardArrayAdapterCategory(ProductOrderFilterSearch.this,listOption,listDialog,previousSlctdCtgry);

		//img_ctgry.setText(previousSlctdCtgry);





		list_store.setAdapter(cardArrayAdapter);
		//	editText.setBackgroundResource(R.drawable.et_boundary);
		img_ctgry.setEnabled(true);





		txtVwCncl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listDialog.dismiss();
				img_ctgry.setEnabled(true);


			}
		});




		//now that the dialog is set up, it's time to show it
		listDialog.show();

	}

	@Override
	public void selectedOption(String selectedCategory, Dialog dialog) {
		dialog.dismiss();
		previousSlctdCtgry=selectedCategory;
		String lastTxtSearch=ed_search.getText().toString().trim();
		//img_ctgry.setText(selectedCategory);
		ed_search.setText(previousSlctdCtgry);
		if(hmapctgry_details.containsKey(selectedCategory))
		{
			searchProduct(selectedCategory,hmapctgry_details.get(selectedCategory));
		}
		else
		{
			searchProduct(selectedCategory,"");
		}



	}

	public void searchProduct(String filterSearchText,String ctgryId)
	{
		progressBarStatus = 0;


		hmapFilterProductList.clear();
		hmapPrdctIdPrdctNameVisible.clear();
		ll_prdct_detal.removeAllViews();
		hmapFilterProductList=dbengine.getFileredProductListMap(filterSearchText.trim(),StoreCurrentStoreType,ctgryId);
		//System.out.println("hmapFilterProductListCount :-"+ hmapFilterProductList.size());


		/*if(hmapFilterProductList.size()<250)
		{*/
			if(hmapFilterProductList.size()>0)
			{
				pDialog2STANDBYabhi=ProgressDialog.show(ProductOrderFilterSearch.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.Loading), false,true);
				myThread = new Thread(myRunnable);
				myThread.setPriority(Thread.MAX_PRIORITY);
				myThread.start();
			}
			else
			{
				allMessageAlert(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertFilter));
			}

		/*}

		else
		{
			allMessageAlert("Please put some extra filter on Search-Box to fetch related product");
		}*/




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
		checkHighAccuracyLocationMode(ProductOrderFilterSearch.this);
		dbengine.open();
		//dbengine.UpdateStoreActualLatLongi(storeID,String.valueOf(fnLati), String.valueOf(fnLongi), "" + fnAccuracy,fnAccurateProvider,flgLocationServicesOnOffOrderReview,flgGPSOnOffOrderReview,flgNetworkOnOffOrderReview,flgFusedOnOffOrderReview,flgInternetOnOffWhileLocationTrackingOrderReview,flgRestartOrderReview,flgStoreOrderOrderReview);


		dbengine.close();

		if(butClickForGPS==1)
		{
			butClickForGPS=0;
			if(ed_LastEditextFocusd!=null)
			{

				getOrderData(ProductIdOnClickedEdit);
         	}


			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSave);
				new SaveData().execute("1");
			}


		}
		else  if(butClickForGPS==2)
		{
			butClickForGPS=0;

			if(ed_LastEditextFocusd!=null)
			{
									 /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
									 {*/
				getOrderData(ProductIdOnClickedEdit);
									 /*}*/

			}

			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSaveExit);
				new SaveData().execute("2");
			}

		}
		else  if(butClickForGPS==3)
		{
			butClickForGPS=0;
			try
			{
				FullSyncDataNow task = new FullSyncDataNow(ProductOrderFilterSearch.this);
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

			if(ed_LastEditextFocusd!=null)
			{
										 /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
										 {*/
				getOrderData(ProductIdOnClickedEdit);
										 /*}*/

			}

			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileReview);
				new SaveData().execute("6");
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
		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductOrderFilterSearch.this);
		alertDialogSubmitConfirm.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.StockOverbooked));
		//int avilabQty=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);//+Integer.parseInt(hmapPrdctOdrQty.get(productOIDClkd));
		int avilabQty=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit)+Integer.parseInt(hmapPrdctOdrQty.get(productOIDClkd));
		alertDialogSubmitConfirm.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.AvailableQty)+avilabQty +"\n"+ProductOrderFilterSearch.this.getResources().getString(R.string.OrderQty)+hmapPrdctOdrQty.get(productOIDClkd)+"\n"+hmapPrdctIdPrdctName.get(ProductIdOnClickedEdit)+" "+getText(R.string.order_exceeds_stock));

		alertDialogSubmitConfirm.setCancelable(false);
/*
		alertDialogSubmitConfirm.setNeutralButton(ProductOrderFilterSearch.this.getResources().getString(R.string.Continue), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
			if(flagClkdButton!=-1)
			{
				if(dbengine.isFlgCrediBalSubmitted(storeID))
				{

					nextStepAfterRetailerCreditBal(flagClkdButton);

				}
				else
				{

					alertForRetailerCreditLimit(flagClkdButton);
				}
			}

				dialog.dismiss();

			}
		});*/

		alertDialogSubmitConfirm.setNegativeButton(ProductOrderFilterSearch.this.getResources().getString(R.string.ChangeQty), new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				edOrderCurrentLast.setText("");
				edOrderCurrent.clearFocus();
				edOrderCurrentLast.requestFocus();

				dialog.dismiss();
			}
		});

		alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);
		AlertDialog alert = alertDialogSubmitConfirm.create();
		alert.show();
	}

	public void alertForRetailerCreditLimit(final int btnClkd)
	{
		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductOrderFilterSearch.this);
		alertDialogSubmitConfirm.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
		alertDialogSubmitConfirm.setMessage(getText(R.string.credit_retailer_balance));
		alertDialogSubmitConfirm.setCancelable(false);

		alertDialogSubmitConfirm.setNeutralButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogYesButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				dbengine.updateFlgCrediBal(storeID,1);

					nextStepAfterRetailerCreditBal(btnClkd);


			}
		});

		alertDialogSubmitConfirm.setNegativeButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogNoButton), new DialogInterface.OnClickListener() {

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

		if(btnClkd==0) // order review
		{
			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);

			String fileName=imei+"_"+storeID;
			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal);
			//File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
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


			butClickForGPS=6;
			flagClkdButton=6;
					  /* dbengine.open();
					   if ((dbengine.PrevLocChk(storeID.trim())) )
						{
						   dbengine.close();*/
			if(ed_LastEditextFocusd!=null)
			{
							  /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
							   {*/
				getOrderData(ProductIdOnClickedEdit);
							   /*}*/
			}


			orderBookingTotalCalc();




			butClickForGPS=0;
			progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSaveExit);
			//  progressTitle="While we save your data then review Order";
			new SaveData().execute("6");
		}

		else if(btnClkd==1) // btn save clkd
		{


			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"Save Button Click on Product List"+StartClickTimeFinal);
			//File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
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
				  /* dbengine.open();
				   if ((dbengine.PrevLocChk(storeID.trim())) )
					{

					   dbengine.close();*/

			if(ed_LastEditextFocusd!=null)
			{
							/* if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
							   {*/
				getOrderData(ProductIdOnClickedEdit);
							   /*}*/

			}

			orderBookingTotalCalc();
			progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileSave);
			new SaveData().execute("1");

		}

		else if(btnClkd==2) // btn clkd save and exit
		{
			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"SaveExit Button Click on Product List"+StartClickTimeFinal);
			//File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
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
			//dbengine.updateflgFromWhereSubmitStatusAgainstStore(storeID, 2);
			if ((dbengine.PrevLocChk(storeID.trim(),"abc")) )
			{
				dbengine.close();
				if(ed_LastEditextFocusd!=null)
				{
						  /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
						   {*/
					getOrderData(ProductIdOnClickedEdit);
						   /*}*/
				}


				orderBookingTotalCalc();
				if(!alertOpens)
				{
					progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSaveExit);
					locationManager=(LocationManager) ProductOrderFilterSearch.this.getSystemService(LOCATION_SERVICE);


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
					// new SaveData().execute("2");
				}
			}
			else
			{


				appLocationService=new AppLocationService();




				pDialog2STANDBY=ProgressDialog.show(ProductOrderFilterSearch.this,getText(R.string.genTermPleaseWaitNew) ,getText(R.string.genTermRetrivingLocation), true);
				pDialog2STANDBY.setIndeterminate(true);

				pDialog2STANDBY.setCancelable(false);
				pDialog2STANDBY.show();

				if(isGooglePlayServicesAvailable()) {
					createLocationRequest();

					mGoogleApiClient = new GoogleApiClient.Builder(ProductOrderFilterSearch.this)
							.addApi(LocationServices.API)
							.addConnectionCallbacks(ProductOrderFilterSearch.this)
							.addOnConnectionFailedListener(ProductOrderFilterSearch.this)
							.build();
					mGoogleApiClient.connect();
				}
				//startService(new Intent(DynamicActivity.this, AppLocationService.class));
				startService(new Intent(ProductOrderFilterSearch.this, AppLocationService.class));
				Location nwLocation=appLocationService.getLocation(locationManager,LocationManager.GPS_PROVIDER,location);
				Location gpsLocation=appLocationService.getLocation(locationManager,LocationManager.NETWORK_PROVIDER,location);
				countDownTimer2 = new CoundownClass2(startTime, interval);
				countDownTimer2.start();


			}
		}
		else if(btnClkd==3) // btn submit clikd
		{

			long StartClickTime = System.currentTimeMillis();
			Date dateobj1 = new Date(StartClickTime);
			SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
			String StartClickTimeFinal = df1.format(dateobj1);


			String fileName=imei+"_"+storeID;

			//StringBuffer content=new StringBuffer(imei+"_"+storeID+"_"+"Submit Button Click on Product List"+StartClickTimeFinal);
			//File file = new File("/sdcard/MeijiIndirectTextFile/"+fileName);
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
			//dbengine.updateflgFromWhereSubmitStatusAgainstStore(storeID, 2);
			dbengine.close();
			if(ed_LastEditextFocusd!=null)
			{
					   /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
					   {*/
				getOrderData(ProductIdOnClickedEdit);
					   /*}*/
			}


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
				else
				{
					fnSaveFilledDataToDatabase(3);
				}
			}
		}
		else if(btnClkd==4) // btn return clkd
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
				if(ed_LastEditextFocusd!=null)
				{
				    		  /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
							   {*/
					getOrderData(ProductIdOnClickedEdit);
							   /*}*/
				}

				orderBookingTotalCalc();
				if(!alertOpens)
				{
					progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.genTermPleaseWaitNew);
					new SaveData().execute("1~3");
				}
			}

			else
			{
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductOrderFilterSearch.this);

				// Setting Dialog Title
				alertDialog.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
				alertDialog.setIcon(R.drawable.error_info_ico);
				alertDialog.setCancelable(false);
				// Setting Dialog Message
				alertDialog.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.NoStocks));

				// On pressing Settings button
				alertDialog.setPositiveButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which) {
						dialog.dismiss();
					}
				});

				// Showing Alert Message
				alertDialog.show();

			}
		}

		else if(btnClkd==5)// btn back pressed
		{
			flagClkdButton=4;
			if(ed_LastEditextFocusd!=null)
			{
					   /*if(!(ed_LastEditextFocusd.getText().toString()).equals(viewCurrentBoxValue))
					   {*/
				getOrderData(ProductIdOnClickedEdit);
					   /*}*/
			}


			orderBookingTotalCalc();
			if(!alertOpens)
			{
				progressTitle=ProductOrderFilterSearch.this.getResources().getString(R.string.WhileWeSave);


				locationManager=(LocationManager) ProductOrderFilterSearch.this.getSystemService(LOCATION_SERVICE);


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
					new SaveData().execute("1~2");
				}


			}
		}
	}

	public void fnCreditAndStockCal(int butnClkd)
	{
		if(ed_LastEditextFocusd!=null)
		{
			String tag=ed_LastEditextFocusd.getTag().toString();
			if(tag.contains("etOrderQty"))
			{

				if (hmapDistPrdctStockCount.containsKey(ProductIdOnClickedEdit))
				{
					if(hmapPrdctIdOutofStock!=null && hmapPrdctIdOutofStock.size()>0)
					{
						if(hmapPrdctIdOutofStock.containsKey(ProductIdOnClickedEdit))
						{
							int lastOrgnlQntty=Integer.parseInt(hmapPrdctIdOutofStock.get(ProductIdOnClickedEdit));
							int netStockLeft=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit)+lastOrgnlQntty;
							hmapDistPrdctStockCount.put(ProductIdOnClickedEdit,netStockLeft);

						}
					}
					int originalNetQntty=0;
					if(!TextUtils.isEmpty(ed_LastEditextFocusd.getText().toString()))
					{
						originalNetQntty=Integer.parseInt(ed_LastEditextFocusd.getText().toString());
					}
					int totalStockLeft = hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);

					int netStock=totalStockLeft-originalNetQntty;
					hmapDistPrdctStockCount.put(ProductIdOnClickedEdit,netStock);

									/*change*/
					/*TextView tv_VanStock= (TextView) ll_prdct_detal.findViewWithTag("etVanStock"+"_"+ProductIdOnClickedEdit);
					if(tv_VanStock!=null)
					{
						tv_VanStock.setText(""+hmapDistPrdctStockCount.get(ProductIdOnClickedEdit));
					}*/


					if(originalNetQntty!=0)
					{
						hmapPrdctIdOutofStock.put(ProductIdOnClickedEdit,ed_LastEditextFocusd.getText().toString().trim());
					}
					else
					{
						hmapPrdctIdOutofStock.remove(ProductIdOnClickedEdit);
						dbengine.deleteExistStockTable(distID,strGlobalOrderID,ProductIdOnClickedEdit);
					}
					if (originalNetQntty>totalStockLeft)
					{

						alertForOrderExceedStock(ProductIdOnClickedEdit,ed_LastEditextFocusd,ed_LastEditextFocusd,-1);
					}
					else
					{

						if(dbengine.isFlgCrediBalSubmitted(storeID))
						{
							nextStepAfterRetailerCreditBal(butnClkd);
						}
						else
						{
							alertForRetailerCreditLimit(butnClkd);
						}

					}

				}
				else
				{
					if(dbengine.isFlgCrediBalSubmitted(storeID))
					{

						nextStepAfterRetailerCreditBal(butnClkd);

					}
					else
					{

						alertForRetailerCreditLimit(butnClkd);
					}
				}

			}


			else
			{
				if(dbengine.isFlgCrediBalSubmitted(storeID))
				{

					nextStepAfterRetailerCreditBal(butnClkd);

				}
				else
				{

					alertForRetailerCreditLimit(butnClkd);
				}
			}
		}
		else
		{
			if(dbengine.isFlgCrediBalSubmitted(storeID))
			{

				nextStepAfterRetailerCreditBal(butnClkd);

			}
			else
			{

				alertForRetailerCreditLimit(butnClkd);
			}
		}


	}

	public boolean validateInviceQty()
	{
		boolean validateDone=true;
		int prdListCount =hmapPrdctIdPrdctNameVisible.size();
		StringBuilder stringBuilder=new StringBuilder();

		for (int index=0; index < prdListCount; index++) {
			View vRow = ll_prdct_detal.getChildAt(index);

			int PCateIdDetails = Integer.parseInt(vRow.getTag().toString().split(Pattern.quote("_"))[0]);
			String ProductID = ((TextView) (vRow).findViewById(R.id.tvProdctName)).getTag().toString().split(Pattern.quote("_"))[1];

			if (hmapPrdctOdrQty.containsKey(ProductID)) {

				int orderQty=Integer.parseInt(hmapPrdctOdrQty.get(ProductID));
				TextView tv_VanStock= (TextView) ll_prdct_detal.findViewWithTag("etVanStock"+"_"+ProductID);
				int totalStockLeft=0;
				if(tv_VanStock!=null)
				{
					totalStockLeft = Integer.parseInt(tv_VanStock.getText().toString());
				}

				if(orderQty>totalStockLeft)
				{
					stringBuilder.append(hmapPrdctIdPrdctName.get(ProductID));

				}
			}
		}
		if(stringBuilder!=null && stringBuilder.length()>0)
		{
			alertForOrderExceedStockValidate(stringBuilder.toString());
			validateDone=false;
		}

		return validateDone;
	}

	public void alertForOrderExceedStockValidate(String prdctName)
	{
		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(ProductOrderFilterSearch.this);
		alertDialogSubmitConfirm.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.StockOverbooked));
		//int avilabQty=hmapDistPrdctStockCount.get(ProductIdOnClickedEdit);//+Integer.parseInt(hmapPrdctOdrQty.get(productOIDClkd));

		alertDialogSubmitConfirm.setMessage("Invoice Quantity can not be grater than Stock Quantity+\n"+prdctName);

		alertDialogSubmitConfirm.setCancelable(false);


		alertDialogSubmitConfirm.setNegativeButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {


				dialog.dismiss();
			}
		});

		alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);
		AlertDialog alert = alertDialogSubmitConfirm.create();
		alert.show();
	}

	private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {


		ProgressDialog pDialogGetStores;
		public FullSyncDataNow(ProductOrderFilterSearch activity)
		{
			pDialogGetStores = new ProgressDialog(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.SubmittingOrderDetails));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();


		}

		@Override

		protected Void doInBackground(Void... params) {

			 int Outstat=3;
				TransactionTableDataDeleteAndSaving(Outstat);
				InvoiceTableDataDeleteAndSaving(Outstat);

			    long  syncTIMESTAMP = System.currentTimeMillis();
				Date dateobj = new Date(syncTIMESTAMP);
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
				String StampEndsTime = df.format(dateobj);


				dbengine.open();
				dbengine.UpdateStoreEndVisit(storeID, StampEndsTime);
				dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"3",strGlobalOrderID,strGlobalInvoiceNumber);
				dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"3",strGlobalOrderID,strGlobalInvoiceNumber);
				dbengine.UpdateStoreFlag(storeID.trim(), 3);
				dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 3,strGlobalOrderID,strGlobalInvoiceNumber);
				//dbengine.UpdateStoreReturnphotoFlag(storeID.trim(), 5);

				dbengine.close();
				//dbengine.updateStoreQuoteSubmitFlgInStoreMstr(storeID.trim(),0);
				if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strGlobalInvoiceNumber)==0)
				{
					String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
					if(!strDefaultPaymentStageForStore.equals(""))
					{
						dbengine.open();
						dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"3",strGlobalInvoiceNumber);
						dbengine.close();
					}
				}
			dbengine.open();
			String presentRoute=dbengine.GetActiveRouteID();
			dbengine.close();


			/*long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);*/
			SimpleDateFormat df1 = new SimpleDateFormat(imei+"."+presentRoute+"."+ "dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);

			newfullFileName=df1.format(dateobj);




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


				//dbengine.deleteAllXmlDataTable( "4");
				/*if(chkflgInvoiceAlreadyGenerated==0) {
					dbengine.updatetblInvoiceCaption(storeID);
				}*/
				dbengine.savetbl_XMLfiles(newfullFileName, "3","1");

				dbengine.open();
				dbengine.UpdateStoreFlag(storeID.trim(), 4);
				dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 4,strGlobalOrderID,strGlobalInvoiceNumber);
				dbengine.UpdateStoreMaterialphotoFlag(storeID.trim(), 5);
				dbengine.UpdateStoreReturnphotoFlag(storeID.trim(), 5);
				dbengine.close();

				VisitTypeStatus="0";
				//dbengine.updateVisitTypeStatusOfStore(storeID,VisitTypeStatus);

				Double outstandingvalue=dbengine.fnGetStoretblLastOutstanding(storeID);
				outstandingvalue=Double.parseDouble(new DecimalFormat("##.##").format(outstandingvalue));

				Double cntInvoceValue=dbengine.fetch_Store_InvValAmount(storeID,strGlobalInvoiceNumber);
				cntInvoceValue=Double.parseDouble(new DecimalFormat("##.##").format(cntInvoceValue));

				Double CollectionAmtAgainstStore=0.00;//dbengine.fnTotCollectionAmtAgainstStore(storeID.trim(),strGlobalInvoiceNumber);
				CollectionAmtAgainstStore=Double.parseDouble(new DecimalFormat("##.##").format(CollectionAmtAgainstStore));

				/*Double newOutStandingAmoutForInvoiceSubmission=((outstandingvalue+cntInvoceValue)-CollectionAmtAgainstStore);
				newOutStandingAmoutForInvoiceSubmission=Double.parseDouble(new DecimalFormat("##.##").format(newOutStandingAmoutForInvoiceSubmission));
				dbengine.updateOutstandingOfStore(storeID,newOutStandingAmoutForInvoiceSubmission);*/
				if(dbengine.checkCountIntblStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strGlobalInvoiceNumber)==0)
				{
					String strDefaultPaymentStageForStore=dbengine.fnGetDefaultStoreOrderPAymentDetails(storeID);
					if(!strDefaultPaymentStageForStore.equals(""))
					{
						dbengine.open();
						dbengine. fnsaveStoreSalesOrderPaymentDetails(storeID,strGlobalOrderID,strDefaultPaymentStageForStore,"4",strGlobalInvoiceNumber);
						dbengine.close();
					}
				}

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
				Intent syncIntent = new Intent(ProductOrderFilterSearch.this, SyncMaster.class);
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

		             mProgressDialog = new ProgressDialog(ProductOrderFilterSearch.this);
		             mProgressDialog.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermPleaseWaitNew));
		             mProgressDialog.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.Loading));
		             mProgressDialog.setIndeterminate(true);
		             mProgressDialog.setCancelable(false);
		            mProgressDialog.show();
		         }

		  @Override
		  protected Void doInBackground(Void... params)
		  {
			  dbengine.open();
			  StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(storeID));
			  dbengine.close();

			  LinkedHashMap<String, String> hmapListQuoteISOfUnmappedWithProducts= dbengine.fnGetListQuoteISOfUnmappedWithProducts(storeID);
			  if(hmapListQuoteISOfUnmappedWithProducts.size()>0)
			  {
				  for(Entry<String, String> entry:hmapListQuoteISOfUnmappedWithProducts.entrySet())
				  {
					  dbengine.fndeleteQuoteISOfUnmappedWithProducts(entry.getKey());
				  }
			  }
			  String routeID=dbengine.GetActiveRouteIDSunil();
			  hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();

			//chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
			/* if(chkflgInvoiceAlreadyGenerated==1)
			 {
				 strGlobalInvoiceNumber=dbengine.fnGetExistingInvoiceNumber(storeID);
			 }
			 else
			 {
				 strGlobalInvoiceNumber=dbengine.fnGettblInvoiceCaption(storeID);
			 }*/
			  CheckIfStoreExistInStoreProdcutPurchaseDetails=dbengine.fnCheckIfStoreExistInStoreProdcutPurchaseDetails(storeID,strGlobalInvoiceNumber,0,chkflgInvoiceAlreadyGenerated);
			  CheckIfStoreExistInStoreProdcutInvoiceDetails=dbengine.fnCheckIfStoreExistInStoreProdcutInvoiceDetails(storeID,strGlobalInvoiceNumber);
		   if(CheckIfStoreExistInStoreProdcutPurchaseDetails==1 || CheckIfStoreExistInStoreProdcutInvoiceDetails==1)
           {
			 //  strGlobalOrderID=dbengine.fngetOrderIDAganistStore(storeID);
           }
		   else
		   {
			   strGlobalOrderID= genOutOrderID();
		   }
		   getCategoryDetail();

		   getProductData();

		   getSchemeSlabDetails();
		   hmapMinDlvrQty=dbengine.getMinDlvryQntty(storeID);
		   hmapMinDlvrQtyQPBT=dbengine.getMinDlvryQnttyQPBT(storeID);
		   hmapMinDlvrQtyQPTaxAmount=dbengine.getMinDlvryQnttyQPTaxAmount(storeID);
		   hmapMinDlvrQtyQPAT=dbengine.getMinDlvryQnttyQPAT(storeID);
			  distID=dbengine.getDisId(storeID);
			  hmapDistPrdctStockCountText=dbengine.getDistStockCount(distID);
			  if(hmapPrdctIdOutofStock!=null && hmapPrdctIdOutofStock.size()>0)
			  {

			  }
			  else {
				  hmapDistPrdctStockCount=dbengine.getDistStockCount(distID);
				  hmapPrdctIdOutofStock=dbengine.getProductStock(strGlobalInvoiceNumber,distID);
			  }
		   return null;
		  }
		   @Override
		         protected void onPostExecute(Void args) {

			   hmapFilterProductList.clear();
	        	hmapPrdctIdPrdctNameVisible.clear();

	        	ll_prdct_detal.removeAllViews();
int flgDrctslsIndrctSls=0;
		       hmapFilterProductList=dbengine.getFileredOrderReviewProductListMap(storeID,strGlobalInvoiceNumber,flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);







	       			Iterator it11new = hmapPrdctIdPrdctName.entrySet().iterator();
	       			String crntPID="0";
	       			int cntPsize=0;
				    while (it11new.hasNext()) {
				        Entry pair = (Entry)it11new.next();
				        if(hmapFilterProductList.containsKey(pair.getKey().toString())){
				        //if(pair.getValue().equals(abc)){
				        	crntPID	=pair.getKey().toString();





				        	/*if(!hmapPrdctIdPrdctNameVisible.containsKey(crntPID))
							   {
								   //createDynamicProduct(crntPID, 0,"");
							       hmapPrdctIdPrdctNameVisible.put(crntPID, hmapPrdctIdPrdctName.get(crntPID));
							       if(hmapPrdctIdPrdctNameVisible.size()>0)
					  	           {
					  	        	   createProductRowColor();
					  	           }
							   }*/
				        }

			    }


				    createProductPrepopulateDetail(CheckIfStoreExistInStoreProdcutPurchaseDetails);
				    setInvoiceTableView();
			   mProgressDialog.dismiss();
			   if(defaultCatName_Id.contains("^"))
			   {
				   ed_search.setText(defaultCatName_Id.split(Pattern.quote("^"))[0]);

				   searchProduct(defaultCatName_Id.split(Pattern.quote("^"))[0],defaultCatName_Id.split(Pattern.quote("^"))[1]);
			   }

			   /*

		     ArrayAdapter adapterCategory=new ArrayAdapter(ProductOrderFilterSearch.this, android.R.layout.simple_spinner_item,categoryNames);
		      adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		       spinner_category.setAdapter(adapterCategory);
		       createProductPrepopulateDetail(CheckIfStoreExistInStoreProdcutPurchaseDetails);
		       setInvoiceTableView();


		       adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, R.id.product_name, products);

				  spinner_product.setOnClickListener(new OnClickListener() {

					    @Override
					    public void onClick(View arg0) {


							AlertDialog.Builder alertDialog = new AlertDialog.Builder(getApplicationContext());
					      alertDialog = new AlertDialog.Builder(ProductOrderFilterSearch.this);
					      LayoutInflater inflater = getLayoutInflater();

					            convertView = (View) inflater.inflate(R.layout.activity_list, null);
					            lvProduct = (ListView)convertView. findViewById(R.id.list_view);
					            inputSearch = (EditText) convertView.findViewById(R.id.inputSearch);
					            lvProduct.setAdapter(adapter);
					            alertDialog.setView(convertView);
					            alertDialog.setTitle("Product");

					            inputSearch.addTextChangedListener(new TextWatcher() {

					               @Override
					                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
					                    // When user changed the Text
					                    ProductOrderFilterSearch.this.adapter.getFilter().filter(cs);
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
					            lvProduct.setOnItemClickListener(new OnItemClickListener() {

					       @Override
					       public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
					        String abc=lvProduct.getItemAtPosition(arg2).toString();

					        String filterSearchText=inputSearch.getText().toString().trim();
					        if(!filterSearchText.trim().equals(""))
					        {
					        	hmapFilterProductList.clear();
					        	hmapPrdctIdPrdctNameVisible.clear();

					        	ll_prdct_detal.removeAllViews();

					        	View namebar = (LinearLayout) (ll_prdct_detal).findViewWithTag(vtag);

				            	//If romoved from list, then remove it from the return  page also and code starts here

				            	String getPIdToremove=vtag.split("_")[1];

				            	//If romoved from list, then remove it from the return  page also and code ends here

				            	((LinearLayout) namebar.getParent()).removeView(namebar);
				            	 if(hmapPrdctIdPrdctNameVisible.size()>0)
				  	           {
				  	        	   createProductRowColor();
				  	           }

						       hmapFilterProductList=dbengine.getFileredProductListMap(filterSearchText.trim());
						       System.out.println("hmapFilterProductListCount :-"+ hmapFilterProductList.size());

					        }
					        else
					        {
					        	Toast.makeText(getApplicationContext(), "Please type some text to fillter!", Toast.LENGTH_LONG).show();
					        }

					       inputSearch.setText("");
					       spinner_product.setText("Select SKU");
					       ad.dismiss();

					       			Iterator it11new = hmapPrdctIdPrdctName.entrySet().iterator();
					       			String crntPID="0";
					       			int cntPsize=0;
								    while (it11new.hasNext()) {
								        Map.Entry pair = (Map.Entry)it11new.next();
								        if(hmapFilterProductList.containsKey(pair.getKey().toString())){
								        //if(pair.getValue().equals(abc)){
								        	crntPID	=pair.getKey().toString();





								        	if(!hmapPrdctIdPrdctNameVisible.containsKey(crntPID))
											   {
												   createDynamicProduct(crntPID, 0,"");
											       hmapPrdctIdPrdctNameVisible.put(crntPID, hmapPrdctIdPrdctName.get(crntPID));
											       if(hmapPrdctIdPrdctNameVisible.size()>0)
									  	           {
									  	        	   createProductRowColor();
									  	           }
											   }
								        }

							    }


					       }
					      });

					            ad=alertDialog.show();
					    }
					   }); */



		  }

		 }

		/*public class SaveData extends AsyncTask<String, String, Void>
		 {
		   @Override
		         protected void onPreExecute() {
		             super.onPreExecute();

		             mProgressDialog = new ProgressDialog(ProductList.this);
		             mProgressDialog.setTitle("Please Wait");
		             mProgressDialog.setMessage("Saving...");
		             mProgressDialog.setIndeterminate(true);
		             mProgressDialog.setCancelable(false);
		             mProgressDialog.show();
		         }

		  @Override
		  protected Void doInBackground(String... params)
		  {
			  int btnClkd=Integer.parseInt(params[0]);

			  fnSaveFilledDataToDatabase(btnClkd);
		   return null;
		  }
		   @Override
		         protected void onPostExecute(Void args) {


		     mProgressDialog.dismiss();
		   }


		 }
	*/
		public class SaveData extends AsyncTask<String, String, Void>
		 {

		   @Override
		         protected void onPreExecute() {
		             super.onPreExecute();
		             //Text need to e changed according to btn Click


		             if(mProgressDialog.isShowing()==false)
		             {
		             mProgressDialog = new ProgressDialog(ProductOrderFilterSearch.this);
		             mProgressDialog.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.Loading));
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

			   if(mProgressDialog.isShowing()==true)
	           {
		     mProgressDialog.dismiss();
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
		    	   Intent fireBackDetPg=new Intent(ProductOrderFilterSearch.this,ReturnActivity.class);
		           fireBackDetPg.putExtra("storeID", storeID);
		           fireBackDetPg.putExtra("SN", SN);
		           fireBackDetPg.putExtra("bck", 1);
		           fireBackDetPg.putExtra("imei", imei);
		           fireBackDetPg.putExtra("userdate", date);
		           fireBackDetPg.putExtra("pickerDate", pickerDate);
		           fireBackDetPg.putExtra("OrderPDAID", strGlobalOrderID);
		           fireBackDetPg.putExtra("flgPageToRedirect", "1");
		          // fireBackDetPg.putExtra("rID", routeID);

		           startActivity(fireBackDetPg);
		           finish();
		     }

		     else if(isReturnClkd==2)
		     {
		    	 //Intent fireBackDetPg=new Intent(ProductOrderSearch.this,POSMaterialActivity.class);
		    	Intent fireBackDetPg=new Intent(ProductOrderFilterSearch.this,LastVisitDetails.class);
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
					  // System.out.println("LOCATION(GPS)  LATTITUDE: " +lattitude + "LONGITUDE:" + longitude+ "accuracy:" + accuracy);
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
					 //  System.out.println("LOCATION(N/W)  LATTITUDE: " +lattitude1 + "LONGITUDE:" + longitude1+ "accuracy:" + accuracy1);
					  // Toast.makeText(this, " LOCATION(NW) \n LATTITUDE: " +lattitude + "\nLONGITUDE:" + longitude, Toast.LENGTH_LONG).show();
					   //text1.setText(" LOCATION(N/W) \n LATTITUDE: " +lattitude1 + "\nLONGITUDE:" + longitude1+ "\naccuracy:" + accuracy1);

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
					 //  fnAccurateProvider="";
					   if(fnAccurateProvider.equals(""))
					   {
						   if(pDialog2STANDBY.isShowing())
						   {
							   pDialog2STANDBY.dismiss();
						   }
						   //alert ... try again nothing found // return back

						  // Toast.makeText(getApplicationContext(), "Please try again, No Fused,GPS or Network found.", Toast.LENGTH_LONG).show();

						  showAlertForEveryOne(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertTryAgain));
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
						  // 28.4873017  77.1045772   10.0
						 //  if(!checkLastFinalLoctionIsRepeated("28.4873017", "77.1045772", "10.0"))
						   if(!checkLastFinalLoctionIsRepeated(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy)))
						   {

							   fnCreateLastKnownFinalLocation(String.valueOf(fnLati), String.valueOf(fnLongi), String.valueOf(fnAccuracy));
							   UpdateLocationAndProductAllData();
						   }
						   else
						   {countSubmitClicked++;
							   if(countSubmitClicked==1)
							   {
								   AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProductOrderFilterSearch.this);

								   // Setting Dialog Title
								   alertDialog.setTitle(ProductOrderFilterSearch.this.getResources().getString(R.string.genTermInformation));
								   alertDialog.setIcon(R.drawable.error_info_ico);
								   alertDialog.setCancelable(false);
								   // Setting Dialog Message
								   alertDialog.setMessage(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertSameLoc));

								   // On pressing Settings button
								   alertDialog.setPositiveButton(ProductOrderFilterSearch.this.getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
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

		      class IAmABackgroundTask extends
		         AsyncTask<String, Integer, Boolean> {
		     @SuppressWarnings("static-access")
		  @Override
		     protected void onPreExecute() {
		      createProductPrepopulateDetailWhileSearch(CheckIfStoreExistInStoreProdcutPurchaseDetails);
		     }

		     @Override
		     protected void onPostExecute(Boolean result) {


		      fnAbhinav(1000);
		     }

		     @Override
		     protected Boolean doInBackground(String... params) {


		         return true;

		     }

		 }
}
