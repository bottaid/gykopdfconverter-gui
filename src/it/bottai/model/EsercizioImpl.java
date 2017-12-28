package it.bottai.model;

import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.util.ArrayList;

public class EsercizioImpl implements Esercizio {

    private String nomeFile;
    private ArrayList<String> righeTabella;
    private StringProperty primaColonna;
    private StringProperty secondaColonna;

    public EsercizioImpl(String nomeFile){
        this.nomeFile = nomeFile;
    }

    public String getNomeFile(){
        return nomeFile;
    }

    public void setRigheTabella(){
        this.righeTabella = righeTabella;
    }

    public String getPrimaColonna() {
        return primaColonna.get();
    }

    public void setPrimaColonna(String primaColonna) {
        this.primaColonna.set(primaColonna);
    }

    public StringProperty primaColonnaProperty() {
        return primaColonna;
    }

    public String getSecondaColonna() {
        return secondaColonna.get();
    }

    public void setSecondaColonna(String secondaColonna) {
        this.secondaColonna.set(secondaColonna);
    }

    public StringProperty secondaColonnaProperty() {
        return secondaColonna;
    }

    @Override
    public void estraiRigheTabella(ArrayList<String> lista) {

    }

    @Override
    public void scriviRigheTabella(ArrayList<String> righeTabella, String outputDir, String filename) throws IOException {

    }

    @Override
    public void parsaRigheTabella(ArrayList<String> righeTabella) {

    }
}
