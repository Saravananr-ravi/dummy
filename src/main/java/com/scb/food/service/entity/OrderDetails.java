package com.scb.food.service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
@Table(name="OrderDetails")
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @JsonIgnore
    @Id
    /*@TableGenerator(name = "orderDetailsSequence", initialValue = 4000000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "orderDetailsSequence")*/
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="order_item_id")
    private long orderItemId;
    @Column(name="vendor_name")
    private String vendorName;
    @Column(name="item_name")
    private String itemName;
    @JsonIgnore
    @Column(name="price")
    private float price;
    @Column(name="qty")
    private int qty;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="food_order_id", updatable = true, insertable = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private FoodOrder foodOrder;
}
