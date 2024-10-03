package view;
import controller.ACController;
import model.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DogAdoptionGUI extends JFrame {
    /**
     * Controller for managing the adoption center model and operations.
     */
    private ACController controller;
    /**
     * Main panels of the GUI.
     * mainPanel: Contains all other panels of the GUI.
     * searchPanel: Contains search input fields and buttons.
     * resultsPanel: Displays search results.
     * wishlistPanel: Contains the wishlist button.
     */
    private JPanel mainPanel, searchPanel, resultsPanel, wishlistPanel;
    /**
     * Text fields for entering search criteria.
     * nameField: For dog name.
     * ageField: For dog age.
     * weightField: For dog weight.
     * priceField: For dog price.
     */
    private JTextField nameField, ageField, weightField, priceField;
    /**
     * Combo boxes for selecting search and sort criteria.
     * sexComboBox: For selecting dog sex.
     * breedComboBox: For selecting dog breed.
     * sortComboBox: For selecting the sorting criteria for search results.
     */
    private JComboBox<String> sexComboBox, breedComboBox, sortComboBox;
    /**
     * Buttons for user interactions.
     * searchButton: To initiate the search based on entered criteria.
     * clearButton: To clear all search fields.
     * showWishlistButton: To display the wishlist.
     */
    private JButton searchButton, clearButton, showWishlistButton;
    /**
     * Panel for displaying individual dog information in a grid layout.
     */
    private JPanel dogGridPanel;
    /**
     * Scroll pane to allow scrolling through search results.
     */
    private JScrollPane scrollPane;

    /**
     * Constructs a new DogAdoptionGUI with the given controller.
     * Sets up the main window and initializes all UI components.
     *
     * @param controller The ACController to be used for managing dog adoption data.
     */
    public DogAdoptionGUI(ACController controller) {
        this.controller = controller;
        setTitle("Dog Adoption Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        createSearchPanel(); // Initializes the search panel
        createResultsPanel(); // Initializes the results panel
        createWishlistPanel(); // Initializes the wishlist panel

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(resultsPanel, BorderLayout.CENTER);
        mainPanel.add(wishlistPanel, BorderLayout.SOUTH);

        add(mainPanel);
        updateDogList(controller.getAdoptableDogs()); // Displays all adoptable dogs initially
        setVisible(true);
    }

    /**
     * Creates and initializes the search panel with input fields and buttons.
     */
    private void createSearchPanel() {
        searchPanel = new JPanel(new GridLayout(5, 2)); // Adjusted GridLayout to accommodate sorting combo box
        nameField = new JTextField(20);
        ageField = new JTextField(5);
        weightField = new JTextField(5);
        priceField = new JTextField(10);
        sexComboBox = new JComboBox<>(new String[]{"Any", "Male", "Female"});
        breedComboBox = new JComboBox<>(getBreedList());
        sortComboBox = new JComboBox<>(new String[]{"Name", "Age", "Weight", "Price"}); // Added sorting options
        searchButton = new JButton("Search");
        clearButton = new JButton("Clear");

        // Adding labels and fields to the search panel
        searchPanel.add(new JLabel("Name:"));
        searchPanel.add(nameField);
        searchPanel.add(new JLabel("Sex:"));
        searchPanel.add(sexComboBox);
        searchPanel.add(new JLabel("Breed:"));
        searchPanel.add(breedComboBox);
        searchPanel.add(new JLabel("Sort By:")); // Label for sorting combo box
        searchPanel.add(sortComboBox); // Add sorting combo box to the panel
        searchPanel.add(searchButton);
        searchPanel.add(clearButton);

        searchButton.addActionListener(e -> performSearch()); // Trigger search when the button is clicked
        clearButton.addActionListener(e -> clearSearch()); // Trigger clear search when the button is clicked
    }

    /**
     * Creates and initializes the results panel to display search results.
     */
    private void createResultsPanel() {
        resultsPanel = new JPanel(new BorderLayout());
        dogGridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // 3 columns, dynamic rows
        scrollPane = new JScrollPane(dogGridPanel);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates and initializes the wishlist panel with a button to show the wishlist.
     */
    private void createWishlistPanel() {
        wishlistPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        showWishlistButton = new JButton("Show Wishlist");
        showWishlistButton.addActionListener(e -> showWishlist()); // Trigger show wishlist when the button is clicked
        wishlistPanel.add(showWishlistButton);
    }

    /**
     * Retrieves and returns a list of all unique dog breeds.
     *
     * @return An array of Strings containing all unique dog breeds, including "Any" as the first option.
     */
    private String[] getBreedList() {
        Set<String> breeds = new HashSet<>();
        breeds.add("Any");
        for (Dog dog : controller.getAdoptableDogs()) {
            breeds.add(dog.getBreed().name());
        }
        String[] breedArray = breeds.toArray(new String[0]);
        Arrays.sort(breedArray);
        return breedArray;
    }

    /**
     * Performs a search based on the user's input criteria and updates the display with the results.
     */
    private void performSearch() {
        List<Dog> allDogs = controller.getAdoptableDogs(); // Get all adoptable dogs
        ACFilterPlanner planner = new ACFilterPlanner(allDogs); // Initialize the filter planner
        boolean nameOn = !nameField.getText().isEmpty();
        boolean ageOn = !ageField.getText().isEmpty();
        boolean sexOn = sexComboBox.getSelectedIndex() > 0;
        boolean breedOn = breedComboBox.getSelectedIndex() > 0 && !breedComboBox.getSelectedItem().equals("Any");
        boolean weightOn = !weightField.getText().isEmpty();
        boolean priceOn = !priceField.getText().isEmpty();

        String nameFilter = nameField.getText();
        String ageFilter = ageField.getText();
        String sexFilter = sexOn ? sexComboBox.getSelectedItem().toString().substring(0, 1).toLowerCase() : null;
        String breedFilter = breedOn ? breedComboBox.getSelectedItem().toString() : null; // Only apply breed filter if not "Any"
        String weightFilter = weightField.getText();
        String priceFilter = priceField.getText();

        String sortOn = sortComboBox.getSelectedItem().toString().toLowerCase();
        boolean ascending = true; // Sorting is always ascending in this implementation

        try {
            Stream<Dog> filteredDogs = planner.filter(
                    nameOn, nameFilter,
                    sexOn, sexFilter,
                    breedOn, breedFilter,
                    ageOn, ageFilter,
                    weightOn, weightFilter,
                    priceOn, priceFilter,
                    sortOn, ascending
            );

            List<Dog> resultList = filteredDogs.collect(Collectors.toList());

            updateDogList(resultList); // Update the UI with the filtered list

            if (resultList.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No dogs match the search criteria.", "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        resultList.size() + " dogs found matching the search criteria.",
                        "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }


        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears all search fields and resets the display to show all adoptable dogs.
     */
    private void clearSearch() {
        nameField.setText("");
        ageField.setText("");
        sexComboBox.setSelectedIndex(0);
        breedComboBox.setSelectedIndex(0);
        weightField.setText("");
        priceField.setText("");
        sortComboBox.setSelectedIndex(0); // Reset sorting to default
        controller.clearSearchFilters(); // Clear filters in the controller
        updateDogList(controller.getAdoptableDogs()); // Display all dogs again
    }

    /**
     * Updates the display with the given list of dogs.
     *
     * @param dogs The list of Dog objects to be displayed.
     */
    private void updateDogList(List<Dog> dogs) {
        dogGridPanel.removeAll(); // Clear the current display
        for (Dog dog : dogs) {
            JPanel dogPanel = createDogPanel(dog); // Create a panel for each dog
            dogGridPanel.add(dogPanel); // Add the dog panel to the grid
        }
        dogGridPanel.revalidate(); // Refresh the grid
        dogGridPanel.repaint();
    }

    /**
     * Creates and returns a JPanel representing a single dog's information.
     *
     * @param dog The Dog object to be represented in the panel.
     * @return A JPanel containing the dog's image and information.
     */
    private JPanel createDogPanel(Dog dog) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Load and display the image
        ImageIcon imageIcon = new ImageIcon(dog.getImage());
        Image image = imageIcon.getImage().getScaledInstance
                (100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        panel.add(imageLabel, BorderLayout.CENTER);

        // Dog information
        JLabel infoLabel = new JLabel("<html>" + dog.getName() + "<br>" +
                (dog.getSex().equals("m") ? "Male" : "Female") + "<br>" +
                dog.getBreed().name() + "<br>" + dog.getWeight() + " pounds<br>" +
                dog.getAge() + " years<br>$" + dog.getPrice() + "</html>");
        panel.add(infoLabel, BorderLayout.SOUTH);

        // Add/Remove from wishlist button
        JButton wishlistButton = new JButton(controller.getWishList().contains(dog)
                ? "Remove from Wishlist" : "Add to Wishlist");
        wishlistButton.addActionListener(e -> toggleWishlist(dog, wishlistButton));
        panel.add(wishlistButton, BorderLayout.NORTH);

        return panel;
    }

    /**
     * Toggles the wishlist status of a dog and updates the button text accordingly.
     *
     * @param dog The Dog object to be added to or removed from the wishlist.
     * @param button The JButton that triggered the action.
     */
    private void toggleWishlist(Dog dog, JButton button) {
        if (controller.getWishList().contains(dog)) {
            controller.removeFromWishList(dog.getID());
            button.setText("Add to Wishlist");
        } else {
            controller.addToWishList(dog);
            button.setText("Remove from Wishlist");
        }
    }

    /**
     * Displays a dialog showing the current wishlist of dogs.
     */
    private void showWishlist() {
        List<Dog> wishlist = controller.getWishList(); // Get the current wishlist
        JDialog wishlistDialog = new JDialog(this, "Wishlist", true);
        wishlistDialog.setSize(400, 300);
        JPanel wishlistPanel = new JPanel(new BorderLayout());
        JPanel dogsPanel = new JPanel(new GridLayout(0, 1));
        for (Dog dog : wishlist) {
            JPanel dogPanel = createDogPanel(dog); // Create a panel for each dog in the wishlist
            dogsPanel.add(dogPanel);
        }
        JScrollPane scrollPane = new JScrollPane(dogsPanel);
        wishlistPanel.add(scrollPane, BorderLayout.CENTER);

        JButton saveButton = new JButton("Save Wishlist");
        saveButton.addActionListener(e -> saveWishlist()); // Trigger save wishlist when the button is clicked
        wishlistPanel.add(saveButton, BorderLayout.SOUTH);

        wishlistDialog.add(wishlistPanel);
        wishlistDialog.setVisible(true);
    }

    /**
     * Opens a file chooser dialog and saves the current wishlist to a JSON file.
     */
    private void saveWishlist() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON files", "json"));
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
            }
            controller.saveList(filePath); // Save the wishlist to the specified file
            JOptionPane.showMessageDialog(this,
                    "Wishlist saved successfully to " + filePath);
        }
    }

    /**
     * The main method to start the Dog Adoption Program.
     * Initializes the model, controller, and GUI.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        AdoptionCenterModel model = new AdoptionCenterModel();
        ACController controller = new ACController(model);
        ApiUtil util = new ApiUtil();
        Map<String, Breed> breeds;
        try {
            breeds = util.parseBreeds(util.getBreeds());
            Dog dog1 = new Dog("1", "Alexander", "M",
                    breeds.get("Caucasian Shepherd Dog"), 24, 12.0, "1.png");
            Dog dog2 = new Dog("2", "Bella", "F",
                    breeds.get("Bouvier des Flandres"), 3, 65.0, "2.png",
                    1200.00, true);
            Dog dog3 = new Dog("3", "Charlie",
                    "M", breeds.get("Hanoverian Scenthound"), 5,
                    70.0, "3.png", 1500.00, true);
            Dog dog4 = new Dog("4", "Daisy", "F",
                    breeds.get("Tibetan Spaniel"), 4, 60.0,
                    "4.png", 1300.00, true);
            Dog dog5 = new Dog("5", "Ella", "F",
                    breeds.get("Border Collie"), 2, 25.0,
                    "5.png", 800.00, true);
            Dog dog6 = new Dog("6", "Finn", "M",
                    breeds.get("Curly-Coated Retriever"), 6, 50.0,
                    "6.png", 900.00, true);
            Dog dog7 = new Dog("7", "Ginger", "F",
                    breeds.get("Skye Terrier"), 7, 55.0,
                    "7.png", 1100.00, true);
            Dog dog8 = new Dog("8", "Hunter", "M",
                    breeds.get("Hokkaido"), 3, 85.0,
                    "8.png", 1700.00, true);
            Dog dog9 = new Dog("9", "Ivy", "F",
                    breeds.get("Hokkaido"), 4, 45.0,
                    "9.png", 1400.00, true);
            Dog dog10 = new Dog("10", "Jack", "M",
                    breeds.get("Japanese Terrier"), 5, 75.0,
                    "10.png", 1600.00, true);
            Dog dog11 = new Dog("11", "Kira", "F",
                    breeds.get("Caucasian Shepherd Dog"), 6, 65.0,
                    "11.png", 1200.00, true);
            Dog dog12 = new Dog("12", "Leo", "M",
                    breeds.get("Bouvier des Flandres"), 2, 20.0,
                    "12.png", 700.00, true);
            Dog dog13 = new Dog("13", "Molly", "F",
                    breeds.get("Hanoverian Scenthound"), 7, 15.0,
                    "13.png", 900.00, true);
            Dog dog14 = new Dog("14", "Nala", "F",
                    breeds.get("Tibetan Spaniel"), 5, 140.0,
                    "14.png", 0, false);
            Dog dog15 = new Dog("15", "Oscar", "M",
                    breeds.get("Border Collie"), 3, 8.0,
                    "15.png", 600.00, true);
            Dog dog16 = new Dog("16", "Penny", "F",
                    breeds.get("Curly-Coated Retriever"), 4, 5.0,
                    "16.png", 800.00, true);
            Dog dog17 = new Dog("17", "Quinn", "F",
                    breeds.get("Skye Terrier"), 6, 12.0,
                    "17.png", 1000.00, true);
            Dog dog18 = new Dog("18", "Rocky", "M",
                    breeds.get("Tibetan Spaniel"), 5, 60.0,
                    "18.png", 1300.00, true);
            Dog dog19 = new Dog("19", "Sophie", "F",
                    breeds.get("Hokkaido"), 7, 7.0,
                    "19.png", 850.00, true);
            Dog dog20 = new Dog("20", "Toby", "M",
                    breeds.get("Japanese Terrier"), 2, 30.0,
                    "20.png", 1100.00, true);
            Dog dog21 = new Dog("21", "Ursula", "F",
                    breeds.get("Caucasian Shepherd Dog"), 3, 70.0,
                    "21.png", 1500.00, true);
            Dog dog22 = new Dog("22", "Victor", "M",
                    breeds.get("Bouvier des Flandres"), 6, 80.0,
                    "22.png", 1700.00, true);
            Dog dog23 = new Dog("23", "Wendy", "F",
                    breeds.get("Hanoverian Scenthound"), 4, 65.0, "23.png");
            Dog dog24 = new Dog("24", "Xander", "M",
                    breeds.get("Tibetan Spaniel"), 5, 50.0,
                    "24.png", 1600.00, true);
            Dog dog25 = new Dog("25", "Yara", "F",
                    breeds.get("Border Collie"), 6, 20.0,
                    "25.png", 1000.00, true);
            Dog dog26 = new Dog("26", "Zane", "M",
                    breeds.get("Curly-Coated Retriever"), 3, 55.0,
                    "26.png", 1300.00, true);
            Dog dog27 = new Dog("27", "Amber", "F",
                    breeds.get("Skye Terrier"), 4, 60.0,
                    "27.png", 1500.00, true);
            Dog dog28 = new Dog("28", "Ben", "M",
                    breeds.get("Caucasian Shepherd Dog"), 2, 90.0,
                    "28.png", 1800.00, true);
            Dog dog29 = new Dog("29", "Cleo", "F",
                    breeds.get("Hokkaido"), 5, 50.0,
                    "29.png", 1200.00, true);
            Dog dog30 = new Dog("30", "Duke", "M",
                    breeds.get("Japanese Terrier"), 6, 40.0,
                    "30.png", 1300.00, true);
            model.addDog(dog1);
            model.addDog(dog2);
            model.addDog(dog3);
            model.addDog(dog4);
            model.addDog(dog5);
            model.addDog(dog6);
            model.addDog(dog7);
            model.addDog(dog8);
            model.addDog(dog9);
            model.addDog(dog10);
            model.addDog(dog11);
            model.addDog(dog12);
            model.addDog(dog13);
            model.addDog(dog14);
            model.addDog(dog15);
            model.addDog(dog16);
            model.addDog(dog17);
            model.addDog(dog18);
            model.addDog(dog19);
            model.addDog(dog20);
            model.addDog(dog21);
            model.addDog(dog22);
            model.addDog(dog23);
            model.addDog(dog24);
            model.addDog(dog25);
            model.addDog(dog26);
            model.addDog(dog27);
            model.addDog(dog28);
            model.addDog(dog29);
            model.addDog(dog30);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new DogAdoptionGUI(controller));
    }
}
