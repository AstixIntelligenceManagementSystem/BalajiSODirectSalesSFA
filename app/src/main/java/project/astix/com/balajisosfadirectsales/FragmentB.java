 package project.astix.com.balajisosfadirectsales;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


import com.astix.Common.CommonInfo;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.app.AlertDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FragmentB extends Fragment implements OnFocusChangeListener ,DatePickerDialog.OnDateSetListener{
	
	 public String ManufacturerIDOnPopupOkclick = "0";

	public String StoreVisitCode="NA";
	LinkedHashMap<String, String> hmapPrcDetailsFromDataBase;
	ViewPager viewPager;
	TextView TextOfCheckbox;
	Boolean popupEdittextNotfill=false;
	int totalOfEdittext=0;
	Boolean multipleCheckBoxflag=false;
	 CheckBox	CB_rateDistibution;
	Boolean multipleCheckBoxflagcopy=false;
	PRJDatabase dbengine;
	TextView	textRateDistribution;
	String globalmarchentName="Select";
	LinearLayout parentOfRateDistribution;
	LinkedHashMap<String, String> hashmapForUomData;
	LinkedHashMap<String, String> hashmapForSponsorMstr;
	LinkedHashMap<String, String> hashmapForManufacturerMstr;
	LinkedHashMap<String, String> hmapProductManufractureIDList;
	LinkedHashMap<String, String> hashmapSavingRateDistribution=new LinkedHashMap<String, String>();
	
	LinkedHashMap<String, String> hashmapForDatabaseRateDistributer=new LinkedHashMap<String, String>();
	View rootView;
	String newfullFileName;
	LinearLayout parentofScrollview;
	ScrollView dynamcDtaContnrScrollview;
	LinkedHashMap<String, String> hashmapForDynamicViewFromDataBase;
	 View mainView,dynamic_container;
	 LinearLayout allDataParent;
	 String SalesQuotePrcsId,SalesQuotePrcs,SalesQuoteValidFrom,SalesQuoteValidTo,SalesQuoteType;
	 public int checkAnyChanges=0;
	public  String prdID="0";
	boolean fromDateBool=false;
	boolean toDateBool=false;
	DatabaseAssistant DA ;
	 TextView frmDate,todateText;
	 Calendar calendar ;
	 DatePickerDialog datePickerDialog ;
	 int Year, Month, Day ;
	 TextView uomtext,marchent_text, MarchentSpinner;
	 AlertDialog.Builder alertDialog;
	 AlertDialog ad;
	 View convertView;
	 ListView listDistributor,listMerchant;
	 Button btnPrevious;
		Button btn_Save_TakeOrder;
		Button Done_btn;
	 ArrayAdapter<String> adapterDistributor,adapterMerchant;
	  String[] distributerList,merchantList;
	  public int StoreCurrentStoreType=0;
	  
	  //surbhi
	  String RateDistrbutionTextData="";
	  
	  public boolean stopSavingFlg=false;
	public FragmentB() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		 rootView= inflater.inflate(R.layout.fragmentb, container, false);
		 dbengine=new PRJDatabase(getActivity());
		 hashmapForUomData=dbengine.fnGettblUOMMstr();
		 viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		 DA = new DatabaseAssistant(getActivity());
		 UOMDataFromHashMap();
		 dynamicViewCreation();
		initializeButtons();

		StoreVisitCode=dbengine.fnGetStoreVisitCode(QuotationActivity.storeID);
		hmapProductManufractureIDList=dbengine.fnProductManufractureIDList();
		
		//RateDistributionSection();
		return rootView;
	}
	public void SetRateDistributionSectionFromDatabaseToLayout() {
		LinkedHashMap<String, ArrayList<String>> RateDistributionDataFromDatabase=	dbengine.fnGettblRateDistribution(QuotationActivity.SalesQuoteId, QuotationActivity.storeID);
if(RateDistributionDataFromDatabase!=null && !RateDistributionDataFromDatabase.isEmpty()){
	
	textRateDistribution.setOnClickListener(new OnClickListener()
	{	
		@Override
		public void onClick(View arg0) 
		{
			openPopupForRateDistribution();
		}
	});
	CB_rateDistibution.setChecked(true);
	String globalSponsorString="";
	if(RateDistributionDataFromDatabase.size()>1){
		 multipleCheckBoxflagcopy=true;
         multipleCheckBoxflag=true;
	}
	for(Map.Entry<String, ArrayList<String>> entry:RateDistributionDataFromDatabase.entrySet())
	{
		
		
				String keyString = entry.getKey().toString().trim();
				ArrayList<String> abc=entry.getValue();
				//String rateDistributionDataValue = entry.getValue().toString().trim();
				String SalesQuoteSponsorIDString = abc.get(0);
				String ManufacturerID = abc.get(1);
				String PercentageString = abc.get(2);
				String SponsorDescrString = abc.get(3);
				String ManufacturerNameString = abc.get(4);
				hashmapSavingRateDistribution.put(SponsorDescrString, PercentageString);
				hashmapForDatabaseRateDistributer.put(SponsorDescrString, PercentageString);
				/*if(globalSponsorString.equals("")){
					globalSponsorString=SponsorDescrString;
				}
				else{
					globalSponsorString=globalSponsorString+","+SponsorDescrString;
				}
				if(!ManufacturerNameString.equals("")){
					globalmarchentName=ManufacturerNameString;
				}*/
				
				if(globalSponsorString.equals(""))

				{
					if(SponsorDescrString.equals("Manufacturer"))
					{

						globalSponsorString=SponsorDescrString+":"+ManufacturerNameString+"- "+PercentageString+"%";
					}
					/*else if(SponsorDescrString.equals("TJUK"))
					{
						globalSponsorString=SponsorDescrString+":"+PercentageString+"%";
					}
					else if(SponsorDescrString.equals("Self"))
					{
						globalSponsorString=SponsorDescrString+":"+PercentageString+"%";
					}*/
					else{
						globalSponsorString=SponsorDescrString+":"+PercentageString+"%";
						
					}

				}
				else
				{
					if(SponsorDescrString.equals("Manufacturer"))
					{
						globalSponsorString=globalSponsorString+":"+SponsorDescrString+"- "+ManufacturerNameString+":"+PercentageString+"%";	
					}
					/*else if(SponsorDescrString.equals("TJUK"))
					{
						globalSponsorString=globalSponsorString+" , "+SponsorDescrString+":"+PercentageString+"%";
					}
					else if(SponsorDescrString.equals("Self"))
					{
						globalSponsorString=globalSponsorString+" , "+SponsorDescrString+":"+PercentageString+"%";
					}*/
					else{
						globalSponsorString=globalSponsorString+" , "+SponsorDescrString+":"+PercentageString+"%";
						
					}
				}
				if(!ManufacturerNameString.equals(""))
				{
					globalmarchentName=ManufacturerNameString;
				}
	
	}
	RateDistrbutionTextData=globalSponsorString;
	SpannableString content1 = new SpannableString(RateDistrbutionTextData);
    content1.setSpan(new UnderlineSpan(), 0, RateDistrbutionTextData.length(), 0);
    textRateDistribution.setText(content1);
}
	}
	
	public void RateDistributionSection() {
		hashmapForSponsorMstr = dbengine.fnGettblSalesQuoteSponsorMstr();
		hashmapForManufacturerMstr = dbengine.fnGettblManufacturerMstrMain();
		CB_rateDistibution = (CheckBox) rootView
				.findViewById(R.id.CB_rateDistibution);
		 TextOfCheckbox = (TextView) rootView.findViewById(R.id.TextOfCheckbox);
		textRateDistribution = (TextView) rootView
				.findViewById(R.id.textRateDistribution);
		CB_rateDistibution.setVisibility(View.GONE);
		TextOfCheckbox.setVisibility(View.GONE);
		textRateDistribution.setVisibility(View.GONE);

if(hashmapForDynamicViewFromDataBase!=null && !hashmapForDynamicViewFromDataBase.isEmpty() && hashmapForDynamicViewFromDataBase.size()>0)
{
	/*if(!hashmapForDynamicViewFromDataBase.isEmpty())
	{
		if(hashmapForDynamicViewFromDataBase.size()>0)
		{*/
			CB_rateDistibution.setVisibility(View.VISIBLE);
			TextOfCheckbox.setVisibility(View.VISIBLE);
			textRateDistribution.setVisibility(View.VISIBLE);
			CB_rateDistibution.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					if(CB_rateDistibution.isChecked())
					{
						openPopupForRateDistribution();
					}
					else
					{
						
						hashmapForDatabaseRateDistributer.clear();
						hashmapSavingRateDistribution.clear();
						textRateDistribution.setText("");
						multipleCheckBoxflag=false;
						multipleCheckBoxflagcopy=false;
						
					}
					
					
					
				}
			});
			
		}
	}
	/*}

	}*/
	public void openPopupForRateDistribution() {


		final Dialog dialog = new Dialog(getActivity());
	    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.setTitle("Calculation");
	    dialog.setCancelable(false);
	    dialog.setContentView(R.layout.dialog_custom_for_quot);
	    parentOfRateDistribution=  (LinearLayout) dialog.findViewById(R.id.parentOfRateDistribution);
	 //CB_multiple code here
	    final CheckBox  CB_multiple=  (CheckBox) dialog.findViewById(R.id.CB_multiple);
	    CB_multiple.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				CheckBox checkboxmultiple=(CheckBox) arg0;
				if(checkboxmultiple.isChecked())
				{
					multipleCheckBoxflag=true;
					checkCheckboxOfRateDistribution();
					
				}
				else
				{
					multipleCheckBoxflag=false;
					checkCheckboxOfRateDistribution();
					
				}
				
			}
		});
	    if(multipleCheckBoxflagcopy){
	    	CB_multiple.setChecked(true);
	    }
	    
	if(hashmapForSponsorMstr!=null && !hashmapForSponsorMstr.isEmpty() && hashmapForSponsorMstr.size()>0 )
	    {
	    	//&& hashmapForManufacturerMstr!=null && !hashmapForManufacturerMstr.isEmpty() && hashmapForManufacturerMstr.size()>0
	    	 for(Map.Entry<String, String> entry:hashmapForSponsorMstr.entrySet())
				{
					
					 String sponsorName=entry.getKey().toString().trim();
		             
		             String value=entry.getValue().toString().trim();
		           
		            
					  View dynamic_container=getActivity().getLayoutInflater().inflate(R.layout.custom_row_for_popup,null);
					  final CheckBox CB_Sponsor=(CheckBox) dynamic_container.findViewById(R.id.CB_Sponsor);
					  //setting drawable as radiobutton
					  if(!multipleCheckBoxflagcopy){
						  CB_Sponsor.setButtonDrawable(getResources().getDrawable(R.drawable.radio_button_background));   
					  }
                      if(multipleCheckBoxflagcopy){
                    	  CB_Sponsor.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_background)); 
					  }
					  CB_Sponsor.setText(sponsorName);
					  CB_Sponsor.setTag(sponsorName);
					  final EditText ED_Sponsor=(EditText) dynamic_container.findViewById(R.id.ED_Sponsor);
					  ED_Sponsor.setTag(sponsorName);
					  
					  if(hashmapForDatabaseRateDistributer.containsKey(sponsorName)){
					String sponsorNameString=	  hashmapForDatabaseRateDistributer.get(sponsorName);
					CB_Sponsor.setChecked(true);
					ED_Sponsor.setText(sponsorNameString);
					ED_Sponsor.setEnabled(true);
					
					  }
					  CB_Sponsor.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							CheckBox checkboxMerchant=(CheckBox) arg0;
							if(CB_multiple.isChecked()){
								if(CB_Sponsor.isChecked()){
									checkboxMerchant.setChecked(true);
									ED_Sponsor.setEnabled(true);
								}
								else{
									checkboxMerchant.setChecked(false);
									ED_Sponsor.setEnabled(false);
									
								}
								
								
							}
							if(!CB_multiple.isChecked()){
							String ff=	ED_Sponsor.getText().toString();
								checkCheckboxOfRateDistribution();
								checkboxMerchant.setChecked(true);
								ED_Sponsor.setEnabled(true);
								ED_Sponsor.setText("100");
							}
							if(checkboxMerchant.isChecked()){
								
								if(MarchentSpinner!=null && checkboxMerchant.getText().toString().contains("Manufacturer")){
									MarchentSpinner.setVisibility(View.VISIBLE);
								}
								
							}
							else
							{
								
									ED_Sponsor.setText("");
								
								
								
								if(MarchentSpinner!=null && checkboxMerchant.getText().toString().contains("Manufacturer"))
								{
									MarchentSpinner.setVisibility(View.GONE);
								}
								
							}
							
						}
					});
					  parentOfRateDistribution.addView(dynamic_container);
					
					  
					  if(sponsorName.contains("Manufacturer"))
						 {
						  
						  
							 View dynamic_Spinner=getActivity().getLayoutInflater().inflate(R.layout.spinner_custom_row,null);
							   MarchentSpinner=(TextView) dynamic_Spinner.findViewById(R.id.MarchentSpinner);
							   MarchentSpinner.setTag(sponsorName);
							   MarchentSpinner.setVisibility(View.GONE);
							   if(hashmapForDatabaseRateDistributer.containsKey(sponsorName)){
								  
										MarchentSpinner.setVisibility(View.VISIBLE);
										MarchentSpinner.setText(globalmarchentName);
									
								   
							   }
							   parentOfRateDistribution.addView(dynamic_Spinner);
							   MarchentSpinner.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View arg0) {
									
									
									marchent_text = (TextView) arg0;
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
								  	            		 merchantList[index]="Select";
								  	            		
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
								  		adapterDistributor = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, merchantList);
								  
	
								  
								  
								  listMerchant.setAdapter(adapterDistributor);
								  alertDialog.setView(convertView);
							        alertDialog.setTitle("Manufacturer");
							        listMerchant.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											String abc=listMerchant.getItemAtPosition(arg2).toString().trim();
											marchent_text.setText(abc);
											ad.dismiss();
											
										}
									});
							        ad=alertDialog.show();}
							});
							 
						 }
				}
	    	  View textview_rowForSpace=getActivity().getLayoutInflater().inflate(R.layout.textview_row,null);
	    	TextView  blanktext= (TextView) textview_rowForSpace.findViewById(R.id.blanktext);
	    	blanktext.setTag("blanktext");
	    	  parentOfRateDistribution.addView(textview_rowForSpace);
	    	  
	    }
	   
	   
	   
	    Button btOK=  (Button) dialog.findViewById(R.id.button1);
		  Button btncncle=  (Button) dialog.findViewById(R.id.btncncle);
		 
		  
	    dialog.show();
	    btOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SavingDataToHashmap();
				if(Validate()){
					
					hashmapForDatabaseRateDistributer.clear();
					hashmapForDatabaseRateDistributer.putAll(hashmapSavingRateDistribution);
					multipleCheckBoxflagcopy=multipleCheckBoxflag;
					//mmmmmmmmmmmmmmmmmmmmmmmmmmm
					
					
					
					if(CB_rateDistibution.isChecked()){
						if(hashmapForDatabaseRateDistributer!=null && !hashmapForDatabaseRateDistributer.isEmpty()){
							for(Map.Entry<String, String> entry:hashmapForDatabaseRateDistributer.entrySet())
			                  {
								
								String SponsorNameKEY = entry.getKey().toString().trim();
								if (SponsorNameKEY.equals("Manufacturer")) {
									if (hashmapForManufacturerMstr.containsKey(globalmarchentName)) {
										String MnfactrName = hashmapForManufacturerMstr.get(globalmarchentName);
										ManufacturerIDOnPopupOkclick = MnfactrName.split(Pattern.quote("^"))[0];
									}

								}
							}
						}
						
					}
					int flgNewcheck=0;
					if(!hashmapForDatabaseRateDistributer.isEmpty())
					{
						for(Map.Entry<String, String> entry:hashmapForDatabaseRateDistributer.entrySet())
						{
							String key=entry.getKey();
							String value=entry.getValue();
							if(!RateDistrbutionTextData.equals(""))
							{
								if(key.equals("Manufacturer"))
								{
									flgNewcheck=1;
								}
							}
						}
					}
					
					if(flgNewcheck==1)
					{
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(
								getActivity());
						alertDialog.setTitle("Information");

						alertDialog.setCancelable(false);
						alertDialog.setMessage("All the products of other manufacturer will be removed! ");
						alertDialog.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										dialog.dismiss();
										

									
										RowDeletedWhenOtherManufacturer(ManufacturerIDOnPopupOkclick);

										

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
					

					
					
					callHmapDataForRateDistribution();
				
					dialog.dismiss();
					
				}
				
			
			}
		});
	    btncncle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				if(hashmapForDatabaseRateDistributer.isEmpty()){
					textRateDistribution.setText("");
					multipleCheckBoxflag=false;
					multipleCheckBoxflagcopy=false;
					CB_rateDistibution.setChecked(false);
				}
			}
		});
		
	
		
	

	}
	public void RowDeletedWhenOtherManufacturer(String ManufacturerIDText) {
		
		if(hmapProductManufractureIDList.size()>0)
		{
			if(hashmapForDynamicViewFromDataBase.size()>0)
			{
				if(!ManufacturerIDText.equals("0"))
				{
					for(Map.Entry<String, String> entry:hashmapForDynamicViewFromDataBase.entrySet())
					{
						LinearLayout llnew=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(entry.getKey().toString().trim());
						if(hmapProductManufractureIDList.containsKey(entry.getKey().toString().trim()))
						{
							if(!hmapProductManufractureIDList.get(entry.getKey().toString().trim()).equals(ManufacturerIDText))
							{
								dbengine.open();
								dbengine.deletetblSalesQuoteProductsMstr(entry.getKey().toString().trim(),QuotationActivity.SalesQuoteId);
								dbengine.close();
								
								LinearLayout ll=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(entry.getKey().toString().trim());
								ll.setVisibility(View.GONE);
							}
						}
					}	
				}
			}
		}
		
		
		
		int intforColorindex=1;
		int flgChecktoShowHideRateDistribution=0;
		if(hashmapForDynamicViewFromDataBase.size()>0)
		{

			for(Map.Entry<String, String> entry:hashmapForDynamicViewFromDataBase.entrySet())
			{
				LinearLayout llnew=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(entry.getKey().toString().trim());
				if(llnew.getVisibility()==View.VISIBLE)
				{
					flgChecktoShowHideRateDistribution=1;
					if((intforColorindex%2)==0){
						llnew.setBackgroundColor(getResources().getColor(R.color.tablecolor));

					}
					else
					{
						llnew.setBackgroundColor(Color.parseColor("#ffffff"));
					}
					intforColorindex++;
				}
			}
		}
		if(flgChecktoShowHideRateDistribution==0)
		{
			CB_rateDistibution.setVisibility(View.GONE);
			CB_rateDistibution.setChecked(false);
			TextOfCheckbox.setVisibility(View.GONE);
			textRateDistribution.setVisibility(View.GONE);
			hashmapForDatabaseRateDistributer.clear();
			hashmapSavingRateDistribution.clear();
			textRateDistribution.setText("");
			multipleCheckBoxflag=false;
			multipleCheckBoxflagcopy=false;
			dbengine.open();
			dbengine.deletetblRateDistribution(QuotationActivity.SalesQuoteId, QuotationActivity.storeID);
			dbengine.close();
		}

	}
	public void callForViewFilledData()
	{
		System.out.println("Shivam Babu Start:"+Calendar.SECOND);
		try {
			//Toast.makeText(getActivity().getApplicationContext(), "Resume", Toast.LENGTH_SHORT).show();
			//saveModifiedDataToDatabase();
			UOMDataFromHashMap();
			if(CommonInfo.quatationFlag.equals("NEW")){
				if(CommonInfo.SalesQuoteId.equals("BLANK")){
					Toast.makeText(getActivity().getApplicationContext(), "First fill the customer info. and click on Save & Next before taking Quotation", Toast.LENGTH_SHORT).show();
					
				}
				else{
					getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
					 FromDate_ToDate_and_FlagValuesFromTable();
					
					 GetDatawhenNEW task = new GetDatawhenNEW();
				  task.execute();
				  	
				    }
				}
			if(CommonInfo.quatationFlag.equals("UPDATE")){
				FromDate_ToDate_and_FlagValuesFromTable();
				GetDataWhenUpdate task = new GetDataWhenUpdate();
				  task.execute();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initializeButtons()
	{
		 btnPrevious=	(Button) rootView.findViewById(R.id.btnPrevious);
		btnPrevious.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveModifiedDataToDatabase();
				
				if(stopSavingFlg==true)
				{
					stopSavingFlg=false;
					
				}
				else
				{
				viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
				viewPager.setCurrentItem(1);
				Fragment ff=		((Myadapter)viewPager.getAdapter()).getRegisteredFragment(1);
				EditText txt_Search=(EditText) ff.getView().findViewById(R.id.txt_Search);
				((FragmentA) ff).clearViewWhenSlide();
				if(!txt_Search.getText().toString().trim().equals("")){
					//android.app.FragmentManager fm = getFragmentManager(); 
					//Fragment fragm = ff; 
					try
					{
						
					/*((FragmentA) ff).hashmapForBackupOfDynamicData.clear();
					((FragmentA) ff).hashmapForDateFromDateTo_UOM.clear();*/
					}
					catch(Exception ex)
					{
						
					}
					
					Button btnGo=	(Button) ff.getView().findViewWithTag("btnGo");
					btnGo.performClick();
				}
				}
			
			}
		});	
		
		
		 btn_Save_TakeOrder=	(Button) rootView.findViewById(R.id.btn_Save_TakeOrder);
		btn_Save_TakeOrder.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				saveModifiedDataToDatabase();
				if(stopSavingFlg==true)
				{
					stopSavingFlg=false;
					
				}
				else
				{
					if(hashmapForDynamicViewFromDataBase!=null)
					{
						AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
					alertDialogNoConn.setTitle("Information");
					alertDialogNoConn.setMessage("Data has been succesfully saved!");
					//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
					alertDialogNoConn.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) 
								{
									
			              dialog.dismiss();
			          	StoreSelection.flgChangeRouteOrDayEnd=0;
			          	 Intent syncIntent = new Intent(getActivity(), ProductOrderReview.class);
			             
			             syncIntent.putExtra("imei",QuotationActivity.imei);
			             syncIntent.putExtra("storeID",QuotationActivity.storeID);
			             syncIntent.putExtra("userdate",QuotationActivity.date);
			             syncIntent.putExtra("pickerDate",QuotationActivity.pickerDate);
			             syncIntent.putExtra("SN",QuotationActivity.selStoreName);
			             startActivity(syncIntent);
			             getActivity().finish();
			             
			              
			           
								}
							});
					alertDialogNoConn.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogNoConn.create();
					alert.show();}
					else
					{
						AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
						alertDialogNoConn.setTitle("Information");
						alertDialogNoConn.setMessage("No Quotation Taken, Now you can click on the back icon above to go back to Visit Summary");
						//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
						alertDialogNoConn.setNeutralButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) 
									{
				              dialog.dismiss();
				            
				             
				              
				           
									}
								});
						alertDialogNoConn.setIcon(R.drawable.info_ico);
						AlertDialog alert = alertDialogNoConn.create();
						alert.show();
					}
				}
				
				 
			
				
			}
		});
		 Done_btn=	(Button) rootView.findViewById(R.id.Done_btn);
		Done_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveModifiedDataToDatabase();
				if(stopSavingFlg==true)
				{
					stopSavingFlg=false;
					
				}
				else
				{
					if(hashmapForDynamicViewFromDataBase!=null)
					{
					if(isOnline())
					{
						new FullSyncDataNow().execute();
					}
					else
					{

						AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
					alertDialogNoConn.setTitle("Information");
					alertDialogNoConn.setMessage("Internet not avilable therefore couldnot send data but data has been succesfully saved!");
					//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
					alertDialogNoConn.setNeutralButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) 
								{
									
			              dialog.dismiss();
			              
			             /* imei = getStorei.getStringExtra("imei").trim();
			              pickerDate = getStorei.getStringExtra("pickerDate").trim();
			              userDate = getStorei.getStringExtra("userDate");*/
			          	StoreSelection.flgChangeRouteOrDayEnd=0;
			          	Intent syncIntent = new Intent(getActivity(), StoreSelection.class);
			            syncIntent.putExtra("imei",QuotationActivity.imei);
			            syncIntent.putExtra("userDate",QuotationActivity.date);
			            syncIntent.putExtra("pickerDate",QuotationActivity.pickerDate);
			            
			            startActivity(syncIntent);
			            getActivity().finish();
						getActivity().finish();
			             
			              
			           
								}
							});
					alertDialogNoConn.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogNoConn.create();
					alert.show();
					}
					}
					else
					{
						AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
						alertDialogNoConn.setTitle("Information");
						alertDialogNoConn.setMessage("No Quotation Taken, Now you can click on the back icon above to go back to Visit Summary");
						//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
						alertDialogNoConn.setNeutralButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) 
									{
				              dialog.dismiss();
				            
				             
				              
				           
									}
								});
						alertDialogNoConn.setIcon(R.drawable.info_ico);
						AlertDialog alert = alertDialogNoConn.create();
						alert.show();
					}
				}
				
				 
			}
		});	
	}
	private void refreshAllPage() {
		

	}
	
	
	public void UOMDataFromHashMap() {
		hashmapForUomData=dbengine.fnGettblUOMMstr();
		int index=0;
		if(hashmapForUomData!=null)
    	{
			distributerList=new String[hashmapForUomData.size()+1];
			LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hashmapForUomData);
			 Set set2 = map.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext()) {
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
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try
		{
			
			hmapPrcDetailsFromDataBase=dbengine.getSalesQuotePrcsMstr();
		}
		catch(Exception ex)
		{
			
		}
		/*try {
			//Toast.makeText(getActivity().getApplicationContext(), "Resume", Toast.LENGTH_SHORT).show();
			//saveModifiedDataToDatabase();
			 UOMDataFromHashMap();
			if(CommonInfo.quatationFlag.equals("NEW")){
				if(CommonInfo.SalesQuoteId.equals("BLANK")){
					Toast.makeText(getActivity().getApplicationContext(), "First fill the customer info. and click on Done before taking Quotation", Toast.LENGTH_SHORT).show();
					
				}
				else{
					getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
					 FromDate_ToDate_and_FlagValuesFromTable();
					GetDatawhenNEW task = new GetDatawhenNEW();
				  task.execute();
				    }
				}
			 
			if(CommonInfo.quatationFlag.equals("UPDATE")){
				FromDate_ToDate_and_FlagValuesFromTable();
				GetDataWhenUpdate task = new GetDataWhenUpdate();
				  task.execute();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
		
		
	public void dynamicViewCreation() {
		parentofScrollview=	(LinearLayout)rootView. findViewById(R.id.parentofScrollview);
		dynamcDtaContnrScrollview=(ScrollView)rootView. findViewById(R.id.dynamcDtaContnrScrollview);
		dynamic_container=getActivity().getLayoutInflater().inflate(R.layout.dynamic_data_container, dynamcDtaContnrScrollview,false);
		   allDataParent= 	(LinearLayout) dynamic_container.findViewById(R.id.dynamic_container);
		 
		  // addViewInTable();

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
			
			
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.genTermRetrivingData));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
			dbengine.open();
			StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(QuotationActivity.storeID));
			dbengine.close();
		}

		@Override
		protected Void doInBackground(Void... args) 
		{
			
			
		try
	  	 {
			hashmapForDynamicViewFromDataBase=	dbengine.getDefaultProductListQuotationMap(QuotationActivity.SalesQuoteId,StoreCurrentStoreType);	
				
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
			
			
			try {
				Log.i("SvcMgr", "Service Execution cycle completed");
				
				if(pDialogGetStores.isShowing()) 
				  {
					   pDialogGetStores.dismiss();
				  }

				
				
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
				else{
					allDataParent.removeAllViews();
					dynamcDtaContnrScrollview.removeAllViews();
					
					getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE); 
					Toast.makeText(getActivity().getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
				
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			System.out.println("Shivam Babu End:"+Calendar.SECOND);
		  
		}
	}
	
	//surbhi
	public void callHmapDataForRateDistribution()
	{
		RateDistrbutionTextData="";
		if(!hashmapForDatabaseRateDistributer.isEmpty())
		{
			for(Map.Entry<String, String> entry:hashmapForDatabaseRateDistributer.entrySet())
			{
				String key=entry.getKey();
				String value=entry.getValue();
				if(value.equals("")){
					value="0";
				}
				
			/*	if(RateDistrbutionTextData.equals(""))
				{
						RateDistrbutionTextData=key;
				}
				else
				{
					RateDistrbutionTextData=RateDistrbutionTextData+","+key;	
				}
			*/
				if(RateDistrbutionTextData.equals(""))
				{
					if(key.equals("Manufacturer"))
					{
						RateDistrbutionTextData=key+":"+globalmarchentName+"- "+value+"%";
					}
					/*else if(key.equals("TJUK"))
					{
						RateDistrbutionTextData=key+":"+value+"%";
					}
					else if(key.equals("Self"))
					{
						RateDistrbutionTextData=key+":"+value+"%";
					}*/
					else{
						RateDistrbutionTextData=key+":"+value+"%";
					}
				}
				else
				{
					if(key.equals("Manufacturer"))
					{
						RateDistrbutionTextData=RateDistrbutionTextData+" , "+key+":"+globalmarchentName+"- "+value+"%";
					}
					/*else if(key.equals("TJUK"))
					{
						RateDistrbutionTextData=RateDistrbutionTextData+" , "+key+":"+value+"%";
					}
					else if(key.equals("Self"))
					{
						RateDistrbutionTextData=RateDistrbutionTextData+" , "+key+":"+value+"%";
					}*/
					else{
						RateDistrbutionTextData=RateDistrbutionTextData+" , "+key+":"+value+"%";
						
					}
				}

				SpannableString content1 = new SpannableString(RateDistrbutionTextData);
			    content1.setSpan(new UnderlineSpan(), 0, RateDistrbutionTextData.length(), 0);
			    textRateDistribution.setText(content1);
			}
		}
		else
		{
			
		}
		
		textRateDistribution.setOnClickListener(new OnClickListener()
		{	
			@Override
			public void onClick(View arg0) 
			{
				openPopupForRateDistribution();
			}
		});
		
		
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
			
			
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.genTermRetrivingData));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
			dbengine.open();
			StoreCurrentStoreType=Integer.parseInt(dbengine.fnGetStoreTypeOnStoreIdBasis(QuotationActivity.storeID));
			dbengine.close();
		}

		@Override
		protected Void doInBackground(Void... args) 
		{
			
			
		try
	  	 {
			hashmapForDynamicViewFromDataBase=	dbengine.getDefaultProductListQuotationMap( QuotationActivity.SalesQuoteId,StoreCurrentStoreType);	
				
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
			else{
				allDataParent.removeAllViews();
				dynamcDtaContnrScrollview.removeAllViews();

				
				Toast.makeText(getActivity().getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
				
			}
		
           
		  
		}
	}
	public void addViewInTable() {
		RateDistributionSection();
		SetRateDistributionSectionFromDatabaseToLayout();
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
	
	
	

	
					
					View	 mainView =  getActivity().getLayoutInflater().inflate(R.layout.pos_data,null);
					LinearLayout ll=	(LinearLayout) mainView.findViewById(R.id.posdata1);
					if((index%2)==0){
						ll.setBackgroundColor(getResources().getColor(R.color.tablecolor));
					}
					ll.setTag(ProductID);
					ll.setVisibility(View.VISIBLE);
					
					TextView	txtVProductname=(TextView) mainView.findViewById(R.id.txtVProductname);
					txtVProductname.setText(productname);
					
					TextView	st_Rate=(TextView) mainView.findViewById(R.id.st_Rate);
					//st_Rate.setText(STrateString);
					st_Rate.setText(StandardRateBeforeTax);
					
					TextView	last_transRate=(TextView) mainView.findViewById(R.id.last_transRate);
					last_transRate.setText(lastTransRTString);  
					
					
					
					TextView	st_ProductTax=(TextView) mainView.findViewById(R.id.st_ProductTax);
					st_ProductTax.setText(ProductTaxRate);
					
					
					TextView	UOM=(TextView) mainView.findViewById(R.id.UOM);
					UOM.setEnabled(true);
					
					if(hashmapForUomData.size()>1)
					{
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
					}
					else
					{
						for(Map.Entry<String, String> entry2:hashmapForUomData.entrySet())
						   {  String keyname=  entry2.getKey().toString().trim();
						   String valueid=  entry2.getValue().toString().trim();
						   
						   UOM.setText(keyname);
							   
						   
							
						   }	
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
							        alertDialog.setTitle("UOM");
							        listDistributor.setOnItemClickListener(new OnItemClickListener() {

										@Override
										public void onItemClick(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											String abc=listDistributor.getItemAtPosition(arg2).toString().trim();
											uomtext.setText(abc);
											ad.dismiss();
											
										}
									});
							        ad=alertDialog.show();
							
						}
						
					});
					 
					
					
			CheckBox 	includingTaxCheckbox=	 (CheckBox) mainView.findViewById(R.id.includingTaxCheckbox);
			//InclTaxString
			includingTaxCheckbox.setEnabled(false);
			if(InclTaxString.equals("1")){
				includingTaxCheckbox.setChecked(true);
			}
			else{
				includingTaxCheckbox.setChecked(false); 
			}
			
			/*includingTaxCheckbox.setTag(ProductID+"^"+"includingTaxCheckbox");
			includingTaxCheckbox.setOnClickListener(new OnClickListener() {});*/
			
					final TextView	fromDate=(TextView) mainView.findViewById(R.id.fromDate);
					fromDate.setText(FromDateString);
					fromDate.setEnabled(true);
					if(FromDateString.equals("0")){
						if(!SalesQuoteType.equals("0")){
							if(SalesQuoteType.trim().equals("1")){
								fromDate.setEnabled(false);
								fromDate.setText(SalesQuoteValidFrom);
							}
							if(SalesQuoteType.trim().equals("2")){
								fromDate.setText(SalesQuoteValidFrom);
							}
							
						}
						}
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
						       datePickerDialog = DatePickerDialog.newInstance(FragmentB.this, Year, Month, Day);

						       datePickerDialog.setThemeDark(false);

						       datePickerDialog.showYearPickerFirst(false);
						       Calendar calendarForSetDate = Calendar.getInstance();
						       calendarForSetDate.setTimeInMillis(System.currentTimeMillis());
						       
						      // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
						       //YOU can set min or max date using this code
						     // datePickerDialog.setMaxDate(Calendar.getInstance());
						      // datePickerDialog.setMinDate(calendar);
						       datePickerDialog.setMinDate(calendarForSetDate);

						       datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

						       datePickerDialog.setTitle("SELECT FROM DATE");
						       datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
							
						
							
						}});
					TextView	todate=(TextView) mainView.findViewById(R.id.todate);
					todate.setText(SalesQuoteValidTo);
					todate.setEnabled(true);
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
						       datePickerDialog = DatePickerDialog.newInstance(FragmentB.this, Year, Month, Day);

						       datePickerDialog.setThemeDark(false);
						       
						       Calendar calendarForSetDate = Calendar.getInstance();
						       calendarForSetDate.setTimeInMillis(System.currentTimeMillis());
						       
						      // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
						       //YOU can set min or max date using this code
						     // datePickerDialog.setMaxDate(Calendar.getInstance());
						      // datePickerDialog.setMinDate(calendar);
						       String dtFrom=  fromDate.getText().toString().trim();
					             if(dtFrom.contains("-")){
					              String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
					            int DayDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[0]);
					            int monthDTFRm=getArrayIndex(MONTHS, dtFrom.split(Pattern.quote("-"))[1]);
					            int yearDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[2]);
					            Calendar calDTFRM=Calendar.getInstance();
					            calDTFRM.set(yearDTFRm, monthDTFRm, DayDTFRm);
					            if(calDTFRM.getTimeInMillis()>System.currentTimeMillis()){
					             calendarForSetDate.set(yearDTFRm, monthDTFRm, DayDTFRm);
					            }
					            
					             }
						       datePickerDialog.setMinDate(calendarForSetDate);

						       datePickerDialog.showYearPickerFirst(false);

						       datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

						       datePickerDialog.setTitle("SELECT DATE UPTO");
						       datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
							
						
							
						}});
					
					TextView	remarktext=(TextView) mainView.findViewById(R.id.remarktext);
					
					
					
					remarktext.setEnabled(true);
					if(!RemarkString.equals("0")){
						remarktext.setText(RemarkString);
					}
					else{
						remarktext.setText(""); 
					}
					remarktext.setTag(ProductID+"^"+"remarktext");
					
					ImageView	delete_icon_quotation=(ImageView) mainView.findViewById(R.id.delete_icon_quotation);
					delete_icon_quotation.setVisibility(View.VISIBLE);
					delete_icon_quotation.setTag(ProductID);
					delete_icon_quotation.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
						 prdID=	v.getTag().toString();
						AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
						 alertDialog.setTitle("Information");
					       
					        alertDialog.setCancelable(false);
					     alertDialog.setMessage("Are you sure to remove this ");
					     alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog,int which) {
					            	dialog.dismiss();
					            	
					            	dbengine.open();
									dbengine.deletetblSalesQuoteProductsMstr(prdID,QuotationActivity.SalesQuoteId);
									dbengine.close();
									
									
									LinearLayout ll=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(prdID);//(R.id.posdata1);
									ll.setVisibility(View.GONE);
									
									
									
									
									
									int intforColorindex=1;
									int flgChecktoShowHideRateDistribution=0;
									if(hashmapForDynamicViewFromDataBase.size()>0)
									{
										
										for(Map.Entry<String, String> entry:hashmapForDynamicViewFromDataBase.entrySet())
										{
											LinearLayout llnew=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(entry.getKey().toString().trim());
											if(llnew.getVisibility()==View.VISIBLE)
											{
												flgChecktoShowHideRateDistribution=1;
												if((intforColorindex%2)==0){
													llnew.setBackgroundColor(getResources().getColor(R.color.tablecolor));
													
												}
												else
												{
													llnew.setBackgroundColor(Color.parseColor("#ffffff"));
												}
												intforColorindex++;
											}
										}
									}
									
									
									if(flgChecktoShowHideRateDistribution==0)
									{
										CB_rateDistibution.setVisibility(View.GONE);
										CB_rateDistibution.setChecked(false);
										TextOfCheckbox.setVisibility(View.GONE);
										textRateDistribution.setVisibility(View.GONE);
										hashmapForDatabaseRateDistributer.clear();
										hashmapSavingRateDistribution.clear();
										textRateDistribution.setText("");
										multipleCheckBoxflag=false;
										multipleCheckBoxflagcopy=false;
										dbengine.open();
										dbengine.deletetblRateDistribution(QuotationActivity.SalesQuoteId, QuotationActivity.storeID);
										dbengine.close();
									}
									
					           
					            }
					        });
					     alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
					            public void onClick(DialogInterface dialog,int which) {
					            	dialog.dismiss();
					            	//saveModifiedDataToDatabase();
					            }
					        });
					 
					        // Showing Alert Message
					        alertDialog.show();
						
						
					
						
						
						
						

						/*if(allDataParent.getChildCount()>0){
							allDataParent.removeAllViews();
							dynamcDtaContnrScrollview.removeAllViews();
							GetDatawhenNEW task2 = new GetDatawhenNEW();
							task2.execute();						}
						else{
							if(allDataParent.getChildCount()>0){
								allDataParent.removeAllViews();
							}
							if(dynamcDtaContnrScrollview.getChildCount()>0){
								dynamcDtaContnrScrollview.removeAllViews();
							}
							
							GetDatawhenNEW task2 = new GetDatawhenNEW();
							task2.execute();
							}*/
					
					
						}
					});
					
					
					
					EditText	RateOffer=(EditText) mainView.findViewById(R.id.RateOffer);
					RateOffer.setEnabled(true);
					//String valueofEdittext=hmapquestion_tableValues.get(keyHashmap).toString().trim();
					if(!RtOfferString.equals("0")){
						RateOffer.setText(RtOfferString);
					}
					/*RateOffer.setTag(ProductID+"^"+"RateOffer");
					RateOffer.setOnClickListener(new OnClickListener() {});*/
                      EditText	minDlv_qty=(EditText) mainView.findViewById(R.id.minDlv_qty);
                      minDlv_qty.setEnabled(true);
					//String valueofEdittext=hmapquestion_tableValues.get(keyHashmap).toString().trim();
					if(!minDlQtyString.equals("0")){
						minDlv_qty.setText(minDlQtyString);
					}
					/*minDlv_qty.setTag(ProductID+"^"+"minDlv_qty");
					remarktext.setOnFocusChangeListener(FragmentA.this);
					RateOffer.setOnFocusChangeListener(FragmentA.this);
					minDlv_qty.setOnFocusChangeListener(FragmentA.this);*/
					
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
					if(SalesQuotePrcsId.equals("4") || SalesQuotePrcsId.equals("5") ){
						
						RateOffer.setEnabled(false);
						minDlv_qty.setEnabled(false);
						delete_icon_quotation.setEnabled(false);
						remarktext.setEnabled(false);
						todate.setEnabled(false);
						fromDate.setEnabled(false);
						includingTaxCheckbox.setEnabled(false);
						UOM.setEnabled(false);
					}
					allDataParent.addView(mainView);
					index++;
			
			
		}
		 dynamcDtaContnrScrollview.addView(allDataParent);
		 if(SalesQuotePrcsId.equals("4") || SalesQuotePrcsId.equals("5") ){
			 btnPrevious.setVisibility(View.GONE);   
			 Done_btn.setVisibility(View.GONE);  
			 btn_Save_TakeOrder.setVisibility(View.GONE);
			 CB_rateDistibution.setEnabled(false);
			 TextOfCheckbox.setEnabled(false);
			 textRateDistribution.setEnabled(false);
		 }
		 getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		
		
		
	}
	public void FromDate_ToDate_and_FlagValuesFromTable() {
		String dateAndFlag=	dbengine.fngettblSalesQuotePersonMeetMstr(QuotationActivity.SalesQuoteId);
		SalesQuotePrcsId="0";
		SalesQuotePrcs="0";
		SalesQuoteValidFrom="0";
		SalesQuoteValidTo="0";
		SalesQuoteType="0";
		if(!dateAndFlag.equals("0")){
			
			 SalesQuotePrcsId=dateAndFlag.split(Pattern.quote("^"))[0];
		 SalesQuotePrcs=dateAndFlag.split(Pattern.quote("^"))[1];
			 SalesQuoteValidFrom=dateAndFlag.split(Pattern.quote("^"))[2];
			 SalesQuoteValidTo=dateAndFlag.split(Pattern.quote("^"))[3];
			 SalesQuoteType=dateAndFlag.split(Pattern.quote("^"))[4];
			}

		}

	public void saveModifiedDataToDatabase()
	{
		int chkval=0;
		if(hashmapForDynamicViewFromDataBase!=null)
		{
			if(!hashmapForDynamicViewFromDataBase.isEmpty()){
				String SalesQuotePrcsForUpdate="0";
				String SalesQuotePrcsIdForUpdate="0";
				if(CommonInfo.quatationFlag.equals("UPDATE")){
					if(CommonInfo.prcID.equals("2")){
						SalesQuotePrcsForUpdate=	hmapPrcDetailsFromDataBase.get("3");
						SalesQuotePrcsIdForUpdate="3";
						dbengine.open();
						 dbengine.UpdateQuotePrcsAgainstQuotationId(QuotationActivity.SalesQuoteId, SalesQuotePrcsIdForUpdate, SalesQuotePrcsForUpdate);
						 dbengine.close();
					}	 
				}
				
				dbengine.open();
			  for(Map.Entry<String, String> entry:hashmapForDynamicViewFromDataBase.entrySet())
			   {
				  String UOMID="0";
				String productID=  entry.getKey().toString().trim();
				 String sku=entry.getValue().toString().trim();
				
				LinearLayout ll=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(productID);//(R.id.posdata1);
				
				//LinearLayout llnew=	(LinearLayout) dynamcDtaContnrScrollview.findViewWithTag(entry.getKey().toString().trim());
				if(ll.getVisibility()==View.VISIBLE)
				{
				    String productname=sku.split(Pattern.quote("^"))[0];
					String STrateString=sku.split(Pattern.quote("^"))[1];
					String StandardRateBeforeTax=sku.split(Pattern.quote("^"))[2];
					
					EditText	RateOffer=(EditText) ll.findViewById(R.id.RateOffer);
					String RtOfferString="0";
					 if(RateOffer.getText().toString().trim().equals("")){
						 RtOfferString="0";
			   		}
			   		else{
			   			RtOfferString=  RateOffer.getText().toString().trim();		
			   		}
					 
					 EditText	minDlv_qty=(EditText) ll.findViewById(R.id.minDlv_qty);
					 String minDlQtyString="0";
					 if(minDlv_qty.getText().toString().trim().equals("")){
						 minDlQtyString="0";
			   		}
			   		else{
			   			minDlQtyString=  minDlv_qty.getText().toString().trim();		
			   		}
					 
					 
					 
					 TextView UOM=(TextView) ll.findViewById(R.id.UOM);;
						String UOMValue=UOM.getText().toString().trim();
						String UomString="0";
						if(hashmapForUomData.containsKey(UOMValue)){
							UomString=	hashmapForUomData.get(UOMValue);
						}
						else
						{
							UomString="0";
						}
					
						TextView	fromDate=(TextView) ll.findViewById(R.id.fromDate); 
						String FromDateString=fromDate.getText().toString().trim();
						TextView	todate=(TextView) ll.findViewById(R.id.todate); 
						String ToDateString=todate.getText().toString().trim();
						TextView tvRemarks=(TextView) ll.findViewById(R.id.remarktext);
						String RemarkString=tvRemarks.getText().toString().trim();
					//String RtOfferString=RateOffer.getText().toString().trim();
					 
					String InclTaxString="1";//sku.split(Pattern.quote("^"))[4];
					//String FromDateString=sku.split(Pattern.quote("^"))[5];
					//String ToDateString=sku.split(Pattern.quote("^"))[6];
					//String minDlQtyString=sku.split(Pattern.quote("^"))[7];
					//String UomString=sku.split(Pattern.quote("^"))[8];
					//String RemarkString=sku.split(Pattern.quote("^"))[9];
					String lastTransRTString=sku.split(Pattern.quote("^"))[10];
					String ProductTaxRate=sku.split(Pattern.quote("^"))[11];
					
					
					
					if(!RtOfferString.equals("0") || !minDlQtyString.equals("0"))
					{
						if(RtOfferString.equals("0"))
						{
							chkval=1;
							//allMessageAlert("Please select Rate Offer");
							stopSavingFlg=true;
							break;
						}
						//Minimum delivery quantity validation is commented now
						/*else if(minDlQtyString.equals("0"))
						{
							chkval=2;
							//allMessageAlert("Please select Minimum Order Quantity");
							stopSavingFlg=true;
							break;
						}*/
						else if(UomString.equals("0")){
							chkval=3;
							//allMessageAlert("Please select UOM");
							
							stopSavingFlg=true;
							break;
						}
					}
					 dbengine.deletetblSalesQuoteProductsMstr(productID,QuotationActivity.SalesQuoteId);
					if(stopSavingFlg==false)
					{
						//if(!RtOfferString.equals("0") && !minDlQtyString.equals("0")   ){
						if(!RtOfferString.equals("0")   ){
							
							dbengine.SalesQuoteProductsMstr(QuotationActivity.SalesQuoteId, "0", productID, STrateString, StandardRateBeforeTax, RtOfferString, InclTaxString, FromDateString, ToDateString, minDlQtyString, UomString, RemarkString, lastTransRTString,ProductTaxRate);
						
					   }
					}
				   
				   
				}
				
				
				
				
		
				}
			  dbengine.close();
			  
			  savingRateDistributionDataToDatabase();
			}
			
			
			
			if(stopSavingFlg==true)
			{
				//stopSavingFlg=false;
				if(chkval==3)
				{
				allMessageAlert("Please select UOM");
				}
				if(chkval==2)
				{
					allMessageAlert("Please select Minimum Dlv. Quantity");
				}
				if(chkval==1)
				{
					allMessageAlert("Please select Rate Offer");
				}
			}
		}
		
	}
	
	public void savingRateDistributionDataToDatabase() {
		dbengine.open();
		dbengine.deletetblRateDistribution(QuotationActivity.SalesQuoteId, QuotationActivity.storeID);
		if(CB_rateDistibution.isChecked()){
			if(hashmapForDatabaseRateDistributer!=null && !hashmapForDatabaseRateDistributer.isEmpty()){
				for(Map.Entry<String, String> entry:hashmapForDatabaseRateDistributer.entrySet())
                  {
					String SalesQuoteSponsorIDText = "";
					String ManufacturerIDText = "";
					String PercentageText = "";
					String SponsorDescrText = "";

					String ManufacturerNameText = "";
					String SponsorNameKEY = entry.getKey().toString().trim();
					if (hashmapForSponsorMstr.containsKey(SponsorNameKEY)) {

						String SpnsrName = hashmapForSponsorMstr
								.get(SponsorNameKEY);

						String SpnsrID = SpnsrName.split(Pattern.quote("^"))[0];
						SalesQuoteSponsorIDText = SpnsrID;
						SponsorDescrText = SponsorNameKEY;
					}
					if (SponsorNameKEY.equals("Manufacturer"))
					{
						if (hashmapForManufacturerMstr.containsKey(globalmarchentName))
						{

							String MnfactrName = hashmapForManufacturerMstr.get(globalmarchentName);

							String MnfctrID = MnfactrName.split(Pattern.quote("^"))[0];
							ManufacturerIDText = MnfctrID;
							ManufacturerNameText = globalmarchentName;
						}

					}

					String PercentageVALUE = entry.getValue().toString().trim();
					PercentageText = PercentageVALUE;
					if(!PercentageText.equals("") && !PercentageText.equals("0")){
					dbengine.saveTblRateDistribution(QuotationActivity.SalesQuoteId, QuotationActivity.storeID, SalesQuoteSponsorIDText, ManufacturerIDText, PercentageText, SponsorDescrText, ManufacturerNameText, "1");
					}
				}
			}
			
		}
		dbengine.close();
		

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		//saveModifiedDataToDatabase();
	}

	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
			int dayOfMonth) {
		 String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		 String mon=MONTHS[monthOfYear];
		 if(fromDateBool){
			 frmDate.setText(dayOfMonth+"-"+mon+"-"+year);
			 }
		 if(toDateBool){
			 todateText.setText(dayOfMonth+"-"+mon+"-"+year);}
		 toDateBool=false;
		 fromDateBool=false;
	}
	private void allMessageAlert(String message) {
		 AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
			alertDialogNoConn.setTitle("Information");
			alertDialogNoConn.setMessage(message);
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
			alertDialogNoConn.setIcon(R.drawable.error_ico);
			AlertDialog alert = alertDialogNoConn.create();
			alert.show();

	}
    
	private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {
		
		
		ProgressDialog pDialogGetStores;
		public FullSyncDataNow() 
		{
			pDialogGetStores = new ProgressDialog(getActivity());
			
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage("Submitting Quotation Details...");
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		   
		  
		}

		@Override
		
		protected Void doInBackground(Void... params) {

			 int Outstat=3;
				//TransactionTableDataDeleteAndSaving(Outstat);
				//InvoiceTableDataDeleteAndSaving(Outstat);
			 
			    long  syncTIMESTAMP = System.currentTimeMillis();
				Date dateobj = new Date(syncTIMESTAMP);
				SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
				String StampEndsTime = df.format(dateobj);
				
				dbengine.open();
				dbengine.UpdateStoreEndVisit(QuotationActivity.storeID, StampEndsTime);
				//dbengine.UpdateStoreProductAppliedSchemesBenifitsRecords(storeID.trim(),"3",strGlobalOrderID);
				//dbengine.UpdateStoreStoreReturnDetail(storeID.trim(),"3",strGlobalOrderID);
				dbengine.UpdateStoreFlagQoutation(QuotationActivity.storeID.trim(), 3);
				
				//dbengine.UpdateStoreOtherMainTablesFlag(storeID.trim(), 3,strGlobalOrderID);
				//dbengine.UpdateStoreReturnphotoFlag(storeID.trim(), 5);
				
				
				String presentRoute=dbengine.GetActiveRouteID();

				dbengine.close();
				dbengine.updateStoreQuoteSubmitFlgInStoreMstr(QuotationActivity.storeID.trim(), 1,StoreVisitCode);
			/*long syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);*/
			SimpleDateFormat df1 = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);
			
			 newfullFileName=CommonInfo.imei+"."+presentRoute+"."+ df1.format(dateobj);
			
			
			 
			
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
				dbengine.savetbl_XMLfiles(newfullFileName,"3","1");
				dbengine.open();
				dbengine.UpdateStoreFlagQoutation(QuotationActivity.storeID.trim(), 4);
				
			
				dbengine.close();
				
				

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
				
					StoreSelection.flgChangeRouteOrDayEnd=3;
					Intent syncIntent = new Intent(getActivity(), SyncMaster.class);
					//syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/TJUKIndirectSFAxml/" + newfullFileName + ".xml");
				    syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/" + CommonInfo.OrderXMLFolder + "/" + newfullFileName + ".xml");

				    syncIntent.putExtra("OrigZipFileName", newfullFileName);
					syncIntent.putExtra("whereTo", "Regular");
					startActivity(syncIntent);
					getActivity().finish();
				
				

			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) 
		{
			return true;
		}
		return false;
	}

	public void checkCheckboxOfRateDistribution() {
		int count = parentOfRateDistribution.getChildCount();

		for (int ui = 0; ui < count; ui++) {

			View viewLinear = parentOfRateDistribution.getChildAt(ui);
			if (viewLinear instanceof LinearLayout) {
				LinearLayout parentLinearlayout = (LinearLayout) viewLinear;
				int count2 = parentLinearlayout.getChildCount();
				for (int uii = 0; uii < count2; uii++) {
					View ch = parentLinearlayout.getChildAt(uii);
					if (ch instanceof CheckBox) {

						/*
						 * String chkedChkBoxTag=ch.getTag().toString(); int
						 * chkedchkQuestId
						 * =Integer.parseInt(chkedChkBoxTag.split(
						 * Pattern.quote("^"))[0]); String
						 * chkedchkOptionId=chkedChkBoxTag
						 * .split(Pattern.quote("^"))[1];
						 * if(valueOfChebox.equals(chkedchkOptionId)) {
						 */
						((CheckBox) ch).setChecked(false);
						 if(!multipleCheckBoxflag){
							 ((CheckBox) ch).setButtonDrawable(getResources().getDrawable(R.drawable.radio_button_background));   
						  }
	                      if(multipleCheckBoxflag){
	                    	  ((CheckBox) ch).setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_background)); 
						  }

						// }

					} else if (ch instanceof EditText) {
						((EditText) ch).setText("");
						((EditText) ch).setEnabled(false);
					} else if (ch instanceof TextView) {
						if (((TextView) ch).getTag().toString().trim()
								.equals("blanktext")) {

						} else {
							((TextView) ch).setVisibility(View.GONE);
						}

					}

				}

			}

		}

	}
	public void SavingDataToHashmap() {
		popupEdittextNotfill=false;
		totalOfEdittext=0;
		globalmarchentName="Select";
		int count = parentOfRateDistribution.getChildCount();
		hashmapSavingRateDistribution.clear();
		for (int ui = 0; ui < count; ui++) {

			View viewLinear = parentOfRateDistribution.getChildAt(ui);
			if (viewLinear instanceof LinearLayout) {
				LinearLayout parentLinearlayout = (LinearLayout) viewLinear;
				int count2 = parentLinearlayout.getChildCount();
				for (int uii = 0; uii < count2; uii++) {
					View ch = parentLinearlayout.getChildAt(uii);
					if (ch instanceof CheckBox)
					{
						if(((CheckBox) ch).isChecked()){
							
							hashmapSavingRateDistribution.put(((CheckBox) ch).getTag().toString().trim(), "0");
						}
						
					} 
					else if (ch instanceof EditText) 
					{
						if(hashmapSavingRateDistribution.containsKey(((EditText) ch).getTag().toString().trim()))
						{
							//if(((EditText) ch).getText().toString().trim().equals("0") || ((EditText) ch).getText().toString().trim().equals("") || ((EditText) ch).getText().toString().trim().equals("00") || ((EditText) ch).getText().toString().trim().equals("000") ){
							/*if( ((EditText) ch).getText().toString().trim().equals("")  ){
								popupEdittextNotfill=true;
								
							}
							else{*/
								hashmapSavingRateDistribution.put(((EditText) ch).getTag().toString().trim(), ((EditText) ch).getText().toString().trim());
								if(((EditText) ch).getText().toString().trim().equals("")){
									totalOfEdittext=totalOfEdittext+0;
								}
								else{
									totalOfEdittext=totalOfEdittext+Integer.parseInt(((EditText) ch).getText().toString().trim());
								}
									
								
							//}
							
						}

					}
					else if (ch instanceof TextView) 
					{
						if (((TextView) ch).getText().toString().trim()
								.equals("")) {

						}
						else{
							
							if(hashmapSavingRateDistribution.containsKey(((TextView) ch).getTag().toString().trim())){
								globalmarchentName=((TextView) ch).getText().toString().trim();
								
							}
						}
						
						
					}

				}

			}

		}

	
		
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
	public boolean Validate() {
		
		if(hashmapSavingRateDistribution.isEmpty()){
			//no check
			 allMessageAlert("Please select Sponsor! ");
			return false;
		}
		else if(popupEdittextNotfill){
			 allMessageAlert("Please fill percentage ");
			return false;
		}
		else if(totalOfEdittext==0 || totalOfEdittext>100 || totalOfEdittext<100){
			 allMessageAlert("Total percentage is not correct ");
			return false;
		}
		else if(hashmapSavingRateDistribution.containsKey("Manufacturer") && globalmarchentName.equals("Select")){
			allMessageAlert("Please select Manufacturer ");
			return false;
		}
		else{
			return true;
			
		}

	}

}
