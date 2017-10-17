package com.util;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;


/**
 * @author chencaihua
 * @description
 * @date 2017-09-28 16:43
 * @modified By
 */
public class FreemarkerUtil {

    private static Template getTemplate(String basepath, String name) {
        try {
            Configuration cfg = new Configuration();
            TemplateLoader templateLoader = new FileTemplateLoader(new File(basepath + "/WEB-INF/ftl/"));
            cfg.setTemplateLoader(templateLoader);
            cfg.setDefaultEncoding("UTF-8");
            return cfg.getTemplate(name, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 输出HTML文件
     *
     * @param templateName
     * @param root
     * @param basepath
     * @param outFilePath
     */
    public static void outHTML(String templateName, Map<String, ?> root, String basepath, String outFilePath) {
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {
            File file = new File(basepath + outFilePath);
            if (!file.exists()) {
                File apipath = new File(file.getParent());
                if (!apipath.exists()) {
                    apipath.mkdirs();
                }
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            osw = new OutputStreamWriter(fos, "UTF-8");
            bw = new BufferedWriter(osw, 1024);
            Template temp = getTemplate(basepath, templateName);
            temp.process(root, bw);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }

                if (fos != null) {
                    fos.flush();
                    fos.close();
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}