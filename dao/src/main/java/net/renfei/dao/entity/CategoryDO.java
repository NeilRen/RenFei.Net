package net.renfei.dao.entity;

public class CategoryDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_category.id
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_category.type_id
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    private Long typeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_category.en_name
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    private String enName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_category.zh_name
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    private String zhName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_category.featured_image
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    private String featuredImage;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_category.id
     *
     * @return the value of t_category.id
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_category.id
     *
     * @param id the value for t_category.id
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_category.type_id
     *
     * @return the value of t_category.type_id
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public Long getTypeId() {
        return typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_category.type_id
     *
     * @param typeId the value for t_category.type_id
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_category.en_name
     *
     * @return the value of t_category.en_name
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public String getEnName() {
        return enName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_category.en_name
     *
     * @param enName the value for t_category.en_name
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_category.zh_name
     *
     * @return the value of t_category.zh_name
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public String getZhName() {
        return zhName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_category.zh_name
     *
     * @param zhName the value for t_category.zh_name
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public void setZhName(String zhName) {
        this.zhName = zhName == null ? null : zhName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_category.featured_image
     *
     * @return the value of t_category.featured_image
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public String getFeaturedImage() {
        return featuredImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_category.featured_image
     *
     * @param featuredImage the value for t_category.featured_image
     *
     * @mbg.generated Fri Jun 21 18:08:07 CST 2019
     */
    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage == null ? null : featuredImage.trim();
    }
}