package org.geotools.geopkg.coverage;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.coverage.grid.io.imageio.GeoToolsWriteParams;
import org.geotools.factory.Hints;
import org.geotools.parameter.DefaultParameterDescriptor;
import org.geotools.parameter.DefaultParameterDescriptorGroup;
import org.geotools.parameter.ParameterGroup;
import org.geotools.referencing.factory.gridshift.DataUtilities;
import org.geotools.util.logging.Logging;
import org.opengis.coverage.grid.GridCoverageWriter;
import org.opengis.parameter.GeneralParameterDescriptor;

/**
 * The GeoPackageGridFormat can read and write coverages in a geopackage
 * database.
 * @author Jared Erickson
 */
public class GeoPackageGridFormat extends AbstractGridFormat {

    private final static Logger LOGGER = Logging.getLogger("org.geotools.geopkg.coverage");

    public static final DefaultParameterDescriptor<String> TABLE_NAME
            = new DefaultParameterDescriptor<String>("TableName", String.class, null, null);

    public static final DefaultParameterDescriptor<AbstractGridFormat> GRID_FORMAT
            = new DefaultParameterDescriptor<AbstractGridFormat>("GridFormat", AbstractGridFormat.class, null, null);

    public static final DefaultParameterDescriptor<String> IDENTIFIER
            = new DefaultParameterDescriptor<String>("identifier", String.class, null, null);

    public static final DefaultParameterDescriptor<String> DESCRIPTION
            = new DefaultParameterDescriptor<String>("description", String.class, null, null);

    public static final DefaultParameterDescriptor<String> RASTER_COLUMN
            = new DefaultParameterDescriptor<String>("rastercolumn", String.class, null, null);

    public static final DefaultParameterDescriptor<String> NAME
            = new DefaultParameterDescriptor<String>("name", String.class, null, null);

    public static final DefaultParameterDescriptor<String> TITLE
            = new DefaultParameterDescriptor<String>("title", String.class, null, null);

    public static final DefaultParameterDescriptor<String> MIME_TYPE
            = new DefaultParameterDescriptor<String>("mimetype", String.class, null, null);

    public static final DefaultParameterDescriptor<String> CONSTRAINT
            = new DefaultParameterDescriptor<String>("constraint", String.class, null, null);

    public GeoPackageGridFormat() {

        final HashMap<String, String> info = new HashMap<String, String>();
        info.put("name", "GeoPackage");
        info.put("description", "GeoPackage plugin");
        info.put("vendor", "Geotools");
        info.put("docURL", "");
        info.put("version", "1.0");
        mInfo = info;

        readParameters = new ParameterGroup(new DefaultParameterDescriptorGroup(mInfo,
                new GeneralParameterDescriptor[]{
                    GRID_FORMAT
                }
        ));

        writeParameters = new ParameterGroup(new DefaultParameterDescriptorGroup(mInfo,
                new GeneralParameterDescriptor[]{
                    GRID_FORMAT, TABLE_NAME, IDENTIFIER, DESCRIPTION, RASTER_COLUMN,
                    NAME, TITLE, MIME_TYPE, CONSTRAINT
                }
        ));

    }

    @Override
    public AbstractGridCoverage2DReader getReader(Object source) {
        return getReader(source, null);
    }

    @Override
    public AbstractGridCoverage2DReader getReader(Object source, Hints hints) {
        return new GeoPackageGridCoverageReader(source, hints);
    }

    @Override
    public GridCoverageWriter getWriter(Object destination) {
        return getWriter(destination, null);
    }

    @Override
    public GridCoverageWriter getWriter(Object destination, Hints hints) {
        return new GeoPackageGridCoverageWriter(destination, hints);
    }

    @Override
    public boolean accepts(Object source, Hints hints) {
        if (source == null) {
            return false;
        }
        File sourceFile = getFileFromSource(source);
        if (sourceFile == null) {
            return false;
        }
        return sourceFile.getName().endsWith(".gpkg");
    }

    @Override
    public GeoToolsWriteParams getDefaultImageIOWriteParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected static File getFileFromSource(Object source) {
        if (source == null) {
            return null;
        }

        File sourceFile = null;

        try {
            if (source instanceof File) {
                sourceFile = (File) source;
            } else if (source instanceof URL) {
                if (((URL) source).getProtocol().equals("file")) {
                    sourceFile = DataUtilities.urlToFile((URL) source);
                }
            } else if (source instanceof String) {
                sourceFile = new File((String) source);
            }
        } catch (Exception e) {
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE, e.getLocalizedMessage(), e);
            }

            return null;
        }

        return sourceFile;
    }
}
