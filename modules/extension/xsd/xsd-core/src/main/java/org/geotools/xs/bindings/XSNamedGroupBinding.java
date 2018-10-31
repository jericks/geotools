/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.xs.bindings;

import javax.xml.namespace.QName;
import org.geotools.xs.XS;
import org.geotools.xsd.AbstractComplexBinding;
import org.geotools.xsd.ElementInstance;
import org.geotools.xsd.Node;
import org.picocontainer.MutablePicoContainer;

/**
 * Binding object for the type http://www.w3.org/2001/XMLSchema:namedGroup.
 *
 * <p>
 *
 * <pre>
 *         <code>
 *  &lt;xs:complexType name="namedGroup"&gt;
 *      &lt;xs:complexContent&gt;
 *          &lt;xs:restriction base="xs:realGroup"&gt;
 *              &lt;xs:sequence&gt;
 *                  &lt;xs:element ref="xs:annotation" minOccurs="0"/&gt;
 *                  &lt;xs:choice minOccurs="1" maxOccurs="1"&gt;
 *                      &lt;xs:element name="all"&gt;
 *                          &lt;xs:complexType&gt;
 *                              &lt;xs:complexContent&gt;
 *                                  &lt;xs:restriction base="xs:all"&gt;
 *                                      &lt;xs:group ref="xs:allModel"/&gt;
 *                                      &lt;xs:attribute name="minOccurs" use="prohibited"/&gt;
 *                                      &lt;xs:attribute name="maxOccurs" use="prohibited"/&gt;
 *                                      &lt;xs:anyAttribute namespace="##other" processContents="lax"/&gt;
 *                                  &lt;/xs:restriction&gt;
 *                              &lt;/xs:complexContent&gt;
 *                          &lt;/xs:complexType&gt;
 *                      &lt;/xs:element&gt;
 *                      &lt;xs:element name="choice" type="xs:simpleExplicitGroup"/&gt;
 *                      &lt;xs:element name="sequence" type="xs:simpleExplicitGroup"/&gt;
 *                  &lt;/xs:choice&gt;
 *              &lt;/xs:sequence&gt;
 *              &lt;xs:attribute name="name" use="required" type="xs:NCName"/&gt;
 *              &lt;xs:attribute name="ref" use="prohibited"/&gt;
 *              &lt;xs:attribute name="minOccurs" use="prohibited"/&gt;
 *              &lt;xs:attribute name="maxOccurs" use="prohibited"/&gt;
 *              &lt;xs:anyAttribute namespace="##other" processContents="lax"/&gt;
 *          &lt;/xs:restriction&gt;
 *      &lt;/xs:complexContent&gt;
 *  &lt;/xs:complexType&gt;
 *
 *          </code>
 *         </pre>
 *
 * @generated
 */
public class XSNamedGroupBinding extends AbstractComplexBinding {
    /** @generated */
    public QName getTarget() {
        return XS.NAMEDGROUP;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public int getExecutionMode() {
        return AFTER;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Class getType() {
        return null;
    }

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public void initialize(ElementInstance instance, Node node, MutablePicoContainer context) {}

    /**
     *
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @generated modifiable
     */
    public Object parse(ElementInstance instance, Node node, Object value) throws Exception {
        // TODO: implement
        return null;
    }
}
