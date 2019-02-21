package examples.aaronhoskins.com.week2day4demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class DataEntryActivity extends AppCompatActivity {
    //Constants
    public static final String KEY_CAR_RESULT = "car_result";
    public static final int RESULT_CODE = 656;

    //Declare views
    EditText etCarMakeDisplay;
    EditText etCarModelDisplay;
    EditText etCarYearDisplay;
    EditText etCarColorDisplay;
    EditText etCarTitleStatusDisplay;
    //The Intent that started this activity
    Intent sentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);
        sentIntent = getIntent(); //gets intent that started the activity
        bindViews();
    }

    //
    //  Bind Views to xml elements
    //  @return void
    public void bindViews() {
        etCarColorDisplay = findViewById(R.id.etCarColor);
        etCarMakeDisplay = findViewById(R.id.etCarMake);
        etCarModelDisplay = findViewById(R.id.etCarModel);
        etCarYearDisplay = findViewById(R.id.etCarYear);
        etCarTitleStatusDisplay = findViewById(R.id.etCarTitleStatus);
    }

    //
    // Create Car Object
    // @return Car The new car object
    //
    public Car generateCarObjectFromInput() {
        Car returnCar = new Car();  //the car we will return from method

        //Set-up Car object
        returnCar.setCarMake(
                etCarMakeDisplay.getText() != null ? etCarMakeDisplay.getText().toString() : "");
        returnCar.setCarModel(
                etCarModelDisplay.getText() != null ? etCarModelDisplay.getText().toString() : "");
        returnCar.setCarYear(
                etCarYearDisplay.getText() != null ? etCarYearDisplay.getText().toString() : "");
        returnCar.setCarColor(
                etCarColorDisplay.getText() != null ? etCarColorDisplay.getText().toString() : "");
        returnCar.setCarTitleStatus(
                etCarTitleStatusDisplay.getText() != null ? etCarTitleStatusDisplay.getText().toString() : "");

        return returnCar;
    }

    public void onClick(View view) {
        Car carResult = generateCarObjectFromInput();
        Bundle bundleOfTheCarResult = new Bundle();
        bundleOfTheCarResult.putParcelable(KEY_CAR_RESULT, carResult); //put car object in bundle
        sentIntent.putExtras(bundleOfTheCarResult); //attach the result bundle to intent
        setResult(RESULT_CODE, sentIntent); //send back bundle with result to activity which called it for result
        finish(); //Make sure the activity is flagged to be destroyed
    }
}
