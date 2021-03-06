package net.renfei.dao.persistences;

import java.util.List;
import net.renfei.dao.entity.TypeDO;
import net.renfei.dao.entity.TypeDOExample;
import org.apache.ibatis.annotations.Param;

public interface TypeDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    long countByExample(TypeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int deleteByExample(TypeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int insert(TypeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int insertSelective(TypeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    List<TypeDO> selectByExample(TypeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    TypeDO selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int updateByExampleSelective(@Param("record") TypeDO record, @Param("example") TypeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int updateByExample(@Param("record") TypeDO record, @Param("example") TypeDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int updateByPrimaryKeySelective(TypeDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_type
     *
     * @mbg.generated Wed Jun 05 18:23:18 CST 2019
     */
    int updateByPrimaryKey(TypeDO record);
}