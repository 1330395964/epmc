package edu.gxuwz.common.utils;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DocUtil {

    public static void main(String[] args) throws IOException {
        Map map=new HashMap();
        map.put("ar_cp_name","月报");
        map.put("ar_dateTime","2018-5-28");
        map.put("ar_info","岁的法国大使馆的风格");
        //getBuild("static/doc/ar_template.doc",map,"D:/aaa.doc");
        InputStream inputStream = DocUtil.class.getClassLoader().getResourceAsStream("static/doc/doc_user_temp.docx");
        XWPFDocument document = new XWPFDocument(inputStream);
        for(XWPFParagraph p : document.getParagraphs())//遍历段落
        {
            System.out.print(p.getParagraphText());
        }
        for(XWPFTable table : document.getTables())//遍历表格
        {
            for(XWPFTableRow row : table.getRows())
            {
                for(XWPFTableCell cell : row.getTableCells())
                {
                    System.out.print(cell.getText());
                }
            }
        }

        List<XWPFTable> tables = document.getTables();
        XWPFTable table = tables.get(0);
        XWPFTableRow row1 = table.getRows().get(table.getRows().size()-1);
        copy(table, row1, table.getRows().size());
        FileOutputStream out = new FileOutputStream(new File("./aaaa.docx"));
        document.write(out);
        out.close();
        document.close();
    }

    public static void copy(XWPFTable table,XWPFTableRow sourceRow,int rowIndex){
        //在表格指定位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(rowIndex);
        //复制行属性
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> cellList = sourceRow.getTableCells();
        if (null == cellList) {
            return;
        }
        //复制列及其属性和内容
        XWPFTableCell targetCell = null;
        for (XWPFTableCell sourceCell : cellList) {
            targetCell = targetRow.addNewTableCell();
            //列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            //段落属性
            if(sourceCell.getParagraphs()!=null&&sourceCell.getParagraphs().size()>0){
                targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
                if(sourceCell.getParagraphs().get(0).getRuns()!=null&&sourceCell.getParagraphs().get(0).getRuns().size()>0){
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    cellR.setText(sourceCell.getText());
                    cellR.setBold(sourceCell.getParagraphs().get(0).getRuns().get(0).isBold());
                }else{
                    targetCell.setText(sourceCell.getText());
                }
            }else{
                targetCell.setText(sourceCell.getText());
            }
        }
    }

    public void getBuild(String  tmpFile, Map<String, String> contentMap, String exportFile) throws FileNotFoundException {
        InputStream inputStream = DocUtil.class.getClassLoader().getResourceAsStream(tmpFile);
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(tmpFile);
        getBuild(inputStream, contentMap, new FileOutputStream(exportFile));
    }

    public void getBuild(InputStream inputStream, Map<String, String> contentMap, OutputStream outputStream) {
        HWPFDocument document = null;
        try {
            document = new HWPFDocument(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }
        //bodyRange.insertAfter()
        //导出到文件
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}