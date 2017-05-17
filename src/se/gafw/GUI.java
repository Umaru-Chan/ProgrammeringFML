package se.gafw;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import se.gafw.customer.Customer;
import se.gafw.customer.account.Account;
import se.gafw.customer.account.SavingsAccount;
import se.gafw.file.FileHandler;
import se.gafw.file.PasswordHandler;

public class GUI extends JFrame {
	private static final long serialVersionUID = 13376969L;

	// the "master-panel" that will contain all the other panels
	private JPanel panels;
	// the individual panels containing swing components for each window
	private JPanel loginPane, registrerPane, customerPane;
	private final static String LOGIN_PANE = "login", REGISTRER_PANE = "registrer", CUSTOMER_PANE = "customer";

	
	private JPasswordField password;
	private JTextField username;
	
	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 315);

		panels = new JPanel(new CardLayout());
		setContentPane(panels);

		//customer pane initialization, will be used when the customer loggs in
		customerPane = new JPanel();
		customerPane.setBorder(new EmptyBorder(5,5,5,5));
		customerPane.setLayout(null);
		panels.add(customerPane, CUSTOMER_PANE);
		
		//login
		loginPane = new JPanel();
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//default start pane
		loginPane.setLayout(null);
		panels.add(loginPane, LOGIN_PANE);
		((CardLayout)panels.getLayout()).show(panels, LOGIN_PANE);				

		username = new JTextField();
		username.setBounds(166, 84, 130, 26);
		loginPane.add(username);
		username.setColumns(10);

		password = new JPasswordField();
		password.setBounds(166, 131, 130, 26);
		loginPane.add(password);

		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(60, 89, 94, 16);
		loginPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(60, 136, 94, 16);
		loginPane.add(lblPassword);

		//only set text if there is any error(s)
		JLabel errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(60, 47, 250, 16);
		loginPane.add(errorLabel);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(ae -> 
		{
			//find the customer (if there is one)
			StringBuilder customerNumber = new StringBuilder();
			for(int i = 0; i < username.getText().length(); i++)
			{
				long current = Long.parseLong(username.getText().substring(i, i+1));
				current += 4; //4??
				current %= 10;//only use one digit
				customerNumber.append(current+"");
			}

			if(customerNumber.toString().length() != 12)
			{
				//throw new RuntimeException("Error parsing login data");
				//System.err.println("Error parsing login data!");

				//wot
				errorLabel.setText("wrong username or password");
				System.err.println("fel längd på användarnamnet lolol ... " + customerNumber.length());
				return;
			}

			//check to see if the customer exists
			if(!FileHandler.fileExists("bank/customers/" + customerNumber.toString() + "/" + customerNumber.toString() + ".ser"))
			{
				errorLabel.setText("wrong username or password");
				return;
			}

			PasswordHandler handler = new PasswordHandler();
			String passwordString = new String(password.getPassword());
			if(!handler.isUser(customerNumber.toString(), passwordString))
			{
				errorLabel.setText("wrong username or password");
				return;
			}

			//if this code is executed then the user exists
			//TODO login
			try{ generateCustomerScreen(Customer.getCustomer(customerNumber.toString())); }
			// this should never throw an error. In reality this is redundant code....
			// (all the error checking has already been done, the file exists and the password is correct)
			catch(Exception e){System.err.println("Error trying to read customer file!!"); e.printStackTrace();}
			
			//switch to the newly generated user page
			((CardLayout)panels.getLayout()).show(panels, CUSTOMER_PANE);
			setBounds(getX(), getY(), 645, 495);
			
			//System.out.println("successfully logged in!!");
		});
		btnLogin.setBounds(166, 169, 117, 29);
		loginPane.add(btnLogin);

		JButton btnRegistrer = new JButton("Register");
		btnRegistrer.addActionListener(ae -> 
		{
			CardLayout cl = (CardLayout)panels.getLayout();
			cl.show(panels, REGISTRER_PANE);
			setBounds(getX(), getY(), getWidth(), 500);
		});
		btnRegistrer.setBounds(166, 207, 117, 29);
		loginPane.add(btnRegistrer);

		JLabel welcome = new JLabel("welcome to nordeAlex!");
		welcome.setBounds(60, 35, 250, 16);
		loginPane.add(welcome);

		//register
		JLabel sscNumberLabel, firstNameLabel, lastNameLabel, adressLabel, zipLabel, cityLabel, phoneLabel, emailLabel;
		JTextField sscNumber, firstName, lastName, adress, zip, city, phone, email;

		registrerPane = new JPanel();
		panels.add(registrerPane, REGISTRER_PANE);
		registrerPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		registrerPane.setLayout(null);

		firstNameLabel = new JLabel("First name");
		firstNameLabel.setBounds(60, 80, 94, 16);
		registrerPane.add(firstNameLabel);

		firstName = new JTextField();
		firstName.setBounds(60, 100, 130, 26);
		registrerPane.add(firstName);

		lastNameLabel = new JLabel("Last name");
		lastNameLabel.setBounds(210, 80, 94, 16);
		registrerPane.add(lastNameLabel);

		lastName = new JTextField();
		lastName.setBounds(210, 100, 130, 26);
		registrerPane.add(lastName);

		sscNumberLabel = new JLabel("Social security");
		sscNumberLabel.setBounds(60, 135, 244, 16);
		registrerPane.add(sscNumberLabel);

		sscNumber = new JTextField();
		sscNumber.setBounds(60, 155, 190, 26);
		registrerPane.add(sscNumber);

		emailLabel = new JLabel("Email");
		emailLabel.setBounds(60, 190, 244, 16);
		registrerPane.add(emailLabel);

		email = new JTextField();
		email.setBounds(60, 210, 190, 26);
		registrerPane.add(email);

		phoneLabel = new JLabel("Phonenumber");
		phoneLabel.setBounds(60, 245, 244, 16);
		registrerPane.add(phoneLabel);

		phone = new JTextField();
		phone.setBounds(60, 265, 190, 26);
		registrerPane.add(phone);

		cityLabel = new JLabel("City");
		cityLabel.setBounds(60, 300, 244, 16);
		registrerPane.add(cityLabel);

		city = new JTextField();
		city.setBounds(60, 320, 190, 26);
		registrerPane.add(city);

		zipLabel = new JLabel("Zip");
		zipLabel.setBounds(60, 355, 40, 16);
		registrerPane.add(zipLabel);

		zip = new JTextField();
		zip.setBounds(60, 375, 75, 26);
		registrerPane.add(zip);

		adressLabel = new JLabel("Adress");
		adressLabel.setBounds(150, 355, 100, 16);
		registrerPane.add(adressLabel);

		adress = new JTextField();
		adress.setBounds(150, 375, 150, 26);
		registrerPane.add(adress);

		JButton finishRegistration = new JButton("Registrer");
		finishRegistration.setBounds(60, 420, 117, 29);
		finishRegistration.addActionListener(ae -> {
			//Error checking!!
			boolean error = false;

			//primitive e-mail check
			if(!email.getText().contains("@") || email.getText().length() < 4)
			{
				emailLabel.setForeground(Color.RED);
				error = true;
			}
			else emailLabel.setForeground(Color.BLACK);

			//check if ssc number contains anything other than numbers

			String ssc = sscNumber.getText();
			// yyyymmddxxxx - length = 12
			if(ssc.length() != 12){
				sscNumberLabel.setForeground(Color.RED);
				error = true;
			}
			else sscNumberLabel.setForeground(Color.BLACK);

			for(char c : ssc.toCharArray())
				if(Character.isAlphabetic(c)){
					sscNumberLabel.setForeground(Color.RED);
					error = true;
				}

			//check if zip contains anything but numbers and if the string is too short
			String zipString = zip.getText();
			if(zipString.length() < 4){
				zipLabel.setForeground(Color.RED);
				error = true;
			}
			for(char c : zipString.toCharArray())
				if(Character.isAlphabetic(c)){
					zipLabel.setForeground(Color.RED);
					error = true;
				}
				else zipLabel.setForeground(Color.BLACK);

			//check first and last name
			String first = firstName.getText(), last = lastName.getText();

			if(first.length() == 0){
				firstNameLabel.setForeground(Color.RED);
				error = true;
			}
			for(char c : first.toCharArray())
				if(!Character.isAlphabetic(c)){
					firstNameLabel.setForeground(Color.RED);
					error = true;
				}
				else firstNameLabel.setForeground(Color.BLACK);

			if(last.length() == 0){
				lastNameLabel.setForeground(Color.RED);
				error = true;
			}
			for(char c : last.toCharArray())
				if(!Character.isAlphabetic(c)){
					lastNameLabel.setForeground(Color.RED);
					error = true;
				}
				else lastNameLabel.setForeground(Color.BLACK);

			if(adress.getText().trim().length() == 0){
				adressLabel.setForeground(Color.RED);
				error = true;
			}
			else adressLabel.setForeground(Color.BLACK);

			//check phone
			String phoneNumber = phone.getText();

			for(char c : phoneNumber.toCharArray())
				if(Character.isAlphabetic(c))
				{
					phoneLabel.setForeground(Color.RED);
					error = true;
				}
			if(phoneNumber.length() < 3)
			{
				phoneLabel.setForeground(Color.RED);
				error = true;
			}else phoneLabel.setForeground(Color.BLACK);

			//check city
			if(city.getText().trim().length() < 2)
			{
				cityLabel.setForeground(Color.RED);
				error = true;
			}else cityLabel.setForeground(Color.BLACK);


			if(!error)
			{
				//generate customer number
				StringBuilder customerNumber = new StringBuilder();
				for(int i = 0; i < sscNumber.getText().length(); i++)
				{
					long current = Long.parseLong(sscNumber.getText().substring(i, i+1));
					current += 4; //4???!!!?!11!?
					current %= 10;//only use one digit
					customerNumber.append(current+"");
				}

				if(customerNumber.toString().length() != 12)
				{
					throw new RuntimeException("Error generationg customer number\nssc:\t\t" + sscNumber.getText() + "\ncustomerNumber\t:" + customerNumber.toString());
				}

				//check to see if the customer already exists
				if(FileHandler.fileExists("bank/customers/" + customerNumber.toString() + "/" + customerNumber.toString() + ".ser"))
				{
					System.err.println("This customer already exists!!");
					error = true;
					return;
				}

				//customer indata, customerNumber, firstName, lastName, ssc, adress, zip, city, phone, mail
				Customer newCustomer = new Customer(customerNumber.toString(), firstName.getText(), lastName.getText(), sscNumber.getText(), adress.getText(), zip.getText(), city.getText(), phone.getText(), email.getText());

				//email the password to the customer
				PasswordHandler pwHandler = new PasswordHandler();
				StringBuilder passwordString = new StringBuilder();
				Random random = new Random();
				char newChar;
				for(int i = 0; i < 15; i++)
				{
					//generate the random password
					
					do{
						newChar = (char)(random.nextInt(93) + 33);
					}while(newChar == '\n' || newChar == '\t' || newChar == ' ' || newChar == '\b' || newChar == '\f' || newChar == '\r');
					if(newChar == '\n') System.out.println("lmao");
					passwordString.append(newChar);
				}
				System.out.println(passwordString.toString()); //TODO email the pw
				if(!pwHandler.addUser(customerNumber.toString(), passwordString.toString()))
				{
					throw new RuntimeException("Failed to add user to password database.");
				}

				CardLayout cl = (CardLayout)panels.getLayout();
				cl.show(panels, LOGIN_PANE);
				username.setText(newCustomer.getAttribute("socialSecurity"));
			}
		});registrerPane.add(finishRegistration);

		JButton cancelButton = new JButton("cancel");
		cancelButton.setBounds(185,420,117,29);cancelButton.addActionListener(ae->
		{
			CardLayout cl = (CardLayout)panels.getLayout();
			cl.show(panels, LOGIN_PANE);
			setBounds(getX(), getY(), getWidth(), 315);
			
		});registrerPane.add(cancelButton);
		username.setText("199808065991");
		password.setText("C+&M3#PWP=]z?db");
	}
	
	private void generateCustomerScreen(Customer currentCustomer)
	{
		JButton logoutButton = new JButton("log out");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)panels.getLayout();
				cl.show(panels, LOGIN_PANE);
				setBounds(getX(), getY(), 430, 315);
				password.setText("");
				username.setText("");
			}
		});
		logoutButton.setBounds(21, 418, 117, 29);
		customerPane.add(logoutButton);
		
		JButton sendButton = new JButton("send");
		sendButton.setEnabled(false);
		sendButton.setBounds(150, 418, 117, 29);
		customerPane.add(sendButton);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setBounds(279, 418, 117, 29);
		customerPane.add(btnNewButton_2);
		
		
		//generate data for table
		String []   columnNames = {"not", "needed", "anymore"};
		Account[] 	accounts = currentCustomer.getAccounts();
		Object [][] data = new Object[accounts.length][columnNames.length];
		for(int i = 0; i < columnNames.length; i++)
		{
			for(int j = 0; j < accounts.length; j++)
			{
				switch(i)
				{
				case(0):data[j][i] = accounts[j] instanceof SavingsAccount ? "sparkonto" : "transaktionskonto"; break;
				case(1):data[j][i] = accounts[j].getFunds(); break;
				case(2):data[j][i] = accounts[j].getOpeningDate(); break;
				
				}
			}
		}


		String[] accountData = new String[accounts.length];
		for(int i = 0; i < accountData.length; i++)
			accountData[i] = accounts[i] instanceof SavingsAccount ? "Saving" : "Transaction";
		
		JLabel openingDate, funds, accountOpeningDate, accountFunds;
		accountFunds = new JLabel(); 
		accountOpeningDate = new JLabel();
		
		openingDate = new JLabel("opened:");
		openingDate.setBounds(240, 87, 60, 24);
		accountOpeningDate.setBounds(240, 107, 200, 24);
		funds = new JLabel("funds:");
		funds.setBounds(240, 150, 60, 24);
		accountFunds.setBounds(240, 170, 60, 24);
		
		JList<Account> accountList = new JList<Account>(accounts);
		accountList.addListSelectionListener((e) -> 
		{
			Account currentSelection = accountList.getSelectedValue();
			System.out.println(currentSelection.getFunds());
			if(currentSelection.toString().contains("Transaction"))
				sendButton.setEnabled(true);
			else sendButton.setEnabled(false);
			accountOpeningDate.setText(currentSelection.getOpeningDate().toString());
			accountFunds.setText(currentSelection.getFunds() + "");
			
		});
		
		
		JScrollPane scrollpane = new JScrollPane(accountList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollpane.setBounds(21, 87, 200, 242);
		customerPane.add(scrollpane);
		customerPane.add(funds);
		customerPane.add(openingDate);
		customerPane.add(accountOpeningDate);
		customerPane.add(accountFunds);
	}
}
