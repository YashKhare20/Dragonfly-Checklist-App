package com.example.dragonfly_main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dragonfly_main.questions.database.AppDatabase;
import com.example.dragonfly_main.questions.qdb.QuestionEntity;
import com.example.dragonfly_main.questions.qdb.QuestionWithChoicesEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FinalSubmitList extends BaseActivity2 {

    Context context;
    LinearLayout resultLinearLayout;
    List<QuestionEntity> questionsList = new ArrayList<>();
    List<QuestionWithChoicesEntity> questionsWithAllChoicesList = new ArrayList<>();
    private AppDatabase appDatabase;
    private NoteDatabase speciesDatabase;
    LiveData<List<NewNote>> notes;
    private NoteViewModel noteViewModel;
    String mLocation,mTime,mDate,mObsType, mObsCount;
    JSONArray speciesDetailsArray = new JSONArray();
    JSONArray questionsWithAnswerArray = new JSONArray();
    JSONArray questionAndAnswerArray = new JSONArray();
    JSONArray selectedAnswerJSONArray = new JSONArray();

    private static final String TAG = "FinalSubmitList";
    private static final String REQUIRED = "Required";



    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_finalsubmit);

        context = this;

        speciesDatabase = NoteDatabase.getInstance(this);
        appDatabase = AppDatabase.getAppDatabase(this);

        resultLinearLayout = findViewById(R.id.resultLinearLayout);
        toolBarInit();

        getResultFromDatabase1();
        getResultFromDatabase();




        //declare the database reference object. This is what we use to access the database.
        //NOTE: Unless you are signed in, this will not be useable.
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    toastMessage("Successfully signed out.");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    Object value = dataSnapshot.getValue();
                   /* for(DataSnapshot ds :dataSnapshot.getChildren()){
                        String key = ds.getKey();
                    }*/
                    Log.d(TAG, "Value is: " + value);
                }
                else {
                    /////
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Button submitEntry = findViewById(R.id.submit_entry);
        submitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Attention!")
                        .setMessage("The data submitted by me can be used for research and conservation and I am fully responsible for the accuracy of the data submitted!")
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    submitPost();
                                    toastMessage("Record Submitted Successfully!!");
                                    SharedPreferences sp = getSharedPreferences("key", 0);
                                    SharedPreferences.Editor sedt = sp.edit();
                                    sedt.putString("mLoc", "");
                                    sedt.putString("mObsCount", "");
                                    sedt.putInt("spinnerSelection", 0);
                                    sedt.apply();
                                    noteViewModel.deleteAllNotes();
                                    questionAndAnswerArray = new JSONArray();
                                    questionsWithAnswerArray = new JSONArray();
                                    selectedAnswerJSONArray = new JSONArray();
                                    startActivity(new Intent(FinalSubmitList.this, BaseActivity.class));

                            }

                        });
                 builder.create();
                 builder.show();
            }
        });

        Intent intent = getIntent();
        mLocation = intent.getStringExtra("location");
        mTime = intent.getStringExtra("time");
        mDate = intent.getStringExtra("date");
        mObsType = intent.getStringExtra("obsType");
        mObsCount = intent.getStringExtra("obsCount");


        TextView textView1 = findViewById(R.id.Location);
        TextView textView2 = findViewById(R.id.Date);
        TextView textView3 = findViewById(R.id.Time);
        TextView textView4 = findViewById(R.id.ObsType);
        TextView textView5 = findViewById(R.id.ObsCount);


        textView1.setText("• Location: " + mLocation);
        textView1.setPadding(60, 30, 16, 30);
        textView2.setText("• Date: " + mDate);
        textView2.setPadding(60, 30, 16, 30);
        textView3.setText("• Time: " + mTime);
        textView3.setPadding(60, 30, 16, 30);
        textView4.setText("• Observer Type: " + mObsType);
        textView4.setPadding(60, 30, 16, 30);
        textView5.setText("• Observer Count: " + mObsCount);
        textView5.setPadding(60, 30, 16, 30);



    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void toolBarInit()
    {
        Toolbar answerToolBar = findViewById(R.id.answerToolbar);
        //answerToolBar.setNavigationIcon(R.drawable.ic_back);
        //answerToolBar.setNavigationOnClickListener(v -> onBackPressed());

        setSupportActionBar(answerToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        answerToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });


        ImageButton shareBtn = answerToolBar.findViewById(R.id.shareButton);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendMail();
            }
        });

    }

    private void sendMail() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Location: " + mLocation + "\nDate: " +  mDate  + "\nTime: " + mTime +  "\n\n• Species Info: " + speciesDetailsArray + "\n\n• Habitat Info: "  +  questionAndAnswerArray);
        startActivity(Intent.createChooser(intent, "Share list via:"));


    }

    @SuppressLint("NewApi")
    private void submitPost() {
        StringBuilder species_name = new StringBuilder();
        StringBuilder answer = new StringBuilder();
        StringBuilder answer1 = new StringBuilder();
        StringBuilder count = new StringBuilder();

        for (int j = 0; j < speciesDetailsArray.length(); j++) {
            try {


                    species_name.insert(0, speciesDetailsArray.getJSONObject(j).getString("Species_name") + ",");
                    answer.insert(0, speciesDetailsArray.getJSONObject(j).getString("Species_details") + ",");
                    count.insert(0, speciesDetailsArray.getJSONObject(j).getString("Species_count") + ",");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        try {
            for (int i = 0; i < questionAndAnswerArray.length(); i++) {

                selectedAnswerJSONArray = questionAndAnswerArray.getJSONObject(i).getJSONArray("selected_answer");
                for (int j = 0; j < selectedAnswerJSONArray.length(); j++) {

                    answer1.insert(0, selectedAnswerJSONArray.getJSONObject(j).getString("answer_choice") + ",");


                }

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }


                // [START single_value_read]
        final String userId = getUid();
        String finalSpecies_name = species_name.toString().toString();
        String finalAnswer = answer.toString();
        String finalAnswer1 = answer1.toString();
        String finalCount = count.toString();
        myRef.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);

                        // [START_EXCLUDE]
                        if (user == null) {
                            // User is null, error out
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(FinalSubmitList.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // Write new post
                            writeNewPost(userId, mLocation, mDate, mTime, finalSpecies_name, finalAnswer, finalCount, finalAnswer1,mObsType,mObsCount);
                        }

                        // Finish this Activity, back to the stream
                        finish();
                        // [END_EXCLUDE]
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });
        // [END single_value_read]
    }

    private void writeNewPost(String userId, String location, String date, String time, String speciesName, String details, String  count, String habAns,String obsType, String obsCount) {
        // Create new post at /user-posts/$userid/$postid and at
        ///posts/$postid simultaneously
        String key = myRef.child("posts").push().getKey();
        MyList species = new MyList(userId, location, date, time, speciesName, details, count, habAns, obsType, obsCount);
        Map<String, Object> postValues = species.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        myRef.updateChildren(childUpdates);
    }

    private void getResultFromDatabase1(){

        Completable.fromAction(() -> {
            notes =  speciesDatabase.noteDao().getAllNotes();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onComplete()
                    {
                        makeJsonDataToMakeResultView1();
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }
                });

    }


    private void makeJsonDataToMakeResultView1()
    {
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        noteViewModel.getAllNotes().observe(this, new Observer<List<NewNote>>() {
                @Override
                public void onChanged(@Nullable List<NewNote> notes) {
                    //JSONArray speciesDetailsArray = new JSONArray();
                    int SpeciesSize = notes.size();
                    if (SpeciesSize > 0)
                    {
                        for (int i = 0; i < SpeciesSize; i++)
                        {
                            JSONObject SpeciesName = new JSONObject();
                            try {
                                SpeciesName.put("Species_name", notes.get(i).getTitle());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                SpeciesName.put("Species_details", notes.get(i).getDescription());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                             String questionId = String.valueOf(notes.get(i).getPriority());
                            try {
                                SpeciesName.put("Species_count",questionId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            speciesDetailsArray.put(SpeciesName);
                        }
                    }

                    SpeciesView(speciesDetailsArray);

                }
            });

        }

    private void SpeciesView(JSONArray speciesDetailsArray)
    {
        if (speciesDetailsArray.length() > 0)
        {
            try
            {
                for (int i = 0; i < speciesDetailsArray.length(); i++)
                {
                    String species_name = speciesDetailsArray.getJSONObject(i).getString("Species_name");

                    TextView questionTextView = new TextView(context);
                    questionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    questionTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    questionTextView.setPadding(40, 30, 16, 30);
                    questionTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    questionTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    questionTextView.setTypeface(null, Typeface.BOLD);
                    questionTextView.setText(species_name);

                    resultLinearLayout.addView(questionTextView);

                    String answer = speciesDetailsArray.getJSONObject(i).getString("Species_details");
                    String count = speciesDetailsArray.getJSONObject(i).getString("Species_count");
                    String formattedAnswer = "• Details: " + answer; // alt + 7 --> •
                    String formattedCount = "• Numbers Observed: " + count;

                    TextView answerTextView = new TextView(context);
                    answerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    answerTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
                    answerTextView.setPadding(60, 30, 16, 30);
                    answerTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    answerTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    answerTextView.setText(formattedAnswer);


                    TextView answerTextView1 = new TextView(context);
                    answerTextView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    answerTextView1.setTextColor(ContextCompat.getColor(context, R.color.black));
                    answerTextView1.setPadding(60, 30, 16, 30);
                    answerTextView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    answerTextView1.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    answerTextView1.setText(formattedCount);

                    View view = new View(context);
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));

                    resultLinearLayout.addView(answerTextView1);
                    resultLinearLayout.addView(answerTextView);
                    resultLinearLayout.addView(view);

                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void getResultFromDatabase()
    {
        Completable.fromAction(() -> {
            questionsList = appDatabase.getQuestionDao().getAllQuestions();
            questionsWithAllChoicesList = appDatabase.getQuestionChoicesDao().getAllQuestionsWithChoices("1");
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {

                    }

                    @Override
                    public void onComplete()
                    {
                        makeJsonDataToMakeResultView();
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                    }
                });

    }


    /*Here, JSON got created and send to make Result View as per Project requirement.
     * Alternatively, in your case, you make Network-call to send the result to back-end.*/
    private void makeJsonDataToMakeResultView()
    {
        try
        {
            //JSONArray questionAndAnswerArray = new JSONArray();
            int questionsSize = questionsList.size();
            if (questionsSize > 0)
            {
                for (int i = 0; i < questionsSize; i++)
                {
                    JSONObject questionName = new JSONObject();
                    questionName.put("question", questionsList.get(i).getQuestion());
                    //questionName.put("question_id", String.valueOf(questionsList.get(i).getQuestionId()));
                    String questionId = String.valueOf(questionsList.get(i).getQuestionId());

                    JSONArray answerChoicesList = new JSONArray();
                    int selectedChoicesSize = questionsWithAllChoicesList.size();
                    for (int k = 0; k < selectedChoicesSize; k++)
                    {
                        String questionIdOfChoice = questionsWithAllChoicesList.get(k).getQuestionId();
                        if (questionId.equals(questionIdOfChoice))
                        {
                            JSONObject selectedChoice = new JSONObject();
                            selectedChoice.put("answer_choice", questionsWithAllChoicesList.get(k).getAnswerChoice());
                            //selectedChoice.put("answer_id", questionsWithAllChoicesList.get(k).getAnswerChoiceId());
                            answerChoicesList.put(selectedChoice);
                        }
                    }
                    questionName.put("selected_answer", answerChoicesList);

                    questionAndAnswerArray.put(questionName);
                }
            }

            questionsAnswerView(questionAndAnswerArray);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }





    private void questionsAnswerView(JSONArray questionsWithAnswerArray)
    {
        if (questionsWithAnswerArray.length() > 0)
        {
            try
            {
                for (int i = 0; i < questionsWithAnswerArray.length(); i++)
                {
                    String question = questionsWithAnswerArray.getJSONObject(i).getString("question");

                    TextView questionTextView = new TextView(context);
                    questionTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    questionTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
                    questionTextView.setPadding(40, 30, 16, 30);
                    questionTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    questionTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    questionTextView.setTypeface(null, Typeface.BOLD);
                    questionTextView.setText(question);

                    resultLinearLayout.addView(questionTextView);

                    selectedAnswerJSONArray = questionsWithAnswerArray.getJSONObject(i).getJSONArray("selected_answer");

                    for (int j = 0; j < selectedAnswerJSONArray.length(); j++)
                    {
                        String answer = selectedAnswerJSONArray.getJSONObject(j).getString("answer_choice");
                        String formattedAnswer = "• " + answer; // alt + 7 --> •

                        TextView answerTextView = new TextView(context);
                        answerTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                        answerTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
                        answerTextView.setPadding(60, 30, 16, 30);
                        answerTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        answerTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                        answerTextView.setText(formattedAnswer);

                        View view = new View(context);
                        view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));

                        resultLinearLayout.addView(answerTextView);
                        resultLinearLayout.addView(view);
                    }
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }





}
