package com.example.dragonfly_main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowCheckListActivity extends AppCompatActivity implements SpeciesCheckAdapter.OnItemClickListener{
    private ArrayList<SpeciesCheck> SpeciesList;


    private RecyclerView mRecyclerView;
    private SpeciesCheckAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SpeciesCheckAdapter.OnItemClickListener listener;
    //private Spinner spinner_list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.species_list_check);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("List Of Dragonflies");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowCheckListActivity.this, BaseActivity.class));
            }
        });

     /*   BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.include2);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    startActivity(new Intent(ShowCheckListActivity.this, BaseActivity.class));
                    break;
                case R.id.action_profile:
                    startActivity(new Intent(ShowCheckListActivity.this, FinalCheckList.class));
                    break;
                *//*case R.id.action_nearby:
                    Toast.makeText(MainActivity.this, "Nearby", Toast.LENGTH_SHORT).show();
                    break;*//*
            }
            return true;
        });*/

        Button reviewList = findViewById(R.id.review_entry);
        reviewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowCheckListActivity.this, FinalCheckList.class));
            }
        });



        createSpeciesList_India();
        buildRecyclerView();
       /* setupSort();*/


    }

   /* private void setupSort() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.sort_types,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_list.setAdapter(adapter);

        spinner_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                   createSpeciesList_India();

                } else if(position == 1) {
                    createSpeciesList_North();

                }
                else if(position == 2) {
                    createSpeciesList_South();

                }
                else if(position == 3) {
                    createSpeciesList_West();

                }
                else {
                    createSpeciesList_East();

                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }*/

    private void createSpeciesList_India() {

        SpeciesList = new ArrayList<>();
        SpeciesList.add(new SpeciesCheck("UnIdentified Species","",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum andamanicum","Andaman Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Caconeura t-coerulea","",false));
        SpeciesList.add(new SpeciesCheck("Bradinopyga konkanensis","Konkan Rockdweller",false));
        SpeciesList.add(new SpeciesCheck("Tetracanthagyna waterhousei","",false));
        SpeciesList.add(new SpeciesCheck("Acrogomphus fraseri","",false));
        SpeciesList.add(new SpeciesCheck("Macrogomphus wynaadicus","Wayanad Bowtail",false));
        SpeciesList.add(new SpeciesCheck("Macromidia donaldi","",false));
        SpeciesList.add(new SpeciesCheck("Lestes malabaricus","Malabar Spreadwing",false));
        SpeciesList.add(new SpeciesCheck("Chlorogomphus xanthoptera","",false));
        SpeciesList.add(new SpeciesCheck("Ictinogomphus decoratus","Common Flangetail",false));
        SpeciesList.add(new SpeciesCheck("Onychogomphus nilgiriensis","Nilgiri Clawtail",false));
        SpeciesList.add(new SpeciesCheck("Nososticta nicobarica","",false));
        SpeciesList.add(new SpeciesCheck("Gomphidia podhigai","",false));
        SpeciesList.add(new SpeciesCheck("Lestes patricia","",false));
        SpeciesList.add(new SpeciesCheck("Asiagomphus nilgiricus","",false));
        SpeciesList.add(new SpeciesCheck("Macromia ellisoni","",false));
        SpeciesList.add(new SpeciesCheck("Idionyx travancorensis","",false));
        SpeciesList.add(new SpeciesCheck("Idionyx saffronata","",false));
        SpeciesList.add(new SpeciesCheck("Idionyx corona","",false));
        SpeciesList.add(new SpeciesCheck("Dubitogomphus bidentatus","",false));
        SpeciesList.add(new SpeciesCheck("Cyclogomphus heterostylus","",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia carminea","",false));
        SpeciesList.add(new SpeciesCheck("Ischnura inarmata","",false));
        SpeciesList.add(new SpeciesCheck("Burmagomphus hasimaricus","",false));
        SpeciesList.add(new SpeciesCheck("Burmagomphus sivalikensis","",false));
        SpeciesList.add(new SpeciesCheck("Anisogomphus occipitalis","Shivalik Clubtail",false));
        SpeciesList.add(new SpeciesCheck("Cyclogomphus flavoannulatus","",false));
        SpeciesList.add(new SpeciesCheck("Paragomphus echinoccipitalis","",false));
        SpeciesList.add(new SpeciesCheck("Phaenandrogomphus aureus","",false));
        SpeciesList.add(new SpeciesCheck("Lestes thoracicus","",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion pilidorsum","",false));
        SpeciesList.add(new SpeciesCheck("Gynacantha subinterrupta","",false));
        SpeciesList.add(new SpeciesCheck("Macrogomphus montanus","",false));
        SpeciesList.add(new SpeciesCheck("Megalogomphus flavicolor","",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia miles","",false));
        SpeciesList.add(new SpeciesCheck("Aristocypha spuria","",false));
        SpeciesList.add(new SpeciesCheck("Hemicordulia asiatica","",false));
        SpeciesList.add(new SpeciesCheck("Protosticta hearseyi","",false));
        SpeciesList.add(new SpeciesCheck("Heliogomphus promelas","Indian Lyretail",false));
        SpeciesList.add(new SpeciesCheck("Camacinia harterti","",false));
        SpeciesList.add(new SpeciesCheck("Libellago balus","",false));
        SpeciesList.add(new SpeciesCheck("Gomphidia t-nigrum","",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion chromothorax","Sindhudurg Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Elattoneura nigerrima","",false));
        SpeciesList.add(new SpeciesCheck("Lyriothemis tricolor","",false));
        SpeciesList.add(new SpeciesCheck("Gynacantha khasiaca","",false));
        SpeciesList.add(new SpeciesCheck("Platylestes platystylus","",false));
        SpeciesList.add(new SpeciesCheck("Caconeura risi","",false));
        SpeciesList.add(new SpeciesCheck("Aristocypha hilaryae","",false));
        SpeciesList.add(new SpeciesCheck("Indolestes indicus","",false));
        SpeciesList.add(new SpeciesCheck("Lyriothemis bivittata","",false));
        SpeciesList.add(new SpeciesCheck("Gomphidia leonorae","",false));
        SpeciesList.add(new SpeciesCheck("Anisogomphus caudalis","",false));
        SpeciesList.add(new SpeciesCheck("Coeliccia renifera","",false));
        SpeciesList.add(new SpeciesCheck("Chlorogomphus mortoni","",false));
        SpeciesList.add(new SpeciesCheck("Aeshna petalura","",false));
        SpeciesList.add(new SpeciesCheck("Megalestes irma","",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion australasiae","",false));
        SpeciesList.add(new SpeciesCheck("Sympecma paedisca","Siberian Winter Damsel",false));
        SpeciesList.add(new SpeciesCheck("Macromia cingulata","",false));
        SpeciesList.add(new SpeciesCheck("Cyclogomphus wilkinsi","",false));
        SpeciesList.add(new SpeciesCheck("Ictinogomphus distinctus","",false));
        SpeciesList.add(new SpeciesCheck("Lestes concinnus","",false));
        SpeciesList.add(new SpeciesCheck("Indothemis limbata","Restless Demon",false));
        SpeciesList.add(new SpeciesCheck("Macromia moorei","",false));
        SpeciesList.add(new SpeciesCheck("Perissogomphus stevensi","",false));
        SpeciesList.add(new SpeciesCheck("Polycanthagyna erythromelas","",false));
        SpeciesList.add(new SpeciesCheck("Pseudothemis zonata ","Pied Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Planaeschna poumai","",false));
        SpeciesList.add(new SpeciesCheck("Cephalaeschna acanthifrons","",false));
        SpeciesList.add(new SpeciesCheck("Ischnura forcipata","Forcipate Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Burmagomphus divaricatus","",false));
        SpeciesList.add(new SpeciesCheck("Davidioides martini","",false));
        SpeciesList.add(new SpeciesCheck("Dysphaea gloriosa","",false));
        SpeciesList.add(new SpeciesCheck("Euphaea masoni","",false));
        SpeciesList.add(new SpeciesCheck("Sympetrum speciosum","",false));
        SpeciesList.add(new SpeciesCheck("Coeliccia svihleri","",false));
        SpeciesList.add(new SpeciesCheck("Macrogomphus seductus","",false));
        SpeciesList.add(new SpeciesCheck("Elattoneura tetrica","",false));
        SpeciesList.add(new SpeciesCheck("Platygomphus dolabratus","",false));
        SpeciesList.add(new SpeciesCheck("Anax parthenope","",false));
        SpeciesList.add(new SpeciesCheck("Microgomphus torquatus","",false));
        SpeciesList.add(new SpeciesCheck("Schmidtiphaea chittaranjani","",false));
        SpeciesList.add(new SpeciesCheck("Anisopleura subplatystyla","",false));
        SpeciesList.add(new SpeciesCheck("Coeliccia didyma","",false));
        SpeciesList.add(new SpeciesCheck("Onychogomphus duaricus","",false));
        SpeciesList.add(new SpeciesCheck("Cyclogomphus ypsilon","",false));
        SpeciesList.add(new SpeciesCheck("Nepogomphus modestus","",false));
        SpeciesList.add(new SpeciesCheck("Burmagomphus pyramidalis ","Sinuate Clubtail",false));
        SpeciesList.add(new SpeciesCheck("Anotogaster nipalensis","",false));
        SpeciesList.add(new SpeciesCheck("Stylogomphus inglisi","",false));
        SpeciesList.add(new SpeciesCheck("Nepogomphus walli","",false));
        SpeciesList.add(new SpeciesCheck("Anisopleura vallei","",false));
        SpeciesList.add(new SpeciesCheck("Lyriothemis acigastra","",false));
        SpeciesList.add(new SpeciesCheck("Merogomphus longistigma","",false));
        SpeciesList.add(new SpeciesCheck("Tramea transmarina","",false));
        SpeciesList.add(new SpeciesCheck("Zygonyx iris ","Emerald Cascader",false));
        SpeciesList.add(new SpeciesCheck("Zyxomma petiolatum","Brown Dusk Hawk",false));
        SpeciesList.add(new SpeciesCheck("Merogomphus martini","",false));
        SpeciesList.add(new SpeciesCheck("Aristocypha cuneata","",false));
        SpeciesList.add(new SpeciesCheck("Macrogomphus annulatus","",false));
        SpeciesList.add(new SpeciesCheck("Esme longistyla","",false));
        SpeciesList.add(new SpeciesCheck("Gynacantha bayadera","Parakeet Darner",false));
        SpeciesList.add(new SpeciesCheck("Hydrobasileus croceus ","Amber-Winged Marsh Glider",false));
        SpeciesList.add(new SpeciesCheck("Onychogomphus acinaces","",false));
        SpeciesList.add(new SpeciesCheck("Gomphidia kodaguensis","",false));
        SpeciesList.add(new SpeciesCheck("Merogomphus tamaracherriensis","",false));
        SpeciesList.add(new SpeciesCheck("Microgomphus souteri","",false));
        SpeciesList.add(new SpeciesCheck("Brachydiplax sobrina","Little Blue Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Brachydiplax farinosa","Black-Tailed Dasher",false));
        SpeciesList.add(new SpeciesCheck("Gynacantha dravida","Brown Darner",false));
        SpeciesList.add(new SpeciesCheck("Paracercion malayanum","Malayan Lilly-Squatter",false));
        SpeciesList.add(new SpeciesCheck("Protosticta sanguinostigma","Red-Spot Reedtail",false));
        SpeciesList.add(new SpeciesCheck("Lyriothemis mortoni","",false));
        SpeciesList.add(new SpeciesCheck("Epithemis mariae","Ruby Hawklet",false));
        SpeciesList.add(new SpeciesCheck("Diplacodes nebulosa","Black-tipped Grounnd Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Camacinia gigantea"," Giant Forest Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Diplacodes lefebvrii","Black Ground Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Cratilla lineata","Emerald-Banded Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Tramea limbata","Black Marsh Trotter",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum japonicum","",false));
        SpeciesList.add(new SpeciesCheck("Agrionoptera insignis","",false));
        SpeciesList.add(new SpeciesCheck("Neurothemis intermedia","",false));
        SpeciesList.add(new SpeciesCheck("Onychogomphus biforceps","",false));
        SpeciesList.add(new SpeciesCheck("Macrodiplax cora","Estuarine Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis kalinga","Indian Hooded Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Anaciaeschna jaspidea","Rusty Darner",false));
        SpeciesList.add(new SpeciesCheck("Anax guttatus","Blue-Tailed Green Darner",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum taeniolatum","Small Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Epophthalmia vittata","Common Torrent Hawk",false));
        SpeciesList.add(new SpeciesCheck("Tramea virginia","",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum triangulare","Blue-Tailed Forest Hawk",false));
        SpeciesList.add(new SpeciesCheck("Sympetrum fonscolombii","Red-Veined Darter",false));
        SpeciesList.add(new SpeciesCheck("Argiocnemis rubescens "," Variable Sprite",false));
        SpeciesList.add(new SpeciesCheck("Hylaeothemis indica","Blue Hawklet",false));
        SpeciesList.add(new SpeciesCheck("Paragomphus lineatus","Common Hook-Tail",false));
        SpeciesList.add(new SpeciesCheck("Aethriamanta brevipennis","Scarlet Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Acisoma panorpoides","Trumpet Tail",false));
        SpeciesList.add(new SpeciesCheck("Tholymis tillarga","Coral-Tailed Cloudwing",false));
        SpeciesList.add(new SpeciesCheck("Lathrecista asiatica","Asiatic Blood-Tail",false));
        SpeciesList.add(new SpeciesCheck("Hemianax ephippiger","Vagrant Emperor",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum luzonicum","Tri-coloured Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Sympetrum commixtum","",false));
        SpeciesList.add(new SpeciesCheck("Anax nigrofasciatus","",false));
        SpeciesList.add(new SpeciesCheck("Sympetrum orientale","",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum pruinosum","Crimson-tailed Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Megalestes major","Giant Green Spreadwing",false));
        SpeciesList.add(new SpeciesCheck("Matrona basilaris","",false));
        SpeciesList.add(new SpeciesCheck("Protosticta ponmudiensis ","Travancore Reedtail",false));
        SpeciesList.add(new SpeciesCheck("Protosticta gravelyi","Pied Reedtail",false));
        SpeciesList.add(new SpeciesCheck("Phylloneura westermanni","Myristica Bambootail",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum glaucum","Blue Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Urothemis signata","Greater Crimson Glider",false));
        SpeciesList.add(new SpeciesCheck("Trithemis pallidinervis","Long-Legged Marsh Glider",false));
        SpeciesList.add(new SpeciesCheck("Indothemis carnatica","Light-tipped Demon",false));
        SpeciesList.add(new SpeciesCheck("Trithemis kirbyi"," Scarlet Rock Glider",false));
        SpeciesList.add(new SpeciesCheck("Amphiallagma parvum","Azure Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Rhodothemis rufa","Rufous Marsh Glider",false));
        SpeciesList.add(new SpeciesCheck("Rhyothemis triangularis","Lesser Blue-Wing",false));
        SpeciesList.add(new SpeciesCheck("Neurothemis fluctuans","Grasshawk Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Esme mudiensis","Travancore Bambootail",false));
        SpeciesList.add(new SpeciesCheck("Palpopleura sexmaculata","Blue-tailed Yellow Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Brachydiplax chalybea","Rufous-Backed Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Prodasineura autumnalis","Black Threadtail",false));
        SpeciesList.add(new SpeciesCheck("Anisopleura comes","",false));
        SpeciesList.add(new SpeciesCheck("Heliocypha perforata","Stream Sapphire",false));
        SpeciesList.add(new SpeciesCheck("Aristocypha trifasciata","Three-banded Emerald Jewel",false));
        SpeciesList.add(new SpeciesCheck("Rhyothemis variegata","Common Picturewing",false));
        SpeciesList.add(new SpeciesCheck("Onychargia atrocyana","Black Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Anax immaculifrons","Blue Darner",false));
        SpeciesList.add(new SpeciesCheck("Rhyothemis plutonia","Greater Blue-Wing",false));
        SpeciesList.add(new SpeciesCheck("Protosticta davenporti","Anamalai Reedtail",false));
        SpeciesList.add(new SpeciesCheck("Onychothemis testacea","Riverhawker",false));
        SpeciesList.add(new SpeciesCheck("Ictinogomphus rapax","Indian Common Clubtail",false));
        SpeciesList.add(new SpeciesCheck("Trithemis aurora","Crimson Marsh Glider",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum sabina","Green Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Diplacodes trivialis","Blue Ground Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Amphithemis vacillans","",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion azureum","Azure Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Pantala flavescens","Wandering Glider",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion microcephalum","Blue Grass Dart",false));
        SpeciesList.add(new SpeciesCheck("Potamarcha congener","Yellow-tailed Ashy Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Neurothemis tullia","Pied Paddy Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Brachythemis contaminata","Ditch Jewel",false));
        SpeciesList.add(new SpeciesCheck("Bradinopyga geminata","Granite Ghost",false));
        SpeciesList.add(new SpeciesCheck("Orthetrum chrysis","Brown-Backed Marsh Hawk",false));
        SpeciesList.add(new SpeciesCheck("Crocothemis servilia","Scarlet Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis femina","Pruinosed Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Paracercion calamorum","Dusky Lilly-squatter",false));
        SpeciesList.add(new SpeciesCheck("Trithemis festiva","Black Stream Glider",false));
        SpeciesList.add(new SpeciesCheck("Neurothemis fulvia","Fulvous Forest Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Tramea basilaris","Red Marsh Trotter",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion spencei","",false));
        SpeciesList.add(new SpeciesCheck("Anax indicus","Lesser Green Emperor",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion malabaricum","Malabar Sprite",false));
        SpeciesList.add(new SpeciesCheck("Anisopleura lestoides","",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion indicum","Yellow-striped Dart",false));
        SpeciesList.add(new SpeciesCheck("Philoganga montana","",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis clauseni","",false));
        SpeciesList.add(new SpeciesCheck("Calocypha laidlawi","Myristica Sapphire",false));
        SpeciesList.add(new SpeciesCheck("Tetrathemis platyptera","Pigmy Skimmer",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis pieris","Indian White Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion hypermelas","",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion decorum","Three-lined Dart",false));
        SpeciesList.add(new SpeciesCheck("ndosticta deccanensis","Saffron Reedtail",false));
        SpeciesList.add(new SpeciesCheck("Archibasis oscillans","Long-banded Bluetail",false));
        SpeciesList.add(new SpeciesCheck("Indocnemis orang","",false));
        SpeciesList.add(new SpeciesCheck("Drepanosticta carmichaeli","Indo-Chinese Blue Reedtail",false));
        SpeciesList.add(new SpeciesCheck("Indolestes gracilis","",false));
        SpeciesList.add(new SpeciesCheck("Bayadera indica","Black Torrent Dart",false));
        SpeciesList.add(new SpeciesCheck("Euphaea dispar","Nilgiri Torrent Dart",false));
        SpeciesList.add(new SpeciesCheck("Ischnura senegalensis","Senegal Golden Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Ischnura rufostigma","",false));
        SpeciesList.add(new SpeciesCheck("Ischnura nursei","Pixie Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Ischnura mildredae","",false));
        SpeciesList.add(new SpeciesCheck("Ischnura rubilio","Western Golden Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Caconeura ramburi","Indian Blue Bambootail",false));
        SpeciesList.add(new SpeciesCheck("Melanoneura bilineata","",false));
        SpeciesList.add(new SpeciesCheck("Euphaea fraseri","Malabar Torrent Dart",false));
        SpeciesList.add(new SpeciesCheck("Euphaea cardinalis","Travancore Torrent Dart",false));
        SpeciesList.add(new SpeciesCheck("Euphaea ochracea","",false));
        SpeciesList.add(new SpeciesCheck("Dysphaea walli","Sapphire Torrent Dart",false));
        SpeciesList.add(new SpeciesCheck("Mortonagrion aborense","",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis splendidissima","Splendid Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Copera vittata","Blue Bush Dart",false));
        SpeciesList.add(new SpeciesCheck("Copera marginipes","Yellow Bush Dart",false));
        SpeciesList.add(new SpeciesCheck("Pseudocopera ciliata","Pied Bush Dart",false));
        SpeciesList.add(new SpeciesCheck("Disparoneura apicalis","Black-tipped Bambootail",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia nipalica","",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia mortoni","",false));
        SpeciesList.add(new SpeciesCheck("Prodasineura verticalis","Red-striped Black Bambootail",false));
        SpeciesList.add(new SpeciesCheck("Disparoneura quadrimaculata","Black-winged Bambootail",false));
        SpeciesList.add(new SpeciesCheck("Elattoneura coomansi","",false));
        SpeciesList.add(new SpeciesCheck("Elattoneura campioni","",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion rubiae","Orange Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion olivaceum","Rusty Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion fallax","Black-tailed Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion coromandelianum","Coromandel Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Ceriagrion cerinorubellum","Orange-tailed Marsh Dart",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis keralensis","Kerala Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis pygmaea","Pygmy Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Agriocnemis lacteola","Milky Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Aciagrion pallidum","Pale Slender Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Aciagrion occidentale","Green-striped Slender Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Aciagrion approximans","Indian Violet Dartlet",false));
        SpeciesList.add(new SpeciesCheck("Elattoneura atkinsoni","",false));
        SpeciesList.add(new SpeciesCheck("Coeliccia schmidti","",false));
        SpeciesList.add(new SpeciesCheck("Coeliccia bimaculata","",false));
        SpeciesList.add(new SpeciesCheck("Pseudagrion rubriceps","Saffron-faced Blue Dart",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia eximia","",false));
        SpeciesList.add(new SpeciesCheck("Lestes elatus","Emerald Spreadwing",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia miniata","",false));
        SpeciesList.add(new SpeciesCheck("Libellago andamanensis","Andaman Heliodor",false));
        SpeciesList.add(new SpeciesCheck("Chlorogomphus campioni","Nilgiri Mountain Hawk",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia erythromelas","",false));
        SpeciesList.add(new SpeciesCheck("Calicnemia imitans","",false));
        SpeciesList.add(new SpeciesCheck("Echo margarita","",false));
        SpeciesList.add(new SpeciesCheck("Paracypha unimaculata","Emerald Prince",false));
        SpeciesList.add(new SpeciesCheck("Vestalis apicalis","Black-tipped Forest Glory",false));
        SpeciesList.add(new SpeciesCheck("Libellago indica","Southern Heliodor",false));
        SpeciesList.add(new SpeciesCheck("Heliocypha biforata","",false));
        SpeciesList.add(new SpeciesCheck("Libellago lineata","River Heliodor",false));
        SpeciesList.add(new SpeciesCheck("Aristocypha quadrimaculata","",false));
        SpeciesList.add(new SpeciesCheck("Heliocypha bisignata","Stream Ruby",false));
        SpeciesList.add(new SpeciesCheck("Vestalis gracilis","Clear-winged Forest Glory",false));
        SpeciesList.add(new SpeciesCheck("Neurobasis chinensis","Stream Glory",false));
        SpeciesList.add(new SpeciesCheck("Lestes viridulus","Emerald-striped Spreadwing",false));
        SpeciesList.add(new SpeciesCheck("Caliphaea confusa","",false));
        SpeciesList.add(new SpeciesCheck("Matrona nigripectus","",false));
        SpeciesList.add(new SpeciesCheck("Lestes umbrinus","Brown Spreadwing",false));
        SpeciesList.add(new SpeciesCheck("Lestes nodalis","",false));
        SpeciesList.add(new SpeciesCheck("Lestes praemorsus","Sapphire-eyed Spreadwing",false));
        SpeciesList.add(new SpeciesCheck("Lestes dorothea","",false));
        SpeciesList.add(new SpeciesCheck("Indolestes cyaneus","",false));

    }


    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.species_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new SpeciesCheckAdapter(this,this,SpeciesList);

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
    public void onItemClicked(View v, int position) {
       /* SpeciesCheck currentItem = SpeciesList.get(position);
        SharedPreferences sp = getSharedPreferences("count", MODE_PRIVATE);
        SpeciesDialogFragment df= new SpeciesDialogFragment();


        String getdetails = sp.getString("tag","");
        int count = sp.getInt("tag1",0);


        Bundle args = new Bundle();
        args.putCharSequence("data1", currentItem.getspecies_name());
        args.putCharSequence("data2", currentItem.getcommon_name());
        args.putString("data3",getdetails);
        args.putInt("data4",count);
        df.setArguments(args);

        df.show(getSupportFragmentManager(), "Dialog");*/


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
