package project.astix.com.balajisosfadirectsales;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.astix.Common.CommonInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class DialogActivity_Report extends BaseActivity
{

    PRJDatabase dbengine = new PRJDatabase(this);
    SharedPreferences sharedPref,sharedPrefReport;
    public String	SelectedDSRValue="";
    public String SelectedDistrbtrName="";
    LinkedHashMap<String,String> hmapDistrbtrList=new LinkedHashMap<>();
    String[] Distribtr_list;
    String DbrNodeId,DbrNodeType,DbrName;
    ArrayList<String> DbrArray=new ArrayList<String>();

    LinkedHashMap<String, String> hmapdsrIdAndDescr_details=new LinkedHashMap<String, String>();
    String[] drsNames;

    public String ReasonId;
    public String ReasonText="NA";
    public static int RowId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_visit_alert);
        try
        {
            getDSRDetail();
            getDistribtrList();
        }
        catch (Exception e)
        {

        }

        initialize();

    }

    void getDistribtrList()
    {
        dbengine.open();

        Distribtr_list=dbengine.getDistributorDataMstr();

        dbengine.close();
        for(int i=0;i<Distribtr_list.length;i++)
        {
            String value=Distribtr_list[i];
            DbrNodeId=value.split(Pattern.quote("^"))[0];
            DbrNodeType=value.split(Pattern.quote("^"))[1];
            DbrName=value.split(Pattern.quote("^"))[2];
            //flgReMap=Integer.parseInt(value.split(Pattern.quote("^"))[3]);

            hmapDistrbtrList.put(DbrName,DbrNodeId+"^"+DbrNodeType);
            DbrArray.add(DbrName);
        }

    }
    private void getDSRDetail() throws IOException
    {

        int check=dbengine.countDataIntblNoVisitReasonMaster();

        hmapdsrIdAndDescr_details=dbengine.fetch_DSRCoverage_List();

        int index=0;
        if(hmapdsrIdAndDescr_details!=null)
        {
            drsNames=new String[hmapdsrIdAndDescr_details.size()];
            LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapdsrIdAndDescr_details);
            Set set2 = map.entrySet();
            Iterator iterator = set2.iterator();
            while(iterator.hasNext()) {
                Map.Entry me2 = (Map.Entry)iterator.next();
                drsNames[index]=me2.getKey().toString();
                index=index+1;
            }
        }


    }

    public void shardPrefForSalesman(int salesmanNodeId,int salesmanNodeType)
    {
         SharedPreferences.Editor editor = sharedPref.edit();
         editor.putInt("SalesmanNodeId", salesmanNodeId);
         editor.putInt("SalesmanNodeType", salesmanNodeType);
         editor.commit();

    }
    public void flgDataScopeSharedPref(int _flgDataScope)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flgDataScope", _flgDataScope);
        editor.commit();
    }

    void initialize()
    {
        sharedPrefReport = getSharedPreferences("Report", MODE_PRIVATE);
        sharedPref = getSharedPreferences(CommonInfo.Preference, MODE_PRIVATE);

        final RadioButton rb_myReport= (RadioButton) findViewById(R.id.rb_myReport);
        final RadioButton rb_dsrReport= (RadioButton) findViewById(R.id.rb_dsrReport);
        final RadioButton rb_WholeReport= (RadioButton) findViewById(R.id.rb_WholeReport);
        final Spinner spinner_dsrVisit= (Spinner) findViewById(R.id.spinner_dsrVisit);

        final RadioButton rb_dsrAttendance= (RadioButton) findViewById(R.id.rb_dsrAttendance);


        final RadioButton rb_distrbtrScope= (RadioButton) findViewById(R.id.rb_distrbtrScope);
        final Spinner spinner_distrbtrScope= (Spinner) findViewById(R.id.spinner_distrbtrScope);

        Button btn_proceed= (Button) findViewById(R.id.btn_proceed);
        Button btn_cancel= (Button) findViewById(R.id.btn_cancel);


        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(rb_myReport.isChecked())
                {
                    String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    int tempSalesmanNodeId=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    int tempSalesmanNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                    shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);

                    flgDataScopeSharedPref(1);
                    CommonInfo.SalesmanNodeId=0;
                    CommonInfo.SalesmanNodeType=0;
                    CommonInfo.flgDataScope=1;
                    Intent i=new Intent(DialogActivity_Report.this,DetailReportSummaryActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(rb_WholeReport.isChecked())
                {
                    String SONodeIdAndNodeType= dbengine.fnGetPersonNodeIDAndPersonNodeTypeForSO();

                    CommonInfo.PersonNodeID=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                    CommonInfo.PersonNodeType=Integer.parseInt(SONodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                    shardPrefForSalesman(0,0);
                    flgDataScopeSharedPref(3);
                    Intent i=new Intent(DialogActivity_Report.this,DetailReportSummaryActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(rb_dsrReport.isChecked())
                {
                    if(!SelectedDSRValue.equals("") && !SelectedDSRValue.equals("Select DSR") && !SelectedDSRValue.equals("No DSR") )
                    {

                        String DSRNodeIdAndNodeType= dbengine.fnGetDSRPersonNodeIdAndNodeType(SelectedDSRValue);
                        int tempSalesmanNodeId=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempSalesmanNodeType=Integer.parseInt(DSRNodeIdAndNodeType.split(Pattern.quote("^"))[1]);
                        shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);
                        flgDataScopeSharedPref(2);

                        Intent i = new Intent(DialogActivity_Report.this, DetailReportSummaryActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                    }
                }
                else if(rb_distrbtrScope.isChecked())
                {
                    if(!SelectedDistrbtrName.equals("") && !SelectedDistrbtrName.equals("Select Distributor") && !SelectedDistrbtrName.equals("No Distributor") )
                    {
                        String DbrNodeIdAndNodeType= hmapDistrbtrList.get(SelectedDistrbtrName);
                        int tempSalesmanNodeId=Integer.parseInt(DbrNodeIdAndNodeType.split(Pattern.quote("^"))[0]);
                        int tempSalesmanNodeType=Integer.parseInt(DbrNodeIdAndNodeType.split(Pattern.quote("^"))[1]);

                        shardPrefForSalesman(tempSalesmanNodeId,tempSalesmanNodeType);

                        flgDataScopeSharedPref(4);
                        Intent i = new Intent(DialogActivity_Report.this, DetailReportSummaryActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        showAlertSingleButtonInfo(getResources().getString(R.string.selectDistributorProceeds));
                    }
                }
                else if(rb_dsrAttendance.isChecked())
                {
                      Intent i=new Intent(DialogActivity_Report.this,WebViewDSRAttendanceReportActivity.class);
                      startActivity(i);
                      finish();
  }
                else
                {
                   showAlertSingleButtonInfo(getResources().getString(R.string.selectOptionProceeds));
                }
            }
        });

        rb_dsrAttendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_dsrAttendance.isChecked())
                {
                    rb_dsrReport.setChecked(false);
                    rb_WholeReport.setChecked(false);
                    rb_dsrReport.setChecked(false);
                    spinner_dsrVisit.setVisibility(View.GONE);
                    rb_distrbtrScope.setChecked(false);
                    rb_dsrAttendance.setChecked(true);
                    spinner_distrbtrScope.setVisibility(View.GONE);
                }
            }
        });
        rb_myReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_myReport.isChecked())
                {
                    rb_dsrReport.setChecked(false);
                    rb_WholeReport.setChecked(false);
                    spinner_dsrVisit.setVisibility(View.GONE);
                    rb_distrbtrScope.setChecked(false);
                    rb_dsrAttendance.setChecked(false);
                    spinner_distrbtrScope.setVisibility(View.GONE);
                }
            }
        });
        rb_WholeReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_WholeReport.isChecked())
                {
                    rb_dsrReport.setChecked(false);
                    rb_myReport.setChecked(false);
                    spinner_dsrVisit.setVisibility(View.GONE);
                    rb_distrbtrScope.setChecked(false);
                    rb_dsrAttendance.setChecked(false);
                    spinner_distrbtrScope.setVisibility(View.GONE);
                }
            }
        });

        rb_dsrReport.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_dsrReport.isChecked())
                {
                    rb_myReport.setChecked(false);
                    rb_WholeReport.setChecked(false);
                    rb_distrbtrScope.setChecked(false);
                    spinner_distrbtrScope.setVisibility(View.GONE);
                    rb_dsrAttendance.setChecked(false);

                    ArrayAdapter adapterCategory=new ArrayAdapter(DialogActivity_Report.this, android.R.layout.simple_spinner_item,drsNames);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_dsrVisit.setAdapter(adapterCategory);
                    spinner_dsrVisit.setVisibility(View.VISIBLE);

                    spinner_dsrVisit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                        {
                            // TODO Auto-generated method stub
                            SelectedDSRValue = spinner_dsrVisit.getSelectedItem().toString();
                           /* ReasonText=spinnerReasonSelected;
                            int check=dbengine.fetchFlgToShowTextBox(spinnerReasonSelected);
                            ReasonId=dbengine.fetchReasonIdBasedOnReasonDescr(spinnerReasonSelected);
                            if(check==0)
                            {
                                et_Reason.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                et_Reason.setVisibility(View.VISIBLE);
                            }*/


                            //ReasonId,ReasonText
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0)
                        {
                        }
                    });

                }
            }
        });

        rb_distrbtrScope.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(rb_distrbtrScope.isChecked())
                {
                    rb_myReport.setChecked(false);
                    rb_WholeReport.setChecked(false);
                    rb_dsrReport.setChecked(false);
                    spinner_dsrVisit.setVisibility(View.GONE);
                    rb_dsrAttendance.setChecked(false);
                    ArrayAdapter adapterCategory=new ArrayAdapter(DialogActivity_Report.this, android.R.layout.simple_spinner_item,DbrArray);
                    adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_distrbtrScope.setAdapter(adapterCategory);
                    spinner_distrbtrScope.setVisibility(View.VISIBLE);

                    spinner_distrbtrScope.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                        {
                            SelectedDistrbtrName = spinner_distrbtrScope.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0)
                        {
                        }
                    });
                }
            }
        });


    }

}
