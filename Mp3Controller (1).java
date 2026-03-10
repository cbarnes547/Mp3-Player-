package org.example.lab2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Mp3Controller {
    //#### Problem 1
    //Declare the parallel String arrays titles and locations of size 20.
    //Declare a variable (numSongs) of type int to keep track of the number of songs since it may be less than 20.
    //Declare a variable (songFile) of type File to be used for loading and saving the song file.
    String[] titles = new String[20];
    String[] locations = new String[20];
    int numSongs = 0;
    File songFile;
    //The best way to do this is using an ObservableArrayList<String> instead if arrays but
    //we have not covered ArrayList yet.



    //Label to display the currently "playing" song.
    @FXML
    private Label lblNowPlaying;

    //ListView control to show the play list of songs.s
    @FXML
    private ListView<String> lstPlayList;


    /***+
     * Called when the loadList button is clicked.
     */
    @FXML
    protected void onLoadListClick() {
        // Allow the user to choose the file with the playlist titles and pathnames.
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        songFile = chooser.showOpenDialog(new Stage());

        //If user did not Cancel, load the parallel arrays and the ListView Control
        if (songFile != null) {
            loadSongInfo(); //#####

            //Load the song titles into the lstPlayList control.
            for (int i = 0; i < numSongs; i++) {
                lstPlayList.getItems().add(titles[i]);
            }
        }

        //Enable single selection mode (no multiple selection)
        lstPlayList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    /**+
     * Associate the File parameter with the Scanner object and read in the song titles and locations
     *
     */
    private void loadSongInfo() {
        //#### Problem 2: Write code to open the file and populate the titles and locations arrays.
        //
        try {
            Scanner reader = new Scanner(songFile);

            // We loop up to 20 times, but we stop early if the file runs out of lines
            for (int i = 0; i < 20; i++) {
                if (reader.hasNextLine()) {

                    // Read title into the current index
                    titles[i] = reader.nextLine();

                    // Read location (assuming it's on the next line)
                    if (reader.hasNextLine()) {
                        locations[i] = reader.nextLine();
                    }

                    // Increment numSongs for every song successfully added
                    numSongs++;

                } else {
                    // If there are no more lines in the file, we break out of the loop
                    break;
                }
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: The file 'songs.txt' was not found.");
        }
    }


    /**+
     * Called when the Play button is clicked
     */
    @FXML
    protected void onPlayClick() {
        //Update the lblNowPlaying Label with the currently selected song title

        //Get a reference to the object that holds the displayed list of songs within the ListView control
        //and then call the method to get the selected item.  This is done in one statement.
        String selectedItem = lstPlayList.getSelectionModel().getSelectedItem();

        //#### Problem 3: Update the Label with the selected song title by calling the Label setText() method.
        if (selectedItem != null) {
            lblNowPlaying.setText("Now Playing: " + selectedItem);
        }
        else {
            lblNowPlaying.setText("No song selected.");
        }
    }


    /**+
     * Called when the Add button is clicked
     */
    @FXML
    protected void onAddSongClick() {
        TextInputDialog txtInput = new TextInputDialog();
        String songTitle;       //Used to hold the song title input from user.
        String songLocation = null;    //Used to hold the user location input from user.

        //Get the new song title
        txtInput.setHeaderText("What is the song title");
        txtInput.showAndWait();
        songTitle = txtInput.getEditor().getText();
        txtInput.getEditor().setText("");

        //Get the new song location
        if (songTitle != null) {
            txtInput.setHeaderText("What is the song pathname/location");
            txtInput.showAndWait();
            songLocation = txtInput.getEditor().getText();


            if (songLocation != null) {
                //#### Problem 4: Add the new song title and location into the arrays and update numSongs
                if (songLocation != null) {
                    titles[numSongs] = songTitle;
                    locations[numSongs] = songLocation;
                    numSongs++;
                    //#### Problem 5: Add new song to the ListView control. Hint: See the statement in the for-loop in onLoadListClick()
                    lstPlayList.getItems().add(songTitle);
                    saveSongInfo();
                }
            }
        }

    }


    /**+
     * Associated the given File parameter with the PrintWriter object and save the titles and location arrays
     *
     */
    private void saveSongInfo() {
        //#### Problem 6: Write code to open the given file for writing and save the titles and locations in the
        // correct format.
        try {
            // Create a PrintWriter linked to your songFile
            java.io.PrintWriter writer = new java.io.PrintWriter(songFile);

            // Loop through only the songs that actually exist (up to numSongs)
            for (int i = 0; i < numSongs; i++) {
                // Write the title
                writer.println(titles[i]);

                // Write the location
                writer.println(locations[i]);
            }

            // Close the writer to "flush" the data and actually save the file
            writer.close();

            System.out.println("Playlist saved successfully!");

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Error: Could not save to the file.");
        }
    }



    /**+
     * Called when the save button is clicked
     */
    @FXML
    protected void onSaveListClick() {
        //Create a new song file if we are starting a new playlist
        if (songFile == null) {
            songFile = new File("songs" + ( (int) (Math.random() * 100)) + ".txt");
        }
        saveSongInfo(); //#####

        //Inform the use that the file was saved.
        Alert a = new Alert( Alert.AlertType.INFORMATION);
        a.setHeaderText("Song File " + songFile.getName() + " was saved");
        a.show();


    }



    /**+
     * Sort the parallel arrays by title.
     */
    //#### Problem 7
    private void selectionSort() {
        //titles, locations, and numSongs are properties of the class, so you have direct access to them.

    }

    /**+
     * Called when the sort button is clicked
     */
    @FXML
    protected void onSortListClick() {
        //Clear the ListView and add reload the song titles
        lstPlayList.getItems().clear();

        selectionSort();  //##### Find the method stub and implement the method body

        //Load the titles array into lstPlayList  -- Hint:  This code was done for you in another method.
        //##### Problem 8.

    }



    /**+
     * Called when the Close button is clicked
     */
    @FXML
    protected void onCloseClick(){
        javafx.application.Platform.exit();
    }

}