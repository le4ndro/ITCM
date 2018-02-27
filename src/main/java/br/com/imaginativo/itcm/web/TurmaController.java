package br.com.imaginativo.itcm.web;

import java.util.ArrayList;
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

import br.com.imaginativo.itcm.model.Ambiente;
import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Curso;
import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Professor;
import br.com.imaginativo.itcm.model.Turma;
import br.com.imaginativo.itcm.model.Unidade;
import br.com.imaginativo.itcm.repository.AmbienteRepository;
import br.com.imaginativo.itcm.repository.CursoRepository;
import br.com.imaginativo.itcm.repository.EmpresaRepository;
import br.com.imaginativo.itcm.repository.ProfessorRepository;
import br.com.imaginativo.itcm.repository.TurmaRepository;
import br.com.imaginativo.itcm.repository.UnidadeRepository;

@Controller
public class TurmaController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    TurmaRepository repository;

    @Autowired
    CursoRepository cursoRepository;

    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    AmbienteRepository ambienteRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @RequestMapping(value = "/turma", method = RequestMethod.GET)
    public String turmas(Model model, HttpSession session) {
    		Conta conta = (Conta) session.getAttribute("Conta");
    		
    		List<Turma> turmas = new ArrayList<Turma>();
    		if (conta.getEmpresa() != null) {
    			turmas = (List<Turma>) repository.findByEmpresa(conta.getEmpresa());
		}else {
			turmas = (List<Turma>) repository.findByConta(conta);
		}
        
        model.addAttribute("turmas", turmas);
        System.out.println("Turmas " + turmas.toString());
        LOGGER.debug("Turmas " + turmas.toString());
        return "turmas/turmaList";
    }

    @RequestMapping(value = "/turma/new", method = RequestMethod.GET)
    public String initCreationForm(Model model, HttpSession session) {
    		Conta conta = (Conta) session.getAttribute("Conta");
    	
        Turma turma = new Turma();
        model.addAttribute("turma", turma);

        //List<Empresa> empresas = (List<Empresa>) empresaRepository.findAll();
        //model.addAttribute("empresas", empresas);
        List<Unidade> unidades = new ArrayList<Unidade>();
        
        if (conta.getEmpresa() != null) {
        		unidades = (List<Unidade>) unidadeRepository.findByEmpresa(conta.getEmpresa());
		}
        
        model.addAttribute("unidades", unidades);

        List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
        model.addAttribute("cursos", cursos);

        List<Professor> professores = (List<Professor>) professorRepository
                .findAll();
        model.addAttribute("professores", professores);

        return "turmas/turmaForm";
    }

    @RequestMapping(value = "/turma/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Turma turma, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes, HttpSession session) {

        if (result.hasErrors()) {
            System.out.println("Turma in if");
            System.out.println("Turma " + result.toString());
            return "turmas/turmaForm";
        } else {
            System.out.println("Turma in else");
            turma.setStatus("ABERTA");
            Conta conta = (Conta) session.getAttribute("Conta");
            turma.setEmpresa(conta.getEmpresa());
            turma.setConta(conta);
            repository.save(turma);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Turma incluída com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/turma";
        }
    }

    @RequestMapping(value = "/turma/{turmaId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("turmaId") int turmaId,
            Model model) {

        Turma turma = repository.findOne((long) turmaId);
        model.addAttribute("turma", turma);

        model.addAttribute("Dom", false);
        model.addAttribute("Seg", false);
        model.addAttribute("Ter", false);
        model.addAttribute("Qua", false);
        model.addAttribute("Qui", false);
        model.addAttribute("Sex", false);
        model.addAttribute("Sab", false);

        String dias[] = turma.getDias().split(";");

        for (String dia : dias) {
            switch (dia) {
            case "Dom":
                model.addAttribute("Dom", true);
                break;
            case "Seg":
                model.addAttribute("Seg", true);
                break;
            case "Ter":
                model.addAttribute("Ter", true);
                break;
            case "Qua":
                model.addAttribute("Qua", true);
                break;
            case "Qui":
                model.addAttribute("Qui", true);
                break;
            case "Sex":
                model.addAttribute("Sex", true);
                break;
            case "Sab":
                model.addAttribute("Sab", true);
                break;
            default:
                break;
            }
        }

        List<Empresa> empresas = (List<Empresa>) empresaRepository.findAll();

        model.addAttribute("empresas", empresas);

        List<Curso> cursos = (List<Curso>) cursoRepository.findAll();
        model.addAttribute("cursos", cursos);

        List<Unidade> unidades = (List<Unidade>) unidadeRepository.findAll();
        model.addAttribute("unidades", unidades);

        List<Ambiente> ambientes = (List<Ambiente>) ambienteRepository
                .findAll();
        model.addAttribute("ambientes", ambientes);

        List<Professor> professores = (List<Professor>) professorRepository
                .findAll();
        model.addAttribute("professores", professores);

        return "turmas/turmaForm";
    }

    @RequestMapping(value = "/turma/{turmaId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@PathVariable("turmaId") int turmaId,
            @Valid Turma turma, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "turmas/turmaForm";
        } else {
            Turma turmaUpdate = repository.findOne((long) turmaId);
            turmaUpdate.setCurso(turma.getCurso());
            turmaUpdate.setUnidade(turma.getUnidade());
            turmaUpdate.setAmbiente(turma.getAmbiente());
            turmaUpdate.setProfessor(turma.getProfessor());
            turmaUpdate.setName(turma.getName());
            turmaUpdate.setDataInicio(turma.getDataInicio());
            turmaUpdate.setDataTermino(turma.getDataTermino());
            turmaUpdate.setDias(turma.getDias());
            turmaUpdate.setHoraInicio(turma.getHoraInicio());
            turmaUpdate.setHoraTermino(turma.getHoraTermino());
            turmaUpdate.setObs(turma.getObs());

            repository.save(turmaUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Turma atualizada com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/turma";
        }
    }

    @RequestMapping(value = "/turma/{turmaId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("turmaId") int turmaId, Model model) {
        Turma turma = repository.findOne((long) turmaId);
        model.addAttribute("turma", turma);

        int vagasRestantes = turma.getAmbiente().getVagas()
                - turma.getIngressos().size();
        model.addAttribute("vagasRestantes", vagasRestantes);

        return "turmas/turmaDetail";
    }

    @RequestMapping(value = "/turma/{turmaId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("turmaId") int turmaId,
            SessionStatus status) {

        repository.delete((long) turmaId);
        status.setComplete();
        return "redirect:/turma";
    }

}
