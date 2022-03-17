package com.example.bottledispenser;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    BottleDispenser bd = BottleDispenser.getInstance();
    TextView text;
    TextView text2;
    TextView balance;
    SeekBar seekbar;
    Spinner spinner;
    String receipt = "None";
    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
        text2 = (TextView) findViewById(R.id.textView2);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        spinner = (Spinner) findViewById(R.id.spinner);
        balance = (TextView) findViewById(R.id.textView3);
        populateSpinner();
        context = MainActivity.this;
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                text2.setText("Add: €" + i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        System.out.println("Directory: " + context.getFilesDir());
        balance.setText("Balance: " + bd.getMoney() + "€");


    }

    private void populateSpinner() {
        ArrayAdapter<String> bottleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.bottle_types));
        bottleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(bottleAdapter);
    }


    public void addMoney (View v) {
        bd.addMoney(seekbar.getProgress());
        text.setText("Added €" + seekbar.getProgress() + ", you have €" + bd.getMoney());
        seekbar.setProgress(0);
        balance.setText("Balance: €" + bd.getMoney());
    }
    public void returnMoney (View v) {
        bd.returnMoney();
        text.setText("Money returned.");
        balance.setText("Balance: €" + bd.getMoney());
    }


    public void buyBottle (View v) {

        String name[];
        if(bd.bottleCount() == 0) {
            text.setText("All out of bottles!");
        } else {
            String btl = spinner.getSelectedItem().toString();
            int id = Integer.parseInt(String.valueOf(btl.charAt(0)));
            System.out.println("id: " + id);
            int j = bd.buyBottle2(id);
            if(j == 0) {
                text.setText("Out of stock.");
            } else if (j == 1) {
                name = btl.split("\\.");
                text.setText("KACHUNK! " + name[1] + " came out of the dispenser!");
                receipt = btl;
                balance.setText("Balance: " + bd.getMoney() + "€");
            } else if (j == 2 ) {
                text.setText("Add more money!");
            }
        }
    }

    public void printList (View v) {
        ArrayList<Bottle> bottle_array = bd.listBottles();
        int bottles = bd.bottleCount();
        text.setText("");
        for(int i = 0; i < bottles; i++) {
            text.append(i+1 + ". Name: " + bottle_array.get(i).getName());
            text.append("\t Size: " + bottle_array.get(i).getSize());
            text.append("\t Price: " + bottle_array.get(i).getPrize() + "€\n");
        }
    }

    public void printRecipt (View v) {
        if(receipt.equals("None")) {
            text.setText("You haven't bought anything yet!");
        } else {
            String name = "receipt.txt";
            try {
                OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(name, context.MODE_PRIVATE));
                osw.write("This is a receipt for your purchase: " + receipt);
                osw.close();
            } catch (IOException e) {
                Log.e("IOException", "Virhe syötteessä");
            } finally {
                text.setText("Your receipt has been written to file '" + name + "'");
                System.out.println("WRITTEN");
            }
        }
    }



}