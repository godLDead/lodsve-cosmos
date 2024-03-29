package message.base.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2015-1-6 20:57
 */
public class EncryptUtils {
    private static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 用MD5算法进行加密
     *
     * @param plainText 明文，需要加密的字符串
     * @return MD5加密后的结果
     */
    public static String encodeMD5(String plainText) {
        return encode(plainText, EncryptType.MD5);
    }

    /**
     * 用SHA算法进行加密
     *
     * @param plainText 明文，需要加密的字符串
     * @return SHA加密后的结果
     */
    public static String encodeSHA(String plainText) {
        return encode(plainText, EncryptType.SHA);
    }

    /**
     * 用base64算法进行加密
     *
     * @param plainText 明文，需要加密的字符串
     * @return base64加密后的结果
     */
    public static String encodeBase64(String plainText) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(plainText.getBytes());
    }

    /**
     * 用base64算法进行解密
     *
     * @param cipherText 密文，需要解密的字符串
     * @return base64解密后的结果
     * @throws IOException
     */
    public static String decodeBase64(String cipherText) throws IOException {
        BASE64Decoder encoder = new BASE64Decoder();
        return new String(encoder.decodeBuffer(cipherText));
    }

    /**
     * 根据类型加密
     *
     * @param plainText   明文
     * @param encryptType 加密类型
     * @return
     */
    private static String encode(String plainText, EncryptType encryptType) {
        String dstr = null;
        try {
            MessageDigest md = MessageDigest.getInstance(encryptType.name());
            md.update(plainText.getBytes());
            dstr = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return dstr;
    }

    /**
     * 加密类型
     */
    private enum EncryptType {
        MD5, SHA
    }

    /**
     * 获取文件MD5值
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) {
        try {
            return getFileMD5String(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return "";
        }
    }

    /**
     * 获取文件MD5值
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(FileInputStream in) {
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");

            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, in.available());
            messagedigest.update(byteBuffer);

            return bufferToHex(messagedigest.digest());
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
