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
public class SORazduzi extends AbstractSO {
    private boolean uspesnoRazduzivanje;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if(!(ado instanceof Zaduzenje)){
            throw new Exception("Prosledjeni objekat nije instanca klase Zaduzenje!");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Zaduzenje zaduzenje = (Zaduzenje) ado;

        
        ArrayList<AbstractDomainObject> sviRekviziti = DBBroker.getInstance().bezuslovniSelect(new Rekvizit());

        Rekvizit rekvizitZaUpdate = null;

        
        for (AbstractDomainObject objekat : sviRekviziti) {
            Rekvizit rekvizitIzBaze = (Rekvizit) objekat;
            if (rekvizitIzBaze.getIdRekvizita() == zaduzenje.getRekvizit().getIdRekvizita()) {
                rekvizitZaUpdate = rekvizitIzBaze;
                break;
            }
        }

        if (rekvizitZaUpdate != null) {
            rekvizitZaUpdate.setKolicina(rekvizitZaUpdate.getKolicina() + zaduzenje.getZaduzena_kolicina());
            DBBroker.getInstance().update(rekvizitZaUpdate); 

            
            DBBroker.getInstance().delete(zaduzenje); // Koristi generički delete

            uspesnoRazduzivanje = true;
        } else {
            throw new Exception("Rekvizit nije pronađen!");
        }
    }

    public boolean isUspesnoRazduzivanje() {
        return uspesnoRazduzivanje;
    }
    
}
