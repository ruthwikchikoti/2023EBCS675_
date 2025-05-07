<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="Home" />
<%@ include file="common/header.jsp" %>

<div class="jumbotron bg-light p-5 rounded">
    <h1 class="display-4">Welcome to the Bookstore</h1>
    <p class="lead">Explore our collection of books and authors.</p>
    <hr class="my-4">
    <p>Browse through our extensive catalog or search for your favorite books and authors.</p>
    <div class="d-flex gap-2">
        <a class="btn btn-primary btn-lg" href="<c:url value='/books' />" role="button">Browse Books</a>
        <a class="btn btn-secondary btn-lg" href="<c:url value='/authors' />" role="button">View Authors</a>
    </div>
</div>

<div class="row mt-5">
    <div class="col-md-12">
        <h2>Recent Books</h2>
        <hr>
    </div>
</div>

<div class="row">
    <c:forEach var="book" items="${recentBooks}" varStatus="status">
        <c:if test="${status.index < 6}">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">by ${book.authorName}</h6>
                        <p class="card-text">
                            <c:choose>
                                <c:when test="${book.description.length() > 100}">
                                    ${book.description.substring(0, 100)}...
                                </c:when>
                                <c:otherwise>
                                    ${book.description}
                                </c:otherwise>
                            </c:choose>
                        </p>
                        <p><strong>Genre:</strong> ${book.genre}</p>
                        <p><strong>Price:</strong> $<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></p>
                        <a href="<c:url value='/books/${book.id}' />" class="btn btn-primary">View Details</a>
                    </div>
                </div>
            </div>
        </c:if>
    </c:forEach>
</div>

<%@ include file="common/footer.jsp" %> 