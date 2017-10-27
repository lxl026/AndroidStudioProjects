package company.leon.lab3;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRcyclerView;
    List<Map<String,Object>> listItems;         //存储商品列表
    List<goods>data;                             //存储商品信息
    FloatingActionButton floatingActionButton;
    ListView mListView;
    boolean flag;//商品列表和购物车的判断
    CommonAdapter commonAdapter;
    List<Map<String,Object>> shopItems;         //存储购物车商品
    SimpleAdapter simpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //商品内容
        String[] Name = new String[]{"Enchated Forest", "Aela Milk", "Devondale Milk", "Kindle Oasis",
                "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers",
                "Lindt", "Borggreve"};
        String[] Initials = new String[]{"E", "A", "D", "K", "w", "M", "F", "M", "L", "B"};
        String[] Price = new String[]{"￥5.00", "￥59.00", "￥79.00", "￥2300.00", "￥179.00", "￥14.90",
                "￥132.59", "￥141.13", "￥139.43", "￥28.90"};
        String[] Info = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8G",
                "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};
        int[] ImageId = new int[]{R.drawable.enchatedforest, R.drawable.arla, R.drawable.devondale, R.drawable.kindle,
                R.drawable.waitrose, R.drawable.mcvitie, R.drawable.ferrero, R.drawable.maltesers,
                R.drawable.lindt, R.drawable.borggreve};

        //初始化各变量
        mRcyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        //设置recyclerView的布局格式
        mRcyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems=new ArrayList<>();
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatBtm);
        flag=true;
        mListView=(ListView)findViewById(R.id.shop_cart);
        data=new ArrayList<>();
        shopItems=new ArrayList<>();
        //初始化商品信息
        for(int i=0;i<10;i++){
            data.add(new goods(i,Initials[i],Name[i],Price[i],Info[i],ImageId[i]));
        }
        //把所有商品放入list中
        for(int i=0;i<10;i++) {
            Map<String,Object> tmp=new LinkedHashMap<>();
            tmp.put("name",Name[i]);
            tmp.put("initial",Initials[i]);
            tmp.put("index",i);//商品在data中的下标
            listItems.add(tmp);
        }
        //从xml和list初始化commonAdapter
        commonAdapter=new CommonAdapter<Map<String,Object>>(this,R.layout.good_list,listItems) {
            @Override
            protected void convert(myViewHolder holder ,Map<String,Object> o) {
                TextView name=holder.getView(R.id.name);
                name.setText(o.get("name").toString());
                TextView initial=holder.getView(R.id.initial);
                initial.setText(o.get("initial").toString());
            }
        };
        //将RecyclerView的adapter设置为commonAdapter
        mRcyclerView.setAdapter(commonAdapter);
        //点击商品
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
             //单击，跳转到详情界面
             @Override
             public void onClick(int position) {
                 Intent intent = new Intent(MainActivity.this, Detail.class);
                 Bundle bundle = new Bundle();
                 int i = Integer.parseInt(listItems.get(position).get("index").toString());
                 bundle.putSerializable("goods", data.get(i));
                 intent.putExtras(bundle);
                 startActivityForResult(intent, 1);
             }
             //长按删除
             @Override
             public void onLongClick(int position)
             {
                 commonAdapter.Remove(position);
                 Toast.makeText(getApplicationContext(),"移除第"+position+"个商品",Toast.LENGTH_SHORT).show();
             }
         });

        //初始化购物车
        Map<String,Object> tem=new LinkedHashMap<>();
        tem.put("name","购物车");
        tem.put("initial","*");
        tem.put("price","价格");
        shopItems.add(tem);
        simpleAdapter=new SimpleAdapter(this,shopItems,R.layout.good_list,
                new String[]{"name","initial","price"},new int[]{R.id.name,R.id.initial,R.id.price});
        mListView.setAdapter(simpleAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    Intent intent1=new Intent(MainActivity.this,Detail.class);
                    Bundle bundle=new Bundle();
                    int position=Integer.parseInt(shopItems.get(i).get("index").toString());
                    bundle.putSerializable("goods",data.get(position));
                    intent1.putExtras(bundle);
                    startActivityForResult(intent1,1);
                }
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i>0) {
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
                    vib.vibrate(10);
                    AlertDialog.Builder alterDialog = new AlertDialog.Builder(MainActivity.this);
                    alterDialog.setTitle("移除商品")
                            .setMessage("从购物车移除" + shopItems.get(i).get("name").toString() + "?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int s) {
                                    shopItems.remove(i);
                                    simpleAdapter.notifyDataSetChanged();
                                }
                            })
                            .create()
                            .show();
                }
                return true;
            }
        });

        //悬浮按钮切换
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(flag){
                    mListView.setVisibility(View.VISIBLE);
                    mRcyclerView.setVisibility(View.INVISIBLE);
                    floatingActionButton.setImageResource(R.drawable.mainpage);
                    flag=false;
                }
                else {
                    mRcyclerView.setVisibility(View.VISIBLE);
                    mListView.setVisibility(View.INVISIBLE);
                    floatingActionButton.setImageResource(R.drawable.shoplist);
                    flag=true;
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent intent){
        if(requestCode==1){
            if(resultCode==1){
//                goods g=(goods)getIntent().getExtras().get("goods");
                Bundle extras=intent.getExtras();
                if(extras!=null){
                    goods g=(goods)extras.get("goods");
                    if(g.isCart()==true) {//如果标记为已添加
                        Map<String, Object> tem = new LinkedHashMap<>();
                        tem.put("name", g.getName());
                        tem.put("initial", g.getInitials());
                        tem.put("price", g.getPrice());
                        tem.put("index", g.getId());
                        shopItems.add(tem);//添加到购物车
                        simpleAdapter.notifyDataSetChanged();
                    }
                    data.set(g.getId(),g);//更新商品信息，因为可能被收藏或取消收藏
                }
            }
        }
   }

}

