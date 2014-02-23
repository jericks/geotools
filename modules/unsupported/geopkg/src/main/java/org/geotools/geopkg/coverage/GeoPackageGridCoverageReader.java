package org.geotools.geopkg.coverage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geotools.coverage.CoverageFactoryFinder;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverage2DReader;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.factory.Hints;
import org.geotools.geometry.GeneralEnvelope;
import org.geotools.geopkg.GeoPackage;
import org.geotools.geopkg.RasterEntry;
import org.geotools.parameter.Parameter;
import org.geotools.referencing.CRS;
import org.geotools.util.Utilities;
import org.geotools.util.logging.Logging;
import org.opengis.coverage.grid.Format;
import org.opengis.coverage.grid.GridCoverageReader;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.referencing.ReferenceIdentifier;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * The GeoPackageGridCoverageReader can read coverages from a geopackage
 * database.
 * @author Jared Erickson
 */
public class GeoPackageGridCoverageReader extends AbstractGridCoverage2DReader {

    private final static Logger LOGGER = Logging.getLogger("org.geotools.geopkg.coverage");

    private final File sourceFile;

    private final Map<String, RasterEntry> cachedRasters = new HashMap<String, RasterEntry>();

    public GeoPackageGridCoverageReader(Object source, Hints hints) {
        coverageFactory = CoverageFactoryFinder.getGridCoverageFactory(this.hints);
        sourceFile = GeoPackageGridFormat.getFileFromSource(source);
    }

    private Map<String, RasterEntry> getRasters() {
        if (cachedRasters.isEmpty()) {
            Map<String, RasterEntry> rasters = new HashMap<String, RasterEntry>();
            try {
                GeoPackage file = new GeoPackage(sourceFile);
                try {
                    for (RasterEntry raster : file.rasters()) {
                        rasters.put(raster.getTableName(), raster);
                    }
                } finally {
                    file.close();
                }
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Unable to read rasters from geopackage database!");
            }
            cachedRasters.putAll(rasters);
        }
        return cachedRasters;
    }

    @Override
    public Format getFormat() {
        return new GeoPackageGridFormat();
    }

    @Override
    protected boolean checkName(String coverageName) {
        Utilities.ensureNonNull("coverageName", coverageName);
        return getRasters().keySet().contains(coverageName);
    }

    @Override
    public GeneralEnvelope getOriginalEnvelope(String coverageName) {
        if (!checkName(coverageName)) {
            throw new IllegalArgumentException("The specified coverageName " + coverageName
                    + "is not supported");
        }

        return new GeneralEnvelope(getRasters().get(coverageName).getBounds());
    }

    @Override
    public CoordinateReferenceSystem getCoordinateReferenceSystem(String coverageName) {
        if (!checkName(coverageName)) {
            throw new IllegalArgumentException("The specified coverageName " + coverageName
                    + "is not supported");
        }

        try {
            return CRS.decode("EPSG:" + getRasters().get(coverageName).getSrid(), true);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String[] getGridCoverageNames() {
        return getRasters().keySet().toArray(new String[getRasters().size()]);
    }

    @Override
    public int getGridCoverageCount() {
        return getRasters().size();
    }

    @Override
    public GridCoverage2D read(String coverageName, GeneralParameterValue[] parameters) throws IllegalArgumentException, IOException {

        AbstractGridFormat format = null;
        if (parameters != null) {
            for (GeneralParameterValue gpv : parameters) {
                final ReferenceIdentifier id = gpv.getDescriptor().getName();
                if (id.equals(GeoPackageGridFormat.GRID_FORMAT.getName())) {
                    format = (AbstractGridFormat) ((Parameter) gpv).getValue();
                    break;
                }
            }
        }

        if (format == null) {
            throw new IllegalArgumentException("Please specify a format!");
        }

        GridCoverage2D c = null;
        GeoPackage geopkg = new GeoPackage(sourceFile);
        try {
            RasterEntry entry = getRasters().get(coverageName);
            GridCoverageReader r = geopkg.reader(entry, format);
            c = (GridCoverage2D) r.read(null);
        } finally {
            geopkg.close();
        }
        return c;
    }

    @Override
    public GridCoverage2D read(GeneralParameterValue[] parameters) throws IllegalArgumentException, IOException {
        throw new IllegalArgumentException("No layer specified!");
    }

}
