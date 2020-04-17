package com.mcoding.common.util.wechat;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;
import java.util.Arrays;


/**
 * 微信工具类
 */
public class WXBizDataCrypt {

    private static final Charset CHARSET = Charset.forName("utf-8");
    private static final int BLOCK_SIZE = 32;

    /**
     * 获得对明文进行补位填充的字节.
     *
     * @param count
     *            需要进行填充补位操作的明文字节个数
     * @return 补齐用的字节数组
     */
    public static byte[] encode(int count) {
        // 计算需要填充的位数
        int amountToPad = BLOCK_SIZE - (count % BLOCK_SIZE);
        if (amountToPad == 0) {
            amountToPad = BLOCK_SIZE;
        }
        // 获得补位所用的字符
        char padChr = chr(amountToPad);
        String tmp = new String();
        for (int index = 0; index < amountToPad; index++) {
            tmp += padChr;
        }
        return tmp.getBytes(CHARSET);
    }

    /**
     * 删除解密后明文的补位字符
     *
     * @param decrypted
     *            解密后的明文
     * @return 删除补位字符后的明文
     */
    public static byte[] decode(byte[] decrypted) {
        int pad = decrypted[decrypted.length - 1];
        if (pad < 1 || pad > 32) {
            pad = 0;
        }
        return Arrays.copyOfRange(decrypted, 0, decrypted.length - pad);
    }

    /**
     * 将数字转化成ASCII码对应的字符，用于对明文进行补码
     *
     * @param a
     *            需要转化的数字
     * @return 转化得到的字符
     */
    public static char chr(int a) {
        byte target = (byte) (a & 0xFF);
        return (char) target;
    }

    /**
     * 解密数据
     * @return
     * @throws Exception
     */
    public static WxUserInfo decrypt(String encryptedData, String sessionKey, String iv){
        WxUserInfo wxUserInfo = null;
        try {
            AES aes = new AES();
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if(null != resultByte && resultByte.length > 0){
                String result = new String(WXBizDataCrypt.decode(resultByte));
                if (StringUtils.isNotEmpty(result)) {
                    wxUserInfo = JSON.parseObject(result, WxUserInfo.class);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wxUserInfo;
    }

    public static void main(String[] args) throws Exception{

        String encryptedData = "0WHUPuR3sVIcbaoAoiNokZba0CKZ0hVMtCu3w78z5CEPSsxOrbZuKtLH21S7hbjpBt49pe40VVQItSNaGup63yjsMtcQ1qyb6tJ5lHxfmge3ZwhHzRwgG1hjNWyv65R5LP7+F+mxC7XNmBQwIyqnSZuo6EfuBmC5QTW15ra3XOz1UR34SB1T64hr+WGgjGW9wg1hZ+LzrOguEP9tDAZonA==";
        String sessionKey = "MtFa+itqjHz6sDkONN29fQ==";
        String iv = "0sX3rQXKX8HevgCi9HSqaw==";

        WxUserInfo wxUserInfo = WXBizDataCrypt.decrypt(encryptedData, sessionKey, iv);

        System.out.println(wxUserInfo);
    }

}