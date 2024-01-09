package src.driverForEmoney;

import java.util.HashMap;
import java.util.Map;

public class ConvertStringToKeyCodes {
    private static final Map<String, Integer> keyCodeToAndroid = new HashMap<>();

    static {
        keyCodeToAndroid.put("Digit0", 7);
        keyCodeToAndroid.put("Digit1", 8);
        keyCodeToAndroid.put("Digit2", 9);
        keyCodeToAndroid.put("Digit3", 10);
        keyCodeToAndroid.put("Digit4", 11);
        keyCodeToAndroid.put("Digit5", 12);
        keyCodeToAndroid.put("Digit6", 13);
        keyCodeToAndroid.put("Digit7", 14);
        keyCodeToAndroid.put("Digit8", 15);
        keyCodeToAndroid.put("Digit9", 16);
        keyCodeToAndroid.put("Numpad0", 7);
        keyCodeToAndroid.put("Numpad1", 8);
        keyCodeToAndroid.put("Numpad2", 9);
        keyCodeToAndroid.put("Numpad3", 10);
        keyCodeToAndroid.put("Numpad4", 11);
        keyCodeToAndroid.put("Numpad5", 12);
        keyCodeToAndroid.put("Numpad6", 13);
        keyCodeToAndroid.put("Numpad7", 14);
        keyCodeToAndroid.put("Numpad8", 15);
        keyCodeToAndroid.put("Numpad9", 16);
        keyCodeToAndroid.put("Star", 17);
        keyCodeToAndroid.put("Pound", 18);
        keyCodeToAndroid.put("__VolumeUp", 24);
        keyCodeToAndroid.put("__VolumeDown", 25);
        keyCodeToAndroid.put("__Power", 26);
        keyCodeToAndroid.put("KeyA", 29);
        keyCodeToAndroid.put("KeyB", 30);
        keyCodeToAndroid.put("KeyC", 31);
        keyCodeToAndroid.put("KeyD", 32);
        keyCodeToAndroid.put("KeyE", 33);
        keyCodeToAndroid.put("KeyF", 34);
        keyCodeToAndroid.put("KeyG", 35);
        keyCodeToAndroid.put("KeyH", 36);
        keyCodeToAndroid.put("KeyI", 37);
        keyCodeToAndroid.put("KeyJ", 38);
        keyCodeToAndroid.put("KeyK", 39);
        keyCodeToAndroid.put("KeyL", 40);
        keyCodeToAndroid.put("KeyM", 41);
        keyCodeToAndroid.put("KeyN", 42);
        keyCodeToAndroid.put("KeyO", 43);
        keyCodeToAndroid.put("KeyP", 44);
        keyCodeToAndroid.put("KeyQ", 45);
        keyCodeToAndroid.put("KeyR", 46);
        keyCodeToAndroid.put("KeyS", 47);
        keyCodeToAndroid.put("KeyT", 48);
        keyCodeToAndroid.put("KeyU", 49);
        keyCodeToAndroid.put("KeyV", 50);
        keyCodeToAndroid.put("KeyW", 51);
        keyCodeToAndroid.put("KeyX", 52);
        keyCodeToAndroid.put("KeyY", 53);
        keyCodeToAndroid.put("KeyZ", 54);
        keyCodeToAndroid.put("Comma", 55);
        keyCodeToAndroid.put("Period", 56);
        keyCodeToAndroid.put("AltLeft", 57);
        keyCodeToAndroid.put("AltRight", 58);
        keyCodeToAndroid.put("ShiftLeft", 59);
        keyCodeToAndroid.put("ShiftRight", 60);
        keyCodeToAndroid.put("Tab", 61);
        keyCodeToAndroid.put("Space", 62);
        keyCodeToAndroid.put("Enter", 66);
        keyCodeToAndroid.put("Delete", 67);
        keyCodeToAndroid.put("Minus", 69);
        keyCodeToAndroid.put("Equal", 70);
        keyCodeToAndroid.put("BracketLeft", 71);
        keyCodeToAndroid.put("BracketRight", 72);
        keyCodeToAndroid.put("Backslash", 73);
        keyCodeToAndroid.put("Semicolon", 74);
        keyCodeToAndroid.put("Quote", 75);
        keyCodeToAndroid.put("Slash", 76);
    }

    public static int convertStringToKeyCodes(String key) {
        Integer keyCode = keyCodeToAndroid.get(key);
        return keyCode != null ? keyCode : 0;
    }
}
