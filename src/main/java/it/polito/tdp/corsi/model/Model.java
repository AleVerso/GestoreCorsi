package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {

	private CorsoDAO corsoDao;

	public Model() {

		this.corsoDao = new CorsoDAO();
	}

	public List<Corso> getCorsiByPeriodo(Integer pd) {
		return corsoDao.getCorsiByPeriodo(pd);
	}

	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd) {
		return corsoDao.getIscrittiByPeriodo(pd);
	}

	public List<Studente> getStudentiByCorso(String codins) {
		return corsoDao.getStudentebyCorso(new Corso(codins, null, null, null));
	}
	
	public Map<String, Integer> getDivisioneCDS(String codins) {

		Map<String, Integer> divisione = new HashMap<>();
		List<Studente> studenti = this.getStudentiByCorso(codins);

		for (Studente s : studenti) {
			if ((!s.getCDS().equals("")) && (s.getCDS()!=null)) {
				if (divisione.get(s.getCDS()) == null) {
					divisione.put(s.getCDS(), 1);
				} else
					divisione.put(s.getCDS(), divisione.get(s.getCDS()) + 1);
			}
		}

		return divisione;

	}
	

	public boolean esisteCorso(String codins) {
		return corsoDao.esisteCorso(new Corso(codins, null, null, null));
	}
}


