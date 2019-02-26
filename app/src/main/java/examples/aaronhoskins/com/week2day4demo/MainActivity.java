package examples.aaronhoskins.com.week2day4demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //Constants
    public static final int REQUEST_CODE_FOR_MAIN = 427;
    public static final String KEY_SHARED_PREF = "shared_pref";
    public static final String KEY_LAST_ENTERED_MAKE = "last_make";
    public static final String KEY_LAST_ENTERED_MODEL = "last_model";

    //Declare views
    TextView tvCarMakeDisplay;
    TextView tvCarModelDisplay;
    TextView tvCarYearDisplay;
    TextView tvCarColorDisplay;
    TextView tvCarTitleStatusDisplay;
    EditText etIdNumber;
    EditText etColor;
    //Shared Preferences Object
    SharedPreferences sharedPreferences;
    //Car Database Declaration
    CarDatabaseHelper carDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(KEY_SHARED_PREF, MODE_PRIVATE);
        //instantiate the car Database
        carDatabaseHelper = new CarDatabaseHelper(this);
        bindViews();

    }

    //
    //  Bind Views to xml elements
    //  @return void
    public void bindViews() {
        tvCarColorDisplay = findViewById(R.id.tvCarColor);
        tvCarMakeDisplay = findViewById(R.id.tvCarMake);
        tvCarModelDisplay = findViewById(R.id.tvCarModel);
        tvCarYearDisplay = findViewById(R.id.tvCarYear);
        tvCarTitleStatusDisplay = findViewById(R.id.tvCarTitleStatus);
        etIdNumber = findViewById(R.id.etIdNumber);
        etColor = findViewById(R.id.etColor);
    }

    //
    // Populate TextViews
    // @params Car car info to populate
    // @return void
    //
    public void populateTextViews(@NonNull Car carInfoToPopulate) {
        tvCarMakeDisplay.setText(carInfoToPopulate.getCarMake());
        tvCarModelDisplay.setText(carInfoToPopulate.getCarModel());
        tvCarYearDisplay.setText(carInfoToPopulate.getCarYear());
        tvCarColorDisplay.setText(carInfoToPopulate.getCarColor());
        tvCarTitleStatusDisplay.setText(carInfoToPopulate.getCarTitleStatus());
    }

    //
    //  Create a explicit intent to start data entry
    //      activity for result, and start the activity for reselt
    //  @return void
    //
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnStartForResult:
                Intent explicitIntent = new Intent(this, DataEntryActivity.class);
                startActivityForResult(explicitIntent, REQUEST_CODE_FOR_MAIN);
                break;
            case R.id.btnUpdateEntry:
                if(etIdNumber.getText() != null && etColor.getText() != null) {
                    //Get our database helper
                    CarDatabaseHelper carDatabaseHelper = new CarDatabaseHelper(this);
                    //Retrive the car which has the same id as the one which we want to edit
                    int carId = Integer.parseInt(etIdNumber.getText().toString());
                    Car carToedit = carDatabaseHelper.getCarById(carId);
                    //set the color to the new value for the car we want to edit
                    carToedit.setCarColor(etColor.getText().toString());
                    //update the database with the new value(s)
                    carDatabaseHelper.updateCarInDatabase(carToedit);
                }
                break;
            case R.id.btnDeleteEntry:
                if(etIdNumber.getText() != null) {
                    //Get Database Helper
                    CarDatabaseHelper carDBHelper = new CarDatabaseHelper(this);
                    //Get Id
                    String carIdToDelete = etIdNumber.getText().toString();
                    //Delete item with id from Database
                    String[] idsToDelete = new String[]{carIdToDelete};
                    Log.d("TAG", "onClick: DELETING " + carIdToDelete);
                    carDBHelper.deleteFromDatabaseById(idsToDelete);

                    ArrayList<Car> carList = carDBHelper.getAllCarsFromDatabase();
                    for(Car currentCar : carList) {
                        Log.d("TAG", currentCar.toString());
                    }
                }
                break;

        }
    }

    //
    // Save to Shared Pref.
    // @param Car Car object which we will save info to shared pref. from
    // @return void
    //
    public void saveMakeModelToSharedPref(@NonNull Car car) {
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putString(KEY_LAST_ENTERED_MAKE, car.getCarMake());
        sharedPrefEditor.putString(KEY_LAST_ENTERED_MODEL, car.getCarModel());
        sharedPrefEditor.commit();
    }

    //
    // Log shared pref values and save new car
    // @param Car New car to save
    //
    public void saveAndLogCarInSharedPref(@NonNull Car car) {
        //Get old values saved in pref
        String make = sharedPreferences.getString(KEY_LAST_ENTERED_MAKE, "NO VALUE ENTERED");
        String model = sharedPreferences.getString(KEY_LAST_ENTERED_MODEL, "NO VALUE ENTERED");

        //Log the old values
        Log.d(
                "TAG",
                "saveAndLogCarInSharedPref: IN SHARED PREF: make = " + make + " | model = " + model);

        //Save new values to shared pref
        saveMakeModelToSharedPref(car);

        //get new values
        make = sharedPreferences.getString(KEY_LAST_ENTERED_MAKE, "NO VALUE ENTERED");
        model = sharedPreferences.getString(KEY_LAST_ENTERED_MODEL, "NO VALUE ENTERED");

        //log the new values
        Log.d(
                "TAG",
                "saveAndLogCarInSharedPref: IN SHARED PREF: make = " + make + " | model = " + model);
    }

    //
    // Save Car to database and print list of all cars currently in DB to log
    //
    public void saveCarToDBandSeeLog(@NonNull Car car){
        //Save car to database
        carDatabaseHelper.insertCarIntoDatabase(car);
        //get all current cars in database and log them
        ArrayList<Car> carList = carDatabaseHelper.getAllCarsFromDatabase();
        for(Car currentCar : carList) {
            Log.d("TAG", currentCar.toString());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // null check the intent sent back with result
        if(data != null) {
            //Get bundle from Intent
            Bundle resultBundle = data.getExtras();
            if(resultBundle != null){
                //Get Car from bundle
                Car resultCar = resultBundle.getParcelable(DataEntryActivity.KEY_CAR_RESULT);
                if(resultCar != null) {
                    saveAndLogCarInSharedPref(resultCar);//save and log make model in shared pref
                    saveCarToDBandSeeLog(resultCar);//save car to db and print list of all cars in db
                    populateTextViews(resultCar); //populate the views
                }

            }
        }
    }
}
