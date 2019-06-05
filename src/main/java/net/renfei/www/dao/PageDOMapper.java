package net.renfei.www.dao;

import java.util.List;
import net.renfei.www.entity.dbo.PageDO;
import net.renfei.www.entity.dbo.PageDOExample;
import net.renfei.www.entity.dbo.PageDOWithBLOBs;
import org.apache.ibatis.annotations.Param;

public interface PageDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    long countByExample(PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int deleteByExample(PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int insert(PageDOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int insertSelective(PageDOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    List<PageDOWithBLOBs> selectByExampleWithBLOBs(PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    List<PageDO> selectByExample(PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    PageDOWithBLOBs selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int updateByExampleSelective(@Param("record") PageDOWithBLOBs record, @Param("example") PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int updateByExampleWithBLOBs(@Param("record") PageDOWithBLOBs record, @Param("example") PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int updateByExample(@Param("record") PageDO record, @Param("example") PageDOExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int updateByPrimaryKeySelective(PageDOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int updateByPrimaryKeyWithBLOBs(PageDOWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_page
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    int updateByPrimaryKey(PageDO record);
}