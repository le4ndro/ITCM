package br.com.imaginativo.itcm.web;

import java.security.Principal;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Perfil;
import br.com.imaginativo.itcm.model.User;
import br.com.imaginativo.itcm.repository.PerfilRepository;
import ch.qos.logback.core.net.SyslogOutputStream;

@Controller
public class HomeController {

	@Autowired
	PerfilRepository perfilRepository;
	
    @RequestMapping("/")
    public String home(
            @RequestParam(value = "name", required = false, defaultValue = "World") String name,
            Model model, HttpSession session, Principal principal) {
        model.addAttribute("name", name);
        
        if (principal != null) {
    			Perfil perfil = perfilRepository.findByUserUsername(principal.getName());
    			Conta conta = perfil.getConta() ;
    			
    			if (conta != null) {    				
    				session.setAttribute("Conta", conta);
    				if (conta.getEmpresa() == null) {
					return String.format("redirect:/conta/%d/detail", conta.getId()); 
				}
    			}
		} 
        
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/newAccount")
    public String newAccount() {
        return "redirect:/conta/new";
    }
    
    @RequestMapping("/forgotPassword")
    public String forgotPassword() {
        return "forgotPassword";
    }
}
