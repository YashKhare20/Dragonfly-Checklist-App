package com.example.dragonfly_main;

public class Species {

    private String img;
    private String species_name;
    private String common_name;

    public Species(String species_name, String common_name, String img) {
        this.img = img;
        this.common_name = common_name;
        this.species_name = species_name;
    }

    public String getcommon_name() {
        return common_name;
    }

    public void setcommon_name(String common_name) {
        this.common_name = common_name;
    }

    public String getspecies_name() {
        return species_name;
    }

    public void setspecies_name(String species_name) {
        this.species_name = species_name;
    }

    public String getimg() {
        return img;
    }

    public void setimg(String img) {
        this.img = img;
    }
}
