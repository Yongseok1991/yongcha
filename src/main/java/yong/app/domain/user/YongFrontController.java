package yong.app.domain.user;

import org.springframework.stereotype.Controller;

@Controller
public class YongFrontController {

    public String index() {
        return "/login";
    }
}
