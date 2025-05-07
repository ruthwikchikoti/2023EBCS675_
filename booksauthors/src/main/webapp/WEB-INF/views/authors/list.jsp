<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Authors" />
<%@ include file="../common/header.jsp" %>

<div class="row">
    <div class="col-md-8">
        <h2>Authors</h2>
    </div>
    <div class="col-md-4 text-end">
        <a href="<c:url value='/authors/new' />" class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> Add New Author
        </a>
    </div>
</div>

<hr>

<div class="row">
    <c:choose>
        <c:when test="${not empty authors}">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Country</th>
                            <th>Birth Year</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="author" items="${authors}">
                            <tr>
                                <td>${author.id}</td>
                                <td>${author.name}</td>
                                <td>${author.email}</td>
                                <td>${author.country}</td>
                                <td>${author.birthYear}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="<c:url value='/authors/${author.id}' />" class="btn btn-sm btn-info">View</a>
                                        <a href="<c:url value='/authors/${author.id}/edit' />" class="btn btn-sm btn-warning">Edit</a>
                                        <a href="<c:url value='/books/by-author/${author.id}' />" class="btn btn-sm btn-primary">Books</a>
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
                    No authors found. <a href="<c:url value='/authors/new' />">Add a new author</a>.
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../common/footer.jsp" %> 