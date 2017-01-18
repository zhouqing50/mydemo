package com.qinghuaci.aop;

import com.qinghuaci.common.result.BaseResult;
import com.qinghuaci.common.result.exception.BizException;
import com.qinghuaci.model.enums.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

/**
 * Created by xialf on 10/31/16.
 *
 * @author xialf
 */
@Slf4j
public class LogAndResultAspect {

    public Object around(ProceedingJoinPoint point) {
        String className = point.getTarget().getClass().getSimpleName();
        if (className.endsWith("Impl")) {
            className = className.substring(0, className.length() - 4);
        }
        String methodName = point.getSignature().getName();
        String serviceMethod = className + "." + methodName;

        Object result;
        long begin = System.currentTimeMillis();
        try {
            log.info("==== Received start serviceMethod[{}]. params[{}].", serviceMethod, Arrays.toString(point.getArgs()));
            result = point.proceed();
            log.info("==== Received end serviceMethod[{}], params[{}], result[{}], cost[{}].", serviceMethod,
                    Arrays.toString(point.getArgs()), result, System.currentTimeMillis() - begin);
            return result;
        } catch (Throwable e) {
            if (e instanceof IllegalArgumentException) {
                result = new BaseResult<>(CodeEnum.PARAM_ILLEGAL_EXCEPTION);
                log.info(
                        "==== Received end IllegalArgumentException. serviceMethod[{}], params[{}], result[{}],cost[{}].",
                        serviceMethod,
                        Arrays.toString(point.getArgs()), result, System.currentTimeMillis() - begin, e);
            } else if (e instanceof BizException) {
                result = new BaseResult<>((BizException) e);
                log.warn("==== Received end BizException. serviceMethod[{}], params[{}], result[{}],cost[{}].",
                        serviceMethod,
                        Arrays.toString(point.getArgs()), result, System.currentTimeMillis() - begin, e);
            /*} else if (e instanceof WechatProxyException) {
                result = new BaseResult<>(CodeEnum.CALL_QY_WECHAT_SERIVCE_EXCEPTION);
                log.warn("==== Received end WechatProxyException. serviceMethod[{}], params[{}], result[{}],cost[{}].",
                        serviceMethod,
                        Arrays.toString(point.getArgs()), result, System.currentTimeMillis() - begin, e);*/
            } else {
                result = new BaseResult<>(CodeEnum.SYSTEM_EXCEPTION);
                log.error("==== Received end SYSTEM_ERROR. serviceMethod[{}], params[{}], result[{}],cost[{}].",
                        serviceMethod,
                        Arrays.toString(point.getArgs()), result, System.currentTimeMillis() - begin, e);
            }
        }

        return result;
    }

}
