package com.example.dragonfly_main;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class SpeciesCheckAdapter extends RecyclerView.Adapter<SpeciesCheckAdapter.ExampleViewHolder> implements Filterable {
    private static final String TAG = "SpeciesCheckAdapter";
    private Context mContext;


    private OnItemClickListener listener;


    public static ArrayList<SpeciesCheck> SpeciesList;
    private ArrayList<SpeciesCheck> SpeciesListFull;

    public interface OnItemClickListener {
        void onItemClicked(View v, int position);
    }



    class ExampleViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        TextView mTextView1;
        TextView mTextView2;
        ConstraintLayout mainLayout;

        ExampleViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.species_select);
            mTextView1 = itemView.findViewById(R.id.myText1);
            mTextView2 = itemView.findViewById(R.id.myText2);
            mainLayout = itemView.findViewById(R.id.checkLayout);

        }


    }


    public SpeciesCheckAdapter(OnItemClickListener listener,Context context, ArrayList<SpeciesCheck> SpeciesList) {
        super();
        this.listener = listener;
        this.SpeciesList = SpeciesList;
        mContext = context;
        SpeciesListFull = new ArrayList<>(SpeciesList);
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list,
                parent, false);

        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, final int position) {

        SpeciesCheck currentItem = SpeciesList.get(position);


        holder.mTextView1.setText(currentItem.getspecies_name());
        holder.mTextView2.setText(currentItem.getcommon_name());


        SharedPreferences sp = mContext.getSharedPreferences("count", MODE_PRIVATE);
        String getdetails = sp.getString("tag","");


        holder.mCheckBox.setChecked(currentItem.isSelected());

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (currentItem.isSelected()) {
                    currentItem.setSelected(false);
                    Toast.makeText(mContext, "Removed from List", Toast.LENGTH_SHORT).show();

                } else {
                    currentItem.setSelected(true);
                    Toast.makeText(mContext, "Added to List", Toast.LENGTH_SHORT).show();

                }
            }
        });


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    listener.onItemClicked(view,position);

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
            ArrayList<SpeciesCheck> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(SpeciesListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SpeciesCheck item : SpeciesListFull) {
                    if (item.getspecies_name().toLowerCase().contains(filterPattern) || item.getcommon_name().toLowerCase().contains(filterPattern)) {
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






















/*

//checkbox click event handling
            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton buttonView,
        boolean isChecked) {
        if (isChecked) {
        Toast.makeText(SpeciesCheckAdapter.this.mcontext1,
        "selected brand is " + speciesName.getText(),
        Toast.LENGTH_LONG).show();
        } else {

        }
        }
        });
        */
