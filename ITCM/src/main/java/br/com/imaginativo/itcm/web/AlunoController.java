package br.com.imaginativo.itcm.web;

import java.util.List;

import javax.validation.Valid;

import org.joda.time.DateTime;
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
import br.com.imaginativo.itcm.repository.AlunoRepository;

@Controller
public class AlunoController {

    @Autowired
    AlunoRepository repository;

    @RequestMapping(value = "/aluno", method = RequestMethod.GET)
    public String alunos(Model model) {
        List<Aluno> alunos = (List<Aluno>) repository.findAll();
        model.addAttribute("alunos", alunos);
        System.out.println("Alunos " + alunos.toString());
        return "alunos/alunoList";
    }

    @RequestMapping(value = "/aluno/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Aluno aluno = new Aluno();
        model.addAttribute("aluno", aluno);
        return "alunos/alunoForm";
    }

    @RequestMapping(value = "/aluno/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Aluno aluno, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Aluno in if");
            System.out.println("Aluno " + result.toString());
            return "alunos/alunoForm";
        } else {
            System.out.println("Aluno in else");
            DateTime today = new DateTime();
            System.out.println(today.toString("yyyyMMddHHmmss"));
            aluno.setMatricula(today.toString("yyyyMMddHHmmss"));
            repository.save(aluno);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Aluno incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();

            return "redirect:/aluno/" + aluno.getId() + "/detail";
        }
    }

    @RequestMapping(value = "/aluno/{alunoId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("alunoId") int alunoId,
            Model model) {
        Aluno aluno = repository.findOne((long) alunoId);
        model.addAttribute("aluno", aluno);

        return "alunos/alunoForm";
    }

    @RequestMapping(value = "/aluno/{alunoId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@PathVariable("alunoId") int alunoId,
            @Valid Aluno aluno, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "alunos/alunoForm";
        } else {
            Aluno alunoUpdate = repository.findOne((long) alunoId);
            alunoUpdate.setName(aluno.getName());
            // TODO: Implementar update
            repository.save(alunoUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Aluno atualizado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/aluno";
        }
    }

    @RequestMapping(value = "/aluno/{alunoId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("alunoId") int alunoId, Model model) {
        Aluno aluno = repository.findOne((long) alunoId);
        model.addAttribute("aluno", aluno);

        return "alunos/alunoDetail";
    }

    @RequestMapping(value = "/aluno/{alunoId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("alunoId") int alunoId,
            SessionStatus status) {

        repository.delete((long) alunoId);
        status.setComplete();
        return "redirect:/aluno";
    }

}
