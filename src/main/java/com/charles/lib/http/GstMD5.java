package com.charles.lib.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

public class GstMD5 {
    private static final String URL_AUTHVERIFY = "http://open.snd02.com/oauth/authverify2.as";
    private static final String URL_TOKEN = "http://open.snd02.com/oauth/token.as";
    private static final String URL_ROUTER = "http://open.snd02.com/invoke/router.as";

    private static final String CLIENT_ID = "O000000005";
    private static final String APP_SECRET = "263E31F3732D6AF5B7522753882A763";
    private static final String REDIRECT_URI = "http://open.snd02.com/demo.jsp";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String UNAME = "qngy02";
    private static final String PASSWD = "qngy888888";
    private static final String PROJECT_CODE = "P00000000009";
    private static final String TIMESTAMP = "20170504160410";

    private static final String METHOD_GET_BOXES = "GET_BOXES";
    private static final String METHOD_GET_BOX_CHANNELS_REALTIME = "GET_BOX_CHANNELS_REALTIME";
    private static final String METHOD_GET_BOX_ALARM = "GET_BOX_ALARM";
    private static final String METHOD_GET_BOX_DAY_POWER = "GET_BOX_DAY_POWER";

    private static final char[] hc = "0123456789abcdef".toCharArray();

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InterruptedException {
//        System.out.println(clientSecret());
//        System.out.println(eboxesSign(METHOD_GET_BOXES));
//        System.out.println(eboxesSign(METHOD_GET_BOX_CHANNELS_REALTIME));

        JSONObject codeJson = post(URL_AUTHVERIFY, getAuthverifyCodeParams());
        String code = codeJson.getString("code");
        JSONObject tokenJson = post(URL_TOKEN, getAccessTokenParams(code));
        String accessToken = tokenJson.getJSONObject("data").getString("accessToken");
        System.out.println(accessToken);

        List<NameValuePair> eboxesParams = getCommonParams(METHOD_GET_BOXES, accessToken);
        eboxesParams.add(new BasicNameValuePair("projectCode", PROJECT_CODE));
        eboxesParams.add(new BasicNameValuePair("sign", getSign(eboxesParams)));
        JSONObject eboxesJson = post(URL_ROUTER, eboxesParams);
        System.out.println(eboxesJson);
        JSONArray jsonArray = eboxesJson.getJSONArray("data");
        System.out.println("****************");
        String mac = "D4BF7F4C36A0";
        //eboxAlarmInfo(accessToken, mac);
        System.out.println("****************");
        //eboxRealtime(accessToken, mac);
        System.out.println("****************");
        eboxDayPower(accessToken, mac);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            System.out.println("MAC: " + jsonObject.getString("mac"));
            eboxDayPower(accessToken, jsonObject.getString("mac"));
        }
//        for (int i = 0; i < 20; i++) {
//            Thread.sleep(1000 * 10);
//            eboxRealtime(accessToken, "187ED530D1F0");
//        }
    }

    private static void eboxAlarmInfo(String accessToken, String mac) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<NameValuePair> alarmInfoParams = getCommonParams(METHOD_GET_BOX_ALARM, accessToken);
        alarmInfoParams.add(new BasicNameValuePair("projectCode", PROJECT_CODE));
        alarmInfoParams.add(new BasicNameValuePair("mac", mac));
        alarmInfoParams.add(new BasicNameValuePair("start", "2016-01-01 00:00"));
        alarmInfoParams.add(new BasicNameValuePair("end", "2017-05-20 23:59"));
        // alarmInfoParams.add(new BasicNameValuePair("page", "1"));
        // alarmInfoParams.add(new BasicNameValuePair("pageSize", "100"));
        alarmInfoParams.add(new BasicNameValuePair("sign", getSign(alarmInfoParams)));
        JSONObject alarmInfoJson = post(URL_ROUTER, alarmInfoParams);
        System.out.println(alarmInfoJson);
    }

    private static void eboxRealtime(String accessToken, String mac)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<NameValuePair> realtimeParams = getCommonParams(METHOD_GET_BOX_CHANNELS_REALTIME, accessToken);
        realtimeParams.add(new BasicNameValuePair("projectCode", PROJECT_CODE));
        realtimeParams.add(new BasicNameValuePair("mac", mac));
        realtimeParams.add(new BasicNameValuePair("sign", getSign(realtimeParams)));
        JSONObject realtimeJson = post(URL_ROUTER, realtimeParams);
        System.out.println(realtimeJson);
    }

    private static void eboxDayPower(String accessToken, String mac)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<NameValuePair> dayPowerParams = getCommonParams(METHOD_GET_BOX_DAY_POWER, accessToken);
        dayPowerParams.add(new BasicNameValuePair("projectCode", PROJECT_CODE));
        dayPowerParams.add(new BasicNameValuePair("mac", mac));
        dayPowerParams.add(new BasicNameValuePair("year", "2017"));
        dayPowerParams.add(new BasicNameValuePair("month", "06"));
        dayPowerParams.add(new BasicNameValuePair("day", "19"));
        dayPowerParams.add(new BasicNameValuePair("sign", getSign(dayPowerParams)));
        JSONObject dayPowerJson = post(URL_ROUTER, dayPowerParams);
        System.out.println(dayPowerJson);
    }

    public static String getSign(List<NameValuePair> params) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Collections.sort(params, Comparator.comparing(NameValuePair::getName));
        // System.out.println(params);
        List<String> paramList = params.stream().map(NameValuePair::getValue).collect(Collectors.toList());
        paramList.add(APP_SECRET);
        // System.out.println(String.format("paramList = %s", StringUtils.join(paramList.toArray())));
        // access_token_md5 + client_id + method_name + project_code + TIMESTAMP
        // return MD5.get(accessToken + CLIENT_ID + methodName + PROJECT_CODE + TIMESTAMP + APP_SECRET);
        return GstMD5.md5(StringUtils.join(paramList.toArray()));
    }

    public static String clientSecret(String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // app_key + grant_type + redirect_uri + code + app_secret
        return GstMD5.md5(CLIENT_ID + GRANT_TYPE + REDIRECT_URI + code + APP_SECRET);
    }

    public static String md5(String param) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset();
        md.update(param.getBytes("utf-8"));
        byte[] d = md.digest();
        StringBuilder r = new StringBuilder(d.length * 2);
        for (byte b : d) {
            r.append(hc[(b >> 4) & 0xF]);
            r.append(hc[(b & 0xF)]);
        }
        return r.toString();
    }

    public static JSONObject post(String requestUri, List<NameValuePair> params) {
        System.out.println(requestUri);
        System.out.println(params);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(requestUri);
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String jsonStr = EntityUtils.toString(entity);//取出应答字符串
                // System.out.println(jsonStr);
                // InputStream is = entity.getContent();
                // String result = convertStreamToString(is);
                if (StringUtils.isNotBlank(jsonStr)) {
                    return JSON.parseObject(jsonStr);
                } else {
                    return new JSONObject();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    private static List<NameValuePair> getCommonParams(String method, String accessToken) {
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("client_id", CLIENT_ID));
        params.add(new BasicNameValuePair("method", method));
        params.add(new BasicNameValuePair("access_token", accessToken));
        params.add(new BasicNameValuePair("timestamp", TIMESTAMP));
        return params;
    }

    private static List<NameValuePair> getAuthverifyCodeParams() {
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("response_type", "code"));
        params.add(new BasicNameValuePair("client_id", CLIENT_ID));
        params.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
        params.add(new BasicNameValuePair("uname", UNAME));
        params.add(new BasicNameValuePair("passwd", PASSWD));
        return params;
    }

    private static List<NameValuePair> getAccessTokenParams(String code) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("client_id", CLIENT_ID));
        params.add(new BasicNameValuePair("client_secret", clientSecret(code)));
        params.add(new BasicNameValuePair("grant_type", GRANT_TYPE));
        params.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));
        params.add(new BasicNameValuePair("code", code));
        return params;
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(is, "gb2312"));
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private static String encodeToGbk(String s) {
        try {
            return URLEncoder.encode(s, "gb2312");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    private static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }
}
