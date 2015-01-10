package de.party.party.domain;

import java.io.Serializable;
import java.util.List;

import de.party.nutzer.domain.Nutzer;

public class FreundeHolder implements Serializable  {

	

	private static final long serialVersionUID = -9058421534040245838L;
	
	private List<Nutzer> zugesagt;
    private List<Nutzer> abgesagt;
    private List<Nutzer> eingeladen;

    
    public FreundeHolder() {
		super();
	}


	public List<Nutzer> getZugesagt() {
		return zugesagt;
	}


	public void setZugesagt(List<Nutzer> zugesagt) {
		this.zugesagt = zugesagt;
	}


	public List<Nutzer> getAbgesagt() {
		return abgesagt;
	}


	public void setAbgesagt(List<Nutzer> abgesagt) {
		this.abgesagt = abgesagt;
	}


	public List<Nutzer> getEingeladen() {
		return eingeladen;
	}


	public void setEingeladen(List<Nutzer> eingeladen) {
		this.eingeladen = eingeladen;
	}


	@Override
	public String toString() {
		return "FreundeHolder [zugesagt=" + zugesagt + ", abgesagt=" + abgesagt
				+ ", eingeladen=" + eingeladen + "]";
	}
    
    
    
	
    
    
   

}
