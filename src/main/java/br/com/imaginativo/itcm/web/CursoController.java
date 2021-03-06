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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Curso;
import br.com.imaginativo.itcm.repository.CursoRepository;

@Controller
public class CursoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    CursoRepository repository;

    @RequestMapping(value = "/curso", method = RequestMethod.GET)
    public String cursos(Model model, HttpSession session) {
    		Conta conta = (Conta) session.getAttribute("Conta");
    		
        List<Curso> cursos = (List<Curso>) repository.findByConta(conta);
        model.addAttribute("cursos", cursos);        
        LOGGER.debug("Cursos " + cursos.toString());
        return "cursos/cursoList";
    }

    @RequestMapping(value = "/curso/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Curso curso = new Curso();
        model.addAttribute("curso", curso);
        return "cursos/cursoForm";
    }

    @RequestMapping(value = "/curso/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Curso curso, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes, HttpSession session) {

        if (result.hasErrors()) {            
            LOGGER.debug("processCreationForm -> Curso hasErrors");
            LOGGER.debug("Curso " + result.toString());
            return "cursos/cursoForm";
        } else {
            LOGGER.debug("processCreationForm -> Curso ok");
            Conta conta = (Conta) session.getAttribute("Conta");
            curso.setConta(conta);          
            repository.save(curso);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Curso incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/curso";
        }
    }

    @RequestMapping(value = "/curso/{cursoId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("cursoId") int cursoId,
            Model model) {
        Curso curso = repository.findOne((long) cursoId);
        model.addAttribute("curso", curso);

        return "cursos/cursoForm";
    }

    @RequestMapping(value = "/curso/{cursoId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@PathVariable("cursoId") int cursoId,
            @Valid Curso curso, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "cursos/cursoForm";
        } else {
            Curso cursoUpdate = repository.findOne((long) cursoId);
            cursoUpdate.setName(curso.getName());
            cursoUpdate.setDescricao(curso.getDescricao());
            cursoUpdate.setPreRequisitos(curso.getPreRequisitos());
            cursoUpdate.setCargaHoraria(curso.getCargaHoraria());
            cursoUpdate.setEmenta(curso.getEmenta());
            cursoUpdate.setValor(curso.getValor());
            cursoUpdate.setTags(curso.getTags());
            repository.save(cursoUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Curso atualizado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/curso";
        }
    }

    @RequestMapping(value = "/curso/{cursoId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("cursoId") int cursoId, Model model) {
        Curso curso = repository.findOne((long) cursoId);
        model.addAttribute("curso", curso);

        return "cursos/cursoDetail";
    }

    @RequestMapping(value = "/curso/{cursoId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("cursoId") int cursoId,
            SessionStatus status) {

        repository.delete((long) cursoId);
        status.setComplete();
        return "redirect:/curso";
    }
}
