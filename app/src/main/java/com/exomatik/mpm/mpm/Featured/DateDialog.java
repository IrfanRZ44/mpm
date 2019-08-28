package com.exomatik.mpm.mpm.Featured;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

@SuppressLint({"ValidFragment"})
public class DateDialog
  extends DialogFragment
  implements DatePickerDialog.OnDateSetListener
{
  TextView txtDate;
  
  public DateDialog(View paramView, TextView paramTextView)
  {
    this.txtDate = paramTextView;
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    Calendar localCalendar = Calendar.getInstance();
    int i = localCalendar.get(Calendar.YEAR);
    int j = localCalendar.get(Calendar.MONTH);
    int k = localCalendar.get(Calendar.DATE);
    return new DatePickerDialog(getActivity(), this, i, j, k);
  }
  
  public void onDateSet(DatePicker paramDatePicker, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2 + 1;
    String str1 = Integer.toString(paramInt1);
    String str2 = Integer.toString(i);
    String str3 = Integer.toString(paramInt3);
    if (i < 10) {
      str2 = "0" + i;
    }
    if (paramInt3 < 10) {
      str3 = "0" + paramInt3;
    }
    String str4 = str1 + "-" + str2 + "--" + str3;
    this.txtDate.setText(str4);
  }
}