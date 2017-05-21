package se.gafw;

import java.awt.CardLayout;
import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

	//needs to be used across several methods, therefore made members of the class instead of temporary variables
	private JPasswordField password;
	private JTextField username;
	
	// when logged in this will hold the current account
	private Account currentSelection;
	
	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 430, 315);
		setSize(430, 315);
		setLocationRelativeTo(null);

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
			setLocationRelativeTo(null);
			
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

			if(phoneNumber.length() < 3)
			{
				phoneLabel.setForeground(Color.RED);
				error = true;
			}else phoneLabel.setForeground(Color.BLACK);
			for(char c : phoneNumber.toCharArray())
				if(Character.isAlphabetic(c))
				{
					phoneLabel.setForeground(Color.RED);
					System.out.println("lmao");
					error = true;
				}

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
				setSize(430, 315);
				setLocationRelativeTo(null);
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
		
		
		//TEMP
		username.setText("199808065990");
		password.setText("tW[&SX|w-4f>x<\"");
		// usname = 189805962492
		//     pw = {zGpOKF"`|+7[Jr
		//     a1 = 523249306838
	}
	
	private void generateCustomerScreen(Customer currentCustomer)
	{
		JButton logoutButton = new JButton("log out");
		logoutButton.addActionListener(ae -> {
			for(int i = 0; i < customerPane.getComponentCount();)customerPane.remove(customerPane.getComponent(i));
			CardLayout cl = (CardLayout)panels.getLayout();
			cl.show(panels, LOGIN_PANE);
			setBounds(getX(), getY(), 430, 315);
			setLocationRelativeTo(null);
			password.setText("");
			username.setText("");
		});
		logoutButton.setBounds(21, 418, 117, 29);
		customerPane.add(logoutButton);
		
		JButton sendButton = new JButton("send");
		sendButton.setEnabled(false);
		sendButton.setBounds(150, 418, 117, 29);
		customerPane.add(sendButton);
		
		JButton newAccountButton = new JButton("new account");
		newAccountButton.setBounds(279, 418, 117, 29);
		newAccountButton.setEnabled(false);
		newAccountButton.setToolTipText("work in progress!"); //TODO 
		customerPane.add(newAccountButton);
		
		JLabel welcomeLabel = new JLabel("Welcome " + currentCustomer.getAttribute("firstName") + " " + currentCustomer.getAttribute("lastName")+"!");
		welcomeLabel.setBounds(21, 20, 150, 25);
		
		Account[] 	accounts = currentCustomer.getAccounts();
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
		
		JButton showLogButton = new JButton("show log");
		showLogButton.setEnabled(false);
		showLogButton.addActionListener(ae -> 
		{
			try {
				JTextArea ta = new JTextArea(20, 75);
				ta.read(new FileReader("bank/customers/"+ currentCustomer.getAttribute("customerNumber") +"/accounts/"+accountList.getSelectedValue().getAccountNumber()+"_LOG.txt"), null);
				ta.setEditable(false);
				JOptionPane.showMessageDialog(this, new JScrollPane(ta));
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		});
		showLogButton.setBounds(408, 418, 117, 29);
		customerPane.add(showLogButton);
		
		
		accountList.addListSelectionListener(ae -> 
		{
			showLogButton.setEnabled(true);
			currentSelection = accountList.getSelectedValue();
			//System.out.println(currentSelection.getFunds());
			if(currentSelection.toString().contains("Transaction"))
				sendButton.setEnabled(true);
			else sendButton.setEnabled(false);
			accountOpeningDate.setText(currentSelection.getOpeningDate().toString());
			accountFunds.setText(currentSelection.getFunds() + "");
			
		});
		
		sendButton.addActionListener(ae -> {
			JFrame sendFrame = new JFrame("send");
			JPanel panel = new JPanel();
			
			JTextField ammountField = new JTextField();
			ammountField.setBounds(10, 25, 100, 25);
			JLabel ammountLabel = new JLabel("ammount");
			ammountLabel.setBounds(10, 5, 100, 25);

			JTextField messageField = new JTextField();
			messageField.setBounds(10, 75, 100, 25);
			JLabel messageLabel = new JLabel("message");
			messageLabel.setBounds(10, 50, 100, 25);
			
			
			JTextField targetField = new JTextField();
			targetField.setBounds(10, 125, 100, 25);
			JLabel targetLabel = new JLabel("recipient");
			targetLabel.setBounds(10, 105, 100, 25);
			
			JButton confirmButton = new JButton("send");
			confirmButton.setBounds(10, 165, 117, 29);
			confirmButton.addActionListener(e -> {
				double ammount;
				try{
					ammount = Double.parseDouble(ammountField.getText());
				}catch(Exception ex){
					ammountLabel.setForeground(Color.RED);
					return;
				}
				
				long target;
				try{
					target = Long.parseLong(targetField.getText());
				}catch(Exception ex){
					targetLabel.setForeground(Color.RED);
					return;
				}
				
				if(!currentSelection.send(target, ammount, messageField.getText()))
				{
					//if there was an error sending
					sendFrame.setVisible(false);
					sendFrame.dispose();
					JOptionPane.showMessageDialog(this, "there was an error sending the money", "error", JOptionPane.ERROR_MESSAGE);
				}else{
					sendFrame.setVisible(false);
					sendFrame.dispose();
					JOptionPane.showMessageDialog(this, "successfully transfered " + ammount + "to " + target, "", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			JButton cancelButton = new JButton("cancel");
			cancelButton.setBounds(10, 190, 117, 29);
			cancelButton.addActionListener(ea->
			{
				sendFrame.setVisible(false);
				sendFrame.dispose();
			});
			
			panel.add(cancelButton);
			panel.add(confirmButton);
			panel.add(targetField);
			panel.add(messageField);
			panel.add(ammountField);
			panel.add(targetLabel);
			panel.add(messageLabel);
			panel.add(ammountLabel);
			
			panel.setLayout(null);
			panel.setSize(140, 240);
			
			sendFrame.setResizable(false);
			sendFrame.setVisible(true);
			sendFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
			sendFrame.add(panel);
			sendFrame.setSize(140,240);
			sendFrame.setLocationRelativeTo(this);
		});
		
		JScrollPane scrollpane = new JScrollPane(accountList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollpane.setBounds(21, 87, 200, 242);
		customerPane.add(welcomeLabel);
		customerPane.add(scrollpane);
		customerPane.add(funds);
		customerPane.add(openingDate);
		customerPane.add(accountOpeningDate);
		customerPane.add(accountFunds);
	}
}
