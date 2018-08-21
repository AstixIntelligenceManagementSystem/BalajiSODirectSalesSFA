package project.astix.com.balajisosfadirectsales;
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.ViewGroup;  
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
public class ListAdapter extends ArrayAdapter<String>  {  
    customButtonListener customListner; 
    PRJDatabase dbengine;
    public String storeStatus;
    public String[] StoreID1;
    public String[] StoreSstat1;
    public String[] StorenameOther1;
    public int checkNoPosition=0;
    public int count=0;
    int pos = 0;
    int save = -1;
    public String pickerDate1; 
    public TextView slctdTxt;
    int spinnerRouteSlctd;
    int spinnerDistSlctd;
    public String TagStoreID;
    public String imei1;
	public String currSysDate1;
	public String[] StorenameList1;
	public String temp;
    public int chk=0;
    public  View[] convertView123;
    public interface customButtonListener {  
    /*    public void onButtonClickListner(int position,String value,TextView txtvw, Button btninv, Button btnCan, CheckBox checkConformExecutionStore,int ClkdView);
    }  */
    public void onButtonClickListner(int position, String value, TextView txtvw, Button btninv, CheckBox checkConformExecutionStore, int ClkdView, String StoreIdOnClickedChekBox);
}  
    public void setCustomButtonListner(customButtonListener listener) {  
        this.customListner = listener;  
    }  
    private Activity context;  
    private ArrayList<String> data = new ArrayList<String>();  
    private Map<Integer, View> myViews = new HashMap<Integer, View>();
    private Map<Integer, String> myViews1 = new HashMap<Integer, String>();
    private HashMap<String, String> hmap1=new HashMap<String, String>();
    HashMap<String, String> hmapStoreIdName1=new HashMap<String, String>();
    HashMap<String, String> hmapStoreIdAndInvoiceOtherDetails1=new HashMap<String, String>();
    private LayoutInflater inflater;
    public ListAdapter(Activity context, ArrayList<String> dataItem,String[] StoreID,String[] StoreSstatnew,String[] StorenameOthernew,String pickerDate,HashMap<String, String> hmap,String imei,String currSysDate,String[] StorenameList,int spinnerRouteSlctd,int spinnerDistSlctd,HashMap<String, String> hmapStoreIdName,HashMap<String, String> hmapStoreIdAndInvoiceOtherDetails)
    {  
    	super(context, R.layout.child_listview, dataItem);  
        this.data = dataItem;  
        this.context = context;  
        
        dbengine= new PRJDatabase(context);
        StoreID1=new String[StoreID.length];
        StoreSstat1=new String[StoreSstatnew.length];
        StoreSstat1=StoreSstatnew;
        StorenameOther1 =new String[StorenameOthernew.length];
        StorenameOther1=StorenameOthernew;
        StorenameList1=new String[StoreID.length];
        StorenameList1=StorenameList;
        StoreID1=StoreID;
        hmap1=hmap;
        hmapStoreIdName1=hmapStoreIdName;
        hmapStoreIdAndInvoiceOtherDetails1=hmapStoreIdAndInvoiceOtherDetails;
        convertView123=new View[StoreID1.length];
        pickerDate1=pickerDate;
        imei1=imei;
        currSysDate1=currSysDate;
        this.spinnerRouteSlctd=spinnerRouteSlctd;
        this.spinnerDistSlctd=spinnerDistSlctd;
        inflater = LayoutInflater.from(context);
        
    }
    @SuppressWarnings("unused")
	public View getView(final int position,  View convertView, ViewGroup parent) 
    {  
       final  ViewHolder viewHolder; 
       
        View view =null;
        if (view == null) 
	     {  
	            view = inflater.inflate(R.layout.child_listview, null);  
	            viewHolder = new ViewHolder();  
	            viewHolder.checkConformExecutionStore = (CheckBox)view.findViewById(R.id.checkBoxExecution);
	            viewHolder.text = (TextView) view.findViewById(R.id.childTextView);  
	          //  viewHolder.butCancel = (Button) view.findViewById(R.id.butCancel); 
	            viewHolder.butInvoice = (Button) view.findViewById(R.id.butInvoice);  
	            view.setTag(viewHolder);
	            	viewHolder.text.setTag(StoreID1[position]);
	            viewHolder.checkConformExecutionStore.setTag(StoreID1[position]);
	            viewHolder.butInvoice.setTag(StoreID1[position]);
	            myViews.put(position, view);
	            myViews1.put(position, StoreID1[position]);
	            viewHolder.text.setTextColor(Color.BLACK);
	      }
	  else 
	       { 
               viewHolder = (ViewHolder)view.getTag();
               viewHolder.text.setTextColor(Color.BLACK);
           }  
        
        
      //  7 for Cancel order
      //  9 for selected store by checkbox
      //hmapStoreIdName
        	temp=getItem(position);
        	viewHolder.text.setText(String.valueOf(hmapStoreIdName1.get(viewHolder.text.getTag())));
        	//viewHolder.text.setText(temp);
        	StringTokenizer ad1 = new StringTokenizer(String.valueOf(hmapStoreIdAndInvoiceOtherDetails1.get(viewHolder.text.getTag())), "_");
     		String TagStoreID1= ad1.nextToken().trim();
     		String TagOdrerID1= ad1.nextToken().trim();
     		String TagRouteID1= ad1.nextToken().trim();
     		String TagDistID1= ad1.nextToken().trim();
     		String TagDate1= ad1.nextToken().trim();
     		String TagSstat1= ad1.nextToken().trim();
     		String cancelConfirmFlag= ad1.nextToken().trim();
            if(Integer.parseInt(TagSstat1)==7) //what ever status you want
            {
         	  viewHolder.text.setTextColor(Color.RED);
         	  viewHolder.butInvoice.setTextColor(Color.RED); 
	         // viewHolder.butCancel.setTextColor(Color.RED); 
	         // viewHolder.butCancel.setBackgroundColor(Color.RED);  
	         // viewHolder.butInvoice.setBackgroundColor(Color.RED);
	          viewHolder.text.setEnabled(false);
	          viewHolder.butInvoice.setEnabled(false);
	        //  viewHolder.butCancel.setEnabled(true);
	          viewHolder.checkConformExecutionStore.setEnabled(false);
	          viewHolder.checkConformExecutionStore.setChecked(false);
	          //viewHolder.butCancel.setBackgroundDrawable(R.drawable.delete_button_selected_red);
	//viewHolder.butCancel.setBackgroundResource(R.drawable.delete_button_selected_red);
	         // viewHolder.checkConformExecutionStore.setBackgroundResource(R.drawable.checknew_red1);
	          viewHolder.checkConformExecutionStore.setButtonDrawable(context.getResources().getDrawable(R.drawable.unchecked));
              
            }
            else if(Integer.parseInt(TagSstat1)==9)//what ever status you want
            {
            	if(Integer.parseInt(cancelConfirmFlag)==0)
            	{
            		  viewHolder.text.setTextColor(context.getResources().getColor(R.color.green_submitted));
                 	  viewHolder.butInvoice.setTextColor(context.getResources().getColor(R.color.green_submitted)); 
            	}
            	else if(Integer.parseInt(cancelConfirmFlag)==1)
            	{
            		  viewHolder.text.setTextColor(Color.RED);
                 	  viewHolder.butInvoice.setTextColor(Color.RED); 
            	}
         	
	 //         viewHolder.butCancel.setTextColor(Color.BLUE); 
	         // viewHolder.butCancel.setBackgroundColor(Color.BLUE);  
	         // viewHolder.butInvoice.setBackgroundColor(Color.BLUE);
            	 viewHolder.text.setEnabled(false);
                 viewHolder.butInvoice.setEnabled(false);
   //              viewHolder.butCancel.setEnabled(false);
                 
                 viewHolder.checkConformExecutionStore.setEnabled(false);
                 
                 viewHolder.butInvoice.setVisibility(View.INVISIBLE);
     //            viewHolder.butCancel.setVisibility(View.GONE);
                 viewHolder.checkConformExecutionStore.setVisibility(View.INVISIBLE);
                 viewHolder.checkConformExecutionStore.setChecked(false);
              
	          
	          
            }
            else if(Integer.parseInt(TagSstat1)==10)//what ever status you want
            {
            	  viewHolder.text.setTextColor(Color.parseColor("#9C27B0"));
            	 viewHolder.text.setEnabled(false);
                 viewHolder.butInvoice.setEnabled(false);
                 viewHolder.butInvoice.setVisibility(View.INVISIBLE);
                 viewHolder.checkConformExecutionStore.setVisibility(View.VISIBLE);
                 viewHolder.checkConformExecutionStore.setChecked(true);
                 viewHolder.checkConformExecutionStore.setButtonDrawable(context.getResources().getDrawable(R.drawable.checked));
            }
            else if(Integer.parseInt(TagSstat1)==1)
            {
            	viewHolder.text.setTextColor(Color.MAGENTA);
            	viewHolder.butInvoice.setTextColor(Color.MAGENTA); 
     //           viewHolder.butCancel.setTextColor(Color.MAGENTA); 
                viewHolder.checkConformExecutionStore.setChecked(false);
                 
            }
            else if(Integer.parseInt(TagSstat1)==4)
            {
         	   viewHolder.text.setTextColor(Color.BLUE);
         	   viewHolder.butInvoice.setTextColor(Color.BLUE); 
  //             viewHolder.butCancel.setTextColor(Color.BLUE); 
               viewHolder.text.setEnabled(false);
               viewHolder.butInvoice.setEnabled(false);
 //              viewHolder.butCancel.setEnabled(false);
               
               viewHolder.checkConformExecutionStore.setEnabled(false);
               
               viewHolder.butInvoice.setVisibility(View.INVISIBLE);
   //            viewHolder.butCancel.setVisibility(View.GONE);
               viewHolder.checkConformExecutionStore.setVisibility(View.INVISIBLE);
               viewHolder.checkConformExecutionStore.setChecked(false);
            }
            else
            {
            	 viewHolder.text.setTextColor(Color.BLACK);
           	     viewHolder.butInvoice.setTextColor(Color.BLACK); 
    //             viewHolder.butCancel.setTextColor(Color.BLACK); 
                 viewHolder.text.setEnabled(true);
                 viewHolder.butInvoice.setEnabled(true);
   //              viewHolder.butCancel.setEnabled(true);
                 viewHolder.checkConformExecutionStore.setEnabled(true);
                 viewHolder.checkConformExecutionStore.setChecked(false);
                 
            }
        viewHolder.butInvoice.setOnClickListener(new OnClickListener() {
            @Override  
            public void onClick(View v)
            {  
            	viewHolder.text.getText();
            	final StringTokenizer ad = new StringTokenizer(String.valueOf(hmapStoreIdAndInvoiceOtherDetails1.get(viewHolder.butInvoice.getTag())), "_");
           		String TagStoreID= ad.nextToken().trim();
          		String TagOrderID= ad.nextToken().trim();
          		String TagRouteID= ad.nextToken().trim();
          		String TagDistID= ad.nextToken().trim();
          		String TagDate= ad.nextToken().trim();
          		String TagSstat= ad.nextToken().trim();
          		Intent intent = new Intent(context, InvoiceProductList.class);
            	intent.putExtra("imei", imei1);
            	intent.putExtra("SelectStoreTag", hmapStoreIdAndInvoiceOtherDetails1.get(viewHolder.butInvoice.getTag()));
            	intent.putExtra("StoreID", TagStoreID);
            	intent.putExtra("StoreName", hmapStoreIdName1.get(viewHolder.butInvoice.getTag()));
            	intent.putExtra("currSysDate", currSysDate1);
            	intent.putExtra("pickerDate", pickerDate1);
            	intent.putExtra("spinnerSlctd", spinnerRouteSlctd);
            	intent.putExtra("spnrDistSlctd", spinnerDistSlctd);
            	
            	context.startActivity(intent);
            	context.finish();
            }  
        });  
/*        viewHolder.butCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) { 
            	temp=viewHolder.text.getText().toString();
            	if (customListner != null)
                {  
            		customListner.onButtonClickListner(position,temp,viewHolder.text,viewHolder.butInvoice,viewHolder.butCancel,viewHolder.checkConformExecutionStore,1 ); 
                } 
             }
        });*/
        
        
        viewHolder.checkConformExecutionStore.setOnClickListener(new OnClickListener() {
			
            @Override
            public void onClick(final View v) { 
            	temp=viewHolder.text.getText().toString();
            	if (customListner != null)
                {  
            		//customListner.onButtonClickLisner(position,temp,viewHolder.text,viewHolder.butInvoice,viewHolder.butCancel,viewHolder.checkConformExecutionStore,2); 
            		customListner.onButtonClickListner(position,temp,viewHolder.text,viewHolder.butInvoice,viewHolder.checkConformExecutionStore,2,viewHolder.checkConformExecutionStore.getTag().toString());
                } 
             }
        });
        
       /* viewHolder.checkConformExecutionStore = null;
        viewHolder.text = null;  
        viewHolder.butInvoice = null;  */
        
        
        
        return view;
        //return view; 
        
        
    }  
    static class ViewHolder {  
        TextView text;  
       // Button butCancel; 
        Button butInvoice;  
        CheckBox checkConformExecutionStore;
    }
}  