package br.com.imaginativo.itcm.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.imaginativo.itcm.model.Empresa;
import br.com.imaginativo.itcm.model.Unidade;
import br.com.imaginativo.itcm.repository.EmpresaRepository;
import br.com.imaginativo.itcm.repository.UnidadeRepository;
import br.com.imaginativo.itcm.ui.UIItem;

@Controller
public class UnidadeRestController {

    @Autowired
    UnidadeRepository repository;

    @Autowired
    EmpresaRepository empresaRepository;

    @RequestMapping(value = "/api/loadselect/unidades/empresa/{empresaId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<UIItem> loadUnidadeByEmpresa(
            @PathVariable("empresaId") int empresaId) {

        Empresa empresa = empresaRepository.findOne((long) empresaId);

        List<Unidade> unidades = (List<Unidade>) repository
                .findByEmpresa(empresa);

        List<UIItem> ret = new ArrayList<UIItem>();

        for (Unidade unidade : unidades) {
            UIItem uiItem = new UIItem(unidade.getId(), unidade.getName());
            ret.add(uiItem);
        }

        return ret;
    }

}
