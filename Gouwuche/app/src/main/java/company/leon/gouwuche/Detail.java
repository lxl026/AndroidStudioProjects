package company.leon.gouwuche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Detail extends AppCompatActivity {

    TextView goods_name;
    TextView goods_price;
    TextView goods_info;
    ImageView goods_pic;
    ImageView back;
    ImageView star;//收藏星标
    ImageView cart;//购物车图标
    ListView bottom;//底部操作
    goods g;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        goods_name =(TextView)findViewById(R.id.goods_name);
        goods_price=(TextView)findViewById(R.id.goods_price);
        goods_info=(TextView)findViewById(R.id.goods_info);
        goods_pic=(ImageView)findViewById(R.id.goods_pic);
        back=(ImageView)findViewById(R.id.back);
        star=(ImageView)findViewById(R.id.star);//收藏星标
        cart=(ImageView)findViewById(R.id.cart);//购物车图标
        bottom=(ListView)findViewById(R.id.listView);//底部操作
        g=(goods)getIntent().getExtras().get("goods");
        if(g!=null)
        {
            goods_name.setText(g.getName());
            goods_price.setText(g.getPrice());
            goods_info.setText(g.getInfo());
            goods_pic.setImageResource(g.getImageid());
            if(g.isCart()) star.setImageResource(R.drawable.full_star);
            else star.setImageResource(R.drawable.empty_star);
        }
        //添加底部操作
        List<Map<String,Object>>list=new ArrayList<>();
        String[] options=new String[]{"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
        for(int i=0;i<4;i++)
        {
            Map<String,Object>tmp=new LinkedHashMap<>();
            tmp.put("option",options[i]);
            list.add(tmp);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.option_item,new String[]{"option"},new int[]{R.id.option_item});
        bottom.setAdapter(simpleAdapter);

        //点击收藏
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(g.isStar())
                {
                    g.setStar(false);
                    star.setImageResource(R.drawable.empty_star);
                }
                else
                {
                    g.setStar(true);
                    star.setImageResource(R.drawable.full_star);
                }
            }
        });

        //添加到购物车
        cart.setClickable(true);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g.setCart(true);
                Toast.makeText(getApplicationContext(),"商品已加到购物车",Toast.LENGTH_SHORT).show();

            }
        });

        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Detail.this,MainActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("goods", g);
                intent.putExtra("goods", g);
                setResult(1,intent);
                finish();
            }
        });
    }
}