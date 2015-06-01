package com.ircar.irremotecontroller;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private RadioGroup mTVModel;

    private Button mPower, mChUp, mChDown, mVolUp, mVolDown;

    HashMap<Integer, SparseArray<String>> mIrData = new HashMap<>();

    ConsumerIrManager mCIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTVModel = (RadioGroup) findViewById(R.id.tvmodel);

        mPower = (Button) findViewById(R.id.power);
        mChUp = (Button) findViewById(R.id.chup);
        mChDown = (Button) findViewById(R.id.chdown);
        mVolUp = (Button) findViewById(R.id.volup);
        mVolDown = (Button) findViewById(R.id.voldown);

        mPower.setOnClickListener(this);
        mChUp.setOnClickListener(this);
        mChDown.setOnClickListener(this);
        mVolUp.setOnClickListener(this);
        mVolDown.setOnClickListener(this);

        // IR
        mCIR = (ConsumerIrManager)getSystemService(Context.CONSUMER_IR_SERVICE);

        initSamsung();
    }

    @Override
    public void onClick(View v) {
        final int controll = v.getId();
        final int tvType = mTVModel.getCheckedRadioButtonId();

        if (mCIR.hasIrEmitter()) {
            irSend(tvType, controll);
        } else {
            Toast.makeText(this, "Telefonul nu are IR", Toast.LENGTH_SHORT).show();
        }
    }

    private void irSend(final int tvType, final int controll) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String data = mIrData.get(tvType).get(controll);
                if (data != null) {
                    String values[] = data.split(",");
                    int[] pattern = new int[values.length-1];

                    for (int i=0; i<pattern.length; i++){
                        pattern[i] = Integer.parseInt(values[i+1]);
                    }

                    mCIR.transmit(Integer.parseInt(values[0]), pattern);
                }
            }
        }).start();
    }

    private void initSamsung() {
        SparseArray<String> irData = new SparseArray<>();
        mIrData.put(R.id.samsung, irData);

        irData.put(
                R.id.power,
                hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0040 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
        irData.put(
                R.id.chup,
                hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 0015 0015 0040 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
        irData.put(
                R.id.chdown,
                hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
        irData.put(
                R.id.volup,
                hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));
        irData.put(
                R.id.voldown,
                hex2dec("0000 006d 0022 0003 00a9 00a8 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 003f 0015 0015 0015 003f 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 0015 003f 0015 0015 0015 003f 0015 003f 0015 003f 0015 003f 0015 0702 00a9 00a8 0015 0015 0015 0e6e"));

    }

    protected String hex2dec(String irData) {
        List<String> list = new ArrayList<>(Arrays.asList(irData
                .split(" ")));
        list.remove(0); // dummy
        int frequency = Integer.parseInt(list.remove(0), 16); // frequency
        list.remove(0); // seq1
        list.remove(0); // seq2

        for (int i = 0; i < list.size(); i++) {
            list.set(i, Integer.toString(Integer.parseInt(list.get(i), 16)));
        }

        frequency = (int) (1000000 / (frequency * 0.241246));
        list.add(0, Integer.toString(frequency));

        irData = "";
        for (String s : list) {
            irData += s + ",";
        }
        return irData;
    }

}
