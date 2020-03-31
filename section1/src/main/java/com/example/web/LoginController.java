package com.example.web;

import javax.servlet.http.HttpServletRequest;


import com.example.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.domain.User;
import com.example.service.UserService;

import java.util.Date;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin")
public class LoginController {

    private UserService userService;

    private final String NotMatchError = "用户名或密码错误";

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login.html")
    public String loginPage() {

        return "login";
    }


    @RequestMapping(value = "/loginCheck.html")
    public String loginCheck(HttpServletRequest request, @Valid LoginInfo loginInfo, Errors errors, Model model) {
        Message message = new Message();
        if(errors.hasErrors()){
            message.setContent(errors.getFieldError().getDefaultMessage());
            model.addAttribute(message);
            return "error";
        }
        boolean isValidUser =
                userService.hasMatchUser(loginInfo.getUserName(),
                        loginInfo.getPassword());
        if (!isValidUser) {
            message.setContent(NotMatchError);
            model.addAttribute(message);
            return "error";
        } else {
            User user = userService.findUserByUserName(loginInfo
                    .getUserName());
            user.setLastIp(request.getLocalAddr());
            user.setLastVisit(new Date());
            userService.saveLog(user);
            request.getSession().setAttribute("user", user);
        }

        return "main";
    }
}
