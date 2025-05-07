<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="title" value="Book Details" />
<%@ include file="../common/header.jsp" %>

<div class="row mb-3">
    <div class="col-md-8">
        <h2>Book Details</h2>
    </div>
    <div class="col-md-4 text-end">
        <a href="<c:url value='/books' />" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Back to Books
        </a>
    </div>
</div>

<div class="row">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-info text-white">
                <h4>${book.title}</h4>
            </div>
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Author:</div>
                    <div class="col-md-9">
                        <a href="<c:url value='/authors/${book.authorId}' />">${book.authorName}</a>
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">ISBN:</div>
                    <div class="col-md-9">${book.isbn}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Genre:</div>
                    <div class="col-md-9">${book.genre}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Publication Date:</div>
                    <div class="col-md-9">
                        <fmt:formatDate value="${book.publicationDate}" pattern="MMMM d, yyyy" />
                    </div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Price:</div>
                    <div class="col-md-9">$<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Page Count:</div>
                    <div class="col-md-9">${book.pageCount}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Description:</div>
                    <div class="col-md-9">${book.description}</div>
                </div>
                
                <div class="mt-4">
                    <a href="<c:url value='/books/${book.id}/edit' />" class="btn btn-warning">
                        <i class="bi bi-pencil"></i> Edit
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %> 