package yong.app.global.interceptor;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.*;

@Slf4j
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("GET".equalsIgnoreCase(request.getMethod()) || "DELETE".equalsIgnoreCase(request.getMethod())) {
            List<String> params = new ArrayList<>();
            request.getParameterNames().asIterator().forEachRemaining(paramName -> params.add(paramName + " : " + request.getParameter(paramName)));
            log.info("parameter: {}", params);
        }
        log.info("received [{} '{}' {}]", request.getMethod(), request.getRequestURI(), request.getProtocol());
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("response status: {}", response.getStatus());
        log.info("===============================================================\n");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> params = new ArrayList<>();
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
        final ContentCachingResponseWrapper cachingResponse = (ContentCachingResponseWrapper) response;
        request.getParameterNames().asIterator().forEachRemaining(paramName -> params.add(paramName + " : " + request.getParameter(paramName)));
        log.info("===============================================================");
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            log.info("parameter: {}", objectMapper.readTree(cachingRequest.getContentAsByteArray()));
            log.info("response: {}", objectMapper.readTree(cachingResponse.getContentAsByteArray()));
        } else {
            log.info("parameter: {}", params);
        }
        super.afterCompletion(request, response, handler, ex);
    }

}
