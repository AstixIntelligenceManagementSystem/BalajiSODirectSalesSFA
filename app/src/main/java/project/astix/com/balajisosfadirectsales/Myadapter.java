package project.astix.com.balajisosfadirectsales;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.util.SparseArray;
import android.view.ViewGroup;

import com.astix.Common.CommonInfo;

class Myadapter extends FragmentPagerAdapter
{
	 SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
	public Myadapter(FragmentManager fm)
	{
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int i) 
	{
		Fragment fragment=null;
		if(i==0)
		{
			fragment=new FragmentA_first();
		}
		if(i==1)
		{
			if(!CommonInfo.prcID.equals("4") && !CommonInfo.prcID.equals("5"))
			{
				fragment=new FragmentA();
			}
			else
			{
				fragment=new FragmentB();
			}
			
		}
		if(!CommonInfo.prcID.equals("4") && !CommonInfo.prcID.equals("5"))
		{
			if(i==2)
			{
				fragment=new FragmentB();
			}
		}
		
		
		
		return fragment;
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		if(!CommonInfo.prcID.equals("4") && !CommonInfo.prcID.equals("5"))
		{
			return 3;
		}
		else
		{
			return 2;
		}
		
	}
	 	@Override
	    public Object instantiateItem(ViewGroup container, int position) 
	     {
	        Fragment fragment = (Fragment) super.instantiateItem(container, position);
	        registeredFragments.put(position, fragment);
	        return fragment;
	     }

	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object) 
	    {
	        registeredFragments.remove(position);
	        super.destroyItem(container, position, object);
	    }

	    public Fragment getRegisteredFragment(int position) 
	    {
	        return registeredFragments.get(position);
	    }
	
	
}

