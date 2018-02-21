package br.com.imaginativo.itcm.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Perfil;
import br.com.imaginativo.itcm.model.User;
import br.com.imaginativo.itcm.repository.ContaRepository;
import br.com.imaginativo.itcm.repository.PerfilRepository;
import br.com.imaginativo.itcm.service.EmailExistsException;
import br.com.imaginativo.itcm.service.UserService;
import br.com.imaginativo.itcm.service.UsernameExistsException;

@Controller
public class ContaController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	ContaRepository repository;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	PerfilRepository perfilRepository;
	
	@RequestMapping(value = "/conta/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Conta conta = new Conta();
        conta.setAdministrador(new User());
        model.addAttribute("conta", conta);
        return "contas/contaForm";
    }

    @RequestMapping(value = "/conta/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Conta conta,
            BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Conta in if");
            System.out.println("Conta " + result.toString());
            return "empresas/empresaForm";
        } else {
            System.out.println("Conta in else");
            LOGGER.info("Conta in else {0}", conta.toString());
            System.out.println("Conta in else " + conta.toString());
            conta.setNumeroConta(1);
            
            //Create new user
            User registered = null;
            try {
                registered = userService.save(conta.getAdministrador());
            } catch (EmailExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (UsernameExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            registered.setEnabled(true);
            conta.setAdministrador(registered);
            repository.save(conta);
            
            Perfil perfil = new Perfil();
            perfil.setConta(conta);
            perfil.setUser(registered);
            perfilRepository.save(perfil);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Conta incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/login";
        }
    }
    
    @RequestMapping(value = "/conta/{contaId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("contaId") int contaId, Model model) {
        Conta conta = repository.findOne((long) contaId);
        model.addAttribute("conta", conta);

        return "contas/contaDetail";
    }
}
