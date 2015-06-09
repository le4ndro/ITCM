package br.com.imaginativo.itcm.web;

import java.util.ArrayList;
import java.util.Date;
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
import br.com.imaginativo.itcm.model.Ingresso;
import br.com.imaginativo.itcm.model.Pagamento;
import br.com.imaginativo.itcm.model.Parcela;
import br.com.imaginativo.itcm.model.Turma;
import br.com.imaginativo.itcm.repository.AlunoRepository;
import br.com.imaginativo.itcm.repository.IngressoRepository;
import br.com.imaginativo.itcm.repository.TurmaRepository;
import br.com.imaginativo.itcm.util.PagamentoParcelaConfig;

@Controller
public class IngressoController {

    @Autowired
    IngressoRepository repository;

    @Autowired
    TurmaRepository turmaRepository;

    @Autowired
    AlunoRepository alunoRepository;

    // @RequestMapping(value = "/ingresso", method = RequestMethod.GET)
    // public String ingressos(Model model) {
    // List<Ingresso> ingressos = (List<Ingresso>) repository.findAll();
    // model.addAttribute("ingressos", ingressos);
    // System.out.println("Ingressos " + ingressos.toString());
    // return "ingressos/ingressoList";
    // }

    @RequestMapping(value = "/ingresso/turma/{turmaId}/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("turmaId") int turmaId,
            Model model) {
        Turma turma = turmaRepository.findOne((long) turmaId);
        Ingresso ingresso = new Ingresso();
        ingresso.setTurma(turma);
        Pagamento pagamento = new Pagamento();
        ingresso.setPagamento(pagamento);
        ingresso.getPagamento().setValorBruto(turma.getCurso().getValor());

        model.addAttribute("ingresso", ingresso);

        List<String> formasDePagamento = new ArrayList<String>();

        formasDePagamento.add("Dinheiro");
        formasDePagamento.add("Boleto");
        formasDePagamento.add("Cartão de Crédito");
        formasDePagamento.add("Cartão de Débito");
        formasDePagamento.add("Cheque");

        model.addAttribute("formasDePagamento", formasDePagamento);

        List<String> parcelas = new ArrayList<String>();
        parcelas.add("1");
        parcelas.add("2");
        parcelas.add("3");
        parcelas.add("4");
        parcelas.add("5");
        parcelas.add("6");
        parcelas.add("7");
        parcelas.add("8");
        parcelas.add("9");
        parcelas.add("10");
        parcelas.add("11");
        parcelas.add("12");

        model.addAttribute("parcelas", parcelas);

        List<Aluno> alunos = (List<Aluno>) alunoRepository.findAll();
        model.addAttribute("alunos", alunos);

        return "ingressos/ingressoForm";
    }

    @RequestMapping(value = "/ingresso/turma/{turmaId}/new", method = RequestMethod.POST)
    public String processCreationForm(@PathVariable("turmaId") int turmaId,
            @Valid Ingresso ingresso, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes,
            PagamentoParcelaConfig pagamentoParcelaConfig) {

        if (result.hasErrors()) {
            System.out.println("Ingresso in if");
            System.out.println("Ingresso " + result.toString());
            return "ingressos/ingressoForm";
        } else {
            System.out.println("Ingresso in else");
            Turma turma = turmaRepository.findOne((long) turmaId);
            ingresso.setTurma(turma);

            long now = System.currentTimeMillis();
            ingresso.setDataIngresso(new Date(now));

            ingresso.setStatus("inscrito");

            List<Parcela> parcelas = new ArrayList<Parcela>();

            int factPrimeiraParcelaAVista = 0;
            if (!pagamentoParcelaConfig.isPrimeiraParcelaAVista()) {
                factPrimeiraParcelaAVista = 1;
            }

            for (int i = 0; i < pagamentoParcelaConfig.getParcelas(); i++) {
                DateTime hoje = new DateTime();
                DateTime vencimento = (hoje
                        .withDayOfMonth(pagamentoParcelaConfig
                                .getDiaVencimento())).plusMonths(i
                        + factPrimeiraParcelaAVista);
                Parcela parcela = new Parcela();

                parcela.setValor(pagamentoParcelaConfig.getValorParcela());
                parcela.setDataVencimento(vencimento.toDate());
                parcela.setDataReferencia(vencimento.withDayOfMonth(1).toDate());
                parcela.setNumeroParcela(i + 1);

                // Caso pagamento em dinheiro ou cartão de débito (somente 1
                // parcela à vista) ou 1 parcela à vista
                // gravando valor de pagamento e data de pagamento
                if ((parcela.getNumeroParcela() == 1)
                        && ((ingresso.getPagamento().getFormaPagamento()
                                .toUpperCase().equals("DINHEIRO") || ingresso
                                .getPagamento().getFormaPagamento()
                                .toUpperCase().equals("CARTÃO DE DÉBITO")) || pagamentoParcelaConfig
                                .isPrimeiraParcelaAVista())) {

                    parcela.setValorPago(pagamentoParcelaConfig
                            .getValorParcela());
                    parcela.setDataPagamento(hoje.toDate());

                }

                parcela.setPagamento(ingresso.getPagamento());

                parcelas.add(parcela);
            }

            ingresso.getPagamento().setParcelas(parcelas);

            repository.save(ingresso);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Aluno matriculado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            // redirecionar para detalhe da turma de origem
            return "redirect:/turma/" + turmaId + "/detail";
        }
    }

    @RequestMapping(value = "/ingresso/{ingressoId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("ingressoId") int ingressoId,
            Model model) {
        Ingresso ingresso = repository.findOne((long) ingressoId);
        model.addAttribute("ingresso", ingresso);

        return "ingressos/ingressoForm";
    }

    @RequestMapping(value = "/ingresso/{ingressoId}/edit", method = RequestMethod.POST)
    public String processUpdateForm(@PathVariable("ingressoId") int ingressoId,
            @Valid Ingresso ingresso, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {

            return "ingressos/ingressoForm";
        } else {
            Ingresso ingressoUpdate = repository.findOne((long) ingressoId);
            // ingressoUpdate.setName(ingresso.getName());
            // ingressoUpdate.setDescricao(ingresso.getDescricao());
            // Atualizar campos
            Long turmaId = ingresso.getTurma().getId();
            repository.save(ingressoUpdate);

            String msginfo = "<script>$(document).ready(function() {toastr.success('Matrícula atualizado com sucesso !');});</script>";
            redirectAttributes.addFlashAttribute("msginfo", msginfo);

            status.setComplete();
            return "redirect:/turma/" + turmaId + "/detail";
        }
    }

    @RequestMapping(value = "/ingresso/{ingressoId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("ingressoId") int ingressoId, Model model) {
        Ingresso ingresso = repository.findOne((long) ingressoId);
        model.addAttribute("ingresso", ingresso);

        return "ingressos/ingressoDetail";
    }

    @RequestMapping(value = "/ingresso/{ingressoId}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("ingressoId") int ingressoId,
            SessionStatus status) {

        Ingresso ingresso = repository.findOne((long) ingressoId);

        long turmaId = ingresso.getTurma().getId();
        ingresso = null;
        repository.delete((long) ingressoId);
        status.setComplete();
        return "redirect:/turma/" + turmaId + "/detail";
    }

}
