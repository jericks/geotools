/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2011, Open Source Geospatial Foundation (OSGeo)
 *    (C) 2005 Open Geospatial Consortium Inc.
 *    (C) 2001-2004 EXSE, Department of Geography, University of Bonn
 *                  lat/lon Fitzke/Fretter/Poth GbR
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.opengis.filter.capability;

/**
 * Capabilities used to convey supported temporal operators.
 *
 * <p>
 *
 * <pre>
 *   &lt;xsd:complexType name="Temporal_CapabilitiesType">
 *     &lt;xsd:sequence>
 *        &lt;xsd:element name="TemporalOperands"
 *                     type="fes:TemporalOperandsType"/>
 *        &lt;xsd:element name="TemporalOperators"
 *                     type="fes:TemporalOperatorsType"/>
 *     &lt;/xsd:sequence>
 *  &lt;/xsd:complexType>
 *   </pre>
 *
 * @author Justin Deoliveira, OpenGeo
 */
public interface TemporalCapabilities {

    /** The temporal operators provided by this capabilities. */
    TemporalOperators getTemporalOperators();
}
