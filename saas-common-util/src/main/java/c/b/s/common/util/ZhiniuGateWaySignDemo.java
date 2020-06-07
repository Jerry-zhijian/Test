package c.b.s.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by guiqingqing on 2019/6/26.
 */
public class ZhiniuGateWaySignDemo {
    public static void main(String[] args) {
        sign();
    }

    private static void sign() {
        String X_GW_APPID = "10001";
        String X_GW_APPSECRETKEY = "0ztdaRUAX2tudO26UdPsskm6a6MsDAc2";
        String X_GW_NONCESTR = "123456";
        String X_GW_TIMESTAMP = String.valueOf(DateUtil.newDate().getTime());

        String[] signKeys = new String[] {"X-GW-APPID", "X-GW-APPSECRETKEY", "X-GW-NONCESTR", "X-GW-TIMESTAMP"};
        Arrays.sort(signKeys);
        Map<String, String> map = new HashMap();
        map.put("X-GW-APPID", X_GW_APPID);
        map.put("X-GW-APPSECRETKEY", X_GW_APPSECRETKEY);
        map.put("X-GW-NONCESTR", X_GW_NONCESTR);
        map.put("X-GW-TIMESTAMP", X_GW_TIMESTAMP);

        StringBuilder sb = new StringBuilder();
        for (String k : signKeys) {
            String v = map.get(k);
            sb.append(k).append("=").append(v).append("&");
        }
        String originSign = sb.toString().toLowerCase();
        if (originSign.endsWith("&")) {
            originSign = originSign.substring(0, originSign.length() - 1);
        }
        String signature = DigestUtils.md5Hex(originSign);

        System.out.println("X-GW-APPID: " + X_GW_APPID);
        System.out.println("X-GW-APPSECRETKEY: " + X_GW_APPSECRETKEY);
        System.out.println("X-GW-NONCESTR: " + X_GW_NONCESTR);
        System.out.println("X-GW-TIMESTAMP: " + X_GW_TIMESTAMP);
        System.out.println("X-GW-SIGN: " + signature);
    }
}