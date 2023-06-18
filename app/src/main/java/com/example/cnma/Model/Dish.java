package com.example.cnma.Model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;

public class Dish implements Serializable, Comparable<Dish> {
    Integer id;
    String name;
    String description;
    String video;
    byte[] image;

    Integer category;
    String material;
    ArrayList<String> tutorial;


    public Dish(Integer id, byte[] image, String name, String video, Integer category, String description, @Nullable ArrayList<String> tutorial, String material) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.video = video;
        this.category = category;
        this.description = description;
        this.tutorial = tutorial;
        this.material = material;
    }

    public void setTutorial(ArrayList<String> tutorial) {
        this.tutorial = tutorial;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCategory() {
        return category;
    }

    public byte[] getImage() {
        return image;
    }

    public String getMaterial() {
        return material;
    }

    public ArrayList<String> getTutorial() {
        return tutorial;
    }

    public String getVideo() {
        return video;
    }

    public String getDesc() {
        return description;
    }

    @Override
    public int compareTo(Dish dish) {
        return name.compareTo(dish.getName());
    }

    public static Comparator<Dish> StringAscComparator = new Comparator<Dish>() {

        @Override
        public int compare(Dish lhs, Dish rhs) {
            return lhs.name.compareToIgnoreCase(rhs.name);
        }


    };

    //Comparator for Descending Order
    public static Comparator<Dish> StringDescComparator = new Comparator<Dish>() {

        public int compare(Dish app1, Dish app2) {


            return app2.name.compareToIgnoreCase(app1.name);
        }
    };
}
