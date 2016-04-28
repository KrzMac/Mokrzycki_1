import Algorithm.bin_morf.BinClosing;
import Algorithm.bin_morf.BinDilation;
import Algorithm.bin_morf.BinErosion;
import Algorithm.bin_morf.BinOpening;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class Main extends Application {

    private String path;
    private ImageView iv1;
    private Image image;

    public static void main(String[] args) {
        launch(args);
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new ActionFrame();
//            }
//        });
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.image = new Image(Main.class.getResourceAsStream(""));
        this.iv1 = new ImageView();
        iv1.setImage(image);

        primaryStage.setTitle("Master");

        Reflection r = new Reflection();
        r.setFraction(1);

        BoxBlur bb = new BoxBlur();
        bb.setWidth(5);
        bb.setHeight(5);
        bb.setIterations(3);

        Light.Distant light = new Light.Distant();
        light.setAzimuth(-135.0f);
        Lighting l = new Lighting();
        l.setLight(light);
        l.setSurfaceScale(5.0f);

        Button btnChooseImage = new Button();
        btnChooseImage.setText("Choose Image");
        btnChooseImage.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
            File file = fileChooser.showOpenDialog(primaryStage);;
            if (file.toURI().toString() != null) {
                image = new Image(file.toURI().toString());
                iv1.setImage(image);
            }
        });

        VBox root = new VBox(5);
        HBox hbEffects = new HBox(5);
        HBox hbImage = new HBox();

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem add = new MenuItem("Open image", new ImageView());
        add.setOnAction(event -> {
            root.setVisible(true);
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(primaryStage);;
            if (file != null) {
                Image image1 = new Image(file.toURI().toString());
                path = file.getPath();
                iv1.setImage(image1);
            }
        });
        menuFile.getItems().addAll(add);

        Menu menuEdit = new Menu("Binary morphology");
        add = new MenuItem("Erosion", new ImageView());
        add.setOnAction(event -> {
            root.setVisible(true);
            BinErosion binErosion = new BinErosion(path);
            this.iv1.setImage(SwingFXUtils.toFXImage(binErosion.getTemplateImage(), null));
        });
        menuEdit.getItems().addAll(add);
        add = new MenuItem("Dilation", new ImageView());
        add.setOnAction(event -> {
            root.setVisible(true);
            BinDilation binDilation = new BinDilation(path);
            this.iv1.setImage(SwingFXUtils.toFXImage(binDilation.getTemplateImage(), null));
        });
        menuEdit.getItems().addAll(add);
        add = new MenuItem("Opening", new ImageView());
        add.setOnAction(event -> {
            root.setVisible(true);
            BinOpening binOpening = new BinOpening(path);
            this.iv1.setImage(SwingFXUtils.toFXImage(binOpening.getTemplateImage(), null));
        });
        menuEdit.getItems().addAll(add);

        add = new MenuItem("Closing", new ImageView());
        add.setOnAction(event -> {
            root.setVisible(true);
            BinClosing binClosing = new BinClosing(path);
            this.iv1.setImage(SwingFXUtils.toFXImage(binClosing.getTemplateImage(), null));
        });
        menuEdit.getItems().addAll(add);


        menuBar.getMenus().addAll(menuFile, menuEdit);

        root.getChildren().addAll(menuBar);

//        hbEffects.getChildren().add(btnChooseImage);
        hbImage.getChildren().add(iv1);

        root.getChildren().add(hbEffects);
        root.getChildren().add(hbImage);





        primaryStage.setScene(new Scene(root, 800, 500, Color.BLANCHEDALMOND));
        primaryStage.show();
    }
}
