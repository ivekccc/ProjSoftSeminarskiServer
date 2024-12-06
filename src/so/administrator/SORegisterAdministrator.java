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
public class SORegisterAdministrator extends AbstractSO {
    private boolean uspesnaRegistracija;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Administrator)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Administrator!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        System.out.println("SO Registracija");
        Administrator administrator = (Administrator) ado;

        int affectedRows = DBBroker.getInstance().insert(administrator).executeUpdate();
        uspesnaRegistracija = affectedRows > 0;
    }

    public boolean isUspesnaRegistracija() {
        return uspesnaRegistracija;
    }
    
    
}
