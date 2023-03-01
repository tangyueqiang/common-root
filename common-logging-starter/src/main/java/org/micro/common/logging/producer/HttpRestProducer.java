package org.micro.common.logging.producer;

import org.micro.common.logging.autoprop.HttpRestProperties;
import org.micro.common.logging.autoprop.LoggingProperties;
import org.micro.commons.basic.beans.Result;
import org.micro.commons.basic.utils.HttpUtil;
import org.micro.los.common.protocol.BaseLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用http请求POST JOSN日志数据
 */
public class HttpRestProducer extends DefaultProducer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public HttpRestProducer(LoggingProperties conf) {
        super(conf);
    }

    @Override
    public void produce(BaseLog log) {
        if (log == null) {
            return;
        }

        HttpRestProperties rest = getConf().getProducer().getHttprest();
        HttpUtil.HttpResult res = HttpUtil.postJson(rest.getUri(), log.toString(), rest.getHeader());
        if (res.getCode() != 200) {
            logger.error(res.toString());
        }
        if (log.getAction() != null) {
            Result result = new Result(res.getBody());
            if (res.getCode() != 200) {
                logger.error(res.toString());
                log.getAction().doFailed(result);
            } else {
                log.getAction().doSuccess(result);
            }
        }

    }

}
