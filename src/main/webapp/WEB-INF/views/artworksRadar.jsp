<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Waiting+for+the+Sunrise' rel='stylesheet' type='text/css'>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/neo4art.css">
	    
	    <script src="http://www.mapquestapi.com/sdk/js/v7.0.s/mqa.toolkit.js?key=Fmjtd%7Cluur2lu8nd%2Cbn%3Do5-901wlf"></script>
	    
	    <script src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.js"></script>
	    <script src="${pageContext.request.contextPath}/resources/js/artworksRadar.js"></script>
	    
	    <script language="javascript">
	    	initArtworksRadar("${pageContext.request.contextPath}");
	    </script>
	  </head>
  <body>
  	<div id="artworksRadar" class="artworksRadar">
   		<div style="width: 100%; text-align: center;">
   			<span style="font-family: Waiting for the Sunrise; font-weight: 300; font-size: 30px; color: rgb(30, 151, 255);">
   				NEO4ART: modelling Van Gogh's Journey in a Neo4j Graph Database
   			</span>
   		</div>
    	<div id="artworksRadarForm" class="artworksRadarForm">
    		<br/>
    		<div style="width: 45%; text-align: left; float: left;">
	    		<strong>Your location:</strong>
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<small>latitude</small>: <span id="currentLatitude" style="font-weight: 400; color: #33a000;"></span>,
	    		<small>longitude</small>: <span id="currentLongitude" style="font-weight: 400; color: #33a000;"></span>
	    		<br/> 
	    		<br/>
	    		<strong>Travel to:</strong>
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<small>latitude</small>: <input type="text" id="travelToLatitude" name="latitude" value="">,
	    		<small>longitude</small>: <input type="text" id="travelToLongitude" name="longitude" value="">
	    		<br/> 
	    		<br/> 
	    		<strong>And find musuems hosting Van Gogh's artworks within distance:</strong>
	    		<p/>
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<small>Km</small>: <input type="text" id="travelToWithinDistance" name="distanceInKm" value="">
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" name="search" value="search" onclick="javascript:findMuseumsWithinDistance('${pageContext.request.contextPath}');">
	    	</div>    		
    		<div style="width: 40%; text-align: left; float: left;">
	    		<strong>You can try to find museums hosting Van Gogh's artworks near:</strong>
	    		<ul>
	    			<li><a href="javascript:travelTo('${pageContext.request.contextPath}', 'neo4j-sanmateo')" style="color: rgb(30, 151, 255);">Neo4j San Mateo, CA</a></li> 
	    			<li><a href="javascript:travelTo('${pageContext.request.contextPath}', 'neo4j-london'  )" style="color: rgb(30, 151, 255);">Neo4j London, UK</a></li>
	    			<li><a href="javascript:travelTo('${pageContext.request.contextPath}', 'neo4j-malmo'   )" style="color: rgb(30, 151, 255);">Neo4j Malm&ouml;, SW</a></li>
	    			<li><a href="javascript:travelTo('${pageContext.request.contextPath}', 'neo4j-desden'  )" style="color: rgb(30, 151, 255);">Neo4j Desden, DE</a></li>
	    			<li><a href="javascript:travelTo('${pageContext.request.contextPath}', 'neo4j-venice'  )" style="color: rgb(30, 151, 255);">"Neo4j" Venice, IT (indeed only Lorenzo's Home)</a></li>
	    		</ul>
	    	</div>
    		<div style="width: 15%; text-align: left; float: right;">
    			<img width="150px" src="${pageContext.request.contextPath}/resources/images/van-gogh.png">
	    	</div>
    	</div>
    	<div id="artworksRadarMap" class="artworksRadarMap"></div>
    	<div id="artworksRadarList" class="artworksRadarList"></div>
  	</div>
  	<div id="artworkThumbnail" style="position:absolute; top: 70px; left:25%; right: 25%; width: 600px; height: 600px; padding: 20px 20px 20px 2px; display: none; border: 1px solid rgb(30, 151, 255); background-color: white; z-index: 100;"></div>
  </body>
</html>
