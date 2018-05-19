package com.common.framework.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 矢量图标工具类
 *
 * @author BoSongsh
 * @create 2018-01-22 16:29
 **/
public final class IConUtils {

    public static List<String> icons = null;

    public static List<String> getIcons() {
        if (icons != null && !icons.isEmpty()) {
            return icons;
        }
        icons = new ArrayList<String>(100);

        // linux、window都支持
        Resource resource = new ClassPathResource("static/assets/global/plugins/simple-line-icons/simple-line-icons.css");
//        File file = new File(fileName);
        BufferedReader reader = null;
        Pattern pattern = Pattern.compile("icon-.*:");
        Matcher matcher = null;
        String tempString = null;
        try {
//            reader = new BufferedReader(new FileReader(resource.getFile()));
            // 放在 jar中的文件不能通过File读取、linux不支持
            reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                matcher = pattern.matcher(tempString);
                if (matcher.find()) {
                    tempString = matcher.group(0);
                    tempString = tempString.replace(":", "");
                    icons.add(tempString);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return icons;
    }

//    public static void main(String[] args) {
//        System.out.println(IConUtils.getIcons());
////        String str = ".icon-volume-off:before {";
////        String result = "";
////        Pattern pattern = Pattern.compile("icon-.*:");
////        Matcher matcher = pattern.matcher(str);
////        if (matcher.find())
////            result = matcher.group(0);
////        System.out.println(result);
//    }

    private IConUtils() {
    }
}
