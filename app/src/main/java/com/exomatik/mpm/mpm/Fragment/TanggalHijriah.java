package com.exomatik.mpm.mpm.Fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.exomatik.mpm.mpm.R;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import net.alhazmy13.hijridatepicker.date.hijri.HijriDatePickerDialog;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TanggalHijriah extends Fragment implements HijriDatePickerDialog.OnDateSetListener{
  private View view;
  private RelativeLayout btnTanggal;
  private HijriDatePickerDialog dpd;
  
  @Nullable
  public View onCreateView(@NonNull LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
    this.view = paramLayoutInflater.inflate(R.layout.content_hijriah, paramViewGroup, false);

    btnTanggal = (RelativeLayout) view.findViewById(R.id.btn_date);

    btnTanggal.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showDateHijri();
      }
    });

    showDateHijri();



    return view;
  }

  private void showDateHijri(){
//    Date c = Calendar.getInstance().getTime();
    UmmalquraCalendar now = new UmmalquraCalendar();
    Date tgl = new Date(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE) - 3);
    String day = (String) DateFormat.format("EEEE", tgl);
    dpd = HijriDatePickerDialog.newInstance(
            TanggalHijriah.this,
            now.get(Calendar.YEAR),
            now.get(Calendar.MONTH),
            now.get(Calendar.DATE)
    );
    dpd.setThemeDark(true);
    dpd.vibrate(true);
    dpd.dismissOnPause(true);
    dpd.showYearPickerFirst(false);
    dpd.setVersion(false ? HijriDatePickerDialog.Version.VERSION_2 : HijriDatePickerDialog.Version.VERSION_1);
    dpd.setAccentColor(Color.parseColor("#52af44"));
    dpd.setTitle(dayToHijri(day));
    final UmmalquraCalendar date1 = new UmmalquraCalendar();
    UmmalquraCalendar date2 = new UmmalquraCalendar();
    date2.add(Calendar.WEEK_OF_MONTH, -1);
    UmmalquraCalendar date3 = new UmmalquraCalendar();
    date3.add(Calendar.WEEK_OF_MONTH, 1);
    UmmalquraCalendar[] days = {date1, date2, date3};
    dpd.setHighlightedDays(days);
    //Change the language to any of supported language
    dpd.setLocale(new Locale("ar"));
    dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

    dpd.registerOnDateChangedListener(new HijriDatePickerDialog.OnDateChangedListener() {
      @Override
      public void onDateChanged() {
        Date date = new Date(dpd.getSelectedDay().getYear(), dpd.getSelectedDay().getMonth(), dpd.getSelectedDay().getDay() - 3);
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);
        Toast.makeText(getActivity(), dayOfTheWeek, Toast.LENGTH_SHORT).show();
        dpd.setTitle(dayToHijri(dayOfTheWeek));
      }
    });
  }

  private String dayToHijri(String day){
    String hijri = null;
    if (day.equals("Minggu")){
      hijri = "Al-Ahad";
    }
    else if (day.equals("Senin")){
      hijri ="Al-Ithnayn";
    }
    else if (day.equals("Selasa")){
      hijri ="Ats-Tsalaatsa'";
    }
    else if (day.equals("Rabu")){
      hijri ="Al-Arbaa-a/Ar-Raabi'";
    }
    else if (day.equals("Kamis")){
      hijri ="Al-Khamsah'";
    }
    else if (day.equals("Jumat")){
      hijri ="Al-Jumu'ah";
    }
    else if (day.equals("Sabtu")){
      hijri ="As-Sabt";
    }
    return hijri;
  }

  @Override
  public void onDateSet(HijriDatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    String date = "You picked the following date: " + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
    Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
  }
}