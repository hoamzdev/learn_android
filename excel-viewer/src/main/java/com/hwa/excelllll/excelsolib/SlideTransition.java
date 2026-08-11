package com.hwa.excelllll.excelsolib;

/** Parsed representation of the transition metadata returned by the native engine. */
public class SlideTransition {
    public String rawValue;
    public String type = "";
    public String direction = "";
    public String speed = "";
    public int durationMilliseconds;

    public SlideTransition(String transitionDescription) {
        this.rawValue = transitionDescription;
        if (transitionDescription == null) {
            return;
        }

        for (String property : transitionDescription.split(";")) {
            String[] keyValue = property.trim().split(":", 2);
            if (keyValue.length != 2) {
                continue;
            }

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();
            if ("type".equals(key)) {
                this.type = value;
            } else if ("direction".equals(key)) {
                this.direction = value;
            } else if ("speed".equals(key)) {
                this.speed = value;
                this.durationMilliseconds = parseDuration(value);
            }
        }
    }

    private int parseDuration(String speed) {
        if ("veryslow".equals(speed)) return 5000;
        if ("slow".equals(speed)) return 1000;
        if ("medium".equals(speed)) return 750;
        if ("veryfast".equals(speed)) return 300;
        if ("fastest".equals(speed)) return 75;
        return 0;
    }
}
