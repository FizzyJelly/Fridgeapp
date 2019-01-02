package pl.edu.agh.fridgeapp.data_classes;

import android.support.annotation.NonNull;

import java.util.Date;

public class ExpiryDate implements Comparable{

    private final int day;
    private final int month;
    private final int year;


    public ExpiryDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        String day=""+this.day;
        String month=""+this.month;
        if (this.day < 10) {
            day = "0" + this.day;
        }
        if(this.month<10){
            month="0"+this.month;
        }
        return day + "." + month + "." + year;

    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof ExpiryDate){
            return dayDifference((ExpiryDate) o);
        }
        return 0;
    }

    public int dayDifference(ExpiryDate date){
        Date date1=new Date(date.year,date.month,date.day);
        Date date2=new Date(this.year,this.month,this.day);
        return date2.compareTo(date1);
    }
}
