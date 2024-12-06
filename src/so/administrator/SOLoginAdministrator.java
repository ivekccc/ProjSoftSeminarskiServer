/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.administrator;

import domain.AbstractDomainObject;
import domain.Administrator;
import so.AbstractSO;
import db.DBBroker;
import java.util.ArrayList;

/**
 *
 * @author x
 */
public class SOLoginAdministrator extends AbstractSO {
    
    private Administrator ulogovaniAdministrator;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Administrator)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Administrator!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        System.out.println("SO Prijava");
        Administrator administrator=(Administrator)ado;
        ArrayList<Administrator> administratori = (ArrayList<Administrator>) (ArrayList<?>) DBBroker.getInstance().bezuslovniSelect(administrator);
        for (Administrator a : administratori) {
            if (a.getEmail().equals(administrator.getEmail()) && a.getLozinka().equals(administrator.getLozinka())) {
                ulogovaniAdministrator = a;
                return;
            }
        }
        throw new Exception("Neispravno korisničko ime ili šifra.");
        
    }
    public Administrator getUlogovaniAdministrator() {
        return ulogovaniAdministrator;
    }
    
}
