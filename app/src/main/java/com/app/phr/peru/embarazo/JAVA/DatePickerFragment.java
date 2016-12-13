package com.app.phr.peru.embarazo.JAVA;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by JSYoo on 2016-10-07.
 */
public class DatePickerFragment extends DialogFragment {



    public int tYear;           //오늘 연월일 변수
    public int tMonth;
    public int tDay;
    Calendar calendar = Calendar.getInstance();

    public int dYear=calendar.get(Calendar.YEAR);        //디데이 연월일 변수
    public int dMonth=calendar.get(Calendar.MONTH);
    public int dDay=calendar.get(Calendar.DAY_OF_MONTH);

    public String YMH;
    Communicator comm;

    public long d;
    public long t;
    public long r;
    public long c;
    public long cc;
    public int resultNumber=0;


    static final int DATE_DIALOG_ID=0;



    public DatePickerFragment(){


    }
    public static String MYH(int dYear, int dMonth, int dDay)
    {
        String a = String.valueOf(dDay)+"/"+String.valueOf(dMonth+1)+"/"+String.valueOf(dYear);
        return a;
    }

    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState){

        comm = (Communicator) getActivity();
        Calendar calendar = Calendar.getInstance();              //현재 날짜 불러옴
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar dCalendar = Calendar.getInstance();
        dCalendar.set(dYear,dMonth, dDay);

        YMH = MYH(dYear,dMonth, dDay);

        t=calendar.getTimeInMillis();                 //오늘 날짜를 밀리타임으로 바꿈
        d=dCalendar.getTimeInMillis();              //디데이날짜를 밀리타임으로 바꿈
      //r=(d-t)/(24*60*60*1000);                 //디데이 날짜에서 오늘 날짜를 뺀 값을 '일'단위로 바꿈
        r=(d)/(24*60*60*1000);
            resultNumber = (int) r + 1;
     //       comm.respond(String.valueOf(resultNumber));
           // comm.respond(YMH);


        Bundle arguments = new Bundle();
        arguments.putString("name", YMH);
       // arguments.putString("name", String.valueOf(resultNumber));
        MainTab.setArguments(arguments);

        return new DatePickerDialog(getActivity(),dDateSetListener,tYear,tMonth,tDay);
}



    private DatePickerDialog.OnDateSetListener dDateSetListener=new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,

                              int dayOfMonth) {
            comm = (Communicator) getActivity();
            // TODO Auto-generated method stub
            dYear = year;
            dMonth = monthOfYear;
            dDay = dayOfMonth;
            final Calendar dCalendar = Calendar.getInstance();
            dCalendar.set(dYear, dMonth, dDay);

            d = dCalendar.getTimeInMillis();
            //r = (d - t) / ( 24 * 60 * 60 * 1000);
            r = (d) / ( 24 * 60 * 60 * 1000);
                resultNumber = (int) r;
                //comm.respond(String.valueOf(resultNumber));
            YMH = MYH(dYear,dMonth, dDay);
            comm.respond(YMH);
            Bundle arguments = new Bundle();
       //     arguments.putString("name", String.valueOf(resultNumber));
            arguments.putString("name", YMH);
            MainTab.setArguments(arguments);

        }

};

    public void onCancel(DialogInterface dialog){
        //comm.respond("null");

    //    Calendar cCalendar = Calendar.getInstance();
   //     c = cCalendar.getTimeInMillis();
   //     cc = c/( 24 * 60 * 60 * 1000);
   //     Bundle arguments = new Bundle();
//        arguments.putString("name", String.valueOf(cc));
//        MainTab.setArguments(arguments);

        dialog.cancel();
        Toast.makeText(getActivity(),"Selección de fecha se ha cancelado.", Toast.LENGTH_SHORT).show();
    }

}


