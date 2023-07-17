package fr.bankwiz.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ControllerInterceptor extends GeneralInterceptor {

    @Around("execution(* fr.bankwiz.server.controller.*.*(..))")
    public final Object logInterceptor(final ProceedingJoinPoint joinPoint) throws Throwable {
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("ENDPOINT - {}", request.getRequestURI());
        return this.logExecutionTime(joinPoint, log);
    }
}