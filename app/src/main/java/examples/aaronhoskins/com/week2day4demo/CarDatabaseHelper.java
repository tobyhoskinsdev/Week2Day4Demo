package examples.aaronhoskins.com.week2day4demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.COLUMN_COLOR;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.COLUMN_ID;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.COLUMN_MAKE;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.COLUMN_MODEL;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.COLUMN_TITLE;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.COLUMN_YEAR;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.DATABASE_NAME;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.DATABASE_VERSION;
import static examples.aaronhoskins.com.week2day4demo.CarDatabaseContract.TABLE_NAME;


public class CarDatabaseHelper extends SQLiteOpenHelper {

    //Constructor for
    public CarDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //Create the tables(will run only once per install)
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CarDatabaseContract.createQuery());
    }
    //If version database changes, make adjustments here
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onCreate(database);
    }

    //Insert a car into the database
    public long insertCarIntoDatabase(@NonNull Car car) {
        SQLiteDatabase writeableDatabase = this.getWritableDatabase();
        //Data container used for database key value pairs
        ContentValues contentValues = new ContentValues();

        //inset key value pairs into the contentValues container
        contentValues.put(COLUMN_MAKE, car.getCarMake());
        contentValues.put(COLUMN_MODEL, car.getCarModel());
        contentValues.put(COLUMN_YEAR, car.getCarYear());
        contentValues.put(COLUMN_COLOR, car.getCarColor());
        contentValues.put(COLUMN_TITLE, car.getCarTitleStatus());

        //insert the car into the table using contentValues
        return writeableDatabase.insert(TABLE_NAME, null, contentValues);
    }

    //Get All Cars from Database and return an ArrayList
    public ArrayList<Car> getAllCarsFromDatabase() {
        ArrayList<Car> returnCarList = new ArrayList<>();
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        //Get results from query and hold in cursor(iterable object for database operations
        Cursor cursor = readableDatabase.rawQuery(CarDatabaseContract.getAllCarsQuery(), null);
        //Check to see if we have any results
        if(cursor.moveToFirst()) {
            //while we have results, get the values and place in list
            do {
                //get values
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String make = cursor.getString(cursor.getColumnIndex(COLUMN_MAKE));
                String model = cursor.getString(cursor.getColumnIndex(COLUMN_MODEL));
                String year = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR));
                String color = cursor.getString(cursor.getColumnIndex(COLUMN_COLOR));
                String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));

                //add to list
                returnCarList.add(new Car(make, model, year, color, title, id));
            } while (cursor.moveToNext());
            //return the result in a list
        }
            cursor.close();
            return returnCarList;
    }

    //Get One entry from database
    public Car getCarById(int id) {
        SQLiteDatabase readableDatabase = this.getReadableDatabase();
        //Car to return
        Car returnCar = new Car();
        //cursor to hold results
        Cursor cursor = readableDatabase.rawQuery(CarDatabaseContract.getOneCarById(id), null);
        if(cursor.moveToFirst()){
            int idFromDB = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String make = cursor.getString(cursor.getColumnIndex(COLUMN_MAKE));
            String model = cursor.getString(cursor.getColumnIndex(COLUMN_MODEL));
            String year = cursor.getString(cursor.getColumnIndex(COLUMN_YEAR));
            String color = cursor.getString(cursor.getColumnIndex(COLUMN_COLOR));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            //set return car
            returnCar = new Car(make, model, year, color, title, idFromDB);
        }
        cursor.close();
        return returnCar;
    }

}

