package com.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BoSongsh
 * @create 2018-07-30 15:24
 **/
public class JavaScriptTest {

    public static void main(String[] args) {

        List<String> auth = new ArrayList<>();
        auth.add("permtype1:permaction4");
        auth.add("permtype1:permaction3");

        String script = "  <shiro:hasPermission name=\"permtype1:permaction3\">\n" +
                "       console.log(\"aaaa\");\n" +
                "    </shiro:hasPermission>\n" +
                "\n" +
                "    <shiro:hasPermission name=\"permtype1:permaction4\">\n" +
                "       console.log(\"bbb\");\n" +
                "     </shiro:hasPermission>\n" +
                "\n" +
                "    function a() {\n" +
                "        console.log(\"funa\");\n" +
                "    }";

        String[] str = script.split("\n");

        List<Object> j = new ArrayList<>(10);
        JavaScriptModel model = null;
        for (String s : str) {
            if (s.contains("<shiro:hasPermission")) {
                model = new JavaScriptModel();
                int begin = s.indexOf("\"");
                int end = s.lastIndexOf("\"");
                model.setAttributeName(s.substring(begin + 1, end));
            } else if (s.contains("</shiro:hasPermission>")) {
                j.add(model);
                model = null;
                continue;
            } else {
                if (model == null) {
                    j.add(s);
                } else {
                    model.setTextContent(s);
                }
            }
        }

        StringBuilder content = new StringBuilder();
        for (Object obj : j) {
            if (obj instanceof String) {
                content.append(obj);
                content.append("\n");
            } else if (obj instanceof JavaScriptModel) {
                model = (JavaScriptModel) obj;
                if (auth.contains(model.getAttributeName())) {
                    content.append(model.getTextContent());
                    content.append("\n");
                }
            }
        }

        System.out.println(content.toString());


//        String str1 = "<shiro:hasPermission name=\"permtype1:permaction3\">";
//        int begin = str1.indexOf("\"");
//        int end = str1.lastIndexOf("\"");
//        System.out.println(str1.substring(begin + 1, end));


    }

}

class JavaScriptModel {

    private StringBuilder textContent;

    private String attributeName;

    public StringBuilder getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {

        if (this.textContent == null) {
            this.textContent = new StringBuilder();
        }
        this.textContent.append(textContent);
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }
}
