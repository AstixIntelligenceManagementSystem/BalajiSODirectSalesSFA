package project.astix.com.balajisosfadirectsales;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.astix.Common.CommonFunction;
import com.astix.Common.CommonInfo;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class DSR_Registration extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    RelativeLayout parent_of_image_holder;
    LinearLayout LL_banner_image,parentOf_questionLayout,parentOf_validationLayout,parentOf_registrationformLayout,parent_of_marriedSection,mContent;
    RadioButton radio_yes,radio_no, radio_Male,radio_Female,radio_married,radio_unmarried;
    TextView welcomeTextView,txt_Dob_credential,Text_Dob,Text_married_date,spinnerQualification,Text_capture_image,Text_Browse_image,spinner_bloodgrp,textMessage,text_UpdateNow,text_NotYou,text_Daystart;
    TextView CovrageArea,textContact,textDob,ContactOnWelcome , DobOnWelcome,textCoverage ,textviewFirstname,textviewLastname,textviewContact,textviewDOB,textviewSex,textviewMaritalStatus,textviewMarriedDate,textviewQualification,textviewUpdatedPhoto,textviewBloodGrp,textviewSignhere;
    EditText ET_mobile_credential,ET_firstname,ET_lastname ,ET_contact_no,editText_emailID;
    Button validate_btn,Submit_btn,BtnCancel;
    ImageView imgView_photo,imgCncl,profile_image;
    public int chkFlgForErrorToCloseApp=0;

    String mobNumberForService;
    String dobForService;

    Calendar calendar ;
    int Year, Month, Day ;
    boolean credential_dob_Bool=false;
    boolean dob_Bool=false;
    boolean married_Bool=false;
    DatePickerDialog datePickerDialog ;

    PRJDatabase dbengine=new PRJDatabase(this);
    public static int RESULT_LOAD_IMAGE=1;

    public  Dialog dialog=null;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture;
    private Button capture,cancelCam, switchCamera;
    private Context myContext;
    private LinearLayout cameraPreview;
    private boolean cameraFront = false;
    ImageView flashImage;
    private boolean isLighOn = false;
    String imageName;
    float mDist=0;
    Uri uriSavedImage;
    LinkedHashMap<String, String> hmapImageData=new LinkedHashMap<String,String>();
    LinkedHashMap<String, String> hmapBloodGroup;
    LinkedHashMap<String, String> hmapDsrRegAllDetails;
    LinkedHashMap<String, String> hmapQualification;
    String PersonNameAndFlgRegistered="0";

    ArrayAdapter<String> adapterDistributor;
    ArrayAdapter<String> adapterBloodGroup;
    String[] merchantList;
    String[] bloodGroupList;
    AlertDialog.Builder alertDialog;
    AlertDialog ad;
    View convertView;
    ListView listDistributor;
    signature mSignature;
    Bitmap bitmap;
    Button  mClear, mGetSign, mCancel;
    File file;
    View view;
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/" + CommonInfo.ImagesFolder + "/";
    String pic_name = new SimpleDateFormat("yyyyMMMdd_HHmmss", Locale.ENGLISH).format(new Date());
    String StoredPath = DIRECTORY + pic_name + ".png";
    ScrollView scrollViewParentOfMap;
    ImageView transparent_image;
    boolean signOrNot=false;
    String FROM="";
    public String userDate;
    public String pickerDate;
    public String imei;
    String photoNameGlobal="NA";
    String userNodeIdGlobal="0";
    String userNodetypeGlobal="0";
    SharedPreferences sPrefAttandance;
    public  String  fDate;


    @Override
    protected void onResume() {
        super.onResume();
       /* if(CommonInfo.DayStartClick==2)
        {
            SharedPreferences.Editor editor1=sPrefAttandance.edit();
            editor1.clear();
            editor1.commit();
            CommonInfo.DayStartClick=0;
            finish();

        }*/
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) // Control the PDA
    // Native Button
    // Handling
    {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
            // finish();
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            // finish();

        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dsr__registration_activity);


        Date date1=new Date();
        SimpleDateFormat  sdf = new SimpleDateFormat("dd-MMM-yyyy",Locale.ENGLISH);
        String  passDate = sdf.format(date1).toString();
        fDate = passDate.trim().toString();

        Intent intent = getIntent();
         FROM= intent.getStringExtra("IntentFrom");
        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        imei = tManager.getDeviceId();
        if(CommonInfo.imei.trim().equals(null) || CommonInfo.imei.trim().equals(""))
        {
            CommonInfo.imei=imei;
        }
        else
        {
            imei= CommonInfo.imei.trim();
        }
      //  sPrefAttandance=getSharedPreferences(CommonInfo.AttandancePreference, MODE_PRIVATE);
        profile_image=(ImageView)findViewById(R.id.profile_image);
        //Retreiving master data from database
        getDataFromDataBase();

        LinearLayoutInitialize();
        RadioButtonInitialize();
        TextViewInitialize();
        SpinnerInitialize();
        EdittextInitilize();
        ButtonInitialize();
        ImageViewInitialize();

        signatureAllCode();
        mustFillViews();
        fillDataToLayoutFromDataBase();


            if(FROM != null && !FROM.isEmpty()) {
            if (FROM.equals("DAYEND") ||  FROM.equals("AllButtonActivity")) {

                imei = intent.getStringExtra("imei").trim();

                if(FROM.equals("DAYEND") ) {

                    pickerDate = intent.getStringExtra("pickerDate").trim();
                    userDate = intent.getStringExtra("userDate");
                }
                parentOf_questionLayout.setVisibility(View.GONE);
                parentOf_registrationformLayout.setVisibility(View.VISIBLE);
                LL_banner_image.setVisibility(View.GONE);
                Submit_btn.setVisibility(View.VISIBLE);

            }
        }

    }

  public  void ImageViewInitialize()
    {

        imgCncl= (ImageView) findViewById(R.id.imgCncl);
        imgCncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String imagNm=  hmapImageData.get("ImageData").split(Pattern.quote("~"))[1];
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imagNm;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }
                parent_of_image_holder.setVisibility(View.GONE);
                hmapImageData.clear();
            }
        });
        imgView_photo= (ImageView) findViewById(R.id.imgView_photo);
        imgView_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    String filePathName="";
                    if(hmapImageData.get("ImageData").split(Pattern.quote("~"))[0].contains("file:")){
                        filePathName=hmapImageData.get("ImageData").split(Pattern.quote("~"))[0].replace("file:","");
                    }
                    else {
                        filePathName=hmapImageData.get("ImageData").split(Pattern.quote("~"))[0];

                    }
                    File file = new File(filePathName);
                    Uri intentUri = FileProvider.getUriForFile(getBaseContext(), getApplicationContext().getPackageName() + ".provider", file);
                    intent.setDataAndType(intentUri, "image/*");
                    startActivity(intent);

                }
                else{
                    Uri intentUri = Uri.parse(hmapImageData.get("ImageData").split(Pattern.quote("~"))[0]);


                    intent.setDataAndType(intentUri, "image/*");
                    startActivity(intent);
                }


            }
        });
    }

    public void RadioButtonInitialize()
    {
        radio_yes= (RadioButton) findViewById(R.id.radio_yes);
        radio_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_no.setChecked(false);
            }
        });
        radio_no= (RadioButton) findViewById(R.id.radio_no);
        radio_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_yes.setChecked(false);
                parentOf_questionLayout.setVisibility(View.GONE);
                parentOf_validationLayout.setVisibility(View.VISIBLE);
            }
        });


        radio_Male= (RadioButton) findViewById(R.id.radio_Male);
        radio_Male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_Female.setChecked(false);

            }
        });

        radio_Female= (RadioButton) findViewById(R.id.radio_Female);
        radio_Female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_Male.setChecked(false);

            }
        });
        radio_married= (RadioButton) findViewById(R.id.radio_married);
        radio_married.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_unmarried.setChecked(false);
                parent_of_marriedSection.setVisibility(View.VISIBLE);

            }
        });

        radio_unmarried= (RadioButton) findViewById(R.id.radio_unmarried);
        radio_unmarried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio_married.setChecked(false);
                parent_of_marriedSection.setVisibility(View.GONE);

            }
        });

    }

    public void LinearLayoutInitialize()
    {
        LL_banner_image= (LinearLayout) findViewById(R.id.LL_banner_image);
        parentOf_questionLayout= (LinearLayout) findViewById(R.id.parentOf_questionLayout);
        parentOf_validationLayout= (LinearLayout) findViewById(R.id.parentOf_validationLayout);
        parentOf_registrationformLayout= (LinearLayout) findViewById(R.id.parentOf_registrationformLayout);
        parent_of_marriedSection= (LinearLayout) findViewById(R.id.parent_of_marriedSection);
        parent_of_image_holder= (RelativeLayout) findViewById(R.id.parent_of_image_holder);


    }

    public void TextViewInitialize()
    {
        textCoverage= (TextView) findViewById(R.id.textCoverage);
        textContact= (TextView) findViewById(R.id.textContact);
        textDob= (TextView) findViewById(R.id.textDob);

        CovrageArea= (TextView) findViewById(R.id.CovrageArea);
        ContactOnWelcome= (TextView) findViewById(R.id.ContactOnWelcome);
        DobOnWelcome= (TextView) findViewById(R.id.DobOnWelcome);



        welcomeTextView= (TextView) findViewById(R.id.welcomeTextView);
        textMessage= (TextView) findViewById(R.id.textMessage);


        text_UpdateNow= (TextView) findViewById(R.id.text_UpdateNow);
        text_UpdateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentOf_questionLayout.setVisibility(View.GONE);
                parentOf_registrationformLayout.setVisibility(View.VISIBLE);
                LL_banner_image.setVisibility(View.GONE);
                Submit_btn.setVisibility(View.VISIBLE);
                BtnCancel.setVisibility(View.VISIBLE);

            }
        });

        text_NotYou= (TextView) findViewById(R.id.text_NotYou);
        text_NotYou.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            parentOf_questionLayout.setVisibility(View.GONE);
            parentOf_validationLayout.setVisibility(View.VISIBLE);

        }
    });


        text_Daystart= (TextView) findViewById(R.id.text_Daystart);
        text_Daystart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i=new Intent(DSR_Registration.this,SalesValueTarget.class);
                i.putExtra("IntentFrom", 0);
                startActivity(i);
                finish();*/

                Intent i=new Intent(DSR_Registration.this,AllButtonActivity.class);
                startActivity(i);
                finish();


                /*Intent i=new Intent(DSR_Registration.this,WarehouseCheckInFirstActivity.class);
                i.putExtra("imei", imei);
                i.putExtra("CstmrNodeId", 0);
                i.putExtra("CstomrNodeType", 0);
                i.putExtra("fDate", fDate);
                startActivity(i);
                finish();*/

            }
        });




        txt_Dob_credential= (TextView) findViewById(R.id.txt_Dob_credential);
        txt_Dob_credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credential_dob_Bool=true;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR) ;
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(DSR_Registration.this, Year-24, Month, Day);

                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);

                Calendar calendarForSetDate = Calendar.getInstance();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(calendar);

                //surbhi
               calendarForSetDate.set(Year - 24, Month, Day);
                datePickerDialog.setMaxDate(calendarForSetDate);
                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle(getResources().getString(R.string.txtSELECTDOB));

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

            }
        });

        Text_Dob= (TextView) findViewById(R.id.Text_Dob);
        Text_Dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dob_Bool=true;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR) ;
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(DSR_Registration.this, Year-24, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                Calendar calendarForSetDate = Calendar.getInstance();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(calendar);

              //  datePickerDialog.setMinDate(calendarForSetDate);
                calendarForSetDate.set(Year - 24, Month, Day);
                datePickerDialog.setMaxDate(calendarForSetDate);
                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle(getResources().getString(R.string.txtSELECTDOB));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

            }
        });
        Text_married_date= (TextView) findViewById(R.id.Text_married_date);
        Text_married_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                married_Bool=true;
                calendar = Calendar.getInstance();
                Year = calendar.get(Calendar.YEAR) ;
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = DatePickerDialog.newInstance(DSR_Registration.this, Year, Month, Day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                Calendar calendarForSetDate = Calendar.getInstance();
                calendarForSetDate.setTimeInMillis(System.currentTimeMillis());

                // calendar.setTimeInMillis(System.currentTimeMillis()+24*60*60*1000);
                //YOU can set min or max date using this code
                // datePickerDialog.setMaxDate(Calendar.getInstance());
                // datePickerDialog.setMinDate(calendar);

               // datePickerDialog.setMinDate(calendarForSetDate);
                datePickerDialog.setAccentColor(Color.parseColor("#544f88"));

                datePickerDialog.setTitle(getResources().getString(R.string.txtSelectMarriedDate));
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

            }
        });


        Text_capture_image= (TextView) findViewById(R.id.Text_capture_image);
        Text_capture_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCustomCamara();
            }
        });

        Text_Browse_image= (TextView) findViewById(R.id.Text_Browse_image);

        Text_Browse_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

                /*Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, SELECT_PICTURE);
                //startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);*/
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
        {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            File f = new File(picturePath);

            String imageName = f.getName();
            cursor.close();


            File newFilename=    getOutputMediaFileWithImageName(imageName);
            if(selectedImage!=null && newFilename!=null)
            {
                uriSavedImage = Uri.fromFile(newFilename);
                String new_pathOfImage=   newFilename.getPath();
                imageName= newFilename.getName();
               /* OutputStream stream;
                try {
                    stream = new FileOutputStream(newFilename.getPath());
                    BitmapFactory.decodeFile(picturePath).compress(Bitmap.CompressFormat.JPEG, 100, stream);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/
                try {
                    copyFile(f,newFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Bitmap bmp = decodeSampledBitmapFromFile(newFilename.getAbsolutePath(), 80, 80);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                byte[] byteArray = stream.toByteArray();

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);
                imgView_photo.setImageBitmap(bitmap);

               // imgView_photo.setImageBitmap(BitmapFactory.decodeFile(new_pathOfImage));
                parent_of_image_holder.setVisibility(View.VISIBLE);
                //deleting previous image if image is already set
                deletingPreviousImage();
                hmapImageData.put("ImageData",uriSavedImage.toString()+"~"+imageName);

            }



        }
    }

    public void SpinnerInitialize()
    {
       /* merchantList=new String[5];
        merchantList[0]="Select";
        merchantList[1]="Graduate";
        merchantList[2]="Postgraduate";
        merchantList[3]="Intermidiate";
        merchantList[4]="High School";
        adapterDistributor = new ArrayAdapter<String>(DSR_Registration.this, R.layout.list_item, R.id.product_name, merchantList);
*/
        spinnerQualification= (TextView) findViewById(R.id.spinnerQualification);
        spinnerQualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(DSR_Registration.this);
                LayoutInflater inflater = getLayoutInflater();
                convertView = (View) inflater.inflate(R.layout.activity_list, null);
                EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                inputSearch.setVisibility(View.GONE);
                listDistributor = (ListView)convertView. findViewById(R.id.list_view);




                int index=0;
                if(hmapQualification!=null)
                {
                    merchantList=new String[hmapQualification.size()];
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapQualification);
                    Set set2 = map.entrySet();
                    Iterator iterator = set2.iterator();
                    while(iterator.hasNext()) {
                        Map.Entry me2 = (Map.Entry)iterator.next();
				            	 /*if(index==0)
				            	 {
				            		 dominantbrandtList[index]="Select";

				            		 index=index+1;

				            		 dominantbrandtList[index]=me2.getKey().toString().trim();

				                	 index=index+1;
				            	 }
				            	 else
				            	 {*/
                        merchantList[index]=me2.getKey().toString().trim();

                        index=index+1;
                        // }

                    }


                }





                adapterDistributor = new ArrayAdapter<String>(DSR_Registration.this, R.layout.list_item, R.id.product_name, merchantList);
                listDistributor.setAdapter(adapterDistributor);
                alertDialog.setView(convertView);
                alertDialog.setTitle(getResources().getString(R.string.txtQualification));
                listDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String abc=listDistributor.getItemAtPosition(position).toString().trim();
                        spinnerQualification.setText(abc);
                        ad.dismiss();

                    }
                });
                ad=alertDialog.show();

            }
        });

       /* bloodGroupList=new String[9];
        bloodGroupList[0]="Select";
        bloodGroupList[1]="A+";
        bloodGroupList[2]="O+";
        bloodGroupList[3]="B+";
        bloodGroupList[4]="AB+";
        bloodGroupList[5]="A-";
        bloodGroupList[6]="O-";
        bloodGroupList[7]="B-";
        bloodGroupList[8]="AB-";
        adapterBloodGroup = new ArrayAdapter<String>(DSR_Registration.this, R.layout.list_item, R.id.product_name, bloodGroupList);
*/

        spinner_bloodgrp= (TextView) findViewById(R.id.spinner_bloodgrp);
        spinner_bloodgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(DSR_Registration.this);
                LayoutInflater inflater = getLayoutInflater();
                convertView = (View) inflater.inflate(R.layout.activity_list, null);
                EditText inputSearch=	 (EditText) convertView.findViewById(R.id.inputSearch);
                inputSearch.setVisibility(View.GONE);
                listDistributor = (ListView)convertView. findViewById(R.id.list_view);


                int index=0;
                if(hmapBloodGroup!=null)
                {
                    bloodGroupList=new String[hmapBloodGroup.size()];
                    LinkedHashMap<String, String> map = new LinkedHashMap<String, String>(hmapBloodGroup);
                    Set set2 = map.entrySet();
                    Iterator iterator = set2.iterator();
                    while(iterator.hasNext()) {
                        Map.Entry me2 = (Map.Entry)iterator.next();
				            	 /*if(index==0)
				            	 {
				            		 dominantbrandtList[index]="Select";

				            		 index=index+1;

				            		 dominantbrandtList[index]=me2.getKey().toString().trim();

				                	 index=index+1;
				            	 }
				            	 else
				            	 {*/
                        bloodGroupList[index]=me2.getKey().toString().trim();

                        index=index+1;
                        // }

                    }


                }
                adapterBloodGroup = new ArrayAdapter<String>(DSR_Registration.this, R.layout.list_item, R.id.product_name, bloodGroupList);
                listDistributor.setAdapter(adapterBloodGroup);
                alertDialog.setView(convertView);
                alertDialog.setTitle(getResources().getString(R.string.txtBloodGroupTitle));
                listDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String abc=listDistributor.getItemAtPosition(position).toString().trim();
                        spinner_bloodgrp.setText(abc);
                        ad.dismiss();

                    }
                });
                ad=alertDialog.show();

            }
        });


    }
//nitika text end today
    public void EdittextInitilize()
    {
        ET_mobile_credential= (EditText) findViewById(R.id.ET_mobile_credential);
        ET_firstname= (EditText) findViewById(R.id.ET_firstname);
        ET_lastname= (EditText) findViewById(R.id.ET_lastname);
        ET_contact_no= (EditText) findViewById(R.id.ET_contact_no);
        editText_emailID= (EditText) findViewById(R.id.editText_emailID);

    }

    public void ButtonInitialize()
    {
       validate_btn=(Button)findViewById(R.id.validate_btn);
       validate_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               check_validationAndGetDataFromServer();


             /* String text="Somu";
               if(!ET_mobile_credential.getText().toString().trim().equals(""))
               {
                   text=ET_mobile_credential.getText().toString().trim();
               }

               Intent sendIntent = new Intent();
               sendIntent.setAction(Intent.ACTION_SEND);
               sendIntent.putExtra(Intent.EXTRA_TEXT, text);
               sendIntent.setType("text/plain");
               sendIntent.setPackage("astix.studyprojects");
               startActivity(sendIntent);*/


           }
       });
       Submit_btn=(Button)findViewById(R.id.Submit_btn);
        Submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             if(validate())
             {
                 AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                         DSR_Registration.this);
                 alertDialog.setTitle(getResources().getString(R.string.genTermNoDataConnection));

                 alertDialog.setCancelable(false);
                 alertDialog.setMessage(getResources().getString(R.string.txtSubmitData));
                 alertDialog.setPositiveButton(getResources().getString(R.string.AlertDialogYesButton),
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog,
                                                 int which) {
                                 dialog.dismiss();




                                 saveDataToDataBase();



                             }
                         });
                 alertDialog.setNegativeButton(getResources().getString(R.string.AlertDialogNoButton),
                         new DialogInterface.OnClickListener() {
                             public void onClick(DialogInterface dialog,
                                                 int which) {
                                 dialog.dismiss();
                             }
                         });

                 // Showing Alert Message
                 alertDialog.show();


             }
            }
        });
        BtnCancel=(Button)findViewById(R.id.BtnCancel);
        BtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //deleting image from image folder
                deletingPreviousImage();

               /* Intent i=new Intent(DSR_Registration.this,SalesValueTarget.class);
                i.putExtra("IntentFrom", 0);
                startActivity(i);
                finish();*/
              /*  Intent i=new Intent(DSR_Registration.this,WarehouseCheckInFirstActivity.class);
                i.putExtra("imei", imei);
                i.putExtra("CstmrNodeId", 0);
                i.putExtra("CstomrNodeType", 0);
                i.putExtra("fDate", fDate);
                startActivity(i);
                finish();*/
                Intent i=new Intent(DSR_Registration.this,AllButtonActivity.class);
                startActivity(i);
                finish();

            }
        });


   }
    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        return false;
    }
    public void showNoConnAlert()
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DSR_Registration.this);
        alertDialogNoConn.setTitle(getResources().getString(R.string.txtErrorNoDataConnection));

        alertDialogNoConn.setMessage(getResources().getString(R.string.txtErrorInternetConnection));
        //alertDialogNoConn.setMessage(getText(R.string.connAlertErrMsg));
        alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        /*if(isMyServiceRunning())
                  		{
                        stopService(new Intent(DynamicActivity.this,GPSTrackerService.class));
                  		}
                        finish();*/
                        //finish();
                    }
                });
        alertDialogNoConn.setIcon(R.drawable.error_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
        // alertDialogLowbatt.show();
    }


    public void check_validationAndGetDataFromServer()
    {
          if( ET_mobile_credential.getText().toString().trim().equals("0000000000") || ET_mobile_credential.getText().toString().trim().equals("") || ET_mobile_credential.getText().toString().trim().length()<10)
          {
        showAlertForEveryOne(getResources().getString(R.string.txtErrorMobileNo));

          }
          else if(txt_Dob_credential.getText().toString().trim().equals(""))
          {
              showAlertForEveryOne(getResources().getString(R.string.txtSelectDOB));

          }
          else
          {
          mobNumberForService=     ET_mobile_credential.getText().toString().trim();
               dobForService=    txt_Dob_credential.getText().toString().trim();
              if(isOnline())
              {

                  try
                  {
                      ValidateAndGetDsrData cuv = new ValidateAndGetDsrData(DSR_Registration.this);
                      cuv.execute();
                  }
                  catch (Exception e) {
                      e.printStackTrace();

                  }

              }
              else {
                  showNoConnAlert();

              }
             /* parentOf_validationLayout.setVisibility(View.GONE);
              parentOf_registrationformLayout.setVisibility(View.VISIBLE);
              LL_banner_image.setVisibility(View.GONE);
              Submit_btn.setVisibility(View.VISIBLE);
              BtnCancel.setVisibility(View.VISIBLE);*/

          }

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        String mon=MONTHS[monthOfYear];

        if(credential_dob_Bool)
        {
            txt_Dob_credential.setText(dayOfMonth+"/"+mon+"/"+year);
        }
        if(dob_Bool)
        {
            Text_Dob.setText(dayOfMonth+"/"+mon+"/"+year);
        }
        if(married_Bool)
        {
            Text_married_date.setText(dayOfMonth+"/"+mon+"/"+year);
        }
        credential_dob_Bool=false;
        dob_Bool=false;
        married_Bool=false;

    }
    public void   openCustomCamara()
    {
        if(dialog!=null)
        {
            if(!dialog.isShowing())
            {
                openCamera();


            }

        }
        else
        {
            openCamera();

        }

    }

    private void handleZoom(MotionEvent event, Camera.Parameters params) {
        int maxZoom = params.getMaxZoom();
        int zoom = params.getZoom();
        float newDist = getFingerSpacing(event);
        if (newDist > mDist) {
            // zoom in
            if (zoom < maxZoom)
                zoom++;
        } else if (newDist < mDist) {
            // zoom out
            if (zoom > 0)
                zoom--;
        }
        mDist = newDist;
        params.setZoom(zoom);
        mCamera.setParameters(params);
    }
    public void handleFocus(MotionEvent event, Camera.Parameters params) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);

        List<String> supportedFocusModes = params.getSupportedFocusModes();
        if (supportedFocusModes != null
                && supportedFocusModes
                .contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean b, Camera camera) {
                    // currently set to auto-focus on single touch
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mCamera!=null){
            mCamera.release();
            mCamera=null;
            if(dialog!=null){
                if(dialog.isShowing()){
                    dialog.dismiss();

                }
            }
        }
    }

    private float getFingerSpacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }
    private void setCameraDisplayOrientation(Activity activity,
                                             int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
    private Camera.PictureCallback getPictureCallback() {
        Camera.PictureCallback picture = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                //make a new picture file
                File pictureFile = getOutputMediaFile();

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;

                if (pictureFile == null) {
                    return;
                }
                try {
                    //write the file
                    FileOutputStream fos = new FileOutputStream(pictureFile);
                    fos.write(data);
                    fos.close();

                    //Toast toast = Toast.makeText(getActivity(), "Picture saved: " + pictureFile.getName(), Toast.LENGTH_LONG);
                    //toast.show();
                    //put data here


                    dialog.dismiss();
                    if(pictureFile!=null)
                    {
                        File file=pictureFile;
                        System.out.println("File +++"+pictureFile);
                        imageName=pictureFile.getName();
                        CommonFunction.normalizeImageForUri(DSR_Registration.this,Uri.fromFile(pictureFile));
                        Bitmap bmp = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(file.getAbsolutePath()),120,120);

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        uriSavedImage = Uri.fromFile(pictureFile);
                        bmp.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        byte[] byteArray = stream.toByteArray();


                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                                byteArray.length);
                        imgView_photo.setImageBitmap(bitmap);
                        parent_of_image_holder.setVisibility(View.VISIBLE);
                        //deleting previous image if image is already set
                        deletingPreviousImage();

                        hmapImageData.put("ImageData",uriSavedImage.toString()+"~"+imageName);


                        //
                      //  setSavedImageToScrollView(bitmap, imageName,valueOfKey,clickedTagPhoto);
                    }



//Show dialog here
//...
//Hide dialog here

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }

                //refresh camera to continue preview--------------------------------------------------------------
                //	mPreview.refreshCamera(mCamera);
                //if want to release camera
                if(mCamera!=null){
                    mCamera.release();
                    mCamera=null;
                }
            }
        };
        return picture;
    }


    View.OnClickListener captrureListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setEnabled(false);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            cancelCam.setEnabled(false);
            flashImage.setEnabled(false);
            if(cameraPreview!=null)
            {
                cameraPreview.setEnabled(false);
            }

            if(mCamera!=null)
            {
                mCamera.takePicture(null, null, mPicture);
            }
            else
            {
                dialog.dismiss();
            }
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }
    };



    private boolean hasCamera(Context context) {
        //check if the device has camera
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }
    private int findFrontFacingCamera() {
        int cameraId = -1;
        // Search for the front facing camera
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                cameraFront = true;
                break;
            }
        }
        return cameraId;
    }
    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                cameraFront = false;
                break;
            }
        }
        return cameraId;
    }


    private static File getOutputMediaFile() {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", CommonInfo.ImagesFolder);

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss",Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
        //mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
       // mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" +CommonInfo.imei+"$"+ timeStamp + ".jpg");
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + CommonInfo.imei+timeStamp + ".jpg");

        return mediaFile;
    }

    private static File getOutputMediaFileWithImageName(String imgName) {
        //make a new file directory inside the "sdcard" folder
        File mediaStorageDir = new File("/sdcard/", CommonInfo.ImagesFolder);

        //if this "JCGCamera folder does not exist
        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        //take the current timeStamp
        String timeStamp = new SimpleDateFormat("yyyyMMMdd_HHmmss",Locale.ENGLISH).format(new Date());
        File mediaFile;
        //and make a media file:
       /// mediaFile = new File(mediaStorageDir.getPath() + File.separator + imgName);
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + CommonInfo.imei+"$"+ timeStamp + ".jpg");

        return mediaFile;
    }



    public void openCamera()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        dialog = new Dialog(DSR_Registration.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        //dialog.setTitle("Calculation");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_main);
        WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();


        parms.height=parms.MATCH_PARENT;
        parms.width=parms.MATCH_PARENT;
        cameraPreview = (LinearLayout)dialog. findViewById(R.id.camera_preview);

        mPreview = new CameraPreview(DSR_Registration.this, mCamera);
        cameraPreview.addView(mPreview);
        //onResume code
        if (!hasCamera(DSR_Registration.this)) {
            Toast toast = Toast.makeText(DSR_Registration.this,getResources().getString(R.string.txtNoCamera), Toast.LENGTH_LONG);
            toast.show();

        }
        if (mCamera == null) {
            //if the front facing camera does not exist
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(DSR_Registration.this, getResources().getString(R.string.txtNoFrontCamera), Toast.LENGTH_LONG).show();
                switchCamera.setVisibility(View.GONE);
            }

            //mCamera = Camera.open(findBackFacingCamera());

            //commenting it because we want selfie camera
           // mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
		/*if(mCamera==null){
			mCamera=Camera.open(0);
		}*/

            if (findFrontFacingCamera() < 0)
            {
                mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            }
            else
            {
                mCamera=Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }

            boolean isParameterSet=false;
            try {
                Camera.Parameters params= mCamera.getParameters();


                List<Camera.Size> sizes = params.getSupportedPictureSizes();
                Camera.Size size = sizes.get(0);
//Camera.Size size1 = sizes.get(0);
                for(int i=0;i<sizes.size();i++)
                {

                    if(sizes.get(i).width > size.width)
                        size = sizes.get(i);


                }

//System.out.println(size.width + "mm" + size.height);

                params.setPictureSize(size.width, size.height);
                params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                //	params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                params.setSceneMode(Camera.Parameters.SCENE_MODE_AUTO);
                params.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_AUTO);

                //	params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);

                isLighOn = false;
                int minExpCom=params.getMinExposureCompensation();
                int maxExpCom=params.getMaxExposureCompensation();

                if( maxExpCom > 4 && minExpCom < 4)
                {
                    params.setExposureCompensation(4);
                }
                else
                {
                    params.setExposureCompensation(0);
                }
                params.setAutoExposureLock(false);
                params.setAutoWhiteBalanceLock(false);
                //String supportedIsoValues = params.get("iso-values");
                // String newVAlue = params.get("iso");
                //  params.set("iso","1600");
                params.setColorEffect("none");
                params.set("scene-mode","auto");


                params.setPictureFormat(ImageFormat.JPEG);
                params.setJpegQuality(70);
                params.setRotation(270);


                mCamera.setParameters(params);
                isParameterSet=true;
            }
            catch (Exception e)
            {

            }
            if(!isParameterSet)
            {
                Camera.Parameters params2= mCamera.getParameters();
                params2.setPictureFormat(ImageFormat.JPEG);
                params2.setJpegQuality(70);
                params2.setRotation(270);

                mCamera.setParameters(params2);
            }






            if (findFrontFacingCamera() < 0)
            {
                setCameraDisplayOrientation(DSR_Registration.this, Camera.CameraInfo.CAMERA_FACING_BACK,mCamera);
            }
            else
            {
                setCameraDisplayOrientation(DSR_Registration.this, Camera.CameraInfo.CAMERA_FACING_FRONT,mCamera);
            }
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }


        capture = (Button)dialog.  findViewById(R.id.button_capture);

        flashImage= (ImageView)dialog.  findViewById(R.id.flashImage);
        flashImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLighOn) {
                    // turn off flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }


                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(params);
                    flashImage.setImageResource(R.drawable.flash_off);
                    isLighOn=false;
                } else {

                    // turn on flash
                    Camera.Parameters params = mCamera.getParameters();

                    if (mCamera == null || params == null) {
                        return;
                    }

                    params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

                    flashImage.setImageResource(R.drawable.flash_on);
                    mCamera.setParameters(params);

                    isLighOn=true;
                }
            }
        });

        final Button cancleCamera= (Button)dialog.  findViewById(R.id.cancleCamera);
        cancelCam=cancleCamera;
        cancleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                v.setEnabled(false);
                capture.setEnabled(false);
                cameraPreview.setEnabled(false);
                flashImage.setEnabled(false);

                Camera.Parameters params = mCamera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(params);
                isLighOn = false;
                dialog.dismiss();
               getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }
        });
        capture.setOnClickListener(captrureListener);

        cameraPreview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Get the pointer ID
                Camera.Parameters params = mCamera.getParameters();
                int action = event.getAction();

                if (event.getPointerCount() > 1) {
                    // handle multi-touch events
                    if (action == MotionEvent.ACTION_POINTER_DOWN) {
                        mDist = getFingerSpacing(event);
                    } else if (action == MotionEvent.ACTION_MOVE
                            && params.isZoomSupported()) {
                        mCamera.cancelAutoFocus();
                        handleZoom(event, params);
                    }
                } else {
                    // handle single touch events
                    if (action == MotionEvent.ACTION_UP) {
                        handleFocus(event, params);
                    }
                }
                return true;
            }
        });

        dialog.show();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);


    }
    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight)
        {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth)
        {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }
    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }
    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(DSR_Registration.this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {

                public void onScanCompleted(String path, Uri uri) {

                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }
    public void deletingPreviousImage()
    {
        if(hmapImageData!=null)
        {
            if(!hmapImageData.isEmpty())
            {
                String imagNm=  hmapImageData.get("ImageData").split(Pattern.quote("~"))[1];
                String file_dj_path = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolder + "/" +imagNm;
                File fdelete = new File(file_dj_path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {

                        callBroadCast();
                    } else {

                    }
                }
            }
        }

    }

    public boolean validate()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String mail=editText_emailID.getText().toString().trim();

        if(ET_firstname.getText().toString().trim().equals(""))
        {

            showAlertForEveryOne(getResources().getString(R.string.txtValidateFirstName));

            return false;
        }
        else if(ET_lastname.getText().toString().trim().equals(""))
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateLastName));

            return false;
        }

        else if( ET_contact_no.getText().toString().trim().equals("0000000000") || ET_contact_no.getText().toString().trim().equals("") || ET_contact_no.getText().toString().trim().length()<10)
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateContactNo));

            return false;
        }
        else if(Text_Dob.getText().toString().trim().equals(getResources().getString(R.string.txtSelectDate)))
        {
            showAlertForEveryOne(getResources().getString(R.string.txtSelectDOB));

            return false;
        }
        else if(!radio_Male.isChecked() && !radio_Female.isChecked())
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateSelectSex));

            return false;
        }
        else if(!radio_married.isChecked() && !radio_unmarried.isChecked())
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateMaritalStatus));

            return false;
        }
        else if(radio_married.isChecked() && Text_married_date.getText().toString().trim().equals(getResources().getString(R.string.txtSelectDate)) )
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateMarriedDate));

            return false;
        }
        else if(spinnerQualification.getText().toString().trim().equals(getResources().getString(R.string.txtSelect)))
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateQualification));

            return false;
        }
        else if(hmapImageData.isEmpty())
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateUpdatePhoto));

            return false;
        }
        else if(!editText_emailID.getText().toString().trim().equals("") && !mail.matches(emailPattern))
        {

            showAlertForEveryOne(getResources().getString(R.string.txtValidateEmailID));


            return false;
        }
        else if(spinner_bloodgrp.getText().toString().trim().equals(getResources().getString(R.string.txtSelect)))
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateBloodGroup));

            return false;
        }
        else if(!signOrNot)
        {
            showAlertForEveryOne(getResources().getString(R.string.txtValidateSignature));

            return false;
        }

        else{
            return true;
        }

    }
    public void showAlertForEveryOne(String msg)
    {
        AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DSR_Registration.this);
        alertDialogNoConn.setTitle(getResources().getString(R.string.genTermNoDataConnection));

        alertDialogNoConn.setMessage(msg);
        alertDialogNoConn.setCancelable(false);
        alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

            }
        });
        alertDialogNoConn.setIcon(R.drawable.info_ico);
        AlertDialog alert = alertDialogNoConn.create();
        alert.show();
    }
    public void fillDataToLayoutFromDataBase()
    {
        if(!PersonNameAndFlgRegistered.equals("0"))
        {
            String personName=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[0];
            String FlgRegistered=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[1];


            String ContactNumberFromServer=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[2];
            String DOB=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[3];
            String SelfieName=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[4];
            String SelfieNameURL=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[5];
            String SalesAreaName=   PersonNameAndFlgRegistered.split(Pattern.quote("^"))[6];




            welcomeTextView.setText(getResources().getString(R.string.txtWelcome)+" "+personName);
            //
           // FlgRegistered="1";
            if(FlgRegistered.equals("0"))
            {
                textMessage.setText(getResources().getString(R.string.txtDSRMsg));

                text_UpdateNow.setVisibility(View.VISIBLE);
                String text=text_UpdateNow.getText().toString().trim();
                SpannableString content1 = new SpannableString(text);
                content1.setSpan(new UnderlineSpan(), 0, text.length(), 0);
                text_UpdateNow.setText(content1);

                CovrageArea.setVisibility(View.VISIBLE);
                ContactOnWelcome.setVisibility(View.VISIBLE);
                DobOnWelcome.setVisibility(View.GONE);
                textCoverage.setVisibility(View.VISIBLE);
                textContact.setVisibility(View.VISIBLE);
                textDob.setVisibility(View.GONE);
                profile_image.setVisibility(View.GONE);
                CovrageArea.setText(SalesAreaName);


                if(!ContactNumberFromServer.equals("0")){

                    ContactOnWelcome.setText(ContactNumberFromServer);
                }



            }
            if(FlgRegistered.equals("1"))
            {
                text_NotYou.setVisibility(View.VISIBLE);
                String text=text_NotYou.getText().toString().trim();
                SpannableString content1 = new SpannableString(text);
                content1.setSpan(new UnderlineSpan(), 0, text.length(), 0);
                text_NotYou.setText(content1);

                CovrageArea.setText(SalesAreaName);
                ContactOnWelcome.setText(ContactNumberFromServer);
                DobOnWelcome.setText(DOB);

                try{
                    String PATH = Environment.getExternalStorageDirectory() + "/" + CommonInfo.ImagesFolderServer + "/";

                    File file2 = new File(PATH + SelfieName);
                    if (file2.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());

                        profile_image.setImageBitmap(myBitmap);
                    }

                }
                catch (Exception e){

                }




            }

        }

    }
    public void getDataFromDataBase()
    {
        hmapBloodGroup=  dbengine.fnGettblBloodGroup();
        hmapQualification=  dbengine.fnGettblEducationQuali();
      PersonNameAndFlgRegistered=  dbengine.fnGetPersonNameAndFlgRegistered();

    }
    public class signature extends View {

        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath)
        {
            Log.v("log_tag", "Width: " + v.getWidth());
            Log.v("log_tag", "Height: " + v.getHeight());

            if (bitmap == null)
            {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }
            Canvas canvas = new Canvas(bitmap);
            try
            {
                // Output the file
                FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                v.draw(canvas);

                // Convert the output file to Image such as .png
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, mFileOutStream);
                mFileOutStream.flush();
                mFileOutStream.close();

            } catch (Exception e) {
                Log.v("log_tag", e.toString());
            }

        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);
            signOrNot=true;

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:

                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;

                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {

            Log.v("log_tag", string);

        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }
   public void signatureAllCode()
   {
       file = new File(DIRECTORY);
       if (!file.exists()) {
           file.mkdir();
       }
       mContent= (LinearLayout) findViewById(R.id.linearLayout);
       mSignature = new signature(getApplicationContext(), null);
       mSignature.setBackgroundColor(Color.WHITE);
       // Dynamically generating Layout through java code
       mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       mGetSign= (Button) findViewById(R.id.getsign);
       //by default disable it when , person do sign it will enable
       mGetSign.setEnabled(false);
       mClear= (Button) findViewById(R.id.clear);
       mCancel= (Button) findViewById(R.id.cancel);
       view=mContent;

       mClear.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               Log.v("log_tag", "Panel Cleared");
               mSignature.clear();
               mGetSign.setEnabled(false);
               signOrNot=false;
           }
       });

       mGetSign.setOnClickListener(new View.OnClickListener() {

           public void onClick(View v) {

               Log.v("log_tag", "Panel Saved");
               view.setDrawingCacheEnabled(true);
               mSignature.save(view, StoredPath);

               Toast.makeText(getApplicationContext(),getResources().getString(R.string.txtSuccessfullySaved), Toast.LENGTH_SHORT).show();
               // Calling the same class
             //  recreate();
           }
       });

       mCancel.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               Log.v("log_tag", "Panel Canceled");

               // Calling the same class
            //   recreate();
           }
       });
       transparent_image=(ImageView)findViewById(R.id.transparent_image);
       scrollViewParentOfMap=(ScrollView)findViewById(R.id.scrollViewParentOfMap);

       transparent_image.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {
               int action = motionEvent.getAction();
               switch (action) {
                   case MotionEvent.ACTION_DOWN:
                       // Disallow ScrollView to intercept touch events.
                       scrollViewParentOfMap.requestDisallowInterceptTouchEvent(true);
                       // Disable touch on transparent view
                       return false;

                   case MotionEvent.ACTION_UP:
                       // Allow ScrollView to intercept touch events.
                       scrollViewParentOfMap.requestDisallowInterceptTouchEvent(false);
                       return true;

                   case MotionEvent.ACTION_MOVE:
                       scrollViewParentOfMap.requestDisallowInterceptTouchEvent(true);
                       return false;

                   default:
                       return true;
               }
           }
       });
   }
   public  void saveDataToDataBase()
   {
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
      // showAlertForEveryOne("Everything is fine");
       //saving sign to folder
       view.setDrawingCacheEnabled(true);
       mSignature.save(view, StoredPath);
       String ddd= pic_name;

       String IMEI_string= CommonInfo.imei;
       String ClickedDateTime_string="0";
       String FirstName_string="0";
       String LastName_string="0";
       String ContactNo_string="0";
       String DOB_string="0";
       String Sex_string="0";
       String MaritalStatus_string="0";
       String MarriedDate_string="NA";
       String Qualification_string="0";
       String SelfieName_string="0";
       String SelfiePath_string="0";
       String EmailID_string="NA";
       String BloodGroup_string="0";
       String SignName_string="0";
       String SignPath_string="0";
       String PhotoName_string=photoNameGlobal;
       String PersonNodeId_string=userNodeIdGlobal;
       String PersonNodeType_string=userNodetypeGlobal;


       long syncTIMESTAMP = System.currentTimeMillis();
       Date datefromat = new Date(syncTIMESTAMP);
       SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.ENGLISH);
       String VisitEndFinalTS = df.format(datefromat);

       ClickedDateTime_string=VisitEndFinalTS;
       FirstName_string=ET_firstname.getText().toString().trim();
       LastName_string=ET_lastname.getText().toString().trim();
       ContactNo_string=ET_contact_no.getText().toString().trim();
       DOB_string=Text_Dob.getText().toString().trim();
       if(radio_Male.isChecked()){
           Sex_string="Male";
       }
       if(radio_Female.isChecked()){
           Sex_string="Female";
       }
       if(radio_married.isChecked()){
           MaritalStatus_string="1";
           MarriedDate_string= Text_married_date.getText().toString().trim();
       }
       if(radio_unmarried.isChecked()){
           MaritalStatus_string="0";
       }
       Qualification_string=spinnerQualification.getText().toString().trim();
       SelfieName_string=  hmapImageData.get("ImageData").split(Pattern.quote("~"))[1];
       SelfiePath_string=  hmapImageData.get("ImageData").split(Pattern.quote("~"))[0];
       if(!editText_emailID.getText().toString().trim().equals("")){
           EmailID_string=editText_emailID.getText().toString().trim();
       }
       BloodGroup_string=spinner_bloodgrp.getText().toString().trim();
       SignName_string=pic_name + ".png";
       SignPath_string=StoredPath;

       dbengine.open();
       dbengine.Delete_tblDsrRegDetails();
       dbengine.savetblDsrRegDetails(IMEI_string,ClickedDateTime_string,FirstName_string,LastName_string,ContactNo_string,DOB_string,Sex_string,MaritalStatus_string,MarriedDate_string,Qualification_string,SelfieName_string,SelfiePath_string,EmailID_string,BloodGroup_string,SignName_string,SignPath_string,3,PhotoName_string,PersonNodeId_string,PersonNodeType_string);
       dbengine.close();
       getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
       if(FROM.equals("DAYEND"))
       {
           Intent trans2storeList = new Intent(DSR_Registration.this, StoreSelection.class);
           trans2storeList.putExtra("imei", imei);
           trans2storeList.putExtra("userDate", userDate);
           trans2storeList.putExtra("pickerDate", pickerDate);
           startActivity(trans2storeList);
           finish();
       }
       else if(FROM.equals("AllButtonActivity")){
           Intent trans2storeList = new Intent(DSR_Registration.this, AllButtonActivity.class);
           trans2storeList.putExtra("imei", imei);
           startActivity(trans2storeList);
           finish();
       }
       else
       {
         /*  Intent i=new Intent(DSR_Registration.this,SalesValueTarget.class);
           i.putExtra("IntentFrom", 0);
           startActivity(i);
           finish();*/
          /* Intent i=new Intent(DSR_Registration.this,WarehouseCheckInFirstActivity.class);
           i.putExtra("imei", imei);
           i.putExtra("CstmrNodeId", 0);
           i.putExtra("CstomrNodeType", 0);
           i.putExtra("fDate", fDate);
           startActivity(i);
           finish();*/
           Intent i=new Intent(DSR_Registration.this,AllButtonActivity.class);
           startActivity(i);
           finish();
       }

   }

    private class ValidateAndGetDsrData extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog pDialogGetStores;
        public ValidateAndGetDsrData(DSR_Registration activity)
        {
            pDialogGetStores = new ProgressDialog(activity);
        }


        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

            pDialogGetStores.setTitle(getText(R.string.genTermPleaseWaitNew));
            pDialogGetStores.setMessage(getText(R.string.ValidatingMsg));
            pDialogGetStores.setIndeterminate(false);
            pDialogGetStores.setCancelable(false);
            pDialogGetStores.setCanceledOnTouchOutside(false);
            pDialogGetStores.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            ServiceWorker newservice = new ServiceWorker();

            try
            {
                newservice = newservice.getDsrRegistrationData(getApplicationContext(), CommonInfo.imei,mobNumberForService,dobForService);
                if(!newservice.director.toString().trim().equals("1"))
                {
                    if(chkFlgForErrorToCloseApp==0)
                    {
                        chkFlgForErrorToCloseApp=1;
                    }

                }
            }
            catch(Exception e)
            {}

            finally
            {}

            return null;
        }


        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);

            if(pDialogGetStores.isShowing())
            {
                pDialogGetStores.dismiss();
            }
            if(chkFlgForErrorToCloseApp==1)   // if Webservice showing exception or not excute complete properly
            {
                chkFlgForErrorToCloseApp=0;
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.internetError), Toast.LENGTH_LONG).show();
               // finish();
            }
            else
            {
                String flag="0";
                String message="0";
              String FlagAndMessage=  dbengine.fnGettblUserRegistarationStatus();
                if(!FlagAndMessage.equals("0"))
                {
                    flag    =   FlagAndMessage.split(Pattern.quote("^"))[0];
                    message =   FlagAndMessage.split(Pattern.quote("^"))[1];


                }

                if(flag.equals("0"))
                {
                    parentOf_validationLayout.setVisibility(View.GONE);
                    parentOf_registrationformLayout.setVisibility(View.VISIBLE);
                    LL_banner_image.setVisibility(View.GONE);
                    Submit_btn.setVisibility(View.VISIBLE);
                    BtnCancel.setVisibility(View.VISIBLE);

                }
                if(flag.equals("1"))
                {
                    AlertDialog.Builder alertDialogNoConn = new AlertDialog.Builder(DSR_Registration.this);
                    alertDialogNoConn.setTitle(getResources().getString(R.string.genTermNoDataConnection));

                    alertDialogNoConn.setMessage(message);
                    alertDialogNoConn.setCancelable(false);
                    alertDialogNoConn.setNeutralButton(getResources().getString(R.string.AlertDialogOkButton),new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                           /* Intent i=new Intent(DSR_Registration.this,SalesValueTarget.class);
                            i.putExtra("IntentFrom", 0);
                            startActivity(i);
                            finish();*/
                           /* Intent i=new Intent(DSR_Registration.this,WarehouseCheckInFirstActivity.class);
                            i.putExtra("imei", imei);
                            i.putExtra("CstmrNodeId", 0);
                            i.putExtra("CstomrNodeType", 0);
                            i.putExtra("fDate", fDate);
                            startActivity(i);
                            finish();*/
                            Intent i=new Intent(DSR_Registration.this,AllButtonActivity.class);
                            startActivity(i);
                            finish();

                        }
                    });
                    alertDialogNoConn.setIcon(R.drawable.info_ico);
                    AlertDialog alert = alertDialogNoConn.create();
                    alert.show();

                }
                if(flag.equals("2"))
                {
                    FillDsrDetailsToLayout();
                    parentOf_validationLayout.setVisibility(View.GONE);
                    parentOf_registrationformLayout.setVisibility(View.VISIBLE);
                    LL_banner_image.setVisibility(View.GONE);
                    Submit_btn.setVisibility(View.VISIBLE);
                    BtnCancel.setVisibility(View.VISIBLE);


                }


            }


        }
    }
    public void FillDsrDetailsToLayout()
    {


        hmapDsrRegAllDetails=  dbengine.fnGettblDsrRegDetails();
        if(hmapDsrRegAllDetails!=null && !hmapDsrRegAllDetails.isEmpty())
        {
         String DSR_All_DATA=   hmapDsrRegAllDetails.get("DSRDETAILS");

            String FirstNameServer=   DSR_All_DATA.split(Pattern.quote("^"))[2];
            String LastNameServer=   DSR_All_DATA.split(Pattern.quote("^"))[3];
            String ContactServer=   DSR_All_DATA.split(Pattern.quote("^"))[4];
            String DobServer=   DSR_All_DATA.split(Pattern.quote("^"))[5];
            String SexServer=   DSR_All_DATA.split(Pattern.quote("^"))[6];
            String MaritalStatusServer=   DSR_All_DATA.split(Pattern.quote("^"))[7];
            String MarriedDateServer=   DSR_All_DATA.split(Pattern.quote("^"))[8];
            String QualificationServer=   DSR_All_DATA.split(Pattern.quote("^"))[9];

            String EmailIdServer=   DSR_All_DATA.split(Pattern.quote("^"))[12];
            String BloodGrpServer=   DSR_All_DATA.split(Pattern.quote("^"))[13];
            if(!DSR_All_DATA.split(Pattern.quote("^"))[16].equals("0")){
                photoNameGlobal=   DSR_All_DATA.split(Pattern.quote("^"))[16];
            }


          userNodeIdGlobal  =   DSR_All_DATA.split(Pattern.quote("^"))[17];
            userNodetypeGlobal=   DSR_All_DATA.split(Pattern.quote("^"))[18];

            if(!FirstNameServer.equals("0")){
                ET_firstname.setText(FirstNameServer);
            }
            if(!LastNameServer.equals("0")){
                ET_lastname.setText(LastNameServer);
            }
            if(!ContactServer.equals("0")){
                ET_contact_no.setText(ContactServer);
            }
            if(!DobServer.equals("0")){
                Text_Dob.setText(DobServer);
            }
            if(!SexServer.equals("0")){
                if(SexServer.equals("Male")){
                    radio_Male.setChecked(true);
                }
                if(SexServer.equals("Female")){
                    radio_Female.setChecked(true);
                }

            }

                if(MaritalStatusServer.equals("1")){
                    radio_married.setChecked(true);
                    parent_of_marriedSection.setVisibility(View.VISIBLE);
                    if(!MarriedDateServer.equals("0")){
                        Text_married_date.setText(MarriedDateServer);
                    }
                }
                if(MaritalStatusServer.equals("0")){
                    radio_unmarried.setChecked(true);
                }



            if(!QualificationServer.equals("0")){
                spinnerQualification.setText(QualificationServer);
            }
            if(!EmailIdServer.equals("0") && !EmailIdServer.equals("NA") ){
                editText_emailID.setText(EmailIdServer);
            }
            if(!BloodGrpServer.equals("0")){
                spinner_bloodgrp.setText(BloodGrpServer);
            }




        }


    }
    public SpannableStringBuilder textWithMandatory(String text_Value)

    {
        String simple = text_Value;
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(colored);
        int start = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), 0, start, 0);
        builder.append(simple);
        int end = builder.length();

        // text.setText(builder);

        return builder;

    }
    public void mustFillViews() {
        textviewFirstname=(TextView) findViewById(R.id.textviewFirstname);
        SpannableStringBuilder text_Value = textWithMandatory(textviewFirstname.getText().toString());
        textviewFirstname.setText(text_Value);

        textviewLastname=(TextView) findViewById(R.id.textviewLastname);
        SpannableStringBuilder text_Value2 = textWithMandatory(textviewLastname.getText().toString());
        textviewLastname.setText(text_Value2);

        textviewContact=(TextView) findViewById(R.id.textviewContact);
        SpannableStringBuilder text_Value3 = textWithMandatory(textviewContact.getText().toString());
        textviewContact.setText(text_Value3);

        textviewDOB=(TextView) findViewById(R.id.textviewDOB);
        SpannableStringBuilder text_Value4 = textWithMandatory(textviewDOB.getText().toString());
        textviewDOB.setText(text_Value4);

        textviewSex=(TextView) findViewById(R.id.textviewSex);
        SpannableStringBuilder text_Value5 = textWithMandatory(textviewSex.getText().toString());
        textviewSex.setText(text_Value5);

        textviewMaritalStatus=(TextView) findViewById(R.id.textviewMaritalStatus);
        SpannableStringBuilder text_Value6 = textWithMandatory(textviewMaritalStatus.getText().toString());
        textviewMaritalStatus.setText(text_Value6);

        textviewMarriedDate=(TextView) findViewById(R.id.textviewMarriedDate);
        SpannableStringBuilder text_Value7 = textWithMandatory(textviewMarriedDate.getText().toString());
        textviewMarriedDate.setText(text_Value7);

        textviewQualification=(TextView) findViewById(R.id.textviewQualification);
        SpannableStringBuilder text_Value8 = textWithMandatory(textviewQualification.getText().toString());
        textviewQualification.setText(text_Value8);

        textviewUpdatedPhoto=(TextView) findViewById(R.id.textviewUpdatedPhoto);
        SpannableStringBuilder text_Value9 = textWithMandatory(textviewUpdatedPhoto.getText().toString());
        textviewUpdatedPhoto.setText(text_Value9);

        textviewBloodGrp=(TextView) findViewById(R.id.textviewBloodGrp);
        SpannableStringBuilder text_Value10 = textWithMandatory(textviewBloodGrp.getText().toString());
        textviewBloodGrp.setText(text_Value10);

        textviewSignhere=(TextView) findViewById(R.id.textviewSignhere);
        SpannableStringBuilder text_Value11 = textWithMandatory(textviewSignhere.getText().toString());
        textviewSignhere.setText(text_Value11);



    }
}
