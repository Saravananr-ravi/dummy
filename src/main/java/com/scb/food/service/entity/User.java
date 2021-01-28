package com.scb.food.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="user")
public class User implements Serializable  {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Id
    @TableGenerator(name = "userSequence", initialValue = 2000000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "userSequence")
    @Column(name="user_id")
    private long userId;
    @Column(name="user_name")
    private String name;
    @Column(name="bank_account_no")
    private String bankAccNo;
    @Column(name="phone_number")
    private String phoneNumber;
}
