package com.example.myproyectomascotas;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myproyectomascotas.Database.DatabaseClass;
import com.example.myproyectomascotas.Database.EntityClass;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener {
    Button btn_time, btn_date, btn_done;
    EditText editext_message;
    Spinner spn_Rciclo;
    String timeTonotify;
    DatabaseClass databaseClass;
    private String value;
    private String date;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        btn_done = findViewById(R.id.btn_done);
        spn_Rciclo = findViewById(R.id.spn_Rciclo);
        editext_message = findViewById(R.id.editext_message);
        btn_time.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        btn_done.setOnClickListener(this);
        databaseClass = DatabaseClass.getDatabase(getApplicationContext());
    }

    @Override
    public void onClick(View view) {
        if (view == btn_time) {
            String selec = spn_Rciclo.getSelectedItem().toString();
            String value = (editext_message.getText().toString().trim());
            String date = (btn_date.getText().toString().trim());
            String time = (btn_time.getText().toString().trim());
            switch (selec) {
                case "Diario": selectTimeD(value,date,time);
                    break;
                case "Semanal": selectTimeS(value,date,time);
                    break;
                case "Mensual": selectTimeM(value,date,time);
                    break;
                case "Dia Especifico": selectTime();
                    break;
            }
        } else if (view == btn_date) {
            selectDate();
        } else {
            submit();
        }
    }

    private void submit() {
        String text = editext_message.getText().toString().trim();
        if (text.isEmpty()) {
            Toast.makeText(this, "Porfavor ingrese mensaje", Toast.LENGTH_SHORT).show();
        } else {
            if (btn_time.getText().toString().equals("Seleccione Hora") || btn_date.getText().toString().equals("Seleccione Fecha")) {
                Toast.makeText(this, "Porfavor seleccione fecha y hora", Toast.LENGTH_SHORT).show();
            } else {

                String id3 = getIntent().getStringExtra("id3");
                EntityClass entityClass = new EntityClass();
                value = (editext_message.getText().toString().trim());
                date = (btn_date.getText().toString().trim());
                time = (btn_time.getText().toString().trim());
                entityClass.setEventidmascota(id3);
                entityClass.setEventdate(date);
                entityClass.setEventname(value);
                entityClass.setEventtime(time);
                databaseClass.EventDao().insertAll(entityClass);

                setAlarm(value, date, time);
            }
        }
    }

    private void selectTimeD(String text, String date, String time) {

        AlarmManager alarmMgrD = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(getApplicationContext(), AlarmBrodcast.class);


        Calendar calendarD = Calendar.getInstance();
        calendarD.get(Calendar.HOUR_OF_DAY);
        calendarD.get(Calendar.MINUTE);
        calendarD.setTimeInMillis(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.setTimeInMillis(System.currentTimeMillis());


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
            }
        }, hour, minute, false);

        timePickerDialog.show();

        intent2.putExtra("event", text);
        intent2.putExtra("time", date);
        intent2.putExtra("date", time);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent2,  PendingIntent.FLAG_UPDATE_CURRENT);

        alarmMgrD.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 1040, alarmIntent);
        timePickerDialog.show();

    }
    private void selectTimeS(String text, String date, String time) {
        AlarmManager alarmMgrD = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(getApplicationContext(), AlarmBrodcast.class);


        Calendar calendarD = Calendar.getInstance();
        calendarD.get(Calendar.HOUR_OF_DAY);
        calendarD.get(Calendar.MINUTE);
        calendarD.setTimeInMillis(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        intent2.putExtra("event", text);
        intent2.putExtra("time", date);
        intent2.putExtra("date", time);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, 0);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
            }
        }, hour, minute, false);



        alarmMgrD.setRepeating(AlarmManager.RTC_WAKEUP, calendarD.getTimeInMillis(), 1000 * 60 * 2, alarmIntent);
        timePickerDialog.show();

    }

    private void selectTimeM(String text, String date, String time) {
        AlarmManager alarmMgrD = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent2 = new Intent(getApplicationContext(), AlarmBrodcast.class);


        Calendar calendarD = Calendar.getInstance();
        calendarD.get(Calendar.HOUR_OF_DAY);
        calendarD.get(Calendar.MINUTE);
        calendarD.setTimeInMillis(System.currentTimeMillis());

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        intent2.putExtra("event", text);
        intent2.putExtra("time", date);
        intent2.putExtra("date", time);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent2, 0);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
            }
        }, hour, minute, false);

        alarmMgrD.setRepeating(AlarmManager.RTC_WAKEUP, calendarD.getTimeInMillis(), 1000 * 60 * 43800, alarmIntent);
        timePickerDialog.show();

    }

    private void selectTime() {

        Calendar calendarT = Calendar.getInstance();
        int hour = calendarT.get(Calendar.HOUR_OF_DAY);
        int minute = calendarT.get(Calendar.MINUTE);
        calendarT.setTimeInMillis(System.currentTimeMillis());
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                timeTonotify = i + ":" + i1;
                btn_time.setText(FormatTime(i, i1));
            }
        }, hour, minute, false);

        timePickerDialog.show();

    }

    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                btn_date.setText(day + "-" + (month + 1) + "-" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editext_message.setText(text.get(0));
            }
        }

    }

    private void setAlarm(String text, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);


        intent.putExtra("event", text);
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeTonotify;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        finish();

    }
}