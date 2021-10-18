package com.example.dragonfly_main;

import java.io.Serializable;

public class SpeciesCheck implements Serializable {


    private String species_name;
    private boolean isSelected;
    private String common_name;


    public SpeciesCheck(String species_name,String common_name,boolean isSelected) {
        this.species_name = species_name;
        this.isSelected = isSelected;
        this.common_name = common_name;
    }


    public String getspecies_name() {
        return species_name;
    }

    public void setspecies_name(String species_name) {
        this.species_name = species_name;
    }

    public String getcommon_name() {
        return common_name;
    }

    public void setcommon_name(String common_name) {
        this.common_name = common_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    private boolean isChecked;

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }



}
