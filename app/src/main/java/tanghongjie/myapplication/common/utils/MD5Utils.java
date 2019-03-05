package tanghongjie.myapplication.common.utils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 创建时间:2018/02/02 11:59
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述:获取md5相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class MD5Utils {
	
	/**  
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合  
     */   
    protected   static   char[]  hexDigits = {  '0' ,  '1' ,  '2' ,  '3' ,  '4' ,  '5' ,  '6' ,
            '7' ,  '8' ,  '9' ,  'a' ,  'b' ,  'c' ,  'd' ,  'e' ,  'f'  };  
  
    private static MessageDigest messagedigest =  null ;

    static  {  
        try  {  
            messagedigest = MessageDigest.getInstance("MD5" );
        } catch  (NoSuchAlgorithmException e) {
            System.err.println(MD5Utils.class .getName() + "初始化失败，MessageDigest不支持MD5Util。" );
            e.printStackTrace();
        }  
    } 

    /**
     * 字符串md5加密
     * @param psw 要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String getMd5String(String psw) {
        StringBuilder buffer = new StringBuilder();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(psw.getBytes());
            for (byte b : result) {
                // 0xff是十六进制，十进制为255
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }


    /**
     * 获取输入流的md5码
     * @param in
     * @return
     */
    public static String encode(InputStream in) {
        try {
            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[8192];
            int byteCount;
            while ((byteCount = in.read(bytes)) > 0) {
                digester.update(bytes, 0, byteCount);
            }
            byte[] digest = digester.digest();

            // byte -128 ---- 127
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                int a = b & 0xff;

                String hex = Integer.toHexString(a);

                if (hex.length() == 1) {
                    hex = 0 + hex;
                }

                sb.append(hex);
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }
        return null;
    }

    /**
     * 获取文件的md5
     * @param file
     * @return
     */
    public static String getFileMd5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte[] buffer = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close(in);
        }
        return bytesToHexString(digest.digest());
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 关闭流
     */
    public static boolean close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
