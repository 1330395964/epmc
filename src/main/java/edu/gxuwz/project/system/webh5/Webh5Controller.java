package edu.gxuwz.project.system.webh5;

import edu.gxuwz.framework.web.controller.BaseController;
import edu.gxuwz.project.system.college.service.ICollegeService;
import edu.gxuwz.project.system.grade.service.IGradeService;
import edu.gxuwz.project.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * h5模块的控制层
 */
@Controller
@RequestMapping("/system/webh5")
public class Webh5Controller extends BaseController {

    private String prefix = "system/webh5";

    @Autowired
    private ICollegeService collegeService;

    @Autowired
    private IGradeService gradeService;

    @GetMapping()
    public String web(ModelMap modelMap){
        User sysUser = getSysUser();
        modelMap.put("user", sysUser);
        modelMap.put("student", sysUser.getStudent());
//        College college = collegeService.selectCollegeById(sysUser.getCollegeId());
//        modelMap.put("college", college);
//        Grade grade = gradeService.selectGradeById(sysUser.getGradeId());
//        modelMap.put("grade", grade);
        return "web";
    }


}
