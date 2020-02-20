package edu.gxuwz.project.system.major.controller;

import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.college.domain.College;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.major.domain.Major;
import edu.gxuwz.project.system.major.service.IMajorService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业Controller
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Controller
@RequestMapping("/system/major")
public class MajorController extends BaseController
{
    private String prefix = "system/major";

    @Autowired private ICollegeService collegeService;

    @Autowired
    private IMajorService majorService;

    @RequiresPermissions("system:major:view")
    @GetMapping()
    public String major(ModelMap modelMap)
    {
        List<College> colleges = collegeService.selectCollegeList(new College());
        modelMap.put("colleges", colleges);
        return prefix + "/major";
    }

    /**
     * 查询专业列表
     */
    @RequiresPermissions("system:major:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Major major)
    {
        startPage();
        List<Major> list = majorService.selectMajorList(major);
        return getDataTable(list);
    }

    /**
     * 导出专业列表
     */
    @RequiresPermissions("system:major:export")
    @Log(title = "专业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Major major)
    {
        List<Major> list = majorService.selectMajorList(major);
        ExcelUtil<Major> util = new ExcelUtil<Major>(Major.class);
        return util.exportExcel(list, "major");
    }

    /**
     * 新增专业
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap)
    {
        List<College> colleges = collegeService.selectCollegeList(new College());
        modelMap.put("colleges", colleges);
        return prefix + "/add";
    }

    /**
     * 新增保存专业
     */
    @RequiresPermissions("system:major:add")
    @Log(title = "专业", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Major major)
    {
        return toAjax(majorService.insertMajor(major));
    }

    /**
     * 修改专业
     */
    @GetMapping("/edit/{majorId}")
    public String edit(@PathVariable("majorId") Long majorId, ModelMap mmap)
    {
        Major major = majorService.selectMajorById(majorId);
        mmap.put("major", major);
        return prefix + "/edit";
    }

    /**
     * 修改保存专业
     */
    @RequiresPermissions("system:major:edit")
    @Log(title = "专业", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Major major)
    {
        return toAjax(majorService.updateMajor(major));
    }

    /**
     * 删除专业
     */
    @RequiresPermissions("system:major:remove")
    @Log(title = "专业", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(majorService.deleteMajorByIds(ids));
    }
}
