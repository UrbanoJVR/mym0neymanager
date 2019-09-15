package com.urbanojvr.mym0neymanager.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String concept;

    @Column(precision = 17, scale = 2)
    @NotNull
    private double amount;

    @Column
    @Temporal(TemporalType.DATE)
    private @NotNull Date date;

    public Transaction(){

    }

    public Transaction(String concept, double amount, @NotNull Date date) {
        this.concept = concept;
        this.amount = amount;
        this.date = date;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(@NotNull Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return "Transaction{" +
                "id=" + id +
                ", concept='" + concept + '\'' +
                ", amount=" + amount +
                ", date=" + dateFormat.format(date) +
                '}';
    }
}
