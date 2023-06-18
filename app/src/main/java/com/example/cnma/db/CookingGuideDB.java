package com.example.cnma.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.cnma.BuildConfig;
import com.example.cnma.Model.Category;
import com.example.cnma.Model.Dish;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;

public class CookingGuideDB {
    private static final int DATABASE_VERSION = 1;
    String dbName = "CookingGuideDB.db";
    Context context;
    SQLiteDatabase db;

    public CookingGuideDB(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDB() {
        return context.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
    }


    public void copyDatabase() {
        File dbFile = context.getDatabasePath(dbName);
//        if (!dbFile.exists()) {
        try {
            InputStream is = context.getAssets().open(dbName);
            OutputStream os = new FileOutputStream(dbFile);
            byte[] buffer = new byte[1024];
            while (is.read(buffer) > 0) {
                os.write(buffer);
            }
            Log.d("1111", "111");
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Error creating source database", e);
        }
//        }
    }


    public ArrayList<Dish> getFavorites() {
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        ArrayList<String> tutorial = new ArrayList<>();
        db = openDB();
        String sql = "SELECT * FROM FAVORITES";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(1);
            Cursor cursor1 = db.rawQuery("SELECT * FROM DISHES WHERE ID=? LIMIT 1", new String[]{id});
            cursor1.moveToFirst();
            Integer dishID = cursor1.getInt(0);
            String name = cursor1.getString(1);
            String video = cursor1.getString(2);
            byte[] image = cursor1.getBlob(3);
            Integer category = cursor1.getInt(4);
            String desc = cursor1.getString(5);
            String material = cursor1.getString(6);
            Cursor cursor2 = db.rawQuery("SELECT * FROM TUTORIALS WHERE DISH=?", new String[]{id});
            while (cursor2.moveToNext()) {
                tutorial.add(cursor2.getString(2));
            }
            Dish dish = new Dish(dishID, image, name, video, category, desc, tutorial, material);
            dishes.add(dish);
        }
        db.close();
        return dishes;
    }

    public void addFavorite(Dish dish) {
        db = openDB();
        db.execSQL("INSERT INTO FAVORITES(DISH) VALUES(?)", new String[]{dish.getId().toString()});
        db.close();
    }


    public boolean isEmptyCategory(Integer categoryID) {
        db = openDB();
        Cursor cursor = db.rawQuery("SELECT * FROM DISHES WHERE CATEGORY=? LIMIT 1", new String[]{categoryID.toString()});
        if (cursor.getCount() > 0) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public ArrayList<Category> getCategories() {
        db = openDB();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORIES", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            categories.add(new Category(id, name));
            cursor.moveToNext();
        }
        db.close();
        return categories;
    }

    public Dish getDishByID(Integer id) {
        db = openDB();
        ArrayList<String> tutorial = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM DISHES WHERE ID=" + id, null);
        cursor.moveToFirst();
        Integer dishID = cursor.getInt(0);
        String name = cursor.getString(1);
        String video = cursor.getString(2);
        byte[] image = cursor.getBlob(3);
        Integer category = cursor.getInt(4);
        String desc = cursor.getString(5);
        String material = cursor.getString(6);
        Cursor cursor1 = db.rawQuery("SELECT * FROM TUTORIALS WHERE DISH=? ORDER BY ID ASC", new String[]{id.toString()});
        cursor1.moveToFirst();
        while (cursor1.moveToNext()) {
            tutorial.add(cursor1.getString(2));
        }
        Dish dish = new Dish(id, image, name, video, category, desc, tutorial, material);
        return dish;
    }

    public ArrayList<Dish> getDishesByCategory(Integer categoryID, @Nullable Integer limit) {
        db = openDB();
        String sql;
        if (limit == null) {
            sql = "SELECT * FROM DISHES WHERE CATEGORY =" + categoryID.toString();
        } else {
            sql = "SELECT * FROM DISHES WHERE CATEGORY= " + categoryID.toString() + " limit " + limit;
        }
        ArrayList<Dish> dishes = new ArrayList<Dish>();
        Cursor cursor = db.rawQuery(sql, null);
        dishes = pushQueryDataToList(cursor);
        db.close();
        return dishes;
    }

    public ArrayList<String> getTutorials(Dish dish) {
        db = openDB();
        ArrayList<String> tutorials = new ArrayList<>();
        Cursor cursor1 = db.rawQuery("SELECT * FROM TUTORIALS WHERE DISH=?", new String[]{dish.getId().toString()});
        cursor1.moveToFirst();
        while (cursor1.moveToNext()) {
            tutorials.add(cursor1.getString(2));
        }
        db.close();
        return tutorials;
    }

    public ArrayList<Dish> getDishesByString(String string) {
        ArrayList<Dish> dishes = new ArrayList<>();
        db = openDB();
        String convertedString =
                Normalizer
                        .normalize(string.trim(), Normalizer.Form.NFD)
                        .replaceAll("[eEyYuUiIoOaAdD]", "%");
        Cursor cursor = db.rawQuery(
                "SELECT * FROM DISHES WHERE NAME LIKE ? ORDER BY NAME ASC",
                new String[]{convertedString});

        dishes = pushQueryDataToList(cursor);
        db.close();
        return dishes;
    }

    public void deleteFavorite(Dish dish) {
        db = openDB();
        db.delete("FAVORITES", "DISH=?", new String[]{dish.getId().toString()});
        db.close();
    }

    public void deleteAllFavorites() {
        db = openDB();
        db.delete("FAVORITES", null, null);
        db.close();
    }

    public boolean isInFavorite(Dish dish) {
        db = openDB();
        Cursor cursor = db.rawQuery("SELECT * FROM FAVORITES WHERE DISH=? LIMIT 1", new String[]{dish.getId().toString()});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public boolean checkExists() {
        db = openDB();

        if (getFavorites() == null) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    ArrayList<Dish> pushQueryDataToList(Cursor cursor) {
        ArrayList<Dish> dishes = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Integer dishID = cursor.getInt(0);
            String name = cursor.getString(1);
            String video = cursor.getString(2);
            byte[] image = cursor.getBlob(3);
            Integer category = cursor.getInt(4);
            String desc = cursor.getString(5);
            String material = cursor.getString(6);
            Dish dish = new Dish(dishID, image, name, video, category, desc, null, material);
            dishes.add(dish);
            cursor.moveToNext();
        }
        return dishes;
    }


}
