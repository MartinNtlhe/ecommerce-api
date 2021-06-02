package com.example.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.ecommerce.util.DateUtil.*;
import static java.util.Calendar.YEAR;

@Entity()
@Table(name = "bill_details")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="reference")
    private String reference;

    @OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.MERGE )
    @JoinColumn(name = "bill")
    private List<BillItem> billItems;

    @Column(name="bill_type")
    private String billType;

    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User userReference;

    @Column(name = "created_at")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Bill() {}

    public List<BillItem> getBillDetailList() {
        return billItems;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public void setUserReference(User userReference) {
        this.userReference = userReference;
    }

    public User getUserReference() {
        return userReference;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBillType() {
        return billType;
    }
}
