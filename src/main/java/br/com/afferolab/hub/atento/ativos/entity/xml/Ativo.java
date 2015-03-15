package br.com.afferolab.hub.atento.ativos.entity.xml;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "participante")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ativo {

	@XmlElement(name = "id")
	private Integer id;
	
	@XmlElement(name = "re")
	private String re;
	
	@XmlElement(name = "nome")
	private String name;
	
	@XmlElement(name = "email")
	private String email;
	
	@XmlElement(name = "dataDeInicio")
	private Date startDate;
	
	@XmlElement(name = "dataDeDesligamento")
	private Date endDate;
	
	@XmlElement(name = "dataDeNascimento")
	private Date birthday;
	
	@XmlElement(name = "senha")
	private String password;
	
	@XmlElement(name = "loginHabilitado")
	private boolean isLoginEnabled;
	
	@XmlElement(name = "senhaExpirada")
	private boolean isExpiredPassword;
	
	@XmlElement(name = "gerente")
	private boolean isManager;
	
	@XmlElement(name = "grupoDeCargo")
	private String roleGroup;
	
	@XmlElement(name = "reSuperior")
	private String reSuperior;
	
	@XmlElement(name = "ativo")
	private boolean isActive;
	
	@XmlElement(name = "situacaoFuncional")
	private int functionalSituation;
	
	@XmlElement(name = "cpf")
	private String cpf;
	
	@XmlElement(name = "sexo")
	private String gender;
	
	@XmlElement(name = "centroDeCusto")
	private Integer costCenter;
	
	@XmlElement(name = "estadoCivil")
	private int civilState;
	
	@XmlElement(name = "escolaridade")
	private Integer scholarity;
	
	@XmlElement(name = "diretoriaexecutiva")
	private String executiveDirectory;
	
	@XmlElement(name = "numTelefone")
	private String phone;
	
	@XmlElement(name = "cargo")
	private String role;
	
	@XmlElement(name = "contrato")
	private String contract;
	
	@XmlElement(name = "areaFuncao")
	private String functionArea;
	
	@XmlElement(name = "site")
	private String site;
	
	@XmlElement(name = "nomeconsultoria")
	private String consultancyName;
	
	@XmlElement(name = "rg")
	private String rg;
	
	@XmlElement(name = "emailcorporativo")
	private String corporativeEmail;
	
	private String function;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRe() {
		return re;
	}

	public void setRe(String re) {
		this.re = re;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoginEnabled() {
		return isLoginEnabled;
	}

	public void setLoginEnabled(boolean isLoginEnabled) {
		this.isLoginEnabled = isLoginEnabled;
	}

	public boolean isExpiredPassword() {
		return isExpiredPassword;
	}

	public void setExpiredPassword(boolean isExpiredPassword) {
		this.isExpiredPassword = isExpiredPassword;
	}

	public boolean isManager() {
		return isManager;
	}

	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}

	public String getReSuperior() {
		return reSuperior;
	}

	public void setReSuperior(String reSuperior) {
		this.reSuperior = reSuperior;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getFunctionalSituation() {
		return functionalSituation;
	}

	public void setFunctionalSituation(int functionalSituation) {
		this.functionalSituation = functionalSituation;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(Integer costCenter) {
		this.costCenter = costCenter;
	}

	public int getCivilState() {
		return civilState;
	}

	public void setCivilState(int civilState) {
		this.civilState = civilState;
	}

	public Integer getScholarity() {
		return scholarity;
	}

	public void setScholarity(Integer scholarity) {
		this.scholarity = scholarity;
	}

	public String getExecutiveDirectory() {
		return executiveDirectory;
	}

	public void setExecutiveDirectory(String executiveDirectory) {
		this.executiveDirectory = executiveDirectory;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getFunctionArea() {
		return functionArea;
	}

	public void setFunctionArea(String functionArea) {
		this.functionArea = functionArea;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getConsultancyName() {
		return consultancyName;
	}

	public void setConsultancyName(String consultancyName) {
		this.consultancyName = consultancyName;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getCorporativeEmail() {
		return corporativeEmail;
	}

	public void setCorporativeEmail(String corporativeEmail) {
		this.corporativeEmail = corporativeEmail;
	}
	
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((re == null) ? 0 : re.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ativo other = (Ativo) obj;
		if (re == null) {
			if (other.re != null)
				return false;
		} else if (!re.equals(other.re))
			return false;
		return true;
	}
}
