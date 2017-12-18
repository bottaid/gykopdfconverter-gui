package it.bottai.model;

import java.io.IOException;
import java.util.ArrayList;

public class EsercizioImpl implements Esercizio {

    private String nomeFile;
    private ArrayList<String> righeTabella;

    public EsercizioImpl(String nomeFile){
        this.nomeFile = nomeFile;
    }

    public String getNomeFile(){
        return nomeFile;
    }

    public void setRigheTabella(){
        this.righeTabella = righeTabella;
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
