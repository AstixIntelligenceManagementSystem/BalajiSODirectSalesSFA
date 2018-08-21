


package project.astix.com.balajisosfadirectsales;


import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;


import com.astix.Common.CommonInfo;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class FragmentA_first extends Fragment implements DatePickerDialog.OnDateSetListener,OnFocusChangeListener
{
	
	String ManufacturerID="0";
	String ManufacturerName="0";
	ViewPager viewPager;
	String quatationFlag;
	String globalValueOfPaymentStageCheck="0"+"_"+"0"+"_"+"0";
	View rootView,veiwBelow;
	EditText percentageOfAdvanceBeforeDelivery,percentageOfOnDelivery,creditDaysOfOnDelivery,PercentageOfCredit,creditDaysOfCredit,creditLimitOfCredit;
	String globalValueOfPmntStageCheckBox="";
	CheckBox AdvanceBeforeDelivery,OnDelivery,Credit,AdvanceBeforeDeliveryCheckBoxNew,OnDeliveryCheckBoxNew,CreditCheckBoxNew;
	String idOfCheckBoxes="";
	 LinkedHashMap<String,String> hmapZoneDisplayMstr;
	 LinkedHashMap<String,String> hmapCreationOfCheckBox;
	 String paymentStageidGlobal="NULL";
	TextView PaymentStageTextView,paymentModeTextviewNew,creditdaysTextboxNew,CreditlimitTextboxNew,percentageTextviewNew,paymentstagetextviewNew, CreditDaysTextbox, PaymentModeTextView, Date,SalesQuoteTypeSpinner,ValFrom,ValTo,SalesQuoteType,ValidityFrom,PaymentTerms,headerstring;
	EditText QuatNameEdit,PaymntTermSpinner,CustNameEdit,ContactPerEdit,ContactPerMobEdit,ContactPerEmailEdit,ExpBusValueEdit,PaymntTermEdit;
	ImageView CalenderValFrom,CalenderValTo;
	Button Done_btn,cancleBtn;
	AlertDialog.Builder alertDialog;
	LinearLayout parentOfCheckBox,parentOfAllPaymentStructure,parentOfCreditPayMentMode,parentOfOnDeliveryPayMentMode,parentOfAdvanceBeforeDeliveryPayMentMode;
	RelativeLayout MainlayoutParentOfWholePage;
	 AlertDialog ad;
	 String whenclickAgain="NULL";
	    CheckBox chBoxView;
	 View convertView;
	 ListView listDominantBrand;
	 View view6;
	 String CurrentDate;
	 
	 ArrayAdapter<String> adapterDominantBrand;
	 Calendar todaydate;
	 LinkedHashMap<String, String> hmapQuotationPersionMeetDetails;
	 LinkedHashMap<String, String> hmapPrcDetailsFromDataBase;
	 LinkedHashMap<String, String> hmapAllValuesOfPaymentMode;
	 LinkedHashMap<String, String> hmapforQuotationtype;
	 String[] dominantbrandtList;
	/* String dominantbrandtList[] = {"Select", "Individual","Periodic"};*/
	 String paymentTermList[] = {"Select", "Week","Month","Year"};
	 Calendar calendar ;
	 DatePickerDialog datePickerDialog ;
	    int Year, Month, Day ;
	    boolean fromDateBool=false;
		boolean toDateBool=false;
		 TextView frmDate,todateText;
		 String uniqueId;
		 PRJDatabase dbengine;
		 String StoreNameFromHmap,ContactPersonFromHmap,ContactPersonPhoneFromHmap,ContactPersonEmailFromHmap,SalesQuoteTypeFromHmap,SalesQuoteValidFromFromHmap,SalesQuoteValidToFromHmap,ExpectedBusinessValueFromHmap,PaymentTermsFromHmap,PaymentTermsTypeFromHmap,DateFromHmap,SalesQuotePrcsIdFromHmap,SalesQuotePrcsFromHmap; 
	public FragmentA_first() 
	{
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		// Inflate the layout for this fragment
		
		 rootView= inflater.inflate(R.layout.fragment_a_first, container, false);
		 
		 MainlayoutParentOfWholePage=(RelativeLayout) rootView.findViewById(R.id.Mainlayout);
		 AdvanceBeforeDelivery=(CheckBox) rootView.findViewById(R.id.AdvanceBeforeDelivery);
		 
		 AdvanceBeforeDeliveryCheckBoxNew =(CheckBox) rootView.findViewById(R.id.AdvanceBeforeDeliveryCheckBoxNew );
		 OnDeliveryCheckBoxNew=(CheckBox) rootView.findViewById(R.id.OnDeliveryCheckBoxNew);
		 CreditCheckBoxNew=(CheckBox) rootView.findViewById(R.id.CreditCheckBoxNew);
		 
		 OnDelivery=(CheckBox) rootView.findViewById(R.id.OnDelivery);
		 Credit=(CheckBox) rootView.findViewById(R.id.Credit);
		 parentOfAllPaymentStructure= (LinearLayout) rootView.findViewById(R.id.parentOfAllPaymentStructure);
		 parentOfCheckBox= (LinearLayout) rootView.findViewById(R.id.ss);
		 parentOfCreditPayMentMode= (LinearLayout) rootView.findViewById(R.id.parentOfCreditPayMentMode);
		 parentOfOnDeliveryPayMentMode= (LinearLayout) rootView.findViewById(R.id.parentOfOnDeliveryPayMentMode);
		 parentOfAdvanceBeforeDeliveryPayMentMode= (LinearLayout) rootView.findViewById(R.id.parentOfAdvanceBeforeDeliveryPayMentMode);
		 
		 veiwBelow=rootView.findViewById(R.id.veiwBelow);
		 view6=rootView.findViewById(R.id.view6);
		 
		 paymentModeTextviewNew=(TextView) rootView.findViewById(R.id.paymentModeTextviewNew);
		 creditdaysTextboxNew=(TextView) rootView.findViewById(R.id.creditdaysTextboxNew);
		 CreditlimitTextboxNew=(TextView) rootView.findViewById(R.id.CreditlimitTextboxNew);
		 
		 
		 percentageTextviewNew=(TextView) rootView.findViewById(R.id.percentageTextviewNew);
		 PaymentStageTextView=(TextView) rootView.findViewById(R.id.PaymentStageTextView);
		 paymentstagetextviewNew=(TextView) rootView.findViewById(R.id.paymentstagetextviewNew);
		 CreditDaysTextbox=(TextView) rootView.findViewById(R.id.CreditDaysTextbox);
		 Date=(TextView) rootView.findViewById(R.id.Date);
		 ValFrom=(TextView) rootView.findViewById(R.id.ValFrom);
		 ValTo=(TextView) rootView.findViewById(R.id.ValTo);
		 PaymntTermSpinner=(EditText) rootView.findViewById(R.id.PaymntTermSpinner);
		 SalesQuoteTypeSpinner= (TextView) rootView.findViewById(R.id.SalesQuoteTypeSpinner);
		 SalesQuoteType= (TextView) rootView.findViewById(R.id.SalesQuoteType);
		 ValidityFrom= (TextView) rootView.findViewById(R.id.ValidityFrom);
		 PaymentTerms=(TextView) rootView.findViewById(R.id.PaymentTerms);
		 headerstring=(TextView) rootView.findViewById(R.id.headerstring);
		 PaymentModeTextView=(TextView) rootView.findViewById(R.id.PaymentModeTextView);
		 
		 QuatNameEdit=(EditText) rootView.findViewById(R.id.QuatNameEdit);
		 CustNameEdit=(EditText) rootView.findViewById(R.id.CustNameEdit);
		 
		 percentageOfAdvanceBeforeDelivery=(EditText) rootView.findViewById(R.id.percentageOfAdvanceBeforeDelivery);
		 percentageOfOnDelivery=(EditText) rootView.findViewById(R.id.percentageOfOnDelivery);
		 creditDaysOfOnDelivery=(EditText) rootView.findViewById(R.id.creditDaysOfOnDelivery);
		 PercentageOfCredit=(EditText) rootView.findViewById(R.id.PercentageOfCredit);
		 creditDaysOfCredit=(EditText) rootView.findViewById(R.id.creditDaysOfCredit);
		 creditLimitOfCredit=(EditText) rootView.findViewById(R.id.creditLimitOfCredit);
		 
		 CustNameEdit.setText(QuotationActivity.selStoreName) ;
		 
		 ContactPerEdit=(EditText) rootView.findViewById(R.id.ContactPerEdit);
		 ContactPerMobEdit=(EditText) rootView.findViewById(R.id.ContactPerMobEdit);
		 ContactPerEmailEdit=(EditText) rootView.findViewById(R.id.ContactPerEmailEdit);
		 ExpBusValueEdit=(EditText) rootView.findViewById(R.id.ExpBusValueEdit);
		 PaymntTermEdit=(EditText) rootView.findViewById(R.id.PaymntTermEdit);
		 
		 CalenderValFrom=(ImageView) rootView.findViewById(R.id.CalenderValFrom);
		 CalenderValTo=(ImageView) rootView.findViewById(R.id.CalenderValTo);
		 
		 Done_btn= (Button) rootView.findViewById(R.id.Done_btn);
		 cancleBtn= (Button) rootView.findViewById(R.id.cancleBtn);
		 dbengine=new PRJDatabase(getActivity());
		 hmapPrcDetailsFromDataBase=dbengine.getSalesQuotePrcsMstr();
		 hmapforQuotationtype=dbengine.fnGettblSalesQuoteTypeMstr();
		
	 quatationFlag=	CommonInfo.quatationFlag;
	 if(quatationFlag.equals("NEW"))
	 {
		 String allmonths[]={"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
	     todaydate=Calendar.getInstance(TimeZone.getDefault());
	     
	      int dy=todaydate.get(Calendar.DAY_OF_MONTH);
	      int mnth=todaydate.get(Calendar.MONTH);
	      int yr=todaydate.get(Calendar.YEAR);
	      Date.setText(dy+"-"+allmonths[mnth]+"-"+yr);
	      CurrentDate=dy+"-"+allmonths[mnth]+"-"+yr;
	   
	      headerstring.setText(" Sales Quotation Status:"+hmapPrcDetailsFromDataBase.get("1")); // SalesQuotePrcsId will not changed,only text can be change
	      String quottype="Select";
	    
	      for(Map.Entry<String, String> entry2:hmapforQuotationtype.entrySet())
	          {  
	    	     String keyname=  entry2.getKey().toString().trim();
	    	     String valueid=  entry2.getValue().toString().trim();
	    	     if(valueid.equals("2"))
	    	     {
	    	    	 quottype=keyname;
		         }
		      }
	   
	      SalesQuoteTypeSpinner.setText(quottype);
	      ValFrom.setText(dy+"-"+allmonths[mnth]+"-"+yr);
	  
	 }
	 spinnerInitialization();
	 calendarInitialization();
	 checkBoxCreationwhenPageLoading("1");
	 checkBoxCreationwhenPageLoading("2");
	 checkBoxCreationwhenPageLoading("3");
	 
	// ChechBoxCreation();
	// whenPaymentStageClick();
	 whenPaymentStageClickNew();
	 editTextCickWhenPaymentStageIsChecked();
	 disableAndUncheckPaymntMdOfAdvanceBeforeDelivery();
	 disableAndUncheckPaymntMdOfOnDelivery();
	 disableAndUncheckPaymntMdOfCredit();
	 removeErrorMsgwhenclickOnedittext();
	 
	 percentageOfAdvanceBeforeDelivery.setOnFocusChangeListener(FragmentA_first.this);
	 percentageOfOnDelivery.setOnFocusChangeListener(FragmentA_first.this);
	 creditDaysOfOnDelivery.setOnFocusChangeListener(FragmentA_first.this);
	 PercentageOfCredit.setOnFocusChangeListener(FragmentA_first.this);
	 creditDaysOfCredit.setOnFocusChangeListener(FragmentA_first.this);
	 creditLimitOfCredit.setOnFocusChangeListener(FragmentA_first.this);
	 
	 mustFillView();
	 hmapAllValuesOfPaymentMode= dbengine.fnGettblSalesQuotePaymentModeMstrAllValues();
	
	
	 
	 MainlayoutParentOfWholePage.setOnClickListener(new OnClickListener() 
	 {
		
		@Override
		public void onClick(View arg0) 
		{
			// TODO Auto-generated method stub
			/*PaymentModeTextView.setError(null);
			PaymentModeTextView.clearFocus();*/
			
			paymentstagetextviewNew.setError(null);
			paymentstagetextviewNew.clearFocus();
			
			
			percentageTextviewNew.setError(null);
			percentageTextviewNew.clearFocus();
			
			paymentModeTextviewNew.setError(null);
			paymentModeTextviewNew.clearFocus();
			
			
			creditdaysTextboxNew.setError(null);
			creditdaysTextboxNew.clearFocus();
			CreditlimitTextboxNew.setError(null);
			CreditlimitTextboxNew.clearFocus();
			
			PaymentModeTextView.setError(null);
			PaymentModeTextView.clearFocus();
		}
	});
	 savingAllData();
	 if(quatationFlag.equals("UPDATE"))
	 {
		 try 
		 {
			 String allValuesOfPaymentStageID="0";
			 getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
			hmapQuotationPersionMeetDetails=	 dbengine.getQuotationPersionMeetDetails(QuotationActivity.SalesQuoteId);
			 
			 if(hmapQuotationPersionMeetDetails.size()>0)
			 {
				 for(Map.Entry<String, String> entry:hmapQuotationPersionMeetDetails.entrySet())
					{
					 
					    String keyHashmapSalesQuoteId=entry.getKey().toString().trim();
						String ValueHashMap=entry.getValue().toString().trim();
						
						StoreNameFromHmap=ValueHashMap.split(Pattern.quote("^"))[0];
						ContactPersonFromHmap=ValueHashMap.split(Pattern.quote("^"))[1];
						ContactPersonPhoneFromHmap=ValueHashMap.split(Pattern.quote("^"))[2];
						ContactPersonEmailFromHmap=ValueHashMap.split(Pattern.quote("^"))[3];
						SalesQuoteTypeFromHmap=ValueHashMap.split(Pattern.quote("^"))[4];
						SalesQuoteValidFromFromHmap=ValueHashMap.split(Pattern.quote("^"))[5];
						SalesQuoteValidToFromHmap=ValueHashMap.split(Pattern.quote("^"))[6];
						ExpectedBusinessValueFromHmap=ValueHashMap.split(Pattern.quote("^"))[7];
						
						PaymentTermsFromHmap=ValueHashMap.split(Pattern.quote("^"))[8];
						
						PaymentTermsTypeFromHmap=ValueHashMap.split(Pattern.quote("^"))[9];
						
						DateFromHmap=ValueHashMap.split(Pattern.quote("^"))[10];
						SalesQuotePrcsIdFromHmap=ValueHashMap.split(Pattern.quote("^"))[11];
						SalesQuotePrcsFromHmap=ValueHashMap.split(Pattern.quote("^"))[12];
				
						String	pymentModeID=ValueHashMap.split(Pattern.quote("^"))[13];
				
						allValuesOfPaymentStageID=ValueHashMap.split(Pattern.quote("^"))[14];
						ManufacturerID=ValueHashMap.split(Pattern.quote("^"))[15];
						ManufacturerName=ValueHashMap.split(Pattern.quote("^"))[16];
						
						   //IF YOU WAANT MULIPLE SELECTION UN-COMMENT THESE LINES
					     /*String allValueOfADVANCE_BEFORE =allValuesOfPaymentStageID.split(Pattern.quote("$"))[0];
					     String allValueOfDELIVERY =allValuesOfPaymentStageID.split(Pattern.quote("$"))[1];
					     String allValueOfCREDIT =allValuesOfPaymentStageID.split(Pattern.quote("$"))[2];*/
					       
					    // end here
				
		String pymntStagIDofAdvn="0";
		if(allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].equals("1"))
		{
			percentageOfAdvanceBeforeDelivery.setText("100");
			enablePaymntMdOfAdvanceBeforeDelivery();
			
			pymntStagIDofAdvn=allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].toString().trim();
		
			String percentageOfAdvn=allValuesOfPaymentStageID.split(Pattern.quote("~"))[1].toString().trim();
			String paymentModeIdOfADVNC=allValuesOfPaymentStageID.split(Pattern.quote("~"))[4].toString().trim();
		
		
			enablePaymntMdOfAdvanceBeforeDelivery();
			AdvanceBeforeDeliveryCheckBoxNew .setChecked(true);
		 
			if(!percentageOfAdvn.equals("0"))
			{
				percentageOfAdvanceBeforeDelivery.setText(percentageOfAdvn);
			}
		 percentageOfAdvanceBeforeDelivery.setEnabled(true);
		
		if(paymentModeIdOfADVNC.contains("|"))
		{

			String[] option=paymentModeIdOfADVNC.split(Pattern.quote("|"));
			for(int i=0;i<option.length;i++)
			{
				String opt=option[i];
				if(hmapAllValuesOfPaymentMode.containsKey(opt))
				  {
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
										if(valueOfChebox.equals(chkedchkOptionId))
											{
													((CheckBox) ch).setChecked(true);
			       				
											}
			            		     }
				              }
				   }
				
			 }
			
		}
		else
		{
			if(hmapAllValuesOfPaymentMode.containsKey(paymentModeIdOfADVNC))
			{
				String valueOfChebox=hmapAllValuesOfPaymentMode.get(paymentModeIdOfADVNC);
				int count = parentOfAdvanceBeforeDeliveryPayMentMode.getChildCount();
				
				 for (int ui=0;ui<count;ui++) 
				 {
					 
					    View ch = parentOfAdvanceBeforeDeliveryPayMentMode.getChildAt(ui);
			            if (ch instanceof CheckBox)
			               {
			            	
			            		String chkedChkBoxTag=ch.getTag().toString();
			        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
			       			 	String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
			       			 	if(valueOfChebox.equals(chkedchkOptionId))
			       			 	{
			       			 		((CheckBox) ch).setChecked(true);
			       				
			       			 	}
			                }
				 }
			}
				
		}
		
			
			
			
		}
		String pymntStagIDofDelivery="0";
       if(allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].equals("2"))
         {
    	   PercentageOfCredit.setText("100");
    	   pymntStagIDofDelivery=allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].toString().trim();
    	   String percentageofDelivery=allValuesOfPaymentStageID.split(Pattern.quote("~"))[1].toString().trim();
           String creditDysofDelivery=allValuesOfPaymentStageID.split(Pattern.quote("~"))[2].toString().trim();
           String pymntmodeIDofDElivery=allValuesOfPaymentStageID.split(Pattern.quote("~"))[4].toString().trim();
           enablePaymntMdOfOnDelivery();
          if(!percentageofDelivery.equals("0"))
          {
    	     percentageOfOnDelivery.setText(percentageofDelivery);
    	  }
         percentageOfOnDelivery.setEnabled(true);
	     if(!creditDysofDelivery.equals("0"))
	     {
		    creditDaysOfOnDelivery.setText(creditDysofDelivery);
		    creditDaysOfOnDelivery.setEnabled(true);
	     }
	     OnDeliveryCheckBoxNew .setChecked(true);
    
	     if(pymntmodeIDofDElivery.contains("|"))
	        {


		     String[] option=pymntmodeIDofDElivery.split(Pattern.quote("|"));
		    for(int i=0;i<option.length;i++)
		    {
			String opt=option[i];
			if(hmapAllValuesOfPaymentMode.containsKey(opt))
			{
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
			       			if(valueOfChebox.equals(chkedchkOptionId))
			       			{
			       				((CheckBox) ch).setChecked(true);
			       			}
			             }
				    }
			}
			
		}
		
	
    	
    }
    else{

		if(hmapAllValuesOfPaymentMode.containsKey(pymntmodeIDofDElivery))
		{
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
			       			    if(valueOfChebox.equals(chkedchkOptionId))
			       			      {
			       			    		((CheckBox) ch).setChecked(true);
			       				
			       			      }
			            		
			             }
				 }
			}
			
			
		
	
    	
    }
			
		}
       String pymntStagIDofcredit="0";
      if(allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].equals("3"))
      {
    	  
    	  percentageOfOnDelivery.setText("100");
    	  pymntStagIDofcredit=allValuesOfPaymentStageID.split(Pattern.quote("~"))[0].toString().trim();
    	  String percentageofcredit=allValuesOfPaymentStageID.split(Pattern.quote("~"))[1].toString().trim();
    	  String creditDysofcredit = allValuesOfPaymentStageID.split(Pattern.quote("~"))[2].toString().trim();
    	  String creditLimitofcredit=allValuesOfPaymentStageID.split(Pattern.quote("~"))[3].toString().trim();
    	  String pymntmodeIDofcredit = allValuesOfPaymentStageID.split(Pattern.quote("~"))[4].toString().trim();
    	  if(!percentageofcredit.equals("0"))
    	    {
    		   PercentageOfCredit.setText(percentageofcredit);
    	    }
    	  PercentageOfCredit.setEnabled(true);
    	  if(!creditDysofcredit.equals("0"))
    	  {
    		  creditDaysOfCredit.setText(creditDysofcredit);
    		  creditDaysOfCredit.setEnabled(true);
    	  }
    	  
    	  if(!creditLimitofcredit.equals("0"))
    	  {
    		  creditLimitOfCredit.setText(creditLimitofcredit);
    		  creditLimitOfCredit.setEnabled(true);
    		  
    	  }
    	  CreditCheckBoxNew.setChecked(true);
    	 
    	  enablePaymntMdOfCredit();
    	  if(pymntmodeIDofcredit.contains("|"))
    	  {
            
    		    String[] option=	pymntmodeIDofcredit.split(Pattern.quote("|"));
    			for(int i=0;i<option.length;i++)
    			{
    				String opt=option[i];
    				if(hmapAllValuesOfPaymentMode.containsKey(opt))
    				{
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
    			        			if(valueOfChebox.equals(chkedchkOptionId))
    			        			  {
    			        					((CheckBox) ch).setChecked(true);
    			       				
    			        			  }
    			            		
    			             }
    				 }
    				}
    				
    			}
    			
    		
    	    	
    	    }
    	    else{

    			if(hmapAllValuesOfPaymentMode.containsKey(pymntmodeIDofcredit))
    			{
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
    			        			if(valueOfChebox.equals(chkedchkOptionId))
    			        			{
    			        				((CheckBox) ch).setChecked(true);
	    			       			}
    			            		
    			            }
    				 }
    				}
    				
    				
    			
    		
    	    	
    	    }
	
        }
				
				
       globalValueOfPaymentStageCheck=pymntStagIDofAdvn+"_"+pymntStagIDofDelivery+"_"+pymntStagIDofcredit;
       
      
				//PaymentModeTextView.setVisibility(View.VISIBLE);
				//veiwBelow.setVisibility(View.VISIBLE);
				//here code of paymentStage with contains
				//checkBoxCreationOnClickOfRadioButton(paymentStageidGlobal);
				/*
				if(pymentModeID.contains("_")){
						String[] option=	pymentModeID.split(Pattern.quote("_"));
						for(int i=0;i<option.length;i++){
							String opt=option[i];
							if(hmapAllValuesOfPaymentMode.containsKey(opt)){
							String valueOfChebox=	hmapAllValuesOfPaymentMode.get(opt);
							int count = parentOfCheckBox.getChildCount();
							
							 for (int ui=0;ui<count;ui++) 
							 {
								 
								 View ch = parentOfCheckBox.getChildAt(ui);
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
						}*/
				
				/*else{if(hmapAllValuesOfPaymentMode.containsKey(pymentModeID)){
					String valueOfChebox=	hmapAllValuesOfPaymentMode.get(pymentModeID);
					int count = parentOfCheckBox.getChildCount();
					
					 for (int ui=0;ui<count;ui++) 
					 {
						 
						 View ch = parentOfCheckBox.getChildAt(ui);
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
					
					
				}*/
						
						//now saving hashMapData data  to layout
						CustNameEdit.setText(StoreNameFromHmap);
						headerstring.setText(" Sales Quotation Status:"+SalesQuotePrcsFromHmap);
						ContactPerEdit.setText(ContactPersonFromHmap);
						ContactPerMobEdit.setText(ContactPersonPhoneFromHmap);
						if(!ContactPersonEmailFromHmap.equals("0")){
							ContactPerEmailEdit.setText(ContactPersonEmailFromHmap);
						}
						 String quottype="Select";
						   for(Map.Entry<String, String> entry2:hmapforQuotationtype.entrySet())
						   { 
							   String keyname=  entry2.getKey().toString().trim();
							   String valueid=  entry2.getValue().toString().trim();
							   if(valueid.equals(SalesQuoteTypeFromHmap))
							   {
								   quottype=keyname;
							   }
						   }
						
						SalesQuoteTypeSpinner.setText(quottype);
						if(SalesQuoteTypeFromHmap.trim().equals("1"))
						{
							CalenderValFrom.setEnabled(false);
							/*CalenderValFrom.setVisibility(View.GONE);
							
							ValidityFrom.setVisibility(View.GONE);
							view6.setVisibility(View.GONE);*/
							ValFrom.setText(SalesQuoteValidFromFromHmap.trim());
							ValTo.setText(SalesQuoteValidToFromHmap);
						}
						if(SalesQuoteTypeFromHmap.trim().equals("2"))
						{
							CalenderValFrom.setVisibility(View.VISIBLE);
							ValFrom.setVisibility(View.VISIBLE);
							CalenderValFrom.setEnabled(true);
							ValidityFrom.setVisibility(View.VISIBLE);
							view6.setVisibility(View.VISIBLE);
							ValFrom.setText(SalesQuoteValidFromFromHmap.trim());
							ValTo.setText(SalesQuoteValidToFromHmap);
						}
					//	ExpBusValueEdit.setText(ExpectedBusinessValueFromHmap);
						/*if(paymentStageidGlobal.equals("1")){
							AdvanceBeforeDelivery.setChecked(true);
							
						}
						if(paymentStageidGlobal.equals("3")){
							Credit.setChecked(true);
							PaymntTermEdit.setText(PaymentTermsFromHmap);
							PaymntTermSpinner.setText(PaymentTermsTypeFromHmap);
							PaymentTerms.setVisibility(View.VISIBLE);
							CreditDaysTextbox.setVisibility(View.VISIBLE);
							PaymntTermEdit.setVisibility(View.VISIBLE);
							PaymntTermSpinner.setVisibility(View.VISIBLE);
						}
					
						if(paymentStageidGlobal.equals("2")){
							OnDelivery.setChecked(true);
							PaymntTermSpinner.setText(PaymentTermsTypeFromHmap);
							PaymentTerms.setVisibility(View.GONE);
							CreditDaysTextbox.setVisibility(View.VISIBLE);
							PaymntTermEdit.setVisibility(View.GONE);
							PaymntTermSpinner.setVisibility(View.VISIBLE);
						}*/
						
						
						
						Date.setText(DateFromHmap);
						CurrentDate=DateFromHmap;
						
						
						
					}
			 }
			 if(CommonInfo.prcID.equals("4") || CommonInfo.prcID.equals("5") )
			 {
				 disableAllViewOfPage();
			 }
			 getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		} 
		 catch (NumberFormatException e)
		 {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
		/*SalesQuoteTypeSpinner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 alertDialog = new AlertDialog.Builder(getActivity());
				 LayoutInflater inflater =getActivity(). getLayoutInflater();
				 convertView = (View) inflater.inflate(R.layout.activity_list, null);
				  listDominantBrand = (ListView)convertView. findViewById(R.id.list_view);
				  adapterDominantBrand = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, dominantbrandtList);
				  listDominantBrand.setAdapter(adapterDominantBrand);
				  alertDialog.setView(convertView);
			        alertDialog.setTitle("Sales Quote Type");
			        listDominantBrand.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							String abc=listDominantBrand.getItemAtPosition(arg2).toString().trim();
							SalesQuoteTypeSpinner.setText(abc);
							ad.dismiss();
							if(abc.equals("Select")){
								SalesQuoteTypeSpinner.setText("Select");
								CalenderValFrom.setVisibility(View.VISIBLE);
								ValFrom.setVisibility(View.VISIBLE);
								ValidityFrom.setVisibility(View.VISIBLE);
								view6.setVisibility(View.VISIBLE);
							}
							if(abc.equals("Individual")){
								
								CalenderValFrom.setVisibility(View.GONE);
								ValFrom.setVisibility(View.GONE);
								ValidityFrom.setVisibility(View.GONE);
								view6.setVisibility(View.GONE);
								
							}
							if(abc.equals("Periodic")){
								
								CalenderValFrom.setVisibility(View.VISIBLE);
								ValFrom.setVisibility(View.VISIBLE);
								ValidityFrom.setVisibility(View.VISIBLE);
								view6.setVisibility(View.VISIBLE);
								
							}
							
							
						}
					});
			        ad=alertDialog.show();
				
			}
		});
*/
	
		return rootView;
	}
public SpannableStringBuilder textWithMandatory(String text_Value)
	{
		String simple = text_Value;
		String colored = "*";
		SpannableStringBuilder builder = new SpannableStringBuilder();
		
	
		builder.append(colored);
		int start = builder.length();
		builder.setSpan(new ForegroundColorSpan(Color.RED), 0, start, 0);
		builder.append(simple);
		int end = builder.length();
	
			//text.setText(builder);
			
			return builder;

	}
public void removeErrorMsgwhenclickOnedittext()
{
	percentageOfAdvanceBeforeDelivery.setOnClickListener(new OnClickListener() 
	{
		
		@Override
		public void onClick(View arg0) 
		{
			percentageTextviewNew.setError(null);
			percentageTextviewNew.clearFocus();
			
		}
	});
	percentageOfOnDelivery.setOnClickListener(new OnClickListener()
	{
		
		@Override
		public void onClick(View arg0)
		{
			percentageTextviewNew.setError(null);
			percentageTextviewNew.clearFocus();
			
		}
	});
	creditDaysOfOnDelivery.setOnClickListener(new OnClickListener() 
	{
	
	@Override
	public void onClick(View arg0)
	{
		creditdaysTextboxNew.setError(null);
		creditdaysTextboxNew.clearFocus();
		
	}
});
	PercentageOfCredit.setOnClickListener(new OnClickListener()
	{
	
	@Override
	public void onClick(View arg0)
	{
		percentageTextviewNew.setError(null);
		percentageTextviewNew.clearFocus();
		
	}
});
	creditDaysOfCredit.setOnClickListener(new OnClickListener()
	{
	
	@Override
	public void onClick(View arg0)
	{
		creditdaysTextboxNew.setError(null);
		creditdaysTextboxNew.clearFocus();
		
	}
});
	creditLimitOfCredit.setOnClickListener(new OnClickListener() 
	{
	
	@Override
	public void onClick(View arg0) 
	{
		CreditlimitTextboxNew.setError(null);
		CreditlimitTextboxNew.clearFocus();
		
	}
});

}
public void mustFillView()
{
	 TextView ContactPertextbox = (TextView)rootView.findViewById(R.id.ContactPer);
	 SpannableStringBuilder text_Value=textWithMandatory(ContactPertextbox.getText().toString());
	 ContactPertextbox.setText(text_Value);
	 
	 TextView ContactPerMobtextbox = (TextView)rootView.findViewById(R.id.ContactPerMob);
	 SpannableStringBuilder ContactPerMobtextboxtext_Value=textWithMandatory(ContactPerMobtextbox.getText().toString());
	 ContactPerMobtextbox.setText(ContactPerMobtextboxtext_Value);
	 
	 
	 SpannableStringBuilder SalesQuoteTypetextboxtext_Value=textWithMandatory(SalesQuoteType.getText().toString());
	 SalesQuoteType.setText(SalesQuoteTypetextboxtext_Value);
	 
	 SpannableStringBuilder ValidityFromtextboxtext_Value=textWithMandatory(ValidityFrom.getText().toString());
	 ValidityFrom.setText(ValidityFromtextboxtext_Value);
	 
	 TextView ValidityTotextbox = (TextView)rootView.findViewById(R.id.ValidityTo);
	 SpannableStringBuilder ValidityTotextboxtext_Value=textWithMandatory(ValidityTotextbox.getText().toString());
	 ValidityTotextbox.setText(ValidityTotextboxtext_Value); 
	 
	 TextView ExpBusValuetextbox = (TextView)rootView.findViewById(R.id.ExpBusValue);
	 SpannableStringBuilder ExpBusValuetextboxtext_Value=textWithMandatory(ExpBusValuetextbox.getText().toString());
	 ExpBusValuetextbox.setText(ExpBusValuetextboxtext_Value); 
	 
	 SpannableStringBuilder PaymentStageTextViewtext_Value=textWithMandatory(PaymentStageTextView.getText().toString());
	 PaymentStageTextView.setText(PaymentStageTextViewtext_Value);
	 
	 SpannableStringBuilder PaymentModeTextViewtext_Value=textWithMandatory(PaymentModeTextView.getText().toString());
	 PaymentModeTextView.setText(PaymentModeTextViewtext_Value);
	 
	 SpannableStringBuilder PaymentTermstext_Value=textWithMandatory(PaymentTerms.getText().toString());
	 PaymentTerms.setText(PaymentTermstext_Value);
	 
	 SpannableStringBuilder CreditDaysTextboxtext_Value=textWithMandatory(CreditDaysTextbox.getText().toString());
	 CreditDaysTextbox.setText(CreditDaysTextboxtext_Value);

}
private void editTextCickWhenPaymentStageIsChecked() 
{
	if(AdvanceBeforeDeliveryCheckBoxNew.isChecked())
	{
		
	}
	if(OnDeliveryCheckBoxNew.isChecked())
	{
		
	}
	if(CreditCheckBoxNew.isChecked())
	{
		
	}
	

}
public void disableAndUncheckPaymntMdOfAdvanceBeforeDelivery()
{
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
public void disableAndUncheckPaymntMdOfOnDelivery() 
{
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
public void disableAndUncheckPaymntMdOfCredit() 
{
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
public void enablePaymntMdOfAdvanceBeforeDelivery() 
{
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

public void enablePaymntMdOfOnDelivery()
{
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
public void enablePaymntMdOfCredit() 
{
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
public void whenPaymentStageClickNew()
{
	AdvanceBeforeDeliveryCheckBoxNew .setOnClickListener(new OnClickListener()
	{
		
		@Override
		public void onClick(View arg0)
		{
			 paymentstagetextviewNew.setError(null);
			 paymentstagetextviewNew.clearFocus();
			 AdvanceBeforeDeliveryCheckBoxNew.setChecked(true);
			   if(AdvanceBeforeDeliveryCheckBoxNew.isChecked())
			   {
				
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
			else
			{/*
				
				
				 
				
				AdvanceBeforeDeliveryCheckBoxNew.setChecked(false);
				percentageOfAdvanceBeforeDelivery.setText("");
				percentageOfAdvanceBeforeDelivery.setEnabled(false);
				percentageOfAdvanceBeforeDelivery.clearFocus();
				disableAndUncheckPaymntMdOfAdvanceBeforeDelivery();
				
				String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
				paymntStgAdvnc="0";
				String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
				String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
				globalValueOfPaymentStageCheck=paymntStgAdvnc+"_"+paymntStgDelvry+"_"+paymntStgCrdt;
				
				
			*/}
			
		}
	});
	OnDeliveryCheckBoxNew .setOnClickListener(new OnClickListener() 
	{
		
		@Override
		public void onClick(View arg0)
		{
			 paymentstagetextviewNew.setError(null);
			 paymentstagetextviewNew.clearFocus();
			 OnDeliveryCheckBoxNew.setChecked(true);
			
			 if(OnDeliveryCheckBoxNew.isChecked())
			  {
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
			else
			{/*
				OnDeliveryCheckBoxNew.setChecked(false);
				percentageOfOnDelivery.setText("");
				creditDaysOfOnDelivery.setText("");
				percentageOfOnDelivery.setEnabled(false);
				creditDaysOfOnDelivery.setEnabled(false);
				percentageOfOnDelivery.clearFocus();
				creditDaysOfOnDelivery.clearFocus();
				disableAndUncheckPaymntMdOfOnDelivery();
				String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
				String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
				paymntStgDelvry="0";
				String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
				globalValueOfPaymentStageCheck=paymntStgAdvnc+"_"+paymntStgDelvry+"_"+paymntStgCrdt;
				
				
			*/}
			
		}
	});
	CreditCheckBoxNew .setOnClickListener(new OnClickListener()
	{
	
	@Override
	public void onClick(View arg0) 
	{
		 paymentstagetextviewNew.setError(null);
		 paymentstagetextviewNew.clearFocus();
		 CreditCheckBoxNew.setChecked(true);
		
		 if(CreditCheckBoxNew.isChecked())
		  {
			
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
		else
		{/*
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
			String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
			String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
			String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
			paymntStgCrdt="0";
			globalValueOfPaymentStageCheck=paymntStgAdvnc+"_"+paymntStgDelvry+"_"+paymntStgCrdt;
		
		*/}
		
	}
});

}
	private void whenPaymentStageClick()
	{
		AdvanceBeforeDelivery.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				
					AdvanceBeforeDelivery.setChecked(true);
					OnDelivery.setChecked(false);
					Credit.setChecked(false);
					PaymentStageTextView.setError(null);
					PaymentStageTextView.clearFocus();
					
					paymentStageidGlobal="1";
					PaymentModeTextView.setVisibility(View.VISIBLE);
					veiwBelow.setVisibility(View.VISIBLE);
					parentOfCheckBox.removeAllViews();
				
				/*if(globalValueOfPmntStageCheckBox.equals("")){
					globalValueOfPmntStageCheckBox="1";
					
				}
				else{
					globalValueOfPmntStageCheckBox=globalValueOfPmntStageCheckBox+"_"+"1";
					
				}*/
				checkBoxCreationOnClickOfRadioButton("1");
				PaymentTerms.setVisibility(View.GONE);
				CreditDaysTextbox.setVisibility(View.GONE);
				PaymntTermEdit.setVisibility(View.GONE);
				PaymntTermSpinner.setVisibility(View.GONE);
				
			}
		});
		OnDelivery.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0)
			{
				
					OnDelivery.setChecked(true);
					Credit.setChecked(false);
					AdvanceBeforeDelivery.setChecked(false);
					PaymentStageTextView.setError(null);
					PaymentStageTextView.clearFocus();
					paymentStageidGlobal="2";
					PaymentModeTextView.setVisibility(View.VISIBLE);
					veiwBelow.setVisibility(View.VISIBLE);
					parentOfCheckBox.removeAllViews();
				
				/*if(globalValueOfPmntStageCheckBox.equals("")){
					globalValueOfPmntStageCheckBox="2";
					
				}
				else{
					globalValueOfPmntStageCheckBox=globalValueOfPmntStageCheckBox+"_"+"2";
					
				}*/
				checkBoxCreationOnClickOfRadioButton("2");
				PaymentTerms.setVisibility(View.GONE);
				CreditDaysTextbox.setVisibility(View.VISIBLE);
				PaymntTermEdit.setVisibility(View.GONE);
				PaymntTermSpinner.setVisibility(View.VISIBLE);
				
				
			}
		});
		Credit.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
					PaymentStageTextView.setError(null);
					PaymentStageTextView.clearFocus();
				
					Credit.setChecked(true);
					OnDelivery.setChecked(false);
					AdvanceBeforeDelivery.setChecked(false);
					paymentStageidGlobal="3";
					PaymentModeTextView.setVisibility(View.VISIBLE);
					veiwBelow.setVisibility(View.VISIBLE);
					parentOfCheckBox.removeAllViews();
				
				/*if(globalValueOfPmntStageCheckBox.equals("")){
					globalValueOfPmntStageCheckBox="3";
					
				}
				else{
					globalValueOfPmntStageCheckBox=globalValueOfPmntStageCheckBox+"_"+"3";
					
				}*/
				checkBoxCreationOnClickOfRadioButton("3");
				PaymentTerms.setVisibility(View.VISIBLE);
				CreditDaysTextbox.setVisibility(View.VISIBLE);
				PaymntTermEdit.setVisibility(View.VISIBLE);
				PaymntTermSpinner.setVisibility(View.VISIBLE);
				
			}
		});

	}
	@Override
	public void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		//Toast.makeText(getActivity().getApplicationContext(), "Resume", Toast.LENGTH_SHORT).show();
	}
	
	/*public void fillValueOnPercentage() {
		
		if(globalValueOfPmntStageCheckBox.contains("_")){
			
		}
		else{
			if(globalValueOfPmntStageCheckBox.equals("")){}
			if(globalValueOfPmntStageCheckBox){}
			if(globalValueOfPmntStageCheckBox){}
			
		}

	}*/
	
	public void disableAllViewOfPage()
	{
		ContactPerEdit.setEnabled(false);
		ContactPerMobEdit.setEnabled(false);
		ContactPerEmailEdit.setEnabled(false);
		SalesQuoteTypeSpinner.setEnabled(false);
		CalenderValFrom.setEnabled(false);
		CalenderValTo.setEnabled(false);
		ExpBusValueEdit.setEnabled(false);
		AdvanceBeforeDelivery.setEnabled(false);
		OnDelivery.setEnabled(false);
		Credit.setEnabled(false);
		PaymntTermEdit.setEnabled(false);
		PaymntTermSpinner.setEnabled(false);
		//Done_btn.setEnabled(false);
		Done_btn.setVisibility(View.INVISIBLE);
		AdvanceBeforeDeliveryCheckBoxNew .setEnabled(false);
		 CreditCheckBoxNew.setEnabled(false);
		 OnDeliveryCheckBoxNew.setEnabled(false);
		 percentageOfAdvanceBeforeDelivery.setEnabled(false);
		 percentageOfOnDelivery.setEnabled(false);
		 creditDaysOfOnDelivery.setEnabled(false);

		 PercentageOfCredit.setEnabled(false);
		 creditDaysOfCredit.setEnabled(false);
		 creditLimitOfCredit.setEnabled(false);
		parentOfAllPaymentStructure.setEnabled(false);
		disableAllCheckBoxOfPaymentMode( parentOfAdvanceBeforeDeliveryPayMentMode);
		disableAllCheckBoxOfPaymentMode( parentOfOnDeliveryPayMentMode);
		disableAllCheckBoxOfPaymentMode( parentOfCreditPayMentMode);
		
	}
	 public void disableAllCheckBoxOfPaymentMode(LinearLayout parentofPaymentModeCheckboxes)
	 {
		// TODO Auto-generated method stub 
		 int count = parentofPaymentModeCheckboxes.getChildCount();
			
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentofPaymentModeCheckboxes.getChildAt(ui);
	            if (ch instanceof CheckBox)
	            {
	            	ch.setEnabled(false);
	            	
	            }
		 }

	}
	
	public void ChechBoxCreation() 
	{
		//hmapZoneDisplayMstr=dbengine.fnGettblSalesQuotePaymentModeMstr();
		/* hmapZoneDisplayMstr.put("1", "Self");
	        hmapZoneDisplayMstr.put("2", "SO");
	        hmapZoneDisplayMstr.put("3", "ASM");
	        hmapZoneDisplayMstr.put("4", "RTMM");
	        hmapZoneDisplayMstr.put("5", "ZSM");
	        hmapZoneDisplayMstr.put("6", "Others ");*/
		parentOfCheckBox= (LinearLayout) rootView.findViewById(R.id.ss);
		for(Map.Entry<String, String> entry:hmapZoneDisplayMstr.entrySet())
		{
        	 int checkBoxDescId=Integer.parseInt(entry.getKey().toString());
			 String checkBoxDesc=entry.getValue().toString().trim();
			 LinearLayout.LayoutParams layoutParamss = new LinearLayout.LayoutParams(
			      LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			 layoutParamss.setMargins(16, 3, 0, 3);
			 chBoxView = new CheckBox(getActivity());
			 chBoxView.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_button_image));
			 chBoxView.setText("   "+checkBoxDesc);
			 chBoxView.setTextColor(getResources().getColor(R.color.black));
			 chBoxView.setTextSize(14);
			 chBoxView.setId(checkBoxDescId);
			 chBoxView.setTag(checkBoxDescId+"^"+checkBoxDesc);
	    	  // ch.setTag(checkBoxDesc);
			 chBoxView.setOnClickListener(getOnClickDoSomething(chBoxView));
	    	 //  ch.setEnabled(false);
	    	   parentOfCheckBox.addView(chBoxView,layoutParamss);
	    	  
    }

	}
	
	public void checkBoxCreationOnClickOfRadioButton(String paymentStageID) 
	{
		hmapZoneDisplayMstr=dbengine.fnGettblSalesQuotePaymentModeMstr(paymentStageID);
		
		for(Map.Entry<String, String> entry:hmapZoneDisplayMstr.entrySet())
		{
        	 int checkBoxDescId=Integer.parseInt(entry.getKey().toString());
			 String checkBoxDesc=entry.getValue().toString().trim();
			 LinearLayout.LayoutParams layoutParamss = new LinearLayout.LayoutParams(
			      LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			 layoutParamss.setMargins(16, 3, 0, 3);
			 chBoxView = new CheckBox(getActivity());
			 chBoxView.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_button_image));
			 chBoxView.setText("   "+checkBoxDesc);
			 chBoxView.setTextColor(getResources().getColor(R.color.black));
			 chBoxView.setTextSize(14);
			 chBoxView.setId(checkBoxDescId);
			 chBoxView.setTag(checkBoxDescId+"^"+checkBoxDesc);
	    	  // ch.setTag(checkBoxDesc);
			 chBoxView.setOnClickListener(getOnClickDoSomething(chBoxView));
	    	 //  ch.setEnabled(false);
	    	 parentOfCheckBox.addView(chBoxView,layoutParamss);
	    	  
    }
		

		

	}
	public void checkBoxCreationwhenPageLoading(String paymentStageID)
	{
		hmapZoneDisplayMstr=dbengine.fnGettblSalesQuotePaymentModeMstr(paymentStageID);
		
		for(Map.Entry<String, String> entry:hmapZoneDisplayMstr.entrySet())
		{
        	 int checkBoxDescId=Integer.parseInt(entry.getKey().toString());
			 String checkBoxDesc=entry.getValue().toString().trim();
			 LinearLayout.LayoutParams layoutParamss = new LinearLayout.LayoutParams(
			      LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

			 layoutParamss.setMargins(3, 3, 0, 3);
			 chBoxView = new CheckBox(getActivity());
			 chBoxView.setButtonDrawable(getResources().getDrawable(R.drawable.checkbox_button_image));
			 chBoxView.setText("   "+checkBoxDesc);
			 chBoxView.setTextColor(getResources().getColor(R.color.black));
			 chBoxView.setTextSize(10);
			 chBoxView.setId(checkBoxDescId);
			 chBoxView.setTag(checkBoxDescId+"^"+checkBoxDesc);
	    	  // ch.setTag(checkBoxDesc);
			 chBoxView.setOnClickListener(getOnClickDoSomething(chBoxView));
	    	 //  ch.setEnabled(false);
			 if(paymentStageID.equals("1"))
			 {
				 
				 parentOfAdvanceBeforeDeliveryPayMentMode.addView(chBoxView,layoutParamss);
			 }
			 if(paymentStageID.equals("2"))
			 {
				 parentOfOnDeliveryPayMentMode.addView(chBoxView,layoutParamss);
			 }
			 if(paymentStageID.equals("3"))
			 {
				 parentOfCreditPayMentMode.addView(chBoxView,layoutParamss);
			 }
			 
	    	 //  parentOfCheckBox.addView(chBoxView,layoutParamss);
	    	  
    }}
	public void retrevingValueOfcheckBox() 
	{
		int count = parentOfCheckBox.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 
			 View ch = parentOfCheckBox.getChildAt(ui);
	            if (ch instanceof CheckBox)
	            {
	            	if(((CompoundButton) ch).isChecked()==true)
					{
	            		String chkedChkBoxTag=ch.getTag().toString();
	        			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
		       			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
		       			if(idOfCheckBoxes.equals(""))
		       			{
		       				idOfCheckBoxes=String.valueOf(chkedchkQuestId);
		       			}
		       			else
		       			{
		       				idOfCheckBoxes=idOfCheckBoxes+"_"+String.valueOf(chkedchkQuestId);
		       				
		       			}
	            		
	            		
	            		
					}
	            	
	            }
		 }

	}
	OnClickListener getOnClickDoSomething(final CheckBox button)
	{
    	return new OnClickListener()
    	{
    		public void onClick(View v) 
    		{
    			String idOfAllCheckedBoxes="";
    			paymentModeTextviewNew.setError(null);
    			paymentModeTextviewNew.clearFocus();
    			
    			PaymentModeTextView.setError(null);
    			PaymentModeTextView.clearFocus();
    			int count = parentOfCheckBox.getChildCount();
    			String curentSelectedZones="";
    			String chkedChkBoxTag=button.getTag().toString();
    			int chkedchkQuestId=Integer.parseInt(chkedChkBoxTag.split(Pattern.quote("^"))[0]);
	   			String chkedchkOptionId=chkedChkBoxTag.split(Pattern.quote("^"))[1];
	   			
	   			if(OnDeliveryCheckBoxNew.isChecked())
	   			{
	   				
	   				if(chkedchkOptionId.trim().equals("Post Dated Cheque"))
	   				{
	   					if(button.isChecked())
	   					{
	   						creditDaysOfOnDelivery.setEnabled(true);
	   					}
	   					else
	   					{
	   						creditDaysOfOnDelivery.setEnabled(false);
	   						creditDaysOfOnDelivery.clearFocus();
	   						creditDaysOfOnDelivery.setText("");
	   					}
	   					
	   				}
	   				
	   			}
   			/*if(button.isChecked()){
   				System.out.println();
   				button.setChecked(true);
   			}
   			else{
   				button.setChecked(false);
   				
   				System.out.println();
   			}*/
   		
   		/* for (int ui=0;ui<count;ui++) 
		 {
			 View ch = chkZoneCombo.getChildAt(ui);
	            if (ch instanceof CheckBox)
	            {
	            String rr=	ch.getTag().toString().split(Pattern.quote("^"))[1];
	            	if(ch.getTag().toString().split(Pattern.quote("^"))[1].contains("None")){
	            		if(((CheckBox) ch).isChecked()){
	            		((CheckBox) ch).setChecked(false);
	            		//button.setEnabled(enabled)
	            		
	            	}}
	            	
	            	if((Integer.parseInt(ch.getTag().toString().split(Pattern.quote("^"))[0])!=chkedchkQuestId))
	            	{
	            		((CheckBox) ch).setChecked(false);
	            		
	            	}
	            	}
	            
		 }*/
   			
   			if(chkedchkOptionId.contains("None"))
   			{
   				
		   			 for (int ui=0;ui<count;ui++) 
					 {
						 View ch = parentOfCheckBox.getChildAt(ui);
				            if (ch instanceof CheckBox)
				            {
				            	
				            	if((Integer.parseInt(ch.getTag().toString().split(Pattern.quote("^"))[0])!=chkedchkQuestId))
				            	{
				            		((CheckBox) ch).setChecked(false);
				            		
				            	}
				            }
				            
					 }
   			 
   			 
		   			if(button.isChecked())
		   			{
		   				System.out.println();
		   				button.setChecked(true);
		
		   	   		}
		   			else
		   			{
		   				button.setChecked(false);
		   				System.out.println();
		   			}
   			 
   			// button.setChecked(true);
   			 
   			}
   			else
   			{
   				
	   				System.out.println("");
	   				for (int ui=0;ui<count;ui++) 
	   			     {
		   				 View ch = parentOfCheckBox.getChildAt(ui);
		   		            if (ch instanceof CheckBox)
		   		            {
		   		            	if(ch.getTag().toString().split(Pattern.quote("^"))[1].contains("None"))
		   		            	{
		   		            		if(((CheckBox) ch).isChecked())
		   		            		{
		   		            			((CheckBox) ch).setChecked(false);
		   		            		}
		   		            	}
		   		            	
		   		            }
	   		            }
   			}
    			/* for (int ui=0;ui<count;ui++) 
    			 {
    				 View ch = chkZoneCombo.getChildAt(ui);
    		            if (ch instanceof CheckBox)
    		            {
    		            	if(((CompoundButton) ch).isChecked()==true)
    						{
    		            		
    		            		if(curentSelectedZones.equals(""))
    		            		{
    		            			curentSelectedZones=""+ch.getTag().toString();
    		            			System.out.println("curentSelectedZones"+ch.getTag().toString());
    		            		}
    		            		else
    		            		{
    		            			curentSelectedZones=curentSelectedZones +"^"+ch.getId();
    		            			System.out.println("curentSelectedZones"+ch.getTag().toString());
    		            		}
    		            		((CheckBox) ch).setChecked(false);
    		            		
    						}
    		            }
    			 }*/
    			
    			
    			//button.setChecked(true);
    			
    			System.out.println("sss"+ button.getId());
    			System.out.println("sss"+ button.getText().toString());
    			String string1=button.getText().toString().trim();
    		/*if(button.getText().toString().contains("Abhinav")){
    			 mainLayout.addView(view2);
    			
    			System.out.println("sssdddd");
    		}
    		if(button.getText().toString().contains("Nitish")){
   			
   			 mainLayout.removeView(view2);
   			System.out.println("sssdddd");
   		}	*/
    		} };

   
}
	public void spinnerInitialization() 
	{
		/*PaymntTermSpinner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				PaymentTerms.setError(null);
				PaymentTerms.clearFocus();
				AlertDialog.Builder	 alertDialog = new AlertDialog.Builder(getActivity());
				 LayoutInflater inflater =getActivity(). getLayoutInflater();
				 convertView = (View) inflater.inflate(R.layout.activity_list, null);
				  listDominantBrand = (ListView)convertView. findViewById(R.id.list_view);
				  adapterDominantBrand = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, paymentTermList);
				  listDominantBrand.setAdapter(adapterDominantBrand);
				  alertDialog.setView(convertView);
			        alertDialog.setTitle("Payment Term");
			        listDominantBrand.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							String abc=listDominantBrand.getItemAtPosition(arg2).toString().trim();
							PaymntTermSpinner.setText(abc);
							ad.dismiss();
							
						}
					});
			        ad=alertDialog.show();
				
			}
		});*/
		SalesQuoteTypeSpinner.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				SalesQuoteType.setError(null);
				SalesQuoteType.clearFocus();
				AlertDialog.Builder	 alertDialog = new AlertDialog.Builder(getActivity());
				 LayoutInflater inflater =getActivity(). getLayoutInflater();
				 convertView = (View) inflater.inflate(R.layout.activity_list, null);
				 EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
					inputSearch.setVisibility(View.GONE);
				  listDominantBrand = (ListView)convertView. findViewById(R.id.list_view);
				  
				  int index=0;
					if(hmapforQuotationtype!=null)
			    	{
						dominantbrandtList=new String[hmapforQuotationtype.size()];
						LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapforQuotationtype);
						 Set set2 = map.entrySet();
				            Iterator iterator = set2.iterator();
				            while(iterator.hasNext()) {
				            	 Map.Entry me2 = (Map.Entry)iterator.next();
				            	 /*if(index==0)
				            	 {
				            		 dominantbrandtList[index]="Select";
				            		
				            		 index=index+1;
				            		
				            		 dominantbrandtList[index]=me2.getKey().toString().trim();
				                	
				                	 index=index+1;
				            	 }
				            	 else
				            	 {*/
				            		 dominantbrandtList[index]=me2.getKey().toString().trim();
				            	 
				            	 index=index+1;
				            	// }
				                 
				            }
						
						
			    	}
				  
				  
				  
				  adapterDominantBrand = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.product_name, dominantbrandtList);
				  listDominantBrand.setAdapter(adapterDominantBrand);
				  alertDialog.setView(convertView);
			        alertDialog.setTitle("Sales Quote Type");
			        listDominantBrand.setOnItemClickListener(new OnItemClickListener() 
			        {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) 
						{
							String abc=listDominantBrand.getItemAtPosition(arg2).toString().trim();
							SalesQuoteTypeSpinner.setText(abc);
							ad.dismiss();
							if(abc.equals("Select"))
							{
								SalesQuoteTypeSpinner.setText("Select");
								//CalenderValFrom.setVisibility(View.VISIBLE);
								CalenderValFrom.setEnabled(true);
								/*ValidityFrom.setVisibility(View.VISIBLE);
								view6.setVisibility(View.VISIBLE);*/
							}
							else
							{
								if(hmapforQuotationtype.get(abc).equals("1"))
								{
									CalenderValFrom.setEnabled(false);
									/*CalenderValFrom.setVisibility(View.GONE);
									ValFrom.setVisibility(View.GONE);
									ValidityFrom.setVisibility(View.GONE);
									view6.setVisibility(View.GONE);*/
									
								}
								if(hmapforQuotationtype.get(abc).equals("2"))
								{
									CalenderValFrom.setEnabled(true);
									/*CalenderValFrom.setVisibility(View.VISIBLE);
									ValFrom.setVisibility(View.VISIBLE);
									ValidityFrom.setVisibility(View.VISIBLE);
									view6.setVisibility(View.VISIBLE);*/
									
								}
							}
							
							
							
						}
					});
			        ad=alertDialog.show();
				
			}
		});

	}
	public void calendarInitialization() 
	{
		CalenderValFrom.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0) 
			{
				   ValFrom.setError(null);
				   ValFrom.clearFocus();
				   fromDateBool=true;
				// frmDate = (TextView) arg0;
				   calendar = Calendar.getInstance();
				   Year = calendar.get(Calendar.YEAR) ;
			       Month = calendar.get(Calendar.MONTH);
			       Day = calendar.get(Calendar.DAY_OF_MONTH);
			       datePickerDialog = DatePickerDialog.newInstance(FragmentA_first.this, Year, Month, Day);

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
				
				
			}
		});
		
		CalenderValTo.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View arg0) 
			{
				   ValTo.setError(null);
				   ValTo.clearFocus();
				   toDateBool=true;
				// frmDate = (TextView) arg0;
				   calendar = Calendar.getInstance();
				   Year = calendar.get(Calendar.YEAR) ;
			       Month = calendar.get(Calendar.MONTH);
			       Day = calendar.get(Calendar.DAY_OF_MONTH);
			       datePickerDialog = DatePickerDialog.newInstance(FragmentA_first.this, Year, Month, Day);

			       datePickerDialog.setThemeDark(false);

			       datePickerDialog.showYearPickerFirst(false);

			       datePickerDialog.setAccentColor(Color.parseColor("#544f88"));
			       Calendar calendarForSetDate = Calendar.getInstance();
			       calendarForSetDate.setTimeInMillis(System.currentTimeMillis());
			       
			      // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
			       //YOU can set min or max date using this code
			     // datePickerDialog.setMaxDate(Calendar.getInstance());
			      // datePickerDialog.setMinDate(calendar);
			      /* String dtFrom=	 ValFrom.getText().toString().trim();
			       if(dtFrom.contains("-")){
			    	   String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
			    		int	DayDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[0]);
			    		int monthDTFRm=getArrayIndex(MONTHS, dtFrom.split(Pattern.quote("-"))[1]);
			    		int	yearDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[2]);
			    		calendarForSetDate.set(yearDTFRm, monthDTFRm, DayDTFRm);
			       }*/
			       datePickerDialog.setMinDate(calendarForSetDate);
			       datePickerDialog.setTitle("SELECT DATE UPTO");
			       datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
			}
		});

	}
	public void savingAllData() 
	{
		Done_btn.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View arg0)
			{
				if(validate())
				{
					
					Done_btn.setEnabled(false);

					/* AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
					 alertDialog.setTitle("Information");
				       
				        alertDialog.setCancelable(false);
				     alertDialog.setMessage("Do you want to submit data ");
				     alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
				            	dialog.dismiss();
				            	*/
				            	saveDataToDataBase();
				            	
				            	
				            	
				       /*     	
				           
				            }
				        });
				     alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog,int which) {
				            	dialog.dismiss();*/
				            	Done_btn.setEnabled(true);
				         /*   }
				        });*/
				 
				        // Showing Alert Message
				       // alertDialog.show();
					
					
					
				
				}
				
				
			}
		});

	}
	@Override
	public void onDateSet(DatePickerDialog view, int year, int monthOfYear,
			int dayOfMonth) {
		 String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		 String mon=MONTHS[monthOfYear];
		 if(fromDateBool){
			 ValFrom.setText(dayOfMonth+"-"+mon+"-"+year);
			 ValTo.setText("");
		 }
		 if(toDateBool){
	String dtFrom=	 ValFrom.getText().toString().trim();
	 ValTo.setText(dayOfMonth+"-"+mon+"-"+year);
	if(dtFrom.contains("-")){
	int	DayDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[0]);
	int monthDTFRm=getArrayIndex(MONTHS, dtFrom.split(Pattern.quote("-"))[1]);
	int	yearDTFRm=Integer.parseInt(dtFrom.split(Pattern.quote("-"))[2]);
	Calendar calDTFRM=Calendar.getInstance();
	calDTFRM.set(yearDTFRm, monthDTFRm, DayDTFRm);
	Calendar calDTTO=Calendar.getInstance();
	calDTTO.set(year, monthOfYear, dayOfMonth);
	
	if(calDTFRM.getTimeInMillis()>calDTTO.getTimeInMillis()){
		Toast.makeText(getActivity(), "DateTo is less than DateFrom ", Toast.LENGTH_SHORT).show();
		 ValTo.setText("");
	}
	
			
	
	}
			
			 
		 }
		 toDateBool=false;
		 fromDateBool=false;
		
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
	public void saveDataToDataBase() 
	{
		
		getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		String QuatNameString="0";
		String CustNameString="0";
		String ContactPerString="0";
		String ContactPerMobString="0";
		String ContactPerEmailString="0";
		String ExpBusValueString="0";
		String PaymntTermEditString="0";  //CreditLimit
		String SalesQuoteTypeString="0";
		String PaymntTermSpinnerString="0"; //CREDITdays
		String ValToString="0";
		String ValFromString="0";
		
		String storeNameString="0";
		String SalesQuoteIdString="0";
		String storeIDString="0";
		String imei="0";
		String	SalesQuoteCode="0";
    	String PAYMENT_STAGEID_Values="0";
    	String PaymentModeId="0";
    	String SalesQuotePrcsId="0";
    	String SalesQuotePrcs="0";
    	retrevingValueOfcheckBox();
    	
    	/*PaymentModeId=idOfCheckBoxes;
    	if(!paymentStageidGlobal.equals("NULL")){
    		PAYMENT_STAGEID=paymentStageidGlobal;
    	}*/
    	String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
		
		String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
		String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
		
		String fullStringOfAdvnc="0";
		String perOfAd="0";
		String checkBoxOfAdvance="0";
		
		if(paymntStgAdvnc.equals("1"))
		{
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
		 
		String fullStringOfDelivery="0";
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
		 fullStringOfCredit=paymntStgCrdt+"~"+perOfCredit+"~"+creditDaysOfDelvry+"~"+creditLimitOfCreditString+"~"+checkBoxOfCredit;
		
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
    	
    	
	 QuatNameString= 	QuatNameEdit.getText().toString().trim();
	 CustNameString= 	CustNameEdit.getText().toString().trim();
	 ContactPerString= 	ContactPerEdit.getText().toString().trim();
	ContactPerMobString= 	ContactPerMobEdit.getText().toString().trim();
	
	
	if(!ContactPerEmailEdit.getText().toString().trim().equals(""))
	{
		ContactPerEmailString= 	ContactPerEmailEdit.getText().toString().trim();
	}
	
	
	//ExpBusValueString= 	ExpBusValueEdit.getText().toString().trim();
	
	/*if(paymentStageidGlobal.equals("3")){
		PaymntTermEditString= 	PaymntTermEdit.getText().toString().trim();  //CreditLimit
		PaymntTermSpinnerString= 	PaymntTermSpinner.getText().toString().trim(); //CREDITdays
	}
	
	SalesQuoteTypeString= 	SalesQuoteTypeSpinner.getText().toString().trim();
	
	if(paymentStageidGlobal.equals("2")){
		PaymntTermSpinnerString= 	PaymntTermSpinner.getText().toString().trim(); //CREDITdays
	}*/
	SalesQuoteTypeString= hmapforQuotationtype.get(SalesQuoteTypeSpinner.getText().toString().trim());
	
	 ValToString= 	ValTo.getText().toString().trim();
	if( ValFrom.getVisibility() == View.VISIBLE)
	{
		 ValFromString= 	ValFrom.getText().toString().trim();
		
	}
	
	
	 storeNameString=	QuotationActivity.selStoreName;
	 SalesQuoteIdString=	QuotationActivity.SalesQuoteId;
	 storeIDString=	QuotationActivity.storeID;
	imei=	QuotationActivity.imei;
	
	if(quatationFlag.equals("NEW"))
	{
	
		long tmInMillis=    System.currentTimeMillis();
	     String strLong = Long.toString(tmInMillis);
	     String new_word = imei.substring(imei.length() - 4);
	     
	     if(CommonInfo.newQuottionID.equals("NULL"))
	     {
	    	 uniqueId=strLong+"~"+new_word;
	    	 CommonInfo.newQuottionID=uniqueId;
	     }
	     else
	     {
	    	 uniqueId=CommonInfo.newQuottionID;
	    	 
	     }
	    
	     SalesQuotePrcsId="1";
	   //  SalesQuotePrcs="Newly Created";
	     SalesQuotePrcs=	hmapPrcDetailsFromDataBase.get("1");
	}
	if(quatationFlag.equals("UPDATE"))
	{
		
		/*if(SalesQuotePrcsFromHmap.equals("Newly Created")){
			SalesQuotePrcsId="1";
		     SalesQuotePrcs="Newly Created";
		}*/
		if(SalesQuotePrcsIdFromHmap.equals("1"))
		{
			SalesQuotePrcs=	hmapPrcDetailsFromDataBase.get("1");
			SalesQuotePrcsId="1";
		   //  SalesQuotePrcs="Newly Created";
			
		}
		
		if(SalesQuotePrcsIdFromHmap.equals("2"))
		{
			SalesQuotePrcs=	hmapPrcDetailsFromDataBase.get("3");
			SalesQuotePrcsId="3";
		    // SalesQuotePrcs="Newly Created";
			
		}
		if(SalesQuotePrcsIdFromHmap.equals("3"))
		{
			SalesQuotePrcs=	hmapPrcDetailsFromDataBase.get("3");
			SalesQuotePrcsId="3";
		    // SalesQuotePrcs="Newly Created";
			
		}
		if(SalesQuotePrcsIdFromHmap.equals("4"))
		{
			SalesQuotePrcs=	hmapPrcDetailsFromDataBase.get("4");
			SalesQuotePrcsId="4";
		    // SalesQuotePrcs="Newly Created";
			
		}
		if(SalesQuotePrcsIdFromHmap.equals("5"))
		{
			SalesQuotePrcs=	hmapPrcDetailsFromDataBase.get("5");
			SalesQuotePrcsId="5";
		    // SalesQuotePrcs="Newly Created";
			
		}
		
	     
	     uniqueId=SalesQuoteIdString;
		}
	SalesQuoteCode=uniqueId;
	PAYMENT_STAGEID_Values=	dbengine.fnGetDefaultStoreOrderPAymentDetails(storeIDString);
	dbengine.open();
	dbengine.deleteSalesQuotePersonMeetMstr(uniqueId);
	 dbengine.saveSalesQuotePersonMeetMstr(uniqueId, SalesQuoteCode, SalesQuotePrcsId, SalesQuotePrcs, storeNameString, "0",
			 storeIDString, PaymntTermEditString, PaymntTermSpinnerString, ExpBusValueString, ValFromString, ValToString, 
			 CurrentDate, SalesQuoteTypeString, ContactPerString, ContactPerEmailString, ContactPerMobString,PaymentModeId,
			 PAYMENT_STAGEID_Values,ManufacturerID,ManufacturerName);
	 
	
	CommonInfo.SalesQuoteId=uniqueId;
	QuotationActivity.SalesQuoteId=uniqueId;
	 dbengine.close();
	
	 getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
	 Done_btn.setEnabled(true);
	 
	 viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		viewPager.setCurrentItem(1);
		
	/* AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(getActivity());
		alertDialogNoConn.setTitle("Information");
		alertDialogNoConn.setMessage("Data Saved Successfully");
		//alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
		alertDialogNoConn.setNeutralButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) 
					{
              dialog.dismiss();
              if(isMyServiceRunning())
        		{
              stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
        		}
              finish();
              //finish();
              
              FragmentTransaction ft = getFragmentManager().beginTransaction();
              ft.show(new FragmentA());
               ft.commit();
              
             // FragmentTransaction ft =getFragmentManager();
              		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
              		viewPager.setCurrentItem(1);
					}
				});
		alertDialogNoConn.setIcon(R.drawable.info_ico);
		AlertDialog alert = alertDialogNoConn.create();
		alert.show();*/
	
		

	}
	public boolean validatePercentage()
	{
		int prcntgOfAd=0;
		int prcntgOfDelvry=0;
		int prcntgOfCrdt=0;
		int totalPercentage=0;
		String paymntStgAdvnc=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0];
		if(paymntStgAdvnc.equals("1"))
		{
			if(!percentageOfAdvanceBeforeDelivery.getText().toString().trim().equals(""))
			{
				prcntgOfAd=Integer.parseInt(percentageOfAdvanceBeforeDelivery.getText().toString().trim());
			}
			 
			
		}
		
		
		String paymntStgDelvry=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1];
          if(paymntStgDelvry.equals("2"))
          {
        	  if(!percentageOfOnDelivery.getText().toString().trim().equals(""))
        	  {
        	    prcntgOfDelvry=Integer.parseInt(percentageOfOnDelivery.getText().toString().trim());
        	  }
			
		}
		String paymntStgCrdt=globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2];
           if(paymntStgCrdt.equals("3"))
           {
        	   if(!PercentageOfCredit.getText().toString().trim().equals(""))
        	   {
        	      prcntgOfCrdt=Integer.parseInt(PercentageOfCredit.getText().toString().trim());
        	   }
			
		}
           totalPercentage=prcntgOfAd+prcntgOfDelvry+prcntgOfCrdt;
           if(totalPercentage==100)
           {
        	   return true;
           }
           else
           {
        	   return false;
           }
           

	}
	
	public boolean validateCheckBox() 
	{
		boolean ckeckbox=false;
		int count = parentOfCheckBox.getChildCount();
		
		 for (int ui=0;ui<count;ui++) 
		 {
			 View ch = parentOfCheckBox.getChildAt(ui);
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
	public boolean validatecheckboxofAdvance() 
	{

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
	
	public boolean validatecheckboxofDelivery() 
	{

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
	public boolean validatecheckboxofCredit() 
	{

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
	public boolean validate()
	{
		String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
		String mail=ContactPerEmailEdit.getText().toString().trim();
		/*if(QuatNameEdit.getText().toString().trim().equals("")){
			
			
			QuatNameEdit.clearFocus();
			QuatNameEdit.requestFocus();
			QuatNameEdit.setError("Quat Name is empty");
			QuatNameEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					QuatNameEdit.setError(null);
					
				}
			});
			return false;
		}*/
		if(CustNameEdit.getText().toString().trim().equals(""))
		{
			CustNameEdit.clearFocus();
			CustNameEdit.requestFocus();
			CustNameEdit.setError("Customer Name is empty");
			CustNameEdit.setOnClickListener(new OnClickListener()
			       {
						
						@Override
						public void onClick(View arg0)
						{
							CustNameEdit.setError(null);
							
						}
					});
					return false;
		}
		else if(ContactPerEdit.getText().toString().trim().equals(""))
		{
	            ContactPerEdit.clearFocus();
				ContactPerEdit.requestFocus();
				ContactPerEdit.setError("Contact Person is empty");
				ContactPerEdit.setOnClickListener(new OnClickListener() 
				{
							
							@Override
							public void onClick(View arg0)
							{
								ContactPerEdit.setError(null);
								
							}
						});
						return false;
		}
     else if(ContactPerMobEdit.getText().toString().trim().equals("0000000000") || ContactPerMobEdit.getText().toString().trim().equals("") || ContactPerMobEdit.getText().toString().trim().length()<10)
     {
	            ContactPerMobEdit.clearFocus();
				ContactPerMobEdit.requestFocus();
				ContactPerMobEdit.setError("Contact no. is not valid");
				ContactPerMobEdit.setOnClickListener(new OnClickListener()
				{
							
							@Override
							public void onClick(View arg0) 
							{
								ContactPerMobEdit.setError(null);
								
							}
						});
						return false;
		}
      else if(!ContactPerEmailEdit.getText().toString().trim().equals("") && !mail.matches(emailPattern))
      {
    	  		ContactPerEmailEdit.clearFocus();
				ContactPerEmailEdit.requestFocus();
				ContactPerEmailEdit.setError("EmailID is not valid");
				ContactPerEmailEdit.setOnClickListener(new OnClickListener()
				{
							
							@Override
							public void onClick(View arg0)
							{
								ContactPerEmailEdit.setError(null);
								
							}
						});
						return false;
		}
      else if(SalesQuoteTypeSpinner.getText().toString().trim().equals("Select"))
      {
	        SalesQuoteType.clearFocus();
			SalesQuoteType.requestFocus();
			SalesQuoteType.setError("Select Value from Sales Quote Type");
			return false;
      }
      else if(ValFrom.getText().toString().trim().equals("") && ValFrom.getVisibility() == View.VISIBLE)
      {
	            ValFrom.clearFocus();
				ValFrom.requestFocus();
				ValFrom.setError("Select From_Date from Calendar");
				return false;
      }
      else if(ValTo.getText().toString().trim().equals(""))
      {
				ValTo.clearFocus();
				ValTo.requestFocus();
				ValTo.setError("Select Valid_UpTO_Date from Calendar");
				return false;
      }
// comment validation of payment section because it not needed now
// start here		
/*else if(ExpBusValueEdit.getText().toString().trim().equals("") ){
	
	
	ExpBusValueEdit.clearFocus();
	ExpBusValueEdit.requestFocus();
	ExpBusValueEdit.setError("Expected Business Value is empty");
	ExpBusValueEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					ExpBusValueEdit.setError(null);
					
				}
			});
			return false;
		}*/
		
		
		
/*else if(globalValueOfPaymentStageCheck.equals("0"+"_"+"0"+"_"+"0")){
	
	paymentstagetextviewNew.clearFocus();
	paymentstagetextviewNew.requestFocus();
	paymentstagetextviewNew.setError("Please Select Payment Stage");  
	return false;
	
}
else if(!validatePercentage()){
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
		
else if(!validatecheckboxofAdvance() && globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[0].equals("1")){
	paymentModeTextviewNew.clearFocus();
	paymentModeTextviewNew.requestFocus();
	paymentModeTextviewNew.setError("Select Payment Mode");
	
	return false;
	
	
}
else if(!validatecheckboxofDelivery() && globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[1].equals("2")){
	paymentModeTextviewNew.clearFocus();
	paymentModeTextviewNew.requestFocus();
	paymentModeTextviewNew.setError("Select Payment Mode");
	
	return false;
	
	
}
else if(!validatecheckboxofCredit() && globalValueOfPaymentStageCheck.split(Pattern.quote("_"))[2].equals("3")){
	paymentModeTextviewNew.clearFocus();
	paymentModeTextviewNew.requestFocus();
	paymentModeTextviewNew.setError("Select Payment Mode");
	
	return false;
	
	
}
else if(creditDaysOfOnDelivery.isEnabled() && creditDaysOfOnDelivery.getText().toString().trim().equals("")){
	
	creditdaysTextboxNew.clearFocus();
	creditdaysTextboxNew.requestFocus();
	creditdaysTextboxNew.setError("Credit days is empty");
	return false;
}
else if(CreditCheckBoxNew.isChecked() && creditDaysOfCredit.getText().toString().trim().equals("")){
	
	creditdaysTextboxNew.clearFocus();
	creditdaysTextboxNew.requestFocus();
	creditdaysTextboxNew.setError("Credit days is empty");
	return false;
}
else if(CreditCheckBoxNew.isChecked() && creditLimitOfCredit.getText().toString().trim().equals("")){
	
	CreditlimitTextboxNew.clearFocus();
	CreditlimitTextboxNew.requestFocus();
	CreditlimitTextboxNew.setError("Credit limit is empty");
	return false;
}
		
else if(creditDaysOfOnDelivery.isEnabled() && creditDaysOfOnDelivery.getText().toString().trim().equals("0")){
	creditDaysOfOnDelivery.clearFocus();
	creditdaysTextboxNew.clearFocus();
	creditdaysTextboxNew.requestFocus();
	creditdaysTextboxNew.setError("Credit days can not be zero.");
	return false;
}
else if(CreditCheckBoxNew.isChecked() && creditDaysOfCredit.getText().toString().trim().equals("0")){
	creditDaysOfCredit.clearFocus();
	creditdaysTextboxNew.clearFocus();
	creditdaysTextboxNew.requestFocus();
	creditdaysTextboxNew.setError("Credit days can not be zero.");
	return false;
}
else if(CreditCheckBoxNew.isChecked() && creditLimitOfCredit.getText().toString().trim().equals("0")){
	creditLimitOfCredit.clearFocus();
	CreditlimitTextboxNew.clearFocus();
	CreditlimitTextboxNew.requestFocus();
	CreditlimitTextboxNew.setError("Credit limit can not be zero.");
	return false;
}*/
		
		
		
		
		
/*else if(!validateCheckBox()){
	
	PaymentModeTextView.clearFocus();
	PaymentModeTextView.requestFocus();
	PaymentModeTextView.setError("Select Payment Mode");
	
	return false;
	
}
else if(PaymntTermEdit.getText().toString().trim().equals("") && PaymntTermEdit.getVisibility() == View.VISIBLE){
	
	
	PaymntTermEdit.clearFocus();
	PaymntTermEdit.requestFocus();
	PaymntTermEdit.setError("CreditLimit is empty");
	PaymntTermEdit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					PaymntTermEdit.setError(null);
					
				}
			});
			return false;
		}

else if(PaymntTermSpinner.getText().toString().trim().equals("") && PaymntTermSpinner.getVisibility() == View.VISIBLE){
	
	
	PaymntTermSpinner.clearFocus();
	PaymntTermSpinner.requestFocus();
	PaymntTermSpinner.setError("CreditDays is empty");
	PaymntTermSpinner.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					PaymntTermSpinner.setError(null);
					
				}
			});
			return false;
	
	
	
}
*/
		//End here
		


		
		else{
			return true;
		}

	}

	@Override
	public void onFocusChange(View arg0, boolean arg1) {
		
		percentageTextviewNew.setError(null);
		percentageTextviewNew.clearFocus();
		percentageTextviewNew.setError(null);
		percentageTextviewNew.clearFocus();
		creditdaysTextboxNew.setError(null);
		creditdaysTextboxNew.clearFocus();
		percentageTextviewNew.setError(null);
		percentageTextviewNew.clearFocus();
		creditdaysTextboxNew.setError(null);
		creditdaysTextboxNew.clearFocus();
		CreditlimitTextboxNew.setError(null);
		CreditlimitTextboxNew.clearFocus();
	}

}

