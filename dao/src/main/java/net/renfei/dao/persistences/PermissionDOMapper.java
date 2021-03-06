package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.PermissionDO;
import net.renfei.dao.entity.PermissionDOExample;
import org.apache.ibatis.annotations.Param;

public interface PermissionDOMapper {
    long countByExample(PermissionDOExample example);

    int deleteByExample(PermissionDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PermissionDO record);

    int insertSelective(PermissionDO record);

    List<PermissionDO> selectByExampleWithBLOBs(PermissionDOExample example);

    List<PermissionDO> selectByExample(PermissionDOExample example);

    PermissionDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByExampleWithBLOBs(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByExample(@Param("record") PermissionDO record, @Param("example") PermissionDOExample example);

    int updateByPrimaryKeySelective(PermissionDO record);

    int updateByPrimaryKeyWithBLOBs(PermissionDO record);

    int updateByPrimaryKey(PermissionDO record);
}