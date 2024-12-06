/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.zaduzenja;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Rekvizit;
import domain.Zaduzenje;
import java.util.ArrayList;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOSacuvajZaduzenje extends AbstractSO {

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Zaduzenje)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Zaduzenje!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Zaduzenje novoZaduzenje = (Zaduzenje) ado;

        
        DBBroker.getInstance().insert(novoZaduzenje);

        
        ArrayList<AbstractDomainObject> sviRekviziti = DBBroker.getInstance().bezuslovniSelect(new Rekvizit());

        Rekvizit stvarniRekvizit = null;
        
        for (AbstractDomainObject objekat : sviRekviziti) {
            Rekvizit rekvizitIzBaze = (Rekvizit) objekat;
            if (rekvizitIzBaze.getIdRekvizita() == novoZaduzenje.getRekvizit().getIdRekvizita()) {
                stvarniRekvizit = rekvizitIzBaze;
                break;
            }
        }

        
        if (stvarniRekvizit != null) {
            stvarniRekvizit.setKolicina(stvarniRekvizit.getKolicina() - novoZaduzenje.getZaduzena_kolicina());
            DBBroker.getInstance().update(stvarniRekvizit);
        } else {
            throw new Exception("Rekvizit nije pronađen u bazi.");
        }
    }
}
