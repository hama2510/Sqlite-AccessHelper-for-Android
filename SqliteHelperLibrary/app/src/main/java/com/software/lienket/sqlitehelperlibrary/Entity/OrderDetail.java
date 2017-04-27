package com.software.lienket.sqlitehelperlibrary.Entity;

import com.software.lienket.library.annotation.BelongsTo;
import com.software.lienket.library.annotation.Column;
import com.software.lienket.library.annotation.Entity;
import com.software.lienket.library.annotation.Id;
import com.software.lienket.library.annotation.Table;

/**
 * Created by KIEN on 4/21/2017.
 */

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @Column(name = "product_id")
    @BelongsTo(table = Product.class)
    private Integer productId;

    @Id
    @Column(name = "order_id")
    @BelongsTo(table = Order.class)
    private Integer orderId;

    @Column(name = "quantity")
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
