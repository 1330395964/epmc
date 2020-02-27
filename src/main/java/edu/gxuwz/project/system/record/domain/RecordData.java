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

    @Excel(name = "学生异常人数")
    private Object countRecordStudent;

    @Excel(name = "教职工异常人数")
    private Object countRecordTeacher;

    @Excel(name = "学生填报人数")
    private Object countStudent;

    @Excel(name = "学生应报人数")
    private Object countStudentShuld;

    @Excel(name = "教职工填报人数")
    private Object countTeacher;

    @Excel(name = "教职工应报人数")
    private Object countTeacherShuld;

    @Excel(name = "统计日期")
    private String date;

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

    public Object getCountRecordStudent() {
        return countRecordStudent;
    }

    public void setCountRecordStudent(Object countRecordStudent) {
        this.countRecordStudent = countRecordStudent;
    }

    public Object getCountRecordTeacher() {
        return countRecordTeacher;
    }

    public void setCountRecordTeacher(Object countRecordTeacher) {
        this.countRecordTeacher = countRecordTeacher;
    }

    public Object getCountStudent() {
        return countStudent;
    }

    public void setCountStudent(Object countStudent) {
        this.countStudent = countStudent;
    }

    public Object getCountTeacher() {
        return countTeacher;
    }

    public void setCountTeacher(Object countTeacher) {
        this.countTeacher = countTeacher;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getCountStudentShuld() {
        return countStudentShuld;
    }

    public void setCountStudentShuld(Object countStudentShuld) {
        this.countStudentShuld = countStudentShuld;
    }

    public Object getCountTeacherShuld() {
        return countTeacherShuld;
    }

    public void setCountTeacherShuld(Object countTeacherShuld) {
        this.countTeacherShuld = countTeacherShuld;
    }

    @Override
    public String toString() {
        return "RecordData{" +
                "index=" + index +
                ", dept=" + dept +
                ", countRecordStudent=" + countRecordStudent +
                ", countRecordTeacher=" + countRecordTeacher +
                ", countStudent=" + countStudent +
                ", countStudentShuld=" + countStudentShuld +
                ", countTeacher=" + countTeacher +
                ", countTeacherShuld=" + countTeacherShuld +
                ", date='" + date + '\'' +
                '}';
    }
}
