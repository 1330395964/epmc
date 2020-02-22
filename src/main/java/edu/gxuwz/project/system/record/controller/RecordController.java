package edu.gxuwz.project.system.record.controller;

import edu.gxuwz.common.utils.DateUtils;
import edu.gxuwz.common.utils.DocumentHandler;
import edu.gxuwz.common.utils.StringUtils;
import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.common.utils.security.ShiroUtils;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.mq.MqMailService;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.dept.domain.Dept;
import edu.gxuwz.project.system.dept.service.IDeptService;
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

    @Autowired
    private MqMailService mqMailService;

    @Autowired
    private IDeptService deptService;

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
    @GetMapping("bumen")
    public String bumen()
    {
        return prefix + "/bumenRecord";
    }

    @GetMapping("geren")
    public String geren()
    {
        return prefix + "/gerenRecord";
    }

    @GetMapping("xuesheng")
    public String xuesheng(ModelMap map)
    {
        List<String> grades = userService.selectGrades();
        List<String> zyNames = userService.selectZy();
        map.put("grades", grades);
        map.put("zyNames", zyNames );
        return prefix + "/xuesheng";
    }

    @GetMapping("jiaozhigong")
    public String jiaozhigong()
    {
        return prefix + "/jiaozhigong";
    }

    /**
     * 部门管理员查看疫情情况
     * @param
     * @return
     */
    @PostMapping("/bumens")
    @ResponseBody
    public TableDataInfo bumen(Record record)
    {
        startPage();
        User user = ShiroUtils.getSysUser();
        record.setDeptId(user.getDeptId());
        List<Record> list = recordService.bumen(record);
        return getDataTable(list);
    }

    @PostMapping("/xueshengs")
    @ResponseBody
    public TableDataInfo xueshengs(Record record)
    {
        startPage();
        List<Record> list = recordService.xuesheng(record);
        return getDataTable(list);
    }

    @PostMapping("/jiaozhigongs")
    @ResponseBody
    public TableDataInfo jiaozhigongs(Record record)
    {
        startPage();
        List<Record> list = recordService.jiaozhigong(record);
        return getDataTable(list);
    }

    /**
     * 个人查看疫情情况
     * @param
     * @return
     */
    @PostMapping("/gerens")
    @ResponseBody
    public TableDataInfo geren(Record record)
    {
        startPage();
        User user = ShiroUtils.getSysUser();
        record.setRecordNumber(user.getLoginName());
        List<Record> list = recordService.geren(record);
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
        String userSelf = request.getParameter("userSelf");
        String other = request.getParameter("other");
        if(!StringUtils.isEmpty(userSelf)){
            User sysUser = getSysUser();
            record.setRecordNumber(sysUser.getLoginName());
        }
        if(!StringUtils.isEmpty(other)){
            record.setRecordNumber(other);
        }
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
                Dept dept = deptService.selectDeptById(user.getDeptId());
                String institute = "";
                if(dept != null){
                    institute = dept.getDeptName();
                }
                if(!StringUtils.isEmpty(user.getGradeId())) {
                    institute += user.getGradeId();
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
                    for(int i=list.size()-1; i>=0;i--){
                        size--;
                        if(size==0){
                            size=i;
                        }
                    }
                }
                for (int i = size; i<list.size(); i++) {
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
        String userSelf = request.getParameter("userSelf");
        String other = request.getParameter("other");
        if(!StringUtils.isEmpty(userSelf)){
            User sysUser = getSysUser();
            record.setRecordNumber(sysUser.getLoginName());
        }
        if(!StringUtils.isEmpty(other)){
            record.setRecordNumber(other);
        }else{
            record.setRecordNumber(getLoginName());
        }
        List<Record> list = recordService.selectRecordList(record);
        if(StringUtils.isEmpty(type)){
            ExcelUtil<Record> util = new ExcelUtil<Record>(Record.class);
            return util.exportExcel(list, "record");
        }else{
            Map<String, Object> dataMap = new HashMap<>();
            String name = "1";
            try {
                String institute = "";
                User user = userService.selectUserByLoginName(record.getRecordNumber());
                Dept dept = deptService.selectDeptById(user.getDeptId());
                if(dept != null){
                    institute = dept.getDeptName();
                }
                if(!StringUtils.isEmpty(user.getGradeId())) {
                    institute += user.getGradeId();
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
                    for(int i=list.size()-1; i>=0;i--){
                        size--;
                        if(size==0){
                            size=i;
                        }
                    }
                }
                for (int i = size; i<list.size(); i++) {
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
        if(record1 == null && !StringUtils.isEmpty(record.getTempMorning())){
            double t = Double.valueOf(record.getTempMorning());
            if(t > 37.3){
                record.setFever(true);
                // ==== 邮件上报上午数据
                try {
                    User user = getSysUser();
                    //College college = collegeService.selectCollegeById(user.getCollegeId());
                    Dept dept = deptService.selectDeptById(user.getDeptId());
                    StringBuilder content = new StringBuilder();
                    content.append("梧州学院疫情管理系统检测到上报数据可能存在问题，上报人信息（学生/教职工）:\r\n")
                            .append("身份证号：" + user.getCardNu() + "\r\n")
                            .append("证件号：" + user.getLoginName() + "\r\n")
                            .append("姓名：" + user.getUserName() + "\r\n");
                    if(dept != null){
                        content.append("所在学院(部门)：" + dept.getDeptName() + "\r\n");
                    }
                    if (!StringUtils.isEmpty(user.getGradeId())) {
                        content.append("所在班级：" + user.getGradeId() + "\r\n");
                    }
                    content.append("温度：" + record.getTempMorning() + "\r\n")
                            .append("目前健康与否：" + (record.getHealth() ? "待观察" : "健康") + "\r\n")
                            .append((record.getFever() ? "目前有发热症状" : "目前无发热症状") + "\r\n")
                            .append((record.getCough() ? "目前有干咳症状" : "目前无干咳症状") + "\r\n")
                            .append((record.getWeak() ? "目前有乏力症状" : "目前无乏力症状") + "\r\n")
                            .append("备注：" + record.getRemark() + "\r\n");
                    List<String> mails = userService.selectEmailByDeptIdAndStudent(user.getDeptId());
                    if(mails != null && mails.size() > 0){
                        new Thread() {
                            @Override
                            public void run() {
                                for (String mail : mails) {
                                    if (!StringUtils.isEmpty(mail)) {
                                        mqMailService.sendSimpleMail(mail, "梧州学院疫情管理系统", content.toString());
                                    }
                                }
                            }
                        }.start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }else{
            double t = Double.valueOf(record.getTempMorning());
            if(t > 37.3){
                record.setFever(true);
                // ==== 邮件上报下午数据
                try {
                    User user = getSysUser();
                    //College college = collegeService.selectCollegeById(user.getCollegeId());
                    Dept dept = deptService.selectDeptById(user.getDeptId());
                    StringBuilder content = new StringBuilder();
                    content.append("梧州学院疫情管理系统检测到上报数据可能存在问题，上报人信息（学生/教职工）:\r\n")
                            .append("身份证号：" + user.getCardNu() + "\r\n")
                            .append("证件号：" + user.getLoginName() + "\r\n")
                            .append("姓名：" + user.getUserName() + "\r\n");
                    if(dept != null){
                        content.append("所在学院（部门）：" + dept.getDeptName() + "\r\n");
                    }
                    if (!StringUtils.isEmpty(user.getGradeId())) {
                        content.append("所在班级：" + user.getGradeId() + "\r\n");
                    }
                    content.append("温度：" + record.getTempMorning() + "\r\n")
                            .append("目前健康与否：" + (record.getHealth() ? "待观察" : "健康") + "\r\n")
                            .append((record.getFever() ? "目前有发热症状" : "目前无发热症状") + "\r\n")
                            .append((record.getCough() ? "目前有干咳症状" : "目前无干咳症状") + "\r\n")
                            .append((record.getWeak() ? "目前有乏力症状" : "目前无乏力症状") + "\r\n")
                            .append("备注：" + record.getRemark() + "\r\n");
                    List<String> mails = userService.selectEmailByDeptIdAndStudent(user.getDeptId());
                    if(mails != null && mails.size() > 0){
                        new Thread(){
                            @Override
                            public void run() {
                                for(String mail:mails){
                                    if(!StringUtils.isEmpty(mail)){
                                        mqMailService.sendSimpleMail(mail, "梧州学院疫情管理系统", content.toString());
                                    }
                                }
                            }
                        }.start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
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
