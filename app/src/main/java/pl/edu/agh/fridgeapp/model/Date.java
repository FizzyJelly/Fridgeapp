package pl.edu.agh.fridgeapp.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Date implements Comparable<Date>, Serializable {

    private final Integer day;
    private final Months month;
    private final Integer year;

    public enum Months {
        JANUARY("January", 1), FEBRUARY("February", 2),
        MARCH("March", 3), APRIL("April", 4),
        MAY("May", 5), JUNE("June", 6),
        JULY("July", 7), AUGUST("August", 8),
        SEPTEMBER("September", 9), OCTOBER("October", 10),
        NOVEMBER("November", 11), DECEMBER("December", 12);

        private final String name;
        private final Integer number;

        Months(String name, Integer number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return name;
        }

        public Integer getNumber() {
            return number;
        }

        public Months previousMonth() {
            if (this.getNumber().equals(1)) {
                return Months.DECEMBER;
            } else {
                return Months.values()[this.getNumber() - 2];
            }
        }

        public Months nextMonth() {
            if (this.getNumber().equals(12)) {
                return Months.JANUARY;
            } else {
                return Months.values()[this.getNumber()];
            }
        }
    }


    public Date(int day, int month, int year) {
        this.day = day;
        this.month = Months.values()[month];
        this.year = year;
    }

    @Override
    public String toString() {
        String day = "" + this.day;
        String month = "" + this.month;
        if (this.day < 10) {
            day = "0" + this.day;
        }
        if (this.month.getNumber() < 10) {
            month = "0" + this.month.getNumber();
        }
        return day + "." + month + "." + year;

    }

    @Override
    public int compareTo(@NonNull Date d) {
        if (this.year.compareTo(d.year) != 0) {
            return this.year.compareTo(d.year);
        } else {
            if (this.month.compareTo(d.month) != 0) {
                return this.month.compareTo(d.month);
            } else {
                return this.day.compareTo(d.day);
            }
        }
    }

    public Integer getDay() {
        return day;
    }

    public Months getMonth() {
        return month;
    }

    public Integer getYear() {
        return year;
    }
}
