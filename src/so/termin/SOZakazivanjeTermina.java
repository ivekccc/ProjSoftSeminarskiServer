/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.termin;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Termin;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOZakazivanjeTermina extends AbstractSO {
    private boolean uspesnoZakazivanje;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Termin)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Termin!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Termin terminZaZakazivanje = (Termin) ado;

        
        terminZaZakazivanje.setZakazan(true);
        DBBroker.getInstance().update(terminZaZakazivanje);
        uspesnoZakazivanje = true;
    }

    public boolean isUspesnoZakazivanje() {
        return uspesnoZakazivanje;
    }

    

    
    
}
