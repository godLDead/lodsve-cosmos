package message.wechat.beans.tidings;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import message.wechat.beans.tidings.items.Music;

/**
 * 音乐客服消息.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 16/2/24 下午12:03
 */
@ApiModel(description = "音乐客服消息")
public class MusicTidings extends Tidings {
    public MusicTidings() {
        setTidingsType(TidingsType.music);
    }

    @ApiModelProperty(value = "音乐", required = true)
    private Music music;

    @ApiModelProperty(value = "音乐", required = true)
    public Music getMusic() {
        return music;
    }

    @ApiModelProperty(value = "音乐", required = true)
    public void setMusic(Music music) {
        this.music = music;
    }
}
