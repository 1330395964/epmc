package edu.gxuwz.project.system.record.mapper;

import edu.gxuwz.project.system.record.domain.Record;
import java.util.List;

/**
 * 记录Mapper接口
 * 
 * @author epmc
 * @date 2020-02-20
 */
public interface RecordMapper 
{
    /**
     * 查询记录
     * 
     * @param recordId 记录ID
     * @return 记录
     */
    public Record selectRecordById(Long recordId);

    /**
     * 查询记录列表
     * 
     * @param record 记录
     * @return 记录集合
     */
    public List<Record> selectRecordList(Record record);

    /**
     * 新增记录
     * 
     * @param record 记录
     * @return 结果
     */
    public int insertRecord(Record record);

    /**
     * 修改记录
     * 
     * @param record 记录
     * @return 结果
     */
    public int updateRecord(Record record);

    /**
     * 删除记录
     * 
     * @param recordId 记录ID
     * @return 结果
     */
    public int deleteRecordById(Long recordId);

    /**
     * 批量删除记录
     * 
     * @param recordIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteRecordByIds(String[] recordIds);
}