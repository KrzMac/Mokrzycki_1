import Algorithm.bin_morf.BinClosing;
import Algorithm.bin_morf.BinDilation;
import Algorithm.bin_morf.BinErosion;
import Algorithm.bin_morf.BinOpening;
import Algorithm.filters.FilterList;
import Algorithm.filters.FilterMedian;
import Algorithm.filters.FilterPass;
import Algorithm.gray_morf.GrayClosing;
import Algorithm.gray_morf.GrayDilation;
import Algorithm.gray_morf.GrayErosion;
import Algorithm.gray_morf.GrayOpening;
import Algorithm.oper_geometr.AspectRatioScale;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;

public class Main extends Application {

    final ImageView iv1 = new ImageView();
    Image image;
    BufferedImage bufferedImage;
    final ScrollPane sp = new ScrollPane();
    private File file;

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
        this.bufferedImage = SwingFXUtils.fromFXImage(image, null);
        iv1.setImage(image);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(iv1);

        primaryStage.setTitle("Wprowadzenie Do Przetwarzania Obrazów");

        VBox root = new VBox(5);
        HBox hbImage = new HBox();

        MenuBar menuBar = new MenuBar();

        Menu menuMorphology = new Menu("Morhpology");
        menuMorphology.getItems().addAll(addBinaryMorphology(), addGrayMorphology());


        menuBar.getMenus().addAll(addFile(primaryStage), menuMorphology, addFilters(), addGeomtr());
        root.getChildren().addAll(menuBar);

        hbImage.getChildren().add(sp);
        root.getChildren().add(hbImage);


        Scene scene = new Scene(root, 800, 500, Color.BLANCHEDALMOND);
        primaryStage.setScene(scene);



        primaryStage.show();
    }

    private Menu addGeomtr() {
        Menu menuGeomtr = new Menu("Geometry");
        MenuItem add;

        add = new MenuItem("Aspect Ratio Scale");
        add.setOnAction(event -> {
            VBox layout = new VBox();

            TextField txtWidth = new TextField();
            TextField txtHeight = new TextField();
            txtHeight.setEditable(false);

            txtWidth.setText(Integer.toString(bufferedImage.getWidth()));
            txtHeight.setText(Integer.toString(bufferedImage.getHeight()));

            AspectRatioScale ars = new AspectRatioScale(bufferedImage);

            // Width
            Label labelWidth = new Label("Width");
            layout.getChildren().add(labelWidth);
            txtWidth.textProperty().addListener(((observable, oldValue, newValue) -> {
                ars.calculate(Integer.parseInt(newValue), Integer.parseInt(txtHeight.getText()));

                txtHeight.setText(Integer.toString(ars.getNewHeight()));
                System.out.println(ars.getNewWidth() + "x" + ars.getNewHeight());
            }));
            layout.getChildren().add(txtWidth);

            // Height
            Label labelHeight = new Label("Height");
            layout.getChildren().add(labelHeight);

//            txtHeight.textProperty().addListener(((observable, oldValue, newValue) -> {
//                ars.calculate(Integer.parseInt(txtWidth.getText()), Integer.parseInt(newValue));
//
//                txtWidth.setText(Integer.toString(ars.getNewWidth()));
//                System.out.println(ars.getNewWidth() + "x" + ars.getNewHeight());
//            }));
            layout.getChildren().add(txtHeight);

            Scene secondScene = new Scene(layout, 200, 100);

            Stage secondStage = new Stage();
            secondStage.setTitle("Set Scale");
            secondStage.setScene(secondScene);

            secondStage.show();
        });
        menuGeomtr.getItems().add(add);


        return menuGeomtr;
    }

    private Menu addFile(Window primaryStage) {
        Menu menuFile = new Menu("File");
        MenuItem add;

        add = new MenuItem("Open", new ImageView());
        add.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            //Set to user directory or go to default if cannot access
            String userDirectoryString = System.getProperty("user.home");
            File userDirectory = new File(userDirectoryString);
            if(!userDirectory.canRead()) {
                userDirectory = new File("c:/");
            }
            fileChooser.setInitialDirectory(userDirectory);


            file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                Image image1 = new Image(file.toURI().toString());
                bufferedImage = SwingFXUtils.fromFXImage(image1, null);
                iv1.setImage(image1);
            }
        });
        menuFile.getItems().add(add);

        add = new MenuItem("Save", new ImageView());
        add.setOnAction(event -> {
            if (file != null) {
                SaveImage saveImage = new SaveImage(file);
                saveImage.save(bufferedImage);
            }
        });
        menuFile.getItems().add(add);

        add = new MenuItem("Save As...", new ImageView());
        add.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                SaveImage saveImage = new SaveImage(file);
                saveImage.save(bufferedImage);
            }
        });
        menuFile.getItems().add(add);

        add = new MenuItem("Exit", new ImageView());
        add.setOnAction(event -> {
            Platform.exit();
            System.exit(0);
        });
        menuFile.getItems().add(add);

        return menuFile;
    }

    private Menu addBinaryMorphology() {
        Menu menuEdit = new Menu("Binary");
        MenuItem add;

        add = new MenuItem("Erosion", new ImageView());
        add.setOnAction(event -> {
            BinErosion binErosion = new BinErosion(bufferedImage);
            bufferedImage = binErosion.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(binErosion.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);
        add = new MenuItem("Dilation", new ImageView());
        add.setOnAction(event -> {
            BinDilation binDilation = new BinDilation(bufferedImage);
            bufferedImage = binDilation.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(binDilation.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);
        add = new MenuItem("Opening", new ImageView());
        add.setOnAction(event -> {
            BinOpening binOpening = new BinOpening(bufferedImage);
            bufferedImage = binOpening.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(binOpening.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);

        add = new MenuItem("Closing", new ImageView());
        add.setOnAction(event -> {
            BinClosing binClosing = new BinClosing(bufferedImage);
            bufferedImage = binClosing.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(binClosing.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);

        return menuEdit;
    }

    private Menu addGrayMorphology() {
        Menu menuEdit = new Menu("Gray");
        MenuItem add;

        add = new MenuItem("Erosion", new ImageView());
        add.setOnAction(event -> {
            GrayErosion grayErosion = new GrayErosion(bufferedImage);
            bufferedImage = grayErosion.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(grayErosion.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);
        add = new MenuItem("Dilation", new ImageView());
        add.setOnAction(event -> {
            GrayDilation grayDilation = new GrayDilation(bufferedImage);
            bufferedImage = grayDilation.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(grayDilation.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);
        add = new MenuItem("Opening", new ImageView());
        add.setOnAction(event -> {
            GrayOpening grayOpening = new GrayOpening(bufferedImage);
            bufferedImage = grayOpening.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(grayOpening.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);

        add = new MenuItem("Closing", new ImageView());
        add.setOnAction(event -> {
            GrayClosing grayClosing = new GrayClosing(bufferedImage);
            bufferedImage = grayClosing.getTemplateImage();
            this.iv1.setImage(SwingFXUtils.toFXImage(grayClosing.getTemplateImage(), null));
        });
        menuEdit.getItems().add(add);

        return menuEdit;
    }

    private Menu addFilters() {
        Menu menuEdit = new Menu("Filters");

        Menu menuLowPass = addFiltersLowPass();
        Menu menuHighPass = addFiltersHighPass();
        Menu menuGradient = addFiltersGradient();
        MenuItem menuMedian = addFiltersMedian();

        menuEdit.getItems().addAll(menuLowPass, menuHighPass, menuGradient, menuMedian);

        return menuEdit;
    }

    private Menu addFiltersLowPass() {
        Menu menuLowPass = new Menu("Low Pass");
        MenuItem add;

        add = new MenuItem("1");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.lowPass1());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuLowPass.getItems().add(add);
        add = new MenuItem("2");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.lowPass2());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuLowPass.getItems().add(add);
        add = new MenuItem("3");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.lowPass3());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuLowPass.getItems().add(add);
        add = new MenuItem("4");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.lowPass4());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuLowPass.getItems().add(add);


        return menuLowPass;
    }

    private Menu addFiltersHighPass() {
        Menu menuHighPass = new Menu("High Pass");
        MenuItem add;

        add = new MenuItem("1");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.highPass1());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuHighPass.getItems().add(add);

        add = new MenuItem("2");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.highPass2());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuHighPass.getItems().add(add);

        add = new MenuItem("3");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.highPass3());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuHighPass.getItems().add(add);

        add = new MenuItem("4");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.highPass4());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuHighPass.getItems().add(add);

        return menuHighPass;
    }

    private Menu addFiltersGradient() {
        Menu menuGradient = new Menu("Gradient Directional");
        MenuItem add;

        add = new MenuItem("East");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalEast());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("South-East");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalSoutheast());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("North-East");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalNortheast());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("West");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalWest());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("South-West");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalSouthwest());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("North-West");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalNorthwest());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("North");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalNorth());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        add = new MenuItem("South");
        add.setOnAction(event -> {
            FilterPass filterPass = new FilterPass(bufferedImage);
            filterPass.setArrayMask(FilterList.gradientDirectionalSouth());
            filterPass.run();

            bufferedImage = filterPass.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });
        menuGradient.getItems().add(add);

        return menuGradient;
    }

    private MenuItem addFiltersMedian() {
        MenuItem menuMedian = new MenuItem("Median");

        menuMedian.setOnAction(event -> {
            FilterMedian filterMedian = new FilterMedian(bufferedImage);

            bufferedImage = filterMedian.getTemplateImage();
            iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
        });

        return menuMedian;
    }

}