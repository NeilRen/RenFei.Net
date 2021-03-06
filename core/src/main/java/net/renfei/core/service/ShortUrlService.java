package net.renfei.core.service;

import com.github.pagehelper.PageHelper;
import net.renfei.core.baseclass.BaseService;
import net.renfei.dao.entity.ShortUrl;
import net.renfei.dao.entity.ShortUrlExample;
import net.renfei.util.ListUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 短网址服务(rnf.pw)
 *
 * @author RenFei
 */
@Service
@CacheConfig(cacheNames = "ShortUrlService")
public class ShortUrlService extends BaseService {
    /**
     * 获取ShortUrl对象
     *
     * @param shortUrl 短网址
     * @return
     */
    @Cacheable(key = "targetClass+'_'+methodName+'_'+#p0", condition = "#p0!=null")
    public ShortUrl getShortUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.length() == 0) {
            return null;
        }
        PageHelper.startPage(1, 1);
        ShortUrlExample shortUrlExample = new ShortUrlExample();
        shortUrlExample.setOrderByClause("add_time ASC");
        shortUrlExample.createCriteria()
                .andShortUrlEqualTo(shortUrl)
                .andStateCodeEqualTo(1);
        ShortUrl shortUrl1 = ListUtil.getOne(shortUrlMapper.selectByExample(shortUrlExample));
        if(shortUrl1!=null){
            shortUrl1.setViews(shortUrl1.getViews() + 1);
            updateShortUrl(shortUrl1);
        }
        return shortUrl1;
    }

    @Async
    public void updateShortUrl(ShortUrl shortUrl) {
        shortUrlMapper.updateByPrimaryKeySelective(shortUrl);
    }

    /**
     * 创建一个短网址对象并插入数据库
     *
     * @param url    长网址
     * @param userId 添加用户
     * @return
     */
    public ShortUrl createShortUrl(String url, Long userId) {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setShortUrl(getShortUrl());
        shortUrl.setUrl(url);
        shortUrl.setAddTime(new Date(System.currentTimeMillis()));
        shortUrl.setAddUser(userId);
        shortUrl.setStateCode(1);
        shortUrl.setViews(0L);
        shortUrlMapper.insertSelective(shortUrl);
        return shortUrl;
    }

    private String getShortUrl() {
        ShortUrl lastShortUrl = getLastShortUrl();
        int len = 4;
        if (lastShortUrl != null) {
            len = lastShortUrl.getShortUrl().length();
        }
        return getShortUrl(len);
    }

    private String getShortUrl(int len) {
        String url = stringUtil.getRandomString(len);
        for (int i = 0; !check(url); i++) {
            if (i > 5) {
                len++;
            }
            url = stringUtil.getRandomString(len);
        }
        return url;
    }

    private boolean check(String url) {
        return getShortUrl(url) == null;
    }

    /**
     * 查询最新的一条记录
     *
     * @return
     */
    private ShortUrl getLastShortUrl() {
        PageHelper.startPage(1, 1);
        ShortUrlExample shortUrlExample = new ShortUrlExample();
        shortUrlExample.setOrderByClause("add_time DESC");
        shortUrlExample.createCriteria();
        return ListUtil.getOne(shortUrlMapper.selectByExample(shortUrlExample));
    }
}
