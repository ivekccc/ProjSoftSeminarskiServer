/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.termin;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Termin;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOVratiTermine extends AbstractSO {
    private List<Termin> listaTermina;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
       
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        listaTermina = (List<Termin>) (List<?>) DBBroker.getInstance().select(new Termin());
    }

    public List<Termin> getListaTermina() {
        return listaTermina;
    }
    
    
}
