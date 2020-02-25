package edu.gxuwz.project.system.user.controller;

import edu.gxuwz.common.constant.UserConstants;
import edu.gxuwz.common.utils.DateUtils;
import edu.gxuwz.common.utils.poi.ExcelUtil;
import edu.gxuwz.common.utils.security.ShiroUtils;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.page.TableDataInfo;
import edu.gxuwz.project.system.post.service.IPostService;
import edu.gxuwz.project.system.role.domain.Role;
import edu.gxuwz.project.system.role.service.IRoleService;
import edu.gxuwz.project.system.user.domain.User;
import edu.gxuwz.project.system.user.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息
 * 
 * @author epmc
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController
{
    private String prefix = "system/user";

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPostService postService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user(ModelMap map)
    {
        map.put("deptId",getSysUser().getDeptId());
        return prefix + "/user";
    }

    /**
     * 未填报记录统计
     * @return
     */
    @GetMapping("/weitianbao")
    public String weitianbao(ModelMap map){
        map.put("deptId", getSysUser().getDeptId());
        map.put("date", DateUtils.getDate());
        return "system/record/weitianbao";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/weitianbao")
    @ResponseBody
    public TableDataInfo weitianbao(User user)
    {
        startPage();
        List<User> list = userService.selectWeitianbao(user);
        return getDataTable(list);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("xuesheng")
    public String xuesheng(ModelMap map)
    {
        map.put("date", DateUtils.getDate())
        ;return prefix + "/xuesheng";
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("jiaozhigong")
    public String jiaozhigong(ModelMap map)
    {
        map.put("date", DateUtils.getDate());
        return prefix + "/jiaozhigong";
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(User user)
    {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/xueshengs")
    @ResponseBody
    public TableDataInfo xueshengs(User user)
    {
        startPage();
        List<User> list = userService.xuesheng(user);
        return getDataTable(list);
    }

    @RequiresPermissions("system:user:list")
    @PostMapping("/jiaozhigongs")
    @ResponseBody
    public TableDataInfo jiaozhigongs(User user)
    {
        startPage();
        List<User> list = userService.jiaozhigong(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
   // @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(User user, HttpServletRequest request)
    {
        List<User> list = new ArrayList<>();
        String excelType = request.getParameter("excelType");
        if("weitianbao".equals(excelType)){
            list = userService.selectWeitianbao(user);
        }else{
            list = userService.selectUserList(user);
        }
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.exportExcel(list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        List<User> userList = util.importExcel(file.getInputStream());
        String message = userService.importUser(userList, updateSupport);
        return AjaxResult.success(message);
    }

    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate()
    {
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 新增用户
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        mmap.put("roles", roleService.selectRoleAll());
        mmap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated User user)
    {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName())))
        {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        }
        else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        List<Role> roles = roleService.selectRolesByUserId(userId);
        User sysUser = getSysUser();
        ArrayList<Role> list = new ArrayList<>();
        if(roles != null && roles.size() > 0 && !sysUser.isAdmin()){
            for (int i=0;i<roles.size();i++){ // 103部门信息员, 104校学生信息员, 105校交工信息员
                Role role = roles.get(i);
                if(!role.isFlag()){
                    switch (role.getRoleId().intValue()){
                        case 103:{
                            list.add(role);
                            break;
                        }
                        case 104:{
                            list.add(role);
                            break;
                        }
                        case 105:{
                            list.add(role);
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
            roles.removeAll(list);
        }
        mmap.put("roles", roles);
        mmap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated User user)
    {
        userService.checkUserAllowed(user);
        if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
        }
        else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.updateUser(user));
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        mmap.put("user", userService.selectUserById(userId));
        return prefix + "/resetPwd";
    }

    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(User user)
    {
        userService.checkUserAllowed(user);
        if (userService.resetUserPwd(user) > 0)
        {
            if (ShiroUtils.getUserId() == user.getUserId())
            {
                setSysUser(userService.selectUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        try
        {
            return toAjax(userService.deleteUserByIds(ids));
        }
        catch (Exception e)
        {
            return error(e.getMessage());
        }
    }

    /**
     * 校验用户名
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(User user)
    {
        return userService.checkLoginNameUnique(user.getLoginName());
    }

    /**
     * 校验手机号码
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(User user)
    {
        return userService.checkPhoneUnique(user);
    }

    /**
     * 校验email邮箱
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(User user)
    {
        return userService.checkEmailUnique(user);
    }

    /**
     * 用户状态修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:user:edit")
    @PostMapping("/changeStatus")
    @ResponseBody
    public AjaxResult changeStatus(User user)
    {
        userService.checkUserAllowed(user);
        return toAjax(userService.changeStatus(user));
    }
}