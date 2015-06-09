package br.com.imaginativo.itcm.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Interesse;
import br.com.imaginativo.itcm.repository.InteresseRepository;

@Controller
public class InteresseController {

    @Autowired
    InteresseRepository repository;

    @RequestMapping(value = "/interesse", method = RequestMethod.GET)
    public String interesses(Model model) {
        // List<Interesse> interesses = (List<Interesse>) repository.findAll();
        // model.addAttribute("interesses", interesses);
        // System.out.println("Interesses " + interesses.toString());
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
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Interesse in if");
            System.out.println("Interesse " + result.toString());
            return "interesses/interesseForm";
        } else {
            System.out.println("Interesse in else");
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
