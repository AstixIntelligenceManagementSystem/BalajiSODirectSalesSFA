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

public class SKUWiseFragmentTwoTab<Context> extends Fragment 
{
	
	public String imei;
	public String fDate;
	public SimpleDateFormat sdf;
	PRJDatabase dbengine;
	private Activity mContext;
	
	LinearLayout ll_Scroll_product,ll_scheme_detail;

	int count=1;
	
	int pos=0;
	
	public String[] AllDataContainer=null;
	public View rootView;
	
	LinkedHashMap<String, String> hmapAll_details=new LinkedHashMap<String, String>();
	LinkedHashMap<String, String> hmapAll_tabelDetails=new LinkedHashMap<String, String>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
    {
       rootView = inflater.inflate(R.layout.sku_summary, container, false);
        
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
		int flgReportFromTmpOrPermanent=0;
		int check=dbengine.fnCountToDisplayDailySummaryDetailsSKUWise(1,flgReportFromTmpOrPermanent);
		
		System.out.println("lucky value check :"+check);
		
		if(check==0)
		{
			Toast.makeText(mContext,getResources().getString(R.string.txtNoRecord) , Toast.LENGTH_SHORT).show();
			
		}
		else
		{
			hmapAll_tabelDetails=dbengine.fnGetTabelEntry();
			hmapAll_details=dbengine.fnGetDailySummaryDetailsSKUWiseCategoryLevel(1,flgReportFromTmpOrPermanent);
          
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
	         
	         
	        TextView total_stores=(TextView)rootView.findViewById(R.id.total_stores);
	 		total_stores.setText(a10);
	 		
	 		TextView total_orderQty=(TextView)rootView.findViewById(R.id.total_orderQty);
	 		TextView total_freeQty=(TextView)rootView.findViewById(R.id.total_freeQty);
	 		
	 		TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
	 		Double DisValue=Double.parseDouble(a6);
	 		DisValue= Double.parseDouble(new DecimalFormat("##.##").format(DisValue));
	 		total_discountValue.setText(""+DisValue.intValue());
	 		
	 		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
	 		Double ValBeforeTax=Double.parseDouble(a7);
	 		ValBeforeTax= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
	 		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
	 		
	 		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
	 		Double ValTax=Double.parseDouble(a8);
	 		ValTax= Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
	 		total_ValTax.setText(""+ValTax.intValue());
	 		
	 		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
	 		Double ValAfterTax=Double.parseDouble(a9);
	 		ValAfterTax= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
	 		total_ValAfterTax.setText(""+ValAfterTax.intValue());
	 		//total_ValAfterTax.setText(a9);
	 		
	 		hmapAll_details.remove("0");
	 		
	 		AllDataContainer=changeHmapToArrayValue(hmapAll_details);
				}
		if(hmapAll_details.size()>0)
		{
			if(AllDataContainer.length>0)
			{
			 /*StringTokenizer tokens = new StringTokenizer(String.valueOf(AllDataContainer[0]), "^");
	         
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
	         String a11 = tokens.nextToken().toString().trim();*/
	        // String a12 = tokens.nextToken().toString().trim();
	        // String a13 = tokens.nextToken().toString().trim();
	        // String a14 = tokens.nextToken().toString().trim();
	        // String a15 = tokens.nextToken().toString().trim();
	         
	         
	        /* CATEGORY :GO Institutional Cheese^0^0^0.88 Kg^0.0 Kg^28.55^356.895^44.620000000000005^401.515^1^1
	         Go_Ch_Wed_PL_140gm^90^81.82^2^0^10.77^134.675^16.84^151.515^1^2
	        GRAND TOTAL :^0^0^0^0^28.55^356.895^44.620000000000005^401.515^2^0 */
	         
	         
	         
	         
	         
	         
	        // CATEGORY :GO Institutional Cheese^0^0^1^0.88 Kg^0.0 Kg^28.55^356.895^44.620000000000005^401.515^1
	       //  Go_Ch_Wed_PL_140gm^90^81.82^2^0^10.77^134.675^16.84^151.515^1^2
	       //  GRAND TOTAL : ^ ^ ^2^ ^ ^28.55^356.895^44.620000000000005^401.515^0
	         
	         
	      
	       //  Go_Ch_Wed_PL_140gm^90^81.82^2^0^33.06^330.581^41.32^371.901^1^2
	        
	        /* Go_Ch_Wed_PL_140gm------>Product Name
	         ^90------>Product MRP
	         ^81.82--->Standard Rate
	         ^2---->Order Qty
	         ^0----->Free Qty
	         ^33.06----->Discount Value
	         ^330.581--->Amount Before Tax
	         ^41.32----->Tax Value
	         ^371.901---->Amount After Tax
	         ^1---------->Store Count
	         ^2----->Level*/
	        
			
			/*TextView total_stores=(TextView)rootView.findViewById(R.id.total_stores);
			total_stores.setText(a10);
			TextView total_orderQty=(TextView)rootView.findViewById(R.id.total_orderQty);
			TextView total_freeQty=(TextView)rootView.findViewById(R.id.total_freeQty);
			TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
			total_discountValue.setText(a9);
			TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
			total_ValBeforeTax.setText(a7);
			TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
			total_ValTax.setText(a8);
			TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
			total_ValAfterTax.setText(a9);*/
			
			
			ll_Scroll_product=(LinearLayout) rootView.findViewById(R.id.ll_Scroll_product);
			ll_Scroll_product.removeAllViews();
			
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
		        /* String s12 = tokens1.nextToken().toString().trim();
		         String s13 = tokens1.nextToken().toString().trim();
		         String s14 = tokens1.nextToken().toString().trim();*/
		        // String s15 = tokens1.nextToken().toString().trim();
		         
		         System.out.println("Value of level :"+s11);
		         
		         
		        LayoutInflater inflaterParent=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
				final View viewParent=inflaterParent.inflate(R.layout.list_sku_wise_header,null);
					
	            LinearLayout ll_store_sku=(LinearLayout) viewParent.findViewById(R.id.ll_store_sku);
		         
		         if(Integer.parseInt(s11)==1)
		         {
		        	TextView txt_catgry_sku=(TextView) viewParent.findViewById(R.id.txt_catgry_sku);
					TextView txt_sku_stores=(TextView) viewParent.findViewById(R.id.txt_sku_stores);
					TextView txt_sku_order_qty=(TextView) viewParent.findViewById(R.id.txt_sku_order_qty);
					TextView txt_sku_free_qty=(TextView) viewParent.findViewById(R.id.txt_sku_free_qty);
					TextView txt_sku_disc_val=(TextView) viewParent.findViewById(R.id.txt_sku_disc_val);
					TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
					TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
					TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
					
					
					
					
					
					txt_catgry_sku.setText(s1);
					txt_sku_stores.setText(s10);
					txt_sku_order_qty.setText(s4);
					txt_sku_free_qty.setText(s5);
					
					Double disc_val=Double.parseDouble(s6);
					disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
					txt_sku_disc_val.setText(""+disc_val.intValue());
					
					Double ValBeforeTax=Double.parseDouble(s7);
			 		ValBeforeTax= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
			 		txt_store_sku_gross_val.setText(""+ValBeforeTax.intValue());
			 		
			 		
			 		Double ValTax=Double.parseDouble(s8);
			 		ValTax= Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
			 		txt_store_sku_tac_val.setText(""+ValTax.intValue());
			 		
			 		
			 		Double ValAfterTax=Double.parseDouble(s9);
			 		ValAfterTax= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
			 		txt_store_sku_net_val.setText(""+ValAfterTax.intValue());
					
					//txt_sku_disc_val.setText(s6);
					//txt_store_sku_gross_val.setText(s7);
					//txt_store_sku_tac_val.setText(s8);
					//txt_store_sku_net_val.setText(s9);
					
		         }
		         else if(Integer.parseInt(s11)==2)
		         {
		        	   TextView txt_catgry_sku=(TextView) viewParent.findViewById(R.id.txt_catgry_sku);
						TextView txt_sku_stores=(TextView) viewParent.findViewById(R.id.txt_sku_stores);
						TextView txt_sku_order_qty=(TextView) viewParent.findViewById(R.id.txt_sku_order_qty);
						TextView txt_sku_free_qty=(TextView) viewParent.findViewById(R.id.txt_sku_free_qty);
						TextView txt_sku_disc_val=(TextView) viewParent.findViewById(R.id.txt_sku_disc_val);
						TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
						TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
						TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
						
						txt_catgry_sku.setVisibility(View.GONE);
						txt_sku_stores.setVisibility(View.GONE);
						txt_sku_order_qty.setVisibility(View.GONE);
						txt_sku_free_qty.setVisibility(View.GONE);
						txt_sku_disc_val.setVisibility(View.GONE);
						txt_store_sku_gross_val.setVisibility(View.GONE);
						txt_store_sku_tac_val.setVisibility(View.GONE);
						txt_store_sku_net_val.setVisibility(View.GONE);
		        	 
		        	 LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
		 			 final View view=inflater.inflate(R.layout.list_item_card_skuwise,null);
		 			 
		 			 
		 			
				        
		 			
		 			
		 			TextView tv_product_name=(TextView) view.findViewById(R.id.txt_prdct);
		 			TextView txt_mrp=(TextView) view.findViewById(R.id.txt_mrp);
		 			TextView txt_rate=(TextView) view.findViewById(R.id.txt_rate);
		 			TextView txt_stores=(TextView) view.findViewById(R.id.txt_stores);
		 			TextView txt_order=(TextView) view.findViewById(R.id.txt_order);
		 			TextView txt_free=(TextView) view.findViewById(R.id.txt_free);
		 			TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
		 			TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
		 			TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
		 			TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
		 			
		 			
		 			 //  Go_Ch_Wed_PL_140gm^90^81.82^2^0^33.06^330.581^41.32^371.901^1^2
			        
			        /* Go_Ch_Wed_PL_140gm------>Product Name
			         ^90------>Product MRP
			         ^81.82--->Standard Rate
			         ^2---->Order Qty
			         ^0----->Free Qty
			         ^33.06----->Discount Value
			         ^330.581--->Amount Before Tax
			         ^41.32----->Tax Value
			         ^371.901---->Amount After Tax
			         ^1---------->Store Count
			         ^2----->Level*/
		 			
					tv_product_name.setText(s1);
					Double prd_mrp=Double.parseDouble(s2);
					prd_mrp= Double.parseDouble(new DecimalFormat("##.##").format(prd_mrp));
					txt_mrp.setText(""+prd_mrp);
					txt_rate.setText(s3);
	                txt_stores.setText(s10);
					txt_order.setText(s4);
					txt_free.setText(s5);	
					
					Double disc_val=Double.parseDouble(s6);
					disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
					txt_discnt_val.setText(""+disc_val.intValue());
					
					Double ValBeforeTax=Double.parseDouble(s7);
			 		ValBeforeTax= Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
			 		txt_gross_val.setText(""+ValBeforeTax.intValue());
			 		
			 		
			 		Double ValTax=Double.parseDouble(s8);
			 		ValTax= Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
			 		txt_tac_val.setText(""+ValTax.intValue());
			 		
			 		
			 		Double ValAfterTax=Double.parseDouble(s9);
			 		ValAfterTax= Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
			 		txt_net_val.setText(""+ValAfterTax.intValue());
					
	               /* txt_discnt_val.setText(s6);
	                txt_gross_val.setText(s7);
					txt_tac_val.setText(s8);
					txt_net_val.setText(s9);*/
					
					ll_store_sku.addView(view);
		         }
		         ll_Scroll_product.addView(viewParent);
		         
			}
			
			
			
			
			
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
