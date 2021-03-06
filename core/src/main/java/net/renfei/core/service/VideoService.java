package net.renfei.core.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.core.entity.VideoDTO;
import net.renfei.core.entity.VideoListDTO;
import net.renfei.dao.entity.*;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@CacheConfig(cacheNames = "VideoService")
public class VideoService extends BaseService {

    /**
     * 获取全部视频并分页
     *
     * @param pages 页码
     * @param rows  每页容量
     * @return
     */
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1", condition = "#p0!=null&&#p1!=null")
    public VideoListDTO getAllVideo(String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        VideoDOExample videoDOExample = new VideoDOExample();
        videoDOExample.setOrderByClause("release_time DESC");
        videoDOExample.createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(intPage, intRows);
        List<VideoDOWithBLOBs> videoDOWithBLOBs = videoDOMapper.selectByExampleWithBLOBs(videoDOExample);
        return convert(videoDOWithBLOBs, page.getTotal());
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0+'_'+#p1+'_'+#p2", condition = "#p0!=null&&#p1!=null&&#p2!=null")
    public VideoListDTO getAllVideoByCatID(Long catID, String pages, String rows) {
        int intPage = convertPage(pages), intRows = convertRows(rows);
        VideoDOExample videoDOExample = new VideoDOExample();
        videoDOExample.setOrderByClause("release_time DESC");
        videoDOExample.createCriteria()
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date())
                .andCategoryIdEqualTo(catID);
        Page page = PageHelper.startPage(intPage, intRows);
        List<VideoDOWithBLOBs> videoDOWithBLOBs = videoDOMapper.selectByExampleWithBLOBs(videoDOExample);
        return convert(videoDOWithBLOBs, page.getTotal());
    }

    public Long getCountByCategoryId(Long catID) {
        VideoDOExample videoDOExample = new VideoDOExample();
        videoDOExample.createCriteria()
                .andCategoryIdEqualTo(catID)
                .andIsDeleteEqualTo(false)
                .andReleaseTimeLessThanOrEqualTo(new Date());
        Page page = PageHelper.startPage(1, 1);
        videoDOMapper.selectByExampleWithBLOBs(videoDOExample);
        return page.getTotal();
    }

    /**
     * 点赞
     *
     * @param id
     */
    public void thumbsUp(String id) {
        VideoDTO videoDTO = getVideoByID(id, false);
        if (videoDTO != null) {
            VideoDOWithBLOBs videoDOWithBLOBs = new VideoDOWithBLOBs();
            videoDOWithBLOBs.setId(videoDTO.getId());
            videoDOWithBLOBs.setThumbsUp(videoDTO.getThumbsUp() + 1);
            updateVideo(videoDOWithBLOBs);
        }
    }

    /**
     * 点踩
     *
     * @param id
     */
    public void thumbsDown(String id) {
        VideoDTO videoDTO = getVideoByID(id, false);
        if (videoDTO != null) {
            VideoDOWithBLOBs videoDOWithBLOBs = new VideoDOWithBLOBs();
            videoDOWithBLOBs.setId(videoDTO.getId());
            videoDOWithBLOBs.setThumbsDown(videoDTO.getThumbsDown() + 1);
            updateVideo(videoDOWithBLOBs);
        }
    }

    public int updateVideo(VideoDOWithBLOBs videoDOWithBLOBs){
        return videoDOMapper.updateByPrimaryKeySelective(videoDOWithBLOBs);
    }

    /**
     * 根据ID获取视频
     *
     * @param id 视频ID
     * @return
     */
    public VideoDTO getVideoByID(String id, boolean updateView) {
        Long ID = 0L;
        if (!stringUtil.isEmpty(id)) {
            try {
                ID = Long.valueOf(id);
                VideoDOExample videoDOExample = new VideoDOExample();
                videoDOExample.createCriteria()
                        .andIsDeleteEqualTo(false)
                        .andReleaseTimeLessThanOrEqualTo(new Date())
                        .andIdEqualTo(ID);
                List<VideoDOWithBLOBs> videoDOWithBLOBsList = videoDOMapper.selectByExampleWithBLOBs(videoDOExample);
                if (videoDOWithBLOBsList != null && videoDOWithBLOBsList.size() > 0) {
                    VideoDTO videoDTO = new VideoDTO();
                    videoDTO = ejbGenerator.convert(videoDOWithBLOBsList.get(0), VideoDTO.class);
                    videoDTO.setVideoSource(getvideoSourceByVideoId(videoDTO.getId()));
                    videoDTO.setVideoTrack(getVideoTrackByVideoId(videoDTO.getId()));
                    if (updateView) {
                        updateView(videoDOWithBLOBsList.get(0));
                    }
                    return videoDTO;
                } else {
                    return null;
                }
            } catch (NumberFormatException nfe) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 根据ID获取视频
     *
     * @param id 视频ID
     * @return
     */
    public VideoDTO getVideoByID(String id) {
        return getVideoByID(id, true);
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public List<VideoSourceDOWithBLOBs> getvideoSourceByVideoId(Long videoId) {
        VideoSourceDOExample videoSourceDOExample = new VideoSourceDOExample();
        videoSourceDOExample.createCriteria()
                .andVideoIdEqualTo(videoId);
        return videoSourceDOMapper.selectByExampleWithBLOBs(videoSourceDOExample);
    }

    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public List<VideoTrackDOWithBLOBs> getVideoTrackByVideoId(Long videoId) {
        VideoTrackDOExample videoTrackDOExample = new VideoTrackDOExample();
        videoTrackDOExample.createCriteria()
                .andVideoIdEqualTo(videoId);
        return videoTrackDOMapper.selectByExampleWithBLOBs(videoTrackDOExample);
    }

    private VideoListDTO convert(List<VideoDOWithBLOBs> videoDOWithBLOBs, Long count) {
        VideoListDTO videoListDTO = new VideoListDTO();
        videoListDTO.setVideos(videoDOWithBLOBs);
        videoListDTO.setCount(count);
        return videoListDTO;
    }
}
