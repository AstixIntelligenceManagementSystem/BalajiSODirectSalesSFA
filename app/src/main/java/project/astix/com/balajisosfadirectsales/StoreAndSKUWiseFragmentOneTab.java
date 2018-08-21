package project.astix.com.balajisosfadirectsales;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.astix.Common.CommonInfo;

public class StoreAndSKUWiseFragmentOneTab<Context> extends Fragment 
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

	public boolean isOnline()
	{
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected())
		{
			return true;
		}
		return false;
	}
	
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
		
		//'868087024619932','29-10-2015'  


		if(isOnline()) {
			try {
				GetSKUWiseSummaryForDay task = new GetSKUWiseSummaryForDay();
				task.execute();
			} catch (Exception e) {
				// TODO Autouuid-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Toast.makeText(mContext, getResources().getString(R.string.NoDataConnectionFullMsg), Toast.LENGTH_SHORT).show();
		}
		 
		
		
        return rootView;
    }
    
	private class GetSKUWiseSummaryForDay extends AsyncTask<Void, Void, Void>
	{		
		
		ProgressDialog pDialogGetStores=new ProgressDialog(mContext);
		/*public GetSKUWiseSummaryForDay(SKUWiseSummary activity) 
		{
			pDialogGetStores = new ProgressDialog(activity);
		}*/
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			

			dbengine.open();
			dbengine.truncateStoreAndSKUWiseDataTable();
			dbengine.close();
			
			
			pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
			pDialogGetStores.setMessage(getText(R.string.genTermRetrivingSummary));
			pDialogGetStores.setIndeterminate(false);
			pDialogGetStores.setCancelable(false);
			pDialogGetStores.setCanceledOnTouchOutside(false);
			pDialogGetStores.show();
		}

		@Override
		protected Void doInBackground(Void... args) 
		{
			ServiceWorker newservice = new ServiceWorker();
			
		try
	  	 {
				newservice = newservice.getCallspRptGetStoreSKUWiseDaySummary(getActivity(),  imei, fDate);
				
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
            dbengine.open();
            AllDataContainer= dbengine.fetchAllDataFromtblStoreSKUWiseDaySummary();
            dbengine.close();
            intializeFields();
		  
		}
	}
	
	private void intializeFields() 
	{
		
		if(AllDataContainer.length>0)
		{
		
		 StringTokenizer tokens = new StringTokenizer(String.valueOf(AllDataContainer[0]), "^");
         
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
         String a12 = tokens.nextToken().toString().trim();
         String a13 = tokens.nextToken().toString().trim();
        
         
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
     	
		/*TextView total_LinesPerBill=(TextView)rootView.findViewById(R.id.total_LinesPerBill);
		total_LinesPerBill.setText(a3);
		TextView total_StockValue=(TextView)rootView.findViewById(R.id.total_StockValue);
		total_StockValue.setText(a4);*/
		TextView total_discountValue=(TextView)rootView.findViewById(R.id.total_discountValue);
		Double disc_val=Double.parseDouble(a7);
		disc_val= Double.parseDouble(new DecimalFormat("##.##").format(disc_val));
		total_discountValue.setText(""+disc_val.intValue());
		
		TextView total_ValBeforeTax=(TextView)rootView.findViewById(R.id.total_ValBeforeTax);
		Double ValBeforeTax=Double.parseDouble(a9);
		ValBeforeTax=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax));
		total_ValBeforeTax.setText(""+ValBeforeTax.intValue());
		
		TextView total_ValTax=(TextView)rootView.findViewById(R.id.total_ValTax);
		Double ValTax=Double.parseDouble(a10);
		ValTax=Double.parseDouble(new DecimalFormat("##.##").format(ValTax));
		total_ValTax.setText(""+ValTax.intValue());
		
		
		TextView total_ValAfterTax=(TextView)rootView.findViewById(R.id.total_ValAfterTax);
		Double ValAfterTax=Double.parseDouble(a11);
		ValAfterTax=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax));
		total_ValAfterTax.setText(""+ValAfterTax.intValue());
		
		
		ll_Scroll_product=(LinearLayout) rootView.findViewById(R.id.ll_Scroll_product);
		ll_Scroll_product.removeAllViews();
		
		for(int i=1;i<AllDataContainer.length;i++)
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
	         String s12 = tokens1.nextToken().toString().trim();
	         String s13 = tokens1.nextToken().toString().trim();
	         String s14="";
	         try
	         {
	         s14 = tokens1.nextToken().toString().trim();
	         }
	         catch(Exception e)
	         {
	        	 
	         }
	         
	         System.out.println("Value of level :"+s13);
	         
	         
	        LayoutInflater inflaterParent=(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
			final View viewParent=inflaterParent.inflate(R.layout.list_store_sku_header_wise,null);
				
           LinearLayout ll_store_sku=(LinearLayout) viewParent.findViewById(R.id.ll_store_sku);
	         
	         if(Integer.parseInt(s12)==1)
	         {
	        	
				
				 TextView txt_store_sku=(TextView) viewParent.findViewById(R.id.txt_store_sku);
				 TextView txt_store_sku_discnt_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_discnt_val);
				 TextView txt_store_sku_gross_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_gross_val);
				 TextView txt_store_sku_tac_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_tac_val);
				 TextView txt_store_sku_net_val=(TextView) viewParent.findViewById(R.id.txt_store_sku_net_val);
				
				
				
				txt_store_sku.setText(s3);
				/*dbengine.open();
				String StoreName=dbengine.FetchStoreName(s13.trim());
				dbengine.close();
				txt_store_sku.setText(StoreName);*/
				
				Double disc_val1=Double.parseDouble(s8);
				disc_val1= Double.parseDouble(new DecimalFormat("##.##").format(disc_val1));
				txt_store_sku_discnt_val.setText(""+disc_val1.intValue());
				
				
				Double ValBeforeTax1=Double.parseDouble(s9);
				ValBeforeTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
				txt_store_sku_gross_val.setText(""+ValBeforeTax1.intValue());
				 txt_store_sku_gross_val.setVisibility(View.GONE);
				
				Double ValTax1=Double.parseDouble(s10);
		 		ValTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
				txt_store_sku_tac_val.setText(""+ValTax1.intValue());
				
				
				Double ValAfterTax1=Double.parseDouble(s11);
		 		ValAfterTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
				txt_store_sku_net_val.setText(""+ValAfterTax1.intValue());
				
				
	         }
	         else if(Integer.parseInt(s12)==2)
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
				tv_product_name.setText(s3);
				
				TextView txt_mrp=(TextView) view.findViewById(R.id.txt_mrp);
				txt_mrp.setText(s4);
				
				TextView txt_rate=(TextView) view.findViewById(R.id.txt_rate);
				txt_rate.setText(s5);
				
				TextView txt_stock=(TextView) view.findViewById(R.id.txt_stock);
				if(s14.equals("") || s14.equals(null))
				{
					txt_stock.setText("");
				}
				else
				{
					Double Valstock=Double.parseDouble(s14);
					Valstock=Double.parseDouble(new DecimalFormat("##.##").format(Valstock));
					txt_stock.setText(""+Valstock.intValue());
				}
				
				
				
				TextView txt_order=(TextView) view.findViewById(R.id.txt_order);
				txt_order.setText(s6);
				
				TextView txt_free=(TextView) view.findViewById(R.id.txt_free);
				txt_free.setText(s7);
				
				
				TextView txt_discnt_val=(TextView) view.findViewById(R.id.txt_discnt_val);
				Double disc_val1=Double.parseDouble(s8);
				disc_val1= Double.parseDouble(new DecimalFormat("##.##").format(disc_val1));
				txt_discnt_val.setText(""+disc_val1.intValue());
				
				
				
				TextView txt_gross_val=(TextView) view.findViewById(R.id.txt_gross_val);
				Double ValBeforeTax1=Double.parseDouble(s9);
				ValBeforeTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValBeforeTax1));
				txt_gross_val.setText(""+ValBeforeTax1.intValue());
				 txt_gross_val.setVisibility(View.GONE);
				
				TextView txt_tac_val=(TextView) view.findViewById(R.id.txt_tac_val);
				Double ValTax1=Double.parseDouble(s10);
		 		ValTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValTax1));
				txt_tac_val.setText(""+ValTax1.intValue());
				
				
				
				TextView txt_net_val=(TextView) view.findViewById(R.id.txt_net_val);
				Double ValAfterTax1=Double.parseDouble(s11);
		 		ValAfterTax1=Double.parseDouble(new DecimalFormat("##.##").format(ValAfterTax1));
				txt_net_val.setText(""+ValAfterTax1.intValue());
				
				
				ll_store_sku.addView(view);
	         }
	         ll_Scroll_product.addView(viewParent);
	         
		}
		
	}	
		
			
			
	}
 
}
