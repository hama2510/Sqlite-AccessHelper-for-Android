package com.software.lienket.sqlitehelperlibrary.Test.Entity;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.BelongsTo;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Id;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Table;

/**
 * Created by KIEN on 4/21/2017.
 */

@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @Column(name = "product_id")
    @BelongsTo(table = "products")
    private Integer productId;

    @Id
    @Column(name = "order_id")
    @BelongsTo(table = "orders")
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
