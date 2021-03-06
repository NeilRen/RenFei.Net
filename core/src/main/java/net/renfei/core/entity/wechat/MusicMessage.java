package net.renfei.core.entity.wechat;

import lombok.Data;

@Data
public class MusicMessage extends WeChatBaseMessage {
    private Music Music;

    public MusicMessage(WeChatMessage weChatMessage) {
        super(weChatMessage, WeChatMessageType.MUSIC);
    }
}
