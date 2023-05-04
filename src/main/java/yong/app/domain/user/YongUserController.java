package yong.app.domain.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import yong.app.domain.role.YongRoleRepository;

@Controller
@RequiredArgsConstructor
public class YongUserController {

    private final YongRoleRepository yongRoleRepository;
    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("author", yongRoleRepository.findAll());
        return "app/join/index";
    }

}
