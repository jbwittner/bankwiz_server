package fr.bankwiz.server.aop;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

public class GeneralInterceptor {
    public Object logExecutionTime(final ProceedingJoinPoint joinPoint, final Logger logger) throws Throwable {
        final long start = System.currentTimeMillis();
        final List<Object> list = Arrays.asList(joinPoint.getArgs());
        final Iterator<Object> iterator = list.iterator();

        String method = joinPoint.getSignature().getDeclaringTypeName();
        method += "." + joinPoint.getSignature().getName();

        logger.info("ENTERING - {}", method);

        Object object;

        if (logger.isDebugEnabled()) {
            while (iterator.hasNext()) {
                object = iterator.next();
                logger.debug("Args list :: {} :: {}", method, object);
            }
        }

        final Object proceed = joinPoint.proceed();

        final long executionTime = System.currentTimeMillis() - start;

        logger.info("EXITING - {} executed in {} ms", method, executionTime);

        return proceed;
    }
}