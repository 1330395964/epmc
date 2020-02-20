package edu.gxuwz.project.system.major.mapper;

import edu.gxuwz.project.system.major.domain.Major;
import java.util.List;

/**
 * 专业Mapper接口
 * 
 * @author epmc
 * @date 2020-02-20
 */
public interface MajorMapper 
{
    /**
     * 查询专业
     * 
     * @param majorId 专业ID
     * @return 专业
     */
    public Major selectMajorById(Long majorId);

    /**
     * 查询专业列表
     * 
     * @param major 专业
     * @return 专业集合
     */
    public List<Major> selectMajorList(Major major);

    /**
     * 新增专业
     * 
     * @param major 专业
     * @return 结果
     */
    public int insertMajor(Major major);

    /**
     * 修改专业
     * 
     * @param major 专业
     * @return 结果
     */
    public int updateMajor(Major major);

    /**
     * 删除专业
     * 
     * @param majorId 专业ID
     * @return 结果
     */
    public int deleteMajorById(Long majorId);

    /**
     * 批量删除专业
     * 
     * @param majorIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMajorByIds(String[] majorIds);
}
