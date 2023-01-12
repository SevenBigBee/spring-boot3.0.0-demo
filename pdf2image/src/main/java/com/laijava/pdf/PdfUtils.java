package com.laijava.pdf;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author Lai
 * @date 2019年9月19日 下午1:33:22
 * @Description PDF 工具类
 */
public class PdfUtils {
    protected final static Logger logger = LoggerFactory.getLogger(PdfUtils.class);



    /**
     * @param is      pdf文件流
     * @param outpath 输出路径
     * @return File
     * @throws IOException
     * @throws InvalidPasswordException
     * @author Lai
     * @date 2019年9月19日 下午1:34:46
     * @description
     */
    public static File pdf2multiImage(InputStream is, String outpath) throws InvalidPasswordException, IOException {
        PDDocument pdf = null;
        try {
            pdf = PDDocument.load(is);
            int actSize = pdf.getNumberOfPages();
            List<BufferedImage> piclist = new ArrayList<BufferedImage>();
            int maxWidth = 0;
            int maxHeight = 0;

            for (int i = 0; i < actSize; i++) {
                BufferedImage image = new PDFRenderer(pdf).renderImageWithDPI(i, 150, ImageType.RGB);
                piclist.add(image);
                maxWidth = maxWidth > image.getWidth() ? maxWidth : image.getWidth();
                maxHeight = maxHeight > image.getHeight() ? maxHeight : image.getHeight();
            }
            /** 调整所有图片宽度统一，以最大图片宽度和其高度统一缩放 **/
            List<BufferedImage> resizeImage = resizeImage(piclist, maxWidth, maxHeight);
            File yPic = yPic(resizeImage, outpath);
            return yPic;
        } finally {
            if (null != is) is.close();
            if (null != pdf) {
                pdf.close();
            }
        }

    }

    /**
     * @param piclist
     * @param maxWidth
     * @param maxHeight
     * @author Lai
     * @date 2020年4月15日 上午9:00:24
     * @description
     */
    private static List<BufferedImage> resizeImage(List<BufferedImage> piclist, int maxWidth, int maxHeight) {
        List<BufferedImage> resizePiclist = new ArrayList<BufferedImage>();
        for (BufferedImage pic : piclist) {
            if (pic.getWidth() != maxWidth) {
                BufferedImage resizePic = resizeBufferedImage(pic, maxWidth, maxHeight, false);
                resizePiclist.add(resizePic);
            } else {
                resizePiclist.add(pic);
            }
        }
        return resizePiclist;
    }

    /**
     * 将宽度相同的图片，竖向追加在一起 ##注意：宽度必须相同
     *
     * @param piclist
     * @param outPath
     * @return
     * @throws IOException
     * @author Lai
     * @date 2019年9月19日 下午1:36:25
     * @description
     */
    private static File yPic(List<BufferedImage> piclist, String outPath) throws IOException {// 纵向处理图片
        if (piclist == null || piclist.size() <= 0) {
            return null;
        }
        int height = 0, // 总高度
                width = 0, // 总宽度
                _height = 0, // 临时的高度 , 或保存偏移高度
                __height = 0, // 临时的高度，主要保存每个高度
                picNum = piclist.size();// 图片的数量
        int[] heightArray = new int[picNum]; // 保存每个文件的高度
        BufferedImage buffer = null; // 保存图片流
        List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
        int[] _imgRGB; // 保存一张图片中的RGB数据
        for (int i = 0; i < picNum; i++) {
            buffer = piclist.get(i);
            heightArray[i] = _height = buffer.getHeight();// 图片高度
            if (i == 0) {
                width = buffer.getWidth();// 图片宽度
            }
            height += _height; // 获取总高度
            _imgRGB = new int[width * _height];// 从图片中读取RGB
            _imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
            imgRGB.add(_imgRGB);
        }
        _height = 0; // 设置偏移高度为0
        // 生成新图片
        BufferedImage imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < picNum; i++) {
            __height = heightArray[i];
            if (i != 0)
                _height += heightArray[i - 1]; // 计算偏移高度
            imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
        }
        File outFile = new File(outPath);
        if (!outFile.exists()) {
            // 先得到文件的上级目录，并创建上级目录，在创建文件
            outFile.getParentFile().mkdir();
            outFile.createNewFile();
        }
        ImageIO.write(imageResult, "jpg", outFile);// 写图片
        return outFile;
    }

    /**
     * 将流写入到file中
     *
     * @param ins
     * @param file
     * @throws IOException
     * @author Lai
     * @date 2019年9月19日 下午1:40:20
     * @description
     */
    public static void inputStreamToFile(InputStream ins, File file) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            if (null != os) os.close();
            if (null != ins) ins.close();
        }
    }

    /**
     * 调整bufferedimage大小
     *
     * @param source  BufferedImage 原始image
     * @param targetW int  目标宽
     * @param targetH int  目标高
     * @param flag    boolean 是否同比例调整
     * @return BufferedImage  返回新image
     */
    private static BufferedImage resizeBufferedImage(BufferedImage source, int targetW, int targetH, boolean flag) {
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        if (flag && sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else if (flag && sx <= sy) {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    /**
     *
     * @param is 文件流
     * @param outpath 输出路径
     * @param fileName 文件名称
     * @param pageFlag 是否分页 “0”不分页，“1”分页，压缩到zip
     * @param dpi  值越大越清晰
     * @return
     * @throws IOException
     */
    public static File pdf2multiImage(InputStream is, String outpath, String fileName, String pageFlag, Integer dpi) throws IOException {
        PDDocument pdf = null;
        try {
            pdf = PDDocument.load(is);
            int actSize = pdf.getNumberOfPages();
            if (actSize > 30) {
                throw new RuntimeException("pdf页数不能超过20页！");
            }
            List<BufferedImage> piclist = new ArrayList<BufferedImage>();
            int maxWidth = 0;
            int maxHeight = 0;

            for (int i = 0; i < actSize; i++) {
                BufferedImage image = new PDFRenderer(pdf).renderImageWithDPI(i, dpi, ImageType.RGB);
                piclist.add(image);
                maxWidth = maxWidth > image.getWidth() ? maxWidth : image.getWidth();
                maxHeight = maxHeight > image.getHeight() ? maxHeight : image.getHeight();
            }
            /** 是否拆分图片**/
            if ("1".equals(pageFlag)) {
                doCompress(outpath, fileName, piclist);
                return null;
            } else {
                /** 调整所有图片宽度统一，以最大图片宽度和其高度统一缩放 **/
                List<BufferedImage> resizeImage = resizeImage(piclist, maxWidth, maxHeight);
                File yPic = yPic(resizeImage, outpath + File.separator + fileName + ".jpg");
                return yPic;
            }

        } finally {
            if (null != is) is.close();
            if (null != pdf) {
                pdf.close();
            }
        }
    }

    private static void doCompress(String outpath, String fileName, List<BufferedImage> piclist) throws IOException {
        int BUFFER_SIZE = 4096;
        InputStream input = null;
        ZipOutputStream zos = null;
        zos = new ZipOutputStream(new FileOutputStream(new File(outpath + File.separator + fileName + ".zip")));
        try {
            for (int i = 0; i < piclist.size(); i++) {
                BufferedImage image = piclist.get(i);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                input = new ByteArrayInputStream(baos.toByteArray());
                byte[] buffer = new byte[BUFFER_SIZE];//缓冲器
                int readLength;
                zos.putNextEntry(new ZipEntry(fileName + i + ".png"));
                while ((readLength = input.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    zos.write(buffer, 0, readLength);
                }
                zos.flush();
                input.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (null != zos) {
                zos.close();
            }
            if (null != input) {
                input.close();
            }
        }

    }
}
