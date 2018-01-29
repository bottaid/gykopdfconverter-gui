package it.bottai.model;

import javafx.beans.property.StringProperty;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;

public class EsercizioImpl implements Esercizio {

    private String nomeFile;
    private File pdfFile;
    private ArrayList<String> righeTabella;
    private ArrayList<String> righeTabellaParsate;
    private ArrayList<String> parsedPdfRows;
    private StringProperty primaColonna;
    private StringProperty secondaColonna;

    public EsercizioImpl(String nomeFile, File pdfFile){
        this.nomeFile = nomeFile;
        this.pdfFile = pdfFile;
    }

    public String getNomeFile(){
        return nomeFile;
    }

    public void setRigheTabella(ArrayList<String> righeTabella){
        this.righeTabella = righeTabella;
    }

    public void setRigheTabellaParsate(ArrayList<String> righeTabellaParsate) {
        this.righeTabellaParsate = righeTabellaParsate;
    }

    public void setPdfRows(ArrayList<String> parsedPdfRows){
        this.parsedPdfRows = parsedPdfRows;
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
    //Estraggo le righe dalla tabella
    public void estraiRigheTabella() {
        ArrayList<String> righeTabella = new ArrayList<String>();
        Integer contatoreRighe = 0;
        for (String riga : parsedPdfRows){
            String primoElemento = riga.split(" ")[0];
            int numeroRiga = estraiNumeroRiga(primoElemento);
            if (riga.startsWith("# ")){
                righeTabella.add(riga);
            }
            else if ((controllaIntero(primoElemento) && numeroRiga == contatoreRighe+1)){
                righeTabella.add(riga);
                contatoreRighe++;
            }
        }
        setRigheTabella(righeTabella);
    }

    @Override
    //Scrivo le righe estratte sul documento csv
    public void scriviRigheTabella(ArrayList<String> righeTabella, String outputDir, String filename) throws IOException {
        StringBuilder finalPath = new StringBuilder(outputDir + filename.split(".pdf")[0]);
        finalPath.append(".csv");
        FileWriter f = new FileWriter(finalPath.toString());
        BufferedWriter scriviTabella = new BufferedWriter(f);
        for (String riga : righeTabella){
            scriviTabella.write(riga+"\n");
        }
        scriviTabella.flush();
    }

    @Override
    //Memorizzo le righe in un ArrayList nella forma dei documenti di tipo csv
    public void parsaRigheTabella() {
        StringBuilder nuovaRiga;
        ArrayList<String> righeTabellaParsate = new ArrayList<String>();
        for (String riga : righeTabella){
            if(!riga.startsWith("# ")) {
                nuovaRiga = new StringBuilder(riga.replace(" ", ";"));
                nuovaRiga.append(";");
                righeTabellaParsate.add(nuovaRiga.toString());
            }else {
                righeTabellaParsate.add(riga);
            }
        }
        setRigheTabellaParsate(righeTabellaParsate);
    }

    //Controlla se il primo elemento è un intero
    public boolean controllaIntero(String primoElemento){
        if(primoElemento == null || primoElemento.trim().isEmpty())
            return false;
        try{
            Integer.parseInt(primoElemento);
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }

    //Restituisce il primo numero della riga
    public int estraiNumeroRiga(String primoElemento){
        int risultato = 0;
        try{
            risultato = Integer.parseInt(primoElemento);
        }catch (NumberFormatException e){

        }
        return risultato;

    }

    private static final String SRC = "./src/main/resources/pdfs/";
    private static final String OUT = "./src/main/resources/csvs/";


    //Questa funzione esegue il parsing dei dati all'interno della tabella
    public void parsePdf() throws IOException {
        try {
            String percorsoFileCompleto = SRC+getNomeFile(); //ADATTARE AL FILE CHE ORA é property DELLA CLASSE
            PdfDocument pdfDoc = new PdfDocument(new PdfReader(percorsoFileCompleto));
            Rectangle rect = new Rectangle(36, 750, 523, 56);

            FontFilter fontFilter = new FontFilter(rect);
            FilteredEventListener listener = new FilteredEventListener();
            LocationTextExtractionStrategy extractionStrategy = listener.attachEventListener(new LocationTextExtractionStrategy(), fontFilter);
            PdfCanvasProcessor parser = new PdfCanvasProcessor(extractionStrategy);
            parser.processPageContent(pdfDoc.getPage(2));

            String actualText = extractionStrategy.getResultantText();
            ArrayList<String> righeDocumento = new ArrayList<String>(Arrays.asList(actualText.split("\n")));

            pdfDoc.close();
            setPdfRows(righeDocumento);
        }catch (NullPointerException e){
            System.out.println("File non trovato.");
            throw e;
        }
    }

    //Questa funzione esegue la conversione del documento PDF
    public void processConversion() throws IOException {
        parsePdf();
        estraiRigheTabella();
        parsaRigheTabella();
        scriviRigheTabella(); //quando è ok, mettere parametri giusti se li ha
    }
}
