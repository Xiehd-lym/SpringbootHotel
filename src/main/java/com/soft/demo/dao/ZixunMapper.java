package com.soft.demo.dao;

import com.soft.demo.domain.Zixun;
import com.soft.demo.domain.ZixunExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ZixunMapper {
    long countByExample(ZixunExample example);

    int deleteByExample(ZixunExample example);

    int insert(Zixun record);

    int insertSelective(Zixun record);

    List<Zixun> selectByExample(ZixunExample example);

    int updateByExampleSelective(@Param("record") Zixun record, @Param("example") ZixunExample example);

    int updateByExample(@Param("record") Zixun record, @Param("example") ZixunExample example);
}