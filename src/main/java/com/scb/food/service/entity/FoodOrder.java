package com.scb.food.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name="food_order")
public class FoodOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Id
/*    @TableGenerator(name = "orderSequence", initialValue = 5000000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orderSequence")*/
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @Column(name="from_acc_no")
    private long fromAccNo;
    @JsonIgnore
    @Column(name="total_amount")
    private float totalAmount;
    @JsonIgnore
    @Column(name="to_account_no")
    private long toAccNo;
    @Column(name="user_id")
    private long userId;
    @JsonIgnore
    @Column(name="isPaymentSuccess")
    private String isPaymentSuccess;
    @JsonIgnore
    @Column(name="order_date_time")
    private LocalDateTime orderDateTime;
    @OneToMany(cascade=CascadeType.ALL,mappedBy = "foodOrder")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<OrderDetails> orderDetailsSet;



    //mappedBy="FoodOrder",
}
