package br.com.tcc.integracao.entity.xml;

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "participantes")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ativos {

	@XmlElement(name = "participante", type = Ativo.class)
	private Set<Ativo> ativos = new HashSet<>();

	public Ativos() {
	}

	public Set<Ativo> getAtivos() {
		return ativos;
	}

	public void setAtivos(Set<Ativo> ativos) {
		this.ativos = ativos;
	}
	
	public void add(Ativo ativo) {
		this.ativos.add(ativo);
	}
}
