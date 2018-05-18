package xujianjie.datapickerlib;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import xujianjie.datapickerlib.view.BasePickerView;
import xujianjie.datapickerlib.view.WheelTime;

public class TimePickerView extends BasePickerView implements View.OnClickListener
{
    public enum Type
    {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
    }

    private WheelTime wheelTime; //自定义控件
    private OnTimeSelectListener timeSelectListener;//回调接口
    private int gravity = Gravity.CENTER;//内容显示位置 默认居中
    private TimePickerView.Type type;// 显示类型

    private String title = "";//标题字符串

    private Date date;//当前选中时间
    private int startYear;//开始年份
    private int endYear;//结尾年份
    private boolean cyclic;//是否循环
    private boolean cancelable;//是否能取消

    private String label_year, label_month, label_day, label_hours, label_mins, label_seconds;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private int color = Color.parseColor("#65a5ff");

    //构造方法
    TimePickerView(Builder builder)
    {
        super(builder.context);
        this.timeSelectListener = builder.timeSelectListener;
        this.gravity = builder.gravity;
        this.type = builder.type;
        this.title = builder.title;
        this.startYear = builder.startYear;
        this.endYear = builder.endYear;
        this.date = builder.date;
        this.cyclic = builder.cyclic;
        this.cancelable = builder.cancelable;
        this.label_year = builder.label_year;
        this.label_month = builder.label_month;
        this.label_day = builder.label_day;
        this.label_hours = builder.label_hours;
        this.label_mins = builder.label_mins;
        this.label_seconds = builder.label_seconds;
        this.color = builder.color;

        initView(builder.context);
    }

    public static class Builder
    {
        private Context context;
        private OnTimeSelectListener timeSelectListener;

        private TimePickerView.Type type = Type.ALL;//显示类型 默认全部显示
        private int gravity = Gravity.CENTER;//内容显示位置 默认居中

        private String title;//标题文字

        private Date date;//当前选中时间
        private int startYear;//开始年份
        private int endYear;//结尾年份
        private boolean cyclic = false;//是否循环
        private boolean cancelable = true;//是否能取消
        private int color = Color.parseColor("#65a5ff");

        private String label_year, label_month, label_day, label_hours, label_mins, label_seconds;//单位

        //Required
        public Builder(Context context, OnTimeSelectListener listener)
        {
            this.context = context;
            this.timeSelectListener = listener;
        }

        //Option
        public Builder setType(TimePickerView.Type type)
        {
            this.type = type;
            return this;
        }

        public Builder gravity(int gravity)
        {
            this.gravity = gravity;
            return this;
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setDate(Date date)
        {
            this.date = date;
            return this;
        }

        public Builder setColor(int color)
        {
            this.color = color;
            return this;
        }

        public Builder setRange(int startYear, int endYear)
        {
            this.startYear = startYear;
            this.endYear = endYear;
            return this;
        }

        public Builder isCyclic(boolean cyclic)
        {
            this.cyclic = cyclic;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable)
        {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setLabel(String label_year, String label_month, String label_day, String label_hours, String label_mins, String label_seconds)
        {
            this.label_year = label_year;
            this.label_month = label_month;
            this.label_day = label_day;
            this.label_hours = label_hours;
            this.label_mins = label_mins;
            this.label_seconds = label_seconds;
            return this;
        }

        public TimePickerView build()
        {
            return new TimePickerView(this);
        }
    }

    private void initView(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.pickerview_time, contentContainer);

        //顶部标题
        TextView tvTitle = (TextView) findViewById(R.id.tvTitle);

        //确定和取消按钮
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel.setTag(TAG_CANCEL);

        btnSubmit.setTextColor(color);
        btnCancel.setTextColor(color);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        wheelTime = new WheelTime(findViewById(R.id.timePicker), type, gravity, 18);

        if (startYear != 0 && endYear != 0 && startYear <= endYear)
        {
            setRange();
        }
        setTime();
        wheelTime.setLabels(label_year, label_month, label_day, label_hours, label_mins, label_seconds);
        setOutSideCancelable(cancelable);
        wheelTime.setCyclic(cyclic);

        tvTitle.setText(title);
    }

    /**
     * 设置可以选择的时间范围, 要在setTime之前调用才有效果
     */
    private void setRange()
    {
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);
    }

    /**
     * 设置选中时间,默认选中当前时间
     */
    private void setTime()
    {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
        {
            calendar.setTimeInMillis(System.currentTimeMillis());
        }
        else
        {
            calendar.setTime(date);
        }

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        wheelTime.setPicker(year, month, day, hours, minute, seconds);
    }

    @Override
    public void onClick(View v)
    {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL))
        {
            dismiss();
        }
        else
        {
            if (timeSelectListener != null)
            {
                try
                {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    timeSelectListener.onTimeSelect(date, v);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            dismiss();
        }
    }

    public interface OnTimeSelectListener
    {
        void onTimeSelect(Date date, View v);
    }
}

