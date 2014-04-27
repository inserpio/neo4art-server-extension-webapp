/**
 * 
 * @param context
 */
function initArtworksRadar(context)
{
	loadMapByUserGeoLocation(context);
}

/**
 * 
 * @param context
 */
function loadMapByUserGeoLocation(context)
{
	if (navigator.geolocation)
	{
		navigator.geolocation.getCurrentPosition(function (position) { loadMapByPosition(context, position); }, function (errorCode) { loadDefaultMap(context); });
	}
	else
	{
		console.log("Unable to get current user geolocation: assigning Venice as default.");
		
		loadDefaultMap(context);
	}
}

/**
 * 
 * @param context
 */
function loadDefaultMap(context)
{
	loadMapByCoordinates(context, 45.4375, 12.335833);
}

/**
 * 
 * @param context
 * @param position
 */
function loadMapByPosition(context, position)
{
	loadMapByCoordinates(context, position.coords.latitude, position.coords.longitude);
}

/**
 * 
 * @param context
 * @param longitude
 * @param latitude
 */
function loadMapByCoordinates(context, latitude, longitude)
{
	//latitude = 51.5086;
	//longitude = -0.1283;
	
	$("#currentLatitude").text(latitude);
	$("#currentLongitude").text(longitude);
	
	$("#travelToLatitude").val(latitude);
	$("#travelToLongitude").val(longitude);
	$("#travelToWithinDistance").val(100.0);
	
	console.log("Loading map for " + latitude + ", " + longitude);
	
	var options = 
	{
		elt : document.getElementById('artworksRadarMap'),	/* ID of element on the page where you want the map added */
		zoom : 14,											/* initial zoom level of the map */
		latLng :
		{
			lat : latitude,
			lng : longitude
		}, 													/* center of map in latitude/longitude */
		mtype : 'osm',										/* map type (osm) */
		bestFitMargin : 0,									/* margin offset from the map viewport when applying a bestfit on shapes */
		zoomOnDoubleClick : true							/* zoom in when double-clicking on map */
	};

	window.artworksRadarMap = new MQA.TileMap(options);
	
    MQA.withModule('largezoom','viewoptions','geolocationcontrol','insetmapcontrol','mousewheel', function()
    {
    	artworksRadarMap.addControl(
    		new MQA.LargeZoom(),
    		new MQA.MapCornerPlacement(MQA.MapCorner.TOP_LEFT, new MQA.Size(5,5))
    	);
   
    	artworksRadarMap.addControl(new MQA.ViewOptions());
   
    	artworksRadarMap.addControl(
    		new MQA.GeolocationControl(),
    		new MQA.MapCornerPlacement(MQA.MapCorner.TOP_RIGHT, new MQA.Size(10,50))
    	);
   
    	var options =
    	{
    		size: { width: 150, height: 125 },
    		zoom: 3,
    		mapType: 'osmsat',
    		minimized: false
    	};
   
    	artworksRadarMap.addControl(
    		new MQA.InsetMapControl(options),
    		new MQA.MapCornerPlacement(MQA.MapCorner.BOTTOM_RIGHT)
    	);
   
    	artworksRadarMap.enableMouseWheelZoom();
	});
    
    addPoi(latitude, longitude, context + '/resources/images/poi-you.png', '');
}

/**
 * 
 * @param context
 */
function findMuseumsWithinDistance(context)
{
	var latitude     = $("#travelToLatitude").val();
	var longitude    = $("#travelToLongitude").val();
	var distanceInKm = $("#travelToWithinDistance").val();
	
	var url = context + "/museums/lon/" + longitude + "/lat/" + latitude + "/distanceInKm/" + distanceInKm;

	console.log(latitude + ", " + longitude + ", " + distanceInKm + ". URL: " + url);
	
	$.ajax(
	{
		type: "GET",
		url: url,
		dataType: "json"
	})
	.done(function(response)
	{
		showMuseums(response, context, latitude, longitude);
	});
}

/**
 * 
 * @param museums
 * @param context
 * @param latitude
 * @param longitude
 */
function showMuseums(museums, context, latitude, longitude)
{
	artworksRadarMap.setCenter({lat: latitude, lng: longitude});
	artworksRadarMap.removeAllShapes();	
	
	if (museums.length == 0)
	{
		var html = "<table class='artworkRadarListContent'>" +
				     "<tr>" +
				       "<td style='font-weight: 400; color: #33a000;'>NO MUSEUM FOUND :(</td>" +
				     "</tr>";
                   "</table>";

        $("#artworksRadarList").html(html);
	}
	else
	{
		for (var i = 0; i < museums.length; i++)
		{
			var museum = museums[i];
			
			addPoi(museum.latitude, museum.longitude, context + '/resources/images/poi-museum.png', buildPoiRolloverContent(context, museum));
		}
	}
	
	addPoi(latitude, longitude, context + '/resources/images/poi-you.png', '', 30, 30);
	
	artworksRadarMap.bestFit();
}

/**
 * 
 * @param latitude
 * @param longitude
 * @param icon
 * @param content
 */
function addPoi(latitude, longitude, icon, content, width, height)
{
	width = (width != undefined && width != null && width != "") ? width : 50;
	height = (height != undefined && height != null && height != "") ? height : 50;
	
	var poi=new MQA.Poi({ lat: latitude, lng: longitude });
	
	/*An example using the MQA.Icon constructor. This constructor takes a URL to an image, and the image's height and width.*/
	var icon=new MQA.Icon(icon, width, height);
	
	poi.setRolloverContent(content);
	
	/*This tells the POI to use the Icon object that was created rather than the default POI icon.*/
	poi.setIcon(icon);
	
	/*Set the shadow offset for your custom icon if necessary.*/
	poi.setShadowOffset({x:8,y:-4});
	
	/*This will add the POI to the map in the map's default shape collection.*/
	artworksRadarMap.addShape(poi);
}

/**
 * 
 * @param context
 * @param museum
 * @returns {String}
 */
function buildPoiRolloverContent(context, museum)
{
	var museumName = (museum.name).replace(/'/g, "&#39;");
	
	console.log(museumName);
	
	var html = "<table>" +
			     "<tr>" +
			       "<td colspan='3'><strong>" + museum.name + "</strong></td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td colspan='3'>&nbsp;</td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td style='font-family: courier new;'>Address</td>" +
			       "<td>:</td>" +
			       "<td>" + ((museum.address != null) ? museum.address : "N.A.") + "</td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td style='font-family: courier new;'>Web Site</td>" +
			       "<td>:</td>" +
			       "<td><a href='" + museum.website + "' target='_blank'>" + museum.website + "</a></td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td style='font-family: courier new;'>Wikipedia</td>" +
			       "<td>:</td>" +
			       "<td><a href='" + museum.wikipedia + "' target='_blank'>" + museum.wikipedia + "</a></td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td style='font-family: courier new;'>Director</td>" +
			       "<td>:</td>" +
			       "<td>" + ((museum.director != null) ? museum.director : "N.A.") + "</td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td style='font-family: courier new;'>Latitude</td>" +
			       "<td>:</td>" +
			       "<td>" + museum.latitude + "</td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td style='font-family: courier new;'>Longitude</td>" +
			       "<td>:</td>" +
			       "<td>" + museum.longitude + "</td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td colspan='3'>&nbsp;</td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td colspan='3' align='center'><strong><a href='javascript:loadArtworks(\"" + context + "\", " + museum.id + ", \"" + museumName + "\")' style='color: red;'>SHOW HOSTED PAINTINGS</a></strong></td>" +
			     "</tr>" +
			   "</table>";
	
	return html;
}

/**
 * 
 * @param context
 * @param museumId
 * @param museumName
 */
function loadArtworks(context, museumId, museumName)
{
	$.ajax(
	{
		type: "GET",
		url: context + "/artworks/museum/" + museumId,
		dataType: "json"
	})
	.done(function(response)
	{
		showArtworksList(context, museumName, response);
	});
}

/**
 * 
 * @param context
 * @param museumName
 * @param artworks
 */
function showArtworksList(context, museumName, artworks)
{
	var html = "<table class='artworkRadarListContent'>" +
                 "<tr>" +
                   "<td style='font-weight: 400; color: #33a000;'>" + museumName + "</td>" +
                 "</tr>";
	
	for (var i = 0; i < artworks.length; i++)
	{
		var artwork = artworks[i];
		
		html +=  "<tr>" +
                   "<td>&nbsp;</td>" +
                 "</tr>" +
                 "<tr>" +
                   "<td><strong>" + artwork.title + "<strong></td>" +
                 "</tr>" +
  			     "<tr>" +
			       "<td><strong>" + artwork.artist.firstName + " " + artwork.artist.lastName + "</strong></td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td><small>" + ((artwork.month != null) ? artwork.month : "") + " " + ((artwork.year != null) ? artwork.year : "")  + "</small></td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td><small>" + artwork.type + "<small></td>" +
			     "</tr>" +
			     "<tr>" +
			       "<td><small><a href='javascript:showArtworkThumbnail(\"" + artwork.thumbnail + "\");' target='_blank'>" + artwork.thumbnail + "</a></small></td>" +
			     "</tr>" +
				 "<tr>" +
				   "<td><small>F:" + artwork.forder + ", JH:" + artwork.jhorder + "</small></td>" +
				 "</tr>";
	}
	
    html += "</table>";

	$("#artworksRadarList").html(html);
}

/**
 * 
 * @param url
 */
function showArtworkThumbnail(url)
{
	var html = "<div style='text-align:center;'>" +
			     "<img style='max-width: 575px; max-height: 575px;' src='" + url + "'>" +
			     "<br/><br/>" +
			     "<strong><a href='javascript:hideArtworkThumbnail();' style='font-family: Source Sans Pro; color: rgb(30, 151, 255);'>CLOSE</a></strong>" +
			   "<div>";

	$("#artworkThumbnail").html(html);
	$("#artworkThumbnail").show();
}

/**
 * 
 * @param url
 */
function hideArtworkThumbnail(url)
{
	$("#artworkThumbnail").hide();
}

/**
 * 
 * @param context
 * @param place
 */
function travelTo(context, place)
{
	if (place == "neo4j-sanmateo") 
	{
		//geo:37.554167,-122.313056
		
		$("#travelToLatitude").val(37.554167);
		$("#travelToLongitude").val(-122.313056);
		$("#travelToWithinDistance").val(40.0);
	}
	else if (place == "neo4j-london")
	{
		// geo:51.5086,-0.1283
		
		$("#travelToLatitude").val(51.5086);
		$("#travelToLongitude").val(-0.1283);
		$("#travelToWithinDistance").val(10.0);
	}
	else if (place == "neo4j-malmo")
	{
		// geo:55.605833,13.035833
		
		$("#travelToLatitude").val(55.605833);
		$("#travelToLongitude").val(13.035833);
		$("#travelToWithinDistance").val(100.0);
	}
	else if (place == "neo4j-desden")
	{
		// geo:51.033333,13.733333
		
		$("#travelToLatitude").val(51.033333);
		$("#travelToLongitude").val(13.733333);
		$("#travelToWithinDistance").val(250.0);
	}
	else if (place == "neo4j-venice")
	{
		// geo:45.4375,12.335833
		
		$("#travelToLatitude").val(45.4375);
		$("#travelToLongitude").val(12.335833);
		$("#travelToWithinDistance").val(500.0);
	}
	
	findMuseumsWithinDistance(context);	
}
