package edu.gxuwz.project.system.grade.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.web.domain.BaseEntity;
import edu.gxuwz.project.system.major.domain.Major;

/**
 * 班级对象 sys_grade
 * 
 * @author epmc
 * @date 2020-02-20
 */
public class Grade extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 班级编号 */
    private Long gradeId;

    /** 班级名称 */
    @Excel(name = "班级名称")
    private String gradeName;

    /** 年级 */
    private String grade;

    /** 专业 */
    private Long majorId;

    @Excel(name = "专业", targetAttr = "majorName")
    private Major major;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public void setGradeId(Long gradeId)
    {
        this.gradeId = gradeId;
    }

    public Long getGradeId() 
    {
        return gradeId;
    }
    public void setGradeName(String gradeName) 
    {
        this.gradeName = gradeName;
    }

    public String getGradeName() 
    {
        return gradeName;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }
    public void setMajorId(Long majorId) 
    {
        this.majorId = majorId;
    }

    public Long getMajorId() 
    {
        return majorId;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gradeId=" + gradeId +
                ", gradeName='" + gradeName + '\'' +
                ", grade='" + grade + '\'' +
                ", majorId=" + majorId +
                ", major=" + major +
                '}';
    }
}
