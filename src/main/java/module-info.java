module dk.easv.extenedcalculatorwoarchitecture {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens dk.easv.extenedcalculatorwoarchitecture to javafx.fxml;
    exports dk.easv.extenedcalculatorwoarchitecture;
}