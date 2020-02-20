package edu.gxuwz.project.system.college.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("collegeId", getCollegeId())
            .append("collegeName", getCollegeName())
            .toString();
    }
}
