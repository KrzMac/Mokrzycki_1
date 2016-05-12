import Algorithm.bin_morf.*;
import Application.*;
import Algorithm.colour_histogram.*;
import Algorithm.filters.*;
import Algorithm.gray_hist.*;
import Algorithm.gray_morf.*;
import Algorithm.oper_geometr.*;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.*;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main extends Application {

    final ImageView iv1 = new ImageView();
    Image image;
    BufferedImage bufferedImage;
    final ScrollPane sp = new ScrollPane();
    private File file;
    Stage pS;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.pS = primaryStage;
        this.image = new Image(Main.class.getResourceAsStream(""));
        this.bufferedImage = SwingFXUtils.fromFXImage(image, null);
        iv1.setImage(image);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setContent(iv1);

        pS.setTitle("Wprowadzenie Przetwarzania Obrazów");

        VBox root = new VBox(5);
        HBox hbImage = new HBox();

        root.getChildren().addAll(new MyMenuBar());

        hbImage.getChildren().add(sp);
        root.getChildren().add(hbImage);

        Scene scene = new Scene(root, 800, 500, Color.BLANCHEDALMOND);
        pS.setScene(scene);



        primaryStage.show();
    }

    public class MyMenuBar extends MenuBar {

        MenuFile menuFile = new MenuFile("File");
        MenuGeometric menuGeometric = new MenuGeometric("Geometric");
        MenuGrayHistogram menuGrayHistogram = new MenuGrayHistogram("Gray Histogram");
        MenuRGBHistogram menuRGBHistogram = new MenuRGBHistogram("RGB Histogram");
        MenuBinaryMorphology menuBinaryMorphology = new MenuBinaryMorphology("Binary Morphology");
        MenuGrayMorphology menuGrayMorphology = new MenuGrayMorphology("Gray Morphology");
        MenuFilters menuFilters = new MenuFilters("Filters");

        public MyMenuBar() {
            this.getMenus().addAll(menuFile, menuGeometric, menuGrayHistogram, menuRGBHistogram, menuBinaryMorphology, menuGrayMorphology, menuFilters);
        }

    }

    public class MenuFile extends MyMenu {

        MenuItem open = setOpen("Open", null),
            openAsBinary = setOpenAs(BufferedImage.TYPE_BYTE_BINARY, "Open As Binary", null),
            openAsGray = setOpenAs(BufferedImage.TYPE_BYTE_GRAY, "Open As Gray", null),
            openAsRGB = setOpenAs(BufferedImage.TYPE_INT_RGB, "Open as RGB", null),
            save = setSave("Save", null),
            saveAs = setSaveAs("Save As...", null),
            exit = setExit("Exit", null);

        public MenuFile(String name) {
            super(name);

            this.getItems().addAll(open, openAsBinary, openAsGray, openAsRGB, save, saveAs, exit);
        }

        public MenuItem setOpen(String name, ImageView imageView) {
            MenuItem open = new MenuItem(name, imageView);

            open.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                //Set to user directory or go to default if cannot access
                String userDirectoryString = System.getProperty("user.home");
                File userDirectory = new File(userDirectoryString);
                if(!userDirectory.canRead()) {
                    userDirectory = new File("c:/");
                }
                fileChooser.setInitialDirectory(userDirectory);

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    bufferedImage = SwingFXUtils.fromFXImage(image1, null);
                    iv1.setImage(image1);
                }
            });

            return open;
        }

        public MenuItem setOpenAs(int Type, String name, ImageView imageView) {
            MenuItem open = new MenuItem(name, imageView);

            open.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle(name);
                //Set to user directory or go to default if cannot access
                String userDirectoryString = System.getProperty("user.home");
                File userDirectory = new File(userDirectoryString);
                if(!userDirectory.canRead()) {
                    userDirectory = new File("c:/");
                }
                fileChooser.setInitialDirectory(userDirectory);

                file = fileChooser.showOpenDialog(pS);
                if (file != null) {
                    Image image1 = new Image(file.toURI().toString());
                    bufferedImage = SwingFXUtils.fromFXImage(image1, null);
                    BufferedImage image = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), Type);
                    Graphics graphics = image.getGraphics();
                    graphics.drawImage(bufferedImage, 0, 0, null);
                    graphics.dispose();
                    iv1.setImage(SwingFXUtils.toFXImage(image, null));
                    bufferedImage = image;
                }
            });

            return open;
        }

        public MenuItem setSave(String name, ImageView imageView) {
            MenuItem save = new MenuItem(name, imageView);

            save.setOnAction(event -> {
                if (file != null) {
                    SaveImage saveImage = new SaveImage(file);
                    saveImage.save(bufferedImage);
                }
            });

            return save;
        }

        public MenuItem setSaveAs(String name, ImageView imageView) {
            MenuItem save = new MenuItem(name, imageView);

            save.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("All Images", "*.*"),
                        new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png"),
                        new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg")
                );
                fileChooser.setInitialDirectory(file.getParentFile());

                File file = fileChooser.showSaveDialog(pS);
                if (file != null) {
                    SaveImage saveImage = new SaveImage(file);
                    saveImage.save(bufferedImage);
                }
            });

            return save;
        }

        public MenuItem setExit(String name, ImageView imageView) {
            MenuItem exit = new MenuItem(name, imageView);

            exit.setOnAction(event -> {
                Platform.exit();
                System.exit(0);
            });

            return exit;
        }
    }

    public class MenuGeometric extends MyMenu {

        MenuItem menuAspectRatioScale = setAspectRatioScale("Aspect Ratio Scale", null),
                menuScale = setScale("Scale", null),
                menuRotate = setRotate("Rotate", null);
        Menu menuSymmetry = new Menu("Symmetry");

        public MenuGeometric(String name) {
            super(name);

            menuSymmetry.getItems().addAll(setSymmetry(0, "OX", null), setSymmetry(1, "OY", null), setSymmetry(2, "OXOY", null));

            this.getItems().addAll(menuAspectRatioScale, menuScale, menuRotate, menuSymmetry);
        }

        public MenuItem setAspectRatioScale(String name, ImageView imageView) {
            MenuItem menuItem = new MenuItem(name, imageView);

            menuItem.setOnAction(event -> {
                VBox layout = new VBox();
                Scene scene = new Scene(layout, 150, 110);

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
                layout.getChildren().add(txtHeight);

                Button btnResize = new Button();
                btnResize.setText("Resize");

                btnResize.setOnAction(event1 -> {
                    ResizeImage resizeImage = new ResizeImage(bufferedImage, Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
                    bufferedImage = resizeImage.resize();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    scene.getWindow().hide();
                });
                layout.getChildren().add(btnResize);

                scene.setRoot(layout);

                Stage stage = new Stage();
                stage.setTitle("Set Scale");
                stage.setScene(scene);

                stage.show();
            });

            return menuItem;
        }

        public MenuItem setScale(String name, ImageView imageView) {
            MenuItem menuItem = new MenuItem(name, imageView);

            menuItem.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 150, 110);

                TextField txtWidth = new TextField();
                TextField txtHeight = new TextField();

                txtWidth.setText(Integer.toString(bufferedImage.getWidth()));
                txtHeight.setText(Integer.toString(bufferedImage.getHeight()));

                // Width
                Label labelWidth = new Label("Width");
                layout.getChildren().add(labelWidth);
                layout.getChildren().add(txtWidth);

                // Height
                Label labelHeight = new Label("Height");
                layout.getChildren().add(labelHeight);
                layout.getChildren().add(txtHeight);

                Button btnResize = new Button();
                btnResize.setText("Resize");

                btnResize.setOnAction(event1 -> {
                    ResizeImage resizeImage = new ResizeImage(bufferedImage, Integer.parseInt(txtWidth.getText()), Integer.parseInt(txtHeight.getText()));
                    bufferedImage = resizeImage.resize();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    secondScene.getWindow().hide();
                });
                layout.getChildren().add(btnResize);

                secondScene.setRoot(layout);
                Stage secondStage = new Stage();
                secondStage.setTitle("Set Scale");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuItem;
        }

        public MenuItem setRotate(String name, ImageView imageView) {
            MenuItem menuRotate = new MenuItem(name, imageView);

            menuRotate.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 150, 110);

                TextField txtRotate = new TextField();

                txtRotate.setText("0");

                // Angle
                Label labelRotate = new Label("Angle");
                layout.getChildren().add(labelRotate);
                layout.getChildren().add(txtRotate);

                Button btnRotate = new Button();
                btnRotate.setText("Rotate");

                btnRotate.setOnAction(event1 -> {
                    RotateImage rotateImage = new RotateImage(bufferedImage, Integer.parseInt(txtRotate.getText()));
                    bufferedImage = rotateImage.rotate();
                    iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                    secondScene.getWindow().hide();
                });
                layout.getChildren().add(btnRotate);

                secondScene.setRoot(layout);
                Stage secondStage = new Stage();
                secondStage.setTitle("Set Scale");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuRotate;
        }

        public MenuItem setSymmetry(int axis, String name, ImageView imageView) {
            MenuItem menuSymmetry = new MenuItem(name, imageView);

            menuSymmetry.setOnAction(event -> {
                SymmetryImage symmetryImage = new SymmetryImage(bufferedImage);
                switch (axis) {
                    case 0:
                        symmetryImage.symmetryImageOX();
                        break;
                    case 1:
                        symmetryImage.symmetryImageOY();
                        break;
                    case 2:
                        symmetryImage.symmetryImageOXOY();
                        break;
                }
                bufferedImage = symmetryImage.getSymmetryImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuSymmetry;
        }
    }

    public class MenuGrayHistogram extends MyMenu {

        MenuItem menuHistogram = setHistogram("Histogram", null),
                menuEqualization = setOperation(0, "Equalization", null),
                menuStretching = setOperation(1, "Stretching", null);

        public MenuGrayHistogram(String name) {
            super(name);

            this.getItems().addAll(menuHistogram, menuEqualization, menuStretching);
        }

        public MenuItem setHistogram(String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 300, 300);

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                GrayHistogram grayHistogram = new GrayHistogram(bufferedImage);
                grayHistogram.histogram(bufferedImage);
                chartHistogram.getData().addAll(
                        grayHistogram.getSeriesGray()
                );

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().addAll(hBox);

                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("Gray Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuHistogram;
        }

        public MenuItem setOperation(int operation, String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 300, 300);

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                GrayHistogram grayHistogram = new GrayHistogram(bufferedImage);
                switch (operation) {
                    case 0:
                        bufferedImage = grayHistogram.getEqualization();
                        break;
                    case 1:
                        bufferedImage = grayHistogram.getStretching();
                        break;
                }

                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                chartHistogram.getData().addAll(
                        grayHistogram.getSeriesGray()
                );

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().addAll(hBox);

                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("Gray Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuHistogram;
        }

    }

    public class MenuRGBHistogram extends MyMenu {

        MenuItem menuHistogram = setHistogram("Histogram", null),
                menuEqualization = setOperation(0, "Equalization", null),
                menuStretching = setOperation(1, "Stretching", null),
                menuThreshold = setThreshold("Threshold", null),
                menuOtsuThreshold = setOtsuThreshold("Otsu Threshold", null);

        public MenuRGBHistogram(String name) {
            super(name);

            this.getItems().addAll(menuHistogram, menuEqualization, menuStretching, menuThreshold, menuOtsuThreshold);
        }

        public MenuItem setHistogram(String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 300, 300);

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<String, Number>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                RGBHistogram rgbHistogram = new RGBHistogram(bufferedImage);
                rgbHistogram.histogram(bufferedImage);
                chartHistogram.getData().addAll(
                        rgbHistogram.getSeriesRed(),
                        rgbHistogram.getSeriesGreen(),
                        rgbHistogram.getSeriesBlue()
                );

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().add(hBox);

                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("RGB Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuHistogram;
        }

        public MenuItem setOtsuThreshold(String name, ImageView imageView) {
            MenuItem menuOtsu = new MenuItem(name, imageView);

            menuOtsu.setOnAction(event -> {
                ThresholdHistogram thresholdHistogram = new ThresholdHistogram(bufferedImage);
                thresholdHistogram.otsuThreshold();

                bufferedImage = thresholdHistogram.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuOtsu;
        }

        public MenuItem setThreshold(String name, ImageView imageView) {
            MenuItem menuThreshold= new MenuItem(name, imageView);

            menuThreshold.setOnAction(event -> {
                ThresholdHistogram thresholdHistogram =  new ThresholdHistogram(bufferedImage);

                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 400, 100);

                Label lblThreshold = new Label("Threshold");

                Slider slider = new Slider();
                slider.setMin(0);
                slider.setMax(255);
                slider.setValue(127);
                slider.setShowTickLabels(true);
                slider.setShowTickMarks(true);
                slider.setMajorTickUnit(17);
                slider.setMinorTickCount(5);
                slider.setBlockIncrement(10);

                Button btnThreshold = new Button();
                btnThreshold.setText("Threshold");

                slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                    thresholdHistogram.threshold(newValue.intValue());
                    iv1.setImage(SwingFXUtils.toFXImage(thresholdHistogram.getTemplateImage(), null));
                });

                layout.getChildren().add(lblThreshold);
                layout.getChildren().add(slider);
//                layout.getChildren().add(btnThreshold);

                secondScene.setRoot(layout);
                Stage secondStage = new Stage();
                secondStage.setTitle("Set Threshold");
                secondStage.setScene(secondScene);

                secondStage.show();

                secondStage.setOnCloseRequest(event1 -> {
                    bufferedImage = thresholdHistogram.getTemplateImage();
                    iv1.setImage(SwingFXUtils.toFXImage(thresholdHistogram.getTemplateImage(), null));
                });
            });

            return menuThreshold;
        }

        public MenuItem setOperation(int operation, String name, ImageView imageView) {
            MenuItem menuHistogram = new MenuItem(name, imageView);

            menuHistogram.setOnAction(event -> {
                VBox layout = new VBox();
                Scene secondScene = new Scene(layout, 300, 300);

                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                final LineChart<String, Number> chartHistogram = new LineChart<>(xAxis, yAxis);
                chartHistogram.setCreateSymbols(false);

                RGBHistogram rgbHistogram = new RGBHistogram(bufferedImage);
                switch (operation) {
                    case 0:
                        bufferedImage = rgbHistogram.getEqualization();
                        break;
                    case 1:
                        bufferedImage = rgbHistogram.getStretching();
                        break;
                }

                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                chartHistogram.getData().addAll(
                        rgbHistogram.getSeriesRed(),
                        rgbHistogram.getSeriesGreen(),
                        rgbHistogram.getSeriesBlue()
                );

                HBox hBox = new HBox();
                hBox.getChildren().addAll(chartHistogram);
                layout.getChildren().addAll(hBox);

                secondScene.setRoot(layout);

                Stage secondStage = new Stage();
                secondStage.setTitle("RGB Histogram");
                secondStage.setScene(secondScene);

                secondStage.show();
            });

            return menuHistogram;
        }

    }

    public class MenuBinaryMorphology extends MyMenu {

        MenuItem erosion = setErosion("Erosion", null),
            dilation = setDilation("Dilation", null),
            opening = setOpening("Opening", null),
            closing = setClosing("Closing", null);

        public MenuBinaryMorphology(String name) {
            super(name);

            this.getItems().addAll(erosion, dilation, opening, closing);
        }

        public MenuItem setErosion(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinErosion binErosion = new BinErosion(bufferedImage);
                bufferedImage = binErosion.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binErosion.getTemplateImage(), null));
            });

            return menuErosion;
        }

        public MenuItem setDilation(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinDilation binDilation = new BinDilation(bufferedImage);
                bufferedImage = binDilation.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binDilation.getTemplateImage(), null));
            });

            return menuErosion;
        }

        public MenuItem setOpening(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinOpening binOpening = new BinOpening(bufferedImage);
                bufferedImage = binOpening.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binOpening.getTemplateImage(), null));
            });

            return menuErosion;
        }

        public MenuItem setClosing(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                BinClosing binClosing = new BinClosing(bufferedImage);
                bufferedImage = binClosing.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(binClosing.getTemplateImage(), null));
            });

            return menuErosion;
        }

    }

    public class MenuGrayMorphology extends MyMenu {

        MenuItem erosion = setErosion("Erosion", null),
                dilation = setDilation("Dilation", null),
                opening = setOpening("Opening", null),
                closing = setClosing("Closing", null);

        public MenuGrayMorphology(String name) {
            super(name);

            this.getItems().addAll(erosion, dilation, opening, closing);
        }

        public MenuItem setErosion(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayErosion grayErosion = new GrayErosion(bufferedImage);
                bufferedImage = grayErosion.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayErosion.getTemplateImage(), null));
            });

            return menuErosion;
        }

        public MenuItem setDilation(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayDilation grayDilation = new GrayDilation(bufferedImage);
                bufferedImage = grayDilation.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayDilation.getTemplateImage(), null));
            });

            return menuErosion;
        }

        public MenuItem setOpening(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayOpening grayOpening = new GrayOpening(bufferedImage);
                bufferedImage = grayOpening.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayOpening.getTemplateImage(), null));
            });

            return menuErosion;
        }

        public MenuItem setClosing(String name, ImageView imageView) {
            MenuItem menuErosion = new MenuItem(name, imageView);

            menuErosion.setOnAction(event -> {
                GrayClosing grayClosing = new GrayClosing(bufferedImage);
                bufferedImage = grayClosing.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(grayClosing.getTemplateImage(), null));
            });

            return menuErosion;
        }
    }

    public class MenuFilters extends MyMenu {

        Menu lowPass = new Menu("Low Pass", null),
            highPass = new Menu("High Pass", null),
            gradient = new Menu("Gradient", null),
            extreme = new Menu("Extreme", null);
        MenuItem median = setMedian("Median", null);

        public MenuFilters(String name) {
            super(name);

            lowPass.getItems().addAll(setLowPass(0, "1", null), setLowPass(1, "2", null), setLowPass(2, "3", null), setLowPass(3, "4", null));
            highPass.getItems().addAll(setHighPass(0, "1", null), setHighPass(1, "2", null), setHighPass(2, "3", null), setHighPass(3, "4", null));
            gradient.getItems().addAll(setGradient("East", null), setGradient("South-East", null), setGradient("North-East", null),
                    setGradient("West", null), setGradient("South-West", null), setGradient("North-West", null), setGradient("North", null),
                    setGradient("South", null));
            extreme.getItems().addAll(setExtreme("Minimum", null), setExtreme("Maximum", null));

            this.getItems().addAll(lowPass, highPass, gradient, median, extreme);
        }

        public MenuItem setLowPass(int operation, String name, ImageView imageView) {
            MenuItem menuLowPass = new MenuItem(name, imageView);

            menuLowPass.setOnAction(event -> {
                FilterPass filterPass = new FilterPass(bufferedImage);
                switch (operation) {
                    case 0:
                        filterPass.setArrayMask(FilterList.lowPass1());
                        break;
                    case 1:
                        filterPass.setArrayMask(FilterList.lowPass2());
                        break;
                    case 2:
                        filterPass.setArrayMask(FilterList.lowPass3());
                        break;
                    case 3:
                        filterPass.setArrayMask(FilterList.lowPass4());
                        break;
                }
                filterPass.run();

                bufferedImage = filterPass.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuLowPass;
        }

        public MenuItem setHighPass(int operation, String name, ImageView imageView) {
            MenuItem menuHighPass = new MenuItem(name, imageView);

            menuHighPass.setOnAction(event -> {
                FilterPass filterPass = new FilterPass(bufferedImage);
                switch (operation) {
                    case 0:
                        filterPass.setArrayMask(FilterList.highPass1());
                        break;
                    case 1:
                        filterPass.setArrayMask(FilterList.highPass2());
                        break;
                    case 2:
                        filterPass.setArrayMask(FilterList.highPass3());
                        break;
                    case 3:
                        filterPass.setArrayMask(FilterList.highPass4());
                        break;
                }
                filterPass.run();

                bufferedImage = filterPass.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuHighPass;
        }

        public MenuItem setGradient(String name, ImageView imageView) {
            MenuItem menuHighPass = new MenuItem(name, imageView);

            menuHighPass.setOnAction(event -> {
                FilterPass filterPass = new FilterPass(bufferedImage);
                switch (name) {
                    case "East":
                        filterPass.setArrayMask(FilterList.gradientDirectionalEast());
                        break;
                    case "South-East":
                        filterPass.setArrayMask(FilterList.gradientDirectionalSoutheast());
                        break;
                    case "North-East":
                        filterPass.setArrayMask(FilterList.gradientDirectionalNortheast());
                        break;
                    case "West":
                        filterPass.setArrayMask(FilterList.gradientDirectionalWest());
                        break;
                    case "South-West":
                        filterPass.setArrayMask(FilterList.gradientDirectionalSouthwest());
                        break;
                    case "North-West":
                        filterPass.setArrayMask(FilterList.gradientDirectionalNorthwest());
                        break;
                    case "North":
                        filterPass.setArrayMask(FilterList.gradientDirectionalNorth());
                        break;
                    case "South":
                        filterPass.setArrayMask(FilterList.gradientDirectionalSouth());
                        break;
                }
                filterPass.run();

                bufferedImage = filterPass.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuHighPass;
        }

        public MenuItem setExtreme(String name, ImageView imageView) {
            MenuItem menuExtreme = new MenuItem(name, imageView);

            menuExtreme.setOnAction(event -> {
                switch (name) {
                    case "Minimum":
                        FilterMinimum filterMinimum = new FilterMinimum(bufferedImage);
                        bufferedImage = filterMinimum.getTemplateImage();
                        break;
                    case "Maximum":
                        FilterMaximum filterMaximum = new FilterMaximum(bufferedImage);
                        bufferedImage = filterMaximum.getTemplateImage();
                        break;
                }

                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuExtreme;
        }

        public MenuItem setMedian(String name, ImageView imageView) {
            MenuItem menuGradient = new MenuItem(name, imageView);

            menuGradient.setOnAction(event -> {
                FilterMedian filterMedian = new FilterMedian(bufferedImage);

                bufferedImage = filterMedian.getTemplateImage();
                iv1.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            });

            return menuGradient;
        }
    }
}