package br.com.imaginativo.itcm.web;

import java.util.ArrayList;
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

import br.com.imaginativo.itcm.model.Ingresso;
import br.com.imaginativo.itcm.model.Parcela;
import br.com.imaginativo.itcm.repository.IngressoRepository;
import br.com.imaginativo.itcm.repository.ParcelaRepository;
import br.com.imaginativo.itcm.util.PagamentoParcelaConfig;

@Controller
public class PagamentoController {

    @Autowired
    IngressoRepository repository;

    @Autowired
    ParcelaRepository parcelaRepository;

    @RequestMapping(value = "/pagamento/{ingressoId}/lancar", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("ingressoId") int ingressoId,
            Model model) {
        Ingresso ingresso = repository.findOne((long) ingressoId);

        List<Parcela> parcelasAPagar = new ArrayList<Parcela>();

        for (Parcela parcela : ingresso.getPagamento().getParcelas()) {
            if (parcela.getDataPagamento() == null
                    && parcela.getValorPago() == null) {
                parcelasAPagar.add(parcela);
            }
        }

        model.addAttribute("parcelasAPagar", parcelasAPagar);
        model.addAttribute("ingresso", ingresso);

        return "pagamentos/pagamentoForm";
    }

    @RequestMapping(value = "/pagamento/{ingressoId}/lancar", method = RequestMethod.POST)
    public String processCreationForm(
            @PathVariable("ingressoId") int ingressoId, @Valid Parcela parcela,
            BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttributes,
            PagamentoParcelaConfig pagamentoParcelaConfig) {

        if (result.hasErrors()) {
            System.out.println("Ingresso in if");
            System.out.println("Ingresso " + result.toString());
            return "pagamentos/pagamentoForm";
        } else {
            System.out.println("Atualizando parcela");
            System.out.println("ID parcela ->  " + parcela.getId());

            Parcela updateParcela = parcelaRepository.findOne(parcela.getId());
            updateParcela.setDataPagamento(parcela.getDataPagamento());
            updateParcela.setValorPago(parcela.getValorPago());
            parcelaRepository.save(updateParcela);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Pagamento lançado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            // redirecionar para detalhe da turma de origem
            return "redirect:/ingresso/" + ingressoId + "/detail";
        }
    }

}
