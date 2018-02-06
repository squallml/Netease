package cn.molong.www.netease.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * Created by 胡锦龙_Squall on 2018/1/23.
 */

public class ImageUtil {
    /**
     * 检测图片是否存在
     * @param imageName 图片地址
     * @return true存在
     */
    public static boolean checkImageIsDownload(String imageName){
        File image = getFileByName(imageName);
        if (image.exists()){
            Bitmap bitmap = getImageBitMapByFile(image);
            if (bitmap != null) {
                return true;
            }
        }

        return false;
    }

    /**
     * 拼接图片地址
     * @param imageName 图片名
     * @return 图片地址
     */
    public static File getFileByName(String imageName){
        File SD = Environment.getExternalStorageDirectory();
        File cacheFile = new File(SD, Constant.CHCHE);
        File image = new File(cacheFile, imageName + ".jpg");
        return image;
    }

    /**
     * 取得图片
     * @param image 图片地址
     * @return Bitmap
     */
    public static Bitmap getImageBitMapByFile(File image){
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
        return bitmap;
    }
}
