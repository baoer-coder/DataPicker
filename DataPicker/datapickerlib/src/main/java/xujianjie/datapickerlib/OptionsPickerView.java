package xujianjie.datapickerlib;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import xujianjie.datapickerlib.view.BasePickerView;
import xujianjie.datapickerlib.view.WheelOptions;

public class OptionsPickerView<T> extends BasePickerView implements View.OnClickListener
{
    WheelOptions<T> wheelOptions;

    private Button btnSubmit, btnCancel; //确定、取消按钮
    private TextView tvTitle;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private OnOptionsSelectListener optionsSelectListener;

    private String title = "";//标题文字

    private boolean cancelable;//是否能取消
    private boolean linkage;//是否联动

    private String label1;//单位
    private String label2;
    private String label3;

    private boolean cyclic1;//是否循环
    private boolean cyclic2;
    private boolean cyclic3;

    private int option1;//默认选中项
    private int option2;
    private int option3;

    private int color = Color.parseColor("#65a5ff");

    //构造方法
    public OptionsPickerView(Builder builder)
    {
        super(builder.context);
        this.optionsSelectListener = builder.optionsSelectListener;
        this.title = builder.title;

        this.cyclic1 = builder.cyclic1;
        this.cyclic2 = builder.cyclic2;
        this.cyclic3 = builder.cyclic3;

        this.cancelable = builder.cancelable;
        this.linkage = builder.linkage;

        this.label1 = builder.label1;
        this.label2 = builder.label2;
        this.label3 = builder.label3;

        this.option1 = builder.option1;
        this.option2 = builder.option2;
        this.option3 = builder.option3;

        this.color = builder.color;

        initView(builder.context);
    }

    public static class Builder
    {
        private Context context;
        private OnOptionsSelectListener optionsSelectListener;

        private String title;//标题文字

        private boolean cancelable = true;//是否能取消
        private boolean linkage = true;//是否联动

        private String label1;
        private String label2;
        private String label3;

        private boolean cyclic1 = false;//是否循环，默认否
        private boolean cyclic2 = false;
        private boolean cyclic3 = false;

        private int option1;//默认选中项
        private int option2;
        private int option3;

        private int color = Color.parseColor("#65a5ff");

        public Builder(Context context, OnOptionsSelectListener listener)
        {
            this.context = context;
            this.optionsSelectListener = listener;
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable)
        {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setLinkage(boolean linkage)
        {
            this.linkage = linkage;
            return this;
        }

        public Builder setLabels(String label1, String label2, String label3)
        {
            this.label1 = label1;
            this.label2 = label2;
            this.label3 = label3;
            return this;
        }

        public Builder setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3)
        {
            this.cyclic1 = cyclic1;
            this.cyclic2 = cyclic2;
            this.cyclic3 = cyclic3;
            return this;
        }

        public Builder setSelectOptions(int option1)
        {
            this.option1 = option1;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2)
        {
            this.option1 = option1;
            this.option2 = option2;
            return this;
        }

        public Builder setSelectOptions(int option1, int option2, int option3)
        {
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            return this;
        }

        public Builder setColor(int color)
        {
            this.color = color;
            return this;
        }

        public OptionsPickerView build()
        {
            return new OptionsPickerView(this);
        }
    }

    private void initView(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.pickerview_options, contentContainer);

        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        //确定和取消按钮
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnSubmit.setTextColor(color);
        btnCancel.setTextColor(color);

        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        wheelOptions = new WheelOptions(findViewById(R.id.optionsPicker), linkage);
        wheelOptions.setTextContentSize(18);
        wheelOptions.setLabels(label1, label2, label3);
        wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);

        setOutSideCancelable(cancelable);
        tvTitle.setText(title);
    }

    public void setPicker(ArrayList<T> optionsItems)
    {
        wheelOptions.setPicker(optionsItems, null, null);
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    public void setPicker(ArrayList<T> options1Items, ArrayList<ArrayList<T>> options2Items)
    {
        wheelOptions.setPicker(options1Items, options2Items, null);
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    public void setPicker(ArrayList<T> options1Items, ArrayList<ArrayList<T>> options2Items, ArrayList<ArrayList<ArrayList<T>>> options3Items)
    {
        wheelOptions.setPicker(options1Items, options2Items, options3Items);
        wheelOptions.setCurrentItems(option1, option2, option3);
    }

    @Override
    public void onClick(View v)
    {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL))
        {
            dismiss();
            return;
        }
        else
        {
            if (optionsSelectListener != null)
            {
                int[] optionsCurrentItems = wheelOptions.getCurrentItems();
                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2], v);
            }
            dismiss();
            return;
        }
    }

    public interface OnOptionsSelectListener
    {
        void onOptionsSelect(int options1, int option2, int options3, View v);
    }
}
