package company.leon.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leon on 2017/10/27.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<goods> list;
    private CommonAdapter.OnItemClickListener mOnItemClickListener=null;
    public MyAdapter(Context context,List<goods> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount(){
        if(list==null){
            return 0;
        }
        return list.size();
    }
    @Override
    public Object getItem(int i){
        if(list==null){
            return null;
        }
        return list.get(i);
    }
    @Override
    public long getItemId(int i){
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        View convertView;
        ViewHolder viewHolder;
        if(view==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.good_list,null);
            viewHolder=new ViewHolder();
            viewHolder.name=(TextView)convertView.findViewById(R.id.name);
            viewHolder.initial=(TextView)convertView.findViewById((R.id.initial));
            viewHolder.price=(TextView)convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        }
        else{
            convertView=view;
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.initial.setText(list.get(i).getInitials());
        viewHolder.price.setText(list.get(i).getPrice());
        return convertView;
    }
    private class ViewHolder{
        public TextView name;
        public TextView initial;
        public TextView price;
    }
    public interface OnItemClickListener
    {
        void onClick(int position);
        void onLongClick(int position);
    }
    public void setOnItemClickListener(CommonAdapter.OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener=onItemClickListener;
    }
}
