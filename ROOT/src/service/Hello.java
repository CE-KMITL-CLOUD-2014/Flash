package service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/hello")
public class Hello {
	@RequestMapping(value="", method = RequestMethod.GET) 
	public String kak(){
		return "Hello test noob";
	}
}
