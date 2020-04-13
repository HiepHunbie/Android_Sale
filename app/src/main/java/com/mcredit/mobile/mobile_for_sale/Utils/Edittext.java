package com.mcredit.mobile.mobile_for_sale.Utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.text.DecimalFormat;

/**
 * Created by MSI on 11/14/2016.
 */

public class Edittext {
    public static void addCommasToEdittext(final EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);

                try {
                    String givenstring = s.toString();
                    Long longval;
                    givenstring = givenstring.replaceAll(",", "").replaceAll("\\.", "");
                    if(givenstring.length()==0){
                        editText.setText(givenstring);
                    }else {
                        longval = Long.parseLong(givenstring.trim());
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String formattedString = formatter.format(longval);
                        editText.setText(formattedString);
                        editText.setSelection(editText.getText().length());
                    }
                    // to place the cursor at the end of text
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public static void setCheckBox(final CheckBox checkBox1, final EditText edittext, final EditText edittext2, final EditText edittext3, final EditText edittext4, final EditText edittext5,final  EditText edittext6){
        if(edittext.getText().toString().length()>0){
            checkBox1.callOnClick();
            checkBox1.setChecked(true);
            edittext.setFocusableInTouchMode(true);
            edittext.setAlpha(1);
            edittext.setEnabled(true);
            if(edittext6!=null){
                edittext6.setFocusableInTouchMode(true);
                edittext6.setAlpha(1);
                edittext6.setEnabled(true);
            }
            edittext2.setFocusable(false);
            edittext3.setFocusable(false);
            edittext4.setFocusable(false);
            edittext5.setFocusable(false);
            edittext2.setEnabled(false);
            edittext3.setEnabled(false);
            edittext4.setEnabled(false);
            edittext5.setEnabled(false);
            edittext2.setAlpha(0.5f);
            edittext3.setAlpha(0.5f);
            edittext4.setAlpha(0.5f);
            edittext5.setAlpha(0.5f);
        }

        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                checkBox1.callOnClick();
                checkBox1.setChecked(true);
                edittext.setFocusableInTouchMode(true);
                edittext.setAlpha(1);
                edittext.setEnabled(true);
                if(edittext6!=null){
                    edittext6.setFocusableInTouchMode(true);
                    edittext6.setAlpha(1);
                    edittext6.setEnabled(true);
                }
                edittext2.setFocusable(false);
                edittext3.setFocusable(false);
                edittext4.setFocusable(false);
                edittext5.setFocusable(false);
                edittext2.setEnabled(false);
                edittext3.setEnabled(false);
                edittext4.setEnabled(false);
                edittext5.setEnabled(false);
                edittext2.setAlpha(0.5f);
                edittext3.setAlpha(0.5f);
                edittext4.setAlpha(0.5f);
                edittext5.setAlpha(0.5f);
                edittext2.setText("");
                edittext3.setText("");
                edittext4.setText("");
                edittext5.setText("");
                return false;
            }
        });

        checkBox1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!checkBox1.isChecked()){
                    edittext.setFocusableInTouchMode(true);
                    edittext.setAlpha(1);
                    edittext.setEnabled(true);
                    if(edittext6!=null){
                        edittext6.setFocusableInTouchMode(true);
                        edittext6.setAlpha(1);
                        edittext6.setEnabled(true);
                    }
                    edittext2.setText("");
                    edittext3.setText("");
                    edittext4.setText("");
                    edittext5.setText("");
                    edittext2.setFocusable(false);
                    edittext3.setFocusable(false);
                    edittext4.setFocusable(false);
                    edittext5.setFocusable(false);
                    edittext2.setEnabled(false);
                    edittext3.setEnabled(false);
                    edittext4.setEnabled(false);
                    edittext5.setEnabled(false);
                    edittext2.setAlpha(0.5f);
                    edittext3.setAlpha(0.5f);
                    edittext4.setAlpha(0.5f);
                    edittext5.setAlpha(0.5f);
                }else {
                    edittext2.setFocusableInTouchMode(true);
                    edittext3.setFocusableInTouchMode(true);
                    edittext4.setFocusableInTouchMode(true);
                    edittext5.setFocusableInTouchMode(true);
                    edittext2.setEnabled(true);
                    edittext3.setEnabled(true);
                    edittext4.setEnabled(true);
                    edittext5.setEnabled(true);
                    edittext2.setAlpha(1);
                    edittext3.setAlpha(1);
                    edittext4.setAlpha(1);
                    edittext5.setAlpha(1);
                }
                return false;
            }
        });
    }

    public static String convertTextToCommas(String text){
        Log.d("formattedString", "Ss" + text);
        String string = "";
        String formattedString = "";
        String startText = "";
        if(text.length()>2) {
            if (text.contains("-")) {
                String[] parts = text.split(" - ");
                if(parts.length>1){
                    String givenstring = parts[0].toString();
                    String givenstring1 = parts[1].toString();
                Long longval;
                Long longval1;
                    givenstring = givenstring.replaceAll(",", "").replaceAll("\\.", "");
                    givenstring1 = givenstring1.replaceAll(",", "").replaceAll("\\.", "");
                    if(givenstring.length()==0){
                        longval =Long.parseLong("0");
                    }else {
                        longval = Long.parseLong(givenstring.trim());
                    }
                    if(givenstring1.length()==0){
                        longval1 =Long.parseLong("0");
                    }else {
                        longval1 = Long.parseLong(givenstring1.trim());
                    }
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                formattedString = formatter.format(longval) + " - " + formatter.format(longval1);
                }
            } else {
                if (text.contains(">")) {
                    string = text.replace("> ", "");
                    startText = "> ";
                } else if (text.contains("<")) {
                    string = text.replace("< ", "");
                    startText = "< ";
                } else {
                    string = text;
                    startText = "";
                }
                String givenstring = string.toString();
                Long longval;
                givenstring = givenstring.replaceAll(",", "").replaceAll("\\.", "");
                if(givenstring.length()==0){
                    longval =Long.parseLong("0");
                }else {
                    longval = Long.parseLong(givenstring.trim());
                }
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                formattedString = formatter.format(longval);
            }
        }
            // to place the cursor at the end of text
        return  startText + formattedString;
    }
}
