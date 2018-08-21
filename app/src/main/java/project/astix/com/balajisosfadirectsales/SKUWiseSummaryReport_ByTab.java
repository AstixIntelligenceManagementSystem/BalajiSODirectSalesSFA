package project.astix.com.balajisosfadirectsales;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MenuItem;
import android.widget.TableLayout;


public class SKUWiseSummaryReport_ByTab extends BaseActivity
{
	
	String date_value="";
	String imei="";
	String pickerDate="";

	String rID;
	PRJDatabase dbengine = new PRJDatabase(this);
	public TableLayout tl2; 
	public int bck = 0;
	public String Noti_text="Null";
	public int MsgServerID=0;
	
	


	

	// Declaring our tabs and the corresponding fragments.
	ActionBar.Tab OneTab, TwoTab, ThreeTab;
	
	Fragment fragmentOneTab = new SKUWiseFragmentOneTab();
	Fragment fragmentTwoTab = new SKUWiseFragmentTwoTab();
	Fragment fragmentThreeTab = new SKUWiseFragmentThreeTab();
	
	
	/*Fragment fragmentOneTabMTD = new SKUWiseFragmentOneTab();
	Fragment fragmentTwoTabMTD = new SKUWiseFragmentTwoTab();
	Fragment fragmentThreeTabMTD = new SKUWiseFragmentThreeTab();*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		 getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.mysummary_bytab);
		
		Intent extras = getIntent();
		bck = extras.getIntExtra("bck", 0);
		
		
		if(extras !=null)
		{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("imei");
			rID=extras.getStringExtra("rID");
	    }
		
		
		
	try
	{
		
		
		
		// Asking for the default ActionBar element that our platform supports.
		ActionBar actionBar = getActionBar();
		 
        // Screen handling while hiding ActionBar icon.
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_back);
 
        // Screen handling while hiding Actionbar title.

		actionBar.setTitle(getResources().getString(R.string.txtSKUWiseSummary));
//        actionBar.setTitle("  SKU Wise Summary");
        actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
        // Creating ActionBar tabs.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
      
        // Setting custom tab icons.
       
        
      
        OneTab = actionBar.newTab().setText(R.string.submit_sent_server);
        
        TwoTab = actionBar.newTab().setText(R.string.saved_not_submit);
        ThreeTab = actionBar.newTab().setText(R.string.submit_not_sent_server);
        
        
       /* TwoTab = actionBar.newTab().setText(R.string.submit_not_sent_server);
        ThreeTab = actionBar.newTab().setText(R.string.saved_not_submit);*/
        
        
        // Setting tab listeners.
        
        
        
        
        
        OneTab.setTabListener(new TabListener(fragmentOneTab));
       // TwoTab.setTabListener(new TabListener(fragmentTwoTab));
        ThreeTab.setTabListener(new TabListener(fragmentThreeTab));
       
        // Adding tabs to the ActionBar.
        actionBar.addTab(OneTab);
       // actionBar.addTab(TwoTab);
        actionBar.addTab(ThreeTab);
        
       
        
        
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#213F7D")));

        // set background for action bar tab
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));   
        
	}
	catch(Exception e)
	{
		
	}
        
        
      
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	        	Intent ide=new Intent(SKUWiseSummaryReport_ByTab.this,DetailReportSummaryActivity.class);
				ide.putExtra("userDate", date_value);
				ide.putExtra("pickerDate", pickerDate);
				ide.putExtra("imei", imei);
				ide.putExtra("rID", rID);
				ide.putExtra("back", "1");
				startActivity(ide);
				finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
}

