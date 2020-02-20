package edu.gxuwz.project.system.record.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 记录对象 sys_record
 * 
 * @author epmc
 * @date 2020-02-20
 */
public class Record extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录编号 */
    private Long recordId;

    /** 记录者工号学号 */
    @Excel(name = "记录者工号学号")
    private String recordNumber;

    /** 记录日期 */
    @Excel(name = "记录日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordDate;

    /** 上午温度 */
    @Excel(name = "上午温度")
    private String tempMorning;

    /** 下午温度 */
    @Excel(name = "下午温度")
    private String tempAfternoon;

    /** 0无咳嗽1有咳嗽 */
    @Excel(name = "0无咳嗽1有咳嗽")
    private String recordCough;

    /** 证明人 */
    @Excel(name = "证明人")
    private String referencesName;

    /** 同居0无咳嗽1有咳嗽 */
    @Excel(name = "同居0无咳嗽1有咳嗽")
    private String otherCough;

    /** 同居人姓名 */
    @Excel(name = "同居人姓名")
    private String otherName;

    /** 同居人情况 */
    @Excel(name = "同居人情况")
    private String otherSituation;

    /** 0无离开1有离开 */
    @Excel(name = "0无离开1有离开")
    private String outsideLife;

    /** 离开事由 */
    @Excel(name = "离开事由")
    private String outsideReason;

    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setRecordNumber(String recordNumber) 
    {
        this.recordNumber = recordNumber;
    }

    public String getRecordNumber() 
    {
        return recordNumber;
    }
    public void setRecordDate(Date recordDate) 
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate() 
    {
        return recordDate;
    }
    public void setTempMorning(String tempMorning) 
    {
        this.tempMorning = tempMorning;
    }

    public String getTempMorning() 
    {
        return tempMorning;
    }
    public void setTempAfternoon(String tempAfternoon) 
    {
        this.tempAfternoon = tempAfternoon;
    }

    public String getTempAfternoon() 
    {
        return tempAfternoon;
    }
    public void setRecordCough(String recordCough) 
    {
        this.recordCough = recordCough;
    }

    public String getRecordCough() 
    {
        return recordCough;
    }
    public void setReferencesName(String referencesName) 
    {
        this.referencesName = referencesName;
    }

    public String getReferencesName() 
    {
        return referencesName;
    }
    public void setOtherCough(String otherCough) 
    {
        this.otherCough = otherCough;
    }

    public String getOtherCough() 
    {
        return otherCough;
    }
    public void setOtherName(String otherName) 
    {
        this.otherName = otherName;
    }

    public String getOtherName() 
    {
        return otherName;
    }
    public void setOtherSituation(String otherSituation) 
    {
        this.otherSituation = otherSituation;
    }

    public String getOtherSituation() 
    {
        return otherSituation;
    }
    public void setOutsideLife(String outsideLife) 
    {
        this.outsideLife = outsideLife;
    }

    public String getOutsideLife() 
    {
        return outsideLife;
    }
    public void setOutsideReason(String outsideReason) 
    {
        this.outsideReason = outsideReason;
    }

    public String getOutsideReason() 
    {
        return outsideReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("recordNumber", getRecordNumber())
            .append("recordDate", getRecordDate())
            .append("tempMorning", getTempMorning())
            .append("tempAfternoon", getTempAfternoon())
            .append("recordCough", getRecordCough())
            .append("referencesName", getReferencesName())
            .append("otherCough", getOtherCough())
            .append("otherName", getOtherName())
            .append("otherSituation", getOtherSituation())
            .append("outsideLife", getOutsideLife())
            .append("outsideReason", getOutsideReason())
            .toString();
    }
}
