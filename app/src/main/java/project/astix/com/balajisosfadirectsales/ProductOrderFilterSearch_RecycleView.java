package project.astix.com.balajisosfadirectsales;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.Map.Entry;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;

import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class ProductOrderFilterSearch_RecycleView  extends BaseActivity
{
    // variable
    public TableLayout tbl1_dyntable_For_ExecutionDetails;
    public TableLayout tbl1_dyntable_For_OrderDetails;

    public  int flgLocationServicesOnOffOrderReview=0;
    public  int flgGPSOnOffOrderReview=0;
    public  int flgNetworkOnOffOrderReview=0;
    public  int flgFusedOnOffOrderReview=0;
    public  int flgInternetOnOffWhileLocationTrackingOrderReview=0;
    public  int flgRestartOrderReview=0;
    public  int flgStoreOrderOrderReview=0;

    public int powerCheck=0;



    int countSubmitClicked=0;
    ArrayList<String> productFullFilledSlabGlobal=new ArrayList<String>();

    TextView img_ctgry;
    String previousSlctdCtgry="";
    LocationRequest mLocationRequest;
    public int StoreCurrentStoreType=0;
    public String Noti_text="Null";
    public int MsgServerID=0;
    Timer timer;

    //MyTimerTask myTimerTask;
    String defaultValForAlert;
    public  String[] arrSchId;
    public String SchemeDesc="NA";
    int progressBarStatus=0;
    Thread myThread;
    private Handler mHandler = new Handler();
    public TextView spinner_product;
    boolean disValClkdOpenAlert=false;
    public String ProductIdOnClickedEdit;
    public String CtaegoryIddOfClickedView;
    public String condtnOddEven;
    public String storeID;
    public String imei;
    String progressTitle;
    public ProgressDialog pDialog2STANDBYabhi;
    public String date;
    public String pickerDate;
    public String SN;
    boolean alertOpens=false;
    int flagClkdButton=0;
    String distID="";
    LinkedHashMap<String,Integer> hmapDistPrdctStockCount =new LinkedHashMap<String,Integer>();
    LinkedHashMap<String,String> hmapPrdctIdOutofStock=new LinkedHashMap<String,String> ();


    //public ProgressDialog pDialogSync;
    public String productID;
    String spinnerCategorySelected;
    String spinnerCategoryIdSelected;
    Location lastKnownLoc;
    int countParentView;
    String alrtSlctPrdctNameId,alrtSpnr_EditText_Value;
    String alrtPrdctId;
    String productIdOnLastEditTextVal="0";
    String alrtValueSpnr;
    private boolean alrtStopResult = false;
    int alrtObjectTypeFlag=1; //1=spinner,edittext=0;
    // Decimal Format
    Locale locale  = new Locale("en", "UK");
    String pattern = "###.##";
    DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
    int CheckIfStoreExistInStoreProdcutPurchaseDetails=0;
    int CheckIfStoreExistInStoreProdcutInvoiceDetails=0;
    public String strGlobalOrderID="0";
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
   // public LinearLayout ll_prdct_detal;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;


    private RecyclerView.LayoutManager layoutManager;

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


    public String newfullFileName;
    int isReturnClkd=0;

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


    private final long startTime = 15000;
    private final long interval = 200;

    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;


    public String fnAccurateProvider="";
    public String fnLati="0";
    public String fnLongi="0";
    public Double fnAccuracy=0.0;


    public EditText   ed_search;
    public ImageView  btn_go;


    //****************************************************************
    //Arrays, HashMap
    View[] hide_View;
    List<String> categoryNames;
    public String[] prductId;
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
    //HashMap<String, String> hmapSchemeStoreID=new HashMap<String, String>();

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

    public int flgApplyFreeProductSelection=0;

    ArrayList<String> arredtboc_OderQuantityFinalSchemesToApply;

    //Database

    PRJDatabase dbengine = new PRJDatabase(this);
    DatabaseAssistant DA = new DatabaseAssistant(this);

    //Common Controls Box

    EditText edtBox;
    TextView viewBox;
    String viewCurrentBoxValue="";
    public int isbtnExceptionVisible=0;
    ImageView img_return;


    public LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    public Location location;

    PowerManager pm;
    PowerManager.WakeLock wl;
    public ProgressDialog pDialog2STANDBY;

    LocationListener locationListener;
    double latitude;
    double longitude;

    private static final long MIN_TIME_BW_UPDATES = 1000  * 1; //1 second

    public int flgProDataCalculation=1;

    public int StoreCatNodeId=0;

    View convertView;

    public ListView lvProduct;

    public EditText inputSearch;
    ArrayAdapter<String> adapter;
    AlertDialog ad;

    String[] products;

    private void getDataFromIntent() {


        Intent passedvals = getIntent();

        storeID = passedvals.getStringExtra("storeID");
        imei = passedvals.getStringExtra("imei");
        date = passedvals.getStringExtra("userdate");
        pickerDate = passedvals.getStringExtra("pickerDate");
        SN = passedvals.getStringExtra("SN");


    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_recycleview);

        getDataFromIntent();
        getProductData();

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            input.add("Test" + i);
        }
        prductId=changeHmapToArrayKey(hmapCtgryPrdctDetail);

        // define an adapter
        mAdapter = new MyRecycleViewAdapter(hmapPrdctIdPrdctName,hmapPrdctVolRatTax,hmapProductMRP,prductId);
        recyclerView.setAdapter(mAdapter);


    }

    private void getProductData() {
        // CategoryID,ProductID,ProductShortName,ProductRLP,Date/Qty)
        dbengine.open();
        StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(storeID));
        dbengine.close();

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


}
