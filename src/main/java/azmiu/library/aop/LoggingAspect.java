package azmiu.library.aop;

import azmiu.library.model.response.PageableResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@Aspect
public class LoggingAspect {

    @Around("within(@azmiu.library.annotation.Log *)")
    @SneakyThrows
    public Object setLog(ProceedingJoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().getName();
        var className = joinPoint.getTarget().getClass().getSimpleName();
        var parameters = formatParameters(joinPoint.getArgs());

        try {
            log.info("ActionLog.{}.{}.start", className, methodName);
            log.info("ActionLog.{}.{}.parameters: {}", className, methodName, parameters);

            var response = joinPoint.proceed();
            logEndResponse(className, methodName, response);

            return response;
        } catch (Throwable throwable) {
            log.error("ActionLog.{}.{}.error {}", className, methodName, throwable.getMessage());
            throw throwable;
        }
    }

    private String formatParameters(Object[] args) {
        return Arrays.stream(args)
                .map(arg -> {
                    if (arg instanceof MultipartFile) {
                        return "MultipartFile (skipped)";
                    }
                    if (arg instanceof List<?>) {
                        return "List(size=" + ((List<?>) arg).size() + ")";
                    }
                    if (arg != null && arg.getClass().getPackageName().startsWith("azmiu.library.dao.entity")) {
                        return arg.getClass().getSimpleName() + " (skipped)";
                    }
                    return arg.getClass().getSimpleName();
                })
                .collect(Collectors.joining(", "));
    }

    private void logEndResponse(String className, String methodName, Object response) {
        if (response instanceof List<?>) {
            log.info("ActionLog.{}.{}.finish - Returned {} items", className, methodName, ((List<?>) response).size());
        } else if (response instanceof PageableResponse<?>) {
            log.info("ActionLog.{}.{}.finish - Returned {} items", className, methodName, ((PageableResponse<?>) response).getTotalElements());
        } else {
            log.info("ActionLog.{}.{}.finish - Response processed", className, methodName);
        }
    }
}
