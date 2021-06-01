package com.example.ecommerce.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "id_number")
	private String idNumber;

	@JoinColumn(name = "user_type", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private UserType userType;

	@Column(name = "join_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private UserAddress address;

	@OneToMany(mappedBy ="userReference" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Bill> bills;

	public User(){}

	public Long getId() {
		return id;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public UserType getUserType() {
		return userType;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public User getUser(String idNumber){
		User user = this;
		return user.getIdNumber().equals(idNumber) ? user : null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public void setAddress(UserAddress address) {
		this.address = address;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
}

