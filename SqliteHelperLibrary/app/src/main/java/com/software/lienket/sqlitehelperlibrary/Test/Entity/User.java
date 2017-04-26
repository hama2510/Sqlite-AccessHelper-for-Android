package com.software.lienket.sqlitehelperlibrary.Test.Entity;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.FieldType;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.HasMany;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Id;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Table;

/**
 * Created by KIEN on 4/21/2017.
 */

@Entity
@Table(name = "users")
@HasMany(entities = {Order.class})
public class User {
    @Id(AI = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username", fieldType = FieldType.UNIQUE)
    private String username;
    @Column(name = "password")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

