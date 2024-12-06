package so.korisnik;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Korisnik;
import domain.Rekvizit;
import domain.Zaduzenje;
import domain.Termin;
import so.AbstractSO;
import java.util.ArrayList;

/**
 *
 * @author x
 */
public class SOBrisanjeKorisnika extends AbstractSO {

    private boolean uspesnoBrisanjeKorisnika;

    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
        if (!(ado instanceof Korisnik)) {
            throw new Exception("Prosledjeni objekat nije instanca klase Korisnik");
        }
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        Korisnik korisnikZaBrisanje = (Korisnik) ado;
        
        uspesnoBrisanjeKorisnika = obrisiZaduzenjaIKolicinuRekvizita(korisnikZaBrisanje);

        if (uspesnoBrisanjeKorisnika) {
            uspesnoBrisanjeKorisnika = otkazivanjeTerminaKorisnika(korisnikZaBrisanje);

            if (uspesnoBrisanjeKorisnika) {
                DBBroker.getInstance().delete(korisnikZaBrisanje);
            }
        }
    }

    public boolean isUspesnoBrisanjeKorisnika() {
        return uspesnoBrisanjeKorisnika;
    }

   private boolean obrisiZaduzenjaIKolicinuRekvizita(Korisnik korisnikZaBrisanje) throws Exception {
    ArrayList<AbstractDomainObject> svaZaduzenja = DBBroker.getInstance().bezuslovniSelect(new Zaduzenje());

    
    ArrayList<Zaduzenje> zaduzenjaZaBrisanje = new ArrayList<>();
    for (AbstractDomainObject ado : svaZaduzenja) {
        Zaduzenje zaduzenje = (Zaduzenje) ado;
        if (zaduzenje.getKorisnik().getKorisnikId() == korisnikZaBrisanje.getKorisnikId()) {
            zaduzenjaZaBrisanje.add(zaduzenje);
        }
    }

    
    for (Zaduzenje z : zaduzenjaZaBrisanje) {
        Rekvizit rekvizit = z.getRekvizit();
        if (rekvizit != null) {
            rekvizit.setKolicina(rekvizit.getKolicina() + z.getZaduzena_kolicina());
            DBBroker.getInstance().update(rekvizit);  
        }

        DBBroker.getInstance().delete(z);  
    }

    return true;
    }

    
    private boolean otkazivanjeTerminaKorisnika(Korisnik korisnikZaBrisanje) throws Exception {
       
        Termin termin = new Termin();
        termin.setKorisnik(korisnikZaBrisanje);

        ArrayList<AbstractDomainObject> termini = DBBroker.getInstance().select(termin);

       
        for (AbstractDomainObject ado : termini) {
            Termin t = (Termin) ado;
            t.setZakazan(false);
            t.setKorisnik(null);
            DBBroker.getInstance().update(t);  
        }

        return true;  
    }
}

