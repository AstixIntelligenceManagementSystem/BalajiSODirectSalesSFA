package project.astix.com.balajisosfadirectsales;





import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class CheckDatabaseData extends BaseActivity
{

	ArrayAdapter<String> dataAdapter = null;
	 String[] storeNames;
	 
	 LinkedHashMap<String, String> hmapStore_details=new LinkedHashMap<String, String>();
	 
	 PRJDatabase dbengine = new PRJDatabase(this);
	 
	 String date_value="";
		String imei="";
		String rID;
		String pickerDate="";

		public String back="0";
		public int bck = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.activity_show_data);
		 
		 Intent extras = getIntent();
			bck = extras.getIntExtra("bck", 0);
			
			
			if(extras !=null)
			{
			date_value=extras.getStringExtra("userDate");
			pickerDate= extras.getStringExtra("pickerDate");
			imei=extras.getStringExtra("imei");
			rID=extras.getStringExtra("rID");
			back=extras.getStringExtra("back");	
			
		    }
		
		 getAllStoreListDetail();
		 
		 initialization();
	}
	

	private void getAllStoreListDetail() 
	{
			
		hmapStore_details=dbengine.fetch_StoreInvoiceWiseData_List();
			
			int index=0;
			if(hmapStore_details!=null)
	    	{
				storeNames=new String[hmapStore_details.size()];
				LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapStore_details); 
	            Set set2 = map.entrySet();
	            Iterator iterator = set2.iterator();
	            while(iterator.hasNext())
	            {
	            	 Map.Entry me2 = (Map.Entry)iterator.next();
	            	 storeNames[index]=me2.getKey().toString();
	                 index=index+1;
	            }
	    	}
	    	
			
		}
	
	public void initialization()
	{
		
		ImageView but_back=(ImageView)findViewById(R.id.backbutton);
		but_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent ide=new Intent(CheckDatabaseData.this,StoreSelection.class);
				ide.putExtra("userDate", date_value);
				ide.putExtra("pickerDate", pickerDate);
				ide.putExtra("imei", imei);
				ide.putExtra("rID", rID);
				//startActivity(ide);
				startActivity(ide);
				finish();
				
				
			}
		});
		
		
		 dataAdapter = new ArrayAdapter<String>(this,R.layout.country_list, storeNames);
    	 final ListView listView = (ListView) findViewById(R.id.listView1);
    	  // Assign adapter to ListView
    	  listView.setAdapter(dataAdapter);
    	  
    	  
    	  listView.setOnItemClickListener(new OnItemClickListener() 
    	  {
    	   
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id)
				{
					// TODO Auto-generated method stub
					// Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
					 
					 String selectedFromList = (String) (listView.getAdapter().getItem(position));
					
					 
					 
				}
    	  });
	}

	
	
}
