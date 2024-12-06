/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.zaduzenja;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Zaduzenje;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOVratiZaduzenja extends AbstractSO {
    private List<Zaduzenje> listaZaduzenja;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        listaZaduzenja = (List<Zaduzenje>) (List<?>) DBBroker.getInstance().bezuslovniSelect(new Zaduzenje());
    }

    public List<Zaduzenje> getListaZaduzenja() {
        return listaZaduzenja;
    }
    
}
