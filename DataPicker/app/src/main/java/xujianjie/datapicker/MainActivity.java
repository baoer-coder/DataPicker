package xujianjie.datapicker;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import xujianjie.datapickerlib.OptionsPickerView;
import xujianjie.datapickerlib.TimePickerView;

public class MainActivity extends AppCompatActivity
{
    private Button button_pickTime;
    private Button button_pickOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        button_pickTime.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new TimePickerView.Builder(MainActivity.this, new TimePickerView.OnTimeSelectListener()
                {
                    @Override
                    public void onTimeSelect(Date date, View v)
                    {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        String selectDate = format.format(date);

                        Log.e("DataPicker", selectDate);
                    }
                }).setType(TimePickerView.Type.YEAR_MONTH_DAY).setRange(2018, 2019).setColor(Color.parseColor("#ff0000")).build().show();
            }
        });

        button_pickOptions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                final ArrayList<String> list = new ArrayList<>();
                list.add("选项1");
                list.add("选项2");
                list.add("选项3");
                list.add("选项4");
                list.add("选项5");
                list.add("选项6");
                list.add("选项7");

                OptionsPickerView optionsPickerView = new OptionsPickerView.Builder(MainActivity.this, new OptionsPickerView.OnOptionsSelectListener()
                {
                    @Override
                    public void onOptionsSelect(int position1, int position2, int position3, View v)
                    {
                        Log.e("DataPicker", list.get(position1));
                    }
                }).setColor(Color.parseColor("#ff0000")).build();
                optionsPickerView.setPicker(list);
                optionsPickerView.show();
            }
        });
    }

    private void initViews()
    {
        button_pickTime = (Button) findViewById(R.id.button_pickTime);
        button_pickOptions = (Button) findViewById(R.id.button_pickOptions);
    }
}
