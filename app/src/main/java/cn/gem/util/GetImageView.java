package cn.gem.util;

import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 再次访问服务器获取图片
 * Created by sony on 2016/10/6.
 */
public class GetImageView {
    String position;
    ImageView imageView;

    public GetImageView(String position,ImageView imageView) {
        this.position=position;
        this.imageView=imageView;
    }

    public void getImage(String position,ImageView imageView){
        String photoUrl=IpChangeAddress.ipChangeAddress+position;
        ImageOptions imageOptions=new ImageOptions.Builder()
                .setCircular(true)
                .setCrop(true).setSize(65,65).build();

        x.image().bind(imageView,photoUrl,imageOptions);
    }
}
