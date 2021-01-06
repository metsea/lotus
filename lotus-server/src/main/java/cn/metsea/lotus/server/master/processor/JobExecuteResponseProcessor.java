package cn.metsea.lotus.server.master.processor;

import cn.metsea.lotus.common.utils.JsonUtils;
import cn.metsea.lotus.remote.message.Message;
import cn.metsea.lotus.remote.message.MessageType;
import cn.metsea.lotus.remote.message.job.JobExecuteResponseMessage;
import cn.metsea.lotus.remote.processor.NettyRequestProcessor;
import com.google.common.base.Preconditions;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * Job Execute Response Processor
 */
@Slf4j
public class JobExecuteResponseProcessor implements NettyRequestProcessor {

    @Override
    public void process(Channel channel, Message message) {
        Preconditions.checkArgument(MessageType.JOB_EXECUTE_RESPONSE == message.getType(), String.format("unsupported message type : %s", message.getType()));
        JobExecuteResponseMessage resp = JsonUtils.parseObject(message.getContent(), JobExecuteResponseMessage.class);
        System.out.println("processor process message: " + resp);
    }

}
