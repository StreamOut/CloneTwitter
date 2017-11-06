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
<div class="container">
  <h2>Login</h2>
    <div class="form-group">
      <label for="email">Email:</label>
      <input type="email" class="form-control" id="email" placeholder="Enter email" name="email">
    </div>
    <div class="form-group">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
    </div>
    <button type="button" class="btn btn-success" onclick='signIn(email.value, pwd.value)'>Let me in !</button>
    <button type="button" class="btn btn-default" onclick='registration()'>Registration</button>
</div>


<script>

	function signIn (mail, pwd){
		console.log("email : "+mail+" pass : "+pwd);
		jQuery.ajax({
			type: 'GET',
		    url: 'http://localhost:9999/api/login',
		    data: {
		    	action : 'login',
		    	mail : mail,
		    	pass : pwd
		    },
		    success: function (result) {
		        console.log("L'appel Ajax est une réussite.");
		        console.log(result);
		        /* $.each(result, function(index, element) {
		        	window.location.assign(element.url);
		        }); */
		        jQuery.ajax({
					type: 'GET',
				    url: 'http://localhost:9999/message',
				    success: function (result) {
				        console.log("L'appel Ajax est une réussite.");
				        console.log(result);
				    },
				    error: function () {
				        console.log("L'appel Ajax est un échec.");
				        alert("error");
				    }
				});
		    },
		    error: function () {
		        console.log("L'appel Ajax est un échec.");
		        alert("error");
		    }
		});
	}
	
	function registration (){
		jQuery.ajax({
			type: 'GET',
		    url: 'http://localhost:9999/message',
		    data: {
		    	registration : 'true'
		    },
		    success: function (result) {
		        console.log("L'appel Ajax est une réussite.");
		        /* console.log(result);*/
		        /* window.location.assign(result.url); */
		       jQuery.ajax({
					type: 'GET',
				    url: 'http://localhost:9999/message',
				    success: function (result) {
				        console.log("L'appel Ajax est une réussite.");
				        console.log(result);
				    },
				    error: function () {
				        console.log("L'appel Ajax est un échec.");
				        alert("error");
				    }
				});
		    },
		    error: function () {
		        console.log("L'appel Ajax est un échec.");
		        alert("error");
		    }
		});
	}
	
</script>
</body>
</html>