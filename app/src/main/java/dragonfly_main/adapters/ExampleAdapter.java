package com.example.dragonfly_main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    public static ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;
    private Context mContext1;
    private String data3;
    private int data4;



    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onItemClicked(View v, int position);
    }




    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }




    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewLine1;
        public TextView mTextViewLine2;
        public ImageView mDeleteImage;
        public TextView mCount;
        public ConstraintLayout mitemLayout;

        public ExampleViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            mTextViewLine1 = itemView.findViewById(R.id.textview_line1);
            mTextViewLine2 = itemView.findViewById(R.id.textview_line_2);
            mDeleteImage = itemView.findViewById(R.id.image_delete);
            mitemLayout = itemView.findViewById(R.id.itemLayout);
            mCount = itemView.findViewById(R.id.image_count);

            mDeleteImage.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList, Context context) {
        mExampleList = exampleList;
        mContext1 = context;
    }



    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);


        holder.mTextViewLine1.setText(currentItem.getLine1());
        holder.mTextViewLine2.setText(currentItem.getLine2());

        //holder.mCount.setText(String.valueOf(data4));


        holder.mitemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mListener.onItemClicked(view,position);

                SpeciesDialogFragment df = new SpeciesDialogFragment(currentItem);
                SharedPreferences sp = mContext1.getSharedPreferences("count", MODE_PRIVATE);
                data3 = sp.getString("tag", "");
                data4 = sp.getInt("tag1", 0);


                Bundle args = new Bundle();
                args.putCharSequence("data1", currentItem.getLine1());
                args.putCharSequence("data2", currentItem.getLine2());
                args.putString("data3", data3);
                args.putInt("data4", data4);
                df.setArguments(args);
                df.show(((AppCompatActivity) mContext1).getSupportFragmentManager(), "Dialog");
                //holder.mCount.setText(String.valueOf(data4));
                notifyDataSetChanged();

            }
        });


    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}