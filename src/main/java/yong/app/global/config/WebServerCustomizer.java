package yong.app.global.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory>, ErrorController {
    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // 404 error
        ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");

        // 500 error
        ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        // runtime Exception 및 runtime Exception 의 자식 예외도 포함
        ErrorPage exPage = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(error404, error500, exPage);
    }
}
