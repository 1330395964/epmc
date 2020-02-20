package edu.gxuwz.common.exception.file;

import edu.gxuwz.common.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author epmc
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
