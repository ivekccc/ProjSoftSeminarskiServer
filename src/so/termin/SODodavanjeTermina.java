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
public class SODodavanjeTermina extends AbstractSO {

    private boolean uspesnoDodavanjeTermina;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
         if (!(ado instanceof Termin)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Termin!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Termin termin = (Termin) ado;
        
        
        DBBroker.getInstance().insert(termin);
        uspesnoDodavanjeTermina = true;
    }

    public boolean isUspesnoDodavanjeTermina() {
        return uspesnoDodavanjeTermina;
    }
    
    
}
