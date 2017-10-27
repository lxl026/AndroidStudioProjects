package company.leon.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Leon on 2017/10/25.
 */

public class myViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;//存储list_Item的子view
    private View mConvertView;//存储list_Item
    public myViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mConvertView=itemView;
        mViews=new SparseArray<View>();
    }
    public static myViewHolder get(Context context, ViewGroup parent, int layoutId)
    {
        View itemView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        myViewHolder holder=new myViewHolder(context,itemView,parent);
        return  holder;
    }
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if(view==null)
        {
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }
}
