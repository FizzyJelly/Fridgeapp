package pl.edu.agh.fridgeapp.utility;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import pl.edu.agh.fridgeapp.R;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    private EditText dateEdit;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar= Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String dayString=((Integer) dayOfMonth).toString();
        if(dayOfMonth<10){
            dayString="0"+dayString;
        }
        String monthString=((Integer) (month+1)).toString();
        if(month<10){
            monthString="0"+monthString;
        }
        dateEdit.setText(dayString+"/"+ monthString +"/"+ year);
    }

    public void setDateEdit(EditText dateEdit) {
        this.dateEdit = dateEdit;
    }
}