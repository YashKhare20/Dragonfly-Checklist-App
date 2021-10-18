package com.example.dragonfly_main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class SpeciesDialogFragment extends AppCompatDialogFragment {

     ExampleItem exampleItem;
     private EditText mcount;
    private static final String TAG = "MyCustomDialog";

    public interface OnInputListener{
        void sendInput(int input);
    }
    public OnInputListener mOnInputListener;



    public SpeciesDialogFragment(ExampleItem exampleItem) {
       this.exampleItem = exampleItem;
    }




    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_dialog, null);



        SharedPreferences sp = getActivity().getSharedPreferences("count", MODE_PRIVATE);
        //mdetails.setText(sp.getString("tag",""));

        mcount = view.findViewById(R.id.count);
        EditText mdetails = view.findViewById(R.id.details);




        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        int value = Integer.parseInt(mcount.getText().toString());
                        String value_detail = mdetails.getText().toString();
                        mOnInputListener.sendInput(value);
                       /* SharedPreferences.Editor preferencesEditor = sp.edit();
                        preferencesEditor.putString("tag", value_detail);
                        preferencesEditor.putInt("tag1", value);
                        preferencesEditor.apply();*/

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SpeciesDialogFragment.this.getDialog().cancel();
                    }
                });


        TextView sName = view.findViewById(R.id.species_name);
        sName.setText(getArguments().getCharSequence("data1"));
        TextView cName = view.findViewById(R.id.common_name);
        cName.setText(getArguments().getCharSequence("data2"));
        /*mdetails.setText(getArguments().getString("data3",""));
        mcount.setText(String.valueOf(getArguments().getInt("data4",0)));
*/

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnInputListener = (OnInputListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }


}
