package project.astix.com.balajisosfadirectsales;









import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.astix.Common.CommonInfo;

public class QuotationActivity extends BaseFragmentActivity implements TabListener {

	ActionBar actionBar;
	ViewPager viewPager;
	RelativeLayout QuotationActivityLayout;
	String prcID="";

static String selStoreName,SalesQuoteId,storeID,imei,date,pickerDate;
	


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quotation_activity);
		 
		QuotationActivityLayout=	(RelativeLayout) findViewById(R.id.QuotationActivityLayout);
		Intent intent = getIntent();
		String quatationFlag= intent.getStringExtra("quatationFlag");
		
		selStoreName = intent.getStringExtra("SN");
		SalesQuoteId=intent.getStringExtra("SalesQuoteId");
		storeID = intent.getStringExtra("storeID");
		imei = intent.getStringExtra("imei");
		date = intent.getStringExtra("userdate");
		pickerDate= intent.getStringExtra("pickerDate");
		prcID=intent.getStringExtra("prcID");
		 CommonInfo.prcID=prcID;
		 CommonInfo.quatationFlag=quatationFlag;
		 viewPager=(ViewPager) findViewById(R.id.pager);
	       // viewPager.setPageTransformer(true, new ZoomOutPageTransformer(TransformType.FLOW));
	        FragmentManager fragmentManager=getSupportFragmentManager();
	        viewPager.setAdapter(new Myadapter(fragmentManager));
	        viewPager.setOffscreenPageLimit(2);
	       
	        actionBar=getActionBar();
			  actionBar.setDisplayShowHomeEnabled(true);
		        actionBar.setIcon(R.drawable.ic_back);
			  
		        // Screen handling while hiding Actionbar title.
		        actionBar.setTitle("  Quotation Activity ");
		        actionBar.setDisplayShowTitleEnabled(true);
		        
		        
	        viewPager.setOnPageChangeListener(new OnPageChangeListener()
	        {
				
				@Override
				public void onPageSelected(int arg0) 
				{
					
					// TODO Auto-generated method stub
					
								actionBar.setSelectedNavigationItem(arg0);
						Fragment ff=((Myadapter)viewPager.getAdapter()).getRegisteredFragment(arg0);
						if(arg0==2 && ff!=null)
						{
							ff.onResume();
						}
						if(arg0==1 && ff!=null)
						{
							//ff.onResume();
							try
							{
								EditText txt_Search=(EditText) ff.getView().findViewById(R.id.txt_Search);
								if(!txt_Search.equals(null))
								{
									if(!txt_Search.getText().toString().trim().equals(""))
									{
										//android.app.FragmentManager fm = getFragmentManager(); 
										//Fragment fragm = ff; 
										((FragmentA) ff).clearViewWhenSlide();
										Button btnGo=	(Button) ff.getView().findViewWithTag("btnGo");
										btnGo.performClick();
									}
								}	
								
								
								 
						      
							}
							catch(Exception ex)
							{
								String AbhinavRaj="Abhi";	
								AbhinavRaj="sfsdfsf";
							}
							if(CommonInfo.prcID.equals("4") || CommonInfo.prcID.equals("5") ){
								 ((FragmentB) ff).callForViewFilledData();
					        }
							
						
						
					}
						
						if(arg0==2 && ff!=null)
						{
							((FragmentB) ff).callForViewFilledData();
						}
				
				}
				
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onPageScrollStateChanged(int arg0) 
				{
					// TODO Auto-generated method stub
					
				}
			});
	      /*  actionBar=getActionBar();
	        actionBar.setHomeAsUpIndicator(R.drawable.ic_launcher);*/
	        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	        actionBar.addTab(actionBar.newTab().setText("Customer Info").setTabListener(this));
	        if(!CommonInfo.prcID.equals("4") && !CommonInfo.prcID.equals("5") )
	        {
	        	  actionBar.addTab(actionBar.newTab().setText("Quotation").setTabListener(this));
	        }
	      
	        actionBar.addTab(actionBar.newTab().setText("View Quotation").setTabListener(this));
	       
	        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5C5696")));
	        

	     // set background for action bar tab
	     actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#5C5696")));  
	     
	       
		
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) 
	{
		viewPager.setCurrentItem(arg0.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1)
	{
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	        	 AlertDialog.Builder alertDialog = new AlertDialog.Builder(QuotationActivity.this);
				 alertDialog.setTitle("Information");
			       
			     alertDialog.setCancelable(false);
			     alertDialog.setMessage("Have you saved your data before going back ");
			     alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
			     {
			            public void onClick(DialogInterface dialog,int which) 
			            {
			            	dialog.dismiss();
			            	Intent ide=new Intent(QuotationActivity.this,LastVisitDetails.class);
							ide.putExtra("SN", selStoreName);
							ide.putExtra("storeID", storeID);
							ide.putExtra("imei", imei);
							ide.putExtra("userdate", date);
							ide.putExtra("pickerDate", pickerDate);
							ide.putExtra("back", 1);
							startActivity(ide);
							
							
							
							CommonInfo.SalesQuoteId="BLANK";
							CommonInfo.prcID="NULL";
							CommonInfo.newQuottionID="NULL";
							CommonInfo.globalValueOfPaymentStage="0"+"_"+"0"+"_"+"0";
				        	
							finish();
			            	
			            }
			        });
			     alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener()
			     {
			            public void onClick(DialogInterface dialog,int which) 
			            {
			            	dialog.dismiss();
			            }
			        });
			 
			        // Showing Alert Message
			        alertDialog.show();
	        	
	        	
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
}

