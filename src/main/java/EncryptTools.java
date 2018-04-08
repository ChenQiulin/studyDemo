

import org.apache.commons.codec.binary.Hex;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptTools {
    //AES加密
    public static String encrypt(String text, String secKey) throws Exception {
        byte[] raw = secKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }
  //字符填充
  public static String zfill(String result, int n) {
        if (result.length() >= n) {
            result = result.substring(result.length() - n, result.length());
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = n; i > result.length(); i--) {
                stringBuilder.append("0");
            }
            stringBuilder.append(result);
            result = stringBuilder.toString();
        }
        return result;
    }

    public static String md5(String pwd) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] bs = digest.digest(pwd.getBytes());
            String hexString = "";
            for (byte b : bs) {
                int temp = b & 255;
                if (temp < 16 && temp >= 0) {
                    hexString = hexString + "0" + Integer.toHexString(temp);
                } else {
                    hexString = hexString + Integer.toHexString(temp);
                }
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void loginAPI(String username, String password) throws Exception {
        password = EncryptTools.md5(password);
        // 私钥，随机16位字符串（自己可改）
        String secKey = "cd859f54539b24b7";
        String text = "{\"username\": \"" + username + "\", \"rememberLogin\": \"true\", \"password\": \"" + password
                + "\"}";
        String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        String nonce = "0CoJUm6Qyw8W8jud";
        String pubKey = "010001";
        // 2次AES加密，得到params
        String params = EncryptTools.encrypt(EncryptTools.encrypt(text, nonce), secKey);
        StringBuffer stringBuffer = new StringBuffer(secKey);
        // 逆置私钥
        secKey = stringBuffer.reverse().toString();
        String hex = Hex.encodeHexString(secKey.getBytes());
        BigInteger bigInteger1 = new BigInteger(hex, 16);
        BigInteger bigInteger2 = new BigInteger(pubKey, 16);
        BigInteger bigInteger3 = new BigInteger(modulus, 16);
        // RSA加密计算
        BigInteger bigInteger4 = bigInteger1.pow(bigInteger2.intValue()).remainder(bigInteger3);
        String encSecKey = Hex.encodeHexString(bigInteger4.toByteArray());
        // 字符填充
        encSecKey = EncryptTools.zfill(encSecKey, 256);
        // 登录请求
        Document document = Jsoup.connect("http://music.163.com/weapi/login/").cookie("appver", "1.5.0.75771")
                .header("Referer", "http://music.163.com/").data("params", params).data("encSecKey", encSecKey)
                .ignoreContentType(true).post();
        System.out.println("登录结果：" + document.text());
    }

    public static void main(String[] args)throws Exception {
        commentAPI();
    }
    public static  void commentAPI() throws Exception {
        //私钥，随机16位字符串（自己可改）
        String secKey = "cd859f54539b24b7";
//        String text = "{\"username\": \"\", \"rememberLogin\": \"true\", \"password\": \"\"}";
//        String text = "{rid:\"\", offset:\"0\", total:\"true\", csrf_token:\"\"}"; ,
        String text = "{rid:\"\", s: \"短发\", csrf_token:\"\"}";
        String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";
        String nonce = "0CoJUm6Qyw8W8jud";
        String pubKey = "010001";
        //2次AES加密，得到params
        String params = EncryptTools.encrypt(EncryptTools.encrypt(text, nonce), secKey);
        StringBuffer stringBuffer = new StringBuffer(secKey);
        //逆置私钥
        secKey = stringBuffer.reverse().toString();
        String hex = Hex.encodeHexString(secKey.getBytes());
        BigInteger bigInteger1 = new BigInteger(hex, 16);
        BigInteger bigInteger2 = new BigInteger(pubKey, 16);
        BigInteger bigInteger3 = new BigInteger(modulus, 16);
        //RSA加密计算
        BigInteger bigInteger4 = bigInteger1.pow(bigInteger2.intValue()).remainder(bigInteger3);
        String encSecKey= Hex.encodeHexString(bigInteger4.toByteArray());
        //字符填充
        encSecKey=EncryptTools.zfill(encSecKey, 256);// "257348aecb5e556c066de214e531faadd1c55d814f9be95fd06d6bff9f4c7a41f831f6394d5a3fd2e3881736d94a02ca919d952872e7d0a50ebfa1769a7a62d512f5f1ca21aec60bc3819a9c3ffca5eca9a0dba6d6f7249b06f5965ecfff3695b54e1c28f3f624750ed39e7de08fc8493242e26dbc4484a01c76f739e135637c";//
        //评论获取
        params="smyjTedeZCPVdQ0luycRyDU42QvO6l160K7BaQDHZH3GldRyK4pCaEh5mHJ4h4XFre3xCbvhZfYBBq6ojhv7TFTbWl+gkX2JeF7TRUW3rXkeewcd+pgwXLJBb8vyZOZwIzb2efkODuLVF59YpYJLrgUfUo754tNEDC67OwovMiSYEdyJFDJqSvLYdpmu9dkksala2axECS8XNuCseNiAH0ZqAfsLeRCY10DOGOsuUYAkKS5ShPCEPs48thV0Mb93JUz7UyhxYyLfDTZz7hjTXp8JE67YkILWlzRFqMHgS5U=";
        encSecKey="9363cd87fe5177437dd14a62afe686fbb8e6d8200616be87027b1e0271fd6574d6584b18579c83a5659c0b4fe396e1fd36d93110acf6f01ef0fd29fa29d09ff391dd940926200777b585f0d539ad2d0895019f4b5f352b4248e555a23d2db16f41c3732851c7ab68fca31e130d554a6ff0c21af0c5adbe5cde63628839ed839f";

        Document document = Jsoup.connect("http://music.163.com/weapi/cloudsearch/get/web?csrf_token=").cookie("appver", "1.5.0.75771")
                .header("Referer", "http://music.163.com/").data("params", params).data("encSecKey", encSecKey)
                .ignoreContentType(true).post();

        //System.out.println(document.text());
        ArrayList<String> s= getBook( document.text());

        for(int i=0;i<s.size();i++){
            String []arr = s.get(i).split("\"") ;
            System.out.println(arr[2]);
        }

//        System.out.println("评论：" +);
    }

    public static ArrayList getBook(String read){
        ArrayList<String> arrayList = new ArrayList<String>() ;

        String con = "content(.*?)\"}" ;
        Pattern ah = Pattern.compile(con);
        Matcher mr = ah.matcher(read);
        while(mr.find()) {
            if (!arrayList.contains(mr.group())) {
                arrayList.add(mr.group());
            }
        }
        return  arrayList ;
    }

}
