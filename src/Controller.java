import java.math.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage; 
import java.util.Random;


public class Controller {

    private int[][] nums = 
    {{211, 657},
    {289, 657}, // 1
    {366, 657},
    {450, 657},
    {550, 657},
    {616, 606},
    {564, 539},
    {606, 460},
    {518, 438},
    {438, 478},
    {354, 503},
    {254, 470},
    {267, 400},
    {375, 375},
    {484, 375},
    {605, 341},
    {521, 302},
    {592, 225},
    {493, 243},
    {396, 285},
    {313, 291},
    {224, 261},
    {216, 200}}; // 22

    private String[] signs = {"+", "-", "x", "÷"};

    private String[] difficulties = {"Easy", "Medium", "Hard"};
    private int difficultyLevel;
    
    @FXML
    private Scene scene;

    private Parent root;

    @FXML
    private ImageView luffyHead;

    @FXML
    private Label num1;
    @FXML
    private Label num2;

    @FXML
    private Text sign;

    @FXML
    private TextField answer;

    @FXML
    private Button doneBtn;

    @FXML
    private Button easyBtn;
    @FXML
    private Button mediumBtn;
    @FXML
    private Button hardBtn;
    @FXML
    private Text difficultyText;
    @FXML
    private Button rollBtn;

    @FXML
    private Text diceNum;

    @FXML
    private AnchorPane mathQ;

    @FXML
    private Text finishText;

    @FXML
    private Text generalText;

    @FXML
    private Button exitBtn;

    @FXML
    private ImageView shark;

    private Stage mainWindow; // Define mainWindow as a variable of type Stage
    private double ans;
    private int count;
    private boolean firstTime = true;
    private int sharkCount;

    public void setMainWindow(Stage mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void startGame(ActionEvent event) throws Exception{
        Button difficulty = (Button) event.getSource();
        for (int i = 0; i < difficulties.length; i++){
            if (difficulty.getText().equals(difficulties[i])){
                difficultyLevel = i;
                // 0 = easy, 1 = medium, 2 = hard
            }
        }
        resetGame();
        resetNums(difficultyLevel);
    }

    public void submit(ActionEvent event) throws Exception{ 
        generalText.setText("");
        int q1 = Integer.parseInt(num1.getText());
        int q2 = Integer.parseInt(num2.getText());
        double rightAns = getNum(q1, q2);
        ans = Double.parseDouble(answer.getText());
        if (round(rightAns) == round(ans)){ // fix rounding later
            if (count == 2){
                luffyHead.setLayoutX(nums[10][0]);
                luffyHead.setLayoutY(nums[10][1]);
                answer.setText("");
                answer.setPromptText("Correct");
                generalText.setText("Skipped through a bridge!");
                count = 10;
            } else if (count == 13){
                luffyHead.setLayoutX(nums[19][0]);
                luffyHead.setLayoutY(nums[19][1]);
                answer.setText("");
                answer.setPromptText("Correct");
                generalText.setText("Skipped through a bridge!");
                count = 19;
            } else {
                answer.setText("");
                answer.setPromptText("Correct");
                rollBtn.setLayoutX(75);
                rollBtn.setLayoutY(375);
                diceNum.setLayoutX(96);
                diceNum.setLayoutY(484);
                doneBtn.setVisible(false);
            }
        } else {
            answer.setText("");
            answer.setPromptText("Incorrect");
        }
        if (!firstTime){
            if (count <= sharkCount){
                difficultyText.setVisible(true);
                mathQ.setVisible(false);
                rollBtn.setVisible(false);
                diceNum.setVisible(false);
                luffyHead.setVisible(false);
                finishText.setText("Game over! Play again?");
                finishText.setLayoutX(24);
                finishText.setLayoutY(49);
                easyBtn.setVisible(true);
                mediumBtn.setVisible(true);
                hardBtn.setVisible(true);
            }
            sharkMove(difficultyLevel);
            sharkCount++;
        }
        if (count >= 4 && firstTime){
            sharkAttack();
            firstTime = false;
        }
        resetNums(difficultyLevel);
    }

    public void rollDice(ActionEvent event) throws Exception{
        diceNum.setText(String.valueOf(generateRandomNumber(1, 6)));
        count += Integer.parseInt(diceNum.getText());
        if (count < 22){
            luffyHead.setLayoutX(nums[count][0]);
            luffyHead.setLayoutY(nums[count][1]);
            doneBtn.setVisible(true);
            rollBtn.setLayoutX(-97);
            rollBtn.setLayoutY(352);
        } else {
            mathQ.setVisible(false);
            rollBtn.setVisible(false);
            diceNum.setVisible(false);
            luffyHead.setLayoutX(214);
            luffyHead.setLayoutY(199);
            finishText.setText("You reached the One Piece! Play again?");
            finishText.setLayoutX(24);
            finishText.setLayoutY(49);
            easyBtn.setVisible(true);
            mediumBtn.setVisible(true);
            hardBtn.setVisible(true);
        }
        
    }
    public void sharkAttack(){
        shark.setLayoutX(112);
        shark.setLayoutY(603);
        generalText.setText("Don't let the Marine Corps catch up!");
    }
    public void sharkMove(int i){
        int percent = 0;
        if (i == 0){
            shark.setLayoutX(nums[sharkCount][0]-8);
            shark.setLayoutY(nums[sharkCount][1]-38);
        } else if (i == 1){
            percent = generateRandomNumber(1, 4);
            if (percent < 3){
                sharkCount++;
                shark.setLayoutX(nums[sharkCount][0]-8);
                shark.setLayoutY(nums[sharkCount][1]-38); 
            }
            shark.setLayoutX(nums[sharkCount][0]-8);
            shark.setLayoutY(nums[sharkCount][1]-38); 
        } else if (i == 2){
            sharkCount++;
            shark.setLayoutX(nums[sharkCount][0]-8);
            shark.setLayoutY(nums[sharkCount][1]-38); 
        }
    }

    private int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    private double getNum(int question1, int question2){
        if (sign.getText().equals(signs[0])){
            return question1+question2;
        } else if (sign.getText().equals(signs[1])){
            return question1-question2;
        } else if (sign.getText().equals(signs[2])){
            return question1*question2;
        } else {
            return (double)question1/question2;
        }
    }
    private static double round(double value) { 
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(0, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public void resetNums(int i){
        if (i == 0){
            num1.setText(String.valueOf(generateRandomNumber(1, 9)));
            num2.setText(String.valueOf(generateRandomNumber(1, 9)));
            sign.setText(signs[generateRandomNumber(0, 3)]);
        } else if (i == 1){
            num1.setText(String.valueOf(generateRandomNumber(10, 99)));
            num2.setText(String.valueOf(generateRandomNumber(10, 99)));
            sign.setText(signs[generateRandomNumber(0, 3)]);
        } else if (i == 2){
            num1.setText(String.valueOf(generateRandomNumber(100, 999)));
            num2.setText(String.valueOf(generateRandomNumber(100, 999)));
            sign.setText(signs[generateRandomNumber(0, 3)]);
        }
    }

    public void resetGame(){
        easyBtn.setVisible(false);
        mediumBtn.setVisible(false);
        hardBtn.setVisible(false);
        difficultyText.setVisible(false);
        sharkCount = 0;
        count = 0;
        firstTime = true;
        shark.setLayoutX(-105);
        shark.setLayoutY(603);
        luffyHead.setVisible(true);
        luffyHead.setLayoutX(211);
        luffyHead.setLayoutY(657);
        mathQ.setVisible(true);
        doneBtn.setVisible(true);
        rollBtn.setVisible(true);
        diceNum.setVisible(true);
        finishText.setLayoutX(25);
        finishText.setLayoutY(-19);
        diceNum.setText("0");
        answer.setPromptText("");
        diceNum.setLayoutX(-31);
        diceNum.setLayoutY(434);
        rollBtn.setLayoutX(-97);
        rollBtn.setLayoutY(352);
    }
    @FXML
    public void closeGame(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void switchToGame(ActionEvent event) throws Exception{
        root = FXMLLoader.load(getClass().getResource("scene2SB.fxml"));
        mainWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        mainWindow.setScene(scene);
        mainWindow.show();
    }

}