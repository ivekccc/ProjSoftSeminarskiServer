/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.korisnik;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Korisnik;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOVratiListuKorisnika extends AbstractSO {
    private List<Korisnik> listaKorisnika;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        listaKorisnika = (List<Korisnik>) (List<?>) DBBroker.getInstance().bezuslovniSelect(new Korisnik());
    }

    public List<Korisnik> getListaKorisnika() {
        return listaKorisnika;
    }
    
}
