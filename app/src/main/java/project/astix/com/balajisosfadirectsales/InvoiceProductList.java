package project.astix.com.balajisosfadirectsales;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class InvoiceProductList  extends BaseActivity implements OnItemSelectedListener
{

	public String imei;
	public String StoreName;
	public String currSysDate;
	public String pickerDate; 
	public TableLayout tl2;
	//public ProgressDialog pDialogSync;
	public String[] CATID;
	public String[] CATIDFomProduct;
	 public String[] CATDesc;
	 public Spinner category_spinner;
	 int spinnerDistSlctd;
	int spinnerRouteSelected;
	 public String fullFileName1;
	 public long syncTIMESTAMP;
	 
	 public String[] PID;
		public String[] PName;
	Button But_Conform_Select_Invoice;
		
		Button But_Cancel_Select;
		
		
		public String[] rt;
		 public String[] Oqty;
		
		 public String[] delvQty;
		 public String[] delvfreeQty;
		 public String[] delvDiscount;
		 
		 
		public String[] ProductID;
		public String[] ProductName;
		public String[] rate;
		public String[] OrderQty;
		public String[] ProdpSampleQty;
		public String[] PDisplayUnit;
		public String[] pDisplayUnit;
		
		
		
		public String[] DeliverQty;
		public String[] FreeQty;
		public String[] DiscountValue;
		
		
		public String selected_Competitor_id="0";
		public CheckBox chkIos;
		
	 PRJDatabase dbengine = new PRJDatabase(this);
	 
	 public String[] pName;
		
		public String[] LODQTY;
		public Double[] rte;
		public int[] stk;
		public int[] oQty;
		public int[] DelQty;
		public int[] fQty;
		public Double[] dVal;
		
		ImageView storeBackDet;
		//Button storeSubmit;
		//Button storeSaveOnly;//Only Save
		public int syncClick = 0;
		String StoreID="0";
		public String SelectStoreTag;
		
		
		public String TagStoreID;
		public String TagOrderID;
		public String TagDistID;
		public String TagRouteID;
		public String TagDate;
		
		public TableRow dataRow;
		
		InvoiceDatabaseAssistant DA = new InvoiceDatabaseAssistant(this); 
		
	 public String Storename;
	 public String Noti_text="Null";
		public int MsgServerID=0;
		
		public EditText etadditionalDiscountValue;
	 
	 
	 
	 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			try
			{

			dbengine.open();
			String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
			dbengine.close();
			System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
			if(!Noti_textWithMsgServerID.equals("Null"))
			{
			StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");
			
			MsgServerID= Integer.parseInt(token.nextToken().trim());
			Noti_text= token.nextToken().trim();
			
			
			if(Noti_text.equals("") || Noti_text.equals("Null"))
			{
				
			}
			else
			{
				
				

				 final AlertDialog builder = new AlertDialog.Builder(InvoiceProductList.this).create();
			       

					LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			        View openDialog = inflater.inflate(R.layout.custom_dialog, null);
			        openDialog.setBackgroundColor(Color.parseColor("#ffffff"));
			        
			        builder.setCancelable(false);
			     	TextView header_text=(TextView)openDialog. findViewById(R.id.txt_header);
			     	final TextView msg=(TextView)openDialog. findViewById(R.id.msg);
			     	
					final Button ok_but=(Button)openDialog. findViewById(R.id.but_yes);
					final Button cancel=(Button)openDialog. findViewById(R.id.but_no);
					
					cancel.setVisibility(View.GONE);
				    header_text.setText(getText(R.string.AlertDialogHeaderMsg));
				     msg.setText(Noti_text);
				     	
				     	ok_but.setText(getText(R.string.AlertDialogOkButton));
				     	
						builder.setView(openDialog,0,0,0,0);

				        ok_but.setOnClickListener(new OnClickListener() 
				        {
							
							@Override
							public void onClick(View arg0) 
							{
								// TODO Auto-generated method stub

								long syncTIMESTAMP = System.currentTimeMillis();
								Date dateobj = new Date(syncTIMESTAMP);
								SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
								String Noti_ReadDateTime = df.format(dateobj);
					    	
								dbengine.open();
								dbengine.updatetblNotificationMstr(MsgServerID,Noti_text,0,Noti_ReadDateTime,3);
								dbengine.close();
								
								try
								{
									dbengine.open();
								    int checkleftNoti=dbengine.countNumberOFNotificationtblNotificationMstr();
								    if(checkleftNoti>0)
								    {
									    	String Noti_textWithMsgServerID=dbengine.fetchNoti_textFromtblNotificationMstr();
											System.out.println("Sunil Tty Noti_textWithMsgServerID :"+Noti_textWithMsgServerID);
											if(!Noti_textWithMsgServerID.equals("Null"))
											{
												StringTokenizer token = new StringTokenizer(String.valueOf(Noti_textWithMsgServerID), "_");
												
												MsgServerID= Integer.parseInt(token.nextToken().trim());
												Noti_text= token.nextToken().trim();
												
												dbengine.close();
												if(Noti_text.equals("") || Noti_text.equals("Null"))
												{
													
												}
												else
												{
													  msg.setText(Noti_text);
												}
											}
								    	
								    }
									else
									{
										builder.dismiss();
									}
								
								}
								catch(Exception e)
								{
									
								}
					            finally
					            {
					            	dbengine.close();
								   
					            }
				            
							
							}
						});
				        
				       
				      
				 
				     	builder.show();
					
					
					

			
				 
			}
			}
			}
			catch(Exception e)
			{
				
			}
			
		}
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invoice_productlist);
		Intent passedvals = getIntent();
		imei = passedvals.getStringExtra("imei");
		StoreName = passedvals.getStringExtra("StoreName");
		currSysDate = passedvals.getStringExtra("currSysDate");
		pickerDate = passedvals.getStringExtra("pickerDate");
		StoreID = passedvals.getStringExtra("StoreID");
		SelectStoreTag = passedvals.getStringExtra("SelectStoreTag");
		spinnerRouteSelected=passedvals.getIntExtra("spinnerSlctd", 0);
		spinnerDistSlctd=passedvals.getIntExtra("spnrDistSlctd", 0);
		
		
		System.out.println("pickerDate in InvoiceProductList :"+pickerDate);
		System.out.println("Hari singh imei recevie oncreate :"+imei);
		
		StringTokenizer ad = new StringTokenizer(String.valueOf(SelectStoreTag), "_");
		
		TagStoreID= ad.nextToken().trim();
		TagOrderID= ad.nextToken().trim();
		TagRouteID= ad.nextToken().trim();
		TagDistID= ad.nextToken().trim();
		TagDate= ad.nextToken().trim();
		System.out.println("Indresh Baba ="+SelectStoreTag);
		
		TextView DistriName = (TextView)findViewById(R.id.textView1_schemeVAL1111);
		TextView StrName = (TextView)findViewById(R.id.textView1_schemeVAL);
		
		System.out.println("Dangi new  testing StoreID on Invoice ProductList :"+StoreID);
		System.out.println("Dangi new  testing SelectStoreTag on Invoice ProductList :"+SelectStoreTag);
		
		 tl2 = (TableLayout) findViewById(R.id.dynprodtable);
		 dbengine.open();
		String Distname=dbengine.FetchDistNameBasedDistID(TagDistID);
		Storename=dbengine.FetchStoreNameBasedStoreID(TagStoreID,TagDate);
		CATID=dbengine.FetchCategoryID(); 
		CATDesc=dbengine.FetchCategoryDesc();
		dbengine.close();
			
			DistriName.setText(Distname);
			StrName.setText(Storename);
			setUpvariable();
			
			System.out.println("Sameer 1");
			
			if(CATID.length>0)
			{
				System.out.println("Sameer 2");
				dbengine.open();
				PID = dbengine.FetchPidInvoice(TagStoreID,TagDate,TagOrderID);
				System.out.println("Singh Testing PID one:");
				System.out.println("Singh Testing PID :"+PID.length);
				 if(PID.length>0)
					{
					 But_Conform_Select_Invoice.setEnabled(true);
					 But_Cancel_Select.setEnabled(true);
				    	//storeSaveOnly.setEnabled(true);
					}
				    else
				    {
				    	But_Conform_Select_Invoice.setEnabled(false);
				    	But_Cancel_Select.setEnabled(false);
				    	//storeSaveOnly.setEnabled(false);
				    }
				    
				System.out.println("Sameer 3");
				//PName = dbengine.FetchPNameInvoice(StoreID);
				//PName = dbengine.FetchPNameInvoice();
				rt = dbengine.FetchRateInvoice(TagStoreID,TagDate,TagOrderID);
				Oqty=dbengine.FetchOrderQtyInvoice(TagStoreID,TagDate,TagOrderID);
				delvDiscount=dbengine.FetchOrderDiscountInvoice(TagStoreID,TagDate,TagOrderID);
				
				delvfreeQty=dbengine.FetchOrderFreeQtyInvoice(TagStoreID,TagDate,TagOrderID);
				/*PID = dbengine.FetchPidInvoice();
				//PName = dbengine.FetchPNameInvoice(StoreID);
				PName = dbengine.FetchPNameInvoice();
				rt = dbengine.FetchRateInvoice();*/
				//Oqty=dbengine.FetchOrderQtyInvoice(StoreID);
				
				CATIDFomProduct=dbengine.FetchCategoryIDfromInvoiceProduct(); 
				dbengine.close();
				
				
				ProductID = new String[PID.length];
				//ProductName = new String[PName.length];
				rate = new String[rt.length];
				OrderQty= new String[Oqty.length];
				
				
				
				category_spinner=(Spinner)findViewById(R.id.competition_spinner);
				category_spinner.setOnItemSelectedListener(this);
			    ArrayAdapter aa=new ArrayAdapter(this, android.R.layout.simple_spinner_item,CATDesc);
			    aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    category_spinner.setAdapter(aa);
			  
				
				//ProdpSampleQty= new String[PName.length];
				
				ProductID = PID;
				//ProductName = PName;
				pDisplayUnit=PDisplayUnit;
				rate = rt;
				OrderQty=Oqty;
				
				
				 LayoutInflater inflater = getLayoutInflater();
				 
				 for (int current = 0; current <= (ProductID.length - 1); current++) 
			        {
						final TableRow row = (TableRow)inflater.inflate(R.layout.invoice_table_row, tl2 , false);
						
						row.setTag(CATIDFomProduct[current]);
						//row.setTag(CATID[current]);
						row.setVisibility(View.VISIBLE);
						TextView tv1 = (TextView)row.findViewById(R.id.tvProd);
						
						final EditText et1 = (EditText)row.findViewById(R.id.tvRate);
						final TextView tv2 = (TextView)row.findViewById(R.id.etOrderQty);
						final EditText et2 = (EditText)row.findViewById(R.id.etDeliverValue);
						final EditText et3 = (EditText)row.findViewById(R.id.tvFreeQty);
						final EditText et4 = (EditText)row.findViewById(R.id.tvDiscountVal);
						
						tv1.setTag(current);
						dbengine.open();
						String PName=dbengine.FetchPNameInvoice(ProductID[current]);
						dbengine.close();
						tv1.setText(PName);
						tv1.setTextSize(12);
						et1.setTag(current);
						et1.setTextSize(12);
						
						et2.setText(OrderQty[current]);
						et2.setTextSize(12);
						
						et3.setText(delvfreeQty[current]);
						et3.setTextSize(12);
						
						et4.setText(delvDiscount[current]);
						et4.setTextSize(12);
						
						
						et1.setText(rate[current]);
						
						tv2.setText(OrderQty[current]);
						tv2.setTextSize(12);
						tl2.addView(row);
						
			        }
				
				
				
				
				
				
			}
	}
	
	public void setUpvariable()
	{
		etadditionalDiscountValue=(EditText)findViewById(R.id.tvadditionalDiscountValue);
		
		But_Conform_Select_Invoice=(Button) findViewById(R.id.But_Conform_Select_Invoice);
		
		But_Cancel_Select=(Button) findViewById(R.id.But_Cancel_Select);
		//Button But_Submit=(Button) findViewById(R.id.But_Submit);
		
		But_Conform_Select_Invoice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				int picsCHK = 0;//dbengine.getExistingPicNos(fStoreID);
				//dbengine.close();
				
				if(picsCHK <= 0 && isOnline()){
					
					showSubmitConfirm();	
				}
				else{
					
					showNoConnAlert();
					
				}
				
			}
		});
		
		
		But_Cancel_Select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				

				  
				  
	              //do something
	            
	              AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(InvoiceProductList.this);
					alertDialogNoConn.setTitle(getText(R.string.genTermInformation));
					alertDialogNoConn.setMessage(getText(R.string.CancelOrder)+Storename);
					
					alertDialogNoConn.setPositiveButton(getText(R.string.AlertDialogYesButton),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) 
								{ 
									
									
				                      dbengine.UpdateProductCancelStoreFlag(TagOrderID.trim(),1);
				                      dbengine.open();
				                      dbengine.saveInvoiceButtonStoreTransac("NA",TagDate,TagStoreID,"0","0",0.0, 0,
				                              0, 0,TagOrderID,"","9",1,0.0,TagRouteID,"0");
				                      dbengine.close();
									Intent fireBackDetPg = new Intent(InvoiceProductList.this, InvoiceStoreSelection.class);
								
								
								fireBackDetPg.putExtra("imei", imei);
								fireBackDetPg.putExtra("currSysDate", currSysDate);
								fireBackDetPg.putExtra("pickerDate", pickerDate);
								fireBackDetPg.putExtra("spinnerSlctd", spinnerRouteSelected);
								fireBackDetPg.putExtra("spnrDistSlctd", spinnerDistSlctd);
								
								startActivity(fireBackDetPg);
								finish();
								}
							});
					alertDialogNoConn.setNegativeButton(getText(R.string.AlertDialogNoButton),
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) 
								{
		                      dialog.dismiss();
		                      //chkIos.setChecked(false);
		                     
								}
							});
					alertDialogNoConn.setIcon(R.drawable.info_ico);
					AlertDialog alert = alertDialogNoConn.create();
					alert.show();
					return;
	             
	             
	          
				  
				 
		                
				
		 
			  
				
			}
		});
	
		
		
		storeBackDet = (ImageView)findViewById(R.id.btn_bck);
	   
	  //  storeSaveOnly = (Button)findViewById(R.id.button33);//Only Save
	    
	   
		storeBackDet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent fireBackDetPg = new Intent(InvoiceProductList.this, InvoiceStoreSelection.class);
				
			/*	fireBackDetPg.putExtra("SID", fStoreID);
				fireBackDetPg.putExtra("SN", SN);
				fireBackDetPg.putExtra("bck", 1);*/
				
				fireBackDetPg.putExtra("imei", imei);
				fireBackDetPg.putExtra("currSysDate", currSysDate);
				fireBackDetPg.putExtra("pickerDate", pickerDate);
				fireBackDetPg.putExtra("spinnerSlctd", spinnerRouteSelected);
				fireBackDetPg.putExtra("spnrDistSlctd", spinnerDistSlctd);
				startActivity(fireBackDetPg);
				finish();
			}
		});
		
		
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		int index=0;
		  LayoutInflater inflater = getLayoutInflater();
		  index=category_spinner.getSelectedItemPosition();
		 selected_Competitor_id=CATID[index];
		 
		 
		 //LayoutInflater inflater = getLayoutInflater();
		 
		 for (int current = 0; current <= (ProductID.length - 1); current++) 
	        {
			 	dataRow = (TableRow)tl2.getChildAt(current);
				//final TableRow row = (TableRow)inflater.inflate(R.layout.table_row, tl2 , false);
				//row.setTag(CATID[current]);
			 	
			 	if(index ==0)
			 	{
			 		dataRow.setVisibility(View.VISIBLE);
			 		selected_Competitor_id="0";
			 	}
			 	else
			 	{
					if(Integer.parseInt(selected_Competitor_id)==Integer.parseInt(dataRow.getTag().toString()))
					{
						dataRow.setVisibility(View.VISIBLE);
					}
					else
					{
						dataRow.setVisibility(View.GONE);
					}
			 	}
				
	        }
	

       
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	

	public void showSubmitConfirm() {
		AlertDialog.Builder alertDialogSubmitConfirm = new AlertDialog.Builder(InvoiceProductList.this);
		alertDialogSubmitConfirm.setTitle(R.string.genTermInformation);
		alertDialogSubmitConfirm.setMessage(getText(R.string.genTernStoreOrderInvoicesubmitConfirmAlert));
		alertDialogSubmitConfirm.setCancelable(false);
		
		alertDialogSubmitConfirm.setNeutralButton(R.string.AlertDialogYesButton, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						syncClick = 1;
						
						//storeSubmit.setEnabled(false);
						//storeSave4Later.setEnabled(false);
						//storeSaveOnly.setEnabled(false);
						//storeBackDet.setEnabled(false);
						
						String additionalDiscountValue="0";
						
						if(TextUtils.isEmpty(etadditionalDiscountValue.getText().toString().trim()))
						{
							additionalDiscountValue="0";
						}
						else
						{
							additionalDiscountValue=etadditionalDiscountValue.getText().toString().trim();
						}
						
						
						
				       
					//	dialog.dismiss();
						
						dbengine.open();
						// change by sunil
						dbengine.deleteOldInvoiceButtonStoreTransac(StoreID);
						//dbengine.deleteOldStoreInvoice(fStoreID);
						dbengine.close();
						//for(int countRow = 0; countRow <= tl2.getChildCount()-1; countRow++){
						for (int countRow = 0; countRow <= (ProductID.length - 1); countRow++)
						{
							
							int haveRows = tl2.getChildCount();
							String CurrentRowCategoryId=(String)((TableRow)tl2.getChildAt(countRow)).getTag().toString();
							pName = new String[haveRows];
							
							rte = new Double[haveRows];
							
							oQty = new int[haveRows];
							DelQty = new int[haveRows];
							fQty = new int[haveRows];
							dVal=new Double[haveRows];
							
							TextView child = (TextView)((TableRow)tl2.getChildAt(countRow)).findViewById(R.id.tvProd);//(TextView)dataRow.getChildAt(0);
							pName[countRow] = child.getText().toString().trim();
							
									
							EditText child2 = (EditText)((TableRow)tl2.getChildAt(countRow)).findViewById(R.id.tvRate);//(TextView)dataRow.getChildAt(2);
							rte[countRow] = Double.parseDouble(child2.getText().toString().trim());
							
							
							TextView child3 =(TextView)((TableRow)tl2.getChildAt(countRow)).findViewById(R.id.etOrderQty);// (EditText)dataRow.getChildAt(4);
							if(!child3.getText().toString().isEmpty()){
								oQty[countRow] = Integer.parseInt(child3.getText().toString().trim());
							}
							else{
								oQty[countRow] = 0;
							}
							
							EditText child4 = (EditText)((TableRow)tl2.getChildAt(countRow)).findViewById(R.id.etDeliverValue);//(TextView)dataRow.getChildAt(5);
							if(!child4.getText().toString().isEmpty()){
								DelQty[countRow] = Integer.parseInt(child4.getText().toString().trim());
							}
							else{
								DelQty[countRow] = 0;
							}
							
							EditText child5 = (EditText)((TableRow)tl2.getChildAt(countRow)).findViewById(R.id.tvFreeQty);//(TextView)dataRow.getChildAt(6);
							if(!child5.getText().toString().isEmpty()){
								fQty[countRow] = Integer.parseInt(child5.getText().toString().trim());
							}
							else{
								fQty[countRow] = 0;
							}
							
							EditText child6 = (EditText)((TableRow)tl2.getChildAt(countRow)).findViewById(R.id.tvDiscountVal);//(TextView)dataRow.getChildAt(6);
							if(!child6.getText().toString().isEmpty()){
								dVal[countRow] = Double.parseDouble(child6.getText().toString().trim());
							}
							else{
								dVal[countRow] = 0.0;
							}
							
							dbengine.open();
							// change by sunil
							//String OrderID=dbengine.FetchOrderIDInvoice(ProductID[countRow]);
							//String OrderID=dbengine.FetchOrderIDInvoiceStoremstr(StoreID);
							
							//TagStoreID= ad.nextToken().trim();
							//TagOrderID= ad.nextToken().trim();
							
							dbengine.saveInvoiceButtonStoreTransac(imei,TagDate,
									TagStoreID,ProductID[countRow],pName[countRow],rte[countRow], oQty[countRow],
									DelQty[countRow], fQty[countRow],TagOrderID,CurrentRowCategoryId,"9",0,dVal[countRow],TagRouteID,additionalDiscountValue);
							//dbengine.saveStoreTransac(imei, pickerDate, StoreID, ProductID[countRow], stk[countRow], oQty[countRow], oVal[countRow], fQty[countRow], dVal[countRow], AppliedSchemeID, AppliedSlab, AppliedAbsVal, newSampleQty, pName[countRow], rte[countRow],CurrentRowCategoryId);//, DisplayName
							dbengine.close();
						}
						
						
						
						dbengine.UpdateProductCancelStoreFlag(TagOrderID,0);
						
						//dialog.dismiss();

						Intent fireBackDetPg = new Intent(InvoiceProductList.this, InvoiceStoreSelection.class);
						
						
						fireBackDetPg.putExtra("imei", imei);
						fireBackDetPg.putExtra("currSysDate", currSysDate);
						fireBackDetPg.putExtra("pickerDate", pickerDate);
						fireBackDetPg.putExtra("spinnerSlctd", spinnerRouteSelected);
						fireBackDetPg.putExtra("spnrDistSlctd", spinnerDistSlctd);
						
						startActivity(fireBackDetPg);
						finish();
						
						// change by sunil
						/*try {
							//new FullSyncDataNow().execute().get();
							
							FullSyncDataNow task = new FullSyncDataNow(InvoiceProductList.this);
							 task.execute();
						   
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						
						//
					
					}
				});
		
		alertDialogSubmitConfirm.setNegativeButton(R.string.AlertDialogNoButton, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				

			}
		});
		
		alertDialogSubmitConfirm.setIcon(R.drawable.info_ico);
		
		AlertDialog alert = alertDialogSubmitConfirm.create();
		
		 /*Window window = alert.getWindow();
		 window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		 window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
		 window.setBackgroundDrawableResource(android.R.color.darker_gray);*/
	     
		alert.show();
		
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		  // TODO Auto-generated method stub
		  if(keyCode==KeyEvent.KEYCODE_BACK){
		   return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_HOME){
		   return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_MENU){
			  return true;
		  }
		  if(keyCode==KeyEvent.KEYCODE_SEARCH){
			  return true;
		  }

		  return super.onKeyDown(keyCode, event);
		 }
	 
	/* private class FullSyncDataNow extends AsyncTask<Void, Void, Void> {
			

		 ProgressDialog pDialogGetStores;
			public FullSyncDataNow(InvoiceProductList activity) 
			{
				pDialogGetStores = new ProgressDialog(activity);
			}
		 
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				
				pDialogSync = ProgressDialog.show(InvoiceProductList.this,getText(R.string.genTermPleaseWaitNew),getText(R.string.genTernSubmittingOrderDetails), true);
				pDialogSync.setIndeterminate(true);
				pDialogSync.setCancelable(false);
				pDialogSync.setCanceledOnTouchOutside(false);
				
				//getWindow().addFlags(WindowManager.LayoutParams.FLAG_);
				 Window window = pDialogSync.getWindow();
				 window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
				 window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
			     //window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
			     window.setBackgroundDrawableResource(android.R.color.background_dark);
			     
			   pDialogSync.show();
			   
			     //window.requestFeature(window.FEATURE_NO_TITLE);
			   
			   
			   pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
				pDialogGetStores.setMessage("Submitting Order Details...");
				pDialogGetStores.setIndeterminate(false);
				pDialogGetStores.setCancelable(false);
				pDialogGetStores.setCanceledOnTouchOutside(false);
				pDialogGetStores.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				try {
					SyncNow();
					}

				 catch (Exception e) {
					Log.i("Sync ASync", "Sync ASync Failed!", e);

				}

				finally {

				}
				syncTIMESTAMP = System.currentTimeMillis();
				Date dateobj = new Date(syncTIMESTAMP);
				
				System.out.println("Hari singh imei :"+imei);
				
				SimpleDateFormat df = new SimpleDateFormat(imei + ".dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);
				fullFileName1 = df.format(dateobj);
				//fullFileName2 = df.format(dateobj) + "_2";
				
					String printINTERNALPATH0 = Environment.getExternalStorageDirectory() + "/NMPdata/" + fullFileName1 + ".zip";
					String printINTERNALPATH = Environment.getDataDirectory() + "/NMPdata/" + fullFileName1 + ".zip";
					String printINTERNALPATH2 = Environment.getRootDirectory() + "/NMPdata/" + fullFileName1 + ".zip";
					
					 //System.out.println("printINTERNALPATH0: " + printINTERNALPATH0);
					 //System.out.println("printINTERNALPATH: " + printINTERNALPATH);
					 //System.out.println("printINTERNALPATH2: "+ printINTERNALPATH2);
				 
				
				try {
					 File MeijiIndirectSFAxmlFolder = new File(Environment.getExternalStorageDirectory(), "TJUKSFAInvoicexml");
						
					 if (!MeijiIndirectSFAxmlFolder.exists()) 
						{
						 MeijiIndirectSFAxmlFolder.mkdirs();
							 
						} 
				
					DA.open();
					DA.export(dbengine.DATABASE_NAME, fullFileName1,1);
					//DA.export("PROdb", fullFileName2);
					DA.close();
					
					pDialogSync.dismiss();
		 
					Intent syncIntent = new Intent(InvoiceProductList.this, InvoiceSyncMaster.class);
					syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/NMPdata/" + fullFileName1 + ".xml");
					syncIntent.putExtra("OrigZipFileName", fullFileName1);
					syncIntent.putExtra("whereTo", "Regular");
					
					syncIntent.putExtra("imei", imei);
					syncIntent.putExtra("currSysDate", currSysDate);
					syncIntent.putExtra("pickerDate", pickerDate);
					startActivity(syncIntent);
					

				} catch (IOException e) {
				
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onCancelled() {
				
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if(pDialogGetStores.isShowing()) 
			      {
			    	   pDialogGetStores.dismiss();
				  }
				try
				{
				
				Intent syncIntent = new Intent(InvoiceProductList.this, InvoiceSyncMaster.class);
				syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/TJUKSFAInvoicexml/" + fullFileName1 + ".xml");
				syncIntent.putExtra("OrigZipFileName", fullFileName1);
				syncIntent.putExtra("whereTo", "11");
				
				syncIntent.putExtra("imei", imei);
				syncIntent.putExtra("currSysDate", currSysDate);
				syncIntent.putExtra("pickerDate", pickerDate);
				startActivity(syncIntent);
				finish();
				

			} catch (Exception e) {
			
				e.printStackTrace();
			}
			}
		}
	 
	 public void SyncNow(){
			
			syncTIMESTAMP = System.currentTimeMillis();
			Date dateobj = new Date(syncTIMESTAMP);
			
			System.out.println("Hari singh imei :"+imei);
			
			SimpleDateFormat df = new SimpleDateFormat(imei + ".dd.MM.yyyy.HH.mm.ss",Locale.ENGLISH);
			fullFileName1 = df.format(dateobj);
			//fullFileName2 = df.format(dateobj) + "_2";
			
				String printINTERNALPATH0 = Environment.getExternalStorageDirectory() + "/NMPdata/" + fullFileName1 + ".zip";
				String printINTERNALPATH = Environment.getDataDirectory() + "/NMPdata/" + fullFileName1 + ".zip";
				String printINTERNALPATH2 = Environment.getRootDirectory() + "/NMPdata/" + fullFileName1 + ".zip";
				
				 //System.out.println("printINTERNALPATH0: " + printINTERNALPATH0);
				 //System.out.println("printINTERNALPATH: " + printINTERNALPATH);
				 //System.out.println("printINTERNALPATH2: "+ printINTERNALPATH2);
			 
			
			try {
				 File MeijiIndirectSFAxmlFolder = new File(Environment.getExternalStorageDirectory(), "TJUKSFAInvoicexml");
					
				 if (!MeijiIndirectSFAxmlFolder.exists()) 
					{
					 MeijiIndirectSFAxmlFolder.mkdirs();
						 
					} 
			
				DA.open();
				DA.export(dbengine.DATABASE_NAME, fullFileName1,1);
				//DA.export("PROdb", fullFileName2);
				DA.close();
				
				pDialogSync.dismiss();
	 
				Intent syncIntent = new Intent(InvoiceProductList.this, InvoiceSyncMaster.class);
				syncIntent.putExtra("xmlPathForSync", Environment.getExternalStorageDirectory() + "/TJUKSFAInvoicexml/" + fullFileName1 + ".xml");
				syncIntent.putExtra("OrigZipFileName", fullFileName1);
				syncIntent.putExtra("whereTo", "Regular");
				
				syncIntent.putExtra("imei", imei);
				syncIntent.putExtra("currSysDate", currSysDate);
				syncIntent.putExtra("pickerDate", pickerDate);
				startActivity(syncIntent);
				

			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
		}
*/}
