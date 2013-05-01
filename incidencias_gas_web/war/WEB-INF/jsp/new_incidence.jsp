<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Nueva Incidencia</title>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		div#map {
			position: relative;
		}
		
		div#crosshair {
			position: absolute;
			top: 192px;
			height: 19px;
			width: 19px;
			left: 50%;
			margin-left: -8px;
			display: block;
			background: url(images/crosshair.gif);
			background-position: center center;
			background-repeat: no-repeat;
		}
	</style>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDDArFQoQKBuyw5xKJFi4Xk4kXfqblK2Bo&sensor=false"></script>
<script type="text/javascript">

	var map;
	var geocoder;
	var centerChangedLast;
	var reverseGeocodedLast;
	var currentReverseGeocodeResponse;

	function initialize() {
		var latlng = new google.maps.LatLng(43.361914, -5.849389);
		var myOptions = {
			zoom : 10,
			center : latlng,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
		geocoder = new google.maps.Geocoder();
		setupEvents();
		centerChanged();
		
		var position = getElementTopLeft("map_canvas");
		var div = document.getElementById('crosshair');
		myLeft = position.left + document.getElementById("map_canvas").offsetWidth/2;
		myTop = position.top + document.getElementById("map_canvas").offsetHeight/2;
		div.style.left = myLeft + 'px';
		div.style.top = myTop + 'px';
		
		
	}
	
	function getElementTopLeft(id) {
		var ele = document.getElementById(id);
		var top = 0;
		var left = 0;
		while (ele.tagName != "BODY") {
			top += ele.offsetTop;
			left += ele.offsetLeft;
			ele = ele.offsetParent;
		}
		return {
			top : top,
			left : left
		};
	}

	function setupEvents() {
		reverseGeocodedLast = new Date();
		centerChangedLast = new Date();

		setInterval(
				function() {
					if ((new Date()).getSeconds()
							- centerChangedLast.getSeconds() > 1) {
						if (reverseGeocodedLast.getTime() < centerChangedLast
								.getTime())
							reverseGeocode();
					}
				}, 1000);

		google.maps.event.addListener(map, 'zoom_changed', function() {
			document.getElementById("zoom_level").innerHTML = map.getZoom();
		});

		google.maps.event.addListener(map, 'center_changed', centerChanged);
		google.maps.event.addDomListener(document.getElementById('crosshair'),
				'dblclick', function() {
					map.setZoom(map.getZoom() + 1);
				});

	}

	function getCenterLatLngText() {
		return '(' + map.getCenter().lat() + ', ' + map.getCenter().lng() + ')';
	}

	function centerChanged() {
		centerChangedLast = new Date();
		var latlng = getCenterLatLngText();
		document.getElementById('latlng').innerHTML = latlng;
		document.getElementById('formatedAddress').innerHTML = '';
		currentReverseGeocodeResponse = null;
	}

	function reverseGeocode() {
		reverseGeocodedLast = new Date();
		geocoder.geocode({
			latLng : map.getCenter()
		}, reverseGeocodeResult);
	}

	function reverseGeocodeResult(results, status) {
		currentReverseGeocodeResponse = results;
		if (status == 'OK') {
			if (results.length == 0) {
				document.getElementById('formatedAddress').innerHTML = 'None';
			} else {
				document.getElementById('formatedAddress').innerHTML = results[0].formatted_address;
			}
		} else {
			document.getElementById('formatedAddress').innerHTML = 'Error';
		}
	}

	function geocode() {
		var address = document.getElementById("address").value;
		geocoder.geocode({
			'address' : address,
			'partialmatch' : true
		}, geocodeResult);
	}

	function geocodeResult(results, status) {
		if (status == 'OK' && results.length > 0) {
			map.fitBounds(results[0].geometry.viewport);
		} else {
			alert("No se han encontrado resultados: " + status);
		}
	}

	function copyAddress() {
		var txtAddress = document.getElementById('formatedAddress').innerHTML;
		txtAddress += " ";
		txtAddress += document.getElementById('latlng').innerHTML;
		document.getElementById("trouble_address").value = txtAddress;
	}
</script>
	
</head>
<body onload="initialize()">
	<h1>Nueva Incidencia</h1>
	<hr>
	<table width="100%" cellpadding="0px" cellspacing="0px">
		<tr>
			<td valign="top">
				<form action="newIncidence" id="new_incidence" method="post">
					<table cellpadding="0px" cellspacing="0px">
						<tr height="50px">
							<td>
								Nombre contacto:
							</td>				
							<td>
								<input id="contact_name" type="text" size="30" maxlength="50">
							</td>
						</tr>
						<tr height="50px">
							<td>
								Teléfono contacto:
							</td>				
							<td>
								<input id="contact_phone" type="text" size="30" maxlength="50">
							</td>
						</tr>
						<tr height="50px">
							<td>
								Dirección avería:
							</td>				
							<td>
								<input id="trouble_address" type="text" size="64" maxlength="200"> 
							</td>
						</tr>			
						<tr height="50px">
							<td valign="top">
								Descripción avería:
							</td>				
							<td>
								<textarea rows="4" cols="50"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="left">
								<button type="button" onclick="document.getElementById('new_incidence').submit();">crear</button>
							</td>
						</tr>		
					</table>			
				</form>			
			</td>
			<td width="50%">
				<table cellpadding="0px" cellspacing="0px" width="80%">
					<tr>
						<td>Dirección avería:</td>
						<td>
							<input id="address" type="text" size="50" maxlength="60">
						</td>
						<td>
							<button type="button" onclick="geocode()">buscar</button>
						</td>					
					</tr>
				</table>
				<br>
				<div id="map_canvas" style="width:560px; height:300px"></div>
				<div id="crosshair"></div> 
				<table>
					<tr>
						<td>Coordenadas: </td>
						<td><div id="latlng"></div></td>
					</tr>
					<tr>
						<td>Dirección:</td>
						<td><div id="formatedAddress"></div></td>
					</tr>
					<tr>
						<td>Zoom</td>
						<td><div id="zoom_level">10</div></td>
					</tr>
					<tr>
						<td colspan="2" align="left"><input type="button" value="copiar dirección" onclick="copyAddress()"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>