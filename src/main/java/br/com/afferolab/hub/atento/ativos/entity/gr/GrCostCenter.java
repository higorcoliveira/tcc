package br.com.afferolab.hub.atento.ativos.entity.gr;

public class GrCostCenter {

	private Long idCostCenter;
	private String name;
	private Integer isInsert;
	
	public Long getIdCostCenter() {
		return idCostCenter;
	}
	
	public void setIdCostCenter(Long idCostCenter) {
		this.idCostCenter = idCostCenter;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsInsert() {
		return isInsert;
	}

	public void setIsInsert(Integer isInsert) {
		this.isInsert = isInsert;
	}
}
