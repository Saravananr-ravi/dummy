package com.scb.food.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name="food")
@DynamicUpdate
public class FoodDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @TableGenerator(name = "foodSequence", initialValue = 300000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "foodSequence")
    @Column(name="item_no")
    private long itemNo;
    @Column(name="item_name")
    private String itemName;
    @Column(name="qty")
    private int qty;
    @Column(name="price")
    private float price;

    @Column(name="vendor_name")
    private String vendorName;
    /*@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name="vendor_id",nullable = false)
    private VendorDetails vendor;
*/}
