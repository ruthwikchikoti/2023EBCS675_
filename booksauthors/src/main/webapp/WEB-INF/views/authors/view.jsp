<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="title" value="Author Details" />
<%@ include file="../common/header.jsp" %>

<div class="row mb-3">
    <div class="col-md-8">
        <h2>Author Details</h2>
    </div>
    <div class="col-md-4 text-end">
        <a href="<c:url value='/authors' />" class="btn btn-secondary">
            <i class="bi bi-arrow-left"></i> Back to Authors
        </a>
    </div>
</div>

<div class="row">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-info text-white">
                <h4>${author.name}</h4>
            </div>
            <div class="card-body">
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Email:</div>
                    <div class="col-md-9">${author.email}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Country:</div>
                    <div class="col-md-9">${author.country}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Birth Year:</div>
                    <div class="col-md-9">${author.birthYear}</div>
                </div>
                
                <div class="row mb-3">
                    <div class="col-md-3 fw-bold">Biography:</div>
                    <div class="col-md-9">${author.biography}</div>
                </div>
                
                <div class="mt-4 d-flex gap-2">
                    <a href="<c:url value='/authors/${author.id}/edit' />" class="btn btn-warning">
                        <i class="bi bi-pencil"></i> Edit
                    </a>
                    <a href="<c:url value='/books/by-author/${author.id}' />" class="btn btn-primary">
                        <i class="bi bi-book"></i> View Books
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %> 