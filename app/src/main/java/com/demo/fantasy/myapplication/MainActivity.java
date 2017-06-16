package com.demo.fantasy.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.demo.fantasy.common.AjaxUtil;
import com.demo.fantasy.scheduler.SampleAlarmReceiver;

public class MainActivity extends AppCompatActivity {

    SampleAlarmReceiver alarm = new SampleAlarmReceiver();

    //ListView 要顯示的內容　改到全域變數
    public String[] str = {"新北市", "台北市", "台中市", "台南市", "高雄市", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 清單
        ListView listview = (ListView) findViewById(R.id.listView);
        //android.R.layout.simple_list_item_1 為內建樣式，還有其他樣式可自行研究
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);       //指定事件 Method

        Button submit = (Button) findViewById(R.id.button);             // 取得按鈕物件
        // 按下按鈕 觸發事件
        submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                EditText h = (EditText) findViewById(R.id.et1);  // 取得身高物件
                EditText w = (EditText) findViewById(R.id.et2);                           // 取得體重物件
                //判斷條件 身高 跟 體重 都有輸入值才執行
                if (!("".equals(h.getText().toString())
                        || "".equals(w.getText().toString()))) {
                    // 回應頁面
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, MainResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("height", h.getText().toString());
                    bundle.putString("width", w.getText().toString());
                    intent.putExtras(bundle);   // 記得put進去，不然資料不會帶過去哦
                    startActivity(intent);
                }
            }
        });

        Button open = (Button) findViewById(R.id.open);             // 取得按鈕物件
        // 按下按鈕 觸發事件
        open.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainWebActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "第一頁 on start", Toast.LENGTH_LONG).show(); //onStart Called
        // 顯示頁面資訊
        GlobalVariable gv = (GlobalVariable) getApplicationContext();
        gv.setCurrPage("第一頁");
        TextView tv3 = (TextView) findViewById(R.id.tv3);
        tv3.setText(gv.getCurrPage());
        TextView tv4 = (TextView) findViewById(R.id.tv4);
        tv4.setText(gv.getFromPage());
        gv.setFromPage("來自第一頁");

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://app-x1945.rhcloud.com/system/wakeup";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Toast.makeText(MainActivity.this, AjaxUtil.test() + "連線正確:" + response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //mTextView.setText("That didn't work!");
                        Toast.makeText(MainActivity.this, AjaxUtil.test() + "連線錯誤", Toast.LENGTH_SHORT).show();
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        // 定時任務
//        alarm.setAlarm(getApplicationContext());
    }

    /***
     * 點擊ListView事件Method
     */
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Toast 快顯功能 第三個參數 Toast.LENGTH_SHORT 2秒  LENGTH_LONG 5秒
            Toast.makeText(MainActivity.this, AjaxUtil.test() + "點選第 " + (position + 1) + " 個 \n內容：" + str[position], Toast.LENGTH_SHORT).show();
        }
    };
}
