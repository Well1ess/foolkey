package com.example.resource.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * 图像bitmap转字节数据
 */
public class ImageService {

    public static final int THRESHOLD = 200;//压缩的阈值，200kb
    public static final int ICON = 100;//缩略图大小，100kb

    //icon缩略图
    //image压缩图或者原图
    public enum ImageKind {
        ICON, IMAGE
    }

    /**
     * 图像转字节
     *
     * @param bm
     * @return
     */
    public static Bitmap getCompress(Bitmap bm, ImageKind imageKind) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //一般Png都比Jpeg大
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 90;//压缩率
        int threshold = imageKind == ImageKind.ICON ? ICON : THRESHOLD;

        //循环压缩
        while (baos.toByteArray().length / 1024 > threshold) {
            //清空数据
            baos.reset();
            //不用外部算法压缩，直接调用系统Api
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
            //快速压缩，每次都将压缩率提高10
            options -= 10;
        }

        //将结果保存
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(bais, null, null);
        return bitmap;
    }
}