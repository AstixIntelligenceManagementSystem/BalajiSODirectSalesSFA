package project.astix.com.balajisosfadirectsales;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Add_New_Store_DynamicSectionWise extends Activity {
	public PRJDatabase helperDb;
	LinkedHashMap<String, String> hmapDistinctDiffrentSection=new LinkedHashMap<String, String>();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newstore_fragmrntmain);
		helperDb=new PRJDatabase(Add_New_Store_DynamicSectionWise.this);
		hmapDistinctDiffrentSection=helperDb.fnGetDistinctSenction();
		
		LinearLayout fragContainer = (LinearLayout) findViewById(R.id.llFragmentContainer);
		for(Entry<String, String> entry:hmapDistinctDiffrentSection.entrySet())
		{
			
			
			LinearLayout rowLayout = new LinearLayout(this);

			android.app.FragmentManager fragMan = getFragmentManager();
			android.app.FragmentTransaction fragTransaction =fragMan.beginTransaction();

			rowLayout.setId(Integer.parseInt(entry.getKey()));

			// add rowLayout to the root layout somewhere here

			Fragment myFrag = new Fragment();
			//fragTransaction.add(rowLayout.getId(), myFrag , "fragment" + 1);
			fragTransaction.add(rowLayout.getId(), TestFragment.newInstance("I am frag "+entry.getKey()), "someTag_"+entry.getKey()).commit();
			//fragTransaction.commit();
			fragContainer.addView(rowLayout);
		}
		

	}
	
	public static class TestFragment extends Fragment {

	    public static TestFragment newInstance(String text) {

	        TestFragment f = new TestFragment();

	        Bundle b = new Bundle();
	        b.putString("text", text);
	        f.setArguments(b);
	        return f;
	    }
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

	        View v =  inflater.inflate(R.layout.fragment_template, container, false);

	        ((TextView) v.findViewById(R.id.tvFragText)).setText(getArguments().getString("text"));     
	        return v;
	    }
	}


	
}
