package org.micro.commons.basic.utils;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GzipUtil {

    private static final Logger logger = LoggerFactory.getLogger(GzipUtil.class);

    /**
     * 先压缩为tmp文件，成功后再rename为最终文件，防止定时ls的时候处理未压缩完成的文件
     *
     * @param srcFile srcFile
     * @param gzFile  gzFile
     * @throws IOException 异常
     */
    public static void gzip(File srcFile, File gzFile, boolean deleteSrcFile) throws IOException {
        String tmpFileName = gzFile.getAbsolutePath() + ".tmp";
        FileInputStream fileInStream = null;
        GZIPOutputStream gzOutStream = null;
        FileOutputStream fileOutStream = new FileOutputStream(tmpFileName);
        logger.info("prepare for compressing file: {}", srcFile.getName());
        long startTime = System.currentTimeMillis();
        try {
            fileInStream = new FileInputStream(srcFile);
            gzOutStream = new GZIPOutputStream(fileOutStream);
            int length;
            byte[] data = new byte[1024];
            while ((length = fileInStream.read(data)) != -1) {
                gzOutStream.write(data, 0, length);
            }
            gzOutStream.close();
            fileOutStream.close();
            fileInStream.close();
            if (deleteSrcFile) {
                FileUtils.forceDelete(srcFile);
            }
            File tmpFile = new File(tmpFileName);
            tmpFile.renameTo(gzFile);
            long spendTime = System.currentTimeMillis() - startTime;
            logger.info("finish compress file:{} took(ms):{}", srcFile.getName(), spendTime);
        } finally {
            try {
                fileOutStream.close();
            } catch (IOException e) {
                logger.error("IOException", e);
            }
            try {
                if (gzOutStream != null) {
                    gzOutStream.close();
                }
            } catch (IOException e) {
                logger.error("IOException", e);
            }
            try {
                if (fileInStream != null) {
                    fileInStream.close();
                }
            } catch (IOException e) {
                logger.error("IOException", e);
            }
        }
    }

}
