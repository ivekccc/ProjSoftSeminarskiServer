/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Administrator;
import domain.Korisnik;
import domain.Rekvizit;
import domain.Termin;
import domain.TipRekvizita;
import domain.Zaduzenje;
import forme.ServerForm;
import java.util.Date;
import java.util.List;
import server.Server;
import so.administrator.SOLoginAdministrator;
import so.administrator.SOProveraEmaila;
import so.administrator.SORegisterAdministrator;
import so.korisnik.SOBrisanjeKorisnika;
import so.korisnik.SODodavanjeKorisnika;
import so.korisnik.SOIzmenaKorisnika;
import so.korisnik.SOVratiListuKorisnika;
import so.rekvizit.SODodajRekvizit;
import so.rekvizit.SOVratiRekvizite;
import so.rekvizit.SOVratiTipove;
import so.termin.SODodavanjeTermina;
import so.termin.SOIzbrisiTermin;
import so.termin.SOOtkazivanjeTermina;
import so.termin.SOVratiTermine;
import so.termin.SOZakazivanjeTermina;
import so.zaduzenja.SORazduzi;
import so.zaduzenja.SOSacuvajZaduzenje;
import so.zaduzenja.SOVratiZaduzenja;

/**
 *
 * @author x
 */
public class ServerController {
    private static ServerController instance;
    private Server server;//referenca na server
    private ServerForm sf;
    private ServerController() {
        
    }

    public void setSf(ServerForm sf) {
        this.sf = sf;
    }
    
    
    public static ServerController getInstance() {
        if (instance == null) {
            instance = new ServerController();
        }
        return instance;
    }
    public void setServer(Server server) {
        this.server = server;
    }
    public Server getServer() {
        return this.server;    
    }

    public Administrator prijaviAdministratora(Administrator administrator) throws Exception{
        System.out.println("ServerController: prijava - Početak");
        SOLoginAdministrator so = new SOLoginAdministrator();
        so.templateExecute(administrator);

        Administrator ulogovaniAdministrator = so.getUlogovaniAdministrator();

        if(ulogovaniAdministrator!=null){
            server.addLoggedInAdministrator(ulogovaniAdministrator);
            System.out.println("Administrator prijavljen: " + ulogovaniAdministrator.getUsername());
            sf.osveziTabeluPrijavljenih();
        }
        return ulogovaniAdministrator;
    }

    public boolean registracijaAdministratora(Administrator admin) throws Exception {
        System.out.println("ServerController: registracija - Početak");
        SORegisterAdministrator so=new SORegisterAdministrator();
        so.templateExecute(admin);
        boolean uspesnaRegistracija=so.isUspesnaRegistracija();
        return uspesnaRegistracija;
    }
    
    public boolean odjaviMenadzera(Administrator administrator) throws Exception {
        if (!server.isAdministratorLoggedIn(administrator)) {
            throw new Exception("Administrator nije prijavljen.");
        }

        server.removeLoggedInAdministrator(administrator);
        sf.osveziTabeluPrijavljenih();
        System.out.println("Usao je u izbacivanje iz liste administratora");
        System.out.println("Da li je ostao ulogovan slucajno "+ server.isAdministratorLoggedIn(administrator));
        
        
        return true;
    }

    public boolean izbrisiTermin(Termin t) throws Exception {
        SOIzbrisiTermin so=new SOIzbrisiTermin();
        so.templateExecute(t);
        boolean uspesnoBrisanjeTermina=so.isUspesnoBrisanjeTermina();
        return uspesnoBrisanjeTermina;
        
    }

    public List<Termin> vratiTermine() throws Exception {
        SOVratiTermine so=new SOVratiTermine();
        so.templateExecute(null);
        return so.getListaTermina();
    }

    public boolean otkaziTermin(Termin terminZaOtkazivanje) throws Exception {
        SOOtkazivanjeTermina so=new SOOtkazivanjeTermina();
        so.templateExecute(terminZaOtkazivanje);
        return so.isUspesnoOtkazivanje();
    }

    public boolean dodajTermin(Termin terminZaDodavanje) throws Exception {
        SODodavanjeTermina so=new SODodavanjeTermina();
        so.templateExecute(terminZaDodavanje);
        return so.isUspesnoDodavanjeTermina();
    }

        public List<Korisnik> vratiKorisnike() throws Exception {
        SOVratiListuKorisnika so=new SOVratiListuKorisnika();
        so.templateExecute(null);
        return so.getListaKorisnika();
    }

        public boolean zakaziTermin(Termin terminZaZakazivanje) throws Exception {
        SOZakazivanjeTermina so=new SOZakazivanjeTermina();
        so.templateExecute(terminZaZakazivanje);
        return so.isUspesnoZakazivanje();
    }

    public boolean izbrisiKorisnika(Korisnik korisnikZaBrisanje) throws Exception {
        SOBrisanjeKorisnika so=new SOBrisanjeKorisnika();
        so.templateExecute(korisnikZaBrisanje);
        return so.isUspesnoBrisanjeKorisnika();
    }

    public boolean dodajKorisnika(Korisnik korisnikZaDodavanje) throws Exception {
        SODodavanjeKorisnika so=new SODodavanjeKorisnika();
        so.templateExecute(korisnikZaDodavanje);
        return so.isUspesnoDodavanjeKorisnika();
    }

    public boolean izmeniKorisnika(Korisnik korisnikZaIzmenu) throws Exception {
        SOIzmenaKorisnika so=new SOIzmenaKorisnika();
        so.templateExecute(korisnikZaIzmenu);
        return so.isUspesnaIzmenaKorisnika();
    }


    

    public boolean emailUUpotrebi(Administrator admin) throws Exception {
        SOProveraEmaila so=new SOProveraEmaila();
        so.templateExecute(admin);
        return so.isEmailUUpotrebi();
    }

    

    public List<Rekvizit> vratiRekvizite(TipRekvizita tip) throws Exception {
        SOVratiRekvizite so=new SOVratiRekvizite();
        so.templateExecute(tip);
        return so.getListaRekvizita();
    }

    

    public List<TipRekvizita> vratiTipove() throws Exception {
        SOVratiTipove so=new SOVratiTipove();
        so.templateExecute(null);
        return so.getListaTipove();
    }

    public boolean sacuvajZaduzenja(List<Zaduzenje> listaZaduzenja) throws Exception {
        for(Zaduzenje zaduzenje:listaZaduzenja){
            SOSacuvajZaduzenje so=new SOSacuvajZaduzenje();
            so.templateExecute(zaduzenje);
        }
        return true;
    }

    public List<Zaduzenje> vratiZaduzenja() throws Exception {
        SOVratiZaduzenja so=new SOVratiZaduzenja();
        so.templateExecute(null);
        return so.getListaZaduzenja();
    }

    public boolean razduzi(Zaduzenje zaduzenje) throws Exception {
        SORazduzi so=new SORazduzi();
        so.templateExecute(zaduzenje);
        return so.isUspesnoRazduzivanje();
    }

    public boolean dodajRekvizit(Rekvizit rekvizit) throws Exception {
        SODodajRekvizit so=new SODodajRekvizit();
        so.templateExecute(rekvizit);
        return so.isUspesnoDodavanjeRekvizita();
    }

    

    

    
    
}
