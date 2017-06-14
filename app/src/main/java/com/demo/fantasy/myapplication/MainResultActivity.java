package com.demo.fantasy.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_result);

        Bundle bundle = getIntent().getExtras();
        String h = bundle.getString("height");
        String w = bundle.getString("width");

        float fh = Float.parseFloat(h);      // 取得 身高輸入值
        float fw = Float.parseFloat(w);     // 取得 體重輸入值
        float fresult;                                     // BMI值 計算結果
        TextView result = (TextView) findViewById(R.id.tv5);// 取得 顯示結果 物件
        fh = fh / 100; // 計算BMI
        fh = fh * fh;  // 計算BMI

        NumberFormat nf = NumberFormat.getInstance();   // 數字格式
        nf.setMaximumFractionDigits(2);                 // 限制小數第二位
        fresult = fw / fh;                                // 計算BMI
        result.setText(nf.format(fw / fh));           // 顯示BMI計算結果
        TextView dia = (TextView) findViewById(R.id.tv6);// 取得 顯示診斷 物件
        // 診斷結果 顯示
        if (fresult < 18.5)
            dia.setText("體重過輕");
        else if (18.5 <= fresult && fresult < 24)
            dia.setText("正常範圍");
        else if (24 <= fresult && fresult < 27)
            dia.setText("過    重");
        else if (27 <= fresult && fresult < 30)
            dia.setText("輕度肥胖");
        else if (30 <= fresult && fresult < 35)
            dia.setText("中度肥胖");
        else if (fresult >= 35)
            dia.setText("重度肥胖        ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "第二頁 on start", Toast.LENGTH_LONG).show(); //onStart Called
        // 顯示頁面資訊
        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        gv.setCurrPage("第二頁");
        TextView tv7 = (TextView) findViewById(R.id.tv7);
        tv7.setText(gv.getCurrPage());
        TextView tv8 = (TextView) findViewById(R.id.tv8);
        tv8.setText(gv.getFromPage());
        gv.setFromPage("來自第二頁A");
    }
}
