package edu.gxuwz.project.system.record.controller;

import edu.gxuwz.common.utils.DateUtils;
import edu.gxuwz.common.utils.DocumentHandler;
import edu.gxuwz.common.utils.StringUtils;
import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.college.domain.College;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.grade.domain.Grade;
import edu.gxuwz.project.system.grade.service.IGradeService;
import edu.gxuwz.project.system.record.domain.Record;
import edu.gxuwz.project.system.record.service.IRecordService;
import edu.gxuwz.project.system.user.domain.User;
import edu.gxuwz.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 记录Controller
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Controller
@RequestMapping("/system/record")
public class RecordController extends BaseController
{
    private String prefix = "system/record";

    @Autowired
    private IRecordService recordService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICollegeService collegeService;

    @Autowired
    private IGradeService gradeService;

    @RequiresPermissions("system:record:view")
    @GetMapping()
    public String record()
    {
        return prefix + "/record";
    }

    /**
     * 查询记录列表
     */
    @RequiresPermissions("system:record:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Record record)
    {
        startPage();
        List<Record> list = recordService.selectRecordList(record);
        return getDataTable(list);
    }

    @GetMapping("/list_list")
    public String listListView(ModelMap modelMap, HttpServletRequest request){
        List<Record> list = new ArrayList<>();
        String m_phone = request.getParameter("M_phone");
        if(!StringUtils.isEmpty(m_phone)){
            modelMap.put("m_phone", true);
            Record record = new Record();
            record.setRecordNumber(getLoginName());
            list = recordService.selectRecordList(record);
            modelMap.put("dataList", list);
        }else{
            modelMap.put("m_phone", false);
        }
        modelMap.put("recordNumber", getSysUser().getLoginName());
        return prefix + "/record_list";
    }

    /**
     * 查询自己的记录列表
     */
    @RequiresPermissions("system:record:list_list")
    @PostMapping("/list_list")
    @ResponseBody
    public TableDataInfo listList(Record record)
    {
        startPage();
        record.setRecordNumber(getLoginName());
        List<Record> list = recordService.selectRecordList(record);
        return getDataTable(list);
    }

    /**
     * 导出记录列表
     */
    @RequiresPermissions("system:record:export")
    @Log(title = "记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Record record, HttpServletRequest request)
    {
        String type = request.getParameter("type");
        List<Record> list = recordService.selectRecordList(record);
        if(StringUtils.isEmpty(type)){
            ExcelUtil<Record> util = new ExcelUtil<Record>(Record.class);
            return util.exportExcel(list, "record");
        }else{
            Map<String, Object> dataMap = new HashMap<>();
            String name = "1";
            try {
                Record record1 = list.get(0);
                User user = userService.selectUserByLoginName(record1.getRecordNumber());
                College college = collegeService.selectCollegeById(user.getCollegeId());
                Grade grade = gradeService.selectGradeById(user.getGradeId());
                String institute = "";
                if(college != null){
                    institute = college.getCollegeName();
                }
                if(grade != null){
                    institute += grade.getGrade()+grade.getGradeName();
                }
                name = user.getLoginName() + user.getUserName();
                dataMap.put("userName", StringUtils.isEmpty(user.getUserName())? "" : user.getUserName());
                String sex = "";
                if(StringUtils.isEmpty(user.getSex())){
                    sex = "未知";
                }else {
                    if("0".equals(user.getSex())){
                        sex = "男";
                    }else {
                        sex = "女";
                    }
                }
                dataMap.put("sex", sex);
                dataMap.put("institute", StringUtils.isEmpty(institute)?"":institute);
                dataMap.put("phone", StringUtils.isEmpty(user.getPhonenumber())?"":user.getPhonenumber());
                dataMap.put("cardNu", StringUtils.isEmpty(user.getCardNu()) ? "" : user.getCardNu());
                dataMap.put("province", StringUtils.isEmpty(user.getProvince())? "":user.getProvince());
                dataMap.put("city", StringUtils.isEmpty(user.getCityName())?"":user.getCityName());
                dataMap.put("county", StringUtils.isEmpty(user.getCounty())?"":user.getCounty());
                dataMap.put("address", StringUtils.isEmpty(user.getAddress())?"":user.getAddress());
                List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
                int size = 0;
                if(list.size()>=14){
                    size = 14;
                }else{
                    size = list.size();
                }
                for (int i = 0; i < size; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Record r = list.get(i);
                    map.put("month",  DateUtils.parseDateToStr("M", r.getRecordDate()));
                    map.put("day", DateUtils.parseDateToStr("d", r.getRecordDate()));
                    map.put("mor", StringUtils.isEmpty(r.getTempMorning())?"":r.getTempMorning());
                    map.put("aft", StringUtils.isEmpty(r.getTempAfternoon())?"":r.getTempAfternoon());
                    map.put("health", r.getHealth());
                    map.put("fever", r.getFever());
                    map.put("cough", r.getCough());
                    map.put("weak", r.getWeak());
                    map.put("remark", StringUtils.isEmpty(r.getRemark())?"":r.getRemark());
                    newsList.add(map);
                }
                dataMap.put("listData", newsList); //注意list 的名字
            }catch (Exception e){
                e.printStackTrace();
            }
            DocumentHandler documentHandler = new DocumentHandler();
            return documentHandler.export(dataMap, name);
        }
    }

    /**
     * 导出自己的记录列表
     */
    @Log(title = "记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSelf")
    @ResponseBody
    public AjaxResult exportSelf(Record record, HttpServletRequest request)
    {
        String type = request.getParameter("type");
        record.setRecordNumber(getLoginName());
        List<Record> list = recordService.selectRecordList(record);
        if(StringUtils.isEmpty(type)){
            ExcelUtil<Record> util = new ExcelUtil<Record>(Record.class);
            return util.exportExcel(list, "record");
        }else{
            Map<String, Object> dataMap = new HashMap<>();
            String name = "1";
            try {
                String institute = "";
                User user = getSysUser();
                Grade grade = gradeService.selectGradeById(user.getGradeId());
                College college = collegeService.selectCollegeById(user.getCollegeId());
                if(college != null){
                    institute = college.getCollegeName();
                }
                name = user.getLoginName() + user.getUserName();
                if(grade != null){
                    institute += grade.getGrade()+grade.getGradeName();
                }
                dataMap.put("userName", StringUtils.isEmpty(user.getUserName())? "" : user.getUserName());
                String sex = "";
                if(StringUtils.isEmpty(user.getSex())){
                    sex = "未知";
                }else {
                    if("0".equals(user.getSex())){
                        sex = "男";
                    }else {
                        sex = "女";
                    }
                }
                dataMap.put("sex", sex);
                dataMap.put("institute", StringUtils.isEmpty(institute)?"":institute);
                dataMap.put("phone", StringUtils.isEmpty(user.getPhonenumber())?"":user.getPhonenumber());
                dataMap.put("cardNu", StringUtils.isEmpty(user.getCardNu()) ? "" : user.getCardNu());
                dataMap.put("province", StringUtils.isEmpty(user.getProvince())? "":user.getProvince());
                dataMap.put("city", StringUtils.isEmpty(user.getCityName())?"":user.getCityName());
                dataMap.put("county", StringUtils.isEmpty(user.getCounty())?"":user.getCounty());
                dataMap.put("address", StringUtils.isEmpty(user.getAddress())?"":user.getAddress());
                List<Map<String, Object>> newsList = new ArrayList<Map<String, Object>>();
                int size = 0;
                if(list.size()>=14){
                    size = 14;
                }else{
                    size = list.size();
                }
                for (int i = 0; i < size; i++) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    Record r = list.get(i);
                    map.put("month",  DateUtils.parseDateToStr("M", r.getRecordDate()));
                    map.put("day", DateUtils.parseDateToStr("d", r.getRecordDate()));
                    map.put("mor", StringUtils.isEmpty(r.getTempMorning())?"":r.getTempMorning());
                    map.put("aft", StringUtils.isEmpty(r.getTempAfternoon())?"":r.getTempAfternoon());
                    map.put("health", r.getHealth());
                    map.put("fever", r.getFever());
                    map.put("cough", r.getCough());
                    map.put("weak", r.getWeak());
                    map.put("remark", StringUtils.isEmpty(r.getRemark())?"":r.getRemark());
                    newsList.add(map);
                }
                dataMap.put("listData", newsList); //注意list 的名字
            }catch (Exception e){
                e.printStackTrace();
            }
            DocumentHandler documentHandler = new DocumentHandler();
            return documentHandler.export(dataMap, name);
        }
    }

    /**
     * 新增记录
     */
    @GetMapping("/add")
    public String add(ModelMap map, HttpServletRequest request)
    {
        String s_type = request.getParameter("S_type");
        if(!StringUtils.isEmpty(s_type)){
            map.put("show_btn", true);
        }else{
            map.put("show_btn", false);
        }
        map.put("date", DateUtils.getDate());
        return prefix + "/add";
    }

    /**
     * 新增保存记录
     */
    @RequiresPermissions("system:record:add")
    @Log(title = "记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated Record record)
    {
        record.setRecordNumber(getLoginName());
        Record record1 = recordService.selectRecordByDateAndId(new Date(System.currentTimeMillis()), getSysUser().getLoginName());
        if(!StringUtils.isEmpty(record.getTempMorning())){
            double t = Double.valueOf(record.getTempMorning());
            if(t > 37.3){
                record.setFever(true);
                // ==== 邮件上报
            }
        }
        if(record1 == null){
            record.setTempAfternoon(null);
            return toAjax(recordService.insertRecord(record));
        }else{
            record1.setTempAfternoon(record.getTempMorning());
            //record1.setTempMorning(record.getTempMorning());
            record1.setCough(record.getCough());
            record1.setFever(record.getFever());
            record1.setHealth(record.getHealth());
            record1.setWeak(record.getWeak());
            return toAjax(recordService.updateRecord(record1));
        }
    }

    /**
     * 修改记录
     */
    @GetMapping("/edit/{recordId}")
    public String edit(@PathVariable("recordId") Long recordId, ModelMap mmap)
    {
        Record record = recordService.selectRecordById(recordId);
        mmap.put("record", record);
        return prefix + "/edit";
    }

    /**
     * 修改保存记录
     */
    @RequiresPermissions("system:record:edit")
    @Log(title = "记录", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Record record)
    {
        return toAjax(recordService.updateRecord(record));
    }

    /**
     * 删除记录
     */
    @RequiresPermissions("system:record:remove")
    @Log(title = "记录", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(recordService.deleteRecordByIds(ids));
    }
}
