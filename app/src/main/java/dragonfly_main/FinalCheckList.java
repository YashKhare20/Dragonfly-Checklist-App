package com.example.dragonfly_main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dragonfly_main.questions.QuestionActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinalCheckList extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,ExampleAdapter.OnItemClickListener,AdapterView.OnItemSelectedListener,SpeciesDialogFragment.OnInputListener{
    ArrayList<ExampleItem> mExampleList;

    private TextView showNoSpecies;

    private Spinner spinner_gender;
    private Button finalShowBtn;

    @BindView(R.id.location_result)
    TextView txtLocationResult;

    @BindView(R.id.btn_start_location_updates)
    Button btnStartUpdates;

    @BindView(R.id.btn_stop_location_updates)
    Button btnStopUpdates;

    // location last updated time
    private String mLastUpdateTime;

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 5000;

    private static final int REQUEST_CHECK_SETTINGS = 100;

    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private TextView mDateView;
    private TextView textViewTime;
    private String Location;
    private String Date;
    private String Time;
    private String Obs_type;
    private String Obs_count;
    private  EditText obsCount;

    // boolean flag to toggle the ui
    private Boolean mRequestingLocationUpdates;

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private static final int QUESTIONNAIRE_REQUEST = 2018;
    private NoteViewModel noteViewModel;

    final NewNoteAdapter adapter = new NewNoteAdapter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checklist_page);



        //loadData();
        buildRecyclerView();
        //saveData();




        spinner_gender = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ObservationType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter);
        spinner_gender.setOnItemSelectedListener(this);

        ButterKnife.bind(this);

        // initialize the necessary libraries
        init();

        // restore the values from saved instance state
        restoreValuesFromBundle(savedInstanceState);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinalCheckList.this, ShowCheckListActivity.class));
            }
        });


        obsCount = findViewById(R.id.obsCount);



        TextView mDateView = toolbar.findViewById(R.id.dateView);
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        mDateView.setText(currentDate);
        mDateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        Calendar calendar_time = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String currentTime = format.format(calendar_time.getTime());

        TextView textViewTime = toolbar.findViewById(R.id.timeView);
        textViewTime.setText(currentTime);

        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });




        finalShowBtn = findViewById(R.id.final_list);
        finalShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location = txtLocationResult.getText().toString();
                Date = mDateView.getText().toString();
                Time = textViewTime.getText().toString();
                Obs_count = obsCount.getText().toString();
                Obs_type = spinner_gender.getSelectedItem().toString();


                if(Location.isEmpty()){
                    Toast.makeText(FinalCheckList.this, "Location field is empty!", Toast.LENGTH_SHORT).show();
                }
                else if(Obs_count.isEmpty()){
                    Toast.makeText(FinalCheckList.this, "Observer count field is empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent data = new Intent(FinalCheckList.this, FinalSubmitList.class);
                    data.putExtra("location", Location);
                    data.putExtra("date", Date);
                    data.putExtra("time", Time);
                    data.putExtra("obsType", Obs_type);
                    data.putExtra("obsCount", Obs_count);

                    startActivity(data);

                    //startActivity(new Intent(FinalCheckList.this, FinalSubmitList.class));
                }
            }

        });


        ImageButton add_newspecies = findViewById(R.id.add_new);
        add_newspecies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Location = txtLocationResult.getText().toString();
                Obs_count = obsCount.getText().toString();

                int selectedPosition = spinner_gender.getSelectedItemPosition();

                SharedPreferences sp = getSharedPreferences("key", 0);
                SharedPreferences.Editor sedt = sp.edit();
                sedt.putString("mLoc", Location);
                sedt.putString("mObsCount", Obs_count);
                sedt.putInt("spinnerSelection", selectedPosition);
                sedt.apply();

                startActivity(new Intent(FinalCheckList.this, ShowCheckListActivity.class));
            }
        });

        Button habitatReview = findViewById(R.id.habitat_review);
        habitatReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent questions = new Intent(FinalCheckList.this, QuestionActivity.class);
                //you have to pass as an extra the json string.
                questions.putExtra("json_questions", loadQuestionnaireJson("questions_example.json"));
                startActivityForResult(questions, QUESTIONNAIRE_REQUEST);
            }
        });




    }


    //json stored in the assets folder. but you can get it from wherever you like.
    private String loadQuestionnaireJson(String filename)
    {
        try
        {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
            return null;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String common = data.getStringExtra(AddEditNoteActivity.EXTRA_COMMON);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            NewNote note = new NewNote(title, common, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String common = data.getStringExtra(AddEditNoteActivity.EXTRA_COMMON);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);
            NewNote note = new NewNote(title, common, description, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }


    private void init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());

                updateLocationUI();
            }
        };

        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Restoring values from saved instance state
     */
    private void restoreValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("is_requesting_updates")) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean("is_requesting_updates");
            }

            if (savedInstanceState.containsKey("last_known_location")) {
                mCurrentLocation = savedInstanceState.getParcelable("last_known_location");
            }

            if (savedInstanceState.containsKey("last_updated_on")) {
                mLastUpdateTime = savedInstanceState.getString("last_updated_on");
            }
        }

        updateLocationUI();
    }


    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {

            txtLocationResult.setText(String.format("%.2f" , mCurrentLocation.getLatitude()) + "," + String.format("%.2f" ,mCurrentLocation.getLongitude()));

            // giving a blink animation on TextView
            txtLocationResult.setAlpha(0);
            txtLocationResult.animate().alpha(1).setDuration(300);

        }

        toggleButtons();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("is_requesting_updates", mRequestingLocationUpdates);
        outState.putParcelable("last_known_location", mCurrentLocation);
        outState.putString("last_updated_on", mLastUpdateTime);

    }

    private void toggleButtons() {
        if (mRequestingLocationUpdates) {
            btnStartUpdates.setEnabled(false);
            btnStopUpdates.setEnabled(true);
        } else {
            btnStartUpdates.setEnabled(true);
            btnStopUpdates.setEnabled(false);
        }
    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {

                        Toast.makeText(getApplicationContext(), "Updates Started", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                        Button button1 = (Button) findViewById(R.id.btn_start_location_updates);
                        button1.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View view) {

                                //TextView Text = findViewById(R.id.location_result);
                                String sendLocation = ("Lat: " + mCurrentLocation.getLatitude()
                                        + ", Lng: " + mCurrentLocation.getLongitude());

                               /* Intent myIntent = new Intent(view.getContext(),Tab1Fragment.class);
                                myIntent.putExtra("mylocation",sendLocation);
                                startActivity(myIntent);*/
                            }
                        });

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(FinalCheckList.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {

                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";


                                Toast.makeText(FinalCheckList.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }

    @OnClick(R.id.btn_start_location_updates)
    public void startLocationButtonClick() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }

    @OnClick(R.id.btn_stop_location_updates)
    public void stopLocationButtonClick() {
        mRequestingLocationUpdates = false;
        stopLocationUpdates();
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "Location stopped", Toast.LENGTH_SHORT).show();
                        toggleButtons();
                    }
                });
    }



    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        TextView dateView = (TextView) findViewById(R.id.dateView);
        dateView.setText(currentDateString);
    }


    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textViewTime = findViewById(R.id.timeView);
        textViewTime.setText(hourOfDay + ":" + minute);
    }



    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mExampleList);
        editor.putString("task list", json);
        editor.apply();

    }



    private void buildRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        TextView showSpecies = findViewById(R.id.Num_Species);

        for (int i = 0; i < SpeciesCheckAdapter.SpeciesList.size(); i++){
            if(SpeciesCheckAdapter.SpeciesList.get(i).isSelected()) {

                String title = SpeciesCheckAdapter.SpeciesList.get(i).getspecies_name();
                String description = "";
                String common = SpeciesCheckAdapter.SpeciesList.get(i).getcommon_name();
                int priority = 1;
                NewNote note = new NewNote(title, common, description, priority);
                noteViewModel.insert(note);
                //Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();


            }
        }

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Get drawable object
        Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider);
        // Create a DividerItemDecoration whose orientation is Horizontal
        DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        // Set the drawable on it
        hItemDecoration.setDrawable(mDivider);

        noteViewModel.getAllNotes().observe(this, new Observer<List<NewNote>>() {
            @Override
            public void onChanged(@Nullable List<NewNote> notes) {
                adapter.submitList(notes);
                showSpecies.setText(adapter.getItemCount() + " Species");

                SharedPreferences sp = getSharedPreferences("key", 0);
                String mloc = sp.getString("mLoc","");
                String mspcount = sp.getString("mObsCount","");

                if(adapter.getItemCount() != 0){
                    obsCount.setText(mspcount);
                    txtLocationResult.setText(mloc);
                    spinner_gender.setSelection(sp.getInt("spinnerSelection",0));
                }
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(FinalCheckList.this, "Note deleted", Toast.LENGTH_SHORT).show();
                showSpecies.setText(adapter.getItemCount() + " Species");
            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new NewNoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NewNote note) {
                Intent intent = new Intent(FinalCheckList.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_COMMON, note.getCommon_name());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });



    }


    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void onItemClicked(View v, int position) {

    }

    @Override
    public void sendInput(int input) {

    }
}
