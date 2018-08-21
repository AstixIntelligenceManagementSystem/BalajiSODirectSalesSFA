package project.astix.com.balajisosfadirectsales;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;

public class GeocodeAddressIntentService extends IntentService {

	protected ResultReceiver resultReceiver;
	String errorMessage;
	StringBuilder outputAddress =new StringBuilder();
	public GeocodeAddressIntentService() {
		super("GeocodeAddressIntentService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Geocoder geocoder=new Geocoder(this,Locale.ENGLISH);
		 List<Address> addresses = null;
		 resultReceiver = intent.getParcelableExtra("Reciever");
		 

         Location location = intent.getParcelableExtra("Location");

         try {
             addresses = geocoder.getFromLocation(
                     location.getLatitude(), location.getLongitude(), 1);
         } catch (IOException ioException) {
             errorMessage = "Service Not Available";
           
         } catch (IllegalArgumentException illegalArgumentException) {
             errorMessage = "Invalid Latitude or Longitude Used";
           
         }
         
         if (addresses == null || addresses.size()  == 0) {
             if (errorMessage.isEmpty()) {
                 errorMessage = "Not Found";
             }
             deliverResultToReceiver(0, errorMessage, null);
         } else {
        	
             for(Address address : addresses) {
               //  String outputAddress = "";
                 for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                	 if(i==1)
                	 {
                		 outputAddress.append(address.getAddressLine(i));
                	 }
                	 else if(i==2)
                	 {
                		 outputAddress.append(",").append(address.getAddressLine(i));
                	 }
                 }
             }
             Address address = addresses.get(0);
             ArrayList<String> addressFragments = new ArrayList<String>();

             for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                 addressFragments.add(address.getAddressLine(i));
             }
 
             deliverResultToReceiver(1,
                     TextUtils.join(System.getProperty("line.separator"),
                             addressFragments), address);
         }
	}
	private void deliverResultToReceiver(int resultCode, String message, Address address) {
		if(outputAddress==null || outputAddress.equals(""))
		{
			outputAddress.append("");
		}
        Bundle bundle = new Bundle();
        bundle.putParcelable("RESULT_ADDRESS", address);
        bundle.putString("RESULT_DATA_KEY", outputAddress.toString());
        resultReceiver.send(resultCode, bundle);
    }
}
