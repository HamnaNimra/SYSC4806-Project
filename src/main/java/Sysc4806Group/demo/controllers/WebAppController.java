package Sysc4806Group.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class WebAppController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
