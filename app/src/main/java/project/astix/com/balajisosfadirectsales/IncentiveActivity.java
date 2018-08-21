package project.astix.com.balajisosfadirectsales;



        import java.util.ArrayList;
        import java.util.LinkedHashMap;
        import java.util.Map;
        import java.util.regex.Pattern;


        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.Gravity;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup.LayoutParams;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;


public class IncentiveActivity extends Activity
{
    PRJDatabase dbengine=new PRJDatabase(IncentiveActivity.this);
    public LinearLayout ll_Parent;
    ProgressDialog pDialogGetStores;
    public String Total_Earning="NA",DisplayMsg="NA";
    public TextView txt_total_earned,txt_display_msg;
    public LinearLayout ll_txt_display_msg;
    public String imei,pickerDate,userDate;

    public ImageView imgVw_next,imgVw_back;
    ArrayList<LinkedHashMap<String, ArrayList<String>>> list_IncentiveMstrData;
    ArrayList<Object> arrLstObjct;

    LinkedHashMap<String, ArrayList<String>> HmapIncIdTypeNameCount;
    LinkedHashMap<String, ArrayList<String>> HmapIncIdColumnNameAndData;
    LinkedHashMap<String, ArrayList<String>> HmapIncPastDetailColData;

    public boolean onKeyDown(int keyCode, KeyEvent event)
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incentive);


        ll_Parent=(LinearLayout) findViewById(R.id.ll_Parent);
        txt_total_earned=(TextView) findViewById(R.id.txt_total_earned);

        ll_txt_display_msg=(LinearLayout) findViewById(R.id.ll_txt_display_msg);
        txt_display_msg=(TextView) findViewById(R.id.txt_display_msg);

        imgVw_next=(ImageView) findViewById(R.id.imgVw_next);
        imgVw_back=(ImageView) findViewById(R.id.imgVw_back);
        Intent intent=getIntent();
        int intentFrom= intent.getIntExtra("IntentFrom", 0);
        if(intentFrom==1)
        {
            imei = intent.getStringExtra("imei").trim();
            pickerDate = intent.getStringExtra("pickerDate").trim();
            userDate = intent.getStringExtra("userDate");
        }
        GetIncentiveData getData=new GetIncentiveData(IncentiveActivity.this);
        getData.execute();

        if(intentFrom==0)
        {
            imgVw_back.setVisibility(View.GONE);
            imgVw_next.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i=new Intent(IncentiveActivity.this,AllButtonActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
        else
        {
            imgVw_next.setVisibility(View.GONE);
            imgVw_back.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent i=new Intent(IncentiveActivity.this,StoreSelection.class);
                    i.putExtra("imei", imei);
                    i.putExtra("userDate", userDate);
                    i.putExtra("pickerDate", pickerDate);
                    startActivity(i);
                    finish();
                }
            });
        }

    }

    void getDataFromDatabase()
    {
        arrLstObjct=dbengine.fetchIncentiveData();
        if(arrLstObjct.size()>0) {
            list_IncentiveMstrData = (ArrayList<LinkedHashMap<String, ArrayList<String>>>) arrLstObjct.get(0);
            HmapIncIdTypeNameCount = list_IncentiveMstrData.get(0);
            HmapIncIdColumnNameAndData = list_IncentiveMstrData.get(1);
            HmapIncPastDetailColData = list_IncentiveMstrData.get(2);


            Total_Earning = (String) arrLstObjct.get(1);
            DisplayMsg = (String) arrLstObjct.get(2);
        }
    }

    void layoutCreation()
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        for(Map.Entry<String, ArrayList<String>> entry:HmapIncIdTypeNameCount.entrySet()) //Hmap(IncID, {Type, Inc Name, ColumnCount})
        {
            View view = inflater.inflate(R.layout.inflate_incentive_header, null);

            String IncId=entry.getKey().toString().trim();

            String Inc_Type=entry.getValue().get(0);
            String Inc_Name=entry.getValue().get(1).toString().trim();
            int Inc_ColumnCount=Integer.parseInt(entry.getValue().get(2));
            String Inc_flgAchieved=entry.getValue().get(3).toString().trim();
            String Inc_Earning=entry.getValue().get(4).toString().trim();
            int Inc_PastDetailColCount=Integer.parseInt(entry.getValue().get(5).toString().trim());

            ArrayList<String> list_ColumnNameData=HmapIncIdColumnNameAndData.get(IncId); //list_ColumnNameData[0]= column names
            //list_ColumnNameData[1,2...]= column data
            ArrayList<String> list_IncPastDetailColData=new ArrayList<String>();
            if(HmapIncPastDetailColData.containsKey(IncId))
            {
                list_IncPastDetailColData=HmapIncPastDetailColData.get(IncId);
            }

            TextView text_header=(TextView) view.findViewById(R.id.text_header); //Incentive name text view
            text_header.setText(Inc_Name);

            LinearLayout ll_earnedPtAndImg=(LinearLayout) view.findViewById(R.id.ll_earnedPtAndImg);
            ll_earnedPtAndImg.setBackgroundColor(Color.parseColor("#DB290F"));

            final TextView txt_earnedpoints=(TextView) view.findViewById(R.id.txt_earnedpoints);
            txt_earnedpoints.setText("Incentive Earned\n"+Inc_Earning);

            LinearLayout ll_hdr=(LinearLayout) view.findViewById(R.id.ll_hdr);
            ll_hdr.setTag(IncId+"_"+Inc_Name+"_false");

            if(Inc_flgAchieved.equals("1"))
            {
                ll_hdr.setBackgroundColor(Color.parseColor("#5A9310"));
                ll_earnedPtAndImg.setBackgroundColor(Color.parseColor("#437500"));
            }
            else if(Inc_flgAchieved.equals("2"))
            {
                ll_hdr.setBackgroundColor(Color.parseColor("#FFB400"));
                ll_earnedPtAndImg.setBackgroundColor(Color.parseColor("#CE9100"));
            }

            final LinearLayout ll_incentiveTblData=(LinearLayout) view.findViewById(R.id.ll_incentiveTblData); //layout for visibility
            ll_incentiveTblData.setTag(IncId);

            final LinearLayout ll_incentiveTblShow=(LinearLayout) view.findViewById(R.id.ll_incentiveTblShow);

            final ImageView img_Openlayout=(ImageView) view.findViewById(R.id.img_Openlayout);

            ll_hdr.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View arg0)
                {
                    String tag=arg0.getTag().toString().trim().split(Pattern.quote("_"))[0]; //tag= Inc Id
                    String tagName=arg0.getTag().toString().trim().split(Pattern.quote("_"))[1]; //tag= Inc Id
                    String strExpandOrNot=arg0.getTag().toString().trim().split(Pattern.quote("_"))[2]; //tag= Inc Id
                    boolean isExpand=Boolean.parseBoolean(strExpandOrNot);
                    //to expand layout
                    if(isExpand)
                    {
                        arg0.setTag(tag+"_"+tagName+"_"+"false");
                        LinearLayout ll_main=(LinearLayout) ll_Parent.findViewWithTag(tag);
                        ll_main.setVisibility(View.GONE);
                        img_Openlayout.setImageResource(R.drawable.expand_arrow);


                    }
                    //to collapse layout
                    else
                    {
                        arg0.setTag(tag+"_"+tagName+"_"+"true");
                        LinearLayout ll_main=(LinearLayout) ll_Parent.findViewWithTag(tag);
                        ll_main.setVisibility(View.VISIBLE);
                        img_Openlayout.setImageResource(R.drawable.collapse_arrow);
                    }
                }
            });

            createIncentiveTables(list_IncPastDetailColData,Inc_PastDetailColCount,ll_incentiveTblShow,"Acheivement/s",Inc_Type);

            createIncentiveTables(list_ColumnNameData,Inc_ColumnCount,ll_incentiveTblShow,"Current Week",Inc_Type);

            ll_Parent.addView(view);
        }

        if(TextUtils.isEmpty(DisplayMsg) || DisplayMsg.equals("0") || DisplayMsg.equals(""))
        {
            txt_display_msg.setVisibility(View.GONE);
        }
        else
        {
            txt_display_msg.setText(DisplayMsg);
        }
        txt_total_earned.setText("Total Incentive Earned : "+Total_Earning);
    }

    void createIncentiveTables(ArrayList<String> list_ColumnNameData,int Inc_ColumnCount,LinearLayout ll_incentiveTblShow, String tbl_header,String Inc_Type)
    {
        if(Inc_ColumnCount != 0)
        {
            View v1=new View(IncentiveActivity.this);
            v1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,3));
            ll_incentiveTblShow.addView(v1);

            TextView txt_header = new TextView(this);
            txt_header.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            txt_header.setBackgroundResource(R.drawable.blue_border_1dp);
            txt_header.setText(tbl_header);
            txt_header.setTextColor(Color.parseColor("#D32F2F"));
            txt_header.setTextSize(12);
            txt_header.setGravity(Gravity.CENTER);
            txt_header.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            txt_header.setPadding(1, 5,1, 5);
            if(Inc_Type.equals("1"))
            {
                if(txt_header.getText().toString().trim().equals("Current Week"))
                {

                }
                else
                {
                    ll_incentiveTblShow.addView(txt_header);
                }
            }
            else
            {
                ll_incentiveTblShow.addView(txt_header);
            }

            // ll_incentiveTblShow.addView(txt_header);

            for(int j=0;j<list_ColumnNameData.size();j++) //list_ColumnNameData contains column name on index 0 and data on index 1,2..
            {
                if(j == 0) // Column headers
                {
                    if(Inc_ColumnCount == 1) // if only single column is to be shown then text view is created
                    {
                        TextView companyTV = new TextView(this);
                        companyTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                        companyTV.setBackgroundResource(R.drawable.blue_border_1dp);
                        companyTV.setText(list_ColumnNameData.get(j).toString().trim());
                        companyTV.setTextColor(Color.parseColor("#D32F2F"));
                        companyTV.setTextSize(12);
                        companyTV.setGravity(Gravity.CENTER);
                        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                        companyTV.setPadding(1, 5,1, 5);

                        ll_incentiveTblShow.addView(companyTV);

                    }
                    else if(Inc_ColumnCount != 1) // if more then one columns are to be shown then linear layout is made
                    {
                        LinearLayout ll = new LinearLayout(this);
                        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                        ll.setLayoutParams(lp1);
                        ll.setOrientation(LinearLayout.HORIZONTAL);
                        ll.setPadding(0, 3, 0, 3);

                        ll.setBackgroundResource(R.drawable.border_header_part_incentiv);

                        for(int i=0;i<Inc_ColumnCount;i++) //check column count to create textviews inside linear layout
                        {
                            TextView companyTV = new TextView(this);
                            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.weight=1;
                            companyTV.setGravity(Gravity.CENTER);
                            companyTV.setLayoutParams(lp);
                            companyTV.setTextSize(12);
                            companyTV.setText(list_ColumnNameData.get(j).toString().trim().split(Pattern.quote("^"))[i]);
                            companyTV.setTextColor(Color.BLACK);
                            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                            companyTV.setPadding(1, 1, 1, 1);
                            lp.setMargins(0, 0, 1, 0);

                            ll.addView(companyTV);

                        }
                        ll_incentiveTblShow.addView(ll);
                    }

                }

                if(j != 0) //for table rows
                {
                    if(Inc_ColumnCount != 0)
                    {
                        if(Inc_ColumnCount == 1)
                        {
                            TextView companyTV = new TextView(this);
                            companyTV.setBackgroundResource(R.drawable.inc_tbl_singlerow_bckgrnd);
                            companyTV.setText(list_ColumnNameData.get(j).toString().trim());
                            companyTV.setTextColor(Color.BLACK);
                            companyTV.setTextSize(11);
                            companyTV.setGravity(Gravity.CENTER);
                            companyTV.setPadding(1, 1, 1, 1);

                            ll_incentiveTblShow.addView(companyTV);

                        }
                        else if(Inc_ColumnCount != 1)
                        {
                            LinearLayout ll = new LinearLayout(this);
                            ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                            ll.setBackgroundResource(R.drawable.inc_tbl_data_bckgrnd);
                            ll.setOrientation(LinearLayout.HORIZONTAL);

                            for(int i=0;i<Inc_ColumnCount;i++)
                            {
                                TextView companyTV = new TextView(this);
                                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT);
                                lp.weight=1;
                                companyTV.setGravity(Gravity.CENTER);
                                companyTV.setLayoutParams(lp);
                                companyTV.setTextSize(11);
                                if(list_ColumnNameData.get(j).toString().split(Pattern.quote("^"))[i].equals("NA"))
                                {
                                    companyTV.setText("");
                                }
                                else
                                {
                                    companyTV.setText(list_ColumnNameData.get(j).toString().split(Pattern.quote("^"))[i]);
                                }
                                //companyTV.setTextSize(11);
                                //companyTV.setText(list_ColumnNameData.get(j).toString().trim().split(Pattern.quote("^"))[i]);
                                companyTV.setTextColor(Color.BLACK);
                                companyTV.setPadding(1, 1, 1, 1);

                                ll.addView(companyTV);

                            }
                            ll_incentiveTblShow.addView(ll);
                        }
                    }
                }
            }
            View v2=new View(IncentiveActivity.this);
            v2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,3));
            ll_incentiveTblShow.addView(v2);
        }
    }

    class GetIncentiveData extends AsyncTask<Void, Void, Void>
    {
        public GetIncentiveData(IncentiveActivity activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialogGetStores.setTitle(getResources().getString(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(getResources().getString(R.string.txtFetchingReports));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                getDataFromDatabase();
            }
            catch (Exception e)
            {}
            finally
            {}

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if(arrLstObjct.size()>0)
            {
                txt_total_earned.setVisibility(View.VISIBLE);
                ll_txt_display_msg.setVisibility(View.VISIBLE);
                layoutCreation();
            }
            else
            {
                txt_total_earned.setVisibility(View.INVISIBLE);
                ll_txt_display_msg.setVisibility(View.INVISIBLE);
            }

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }
        }
    }

}

