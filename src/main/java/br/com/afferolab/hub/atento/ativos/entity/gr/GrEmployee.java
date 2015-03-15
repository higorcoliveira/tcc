package br.com.afferolab.hub.atento.ativos.entity.gr;

public class GrEmployee {

	private Integer id;
	private String re;
	private String name;
	private String email;
	private String password;
	private Boolean active;
	private String cpf;
	private String contract;
	private String phoneNumber;
	private Long costCenter;
	private String roleGroup;
	private Integer changePassword;
	private Integer idPublicPool;
	private Integer idRoleReadOnly;
	private Integer idEmployee;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(Long costCenter) {
		this.costCenter = costCenter;
	}

	public String getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}
	
	public Integer getChangePassword() {
		return changePassword;
	}

	public void setChangePassword(Integer changePassword) {
		this.changePassword = changePassword;
	}

	public Integer getIdPublicPool() {
		return idPublicPool;
	}

	public void setIdPublicPool(Integer idPublicPool) {
		this.idPublicPool = idPublicPool;
	}

	public Integer getIdRoleReadOnly() {
		return idRoleReadOnly;
	}

	public void setIdRoleReadOnly(Integer idRoleReadOnly) {
		this.idRoleReadOnly = idRoleReadOnly;
	}

	public Integer getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}
}
