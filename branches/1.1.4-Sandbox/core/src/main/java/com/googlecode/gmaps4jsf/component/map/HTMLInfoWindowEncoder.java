/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.googlecode.gmaps4jsf.component.map;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.window.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Jul 31, 2008
 * The HTMLInfoWindowEncoder is used for encpding the map info windows.
 */
public class HTMLInfoWindowEncoder {
    
    private static final String JS_FUNC_GET_INFO_WINDOW = "getInfoWindow";
    private static final String JS_FUNC_OPEN_INFO_WINDOW_HTML = "openInfoWindowHtml";

    /**
     * The (encodeMarkerHTMLInfoWindow) method is used for encoding the 
     * HTMLInfoWindow when its parent is the marker. 
     * @param facesContext
     * @param marker
     * @param window
     * @param writer
     * @throws IOException
     */
    public static void encodeMarkerHTMLInfoWindow(FacesContext facesContext,
                                                  Marker marker, 
                                                  HTMLInformationWindow window, 
                                                  ResponseWriter writer)
                                                  throws IOException {

        writer.write(ComponentConstants.CONST_MARKER_PREFIX + marker.getClientId(facesContext).replace(':', '_')
                    + "." 
                    + HTMLInfoWindowEncoder.JS_FUNC_OPEN_INFO_WINDOW_HTML 
                    + "('" + window.getHtmlText() + "');    ");

        writer.write("var window_" + window.getId() + " = "
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE
                    + "." 
                    + JS_FUNC_GET_INFO_WINDOW 
                    + "();    ");

        // encode window events.
        for (Iterator iterator = window.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof EventListener) {
                EventEncoder.encodeEventListenersFunctionScript(facesContext,
                                                                window, 
                                                                writer, 
                                                                "window_" + window.getId());
                
                EventEncoder.encodeEventListenersFunctionScriptCall(facesContext, 
                                                                    window, 
                                                                    writer, 
                                                                    "window_" + window.getId());
            }
        }
    }
    
    public static void encodeHTMLInfoWindowsFunctionScript(FacesContext facesContext,
                                                           Map mapComponent, 
                                                           ResponseWriter writer) 
                                                           throws IOException {

        writer.write(ComponentConstants.JS_FUNCTION
                    + ComponentConstants.JS_CREATE_HTMLINFOWINDOWS_FUNCTION_PREFIX
                    + mapComponent.getId() + "("
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");
        
        for (Iterator iterator = mapComponent.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof HTMLInformationWindow && component.isRendered()) {
                encodeMapHTMLInfoWindow(facesContext, mapComponent, (HTMLInformationWindow) component, writer);
            }
        }
        
        writer.write("}");
    }    
    
    public static void encodeHTMLInfoWindowsFunctionScriptCall(FacesContext facesContext, 
                                                               Map mapComponent, 
                                                               ResponseWriter writer)
                                                               throws IOException {

        writer.write(ComponentConstants.JS_CREATE_HTMLINFOWINDOWS_FUNCTION_PREFIX
                    + mapComponent.getId()
                    + "("
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE + ");    ");
    }
    
    private static void encodeMapHTMLInfoWindow(FacesContext facesContext,
                                                Map mapComponent, 
                                                HTMLInformationWindow window,
                                                ResponseWriter writer) 
                                                throws IOException {

        String longitude;
        String latitude;

        if (window.getLatitude() != null) {
            latitude = window.getLatitude();
        } else {
            latitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
                     + ".getCenter().lat()";
        }

        if (window.getLongitude() != null) {
            longitude = window.getLongitude();
        } else {
            longitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
                      + ".getCenter().lng()";
        }

        writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
                    + "." 
                    + JS_FUNC_OPEN_INFO_WINDOW_HTML 
                    + "(new " + ComponentConstants.JS_GLatLng_OBJECT 
                    + "(" + latitude + ", " + longitude + "), '" + window.getHtmlText() + "');    ");   
        
        writer.write("var window_" + window.getId() + " = "
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE
                    + "." 
                    + HTMLInfoWindowEncoder.JS_FUNC_GET_INFO_WINDOW 
                    + "();    "); 
    
        // encode window events.
        for (Iterator iterator = window.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof EventListener) {
                EventEncoder.encodeEventListenersFunctionScript(facesContext,
                                                                window, 
                                                                writer, 
                                                                "window_" + window.getId());
                
                EventEncoder.encodeEventListenersFunctionScriptCall(facesContext, 
                                                                    window, 
                                                                    writer, 
                                                                    "window_" + window.getId());
            }
        }           
    }    
}