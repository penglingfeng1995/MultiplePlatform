package com.plf.mp.mapper;

import com.plf.mp.model.MpUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author plf
 */
@Repository
public interface MpUserMapper {

    /**
     * 查询用户根据用户名
     * @param username 用户名
     * @return 用户
     */
    MpUser getUserByUsername(@Param("username") String username);
}
