package xujianjie.datapickerlib.adapter;

public interface WheelAdapter<T>
{
    int getItemsCount();

    T getItem(int index);

    int indexOf(T o);
}