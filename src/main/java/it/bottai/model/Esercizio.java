package it.bottai.model;

import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.ArrayList;

public interface Esercizio {
    public ArrayList<String> estraiRigheTabella(ArrayList<String> lista);
    public void scriviRigheTabella(ArrayList<String> righeTabella, String outputDir, String filename) throws IOException;
    public ArrayList<String> parsaRigheTabella(ArrayList<String> righeTabella);
    public StringProperty primaColonnaProperty();
    public StringProperty secondaColonnaProperty();
}
