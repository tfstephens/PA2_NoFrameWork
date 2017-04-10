package com.tstephs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//https://docs.oracle.com/cd/E17802_01/products/products/servlet/2.1/api/javax.servlet.http.HttpServlet.html

public class ProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/product.jsp";
    private static String LIST_PRODUCT = "/listProduct.jsp";
    private ProductDao dao;

    public ProductController() {
        super();
        dao = new ProductDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String forward="";
     
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")){
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));
            dao.deleteProduct(orderNum);
            forward = LIST_PRODUCT;
            request.setAttribute("products", dao.getAllProducts());    
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int orderNum = Integer.parseInt(request.getParameter("orderNum"));
            Product prod = dao.getProductByOrderNum(orderNum);
            request.setAttribute("product", prod);
        } else if (action.equalsIgnoreCase("listProduct")){
            forward = LIST_PRODUCT;
            request.setAttribute("products", dao.getAllProducts());
        } else {
            forward = INSERT_OR_EDIT;
        }

        //fowards it to the specific page
        RequestDispatcher view = request.getRequestDispatcher(forward);
        
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product prod = new Product();
        prod.setCustomerId(Integer.parseInt(request.getParameter("customerId")));
        prod.setProductId(Integer.parseInt(request.getParameter("productId")));
        prod.setQuality(Integer.parseInt(request.getParameter("quality")));
        prod.setShippingCost(Integer.parseInt(request.getParameter("shippingCost")));
        
        try {
            Date salesDate = new SimpleDateFormat("mm-dd-yyyy").parse(request.getParameter("salesDate"));
            prod.setSalesDate(salesDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
         try {
            Date shippingDate = new SimpleDateFormat("mm-dd-yyyy").parse(request.getParameter("shippingDate"));
            prod.setShippingDate(shippingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String orderNum = request.getParameter("orderNum");
        if(orderNum != null)
        {
             prod.setOrderNum(Integer.parseInt(orderNum));
            dao.addProduct(prod);
        }
        else
        {
            prod.setOrderNum(Integer.parseInt(orderNum));
            dao.updateProduct(prod);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_PRODUCT);
        request.setAttribute("products", dao.getAllProducts());
        view.forward(request, response);
    }
}