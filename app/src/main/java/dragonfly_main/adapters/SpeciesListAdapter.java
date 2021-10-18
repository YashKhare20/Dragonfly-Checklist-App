package com.example.dragonfly_main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpeciesListAdapter extends RecyclerView.Adapter<SpeciesListAdapter.ExampleViewHolder> implements Filterable {
    private static final String TAG = "SpeciesListAdapter";
    private Context mContext;

    private ArrayList<Species> SpeciesList = new ArrayList<>();
    private ArrayList<Species> SpeciesListFull = new ArrayList<>();

    static class ExampleViewHolder extends RecyclerView.ViewHolder{
        CircleImageView mImageView;
        TextView mTextView1;
        TextView mTextView2;
        ConstraintLayout mainLayout;

         ExampleViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.myImageView);
            mTextView1 = itemView.findViewById(R.id.myText1);
            mTextView2 = itemView.findViewById(R.id.myText2);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
}


    public SpeciesListAdapter(Context context,ArrayList<Species> SpeciesList) {
        this.SpeciesList = SpeciesList;
        mContext = context;
        SpeciesListFull = new ArrayList<>(SpeciesList);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row,
                parent, false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, final int position) {

        Species currentItem = SpeciesList.get(position);


        holder.mTextView1.setText(currentItem.getspecies_name());
        holder.mTextView2.setText(currentItem.getcommon_name());

       if(mContext != null){
        Glide.with(mContext)
                .asBitmap()
                .load(currentItem.getimg())
                .into(holder.mImageView);}

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /* Intent intent = new Intent(mContext, SecondActivity.class);
                intent.putExtra("data1", currentItem.getspecies_name());
                intent.putExtra("data2", currentItem.getcommon_name());
                intent.putExtra("myImage",currentItem.getimg());
              mContext.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return SpeciesList.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<Species> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(SpeciesListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Species item : SpeciesListFull) {
                        if (item.getspecies_name().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                SpeciesList.clear();
                SpeciesList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };


}

