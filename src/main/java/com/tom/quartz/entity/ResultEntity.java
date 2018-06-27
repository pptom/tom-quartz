package com.tom.quartz.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr Tom
 * @date 2017/11/23
 * @email ptomjie@gmail.com
 * @description 返回规定的标准格式的json {"success":true/false, "msg":"some msg", more...}
 * @since 2017/11/23
 */
public class ResultEntity implements Serializable {


    private static final long serialVersionUID = -2431333863007594548L;

    /**
     * 返回失败的结果
     * @param errorCode
     * @param msg
     * @return
     */
    public static Map<String, Object> fail(int errorCode, String msg) {
        return returnResult(false, msg, errorCode, null);
    }

    /**
     * 返回失败的结果
     * @param errorCode
     * @param msg
     * @return
     */
    public static Map<String, Object> fail(int errorCode, String msg, Map<String, Object> map) {
        return returnResult(false, msg, errorCode, map);
    }

    /**
     * 返回成功的结果
     * @param msg
     * @return
     */
    public static Map<String, Object> success(String msg) {
        return returnResult(true, msg, null, null);
    }


    /**
     * 返回成功的结果
     * @param msg
     * @param map
     * @return
     */
    public static Map<String, Object> success(String msg, Map<String, Object> map) {
        return returnResult(true, msg, null, map);
    }


    private static Map<String, Object> returnResult(boolean success, String msg, Integer errorCode, Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        result.put("msg", msg);
        if (map != null) {
            result.putAll(map);
        }
        if(errorCode != null) {
            result.put("errorCode", errorCode);
        }
        return result;
    }
}
