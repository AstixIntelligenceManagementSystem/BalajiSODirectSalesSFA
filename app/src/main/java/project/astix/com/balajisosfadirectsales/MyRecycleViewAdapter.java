package project.astix.com.balajisosfadirectsales;

/**
 * Created by Sunil on 12/4/2017.
 */

import java.util.HashMap;
import java.util.regex.Pattern;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder>
{
   // private List<String> values;
    HashMap<String, String> hmapPrdctIdPrdctName;//=new HashMap<String, String>();
    HashMap<String, String> hmapPrdctVolRatTax;
    HashMap<String, String> hmapProductMRP;
    String [] prductId;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView tvProdctName;
        public TextView txtVwRate;
        public EditText et_ProductMRP;
        public View layout;
       public EditText et_OrderQty;
        public TextView tv_Orderval;


        public ViewHolder(View v)
        {
            super(v);
            layout = v;
            tvProdctName = (TextView) v.findViewById(R.id.tvProdctName);
            txtVwRate = (TextView) v.findViewById(R.id.txtVwRate);
            et_ProductMRP = (EditText) v.findViewById(R.id.et_ProductMRP);
            et_OrderQty = (EditText) v.findViewById(R.id.et_OrderQty);
            tv_Orderval = (TextView) v.findViewById(R.id.tv_Orderval);


            et_OrderQty.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s)
                {
                   // data[getAdapterPosition()][1] = s.toString();
                   // Log.d("DATA" + getAdapterPosition() + "1", s.toString());
                    if(!TextUtils.isEmpty(s))
                    {
                        Double totalval=Integer.parseInt(s.toString().trim())*Double.parseDouble(et_ProductMRP.getText().toString().trim());
                        tv_Orderval.setText(""+totalval);
                    }
                    else
                    {
                        tv_Orderval.setText("0.00");
                    }

                }
            });
        }
    }



    public void add(int position, String item)
    {
      //  hmapPrdctIdPrdctName.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position)
    {
        hmapPrdctIdPrdctName.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyRecycleViewAdapter(HashMap<String, String> hmapPrdctIdPrdctName1,HashMap<String, String> hmapPrdctVolRatTax1,HashMap<String, String> hmapProductMRP1,String [] prductId1)
    {
        hmapPrdctIdPrdctName = hmapPrdctIdPrdctName1;
        hmapPrdctVolRatTax=hmapPrdctVolRatTax1;
        hmapProductMRP=hmapProductMRP1;
        prductId = prductId1;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType)
    {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        //View v =inflater.inflate(R.layout.row_layout, parent, false);
        View v=inflater.inflate(R.layout.list_item_card,parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
      //  final String name = hmapPrdctIdPrdctName.get(prductId[position]);
        holder.tvProdctName.setText(hmapPrdctIdPrdctName.get(prductId[position]));
        holder.txtVwRate.setText(hmapPrdctVolRatTax.get(prductId[position]).split(Pattern.quote("."))[2]);
        holder.et_ProductMRP.setText(hmapProductMRP.get(prductId[position]));
      /*  holder.txtHeader.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });*/

        //holder.txtFooter.setText("Footer: " + name);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return hmapPrdctIdPrdctName.size();
    }
}
