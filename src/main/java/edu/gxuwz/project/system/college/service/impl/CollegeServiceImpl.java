package edu.gxuwz.project.system.college.service.impl;

import edu.gxuwz.common.utils.text.Convert;
import edu.gxuwz.project.system.college.domain.College;
import edu.gxuwz.project.system.college.mapper.CollegeMapper;
import edu.gxuwz.project.system.college.service.ICollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 二级学院与部门Service业务层处理
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Service
public class CollegeServiceImpl implements ICollegeService 
{
    @Autowired
    private CollegeMapper collegeMapper;

    /**
     * 查询二级学院与部门
     * 
     * @param collegeId 二级学院与部门ID
     * @return 二级学院与部门
     */
    @Override
    public College selectCollegeById(Long collegeId)
    {
        return collegeMapper.selectCollegeById(collegeId);
    }

    /**
     * 查询二级学院与部门列表
     * 
     * @param college 二级学院与部门
     * @return 二级学院与部门
     */
    @Override
    public List<College> selectCollegeList(College college)
    {
        return collegeMapper.selectCollegeList(college);
    }

    /**
     * 新增二级学院与部门
     * 
     * @param college 二级学院与部门
     * @return 结果
     */
    @Override
    public int insertCollege(College college)
    {
        return collegeMapper.insertCollege(college);
    }

    /**
     * 修改二级学院与部门
     * 
     * @param college 二级学院与部门
     * @return 结果
     */
    @Override
    public int updateCollege(College college)
    {
        return collegeMapper.updateCollege(college);
    }

    /**
     * 删除二级学院与部门对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCollegeByIds(String ids)
    {
        return collegeMapper.deleteCollegeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除二级学院与部门信息
     * 
     * @param collegeId 二级学院与部门ID
     * @return 结果
     */
    @Override
    public int deleteCollegeById(Long collegeId)
    {
        return collegeMapper.deleteCollegeById(collegeId);
    }
}
