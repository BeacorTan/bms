package com.common.shiro;

import at.pollux.thymeleaf.shiro.processor.ShiroFacade;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IText;
import org.thymeleaf.processor.text.AbstractTextProcessor;
import org.thymeleaf.processor.text.ITextStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.List;

public class HasPermissionJavascriptProcessor extends AbstractTextProcessor {

    private static String SHIRO_JAVASCRIPT = "shiro:hasPermission";

    private static String SHIRO_JAVASCRIPT_PREFIX = "<shiro:hasPermission";

    private static String SHIRO_JAVASCRIPT_SUFFIX = "</shiro:hasPermission>";

    private static String SHIRO_JAVASCRIPT_LINE = "\n";


    public HasPermissionJavascriptProcessor() {
        super(TemplateMode.JAVASCRIPT, 1001);
    }

    protected void doProcess(ITemplateContext context, IText text, ITextStructureHandler structureHandler) {

        String lineText = text.getText();
        if (lineText == null || lineText.trim().length() == 0) {
            return;
        }

        if (!lineText.contains(SHIRO_JAVASCRIPT)) {
            return;
        }

        String[] str = lineText.split(SHIRO_JAVASCRIPT_LINE);
        if (str == null || str.length == 0) {
            return;
        }

        List<Object> j = new ArrayList<Object>(10);
        JavaScriptModel model = null;
        for (String s : str) {
            if (s.contains(SHIRO_JAVASCRIPT_PREFIX)) {
                model = new JavaScriptModel();
                int begin = s.indexOf("\"");
                int end = s.lastIndexOf("\"");
                model.setAttributeName(s.substring(begin + 1, end));
            } else if (s.contains(SHIRO_JAVASCRIPT_SUFFIX)) {
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
                content.append(SHIRO_JAVASCRIPT_LINE);
            } else if (obj instanceof JavaScriptModel) {
                model = (JavaScriptModel) obj;
                if (ShiroFacade.hasAllPermissions(model.getAttributeName())) {
                    content.append(model.getTextContent());
                    content.append(SHIRO_JAVASCRIPT_LINE);
                }
            }
        }
        structureHandler.setText(content);
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

