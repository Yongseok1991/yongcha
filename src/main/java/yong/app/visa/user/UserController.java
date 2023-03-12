package yong.app.visa.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/users")
    public String index() {
        return "user/index";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
