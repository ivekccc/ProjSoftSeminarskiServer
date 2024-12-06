/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.termin;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Korisnik;
import domain.Termin;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOOtkazivanjeTermina extends AbstractSO {

    private boolean uspesnoOtkazivanje;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
         if (!(ado instanceof Termin)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Termin!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Termin termin = (Termin) ado;
        
        Korisnik korisnik = termin.getKorisnik(); 
        termin.setZakazan(false);
        termin.setKorisnik(null);
        
        DBBroker.getInstance().update(termin);  
        
        
         
        if (korisnik != null) {
            korisnik.setBrojOtkazivanja(korisnik.getBrojOtkazivanja() + 1);
            DBBroker.getInstance().update(korisnik);  
        }

        uspesnoOtkazivanje = true;
    }

    public boolean isUspesnoOtkazivanje() {
        return uspesnoOtkazivanje;
    }
    
    
}
