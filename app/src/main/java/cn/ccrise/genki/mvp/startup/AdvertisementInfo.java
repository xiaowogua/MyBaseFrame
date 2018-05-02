package cn.ccrise.genki.mvp.startup;

/**
 * 广告信息
 * Created by wxl on 2017/3/1.
 */

public class AdvertisementInfo {

    public static final String TYPE_START_UP = "0";
    public static final String TYPE_HOME = "1";

    /**
     * 广告id
     */
    private String id;
    /**
     * 广告内容（富文本）
     */
    private String content;
    /**
     * 广告标题
     */
    private String title;
    /**
     * 广告图片路径
     */
    private String imgPath;
    /**
     * 广告位置（0启动页1首页）
     */
    private String type;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 广告跳转地址
     */
    private String advertisementUrl;
    /**
     * 图片本地路径
     */
    private String imageLocalPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAdvertisementUrl() {
        return advertisementUrl;
    }

    public void setAdvertisementUrl(String advertisementUrl) {
        this.advertisementUrl = advertisementUrl;
    }

    public String getImageLocalPath() {
        return imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    @Override
    public String toString() {
        return "AdvertisementInfo{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                ", advertisementUrl='" + advertisementUrl + '\'' +
                ", imageLocalPath='" + imageLocalPath + '\'' +
                '}';
    }
}
