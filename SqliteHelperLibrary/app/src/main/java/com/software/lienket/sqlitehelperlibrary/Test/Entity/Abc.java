package com.software.lienket.sqlitehelperlibrary.Test.Entity;

import android.content.Intent;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Column;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Entity;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Id;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Annotation.Table;

/**
 * Created by KIEN on 4/27/2017.
 */

@Entity
@Table(name = "abc")
public class Abc {
    @Id(AI = true)
    @Column(name = "id")
    private Integer id;
    @Column(name = "test")
    private String test;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
