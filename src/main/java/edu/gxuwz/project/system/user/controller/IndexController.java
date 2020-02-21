package edu.gxuwz.project.system.user.controller;

import edu.gxuwz.common.utils.StringUtils;
import edu.gxuwz.framework.config.RuoYiConfig;
import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.project.system.menu.domain.Menu;
import edu.gxuwz.project.system.menu.service.IMenuService;
import edu.gxuwz.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页 业务处理
 * 
 * @author epmc
 */
@Controller
public class IndexController extends BaseController
{
    @Autowired
    private IMenuService menuService;

    @Autowired
    private RuoYiConfig ruoYiConfig;

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap)
    {
        // 取身份信息
        User user = getSysUser();
        // 根据用户id取出菜单
        List<Menu> menus = menuService.selectMenusByUser(user);
        mmap.put("menus", menus);
        mmap.put("user", user);
        mmap.put("copyrightYear", ruoYiConfig.getCopyrightYear());
        mmap.put("demoEnabled", ruoYiConfig.isDemoEnabled());
        return "index";
    }

    /**
     * 完善信息监测
     * @return
     */
    @GetMapping("/wanshanXinxi")
    @ResponseBody
    public Map<String,Object> wanshanXinxi(){
        HashMap<String, Object> map = new HashMap<>();
        User sysUser = getSysUser();
        if(!sysUser.isAdmin()){
            map.put("code", -1);
        }else{
            map.put("code", 0);
        }
        if(sysUser.getStudent()){
            if(StringUtils.isEmpty(sysUser.getAddress())
                    || StringUtils.isEmpty(sysUser.getCollegeId()+"")){
                map.put("code", -1);
            }else{
                map.put("code", 0);
            }
            map.put("student", true);
        }else{
            map.put("student", false);
        }
        return map;
    }

    // 切换主题
    @GetMapping("/system/switchSkin")
    public String switchSkin(ModelMap mmap)
    {
        return "skin";
    }

    // 系统介绍
    @GetMapping("/system/main")
    public String main(ModelMap mmap)
    {
        mmap.put("version", ruoYiConfig.getVersion());
        return "main_v1";
    }
}
