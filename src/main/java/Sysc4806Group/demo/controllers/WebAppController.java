package Sysc4806Group.demo.controllers;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebAppController {

    @RequestMapping("/")
    public String index(){
        return "Welcome to Amazin Store!";
    }

}