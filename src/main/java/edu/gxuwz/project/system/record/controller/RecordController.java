package edu.gxuwz.project.system.record.controller;

import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.record.domain.Record;
import edu.gxuwz.project.system.record.service.IRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 导出记录列表
     */
    @RequiresPermissions("system:record:export")
    @Log(title = "记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Record record)
    {
        List<Record> list = recordService.selectRecordList(record);
        ExcelUtil<Record> util = new ExcelUtil<Record>(Record.class);
        return util.exportExcel(list, "record");
    }

    /**
     * 新增记录
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存记录
     */
    @RequiresPermissions("system:record:add")
    @Log(title = "记录", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Record record)
    {
        return toAjax(recordService.insertRecord(record));
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
