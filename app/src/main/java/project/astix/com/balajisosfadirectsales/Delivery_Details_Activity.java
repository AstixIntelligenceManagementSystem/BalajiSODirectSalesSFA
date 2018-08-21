package project.astix.com.balajisosfadirectsales;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Delivery_Details_Activity extends Activity implements  DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
	TextView PaymentStageTextView,paymentModeTextviewNew,creditdaysTextboxNew,CreditlimitTextboxNew,percentageTextviewNew,paymentstagetextviewNew, CreditDaysTextbox, PaymentModeTextView, Date,SalesQuoteTypeSpinner,ValFrom,ValTo,SalesQuoteType,ValidityFrom,PaymentTerms,headerstring;
	RelativeLayout DeliveryDetailsHeader,delivey_details_parent,PaymentDetailsHeader, Payment_Details_Parent;
	CheckBox arrowOfDeleveryInfo, arrowOfPaymentDetails;
	ImageView img_back, CalenderValTo, DeliveryTimeImg_From,DeliveryTimeImg_To,DoNotDeliverTimeImg_From,DoNotDeliverTimeImg_To;
	TextView RequiredDeliveryDate, Delivery_Time_From,biltocustomernameSpinner,Billing_AddressText,Delivery_Time_To,DoNotDeliverTime_From,DoNotDeliverTime_To;
	LinearLayout ll_data,parentOfAdvanceBeforeDeliveryPayMentMode,parentOfOnDeliveryPayMentMode,parentOfCreditPayMentMode,parentOfCheckBox;
	public String storeID;
	public String StoreVisitCode="NA";
	public String OrderPDAID;
	public String imei;
	public String date;
	public String pickerDate;
	public String SN;
	boolean DeliveryTimeImg_From_boolean=false;
	boolean DeliveryTimeImg_To_boolean=false;
	boolean DoNotDeliverTimeImg_From_boolean=false;
	boolean DoNotDeliverTimeImg_To_boolean=false;
	Calendar calendar;
	DatePickerDialog datePickerDialog;
	int Year, Month, Day;
	Button Done_btn;
	 AlertDialog ad;

	public String strGlobalInvoiceNumber="NA";
	public int chkflgInvoiceAlreadyGenerated=0;


	 View convertView;
	TextView Delivery_Location_Spinner;
	 PRJDatabase helperDb;
	 ArrayAdapter<String> adapterDeliveryAddress,adapterBillingAddress;
	 ArrayList<LinkedHashMap<String, String>> arrgetStoreBillToAddressAndDiliverToAddress;
	 LinkedHashMap<String, String> hmapStoreBillTogAddresse;
	  LinkedHashMap<String, String> hmapStoreShippToAddress;
	  LinkedHashMap<String, String> hmapStoreAddress;
	  String[]DeliverAddresstList,BillingAdressList;
	 LinkedHashMap<String, String> hmapAllValuesOfPaymentMode;
	String globalValueOfPaymentStageCheck="0"+"_"+"0"+"_"+"0";
	 LinkedHashMap<String,String> hmapZoneDisplayMstr;
	CheckBox chBoxView,AdvanceBeforeDeliveryCheckBoxNew,OnDeliveryCheckBoxNew,CreditCheckBoxNew;
	ListView listDeliveryLocation,listBillingAdress;
	TextView Delivery_Location_Address;
	EditText percentageOfAdvanceBeforeDelivery,percentageOfOnDelivery,creditDaysOfOnDelivery,PercentageOfCredit,creditDaysOfCredit,creditLimitOfCredit;
	LinearLayout MainlayoutParentOfWholePage;
	  String fullStringOfDelivery="0";
	  public boolean onKeyDown(int keyCode, KeyEvent event)    // Control the PDA Native Button Handling
		{
			  // TODO Auto-generated method stub
			  if(keyCode==KeyEvent.KEYCODE_BACK)
			  {
				  return true;
				 // finish();
			  }
			  if(keyCode==KeyEvent.KEYCODE_HOME)
			  {
				 // finish();
				  
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delivery_details_activity);
		helperDb=new PRJDatabase(this);
		arrowHeaderSelection();
		StoreVisitCode=helperDb.fnGetStoreVisitCode(storeID);
		timeSelection();
		getDataFromIntent();
		initializeAllViewOfPaymentSection();
		savingData();
		initializeDropdown();
		back_buttonclick();
		fillAllDataOfDeliverySectionToView();
		arrowOfDeleveryInfo.setChecked(true);
		delivey_details_parent.setVisibility(View.VISIBLE);
		chkflgInvoiceAlreadyGenerated=helperDb.fnCheckForNewInvoiceOrPreviousValue(storeID,StoreVisitCode);//0=Need to Generate Invoice Number,1=No Need of Generating Invoice Number
		if(chkflgInvoiceAlreadyGenerated==1)
		{
			strGlobalInvoiceNumber=helperDb.fnGetExistingInvoiceNumber(storeID);
		}
		/*arrowOfPaymentDetails.setChecked(true);
		Payment_Details_Parent.setVisibility(View.VISIBLE);*/
		
		
		
		
		
		
	}
	public void fillAllDataOfDeliverySectionToView() {
		
		//when store added offline
		if(helperDb.checkIfCurrentStoreIsNewInOrderBill(storeID)==1){
		      String strBillAddress=helperDb.fetchBillAddress(storeID);
		      String strDeliveryAddress=helperDb.fetchDeliverAddress(storeID);
		      Delivery_Location_Address.setText(strDeliveryAddress);
		      Delivery_Location_Spinner.setEnabled(false);
		      Billing_AddressText.setText(strBillAddress);
		      biltocustomernameSpinner.setEnabled(false);
		      
		     }
		//end here
		if(helperDb.checkStoreOrderBillAddressDetails(storeID, OrderPDAID)==1)
		{
			String strPreFilledStoreOrderBillAddressDetails=helperDb.fnGetPreFilledStoreOrderBillAddressDetails(storeID, OrderPDAID);
			String currentBillToAddress=strPreFilledStoreOrderBillAddressDetails.split(Pattern.quote("$"))[0];
			if(currentBillToAddress.equals("0")){
				biltocustomernameSpinner.setText("Select");
				
			}
			else{
			
				 if(hmapStoreBillTogAddresse.size()>0){
					 for(Map.Entry<String, String> entry:hmapStoreBillTogAddresse.entrySet())
						{
						 
						 String keyHashmap=entry.getKey().toString().trim();
							String ValueHashMap=entry.getValue().toString().trim();
							if(currentBillToAddress.equals(ValueHashMap)){
								biltocustomernameSpinner.setText(keyHashmap);
								Billing_AddressText.setText(hmapStoreAddress.get(hmapStoreBillTogAddresse.get(keyHashmap)));
								break;
							}
							}
					 }
				 
				
			}
			
			String currentShipToAddress=strPreFilledStoreOrderBillAddressDetails.split(Pattern.quote("$"))[1];
			String string_Delivery_Location_Spinner=currentShipToAddress.split(Pattern.quote("^"))[0];
			
			if(string_Delivery_Location_Spinner.equals("0")){
				Delivery_Location_Spinner.setText("Select");
				
			}
			else{
			
				 if(hmapStoreShippToAddress.size()>0){
					 for(Map.Entry<String, String> entry:hmapStoreShippToAddress.entrySet())
						{
						 
						 String keyHashmap=entry.getKey().toString().trim();
							String ValueHashMap=entry.getValue().toString().trim();
							if(string_Delivery_Location_Spinner.equals(ValueHashMap)){
								Delivery_Location_Spinner.setText(keyHashmap);
								Delivery_Location_Address.setText(hmapStoreAddress.get(hmapStoreShippToAddress.get(keyHashmap)));	
								break;
							}
							}
					 }
				 
				
			}
			
			String string_Delivery_Time_From=currentShipToAddress.split(Pattern.quote("^"))[1];
			if(!string_Delivery_Time_From.equals("0")){
				Delivery_Time_From.setText(string_Delivery_Time_From);
			}
			
			String string_Delivery_Time_To=currentShipToAddress.split(Pattern.quote("^"))[2];
                   if(!string_Delivery_Time_To.equals("0")){
                	   Delivery_Time_To.setText(string_Delivery_Time_To);
				
			}
			String string_DoNotDeliverTime_From=currentShipToAddress.split(Pattern.quote("^"))[3];
            if(!string_DoNotDeliverTime_From.equals("0")){
            	DoNotDeliverTime_From.setText(string_DoNotDeliverTime_From);
				
			}
			String string_DoNotDeliverTime_To=currentShipToAddress.split(Pattern.quote("^"))[4];
               if(!string_DoNotDeliverTime_To.equals("0")){
            	   DoNotDeliverTime_To.setText(string_DoNotDeliverTime_To);
				
			}
			String string_RequiredDeliveryDate=currentShipToAddress.split(Pattern.quote("^"))[5];
                 if(!string_RequiredDeliveryDate.equals("0")){
                	 RequiredDeliveryDate.setText(string_RequiredDeliveryDate);
				
			}
			
			
			
			
		}

	}
	public void back_buttonclick() {
		img_back=(ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(true)
			//	if(validateDelivery_Location_Spinner())
				{
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Delivery_Details_Activity.this);
					alertDialog.setTitle("Information");

					alertDialog.setCancelable(false);
					alertDialog.setMessage("Have you saved data, before going back ?");
					alertDialog.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									Intent fireBackDetPg=new Intent(Delivery_Details_Activity.this,ProductOrderReview.class);
						               fireBackDetPg.putExtra("storeID", storeID);
						               fireBackDetPg.putExtra("SN", SN);
						              fireBackDetPg.putExtra("imei", imei);
						               fireBackDetPg.putExtra("userdate", date);
						               fireBackDetPg.putExtra("pickerDate", pickerDate);
						               
						             
						               startActivity(fireBackDetPg);
						               finish();
									

									

									

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
			
			}
		});
	}
	public void initializeDropdown() {
		Delivery_Location_Address=(TextView) findViewById(R.id.Delivery_Location_Address);
		Billing_AddressText=(TextView) findViewById(R.id.Billing_AddressText);
		arrgetStoreBillToAddressAndDiliverToAddress=	helperDb.fngetStoreBillToAddressAndDiliverToAddress(storeID);
		  hmapStoreBillTogAddresse=arrgetStoreBillToAddressAndDiliverToAddress.get(0);
		   hmapStoreShippToAddress=arrgetStoreBillToAddressAndDiliverToAddress.get(1);
		   hmapStoreAddress=arrgetStoreBillToAddressAndDiliverToAddress.get(2);
		   biltocustomernameSpinner=(TextView) findViewById(R.id.biltocustomernameSpinner);
		   Delivery_Location_Spinner=(TextView) findViewById(R.id.Delivery_Location_Spinner);
		   if(hmapStoreShippToAddress.size()==1){
			   for(Map.Entry<String, String> entry:hmapStoreShippToAddress.entrySet())
				{
				 
				 String keyHashmap=entry.getKey().toString().trim();
					String ValueHashMap=entry.getValue().toString().trim();
					
					Delivery_Location_Spinner.setText(keyHashmap);
					Delivery_Location_Address.setText(hmapStoreAddress.get(hmapStoreShippToAddress.get(keyHashmap)));
						break;
					
					}
		   }
		   
		   if(hmapStoreBillTogAddresse.size()==1){
			   for(Map.Entry<String, String> entry:hmapStoreBillTogAddresse.entrySet())
				{
				 
				 String keyHashmap=entry.getKey().toString().trim();
					String ValueHashMap=entry.getValue().toString().trim();
					
					biltocustomernameSpinner.setText(keyHashmap);
					Billing_AddressText.setText(hmapStoreAddress.get(hmapStoreBillTogAddresse.get(keyHashmap)));
						break;
					
					}
		   }
		   Delivery_Location_Spinner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!hmapStoreShippToAddress.equals(null) && !hmapStoreShippToAddress.isEmpty()){
					AlertDialog.Builder	 alertDialog = new AlertDialog.Builder(Delivery_Details_Activity.this);
					 LayoutInflater inflater = getLayoutInflater();
					 convertView = (View) inflater.inflate(R.layout.activity_list, null);
					 EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
						inputSearch.setVisibility(View.GONE);
					  listDeliveryLocation = (ListView)convertView. findViewById(R.id.list_view);
					  
					  int index=0;
						if(hmapStoreShippToAddress!=null)
				    	{
							if(hmapStoreShippToAddress.size()>0)
							{
								DeliverAddresstList=new String[hmapStoreShippToAddress.size()+ 1];
							}
							else
							{
								DeliverAddresstList=new String[1];
							}
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapStoreShippToAddress);
							 Set set2 = map.entrySet();
					            Iterator iterator = set2.iterator();
					            while(iterator.hasNext()) {
					            	 Map.Entry me2 = (Map.Entry)iterator.next();
					            	 if(index==0)
					            	 {
					            		 DeliverAddresstList[index]="Select";
					            		
					            		 index=index+1;
					            		
					            		 
					            	 }
					            	 DeliverAddresstList[index]=me2.getKey().toString().trim();
					                	
				                	 index=index+1;
					                 
					            }
							
							
				    	}
						 adapterDeliveryAddress = new ArrayAdapter<String>(Delivery_Details_Activity.this, R.layout.list_item, R.id.product_name, DeliverAddresstList);
						 listDeliveryLocation.setAdapter(adapterDeliveryAddress);
						  alertDialog.setView(convertView);
					        alertDialog.setTitle("Delivery Location");
					        listDeliveryLocation.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
									String abc=listDeliveryLocation.getItemAtPosition(arg2).toString().trim();
									Delivery_Location_Spinner.setText(abc);
									ad.dismiss();
									if(abc.equals("Select")){
										Delivery_Location_Spinner.setText("Select");
										Delivery_Location_Address.setText("");
										}
									else{
										
										
										Delivery_Location_Address.setText(hmapStoreAddress.get(hmapStoreShippToAddress.get(abc)));
										
										}
																		
								}
							});
					        ad=alertDialog.show();
				}
				
			}
		}) ;
		   
		   biltocustomernameSpinner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!hmapStoreBillTogAddresse.equals(null) && !hmapStoreBillTogAddresse.isEmpty()){
					AlertDialog.Builder	 alertDialog = new AlertDialog.Builder(Delivery_Details_Activity.this);
					 LayoutInflater inflater = getLayoutInflater();
					 convertView = (View) inflater.inflate(R.layout.activity_list, null);
					 EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
						inputSearch.setVisibility(View.GONE);
					  listBillingAdress = (ListView)convertView. findViewById(R.id.list_view);
					  
					  int index=0;
						if(hmapStoreBillTogAddresse!=null)
				    	{
							if(hmapStoreBillTogAddresse.size()>0)
							{
								BillingAdressList=new String[hmapStoreBillTogAddresse.size()+ 1];
							}
							else
							{
								BillingAdressList=new String[1];
							}
							LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapStoreBillTogAddresse);
							 Set set2 = map.entrySet();
					            Iterator iterator = set2.iterator();
					            while(iterator.hasNext()) {
					            	 Map.Entry me2 = (Map.Entry)iterator.next();
					            	 if(index==0)
					            	 {
					            		 BillingAdressList[index]="Select";
					            		
					            		 index=index+1;
					            		
					            		 
					            	 }
					            	 BillingAdressList[index]=me2.getKey().toString().trim();
					                	
				                	 index=index+1;
					                 
					            }
							
							
				    	}
						 adapterBillingAddress = new ArrayAdapter<String>(Delivery_Details_Activity.this, R.layout.list_item, R.id.product_name, BillingAdressList);
						 listBillingAdress.setAdapter(adapterBillingAddress);
						  alertDialog.setView(convertView);
					        alertDialog.setTitle("Bill to customer name");
					        listBillingAdress.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1,
										int arg2, long arg3) {
									String abc=listBillingAdress.getItemAtPosition(arg2).toString().trim();
									biltocustomernameSpinner.setText(abc);
									ad.dismiss();
									if(abc.equals("Select")){
										biltocustomernameSpinner.setText("Select");
										Billing_AddressText.setText("");
										}
									else{
										
										
										Billing_AddressText.setText(hmapStoreAddress.get(hmapStoreBillTogAddresse.get(abc)));
										
										}
																		
								}
							});
					        ad=alertDialog.show();
				}
				
			}
		});
	}
	 public boolean validatecheckboxofAdvance() {

			boolean ckeckbox=false;
			int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
			
			 for (int ui=0;ui<count;ui++) 
			 {
				 View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
		            if (ch instanceof CheckBox)
		            {
		            	if(((CompoundButton) ch).isChecked()==true)
						{
		            		ckeckbox=true;
		            		
		            		break;
						}
		            	
		            }
			 }
			 
				 return ckeckbox;		

		}
	 public boolean validatecheckboxofDelivery() {

			boolean ckeckbox=false;
			int count = parentOfOnDeliveryPayMentMode.getChildCount();
			
			 for (int ui=0;ui<count;ui++) 
			 {
				 View ch = parentOfOnDeliveryPayMentMode.getChildAt(ui);
		            if (ch instanceof CheckBox)
		            {
		            	if(((CompoundButton) ch).isChecked()==true)
						{
		            		ckeckbox=true;
		            		
		            		break;
						}
		            	
		            }
			 }
			 
				 return ckeckbox;		

		}
		public boolean validatecheckboxofCredit() {

			boolean ckeckbox=false;
			int count = parentOfCreditPayMentMode.getChildCount();
			
			 for (int ui=0;ui<count;ui++) 
			 {
				 View ch = parentOfCreditPayMentMode.getChildAt(ui);
		            if (ch instanceof CheckBox)
		            {
		            	if(((CompoundButton) ch).isChecked()==true)
						{
		            		ckeckbox=true;
		            		
		            		break;
						}
		            	
		            }
			 }
			 
				 return ckeckbox;		

		} 
		public void savingDataOfDeliverySectionTodatabase() {
		String	string_Delivery_Location_Spinner="0";
			
		String	string_Delivery_Time_From="0";
		String	string_Delivery_Time_To="0";
		String	string_DoNotDeliverTime_From="0";
		String	string_DoNotDeliverTime_To="0";
		String	string_RequiredDeliveryDate="0";
			
		String	string_biltocustomernameSpinner="0";
		String fullstring="0";
		
		if(!Delivery_Location_Spinner.getText().toString().trim().equals("Select")){
			if(hmapStoreShippToAddress.containsKey(Delivery_Location_Spinner.getText().toString().trim())){
			
				string_Delivery_Location_Spinner=	hmapStoreShippToAddress.get(Delivery_Location_Spinner.getText().toString().trim());
		}
			}
		
		if(!Delivery_Time_From.getText().toString().trim().equals("")){
			string_Delivery_Time_From=Delivery_Time_From.getText().toString().trim();
		}
		if(!Delivery_Time_To.getText().toString().trim().equals("")){
			string_Delivery_Time_To=Delivery_Time_To.getText().toString().trim();
		}
		if(!DoNotDeliverTime_From.getText().toString().trim().equals("")){
			string_DoNotDeliverTime_From=DoNotDeliverTime_From.getText().toString().trim();
		}
		if(!DoNotDeliverTime_To.getText().toString().trim().equals("")){
			string_DoNotDeliverTime_To=DoNotDeliverTime_To.getText().toString().trim();
		}
		if(!RequiredDeliveryDate.getText().toString().trim().equals("")){
			string_RequiredDeliveryDate=RequiredDeliveryDate.getText().toString().trim();
		}
		
		if(!biltocustomernameSpinner.getText().toString().trim().equals("Select")){
			if(hmapStoreBillTogAddresse.containsKey(biltocustomernameSpinner.getText().toString().trim())){
			
				string_biltocustomernameSpinner=	hmapStoreBillTogAddresse.get(biltocustomernameSpinner.getText().toString().trim());
		}
			}
		
		fullstring=string_Delivery_Location_Spinner+"^"+string_Delivery_Time_From+"^"+string_Delivery_Time_To+"^"+string_DoNotDeliverTime_From+"^"+string_DoNotDeliverTime_To+"^"+string_RequiredDeliveryDate+"^"+string_biltocustomernameSpinner;
		helperDb.fndeleteStoreOrderBillAddressDetails(storeID, OrderPDAID);
		helperDb.open();
		helperDb.fnsaveStoreOrderBillAddressDetails(storeID, OrderPDAID, string_biltocustomernameSpinner, string_Delivery_Location_Spinner+"^"+string_Delivery_Time_From+"^"+string_Delivery_Time_To+"^"+string_DoNotDeliverTime_From+"^"+string_DoNotDeliverTime_To+"^"+string_RequiredDeliveryDate, "1");
		helperDb.close();
		//"1"+"^"+string_biltocustomernameSpinner=Bill To Customer

		}
		
		public void saveDataToDataBase() {
			
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
			
			String PaymntTermEditString="0";  //CreditLimit
			String SalesQuoteTypeString="0";
			String PaymntTermSpinnerString="0"; //CREDITdays
			
			String storeIDString="0";
			
	    	String PAYMENT_STAGEID_Values="0";
	    	String PaymentModeId="0";
	    	
	    	String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
			
			String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
			String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
			
			String fullStringOfAdvnc="0";
			String perOfAd="0";
			String checkBoxOfAdvance="0";
			
			if(paymntStgAdvnc.equals("1")){
				if(!percentageOfAdvanceBeforeDelivery.getText().toString().trim().equals("")){
					perOfAd=percentageOfAdvanceBeforeDelivery.getText().toString().trim();
					
				}
				int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
				
				 for (int ui=0;ui<count;ui++) 
				 {
					 View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
			            if (ch instanceof CheckBox)
			            {
			            	if(((CompoundButton) ch).isChecked()==true)
							{
			            		
			            		int chkedchkQuestId=Integer.parseInt(ch.getTag().toString().trim().split(Pattern.quote("^"))[0]);
			            		
			            		if(checkBoxOfAdvance.equals("0")){
			            			checkBoxOfAdvance=String.valueOf(chkedchkQuestId);
			            		}
			            		else{
			            			checkBoxOfAdvance=checkBoxOfAdvance+"|"+String.valueOf(chkedchkQuestId);
			            			
			            		}
			            		
							}
			            	
			            }
				 }
				
				
			}
			 fullStringOfAdvnc=paymntStgAdvnc+"~"+perOfAd+"~"+"0"+"~"+"0"+"~"+checkBoxOfAdvance;
			 
			/*String fullStringOfDelivery="0";*/
			 fullStringOfDelivery="0";
			String perOfDelivery="0";
			String checkBoxOfDelivery="0";
			String creditDaysOfDelvry="0";
			
			if(paymntStgDelvry.equals("2")){

				if(!percentageOfOnDelivery.getText().toString().trim().equals("")){
					perOfDelivery=percentageOfOnDelivery.getText().toString().trim();
					
				}
				int count = parentOfOnDeliveryPayMentMode.getChildCount();
				
				 for (int ui=0;ui<count;ui++) 
				 {
					 View ch = parentOfOnDeliveryPayMentMode.getChildAt(ui);
			            if (ch instanceof CheckBox)
			            {
			            	if(((CompoundButton) ch).isChecked()==true)
							{
			            		
			            		int chkedchkQuestId=Integer.parseInt(ch.getTag().toString().trim().split(Pattern.quote("^"))[0]);
			            		
			            		if(checkBoxOfDelivery.equals("0")){
			            			checkBoxOfDelivery=String.valueOf(chkedchkQuestId);
			            		}
			            		else{
			            			checkBoxOfDelivery=checkBoxOfDelivery+"|"+String.valueOf(chkedchkQuestId);
			            			
			            		}
			            		
							}
			            	
			            }
				 }
				 if(creditDaysOfOnDelivery.isEnabled() && !creditDaysOfOnDelivery.getText().toString().trim().equals("")){
					 
					 creditDaysOfDelvry=	 creditDaysOfOnDelivery.getText().toString().trim();
					 
				 }
				
				
			
				
			}
			 fullStringOfDelivery=paymntStgDelvry+"~"+perOfDelivery+"~"+creditDaysOfDelvry+"~"+"0"+"~"+checkBoxOfDelivery;
			 
			String fullStringOfCredit="0";
			String perOfCredit="0";
			String checkBoxOfCredit="0";
			String creditDaysOfCreditString="0";
			String creditLimitOfCreditString="0";
			
			if(paymntStgCrdt.equals("3")){
				


				if(!PercentageOfCredit.getText().toString().trim().equals("")){
					perOfCredit=PercentageOfCredit.getText().toString().trim();
					
				}
				int count = parentOfCreditPayMentMode.getChildCount();
				
				 for (int ui=0;ui<count;ui++) 
				 {
					 View ch = parentOfCreditPayMentMode.getChildAt(ui);
			            if (ch instanceof CheckBox)
			            {
			            	if(((CompoundButton) ch).isChecked()==true)
							{
			            		
			            		int chkedchkQuestId=Integer.parseInt(ch.getTag().toString().trim().split(Pattern.quote("^"))[0]);
			            		
			            		if(checkBoxOfCredit.equals("0")){
			            			checkBoxOfCredit=String.valueOf(chkedchkQuestId);
			            		}
			            		else{
			            			checkBoxOfCredit=checkBoxOfCredit+"|"+String.valueOf(chkedchkQuestId);
			            			
			            		}
			            		
							}
			            	
			            }
				 }
				 if(!creditDaysOfCredit.getText().toString().trim().equals("")){
					 
					 creditDaysOfDelvry=	 creditDaysOfCredit.getText().toString().trim();
					 
				 }
	 if(!creditLimitOfCredit.getText().toString().trim().equals("")){
					 
		 creditLimitOfCreditString=	 creditLimitOfCredit.getText().toString().trim();
					 
				 }
				 

				
			
				
			
				
			}
			 fullStringOfCredit=paymntStgCrdt+"~"+"100"+"~"+creditDaysOfDelvry+"~"+creditLimitOfCreditString+"~"+checkBoxOfCredit;
			
			 PAYMENT_STAGEID_Values=fullStringOfAdvnc+"$"+fullStringOfDelivery+"$"+fullStringOfCredit;
			 
			/// if you want multiple selection again comment these lises
			   if(paymntStgAdvnc.equals("1")){
			    
			    PAYMENT_STAGEID_Values=fullStringOfAdvnc;
			   } 
			   if(paymntStgDelvry.equals("2")){
			    
			    PAYMENT_STAGEID_Values=fullStringOfDelivery;
			   } 
			   if(paymntStgCrdt.equals("3")){
			    
			    PAYMENT_STAGEID_Values=fullStringOfCredit;
			   }
			   // end here
	    	
	    
		 storeIDString=	storeID;
		
		
		
		helperDb.open();
		helperDb.fndeleteStoreSalesOrderPaymentDetails(storeIDString,OrderPDAID,strGlobalInvoiceNumber);
		helperDb.fnsaveStoreSalesOrderPaymentDetails(storeIDString,OrderPDAID,PAYMENT_STAGEID_Values,"1",strGlobalInvoiceNumber);
	
		helperDb.close();
		
		 getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		 Intent fireBackDetPg=new Intent(Delivery_Details_Activity.this,ProductOrderReview.class);
         fireBackDetPg.putExtra("storeID", storeID);
         fireBackDetPg.putExtra("SN", SN);
        fireBackDetPg.putExtra("imei", imei);
         fireBackDetPg.putExtra("userdate", date);
         fireBackDetPg.putExtra("pickerDate", pickerDate);
         
       
         startActivity(fireBackDetPg);
         finish();
		
		}
		
	public void savingData() {
		Done_btn=(Button) findViewById(R.id.Done_btn);
		Done_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(validateDelivery_Location_Spinner()){

					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Delivery_Details_Activity.this);
					alertDialog.setTitle("Information");

					alertDialog.setCancelable(false);
					alertDialog.setMessage("Do you want to save data ");
					alertDialog.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();
									

								
									savingDataOfDeliverySectionTodatabase();
									saveDataToDataBase();

									

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
				
			}
		});
		

	}
	public boolean validatePaymentStageID()
	 {
		 boolean returnResult=true;
		 if(globalValueOfPaymentStageCheck.equals("0"+"_"+"0"+"_"+"0")){
				
				paymentstagetextviewNew.clearFocus();
				paymentstagetextviewNew.requestFocus();
				
				/*String estring = "Please Select Payment Stage";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   paymentstagetextviewNew.setError(ssbuilder);*/
				paymentstagetextviewNew.setError("Please Select Payment Stage");  
				returnResult= false;
				
			}
			/*else if(!validatePercentage()){
				percentageOfAdvanceBeforeDelivery.clearFocus();

				percentageOfOnDelivery.clearFocus();
				creditDaysOfOnDelivery.clearFocus();

				PercentageOfCredit.clearFocus();
				creditDaysOfCredit.clearFocus();
				creditLimitOfCredit.clearFocus();
				
				percentageTextviewNew.clearFocus();
				percentageTextviewNew.requestFocus();
				percentageTextviewNew.setError("Percentage is not 100%");  
				
				return false;
				
			}
					
			*/else if(!validatecheckboxofAdvance() && globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0].equals("1")){
				paymentModeTextviewNew.clearFocus();
				paymentModeTextviewNew.requestFocus();
				
				/*String estring = "Select Payment Mode";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   paymentModeTextviewNew.setError(ssbuilder);*/

				paymentModeTextviewNew.setError("Select Payment Mode");
				
				returnResult= false;
				
				
			}
			else if(!validatecheckboxofDelivery() && globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1].equals("2")){
				paymentModeTextviewNew.clearFocus();
				paymentModeTextviewNew.requestFocus();
				
				/*String estring = "Select Payment Mode";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   paymentModeTextviewNew.setError(ssbuilder);*/
				paymentModeTextviewNew.setError("Select Payment Mode");
				
				returnResult= false;
				
				
			}
			else if(!validatecheckboxofCredit() && globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2].equals("3")){
				paymentModeTextviewNew.clearFocus();
				paymentModeTextviewNew.requestFocus();
				/*String estring = "Select Payment Mode";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   paymentModeTextviewNew.setError(ssbuilder);*/
				paymentModeTextviewNew.setError("Select Payment Mode");
				
				returnResult= false;
				
				
			}
			else if(creditDaysOfOnDelivery.isEnabled() && creditDaysOfOnDelivery.getText().toString().trim().equals("")){
				
				creditdaysTextboxNew.clearFocus();
				creditdaysTextboxNew.requestFocus();
				/*String estring = "Credit days is empty";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   creditdaysTextboxNew.setError(ssbuilder);*/
				
				creditdaysTextboxNew.setError("Credit days is empty");
				returnResult= false;
			}
			else if(CreditCheckBoxNew.isChecked() && creditDaysOfCredit.getText().toString().trim().equals("")){
				
				creditdaysTextboxNew.clearFocus();
				creditdaysTextboxNew.requestFocus();
				/*String estring = "Credit days is empty";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   creditdaysTextboxNew.setError(ssbuilder);*/
				
				creditdaysTextboxNew.setError("Credit days is empty");
				returnResult= false;
			}
			else if(CreditCheckBoxNew.isChecked() && creditLimitOfCredit.getText().toString().trim().equals("")){
				
				CreditlimitTextboxNew.clearFocus();
				CreditlimitTextboxNew.requestFocus();
				/*String estring = "Credit limit is empty";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   CreditlimitTextboxNew.setError(ssbuilder);*/
				CreditlimitTextboxNew.setError("Credit limit is empty");
				returnResult= false;
			}
					
			else if(creditDaysOfOnDelivery.isEnabled() && creditDaysOfOnDelivery.getText().toString().trim().equals("0")){
				creditDaysOfOnDelivery.clearFocus();
				creditdaysTextboxNew.clearFocus();
				creditdaysTextboxNew.requestFocus();
				/*String estring = "Credit days can not be zero.";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   creditdaysTextboxNew.setError(ssbuilder);*/
				creditdaysTextboxNew.setError("Credit days can not be zero.");
				returnResult= false;
			}
			else if(CreditCheckBoxNew.isChecked() && creditDaysOfCredit.getText().toString().trim().equals("0")){
				creditDaysOfCredit.clearFocus();
				creditdaysTextboxNew.clearFocus();
				creditdaysTextboxNew.requestFocus();
				/*String estring = "Credit days can not be zero.";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   creditdaysTextboxNew.setError(ssbuilder);*/
				creditdaysTextboxNew.setError("Credit days can not be zero.");
				returnResult= false;
			}
			else if(CreditCheckBoxNew.isChecked() && creditLimitOfCredit.getText().toString().trim().equals("0")){
				creditLimitOfCredit.clearFocus();
				CreditlimitTextboxNew.clearFocus();
				CreditlimitTextboxNew.requestFocus();
				/*String estring = "Credit limit can not be zero.";
				   ForegroundColorSpan fgcspan = new ForegroundColorSpan(Color.BLACK);
				   SpannableStringBuilder ssbuilder = new SpannableStringBuilder(estring);
				   ssbuilder.setSpan(fgcspan, 0, estring.length(), 0);
				   CreditlimitTextboxNew.setError(ssbuilder);*/

				CreditlimitTextboxNew.setError("Credit limit can not be zero.");
				returnResult= false;
			}
		 return returnResult;
	 }
	
	public void initializeAllViewOfPaymentSection() {
		MainlayoutParentOfWholePage=(LinearLayout) findViewById(R.id.Parent_of_Whole);
		hmapAllValuesOfPaymentMode= helperDb.fnGettblSalesQuotePaymentModeMstrAllValues();
		
		 parentOfCreditPayMentMode= (LinearLayout) findViewById(R.id.parentOfCreditPayMentMode);
		 parentOfOnDeliveryPayMentMode= (LinearLayout) findViewById(R.id.parentOfOnDeliveryPayMentMode);
		 parentOfAdvanceBeforeDeliveryPayMentMode= (LinearLayout) findViewById(R.id.parentOfAdvanceBeforeDeliveryPayMentMode);
		 paymentModeTextviewNew=(TextView) findViewById(R.id.paymentModeTextviewNew);
		 creditdaysTextboxNew=(TextView) findViewById(R.id.creditdaysTextboxNew);
		 CreditlimitTextboxNew=(TextView) findViewById(R.id.CreditlimitTextboxNew);
		 //PaymentModeTextView=(TextView) findViewById(R.id.PaymentModeTextView);
		 
		 percentageTextviewNew=(TextView) findViewById(R.id.percentageTextviewNew);
		 PaymentStageTextView=(TextView) findViewById(R.id.PaymentStageTextView);
		 paymentstagetextviewNew=(TextView) findViewById(R.id.paymentstagetextviewNew);
		 
		 
		 AdvanceBeforeDeliveryCheckBoxNew =(CheckBox) findViewById(R.id.AdvanceBeforeDeliveryCheckBoxNew );
		 OnDeliveryCheckBoxNew=(CheckBox) findViewById(R.id.OnDeliveryCheckBoxNew);
		 CreditCheckBoxNew=(CheckBox) findViewById(R.id.CreditCheckBoxNew);
		 percentageOfAdvanceBeforeDelivery=(EditText) findViewById(R.id.percentageOfAdvanceBeforeDelivery);
		 percentageOfOnDelivery=(EditText) findViewById(R.id.percentageOfOnDelivery);
		 creditDaysOfOnDelivery=(EditText) findViewById(R.id.creditDaysOfOnDelivery);
		 PercentageOfCredit=(EditText) findViewById(R.id.PercentageOfCredit);
		 creditDaysOfCredit=(EditText) findViewById(R.id.creditDaysOfCredit);
		 creditLimitOfCredit=(EditText) findViewById(R.id.creditLimitOfCredit);
		 checkBoxCreationwhenPageLoading("1");
		 checkBoxCreationwhenPageLoading("2");
		 checkBoxCreationwhenPageLoading("3");
		 whenPaymentStageClickNew();
		 disableAndUncheckPaymntMdOfAdvanceBeforeDelivery();
		 disableAndUncheckPaymntMdOfOnDelivery();
		disableAndUncheckPaymntMdOfCredit();
		removeErrorMsgwhenclickOnedittext();
		
		 MainlayoutParentOfWholePage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					/*PaymentModeTextView.setError(null);
					PaymentModeTextView.clearFocus();*/
					
					paymentstagetextviewNew.setError(null);
					paymentstagetextviewNew.clearFocus();
					
					
					//percentageTextviewNew.setError(null);
					//percentageTextviewNew.clearFocus();
					
					paymentModeTextviewNew.setError(null);
					paymentModeTextviewNew.clearFocus();
					
					
					creditdaysTextboxNew.setError(null);
					creditdaysTextboxNew.clearFocus();
					CreditlimitTextboxNew.setError(null);
					CreditlimitTextboxNew.clearFocus();
					
					
					
						//PaymentModeTextView.setError(null);
					               // PaymentModeTextView.clearFocus();
				}
			});
		 
		 if(helperDb.checkCountIntblStoreSalesOrderPaymentDetails(storeID,OrderPDAID,strGlobalInvoiceNumber)==1){
			 String allValuesOfPaymentStageID="0";	
			 fillValuesInPaymentSection(allValuesOfPaymentStageID);
		 }
		
		

	}
	public void enablePaymntMdOfAdvanceBeforeDelivery() {
		int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
	           if (ch instanceof CheckBox)
	           {
	           	//((CheckBox) ch).setChecked(false);
	           	ch.setEnabled(true);
	           	
	           	
	           }
		 }
		

	}
	
	public void fillValuesInPaymentSection(String allValuesOfPaymentStageID) {
		//String all	helperDb.fngettblNewStoreSalesQuotePaymentDetails(AddNewStore_DynamicSectionWise.selStoreID)
			allValuesOfPaymentStageID=helperDb.fngettblStoreSalesOrderPaymentDetails(storeID,OrderPDAID,strGlobalInvoiceNumber);
			
			String pymntStagIDofAdvn="0";
			if(allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].equals("1")){
				percentageOfAdvanceBeforeDelivery.setText("100");
				enablePaymntMdOfAdvanceBeforeDelivery();
				
			pymntStagIDofAdvn=	allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].toString().trim();
			
		String percentageOfAdvn=		allValuesOfPaymentStageID.split(Pattern.quote("~"))[1].toString().trim();
			String paymentModeIdOfADVNC=	allValuesOfPaymentStageID.split(Pattern.quote("~"))[4].toString().trim();
			
			
			enablePaymntMdOfAdvanceBeforeDelivery();
			AdvanceBeforeDeliveryCheckBoxNew .setChecked(true);
			 if(!percentageOfAdvn.equals("0")){
				 percentageOfAdvanceBeforeDelivery.setText(percentageOfAdvn);
				 
				  }
			 percentageOfAdvanceBeforeDelivery.setEnabled(true);
			
			if(paymentModeIdOfADVNC.contains("|")){

				String[] option=	paymentModeIdOfADVNC.split(Pattern.quote("|"));
				for(int i=0;i<option.length;i++){
					String opt=option[i];
					if(hmapAllValuesOfPaymentMode.containsKey(opt)){
					String valueOfChebox=	hmapAllValuesOfPaymentMode.get(opt);
					int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
					
					 for (int ui=0;ui<count;ui++) 
					 {
						 
						 View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
				            if (ch instanceof CheckBox)
				            {
				            	
				            		String chkedChkBoxTag=ch.getTag().toString();
				        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
				       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
				       			if(valueOfChebox.equals(chkedchkOptionId)){
				       				((CheckBox) ch).setChecked(true);
				       				
				       			}
				            		
				            		
				            		
								
				            	
				            }
					 }
					}
					
				}
				
			}
			else{
				if(hmapAllValuesOfPaymentMode.containsKey(paymentModeIdOfADVNC)){
					String valueOfChebox=	hmapAllValuesOfPaymentMode.get(paymentModeIdOfADVNC);
					int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
					
					 for (int ui=0;ui<count;ui++) 
					 {
						 
						 View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
				            if (ch instanceof CheckBox)
				            {
				            	
				            		String chkedChkBoxTag=ch.getTag().toString();
				        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
				       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
				       			if(valueOfChebox.equals(chkedchkOptionId)){
				       				((CheckBox) ch).setChecked(true);
				       				
				       			}
				            		
				            		
				            		
								
				            	
				            }
					 }
					}
					
					
				
			}
			
				
				
				
			}
			String pymntStagIDofDelivery="0";
	       if(allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].equals("2")){
	    	   
	    	   PercentageOfCredit.setText("100");
	    	   
	     pymntStagIDofDelivery=	   allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].toString().trim();
	    		String percentageofDelivery=allValuesOfPaymentStageID.split(Pattern.quote("~"))[1].toString().trim();
	    String creditDysofDelivery=	   allValuesOfPaymentStageID.split(Pattern.quote("~"))[2].toString().trim();
	    String pymntmodeIDofDElivery=	   allValuesOfPaymentStageID.split(Pattern.quote("~"))[4].toString().trim();
	    enablePaymntMdOfOnDelivery();
	    if(!percentageofDelivery.equals("0")){
	    	percentageOfOnDelivery.setText(percentageofDelivery);
	    	
		  }
	    percentageOfOnDelivery.setEnabled(true);
		  if(!creditDysofDelivery.equals("0")){
			  creditDaysOfOnDelivery.setText(creditDysofDelivery);
			  creditDaysOfOnDelivery.setEnabled(true);
		  }
		  OnDeliveryCheckBoxNew .setChecked(true);
	    if(pymntmodeIDofDElivery.contains("|")){


			String[] option=	pymntmodeIDofDElivery.split(Pattern.quote("|"));
			for(int i=0;i<option.length;i++){
				String opt=option[i];
				if(hmapAllValuesOfPaymentMode.containsKey(opt)){
				String valueOfChebox=	hmapAllValuesOfPaymentMode.get(opt);
				int count = parentOfOnDeliveryPayMentMode.getChildCount();
				
				 for (int ui=0;ui<count;ui++) 
				 {
					 
					 View ch = parentOfOnDeliveryPayMentMode.getChildAt(ui);
			            if (ch instanceof CheckBox)
			            {
			            	
			            		String chkedChkBoxTag=ch.getTag().toString();
			        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
			       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
			       			if(valueOfChebox.equals(chkedchkOptionId)){
			       				((CheckBox) ch).setChecked(true);
			       				
			       			}
			            		
			            		
			            		
							
			            	
			            }
				 }
				}
				
			}
			
		
	    	
	    }
	    else{

			if(hmapAllValuesOfPaymentMode.containsKey(pymntmodeIDofDElivery)){
				String valueOfChebox=	hmapAllValuesOfPaymentMode.get(pymntmodeIDofDElivery);
				int count = parentOfOnDeliveryPayMentMode.getChildCount();
				
				 for (int ui=0;ui<count;ui++) 
				 {
					 
					 View ch = parentOfOnDeliveryPayMentMode.getChildAt(ui);
			            if (ch instanceof CheckBox)
			            {
			            	
			            		String chkedChkBoxTag=ch.getTag().toString();
			        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
			       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
			       			if(valueOfChebox.equals(chkedchkOptionId)){
			       				((CheckBox) ch).setChecked(true);
			       				
			       			}
			            		
			            		
			            		
							
			            	
			            }
				 }
				}
				
				
			
		
	    	
	    }
				
			}
	       String pymntStagIDofcredit="0";
	      if(allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].equals("3")){
	    	  
	    	  percentageOfOnDelivery.setText("100");
	    	  pymntStagIDofcredit=	  allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].toString().trim();
	    	  String percentageofcredit=	  allValuesOfPaymentStageID.split(Pattern.quote("~"))[1].toString().trim();
	    	  String creditDysofcredit = allValuesOfPaymentStageID.split(Pattern.quote("~"))[2].toString().trim();
	    	  String creditLimitofcredit=	  allValuesOfPaymentStageID.split(Pattern.quote("~"))[3].toString().trim();
	    	  String pymntmodeIDofcredit = allValuesOfPaymentStageID.split(Pattern.quote("~"))[4].toString().trim();
	    	  if(!percentageofcredit.equals("0")){
	    		  PercentageOfCredit.setText(percentageofcredit);
	    		  
	    	  }
	    	  PercentageOfCredit.setEnabled(true);
	    	  if(!creditDysofcredit.equals("0")){
	    		  creditDaysOfCredit.setText(creditDysofcredit);
	    		  creditDaysOfCredit.setEnabled(true);
	    	  }
	    	  
	    	  if(!creditLimitofcredit.equals("0")){
	    		  creditLimitOfCredit.setText(creditLimitofcredit);
	    		  creditLimitOfCredit.setEnabled(true);
	    		  
	    	  }
	    	  CreditCheckBoxNew.setChecked(true);
	    	 
	    	  enablePaymntMdOfCredit();
	    	  if(pymntmodeIDofcredit.contains("|")){


	    			String[] option=	pymntmodeIDofcredit.split(Pattern.quote("|"));
	    			for(int i=0;i<option.length;i++){
	    				String opt=option[i];
	    				if(hmapAllValuesOfPaymentMode.containsKey(opt)){
	    				String valueOfChebox=	hmapAllValuesOfPaymentMode.get(opt);
	    				int count = parentOfCreditPayMentMode.getChildCount();
	    				
	    				 for (int ui=0;ui<count;ui++) 
	    				 {
	    					 
	    					 View ch = parentOfCreditPayMentMode.getChildAt(ui);
	    			            if (ch instanceof CheckBox)
	    			            {
	    			            	
	    			            		String chkedChkBoxTag=ch.getTag().toString();
	    			        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
	    			       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
	    			       			if(valueOfChebox.equals(chkedchkOptionId)){
	    			       				((CheckBox) ch).setChecked(true);
	    			       				
	    			       			}
	    			            		
	    			            		
	    			            		
	    							
	    			            	
	    			            }
	    				 }
	    				}
	    				
	    			}
	    			
	    		
	    	    	
	    	    }
	    	    else{

	    			if(hmapAllValuesOfPaymentMode.containsKey(pymntmodeIDofcredit)){
	    				String valueOfChebox=	hmapAllValuesOfPaymentMode.get(pymntmodeIDofcredit);
	    				int count = parentOfCreditPayMentMode.getChildCount();
	    				
	    				 for (int ui=0;ui<count;ui++) 
	    				 {
	    					 
	    					 View ch = parentOfCreditPayMentMode.getChildAt(ui);
	    			            if (ch instanceof CheckBox)
	    			            {
	    			            	
	    			            		String chkedChkBoxTag=ch.getTag().toString();
	    			        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
	    			       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
	    			       			if(valueOfChebox.equals(chkedchkOptionId)){
	    			       				((CheckBox) ch).setChecked(true);
	    			       				
	    			       			}
	    			            		
	    			            		
	    			            		
	    							
	    			            	
	    			            }
	    				 }
	    				}
	    				
	    				
	    			
	    		
	    	    	
	    	    }
		
	        }
					
					
	       globalValueOfPaymentStageCheck=pymntStagIDofAdvn+"_"+pymntStagIDofDelivery+"_"+pymntStagIDofcredit;
	       
			 

		}
	public void enablePaymntMdOfOnDelivery() {
		int count = parentOfOnDeliveryPayMentMode.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfOnDeliveryPayMentMode.getChildAt(ui);
	           if (ch instanceof CheckBox)
	           {
	           	//((CheckBox) ch).setChecked(false);
	           	ch.setEnabled(true);
	           	
	           	
	           }
		 }
		

	}
	public void enablePaymntMdOfCredit() {
		int count = parentOfCreditPayMentMode.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfCreditPayMentMode.getChildAt(ui);
	           if (ch instanceof CheckBox)
	           {
	           	//((CheckBox) ch).setChecked(false);
	           	ch.setEnabled(true);
	           	
	           	
	           }
		 }
		

	}
		
	public void removeErrorMsgwhenclickOnedittext() {
		/*percentageOfAdvanceBeforeDelivery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				percentageTextviewNew.setError(null);
				percentageTextviewNew.clearFocus();
				
			}
		});*/
		/*percentageOfOnDelivery.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				percentageTextviewNew.setError(null);
				percentageTextviewNew.clearFocus();
				
			}
		});*/
		creditDaysOfOnDelivery.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			creditdaysTextboxNew.setError(null);
			creditdaysTextboxNew.clearFocus();
			
		}
	});
		/*PercentageOfCredit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			percentageTextviewNew.setError(null);
			percentageTextviewNew.clearFocus();
			
		}
	});*/
		creditDaysOfCredit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			creditdaysTextboxNew.setError(null);
			creditdaysTextboxNew.clearFocus();
			
		}
	});
		creditLimitOfCredit.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			CreditlimitTextboxNew.setError(null);
			CreditlimitTextboxNew.clearFocus();
			
		}
	});

	}
	
	public void disableAndUncheckPaymntMdOfCredit() {
		int count = parentOfCreditPayMentMode.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfCreditPayMentMode.getChildAt(ui);
	           if (ch instanceof CheckBox)
	           {
	           	((CheckBox) ch).setChecked(false);
	           	ch.setEnabled(false);
	           	
	           	
	           }
		 }
		

	}
	public void disableAndUncheckPaymntMdOfAdvanceBeforeDelivery() {
		int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
	           if (ch instanceof CheckBox)
	           {
	           	((CheckBox) ch).setChecked(false);
	           	ch.setEnabled(false);
	           	
	           	
	           }
		 }
		

	}
	public void disableAndUncheckPaymntMdOfOnDelivery() {
		int count = parentOfOnDeliveryPayMentMode.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfOnDeliveryPayMentMode.getChildAt(ui);
	           if (ch instanceof CheckBox)
	           {
	           	((CheckBox) ch).setChecked(false);
	           	ch.setEnabled(false);
	           	
	           	
	           }
		 }
		

	}
	public void whenPaymentStageClickNew() {
		AdvanceBeforeDeliveryCheckBoxNew .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 paymentstagetextviewNew.setError(null);
					paymentstagetextviewNew.clearFocus();
					AdvanceBeforeDeliveryCheckBoxNew.setChecked(true);
				if(AdvanceBeforeDeliveryCheckBoxNew.isChecked()){
					
					//setting percentage 100 because we hide the percent edittext but it also now in validation
					percentageOfAdvanceBeforeDelivery.setText("100");
					
					//uncheck ,disable and delete data of other checkboxes when click on this checkbox
					

					OnDeliveryCheckBoxNew.setChecked(false);
					percentageOfOnDelivery.setText("");
					creditDaysOfOnDelivery.setText("");
					percentageOfOnDelivery.setEnabled(false);
					creditDaysOfOnDelivery.setEnabled(false);
					percentageOfOnDelivery.clearFocus();
					creditDaysOfOnDelivery.clearFocus();
					disableAndUncheckPaymntMdOfOnDelivery();
					String paymntStgAdvnc2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
					String paymntStgDelvry2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
					paymntStgDelvry2="0";
					String paymntStgCrdt2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
					globalValueOfPaymentStageCheck=paymntStgAdvnc2+"_"+paymntStgDelvry2+"_"+paymntStgCrdt2;
					

					CreditCheckBoxNew.setChecked(false);
					PercentageOfCredit.setText("");
					creditDaysOfCredit.setText("");
					creditLimitOfCredit.setText("");
					PercentageOfCredit.setEnabled(false);
					creditDaysOfCredit.setEnabled(false);
					creditLimitOfCredit.setEnabled(false);
					PercentageOfCredit.clearFocus();
					creditDaysOfCredit.clearFocus();
					creditLimitOfCredit.clearFocus();
					disableAndUncheckPaymntMdOfCredit();
					String paymntStgAdvnc3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
					String paymntStgDelvry3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
					String paymntStgCrdt3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
					paymntStgCrdt3="0";
					globalValueOfPaymentStageCheck=paymntStgAdvnc3+"_"+paymntStgDelvry3+"_"+paymntStgCrdt3;
				
				
					
					
				
					
					
					//And now update the enable the checkbox ,which is clicked
					AdvanceBeforeDeliveryCheckBoxNew.setChecked(true);
					percentageOfAdvanceBeforeDelivery.setEnabled(true);
					enablePaymntMdOfAdvanceBeforeDelivery();
					
					String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
					paymntStgAdvnc="1";
					String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
					String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
					globalValueOfPaymentStageCheck=paymntStgAdvnc+"_"+paymntStgDelvry+"_"+paymntStgCrdt;
					
					
					
				}
				
				
			}
		});
		OnDeliveryCheckBoxNew .setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 paymentstagetextviewNew.setError(null);
					paymentstagetextviewNew.clearFocus();
					OnDeliveryCheckBoxNew.setChecked(true);
				if(OnDeliveryCheckBoxNew.isChecked()){
					//setting percentage 100 because we hide the percent edittext but it also now in validation
					percentageOfOnDelivery.setText("100");
					
					
		//@@@@@@@@@@@  uncheck ,disable and delete data of other checkboxes when click on this checkbox
					
					AdvanceBeforeDeliveryCheckBoxNew.setChecked(false);
					percentageOfAdvanceBeforeDelivery.setText("");
					percentageOfAdvanceBeforeDelivery.setEnabled(false);
					percentageOfAdvanceBeforeDelivery.clearFocus();
					disableAndUncheckPaymntMdOfAdvanceBeforeDelivery();
					
					String paymntStgAdvnc2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
					paymntStgAdvnc2="0";
					String paymntStgDelvry2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
					String paymntStgCrdt2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
					globalValueOfPaymentStageCheck=paymntStgAdvnc2+"_"+paymntStgDelvry2+"_"+paymntStgCrdt2;
					

					CreditCheckBoxNew.setChecked(false);
					PercentageOfCredit.setText("");
					creditDaysOfCredit.setText("");
					creditLimitOfCredit.setText("");
					PercentageOfCredit.setEnabled(false);
					creditDaysOfCredit.setEnabled(false);
					creditLimitOfCredit.setEnabled(false);
					PercentageOfCredit.clearFocus();
					creditDaysOfCredit.clearFocus();
					creditLimitOfCredit.clearFocus();
					disableAndUncheckPaymntMdOfCredit();
					String paymntStgAdvnc3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
					String paymntStgDelvry3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
					String paymntStgCrdt3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
					paymntStgCrdt3="0";
					globalValueOfPaymentStageCheck=paymntStgAdvnc3+"_"+paymntStgDelvry3+"_"+paymntStgCrdt3;
				
				
					
					
					
					
					//And now update the enable the checkbox ,which is clicked
					
					
					OnDeliveryCheckBoxNew.setChecked(true);
					percentageOfOnDelivery.setEnabled(true);
					//creditDaysOfOnDelivery.setEnabled(true);
					enablePaymntMdOfOnDelivery();
					String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
					String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
					paymntStgDelvry="2";
					String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
					globalValueOfPaymentStageCheck=paymntStgAdvnc+"_"+paymntStgDelvry+"_"+paymntStgCrdt;
					
				}
				
				
			}
		});
		CreditCheckBoxNew .setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			 paymentstagetextviewNew.setError(null);
				paymentstagetextviewNew.clearFocus();
				CreditCheckBoxNew.setChecked(true);
			if(CreditCheckBoxNew.isChecked()){
				
				//setting percentage 100 because we hide the percent edittext but it also now in validation
			PercentageOfCredit.setText("100");
				
				
				//uncheck ,disable and delete data of other checkboxes when click on this checkbox
				OnDeliveryCheckBoxNew.setChecked(false);
				percentageOfOnDelivery.setText("");
				creditDaysOfOnDelivery.setText("");
				percentageOfOnDelivery.setEnabled(false);
				creditDaysOfOnDelivery.setEnabled(false);
				percentageOfOnDelivery.clearFocus();
				creditDaysOfOnDelivery.clearFocus();
				disableAndUncheckPaymntMdOfOnDelivery();
				String paymntStgAdvnc2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
				String paymntStgDelvry2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
				paymntStgDelvry2="0";
				String paymntStgCrdt2=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
				globalValueOfPaymentStageCheck=paymntStgAdvnc2+"_"+paymntStgDelvry2+"_"+paymntStgCrdt2;
				
				

				
				
				 
				
				AdvanceBeforeDeliveryCheckBoxNew.setChecked(false);
				percentageOfAdvanceBeforeDelivery.setText("");
				percentageOfAdvanceBeforeDelivery.setEnabled(false);
				percentageOfAdvanceBeforeDelivery.clearFocus();
				disableAndUncheckPaymntMdOfAdvanceBeforeDelivery();
				
				String paymntStgAdvnc3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
				paymntStgAdvnc3="0";
				String paymntStgDelvry3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
				String paymntStgCrdt3=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
				globalValueOfPaymentStageCheck=paymntStgAdvnc3+"_"+paymntStgDelvry3+"_"+paymntStgCrdt3;
				
				
			
				//And now update the enable the checkbox ,which is clicked
				
				CreditCheckBoxNew.setChecked(true);
				PercentageOfCredit.setEnabled(true);
				creditDaysOfCredit.setEnabled(true);
				creditLimitOfCredit.setEnabled(true);
				enablePaymntMdOfCredit();
				String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
				String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
				String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
				paymntStgCrdt="3";
				globalValueOfPaymentStageCheck=paymntStgAdvnc+"_"+paymntStgDelvry+"_"+paymntStgCrdt;
				
				
			}
			
		}
	});

	}
	OnClickListener getOnClickDoSomething(final CheckBox button) {
    	return new OnClickListener() {
    		public void onClick(View v) {
    			String idOfAllCheckedBoxes="";
    			paymentModeTextviewNew.setError(null);
    			paymentModeTextviewNew.clearFocus();
    			
    			/*PaymentModeTextView.setError(null);
    			PaymentModeTextView.clearFocus();*/
    			//int count = parentOfCheckBox.getChildCount();
    			String curentSelectedZones="";
    			String chkedChkBoxTag=button.getTag().toString();
    			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
   			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
   			
   			if(OnDeliveryCheckBoxNew.isChecked()){
   				
   				if(chkedchkOptionId.trim().equals("Post Dated Cheque")){
   					if(button.isChecked()){
   						creditDaysOfOnDelivery.setEnabled(true);
   					}
   					else{
   						creditDaysOfOnDelivery.setEnabled(false);
   						creditDaysOfOnDelivery.clearFocus();
   						creditDaysOfOnDelivery.setText("");
   					}
   					
   				}
   				
   			}
   			
   		
   			
    			
    		} };

   
}
	public void checkBoxCreationwhenPageLoading(String paymentStageID) {
		hmapZoneDisplayMstr=helperDb.fnGettblSalesQuotePaymentModeMstr(paymentStageID);
		
		for(Map.Entry<String, String> entry:hmapZoneDisplayMstr.entrySet())
		{
        	 int checkBoxDescId=Integer.parseInt(entry.getKey().toString());
			 String checkBoxDesc=entry.getValue().toString().trim();
        LinearLayout.LayoutParams layoutParamss = new LinearLayout.LayoutParams(
			      LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			 layoutParamss.setMargins(3, 3, 0, 3);
			 chBoxView = new CheckBox(this);
			 chBoxView.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_button_image));
			 chBoxView.setText("   "+checkBoxDesc);
			 chBoxView.setTextColor(getResources().getColor(R.color.black));
			 chBoxView.setTextSize(10);
			 chBoxView.setId(checkBoxDescId);
			 chBoxView.setTag(checkBoxDescId+"^"+checkBoxDesc);
	    	 chBoxView.setOnClickListener(getOnClickDoSomething(chBoxView));
	    	 
			 if(paymentStageID.equals("1")){
				 
				 parentOfAdvanceBeforeDeliveryPayMentMode.addView(chBoxView,layoutParamss);
			 }
			 if(paymentStageID.equals("2")){
				 parentOfOnDeliveryPayMentMode.addView(chBoxView,layoutParamss);
			 }
			 if(paymentStageID.equals("3")){
				 parentOfCreditPayMentMode.addView(chBoxView,layoutParamss);
			 }
			 
	    	 //  parentOfCheckBox.addView(chBoxView,layoutParamss);
	    	  
    }}
	private void getDataFromIntent() {
		  
		  
		  Intent passedvals = getIntent();

		  storeID = passedvals.getStringExtra("storeID");
		  imei = passedvals.getStringExtra("imei");
		  date = passedvals.getStringExtra("userdate");
		  pickerDate = passedvals.getStringExtra("pickerDate");
		  SN = passedvals.getStringExtra("SN");
		  OrderPDAID= passedvals.getStringExtra("OrderPDAID");
		    
		
		 // hmapProductRelatedSchemesList=dbengine.fnProductRelatedSchemesList();
		 
		  
		 }
	public void timeSelection() {
		Delivery_Time_To=(TextView) findViewById(R.id.Delivery_Time_To);
		DeliveryTimeImg_To=(ImageView) findViewById(R.id.DeliveryTimeImg_To);
		DeliveryTimeImg_To.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DeliveryTimeImg_To_boolean=true;
				 Calendar now = Calendar.getInstance();
		            TimePickerDialog tpd = TimePickerDialog.newInstance(
		                    Delivery_Details_Activity.this,
		                    now.get(Calendar.HOUR_OF_DAY),
		                    now.get(Calendar.MINUTE),
		                    
		                    false
		            );
		            tpd.setThemeDark(false);
		            tpd.setAccentColor(Color.parseColor("#544f88"));
		            
		            tpd.setTitle("SELECT FROM Time");
		            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
		                @Override
		                public void onCancel(DialogInterface dialogInterface) {
		                    Log.d("TimePicker", "Dialog was cancelled");
		                }
		            });
		            tpd.show(getFragmentManager(), "Timepickerdialog");
				
			}
		});

		
		DoNotDeliverTime_From=(TextView) findViewById(R.id.DoNotDeliverTime_From);
		DoNotDeliverTimeImg_From=(ImageView) findViewById(R.id.DoNotDeliverTimeImg_From);
		DoNotDeliverTimeImg_From.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DoNotDeliverTimeImg_From_boolean=true;
				 Calendar now = Calendar.getInstance();
		            TimePickerDialog tpd = TimePickerDialog.newInstance(
		                    Delivery_Details_Activity.this,
		                    now.get(Calendar.HOUR_OF_DAY),
		                    now.get(Calendar.MINUTE),
		                    false
		            );
		            tpd.setThemeDark(false);
		            tpd.setAccentColor(Color.parseColor("#544f88"));

		            tpd.setTitle("SELECT FROM Time");
		            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
		                @Override
		                public void onCancel(DialogInterface dialogInterface) {
		                    Log.d("TimePicker", "Dialog was cancelled");
		                }
		            });
		            tpd.show(getFragmentManager(), "Timepickerdialog");
				
			}
		});
		
		DoNotDeliverTime_To=(TextView) findViewById(R.id.DoNotDeliverTime_To);
		DoNotDeliverTimeImg_To=(ImageView) findViewById(R.id.DoNotDeliverTimeImg_To);
		DoNotDeliverTimeImg_To.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				DoNotDeliverTimeImg_To_boolean=true;
				 Calendar now = Calendar.getInstance();
		            TimePickerDialog tpd = TimePickerDialog.newInstance(
		                    Delivery_Details_Activity.this,
		                    now.get(Calendar.HOUR_OF_DAY),
		                    now.get(Calendar.MINUTE),
		                    false
		            );
		            tpd.setThemeDark(false);
		            tpd.setAccentColor(Color.parseColor("#544f88"));

		            tpd.setTitle("SELECT FROM Time");
		            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
		                @Override
		                public void onCancel(DialogInterface dialogInterface) {
		                    Log.d("TimePicker", "Dialog was cancelled");
		                }
		            });
		            tpd.show(getFragmentManager(), "Timepickerdialog");
				
			}
		});
		
		Delivery_Time_From=(TextView) findViewById(R.id.Delivery_Time_From);
		DeliveryTimeImg_From=(ImageView) findViewById(R.id.DeliveryTimeImg_From);
		DeliveryTimeImg_From.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				DeliveryTimeImg_From_boolean=true;
				 Calendar now = Calendar.getInstance();
		            TimePickerDialog tpd = TimePickerDialog.newInstance(
		                    Delivery_Details_Activity.this,
		                    now.get(Calendar.HOUR_OF_DAY),
		                    now.get(Calendar.MINUTE),
		                    false
		            );
		            tpd.setThemeDark(false);
		            tpd.setAccentColor(Color.parseColor("#544f88"));

		            tpd.setTitle("SELECT FROM Time");
		            tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
		                @Override
		                public void onCancel(DialogInterface dialogInterface) {
		                    Log.d("TimePicker", "Dialog was cancelled");
		                }
		            });
		            tpd.show(getFragmentManager(), "Timepickerdialog");
				
			}
		});
		CalenderValTo=(ImageView) findViewById(R.id.CalenderValTo);
		RequiredDeliveryDate=(TextView) findViewById(R.id.RequiredDeliveryDate);
		CalenderValTo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				calendar = Calendar.getInstance();

				Year = calendar.get(Calendar.YEAR);
				Month = calendar.get(Calendar.MONTH);
				Day = calendar.get(Calendar.DAY_OF_MONTH);
				datePickerDialog = DatePickerDialog.newInstance(
						Delivery_Details_Activity.this, Year, Month, Day);

				datePickerDialog.setThemeDark(false);

				datePickerDialog.showYearPickerFirst(false);

				Calendar calendarForSetDate = Calendar.getInstance();
				calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

				// calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
				// YOU can set min or max date using this code
				// datePickerDialog.setMaxDate(Calendar.getInstance());
				// datePickerDialog.setMinDate(calendar);

				datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

				datePickerDialog.setTitle("Delivery Date");
			//	datePickerDialog.setMaxDate(calendarForSetDate);
				/*
				 * Calendar calendar = Calendar.getInstance();
				 * calendar.setTimeInMillis
				 * (System.currentTimeMillis()+24*60*60*1000);
				 */
				// YOU can set min or max date using this code
				// datePickerDialog.setMaxDate(Calendar.getInstance());
				// datePickerDialog.setMinDate(calendar);
				datePickerDialog.setMinDate(Calendar.getInstance());
				datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
				
			}
		});

	}
	 public void showAlertForEveryOne(String msg) 
	 {
	 		AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(Delivery_Details_Activity.this);
	 		alertDialogNoConn.setTitle("Information");
	 		alertDialogNoConn.setMessage(msg);
	 		alertDialogNoConn.setCancelable(false);
	 		alertDialogNoConn.setNeutralButton(R.string.AlertDialogOkButton,new DialogInterface.OnClickListener()
	 		       {
	 					public void onClick(DialogInterface dialog, int which) 
	 					 {
	                       dialog.dismiss();
	                      // finish();
	                     }
	 				});
	 		alertDialogNoConn.setIcon(R.drawable.info_ico);
	 		AlertDialog alert = alertDialogNoConn.create();
	 		alert.show();
	 }
	public void arrowHeaderSelection() {
		delivey_details_parent = (RelativeLayout) findViewById(R.id.delivey_details_parent);

		arrowOfDeleveryInfo = (CheckBox) findViewById(R.id.arrowOfDeleveryInfo);

		arrowOfDeleveryInfo.setButtonDrawable(getResources().getDrawable(
				R.drawable.checkbox_button_image_for_delivery));
		arrowOfDeleveryInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				/*if(helperDb.checkIfCurrentStoreIsNewInOrderBill(storeID)==1){
					arrowOfDeleveryInfo.setChecked(false);
					delivey_details_parent.setVisibility(View.GONE);
					showAlertForEveryOne("The Delivery Address and Time Can not be set as this store is not submitted to the Server");
					
					
				}*/
			//	else{
					if (arrowOfDeleveryInfo.isChecked()) {
						arrowOfDeleveryInfo.setChecked(true);
						delivey_details_parent.setVisibility(View.VISIBLE);
					} else {
						arrowOfDeleveryInfo.setChecked(false);
						delivey_details_parent.setVisibility(View.GONE);
					}
					
				//}

				

			}
		});
		arrowOfPaymentDetails = (CheckBox) findViewById(R.id.arrowOfPaymentDetails);
		Payment_Details_Parent = (RelativeLayout) findViewById(R.id.Payment_Details_Parent);
		arrowOfPaymentDetails.setButtonDrawable(getResources().getDrawable(
				R.drawable.checkbox_button_image_for_delivery));
		arrowOfPaymentDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (arrowOfPaymentDetails.isChecked()) {
					arrowOfPaymentDetails.setChecked(true);
					Payment_Details_Parent.setVisibility(View.VISIBLE);
				} else {
					arrowOfPaymentDetails.setChecked(false);
					Payment_Details_Parent.setVisibility(View.GONE);
				}

			}
		});
		

	}
	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
				"Aug", "Sep", "Oct", "Nov", "Dec" };
		String mon = MONTHS[monthOfYear];
		RequiredDeliveryDate.setText(dayOfMonth + "-" + mon + "-" + year);
		
	}
	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute,
			int second) {
		 String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
	        String minuteString = minute < 10 ? "0"+minute : ""+minute;
	        String secondString = second < 10 ? "0"+second : ""+second;
	        String time = " "+hourString+":"+minuteString;
	        String _24HourTime = "22:15";
	        String _12HourFormatetime="";
	        _24HourTime=time;
	           SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	           SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
	           try {
				java.util.Date _24HourDt = _24HourSDF.parse(_24HourTime);
				   System.out.println("SOMU24"+_24HourDt);
				   System.out.println("SOMU12"+_12HourSDF.format(_24HourDt));
				   _12HourFormatetime=_12HourSDF.format(_24HourDt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           
	           
	        if(DeliveryTimeImg_From_boolean){
	        	 Delivery_Time_From.setText(_12HourFormatetime);
	        	 DeliveryTimeImg_From_boolean=false;
	        	 
	        }
	        if(DeliveryTimeImg_To_boolean){
	        	Delivery_Time_To.setText(_12HourFormatetime);
	        	 DeliveryTimeImg_To_boolean=false;
	        	 
	        }
	        if(DoNotDeliverTimeImg_From_boolean){
	        	DoNotDeliverTime_From.setText(_12HourFormatetime);
	        	DoNotDeliverTimeImg_From_boolean=false;
	        	 
	        }
	        if(DoNotDeliverTimeImg_To_boolean){
	        	DoNotDeliverTime_To.setText(_12HourFormatetime);
	        	DoNotDeliverTimeImg_To_boolean=false;
	        	 
	        }
	       
	       
		
	}
	
	public boolean validateDelivery_Location_Spinner()
	{
		boolean isDeliveryTimeImg_From_booleanSelected=false;
		if(helperDb.checkIfCurrentStoreIsNewInOrderBill(storeID)==0)
		{
			if(Delivery_Location_Spinner.getText().toString().trim().equals("Select"))
			{
				alertError("Please Select DeliveryLocation");
			}
			else
			{
				isDeliveryTimeImg_From_booleanSelected=true;
			}	
		}
		else
		{
			isDeliveryTimeImg_From_booleanSelected=true;
		}
		
		return isDeliveryTimeImg_From_booleanSelected;
		
	}
	
	public void alertError(String msg)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(
				Delivery_Details_Activity.this).create();

// Setting Dialog Title
alertDialog.setTitle("Validation Info");

// Setting Dialog Message
alertDialog.setMessage(msg);

// Setting OK Button
alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
		// Write your code here to execute after dialog closed
	dialog.dismiss();
		}
});

// Showing Alert Message
alertDialog.show();
	}

}
