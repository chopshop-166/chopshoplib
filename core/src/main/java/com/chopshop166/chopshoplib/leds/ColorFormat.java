package com.chopshop166.chopshoplib.leds;

import edu.wpi.first.wpilibj.util.Color;

/** Formats colors into a wire format */
public enum ColorFormat {
    /** Formats LED into red green blue format */
    RGB {
        @Override
        public Color convert(final Color c) {
            return c;
        }
    },
    /** Formats LED into green red blue format */
    GRB {
        @Override
        public Color convert(final Color c) {
            return new Color(c.green, c.red, c.blue);
        }
    };

    /**
     * Convert color to the wire format
     * 
     * @param c The imput color
     * @return The converted color
     */
    public abstract Color convert(Color c);
}
