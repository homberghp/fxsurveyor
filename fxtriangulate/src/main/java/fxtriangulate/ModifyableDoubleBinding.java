package fxtriangulate;

import java.util.HashSet;
import java.util.Set;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableDoubleValue;

/**
 * A double binding that allows modification of its dependencies.
 *
 * @author Pieter van den Hombergh {@code Pieter.van.den.Hombergh@gmail.com}
 */
class ModifyableDoubleBinding extends DoubleBinding {

    Set<ObservableDoubleValue> values = new HashSet<>();

    @Override
    protected double computeValue() {
        double result = values.stream()
                .mapToDouble( ObservableDoubleValue::get ).sum();
        return result;
    }

    void add( ObservableDoubleValue v ) {
        unbind( values.toArray( ObservableDoubleValue[]::new ) );
        values.add( v );
        bind( values.toArray( ObservableDoubleValue[]::new ) );
        invalidate();
    }

    void remove( ObservableDoubleValue v ) {
        unbind( values.toArray( ObservableDoubleValue[]::new ) );
        values.remove( v );
        bind( values.toArray( ObservableDoubleValue[]::new ) );
        invalidate();
    }
}
