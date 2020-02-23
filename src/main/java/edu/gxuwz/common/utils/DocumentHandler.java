package edu.gxuwz.common.utils;

import edu.gxuwz.framework.config.RuoYiConfig;
import edu.gxuwz.framework.web.domain.AjaxResult;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

public class DocumentHandler {

    private Configuration configuration = null;

    public DocumentHandler() {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
    }


//    public static void main(String[] args) {
//        DocumentHandler handler = new DocumentHandler();
//        handler.createDoc();
//        System.out.println("成功");
//    }

    public String createDoc() {
        // 要填入模本的数据文件
        Map dataMap = new HashMap();
        getData(dataMap);
        File outFile = new File("./"+ UUID.randomUUID() +".doc");  //要与上下文编码一致
        return createDoc(dataMap,"./"+ UUID.randomUUID(), "doc");
    }

    public String createDoc(Map<String, Object> dataMap, String path) {
        // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        // 这里我们的模板是放在com.template包下面
        configuration.setClassForTemplateLoading(this.getClass(), "/static/doc");
        Template t = null;
        try {
            // test.ftl为要装载的模板
            t = configuration.getTemplate("doc_data_temp.ftl");
            t.setEncoding("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出文档路径及名称
        File outFile = new File(path);  //要与上下文编码一致
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "utf-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            t.process(dataMap, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
    public String createDoc(Map<String, Object> dataMap, String path, String type) {
        // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        // 这里我们的模板是放在com.template包下面
        String name = "";
        configuration.setClassForTemplateLoading(this.getClass(), "/static/doc");
        Template t = null;
        try {
            // test.ftl为要装载的模板
            t = configuration.getTemplate("doc_data_temp.ftl");
            t.setEncoding("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出文档路径及名称
        File outFile = new File(path + "." + type);  //要与上下文编码一致
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(outFile), "utf-8"));
            name = outFile.getAbsolutePath();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            t.process(dataMap, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 注意dataMap里存放的数据Key值要与模板中的参数相对应
     *
     * @param dataMap
     */
    @SuppressWarnings("unchecked")
    private void getData(Map dataMap) {

        dataMap.put("userName", "刘XX");
        dataMap.put("sex", "男");
        dataMap.put("institute", "大数据学院-2016软件工程3班");
        dataMap.put("phone", "18577409493");
        dataMap.put("cardNu", "450XXXX");
        dataMap.put("province", "广西");
        dataMap.put("city", "玉林");
        dataMap.put("county", "博白县");
        dataMap.put("address", "东平镇富新村秧地坡");

        List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();

        for (int i = 1; i <= 5; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("month", i++ );
            map.put("day", (i * i + 1 ) / 2);
            map.put("mor", 37.1);
            map.put("aft", 37.2);
            map.put("health", true);
            map.put("fever", true);
            map.put("cough", true);
            map.put("weak", true);
            map.put("remark", "正常");
            newsList.add(map);
        }

        dataMap.put("listData", newsList); //注意list 的名字
    }


    /**
     * 编码文件名
     */
    public String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename;
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public String getAbsoluteFile(String filename)
    {
        String downloadPath = RuoYiConfig.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }

    public AjaxResult export(Map<String, Object> dataMap, String name){
        String filename = encodingFilename(name);
        String absoluteFile = getAbsoluteFile(filename + ".doc");
        createDoc(dataMap, absoluteFile);
        return AjaxResult.success(filename  + ".doc");
    }
}