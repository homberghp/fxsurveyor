package surveyor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToDoubleFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pieter van den Hombergh {@code Pieter.van.den.Hombergh@gmail.com}
 */
public class SurveyorBusiness {

    String resultFile;
    String pointsFile;
    boolean unSaved;
    ObservableList<Measurement> measurements = FXCollections
            .observableArrayList();

    public SurveyorBusiness() {
    }

    void loadFile( File f ) {

    }

    void saveFile( File f ) {

    }

    boolean hasUnsavedWork() {
        return unSaved;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    void acceptFile( File selectedFile ) {
        if ( selectedFile == null ) {
            return;
        }
        try {
            double[] result = Files.lines( selectedFile.toPath() )
                    .map( l -> l.split( "\\s*,\\s*" ) )
                    .flatMap( a -> Arrays.stream( a ) )
                    .map( Double::parseDouble )
                    .mapToDouble( Double::doubleValue ).toArray();
        } catch ( IOException ex ) {
            Logger.getLogger( SurveyorBusiness.class.getName() )
                    .log( Level.SEVERE, null, ex );
        }
    }

    void accept( Measurement measurement ) {
        this.measurements.add( 0, measurement );
    }

    DoubleBinding totalAreaBinding() {
        return totalBinding( measurements, Measurement::getArea );
    }

    DoubleBinding borederLengthBinding() {
        return totalBinding( measurements, Measurement::getLength );
    }

    DoubleBinding totalBinding( ObservableList<Measurement> m, ToDoubleFunction<Measurement> fun ) {
        return new DoubleBinding() {
            {
                bind( m );
            }

            @Override
            protected double computeValue() {
                return m.stream()
                        .mapToDouble( fun )
                        .sum();
            }
        };
    }

    void clear() {
        this.measurements.clear();

    }
}
