package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.PostsDTO;
import net.renfei.core.entity.PostsListDTO;
import net.renfei.dao.entity.*;
import net.renfei.util.PageRankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章服务
 */
@Service
@CacheConfig(cacheNames = "posts")
public class PostsService extends BaseService {
    private static final Double DateWeighted = 37.5D;
    private static final Double ViewWeighted = 57.5D;
    private static final Double Commenthted = 5D;
    @Autowired
    private TagService tagService;
    @Autowired
    private DownloadService downloadService;
    @Autowired
    protected GlobalService globalService;

    public int updatePost(PostsDOWithBLOBs postsDOWithBLOBs) {
        return postsDOMapper.updateByPrimaryKeySelective(postsDOWithBLOBs);
    }

    public PostsDOWithBLOBs addPost(PostsDOWithBLOBs postsDOWithBLOBs) {
        postsDOWithBLOBs.setAddTime(new Date());
        postsDOWithBLOBs.setIsDelete(false);
        postsDOWithBLOBs.setPageRank(10000D);
        postsDOMapper.insertSelective(postsDOWithBLOBs);
        return postsDOWithBLOBs;
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void thumbsUp(String id) {
        PostsDO postsDO = getPostsByID(id, false);
        if (postsDO != null) {
            PostsDOWithBLOBs postsDOWithBLOBs = new PostsDOWithBLOBs();
            postsDOWithBLOBs.setId(postsDO.getId());
            postsDOWithBLOBs.setThumbsUp(postsDO.getThumbsUp() + 1);
            updatePost(postsDOWithBLOBs);
        }
    }

    /**
     * 点踩
     *
     * @param id
     */
    public void thumbsDown(String id) {
        PostsDO postsDO = getPostsByID(id, false);
        if (postsDO != null) {
            PostsDOWithBLOBs postsDOWithBLOBs = new PostsDOWithBLOBs();
            postsDOWithBLOBs.setId(postsDO.getId());
            postsDOWithBLOBs.setThumbsDown(postsDO.getThumbsDown() + 1);
            updatePost(postsDOWithBLOBs);
        }
    }

    /**
     * 获取文章列表
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    public PostsListDTO getAllPosts(String pages, String rows) {
        return getAllPosts(pages, rows, "page_rank DESC,release_time DESC");
    }

    /**
     * 获取文章列表
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public PostsListDTO getAllPosts(String pages, String rows, String orderBy) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample.setOrderByClause(orderBy);
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        return doSelect(postsDOExample, intPage, intRows);
    }

    /**
     * 获取文章列表
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    public PostsListDTO getAdminPosts(String pages, String rows, String orderBy) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample.setOrderByClause(orderBy);
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false);
        return doSelect(postsDOExample, intPage, intRows);
    }

    /**
     * 获取文章列表
     *
     * @param pages     页码
     * @param rows      每页容量
     * @param tagEnName 标签
     * @return
     */
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public PostsListDTO getAllPostsByTag(String pages, String rows, String tagEnName) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        List<TagRelationDO> tagRelationDOS = tagService.getTagRelationByEnName(tagEnName, 1L);
        if (tagRelationDOS != null && tagRelationDOS.size() > 0) {
            List<Long> ids = new ArrayList<>();
            for (TagRelationDO tag : tagRelationDOS
            ) {
                ids.add(tag.getTargetId());
            }

            PostsDOExample postsDOExample = new PostsDOExample();
            postsDOExample.setOrderByClause("page_rank DESC,release_time DESC");
            postsDOExample
                    .createCriteria()
                    .andIsDeleteEqualTo(false)
                    .andReleaseTimeLessThanOrEqualTo(new Date())
                    .andIdIn(ids);
            return doSelect(postsDOExample, intPage, intRows);
        }
        return null;
    }

    /**
     * 获取相关文章
     *
     * @return
     */
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public PostsListDTO getRelated(String id) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
            } catch (NumberFormatException nfe) {
                return null;
            }
            PostsDOExample postsDOExample = new PostsDOExample();
            postsDOExample.createCriteria()
                    .andIdEqualTo(ID)
                    .andReleaseTimeLessThanOrEqualTo(new Date())
                    .andIsDeleteEqualTo(false);
            List<PostsDOWithBLOBs> postsDOWithBLOBs = postsDOMapper.selectByExampleWithBLOBs(postsDOExample);
            if (postsDOWithBLOBs != null && postsDOWithBLOBs.size() > 0) {
                PostsDOWithBLOBs postsDOWithBLOBs1 = postsDOWithBLOBs.get(0);
                //1、先拿到文章的标签组
                List<TagRelationDO> tagRelationDOS = tagService.getTagRelationByTargetId(postsDOWithBLOBs1.getId());
                List<Long> ids = new ArrayList<>();
                for (TagRelationDO tagRelationDO : tagRelationDOS
                ) {
                    ids.add(tagRelationDO.getTagId());
                }
                //2、根据标签组拿到所有文章id
                List<TagRelationDO> tagRelationDOS1 = tagService.getTagRelationByTagIds(ids);
                ids = new ArrayList<>();
                for (TagRelationDO tagRelationDO : tagRelationDOS1
                ) {
                    ids.add(tagRelationDO.getTargetId());
                }
                //3、根据文章ID获得所有文章
                postsDOExample = new PostsDOExample();
                postsDOExample.setDistinct(true);
                postsDOExample.setOrderByClause("page_rank DESC,release_time DESC");
                postsDOExample.createCriteria()
                        .andIdIn(ids)
                        .andIsDeleteEqualTo(false)
                        .andIdNotEqualTo(ID)
                        .andReleaseTimeLessThanOrEqualTo(new Date());
                return doSelect(postsDOExample, 1, 10);
            }
        }
        return null;
    }

    /**
     * 执行查询并转换
     *
     * @param postsDOExample
     * @param ipage
     * @param rows
     * @return
     */
    private PostsListDTO doSelect(PostsDOExample postsDOExample, int ipage, int rows) {
        Page page = PageHelper.startPage(ipage, rows);
        List<PostsDOWithBLOBs> postsDOWithBLOBs = postsDOMapper.selectByExampleWithBLOBs(postsDOExample);
        for (PostsDOWithBLOBs p : postsDOWithBLOBs
        ) {
            if (stringUtil.isEmpty(p.getDescribes())) {
                p.setDescribes(stringUtil.getTextFromHtml(p.getContent()));
            }
        }
        return convert(postsDOWithBLOBs, page.getTotal());
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public PostsListDTO getAllPostsByCatID(Long catID, String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample.setOrderByClause("page_rank DESC,release_time DESC");
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andCategoryIdEqualTo(catID)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(intPage, intRows);
        return convert(postsDOMapper.selectByExampleWithBLOBs(postsDOExample), page.getTotal());
    }

    public Long getCountByCategoryId(Long catId) {
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample
                .createCriteria()
                .andIsDeleteEqualTo(false)
                .andCategoryIdEqualTo(catId)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(1, 1);
        postsDOMapper.selectByExampleWithBLOBs(postsDOExample);
        return page.getTotal();
    }

    /**
     * 根据ID获取文章
     *
     * @param id          文章编号
     * @param recordViews 是否记录浏览量
     * @return
     */
    public PostsDTO getPostsByID(String id, boolean recordViews) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                PostsDOExample postsDOExample = new PostsDOExample();
                postsDOExample.createCriteria()
                        .andIdEqualTo(ID)
                        .andReleaseTimeLessThanOrEqualTo(new Date())
                        .andIsDeleteEqualTo(false);
                List<PostsDOWithBLOBs> postsDOWithBLOBs = postsDOMapper.selectByExampleWithBLOBs(postsDOExample);
                if (postsDOWithBLOBs != null && postsDOWithBLOBs.size() > 0) {
                    PostsDOWithBLOBs postsDOWithBLOBs1 = postsDOWithBLOBs.get(0);
                    if (recordViews) {
                        updateView(postsDOWithBLOBs1);
                    }
                    return ejbGenerator.convert(postsDOWithBLOBs1, PostsDTO.class);
                }
                return null;
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void addAds(PostsDTO postsDTO) {
        String[] conts = postsDTO.getContent().split("</p>");
        if (conts.length > 5) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < conts.length; i++) {
                if (i == 4) {
                    sb.append("<ins class=\"adsbygoogle\"\n");
                    sb.append("     style=\"display:block; text-align:center;\"\n");
                    sb.append("     data-ad-layout=\"in-article\"\n");
                    sb.append("     data-ad-format=\"fluid\"\n");
                    sb.append("     data-ad-client=\"ca-pub-8859756463807757\"\n");
                    sb.append("     data-ad-slot=\"4815931302\"></ins>\n");
                    sb.append("<script>(adsbygoogle = window.adsbygoogle || []).push({});</script>");
                }
                sb.append(conts[i]);
                sb.append("</p>");
            }
            postsDTO.setContent(sb.toString());
        }
    }

    /**
     * 获取文章扩展信息
     *
     * @param id 文章ID
     * @param mv 视图对象
     */
    public void getPostsExtraByID(String id, ModelAndView mv) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                PostsExtraDOExample postsExtraDOExample = new PostsExtraDOExample();
                postsExtraDOExample.createCriteria()
                        .andPostIdEqualTo(ID);
                List<PostsExtraDO> postsExtraDOS = postsExtraDOMapper.selectByExampleWithBLOBs(postsExtraDOExample);
                if (postsExtraDOS != null && postsExtraDOS.size() > 0) {
                    for (PostsExtraDO postsExtra : postsExtraDOS
                    ) {
                        if ("download".equals(postsExtra.getExtraType())) {
                            //文章扩展下载服务
                            DownloadDO downloadDO = downloadService.getDownloadInfoById(postsExtra.getExtraValue());
                            mv.addObject("downloadDO", downloadDO);
                        }
                        //....可以扩展其他类型
                    }
                }
            } catch (NumberFormatException nfe) {
            }
        }
    }

    public int deletePostById(Long id) {
        return postsDOMapper.deleteByPrimaryKey(id);
    }

    private PostsListDTO convert(List<PostsDOWithBLOBs> postsDOWithBLOBs, Long count) {
        PostsListDTO postsListDTO = new PostsListDTO();
        postsListDTO.setPostsList(postsDOWithBLOBs);
        postsListDTO.setCount(count);
        return postsListDTO;
    }

    /**
     * 更新文章的权重
     */
    public void updatePageRank() {
        PostsDOExample postsDOExample = new PostsDOExample();
        postsDOExample.createCriteria();
        List<PostsDO> postsDOS = postsDOMapper.selectByExample(postsDOExample);
        for (PostsDO post : postsDOS
        ) {
            setPageRank(post);
            postsDOMapper.updateByPrimaryKey(post);
        }
    }

    private void setPageRank(PostsDO postsDO) {
        PageRankUtil pageRankUtil = new PageRankUtil();
        postsDO.setPageRank(pageRankUtil.getPageRank(
                postsDO.getReleaseTime(),
                postsDO.getViews(),
                0L,
                DateWeighted, ViewWeighted, Commenthted
        ));
        postsDO.setPageRankUpdateTime(new Date());
    }
}
