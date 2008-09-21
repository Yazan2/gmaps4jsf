<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"
      type="text/javascript"></script>
	<style>
		html, body { height: 100% }
	</style>  
    </HEAD>
	
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
		  	<div>Mixed Polylines.</div>    	
    		<m:map width="90%" height="90%" latitude="24" longitude="15" zoom="2">
    			<m:polygon lineWidth="1">
    				<m:point latitude="30.01" longitude="31.14"/>
    				<m:point latitude="-33" longitude="19"/>    				
    				<m:point latitude="39" longitude="-101"/>  	
    				<m:point latitude="30.01" longitude="31.14"/>
					<m:eventListener eventName="click" jsFunction="polgonClickHandler"/>	    				    								
    			</m:polygon>
    			<m:polyline lineWidth="10" hexaColor="#ff0000" geodesic="true">
    				<m:point latitude="30.01" longitude="31.14"/>
    				<m:point latitude="48" longitude="2"/>    				
    				<m:point latitude="43" longitude="141"/>  	   								
					<m:eventListener eventName="click" jsFunction="polylineClickHandler"/>	    				
    			</m:polyline>   
    			
			    <script>
			   	function polgonClickHandler() {
			   		alert("You clicked on the area of (Egypt, South Africa, USA)");  	 	
			   	}
			   	function polylineClickHandler() {
			   		alert("You clicked on the line of (Egypt, France, Japan)");  
			   	}		   		   	
			    </script>       			
    			         			
    		</m:map>		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
