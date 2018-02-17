package br.com.imaginativo.itcm.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.imaginativo.itcm.model.Parcela;
import br.com.imaginativo.itcm.repository.ParcelaRepository;
import br.com.imaginativo.itcm.util.ParcelaData;

@Controller
public class PagamentoRestController {

    @Autowired
    ParcelaRepository parcelaRepository;

    @RequestMapping(value = "/api/loaddata/parcela/{parcelaId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ParcelaData loadUnidadeByEmpresa(
            @PathVariable("parcelaId") int parcelaId) {

        Parcela parcela = parcelaRepository.findOne((long) parcelaId);

        ParcelaData parcelaData = new ParcelaData();

        parcelaData.setNumeroParcela(parcela.getNumeroParcela());
        parcelaData.setDataReferencia(parcela.getDataReferencia());
        parcelaData.setDataVencimento(parcela.getDataVencimento());
        parcelaData.setValor(parcela.getValor());

        return parcelaData;
    }

}
