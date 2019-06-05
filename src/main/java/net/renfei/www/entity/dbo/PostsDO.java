package net.renfei.www.entity.dbo;

import java.util.Date;

public class PostsDO {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.id
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.category_id
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Long categoryId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.is_original
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Boolean isOriginal;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.views
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Long views;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.release_time
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Date releaseTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.add_time
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Date addTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_posts.is_delete
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    private Boolean isDelete;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.id
     *
     * @return the value of t_posts.id
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.id
     *
     * @param id the value for t_posts.id
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.category_id
     *
     * @return the value of t_posts.category_id
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Long getCategoryId() {
        return categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.category_id
     *
     * @param categoryId the value for t_posts.category_id
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.is_original
     *
     * @return the value of t_posts.is_original
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Boolean getIsOriginal() {
        return isOriginal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.is_original
     *
     * @param isOriginal the value for t_posts.is_original
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setIsOriginal(Boolean isOriginal) {
        this.isOriginal = isOriginal;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.views
     *
     * @return the value of t_posts.views
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Long getViews() {
        return views;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.views
     *
     * @param views the value for t_posts.views
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setViews(Long views) {
        this.views = views;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.release_time
     *
     * @return the value of t_posts.release_time
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.release_time
     *
     * @param releaseTime the value for t_posts.release_time
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.add_time
     *
     * @return the value of t_posts.add_time
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.add_time
     *
     * @param addTime the value for t_posts.add_time
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_posts.is_delete
     *
     * @return the value of t_posts.is_delete
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public Boolean getIsDelete() {
        return isDelete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_posts.is_delete
     *
     * @param isDelete the value for t_posts.is_delete
     *
     * @mbg.generated Wed Jun 05 18:24:36 CST 2019
     */
    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}