package xujianjie.datapickerlib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

import xujianjie.datapickerlib.utils.AppUtil;
import xujianjie.datapickerlib.view.WheelOptions;

public class OptionsPicker<T> extends Dialog
{
    private Context context;

    private WheelOptions<T> wheelOptions;
    private OnOptionsSelectedListener optionsSelectListener;

    private ArrayList<T> options1Items;
    private ArrayList<ArrayList<T>> options2Items;
    private ArrayList<ArrayList<ArrayList<T>>> options3Items;

    private String label1;//单位
    private String label2;
    private String label3;

    private int option1;//默认选中项
    private int option2;
    private int option3;

    private int textSize;

    private int color = Color.parseColor("#65a5ff");

    private Button button_submit;
    private Button button_cancel;

    public OptionsPicker(Context context)
    {
        super(context, R.style.popDialogStyle);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickerview_options);

        initViews();

        wheelOptions = new WheelOptions(findViewById(R.id.optionsPicker), true);
        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        wheelOptions.setCurrentItems(option1, option2, option3);
        wheelOptions.setTextContentSize(textSize);
        wheelOptions.setLabels(label1, label2, label3);

        button_submit.setTextColor(color);
        button_cancel.setTextColor(color);

        button_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                if (optionsSelectListener != null)
                {
                    optionsSelectListener.onOptionsSelected(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], view);
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

    public void setOptionsSelectListener(OnOptionsSelectedListener optionsSelectListener)
    {
        this.optionsSelectListener = optionsSelectListener;
    }

    public String getLabel1()
    {
        return label1;
    }

    public void setLabel1(String label1)
    {
        this.label1 = label1;
    }

    public String getLabel2()
    {
        return label2;
    }

    public void setLabel2(String label2)
    {
        this.label2 = label2;
    }

    public String getLabel3()
    {
        return label3;
    }

    public void setLabel3(String label3)
    {
        this.label3 = label3;
    }

    public int getOption1()
    {
        return option1;
    }

    public void setOption1(int option1)
    {
        this.option1 = option1;
    }

    public int getOption2()
    {
        return option2;
    }

    public void setOption2(int option2)
    {
        this.option2 = option2;
    }

    public int getOption3()
    {
        return option3;
    }

    public void setOption3(int option3)
    {
        this.option3 = option3;
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

    public void setOptions1Items(ArrayList<T> options1Items)
    {
        this.options1Items = options1Items;
    }

    public void setOptions2Items(ArrayList<ArrayList<T>> options2Items)
    {
        this.options2Items = options2Items;
    }

    public void setOptions3Items(ArrayList<ArrayList<ArrayList<T>>> options3Items)
    {
        this.options3Items = options3Items;
    }

    public interface OnOptionsSelectedListener
    {
        void onOptionsSelected(int options1, int option2, int options3, View v);
    }

    private void initViews()
    {
        button_submit = findViewById(R.id.btnSubmit);
        button_cancel = findViewById(R.id.btnCancel);
    }
}