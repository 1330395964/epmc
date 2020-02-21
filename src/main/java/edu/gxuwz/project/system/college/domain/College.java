package edu.gxuwz.project.system.college.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.web.domain.BaseEntity;
import edu.gxuwz.project.system.dept.domain.Dept;

/**
 * 二级学院与部门对象 sys_college
 * 
 * @author epmc
 * @date 2020-02-20
 */
public class College extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学院 */
    private Long collegeId;

    /** 名称 */
    @Excel(name = "名称")
    private String collegeName;

    private Long deptId;

    @Excel(name = "部门" , targetAttr = "deptName")
    private Dept dept;

    @Override
    public String toString() {
        return "College{" +
                "collegeId=" + collegeId +
                ", collegeName='" + collegeName + '\'' +
                ", deptId=" + deptId +
                ", dept=" + dept +
                '}';
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public void setCollegeId(Long collegeId)
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId() 
    {
        return collegeId;
    }
    public void setCollegeName(String collegeName) 
    {
        this.collegeName = collegeName;
    }

    public String getCollegeName() 
    {
        return collegeName;
    }

}
