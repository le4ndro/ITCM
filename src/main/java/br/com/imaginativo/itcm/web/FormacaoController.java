package br.com.imaginativo.itcm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Formacao;
import br.com.imaginativo.itcm.model.Professor;
import br.com.imaginativo.itcm.repository.FormacaoRepository;
import br.com.imaginativo.itcm.repository.ProfessorRepository;

@Controller
public class FormacaoController {

    @Autowired
    FormacaoRepository repository;

    @Autowired
    ProfessorRepository professorRepository;

    // @RequestMapping(value = "/formacao", method = RequestMethod.GET)
    // public String formacaos(Model model) {
    // List<Formacao> formacaos = (List<Formacao>) repository.findAll();
    // model.addAttribute("formacaos", formacaos);
    // System.out.println("Formacaos " + formacaos.toString());
    // return "formacaos/formacaoList";
    // }

    @RequestMapping(value = "/formacao/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Formacao formacao = new Formacao();
        model.addAttribute("formacao", formacao);
        return "formacaos/formacaoForm";
    }

    @RequestMapping(value = "/formacao/professor/{professorId}/new", method = RequestMethod.POST)
    public String processCreationForm(
            @PathVariable("professorId") int professorId, Formacao formacao,
            Model model, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Formacao in if");
            System.out.println("Formacao " + result.toString());
            return "redirect:/professor/" + professorId + "/detail";
        } else {
            System.out.println("Formacao in else");
            Professor professor = professorRepository
                    .findOne((long) professorId);
            formacao.setProfessor(professor);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Formação incluída com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            repository.save(formacao);
            status.setComplete();
            return "redirect:/professor/" + professorId + "/detail";
        }
    }

    // @RequestMapping(value = "/formacao/{formacaoId}/edit", method =
    // RequestMethod.GET)
    // public String initUpdateForm(@PathVariable("formacaoId") int formacaoId,
    // Model model) {
    // Formacao formacao = repository.findOne((long) formacaoId);
    // model.addAttribute("formacao", formacao);
    //
    // return "formacaos/formacaoForm";
    // }
    //
    // @RequestMapping(value = "/formacao/{formacaoId}/edit", method =
    // RequestMethod.POST)
    // public String processUpdateForm(@PathVariable("formacaoId") int
    // formacaoId,
    // @Valid Formacao formacao, BindingResult result, SessionStatus status) {
    // if (result.hasErrors()) {
    //
    // return "formacaos/formacaoForm";
    // } else {
    // Formacao formacaoUpdate = repository.findOne((long) formacaoId);
    // formacaoUpdate.setName(formacao.getName());
    // formacaoUpdate.setDescricao(formacao.getDescricao());
    //
    // repository.save(formacaoUpdate);
    // status.setComplete();
    // return "redirect:/formacao";
    // }
    // }

    // @RequestMapping(value = "/formacao/{formacaoId}/detail", method =
    // RequestMethod.GET)
    // public String detail(@PathVariable("formacaoId") int formacaoId, Model
    // model) {
    // Formacao formacao = repository.findOne((long) formacaoId);
    // model.addAttribute("formacao", formacao);
    //
    // return "formacaos/formacaoDetail";
    // }

    @RequestMapping(value = "/formacao/{formacaoId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("formacaoId") int formacaoId,
            SessionStatus status) {
        Formacao formacao = repository.findOne((long) formacaoId);
        long professorId = formacao.getProfessor().getId();
        formacao = null;
        repository.delete((long) formacaoId);
        status.setComplete();
        return "redirect:/professor/" + professorId + "/detail";
    }

}
