<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${book.id == null ? 'Add Book' : 'Edit Book'}" />
<%@ include file="../common/header.jsp" %>

<div class="row">
    <div class="col-md-8 offset-md-2">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4>${book.id == null ? 'Add New Book' : 'Edit Book'}</h4>
            </div>
            <div class="card-body">
                <form:form action="${book.id == null ? '/books' : '/books/'.concat(book.id)}" method="post" modelAttribute="book">
                    <c:if test="${book.id != null}">
                        <form:hidden path="id" />
                    </c:if>
                    
                    <div class="mb-3">
                        <label for="title" class="form-label">Title</label>
                        <form:input path="title" id="title" class="form-control" required="true" />
                        <form:errors path="title" cssClass="text-danger" />
                    </div>
                    
                    <div class="mb-3">
                        <label for="authorId" class="form-label">Author</label>
                        <form:select path="authorId" id="authorId" class="form-select" required="true">
                            <form:option value="" label="-- Select Author --" />
                            <c:forEach var="author" items="${authors}">
                                <form:option value="${author.id}" label="${author.name}" />
                            </c:forEach>
                        </form:select>
                        <form:errors path="authorId" cssClass="text-danger" />
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="isbn" class="form-label">ISBN</label>
                            <form:input path="isbn" id="isbn" class="form-control" />
                            <form:errors path="isbn" cssClass="text-danger" />
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="genre" class="form-label">Genre</label>
                            <form:input path="genre" id="genre" class="form-control" />
                            <form:errors path="genre" cssClass="text-danger" />
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="publicationDate" class="form-label">Publication Date</label>
                            <form:input path="publicationDate" id="publicationDate" class="form-control" type="date" />
                            <form:errors path="publicationDate" cssClass="text-danger" />
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="price" class="form-label">Price</label>
                            <div class="input-group">
                                <span class="input-group-text">$</span>
                                <form:input path="price" id="price" class="form-control" type="number" step="0.01" min="0" />
                            </div>
                            <form:errors path="price" cssClass="text-danger" />
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="pageCount" class="form-label">Page Count</label>
                        <form:input path="pageCount" id="pageCount" class="form-control" type="number" min="1" required="true" />
                        <form:errors path="pageCount" cssClass="text-danger" />
                    </div>
                    
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <form:textarea path="description" id="description" class="form-control" rows="4" />
                        <form:errors path="description" cssClass="text-danger" />
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <a href="<c:url value='/books' />" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-primary">${book.id == null ? 'Create Book' : 'Update Book'}</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %> 