/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rekvizit;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Rekvizit;
import domain.TipRekvizita;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOVratiRekvizite extends AbstractSO {
    private List<Rekvizit> listaRekvizita;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if(ado!=null && !(ado instanceof TipRekvizita)){
            throw new Exception("Prosledjeni objekat nije instanca klase TipRekvizita");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Rekvizit rekvizit = new Rekvizit();
        if (ado == null) {
            
            listaRekvizita = (List<Rekvizit>) (List<?>) DBBroker.getInstance().bezuslovniSelect(rekvizit);
        } else {
            
            rekvizit.setTipRekvizita((TipRekvizita) ado);
            listaRekvizita = (List<Rekvizit>) (List<?>) DBBroker.getInstance().select(rekvizit);
        }
    }

    public List<Rekvizit> getListaRekvizita() {
        return listaRekvizita;
    }
    
}
