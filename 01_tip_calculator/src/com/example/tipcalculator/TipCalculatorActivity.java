package com.example.tipcalculator;

import java.text.DecimalFormat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class TipCalculatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void cal10(View v) {
    	setTip(0.1);
    }
    
    public void cal20(View v) {
    	setTip(0.2);
    }
    
    public void cal30(View v) {
    	setTip(0.3);
    }
    
    private void setTip(double percentage) {
    	EditText etAmount = (EditText) findViewById(R.id.etAmount);
    	double amount = Double.parseDouble(etAmount.getText().toString());
    	double tip    = amount * percentage;
    	
    	TextView tvTip = (TextView) findViewById(R.id.tvTip);
    	tvTip.setText("Tip is " + String.valueOf(tip)); 	
    }
    
}
