package com.example.onlyclock;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView mTv1, mTv2, mTv3, mTv4, mTv5;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setBrightness(this,0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runnable.run();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            this.update();
            handler.postDelayed(this, 1000);
        }

        void update() {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);//获取当前年
            int mouth = calendar.get(Calendar.MONTH) + 1;//获取当前月
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//获取今天是本周的第几天
            int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);//获取当前月的第几天
            int hour = calendar.get(Calendar.HOUR_OF_DAY);//当前时间小时
            int minute = calendar.get(Calendar.MINUTE);//当前时间分钟
            int second = calendar.get(Calendar.SECOND);//获取当前分钟的秒
            String date = year + "/" + mouth + "/" + String.format("%02d", day_of_month);
            String week = "";
            switch (dayOfWeek) {
                case 1:
                    week = "星期日";
                    break;
                case 2:
                    week = "星期一";
                    break;
                case 3:
                    week = "星期二";
                    break;
                case 4:
                    week = "星期三";
                    break;
                case 5:
                    week = "星期四";
                    break;
                case 6:
                    week = "星期五";
                    break;
                case 7:
                    week = "星期六";
                    break;
            }
            if(2 <= dayOfWeek && dayOfWeek < 7){//在上班时间
                if(0 <= hour && hour < 6){
                    setBrightness(MainActivity.this,0);
                } else if(6 <= hour && hour < 8){
                    setBrightness(MainActivity.this,120);
                } else if(8 <= hour && hour < 24){
                    setBrightness(MainActivity.this,10);
                }
            } else {//在非工作日
                if(0 <= hour && hour < 6){
                    setBrightness(MainActivity.this,0);
                } else if(6 <= hour && hour < 19){
                    setBrightness(MainActivity.this,120);
                } else if(19 <= hour && hour < 24){
                    setBrightness(MainActivity.this,10);
                }
            }

            mTv1 = findViewById(R.id.tv_1);
            mTv1.setText(String.format("%02d", hour));

            mTv2 = findViewById(R.id.tv_2);
            mTv2.setText(String.format("%02d", minute));

            mTv3 = findViewById(R.id.tv_3);
            mTv3.setText(String.format("%02d", second));

            mTv4 = findViewById(R.id.tv_4);
            mTv4.setText(date);

            mTv5 = findViewById(R.id.tv_5);
            mTv5.setText(week);
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable); //停止刷新
        super.onDestroy();
    }

    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        activity.getWindow().setAttributes(lp);

    }
}