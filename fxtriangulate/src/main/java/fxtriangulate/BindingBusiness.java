package fxtriangulate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/**
 *
 * @author Pieter van den Hombergh {@code Pieter.van.den.Hombergh@gmail.com}
 */
class BindingBusiness {

    private Circle redCircle, greenCircle, blueCircle;

    final Map<String, Line> lines = new HashMap<>();

   final void setLinesAndCircles( Circle red, Circle green, Circle blue, Line redLine, Line greenLine, Line blueLine ) {
        this.redCircle = red;
        this.greenCircle = green;
        this.blueCircle = blue;
        connect(redLine, greenCircle, blueCircle);
        connect(greenLine, blueCircle, redCircle);
        connect(blueLine, redCircle, greenCircle);
    }

    /**
     * Connect two circles with a line.
     *
     * @param line to connect
     * @param c1   first
     * @param c2   second circle
     *
     * @return the connecting line.
     */
    final void connect( Line line, Circle c1, Circle c2 ) {
        lines.put( line.getId(), line );

        line.startXProperty().bind( c1.centerXProperty() );
        line.startYProperty().bind( c1.centerYProperty() );

        line.endXProperty().bind( c2.centerXProperty() );
        line.endYProperty().bind( c2.centerYProperty() );
    }

    /**
     * Helper to get the center-y of a line.
     *
     * @param lineA the line
     *
     * @return the center-y coordinate
     */
    DoubleBinding lineCenterY( Line lineA ) {
        DoubleBinding line1CenterYBinding = midpointBinding( lineA.
                startYProperty(), lineA.endYProperty() );
        return line1CenterYBinding;
    }

    /**
     * etBindings().areaBinding()
     * .asString( "%6.2f" ) );
     * Helper to get the center-x of a line.
     *
     * @param lineA the line
     *
     * @return the center-y coordinate
     */
    DoubleBinding lineCenterX( Line lineA ) {
        DoubleBinding line1CenterXBinding = midpointBinding( lineA.
                startXProperty(), lineA.endXProperty() );
        return line1CenterXBinding;
    }

    /**
     * Center a label vertically around the given binding.
     *
     * @param aLab                label to center
     * @param line1CenterYBinding to bind
     */
    Label centerLabelV( Label aLab, DoubleBinding line1CenterYBinding ) {
        aLab.layoutYProperty().bind( line1CenterYBinding.subtract( aLab.
                heightProperty().divide( 2 ) ) );
        return aLab;
    }

    /**
     * Center a label horizontally around the given binding.
     *
     * @param aLab                label to center
     * @param line1CenterXBinding to bind
     */
    Label centerLabelH( Label aLab, DoubleBinding line1CenterXBinding ) {
        aLab.layoutXProperty().bind( line1CenterXBinding.subtract( aLab.
                widthProperty().divide( 2 ) ) );
        return aLab;
    }

    /**
     * Cache for area binding.
     */
    private DoubleBinding areaBinding = null;

    /**
     * Create a binding to compute the area with a triangle determined by side
     * lengths a, b and c. This method consults the cache and has the value computed 
     *
     * @return the area binding
     */
    final DoubleBinding areaBinding() {
        if ( areaBinding == null ) {
            areaBinding = areaBinding( distanceBinding( redCircle, greenCircle ),
                    distanceBinding( greenCircle, blueCircle ),
                    distanceBinding( blueCircle, redCircle ) );
        }
        return areaBinding;
    }

    /**
     * Create the area binding using herons formula.
     *
     * @see https://en.wikipedia.org/wiki/Triangle}
     * @param al first, 
     * @param bl second, 
     * @param cl and third side length as binding.
     *
     * @return the binding
     */
    final DoubleBinding areaBinding( DoubleBinding al, DoubleBinding bl,
            DoubleBinding cl ) {
        // bind values to compute area
        //Start Solution::replacewith:://TODO create the binding
        DoubleBinding result = new DoubleBinding() {
            {
                bind( al, bl, cl );
            }

            @Override
            protected double computeValue() {
                // using herons formula, see https://en.wikipedia.org/wiki/Triangle
                double a = al.get();
                double b = bl.get();
                double c = cl.get();
                double s = ( a + b + c ) / 2;
                double area = Math.sqrt( s * ( s - a ) * ( s - b ) * ( s - c ) );
                return area;
            }
        };
        return result;
        //End Solution::replacewith::return null;
    }

    /**
     * We want to map lines to their length bindings, to avoid recomputation in multiple places.
     */
    private final Map<Line, DoubleBinding> lengthBindings = new HashMap<>();

    final ModifyableDoubleBinding activeLengthBinding = new ModifyableDoubleBinding();

    /**
     * Return the active line binding. The binding is final, but its dependencies can change over time.
     * @return the binding.
     */
    DoubleBinding activeLineLength() {
        return activeLengthBinding;
    }

    void activateLine( boolean active, String lineName ) {
        Line l = lines.get( lineName );
        if ( active ) {
            activeLengthBinding.add( lengthBinding( l ) );
            l.setStrokeWidth( 5.0);
        } else {
            activeLengthBinding.remove( lengthBinding( l ) );
            l.setStrokeWidth( 3.0);
        }
    }

    /**
     * Create a binding for the length of as line.
     *
     * @param aLine the line
     *
     * @return a double binding that computes the line length.
     */
    DoubleBinding lengthBinding( Line aLine ) {
        return lengthBindings
                .computeIfAbsent( aLine,
                        l -> lengthBinding(
                                l.startXProperty(),
                                l.startYProperty(),
                                l.endXProperty(),
                                l.endYProperty()
                        )
                );
    }

    DoubleBinding lengthBinding( String name ) {
        return lengthBinding( lines.get( name ) );
    }

    DoubleBinding distanceBinding( Circle a, Circle b ) {
        return lengthBinding( a.centerXProperty(), b.centerXProperty(), a
                .centerYProperty(), b.centerYProperty() );
    }

    /**
     * Compute the distance between the tow endpoints of a line segment using
     * Pythagoras' formula.
     *
     * @param startX first x
     * @param startY first y
     * @param endX   second x
     * @param endY   second y
     *
     * @return the binding
     */
    DoubleBinding lengthBinding(
            DoubleProperty startX,
            DoubleProperty startY,
            DoubleProperty endX,
            DoubleProperty endY
    ) {
        //Start Solution::replacewith:://TODO create the binding
        DoubleBinding result = new DoubleBinding() {
            {
                bind( startX, startY, endX, endY );
            }

            @Override
            protected double computeValue() {
                double x = endX.get() - startX.get();
                double y = endY.get() - startY.get();
                double result = Math.sqrt( x * x + y * y );
                return result;
            }

        };
        return result;
        //End Solution::replacewith::return null;
    }

    /**
     * Create a binding binding that computes the average of the given
     * properties. This can be used to compute x or y of the midpoint of a line
     * or of the center of gravity of a triangle.
     *
     * @param props to average
     *
     * @return the average value
     */
    DoubleBinding midpointBinding( final DoubleProperty... props ) {
        DoubleBinding lineCenterXBinding;
        lineCenterXBinding = new DoubleBinding() {

            {
                bind( props );
            }

            @Override
            protected double computeValue() {
                return Arrays.stream( props ).mapToDouble(
                        DoubleProperty::doubleValue ).average().getAsDouble();
            }

        };
        return lineCenterXBinding;
    }

    void connect( Line lineA, Label aLab ) {
        centerLabelH( aLab, lineCenterX( lineA ) );
        centerLabelV( aLab, lineCenterY( lineA ) );
        aLab.textProperty().bind( lengthBinding( lineA ).asString( aLab
                .getText() + ":%6.2f" ) );
    }

    void connect( Circle centerCircle, Label centerLabel ) {

        DoubleBinding cogXBinding
                = midpointBinding( redCircle.centerXProperty(),
                        greenCircle.centerXProperty(),
                        blueCircle.centerXProperty()
                );
        DoubleBinding cogYBinding
                = midpointBinding( redCircle.centerYProperty(),
                        greenCircle.centerYProperty(), blueCircle
                        .centerYProperty()
                );
        centerCircle.centerXProperty().bind( cogXBinding );
        centerCircle.centerYProperty().bind( cogYBinding.add( 10.0 ) );
        centerLabelH( centerLabel, cogXBinding );
        centerLabelV( centerLabel, cogYBinding );

        centerLabel.textProperty()
                .bind( areaBinding().asString( "Area: %6.2f" ) );
    }
}
