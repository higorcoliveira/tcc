package br.com.tcc.integracao.entity.lms;

import java.util.Date;

import org.joda.time.DateTime;

public class LMSParticipant {

	private final static Integer STUDENT = 1;
	
	private Integer id;
	private String companyArea;
	private String companyHierarchy;
	private String re;
	private String name;
	private String email;
	private String password;
	private Boolean active;
	private String gender;
	private Date fullBirthDay;
	private String cpf;
	private String contract;
	private String reSuperior;
	private Integer maritalStatus;
	private Integer scholarity;
	private String costCenter;
	private Date admissionDate;
	private String rg;
	private String executiveBoard;
	private String consultingName;
	private Integer type;
	private Boolean manager;
	private Date creationDate;
	
	public LMSParticipant() {
		this.type = STUDENT;
		this.manager = false;
		this.creationDate = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyArea() {
		return companyArea;
	}

	public void setCompanyArea(String companyArea) {
		this.companyArea = companyArea;
	}

	public String getCompanyHierarchy() {
		return companyHierarchy;
	}

	public void setCompanyHierarchy(String companyHierarchy) {
		this.companyHierarchy = companyHierarchy;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Date getFullBirthDay() {
		return fullBirthDay;
	}

	public void setFullBirthDay(Date fullBirthDay) {
		this.fullBirthDay = fullBirthDay;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getReSuperior() {
		return reSuperior;
	}

	public void setReSuperior(String reSuperior) {
		this.reSuperior = reSuperior;
	}

	public Integer getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getScholarity() {
		return scholarity;
	}

	public void setScholarity(Integer scholarity) {
		this.scholarity = scholarity;
	}

	public String getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	public Date getAdmissionDate() {
		return admissionDate;
	}

	public void setAdmissionDate(Date admissionDate) {
		this.admissionDate = admissionDate;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getExecutiveBoard() {
		return executiveBoard;
	}

	public void setExecutiveBoard(String executiveBoard) {
		this.executiveBoard = executiveBoard;
	}

	public String getConsultingName() {
		return consultingName;
	}

	public void setConsultingName(String consultingName) {
		this.consultingName = consultingName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getManager() {
		return manager;
	}

	public void setManager(Boolean manager) {
		this.manager = manager;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getBirthDay() {
		DateTime dt = new DateTime(fullBirthDay);
		String day = String.valueOf(dt.getDayOfMonth());
		
		return day;
	}

	public String getBirthMonth() {
		DateTime dt = new DateTime(fullBirthDay);
		String month = String.valueOf(dt.getMonthOfYear());
		
		return month;
	}
	
	public String getBirthYear() {
		DateTime dt = new DateTime(fullBirthDay);
		String year = String.valueOf(dt.getYear());
		
		return year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((re == null) ? 0 : re.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LMSParticipant other = (LMSParticipant) obj;
		if (cpf == null) {
			if (other.cpf != null) {
				return false;
			}
		} else if (!cpf.equals(other.cpf)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (re == null) {
			if (other.re != null) {
				return false;
			}
		} else if (!re.equals(other.re)) {
			return false;
		}
		return true;
	}
}
