package com.software.lienket.sqlitehelperlibrary.Entity;

import com.software.lienket.library.annotation.BelongsTo;
import com.software.lienket.library.annotation.Column;
import com.software.lienket.library.annotation.Entity;
import com.software.lienket.library.annotation.HasMany;
import com.software.lienket.library.annotation.Id;
import com.software.lienket.library.annotation.Table;

/**
 * Created by KIEN on 4/21/2017.
 */

@Entity
@Table(name = "orders")
@HasMany(entities = {OrderDetail.class})
public class Order {
    @Id(AI = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    @BelongsTo(table = User.class)
    private Integer userId;
    @Column(name = "date")
    private String date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
