package cc.ly.mc.core.io;

/**
 * 当消息解析后验证合法，进行业务处理
 * Created by ly on 9/10/15.
 */
public interface Handler {

    void onReceived();

}
