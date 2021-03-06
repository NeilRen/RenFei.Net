package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.PostsExtraDO;
import net.renfei.dao.entity.PostsExtraDOExample;
import org.apache.ibatis.annotations.Param;

public interface PostsExtraDOMapper {
    long countByExample(PostsExtraDOExample example);

    int deleteByExample(PostsExtraDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PostsExtraDO record);

    int insertSelective(PostsExtraDO record);

    List<PostsExtraDO> selectByExampleWithBLOBs(PostsExtraDOExample example);

    List<PostsExtraDO> selectByExample(PostsExtraDOExample example);

    PostsExtraDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PostsExtraDO record, @Param("example") PostsExtraDOExample example);

    int updateByExampleWithBLOBs(@Param("record") PostsExtraDO record, @Param("example") PostsExtraDOExample example);

    int updateByExample(@Param("record") PostsExtraDO record, @Param("example") PostsExtraDOExample example);

    int updateByPrimaryKeySelective(PostsExtraDO record);

    int updateByPrimaryKeyWithBLOBs(PostsExtraDO record);

    int updateByPrimaryKey(PostsExtraDO record);
}