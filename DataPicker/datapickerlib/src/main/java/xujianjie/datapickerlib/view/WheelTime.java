package xujianjie.datapickerlib.view;

import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import xujianjie.datapickerlib.R;
import xujianjie.datapickerlib.TimePicker;
import xujianjie.datapickerlib.adapter.NumericWheelAdapter;
import xujianjie.datapickerlib.lib.WheelView;
import xujianjie.datapickerlib.listener.OnItemSelectedListener;

public class WheelTime
{
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;
    private WheelView wv_seconds;
    private int gravity;

    private List<TimePicker.Type> typeList;
    private static final int DEFAULT_START_YEAR = 1900;
    private static final int DEFAULT_END_YEAR = 2100;
    private int startYear = DEFAULT_START_YEAR;
    private int endYear = DEFAULT_END_YEAR;

    // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
    private int textSize = 14;

    public WheelTime(View view)
    {
        super();
        this.view = view;
        typeList = new ArrayList<>();
        setView(view);
    }

    public WheelTime(View view, List<TimePicker.Type> typeList, int gravity, int textSize)
    {
        super();
        this.view = view;
        this.typeList = typeList;
        this.gravity = gravity;
        this.textSize = textSize;
        setView(view);
    }

    public void setPicker(int year, int month, int day)
    {
        this.setPicker(year, month, day, 0, 0, 0);
    }

    public void setPicker(int year, int month, int day, int h, int m, int s)
    {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        // 年
        wv_year = view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(startYear, endYear));// 设置"年"的显示数据
        wv_year.setLabel("年");// 添加文字
        wv_year.setCurrentItem(year - startYear);// 初始化时显示的数据
        wv_year.setGravity(gravity);
        // 月
        wv_month = view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));
        wv_month.setLabel("月");
        wv_month.setCurrentItem(month);
        wv_month.setGravity(gravity);
        // 日
        wv_day = view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1)))
        {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        }
        else if (list_little.contains(String.valueOf(month + 1)))
        {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        }
        else
        {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            else
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
        }
        wv_day.setLabel("日");
        wv_day.setCurrentItem(day - 1);
        wv_day.setGravity(gravity);
        //时
        wv_hours = view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
        wv_hours.setLabel("时");// 添加文字
        wv_hours.setCurrentItem(h);
        wv_hours.setGravity(gravity);
        //分
        wv_mins = view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
        wv_mins.setLabel("分");// 添加文字
        wv_mins.setCurrentItem(m);
        wv_mins.setGravity(gravity);
        //秒
        wv_seconds = view.findViewById(R.id.second);
        wv_seconds.setAdapter(new NumericWheelAdapter(0, 59));
        wv_seconds.setLabel("秒");// 添加文字
        wv_seconds.setCurrentItem(s);
        wv_seconds.setGravity(gravity);

        // 添加"年"监听
        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(int index)
            {
                int year_num = index + startYear;
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem;
                if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1)))
                {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                }
                else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1)))
                {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                }
                else
                {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
                    {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    }
                    else
                    {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1)
                {
                    wv_day.setCurrentItem(maxItem - 1);
                }
            }
        };
        // 添加"月"监听
        OnItemSelectedListener wheelListener_month = new OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(int index)
            {
                int month_num = index + 1;
                int maxItem = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num)))
                {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                }
                else if (list_little.contains(String.valueOf(month_num)))
                {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                }
                else
                {
                    if (((wv_year.getCurrentItem() + startYear) % 4 == 0 && (wv_year.getCurrentItem() + startYear) % 100 != 0)
                            || (wv_year.getCurrentItem() + startYear) % 400 == 0)
                    {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    }
                    else
                    {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1)
                {
                    wv_day.setCurrentItem(maxItem - 1);
                }

            }
        };
        wv_year.setOnItemSelectedListener(wheelListener_year);
        wv_month.setOnItemSelectedListener(wheelListener_month);

        for (TimePicker.Type type:typeList)
        {
            if(type == TimePicker.Type.YEAR )
            {
                wv_year.setVisibility(View.VISIBLE);
            }
            else if(type == TimePicker.Type.MONTH )
            {
                wv_month.setVisibility(View.VISIBLE);
            }
            else if(type == TimePicker.Type.DAY )
            {
                wv_day.setVisibility(View.VISIBLE);
            }
            else if(type == TimePicker.Type.HOUR )
            {
                wv_hours.setVisibility(View.VISIBLE);
            }
            else if(type == TimePicker.Type.MINUTE )
            {
                wv_mins.setVisibility(View.VISIBLE);
            }
            else if(type == TimePicker.Type.SECOND )
            {
                wv_seconds.setVisibility(View.VISIBLE);
            }
        }

        setContentTextSize();
    }

    private void setContentTextSize()
    {
        wv_day.setTextSize(textSize);
        wv_month.setTextSize(textSize);
        wv_year.setTextSize(textSize);
        wv_hours.setTextSize(textSize);
        wv_mins.setTextSize(textSize);
        wv_seconds.setTextSize(textSize);
    }

    public void setLabels(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds)
    {
        if (label_year != null)
            wv_year.setLabel(label_year);
        if (label_month != null)
            wv_month.setLabel(label_month);
        if (label_day != null)
            wv_day.setLabel(label_day);
        if (label_hours != null)
            wv_hours.setLabel(label_hours);
        if (label_mins != null)
            wv_mins.setLabel(label_mins);
        if (label_seconds != null)
            wv_seconds.setLabel(label_seconds);
    }

    public void setCyclic(boolean cyclic)
    {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
        wv_seconds.setCyclic(cyclic);
    }

    public String getTime()
    {
        StringBuilder sb = new StringBuilder();
        sb.append((wv_year.getCurrentItem() + startYear)).append("-")
                .append((wv_month.getCurrentItem() + 1)).append("-")
                .append((wv_day.getCurrentItem() + 1)).append(" ")
                .append(wv_hours.getCurrentItem()).append(":")
                .append(wv_mins.getCurrentItem()).append(":")
                .append(wv_seconds.getCurrentItem());
        return sb.toString();
    }

    public View getView()
    {
        return view;
    }

    public void setView(View view)
    {
        this.view = view;
    }

    public int getStartYear()
    {
        return startYear;
    }

    public void setStartYear(int startYear)
    {
        this.startYear = startYear;
    }

    public int getEndYear()
    {
        return endYear;
    }

    public void setEndYear(int endYear)
    {
        this.endYear = endYear;
    }
}
