package br.com.imaginativo.itcm.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.imaginativo.itcm.model.Ambiente;
import br.com.imaginativo.itcm.model.Unidade;
import br.com.imaginativo.itcm.repository.AmbienteRepository;
import br.com.imaginativo.itcm.repository.UnidadeRepository;
import br.com.imaginativo.itcm.ui.UIItem;

@Controller
public class AmbienteRestController {

    @Autowired
    AmbienteRepository repository;

    @Autowired
    UnidadeRepository unidadeRepository;

    @RequestMapping(value = "/api/loadselect/ambientes/unidade/{unidadeId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<UIItem> loadAmbienteByUnidade(
            @PathVariable("unidadeId") int unidadeId) {

        Unidade unidade = unidadeRepository.findOne((long) unidadeId);

        List<Ambiente> ambientes = (List<Ambiente>) repository
                .findByUnidade(unidade);

        List<UIItem> ret = new ArrayList<UIItem>();

        for (Ambiente ambiente : ambientes) {
            UIItem uiItem = new UIItem(ambiente.getId(), ambiente.getName());
            ret.add(uiItem);
        }

        return ret;
    }
}
