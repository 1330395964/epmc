package edu.gxuwz.project.system.user.domain;

import edu.gxuwz.framework.aspectj.lang.annotation.Excel;
import edu.gxuwz.framework.aspectj.lang.annotation.Excels;
import edu.gxuwz.framework.web.domain.BaseEntity;
import edu.gxuwz.project.system.dept.domain.Dept;
import edu.gxuwz.project.system.role.domain.Role;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 * 
 * @author epmc
 */
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @Excel(name = "用户序号", cellType = Excel.ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;

    /** 部门ID */
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /** 部门父ID */
    private Long parentId;

    /** 角色ID */
    private Long roleId;

    /** 登录名称 */
    @Excel(name = "登录名称")
    private String loginName;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    /** 用户邮箱 */
    @Excel(name = "用户邮箱")
    private String email;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 用户性别 */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 密码 */
    private String password;

    /** 盐加密 */
    private String salt;

    /** 帐号状态（0正常 1停用） */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登陆IP */
    @Excel(name = "最后登陆IP", type = Excel.Type.EXPORT)
    private String loginIp;

    /** 最后登陆时间 */
    @Excel(name = "最后登陆时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date loginDate;

    /**
     * 是否学生true学生false教师管理者
     */
    @Excel(name = "是否学生", readConverterExp = "true=学生,false=职工")
    private Boolean student;

    /**
     *家长或联系人
     */
    @Excel(name = "家长或联系人电话")
    private String parentsName;

    /**
     * 家长或联系人电话
     */
    @Excel(name = "家长或联系人电话")
    private String parentsPhnu;

    /**
     * 所在省份
     */
    @Excel(name = "所在省份")
    private String province;

    /**
     * 所在城市
     */
    @Excel(name = "所在城市")
    private String cityName;

    /**
     * 所在区、县
     */
    @Excel(name = "所在区、县")
    private String county;

    /**
     * 详细住址
     */
    @Excel(name = "详细住址")
    private String address;

    /**
     * 0非毕业生1毕业生
     */
    @Excel(name = "是否毕业生", readConverterExp = "0=非毕业生,1=毕业生")
    private String graduates;

    /**
     * 0在校1在岗位2离岗
     */
    @Excel(name = "是否在工作岗位", readConverterExp = "0=在校,1=在岗位,2=离岗")
    private String onJob;

    /**
     * 岗位实习岗位
     */
    @Excel(name = "岗位实习地点")
    private String workplace;

    /**
     * 0近期未去过武汉1去过
     */
    @Excel(name = "是否近期去过武汉", readConverterExp = "0=未去过,1=有去过")
    private String afterWuhan;

    /**
     * 0未接触感染或确诊人员1接触过
     */
    @Excel(name = "是否有感染接触", readConverterExp = "0=未接触,1=有接触")
    private String contactWuhan;

    /**
     * 班级
     */
    @Excel(name = "班级")
    private String gradeId;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public Boolean getStudent() {
        return student;
    }

    public void setStudent(Boolean student) {
        this.student = student;
    }

    public String getParentsName() {
        return parentsName;
    }

    public void setParentsName(String parentsName) {
        this.parentsName = parentsName;
    }

    public String getParentsPhnu() {
        return parentsPhnu;
    }

    public void setParentsPhnu(String parentsPhnu) {
        this.parentsPhnu = parentsPhnu;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGraduates() {
        return graduates;
    }

    public void setGraduates(String graduates) {
        this.graduates = graduates;
    }

    public String getOnJob() {
        return onJob;
    }

    public void setOnJob(String onJob) {
        this.onJob = onJob;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getAfterWuhan() {
        return afterWuhan;
    }

    public void setAfterWuhan(String afterWuhan) {
        this.afterWuhan = afterWuhan;
    }

    public String getContactWuhan() {
        return contactWuhan;
    }

    public void setContactWuhan(String contactWuhan) {
        this.contactWuhan = contactWuhan;
    }

    @Excel(name = "身份证号")
    private String cardNu;

    public String getCardNu() {
        return cardNu;
    }

    public void setCardNu(String cardNu) {
        this.cardNu = cardNu;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", deptId=" + deptId +
                ", parentId=" + parentId +
                ", roleId=" + roleId +
                ", loginName='" + loginName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate=" + loginDate +
                ", student=" + student +
                ", parentsName='" + parentsName + '\'' +
                ", parentsPhnu='" + parentsPhnu + '\'' +
                ", province='" + province + '\'' +
                ", cityName='" + cityName + '\'' +
                ", county='" + county + '\'' +
                ", address='" + address + '\'' +
                ", graduates='" + graduates + '\'' +
                ", onJob='" + onJob + '\'' +
                ", workplace='" + workplace + '\'' +
                ", afterWuhan='" + afterWuhan + '\'' +
                ", contactWuhan='" + contactWuhan + '\'' +
                ", gradeId=" + gradeId +
                ", cardNu='" + cardNu + '\'' +
                ", dept=" + dept +
                ", roles=" + roles +
                ", roleIds=" + Arrays.toString(roleIds) +
                ", postIds=" + Arrays.toString(postIds) +
                '}';
    }

    /** 部门对象 */
    @Excels({
        @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
        @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private Dept dept;

    private List<Role> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    public User()
    {

    }

    public User(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

    @NotBlank(message = "登录账号不能为空")
    @Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符")
    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    /**
     * 生成随机盐
     */
    public void randomSalt()
    {
        // 一个Byte占两个字节，此处生成的3字节，字符串长度为6
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String hex = secureRandom.nextBytes(3).toHex();
        setSalt(hex);
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public Dept getDept()
    {
        if (dept == null)
        {
            dept = new Dept();
        }
        return dept;
    }

    public void setDept(Dept dept)
    {
        this.dept = dept;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

}
