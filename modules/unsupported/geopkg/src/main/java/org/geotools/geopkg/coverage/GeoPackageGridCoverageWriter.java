package org.geotools.geopkg.coverage;

import java.io.File;
import java.io.IOException;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridCoverageWriter;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.factory.Hints;
import org.geotools.geopkg.GeoPackage;
import org.geotools.geopkg.RasterEntry;
import org.geotools.parameter.Parameter;
import org.opengis.coverage.grid.Format;
import org.opengis.coverage.grid.GridCoverage;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.referencing.ReferenceIdentifier;

/**
 * The GeoPackageGridCoverageWriter writes coverages to a geopackage database.
 * @author Jared Erickson
 */
public class GeoPackageGridCoverageWriter extends AbstractGridCoverageWriter {

    public GeoPackageGridCoverageWriter(Object destination, Hints hints) {
        this.destination = destination;
        this.hints = hints;
    }

    @Override
    public Format getFormat() {
        return new GeoPackageGridFormat();
    }

    @Override
    public void write(GridCoverage coverage, GeneralParameterValue[] parameters) throws IllegalArgumentException, IOException {
        
        // Required parameters
        String tableName = null;
        AbstractGridFormat format = null;
        
        // Optional parameters
        String identifier = null;
        String description = null;
        String rasterColumn = null;
        String name = null;
        String title = null;
        String constraint = null;
        
        if (parameters != null) {
            for (GeneralParameterValue gpv : parameters) {
                final ReferenceIdentifier id = gpv.getDescriptor().getName();
                // Required
                if (id.equals(GeoPackageGridFormat.TABLE_NAME.getName())) {
                    tableName = (String) ((Parameter) gpv).getValue();
                } else if (id.equals(GeoPackageGridFormat.GRID_FORMAT.getName())) {
                    format = (AbstractGridFormat) ((Parameter) gpv).getValue();
                }
                // Optional
                else if (id.equals(GeoPackageGridFormat.IDENTIFIER.getName())) {
                    identifier = (String) ((Parameter) gpv).getValue();
                }
                else if (id.equals(GeoPackageGridFormat.DESCRIPTION.getName())) {
                    description = (String) ((Parameter) gpv).getValue();
                }
                else if (id.equals(GeoPackageGridFormat.RASTER_COLUMN.getName())) {
                    rasterColumn = (String) ((Parameter) gpv).getValue();
                }
                else if (id.equals(GeoPackageGridFormat.NAME.getName())) {
                    name = (String) ((Parameter) gpv).getValue();
                }
                else if (id.equals(GeoPackageGridFormat.TITLE.getName())) {
                    title = (String) ((Parameter) gpv).getValue();
                }
                else if (id.equals(GeoPackageGridFormat.CONSTRAINT.getName())) {
                    constraint = (String) ((Parameter) gpv).getValue();
                }
            }
        }
        
        // Check for required parameters
        if (tableName == null) {
            throw new IllegalArgumentException("Please specify a table name!");
        } else if (format == null) {
            throw new IllegalArgumentException("Please specify a format!");
        }
        
        // Write the Raster
        File file = GeoPackageGridFormat.getFileFromSource(destination);
        GeoPackage geopkg = new GeoPackage(file);
        try {
            RasterEntry entry = new RasterEntry();
            entry.setTableName(tableName);
            if (identifier != null) {
                entry.setIdentifier(identifier);
            }
            if (description != null) {
                entry.setDescription(description);
            }
            if (rasterColumn != null) {
                entry.setRasterColumn(rasterColumn);
            }
            if (name != null) {
                entry.setName(name);
            }
            if (title != null) {
                entry.setTitle(title);
            }
            if (constraint != null) {
                entry.setConstraint(constraint);
            }
            geopkg.add(entry, (GridCoverage2D) coverage, format);
        } finally {
            geopkg.close();
        }
    }
}