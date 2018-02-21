package br.com.imaginativo.itcm.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Interesse;
import br.com.imaginativo.itcm.repository.InteresseRepository;

@Controller
public class InteresseController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    InteresseRepository repository;

    @RequestMapping(value = "/interesse", method = RequestMethod.GET)
    public String interesses(Model model, HttpSession session) {
    		//Conta conta = (Conta) session.getAttribute("Conta");
        //List<Interesse> interesses = (List<Interesse>) repository.findByConta(conta);
        //model.addAttribute("interesses", interesses);
        //LOGGER.debug("Interesses " + interesses.toString());
        return "interesses/interesseDashboard";
    }

    @RequestMapping(value = "/interesse/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Interesse interesse = new Interesse();
        model.addAttribute("interesse", interesse);
        return "interesses/interesseForm";
    }

    @RequestMapping(value = "/interesse/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Interesse interesse,
            BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes, HttpSession session) {

        if (result.hasErrors()) {            
            LOGGER.debug("Interesse hasErrors");
            LOGGER.debug("Interesse " + result.toString());
            return "interesses/interesseForm";
        } else {            
            LOGGER.debug("processCreationForm Interesse ok");
            Conta conta = (Conta) session.getAttribute("Conta");
            interesse.setConta(conta);
            repository.save(interesse);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Interesse incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/interesse";
        }
    }

    // @RequestMapping(value = "/interesse/{interesseId}/edit", method =
    // RequestMethod.GET)
    // public String initUpdateForm(@PathVariable("interesseId") int
    // interesseId,
    // Model model) {
    // Interesse interesse = repository.findOne((long) interesseId);
    // model.addAttribute("interesse", interesse);
    //
    // return "interesses/interesseForm";
    // }

    // @RequestMapping(value = "/interesse/{interesseId}/edit", method =
    // RequestMethod.POST)
    // public String processUpdateForm(
    // @PathVariable("interesseId") int interesseId,
    // @Valid Interesse interesse, BindingResult result,
    // SessionStatus status, RedirectAttributes redirectAttributes) {
    // if (result.hasErrors()) {
    //
    // return "interesses/interesseForm";
    // } else {
    // Interesse interesseUpdate = repository.findOne((long) interesseId);
    // repository.save(interesseUpdate);
    //
    // String msginfo =
    // "<script>$(document).ready(function() {toastr.success('Interesse atualizado com sucesso !');});</script>";
    // redirectAttributes.addFlashAttribute("msginfo", msginfo);
    //
    // status.setComplete();
    // return "redirect:/interesse";
    // }
    // }

    // @RequestMapping(value = "/interesse/{interesseId}/detail", method =
    // RequestMethod.GET)
    // public String detail(@PathVariable("interesseId") int interesseId,
    // Model model) {
    // Interesse interesse = repository.findOne((long) interesseId);
    // model.addAttribute("interesse", interesse);
    //
    // return "interesses/interesseDetail";
    // }

    // @RequestMapping(value = "/interesse/{interesseId}/delete", method =
    // RequestMethod.GET)
    // public String delete(@PathVariable("interesseId") int interesseId,
    // SessionStatus status) {
    //
    // repository.delete((long) interesseId);
    // status.setComplete();
    // return "redirect:/interesse";
    // }

}
