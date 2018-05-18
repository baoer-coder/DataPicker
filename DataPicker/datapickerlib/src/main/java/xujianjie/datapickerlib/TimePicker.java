package xujianjie.datapickerlib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import xujianjie.datapickerlib.utils.AppUtil;
import xujianjie.datapickerlib.view.WheelTime;

public class TimePicker extends Dialog
{
    public enum Type
    {
        YEAR, MONTH, DAY, HOUR, MINUTE, SECOND
    }

    private Context context;

    private WheelTime wheelTime;
    private OnTimeSelectedListener onTimeSelectedListener;
    private List<TimePicker.Type> typeList;
    private int color = Color.parseColor("#65a5ff");
    private int textSize;

    private int startYear;
    private int endYear;

    private Button button_submit;
    private Button button_cancel;

    public TimePicker(Context context, List<TimePicker.Type> typeList)
    {
        super(context, R.style.popDialogStyle);
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickerview_time);

        initViews();

        wheelTime = new WheelTime(findViewById(R.id.timePicker), typeList, Gravity.CENTER, textSize);
        wheelTime.setStartYear(startYear);
        wheelTime.setEndYear(endYear);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        wheelTime.setPicker(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
        wheelTime.setCyclic(false);

        button_submit.setTextColor(color);
        button_cancel.setTextColor(color);

        button_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                try
                {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    if (onTimeSelectedListener != null)
                    {
                        onTimeSelectedListener.onTimeSelected(date, view);
                    }
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }

                dismiss();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dismiss();
            }
        });

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = 0;
        params.y = AppUtil.getScreenWidthHeight(context)[1];
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().setAttributes(params);
        getWindow().setWindowAnimations(R.style.bottomDialogAnim);

        setCanceledOnTouchOutside(true);
        setCancelable(true);
    }

    public void setStartYear(int startYear)
    {
        this.startYear = startYear;
    }

    public void setEndYear(int endYear)
    {
        this.endYear = endYear;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public void setTextSize(int textSize)
    {
        this.textSize = textSize;
    }

    public void setTimeSelectListener(OnTimeSelectedListener onTimeSelectedListener)
    {
        this.onTimeSelectedListener = onTimeSelectedListener;
    }

    public interface OnTimeSelectedListener
    {
        void onTimeSelected(Date date, View v);
    }

    private void initViews()
    {
        button_submit = findViewById(R.id.btnSubmit);
        button_cancel = findViewById(R.id.btnCancel);
    }
}

