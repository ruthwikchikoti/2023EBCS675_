<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="Books" />
<%@ include file="../common/header.jsp" %>

<div class="row">
    <div class="col-md-8">
        <h2>Books</h2>
    </div>
    <div class="col-md-4 text-end">
        <a href="<c:url value='/books/new' />" class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> Add New Book
        </a>
    </div>
</div>

<hr>

<div class="row">
    <c:choose>
        <c:when test="${not empty books}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Genre</th>
                            <th>ISBN</th>
                            <th>Price</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.id}</td>
                                <td>${book.title}</td>
                                <td>${book.authorName}</td>
                                <td>${book.genre}</td>
                                <td>${book.isbn}</td>
                                <td>$<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="<c:url value='/books/${book.id}' />" class="btn btn-sm btn-info">View</a>
                                        <a href="<c:url value='/books/${book.id}/edit' />" class="btn btn-sm btn-warning">Edit</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:when>
        <c:otherwise>
            <div class="col-md-12">
                <div class="alert alert-info">
                    No books found. <a href="<c:url value='/books/new' />">Add a new book</a>.
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../common/footer.jsp" %> 