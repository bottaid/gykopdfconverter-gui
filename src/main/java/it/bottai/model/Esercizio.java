package it.bottai.model;

import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.ArrayList;

public interface Esercizio {
    public void estraiRigheTabella();
    public void scriviRigheTabella(ArrayList<String> righeTabella, String outputDir, String filename) throws IOException;
    public void parsaRigheTabella();
    public StringProperty primaColonnaProperty();
    public StringProperty secondaColonnaProperty();
}
