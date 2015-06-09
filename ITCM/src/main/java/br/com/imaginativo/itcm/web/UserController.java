package br.com.imaginativo.itcm.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.User;
import br.com.imaginativo.itcm.registration.OnRegistrationCompleteEvent;
import br.com.imaginativo.itcm.service.EmailExistsException;
import br.com.imaginativo.itcm.service.UserService;
import br.com.imaginativo.itcm.service.UsernameExistsException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String form() {
        return "users/userForm";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/userList";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes, BindingResult result,
            SessionStatus status, HttpServletRequest request, Errors errors)
            throws Exception {

        System.out.println("" + user.getUsername());
        System.out.println("" + user.getPassword());
        System.out.println("" + user.getEmail());

        User registered = null;
        if (!result.hasErrors()) {

            try {
                registered = userService.save(user);
            } catch (EmailExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UsernameExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (registered == null) {

            result.rejectValue("email", "message.registration.error.email");

        }

        if (result.hasErrors()) {

            return "users/userForm";
        } else {

            String msginfo = "<script>$(document).ready(function() {toastr.success('Usuário incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            String appUrl = request.getContextPath();

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(
                    registered, request.getLocale(), appUrl));

            status.setComplete();

            return "redirect:/user/list";
        }
    }

}
