package br.com.imaginativo.itcm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Ambiente;
import br.com.imaginativo.itcm.model.Unidade;
import br.com.imaginativo.itcm.repository.AmbienteRepository;
import br.com.imaginativo.itcm.repository.UnidadeRepository;

@Controller
public class AmbienteController {

    @Autowired
    AmbienteRepository repository;

    @Autowired
    UnidadeRepository unidadeRepository;

    // @RequestMapping(value = "/ambiente", method = RequestMethod.GET)
    // public String ambientes(Model model) {
    // List<Ambiente> ambientes = (List<Ambiente>) repository.findAll();
    // model.addAttribute("ambientes", ambientes);
    // System.out.println("Ambientes " + ambientes.toString());
    // return "ambientes/ambienteList";
    // }

    @RequestMapping(value = "/ambiente/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Ambiente ambiente = new Ambiente();
        model.addAttribute("ambiente", ambiente);
        return "ambientes/ambienteForm";
    }

    @RequestMapping(value = "/ambiente/unidade/{unidadeId}/new", method = RequestMethod.POST)
    public String processCreationForm(@PathVariable("unidadeId") int unidadeId,
            Ambiente ambiente, Model model, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Ambiente in if");
            System.out.println("Ambiente " + result.toString());
            return "redirect:/unidade/" + unidadeId + "/detail";
        } else {
            System.out.println("Ambiente in else");
            Unidade unidade = unidadeRepository.findOne((long) unidadeId);
            ambiente.setUnidade(unidade);
            repository.save(ambiente);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Ambiente incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/unidade/" + unidadeId + "/detail";
        }
    }

    // @RequestMapping(value = "/ambiente/{ambienteId}/edit", method =
    // RequestMethod.GET)
    // public String initUpdateForm(@PathVariable("ambienteId") int ambienteId,
    // Model model) {
    // Ambiente ambiente = repository.findOne((long) ambienteId);
    // model.addAttribute("ambiente", ambiente);
    //
    // return "ambientes/ambienteForm";
    // }
    //
    // @RequestMapping(value = "/ambiente/{ambienteId}/edit", method =
    // RequestMethod.POST)
    // public String processUpdateForm(@PathVariable("ambienteId") int
    // ambienteId,
    // @Valid Ambiente ambiente, BindingResult result, SessionStatus status) {
    // if (result.hasErrors()) {
    //
    // return "ambientes/ambienteForm";
    // } else {
    // Ambiente ambienteUpdate = repository.findOne((long) ambienteId);
    // ambienteUpdate.setName(ambiente.getName());
    // ambienteUpdate.setDescricao(ambiente.getDescricao());
    //
    // repository.save(ambienteUpdate);
    // status.setComplete();
    // return "redirect:/ambiente";
    // }
    // }

    // @RequestMapping(value = "/ambiente/{ambienteId}/detail", method =
    // RequestMethod.GET)
    // public String detail(@PathVariable("ambienteId") int ambienteId, Model
    // model) {
    // Ambiente ambiente = repository.findOne((long) ambienteId);
    // model.addAttribute("ambiente", ambiente);
    //
    // return "ambientes/ambienteDetail";
    // }

    @RequestMapping(value = "/ambiente/{ambienteId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("ambienteId") int ambienteId,
            SessionStatus status) {
        Ambiente ambiente = repository.findOne((long) ambienteId);
        long unidadeId = ambiente.getUnidade().getId();
        ambiente = null;
        repository.delete((long) ambienteId);
        status.setComplete();
        return "redirect:/unidade/" + unidadeId + "/detail";
    }

    @RequestMapping(value = "/ambiente/unidade/{unidadeId}", produces = { "application/json" }, method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Ambiente> loadAmbienteByUnidade(
            @PathVariable("unidadeId") int unidadeId, Model model,
            SessionStatus status) {

        Unidade unidade = unidadeRepository.findOne((long) unidadeId);

        List<Ambiente> ambientes = (List<Ambiente>) repository
                .findByUnidade(unidade);

        model.addAttribute("ambientes", ambientes);

        return ambientes;
    }

}
