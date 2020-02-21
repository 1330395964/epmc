package edu.gxuwz.project.system.college.controller;

import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.college.domain.College;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.dept.service.IDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 二级学院与部门Controller
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Controller
@RequestMapping("/system/college")
public class CollegeController extends BaseController
{
    private String prefix = "system/college";

    @Autowired
    private ICollegeService collegeService;

    @Autowired
    private IDeptService deptService;

    @RequiresPermissions("system:college:view")
    @GetMapping()
    public String college()
    {
        return prefix + "/college";
    }

    /**
     * 查询二级学院与部门列表
     */
    @RequiresPermissions("system:college:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(College college)
    {
        startPage();
        List<College> list = collegeService.selectCollegeList(college);
        return getDataTable(list);
    }

    /**
     * 导出二级学院与部门列表
     */
    @RequiresPermissions("system:college:export")
    @Log(title = "二级学院与部门", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(College college)
    {
        List<College> list = collegeService.selectCollegeList(college);
        ExcelUtil<College> util = new ExcelUtil<College>(College.class);
        return util.exportExcel(list, "college");
    }

    /**
     * 新增二级学院与部门
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存二级学院与部门
     */
    @RequiresPermissions("system:college:add")
    @Log(title = "二级学院与部门", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(College college)
    {
        return toAjax(collegeService.insertCollege(college));
    }

    /**
     * 修改二级学院与部门
     */
    @GetMapping("/edit/{collegeId}")
    public String edit(@PathVariable("collegeId") Long collegeId, ModelMap mmap)
    {
        College college = collegeService.selectCollegeById(collegeId);
        mmap.put("college", college);
        return prefix + "/edit";
    }

    /**
     * 修改保存二级学院与部门
     */
    @RequiresPermissions("system:college:edit")
    @Log(title = "二级学院与部门", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(College college)
    {
        return toAjax(collegeService.updateCollege(college));
    }

    /**
     * 删除二级学院与部门
     */
    @RequiresPermissions("system:college:remove")
    @Log(title = "二级学院与部门", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(collegeService.deleteCollegeByIds(ids));
    }
}
