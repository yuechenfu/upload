package com.hiveel.upload.web.cache;

import com.hiveel.core.log.util.LogUtil;
import com.hiveel.core.util.StringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ConfigurationProperties(prefix = "apikey")
public class ApiKeyCache {

    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        if (list.isEmpty()) {
            LogUtil.warn("设置预apiKey，现在服务器没有预先设置apiKey, 请尽快设置，否则无管理员请求能成功");
            return;
        }
        this.list = list;
        SET.addAll(list);
    }

    private static final CopyOnWriteArraySet<String> SET = new CopyOnWriteArraySet<>();

    public static boolean isExist(String apiKey) {
        return SET.contains(apiKey);
    }

    public static void replace(String oldKey, String newKey) {
        SET.remove(oldKey);
        SET.add(newKey);
    }

    public static void add(String apiKey) {
        boolean flag = SET.add(apiKey);
        LogUtil.info("添加一个apiKey(成功? " + flag + "): " + StringUtil.coverMiddle(apiKey));
    }

    public static void remove(String apiKey) {
        boolean flag = SET.remove(apiKey);
        LogUtil.info("删除一个apiKey(成功? " + flag + "): " + StringUtil.coverMiddle(apiKey));
    }
}