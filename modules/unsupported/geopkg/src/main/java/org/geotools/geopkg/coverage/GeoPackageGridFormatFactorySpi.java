package org.geotools.geopkg.coverage;

import java.awt.RenderingHints;
import java.util.Collections;
import java.util.Map;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.GridFormatFactorySpi;

/**
 * The GeoPackageGridFormatFactorySpi registers the GeoPackageGridFormat
 * with the Service Provider Interface.
 * @author Jared Erickson
 */
public class GeoPackageGridFormatFactorySpi implements GridFormatFactorySpi {

    @Override
    public AbstractGridFormat createFormat() {
        return new GeoPackageGridFormat();
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public Map<RenderingHints.Key, ?> getImplementationHints() {
        return Collections.emptyMap();
    }

}
