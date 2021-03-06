package net.renfei.web.controller;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import net.renfei.core.entity.LinkDTO;
import net.renfei.core.service.aliyun.AliyunOSS;
import net.renfei.dao.entity.DownloadDO;
import net.renfei.dao.entity.LinkDOWithBLOBs;
import net.renfei.web.baseclass.BaseController;
import net.renfei.web.entity.APIResult;
import net.renfei.web.entity.FriendLinkVO;
import net.renfei.web.entity.ThumbsVO;
import net.renfei.web.service.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/other")
public class OtherController extends BaseController {
    @Autowired
    private AliyunOSS aliyunOSS;

    /**
     * 链接重定向（注意地址需要Base64编码以后传入）
     *
     * @param url URL需要Base64编码
     * @param mv
     * @return
     */
    @RequestMapping("urlredirect")
    public ModelAndView urlredirect(String url, ModelAndView mv) {
        if (stringUtil.isEmpty(url)) {
            return new ModelAndView("redirect:/");
        }
        try {
            byte[] asBytes = Base64.getDecoder().decode(url);
            mv.addObject("siteName", siteName);
            String uri = new String(asBytes, "utf-8");
            if (!uri.startsWith("http://") && !uri.startsWith("https://")) {
                uri = "http://" + uri;
            }
            if (uri.startsWith(domain)) {
                return new ModelAndView("redirect:" + uri);
            }
            mv.addObject("url", uri);
            setHead(mv, "提示！Notice!");
            mv.setViewName("other/urlredirect");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ModelAndView("redirect:/");
        }
        return mv;
    }

    /**
     * 友情链接申请
     *
     * @param mv
     * @return
     */
    @RequestMapping("friendlink")
    public ModelAndView friendlink(ModelAndView mv) {
        setHead(mv, "友情链接申请",
                "为了与更多建站伙伴互相提高权重和品牌,特开放友情链接申请。您可以使用友情链接申请功能向我们提交友情链接交换的请求,我们会立即审核您的申请。",
                "友情链接,友情,链接,在线,申请");
        LinkDTO linkDTO = linkService.getLinks();
        mv.addObject("linkDTO", linkDTO);
        mv.setViewName("other/friendlink");
        return mv;
    }

    @PostMapping("friendlink/submit")
    @ResponseBody
    public APIResult friendlink(FriendLinkVO friendLinkVO) {
        APIResult apiResult = ejbGenerator.convert(linkService.addFriendLink(ejbGenerator.convert(friendLinkVO, LinkDOWithBLOBs.class)), APIResult.class);
        if (apiResult.getSuccess()) {
            linkService.sendNotify(ejbGenerator.convert(friendLinkVO, LinkDOWithBLOBs.class));
        }
        return apiResult;
    }

    /**
     * 获取极速下载链接
     *
     * @param code 从微信里返回的授权码
     * @return
     */
    @GetMapping("JiSuDownloadLink")
    @ResponseBody
    public APIResult getJiSuDownloadLink(String code) {
        APIResult apiResult = APIResult.fillResult(false);
        String json = cacheService.get(code);
        if (json != null && json.length() > 0) {
            try {
                DownloadDO downloadDO = JSON.parseObject(json, DownloadDO.class);
                if (downloadDO != null) {
                    String link = "";
                    Date expires = new Date(System.currentTimeMillis() + 21600000); //有效期6个小时，6时(h)=21600000毫秒(ms)
                    if ("https://download.renfei.net".equals(downloadDO.getBucket())) {
                        //阿里云的储存
                        link = aliyunOSS.getPresignedUrl(downloadDO.getFilePath(), expires);
                    }
                    Map<String, String> map = new HashMap<>();
                    map.put("jisulink", link);
                    map.put("expires", dateUtil.format(expires, "yyyy-MM-dd HH:mm:ss"));
                    apiResult.setSuccess(true);
                    apiResult.setData(map);
                } else {
                    apiResult.setSuccess(false);
                    apiResult.setMessage("授权码不存在或者已过期，请重新获取");
                }
            } catch (Exception ex) {
                apiResult.setSuccess(false);
                apiResult.setMessage("授权码不存在或者已过期，请重新获取");
            }
        } else {
            apiResult.setSuccess(false);
            apiResult.setMessage("授权码不存在或者已过期，请重新获取");
        }
        return apiResult;
    }

    @GetMapping("qrcode")
    public void qrcode(String content, @PathVariable(value = "size", required = false) String size) throws Exception {
        ServletOutputStream stream = null;
        QRCodeService qrCodeService;
        if (!ObjectUtils.isEmpty(size)) {
            int qrCodeSize;
            try {
                qrCodeSize = Integer.valueOf(size);
                qrCodeService = new QRCodeService(qrCodeSize);
            } catch (NumberFormatException nfe) {
                qrCodeService = new QRCodeService();
            }
        } else {
            qrCodeService = new QRCodeService();
        }
        try {
            localResponse.get().setHeader("Content-Type", "image/jpg");
            stream = localResponse.get().getOutputStream();
            //使用工具类生成二维码
            qrCodeService.encode(content, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    @PostMapping("thumbs")
    @ResponseBody
    public APIResult thumbs(ThumbsVO thumbsVO) {
        thumbsService.thumbs(thumbsVO.getSystem(), thumbsVO.getType(), thumbsVO.getId());
        return APIResult.fillResult(true);
    }
}
