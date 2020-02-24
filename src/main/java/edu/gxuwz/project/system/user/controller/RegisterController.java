package edu.gxuwz.project.system.user.controller;

import edu.gxuwz.common.constant.UserConstants;
import edu.gxuwz.common.utils.StringUtils;
import edu.gxuwz.framework.shiro.service.PasswordService;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.framework.web.domain.AjaxResult;
import edu.gxuwz.framework.web.domain.Ztree;
import edu.gxuwz.project.system.dept.domain.Dept;
import edu.gxuwz.project.system.dept.service.IDeptService;
import edu.gxuwz.project.system.user.domain.User;
import edu.gxuwz.project.system.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private PasswordService passwordService;

    @GetMapping()
    public String register(){
        return "register";
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
     * 选择部门树
     */
    @GetMapping("/selectDeptTree/{deptId}")
    public String selectDeptTree(@PathVariable("deptId") Long deptId, ModelMap mmap)
    {
        mmap.put("dept", deptService.selectDeptById(deptId));
        return "tree";
    }

    /**
     * 加载部门列表树
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData()
    {
        List<Ztree> ztrees = deptService.selectDeptTree(new Dept());
        return ztrees;
    }

    /**
     * 新增保存用户
     */
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated User user)
    {
        if(StringUtils.isEmpty(user.getLoginName())){
            user.setLoginName(user.getCardNu());
        }
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
        //user.setCardNu(user.getLoginName());
        user.setUserName(StringUtils.trim(user.getUserName()));
        user.setCardNu(StringUtils.trim(user.getCardNu()));
        user.setLoginName(StringUtils.trim(user.getLoginName()));
        user.setEmail(StringUtils.trim(user.getEmail()));
        user.setPhonenumber(StringUtils.trim(user.getPhonenumber()));


        user.setPassword(user.getCardNu().substring(user.getCardNu().length()-8));
        if(user.getStudent()){
           user.setRoleIds(new Long[]{100L});
        }else{
            user.setRoleIds(new Long[]{102L});
        }
        return toAjax(userService.insertUser(user));
    }


    @GetMapping("/init")
    @ResponseBody
    public String initUserPwd(){
        List<User> users = userService.selectUserList(new User());
        for(User u:users){
            if(StringUtils.isEmpty(u.getPassword())){
                u.randomSalt();
                u.setPassword("123456");
                u.setPassword(passwordService.encryptPassword(u.getLoginName(), u.getPassword(), u.getSalt()));
                String by = u.getUserName();
                u.setCreateBy(by);
                if(u.getStudent()){
                    u.setRoleIds(new Long[]{100L});
                }else{
                    u.setRoleIds(new Long[]{102L});
                }
                // 新增用户与角色管理
                userService.updateUser(u);
            }
        }
        return "可以";
    }

}
