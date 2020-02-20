package edu.gxuwz.project.system.grade.service.impl;

import edu.gxuwz.common.utils.text.Convert;
import edu.gxuwz.project.system.grade.domain.Grade;
import edu.gxuwz.project.system.grade.mapper.GradeMapper;
import edu.gxuwz.project.system.grade.service.IGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 班级Service业务层处理
 * 
 * @author epmc
 * @date 2020-02-20
 */
@Service
public class GradeServiceImpl implements IGradeService 
{
    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 查询班级
     * 
     * @param gradeId 班级ID
     * @return 班级
     */
    @Override
    public Grade selectGradeById(Long gradeId)
    {
        return gradeMapper.selectGradeById(gradeId);
    }

    /**
     * 查询班级列表
     * 
     * @param grade 班级
     * @return 班级
     */
    @Override
    public List<Grade> selectGradeList(Grade grade)
    {
        return gradeMapper.selectGradeList(grade);
    }

    /**
     * 新增班级
     * 
     * @param grade 班级
     * @return 结果
     */
    @Override
    public int insertGrade(Grade grade)
    {
        return gradeMapper.insertGrade(grade);
    }

    /**
     * 修改班级
     * 
     * @param grade 班级
     * @return 结果
     */
    @Override
    public int updateGrade(Grade grade)
    {
        return gradeMapper.updateGrade(grade);
    }

    /**
     * 删除班级对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteGradeByIds(String ids)
    {
        return gradeMapper.deleteGradeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除班级信息
     * 
     * @param gradeId 班级ID
     * @return 结果
     */
    @Override
    public int deleteGradeById(Long gradeId)
    {
        return gradeMapper.deleteGradeById(gradeId);
    }

    @Override
    public List<String> selectAllGrade() {
        return gradeMapper.selectAllGrade();
    }
}
