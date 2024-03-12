package org.omega.vktesttask.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.omega.vktesttask.entity.AuditEntity;
import org.omega.vktesttask.entity.User;
import org.omega.vktesttask.repository.AuditRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Order(1) // to call aspect before cacheable

public class AuditAspect {
    private final AuditRepository auditRepository;
    private final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    @Before("@annotation(Audit)")
    public void auditEndpoint(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String userName = auth.getName();
        String endpoint = extractEndpoint(joinPoint, request);
        String arguments = Arrays.toString(joinPoint.getArgs());
        String address = getIpAddress(request);
        Instant currDate = Instant.now();
        String httpMethodName = getHttpMethodName(request);

        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setEndpoint(endpoint);
        auditEntity.setDate(currDate);
        auditEntity.setIpAddress(address);
        auditEntity.setRequestArguments(arguments);
        auditEntity.setMethod(httpMethodName);

        try {
            User user = (User) auth.getPrincipal();
            auditEntity.setUser(user);
        } catch (Exception ignored) {

        }

        auditRepository.save(auditEntity);
        logger.info(userName + " : " + auditEntity);
    }

    private String extractEndpoint(JoinPoint joinPoint, HttpServletRequest httpServletRequest) {
        Class<?> controllerClass = joinPoint.getTarget().getClass();

        if (AnnotationUtils.findAnnotation(controllerClass, RestController.class) != null) {
            return httpServletRequest.getRequestURI();
        }
        return "Unknown endpoint";
    }

    private String getHttpMethodName(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getMethod();
    }

    private String getIpAddress(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getRemoteAddr();
    }



}
