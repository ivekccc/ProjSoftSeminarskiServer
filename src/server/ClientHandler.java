/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import controller.ServerController;
import domain.Administrator;
import domain.Korisnik;
import domain.Rekvizit;
import domain.Termin;
import domain.TipRekvizita;
import domain.Zaduzenje;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import operation.Operation;
import transfer.Request;
import transfer.Response;

/**
 *
 * @author x
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Server server;
    private boolean isRunning;
    private Administrator ulogovanAdministrator;
    
     public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.isRunning = true;
    }

    public Administrator getUlogovanAdministrator() {
        return ulogovanAdministrator;
    }
    
    @Override
    public void run() {
        System.out.println("ClientHandler: Početak run metode");
        try {
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());

            while (isRunning) {
                try {
                    Request request = (Request) input.readObject();
                    System.out.println("ClientHandler: Primljen request: " + request.getOperation());
                    Response response = handleRequest(request);
                    output.writeObject(response);
                    System.out.println("ClientHandler: Poslat response: " + response.isSuccessful());
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    isRunning = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
    public void close() {
        try {
            if (ulogovanAdministrator != null) {
                server.removeLoggedInAdministrator(ulogovanAdministrator);
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private Response handleRequest(Request request) {
        Response response = new Response();
        try {
        switch (request.getOperation()) {
            case Operation.LOGIN:
                    Administrator administrator = (Administrator) request.getRequestObject();
                    System.out.println("KLIJENT HENDLER "+server.isAdministratorLoggedIn(administrator));
                    if (server.isAdministratorLoggedIn(administrator)) {
                        response.setSuccessful(false);
                        response.setMessage("Administrator je već ulogovan.");
                    } else {
                        Administrator loggedInAdministrator = ServerController.getInstance().prijaviAdministratora(administrator);
                        if (loggedInAdministrator != null) {
                            this.ulogovanAdministrator = loggedInAdministrator;
                            response.setSuccessful(true);
                            response.setResult(loggedInAdministrator);
                            response.setMessage("Uspesna prijava");
                        } else {
                            response.setSuccessful(false);
                            response.setMessage("Prijava nije uspela. Proverite email i šifru.");
                        }
                    }
                break;
            case Operation.REGISTER:
                Administrator admin=(Administrator)request.getRequestObject();
                boolean emailUUpotrebi=ServerController.getInstance().emailUUpotrebi(admin);
                if(emailUUpotrebi){
                    response.setResult(false);
                    response.setSuccessful(false);
                    response.setMessage("Neuspesna registracija. Ovaj email je vec u upotrebi");
                }
                else{
                    boolean uspesnoRegistrovanje=ServerController.getInstance().registracijaAdministratora(admin);
                    if(uspesnoRegistrovanje){
                        response.setSuccessful(true);
                        response.setResult(true);
                        response.setMessage("Uspesna registracija");
                    }
                    else{
                        response.setSuccessful(false);
                        response.setMessage("Neuspesna registracija");
                    }
                }
                break;
            case Operation.ODJAVA:
                if (ulogovanAdministrator != null) {
                        ServerController.getInstance().odjaviMenadzera(ulogovanAdministrator);
                        ulogovanAdministrator = null;
                        System.out.println("Administrator uspešno odjavljen na serveru.");
                    }
                response.setResult(true);
                response.setSuccessful(true);
                response.setMessage("Uspešna odjava.");
                break;
            case Operation.IZBRISI_TERMIN:
                Termin t=(Termin)request.getRequestObject();
                boolean uspesnoBrisanjeTermina=ServerController.getInstance().izbrisiTermin(t);
                if(uspesnoBrisanjeTermina){
                    response.setSuccessful(true);
                    response.setResult(true);
                    response.setMessage("Uspesno brisanje termina");
                }
                else{
                    response.setSuccessful(false);
                    response.setResult(false);
                    response.setMessage("Greska pri brisanju termina");
                }
                break;
            case Operation.VRATI_TERMINE:
                List<Termin> listaTermina=ServerController.getInstance().vratiTermine();
                response.setResult(listaTermina);
                response.setSuccessful(true);
                break;
            case Operation.OTKAZI_TERMIN:
                Termin terminZaOtkazivanje=(Termin)request.getRequestObject();
                    boolean uspesnoOtkazivanjeTermina=ServerController.getInstance().otkaziTermin(terminZaOtkazivanje);
                    response.setResult(uspesnoOtkazivanjeTermina);
                    response.setSuccessful(uspesnoOtkazivanjeTermina);
                    if(uspesnoOtkazivanjeTermina){
                        response.setMessage("Uspesno otkazivanje termina");
                    }
                    else{
                        response.setMessage("Greska pri otkazivanju termina");
                    }
                break;
            case Operation.DODAJ_TERMIN:
                Termin terminZaDodavanje=(Termin)request.getRequestObject();
                boolean uspesnoDodavanjeTermina=ServerController.getInstance().dodajTermin(terminZaDodavanje);
                response.setSuccessful(uspesnoDodavanjeTermina);
                response.setResult(uspesnoDodavanjeTermina);
                if(uspesnoDodavanjeTermina){
                    response.setMessage("Uspesno dodavanje termina");
                }
                else{
                    response.setMessage("Greska pri dodavanju termina");
                }
                break;
            case Operation.VRATI_KORISNIKE:
                List<Korisnik> listaKorisnika=ServerController.getInstance().vratiKorisnike();
                response.setResult(listaKorisnika);
                response.setSuccessful(true);
                break;
            case Operation.ZAKAZI_TERMIN:
                Termin terminZaZakazivanje=(Termin)request.getRequestObject();
                boolean uspesnoZakazivanjeTermina=ServerController.getInstance().zakaziTermin(terminZaZakazivanje);
                response.setResult(uspesnoZakazivanjeTermina);
                if(uspesnoZakazivanjeTermina){
                    response.setMessage("Uspesno zakazivanje termina");
                    response.setSuccessful(true);
                }
                else{
                    response.setMessage("Greska pri zakazivanju termina");
                    response.setResult(false);
                }
                break;
            case Operation.IZBRISI_KORISNIKA:
                Korisnik korisnikZaBrisanje=(Korisnik)request.getRequestObject();
                boolean uspesnoBrisanjeKorisnika=ServerController.getInstance().izbrisiKorisnika(korisnikZaBrisanje);
                response.setResult(uspesnoBrisanjeKorisnika);
                response.setSuccessful(uspesnoBrisanjeKorisnika);
                if(uspesnoBrisanjeKorisnika){
                    response.setMessage("Uspesno brisanje korisnika");
                }
                else{
                    response.setMessage("Greska pri brisanju korisnika");
                }
                break;
            case Operation.DODAJ_KORISNIKA:
                Korisnik korisnikZaDodavanje=(Korisnik)request.getRequestObject();
                boolean uspesnoDodavanjeKorisnika=ServerController.getInstance().dodajKorisnika(korisnikZaDodavanje);
                response.setResult(uspesnoDodavanjeKorisnika);
                response.setSuccessful(uspesnoDodavanjeKorisnika);
                if(uspesnoDodavanjeKorisnika){
                    response.setMessage("Uspesno dodavanje korisnika");
                }
                else{
                    response.setMessage("Neuspesno dodavanje korisnika. ");
                }
                break;
            case Operation.IZMENI_KORISNIKA:
                Korisnik korisnikZaIzmenu=(Korisnik)request.getRequestObject();
                boolean uspesnaIzmenaKorisnika=ServerController.getInstance().izmeniKorisnika(korisnikZaIzmenu);
                response.setResult(uspesnaIzmenaKorisnika);
                response.setSuccessful(uspesnaIzmenaKorisnika);
                if(uspesnaIzmenaKorisnika){
                    response.setMessage("Uspesna izmena korisnika");
                } 
                else{
                    response.setMessage("Greska pri izmeni korisnika");
                }
                break;
            case Operation.VRATI_REKVIZITE:
                TipRekvizita tip=(TipRekvizita)request.getRequestObject();
                List<Rekvizit> listaRekvizita=ServerController.getInstance().vratiRekvizite(tip);
                response.setResult(listaRekvizita);
                response.setSuccessful(true);
                break;
            case Operation.VRATI_TIPOVE:
                List<TipRekvizita> listaTipova=ServerController.getInstance().vratiTipove();
                response.setResult(listaTipova);
                response.setSuccessful(true);
                break;
            case Operation.SACUVAJ_ZADUZENJE:
                List<Zaduzenje> listaZaduzenja=(List<Zaduzenje>)request.getRequestObject();
                boolean uspesnoCuvanjeZaduzenja=ServerController.getInstance().sacuvajZaduzenja(listaZaduzenja);
                response.setResult(uspesnoCuvanjeZaduzenja);
                response.setSuccessful(uspesnoCuvanjeZaduzenja);
                response.setMessage("Uspesno zaduzivanje!");
                break;
            case Operation.VRATI_ZADUZENJA:
                List<Zaduzenje> listaZaduzenjaIzBaze=ServerController.getInstance().vratiZaduzenja();
                response.setResult(listaZaduzenjaIzBaze);
                response.setSuccessful(true);
                break;
            case Operation.RAZDUZI:
                Zaduzenje zaduzenje=(Zaduzenje)request.getRequestObject();
                boolean uspesnoRazduzenje=ServerController.getInstance().razduzi(zaduzenje);
                response.setResult(uspesnoRazduzenje);
                response.setSuccessful(uspesnoRazduzenje);
                if(uspesnoRazduzenje){
                    response.setMessage("Uspesno razduzivanje.");
                }
                else{
                    response.setMessage("Greska pri razduzivanju");
                }
                break;
            case Operation.DODAJ_REKVIZIT:
                Rekvizit rekvizit=(Rekvizit)request.getRequestObject();
                boolean uspesnoDodavanjeRekvizita=ServerController.getInstance().dodajRekvizit(rekvizit);
                response.setResult(uspesnoDodavanjeRekvizita);
                response.setSuccessful(uspesnoDodavanjeRekvizita);
                if(uspesnoDodavanjeRekvizita){
                    response.setMessage("Uspesno dodavanje rekvizita!");
                }
                else{
                    response.setMessage("Greska pri dodavanju rekvizita!");
                }
                break;
                case Operation.PREKINI_KONEKCIJU:
                    isRunning = false;
                    break;
            default:
                    response.setSuccessful(false);
                    response.setMessage("Nepoznata operacija.");
                break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setSuccessful(false);
            response.setMessage("Greška: " + e.getMessage());
        }
        return response;
        }
    }

