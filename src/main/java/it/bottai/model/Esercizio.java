package it.bottai.model;

import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.ArrayList;

public interface Esercizio {
    public void estraiRigheTabella();
    public void scriviRigheTabella() throws IOException;
    public void parsaRigheTabella();
}
