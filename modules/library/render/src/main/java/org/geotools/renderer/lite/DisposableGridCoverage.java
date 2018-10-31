/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2004-2008, Open Source Geospatial Foundation (OSGeo)
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
package org.geotools.renderer.lite;

import java.awt.image.RenderedImage;
import javax.media.jai.PlanarImage;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.image.util.ImageUtilities;

/**
 * Internal wrapper used by streaming renderer to figure out if a GridCoverage2D should be disposed
 * at the end of processing, or not. Used when the coverage has been generated by a rendering
 * transformation, instead of coming from an input data
 *
 * @author Andrea Aime - GeoSolutions
 */
class DisposableGridCoverage extends GridCoverage2D {

    /** */
    private static final long serialVersionUID = 1L;

    /**
     * A custom propertySource allowing to redefine properties (since getProperties return a clone)
     */
    //    private PropertySourceImpl wrappedPropertySource;

    GridCoverage2D delegate;

    public DisposableGridCoverage(GridCoverage2D coverage) {
        super(coverage.getName(), coverage);
        delegate = coverage;
        //        wrappedPropertySource = new PropertySourceImpl(
        //                Collections.singletonMap("disposable", true),
        //                coverage instanceof PropertySource ? (PropertySource) coverage : null);
    }

    //    @Override
    //    public Map getProperties() {
    //        return wrappedPropertySource.getProperties();
    //    }
    //
    //    @Override
    //    public Object getProperty(String arg0) {
    //        return wrappedPropertySource.getProperty(arg0);
    //    }

    @Override
    public synchronized boolean dispose(boolean force) {
        try {
            delegate.dispose(force);
            final RenderedImage image = delegate.getRenderedImage();
            if (image instanceof PlanarImage) {
                ImageUtilities.disposePlanarImageChain((PlanarImage) image);
            }
        } finally {
            return super.dispose(force);
        }
    }
}
