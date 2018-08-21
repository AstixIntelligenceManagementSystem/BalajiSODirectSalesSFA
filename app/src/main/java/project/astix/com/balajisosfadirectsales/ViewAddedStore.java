package project.astix.com.balajisosfadirectsales;


import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewAddedStore extends Activity {

	LinkedHashMap<String, String> lnkdHmapAddedStore=new LinkedHashMap<String, String>();
	PRJDatabase dbHelper;
	LinearLayout ll_addedOutlet;
	View viewAddedStore;
public String userDate;
	
	public String pickerDate;
	public String imei;
	public String rID;
	
	public boolean onKeyDown(int keyCode, KeyEvent event)  // Control the PDA Native Button Handling
	{
		  // TODO Auto-generated method stub
		  if(keyCode==KeyEvent.KEYCODE_BACK)
		  {
		   return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_HOME)
		  {
			 // finish();
			  return true;
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_added_store);
		
		dbHelper=new PRJDatabase(ViewAddedStore.this);
		Intent getStorei = getIntent();
		if(getStorei !=null)
		{
		imei = getStorei.getStringExtra("imei").trim();
        pickerDate = getStorei.getStringExtra("pickerDate").trim();
        userDate = getStorei.getStringExtra("userDate");
		rID = getStorei.getStringExtra("rID");
		}
		getNewStoresFromDataBase();
		intializeFields();
		
	}
	private void intializeFields() {
		
		ll_addedOutlet=(LinearLayout) findViewById(R.id.ll_addedOutlet);
		ImageView img_back=(ImageView) findViewById(R.id.img_back);
		img_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(ViewAddedStore.this, StoreSelection.class);
				//Intent intent = new Intent(StoreSelection.this, Add_New_Store_NewFormat.class);
				//Intent intent = new Intent(StoreSelection.this, Add_New_Store.class);
			
				intent.putExtra("userdate", userDate);
				intent.putExtra("pickerDate", pickerDate);
				intent.putExtra("imei", imei);
				intent.putExtra("rID", rID);
				startActivity(intent);
				finish();
			}
		});
		
		showOutletAdded();
		
		
	}
	private void showOutletAdded() {
		
		if(lnkdHmapAddedStore!=null)
		{
			if(lnkdHmapAddedStore.size()>0)
			{
				LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				for(Entry<String, String> entry:lnkdHmapAddedStore.entrySet())
				{
					viewAddedStore=inflater.inflate(R.layout.custom_view_outletadded, null);
					RelativeLayout rlOutltActivity= (RelativeLayout) viewAddedStore.findViewById(R.id.rlOutltActivity);
					ImageView imgVw_dtlOutlet=(ImageView) viewAddedStore.findViewById(R.id.imgVw_dtlOutlet);
					TextView tvOutletName=(TextView) viewAddedStore.findViewById(R.id.tvOutletName);
					tvOutletName.setText(entry.getValue().toString());
					rlOutltActivity.setTag(entry.getKey());
					rlOutltActivity.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							
							Intent intent = new Intent(ViewAddedStore.this, AddNewStore_DynamicSectionWiseSO.class);
							//Intent intent = new Intent(StoreSelection.this, Add_New_Store_NewFormat.class);
							//Intent intent = new Intent(StoreSelection.this, Add_New_Store.class);
							intent.putExtra("activityFrom", "ViewAddedStore");
							intent.putExtra("outletId", v.getTag().toString());
							intent.putExtra("userdate", userDate);
							intent.putExtra("pickerDate", pickerDate);
							intent.putExtra("imei", imei);
							intent.putExtra("rID", rID);
							startActivity(intent);
							finish();
							
						}
					});
					ll_addedOutlet.addView(viewAddedStore);
				}
				
			}
			else
			{
				LayoutParams lparams = new LayoutParams(
						   LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						TextView tv=new TextView(this);
						tv.setLayoutParams(lparams);
						tv.setText("No new Store Added,Please Add new Store then view Newly Store added");
						
				ll_addedOutlet.addView(tv);
			}

		}
		
		else
		{
			LayoutParams lparams = new LayoutParams(
					   LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					TextView tv=new TextView(this);
					tv.setLayoutParams(lparams);
					tv.setText("test");
					
			ll_addedOutlet.addView(tv);
		}
		
	}
	private void getNewStoresFromDataBase() {
		
		lnkdHmapAddedStore=dbHelper.getAllNewAddedStoreName();
		
	}

	
}
