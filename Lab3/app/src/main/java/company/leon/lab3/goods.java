package company.leon.lab3;

import java.io.Serializable;

/**
 * Created by Leon on 2017/10/25.
 */

public class goods implements Serializable{
    private int id;//商品编号
    private String initials;//商品的名字的首字母
    private String name;//商品名字
    private String price;//商品价格
    private String info;//商品的其他信息
    private int imageid;//商品对应的图片id
    private boolean star;//标记是否被收藏
    private boolean cart;//是否添加到购物车
    public goods(int id,String initials,String name,String price,String info,int imageid)
    {
        this.id=id;
        this.initials=initials;
        this.name=name;
        this.price=price;
        this.info=info;
        this.imageid=imageid;
        star=false;
        cart=false;
    }

    public int getId() {
        return id;
    }

    public String getInitials() {
        return initials;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getInfo() {
        return info;
    }

    public int getImageid() {
        return imageid;
    }

    public boolean isCart() {
        return cart;
    }

    public boolean isStar() {
        return star;
    }

    public void setCart(boolean cart) {
        this.cart = cart;
    }

    public void setStar(boolean star) {
        this.star = star;
    }
}
