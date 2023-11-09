package de.ssherlock.global.transport;

import java.awt.*;
import java.util.List;

public record SystemSettings(
        String emailRegex,
        String primaryColorHex,
        String secondaryColorHex,
        String systemName,
        Image logo,
        List<String> faculties
){
}
