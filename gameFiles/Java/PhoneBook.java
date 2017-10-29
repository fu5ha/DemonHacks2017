import java.io.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class PhoneBook {
	private List<Contact> contacts;

	PhoneBook() {
		contacts = new ArrayList<>();
		loadContacts();
	}

	// load txt file with contacts
	public void loadContacts() {
		String tokens[] = null;
		String firstName, lastName, phone;
		try {
			FileReader fr = new FileReader("contacts.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine();
			while (line != null) {
				tokens = line.split(",");
				firstName = tokens[0];
				lastName = tokens[1];
				phone = tokens[2];
				Contact p = new Contact(firstName, lastName, phone);
				contacts.add(p);
				line = br.readLine();
			}
			br.close();
			fr.close();
		} catch (IOException ioEx) {
			System.out.println(ioEx);
		}
	}

	public void saveContacts() {
		try {
			Contact p;
			String line;
			PrintStream fileWriter = new PrintStream("contacts.txt");
			for (int i = 0; i < contacts.size(); i++) {
				p = (Contact) contacts.get(i);
				line = p.firstName + "," + p.lastName + "," + p.phone;
				// writes line to file (contacts.txt)
				fileWriter.println(line);
			}
			// fileWriter.flush();
			fileWriter.close();

		} catch (IOException ioEx) {
			System.out.println(ioEx);
		}
	}

	public void addPerson() {
		System.out.println("Enter first name: ");
		Scanner input = new Scanner(System.in);
		String firstName = input.nextLine().trim();

		System.out.println("Enter last name: ");
		String lastName = input.nextLine().trim();

		System.out.println("Enter phone number: ");
		String phone = input.nextLine().trim();

		// construct new person object
		Contact newContact = new Contact(firstName, lastName, phone);
		// add the above PersonInfo object to arraylist
		contacts.add(newContact);
	}

	// delete (by index) person record by name by iterating over arraylist
	public void deleteContact(String fullName) {
		boolean check = false;
		for (int i = 0; i < contacts.size(); i++) {
			Contact p = (Contact) contacts.get(i);
			if (fullName.equals(p.firstName + " " + p.lastName)) {
				contacts.remove(i);
				check = true;
			}
		}
		if (check) {
			System.out.println("Contact deleted!\n");
		} else {
			System.out.println("No match found!\n");
		}
		Contact.count = contacts.size();
	}

	public void searchContact(String name) {
		boolean check = false;
		for (int i = 0; i < contacts.size(); i++) {
			Contact p = (Contact) contacts.get(i);
			if (name.equals(p.firstName)) { // first name li samo?
				p.printContact();
				check = true;
			}
		}
		if (!check) {
			System.out.println("No match found!");
		}
	}

	public void viewAllContacts() {
		System.out.println("==========================");
		contacts.stream().sorted((x, y) -> x.firstName.toLowerCase().compareTo(y.firstName.toLowerCase()))
				.forEach(x -> System.out.println(x.firstName + " " + x.lastName + " " + x.phone));
		System.out.println("==========================");
	}

}
