package edu.gxuwz.project.system.grade.controller;

import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.grade.domain.Grade;
import edu.gxuwz.project.system.grade.service.IGradeService;
import edu.gxuwz.project.system.major.domain.Major;
import edu.gxuwz.project.system.major.service.IMajorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级Controller
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Controller
@RequestMapping("/system/grade")
public class GradeController extends BaseController
{
    private String prefix = "system/grade";

    @Autowired private ICollegeService collegeService;

    @Autowired private IMajorService majorService;

    @Autowired
    private IGradeService gradeService;

    @RequiresPermissions("system:grade:view")
    @GetMapping()
    public String grade(ModelMap modelMap)
    {
        List<String> strings = gradeService.selectAllGrade();
        modelMap.put("grades", strings);
        List<Major> majors = majorService.selectMajorList(new Major());
        modelMap.put("majors", majors);
        return prefix + "/grade";
    }

    /**
     * 查询班级列表
     */
    @RequiresPermissions("system:grade:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Grade grade)
    {
        startPage();
        List<Grade> list = gradeService.selectGradeList(grade);
        return getDataTable(list);
    }

    /**
     * 导出班级列表
     */
    @RequiresPermissions("system:grade:export")
    @Log(title = "班级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Grade grade)
    {
        List<Grade> list = gradeService.selectGradeList(grade);
        ExcelUtil<Grade> util = new ExcelUtil<Grade>(Grade.class);
        return util.exportExcel(list, "grade");
    }

    /**
     * 新增班级
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        List<Major> majors = majorService.selectMajorList(new Major());
        modelMap.put("majors", majors);
        return prefix + "/add";
    }

    /**
     * 新增保存班级
     */
    @RequiresPermissions("system:grade:add")
    @Log(title = "班级", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Grade grade)
    {
        return toAjax(gradeService.insertGrade(grade));
    }

    /**
     * 修改班级
     */
    @GetMapping("/edit/{gradeId}")
    public String edit(@PathVariable("gradeId") Long gradeId, ModelMap mmap)
    {
        Grade grade = gradeService.selectGradeById(gradeId);
        mmap.put("grade", grade);
        List<Major> majors = majorService.selectMajorList(new Major());
        mmap.put("majors", majors);
        return prefix + "/edit";
    }

    /**
     * 修改保存班级
     */
    @RequiresPermissions("system:grade:edit")
    @Log(title = "班级", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Grade grade)
    {
        return toAjax(gradeService.updateGrade(grade));
    }

    /**
     * 删除班级
     */
    @RequiresPermissions("system:grade:remove")
    @Log(title = "班级", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(gradeService.deleteGradeByIds(ids));
    }
}
