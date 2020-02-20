package edu.gxuwz.project.system.major.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.web.domain.BaseEntity;
import edu.gxuwz.project.system.college.domain.College;

/**
 * 专业对象 sys_major
 * 
 * @author epmc
 * @date 2020-02-20
 */
public class Major extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 专业编号 */
    private Long majorId;

    /** 专业名称 */
    @Excel(name = "专业名称")
    private String majorName;

    /** 学院 */
    private Long collegeId;

    @Excel(name = "学院", targetAttr = "collegeName")
    private College college;

    public void setMajorId(Long majorId) 
    {
        this.majorId = majorId;
    }

    public Long getMajorId() 
    {
        return majorId;
    }
    public void setMajorName(String majorName) 
    {
        this.majorName = majorName;
    }

    public String getMajorName() 
    {
        return majorName;
    }
    public void setCollegeId(Long collegeId) 
    {
        this.collegeId = collegeId;
    }

    public Long getCollegeId() 
    {
        return collegeId;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }

    @Override
    public String toString() {
        return "Major{" +
                "majorId=" + majorId +
                ", majorName='" + majorName + '\'' +
                ", collegeId=" + collegeId +
                ", college=" + college +
                '}';
    }
}
