package com.software.lienket.sqlitehelperlibrary.Test.Entity;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.BelongsTo;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.HasMany;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Id;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Table;

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
