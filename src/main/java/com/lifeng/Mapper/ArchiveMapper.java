package com.lifeng.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ArchiveMapper {
    int updateArchiveStatus(@Param("archiveId") Long archiveId);
    int insertArchiveLog(@Param("archiveId") Long archiveId, @Param("logContent") String logContent);
}
