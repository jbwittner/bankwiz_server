package fr.bankwiz.server.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class RepositoryInterceptor extends GeneralInterceptor {

    @Around("execution(* fr.bankwiz.server.repository.*.*(..))")
    public final Object logInterceptor(final ProceedingJoinPoint joinPoint) throws Throwable {
        return this.logExecutionTime(joinPoint, log);
    }
}
