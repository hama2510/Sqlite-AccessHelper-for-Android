package com.software.lienket.sqlitehelperlibrary.Test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.software.lienket.sqlitehelperlibrary.HelperLibrary.DataAccessObject;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Query;
import com.software.lienket.sqlitehelperlibrary.Test.Entity.Order;
import com.software.lienket.sqlitehelperlibrary.Test.Entity.OrderDetail;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.DatabaseConnection;
import com.software.lienket.sqlitehelperlibrary.HelperLibrary.Utils.QueryUtil;
import com.software.lienket.sqlitehelperlibrary.R;
import com.software.lienket.sqlitehelperlibrary.Test.Entity.Product;
import com.software.lienket.sqlitehelperlibrary.Test.Entity.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseConnection.init(this);
        DatabaseConnection.setupDatabase("as", 1, "com.software.lienket.sqlitehelperlibrary.Test.Entity");
        DataAccessObject dao = DataAccessObject.getInstance();
        Product p = new Product();
        p.setPrice(1000);
        p.setProductName("abc");
        dao.insert(p);
        User user = new User();
        user.setPassword("asdasd");
        user.setUsername("Asdasd");
        dao.insert(user);
        Order order = new Order();
        order.setDate("Asdsa");
        order.setUserId(user.getId());
        dao.insert(order);
        OrderDetail detail = new OrderDetail();
        detail.setOrderId(order.getId());
        detail.setProductId(p.getId());
        detail.setQuantity(123);
        dao.insert(detail);
    }
}
