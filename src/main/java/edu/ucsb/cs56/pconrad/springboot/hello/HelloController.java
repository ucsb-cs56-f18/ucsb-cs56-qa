package edu.ucsb.cs56.pconrad.springboot.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String login() {
        return "login";
    }

	@RequestMapping("/home")
    public String home() {
        return "home";
    }

	@RequestMapping("/answer")
	public String answer() {
        return "answer";
    }

	@RequestMapping("profile")
	public String profile() {
        return "profile";
    }

	
}
