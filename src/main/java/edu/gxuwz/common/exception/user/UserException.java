package edu.gxuwz.common.exception.user;

import edu.gxuwz.common.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author epmc
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }

}
