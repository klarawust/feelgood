package feelgood;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FeelGoodController {
    //overføres data fra fxml til variabler vi kan bruke i andre klasser
    //starte metoder i de andre klassene
    //som en main-metode
    // Trenger ikke konstruktør // Kobler opp FXML med controlleren 

    private Summary summary;
    private FileReadWrite writeHandler = new FileDealer(); 
    private Day day;
    private ArrayList<Day> allDays = new ArrayList<>(); 

    private FileDealer filedealer; 
   


    @FXML
    public TextField glassVann, komplement, timerSovn, verdigNavn, matteSum, brukernavn;

    @FXML
    private Text feedback;
    
    
    
    public void initialize() {
        // Dette er på en måte konstruktøren vår -- kjører når appen starter opp
        summary = new Summary();

    }

    private String getFilename(){
        String filename = this.brukernavn.getText();
        return filename; 
    }

    

    @FXML
    private void lagreSvar() {
        //if brukernavnexists typ, kjør funksjonen
        System.out.println("lagreSvar kjører");
        try{
            Day newDay = new Day(Double.parseDouble(glassVann.getText()), komplement.getText(), Double.parseDouble(timerSovn.getText()), verdigNavn.getText(), Double.parseDouble(matteSum.getText()));
            summary.add(newDay); //henter info fra tekstfelt og sender til add().
            allDays.add(newDay);
            writeHandler.writeFile(getFilename(), allDays);
        } catch(IllegalArgumentException e){
            Alert feilmelding = new Alert(AlertType.ERROR);
            //feilmelding.setContentText(e.getMessage());
            feilmelding.setTitle("Feil");
            feilmelding.setHeaderText(e.getMessage());
            feilmelding.show();
        }

        glassVann.clear(); komplement.clear(); timerSovn.clear(); verdigNavn.clear(); matteSum.clear();
    }

    @FXML
    void handleRead(){
        allDays = writeHandler.readFile(getFilename()); 

    }

    

    @FXML
    private void oppsummering(){
        System.out.println("oppsummering kjører");
        System.out.println(summary.calculations());
        System.out.println(summary.motivationalMessage());
        giResultat();
        summary = new Summary();
    }

    private void giResultat(){
        // Alert oppsummering = new Alert(AlertType.INFORMATION);
        // oppsummering.setTitle("Du er best");
        // oppsummering.setHeaderText(null);
        // oppsummering.show();
        // oppsummering.setContentText(summary.motivationalMessage());
        feedback.setText(summary.motivationalMessage());
        feedback.setFill(Color.BLUE);
    }
}
