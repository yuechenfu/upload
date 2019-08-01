package com.hiveel.upload.web.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "folder")
public class FolderCache {
    private static final ArrayList<String> staticList = new ArrayList<>();
    private static String staticRootDir;
    static {
        ApplicationHome home = new ApplicationHome(FolderCache.class);
        File dir = home.getDir();
        staticRootDir =  dir.getAbsolutePath() + File.separator ;
    }

    private List<String> list;
    private String rootDir;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        if (list.isEmpty()) {
            return;
        }
        this.list = list;
        staticList.addAll(list);
    }

    public static ArrayList<String> getStaticList() {
        return staticList;
    }

    public static String getUploadRootDir() {
        return staticRootDir;
    }
}

