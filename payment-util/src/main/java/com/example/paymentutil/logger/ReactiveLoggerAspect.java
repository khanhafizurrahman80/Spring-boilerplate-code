package com.example.paymentutil.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * @author Khan Hafizur Rahman
 * @since 5/1/22
 */
@Slf4j
@Aspect
public class ReactiveLoggerAspect {
    @Pointcut("@annotation(ReactiveLoggable)")
    public void loggablePointcut() {
    }

    @Around("loggablePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var isLoggable = isMethodLoggable(joinPoint);
        if (isLoggable) {
            log.info("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
                     joinPoint.getSignature().getName(), managerPrintableContent(joinPoint.getArgs()));
        }

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        if (result instanceof Mono) {
            var monoResult = (Mono) result;

            return monoResult
                    .doOnSuccess(o -> logResult(isLoggable, joinPoint, o))
                    .doOnError(throwable -> logError(isLoggable, joinPoint, throwable))
                    .doFinally(signalType -> logExecutionTime(isLoggable, joinPoint, startTime));
        } else if (result instanceof Flux) {
            var fluxResult = (Flux) result;

            return fluxResult
                    .doOnEach(o -> logResult(isLoggable, joinPoint, o))
                    .doOnError(throwable -> logError(isLoggable, joinPoint, throwable))
                    .doFinally(signalType -> logExecutionTime(isLoggable,joinPoint,startTime));
        } else {
            try {
                logResult(isLoggable, joinPoint, result);
            } catch (Exception e) {
                logError(isLoggable, joinPoint, e);
            } finally {
                logExecutionTime(isLoggable, joinPoint, startTime);
            }
        }
        return result;
    }

    private void logExecutionTime(boolean isLoggable, ProceedingJoinPoint joinPoint, long startTime) {
        if (isLoggable) {
            var endtime = System.currentTimeMillis();
            log.info("Execution time: {}.{}() = {} ms",
                     joinPoint.getSignature().getDeclaringTypeName(),
                     joinPoint.getSignature().getName(),
                     (endtime -startTime));

        }
    }

    private void logError(boolean isLoggable, ProceedingJoinPoint joinPoint, Object throwable) {
        if (isLoggable) {
            log.error("Exception in {}.{}() with cause = {} with args = {}", joinPoint.getSignature().getDeclaringTypeName(),
                      joinPoint.getSignature().getName(), throwable.toString(), Arrays.toString(joinPoint.getArgs()));
        }
    }

    private void logResult(boolean isLoggable, ProceedingJoinPoint joinPoint, Object object) {
        if (isLoggable) {
            var response = "";
            if (Objects.nonNull(object)) {
                response = object.toString();
            }
            log.info("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                     joinPoint.getSignature().getName(), response);
        }
    }

    private Object managerPrintableContent(Object[] args) {
        return Arrays.stream(args).map(arg -> {
            return arg != null ? arg.toString() : null;
        }).collect(Collectors.toList());
    }

    private boolean isMethodLoggable(ProceedingJoinPoint joinPoint) {
        var isLoggable = false;
        var signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {
            Method method = ((MethodSignature) signature).getMethod();
            var loggable = method.getAnnotation(ReactiveLoggable.class);
            isLoggable = loggable != null && loggable.enable();
        }
        
        return isLoggable;

    }
}
