package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

public class mainController {

    @FXML private ToggleButton signInToggle;
    @FXML private Button signIn;

    @FXML private TextField profName;
    @FXML private TextField profMail;
    @FXML private TextField profPass;
    @FXML private TextField profPass2;
    @FXML private TextField profCourse;

    @FXML private TextField studName;
    @FXML private TextField studMail;
    @FXML private TextField studPass;
    @FXML private TextField studPass2;

    @FXML private TextField signMail;
    @FXML private TextField signPass;

    //those that signed in succesfully
    public static Student stud;
    public static Professor prof;


    private static Boolean b=true;

    public void changeToggleText(ActionEvent e){
//        System.out.println(signInToggle.getText());
        if(b){
            signInToggle.setText("I am a Teacher");
            b=false;
        }else {
            signInToggle.setText("I am a Student");
            b=true;
        }

    }

    public void onClickSignIn(ActionEvent e){

        //true == student
        if(b){
            //search in the student table
            this.stud=SQLiteJDBC.findStud(addThingies(signMail.getText()),addThingies(DigestUtils.sha1Hex(signPass.getText())));

            if(stud.getStudId().equals(-1)){
                showAlert(Alert.AlertType.WARNING,"Student not found","Please fill the fields again!");
                blankSign();
                return;
            }

            try{
                //new stage
                System.out.println("going to students dashboard\n");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("stud.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Student Home");
                stage.setScene(new Scene(root1)); stage.show();
//                new studController(this.stud);
                //close current stage
                Stage finale = (Stage) signIn.getScene().getWindow();
                finale.close();
            }catch(Exception es){
                es.printStackTrace();
            }

        }else{
            //search in the prof table
            this.prof=SQLiteJDBC.findProf(addThingies(signMail.getText()),addThingies(DigestUtils.sha1Hex(signPass.getText())));
            if(prof.getId().equals(-1)){
                showAlert(Alert.AlertType.WARNING,"Professor not found","Please fill the fields again!");
                blankSign();
                return;
            }

            try{
                //new stage
                System.out.println("going to professors dashboard\n");
                System.out.println(prof.getCourse());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prof.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Professor Home");
                stage.setScene(new Scene(root1)); stage.show();
                //close current stage
                Stage finale = (Stage) signIn.getScene().getWindow();
                finale.close();
            }catch(Exception ep){
                ep.printStackTrace();
            }

        }
    }

    public void onRegisterProf(ActionEvent e){
        //runs insertProf
        String name=addThingies(profName.getText());
        String mail=addThingies(profMail.getText());
        String pass=profPass.getText();
        String pass2=profPass2.getText();
        String course=addThingies(profCourse.getText());


        //are all the fields filled?
        if(profName.getText().equals("") || profMail.getText().equals("") || profPass.getText().equals("") || profCourse.getText().equals("")){
            showAlert(Alert.AlertType.WARNING,"You need to fill in all the fields!","Try Again!");

            profPass.setText("");
            profPass2.setText("");
            return;
        }

        if(!pass.equals(pass2)){
            showAlert(Alert.AlertType.WARNING,"Passwords don't match!","Try Again!");


            profPass.setText("");
            profPass2.setText("");
            return;
        }

        //προσθέτουμε τα quotes ελέγχουμε γia sqli και περνάμε απο συνάρτηση σύνοψης.
        pass = addThingies(DigestUtils.sha1Hex(pass));


        SQLiteJDBC.insertProf(name,mail,pass,course);

        blankProf();

    }

    public static String addThingies(String s){
        return "'"+mysql_real_escape_string(s)+"'";
    }

    //SQLi protection
    public static String mysql_real_escape_string(String str)
    {
        if (str == null) {
            return null;
        }

        if (str.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/? ]","").length() < 1) {
            return str;
        }

        String clean_string = str;
        clean_string = clean_string.replaceAll("\\\\", "\\\\\\\\");
        clean_string = clean_string.replaceAll("\\n","\\\\n");
        clean_string = clean_string.replaceAll("\\r", "\\\\r");
        clean_string = clean_string.replaceAll("\\t", "\\\\t");
        clean_string = clean_string.replaceAll("\\00", "\\\\0");
        clean_string = clean_string.replaceAll("'", "\\\\'");
        clean_string = clean_string.replaceAll("\\\"", "\\\\\"");

        if (clean_string.replaceAll("[a-zA-Z0-9_!@#$%^&*()-=+~.;:,\\Q[\\E\\Q]\\E<>{}\\/?\\\\\"' ]"
                ,"").length() < 1)
        {
            return clean_string;
        }
        return str;
    }

    public void blankProf(){
        profPass.setText("");
        profPass2.setText("");
        profName.setText("");
        profCourse.setText("");
        profMail.setText("");
    }

    public void blankStud(){
        studPass.setText("");
        studPass2.setText("");
        studName.setText("");
        studMail.setText("");
    }

    public void blankSign(){
        signMail.setText("");
        signPass.setText("");
    }

    public void onRegisterStud(ActionEvent e){
        //runs insertStud
        String name=addThingies(studName.getText());
        String mail=addThingies(studMail.getText());
        String pass=studPass.getText();
        String pass2=studPass2.getText();

        // are all the fields filled?
        if(studName.getText().equals("") || studMail.getText().equals("") || studPass.getText().equals("") ){

            showAlert(Alert.AlertType.WARNING,"You need to fill in all the fields!","Try Again!");

            profPass.setText("");
            profPass2.setText("");
            return;
        }

        //is the password correct?
        if(!pass.equals(pass2)){
            showAlert(Alert.AlertType.WARNING,"Passwords don't match!","Try Again!");

            studPass.setText("");
            studPass2.setText("");
            return;
        }

        pass = addThingies(DigestUtils.sha1Hex(pass));


        //in here we check if the email already exists
        SQLiteJDBC.insertStud(name,mail,pass);


        blankStud();

    }

    public static void showAlert(Alert.AlertType a,String header, String body){
        Alert alert = new Alert(a);
        if(a == Alert.AlertType.WARNING){
            alert.setTitle("Attention!");

        }else if(a == Alert.AlertType.INFORMATION){
            alert.setTitle("Success");
        }
        alert.setHeaderText(header);
        alert.setContentText(body);
        alert.showAndWait();
    }

}
