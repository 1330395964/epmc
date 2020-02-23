package edu.gxuwz.project.system.record.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.web.domain.BaseEntity;
import edu.gxuwz.project.system.dept.domain.Dept;

/**
 * 导出记录统计数据
 */
public class RecordData extends BaseEntity {

    @Excel(name = "序号")
    private Long index;

    @Excel(name = "部门", targetAttr = "deptName")
    private Dept dept;

    @Excel(name = "填报总人数")
    private Long countNu;

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


    public RecordData(){}

    public RecordData(Record record){

    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public Long getCountNu() {
        return countNu;
    }

    public void setCountNu(Long countNu) {
        this.countNu = countNu;
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

    public Boolean getCough() {
        return cough;
    }

    public void setCough(Boolean cough) {
        this.cough = cough;
    }

    public Boolean getWeak() {
        return weak;
    }

    public void setWeak(Boolean weak) {
        this.weak = weak;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "index=" + index +
                ", dept=" + dept +
                ", countNu=" + countNu +
                ", health=" + health +
                ", fever=" + fever +
                ", cough=" + cough +
                ", weak=" + weak +
                ", remark='" + remark + '\'' +
                '}';
    }
}
