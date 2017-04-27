package com.software.lienket.sqlitehelperlibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.software.lienket.library.DataAccessObject;
import com.software.lienket.library.DatabaseConnection;
import com.software.lienket.library.Query;
import com.software.lienket.sqlitehelperlibrary.Entity.Abc;
import com.software.lienket.sqlitehelperlibrary.Entity.Order;
import com.software.lienket.sqlitehelperlibrary.Entity.OrderDetail;
import com.software.lienket.sqlitehelperlibrary.Entity.Product;
import com.software.lienket.sqlitehelperlibrary.Entity.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseConnection.init(this);
        DatabaseConnection.setupDatabase("as", 2, "com.software.lienket.sqlitehelperlibrary.Entity");
        DataAccessObject dao = DataAccessObject.getInstance();
        Product p = new Product();
        p.setPrice(1000);
        p.setProductName("abc");
        dao.insert(p);
        User user = new User();
        user.setPassword("asdas2d1");
        user.setUsername("Asdasd12");
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
        Query query = dao.createQuery(Abc.class);
        Abc abc = new Abc();
        abc.setTest("ádad");
        dao.insert(abc);
//        Log.e("asdas", ((Product) query.findById(p.getId())).getProductName());
        Log.e("asdas", ((Abc) query.findById(abc.getId())).getTest());
    }
}
