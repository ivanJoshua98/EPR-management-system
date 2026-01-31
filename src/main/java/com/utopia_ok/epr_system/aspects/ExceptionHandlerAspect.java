package com.utopia_ok.epr_system.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.utopia_ok.epr_system.exceptions.ResourceAlreadyExistsException;
import com.utopia_ok.epr_system.exceptions.ResourceNotFoundException;

@Aspect
@Component
@Order(0)
public class ExceptionHandlerAspect {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAspect.class);

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
  public void methodsStarterServicePointCut() {
  }

  @Around("methodsStarterServicePointCut()")
  public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
    try {
      return joinPoint.proceed();
    } catch (ResourceNotFoundException e) {
      logger.error("Around Pointcut - Error in method {}", joinPoint.getSignature().getName(), e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    } catch (ResourceAlreadyExistsException e) {
      logger.error("Around Pointcut - Error in method {}", joinPoint.getSignature().getName(), e);
      return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
  }
}