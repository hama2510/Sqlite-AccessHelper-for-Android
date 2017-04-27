package com.software.lienket.sqlitehelperlibrary.Entity;

import com.software.lienket.library.annotation.Column;
import com.software.lienket.library.annotation.Entity;
import com.software.lienket.library.annotation.HasMany;
import com.software.lienket.library.annotation.Id;
import com.software.lienket.library.annotation.Table;

/**
 * Created by KIEN on 4/21/2017.
 */

@Entity
@Table(name = "products")
@HasMany(entities = {OrderDetail.class})
public class Product {
    @Id(AI = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "price")
    private Integer price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
