package edu.gxuwz.common.utils;

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


    public static void main(String[] args) {
        DocumentHandler handler = new DocumentHandler();
        handler.createDoc();
        System.out.println("成功");
    }

    public void createDoc() {
        // 要填入模本的数据文件
        Map dataMap = new HashMap();
        getData(dataMap);
//      getTest(dataMap);
        // 设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
        // 这里我们的模板是放在com.template包下面
        configuration.setClassForTemplateLoading(this.getClass(),
                "/static/doc");
        Template t = null;
        try {
            // test.ftl为要装载的模板
            t = configuration.getTemplate("doc_data_temp.ftl");
            t.setEncoding("utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 输出文档路径及名称
        File outFile = new File("./"+ UUID.randomUUID() +".docx");  //要与上下文编码一致
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
}