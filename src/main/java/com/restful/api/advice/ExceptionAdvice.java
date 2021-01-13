package com.restful.api.advice;

import com.restful.api.advice.exception.CAuthenticationEntryPointException;
import com.restful.api.exception.CCommunicationException;
import com.restful.api.exception.CSigninFailedException;
import com.restful.api.exception.CUserNotFoundException;
import com.restful.api.model.response.CommonResult;
import com.restful.api.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    private final ResponseService responseService;

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult();
    }

    @ExceptionHandler(CSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CSigninFailedException e) {
        return responseService.getFailResultLogin();
    }

    @ExceptionHandler(CCommunicationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult socialLoginFiled(HttpServletRequest request, CCommunicationException e) {
        return responseService.getFailResultLogin();
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult socialLoginFiled(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResultJwt();
    }
}

