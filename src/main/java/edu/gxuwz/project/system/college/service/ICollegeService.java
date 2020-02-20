package edu.gxuwz.project.system.college.service;

import edu.gxuwz.project.system.college.domain.College;
import java.util.List;

/**
 * 二级学院与部门Service接口
 * 
 * @author epmc
 * @date 2020-02-20
 */
public interface ICollegeService 
{
    /**
     * 查询二级学院与部门
     * 
     * @param collegeId 二级学院与部门ID
     * @return 二级学院与部门
     */
    public College selectCollegeById(Long collegeId);

    /**
     * 查询二级学院与部门列表
     * 
     * @param college 二级学院与部门
     * @return 二级学院与部门集合
     */
    public List<College> selectCollegeList(College college);

    /**
     * 新增二级学院与部门
     * 
     * @param college 二级学院与部门
     * @return 结果
     */
    public int insertCollege(College college);

    /**
     * 修改二级学院与部门
     * 
     * @param college 二级学院与部门
     * @return 结果
     */
    public int updateCollege(College college);

    /**
     * 批量删除二级学院与部门
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCollegeByIds(String ids);

    /**
     * 删除二级学院与部门信息
     * 
     * @param collegeId 二级学院与部门ID
     * @return 结果
     */
    public int deleteCollegeById(Long collegeId);
}
