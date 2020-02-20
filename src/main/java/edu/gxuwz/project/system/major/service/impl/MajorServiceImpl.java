package edu.gxuwz.project.system.major.service.impl;

import edu.gxuwz.common.utils.text.Convert;
import edu.gxuwz.project.system.major.domain.Major;
import edu.gxuwz.project.system.major.mapper.MajorMapper;
import edu.gxuwz.project.system.major.service.IMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专业Service业务层处理
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Service
public class MajorServiceImpl implements IMajorService 
{
    @Autowired
    private MajorMapper majorMapper;

    /**
     * 查询专业
     * 
     * @param majorId 专业ID
     * @return 专业
     */
    @Override
    public Major selectMajorById(Long majorId)
    {
        return majorMapper.selectMajorById(majorId);
    }

    /**
     * 查询专业列表
     * 
     * @param major 专业
     * @return 专业
     */
    @Override
    public List<Major> selectMajorList(Major major)
    {
        return majorMapper.selectMajorList(major);
    }

    /**
     * 新增专业
     * 
     * @param major 专业
     * @return 结果
     */
    @Override
    public int insertMajor(Major major)
    {
        return majorMapper.insertMajor(major);
    }

    /**
     * 修改专业
     * 
     * @param major 专业
     * @return 结果
     */
    @Override
    public int updateMajor(Major major)
    {
        return majorMapper.updateMajor(major);
    }

    /**
     * 删除专业对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMajorByIds(String ids)
    {
        return majorMapper.deleteMajorByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除专业信息
     * 
     * @param majorId 专业ID
     * @return 结果
     */
    @Override
    public int deleteMajorById(Long majorId)
    {
        return majorMapper.deleteMajorById(majorId);
    }
}
