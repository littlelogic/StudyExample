package test.algorithms.ListNode;


import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


/**
 * @类功能说明: java拼接多张图片，生成的格式是jpg、bmp、gif等，如果其他格式则报错或者photoshop或者画图工具打不开
 * @修改人员名: yang
 * @修改日期： 2016-3-10 上午10:35:04
 * @创建时间： 2016-3-10 上午10:35:04
 * -------------------------------------------------------------------------------------------
 * 修改次数         修改人员    修改内容                       修改原因                     
 *   
 * @备注：只能拼接同类型的图片（不能连续拼接）、宽度需要一致（高度可以不限制）
 * @版本： V1.0
 */
public class JoinTwoImage {

    //测试类
    public static void main(String[] args) {
        //输入图片地址
        String[] imgs = {"F:/4.jpg", "F:/5.jpg", "F:/6.jpg"};
        //调用方法生成图片
        JoinTwoImage.merge(imgs, "jpg", "F:/test2.jpg");
    }

    /**
     * Java拼接多张图片
     *
     * @param imgs
     * @param type
     * @param dst_pic
     * @return
     */
    public static boolean merge(String[] imgs, String type, String dst_pic) {
        //获取需要拼接的图片长度
        int len = imgs.length;
        //判断长度是否大于0
        if (len < 1) {
            return false;
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(imgs[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            // 从图片中读取RGB 像素
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }

        int dst_height = 0;
        int dst_width = images[0].getWidth();
        //合成图片像素
        for (int i = 0; i < images.length; i++) {
            dst_width = dst_width > images[i].getWidth() ? dst_width : images[i].getWidth();
            dst_height += images[i].getHeight();
        }
        //合成后的图片
        System.out.println("宽度:" + dst_width);
        System.out.println("高度:" + dst_height);
        if (dst_height < 1) {
            System.out.println("dst_height < 1");
            return false;
        }
        // 生成新图片   
        try {
            // dst_width = images[0].getWidth();   
            BufferedImage ImageNew = new BufferedImage(dst_width, dst_height,
                    BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            for (int i = 0; i < images.length; i++) {
                ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(),
                        ImageArrays[i], 0, dst_width);
                height_i += images[i].getHeight();
            }

            File outFile = new File(dst_pic);
            ImageIO.write(ImageNew, type, outFile);// 写图片 ，输出到硬盘
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}