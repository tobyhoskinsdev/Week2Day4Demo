package examples.aaronhoskins.com.week2day4demo;

import android.os.Parcel;
import android.os.Parcelable;

public class Car implements Parcelable {
    private String carMake;
    private String carModel;
    private String carYear;
    private String carColor;
    private String carTitleStatus;
    private int carId;

    public Car(){}

    public Car(String carMake, String carModel, String carYear, String carColor, String carTitleStatus, int carId) {
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.carColor = carColor;
        this.carTitleStatus = carTitleStatus;
        this.carId = carId;
    }

    protected Car(Parcel in) {
        carMake = in.readString();
        carModel = in.readString();
        carYear = in.readString();
        carColor = in.readString();
        carTitleStatus = in.readString();
        carId = in.readInt();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public void setCarYear(String carYear) {
        this.carYear = carYear;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarTitleStatus() {
        return carTitleStatus;
    }

    public void setCarTitleStatus(String carTitleStatus) {
        this.carTitleStatus = carTitleStatus;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carMake='" + carMake + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carYear='" + carYear + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carTitleStatus='" + carTitleStatus + '\'' +
                ", carId=" + carId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(carMake);
        dest.writeString(carModel);
        dest.writeString(carYear);
        dest.writeString(carColor);
        dest.writeString(carTitleStatus);
        dest.writeInt(carId);
    }
}
