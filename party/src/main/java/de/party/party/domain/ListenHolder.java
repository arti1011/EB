package de.party.party.domain;

import java.io.Serializable;
import java.util.List;

import de.party.party.domain.Party;

public class ListenHolder implements Serializable  {

	
	private static final long serialVersionUID = 4913498726240642684L;
	
	private List<Party> meinepartys;
    private List<Party> offene;
    private List<Party> ichnehmeteil;
    private List<Party> abgesagtnochzukunft;
    private List<Party> ichhabeteilgenommen;
    private List<Party> torate;
    private List<Party> meinepartysvergangen;
    
    public ListenHolder() {
		super();
	}
    
	public List<Party> getMeinepartys() {
		return meinepartys;
	}
	public void setMeinepartys(List<Party> meinepartys) {
		this.meinepartys = meinepartys;
	}
	public List<Party> getOffene() {
		return offene;
	}
	public void setOffene(List<Party> offene) {
		this.offene = offene;
	}
	public List<Party> getIchnehmeteil() {
		return ichnehmeteil;
	}
	public void setIchnehmeteil(List<Party> ichnehmeteil) {
		this.ichnehmeteil = ichnehmeteil;
	}
	public List<Party> getAbgesagtnochzukunft() {
		return abgesagtnochzukunft;
	}
	public void setAbgesagtnochzukunft(List<Party> abgesagtnochzukunft) {
		this.abgesagtnochzukunft = abgesagtnochzukunft;
	}
	public List<Party> getIchhabeteilgenommen() {
		return ichhabeteilgenommen;
	}
	public void setIchhabeteilgenommen(List<Party> ichhabeteilgenommen) {
		this.ichhabeteilgenommen = ichhabeteilgenommen;
	}
	public List<Party> getTorate() {
		return torate;
	}
	public void setTorate(List<Party> torate) {
		this.torate = torate;
	}
	public List<Party> getMeinepartysvergangen() {
		return meinepartysvergangen;
	}
	public void setMeinepartysvergangen(List<Party> meinepartysvergangen) {
		this.meinepartysvergangen = meinepartysvergangen;
	}
	@Override
	public String toString() {
		return "ListenHolder [meinepartys=" + meinepartys + ", offene="
				+ offene + ", ichnehmeteil=" + ichnehmeteil
				+ ", abgesagtnochzukunft=" + abgesagtnochzukunft
				+ ", ichhabeteilgenommen=" + ichhabeteilgenommen + ", torate="
				+ torate + ", meinepartysvergangen=" + meinepartysvergangen
				+ "]";
	}
    
    
   

}
