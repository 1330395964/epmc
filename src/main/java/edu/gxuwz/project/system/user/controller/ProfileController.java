package edu.gxuwz.project.system.user.controller;

import edu.gxuwz.common.utils.StringUtils;
import edu.gxuwz.common.utils.file.FileUploadUtils;
import edu.gxuwz.framework.aspectj.lang.annotation.Log;
import edu.gxuwz.framework.aspectj.lang.enums.BusinessType;
import edu.gxuwz.framework.config.RuoYiConfig;
import edu.gxuwz.framework.shiro.service.PasswordService;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.dept.domain.Dept;
import edu.gxuwz.project.system.dept.service.IDeptService;
import edu.gxuwz.project.system.grade.domain.Grade;
import edu.gxuwz.project.system.grade.service.IGradeService;
import edu.gxuwz.project.system.user.domain.User;
import edu.gxuwz.project.system.user.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 个人信息 业务处理
 * 
 * @author epmc
 */
@Controller
@RequestMapping("/system/user/profile")
public class ProfileController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private String prefix = "system/user/profile";

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private ICollegeService collegeService;

    @Autowired
    private IGradeService gradeService;

    @Autowired
    private IDeptService deptService;

    /**
     * 个人信息
     */
    @GetMapping()
    public String profile(ModelMap mmap)
    {
        User user = getSysUser();
        mmap.put("user", user);
        mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
        mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }

    /**
     * 个人信息
     */
    @GetMapping("/profile1")
    public String profile1(ModelMap mmap)
    {
        User user = getSysUser();
        mmap.put("user", user);
        Dept dept = deptService.selectDeptById(user.getDeptId());
        if(dept == null){
            dept = new Dept();
        }
        user.setDept(dept);
        //List<College> colleges = collegeService.selectCollegeList(new College());
        List<Grade> grades = gradeService.selectGradeList(new Grade());
        //mmap.put("colleges", colleges);
        mmap.put("grades", grades);
        return prefix + "/profile1";
    }

    @GetMapping("/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password)
    {
        User user = getSysUser();
        if (passwordService.matches(user, password))
        {
            return true;
        }
        return false;
    }

    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap mmap)
    {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String oldPassword, String newPassword)
    {
        User user = getSysUser();
        if (StringUtils.isNotEmpty(newPassword) && passwordService.matches(user, oldPassword))
        {
            user.setPassword(newPassword);
            if (userService.resetUserPwd(user) > 0)
            {
                setSysUser(userService.selectUserById(user.getUserId()));
                return success();
            }
            return error();
        }
        else
        {
            return error("修改密码失败，旧密码错误");
        }

    }

    /**
     * 修改用户
     */
    @GetMapping("/edit")
    public String edit(ModelMap mmap)
    {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/edit";
    }

    /**
     * 修改头像
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap mmap)
    {
        User user = getSysUser();
        mmap.put("user", userService.selectUserById(user.getUserId()));
        return prefix + "/avatar";
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(User user)
    {
        User currentUser = getSysUser();
        currentUser.setUserName(user.getUserName());
        currentUser.setEmail(user.getEmail());
        currentUser.setPhonenumber(user.getPhonenumber());
        currentUser.setSex(user.getSex());
        currentUser.setCardNu(user.getCardNu());
        currentUser.setGradeId(user.getGradeId());
        currentUser.setContactWuhan(user.getContactWuhan());
        currentUser.setAfterWuhan(user.getAfterWuhan());
        currentUser.setWorkplace(user.getWorkplace());
        currentUser.setOnJob(user.getOnJob());
        currentUser.setGraduates(user.getGraduates());
        currentUser.setAddress(user.getAddress());
        currentUser.setCounty(user.getCounty());
        currentUser.setCityName(user.getCityName());
        currentUser.setProvince(user.getProvince());
        currentUser.setParentsPhnu(user.getParentsPhnu());
        currentUser.setParentsName(user.getParentsName());
        currentUser.setDeptId(user.getDeptId());
        currentUser.setGradeId(user.getGradeId());
        currentUser.setUpdateTime(new Date());
        if (userService.updateUserInfo(currentUser) > 0)
        {
            setSysUser(userService.selectUserById(currentUser.getUserId()));
            return success();
        }
        return error();
    }

    /**
     * 保存头像
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/updateAvatar")
    @ResponseBody
    public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file)
    {
        User currentUser = getSysUser();
        try
        {
            if (!file.isEmpty())
            {
                String avatar = FileUploadUtils.upload(RuoYiConfig.getAvatarPath(), file);
                currentUser.setAvatar(avatar);
                if (userService.updateUserInfo(currentUser) > 0)
                {
                    setSysUser(userService.selectUserById(currentUser.getUserId()));
                    return success();
                }
            }
            return error();
        }
        catch (Exception e)
        {
            log.error("修改头像失败！", e);
            return error(e.getMessage());
        }
    }
}
