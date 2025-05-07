<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="title" value="${author.id == null ? 'Add Author' : 'Edit Author'}" />
<%@ include file="../common/header.jsp" %>

<div class="row">
    <div class="col-md-8 offset-md-2">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4>${author.id == null ? 'Add New Author' : 'Edit Author'}</h4>
            </div>
            <div class="card-body">
                <form:form action="${author.id == null ? '/authors' : '/authors/'.concat(author.id)}" method="post" modelAttribute="author">
                    <c:if test="${author.id != null}">
                        <form:hidden path="id" />
                    </c:if>
                    
                    <div class="mb-3">
                        <label for="name" class="form-label">Name</label>
                        <form:input path="name" id="name" class="form-control" required="true" />
                        <form:errors path="name" cssClass="text-danger" />
                    </div>
                    
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <form:input path="email" id="email" class="form-control" type="email" />
                        <form:errors path="email" cssClass="text-danger" />
                    </div>
                    
                    <div class="mb-3">
                        <label for="biography" class="form-label">Biography</label>
                        <form:textarea path="biography" id="biography" class="form-control" rows="4" />
                        <form:errors path="biography" cssClass="text-danger" />
                    </div>
                    
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="birthYear" class="form-label">Birth Year</label>
                            <form:input path="birthYear" id="birthYear" class="form-control" type="number" />
                            <form:errors path="birthYear" cssClass="text-danger" />
                        </div>
                        
                        <div class="col-md-6 mb-3">
                            <label for="country" class="form-label">Country</label>
                            <form:input path="country" id="country" class="form-control" />
                            <form:errors path="country" cssClass="text-danger" />
                        </div>
                    </div>
                    
                    <div class="d-flex justify-content-between">
                        <a href="<c:url value='/authors' />" class="btn btn-secondary">Cancel</a>
                        <button type="submit" class="btn btn-primary">${author.id == null ? 'Create Author' : 'Update Author'}</button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %> 