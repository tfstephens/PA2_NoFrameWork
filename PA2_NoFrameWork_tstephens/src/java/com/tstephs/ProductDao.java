package com.tstephs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private Connection connection;

    public ProductDao() {
        connection = DbConnectionUtil.getConnection();
    }

    public void addProduct(Product prod) {
        List<Product> products = getAllProducts();
        for (int i = 0; i < products.size(); i++) {
            if (prod.getOrderNum() == products.get(i).getOrderNum()) {
                deleteProduct(prod.getOrderNum());
            }
        }

        try {
            PreparedStatement st = connection.prepareStatement("INSERT INTO PRODUCT(ORDER_NUM, CUSTOMER_ID, PRODUCT_ID, QUALITY, SHIPPING_COST,SALES_DATE,SHIPPING_DATE) VALUES(?,?,?,?,?,?,?)");

            st.setInt(1, prod.getOrderNum());
            st.setInt(2, prod.getCustomerId());
            st.setInt(3, prod.getProductId());
            st.setInt(4, prod.getQuality());
            st.setInt(5, prod.getShippingCost());
            st.setDate(6, new java.sql.Date(prod.getSalesDate().getTime()));
            st.setDate(7, new java.sql.Date(prod.getShippingDate().getTime()));
            st.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int orderNum) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from product where ORDER_NUM = " + orderNum);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateProduct(Product prod) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update product set customer_id=?, product_id=?, quality=?, shipping_cost=?, sales_date=?, shipping_date=?"
                            + "where order_num=?");

            preparedStatement.setInt(1, prod.getCustomerId());
            preparedStatement.setInt(2, prod.getProductId());
            preparedStatement.setInt(3, prod.getQuality());
            preparedStatement.setInt(4, prod.getShippingCost());
            preparedStatement.setDate(5, new java.sql.Date(prod.getSalesDate().getTime()));
            preparedStatement.setDate(6, new java.sql.Date(prod.getShippingDate().getTime()));
            preparedStatement.setInt(7, prod.getOrderNum());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT");
            while (rs.next()) {
                Product prod = new Product();
                prod.setOrderNum(rs.getInt("ORDER_NUM"));
                prod.setCustomerId(rs.getInt("CUSTOMER_ID"));
                prod.setProductId(rs.getInt("PRODUCT_ID"));
                prod.setQuality(rs.getInt("QUALITY"));
                prod.setShippingCost(rs.getInt("SHIPPING_COST"));
                prod.setSalesDate(rs.getDate("SALES_DATE"));
                prod.setShippingDate(rs.getDate("SHIPPING_DATE"));
                products.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductByOrderNum(int orderNum) {
        Product prod = new Product();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("SELECT * FROM PRODUCT WHERE ORDER_NUM = ?");
            preparedStatement.setInt(1, orderNum);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                prod.setOrderNum(rs.getInt("ORDER_NUM"));
                prod.setCustomerId(rs.getInt("CUSTOMER_ID"));
                prod.setProductId(rs.getInt("PRODUCT_ID"));
                prod.setQuality(rs.getInt("QUALITY"));
                prod.setShippingCost(rs.getInt("SHIPPING_COST"));
                prod.setSalesDate(rs.getDate("SALES_DATE"));
                prod.setShippingDate(rs.getDate("SHIPPING_DATE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prod;
    }
}
