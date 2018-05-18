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
import java.util.List;
import java.util.Locale;

import xujianjie.datapickerlib.OptionsPicker;
import xujianjie.datapickerlib.TimePicker;

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
                List<TimePicker.Type> typeList = new ArrayList<>();
                typeList.add(TimePicker.Type.MONTH);
                typeList.add(TimePicker.Type.DAY);
                typeList.add(TimePicker.Type.HOUR);
                typeList.add(TimePicker.Type.MINUTE);

                TimePicker timePicker = new TimePicker(MainActivity.this, typeList);
                timePicker.setTimeSelectListener(new TimePicker.OnTimeSelectedListener()
                {
                    @Override
                    public void onTimeSelected(Date date, View v)
                    {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        String selectDate = format.format(date);

                        Log.e("DataPicker", selectDate);
                    }
                });
                timePicker.setTextSize(16);
                timePicker.setStartYear(2018);
                timePicker.setEndYear(2019);
                timePicker.setColor(Color.parseColor("#ff0000"));
                timePicker.show();
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

                OptionsPicker optionsPicker = new OptionsPicker(MainActivity.this);
                optionsPicker.setOptionsSelectListener(new OptionsPicker.OnOptionsSelectedListener()
                {
                    @Override
                    public void onOptionsSelected(int position1, int option2, int options3, View v)
                    {
                        Log.e("DataPicker", list.get(position1));
                    }
                });
                optionsPicker.setOptions1Items(list);
                optionsPicker.setColor(Color.parseColor("#ff0000"));
                optionsPicker.setTextSize(16);
                optionsPicker.show();
            }
        });
    }

    private void initViews()
    {
        button_pickTime = findViewById(R.id.button_pickTime);
        button_pickOptions = findViewById(R.id.button_pickOptions);
    }
}
