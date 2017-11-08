<!DOCTYPE html>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en">
<head>
  <title>Twitter</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
  </style>
</head>
<body>

  <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
      <div class="container">
        <a class="navbar-brand" href="message">Twitter</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item">
              <a class="nav-link" href="#">Sign up</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Login</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>

 <div id="tweets" class="list-group">
</div>
<div class="form-group">
  <label for="usr">Tweet:</label>
  <input type="text" class="form-control" onkeypress='runScript(event, this)' id="usr">
</div>  
<footer class="container-fluid text-center">
  <h3 id="nbMessages">Nombre de Messages : <c:out value="${ nbMessages }" /></h3>
</footer>
<script>
	$("#usr").focus();
	loadMessage();
	
	function loadMessage(){
		jQuery.ajax({
			type: 'GET',
		    url: 'http://localhost:9999/api',
		    data: {
		    	action : 'list'
		    },
		    success: function (result) {
		        console.log("L'appel Ajax est une réussite.");
		        console.log(result);
		        $.each(result, function(index, element) {
		        	console.log(element)
		        	addMessage(element.contenu, element.date, element.id, element.nbMessage, element.auteur, element.like);
		        });
		        addLikeurs();
		    },
		    error: function () {
		        console.log("L'appel Ajax est un échec.");
		        alert("error loadMessage");
		    }
		});
	}
	
	function onSuppClick (id) {
		jQuery.ajax({
			type: 'GET',
		    url: 'http://localhost:9999/api',
		    data: {
		    	action : 'remove',
		    	messageId : id
		    },
		    success: function (result) {
		        console.log("L'appel Ajax est une réussite.");
		        console.log(result);
		        $.each(result, function(index, element) {
		        	suppMessage(element.id, element.nbMessage);
		        });
		    },
		    error: function () {
		        console.log("L'appel Ajax est un échec.");
		        alert("error");
		    }
		});
	}
	
	function onTest (param){
		alert(param);
	}
	
	function runScript(event, input) {
		if(event.keyCode == 13){
			jQuery.ajax({
				type: 'GET',
			    url: 'http://localhost:9999/api',
			    data: {
			    	action : 'add',
			    	contenu : input.value
			    },
			    success: function (result) {
			        console.log("L'appel Ajax est une réussite.");
			        console.log(result);
			        $.each(result, function(index, element) {
			        	addMessage(element.contenu, element.date, element.id, element.nbMessage, element.auteur, element.like);
			        });
			        input.value = "";
			    },
			    error: function () {
			        console.log("L'appel Ajax est un échec.");
			        alert("error");
			    }
			});
		}
	}
	function onLikeClick(messageId){
		jQuery.ajax({
			type: 'GET',
		    url: 'http://localhost:9999/api',
		    data: {
		    	action : 'like',
		    	messageId : messageId
		    },
		    success: function (result) {
		        console.log("L'appel Ajax est une réussite.");
		        console.log(result);
		        loadMessage();
		        $.each(result, function(index, element) {
		        	likeMessage(element.id,element.like);
		        });
		    },
		    error: function (status) {
		        console.log("L'appel Ajax est un échec.");
		        alert("error onLikeClick");
		    }
		});
		/* reload(); */
	}
	
	function addMessage(contenu, date, ident, nbMessage, auteur, like){
		$("#tweets #"+ident).remove();
		console.log("add : "+ident);
		$("#tweets").append("<div id='" + ident + "' class='list-group-item'> <h3 class='list-group-item-heading'><i>" + auteur + " : </i></h3> <h4 class='list-group-item-text'>" + contenu + "</h4> <p id='footer' class='list-group-item-footer'>"+date+" </p> <button id='like' type='button' onclick='onLikeClick( \"" + ident + "\" )' class='btn btn-success'> Like </button> <button type='button' onclick='onSuppClick( \"" + ident + "\" )' class='btn btn-danger'>Supprimer</button> <p id='like-footer' class='list-group-item-footer'> like it ! </p> </div>");
		likeMessage(ident, like);
		$("#nbMessages").text("Nombre de Messages : " + nbMessage);
		console.log(nbMessage);
	}
	
	function addLikeurs(){
		jQuery.ajax({
			type: 'GET',
		    url: 'http://localhost:9999/api',
		    data: {
		    	action : 'likeurs'
		    },
		    success: function (result) {
		        console.log("L'appel Ajax est une réussite.");
		        console.log("lik "+result);
		        $.each(result, function(index, element) {
		        	console.log("like " +result);
		        	$("#tweets #"+element.id+" #like-footer").prepend(element.likeur+" ");
		        });
		    },
		    error: function (result) {
		        console.log("L'appel Ajax est un échec."+result);
		        alert("error addLikeurs");
		    }
		});
	}
	
	function likeMessage(id, like){
		if(like){
			$("#tweets #"+id+" #like").removeClass();
			$("#tweets #"+id+" #like").text("Unlike");
			$("#tweets #"+id+" #like").addClass("btn btn-info");
			/* $("#tweets #"+id+" #like-footer").prepend(user); */
		}
		else {
			$("#tweets #"+id+" #like").removeClass();
			$("#tweets #"+id+" #like").text("Like");
			$("#tweets #"+id+" #like").addClass("btn btn-success");
		}
	}
	
	function suppMessage(id, nbMessage){
		console.log("supp : "+id)
		$("#tweets #"+id).remove();
		$("#nbMessages").text("Nombre de Messages : " + nbMessage);
	}
</script>
</body>
</html>