import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class Passenger {
    private String name;
    private String contact;
    private String address;

    public Passenger(String name, String contact, String address) {
        this.name = name;
        this.contact = contact;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }
}

class Flight {
    private String flightNumber;
    private int totalSeats;
    private int availableSeats;

    public Flight(String flightNumber, int totalSeats) {
        this.flightNumber = flightNumber;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            System.out.println("Seat booked successfully!");
        } else {
            System.out.println("Sorry, no seats available on this flight.");
        }
    }

    public void cancelSeat() {
        if (availableSeats < totalSeats) {
            availableSeats++;
            System.out.println("Reservation canceled successfully!");
        } else {
            System.out.println("No reservations to cancel.");
        }
    }
}

public class Air extends Frame implements ActionListener {
    private Flight flight1;
    private Flight flight2;
    private Label label;
    private Label nameLabel;
    private Label addressLabel;
    private Label contactLabel;

    private Label travelDatesLabel;
    private Label availableSeatsLabel1;
    private Label availableSeatsLabel2;

    private TextField nameField;
    private TextField contactField;
    private TextField addressField;

    private BufferedImage backgroundImage;

    private String departurePlace; // Variable to store departure place
    private String destinationPlace; // Variable to store destination place

    public Air() {
        flight1 = new Flight("FL001", 100);
        flight2 = new Flight("FL002", 150);

        try {
            backgroundImage = ImageIO.read(new File("g.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        label = new Label("Airline Reservation System", Label.CENTER);
        label.setBounds(50, 50, 200, 30);
        add(label);

        nameLabel = new Label("Name:");
        nameLabel.setBounds(50, 100, 50, 20);
        add(nameLabel);

        nameField = new TextField();
        nameField.setBounds(100, 100, 150, 20);
        add(nameField);

        addressLabel = new Label("Address:");
        addressLabel.setBounds(50, 130, 50, 20);
        add(addressLabel);

        addressField = new TextField();
        addressField.setBounds(100, 130, 150, 20);
        add(addressField);

        contactLabel = new Label("Contact:");
        contactLabel.setBounds(50, 160, 50, 20);
        add(contactLabel);

        contactField = new TextField();
        contactField.setBounds(100, 160, 150, 20);
        add(contactField);

        availableSeatsLabel1 = new Label("Available Seats: " + flight1.getAvailableSeats());
        availableSeatsLabel1.setBounds(50, 190, 200, 20);
        add(availableSeatsLabel1);

        availableSeatsLabel2 = new Label("Available Seats: " + flight2.getAvailableSeats());
        availableSeatsLabel2.setBounds(50, 220, 200, 20);
        add(availableSeatsLabel2);

        Button bookButton = new Button("Book a seat");
        bookButton.setBounds(50, 250, 150, 30);
        bookButton.addActionListener(this);
        add(bookButton);

        Button cancelButton = new Button("Cancel reservation");
        cancelButton.setBounds(50, 280, 150, 30);
        cancelButton.addActionListener(this);
        add(cancelButton);

        Button viewButton = new Button("View available seats");
        viewButton.setBounds(50, 310, 150, 30);
        viewButton.addActionListener(this);
        add(viewButton);

        setTitle("Airline Reservation System");
        setSize(300, 500);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("Book a seat")) {
            departurePlace = JOptionPane.showInputDialog(null, "Enter departure place:", "Departure Place", JOptionPane.QUESTION_MESSAGE);
            destinationPlace = JOptionPane.showInputDialog(null, "Enter destination place:", "Destination Place", JOptionPane.QUESTION_MESSAGE);

            if (departurePlace != null && destinationPlace != null) {
                String flightNumber = (String) JOptionPane.showInputDialog(null, "Enter flight number (FL001 or FL002):", "Book a seat", JOptionPane.QUESTION_MESSAGE, null, new String[]{"FL001", "FL002"}, "FL001");

                if (flightNumber != null) {
                    String name = nameField.getText();
                    String contact = contactField.getText();
                    String address = addressField.getText();

                    if (!name.isEmpty() && !contact.isEmpty()) {
                        Passenger passenger = new Passenger(name, contact, address);
                        if (flightNumber.equals("FL001")) {
                            flight1.bookSeat();
                            availableSeatsLabel1.setText("Available Seats: " + flight1.getAvailableSeats());
                        } else if (flightNumber.equals("FL002")) {
                            flight2.bookSeat();
                            availableSeatsLabel2.setText("Available Seats: " + flight2.getAvailableSeats());
                        }

                        confirmBooking(passenger, flightNumber, departurePlace, destinationPlace);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please provide your name and contact information.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else if (command.equals("Cancel reservation")) {
            String cancelFlightNumber = (String) JOptionPane.showInputDialog(null, "Enter flight number to cancel reservation (FL001 or FL002):", "Cancel reservation", JOptionPane.QUESTION_MESSAGE, null, new String[]{"FL001", "FL002"}, "FL001");
            if (cancelFlightNumber != null) {
                if (cancelFlightNumber.equals("FL001")) {
                    flight1.cancelSeat();
                    availableSeatsLabel1.setText("Available Seats: " + flight1.getAvailableSeats());
                } else if (cancelFlightNumber.equals("FL002")) {
                    flight2.cancelSeat();
                    availableSeatsLabel2.setText("Available Seats: " + flight2.getAvailableSeats());
                }
            }
       
} else if (command.equals("View available seats")) {
            String message = "Available Seats:\n";
            message += "Flight FL001: " + flight1.getAvailableSeats() + "\n";
            message += "Flight FL002: " + flight2.getAvailableSeats() + "\n";
            JOptionPane.showMessageDialog(null, message, "Available Seats", JOptionPane.INFORMATION_MESSAGE);
        }
    }




   private void confirmBooking(Passenger passenger, String flightNumber, String departurePlace, String destinationPlace) {
    String message = "Booking Confirmed!\n";
    message += "Name: " + passenger.getName() + "\n";
    message += "Contact: " + passenger.getContact() + "\n";
message += "Address: " + passenger.getAddress() + "\n";
    message += "Flight Number: " + flightNumber + "\n";
    message += "Departure Place: " + departurePlace + "\n";
    message += "Destination Place: " + destinationPlace;
    JOptionPane.showMessageDialog(null, message, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
}

    public static void main(String[] args) {
        new Air();


 // Existing code for canceling reservation and viewing available seats remains the same
    }
}