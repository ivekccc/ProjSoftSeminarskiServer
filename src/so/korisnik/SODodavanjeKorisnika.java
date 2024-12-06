/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.korisnik;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Korisnik;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SODodavanjeKorisnika extends AbstractSO {
    private boolean uspesnoDodavanjeKorisnika;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if(!(ado instanceof Korisnik)){
            throw new Exception("Prosledjeni objekat nije instanca klase Korisnik");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Korisnik korisnikZaDodavanje=(Korisnik)ado;
        DBBroker.getInstance().insert(korisnikZaDodavanje);
        uspesnoDodavanjeKorisnika=true;
    }

    public boolean isUspesnoDodavanjeKorisnika() {
        return uspesnoDodavanjeKorisnika;
    }
    
    
    
}
