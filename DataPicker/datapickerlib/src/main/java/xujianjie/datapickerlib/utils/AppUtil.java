package xujianjie.datapickerlib.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class AppUtil
{
    //获取屏幕宽高
    public static int[] getScreenWidthHeight(Context context)
    {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        if (manager != null)
        {
            manager.getDefaultDisplay().getMetrics(dm);
        }

        return new int[]{dm.widthPixels, dm.heightPixels};
    }
}