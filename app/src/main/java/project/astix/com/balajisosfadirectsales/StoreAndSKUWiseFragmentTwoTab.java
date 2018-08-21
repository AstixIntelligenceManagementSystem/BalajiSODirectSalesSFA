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

public class StoreAndSKUWiseFragmentTwoTab<Context> extends Fragment 
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
       rootView = inflater.inflate(R.layout.store_and_sku_wise, container, false);
        
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
		
		//  dbengine.open();
		int flgReportFromTmpOrPermanent=0;
	      int check=dbengine.fnCountToDisplayDailySummaryDetailsStoreSKUWise(1,flgReportFromTmpOrPermanent);
	     // dbengine.close();
			System.out.println("lucky value check :"+check);
			
			if(check==0)
			{
				Toast.makeText(mContext, getResources().getString(R.string.txtNoRecord), Toast.LENGTH_SHORT).show();
				
			}
			else
			{
				//dbengine.open();
				hmapAll_details=dbengine.fnGetDailySummaryDetailsStoreSKUWiseLevel(1,flgReportFromTmpOrPermanent);
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
         String a8 = tokens.nextToken().toString().trim();
         String a9 = tokens.nextToken().toString().trim();
         String a10 = tokens.nextToken().toString().trim();
         String a11 = tokens.nextToken().toString().trim();
        // String a12 = tokens.nextToken().toString().trim();
        // String a13 = tokens.nextToken().toString().trim();
        
         
       /* initialValues.put("AutoId", AutoId);
 		initialValues.put("ProductId", ProductId.trim());
 		initialValues.put("Product", Product.trim());
 		initialValues.put("MRP", MRP.trim());
 		initialValues.put("Rate", Rate.trim());
 		initialValues.put("OrderQty", OrderQty.trim());
 		initialValues.put("FreeQty", FreeQty.trim());
 		initialValues.put("DiscValue", DiscValue.trim());
 		initialValues.put("ValBeforeTax", ValBeforeTax.trim());
 		initialValues.put("TaxValue", TaxValue.trim());
 		initialValues.put("ValAfterTax", ValAfterTax.trim());
 		initialValues.put("Lvl", Lvl.trim());
 		initialValues.put("StoreId", StoreId.trim());*/
     	
		
		/*TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
		total_discountValue.setText(a7);
		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
		total_ValBeforeTax.setText(a8);
		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
		total_ValTax.setText(a9);
		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
		total_ValAfterTax.setText(a10);*/
         
         
         /*TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
 		total_discountValue.setText(a7);*/
 		
 		
 		TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
 		Double disc_val=Double.parseDouble(a7);
 		disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
 		total_discountValue.setText(""+disc_val.intValue());
 		
 		
 		/*TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
 		total_ValBeforeTax.setText(a8);
 		*/
 		
 		
 		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
 		Double ValBeforeTax=Double.parseDouble(a8);
 		ValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
 		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
 		
 		
 		
 		/*TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
 		total_ValTax.setText(a9);*/
 		
 		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
 		Double ValTax=Double.parseDouble(a9);
 		ValTax=Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
 		total_ValTax.setText(""+ValTax.intValue());
 		
 		
 		
 		
 		/*TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
 		total_ValAfterTax.setText(a10);*/
 		
 		
 		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
 		Double ValAfterTax=Double.parseDouble(a10);
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
	         String s8 = tokens1.nextToken().toString().trim();
	         String s9 = tokens1.nextToken().toString().trim();
	         String s10 = tokens1.nextToken().toString().trim();
	         String s11 = tokens1.nextToken().toString().trim();
	        // String s12 = tokens1.nextToken().toString().trim();
	        
	         
	     
	         
	         
	        LayoutInflater inflaterParent=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
			final View viewParent=inflaterParent.inflate(R.layout.list_store_sku_header_wise,null);
				
           LinearLayout ll_store_sku=(LinearLayout) viewParent.findViewById(R.id.ll_store_sku);
	         
	         if(Integer.parseInt(s11)==1)
	         {
	        	
				
				 TextView txt_store_sku=(TextView) viewParent.findViewById(R.id.txt_store_sku);
				 TextView txt_store_sku_discnt_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_discnt_val);
				 TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
				 TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
				 TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
				
				
				
				txt_store_sku.setText(s1);
				
				
				Double disc_val=Double.parseDouble(s7);
				disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
				txt_store_sku_discnt_val.setText(""+disc_val.intValue());
				
				
				Double ValBeforeTax1=Double.parseDouble(s8);
				ValBeforeTax1=Math.round(ValBeforeTax1 * 100.0)/100.0;
				txt_store_sku_gross_val.setText(""+ValBeforeTax1.intValue());
				
				
				Double ValTax1=Double.parseDouble(s9);
		 		ValTax1=Math.round(ValTax1 * 100.0)/100.0;
				txt_store_sku_tac_val.setText(""+ValTax1.intValue());
				
				
				Double ValAfterTax1=Double.parseDouble(s10);
		 		ValAfterTax1=Math.round(ValAfterTax1 * 100.0)/100.0;
				txt_store_sku_net_val.setText(""+ValAfterTax1.intValue());
				
				
	         }
	         else if(Integer.parseInt(s11)==2)
	         {
	        	 TextView txt_store_sku=(TextView) viewParent.findViewById(R.id.txt_store_sku);
				 TextView txt_store_sku_discnt_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_discnt_val);
				 TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
				 TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
				 TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
				
				 txt_store_sku.setVisibility(View.GONE);
				 txt_store_sku_discnt_val.setVisibility(View.GONE);
				 txt_store_sku_gross_val.setVisibility(View.GONE);
				 txt_store_sku_tac_val.setVisibility(View.GONE);
				 txt_store_sku_net_val.setVisibility(View.GONE);
					
	        	 LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
	 			 final View view=inflater.inflate(R.layout.list_store_sku_wise,null);
	 			
	 			 /* initialValues.put("AutoId", AutoId);
		      		initialValues.put("ProductId", ProductId.trim());
		      		initialValues.put("Product", Product.trim());
		      		initialValues.put("MRP", MRP.trim());
		      		initialValues.put("Rate", Rate.trim());
		      		initialValues.put("OrderQty", OrderQty.trim());
		      		initialValues.put("FreeQty", FreeQty.trim());
		      		initialValues.put("DiscValue", DiscValue.trim());
		      		initialValues.put("ValBeforeTax", ValBeforeTax.trim());
		      		initialValues.put("TaxValue", TaxValue.trim());
		      		initialValues.put("ValAfterTax", ValAfterTax.trim());
		      		initialValues.put("Lvl", Lvl.trim());
		      		initialValues.put("StoreId", StoreId.trim());*/
				
	 			TextView tv_product_name=(TextView) view.findViewById(R.id.txt_prdct);
				tv_product_name.setText(s1);
				
				TextView txt_mrp=(TextView) view.findViewById(R.id.txt_mrp);
				
				Double prd_mrp=Double.parseDouble(s2);
				prd_mrp= Double.parseDouble(new DecimalFormat("##.##").format(prd_mrp));
				txt_mrp.setText(""+prd_mrp);
				//txt_mrp.setText(s2);
				
				TextView txt_rate=(TextView) view.findViewById(R.id.txt_rate);
				
				
				txt_rate.setText(s3);
				
				TextView txt_stock=(TextView) view.findViewById(R.id.txt_stock);
				txt_stock.setText(s4);
				
				TextView txt_order=(TextView) view.findViewById(R.id.txt_order);
				txt_order.setText(s5);
				
				TextView txt_free=(TextView) view.findViewById(R.id.txt_free);
				txt_free.setText(s6);
				
				
				/*TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				txt_discnt_val.setText(s7);
				
				
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				txt_gross_val.setText(s8);
				
				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				txt_tac_val.setText(s9);
				
				
				
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				txt_net_val.setText(s10);*/
				
				/*TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				txt_discnt_val.setText(s7);*/
				
				TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				Double disc_val1=Double.parseDouble(s7);
				disc_val1= Double.parseDouble(new DecimalFormat("##.##").format(disc_val1));
				txt_discnt_val.setText(""+disc_val1.intValue());
				
				
				
				/*TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				txt_gross_val.setText(s8);*/
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				Double ValBeforeTax1=Double.parseDouble(s8);
				ValBeforeTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
				txt_gross_val.setText(""+ValBeforeTax1.intValue());
				
				
				/*TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				txt_tac_val.setText(s9);*/
				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				Double ValTax1=Double.parseDouble(s9);
		 		ValTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
				txt_tac_val.setText(""+ValTax1.intValue());
				
				
				
				/*TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				txt_net_val.setText(s10);*/
				
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				Double ValAfterTax1=Double.parseDouble(s10);
		 		ValAfterTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
				txt_net_val.setText(""+ValAfterTax1.intValue());
				
				
				
				ll_store_sku.addView(view);
	         }
	         ll_Scroll_product.addView(viewParent);
	         
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
 
}
