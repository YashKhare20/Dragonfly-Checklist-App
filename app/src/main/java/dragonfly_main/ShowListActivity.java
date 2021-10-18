package com.example.dragonfly_main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {
    private ArrayList<Species> SpeciesList;


    private RecyclerView mRecyclerView;
        private SpeciesListAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activitylist_21);

            createSpeciesList();
            buildRecyclerView();

            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("List Of Dragonflies");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

        }


        private void createSpeciesList() {

            SpeciesList = new ArrayList<>();
            SpeciesList.add(new Species("Brachythemis contaminata","Ditch Jewel","https://static.inaturalist.org/photos/4939409/medium.jpeg?1474303832"));
            SpeciesList.add(new Species("Pantala flavescens","Wandering Glider","https://static.inaturalist.org/photos/568146/medium.jpg?1383863786"));
            SpeciesList.add(new Species("Trithemis aurora","Crimson Marsh Glider","https://static.inaturalist.org/photos/10507881/medium.jpg?1505606884"));
            SpeciesList.add(new Species("Crocothemis servilia","Ruddy Marsh Skimmer ","https://static.inaturalist.org/photos/15611/medium.jpg?1545361690"));
            SpeciesList.add(new Species("Orthetrum sabina","Green Marsh Hawk","https://static.inaturalist.org/photos/235586/medium.jpg?1545508040"));
            SpeciesList.add(new Species("Orthetrum pruinosum","Crimson-tailed Marsh Hawk","https://static.inaturalist.org/photos/182565/medium.jpg?1545424366"));
            SpeciesList.add(new Species("Ictinogomphus rapax","Common Club tail","https://static.inaturalist.org/photos/27053941/medium.jpg?1540204017"));
            SpeciesList.add(new Species("Anax immaculifrons","Blue Darner","https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Anax_immaculifrons_by_Bala_Chandran.jpg/500px-Anax_immaculifrons_by_Bala_Chandran.jpg"));
            SpeciesList.add(new Species("Neurothemis tullia","Pied Paddy Skimmer","https://static.inaturalist.org/photos/797037/medium.jpg?1545541266"));
            SpeciesList.add(new Species("Neurothemis fulvia","Fulvous Forest Skimmer","https://static.inaturalist.org/photos/165896/medium.jpg?1444560157"));
            SpeciesList.add(new Species("Tholymis tillarga","Coral-tailed Cloud Wing","https://static.inaturalist.org/photos/275830/medium.jpg?1499366122"));
            SpeciesList.add(new Species("Trithemis pallidinervis","Long-legged Marsh Glider","https://static.inaturalist.org/photos/945556/medium.JPG?1403599324"));
            SpeciesList.add(new Species("Trithemis festiva","Black Stream Glider ","https://static.inaturalist.org/photos/197450/medium.jpg?1444610634"));
            SpeciesList.add(new Species("Diplacodes trivialis","Ground Skimmer","https://static.inaturalist.org/photos/18280230/medium.jpeg?1526823237"));
            SpeciesList.add(new Species("Rhyothemis variegata","Common Picture wing","https://static.inaturalist.org/photos/34635/medium.jpg?1444321965"));
            SpeciesList.add(new Species("Acisoma panorpoides","Trumpet Tail","https://static.inaturalist.org/photos/917432/medium.jpg?1545548018"));


        }

        private void buildRecyclerView() {
            mRecyclerView = findViewById(R.id.recyclerView);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mAdapter = new SpeciesListAdapter(this,SpeciesList);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

            // Get drawable object
            Drawable mDivider = ContextCompat.getDrawable(this, R.drawable.divider);
            // Create a DividerItemDecoration whose orientation is Horizontal
            DividerItemDecoration hItemDecoration = new DividerItemDecoration(this,
                    DividerItemDecoration.HORIZONTAL);
            // Set the drawable on it
            hItemDecoration.setDrawable(mDivider);
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.species_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }


    }




