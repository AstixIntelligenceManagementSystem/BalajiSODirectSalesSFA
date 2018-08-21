package project.astix.com.balajisosfadirectsales;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;


import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.Fragment;

import com.astix.Common.CommonInfo;

public class StoreWiseFragmentThreeTab<Context> extends Fragment 
{
	
	public String imei;
	public String fDate;
	public SimpleDateFormat sdf;
	PRJDatabase dbengine;
	private Activity mContext;
	
	LinearLayout ll_Scroll_product,ll_scheme_detail;

	int count=1;
	
	int pos=0;
	
	public String[] AllDataContainer;
	public View rootView;
	
	LinkedHashMap<String, String> hmapAll_details=new LinkedHashMap<String, String>();
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
       rootView = inflater.inflate(R.layout.store_summary, container, false);
        
        mContext = getActivity();
        dbengine = new PRJDatabase(mContext);
        
        TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
		imei = tManager.getDeviceId();
		
		if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
		{
			imei = tManager.getDeviceId();
			CommonInfo.imei=imei;
		}
		else
		{
			imei=CommonInfo.imei.trim();
		}
		
		
		Date date1=new Date();
		sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
		fDate = sdf.format(date1).toString().trim();
		//fDate="29-10-2015";
		
		//'868087024619932','29-10-2015'  
		int flgReportFromTmpOrPermanent=1;
		dbengine.open();
      int check=dbengine.fnCountToDisplayDailySummaryDetailsStoreWiseLines(5,flgReportFromTmpOrPermanent);
      dbengine.close();
		System.out.println("lucky value check :"+check);
		
		if(check==0)
		{
			Toast.makeText(mContext,getResources().getString(R.string.txtNoStoreToDisplay), Toast.LENGTH_SHORT).show();
			
		}
		else
		{
			//dbengine.open();
			hmapAll_details=dbengine.fnGetDailySummaryDetailsStoreWise(5,flgReportFromTmpOrPermanent);
			//dbengine.close();
            intializeFields();
		}
		
		
        return rootView;
    }
    
	
	
	private void intializeFields() 
	{
		if(hmapAll_details.size()>0)
		{
			String abcd=hmapAll_details.get("0");
		
		 StringTokenizer tokens = new StringTokenizer(String.valueOf(abcd), "^");
         
         String a1 = tokens.nextToken().toString().trim();
         String a2 = tokens.nextToken().toString().trim();
         String a3 = tokens.nextToken().toString().trim();
         String a4 = tokens.nextToken().toString().trim();
         String a5 = tokens.nextToken().toString().trim();
         String a6 = tokens.nextToken().toString().trim();
         String a7 = tokens.nextToken().toString().trim();
        // String a8 = tokens.nextToken().toString().trim();
        // String a9 = tokens.nextToken().toString().trim();
         
         
        // TOTAL :^4^0^0^151.218^4.43^155.648
        
     	/*initialValues.put("AutoId", AutoId);
			initialValues.put("Store", Store.trim());
			initialValues.put("LinesperBill", LinesperBill.trim());
			initialValues.put("StockValue", StockValue.trim());
			initialValues.put("DiscValue", DiscValue.trim());
			initialValues.put("ValBeforeTax", ValBeforeTax.trim());
			initialValues.put("TaxValue", TaxValue.trim());
			initialValues.put("ValAfterTax", ValAfterTax.trim());
			initialValues.put("Lvl", Lvl.trim());*/
        
        
		
		TextView total_LinesPerBill=(TextView)rootView.findViewById(R.id.total_LinesPerBill);
		total_LinesPerBill.setText(a2);
		
		TextView total_StockValue=(TextView)rootView.findViewById(R.id.total_StockValue);
		Double StockValue_val=Double.parseDouble(a3);
		StockValue_val= Double.parseDouble(new DecimalFormat("##.##").format(StockValue_val));
		total_StockValue.setText(""+StockValue_val);
		
		
		TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
		Double disc_val=Double.parseDouble(a4);
		disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
		total_discountValue.setText(""+disc_val.intValue());
		
		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
		Double ValBeforeTax=Double.parseDouble(a5);
		ValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
		
		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
		Double ValTax=Double.parseDouble(a6);
		ValTax=Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
		total_ValTax.setText(""+ValTax.intValue());
		
		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
		Double ValAfterTax=Double.parseDouble(a7);
		ValAfterTax=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
		total_ValAfterTax.setText(""+ValAfterTax.intValue());
		
		hmapAll_details.remove("0");
 		
 		AllDataContainer=changeHmapToArrayValue(hmapAll_details);
 		}
		
		
		ll_Scroll_product=(LinearLayout) rootView.findViewById(R.id.ll_Scroll_product);
		ll_Scroll_product.removeAllViews();
		if(hmapAll_details.size()>0)
		{
		
		for(int i=0;i<AllDataContainer.length;i++)
		{
		
			 StringTokenizer tokens1 = new StringTokenizer(String.valueOf(AllDataContainer[i]), "^");
	         
	         String s1 = tokens1.nextToken().toString().trim();
	         String s2 = tokens1.nextToken().toString().trim();
	         String s3 = tokens1.nextToken().toString().trim();
	         String s4 = tokens1.nextToken().toString().trim();
	         String s5 = tokens1.nextToken().toString().trim();
	         String s6 = tokens1.nextToken().toString().trim();
	         String s7 = tokens1.nextToken().toString().trim();
	        // String s8 = tokens1.nextToken().toString().trim();
	        // String s9 = tokens1.nextToken().toString().trim();
	        /* String s10 = tokens1.nextToken().toString().trim();
	         String s11 = tokens1.nextToken().toString().trim();
	         String s12 = tokens1.nextToken().toString().trim();
	         String s13 = tokens1.nextToken().toString().trim();
	         String s14 = tokens1.nextToken().toString().trim();
	         String s15 = tokens1.nextToken().toString().trim();*/
	         
	         LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
				final View view=inflater.inflate(R.layout.list_store_wise,null);
				if(i%2==0)
				{
					view.setBackgroundResource(R.drawable.card_background_summary);
				}
				view.setTag(count);
			
				
				
				/*initialValues.put("AutoId", AutoId);
				initialValues.put("Store", Store.trim());
				initialValues.put("LinesperBill", LinesperBill.trim());
				initialValues.put("StockValue", StockValue.trim());
				initialValues.put("DiscValue", DiscValue.trim());
				initialValues.put("ValBeforeTax", ValBeforeTax.trim());
				initialValues.put("TaxValue", TaxValue.trim());
				initialValues.put("ValAfterTax", ValAfterTax.trim());
				initialValues.put("Lvl", Lvl.trim());*/
				
				 // TOTAL :^4^0^0^151.218^4.43^155.648
				
				
			
				TextView tv_product_name=(TextView) view.findViewById(R.id.txt_store);
				tv_product_name.setText(s1);
				
				TextView txt_lines_bill=(TextView) view.findViewById(R.id.txt_lines_bill);
				txt_lines_bill.setText(s2);
				
				TextView txt_stock=(TextView) view.findViewById(R.id.txt_stock);
				txt_stock.setText(s3);
				
				
				TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				Double disc_val=Double.parseDouble(s4);
				disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
				txt_discnt_val.setText(""+disc_val.intValue());
				
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				Double beforeTax_val=Double.parseDouble(s5);
				beforeTax_val= Double.parseDouble(new DecimalFormat("##.##").format(beforeTax_val));
				txt_gross_val.setText(""+beforeTax_val.intValue());
				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				Double Tax_val=Double.parseDouble(s6);
				Tax_val= Double.parseDouble(new DecimalFormat("##.##").format(Tax_val));
				txt_tac_val.setText(""+Tax_val.intValue());
			
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				Double AfterTax_val=Double.parseDouble(s7);
				AfterTax_val= Double.parseDouble(new DecimalFormat("##.##").format(AfterTax_val));
				txt_net_val.setText(""+AfterTax_val.intValue());
				
				
				
				
			ll_Scroll_product.addView(view);
				
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
	
	public String[] changeHmapToArrayValue(LinkedHashMap hmap)
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
			
	}
 



