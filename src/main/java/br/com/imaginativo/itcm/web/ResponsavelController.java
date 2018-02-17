package br.com.imaginativo.itcm.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Aluno;
import br.com.imaginativo.itcm.model.Responsavel;
import br.com.imaginativo.itcm.repository.AlunoRepository;
import br.com.imaginativo.itcm.repository.ResponsavelRepository;

@Controller
public class ResponsavelController {

    @Autowired
    ResponsavelRepository repository;

    @Autowired
    AlunoRepository alunoRepository;

    // @RequestMapping(value = "/responsavel", method = RequestMethod.GET)
    // public String responsavels(Model model) {
    // List<Responsavel> responsaveis = (List<Responsavel>) repository
    // .findAll();
    // model.addAttribute("responsaveis", responsaveis);
    // System.out.println("Responsaveis " + responsaveis.toString());
    // return "responsaveis/responsavelList";
    // }

    @RequestMapping(value = "/responsavel/aluno/{alunoId}/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("alunoId") int alunoId,
            Model model) {
        Aluno aluno = alunoRepository.findOne((long) alunoId);
        Responsavel responsavel = new Responsavel();
        responsavel.setAluno(aluno);
        model.addAttribute("responsavel", responsavel);
        return "responsaveis/responsavelForm";
    }

    @RequestMapping(value = "/responsavel/aluno/{alunoId}/new", method = RequestMethod.POST)
    public String processCreationForm(@PathVariable("alunoId") int alunoId,
            @Valid Responsavel responsavel, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Responsavel in if");
            System.out.println("Responsavel " + result.toString());
            return "responsaveis/responsavelForm";
        } else {
            System.out.println("Responsavel in else");
            Aluno aluno = alunoRepository.findOne((long) alunoId);
            responsavel.setAluno(aluno);

            repository.save(responsavel);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Responsável incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();

            return "redirect:/aluno/" + alunoId + "/detail";
        }
    }

    @RequestMapping(value = "/responsavel/{responsavelId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(
            @PathVariable("responsavelId") int responsavelId, Model model) {
        Responsavel responsavel = repository.findOne((long) responsavelId);
        model.addAttribute("responsavel", responsavel);

        return "responsaveis/responsavelForm";
    }

    @RequestMapping(value = "/responsavel/{responsavelId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(
            @PathVariable("responsavelId") int responsavelId,
            @Valid Responsavel responsavel, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "responsaveis/responsavelForm";
        } else {
            Responsavel responsavelUpdate = repository
                    .findOne((long) responsavelId);
            responsavelUpdate.setName(responsavel.getName());

            Long alunoId = responsavel.getAluno().getId();
            repository.save(responsavelUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Responsável atualizado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();

            return "redirect:/aluno/" + alunoId + "/detail";
        }
    }

    // @RequestMapping(value = "/responsavel/{responsavelId}/detail", method =
    // RequestMethod.GET)
    // public String detail(@PathVariable("responsavelId") int responsavelId,
    // Model model) {
    // Responsavel responsavel = repository.findOne((long) responsavelId);
    // model.addAttribute("responsavel", responsavel);
    //
    // return "responsaveis/responsavelDetail";
    // }

    @RequestMapping(value = "/responsavel/{responsavelId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("responsavelId") int responsavelId,
            SessionStatus status) {

        Responsavel responsavel = repository.findOne((long) responsavelId);

        long alunoId = responsavel.getAluno().getId();
        responsavel = null;
        repository.delete((long) responsavelId);
        status.setComplete();
        return "redirect:/aluno/" + alunoId + "/detail";
    }

}
