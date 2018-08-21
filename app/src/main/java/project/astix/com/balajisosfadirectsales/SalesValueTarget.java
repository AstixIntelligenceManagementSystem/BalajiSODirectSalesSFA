package project.astix.com.balajisosfadirectsales;



        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.Html;
        import android.text.TextUtils;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.astix.Common.CommonInfo;


public class SalesValueTarget extends Activity {

    TextView txt_stv;
    ImageView imgVw_next,imgVw_back;
    String imei,pickerDate,userDate;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_value_target);
        txt_stv=(TextView) findViewById(R.id.txt_stv);
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
        //Html.fromHtml(CommonInfo.SalesPersonTodaysTargetMsg)
        if(!TextUtils.isEmpty(CommonInfo.SalesPersonTodaysTargetMsg))
        {
            txt_stv.setText(Html.fromHtml(CommonInfo.SalesPersonTodaysTargetMsg));
        }
        else
        {
            if(intentFrom==0)
            {
                Intent i=new Intent(SalesValueTarget.this,IncentiveActivity.class);
                startActivity(i);
                finish();
            }

        }

        if(intentFrom==0)
        {
            imgVw_back.setVisibility(View.GONE);
            imgVw_next.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(SalesValueTarget.this,IncentiveActivity.class);
                    startActivity(i);
                    finish();

                }
            });
        }
        else
        {
            imgVw_next.setVisibility(View.GONE);
            imgVw_back.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent i=new Intent(SalesValueTarget.this,StoreSelection.class);
                    i.putExtra("imei", imei);
                    i.putExtra("userDate", userDate);
                    i.putExtra("pickerDate", pickerDate);
                    startActivity(i);
                    finish();

                }
            });
        }



    }


}
