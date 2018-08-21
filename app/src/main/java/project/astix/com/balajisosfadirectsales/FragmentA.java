package project.astix.com.balajisosfadirectsales;


import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


import com.astix.Common.CommonInfo;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.support.v4.app.FragmentTransaction;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FragmentA extends Fragment implements DatePickerDialog.OnDateSetListener,OnFocusChangeListener{
	
	
	
	public boolean stopSavingFlg=false;
	ViewPager viewPager;
	EditText ed1,ed2;
	ProgressDialog pDialogGetStores;
	TextView tx;
	ImageView gifView;
	RelativeLayout QuotationActivityLayout;
	Button b;
	View rootView;
	boolean fromDateBool=false;
	boolean toDateBool=false;
	EditText txt_Search;
	CheckBox checkBox;
	public int StoreCurrentStoreType=0;
	LinkedHashMap<String, String> hashmapForDynamicView;
	LinkedHashMap<String, String> hashmapForDynamicViewFromDataBase;
	LinkedHashMap<String, String> hashmapForUomData;
	LinkedHashMap<String, String> hashmapForBackupOfDynamicData= new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> hashmapForDateFromDateTo_UOM= new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> hashmapSavingAllUpdateValue= new LinkedHashMap<String, String>();
	 LinkedHashMap<String, String> hmapPrcDetailsFromDataBase;
	LinearLayout parentofScrollview;
	ScrollView dynamcDtaContnrScrollview;
	 View mainView,dynamic_container;
	 LinearLayout allDataParent;
	 Calendar calendar ;
	 DatePickerDialog datePickerDialog ;
	    int Year, Month, Day ;
	    TextView frmDate,todateText;
	    PRJDatabase dbengine;
	    String[] distributerList;
	    ArrayAdapter<String> adapterDistributor;
	    AlertDialog.Builder alertDialog;
		 AlertDialog ad;
		 View convertView;
		 ListView listDistributor;
		 TextView uomtext;
		 public EditText edtBoxs;
		 String tag2;
		 String SalesQuotePrcsId,SalesQuotePrcs,SalesQuoteValidFrom,SalesQuoteValidTo,SalesQuoteType;
		 
		 public int chkmsgnew=0;
		 
		 TextView Manufacture_rateSpinner,marchent_text;
		
		 ListView listMerchant;
		 LinkedHashMap<String, String> hashmapForManufacturerMstr;
		 String[] merchantList;
		 String globalmarchentName="Select";
		 
		public String ManufacturerID="";
		public String ManufacturerName="";
		
		 
	public FragmentA(
			) {
		// Required empty public constructor
	}
	
	public void spinnerInitialization()
	{
		
		
		
		 hashmapForManufacturerMstr = dbengine.fnGettblManufacturerMstrMain();
		 Manufacture_rateSpinner= (TextView) rootView.findViewById(R.id.Manufacture_rateSpinner);
		 ManufacturerID="0";
		 Manufacture_rateSpinner.setOnClickListener(new OnClickListener() 
		 {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				marchent_text = (TextView) v;
				alertDialog = new AlertDialog.Builder(getActivity());
			    LayoutInflater inflater = getActivity().getLayoutInflater();
			    convertView = (View) inflater.inflate(R.layout.activity_list, null);
			    EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
				inputSearch.setVisibility(View.GONE);
				listMerchant = (ListView)convertView. findViewById(R.id.list_view);
			  
			  //hashmap data setting to adapter
			  			
			  		int index=0;
			  		if(hashmapForManufacturerMstr!=null)
			      	{
			  			merchantList=new String[hashmapForManufacturerMstr.size()+1];
			  			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hashmapForManufacturerMstr);
			  			 Set set2 = map.entrySet();
			  	            Iterator iterator = set2.iterator();
			  	            while(iterator.hasNext()) {
			  	            	 Map.Entry me2 = (Map.Entry)iterator.next();
			  	            	 if(index==0)
			  	            	 {
			  	            		 merchantList[index]="Select Manufacturer";
			  	            		
			  	            		 index=index+1;
			  	            		
			  	            		 merchantList[index]=me2.getKey().toString().trim();
			  	                	
			  	                	 index=index+1;
			  	            	 }
			  	            	 else
			  	            	 {
			  	            		 merchantList[index]=me2.getKey().toString().trim();
			  	            	 
			  	            	 index=index+1;
			  	            	 }
			  	                 
			  	            }
			  			
			  			
			      	}
			  		else
			  		{
			  			Toast.makeText(getActivity(), "There is no record found", Toast.LENGTH_SHORT).show();
			  			return;
			  		}
			  		adapterDistributor = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, merchantList);
			  

			  
			  
			    listMerchant.setAdapter(adapterDistributor);
			    alertDialog.setView(convertView);
		        alertDialog.setTitle("Manufacturer");
		        listMerchant.setOnItemClickListener(new OnItemClickListener()
		        {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
					{
						String abc=listMerchant.getItemAtPosition(arg2).toString().trim();
						if(abc.equals("Select Manufacturer"))
						{
							ManufacturerName="0";
							ManufacturerID="0";
						}
						else
						{
						
							ManufacturerName=abc;
							 if (hashmapForManufacturerMstr.containsKey(ManufacturerName))
								{
	
									String MnfactrName = hashmapForManufacturerMstr.get(ManufacturerName);
	
									ManufacturerID = MnfactrName.split(Pattern.quote("^"))[0];
									
								}
						}
						marchent_text.setText(abc);
						ad.dismiss();
						
						 dbengine.updateManufacturerIDAndName(QuotationActivity.SalesQuoteId,ManufacturerID,ManufacturerName);
						
						
					}
				});
		        ad=alertDialog.show();
				
			}
		});
		 
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		 FragmentTransaction transaction=getFragmentManager().beginTransaction();
		 transaction.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
		 rootView= inflater.inflate(R.layout.fragment_a, container, false);
		 dbengine=new PRJDatabase(getActivity());
		 hmapPrcDetailsFromDataBase=dbengine.getSalesQuotePrcsMstr();
		 
		 
		 spinnerInitialization();
             //	public String ManufacturerID="";
			//	public String ManufacturerName="";
		String ManufacturerIDAndName= dbengine.getManufacturerIDAndManufacturerNameDetails(QuotationActivity.SalesQuoteId);
		
		 ManufacturerID=ManufacturerIDAndName.split(Pattern.quote("^"))[0];
		 ManufacturerName=ManufacturerIDAndName.split(Pattern.quote("^"))[1];
		 
		 if(ManufacturerName.equals("0"))
		 {
			 Manufacture_rateSpinner.setText("Select Manufacturer");
		 }
		 else
		 {
			 Manufacture_rateSpinner.setText(ManufacturerName);
		 }
		
		 
		 dbengine.updateManufacturerIDAndName(QuotationActivity.SalesQuoteId,ManufacturerID,ManufacturerName);
		 txt_Search=(EditText) rootView.findViewById(R.id.txt_Search);
		 gifView=(ImageView) rootView.findViewById(R.id.gifView);
		 pDialogGetStores=new ProgressDialog(getActivity());
		 QuotationActivityLayout=	 (RelativeLayout) getActivity().findViewById(R.id.QuotationActivityLayout);
		 viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		/*hashmapForDynamicView=new LinkedHashMap<String, String>();
		hashmapForDynamicView.put("Product1", "100"+"^"+"70"+"^"+"0"+"^"+"20.5"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"1");
		hashmapForDynamicView.put("Product2", "200"+"^"+"80"+"^"+"0"+"^"+"76.6"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"2");
		hashmapForDynamicView.put("Product3", "300"+"^"+"90"+"^"+"0"+"^"+"87"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"3");
		hashmapForDynamicView.put("Product4", "400"+"^"+"550"+"^"+"0"+"^"+"87"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"4");
		hashmapForDynamicView.put("Product5", "500"+"^"+"650"+"^"+"0"+"^"+"90"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"5");
		hashmapForDynamicView.put("Product6", "600"+"^"+"760"+"^"+"0"+"^"+"23"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"6");
		hashmapForDynamicView.put("Product7", "700"+"^"+"560"+"^"+"0"+"^"+"21"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"7");
		hashmapForDynamicView.put("Product8", "800"+"^"+"650"+"^"+"0"+"^"+"45.9"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"8");
		hashmapForDynamicView.put("Product9", "900"+"^"+"345"+"^"+"0"+"^"+"13.9"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"9");
		hashmapForDynamicView.put("Product10", "1000"+"^"+"980"+"^"+"0"+"^"+"34.5"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"10");*/
		
		 dynamicViewCreation();
		savingDataToDataBase();
		//addViewInTable();
	
		return rootView;
		
		
	}
	public void savingDataToDataBase() {
		
		Button btnPrevious=	(Button) rootView.findViewById(R.id.btnPrevious);
		btnPrevious.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View arg0) 
			{
				viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
				viewPager.setCurrentItem(0);

			}
		});

		
			
	Button doneButton=	(Button) rootView.findViewById(R.id.Done_btn);
	doneButton.setOnClickListener(new OnClickListener()
	{
		
		@Override
		public void onClick(View arg0) 
		{
			
			/*AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
			 alertDialog.setTitle("Information");
		       
		        alertDialog.setCancelable(false);
		     alertDialog.setMessage("Do you want to submit data ");
		     alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog,int which) {
		            	dialog.dismiss();*/
		            	
		            	saveDataTodatabaseOnDone();
		            	
		            	
		            	
		            	//FragmentManager fm =getFragmentManager().beginTransaction();;// getFragmentManager();
		                  /* FragmentTransaction ft = getFragmentManager().beginTransaction();
		                   ft.replace(R.id.QuotationActivityLayout, new Fragment(), "Quotation");
		                    ft.commit();*/
		           
		          /*  }
		        });
		     alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface dialog,int which) {
		            	dialog.dismiss();
		            }
		        });*/
		 
		        // Showing Alert Message
		      //  alertDialog.show();
			
			
		}
	});
	
	

	}
	
	public boolean validate_UOM() 
	{
		boolean stopSavingFlgValidation=true;
		if(!hashmapForBackupOfDynamicData.isEmpty())
		{
			boolean uom_is_select=true;
			
			//dbengine.open();
		  for(Map.Entry<String, String> entry:hashmapForBackupOfDynamicData.entrySet())
		   {
			  String UOMID="0";
			  
			String productID=  entry.getKey().toString().trim();
         String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID).toString().trim();
         
			
			String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
			String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
			String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
			String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
			
			if(hashmapForUomData.containsKey(UOM)){
			UOMID=	hashmapForUomData.get(UOM);
			}
			
			String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
			String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
			String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
			
			String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
			String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
			String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
			String ProductTaxRateBK=valueOnHashMap2.split(Pattern.quote("^"))[10];
			
				if(!RateOffer.equals("0") || !minDlv_qty.equals("0"))
				{
					if(UOMID.equals("0"))
					{
						//allMessageAlert("Please select UOM");
						stopSavingFlgValidation=false;
						break;
						
					}
				}
		   }
		  
		}
		
       return stopSavingFlgValidation;
	}
	public void saveDataTodatabaseOnDone() 
	{
		
		String SalesQuotePrcsForUpdate="0";
		String SalesQuotePrcsIdForUpdate="0";

		retreivingDataOfLastEdittext();
		int chkmsg=0;
		if(validate_UOM())
		{
			
			if(!hashmapForBackupOfDynamicData.isEmpty())
			{
				dbengine.open();
			  for(Map.Entry<String, String> entry:hashmapForBackupOfDynamicData.entrySet())
			   {
				  String UOMID="0";
				  //stopSavingFlg=false;
				String productID=  entry.getKey().toString().trim();
	            String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID).toString().trim();
	         
				
				String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
				String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
				String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
				String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
				
				if(hashmapForUomData.containsKey(UOM))
				{
					UOMID=	hashmapForUomData.get(UOM);
				}
				
				String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
				String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
				String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
				
				String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
				String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
				String ProductTaxRateBK=valueOnHashMap2.split(Pattern.quote("^"))[10];
				
				if(!RateOffer.equals("0") || !minDlv_qty.equals("0"))
				{
					if(Double.parseDouble(RateOffer)==0.0)
					{
						    chkmsg=1;
						    //allMessageAlert("Please select Rate Offer");
						    QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						    stopSavingFlg=true;
						    break;
					} 
					//Minimum delivery quantity validation is commented now
					/*else if(Double.parseDouble(minDlv_qty)==0.0)
					{
						chkmsg=2;
						//allMessageAlert("Please select Minimum Order Quantity");
						    QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						    stopSavingFlg=true;
						   break;
					}*/
					else if(UOMID.equals("0"))
					{
							chkmsg=3;
						//allMessageAlert("Please select UOM");
						    QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						    stopSavingFlg=true;
						    break;
					}
					
				}
	 
				if(stopSavingFlg==false)
				{
					dbengine.deletetblSalesQuoteProductsMstr(productID,QuotationActivity.SalesQuoteId);
					//if(!RateOffer.equals("0") && !minDlv_qty.equals("0"))
						if(!RateOffer.equals("0") )
					{
					  dbengine.SalesQuoteProductsMstr(QuotationActivity.SalesQuoteId, "0", productID, STrateStringBK, StandardRateBeforeTaxBK, RateOffer, includingTaxCheckbox, fromDate, todate, minDlv_qty, UOMID, remarktext, lastTransRTStringBK,ProductTaxRateBK);
				    }
					
				
				}
			
				}
			  dbengine.close();
			 if(stopSavingFlg==true)
			  {
				  stopSavingFlg=false;
				  if(chkmsg==3)
				  {
				  allMessageAlert("Please select UOM");
				  }
				  if(chkmsg==2)
				  {
					  allMessageAlert("Please select Minimum Dlv. Quantity");
				  }
				  if(chkmsg==1)
				  {
					  allMessageAlert("Please select Rate Offer");
				  }
			  }
			  else
			  {
			  if(CommonInfo.quatationFlag.equals("UPDATE"))
			  {
					  if(CommonInfo.prcID.equals("2"))
					  {
							 SalesQuotePrcsForUpdate=	hmapPrcDetailsFromDataBase.get("3");
							 SalesQuotePrcsIdForUpdate="3";
							 dbengine.open();
							 dbengine.UpdateQuotePrcsAgainstQuotationId(QuotationActivity.SalesQuoteId, SalesQuotePrcsIdForUpdate, SalesQuotePrcsForUpdate);
							 dbengine.close();
						} 
				 	}
				  
				  hashmapForBackupOfDynamicData.clear();
				  hashmapForDateFromDateTo_UOM.clear();
				 /* allDataParent.removeAllViews();
				  dynamcDtaContnrScrollview.removeAllViews();*/
					
					
					//viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
					//Fragment ff=((Myadapter)viewPager.getAdapter()).getRegisteredFragment(2);
					//Fragment fragm = ff; 
					//((FragmentB) ff).callForViewFilledData();
					/*try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					viewPager.setCurrentItem(2);
					
			  }
			
			 
			
			}
			else
			{
				
				allMessageAlert("NO DATA TO SAVE INTO TABLE");
				
				
				//Toast.makeText(getActivity().getApplicationContext(), "NO DATA TO SAVE INTO TABLE", Toast.LENGTH_SHORT).show();
			}
			
			
			
		

		
		}
		else
		{
			//allMessageAlert("Please select UOM");
			if(chkmsg==3)
			  {
			      allMessageAlert("Please select UOM");
			  }
			  if(chkmsg==2)
			  {
				  allMessageAlert("Please select Minimum Dlv. Quantity");
			  }
			  if(chkmsg==1)
			  {
				  allMessageAlert("Please select Rate Offer");
			  }
		}
		
		
	}
	private void allMessageAlert(String message) 
	{
		    AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
			alertDialogNoConn.setTitle("Information");
			alertDialogNoConn.setMessage(message);
			//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
			alertDialogNoConn.setNeutralButton("OK",
					new DialogInterface.OnClickListener() 
						{
						 public void onClick(DialogInterface dialog, int which) 
						  {
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

	}
	public void FromDate_ToDate_and_FlagValuesFromTable()
	{
		try
		{
			String dateAndFlag=	dbengine.fngettblSalesQuotePersonMeetMstr(QuotationActivity.SalesQuoteId);
			SalesQuotePrcsId="0";
			SalesQuotePrcs="0";
			SalesQuoteValidFrom="0";
			SalesQuoteValidTo="0";
			SalesQuoteType="0";
			if(!dateAndFlag.equals("0"))
			{
				
				 SalesQuotePrcsId=dateAndFlag.split(Pattern.quote("^"))[0];
				 SalesQuotePrcs=dateAndFlag.split(Pattern.quote("^"))[1];
				 SalesQuoteValidFrom=dateAndFlag.split(Pattern.quote("^"))[2];
				 SalesQuoteValidTo=dateAndFlag.split(Pattern.quote("^"))[3];
				 SalesQuoteType=dateAndFlag.split(Pattern.quote("^"))[4];
			}
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public void UOMDataFromHashMap()
	{
		hashmapForUomData=dbengine.fnGettblUOMMstr();
		int index=0;
		if(hashmapForUomData!=null)
    	{
			distributerList=new String[hashmapForUomData.size()+1];
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hashmapForUomData);
			 Set set2 = map.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext()) 
	            {
	            	 Map.Entry me2 = (Map.Entry)iterator.next();
	            	 if(index==0)
	            	 {
	            		 distributerList[index]="Select";
	            		 index=index+1;
	            		 distributerList[index]=me2.getKey().toString().trim();
	                	 index=index+1;
	            	 }
	            	 else
	            	 {
	            		 distributerList[index]=me2.getKey().toString().trim();
	            	     index=index+1;
	            	 }
	                 
	            }
			
			
    	}
		adapterDistributor = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, distributerList);

	}
	
	public void clearViewWhenSlide()
	{
		try
		{
			edtBoxs.setText("FROM");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		hashmapForBackupOfDynamicData.clear();
		
		hashmapForDateFromDateTo_UOM.clear();
		
		hashmapForBackupOfDynamicData.clear();
		if(allDataParent.getChildCount()>0){
			allDataParent.removeAllViews();
			dynamcDtaContnrScrollview.removeAllViews();
		
		}
		else{
			if(allDataParent.getChildCount()>0){
				allDataParent.removeAllViews();
			}
			if(dynamcDtaContnrScrollview.getChildCount()>0){
				dynamcDtaContnrScrollview.removeAllViews();
			}
			
	}

	}
	@Override
	public void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();
		
	Button btnGo=	(Button) rootView.findViewById(R.id.btnGo);
	btnGo.setTag("btnGo");
	btnGo.setOnClickListener(new OnClickListener()
	{
		
		@Override
		public void onClick(View arg0) 
		{
			InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(arg0.getWindowToken(), 0);
			
			
			 UOMDataFromHashMap();
			 FromDate_ToDate_and_FlagValuesFromTable();
			 retreivingDataOfLastEdittext();
			if(CommonInfo.quatationFlag.equals("NEW"))
			{
				if(CommonInfo.SalesQuoteId.equals("BLANK"))
				{
					allMessageAlert("First fill the customer info. and click on Save & Next before taking Quotation");
					//Toast.makeText(getActivity().getApplicationContext(), "First fill data of first tab", Toast.LENGTH_SHORT).show();
					
				}
				else
				{
					if(txt_Search.getText().toString().trim().equals(""))
					{
						allMessageAlert("Please enter some text to filter");
						
						//Toast.makeText(getActivity().getApplicationContext(), "Please enter some text to filter", Toast.LENGTH_SHORT).show();
					}
					else
					{
						
						GetDatawhenNEW task = new GetDatawhenNEW();
						  task.execute();
						/*
						hashmapForDynamicViewFromDataBase=	dbengine.getFileredProductListQuotationMap(txt_Search.getText().toString().trim(), QuotationActivity.SalesQuoteId);
						
						if(hashmapForDynamicViewFromDataBase.size()>0){
							
							if(allDataParent.getChildCount()>0){
								allDataParent.removeAllViews();
								dynamcDtaContnrScrollview.removeAllViews();
								addViewInTable();
							}
							else{
								if(allDataParent.getChildCount()>0){
									allDataParent.removeAllViews();
								}
								if(dynamcDtaContnrScrollview.getChildCount()>0){
									dynamcDtaContnrScrollview.removeAllViews();
								}
								
								addViewInTable();}
							
						}
						else{ Toast.makeText(getActivity().getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
						
						}
					*/}
					
				}
			}
			if(CommonInfo.quatationFlag.equals("UPDATE"))
			{
				if(txt_Search.getText().toString().trim().equals(""))
				{
					allMessageAlert("Please enter some text to filter");
					//Toast.makeText(getActivity().getApplicationContext(), "Please enter some text to filter", Toast.LENGTH_SHORT).show();
				}
				else
				{
					GetDataWhenUpdate task = new GetDataWhenUpdate();
					  task.execute();
					/*
					hashmapForDynamicViewFromDataBase=	dbengine.getFileredProductListQuotationMap(txt_Search.getText().toString().trim(), QuotationActivity.SalesQuoteId);
					if(hashmapForDynamicViewFromDataBase.size()>0){
						
						if(allDataParent.getChildCount()>0){
							allDataParent.removeAllViews();
							dynamcDtaContnrScrollview.removeAllViews();
							addViewInTable();
						}
						else{
							if(allDataParent.getChildCount()>0){
								allDataParent.removeAllViews();
							}
							if(dynamcDtaContnrScrollview.getChildCount()>0){
								dynamcDtaContnrScrollview.removeAllViews();
							}
							
							addViewInTable();}
						
					}
					else{ Toast.makeText(getActivity().getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
						
					}
				*/}
			}
			txt_Search.requestFocus();
			
		}
	});
	}
	public void dynamicViewCreation()
	{
		parentofScrollview=	(LinearLayout)rootView. findViewById(R.id.parentofScrollview);
		dynamcDtaContnrScrollview=(ScrollView)rootView. findViewById(R.id.dynamcDtaContnrScrollview);
		dynamic_container=getActivity().getLayoutInflater().inflate(R.layout.dynamic_data_container, dynamcDtaContnrScrollview,false);
		allDataParent= 	(LinearLayout) dynamic_container.findViewById(R.id.dynamic_container);
		 
		  // addViewInTable();
	}
	
	public void addViewInTable() 
	{
		int index=1;
		for(Map.Entry<String, String> entry:hashmapForDynamicViewFromDataBase.entrySet())
		{
			      /* String productname=entry.getKey().toString().trim();
			// hashmapForDynamicView.put("Product10", "1000"+"^"+"980"+"^"+"0"+"^"+"34.5"+"^"+"21-mar-16"+"^"+"21-april-16"+"^"+"0"+"^"+"0"+"^"+"0"+"^"+"10");
                                                   //  st.Rte  lastTransRT  UOM    Incl.Tax                                   RT.Offer minDl.Qty Remark   
				String sku=entry.getValue().toString().trim();
				 String STrateString=sku.split(Pattern.quote("^"))[0];
					String lastTransRTString=sku.split(Pattern.quote("^"))[1];*/
					 String ProductID=entry.getKey().toString().trim();
                     
                     String sku=entry.getValue().toString().trim();
		    String productname=sku.split(Pattern.quote("^"))[0];
			String STrateString=sku.split(Pattern.quote("^"))[1];
			String StandardRateBeforeTax=sku.split(Pattern.quote("^"))[2];
			
			String RtOfferString=sku.split(Pattern.quote("^"))[3];
			String InclTaxString=sku.split(Pattern.quote("^"))[4];
			String FromDateString=sku.split(Pattern.quote("^"))[5];
			String ToDateString=sku.split(Pattern.quote("^"))[6];
			String minDlQtyString=sku.split(Pattern.quote("^"))[7];
			String UomString=sku.split(Pattern.quote("^"))[8];
			String RemarkString=sku.split(Pattern.quote("^"))[9];
			String lastTransRTString=sku.split(Pattern.quote("^"))[10];
			String ProductTaxRate=sku.split(Pattern.quote("^"))[11];
			if(CommonInfo.quatationFlag.equals("UPDATE"))
			{}
	
			//hashmapSavingAllUpdateValue.put(ProductID, STrateString+"^"+StandardRateBeforeTax+"^"+RtOfferString+"^"+InclTaxString+"^"+FromDateString+"^"+ToDateString+"^"+minDlQtyString+"^"+UomString+"^"+RemarkString+"^"+lastTransRTString);
			if(hashmapForUomData.size()>1)
			{
			for(Map.Entry<String, String> entry2:hashmapForUomData.entrySet())
			   {  String keyname=  entry2.getKey().toString().trim();
			   String valueid=  entry2.getValue().toString().trim();
			   if(valueid.equals(UomString)){
				   UomString=keyname;
				   
			   }
				
			   }
			}
			else
			{
				for(Map.Entry<String, String> entry2:hashmapForUomData.entrySet())
				   {  String keyname=  entry2.getKey().toString().trim();
				   String valueid=  entry2.getValue().toString().trim();
				   
					   UomString=keyname;
					   
				   
					
				   }	
			}
			
			if(!hashmapForDateFromDateTo_UOM.containsKey(ProductID))
			{
				hashmapForDateFromDateTo_UOM.put(ProductID, FromDateString+"^"+ToDateString+"^"+UomString+"^"+InclTaxString+"^"+STrateString+"^"+lastTransRTString+"^"+StandardRateBeforeTax+"^"+ProductTaxRate);
			}
	
	
	
	
	hashmapForBackupOfDynamicData.put(ProductID, RtOfferString+"^"+minDlQtyString+"^"+InclTaxString+"^"+UomString+"^"+FromDateString+"^"+ToDateString+"^"+RemarkString+"^"+STrateString+"^"+lastTransRTString+"^"+StandardRateBeforeTax+"^"+ProductTaxRate);
	if(!hashmapForBackupOfDynamicData.isEmpty())
	{/*
          if(hashmapForBackupOfDynamicData.containsKey(ProductID)){
		String valuveFromBackUPHmap=	hashmapForBackupOfDynamicData.get(ProductID);
		
		String RateOfferFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[0];
		if(!RateOfferFromBackUP.equals("0")){
			RtOfferString=RateOfferFromBackUP;
		}
		String minDlv_qtyFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[1];
		if(!minDlv_qtyFromBackUP.equals("0")){
			minDlQtyString=minDlv_qtyFromBackUP;
		}
		String includingTaxCheckboxFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[2];
		if(!includingTaxCheckboxFromBackUP.equals("0")){
			InclTaxString=includingTaxCheckboxFromBackUP;
		}
		String UOMFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[3];
		if(!UOMFromBackUP.equals("0")){
			UomString=UOMFromBackUP;
		}
		String fromDateFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[4];
		if(!fromDateFromBackUP.equals("0")){
			FromDateString=fromDateFromBackUP;
		}
		String todateFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[5];
		if(!todateFromBackUP.equals("0")){
			ToDateString=todateFromBackUP;
		}
	String remarktextFromBackUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[6];
	if(!remarktextFromBackUP.equals("0")){
		RemarkString=remarktextFromBackUP;
	}
	String STrateStringBKUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[7];
		String lastTransRTStringBKUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[8];
	String StandardRateBeforeTaxBKUP=valuveFromBackUPHmap.split(Pattern.quote("^"))[9];
	
}
	*/}

	
					
					View	 mainView =  getActivity().getLayoutInflater().inflate(R.layout.pos_data,null);
					LinearLayout ll=	(LinearLayout) mainView.findViewById(R.id.posdata1);
					if((index%2)==0){
						ll.setBackgroundColor(getResources().getColor(R.color.tablecolor));
					}
					
					TextView	txtVProductname=(TextView) mainView.findViewById(R.id.txtVProductname);
					txtVProductname.setText(productname);
					
					TextView	st_Rate=(TextView) mainView.findViewById(R.id.st_Rate);
					//st_Rate.setText(STrateString);
					st_Rate.setText(StandardRateBeforeTax);
					
					TextView	st_ProductTax=(TextView) mainView.findViewById(R.id.st_ProductTax);
					st_ProductTax.setText(ProductTaxRate);
					
					
					TextView	last_transRate=(TextView) mainView.findViewById(R.id.last_transRate);
					last_transRate.setText(lastTransRTString);  
					
					TextView	UOM=(TextView) mainView.findViewById(R.id.UOM);
					if(!UomString.equals("0")){
						UOM.setText(UomString);
						for(Map.Entry<String, String> entry2:hashmapForUomData.entrySet())
						   {  String keyname=  entry2.getKey().toString().trim();
						   String valueid=  entry2.getValue().toString().trim();
						   if(valueid.equals(UomString)){
							   UOM.setText(keyname);
						   }
							
						   }
					}
					else{
						UOM.setText("Select"); 
					}
					UOM.setTag(ProductID+"^"+"UOM");
					UOM.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							 uomtext = (TextView) arg0;
							alertDialog = new AlertDialog.Builder(getActivity());
							 LayoutInflater inflater = getActivity().getLayoutInflater();
							  convertView = (View) inflater.inflate(R.layout.activity_list, null);
							  EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
								inputSearch.setVisibility(View.GONE);
							  listDistributor = (ListView)convertView. findViewById(R.id.list_view);
							  listDistributor.setAdapter(adapterDistributor);
							  alertDialog.setView(convertView);
						        alertDialog.setTitle("Payment Term");
						        listDistributor.setOnItemClickListener(new OnItemClickListener() {

									@Override
									public void onItemClick(
											AdapterView<?> arg0, View arg1,
											int arg2, long arg3) {
										String abc=listDistributor.getItemAtPosition(arg2).toString().trim();
										uomtext.setText(abc);
										 String FROMDATETAG=	uomtext.getTag().toString().trim();

											
										 
											String productID_inTAG2=FROMDATETAG.split(Pattern.quote("^"))[0];
											 String secondPartOfTag2=FROMDATETAG.split(Pattern.quote("^"))[1];
										String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
										String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
										 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
										 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
										 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
										 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
										 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
										 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
										 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
										 UOMTXT=abc;
										 hashmapForDateFromDateTo_UOM.put(productID_inTAG2, FROMDATE+"^"+TODATE+"^"+UOMTXT+"^"+TAXTEXT+"^"+STrateStringHM+"^"+lastTransRTStringHM+"^"+StandardRateBeforeTaxHM+"^"+ProductTaxRateHM);
										 saveUoMDATAToHashMap();
										ad.dismiss();
										
									}
								});
						        ad=alertDialog.show();
							
						}
					});
					 
					
					
			CheckBox 	includingTaxCheckbox=	 (CheckBox) mainView.findViewById(R.id.includingTaxCheckbox);
			//InclTaxString
			if(InclTaxString.equals("1")){
				includingTaxCheckbox.setChecked(true);
			}
			else{
				includingTaxCheckbox.setChecked(false); 
			}
			includingTaxCheckbox.setTag(ProductID+"^"+"includingTaxCheckbox");
			includingTaxCheckbox.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				 checkBox = (CheckBox) arg0;
					
					if(checkBox.isChecked()){
						checkBox.setChecked(true);
						String CheckBoxTAG=	checkBox.getTag().toString().trim();

						
						 
						String productID_inTAG2=CheckBoxTAG.split(Pattern.quote("^"))[0];
						 String secondPartOfTag2=CheckBoxTAG.split(Pattern.quote("^"))[1];
					String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
					String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
					 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
					 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
					 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
					 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
					 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
					 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
					 TAXTEXT="1";
					 hashmapForDateFromDateTo_UOM.put(productID_inTAG2, FROMDATE+"^"+TODATE+"^"+UOMTXT+"^"+TAXTEXT+"^"+STrateStringHM+"^"+lastTransRTStringHM+"^"+StandardRateBeforeTaxHM+"^"+ProductTaxRateHM);
					 savingCheckBoxDataToHashmap();
					}
					else{
						checkBox.setChecked(false);
						String CheckBoxTAG=	checkBox.getTag().toString().trim();

						
						 
						String productID_inTAG2=CheckBoxTAG.split(Pattern.quote("^"))[0];
						 String secondPartOfTag2=CheckBoxTAG.split(Pattern.quote("^"))[1];
					String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
					String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
					 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
					 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
					 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
					 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
 					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
					 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
					 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
					 TAXTEXT="0";
					 hashmapForDateFromDateTo_UOM.put(productID_inTAG2, FROMDATE+"^"+TODATE+"^"+UOMTXT+"^"+TAXTEXT+"^"+STrateStringHM+"^"+lastTransRTStringHM+"^"+StandardRateBeforeTaxHM+"^"+ProductTaxRateHM);
					 savingCheckBoxDataToHashmap();
						
					}
					
				}
			});
			
					final TextView	fromDate=(TextView) mainView.findViewById(R.id.fromDate);
					fromDate.setText(FromDateString);
					
					if(FromDateString.equals("0")){
					if(!SalesQuoteType.equals("0")){
						if(SalesQuoteType.trim().equals("1")){
							fromDate.setEnabled(false);
							fromDate.setText(SalesQuoteValidFrom);
						}
						if(SalesQuoteType.trim().equals("2")){
							fromDate.setText(SalesQuoteValidFrom);
						}
						
					}}
					else{
						if(SalesQuoteType.trim().equals("1")){
							fromDate.setEnabled(false);
							fromDate.setText(FromDateString);
						}
						if(SalesQuoteType.trim().equals("2")){
							fromDate.setText(FromDateString);
						}
						
					}
					fromDate.setTag(ProductID+"^"+"fromDate");
					
					fromDate.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							fromDateBool=true;
							 frmDate = (TextView) arg0;
							 calendar = Calendar.getInstance();
							 Year = calendar.get(Calendar.YEAR) ;
						       Month = calendar.get(Calendar.MONTH);
						       Day = calendar.get(Calendar.DAY_OF_MONTH);
						       datePickerDialog = DatePickerDialog.newInstance(FragmentA.this, Year, Month, Day);

						       datePickerDialog.setThemeDark(false);

						       datePickerDialog.showYearPickerFirst(false);
						       
						       Calendar calendarForSetDate = Calendar.getInstance();           
						       calendarForSetDate.setTimeInMillis(System.currentTimeMillis());    
						       // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);    
						       //YOU can set min or max date using this code          
						       // datePickerDialog.setMaxDate(Calendar.getInstance());         
						       // datePickerDialog.setMinDate;            
						       datePickerDialog.setMinDate(calendarForSetDate);

						       datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

						       datePickerDialog.setTitle("SELECT FROM DATE");
						       datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
							
						}
					});
					final TextView	todate=(TextView) mainView.findViewById(R.id.todate);
					todate.setText(SalesQuoteValidTo);
					if(ToDateString.equals("0")){
						todate.setText(SalesQuoteValidTo);
					}
					else{
						todate.setText(ToDateString);
					}
					todate.setTag(ProductID+"^"+"todate");
					todate.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							toDateBool=true;
							 todateText = (TextView) arg0;
							 calendar = Calendar.getInstance();
							 Year = calendar.get(Calendar.YEAR) ;
						       Month = calendar.get(Calendar.MONTH);
						       Day = calendar.get(Calendar.DAY_OF_MONTH);
						       datePickerDialog = DatePickerDialog.newInstance(FragmentA.this, Year, Month, Day);

						       datePickerDialog.setThemeDark(false);

						       datePickerDialog.showYearPickerFirst(false);
						       
						       
						       Calendar calendarForSetDate = Calendar.getInstance();           
						       calendarForSetDate.setTimeInMillis(System.currentTimeMillis());    
						       // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);    
						       //YOU can set min or max date using this code          
						       // datePickerDialog.setMaxDate(Calendar.getInstance());         
						       // datePickerDialog.setMinDate;   
						       
						       String dtFrom=	 fromDate.getText().toString().trim();
						       if(dtFrom.contains("-")){
						    	   String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
						    		int	DayDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[0]);
						    		int monthDTFRm=getArrayIndex(MONTHS, dtFrom.split(Pattern.quote("-"))[1]);
						    		int	yearDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[2]);
						    		Calendar calDTFRM=Calendar.getInstance();
						    		calDTFRM.set(yearDTFRm, monthDTFRm, DayDTFRm);
						    		if(calDTFRM.getTimeInMillis()>System.currentTimeMillis()){
						    			calendarForSetDate.set(yearDTFRm, monthDTFRm, DayDTFRm);
						    		}
						    		
						       }
						       datePickerDialog.setMinDate(calendarForSetDate);

						       datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

						       datePickerDialog.setTitle("SELECT DATE UPTO");
						       datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
							
						}
					});
					
					TextView	remarktext=(TextView) mainView.findViewById(R.id.remarktext);
					
					
					if(!RemarkString.equals("0")){
						remarktext.setText(RemarkString);
					}
					else{
						remarktext.setText(""); 
					}
					remarktext.setTag(ProductID+"^"+"remarktext");
					EditText	RateOffer=(EditText) mainView.findViewById(R.id.RateOffer);
					//String valueofEdittext=hmapquestion_tableValues.get(keyHashmap).toString().trim();
					if(!RtOfferString.equals("0")){
						RateOffer.setText(RtOfferString);
					}
					RateOffer.setTag(ProductID+"^"+"RateOffer");
					RateOffer.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							
							
						}
					});
                      EditText	minDlv_qty=(EditText) mainView.findViewById(R.id.minDlv_qty);
					//String valueofEdittext=hmapquestion_tableValues.get(keyHashmap).toString().trim();
					if(!minDlQtyString.equals("0")){
						minDlv_qty.setText(minDlQtyString);
					}
					minDlv_qty.setTag(ProductID+"^"+"minDlv_qty");
					remarktext.setOnFocusChangeListener(FragmentA.this);
					RateOffer.setOnFocusChangeListener(FragmentA.this);
					minDlv_qty.setOnFocusChangeListener(FragmentA.this);
					
					/* EditText	remarkEditText=(EditText) mainView.findViewById(R.id.remarkEditText);
						//String valueofEdittext=hmapquestion_tableValues.get(keyHashmap).toString().trim();
						if(!remarks.equals("0")){
							remarkEditText.setText(remarks);
						}*/
						/*remarkEditText.setOnTouchListener(new OnTouchListener() {
							
							@Override
							 public boolean onTouch(View v, MotionEvent event) {
				              
				                    v.getParent().requestDisallowInterceptTouchEvent(true);
				                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
				                    case MotionEvent.ACTION_UP:
				                        v.getParent().requestDisallowInterceptTouchEvent(false);
				                        break;
				                    }
				                
				                return false;
				            }
				        });*/
					
					/*optionValue.setTag(keyHashmap);
					optionValue.setOnFocusChangeListener(AllViewActivity.this);
					
					mainView.setTag(categorytag);*/
					allDataParent.addView(mainView);
					index++;
			
			
		}
		 dynamcDtaContnrScrollview.addView(allDataParent);
		 QuotationActivityLayout.setEnabled(true);
			gifView.setVisibility(View.GONE);
			getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		
		
	}
	public int getArrayIndex(String[] arr,String value) {

        int k=0;
        for(int i=0;i<arr.length;i++){

            if(arr[i].contains(value)){
                k=i;
                break;
            }
        }
    return k;
}
	public void savingCheckBoxDataToHashmap() {
		


		String checkBox_Tag=	 checkBox.getTag().toString().trim();
		String productID_inTAG2=checkBox_Tag.split(Pattern.quote("^"))[0];
		 String secondPartOfTag2=checkBox_Tag.split(Pattern.quote("^"))[1];
		 if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG2)){
				String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID_inTAG2).toString().trim();
				
				String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
				String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
				String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
				String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
				String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
				String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
				String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
				
				String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
				String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
				
				
			      
				
			       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
					String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
					 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
					 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
					 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
					 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
 					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
					 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
					 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
					 
					 fromDate=FROMDATE;
					 todate=TODATE;
					 UOM=UOMTXT;
					 STrateStringBK=STrateStringHM;
					 lastTransRTStringBK=lastTransRTStringHM;
					 StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
					 
				 includingTaxCheckbox=TAXTEXT;
			       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
				
				}
		 else{
				String RateOffer="0";
				String minDlv_qty="0";
				String includingTaxCheckbox="0";
				String UOM="0";
				String fromDate="0";
				String todate="0";
				String remarktext="0";
				String STrateStringBK="0";
				String lastTransRTStringBK="0";
				String StandardRateBeforeTaxBK="0";
				
				 
			       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
					String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
					 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
					 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
					 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
					 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
 					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
					 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
					 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
					 fromDate=FROMDATE;
					 todate=TODATE;
					 UOM=UOMTXT;
				 includingTaxCheckbox=TAXTEXT;
				 
				 STrateStringBK=STrateStringHM;
		         lastTransRTStringBK=lastTransRTStringHM;
		         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
			       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
				
			}
			

		
		

	}
	public void saveUoMDATAToHashMap() {

	String UOMTAG=	 uomtext.getTag().toString().trim();
	String productID_inTAG2=UOMTAG.split(Pattern.quote("^"))[0];
	 String secondPartOfTag2=UOMTAG.split(Pattern.quote("^"))[1];
	 if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG2)){
			String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID_inTAG2).toString().trim();
			
			String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
			String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
			String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
			String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
			String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
			String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
			String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
			String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
			String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
			
		      if(secondPartOfTag2.equals("RateOffer")){
		    	 
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  RateOffer="0";
		   		}
		   		else{
		   		 RateOffer=  uomtext.getText().toString().trim();		
		   		}
				
			    }
		     if(secondPartOfTag2.equals("minDlv_qty")){
		    	
		    	 if(uomtext.getText().toString().trim().equals("")){
		    		 minDlv_qty="0";
		   		}
		   		else{
		   		 minDlv_qty=  uomtext.getText().toString().trim();		
		   		}
		       }
		      if(secondPartOfTag2.equals("includingTaxCheckbox")){
		    	  
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  includingTaxCheckbox="0";
			   		}
			   		else{
			   			includingTaxCheckbox=  uomtext.getText().toString().trim();		
			   		}
		       }
		      if(secondPartOfTag2.equals("UOM")){
		    	  
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  UOM="0";
			   		}
			   		else{
			   			UOM=  uomtext.getText().toString().trim();	
			   		}
		     }
		       if(secondPartOfTag2.equals("fromDate")){
		    	   
		    	   if(uomtext.getText().toString().trim().equals("")){
		    		   fromDate="0";
				   		}
				   		else{
				   			fromDate=  uomtext.getText().toString().trim();	
				   		}
		     }
		      if(secondPartOfTag2.equals("todate")){
		    	  
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  todate="0";
				   		}
				   		else{
				   			todate=  uomtext.getText().toString().trim();	
				   		}
		     }
		       if(secondPartOfTag2.equals("remarktext")){
		    	  
		    	   if(uomtext.getText().toString().trim().equals("")){
		    		   remarktext="0";
					   		}
					   		else{
					   		 remarktext=  uomtext.getText().toString().trim();	
					   		}
		          }
		       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
				String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
				 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
				 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
				 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
				 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
				 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
				 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
				 fromDate=FROMDATE;
				 todate=TODATE;
				 UOM=UOMTXT;
			 includingTaxCheckbox=TAXTEXT;
			 STrateStringBK=STrateStringHM;
	         lastTransRTStringBK=lastTransRTStringHM;
	         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
		       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
			
			}
	 else{
			String RateOffer="0";
			String minDlv_qty="0";
			String includingTaxCheckbox="0";
			String UOM="0";
			String fromDate="0";
			String todate="0";
			String remarktext="0";
			 String STrateStringBK="0";
			 String lastTransRTStringBK="0";
			String StandardRateBeforeTaxBK="0";
			
			 if(secondPartOfTag2.equals("RateOffer")){
		    	 
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  RateOffer="0";
		   		}
		   		else{
		   		 RateOffer=  uomtext.getText().toString().trim();		
		   		}
				
			    }
		     if(secondPartOfTag2.equals("minDlv_qty")){
		    	
		    	 if(uomtext.getText().toString().trim().equals("")){
		    		 minDlv_qty="0";
		   		}
		   		else{
		   		 minDlv_qty=  uomtext.getText().toString().trim();		
		   		}
		       }
		      if(secondPartOfTag2.equals("includingTaxCheckbox")){
		    	  
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  includingTaxCheckbox="0";
			   		}
			   		else{
			   			includingTaxCheckbox=  uomtext.getText().toString().trim();		
			   		}
		       }
		      if(secondPartOfTag2.equals("UOM")){
		    	  
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  UOM="0";
			   		}
			   		else{
			   			UOM=  uomtext.getText().toString().trim();	
			   		}
		     }
		       if(secondPartOfTag2.equals("fromDate")){
		    	   
		    	   if(uomtext.getText().toString().trim().equals("")){
		    		   fromDate="0";
				   		}
				   		else{
				   			fromDate=  uomtext.getText().toString().trim();	
				   		}
		     }
		      if(secondPartOfTag2.equals("todate")){
		    	  
		    	  if(uomtext.getText().toString().trim().equals("")){
		    		  todate="0";
				   		}
				   		else{
				   			todate=  uomtext.getText().toString().trim();	
				   		}
		     }
		       if(secondPartOfTag2.equals("remarktext")){
		    	  
		    	   if(uomtext.getText().toString().trim().equals("")){
		    		   remarktext="0";
					   		}
					   		else{
					   		 remarktext=  uomtext.getText().toString().trim();	
					   		}
		          }
		       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
				String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
				 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
				 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
				 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
				 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
				 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
				 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
				 fromDate=FROMDATE;
				 todate=TODATE;
				 UOM=UOMTXT;
			 includingTaxCheckbox=TAXTEXT;
			 STrateStringBK=STrateStringHM;
	         lastTransRTStringBK=lastTransRTStringHM;
	         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
		       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
			
		}
		

	}
	public void retreivingDataOfLastEdittext() {
		if(edtBoxs!=null ){
			if(!edtBoxs.getText().toString().equals("FROM")){
		 tag2= edtBoxs.getTag().toString().trim();
	String productID_inTAG2=tag2.split(Pattern.quote("^"))[0];
	 String secondPartOfTag2=tag2.split(Pattern.quote("^"))[1];
	 if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG2)){
			String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID_inTAG2).toString().trim();
			
			String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
			String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
			String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
			String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
			String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
			String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
			String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
			String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
			String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
			
		      if(secondPartOfTag2.equals("RateOffer")){
		    	 
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  RateOffer="0";
		   		}
		   		else{
		   		 RateOffer=  edtBoxs.getText().toString().trim();		
		   		}
				
			    }
		     if(secondPartOfTag2.equals("minDlv_qty")){
		    	
		    	 if(edtBoxs.getText().toString().trim().equals("")){
		    		 minDlv_qty="0";
		   		}
		   		else{
		   		 minDlv_qty=  edtBoxs.getText().toString().trim();		
		   		}
		       }
		      if(secondPartOfTag2.equals("includingTaxCheckbox")){
		    	  
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  includingTaxCheckbox="0";
			   		}
			   		else{
			   			includingTaxCheckbox=  edtBoxs.getText().toString().trim();		
			   		}
		       }
		      if(secondPartOfTag2.equals("UOM")){
		    	  
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  UOM="0";
			   		}
			   		else{
			   			UOM=  edtBoxs.getText().toString().trim();	
			   		}
		     }
		       if(secondPartOfTag2.equals("fromDate")){
		    	   
		    	   if(edtBoxs.getText().toString().trim().equals("")){
		    		   fromDate="0";
				   		}
				   		else{
				   			fromDate=  edtBoxs.getText().toString().trim();	
				   		}
		     }
		      if(secondPartOfTag2.equals("todate")){
		    	  
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  todate="0";
				   		}
				   		else{
				   			todate=  edtBoxs.getText().toString().trim();	
				   		}
		     }
		       if(secondPartOfTag2.equals("remarktext")){
		    	  
		    	   if(edtBoxs.getText().toString().trim().equals("")){
		    		   remarktext="0";
					   		}
					   		else{
					   		 remarktext=  edtBoxs.getText().toString().trim();	
					   		}
		          }
		       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
				String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
				 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
				 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
				 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
				 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
				 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
				 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
				 fromDate=FROMDATE;
				 todate=TODATE;
				 UOM=UOMTXT;
			 includingTaxCheckbox=TAXTEXT;
			 STrateStringBK=STrateStringHM;
	         lastTransRTStringBK=lastTransRTStringHM;
	         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
		       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
			
			}
	 else{
			String RateOffer="0";
			String minDlv_qty="0";
			String includingTaxCheckbox="0";
			String UOM="0";
			String fromDate="0";
			String todate="0";
			String remarktext="0";
			String STrateStringBK="0";
			 String lastTransRTStringBK="0";
			String StandardRateBeforeTaxBK="0";
			
			 if(secondPartOfTag2.equals("RateOffer")){
		    	 
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  RateOffer="0";
		   		}
		   		else{
		   		 RateOffer=  edtBoxs.getText().toString().trim();		
		   		}
				
			    }
		     if(secondPartOfTag2.equals("minDlv_qty")){
		    	
		    	 if(edtBoxs.getText().toString().trim().equals("")){
		    		 minDlv_qty="0";
		   		}
		   		else{
		   		 minDlv_qty=  edtBoxs.getText().toString().trim();		
		   		}
		       }
		      if(secondPartOfTag2.equals("includingTaxCheckbox")){
		    	  
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  includingTaxCheckbox="0";
			   		}
			   		else{
			   			includingTaxCheckbox=  edtBoxs.getText().toString().trim();		
			   		}
		       }
		      if(secondPartOfTag2.equals("UOM")){
		    	  
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  UOM="0";
			   		}
			   		else{
			   			UOM=  edtBoxs.getText().toString().trim();	
			   		}
		     }
		       if(secondPartOfTag2.equals("fromDate")){
		    	   
		    	   if(edtBoxs.getText().toString().trim().equals("")){
		    		   fromDate="0";
				   		}
				   		else{
				   			fromDate=  edtBoxs.getText().toString().trim();	
				   		}
		     }
		      if(secondPartOfTag2.equals("todate")){
		    	  
		    	  if(edtBoxs.getText().toString().trim().equals("")){
		    		  todate="0";
				   		}
				   		else{
				   			todate=  edtBoxs.getText().toString().trim();	
				   		}
		     }
		       if(secondPartOfTag2.equals("remarktext")){
		    	  
		    	   if(edtBoxs.getText().toString().trim().equals("")){
		    		   remarktext="0";
					   		}
					   		else{
					   		 remarktext=  edtBoxs.getText().toString().trim();	
					   		}
		          }
		       if(hashmapForDateFromDateTo_UOM.containsKey(productID_inTAG2)){
		       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
				String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
				 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
				 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
				 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
				 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
				 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
				 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
				 fromDate=FROMDATE;
				 todate=TODATE;
				 UOM=UOMTXT;
			 includingTaxCheckbox=TAXTEXT;
			 STrateStringBK=STrateStringHM;
	         lastTransRTStringBK=lastTransRTStringHM;
	         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
		       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
			
		}}}}

	}

	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
			int dayOfMonth) {
		 String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		 String mon=MONTHS[monthOfYear];
		 if(fromDateBool){
			 frmDate.setText(dayOfMonth+"-"+mon+"-"+year);
			 String FROMDATETAG=	frmDate.getTag().toString().trim();

				
			 
				String productID_inTAG2=FROMDATETAG.split(Pattern.quote("^"))[0];
				 String secondPartOfTag2=FROMDATETAG.split(Pattern.quote("^"))[1];
			String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
			String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
			 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
			 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
			 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
			 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
				 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
			 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
			 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
			 FROMDATE=dayOfMonth+"-"+mon+"-"+year;
			 hashmapForDateFromDateTo_UOM.put(productID_inTAG2, FROMDATE+"^"+TODATE+"^"+UOMTXT+"^"+TAXTEXT+"^"+STrateStringHM+"^"+lastTransRTStringHM+"^"+StandardRateBeforeTaxHM+"^"+ProductTaxRateHM);
			 saveCalendarFRomDATEDataToHashMap();
		 }
		 if(toDateBool){
			 todateText.setText(dayOfMonth+"-"+mon+"-"+year);
			 String FROMDATETAG=	todateText.getTag().toString().trim();

				
			 
				String productID_inTAG2=FROMDATETAG.split(Pattern.quote("^"))[0];
				 String secondPartOfTag2=FROMDATETAG.split(Pattern.quote("^"))[1];
			String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
			String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
			 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
			 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
			 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
			 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
				 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
			 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
			 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
			 TODATE=dayOfMonth+"-"+mon+"-"+year;
			 hashmapForDateFromDateTo_UOM.put(productID_inTAG2, FROMDATE+"^"+TODATE+"^"+UOMTXT+"^"+TAXTEXT+"^"+STrateStringHM+"^"+lastTransRTStringHM+"^"+StandardRateBeforeTaxHM+"^"+ProductTaxRateHM);
			 saveCalendarToDATEDataToHashMap(); 
		 }
		 toDateBool=false;
		 fromDateBool=false;
		 
		
		
	}
	public void saveCalendarToDATEDataToHashMap() {
		String ToDateTag=	todateText.getTag().toString().trim();

			
			 
		String productID_inTAG2=ToDateTag.split(Pattern.quote("^"))[0];
		 String secondPartOfTag2=ToDateTag.split(Pattern.quote("^"))[1];
		 if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG2)){
				String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID_inTAG2).toString().trim();
				
				String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
				String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
				String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
				String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
				String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
				String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
				String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
				String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
 				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
				String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
				
			      if(secondPartOfTag2.equals("RateOffer")){
			    	 
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  RateOffer="0";
			   		}
			   		else{
			   			RateOffer=  todateText.getText().toString().trim();		
			   		}
					
				    }
			     if(secondPartOfTag2.equals("minDlv_qty")){
			    	
			    	 if(todateText.getText().toString().trim().equals("")){
			    		 minDlv_qty="0";
			   		}
			   		else{
			   		 minDlv_qty=  todateText.getText().toString().trim();		
			   		}
			       }
			      if(secondPartOfTag2.equals("includingTaxCheckbox")){
			    	  
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  includingTaxCheckbox="0";
				   		}
				   		else{
				   			includingTaxCheckbox=  todateText.getText().toString().trim();		
				   		}
			       }
			      if(secondPartOfTag2.equals("UOM")){
			    	  
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  UOM="0";
				   		}
				   		else{
				   			UOM=  todateText.getText().toString().trim();	
				   		}
			     }
			       if(secondPartOfTag2.equals("fromDate")){
			    	   
			    	   if(todateText.getText().toString().trim().equals("")){
			    		   fromDate="0";
					   		}
					   		else{
					   			fromDate=  todateText.getText().toString().trim();	
					   		}
			     }
			      if(secondPartOfTag2.equals("todate")){
			    	  
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  todate="0";
					   		}
					   		else{
					   			todate=  todateText.getText().toString().trim();	
					   		}
			     }
			       if(secondPartOfTag2.equals("remarktext")){
			    	  
			    	   if(todateText.getText().toString().trim().equals("")){
			    		   remarktext="0";
						   		}
						   		else{
						   		 remarktext=  todateText.getText().toString().trim();	
						   		}
			          }
			       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
					String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
					 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
					 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
					 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
					 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
 					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
					 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
					 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
					 fromDate=FROMDATE;
					 todate=TODATE;
					 UOM=UOMTXT;
				 includingTaxCheckbox=TAXTEXT;
				 STrateStringBK=STrateStringHM;
		         lastTransRTStringBK=lastTransRTStringHM;
		         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
			       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
				
				}
		 else{
				String RateOffer="0";
				String minDlv_qty="0";
				String includingTaxCheckbox="0";
				String UOM="0";
				String fromDate="0";
				String todate="0";
				String remarktext="0";
				String STrateStringBK="0";
				 String lastTransRTStringBK="0";
				String StandardRateBeforeTaxBK="0";
				
				 if(secondPartOfTag2.equals("RateOffer")){
			    	 
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  RateOffer="0";
			   		}
			   		else{
			   		 RateOffer=  todateText.getText().toString().trim();		
			   		}
					
				    }
			     if(secondPartOfTag2.equals("minDlv_qty")){
			    	
			    	 if(todateText.getText().toString().trim().equals("")){
			    		 minDlv_qty="0";
			   		}
			   		else{
			   		 minDlv_qty=  todateText.getText().toString().trim();		
			   		}
			       }
			      if(secondPartOfTag2.equals("includingTaxCheckbox")){
			    	  
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  includingTaxCheckbox="0";
				   		}
				   		else{
				   			includingTaxCheckbox=  todateText.getText().toString().trim();		
				   		}
			       }
			      if(secondPartOfTag2.equals("UOM")){
			    	  
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  UOM="0";
				   		}
				   		else{
				   			UOM=  todateText.getText().toString().trim();	
				   		}
			     }
			       if(secondPartOfTag2.equals("fromDate")){
			    	   
			    	   if(todateText.getText().toString().trim().equals("")){
			    		   fromDate="0";
					   		}
					   		else{
					   			fromDate=  todateText.getText().toString().trim();	
					   		}
			     }
			      if(secondPartOfTag2.equals("todate")){
			    	  
			    	  if(todateText.getText().toString().trim().equals("")){
			    		  todate="0";
					   		}
					   		else{
					   			todate=  todateText.getText().toString().trim();	
					   		}
			     }
			       if(secondPartOfTag2.equals("remarktext")){
			    	  
			    	   if(todateText.getText().toString().trim().equals("")){
			    		   remarktext="0";
						   		}
						   		else{
						   		 remarktext=  todateText.getText().toString().trim();	
						   		}
			          }
			       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
					String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
					 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
					 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
					 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
					 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
 					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
					 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
					 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
					 fromDate=FROMDATE;
					 todate=TODATE;
					 UOM=UOMTXT;
				 includingTaxCheckbox=TAXTEXT;
				 STrateStringBK=STrateStringHM;
		         lastTransRTStringBK=lastTransRTStringHM;
		         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
			       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
				
			}

		

		}
	public void saveCalendarFRomDATEDataToHashMap() {
	String fromDateTag=	frmDate.getTag().toString().trim();

		
		 
	String productID_inTAG2=fromDateTag.split(Pattern.quote("^"))[0];
	 String secondPartOfTag2=fromDateTag.split(Pattern.quote("^"))[1];
	 if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG2)){
			String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID_inTAG2).toString().trim();
			
			String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
			String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
			String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
			String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
			String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
			String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
			String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];

				String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
			String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
			
		      if(secondPartOfTag2.equals("RateOffer")){
		    	 
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  RateOffer="0";
		   		}
		   		else{
		   			RateOffer=  frmDate.getText().toString().trim();		
		   		}
				
			    }
		     if(secondPartOfTag2.equals("minDlv_qty")){
		    	
		    	 if(frmDate.getText().toString().trim().equals("")){
		    		 minDlv_qty="0";
		   		}
		   		else{
		   		 minDlv_qty=  frmDate.getText().toString().trim();		
		   		}
		       }
		      if(secondPartOfTag2.equals("includingTaxCheckbox")){
		    	  
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  includingTaxCheckbox="0";
			   		}
			   		else{
			   			includingTaxCheckbox=  frmDate.getText().toString().trim();		
			   		}
		       }
		      if(secondPartOfTag2.equals("UOM")){
		    	  
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  UOM="0";
			   		}
			   		else{
			   			UOM=  frmDate.getText().toString().trim();	
			   		}
		     }
		       if(secondPartOfTag2.equals("fromDate")){
		    	   
		    	   if(frmDate.getText().toString().trim().equals("")){
		    		   fromDate="0";
				   		}
				   		else{
				   			fromDate=  frmDate.getText().toString().trim();	
				   		}
		     }
		      if(secondPartOfTag2.equals("todate")){
		    	  
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  todate="0";
				   		}
				   		else{
				   			todate=  frmDate.getText().toString().trim();	
				   		}
		     }
		       if(secondPartOfTag2.equals("remarktext")){
		    	  
		    	   if(frmDate.getText().toString().trim().equals("")){
		    		   remarktext="0";
					   		}
					   		else{
					   		 remarktext=  frmDate.getText().toString().trim();	
					   		}
		          }
		       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
				String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
				 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
				 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
				 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
				 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
				 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
				 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
				 fromDate=FROMDATE;
				 todate=TODATE;
				 UOM=UOMTXT;
			 includingTaxCheckbox=TAXTEXT;
			 STrateStringBK=STrateStringHM;
	         lastTransRTStringBK=lastTransRTStringHM;
	         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
		       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
			
			}
	 else{
			String RateOffer="0";
			String minDlv_qty="0";
			String includingTaxCheckbox="0";
			String UOM="0";
			String fromDate="0";
			String todate="0";
			String remarktext="0";
			 String STrateStringBK="0";
			 String lastTransRTStringBK="0";
			String StandardRateBeforeTaxBK="0";
			
			 if(secondPartOfTag2.equals("RateOffer")){
		    	 
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  RateOffer="0";
		   		}
		   		else{
		   		 RateOffer=  frmDate.getText().toString().trim();		
		   		}
				
			    }
		     if(secondPartOfTag2.equals("minDlv_qty")){
		    	
		    	 if(frmDate.getText().toString().trim().equals("")){
		    		 minDlv_qty="0";
		   		}
		   		else{
		   		 minDlv_qty=  frmDate.getText().toString().trim();		
		   		}
		       }
		      if(secondPartOfTag2.equals("includingTaxCheckbox")){
		    	  
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  includingTaxCheckbox="0";
			   		}
			   		else{
			   			includingTaxCheckbox=  frmDate.getText().toString().trim();		
			   		}
		       }
		      if(secondPartOfTag2.equals("UOM")){
		    	  
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  UOM="0";
			   		}
			   		else{
			   			UOM=  frmDate.getText().toString().trim();	
			   		}
		     }
		       if(secondPartOfTag2.equals("fromDate")){
		    	   
		    	   if(frmDate.getText().toString().trim().equals("")){
		    		   fromDate="0";
				   		}
				   		else{
				   			fromDate=  frmDate.getText().toString().trim();	
				   		}
		     }
		      if(secondPartOfTag2.equals("todate")){
		    	  
		    	  if(frmDate.getText().toString().trim().equals("")){
		    		  todate="0";
				   		}
				   		else{
				   			todate=  frmDate.getText().toString().trim();	
				   		}
		     }
		       if(secondPartOfTag2.equals("remarktext")){
		    	  
		    	   if(frmDate.getText().toString().trim().equals("")){
		    		   remarktext="0";
					   		}
					   		else{
					   		 remarktext=  frmDate.getText().toString().trim();	
					   		}
		          }
		       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG2);
				String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
				 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
				 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
				 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
				 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
					 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
				 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
				 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
				 fromDate=FROMDATE;
				 todate=TODATE;
				 UOM=UOMTXT;
			 includingTaxCheckbox=TAXTEXT;
			 STrateStringBK=STrateStringHM;
	         lastTransRTStringBK=lastTransRTStringHM;
	         StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
		       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
			
		}

	

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
   
if(!hasFocus){  
	String tag=	v.getTag().toString();
	EditText edittext = (EditText) v;
	 String productID_inTAG=tag.split(Pattern.quote("^"))[0];
	 String secondPartOfTag=tag.split(Pattern.quote("^"))[1];
	
	if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG)){
	String valueOnHashMap=	hashmapForBackupOfDynamicData.get(productID_inTAG).toString().trim();
	
	String RateOffer=valueOnHashMap.split(Pattern.quote("^"))[0];
	String minDlv_qty=valueOnHashMap.split(Pattern.quote("^"))[1];
	String includingTaxCheckbox=valueOnHashMap.split(Pattern.quote("^"))[2];
	String UOM=valueOnHashMap.split(Pattern.quote("^"))[3];
	String fromDate=valueOnHashMap.split(Pattern.quote("^"))[4];
	String todate=valueOnHashMap.split(Pattern.quote("^"))[5];
	String remarktext=valueOnHashMap.split(Pattern.quote("^"))[6];
	String STrateStringBK=valueOnHashMap.split(Pattern.quote("^"))[7];
		String lastTransRTStringBK=valueOnHashMap.split(Pattern.quote("^"))[8];
	String StandardRateBeforeTaxBK=valueOnHashMap.split(Pattern.quote("^"))[9];
	
      if(secondPartOfTag.equals("RateOffer")){
    	 
		if(edittext.getText().toString().trim().equals("")){
			RateOffer="0";
		}
		else{
			 RateOffer=  edittext.getText().toString().trim();			
		}
	    }
     if(secondPartOfTag.equals("minDlv_qty")){
    	
    	 if(edittext.getText().toString().trim().equals("")){
    		 minDlv_qty="0";
 		}
 		else{
 			 minDlv_qty=  edittext.getText().toString().trim();			
 		}
       }
      if(secondPartOfTag.equals("includingTaxCheckbox")){
    	  
    	  if(edittext.getText().toString().trim().equals("")){
    		  includingTaxCheckbox="0";
  		}
  		else{
  			includingTaxCheckbox=  edittext.getText().toString().trim();			
  		}
       }
      if(secondPartOfTag.equals("UOM")){
    	 
    	  
    	  if(edittext.getText().toString().trim().equals("")){
    		  UOM="0";
  		}
  		else{
  			 UOM=  edittext.getText().toString().trim();			
  		}
     }
       if(secondPartOfTag.equals("fromDate")){
    	   
    	   if(edittext.getText().toString().trim().equals("")){
    		   fromDate="0";
   		}
   		else{
   			fromDate=  edittext.getText().toString().trim();			
   		}
     }
      if(secondPartOfTag.equals("todate")){
    	 
    	  if(edittext.getText().toString().trim().equals("")){
    		  todate="0";
  		}
  		else{
  			 todate=  edittext.getText().toString().trim();			
  		}
     }
       if(secondPartOfTag.equals("remarktext")){
    	  
    	   if(edittext.getText().toString().trim().equals("")){
    		   remarktext="0";
   		}
   		else{
   		 remarktext=  edittext.getText().toString().trim();		
   		}
          }
       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG);
		String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
		 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
		 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
		 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
		 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
			 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
		 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
		 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
		 fromDate=FROMDATE;
		 todate=TODATE;
		 UOM=UOMTXT;
includingTaxCheckbox=TAXTEXT;
STrateStringBK=STrateStringHM;
lastTransRTStringBK=lastTransRTStringHM;
StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
       hashmapForBackupOfDynamicData.put(productID_inTAG, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
	
	}
	else{
		String RateOffer="0";
		String minDlv_qty="0";
		String includingTaxCheckbox="0";
		String UOM="0";
		String fromDate="0";
		String todate="0";
		String remarktext="0";
		 String STrateStringBK="0";
		 String lastTransRTStringBK="0";
		String StandardRateBeforeTaxBK="0";
		
		if(secondPartOfTag.equals("RateOffer")){
	    	 
			if(edittext.getText().toString().trim().equals("")){
				RateOffer="0";
			}
			else{
				 RateOffer=  edittext.getText().toString().trim();			
			}
		    }
	     if(secondPartOfTag.equals("minDlv_qty")){
	    	
	    	 if(edittext.getText().toString().trim().equals("")){
	    		 minDlv_qty="0";
	 		}
	 		else{
	 			 minDlv_qty=  edittext.getText().toString().trim();			
	 		}
	       }
	      if(secondPartOfTag.equals("includingTaxCheckbox")){
	    	  
	    	  if(edittext.getText().toString().trim().equals("")){
	    		  includingTaxCheckbox="0";
	  		}
	  		else{
	  			includingTaxCheckbox=  edittext.getText().toString().trim();			
	  		}
	       }
	      if(secondPartOfTag.equals("UOM")){
	    	 
	    	  
	    	  if(edittext.getText().toString().trim().equals("")){
	    		  UOM="0";
	  		}
	  		else{
	  			 UOM=  edittext.getText().toString().trim();			
	  		}
	     }
	       if(secondPartOfTag.equals("fromDate")){
	    	   
	    	   if(edittext.getText().toString().trim().equals("")){
	    		   fromDate="0";
	   		}
	   		else{
	   			fromDate=  edittext.getText().toString().trim();			
	   		}
	     }
	      if(secondPartOfTag.equals("todate")){
	    	 
	    	  if(edittext.getText().toString().trim().equals("")){
	    		  todate="0";
	  		}
	  		else{
	  			 todate=  edittext.getText().toString().trim();			
	  		}
	     }
	       if(secondPartOfTag.equals("remarktext")){
	    	  
	    	   if(edittext.getText().toString().trim().equals("")){
	    		   remarktext="0";
	   		}
	   		else{
	   		 remarktext=  edittext.getText().toString().trim();		
	   		}
	          }
	       
	       if(hashmapForDateFromDateTo_UOM.containsKey(productID_inTAG)){
	       String valueOfDateHmap=	 hashmapForDateFromDateTo_UOM.get(productID_inTAG);
	 		String FROMDATE=valueOfDateHmap.split(Pattern.quote("^"))[0];
	 		 String TODATE=valueOfDateHmap.split(Pattern.quote("^"))[1];
	 		 String UOMTXT=valueOfDateHmap.split(Pattern.quote("^"))[2];
	 		 String TAXTEXT=valueOfDateHmap.split(Pattern.quote("^"))[3];
	 		 String STrateStringHM=valueOfDateHmap.split(Pattern.quote("^"))[4];
				 String lastTransRTStringHM=valueOfDateHmap.split(Pattern.quote("^"))[5];
			 String StandardRateBeforeTaxHM=valueOfDateHmap.split(Pattern.quote("^"))[6];
			 String ProductTaxRateHM=valueOfDateHmap.split(Pattern.quote("^"))[7];
	 		 fromDate=FROMDATE;
	 		 todate=TODATE;
	 		 UOM=UOMTXT;
includingTaxCheckbox=TAXTEXT;
STrateStringBK=STrateStringHM;
lastTransRTStringBK=lastTransRTStringHM;
StandardRateBeforeTaxBK=StandardRateBeforeTaxHM;
	       hashmapForBackupOfDynamicData.put(productID_inTAG, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext+"^"+STrateStringBK+"^"+lastTransRTStringBK+"^"+StandardRateBeforeTaxBK+"^"+ProductTaxRateHM);
	       }
	}
	
	
}
else{
	   if(v instanceof EditText)
	   {edtBoxs=(EditText) v;
		   /*
		   edtBoxs=(EditText) v;
		   tag2= edtBoxs.getTag().toString().trim();
		   edtBoxs.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				String productID_inTAG2=tag2.split(Pattern.quote("^"))[0];
				 String secondPartOfTag2=tag2.split(Pattern.quote("^"))[1];
				 if(hashmapForBackupOfDynamicData.containsKey(productID_inTAG2)){
						String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID_inTAG2).toString().trim();
						
						String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
						String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
						String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
						String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
						String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
						String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
						String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
						
					      if(secondPartOfTag2.equals("RateOffer")){
					    	 
					    	  if(edtBoxs.getText().toString().trim().equals("")){
					    		  RateOffer="0";
					   		}
					   		else{
					   		 RateOffer=  edtBoxs.getText().toString().trim();		
					   		}
							
						    }
					     if(secondPartOfTag2.equals("minDlv_qty")){
					    	
					    	 if(edtBoxs.getText().toString().trim().equals("")){
					    		 minDlv_qty="0";
					   		}
					   		else{
					   		 minDlv_qty=  edtBoxs.getText().toString().trim();		
					   		}
					       }
					      if(secondPartOfTag2.equals("includingTaxCheckbox")){
					    	  
					    	  if(edtBoxs.getText().toString().trim().equals("")){
					    		  includingTaxCheckbox="0";
						   		}
						   		else{
						   			includingTaxCheckbox=  edtBoxs.getText().toString().trim();		
						   		}
					       }
					      if(secondPartOfTag2.equals("UOM")){
					    	  
					    	  if(edtBoxs.getText().toString().trim().equals("")){
					    		  UOM="0";
						   		}
						   		else{
						   			UOM=  edtBoxs.getText().toString().trim();	
						   		}
					     }
					       if(secondPartOfTag2.equals("fromDate")){
					    	   
					    	   if(edtBoxs.getText().toString().trim().equals("")){
					    		   fromDate="0";
							   		}
							   		else{
							   			fromDate=  edtBoxs.getText().toString().trim();	
							   		}
					     }
					      if(secondPartOfTag2.equals("todate")){
					    	  
					    	  if(edtBoxs.getText().toString().trim().equals("")){
					    		  todate="0";
							   		}
							   		else{
							   			todate=  edtBoxs.getText().toString().trim();	
							   		}
					     }
					       if(secondPartOfTag2.equals("remarktext")){
					    	  
					    	   if(edtBoxs.getText().toString().trim().equals("")){
					    		   remarktext="0";
								   		}
								   		else{
								   		 remarktext=  edtBoxs.getText().toString().trim();	
								   		}
					          }
					       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext);
						
						}
						else{
							String RateOffer="0";
							String minDlv_qty="0";
							String includingTaxCheckbox="0";
							String UOM="0";
							String fromDate="0";
							String todate="0";
							String remarktext="0";
							
							 if(secondPartOfTag2.equals("RateOffer")){
						    	 
						    	  if(edtBoxs.getText().toString().trim().equals("")){
						    		  RateOffer="0";
						   		}
						   		else{
						   		 RateOffer=  edtBoxs.getText().toString().trim();		
						   		}
								
							    }
						     if(secondPartOfTag2.equals("minDlv_qty")){
						    	
						    	 if(edtBoxs.getText().toString().trim().equals("")){
						    		 minDlv_qty="0";
						   		}
						   		else{
						   		 minDlv_qty=  edtBoxs.getText().toString().trim();		
						   		}
						       }
						      if(secondPartOfTag2.equals("includingTaxCheckbox")){
						    	  
						    	  if(edtBoxs.getText().toString().trim().equals("")){
						    		  includingTaxCheckbox="0";
							   		}
							   		else{
							   			includingTaxCheckbox=  edtBoxs.getText().toString().trim();		
							   		}
						       }
						      if(secondPartOfTag2.equals("UOM")){
						    	  
						    	  if(edtBoxs.getText().toString().trim().equals("")){
						    		  UOM="0";
							   		}
							   		else{
							   			UOM=  edtBoxs.getText().toString().trim();	
							   		}
						     }
						       if(secondPartOfTag2.equals("fromDate")){
						    	   
						    	   if(edtBoxs.getText().toString().trim().equals("")){
						    		   fromDate="0";
								   		}
								   		else{
								   			fromDate=  edtBoxs.getText().toString().trim();	
								   		}
						     }
						      if(secondPartOfTag2.equals("todate")){
						    	  
						    	  if(edtBoxs.getText().toString().trim().equals("")){
						    		  todate="0";
								   		}
								   		else{
								   			todate=  edtBoxs.getText().toString().trim();	
								   		}
						     }
						       if(secondPartOfTag2.equals("remarktext")){
						    	  
						    	   if(edtBoxs.getText().toString().trim().equals("")){
						    		   remarktext="0";
									   		}
									   		else{
									   		 remarktext=  edtBoxs.getText().toString().trim();	
									   		}
						          }
						       hashmapForBackupOfDynamicData.put(productID_inTAG2, RateOffer+"^"+minDlv_qty+"^"+includingTaxCheckbox+"^"+UOM+"^"+fromDate+"^"+todate+"^"+remarktext);
							
						}
				
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
		   
		
		
	   */}
	
    }
		
	}
	private class GetDatawhenNEW extends AsyncTask<Void, Void, Void>
	{		
		
		ProgressDialog pDialogGetStores=new ProgressDialog(getActivity());
		/*public GetSKUWiseSummaryForDay(SKUWiseSummary activity) 
		{
			pDialogGetStores = new ProgressDialog(activity);
		}*/
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
		    getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		    chkmsgnew=0;
		    QuotationActivityLayout.setEnabled(false);
            gifView.setVisibility(View.VISIBLE);
            dbengine.open();
			StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(QuotationActivity.storeID));
			dbengine.close();
			if(!hashmapForBackupOfDynamicData.isEmpty()){
				dbengine.open();
				//StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(QuotationActivity.storeID));
			  for(Map.Entry<String, String> entry:hashmapForBackupOfDynamicData.entrySet())
			   {
				  String UOMID="0";
				String productID=  entry.getKey().toString().trim();
             String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID).toString().trim();
             
				
				String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
				String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
				String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
				String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
				
				if(hashmapForUomData.containsKey(UOM)){
				UOMID=	hashmapForUomData.get(UOM);
				}
				
				String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
				String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
				String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
				
				String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
				String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
				String ProductTaxRateBK=valueOnHashMap2.split(Pattern.quote("^"))[10];
				

				if(!RateOffer.equals("0") || !minDlv_qty.equals("0"))
				{
					if(Double.parseDouble(RateOffer)==0.0)
					{
						chkmsgnew=1;
						//allMessageAlert("Please select Rate Offer");
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						stopSavingFlg=true;
						break;
					}
					//Minimum delivery quantity validation is commented now
					/*else if(Double.parseDouble(minDlv_qty)==0.0)
					{
						chkmsgnew=2;
						//allMessageAlert("Please select Minimum Order Quantity");
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						stopSavingFlg=true;
						break;
					}*/
					else if(UOMID.equals("0")){
						chkmsgnew=3;
						//allMessageAlert("Please select UOM");
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						stopSavingFlg=true;
						break;
					}
				}
				
				dbengine.deletetblSalesQuoteProductsMstr(productID,QuotationActivity.SalesQuoteId);
				//if(!RateOffer.equals("0") && !minDlv_qty.equals("0")   ){
				if(!RateOffer.equals("0")  ){
				 dbengine.SalesQuoteProductsMstr(QuotationActivity.SalesQuoteId, "0", productID, STrateStringBK, StandardRateBeforeTaxBK, RateOffer, includingTaxCheckbox, fromDate, todate, minDlv_qty, UOMID, remarktext, lastTransRTStringBK,ProductTaxRateBK);
				
			   }
				}
			  dbengine.close();
			  
			  if(stopSavingFlg==false)
			  {
			  hashmapForBackupOfDynamicData.clear();
			  hashmapForDateFromDateTo_UOM.clear();
			  }
			  
			  
			 // hashmapForBackupOfDynamicData.clear();
			 // hashmapForDateFromDateTo_UOM.clear();
			 /* allDataParent.removeAllViews();
				dynamcDtaContnrScrollview.removeAllViews();*/
			}
			
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.genTermRetrivingData));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		}

		@Override
		protected Void doInBackground(Void... args) 
		{
			
			
		try
	  	 {
			
			hashmapForDynamicViewFromDataBase=	dbengine.getFileredProductListQuotationMap(txt_Search.getText().toString().trim(), QuotationActivity.SalesQuoteId,StoreCurrentStoreType,ManufacturerID);	
				
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

            
			if(stopSavingFlg==true)
			{
				stopSavingFlg=false;
				//allMessageAlert("Please select UOM");
				if(chkmsgnew==3)
				  {
				  allMessageAlert("Please select UOM");
				  }
				  if(chkmsgnew==2)
				  {
					  allMessageAlert("Please select Minimum Dlv. Quantity");
				  }
				  if(chkmsgnew==1)
				  {
					  allMessageAlert("Please select Rate Offer");
				  }
			}
			else
			{

				if(hashmapForDynamicViewFromDataBase.size()>0){
					
					if(hashmapForDynamicViewFromDataBase.size()<250){
						if(allDataParent.getChildCount()>0){
							allDataParent.removeAllViews();
							dynamcDtaContnrScrollview.removeAllViews();
							addViewInTable();
						}
						else{
							if(allDataParent.getChildCount()>0){
								allDataParent.removeAllViews();
							}
							if(dynamcDtaContnrScrollview.getChildCount()>0){
								dynamcDtaContnrScrollview.removeAllViews();
							}
							
							addViewInTable();}
					}
					else{
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
							 AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
								alertDialogNoConn.setTitle("Information");
								alertDialogNoConn.setMessage("Please put some extra filter on Search-Box to fetch related product");
								//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
								alertDialogNoConn.setNeutralButton("OK",
										new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) 
											{
					                     dialog.dismiss();
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
							//allMessageAlert(" Resultset is too long, please put some extra filter on Search-Box");
					}
					
					
					
				}
				else{ allDataParent.removeAllViews();
				QuotationActivityLayout.setEnabled(true);
				gifView.setVisibility(View.GONE);
				dynamcDtaContnrScrollview.removeAllViews();
				getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
					
					Toast.makeText(getActivity().getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
				
				}
			}
			
		
           
		  
		}
	}
	private class GetDataWhenUpdate extends AsyncTask<Void, Void, Void>
	{		
		
		ProgressDialog pDialogGetStores=new ProgressDialog(getActivity());
		/*public GetSKUWiseSummaryForDay(SKUWiseSummary activity) 
		{
			pDialogGetStores = new ProgressDialog(activity);
		}*/
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
			QuotationActivityLayout.setEnabled(false);
			gifView.setVisibility(View.VISIBLE);
			chkmsgnew=0;
			dbengine.open();
			StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(QuotationActivity.storeID));
			dbengine.close();
			if(!hashmapForBackupOfDynamicData.isEmpty()){
				dbengine.open();
			  for(Map.Entry<String, String> entry:hashmapForBackupOfDynamicData.entrySet())
			   {
				  String UOMID="0";
				String productID=  entry.getKey().toString().trim();
             String valueOnHashMap2=	hashmapForBackupOfDynamicData.get(productID).toString().trim();
             
				
				String RateOffer=valueOnHashMap2.split(Pattern.quote("^"))[0];
				String minDlv_qty=valueOnHashMap2.split(Pattern.quote("^"))[1];
				String includingTaxCheckbox=valueOnHashMap2.split(Pattern.quote("^"))[2];
				String UOM=valueOnHashMap2.split(Pattern.quote("^"))[3];
				
				if(hashmapForUomData.containsKey(UOM)){
				UOMID=	hashmapForUomData.get(UOM);
				}
				
				String fromDate=valueOnHashMap2.split(Pattern.quote("^"))[4];
				String todate=valueOnHashMap2.split(Pattern.quote("^"))[5];
				String remarktext=valueOnHashMap2.split(Pattern.quote("^"))[6];
				
				String STrateStringBK=valueOnHashMap2.split(Pattern.quote("^"))[7];
				String lastTransRTStringBK=valueOnHashMap2.split(Pattern.quote("^"))[8];
				String StandardRateBeforeTaxBK=valueOnHashMap2.split(Pattern.quote("^"))[9];
				String ProductTaxRateBK=valueOnHashMap2.split(Pattern.quote("^"))[10];
				
				
				if(!RateOffer.equals("0") || !minDlv_qty.equals("0"))
				{
					if(Double.parseDouble(RateOffer)==0.0)
					{
						chkmsgnew=1;
						//allMessageAlert("Please select Rate Offer");
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						stopSavingFlg=true;
						break;
					}
					/*else if(Double.parseDouble(minDlv_qty)==0.0)
					{
						chkmsgnew=2;
						//allMessageAlert("Please select Minimum Order Quantity");
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						stopSavingFlg=true;
						break;
					}*/
					else if(UOMID.equals("0")){
						chkmsgnew=3;
						//allMessageAlert("Please select UOM");
						stopSavingFlg=true;
						 QuotationActivityLayout.setEnabled(true);
							gifView.setVisibility(View.GONE);
							getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
						break;
					}
				}
				
				dbengine.deletetblSalesQuoteProductsMstr(productID,QuotationActivity.SalesQuoteId);
			//	if(!RateOffer.equals("0") && !minDlv_qty.equals("0")   ){
				if(!RateOffer.equals("0")  ){
				 dbengine.SalesQuoteProductsMstr(QuotationActivity.SalesQuoteId, "0", productID, STrateStringBK, StandardRateBeforeTaxBK, RateOffer, includingTaxCheckbox, fromDate, todate, minDlv_qty, UOMID, remarktext, lastTransRTStringBK,ProductTaxRateBK);
				
			   }
				}
			  dbengine.close();
			  if(stopSavingFlg==false)
			  {
			  hashmapForBackupOfDynamicData.clear();
			  hashmapForDateFromDateTo_UOM.clear();
			  }
			 /* allDataParent.removeAllViews();
				dynamcDtaContnrScrollview.removeAllViews();*/
			}
			
			
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.genTermRetrivingData));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		}

		@Override
		protected Void doInBackground(Void... args) 
		{
			
			
		try
	  	 {
			
			hashmapForDynamicViewFromDataBase=	dbengine.getFileredProductListQuotationMap(txt_Search.getText().toString().trim(), QuotationActivity.SalesQuoteId,StoreCurrentStoreType,ManufacturerID);	
				
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

            
            if(stopSavingFlg==true)
			{
				stopSavingFlg=false;
				if(chkmsgnew==3)
				{
				allMessageAlert("Please select UOM");
				}
				if(chkmsgnew==2)
				{
					allMessageAlert("Please select Minimum Dlv. Quantity");
				}
				if(chkmsgnew==1)
				{
					allMessageAlert("Please select Rate Offer");
				}
			}
            else
            {
            	if(hashmapForDynamicViewFromDataBase.size()>0){
    				
    				if(hashmapForDynamicViewFromDataBase.size()<250){
    					if(allDataParent.getChildCount()>0){
    						allDataParent.removeAllViews();
    						dynamcDtaContnrScrollview.removeAllViews();
    						addViewInTable();
    					}
    					else{
    						if(allDataParent.getChildCount()>0){
    							allDataParent.removeAllViews();
    						}
    						if(dynamcDtaContnrScrollview.getChildCount()>0){
    							dynamcDtaContnrScrollview.removeAllViews();
    						}
    						
    						addViewInTable();}
    				}
    				else{
    					 QuotationActivityLayout.setEnabled(true);
    						gifView.setVisibility(View.GONE);
    						getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    						 AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
    							alertDialogNoConn.setTitle("Information");
    							alertDialogNoConn.setMessage("Please put some extra filter on Search-Box to fetch related product");
    							//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
    							alertDialogNoConn.setNeutralButton("OK",
    									new DialogInterface.OnClickListener() {
    										public void onClick(DialogInterface dialog, int which) 
    										{
    				                     dialog.dismiss();
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
    					//allMessageAlert(" Resultset is too long, please put some extra filter on Search-Box");
    				}
    				
    				
    				
    			}
    			else{ 
    				allDataParent.removeAllViews();
    				dynamcDtaContnrScrollview.removeAllViews();
    				QuotationActivityLayout.setEnabled(true);
    				gifView.setVisibility(View.GONE);
    				getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    				Toast.makeText(getActivity().getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
    				
    			}
    		
               
    	
            }
			
				  
		}
	}

}
