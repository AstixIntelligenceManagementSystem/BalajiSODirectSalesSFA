package project.astix.com.balajisosfadirectsales;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class StockRequestActivity extends BaseActivity {


    boolean serviceException=false;
    ArrayAdapter<String> dataAdapter = null;
    String[] storeNames;

    LinkedHashMap<String, String> hmapStore_details=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapPrdct_details=new LinkedHashMap<String, String>();

    LinkedHashMap<String, String> hmapUOMMstrNameId=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapUOMMstrIdName=new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> hmapBaseUOMCalcValue=new LinkedHashMap<String, String>();
    LinkedHashMap<String,ArrayList<String>> hmapUOMPrdctWise;
    StringBuilder strReqStockToSend=new StringBuilder();

    PRJDatabase dbengine = new PRJDatabase(this);
    View viewProduct;
    String date_value="";
    String imei="";
    String rID;
    int baseUOMID;
    String pickerDate="";
    public LinearLayout ll_product_stock;
    public String back="0";
    public int bck = 0;
    public LinearLayout listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_request);

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        // imei = tManager.getDeviceId();

        if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
        {
            imei = tManager.getDeviceId();
            CommonInfo.imei=imei;
        }
        else
        {
            imei= CommonInfo.imei.trim();
        }

        getAllStoreListDetail();

        initialization();
    }


    private void getAllStoreListDetail()
    {

        hmapStore_details=dbengine.fetch_Store_Req();
        hmapPrdct_details=dbengine. fetch_Store_Req_Prdct();
       ArrayList<LinkedHashMap<String,String>> listUOMData= dbengine.getUOMMstrForRqstStock();
        hmapUOMMstrNameId=listUOMData.get(0);
        hmapUOMMstrIdName=listUOMData.get(1);
         hmapUOMPrdctWise=dbengine.getPrdctMpngWithUOM();
        baseUOMID=dbengine.getBaseUOMId();
        hmapBaseUOMCalcValue=dbengine.getBaseUOMCalcValue(baseUOMID);

    }

    public void initialization()
    {

        ImageView but_back=(ImageView)findViewById(R.id.backbutton);
        Button btn_sbmt= (Button) findViewById(R.id.btn_sbmt);
        btn_sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOnline())
                {
                    if(dataSaved())
                    {
                        new GetRqstStockForDay(StockRequestActivity.this).execute();
                    }
                }
                else
                {
                    showNoConnAlert();
                }

            }
        });
        but_back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent=new Intent(StockRequestActivity.this,AllButtonActivity.class);
                startActivity(intent);
                finish();

            }
        });

        createProductDetail();
    }

    public boolean dataSaved()
    {
        boolean isDataSaved=false;
        int index=0;
        strReqStockToSend=new StringBuilder();
        for(Map.Entry<String,String> entry:hmapPrdct_details.entrySet())
        {
            String prdctId=entry.getKey();
            EditText edRqrdStk= (EditText) listView.findViewWithTag(prdctId+"_edRqstStk");
            if(edRqrdStk!=null)
            {
                if(!TextUtils.isEmpty(edRqrdStk.getText().toString()) &&(Integer.parseInt(edRqrdStk.getText().toString())>0))

                {
                    int requiredStk=Integer.parseInt(edRqrdStk.getText().toString());
                    TextView tvUOM= (TextView) listView.findViewWithTag (prdctId+"_tvUOM");
                    String uomIDSlctd="0";
                    if(tvUOM!=null)
                    {
                        Double valueInBaseUnit=0.0;
                        uomIDSlctd=hmapUOMMstrNameId.get(tvUOM.getText().toString());
                        if(Integer.parseInt(uomIDSlctd)!=baseUOMID)
                        {
                            Double conversionUnit=Double.parseDouble(hmapBaseUOMCalcValue.get(prdctId+"^"+uomIDSlctd));
                             valueInBaseUnit=conversionUnit*requiredStk;

                        }
                        else
                        {
                            valueInBaseUnit=Double.parseDouble(String.valueOf(requiredStk));
                        }
                        isDataSaved=true;
                        if(index==0)
                        {
                            strReqStockToSend.append(prdctId+"^"+valueInBaseUnit+"$"+baseUOMID+"^"+uomIDSlctd);
                        }
                        else
                        {
                            strReqStockToSend.append("|").append(prdctId+"^"+valueInBaseUnit+"$"+baseUOMID+"^"+uomIDSlctd);
                        }
                    }
                }//end  if(TextUtils.isEmpty(edRqrdStk.getText().toString()))

            }//end if(edRqrdStk!=null)


           // hmapBaseUOMCalcValue
            index++;
        }

        return isDataSaved;
    }

    public void createProductDetail() {
        LayoutInflater inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listView = (LinearLayout) findViewById(R.id.listView1);


        if(hmapPrdct_details!=null && hmapPrdct_details.size()>0)
        {
            int index=0;
           for(Map.Entry<String,String> entryPrdct:hmapPrdct_details.entrySet())
            {

                viewProduct=inflater.inflate(R.layout.list_stock_request,null);

                final String prdctId=entryPrdct.getKey();
                final String prdctName=entryPrdct.getValue();
                TextView tv_product_name=(TextView) viewProduct.findViewById(R.id.tvProdctName);
                tv_product_name.setText(prdctName);

                final TextView tvOpnStk=(TextView) viewProduct.findViewById(R.id.tvOpnStk);
                if(hmapStore_details.containsKey(prdctId+"^"+prdctName))
                {
                    tvOpnStk.setText(hmapStore_details.get(prdctId+"^"+prdctName));
                }
                else
                {
                    tvOpnStk.setText("0");
                }

                final EditText edReqStk=(EditText) viewProduct.findViewById(R.id.tvReqStk);
                edReqStk.setTag(prdctId+"_edRqstStk");

                final TextView tvUOM=(TextView) viewProduct.findViewById(R.id.tvUOM);
                tvUOM.setTag(prdctId+"_tvUOM");
                tvUOM.setText(hmapUOMMstrIdName.get(String.valueOf(baseUOMID)));

                final TextView tvFnlStock=(TextView) viewProduct.findViewById(R.id.tvFnlStock);
                if(hmapStore_details.containsKey(prdctId+"^"+prdctName))
                {
                    tvFnlStock.setText(hmapStore_details.get(prdctId+"^"+prdctName));
                }
                else
                {
                    tvFnlStock.setText("0");
                }

                edReqStk.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if(!TextUtils.isEmpty(edReqStk.getText().toString()))
                        {
                            if((Integer.parseInt(edReqStk.getText().toString())>0))
                            {
                                int finalStk=Integer.parseInt(edReqStk.getText().toString())+Integer.parseInt(tvOpnStk.getText().toString());
                                tvFnlStock.setText(""+finalStk);
                            }
                            else
                            {
                                tvFnlStock.setText(tvOpnStk.getText().toString());
                            }

                        }
                        else
                        {
                            tvFnlStock.setText(tvOpnStk.getText().toString());
                        }
                    }
                });

                tvUOM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Dialog listDialog = new Dialog(StockRequestActivity.this);
                        LayoutInflater inflater = getLayoutInflater();
                       View convertView = (View) inflater.inflate(R.layout.activity_list, null);
                        EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                        inputSearch.setVisibility(View.GONE);
                        final ListView   listUOM = (ListView)convertView. findViewById(R.id.list_view);

                        String[] UOMArray;



                        if(hmapUOMPrdctWise.containsKey(prdctId))
                        {
                            UOMArray=new String[(hmapUOMPrdctWise.get(prdctId)).size()];
                          //  LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapUOM);
                            ArrayList<String> listPrdctUOM=hmapUOMPrdctWise.get(prdctId);
                           int UomIndex=0;
                            for(String uomToSpinner:listPrdctUOM)
                            {
                                if(hmapUOMMstrIdName.containsKey(uomToSpinner))
                                {
                                    UOMArray[UomIndex]=hmapUOMMstrIdName.get(uomToSpinner);
                                    ArrayAdapter  adapterUOM = new ArrayAdapter<String>(StockRequestActivity.this, R.layout.list_item, R.id.product_name, UOMArray);
                                    listUOM.setAdapter(adapterUOM);
                                    listDialog.setContentView(convertView);
                                    listDialog.setTitle(getResources().getString(R.string.txtQualification));
                                    listUOM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            String abc=listUOM.getItemAtPosition(position).toString().trim();
                                            tvUOM.setText(abc);
                                            listDialog.dismiss();

                                        }
                                    });
                                }

                                UomIndex++;
                            }


                        }






                        listDialog.show();

                    }
                });


                listView.addView(viewProduct);

            }



        }



    }


    private class GetRqstStockForDay extends AsyncTask<Void, Void, Void>
    {

        int flgStockOut=0;
        public GetRqstStockForDay(StockRequestActivity activity)
        {

        }
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();




            // Base class method for Creating ProgressDialog
            showProgress(getResources().getString(R.string.RetrivingDataMsg));


        }

        @Override
        protected Void doInBackground(Void... args)
        {


            try
            {

                String RouteType="0";

                for(int mm = 1; mm < 2  ; mm++)
                {



                    // System.out.println("Excecuted function : "+newservice.flagExecutedServiceSuccesfully);
                    if (mm == 1) {
                       String prsnCvrgId_NdTyp=dbengine.fngetSalesPersonCvrgIdCvrgNdTyp();
                        newservice = newservice.getConfirmtionRqstStock(getApplicationContext(), strReqStockToSend.toString(),imei,prsnCvrgId_NdTyp.split(Pattern.quote("^"))[0],prsnCvrgId_NdTyp.split(Pattern.quote("^"))[1]);

                        if (!newservice.director.toString().trim().equals("1")) {

                            serviceException = true;
                            break;

                        }
                    }



                }
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


            dismissProgress();   // Base class method for dismissing ProgressDialog



            //  flgStockOut=1;
            if(serviceException)
            {
                serviceException=false;
                //showAlertStockOut("Error","Error While Retrieving Data.");
                 //showAlertException(getResources().getString(R.string.txtError),getResources().getString(R.string.txtErrorRetrievingData));
                //Toast.makeText(StockRequestActivity.this,"Please fill Stock out first for starting your market visit.", Toast.LENGTH_SHORT).show();
                //  showSyncError();
                showAlertStockOut(getString(R.string.AlertDialogHeaderMsg),getString(R.string.AlertDialogRequstionStock));
            }

            else  {
                showAlertForSubmission(getString(R.string.DataSucc));



            }




        }
    }


    public void showAlertForSubmission(String msg){
        AlertDialog.Builder alertDialogGps = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialogGps.setTitle("Information");
        alertDialogGps.setIcon(R.drawable.error_info_ico);
        alertDialogGps.setCancelable(false);
        // Setting Dialog Message
        alertDialogGps.setMessage(msg);

        // On pressing Settings button
        alertDialogGps.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(StockRequestActivity.this,AllButtonActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Showing Alert Message
        alertDialogGps.create();
        alertDialogGps.show();
    }

    public void showAlertStockOut(String title,String msg)
    {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(StockRequestActivity.this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.drawable.error);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogOkButton), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which)
            {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
}
