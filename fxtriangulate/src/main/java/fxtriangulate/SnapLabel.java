package fxtriangulate;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * SnapTarget as Label.
 * @author "Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}"
 */
public class SnapLabel extends Label implements SnapTarget {

    public SnapLabel() {
    }

    public SnapLabel( String arg0 ) {
        super( arg0 );
    }

    public SnapLabel( String string, Node node ) {
        super( string, node );
    }

    @Override
    public String toString() {
        return "SnapLabel{" + getText() + " at(" + getLayoutX()
                + "," + getLayoutY()
                + ")" + '}';
    }

    @Override
    public SnapLabel makeHot( boolean hot ) {
        if ( hot ) {
            getStyleClass().add( "hot" );
        } else {
            getStyleClass().remove( "hot" );
        }
        return this;
    }

}
