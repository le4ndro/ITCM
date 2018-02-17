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
import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Endereco;
import br.com.imaginativo.itcm.repository.EmpresaRepository;

@Controller
public class EmpresaController {

    @Autowired
    EmpresaRepository repository;

    @RequestMapping(value = "/empresa", method = RequestMethod.GET)
    public String empresas(Model model) {
        List<Empresa> empresas = (List<Empresa>) repository.findAll();
        model.addAttribute("empresas", empresas);
        System.out.println("Empresas " + empresas.toString());
        return "empresas/empresaList";
    }

    @RequestMapping(value = "/empresa/new", method = RequestMethod.GET)
    public String initCreationForm(Model model) {
        Empresa empresa = new Empresa();
        model.addAttribute("empresa", empresa);
        return "empresas/empresaForm";
    }

    @RequestMapping(value = "/empresa/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Empresa empresa,
            BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            System.out.println("Empresa in if");
            System.out.println("Empresa " + result.toString());
            return "empresas/empresaForm";
        } else {
            System.out.println("Empresa in else");
            repository.save(empresa);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Empresa incluído com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/empresa";
        }
    }

    @RequestMapping(value = "/empresa/{empresaId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("empresaId") int empresaId,
            Model model) {
        Empresa empresa = repository.findOne((long) empresaId);
        model.addAttribute("empresa", empresa);

        return "empresas/empresaForm";
    }

    @RequestMapping(value = "/empresa/{empresaId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@PathVariable("empresaId") int empresaId,
            @Valid Empresa empresa, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "empresas/empresaForm";
        } else {
            Empresa empresaUpdate = repository.findOne((long) empresaId);
            empresaUpdate.setName(empresa.getName());
            empresaUpdate.setRazaoSocial(empresa.getRazaoSocial());
            empresaUpdate.setCNPJ(empresa.getCNPJ());
            empresaUpdate.setInscEstadual(empresa.getInscEstadual());
            empresaUpdate.setInscMunicipal(empresa.getInscMunicipal());
            empresaUpdate.setNIRE(empresa.getNIRE());
            empresaUpdate.setRamo(empresa.getRamo());
            empresaUpdate.setDataFundacao(empresa.getDataFundacao());
            empresaUpdate.setDataRegistro(empresa.getDataRegistro());
            empresaUpdate
                    .setNumeroFuncionarios(empresa.getNumeroFuncionarios());
            empresaUpdate.setObs(empresa.getObs());
            // tratar endereço
            Endereco endereco = new Endereco();
            empresaUpdate.setEndereco(endereco);
            empresaUpdate.getEndereco().setLogradouro(
                    empresa.getEndereco().getLogradouro());
            empresaUpdate.getEndereco().setComplemento(
                    empresa.getEndereco().getComplemento());
            empresaUpdate.getEndereco().setBairro(
                    empresa.getEndereco().getBairro());
            empresaUpdate.getEndereco().setCidade(
                    empresa.getEndereco().getCidade());
            empresaUpdate.getEndereco().setUF(empresa.getEndereco().getUF());
            empresaUpdate.getEndereco().setCEP(empresa.getEndereco().getCEP());
            // tratar endereco corporativo
            Endereco enderecoCorporativo = new Endereco();
            empresaUpdate.setEnderecoCorporativo(enderecoCorporativo);
            empresaUpdate.getEnderecoCorporativo().setLogradouro(
                    empresa.getEnderecoCorporativo().getLogradouro());
            empresaUpdate.getEnderecoCorporativo().setComplemento(
                    empresa.getEnderecoCorporativo().getComplemento());
            empresaUpdate.getEnderecoCorporativo().setBairro(
                    empresa.getEnderecoCorporativo().getBairro());
            empresaUpdate.getEnderecoCorporativo().setCidade(
                    empresa.getEnderecoCorporativo().getCidade());
            empresaUpdate.getEnderecoCorporativo().setUF(
                    empresa.getEnderecoCorporativo().getUF());
            empresaUpdate.getEnderecoCorporativo().setCEP(
                    empresa.getEnderecoCorporativo().getCEP());
            // tratar contato
            Contato contato = new Contato();
            empresaUpdate.setContato(contato);
            empresaUpdate.getContato().setTel1(empresa.getContato().getTel1());
            empresaUpdate.getContato().setTel2(empresa.getContato().getTel2());
            empresaUpdate.getContato().setTel3(empresa.getContato().getTel3());
            empresaUpdate.getContato()
                    .setMovel(empresa.getContato().getMovel());
            empresaUpdate.getContato().setFax(empresa.getContato().getFax());
            empresaUpdate.getContato().setSite(empresa.getContato().getSite());
            empresaUpdate.getContato()
                    .setEmail(empresa.getContato().getEmail());
            empresaUpdate.getContato()
                    .setSkype(empresa.getContato().getSkype());

            repository.save(empresaUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Empresa atualizada com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/empresa";
        }
    }

    @RequestMapping(value = "/empresa/{empresaId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("empresaId") int empresaId, Model model) {
        Empresa empresa = repository.findOne((long) empresaId);
        model.addAttribute("empresa", empresa);

        return "empresas/empresaDetail";
    }

    @RequestMapping(value = "/empresa/{empresaId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("empresaId") int empresaId,
            SessionStatus status) {

        repository.delete((long) empresaId);
        status.setComplete();
        return "redirect:/empresa";
    }
}
