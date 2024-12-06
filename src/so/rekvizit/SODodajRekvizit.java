/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rekvizit;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Rekvizit;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SODodajRekvizit extends AbstractSO {
    private boolean uspesnoDodavanjeRekvizita;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if(!(ado instanceof  Rekvizit)){
            throw new Exception("Prosledjeni objekat nije instanca klase Rekvizit");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Rekvizit rekvizit=(Rekvizit)ado;
        DBBroker.getInstance().insert(rekvizit);
        uspesnoDodavanjeRekvizita = true;
    }

    public boolean isUspesnoDodavanjeRekvizita() {
        return uspesnoDodavanjeRekvizita;
    }
    
}
