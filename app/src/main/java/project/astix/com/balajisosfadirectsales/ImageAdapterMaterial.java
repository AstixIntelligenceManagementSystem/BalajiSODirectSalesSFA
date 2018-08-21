package project.astix.com.balajisosfadirectsales;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


class ImageAdapterMaterial extends BaseAdapter
 {
	 private Context context;
	 private List<Bitmap> itemsName;
	 int imgLocation;
	 int picPostion;
	 List<String> productIdTag;
	 LayoutInflater inflater ;
	
	 DeletePic delPicInterface;
	
	public static class ViewHolder
	{
        public ImageView img_thumbnail;
        public ImageView imgCncl;
    }


	public ImageAdapterMaterial(Context context) 
	{
		this.context = context;
		itemsName=new ArrayList<Bitmap>();
		productIdTag=new ArrayList<String>();
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	 public void add(int location,Bitmap imageValues,String imagename)
	 {
	        itemsName.add(location,imageValues);
	        productIdTag.add(location,imagename);
	        picPostion=location;
	        notifyDataSetChanged();
	 }
	 
	 public void remove(Bitmap bmp)
	    {
	      
	        itemsName.remove(bmp);
	        notifyDataSetChanged();
	    }


	public View getView(final int position, View convertView, ViewGroup parent) 
	{

	    final ViewHolder holder;
		

		if (convertView == null) 
		{

		    // get layout from mobile.xml
			convertView = inflater.inflate(R.layout.images_return_grid, null);
			holder=new ViewHolder();
			holder.img_thumbnail = (ImageView) convertView.findViewById(R.id.img_thumbnail);
			holder.imgCncl = (ImageView) convertView.findViewById(R.id.imgCncl);
			holder.imgCncl.setTag(picPostion);
			
			
		    delPicInterface=(DeletePic) context;
		    convertView.setTag(holder);
		       
		} 
		else
		{
			 holder = (ViewHolder) convertView.getTag();
		}
		
		 holder.img_thumbnail.setImageBitmap(itemsName.get(position));
		 final Bitmap info = itemsName.get(position);
		 final String productIdDelPhotoDetail=productIdTag.get(position);
		 holder.img_thumbnail.setTag(productIdTag.get(position));
		 System.out.println("Picture Bitmap "+position);
		 holder.imgCncl.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					delPicInterface.delPic(info,holder.img_thumbnail.getTag().toString());
					productIdTag.remove(productIdDelPhotoDetail);
				}
			});
	        holder.img_thumbnail.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					delPicInterface.getProductPhotoDetail(productIdDelPhotoDetail);
				}
			});
	

		return convertView;
	}
	

	@Override
	public int getCount() {
		return itemsName.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	

}