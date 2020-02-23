package edu.gxuwz.project.system.record.service;

import edu.gxuwz.project.system.record.domain.Record;

import java.sql.Date;
import java.util.List;

/**
 * 记录Service接口
 * 
 * @author epmc
 * @date 2020-02-20
 */
public interface IRecordService 
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
     * 查询记录列表
     *
     * @param record 记录
     * @return 记录集合
     */
    public List<Record> selectRecordList1(Record record);

    public List<Record> bumen(Record record);
    public List<Record> geren(Record record);
    public List<Record> xuesheng(Record record);
    public List<Record> jiaozhigong(Record record);

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
     * 批量删除记录
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteRecordByIds(String ids);

    /**
     * 删除记录信息
     * 
     * @param recordId 记录ID
     * @return 结果
     */
    public int deleteRecordById(Long recordId);

    /**
     * 查询当天记录
     * @param recordDate
     * @param recordNumber
     * @return
     */
    Record selectRecordByDateAndId(Date recordDate, String recordNumber);

    /**
     * 异常统计
     * @param record
     * @return
     */
    List<Record> selectYichang(Record record);

}
