package com.example.todospringbootbackend.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//LoginController does not re-direct to another page
//view is different but page is still /login!
@Controller
//@SessionAttributes("name")
public class WelcomeController {

//    //For valdiation of password
//    @Autowired
//    LoginService loginService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model) {
        model.put("name", getLoggedInUserName());
        return "welcome";
    }

    //in Spring: logged-in user is called "principal"
    private String getLoggedInUserName() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        //userDetails is a Spring Security class to store user details
        if (principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();

        return principal.toString();
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password) {
//        boolean isValidUser = loginService.validateUser(name, password);
//        if (!isValidUser) {
//            model.put("errorMessage", "invalid credentials!");
//            return "login";
//        }
//        model.put("name", name);
//        model.put("password", password);
//        return "welcome";
//    }
}
