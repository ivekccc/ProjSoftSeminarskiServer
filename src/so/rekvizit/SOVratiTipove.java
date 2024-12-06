/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package so.rekvizit;

import db.DBBroker;
import domain.AbstractDomainObject;
import domain.Rekvizit;
import domain.TipRekvizita;
import java.util.List;
import so.AbstractSO;

/**
 *
 * @author x
 */
public class SOVratiTipove extends AbstractSO {
    private List<TipRekvizita> listaTipova;
    @Override
    protected void validate(AbstractDomainObject ado) throws Exception {
    }

    @Override
    protected void execute(AbstractDomainObject ado) throws Exception {
        listaTipova = (List<TipRekvizita>) (List<?>) DBBroker.getInstance().bezuslovniSelect(new TipRekvizita());
    }

    public List<TipRekvizita> getListaTipove() {
        return listaTipova;
    }
    
}
