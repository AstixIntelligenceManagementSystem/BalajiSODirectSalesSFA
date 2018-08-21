package project.astix.com.balajisosfadirectsales;

import java.util.StringTokenizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class LastVisitDetailsSecondPart extends BaseActivity
{

	public String fStoreID;
	public String SN;
	public String fIMEI;
	public String fDate;
	public String pickerDate;
	public TableLayout tbl1_dyntable_For_LastVisitDate;
	public TableLayout tbl1_dyntable_For_LastOrderDate;
	public String LastVisitDateForTable[]=new String[1];
	PRJDatabase dbengine = new PRJDatabase(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_last_visit_details_second_part);
		
		Intent passedvals = getIntent();

		fStoreID = passedvals.getStringExtra("storeID");
		fIMEI = passedvals.getStringExtra("imei");
		fDate = passedvals.getStringExtra("userdate");
		pickerDate = passedvals.getStringExtra("pickerDate");
		SN = passedvals.getStringExtra("SN");
		LastVisitDateForTable[0]="NA";
		setUpVariable();
		setUpTablesData();
		
		
		
	}
	
	public void setUpVariable()
	{
		 TextView StoreName = (TextView)findViewById(R.id.StoreName);
		    dbengine.open();
			String selStoreName=dbengine.FetchStoreName(fStoreID);
			dbengine.close();
			StoreName.setText(selStoreName+" "+getText(R.string.Summary));
			
			
			
			
			
		
		Button but_Back = (Button)findViewById(R.id.Back_but);		
		but_Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent fireBackDetPg = new Intent(LastVisitDetailsSecondPart.this, LastVisitDetails.class);
				
				
				fireBackDetPg.putExtra("storeID", fStoreID);
				fireBackDetPg.putExtra("SN", SN);
				fireBackDetPg.putExtra("bck", 1);
				fireBackDetPg.putExtra("imei", fIMEI);
				fireBackDetPg.putExtra("date", fDate);
				fireBackDetPg.putExtra("pickerDate", pickerDate);
				
				
				startActivity(fireBackDetPg);
				finish();
				
			}
		});
		
		dbengine.open();
		int checkDataForTotalValues=dbengine.counttblspForPDAGetLastOrderDetails_TotalValues(fStoreID);
		System.out.println("Hello Value checkDataForTotalValues :"+checkDataForTotalValues);
		dbengine.close();
		if(checkDataForTotalValues==1)
		{
			
			 TextView OrderValue = (TextView)findViewById(R.id.OrderValue);
			 TextView ExecutionValue = (TextView)findViewById(R.id.ExecutionValue);
			dbengine.open();
			String LastTotalOrderAndExecutionValue[]=dbengine.fetchAllDataFromtblspForPDAGetLastOrderDetails_TotalValues(fStoreID);
			StringTokenizer tokens = new StringTokenizer(String.valueOf(LastTotalOrderAndExecutionValue[0]), "_");
			
			String OV=tokens.nextToken().trim().toString();
			String EV=tokens.nextToken().trim().toString();
			
			System.out.println("Hello Value OV :"+OV);
			System.out.println("Hello Value EV :"+EV);
			
			OrderValue.setText("Rs. "+OV);
			
			ExecutionValue.setText("Rs. "+EV);
			dbengine.close();
		}
		
	}
	
	public void setUpTablesData()
	{
		dbengine.open();
		System.out.println("New Getting fStoreID :"+fStoreID);
		int checkDataForVisitDetails=dbengine.counttblForPDAGetLastVisitDetails(fStoreID);
		dbengine.close();
		System.out.println("New Getting checkDataForVisitDetails :"+checkDataForVisitDetails);
		if(checkDataForVisitDetails==1)
		{
			LinearLayout VisitDetailLayout = (LinearLayout)findViewById(R.id.VisitDetailLayout);
			VisitDetailLayout.setVisibility(View.VISIBLE);
			RelativeLayout relativeLayoutLastVisitDate = (RelativeLayout)findViewById(R.id.relativeLayoutLastVisitDate);
			relativeLayoutLastVisitDate.setVisibility(View.VISIBLE);
			dbengine.open();
			String LastVisitDetails[]=dbengine.fetchAllDataFromtblForPDAGetLastVisitDetails(fStoreID);
			LastVisitDateForTable=dbengine.fetchDateFromtblForPDAGetLastVisitDetails(fStoreID);
			//System.out.println("Ashish and Anuj LTDdet[i] : "+LTDdet.length);
			dbengine.close();
			
			TextView VisitDate = (TextView)findViewById(R.id.VisitDateId);
			VisitDate.setText("("+LastVisitDateForTable[0]+")");
			//TextView ExecutedDate = (TextView)findViewById(R.id.ExecutedDate);
			//ExecutedDate.setText(getText(R.string.Executed)+"("+LastVisitDateForTable[0]+")");
			
			
			LayoutInflater inflater = getLayoutInflater();
			
			DisplayMetrics dm = new DisplayMetrics();
		    getWindowManager().getDefaultDisplay().getMetrics(dm);
		    double x = Math.pow(dm.widthPixels/dm.xdpi,2);
		    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
		    double screenInches = Math.sqrt(x+y);
			if(LastVisitDetails.length>0)
			{
				
                 tbl1_dyntable_For_LastVisitDate = (TableLayout)findViewById(R.id.dyntable_For_LastVisitDate);
			
			for (int current = 0; current <= (LastVisitDetails.length - 1); current++) 
			{

				final TableRow row = (TableRow)inflater.inflate(R.layout.table_last_visit_details, tbl1_dyntable_For_LastVisitDate, false);
				
				TextView tv1 = (TextView)row.findViewById(R.id.RowSKUName);
				//TextView tv2 = (TextView)row.findViewById(R.id.RowStock);
				//TextView tv3 = (TextView)row.findViewById(R.id.RowOrderQty);
				TextView tv4 = (TextView)row.findViewById(R.id.RowExecutionQty);
				
				if(screenInches>6.5)
				{
					tv1.setTextSize(14);
					//tv2.setTextSize(14);
					//tv3.setTextSize(14);
					tv4.setTextSize(14);
				}
				else
				{
					
				}
				
				//System.out.println("Abhinav Raj LTDdet[current]:"+LTDdet[current]);
				StringTokenizer tokens = new StringTokenizer(String.valueOf(LastVisitDetails[current]), "^");
				
				tv1.setText(tokens.nextToken().trim());
				tokens.nextToken().trim();
				tokens.nextToken().trim();
				//tv2.setText(tokens.nextToken().trim());
				//tv3.setText(tokens.nextToken().trim());
				tv4.setText(tokens.nextToken().trim());
			
				tbl1_dyntable_For_LastVisitDate.addView(row);
			}
			
		}
			
		}
		else
		{
			
			LinearLayout VisitDetailLayout = (LinearLayout)findViewById(R.id.VisitDetailLayout);
			VisitDetailLayout.setVisibility(View.GONE);
			RelativeLayout relativeLayoutLastVisitDate = (RelativeLayout)findViewById(R.id.relativeLayoutLastVisitDate);
			relativeLayoutLastVisitDate.setVisibility(View.GONE);
		}
		
		
		
		

		dbengine.open();
		int checkDataForOrderDetails=dbengine.counttblForPDAGetLastOrderDetails(fStoreID);
		dbengine.close();
		if(checkDataForOrderDetails==1)
		{
			
			
			dbengine.open();
			String LastOrderDetails[]=dbengine.fetchAllDataFromtblForPDAGetLastOrderDetails(fStoreID);
			//System.out.println("Ashish and Anuj LTDdet[i] : "+LTDdet.length);
			String LastOrderDateForTable[]=dbengine.fetchOrderDateFromtblForPDAGetLastOrderDetails(fStoreID);
			dbengine.close();
			/*System.out.println("New Value LastOrderDateForTable.length"+LastOrderDateForTable.length);
			System.out.println("New Value LastVisitDateForTable.length"+LastVisitDateForTable.length);
			System.out.println("New Value LastVisitDateForTable "+LastVisitDateForTable[0]);
			System.out.println("New Value LastOrderDateForTable"+LastOrderDateForTable[0]);*/
			
			if(LastVisitDateForTable[0].equals(LastOrderDateForTable[0]))
			{
				TextView LastVisitHeading = (TextView)findViewById(R.id.LastVisitHeading);
				LastVisitHeading.setText(getText(R.string.LastVisitOrderDetails));
				RelativeLayout relativeLayoutForOrderDetails = (RelativeLayout)findViewById(R.id.relativeLayoutForOrderDetails);
				relativeLayoutForOrderDetails.setVisibility(View.GONE);
				LinearLayout OrderDetailLayout = (LinearLayout)findViewById(R.id.OrderDetailLayout);
				OrderDetailLayout.setVisibility(View.GONE);
				
				
			}
			else
			{
				
				RelativeLayout relativeLayoutForOrderDetails = (RelativeLayout)findViewById(R.id.relativeLayoutForOrderDetails);
				relativeLayoutForOrderDetails.setVisibility(View.VISIBLE);
			TextView OrderDate = (TextView)findViewById(R.id.OrderDateId);
			OrderDate.setText("("+LastOrderDateForTable[0]+")");
			//TextView ExecutedDateOrder = (TextView)findViewById(R.id.ExecutedDateOrder);
			//ExecutedDateOrder.setText(getText(R.string.Executed)+"("+LastOrderDateForTable[0]+")");
			
			LayoutInflater inflater = getLayoutInflater();
			
			DisplayMetrics dm = new DisplayMetrics();
		    getWindowManager().getDefaultDisplay().getMetrics(dm);
		    double x = Math.pow(dm.widthPixels/dm.xdpi,2);
		    double y = Math.pow(dm.heightPixels/dm.ydpi,2);
		    double screenInches = Math.sqrt(x+y);
			if(LastOrderDetails.length>0)
			{
				
				tbl1_dyntable_For_LastOrderDate = (TableLayout)findViewById(R.id.dyntable_For_OrderDetails);
			
			for (int current = 0; current <= (LastOrderDetails.length - 1); current++) 
			{

				final TableRow row = (TableRow)inflater.inflate(R.layout.table_last_order_details, tbl1_dyntable_For_LastOrderDate, false);
				
				TextView tv1 = (TextView)row.findViewById(R.id.RowSKUName);
				TextView tv2 = (TextView)row.findViewById(R.id.RowOrder);
				//TextView tv3 = (TextView)row.findViewById(R.id.RowFreeQty);
				//TextView tv4 = (TextView)row.findViewById(R.id.RowExecutionQty);
				
				if(screenInches>6.5)
				{
					tv1.setTextSize(14);
					tv2.setTextSize(14);
					//tv3.setTextSize(14);
					//tv4.setTextSize(14);
				}
				else
				{
					
				}
				
				//System.out.println("Abhinav Raj LTDdet[current]:"+LTDdet[current]);
				StringTokenizer tokens = new StringTokenizer(String.valueOf(LastOrderDetails[current]), "^");
				
				tv1.setText(tokens.nextToken().trim());
				
				tv2.setText(tokens.nextToken().trim());
				//tv3.setText(tokens.nextToken().trim());
				//tv4.setText(tokens.nextToken().trim());
			
				tbl1_dyntable_For_LastOrderDate.addView(row);
			}
			
		}
			}
			
		}
		else
		{
			RelativeLayout relativeLayoutForOrderDetails = (RelativeLayout)findViewById(R.id.relativeLayoutForOrderDetails);
			relativeLayoutForOrderDetails.setVisibility(View.GONE);
			LinearLayout OrderDetailLayout = (LinearLayout)findViewById(R.id.OrderDetailLayout);
			OrderDetailLayout.setVisibility(View.GONE);
			
		}
	
	}
	

}
