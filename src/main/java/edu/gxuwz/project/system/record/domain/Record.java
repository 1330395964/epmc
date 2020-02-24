package edu.gxuwz.project.system.record.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.aspectj.lang.annotation.Excels;
import edu.gxuwz.framework.web.domain.BaseEntity;
import edu.gxuwz.project.system.dept.domain.Dept;
import edu.gxuwz.project.system.user.domain.User;

import java.sql.Date;

/**
 * 记录对象 sys_record
 *
 * @author epmc
 * @date 2020-02-20
 */
public class Record extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    //@Excel(name = "学院（部门）", targetAttr = "deptName")
    private Dept dept;

    @Override
    public String toString() {
        return "Record{" +
                "dept=" + dept +
                ", user=" + user +
                ", recordId=" + recordId +
                ", recordNumber='" + recordNumber + '\'' +
                ", recordDate=" + recordDate +
                ", tempMorning='" + tempMorning + '\'' +
                ", tempAfternoon='" + tempAfternoon + '\'' +
                ", recordCough=" + recordCough +
                ", referencesName='" + referencesName + '\'' +
                ", otherCough=" + otherCough +
                ", otherName='" + otherName + '\'' +
                ", otherSituation='" + otherSituation + '\'' +
                ", outsideLife=" + outsideLife +
                ", outsideReason='" + outsideReason + '\'' +
                ", health=" + health +
                ", fever=" + fever +
                ", cough=" + cough +
                ", weak=" + weak +
                ", remark='" + remark + '\'' +
                ", deptId=" + deptId +
                '}';
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Excels({
            @Excel(name = "姓名", targetAttr = "userName"),
            @Excel(name = "班级", targetAttr = "gradeId"),
            @Excel(name = "学院（部门）", targetAttr = "dept.deptName"),
            @Excel(name = "班级", targetAttr = "gradeId"),
            @Excel(name = "电话", targetAttr = "phonenumber")
    })
    private User user;

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
    //@Excel(name = "0无咳嗽1有咳嗽")
    private Boolean recordCough;

    /** 证明人 */
    //@Excel(name = "证明人")
    private String referencesName;

    /** 同居0无咳嗽1有咳嗽 */
    //@Excel(name = "同居0无咳嗽1有咳嗽")
    private Boolean otherCough;

    /** 同居人姓名 */
    //@Excel(name = "同居人姓名")
    private String otherName;

    /** 同居人情况 */
    //@Excel(name = "同居人情况")
    private String otherSituation;

    /** 0无离开1有离开 */
    //@Excel(name = "0无离开1有离开")
    private Boolean outsideLife;

    /** 离开事由 */
    //@Excel(name = "离开事由")
    private String outsideReason;

    /** 是否健康：0是 1否*/
    @Excel(name = "(true:不健康)(false:健康)", readConverterExp = "0=是,1=否")
    private Boolean health;

    /** 是否发热：1是 0否 */
    @Excel(name = "(true:发热)(false:不发热)", readConverterExp = "1=是,0=否")
    private Boolean fever;

    /** 是否干咳：1是 0否*/
    @Excel(name = "(true:干咳)(false:不干咳)", readConverterExp = "1=是,0=否")
    private Boolean cough;

    /** 是否乏力：1是 0否*/
    @Excel(name = "(true:乏力)(false:不乏力)", readConverterExp = "1=是,0=否")
    private Boolean weak;

    /** 其他 */
    @Excel(name = "所在地")
    private String remark;

    private Long deptId;

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getTempAfternoon()
    {
        return tempAfternoon;
    }
    public void setRecordCough(Boolean recordCough)
    {
        this.recordCough = recordCough;
    }

    public Boolean getRecordCough()
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
    public void setOtherCough(Boolean otherCough)
    {
        this.otherCough = otherCough;
    }

    public Boolean getOtherCough()
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
    public void setOutsideLife(Boolean outsideLife)
    {
        this.outsideLife = outsideLife;
    }

    public Boolean getOutsideLife()
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

    public Boolean getHealth() {
        return health;
    }

    public void setHealth(Boolean health) {
        this.health = health;
    }

    public Boolean getFever() {
        return fever;
    }

    public void setFever(Boolean fever) {
        this.fever = fever;
    }

    public Boolean getWeak() {
        return weak;
    }

    public void setWeak(Boolean weak) {
        this.weak = weak;
    }

    public Boolean getCough() {
        return cough;
    }

    public void setCough(Boolean cough) {
        this.cough = cough;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
