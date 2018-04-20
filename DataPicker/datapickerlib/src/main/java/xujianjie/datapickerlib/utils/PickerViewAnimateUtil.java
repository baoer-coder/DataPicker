package xujianjie.datapickerlib.utils;

import android.view.Gravity;

import xujianjie.datapickerlib.R;

public class PickerViewAnimateUtil
{
    private static final int INVALID = -1;

    public static int getAnimationResource(int gravity, boolean isInAnimation)
    {
        switch (gravity)
        {
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.bottom_picker_anim_in : R.anim.bottom_picker_anim_out;
        }
        return INVALID;
    }
}
