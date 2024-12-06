/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.administrator;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Administrator;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOProveraEmaila extends AbstractSO {
    private boolean emailUUpotrebi;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if(!(ado instanceof Administrator)){
            throw new Exception("Prosledjeni objeckat nije instanca klase Administrator");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Administrator admin=(Administrator)ado;
        ArrayList<AbstractDomainObject> adminiSaIstimEmailom = DBBroker.getInstance().select(admin);
        emailUUpotrebi = !adminiSaIstimEmailom.isEmpty();
    }

    public boolean isEmailUUpotrebi() {
        return emailUUpotrebi;
    }
    
}
