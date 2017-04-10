<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Show All Products</title>
</head>
<body>
    <table border=1>
        <thead>
            <tr>
                <th>Order Number</th>
                <th>Customer ID</th>
                <th>Product ID</th> 
                <th>Quality</th>
                <th>Shipping Cost</th>
                <th>Sales Date</th>
                <th>Shipping Date</th>
                <th colspan=2>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td><c:out value="${product.orderNum}" /></td>
                    <td><c:out value="${product.customerId}" /></td>
                    <td><c:out value="${product.productId}" /></td>
                    <td><c:out value="${product.quality}" /></td>
                    <td><c:out value="${product.shippingCost}" /></td>
                    <td><fmt:formatDate pattern="yyyy-MMM-dd" value="${product.salesDate}" /></td>
                    <td><fmt:formatDate pattern="yyyy-MMM-dd" value="${product.shippingDate}" /></td>
                    <td><a href="ProductController?action=edit&orderNum=<c:out value="${product.orderNum}"/>">Update</a></td>
                    <td><a href="ProductController?action=delete&orderNum=<c:out value="${product.orderNum}"/>">Delete</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <p><a href="ProductController?action=insert">Add Product</a></p>
</body>
</html>