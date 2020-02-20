package edu.gxuwz.project.system.grade.service;

import edu.gxuwz.project.system.grade.domain.Grade;
import java.util.List;

/**
 * 班级Service接口
 * 
 * @author epmc
 * @date 2020-02-20
 */
public interface IGradeService 
{
    /**
     * 查询班级
     * 
     * @param gradeId 班级ID
     * @return 班级
     */
    public Grade selectGradeById(Long gradeId);

    /**
     * 查询班级列表
     * 
     * @param grade 班级
     * @return 班级集合
     */
    public List<Grade> selectGradeList(Grade grade);

    /**
     * 新增班级
     * 
     * @param grade 班级
     * @return 结果
     */
    public int insertGrade(Grade grade);

    /**
     * 修改班级
     * 
     * @param grade 班级
     * @return 结果
     */
    public int updateGrade(Grade grade);

    /**
     * 批量删除班级
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGradeByIds(String ids);

    /**
     * 删除班级信息
     * 
     * @param gradeId 班级ID
     * @return 结果
     */
    public int deleteGradeById(Long gradeId);

    /**
     * 所有年级
     * @return
     */
    public List<String> selectAllGrade();
}
