package org.geotools.geopkg.coverage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.apache.commons.io.FileUtils;
import org.geotools.TestData;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.io.AbstractGridFormat;
import org.geotools.data.DataUtilities;
import org.geotools.gce.geotiff.GeoTiffFormat;
import org.geotools.gce.geotiff.GeoTiffReader;
import org.geotools.gce.image.WorldImageFormat;
import org.geotools.gce.image.WorldImageReader;
import org.geotools.geopkg.GeoPackage;
import org.geotools.geopkg.RasterEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import org.opengis.coverage.grid.GridCoverageReader;
import org.opengis.parameter.GeneralParameterValue;
import org.opengis.parameter.ParameterValue;

public class GeoPackageGridFormatTest {
    
    GeoPackage geopkg;
    
    @Before
    public void setUp() throws Exception {
        geopkg = new GeoPackage(File.createTempFile("geopkg", "db", new File("target")));
        geopkg.init();
    }

    @After
    public void tearDown() throws Exception {
        geopkg.close();

        //for debugging, copy the current geopackage file to a well known file
        File f = new File("target", "geopkg.db");
        if (f.exists()) {
            f.delete();
        }

        FileUtils.copyFile(geopkg.getFile(), f);
    }
    
    @Test 
    public void writeReadTiff() throws Exception {
        
        GeoPackageGridFormat gpkgFormat = new GeoPackageGridFormat();
        
        // Read GeoTiff file
        GeoTiffFormat format = new GeoTiffFormat();
        GeoTiffReader reader = format.getReader(setUpGeoTiff());
        GridCoverage2D cov = reader.read(null);
        assertNotNull(cov);
        
        // Get GeoPackage GridCoverageWriter
        GeoPackageGridCoverageWriter gpkgWriter = (GeoPackageGridCoverageWriter) gpkgFormat.getWriter(geopkg.getFile());
        
        // Get parameters for table name and GridFormat
        String rasterTableName = "raster_tif";
        ParameterValue<String> tableName = GeoPackageGridFormat.TABLE_NAME.createValue();
        tableName.setValue(rasterTableName);
        
        ParameterValue<AbstractGridFormat> gridFormat = GeoPackageGridFormat.GRID_FORMAT.createValue();
        gridFormat.setValue(format);
        
        // Write Coverage to GeoPackage
        gpkgWriter.write(cov, new GeneralParameterValue[]{
            tableName, gridFormat
        });

        // Get RasterEntry and check for coverage
        RasterEntry entry = geopkg.raster(rasterTableName);
        GridCoverage2D c;
        GridCoverageReader r = geopkg.reader(entry, format);
        c = (GridCoverage2D) r.read(null);
        assertNotNull(c);
        
        // Get GeoPackage GridCoverageReader
        GeoPackageGridCoverageReader gpkgReader = (GeoPackageGridCoverageReader) gpkgFormat.getReader(geopkg.getFile());
        
        // Read coverage from raster table
        c = gpkgReader.read(rasterTableName, new GeneralParameterValue[]{
            gridFormat
        });
        assertNotNull(c);
    }
    
    @Test @Ignore
    public void writeReadPng() throws Exception {
        //TODO: re-enable this test once we can pass in the bounds to a world image
        GeoPackageGridFormat gpkgFormat = new GeoPackageGridFormat();
        
        // Read PNG file
        WorldImageFormat format = new WorldImageFormat();
        WorldImageReader reader = format.getReader(setUpPNG());
        GridCoverage2D cov = reader.read(null);
        assertNotNull(cov);
        
        // Get GeoPackage GridCoverageWriter
        GeoPackageGridCoverageWriter gpkgWriter = (GeoPackageGridCoverageWriter) gpkgFormat.getWriter(geopkg.getFile());
        
        // Get parameters for table name and GridFormat
        String rasterTableName = "raster_png";
        ParameterValue<String> tableName = GeoPackageGridFormat.TABLE_NAME.createValue();
        tableName.setValue(rasterTableName);
        
        ParameterValue<AbstractGridFormat> gridFormat = GeoPackageGridFormat.GRID_FORMAT.createValue();
        gridFormat.setValue(format);
        
        // Write Coverage to GeoPackage
        gpkgWriter.write(cov, new GeneralParameterValue[]{
            tableName, gridFormat
        });

        // Get RasterEntry and check for coverage
        RasterEntry entry = geopkg.raster(rasterTableName);
        GridCoverage2D c;
        GridCoverageReader r = geopkg.reader(entry, format);
        c = (GridCoverage2D) r.read(null);
        assertNotNull(c);
        
        // Get GeoPackage GridCoverageReader
        GeoPackageGridCoverageReader gpkgReader = (GeoPackageGridCoverageReader) gpkgFormat.getReader(geopkg.getFile());
        
        // Read coverage from raster table
        c = gpkgReader.read(rasterTableName, new GeneralParameterValue[]{
            gridFormat
        });
        assertNotNull(c);
    }
    
    URL setUpGeoTiff() throws IOException {
        File d = File.createTempFile("world", "tiff", new File("target"));
        d.delete();
        d.mkdirs();

        FileUtils.copyURLToFile(TestData.url("geotiff/world.tiff"), new File(d, "world.tiff"));
        return DataUtilities.fileToURL(new File(d, "world.tiff")); 
    }

    URL setUpPNG() throws IOException {
        File d = File.createTempFile("Pk50095", "png", new File("target"));
        d.delete();
        d.mkdirs();

        FileUtils.copyURLToFile(TestData.url(geopkg, "Pk50095.png"), new File(d, "Pk50095.png"));
        FileUtils.copyURLToFile(TestData.url(geopkg, "Pk50095.pgw"), new File(d, "Pk50095.pgw"));
        return DataUtilities.fileToURL(new File(d, "Pk50095.png")); 
    }
    
}
