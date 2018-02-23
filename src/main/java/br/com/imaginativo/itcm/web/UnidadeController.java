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

import br.com.imaginativo.itcm.model.Conta;
import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Unidade;
import br.com.imaginativo.itcm.repository.EmpresaRepository;
import br.com.imaginativo.itcm.repository.UnidadeRepository;

@Controller
public class UnidadeController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    UnidadeRepository repository;

    @Autowired
    EmpresaRepository empresaRepository;

    @RequestMapping(value = "/unidade", method = RequestMethod.GET)
    public String unidades(Model model, HttpSession session) {
    		List<Unidade> unidades = null;
    		Long empresaID = 0l;
    		Conta conta = (Conta) session.getAttribute("Conta");
    		if (conta.getEmpresa() != null) {
    			unidades = (List<Unidade>) repository.findByEmpresa(conta.getEmpresa());
    			empresaID = conta.getEmpresa().getId();
		}else {
			unidades = new ArrayList<Unidade>();
		}
        
        model.addAttribute("unidades", unidades);
        model.addAttribute("empresaID", empresaID);
        
        LOGGER.debug("Unidades " + unidades.toString());
        LOGGER.debug("Empresa ID " + empresaID);
        return "unidades/unidadeList";
    }

    @RequestMapping(value = "/unidade/empresa/{empresaId}/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("empresaId") int empresaId,
            Model model) {
        Empresa empresa = empresaRepository.findOne((long) empresaId);
        Unidade unidade = new Unidade();
        unidade.setEmpresa(empresa);
        model.addAttribute("unidade", unidade);
        return "unidades/unidadeForm";
    }

    @RequestMapping(value = "/unidade/empresa/{empresaId}/new", method = RequestMethod.POST)
    public String processCreationForm(@PathVariable("empresaId") int empresaId,
            @Valid Unidade unidade, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Unidade in if");
            System.out.println("Unidade " + result.toString());
            LOGGER.debug("Unidade hasErrors");
            LOGGER.debug("Unidade " + result.toString());
            return "unidades/unidadeForm";
        } else {            
            LOGGER.debug("processCreationForm Unidade ok");
            Empresa empresa = empresaRepository.findOne((long) empresaId);
            unidade.setEmpresa(empresa);
            unidade.setAtiva(true);
            repository.save(unidade);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Unidade incluída com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();

            return "redirect:/unidade";
        }
    }

    @RequestMapping(value = "/unidade/{unidadeId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("unidadeId") int unidadeId,
            Model model) {
        Unidade unidade = repository.findOne((long) unidadeId);
        model.addAttribute("unidade", unidade);

        return "unidades/unidadeForm";
    }

    @RequestMapping(value = "/unidade/{unidadeId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@PathVariable("unidadeId") int unidadeId,
            @Valid Unidade unidade, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "unidades/unidadeForm";
        } else {
            Unidade unidadeUpdate = repository.findOne((long) unidadeId);
            unidadeUpdate.setName(unidade.getName());
            unidadeUpdate.setDescricao(unidade.getDescricao());
            // Todo Verificar update
            
            repository.save(unidadeUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Unidade atualizada com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();

            return "redirect:/unidade";
        }
    }

    @RequestMapping(value = "/unidade/{unidadeId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("unidadeId") int unidadeId, Model model) {
        Unidade unidade = repository.findOne((long) unidadeId);
        model.addAttribute("unidade", unidade);

        return "unidades/unidadeDetail";
    }

    @RequestMapping(value = "/unidade/{unidadeId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("unidadeId") int unidadeId,
            SessionStatus status) {
        repository.delete((long) unidadeId);
        status.setComplete();
        return "redirect:/unidade";
    }

}
