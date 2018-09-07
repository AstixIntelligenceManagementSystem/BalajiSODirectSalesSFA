package project.astix.com.balajisosfadirectsales;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;


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
import android.location.Location;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonInfo;

@SuppressWarnings("deprecation")
public class ReturnActivity extends BaseActivity implements OnItemSelectedListener, OnClickListener, DeletePic,OnFocusChangeListener
{
	public int flgDrctslsIndrctSls=0;
	public String StoreVisitCode="NA";
	public  Dialog dialog=null;
	private Camera mCamera;
	private CameraPreview mPreview;
	private Camera.PictureCallback mPicture;
	private Button capture,cancelCam, switchCamera;
	private Context myContext;
	private LinearLayout cameraPreview;
	private boolean cameraFront = false;
	ImageView flashImage;
	private boolean isLighOn = false;
	//String imageName;
	float mDist=0;

	private String FilePathStrings;
	private String FileNameStrings;
	private File[] listFile;
	public String strGlobalInvoiceNumber="NA";
	public int chkflgInvoiceAlreadyGenerated=0;
	File fileintial;


	ServiceWorker service;

	public Bitmap finalBitmap = null;
	public static Bitmap mIcon1;
	public int maxLIMIT;
	public String flgPageToRedirect="0";
	ProgressDialog PDpicTasker;
	//	private static final String DATASUBDIRECTORY = "NMPphotos";
//	private static int TAKE_PICTURE = 1;
	public String fIMGname;
	public static String outletpicpathIO;
	//	private Uri outputFileUri;
	public String fnameIMG;
	boolean isSaveAndExit=false;
	String productIdPhoto;
	String encodedImage;
	String imageName;
	public static String[] imgs;
	public static String[] comments;
	String productIdOnLastEditText;
	File imageF;
	Uri uriSavedImage;
	String onlyDate;
	String photoClickedDateTime="";
	String productPhotoDetail;
	String returnReason;
	int picAddPosition=0;
	int removePicPositin=0;
	public  String[] arrSchId;
	public String SchemeDesc="NA";

	public String storeID;
	public String OrderPDAID;
	public String imei;
	public String date;
	public String pickerDate;
	public String SN;
	public String routeID;
	public ProgressDialog pDialogSync;
	public String productID;
	String spinnerCategorySelected;
	String spinnerCategoryIdSelected;
	Location lastKnownLoc;

	int countParentView;
	String alrtSlctPrdctNameId,alrtSpnr_EditText_Value;
	String alrtPrdctId;
	String alrtValueSpnr;
	private boolean alrtStopResult = false;


	int alrtObjectTypeFlag=1; //1=spinner,edittext=0;
	// Decimal Format
	Locale locale  = new Locale("en", "UK");
	String pattern = "###.##";
	DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
	int CheckIfStoreExistInStoreProdcutPurchaseDetails=0;
	ProgressDialog mProgressDialog;
	ProgressDialog mProgressDialogA;

	ArrayAdapter adapterReason;
	List<String> listReason=new ArrayList<String>();
//****************************************************************
	//Field

	ImageAdapter adapterImage;
	GridView recycler_view_images;
	View viewProduct;
	TextView txtVw_schemeApld;
	EditText alrtcrntEditTextValue;
	TextView alrtPrvsPrdctSlctd;
	Spinner alrtPrvsSpnrSlctd;
	Spinner spinner_category;
	ImageView btn_bck;
	public LinearLayout ll_prdct_detal;
	Spinner spnrRsn;
	Spinner spnrRsnSave;

	ImageView image;

	TextView txt_product_Name;
	TextView txt_reason;
	TextView txtVwRate;
	TextView textViewFreeQty,textViewRate,textViewDiscount;
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
	LinearLayout ll_scheme_detail;
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


	//****************************************************************
	//Arrays, HashMap
	View[] hide_View;
	String[] categoryNames;
	public String[] prductId;

	EditText editTextLastFocus;
	LinkedHashMap<String, String> hmapRtrnRsn=new LinkedHashMap<String, String>();
	HashMap<String,String> hmapKPhotoNameVProductIdRsn=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=ReturnDetail
	HashMap<String, String> hmapRtrnPrdctDetail=new HashMap<String, String>();
	HashMap<String, String> hmapEDReason=new HashMap<String, String>();
	HashMap<String, String> hmapFilledEDReason=new HashMap<String, String>();

	//hmapCtgryPrdctDetail= key=prdctId,val=CategoryID
	HashMap<String, String> hmapCtgryPrdctDetail=new HashMap<String, String>();

	//hmapCtgryPrdctDetail= key=prdctId,val=ProductName
	HashMap<String, String> hmapPrdctIdPrdctName=new HashMap<String, String>();
	//hmapCtgryPrdctDetail= key=prdctId,val=Stock
	HashMap<String, String> hmapPrdctIdPrdctStock=new HashMap<String, String>();

	//hmapCtgryPrdctDetail= key=prdctId,val=ProductOrderQty,Reason
	HashMap<String, String> hmapPrdctIdPrdctRtrnQtyReason=new HashMap<String, String>();



	// HashMap PhotoDetail;
	HashMap<String,String> hmapPhotoDetail=new HashMap<String, String>();
	LinkedHashMap<String, String> hmapctgry_details=new LinkedHashMap<String, String>();

	HashMap<String, String> hmapProductToBeFree=new HashMap<String, String>();    //Not is use

	ArrayList<HashMap<String, String>> arrLstHmapPrdct=new ArrayList<HashMap<String,String>>();




	//hmapCtgryPrdctDetail= key=prdctId,val=CategoryID


	//hmapCtgryPrdctDetail= key=prdctId,val=PrdString Applied Schemes,Slabs and other details
	HashMap<String, String> hmapProductRelatedSchemesList=new HashMap<String, String>();


	// hmapSchemeIdStoreID= key=SchemeId value StoreId
	HashMap<String, String> hmapSchemeIdStoreID=new HashMap<String, String>();

	// hmapSchmSlabIdSchmID key=SchemeSlabId value = SchemeID
	HashMap<String, String> hmapSchmSlabIdSchmID=new HashMap<String, String>();

	// hmaSchemeSlabIdSlabDes key=SchemeSlabId value=SchemeSlab Description
	HashMap<String, String> hmapSchemeSlabIdSlabDes=new HashMap<String, String>();

	// hmapSchemeSlabIdBenifitDes key=SchemeSlabId value = BenifitDescription
	HashMap<String, String> hmapSchemeSlabIdBenifitDes=new HashMap<String, String>();

	// hmapProductIdOrdrVal key=Product Id value=ProductOrderVal
	HashMap<String, String> hmapProductIdOrdrVal =new HashMap<String, String>();

	// hmapProductIdStock key = ProductID value= Stock
	HashMap<String, String> hmapProductIdStock=new HashMap<String, String>();

	ArrayList<HashMap<String, String>> arrayListSchemeSlabDteail=new ArrayList<HashMap<String,String>>();
	//hmapSchmeSlabIdSchemeId= key =SchemeSlabId         value=SchemeID
	HashMap<String, String> hmapSchmeSlabIdSchemeId=new HashMap<String, String>();
	//hmapSchmeSlabIdSlabDes= key =SchemeSlabId         value=SchemeSlabDes
	HashMap<String, String> hmapSchmeSlabIdSlabDes=new HashMap<String, String>();
	//hmapSchmeSlabIdBenifitDes= key = SchemeSlabId        value=BenifitDescription
	HashMap<String, String> hmapSchmeSlabIdBenifitDes=new HashMap<String, String>();
	//HashMap<String, String> hmapSchemeStoreID=new HashMap<String, String>();



	//hmapProductVolumePer= key =ProductID         value=Per
	HashMap<String, String> hmapProductVolumePer=new HashMap<String, String>();


	//hmapProductVolumePer= key =ProductID         value=even/odd
	HashMap<String, String> hmapProductViewTag=new HashMap<String, String>();


	HashMap<String, HashMap<String, HashMap<String, String>>> hmapPrdtAppliedSchIdsAppliedSlabIdsDefination;

	public int flgApplyFreeProductSelection=0;

	ArrayList<String> arredtboc_OderQuantityFinalSchemesToApply;

	//Database

	PRJDatabase dbengine = new PRJDatabase(this);
	DatabaseAssistant DA = new DatabaseAssistant(this);

	//Common Controls Box

	EditText edtBox;
	TextView viewBox;
	String viewCurrentBoxValue="";

	public int powerCheck=0;

	public  PowerManager.WakeLock wl;

	public void onDestroy()
	{
		super.onDestroy();
		wl.release();


	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_return_list);

		service=new ServiceWorker();
		hmapRtrnRsn=dbengine.getReasonReturn();
		initializeFields();

		if(powerCheck==0)
		{
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
			wl.acquire();
		}
	}




	public void initializeFields()
	{


		Button btn_SaveExit=(Button) findViewById(R.id.btn_saveExitRtrn);
		btn_SaveExit.setOnClickListener(this);

		Button btn_Save=(Button) findViewById(R.id.btn_saveRtrn);
		btn_Save.setOnClickListener(this);



		spinner_category=(Spinner) findViewById(R.id.spinner_category);
		btn_bck=(ImageView) findViewById(R.id.btn_bck);
		txt_product_Name=(TextView) findViewById(R.id.txt_product_Name);
		txt_reason=(TextView) findViewById(R.id.txt_reason);
		ll_prdct_detal=(LinearLayout) findViewById(R.id.ll_prdct_detal);
		ll_scheme_detail=(LinearLayout) findViewById(R.id.ll_scheme_detail);
		recycler_view_images=(GridView) findViewById(R.id.recycler_view_images);




		List<String> listReason=new ArrayList<String>();

		int index=0;
		for(Map.Entry<String, String> entry:hmapRtrnRsn.entrySet())
		{
			if(index==0)
			{
				listReason.add("Select Stock Type");
				index++;
			}
			listReason.add(entry.getKey());
		}

		// old design of drop down with radiobutton
		//adapterReason=new ArrayAdapter(ReturnActivity.this, android.R.layout.simple_spinner_item,listReason);
		//adapterReason.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// new design of drop down without radiobutton
		adapterReason=new ArrayAdapter(ReturnActivity.this, R.layout.initial_spinner_text,listReason);
		adapterReason.setDropDownViewResource(R.layout.spina);



		adapterImage = new ImageAdapter(this);
		recycler_view_images.setAdapter(adapterImage);



		btn_bck.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				if(hmapRtrnPrdctDetail!=null)
				{
					if(validateReason())
					{
						if(editTextLastFocus!=null)
						{
							productID=(editTextLastFocus.getTag().toString()).split(Pattern.quote("_"))[1];

							spnrRsnSave=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+productID);
							String selected_text;
							int position=0;
							if (spnrRsnSave.getSelectedItem() != null)
							{
								selected_text = spnrRsnSave.getSelectedItem().toString();
								position=adapterReason.getPosition(selected_text);
							}
							else
							{
								selected_text="Please Select Stock Type";
							}
							//routeID,storeID,returnProductID,prodReturnQty,prodReturnReason, prodReturnReasonIndex,returnDate, _outstat
							hmapRtrnPrdctDetail.put(productID, routeID+"~"+storeID+"~"+editTextLastFocus.getText().toString()+"~"+selected_text+"~"+String.valueOf(position)+"~"+date+"~"+1);//"0");

						}
						getAllReturnReason();

						//Validate condition

						new SetDataToDatabase().execute("1~2");
					}

				}
				else
				{
					if(flgPageToRedirect.equals("1"))
					{
						Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductOrderEntry.class);
						fireBackDetPg.putExtra("storeID", storeID);
						fireBackDetPg.putExtra("SN", SN);
						fireBackDetPg.putExtra("bck", 1);
						fireBackDetPg.putExtra("imei", imei);
						fireBackDetPg.putExtra("userdate", date);
						fireBackDetPg.putExtra("pickerDate", pickerDate);
						fireBackDetPg.putExtra("rID", routeID);

						startActivity(fireBackDetPg);
						finish();
					}
					if(flgPageToRedirect.equals("2"))
					{
						Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductOrderReview.class);
						fireBackDetPg.putExtra("storeID", storeID);
						fireBackDetPg.putExtra("SN", SN);
						fireBackDetPg.putExtra("bck", 1);
						fireBackDetPg.putExtra("imei", imei);
						fireBackDetPg.putExtra("userdate", date);
						fireBackDetPg.putExtra("pickerDate", pickerDate);
						fireBackDetPg.putExtra("rID", routeID);

						startActivity(fireBackDetPg);
						finish();
					}

				}
			}
		});

		spinner_category.setOnItemSelectedListener(this);


		getDataFromIntent();
		StoreVisitCode=dbengine.fnGetStoreVisitCode(storeID);


		new GetData().execute();
	}

	public class GetData extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(ReturnActivity.this);
			mProgressDialog.setTitle(getText(R.string.genTermPleaseWaitNew));
			mProgressDialog.setMessage(getText(R.string.Loading));
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			//hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();
			if(CommonInfo.FlgDSRSO==1)
			{
				String SOCoverageAreaIDAndType=dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();
				flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[0]),Integer.parseInt(SOCoverageAreaIDAndType.split(Pattern.quote("^"))[1]));
			}
			else
			{
				flgDrctslsIndrctSls=dbengine.fnGetflgDrctslsIndrctSls(CommonInfo.CoverageAreaNodeID,CommonInfo.CoverageAreaNodeType);
			}
            chkflgInvoiceAlreadyGenerated=dbengine.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode,flgDrctslsIndrctSls);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
            if(chkflgInvoiceAlreadyGenerated==1)
            {
                strGlobalInvoiceNumber=dbengine.fnGetExistingInvoiceNumber(storeID);
            }
			CheckIfStoreExistInStoreProdcutPurchaseDetails=dbengine.fnCheckIfStoreExistInStoreProdcutPurchaseDetails(storeID,strGlobalInvoiceNumber,flgDrctslsIndrctSls,chkflgInvoiceAlreadyGenerated);
			getCategoryDetail();

			getProductData();
			return null;
		}
		@Override
		protected void onPostExecute(Void args)
		{


			ArrayAdapter adapterCategory=new ArrayAdapter(ReturnActivity.this, android.R.layout.simple_spinner_item,categoryNames);
			adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_category.setAdapter(adapterCategory);

			createProductDetail(CheckIfStoreExistInStoreProdcutPurchaseDetails);
			// Check for SD Card
			if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{

			}
			else
			{
				// Locate the image folder in your SD Card
				fileintial = new File(Environment.getExternalStorageDirectory()+ File.separator + CommonInfo.ImagesFolder);
				// Create a new folder if no folder named SDImageTutorial exist
				fileintial.mkdirs();
			}

			if (fileintial.isDirectory()) {
				listFile = fileintial.listFiles();

				hmapKPhotoNameVProductIdRsn=dbengine.getProductPicInfo(listFile,storeID,strGlobalInvoiceNumber);
				String productIdtoBeSetOnAdapter;

				for (int i = 0; i < listFile.length; i++) {
					// Get the path of the image file
					if(hmapKPhotoNameVProductIdRsn.containsKey(listFile[i].getName()))
					{
						FilePathStrings = listFile[i].getAbsolutePath();
						Bitmap bmp = decodeSampledBitmapFromFile(FilePathStrings, 160, 160);
						//Bitmap bmp = BitmapFactory.decodeFile(FilePathStrings);
						// Get the name image file
						FileNameStrings = listFile[i].getName();
						removePicPositin=picAddPosition;

						hmapPhotoDetail.put(((hmapKPhotoNameVProductIdRsn.get(FileNameStrings)).split(Pattern.quote("~"))[0].toString()), (hmapKPhotoNameVProductIdRsn.get(FileNameStrings)).split(Pattern.quote("~"))[1].toString()+"~"+hmapPrdctIdPrdctName.get((hmapKPhotoNameVProductIdRsn.get(FileNameStrings)).split(Pattern.quote("~"))[0].toString()));
						adapterImage.add(picAddPosition,bmp,listFile[i].getName());
						picAddPosition++;
					}

				}
			}
			mProgressDialog.dismiss();
		}

	}

	@Override
	public void onClick(View v) {
		if(v.getId()== R.id.imgVw_Photo)
		{
			productIdPhoto=(v.getTag().toString()).split(Pattern.quote("_"))[1].toString();
			Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			spnrRsn=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+productIdPhoto);

			saveImage();

			openCustomCamara();
			/*
			imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
			startActivityForResult(imageIntent,
					100);*/
		}

		if(v.getId()==R.id.btn_saveExitRtrn) {

			// TODO Auto-generated method stub
			if(hmapRtrnPrdctDetail!=null)
			{
				if(validateReason())
				{
					if(editTextLastFocus!=null)
					{
						productID=(editTextLastFocus.getTag().toString()).split(Pattern.quote("_"))[1];

						spnrRsnSave=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+productID);
						String selected_text;
						int position=0;
						if (spnrRsnSave.getSelectedItem() != null)
						{
							selected_text = spnrRsnSave.getSelectedItem().toString();
							position=adapterReason.getPosition(selected_text);
						}
						else
						{
							selected_text="Please Select Stock Type";
						}
						//routeID,storeID,returnProductID,prodReturnQty,prodReturnReason, prodReturnReasonIndex,returnDate, _outstat
						hmapRtrnPrdctDetail.put(productID, routeID+"~"+storeID+"~"+editTextLastFocus.getText().toString()+"~"+selected_text+"~"+String.valueOf(position)+"~"+date+"~"+"1");//"0");
					}



					getAllReturnReason();
					new SetDataToDatabase().execute("1~2");
				}

			}
			else
			{
				if(flgPageToRedirect.equals("1"))
				{
					Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductOrderEntry.class);
					fireBackDetPg.putExtra("storeID", storeID);
					fireBackDetPg.putExtra("SN", SN);
					fireBackDetPg.putExtra("bck", 1);
					fireBackDetPg.putExtra("imei", imei);
					fireBackDetPg.putExtra("userdate", date);
					fireBackDetPg.putExtra("pickerDate", pickerDate);
					fireBackDetPg.putExtra("rID", routeID);

					startActivity(fireBackDetPg);
					finish();
				}
				if(flgPageToRedirect.equals("2"))
				{
					Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductOrderReview.class);
					fireBackDetPg.putExtra("storeID", storeID);
					fireBackDetPg.putExtra("SN", SN);
					fireBackDetPg.putExtra("bck", 1);
					fireBackDetPg.putExtra("imei", imei);
					fireBackDetPg.putExtra("userdate", date);
					fireBackDetPg.putExtra("pickerDate", pickerDate);
					fireBackDetPg.putExtra("rID", routeID);

					startActivity(fireBackDetPg);
					finish();
				}

			}
		}
		if(v.getId()==R.id.btn_saveRtrn)
		{

			// TODO Auto-generated method stub
			if(hmapRtrnPrdctDetail!=null)
			{
				if(validateReason())
				{
					if(editTextLastFocus!=null)
					{
						productID=(editTextLastFocus.getTag().toString()).split(Pattern.quote("_"))[1];

						spnrRsnSave=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+productID);
						String selected_text;
						int position=0;
						if (spnrRsnSave.getSelectedItem() != null)
						{
							selected_text = spnrRsnSave.getSelectedItem().toString();
							position=adapterReason.getPosition(selected_text);
						}
						else
						{
							selected_text="Please Select Stock Type";
						}
						//routeID,storeID,returnProductID,prodReturnQty,prodReturnReason, prodReturnReasonIndex,returnDate, _outstat
						hmapRtrnPrdctDetail.put(productID, routeID+"~"+storeID+"~"+editTextLastFocus.getText().toString()+"~"+selected_text+"~"+String.valueOf(position)+"~"+date+"~"+"1");//+"0");

					}
					getAllReturnReason();
					new SetDataToDatabase().execute("0");
				}

			}




		}



	}


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
							   long id) {



		spinnerCategorySelected = spinner_category.getSelectedItem().toString();

		filterProduct(spinnerCategorySelected);



	}


	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	private void getDataFromIntent()
	{


		Intent intentData=getIntent();
		Intent passedvals = getIntent();

		storeID = passedvals.getStringExtra("storeID");
		imei = passedvals.getStringExtra("imei");
		date = passedvals.getStringExtra("userdate");
		pickerDate = passedvals.getStringExtra("pickerDate");
		SN = passedvals.getStringExtra("SN");
		OrderPDAID= passedvals.getStringExtra("OrderPDAID");
		flgPageToRedirect= passedvals.getStringExtra("flgPageToRedirect");
		dbengine.open();
		routeID= dbengine.GetActiveRouteID();
		dbengine.close();



		//passedvals.getStringExtra("rID");

		//dbengine.fnProductWiseAppliedScehmeSlabDetails(storeIdProd);
		// hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();
		//hmapPrdtAppliedSchIdsAppliedSlabIdsDefination=dbengine.fnProductWiseAppliedScehmeSlabDetails(StoreID);

		// hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();

	}


	private void getCategoryDetail()
	{

		hmapctgry_details=dbengine.fetch_Category_List_Return(storeID);

		int index=0;
		if(hmapctgry_details!=null)
		{
			categoryNames=new String[hmapctgry_details.size()];

			Set set2 = hmapctgry_details.entrySet();
			Iterator iterator = set2.iterator();
			while(iterator.hasNext())
			{
				Map.Entry me2 = (Map.Entry)iterator.next();
				categoryNames[index]=me2.getKey().toString();
				index=index+1;
			}
		}


	}

	private void getProductData() {
		// CategoryID,ProductID,ProductShortName,ProductRLP,Date/Qty)

		arrLstHmapPrdct=dbengine.fetch_catgry_prdctReturnData(storeID,OrderPDAID,strGlobalInvoiceNumber);
		hmapFilledEDReason=dbengine.getSavedRemark(storeID,OrderPDAID,strGlobalInvoiceNumber);
		if(arrLstHmapPrdct.size()>0)
		{
			hmapCtgryPrdctDetail=arrLstHmapPrdct.get(0);
			hmapPrdctIdPrdctName=arrLstHmapPrdct.get(1);
			hmapPrdctIdPrdctRtrnQtyReason=arrLstHmapPrdct.get(2);
			hmapPrdctIdPrdctStock=arrLstHmapPrdct.get(3);
		}


	}

	public void createProductDetail(int CheckIfStoreExistInStoreProdcutPurchaseDetails) {
		System.out.println("Abhinav Nitish Ankit New :"+CheckIfStoreExistInStoreProdcutPurchaseDetails);
		hide_View=new View[hmapCtgryPrdctDetail.size()];
		prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);
		if(prductId.length>0)
		{


			LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			for(int position=0;position<hmapCtgryPrdctDetail.size();position++)
			{


				countParentView=position;
				viewProduct=inflater.inflate(R.layout.list_item_card_return,null);

				viewProduct.setTag(hmapCtgryPrdctDetail.get(prductId[position])+"_"+prductId[position]);
				hide_View[position]=viewProduct;
				hmapProductViewTag.put(viewProduct.getTag().toString(), "even");
				LinearLayout ll_sample = (LinearLayout) viewProduct.findViewById(R.id.ll_sample);

				TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);

				tv_product_name.setTag("tvProductName"+"_"+prductId[position]);
				tv_product_name.setText(hmapPrdctIdPrdctName.get(prductId[position]));
				EditText ed_Remark=(EditText) viewProduct.findViewById(R.id.ed_Remark);
				ed_Remark.setTag("ed_remark_"+prductId[position]);
				ImageView imgVw_Photo=(ImageView) viewProduct.findViewById(R.id.imgVw_Photo);
				imgVw_Photo.setTag("imgVwPhoto"+"_"+prductId[position]);
				imgVw_Photo.setOnClickListener(this);




				final TextView et_Stock=(TextView) viewProduct.findViewById(R.id.txtVw_Stock);
				et_Stock.setTag("txtVwStock"+"_"+prductId[position]);
				if(hmapPrdctIdPrdctStock.containsKey(prductId[position]))
				{
					et_Stock.setText(hmapPrdctIdPrdctStock.get(prductId[position]));
				}
				else
				{
					et_Stock.setText("0");
				}
				if(hmapFilledEDReason.containsKey(prductId[position]))
				{
					ed_Remark.setText(hmapFilledEDReason.get(prductId[position]));
				}
				else
				{

				}

				final Spinner spnr_comment=(Spinner) viewProduct.findViewById(R.id.spnr_comment_Rtrn);
				spnr_comment.setTag("spnrReason_"+prductId[position]);
				spnr_comment.setAdapter(adapterReason);
				hmapEDReason.put(prductId[position], "");
				spnr_comment.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
											   int position, long id) {

						String productIdOfSpnr=(spnr_comment.getTag().toString()).split(Pattern.quote("_"))[1];
						EditText edForProductReturn=(EditText) ll_prdct_detal.findViewWithTag("etReturnQty_"+productIdOfSpnr);
						dbengine.open();
						dbengine.updateReason(storeID, productIdOfSpnr, spnr_comment.getSelectedItem().toString());
						dbengine.close();
						String sipnerval="0";
						hmapPhotoDetail.put(productIdOfSpnr, spnr_comment.getSelectedItem().toString()+"~"+hmapPrdctIdPrdctName.get(productIdOfSpnr));
						if(hmapRtrnRsn.containsKey(spnr_comment.getSelectedItem().toString()))
						{
							sipnerval=hmapRtrnRsn.get(spnr_comment.getSelectedItem().toString());
						}

						hmapRtrnPrdctDetail.put(productIdOfSpnr, routeID+"~"+storeID+"~"+edForProductReturn.getText().toString()+"~"+""+"~"+sipnerval+"~"+date+"~"+"1");//"0");

					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
				if(hmapPrdctIdPrdctRtrnQtyReason.containsKey(prductId[position]))
				{
					for(Map.Entry<String, String> entry:hmapRtrnRsn.entrySet())
					{
						String indexId= hmapPrdctIdPrdctRtrnQtyReason.get(prductId[position]).split(Pattern.quote("^"))[1];
						if(indexId.equals(entry.getValue()))
						{
							spnr_comment.setSelection(adapterReason.getPosition(entry.getKey()));
						}
					}

				}
				else
				{
					spnr_comment.setSelection(0);
				}

				// spnr_comment.setEnabled(false);

				final EditText et_ReturnQty=(EditText) viewProduct.findViewById(R.id.et_ReturnQty);
				et_ReturnQty.setTag("etReturnQty"+"_"+prductId[position]);
				if(hmapPrdctIdPrdctRtrnQtyReason.containsKey(prductId[position]))
				{
					et_ReturnQty.setText((hmapPrdctIdPrdctRtrnQtyReason.get(prductId[position])).split(Pattern.quote("^"))[0]);
				}
				else
				{

					et_ReturnQty.setText("");
					et_ReturnQty.setHint(getText(R.string.RetQty));
					spnr_comment.setEnabled(false);
				}
				et_ReturnQty.setOnFocusChangeListener(this);
				et_ReturnQty.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start, int before, int count) {
						String numberRtrnQty = null;
						if(s.toString().equals(""))
						{
							numberRtrnQty="0";
						}
						else
						{
							numberRtrnQty=s.toString();
						}
						if(Integer.parseInt(hmapPrdctIdPrdctStock.get(((et_ReturnQty.getTag().toString()).split(Pattern.quote("_")))[1]))<Integer.parseInt(numberRtrnQty))
						{
							et_ReturnQty.setText("");
							et_ReturnQty.setFocusable(true);
							// whatever color you want
							String estring = ReturnActivity.this.getResources().getString(R.string.RetQTyExcStock);
							ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.WHITE);
							SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
							ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);

							et_ReturnQty.setError(ssbuilder);
						}
						else
						{

							et_ReturnQty.setError(null);
						}
						editTextLastFocus=et_ReturnQty;

					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
												  int after) {


					}

					@Override
					public void afterTextChanged(Editable s) {
						if(!TextUtils.isEmpty(et_ReturnQty.getText()))
						{
							spnr_comment.setEnabled(true);
						}
						else
						{
							spnr_comment.setEnabled(false);
						}
					}
				});

	   /*	hmapCtgryPrdctDetail=arrLstHmapPrdct.get(0);
		hmapPrdctIdPrdctName=arrLstHmapPrdct.get(1);
		hmapPrdctIdPrdctRtrnQtyReason=arrLstHmapPrdct.get(2);
		hmapPrdctIdPrdctStock=arrLstHmapPrdct.get(3);*/


				ll_prdct_detal.addView(viewProduct);
			}
		}

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
				Map.Entry me2 = (Map.Entry)iterator.next();
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
				Map.Entry me2 = (Map.Entry)iterator.next();
				stringArray[index]=me2.getValue().toString();
				System.out.println("Betu Slab = "+stringArray[index]);
				index=index+1;
			}
		}
		return stringArray;
	}


	private void filterProduct(String slctdProduct)
	{

		spinnerCategoryIdSelected=hmapctgry_details.get(slctdProduct);

		int currentPos=1;



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
					hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_return_even);
					hide_View[posHideVsbl].setTag(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl]+"_"+"even");
					hmapProductViewTag.put(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl], "even");
				}
				else
				{
					hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_return_odd);
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
						hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_return_even);
						hide_View[posHideVsbl].setTag(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl]+"_"+"even");
						hmapProductViewTag.put(hmapCtgryPrdctDetail.get(prductId[posHideVsbl])+"_"+prductId[posHideVsbl], "even");
					}
					else

					{
						hide_View[posHideVsbl].setBackgroundResource(R.drawable.card_background_return_odd);
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
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
	}

	public void saveImage()
	{//photoClickedDateTime
		long syncTIMESTAMP = System.currentTimeMillis();
		Date datefromat = new Date(syncTIMESTAMP);
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
		SimpleDateFormat dfClickedDateTime = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS",Locale.ENGLISH);
		onlyDate=df.format(datefromat);
		photoClickedDateTime=dfClickedDateTime.format(datefromat);
		onlyDate=onlyDate.replace(":","").trim().replace("-", "").replace(" ","").trim().toString();

		File imagesFolder = new File(Environment.getExternalStorageDirectory(), CommonInfo.ImagesFolder);
		if (!imagesFolder.exists())
		{
			imagesFolder.mkdirs();

		}

		imageName=imei+"~"+storeID+"~"+productIdPhoto+"~"+onlyDate+".jpg";

		imageF = new File(imagesFolder,imageName);

		try {
			FileOutputStream fo = new FileOutputStream(imageF);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		uriSavedImage = Uri.fromFile(imageF);





	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			if (resultCode == this.RESULT_OK) {
				File file=imageF;
				System.out.println("File +++"+imageF);

				Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 160, 160);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();

				bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
				byte[] byteArray = stream.toByteArray();

				// Convert ByteArray to Bitmap::

				Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
						byteArray.length);
				long syncTIMESTAMP = System.currentTimeMillis();
				Date dateobj = new Date(syncTIMESTAMP);
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
				String clkdTime = df.format(dateobj);

				imageName=file.getName();


				if(bitmap!=null)
				{
					if(true)
					{
						dbengine.insertPhotoDetail(storeID,productIdPhoto,clkdTime,imageName,spnrRsn.getSelectedItem().toString(),"1",uriSavedImage.toString(),1,OrderPDAID,strGlobalInvoiceNumber);
						removePicPositin=picAddPosition;

						hmapPhotoDetail.put(productIdPhoto, spnrRsn.getSelectedItem().toString()+"~"+hmapPrdctIdPrdctName.get(productIdPhoto));
						adapterImage.add(picAddPosition,bitmap,imageName);
						System.out.println("Picture Adapter"+picAddPosition);
						picAddPosition++;
					}
				}

				encodedImage=Base64.encodeToString(byteArray, Base64.DEFAULT);

				//new SyncImgTasker().execute();


			}
		}
	}

	private class SyncImgTasker extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			mProgressDialog = new ProgressDialog(ReturnActivity.this);
			mProgressDialog.setTitle(getText(R.string.genTermPleaseWaitNew));
			mProgressDialog.setMessage(getText(R.string.Loading));
			mProgressDialog.setIndeterminate(true);
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();

		}

		@Override
		protected Void doInBackground(Void... params)
		{

			// Creating HTTP client
			HttpClient httpClient = new DefaultHttpClient();
			// Creating HTTP Post

			// it for Development

			// HttpPost httpPost = new HttpPost("http://115.124.126.184/paragDairySFA/PDA/FrmStoreImageSync.aspx");

			HttpPost httpPost = new HttpPost(CommonInfo.ImageSyncPath.trim());

			// Building post parameters

			// key and value pair
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(5);
			nameValuePair.add(new BasicNameValuePair("image", encodedImage));
			nameValuePair.add(new BasicNameValuePair("FileName",imageName));
			nameValuePair.add(new BasicNameValuePair("comment","NA"));
			nameValuePair.add(new BasicNameValuePair("storeID",storeID));
			nameValuePair.add(new BasicNameValuePair("date",date));
			nameValuePair.add(new BasicNameValuePair("routeID",routeID));

			// Url Encoding the POST parameters
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			} catch (UnsupportedEncodingException e) {
				// writing error to Log
				e.printStackTrace();
			}

			// Making HTTP Request
			try {
				HttpResponse response = httpClient.execute(httpPost);

				// writing response to log
				Log.d("Http Response:", response.toString());
			} catch (ClientProtocolException e) {
				// writing exception to log
				e.printStackTrace();
			} catch (IOException e) {
				// writing exception to log
				e.printStackTrace();

			}
			return null;
		}

		@Override
		protected void onCancelled() {
			Log.i("SvcMgr", "Service Execution Cancelled");
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			mProgressDialog.dismiss();

			Log.i("SvcMgr", "Service Execution cycle completed");

		}
	}



	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
	{ // BEST QUALITY MATCH

		//First decode with inJustDecodeBounds=true to check dimensions
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

	public void destroyNcleanup(int code){

		if(code == 1 && mIcon1 != null){
			mIcon1.recycle();
		}

		if(finalBitmap != null){
			finalBitmap.recycle();
		}

		System.gc();
		Runtime.getRuntime().gc();

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
	public void delPic(Bitmap bmp,String imageNameToDel)
	{
		removePicPositin=removePicPositin-1;
		picAddPosition=picAddPosition-1;
		//	String photoToBeDletedFromPath=dbengine.getPdaPhotoPath(imageNameToDel);

		dbengine.updatePhotoValidation("0", imageNameToDel);
		adapterImage.remove(bmp);
		txt_product_Name.setText(ReturnActivity.this.getResources().getString(R.string.txtProductName));
		txt_reason.setText(ReturnActivity.this.getResources().getString(R.string.ReturnsType));
		//  String file_dj_path = Environment.getExternalStorageDirectory() + "/RSPLSFAImages/"+imageNameToDel;
		String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imageNameToDel;

		File fdelete = new File(file_dj_path);
		if (fdelete.exists())
		{
			if (fdelete.delete())
			{
				Log.e("-->", "file Deleted :" + file_dj_path);
				callBroadCast();
			}
			else
			{
				Log.e("-->", "file not Deleted :" + file_dj_path);
			}
		}

	}


	@Override
	public void getProductPhotoDetail(String productIdTag)
	{

		//	imageName=imei+"~"+storeID+"~"+productIdPhoto+"~"+onlyDate+".jpg";
		txt_product_Name.setText((hmapPhotoDetail.get(productIdTag.split(Pattern.quote("~"))[2])).split(Pattern.quote("~"))[1].toString());
		txt_reason.setText((hmapPhotoDetail.get(productIdTag.split(Pattern.quote("~"))[2])).split(Pattern.quote("~"))[0].toString());
	}


	@Override
	public void onFocusChange(View v, boolean hasFocus) {

		if(!hasFocus)
		{

			EditText edRtrnQtyView=(EditText)v;
			edRtrnQtyView.setError(null);
			if(v.getId()==R.id.et_ReturnQty)
			{
				String productIDFocusLost = "0";
				edRtrnQtyView.setHint(getText(R.string.RetQty));
				if(!viewCurrentBoxValue.equals(edRtrnQtyView.getText().toString().trim()))
				{
					productIDFocusLost=edRtrnQtyView.getTag().toString().split(Pattern.quote("_"))[1].toString();
					spnrRsnSave=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+productIDFocusLost);

					String selected_text;
					int position=0;
					if (spnrRsnSave!=null && spnrRsnSave.getSelectedItem() != null)
					{
						selected_text = spnrRsnSave.getSelectedItem().toString();
						position=adapterReason.getPosition(selected_text);
					}
					else
					{
						selected_text="Please Select Stock Type";
					}


					hmapRtrnPrdctDetail.put(productIDFocusLost, routeID+"~"+storeID+"~"+edRtrnQtyView.getText().toString()+"~"+spnrRsnSave.getSelectedItem().toString()+"~"+String.valueOf(position)+"~"+date+"~"+"1");



				}

			}
		}
		else if(hasFocus)
		{
			EditText edRtrnQtyFocusView=(EditText)v;
			if(v.getId()==R.id.et_ReturnQty)
			{
				edRtrnQtyFocusView.setHint("");
				viewCurrentBoxValue=edRtrnQtyFocusView.getText().toString();
			}
		}
	}



	public class SetDataToDatabase extends AsyncTask<String, Void, Void>
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();

			mProgressDialogA = new ProgressDialog(ReturnActivity.this);
			mProgressDialogA.setTitle(getText(R.string.genTermPleaseWaitNew));
			mProgressDialogA.setMessage(getText(R.string.Loading));
			mProgressDialogA.setIndeterminate(true);
			mProgressDialogA.setCancelable(false);
			mProgressDialogA.show();
		}

		@Override
		protected Void doInBackground(String... params)
		{
			String executedData=params[0];
			int btnClkd;
			if(executedData.contains("~"))
			{
				isSaveAndExit=true;
			}


			dbengine.updateReturnData(hmapRtrnPrdctDetail,OrderPDAID,hmapEDReason,strGlobalInvoiceNumber);
			return null;
		}
		@Override
		protected void onPostExecute(Void args)
		{


			mProgressDialogA.dismiss();
			if(isSaveAndExit)
			{
				if(flgPageToRedirect.equals("1"))
				{
					Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductOrderEntry.class);
					fireBackDetPg.putExtra("storeID", storeID);
					fireBackDetPg.putExtra("SN", SN);
					fireBackDetPg.putExtra("bck", 1);
					fireBackDetPg.putExtra("imei", imei);
					fireBackDetPg.putExtra("userdate", date);
					fireBackDetPg.putExtra("pickerDate", pickerDate);
					fireBackDetPg.putExtra("rID", routeID);

					startActivity(fireBackDetPg);
					finish();
				}
				if(flgPageToRedirect.equals("2"))
				{
					Intent fireBackDetPg=new Intent(ReturnActivity.this,ProductOrderReview.class);
					fireBackDetPg.putExtra("storeID", storeID);
					fireBackDetPg.putExtra("SN", SN);
					fireBackDetPg.putExtra("bck", 1);
					fireBackDetPg.putExtra("imei", imei);
					fireBackDetPg.putExtra("userdate", date);
					fireBackDetPg.putExtra("pickerDate", pickerDate);
					fireBackDetPg.putExtra("rID", routeID);

					startActivity(fireBackDetPg);
					finish();
				}
			}
		}

	}

	public void getAllReturnReason()
	{
		for(Map.Entry<String, String> entry:hmapEDReason.entrySet())
		{
			//
			EditText edRemark=(EditText) ll_prdct_detal.findViewWithTag("ed_remark_"+entry.getKey());
			String valueRemark=edRemark.getText().toString();
			if(TextUtils.isEmpty(valueRemark))
			{

			}
			else
			{
				hmapEDReason.put(entry.getKey(), valueRemark);
			}
		}
	}


	public boolean validateReason()
	{
		boolean isFilledStockFilledWdReason=true;
		for(Map.Entry<String, String> entry:hmapCtgryPrdctDetail.entrySet())
		{
			EditText edTextStock=(EditText) ll_prdct_detal.findViewWithTag("etReturnQty_"+entry.getKey());
			int stock=0;
			if(!TextUtils.isEmpty(edTextStock.getText().toString()))
			{
				stock=Integer.valueOf(edTextStock.getText().toString());
			}
			if(stock>0)
			{
				//"spnrReason_"+prductId[position]
				Spinner spnrStockReason=(Spinner) ll_prdct_detal.findViewWithTag("spnrReason_"+entry.getKey());
				if(spnrStockReason.getSelectedItem().toString().equals("Select Stock Type"))
				{
					//"tvProductName"+"_"+prductId[position]
					isFilledStockFilledWdReason=false;
					TextView txtVWPrdctName=(TextView) ll_prdct_detal.findViewWithTag("tvProductName"+"_"+entry.getKey());
					alertError(getText(R.string.SelectReasonToReturn)+txtVWPrdctName.getText().toString());
					edTextStock.requestFocus();
					break;
				}
				else
				{
					isFilledStockFilledWdReason=true;
				}
			}
		}
		return isFilledStockFilledWdReason;
	}

	public void alertError(String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(ReturnActivity.this).create();

		// Setting Dialog Title
		alertDialog.setTitle(getText(R.string.AlertDialogHeaderMsg));

		// Setting Dialog Message
		alertDialog.setMessage(message);


		// Setting OK Button
		alertDialog.setButton(getText(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				dialog.dismiss();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	public void   openCustomCamara()
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
	private void handleZoom(MotionEvent event, Camera.Parameters params) {
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
	public void handleFocus(MotionEvent event, Camera.Parameters params) {
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

	@Override
	public void onStop() {
		super.onStop();
		if(mCamera!=null){
			mCamera.release();
			mCamera=null;
			if(dialog!=null){
				if(dialog.isShowing()){
					dialog.dismiss();

				}
			}
		}
	}

	private float getFingerSpacing(MotionEvent event) {
		// ...
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float)Math.sqrt(x * x + y * y);
	}
	private void setCameraDisplayOrientation(Activity activity,
											 int cameraId, Camera camera) {
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
				try {
					//write the file
					FileOutputStream fos = new FileOutputStream(pictureFile);
					fos.write(data);
					fos.close();

					//Toast toast = Toast.makeText(getActivity(), "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
					//toast.show();
					//put data here


					dialog.dismiss();
					if(pictureFile!=null)
					{
						File file=pictureFile;
						System.out.println("File +++"+pictureFile);
						imageName=pictureFile.getName();
						Bitmap bmp = decodeSampledBitmapFromFile(file.getAbsolutePath(), 160, 160);
						ByteArrayOutputStream stream = new ByteArrayOutputStream();

						bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
						byte[] byteArray = stream.toByteArray();

						// Convert ByteArray to Bitmap::

						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
								byteArray.length);
						uriSavedImage = Uri.fromFile(file);

						if(bitmap!=null)
						{
							if(true)
							{
								dbengine.insertPhotoDetail(storeID,productIdPhoto,photoClickedDateTime,imageName,spnrRsn.getSelectedItem().toString(),"1",uriSavedImage.toString(),1,OrderPDAID,strGlobalInvoiceNumber);
								removePicPositin=picAddPosition;

								hmapPhotoDetail.put(productIdPhoto, spnrRsn.getSelectedItem().toString()+"~"+hmapPrdctIdPrdctName.get(productIdPhoto));
								adapterImage.add(picAddPosition,bitmap,imageName);
								System.out.println("Picture Adapter"+picAddPosition);
								picAddPosition++;
							}
						}

						encodedImage=Base64.encodeToString(byteArray, Base64.DEFAULT);

						// Convert ByteArray to Bitmap::\
						//
						/*long syncTIMESTAMP = System.currentTimeMillis();
						Date dateobj = new Date(syncTIMESTAMP);
						SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						String clkdTime = df.format(dateobj);
						//	String valueOfKey=imagButtonTag+"~"+tempId+"~"+file.getAbsolutePath()+"~"+clkdTime+"~"+"2";
						String valueOfKey=clickedTagPhoto+"~"+AddNewStore_DynamicSectionWise.selStoreID+"~"+uriSavedImage.toString()+"~"+clkdTime+"~"+"1";
						//   helperDb.insertImageInfo(tempId,imagButtonTag, imageName, file.getAbsolutePath(), 2);
						Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
								byteArray.length);

						//
						setSavedImageToScrollView(bitmap, imageName,valueOfKey,clickedTagPhoto);*/
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


	OnClickListener captrureListener = new OnClickListener() {
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


	public   File getOutputMediaFile() {





		//make a new file directory inside the "sdcard" folder
		File mediaStorageDir = imageF;

		/*//if this "JCGCamera folder does not exist
		if (!mediaStorageDir.exists()) {
			//if you cannot make this folder return
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}

		//take the current timeStamp
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		//and make a media file:
		mediaFile = new File(mediaStorageDir.getPath() + imageName);*/

		return mediaStorageDir;
	}



	public void openCamera()
	{
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		dialog = new Dialog(ReturnActivity.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

		//dialog.setTitle("Calculation");
		dialog.setCancelable(false);
		dialog.setContentView(R.layout.activity_main);
		WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();


		parms.height=parms.MATCH_PARENT;
		parms.width=parms.MATCH_PARENT;
		cameraPreview = (LinearLayout)dialog. findViewById(R.id.camera_preview);

		mPreview = new CameraPreview(ReturnActivity.this, mCamera);
		cameraPreview.addView(mPreview);
		//onResume code
		if (!hasCamera(ReturnActivity.this)) {
			Toast toast = Toast.makeText(ReturnActivity.this, getText(R.string.txtNoCamera), Toast.LENGTH_LONG);
			toast.show();

		}
		if (mCamera == null) {
			//if the front facing camera does not exist
			if (findFrontFacingCamera() < 0) {
				Toast.makeText(ReturnActivity.this, getText(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
				switchCamera.setVisibility(View.GONE);
			}

			//mCamera = Camera.open(findBackFacingCamera());
			mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
      /*if(mCamera==null){
         mCamera=Camera.open(0);
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
				// params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
				params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
				params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);

				// params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

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
				Camera.Parameters params2= mCamera.getParameters();
				params2.setPictureFormat(ImageFormat.JPEG);
				params2.setJpegQuality(70);
				params2.setRotation(90);

				mCamera.setParameters(params2);
			}






			setCameraDisplayOrientation(ReturnActivity.this, Camera.CameraInfo.CAMERA_FACING_BACK,mCamera);
			mPicture = getPictureCallback();
			mPreview.refreshCamera(mCamera);
		}


		capture = (Button)dialog.  findViewById(R.id.button_capture);

		flashImage= (ImageView)dialog.  findViewById(R.id.flashImage);
		flashImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLighOn) {
					// turn off flash
					Camera.Parameters params = mCamera.getParameters();

					if (mCamera == null || params == null) {
						return;
					}


					params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
					mCamera.setParameters(params);
					flashImage.setImageResource(R.drawable.flash_off);
					isLighOn=false;
				} else {

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
		cancleCamera.setOnClickListener(new OnClickListener() {
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
}
