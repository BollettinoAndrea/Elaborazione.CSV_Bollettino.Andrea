package elaborazioneCSV;

package elaborazioneCSV;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Random;

public class GestoreCSV {
    private File fileAnalizzare;

    public GestoreCSV(File fileAnalizzare){
        this.fileAnalizzare=fileAnalizzare;
    }

    public boolean controllaIncrementoCampi() throws RuntimeException{
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            String [] recordControllo=reader.readLine().split(";");
            return recordControllo.length>10;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void aggiungiCampi() throws RuntimeException{
        File vecchioFile = this.fileAnalizzare;
        File nuovoFile = new File("Elaborazione.CSV_Bollettino.Andrea/src/bollettino.csv");

        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            if (controllaIncrementoCampi())
                throw new RuntimeException("Dati già elaborati");
            else {
                PrintWriter writerNuovo = new PrintWriter(new FileWriter(nuovoFile));

                boolean isIntestazione=true;
                String next;
                while ((next = reader.readLine()) != null) {
                    if (isIntestazione){
                        writerNuovo.println(next + "; " +"mio_parametro; " + "cancellazione_logica");
                        isIntestazione= false;
                    }else
                        writerNuovo.println(next + ";" + (new Random().nextInt(11) + 10) + ";" + true);
                }
                writerNuovo.flush();
                writerNuovo.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!vecchioFile.delete()) {
            throw new RuntimeException("Errore nella cancellazione del file originale");
        }
        if (!nuovoFile.renameTo(vecchioFile)) {
            throw new RuntimeException("Errore nel rinominare il nuovo file come quello originale");
        }
    }

    public int contaCampi() throws RuntimeException{
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            String [] recordControllo=reader.readLine().split(";");
            return recordControllo.length;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int maxRecord() throws RuntimeException{
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            reader.readLine(); 

            int maxRecord=0;
            String next;
            while ((next = reader.readLine()) != null){
                if (next.length()>maxRecord)
                    maxRecord=next.length();
            }
            return maxRecord;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int lunghezzaMaxCampo(int numCampo) throws ArrayIndexOutOfBoundsException{
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            int maxCampo=0;
            String [] recordControllo=reader.readLine().split(";"); //Elimino l'intestazione dal conteggio e conto il numero di campi
            if (recordControllo.length<numCampo || numCampo<0)
                throw new ArrayIndexOutOfBoundsException("Campo non trovabile");
            else{
                String next;
                while ((next = reader.readLine()) != null){
                    String [] recordAttuale=next.split(";");
                    if (maxCampo<recordAttuale[numCampo].length())
                        maxCampo=recordAttuale[numCampo].length();
                }
                return maxCampo;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void spaziamentoFisso() throws RuntimeException {
        File vecchioFile = this.fileAnalizzare;
        File nuovoFile = new File("Elaborazione.CSV_Bollettino.Andrea/src/bollettino.csv");
        int[] dimMaxCampi;

        try (BufferedReader reader = new BufferedReader(new FileReader(vecchioFile))) {
            String primaRiga = reader.readLine();
            if (primaRiga == null) return;
            String[] recordControllo = primaRiga.split(";");

            dimMaxCampi = new int[recordControllo.length];
            for (int i = 0; i < dimMaxCampi.length; i++) {
                dimMaxCampi[i] = lunghezzaMaxCampo(i);
            }
        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura del file originale", e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(vecchioFile));
             PrintWriter writerNuovo = new PrintWriter(new FileWriter(nuovoFile))) {

            String next;
            while ((next = reader.readLine()) != null) {
                String[] recordAttuale = next.split(";");
                for (int i = 0; i < recordAttuale.length; i++) {
                    String campo = recordAttuale[i];

                    while (campo.length() < dimMaxCampi[i]) {
                        campo += " ";
                    }

                    writerNuovo.print(campo);
                    if (i < recordAttuale.length - 1) //No ; alla fine
                        writerNuovo.print(";");
                }
                writerNuovo.println();
            }

        } catch (IOException e) {
            throw new RuntimeException("Errore durante la scrittura del nuovo file", e);
        }

        if (!vecchioFile.delete()) {
            throw new RuntimeException("Errore nella cancellazione del file originale");
        }
        if (!nuovoFile.renameTo(vecchioFile)) {
            throw new RuntimeException("Errore nel rinominare il nuovo file come quello originale");
        }
    }
    
    
    public void aggiungiRecord (Record record) throws RuntimeException{
        try (PrintWriter writerNuovo = new PrintWriter(new FileWriter(this.fileAnalizzare, true))){
            if (controllaIncrementoCampi() && !(record instanceof RecordAggiunte))
                throw new RuntimeException("Record inserito non corretto");
            else {
                writerNuovo.println(record.toString());
                writerNuovo.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public String filtraCampi(int campo1, int campo2, int campo3) throws RuntimeException{
        String datiDaMostrare="";
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            String [] recordControllo=reader.readLine().split(";");
            if (campo1 < 0 || campo2 < 0 || campo3 < 0 || campo1 > recordControllo.length-1|| campo2 > recordControllo.length-1 || campo3 > recordControllo.length-1 )
                throw new RuntimeException("Campo non presente");
            else {
                datiDaMostrare+=datiDaMostrare+recordControllo[campo1]+";"+recordControllo[campo2]+";"+recordControllo[campo3];
                String next;
                while ((next=reader.readLine())!=null){
                    String [] recordAttuale=next.split(";");
                    datiDaMostrare=datiDaMostrare+recordAttuale[campo1]+";"+recordAttuale[campo2]+";"+recordAttuale[campo3]+"\n";
                }
                return datiDaMostrare;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
   
    
    public int cercaRecord(String nomeItalianoRifugio) throws RuntimeException{
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            int numeroRecord=-1;
            String next;
            while ((next=reader.readLine())!=null){
                numeroRecord++;
                String [] recordAttuale=next.split(";");
                if (recordAttuale[2].trim().equalsIgnoreCase(nomeItalianoRifugio.trim()))
                    return numeroRecord;
            }
            return -1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean modificaRecord(Record recordVecchio, Record recordNuovo) throws RuntimeException{
        File vecchioFile = this.fileAnalizzare;
        File nuovoFile = new File("Elaborazione.CSV_Bollettino.Andrea/src/bollettino.csv");

        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            if (controllaIncrementoCampi() && !(recordNuovo instanceof RecordAggiunte)  && !(recordVecchio instanceof RecordAggiunte) )
                throw new RuntimeException("Record inseriti non corretti in relazione al csv");
            else {
                int pos=cercaRecord(recordVecchio.getNome());
                if (pos==-1) {
                    throw new RuntimeException("Record non trovato");
                }  else{
                    PrintWriter writerNuovo = new PrintWriter(new FileWriter(nuovoFile));

                    int recordAnalizzati=-1;
                    String next; 
                    while ((next = reader.readLine()) != null) {
                        if (recordAnalizzati==pos) {
                            writerNuovo.println(recordNuovo.toString());
                        }else{
                            writerNuovo.println(next);
                        }
                        recordAnalizzati++;
                    }
                    writerNuovo.flush();
                    writerNuovo.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!vecchioFile.delete()) {
            throw new RuntimeException("Errore nella cancellazione del file originale");
        }
        if (!nuovoFile.renameTo(vecchioFile)) {
            throw new RuntimeException("Errore nel rinominare il nuovo file come quello originale");
        }
        return true;
    }

    public boolean cancellaRecord(String recordDaCancellare) throws RuntimeException{
        File vecchioFile = this.fileAnalizzare;
        File nuovoFile = new File("Elaborazione.CSV_Bollettino.Andrea/src/bollettino.csv");
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            if (!controllaIncrementoCampi())
                throw new RuntimeException("Cancellazione impossibile, ricordarsi di inserire i nuovi campi.");
            else {
                int pos=cercaRecord(recordDaCancellare);
                if (pos==-1) {
                    throw new RuntimeException("Record non trovato");
                }  else{
                    PrintWriter writerNuovo = new PrintWriter(new FileWriter(nuovoFile));

                    int recordAnalizzati=-1;
                    String next;
                    while ((next = reader.readLine()) != null) {
                        if (recordAnalizzati==pos) {
                            String [] record=next.split(";");
                            record[record.length-1]="false";
                            for (String campo : record){
                                if (!campo.equalsIgnoreCase("false"))
                                    writerNuovo.print(campo+";");
                                else
                                    writerNuovo.print(campo);
                            }
                            writerNuovo.println();
                        }else{
                            writerNuovo.println(next);
                        }
                        recordAnalizzati++;
                    }
                    writerNuovo.flush();
                    writerNuovo.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!vecchioFile.delete()) {
            throw new RuntimeException("Errore nella cancellazione del file originale");
        }
        if (!nuovoFile.renameTo(vecchioFile)) {
            throw new RuntimeException("Errore nel rinominare il nuovo file come quello originale");
        }
        return true;
    }

    public String vediCampi() throws RuntimeException{
        String datiDaMostrare="";
        try (BufferedReader reader=new BufferedReader(new FileReader(this.fileAnalizzare))){
            if (controllaIncrementoCampi()) { 
                String next;
                boolean isIntestazione=true;
                while ((next=reader.readLine())!=null){
                    if (isIntestazione) {
                        datiDaMostrare = datiDaMostrare + next + "\n";
                        isIntestazione=false;
                    } else {
                        String[] recordAttuale = next.split(";");
                        if (Boolean.parseBoolean(recordAttuale[recordAttuale.length-1].trim()))
                            datiDaMostrare = datiDaMostrare + next + "\n";
                    }
                }
                return datiDaMostrare;
            } else { 
                String next;
                while ((next=reader.readLine())!=null) datiDaMostrare=datiDaMostrare+next+"\n";
                return datiDaMostrare;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
