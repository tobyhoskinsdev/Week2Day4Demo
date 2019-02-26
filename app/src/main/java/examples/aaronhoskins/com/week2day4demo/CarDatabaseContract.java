package examples.aaronhoskins.com.week2day4demo;

import android.util.Log;

import java.util.Locale;

public class CarDatabaseContract {
    //Database name and default version
    public static final String DATABASE_NAME = "car_db";
    public static final int DATABASE_VERSION = 1;
    //Database table name
    public static final String TABLE_NAME = "Cars";
    //Columns in database names
    public static final String COLUMN_MAKE = "make";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ID = "id";

    //
    // Create the create table query for the database
    //
    public static String createQuery() {
        StringBuilder queryBuilder = new StringBuilder();
        //Query to create Table
        queryBuilder.append("CREATE TABLE ");
        queryBuilder.append(TABLE_NAME);
        queryBuilder.append(" ( ");
        //Must have unique primary key
        queryBuilder.append(COLUMN_ID);
        queryBuilder.append(" ");
        queryBuilder.append(" INTEGER PRIMARY KEY AUTOINCREMENT, ");
        //Add rest of the columns
        queryBuilder.append(COLUMN_MAKE);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COLUMN_MODEL);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COLUMN_YEAR);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COLUMN_COLOR);
        queryBuilder.append(" TEXT, ");
        queryBuilder.append(COLUMN_TITLE);
        queryBuilder.append(" TEXT )");

        //Log the query so we can check for correctness
        Log.d("TAG", "createQuery: " + queryBuilder.toString());

        return queryBuilder.toString();
    }

    public static String getAllCarsQuery() {
        return "SELECT * FROM " + TABLE_NAME;
    }

    public static String getOneCarById(int id) {
        return String.format("SELECT * FROM %s WHERE %s = \"%d\"", TABLE_NAME, COLUMN_ID, id);
    }

    public static String getWhereClauseById() {
        return String.format(Locale.US, "%s = ", COLUMN_ID);
    }

}
