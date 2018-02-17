package br.com.imaginativo.itcm.web;

import java.util.List;

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

import br.com.imaginativo.itcm.model.Contato;
import br.com.imaginativo.itcm.model.Endereco;
import br.com.imaginativo.itcm.model.Identificacao;
import br.com.imaginativo.itcm.model.Professor;
import br.com.imaginativo.itcm.repository.ProfessorRepository;

@Controller
public class ProfessorController {

    @Autowired
    ProfessorRepository repository;

    @RequestMapping(value = "/professor", method = RequestMethod.GET)
    public String professors(Model model) {
        List<Professor> professors = (List<Professor>) repository.findAll();
        model.addAttribute("professors", professors);

        System.out.println("Professors " + professors.toString());

        return "professores/professorList";
    }

    @RequestMapping(value = "/professor/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Professor professor = new Professor();
        model.addAttribute("professor", professor);
        return "professores/professorForm";
    }

    @RequestMapping(value = "/professor/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Professor professor,
            BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Professor in if");
            System.out.println("Professor " + result.toString());
            return "professores/professorForm";
        } else {
            System.out.println("Professor in else");
            repository.save(professor);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Professor incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/professor";
        }
    }

    @RequestMapping(value = "/professor/{professorId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("professorId") int professorId,
            Model model) {
        Professor professor = repository.findOne((long) professorId);
        model.addAttribute("professor", professor);

        return "professores/professorForm";
    }

    @RequestMapping(value = "/professor/{professorId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(
            @PathVariable("professorId") int professorId,
            @Valid Professor professor, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "professores/professorForm";
        } else {
            Professor professorUpdate = repository.findOne((long) professorId);
            professorUpdate.setName(professor.getName());
            professorUpdate.setValorHora(professor.getValorHora());
            professorUpdate
                    .setDataDeNascimento(professor.getDataDeNascimento());
            // tratar identificacao
            Identificacao identificacao = new Identificacao();
            professorUpdate.setIdentificacao(identificacao);
            professorUpdate.getIdentificacao().setCpf(
                    professor.getIdentificacao().getCpf());
            professorUpdate.getIdentificacao().setRg(
                    professor.getIdentificacao().getRg());
            professorUpdate.getIdentificacao().setRgUF(
                    professor.getIdentificacao().getRgUF());
            professorUpdate.getIdentificacao().setRgOrgaoEmissor(
                    professor.getIdentificacao().getRgOrgaoEmissor());
            professorUpdate.getIdentificacao().setRgDataExpedicao(
                    professor.getIdentificacao().getRgDataExpedicao());
            professorUpdate.getIdentificacao().setTituloEleitoral(
                    professor.getIdentificacao().getTituloEleitoral());
            professorUpdate.getIdentificacao().setTituloEleitoralZona(
                    professor.getIdentificacao().getTituloEleitoralZona());
            professorUpdate.getIdentificacao().setTituloEleitoralSecao(
                    professor.getIdentificacao().getTituloEleitoralSecao());
            professorUpdate.getIdentificacao().setCertificadoDeReservista(
                    professor.getIdentificacao().getCertificadoDeReservista());
            professorUpdate.getIdentificacao().setEstadoCivil(
                    professor.getIdentificacao().getEstadoCivil());
            professorUpdate.getIdentificacao().setSexo(
                    professor.getIdentificacao().getSexo());
            professorUpdate.getIdentificacao().setNaturalidade(
                    professor.getIdentificacao().getNaturalidade());

            // tratar endereco
            Endereco endereco = new Endereco();
            professorUpdate.setEndereco(endereco);
            professorUpdate.getEndereco().setLogradouro(
                    professor.getEndereco().getLogradouro());
            professorUpdate.getEndereco().setComplemento(
                    professor.getEndereco().getComplemento());
            professorUpdate.getEndereco().setBairro(
                    professor.getEndereco().getBairro());
            professorUpdate.getEndereco().setCidade(
                    professor.getEndereco().getCidade());
            professorUpdate.getEndereco()
                    .setUF(professor.getEndereco().getUF());
            professorUpdate.getEndereco().setCEP(
                    professor.getEndereco().getCEP());
            // tratar contato
            Contato contato = new Contato();
            professorUpdate.setContato(contato);
            professorUpdate.getContato().setTel1(
                    professor.getContato().getTel1());
            professorUpdate.getContato().setTel2(
                    professor.getContato().getTel2());
            professorUpdate.getContato().setTel3(
                    professor.getContato().getTel3());
            professorUpdate.getContato().setMovel(
                    professor.getContato().getMovel());
            professorUpdate.getContato()
                    .setFax(professor.getContato().getFax());
            professorUpdate.getContato().setSite(
                    professor.getContato().getSite());
            professorUpdate.getContato().setEmail(
                    professor.getContato().getEmail());
            professorUpdate.getContato().setSkype(
                    professor.getContato().getSkype());
            repository.save(professorUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Professor atualizado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/professor";

        }
    }

    @RequestMapping(value = "/professor/{professorId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("professorId") int professorId,
            Model model) {
        Professor professor = repository.findOne((long) professorId);
        model.addAttribute("professor", professor);

        return "professores/professorDetail";
    }

    @RequestMapping(value = "/professor/{professorId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("professorId") int professorId,
            SessionStatus status) {

        repository.delete((long) professorId);
        status.setComplete();
        return "redirect:/professor";
    }
}
