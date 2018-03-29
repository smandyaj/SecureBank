package edu.asu.sbs.controllers;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.Email;
import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.ExternalUserSearch;
import edu.asu.sbs.model.ExternalUserTransactionResult;
import edu.asu.sbs.model.InternalUser;
import edu.asu.sbs.model.ModifiedUser;
import edu.asu.sbs.model.Otp;
import edu.asu.sbs.model.SystemLog;
import edu.asu.sbs.model.Transaction;
import edu.asu.sbs.model.TransferEmailParameter;
import edu.asu.sbs.model.otpResult;
import edu.asu.sbs.services.AccountService;
import edu.asu.sbs.services.BCryptHashService;
import edu.asu.sbs.services.EmailService;
import edu.asu.sbs.services.ExternalUserService;
import edu.asu.sbs.services.ModifiedUserService;
import edu.asu.sbs.services.OTPService;
import edu.asu.sbs.services.SystemLogService;
import edu.asu.sbs.services.TransactionService;
import edu.asu.sbs.services.TransferPhoneParameter;

@Controller
public class ExternalUserController {
	@Autowired
	private ExternalUserService externalUserService;

	@Autowired
	private ModifiedUserService modifiedUserService;

	@Autowired
	private OTPService otpService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private BCryptHashService hashService;
	
	@Autowired		
	private SystemLogService systemLogService;
	
	@ModelAttribute
	public ExternalUserSearch getExternalUser() {
		return new ExternalUserSearch();
	}

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/customer/home", method = RequestMethod.GET)
	public String getHome(ModelMap model) {
		ExternalUser user = externalUserService.findByUserName();
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("customerId", user.getCustomerId());
		return "customerHome";
	}

	@RequestMapping(value = "/customer/profile", method = RequestMethod.GET)
	public ModelAndView getCustomerProfile() {
		ModelAndView modelAndView = new ModelAndView("customer-profile");
		modelAndView.addObject("customerForm", externalUserService.findByUserName());
		System.out.println("user is: " + externalUserService.findByUserName().getCustomerId());
		return modelAndView;

	}

	@RequestMapping(value = "/customer/modify-profile", method = RequestMethod.POST)
	public String addModifiedProfile(@ModelAttribute ExternalUser externalUser) {
		ModelAndView modelAndView = new ModelAndView("customer-profile");
		System.out.println("User name to be modified ::" + externalUser.getFirstName());
		System.out.println("User details ::" + externalUser);
		ModifiedUser modUser = new ModifiedUser(externalUser.getCustomerId(), externalUser.getFirstName(),
				externalUser.getLastName(), externalUser.getEmailId(), externalUser.getPhone(),
				externalUser.getCustomerAddress(), 0, "pending", 0, externalUser.getUserName());
		modifiedUserService.addUser(modUser);
		modelAndView.addObject("msg", "Profile has been submitted for approval");
		// return modelAndView;
		return "redirect:/customer-profile";
	}

	@RequestMapping(value = "/customer/credit-debit", method = RequestMethod.GET)
	public String getCreditDebit(ModelMap model) {
		ExternalUser user = externalUserService.findByUserName();
		model.put("user", user);

		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		return "creditdebit";
	}

	@RequestMapping(value = "/customer/credit-debit", method = RequestMethod.POST)
	public String postCreditDebit(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction transaction, BindingResult result, RedirectAttributes attr) {
		int transCritical = 0;
		// Get user details
		ExternalUser user = externalUserService.findByUserName();
		model.put("user", user);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SystemLog systemLog = new SystemLog(timestamp, user.getFirstName(), user.getLastName(), "credit/debit");
		systemLogService.addSystemLog(systemLog);
		
		// Get user accounts and other data for display
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("fullname", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + user.getFirstName());

		double amount = Double.parseDouble(request.getParameter("amount"));

		boolean isCritical = transactionService.isTransferCritical(amount);

		if (isCritical) {
			transCritical = 1;
		}
		// set transaction type
		int transactionType = 0;
		if (request.getParameter("type").equalsIgnoreCase("credit")) {
			transactionType = 1;
		}
		// create the transaction object
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		transaction = new Transaction(transactionType, currentTimestamp, user.getCustomerId(), user.getCustomerId(),
				amount, 0, 0, "pending", Integer.parseInt(request.getParameter("number")),
				Integer.parseInt(request.getParameter("number")), 0, transCritical);

		// If account is empty or null, skip the account service check
		Account account = accountService.getAccountByNumber(Integer.parseInt(request.getParameter("number")));

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			System.out.println("Someone tried credit/debit functionality for some other account. Details:");
			System.out.println("Credit/Debit Acc No: " + request.getParameter("number"));
			System.out.println("Customer ID: " + user.getCustomerId());
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Please try again or contact the bank.");
			return "redirect:/customer/credit-debit";
		}

		// Check if Debit amount is < balance in the account
		if (request.getParameter("type").equalsIgnoreCase("debit") && (account.getAccountBalance() < amount)) {
			attr.addFlashAttribute("failureMsg",
					"Could not process your transaction. Debit amount cannot be higher than account balance");
			return "redirect:/customer/credit-debit";
		}

		// OTP only for critical transactions
		if (isCritical) {

			String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
			System.out.println("Got session id: " + sessionId);
			Random range = new Random();
			int rand = range.nextInt(Integer.MAX_VALUE);
			String otp = "";
			try {
				otp = otpService.generateOTP(8).toString();
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// call the email Service
			
			Email email = new Email(user.getEmailId(), "SBS OTP For Transaction", "This OTP will expire in 2 min : " + otp);
			emailService.sendEmail(email);
			transaction.setStatus(4);
			transaction.setStatus_quo("otp");
			int transactionId = transactionService.addTransaction(transaction);
			Otp otpObj = new Otp(hashService.getBCryptHash(otp), currentTimestamp,user.getCustomerId(), transactionId, "creditdebit");
			int otpId = otpService.addOTP(otpObj);


			String content = "You have made a new request to for Credit / Debit "
					+ "The payment request will expire in the next 10 minutes from now.\n\n"
					+ "Please use the following OTP to accept the payment: " + "\n\n"
					+ "You can accept the payment or cancel it.";

			model.addAttribute("heading", "SBS Credit / Debit Funds");
			model.addAttribute("title", "Verify Transaction For Credit/Debit");
			model.addAttribute("transactionId", transactionId);
			model.addAttribute("type", "creditdebit");
			model.addAttribute("otpId", otpId); 
			
			return "otp";

		}

		transactionService.addTransaction(transaction);

		attr.addFlashAttribute("successMsg",
				"Transaction completed successfully. Transaction should show up on your account shortly after bank approval.");

		// redirect to the credit debit view page
		return "redirect:/customer/credit-debit";
	}
	
	
	@RequestMapping(value = "/customer/process-otp", method = RequestMethod.POST)
	public String processOTP(ModelMap model, HttpServletRequest request,RedirectAttributes attr) {
		System.out.println("process OTP");
		String otp = request.getParameter("otp");
		String otpId = request.getParameter("otpId");
		//String originalOtp = otpService.getOTP(transactionId);
		String transactionId = request.getParameter("transactionId");
		String type = request.getParameter("type");
		String redirectUrl = "redirect:/opt";
		/// please create the otp object and save
		System.out.println("otp >>>" + otp);
		System.out.println("otpId >>>" + otpId);
		System.out.println("transaction Id >>>" + transactionId);
		System.out.println("type >>>" + type);
		if (type == null) {

			attr.addFlashAttribute("failureMsg",
					"OTP could not be verified. Please try again");
			return redirectUrl;
		}
		
		
		Otp otpObj = otpService.getOTP(otpId);
		if( otpObj == null) {
			System.out.println("It is null");
		}
		System.out.println("hashOtp from the db >>>"+ otpObj.getTransactionId());
		if( ! hashService.checkBCryptHash(otp, otpObj.getOtp())) {
			System.out.println("OTP doesnt match");
			return "redirect:/customer/home";
		}
		
		System.out.println("OTP matched and updating the transactions");
		// get the transaction using the id and change the status to pending
		Transaction transaction = transactionService.getTransactionById(Integer.parseInt(transactionId));
		transaction.setStatus(0);
		transaction.setStatus_quo("pending");
		transactionService.updateTransaction(transaction);
		return "redirect:/customer/home";
	}

	@RequestMapping(value = "/customer/customer-transaction", method = RequestMethod.GET)
	public String returnCustomerTransactionPage(ModelMap model) {
		System.out.println("Fetch the user: " + externalUserService.findByUserName().getUserName());

		ExternalUser user = externalUserService.findByUserName();
		model.put("user", user);
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("title", "Welcome " + user.getFirstName());
		model.addAttribute("fullname", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("customerId", user.getCustomerId());
		return "external_usertransaction";

	}

	@RequestMapping(value = "/customer/customer-transaction", method = RequestMethod.POST)
	public ModelAndView returnCustomerTransactionPage(@ModelAttribute ExternalUser customer) {
		ModelAndView modelAndView = new ModelAndView("external_usertransaction");
		System.out.println("Fetching the user details " + customer.getCustomerId());
		ExternalUser externalUser = externalUserService.findUserById(customer.getCustomerId());
		// ExternalUser extUser=externalUserService.findByUserName();
		List<Account> accounts = accountService.getAccountByCustomerId(externalUser.getCustomerId());
		System.out.println("User accounts ::" + externalUser.getLastName());
		modelAndView.addObject("accounts", accounts);
		modelAndView.addObject("user", externalUser);
		return modelAndView;
	}

	@RequestMapping(value = "/customer/addTransactionSuccess", method = RequestMethod.POST)
	public String processTransaction(ModelMap model, HttpServletRequest request,
			@ModelAttribute("transaction") Transaction senderTransaction, BindingResult result,
			RedirectAttributes attr) {

		boolean isTransferAccountValid;
		int transCritical = 0;
		// get account of the sender
		Account account = accountService.getAccountByNumber(senderTransaction.getSenderAccNumber());

		// Exit the transaction if Account doesn't exist
		if (account == null) {
			System.out.println("Someone tried credit/debit functionality for some other account. Details:");
			// System.out.println("Credit/Debit Acc No: " + request.getParameter("number"));
			// add the return attributes
			model.put("msg", "The given account doesnt exists");
			return "redirect:/customer/home";
		}

		int receiverAccNumber = 0;
		BigInteger receiverPhoneNum = BigInteger.ZERO;
		String receiverEmailId = "";
		if (request.getParameter("type").equalsIgnoreCase("internal")) {
			// if it is internal
			receiverAccNumber = Integer.parseInt(request.getParameter("receiverAccNumber"));
			System.out.println("internal transfer");
		} else {
			// if it us external
			String type = request.getParameter("modes");
			System.out.println("Mode" + type);
			if (type.equals("receiverEmailId")) {
				System.out.println("Email id" + request.getParameter("receiverEmailId"));
				receiverEmailId = request.getParameter("receiverEmailId");
				System.out.println("Transfer through email.");
			} else if (type.equals("receiverPhoneNumber")) {
				System.out.println("Phone Number:" + request.getParameter("receiverPhoneNumber"));
				receiverPhoneNum = new BigInteger(request.getParameter("receiverPhoneNumber"));
				System.out.println("Transfer through phone.");
			} else if (type.equals("receiverAccNumberExt")) {
				System.out.println("Account Number:" + request.getParameter("receiverAccNumberExt"));
				receiverAccNumber = Integer.parseInt(request.getParameter("receiverAccNumberExt"));
				System.out.println("Transfer through accountNum.");
			}
			/*
			 * receiverAccNumber =
			 * Integer.parseInt(request.getParameter("receiverAccNumberExt"));
			 * System.out.println("external transfer");
			 */

		}

		if (receiverAccNumber == Integer.parseInt(request.getParameter("senderAccNumber"))) {
			// same account transfer not allowed
			return "redirect:/customer/home";
		}
		List<Account> accList = null;
		Account toAccount = null;
		ExternalUser receiver = null;
		if (receiverAccNumber != 0) {
			toAccount = accountService.getAccountByNumber(receiverAccNumber);
		} else if (receiverEmailId != null && !receiverEmailId.isEmpty()) {
			System.out.println("in mail");
			receiver = externalUserService.findByEmail(receiverEmailId);
			accList = accountService.getAccountByCustomerId(receiver.getCustomerId());
			for (int i = 0; i < accList.size(); i++) {
				if (accList.get(i).getAccountType() == 0) {
					toAccount = accList.get(i);
					break;
				}
			}
			receiverAccNumber = toAccount.getAccountId();
			/*
			 * receiverAccNumber=toAccount.getAccountId();
			 * System.out.println("acc num="+receiverAccNumber);
			 */

		} else if (receiverPhoneNum != BigInteger.ZERO) {
			receiver = externalUserService.findByPhone(receiverPhoneNum);
			accList = accountService.getAccountByCustomerId(receiver.getCustomerId());
			for (int i = 0; i < accList.size(); i++) {
				if (accList.get(i).getAccountType() == 0) {
					toAccount = accList.get(i);
					break;
				}
			}
			receiverAccNumber = toAccount.getAccountId();
		}
		// get the to account details

		if (toAccount != null) {
			isTransferAccountValid = true;
		} else {
			isTransferAccountValid = false;
		}

		System.out.println("isTransferAccountValid: " + isTransferAccountValid);
		if (isTransferAccountValid) {

			double amount = Double.parseDouble((request.getParameter("amount")));
			if (transactionService.isTransferCritical(amount)) {
				transCritical = 1;
			}
			System.out.println("receiverAccNumber: " + receiverAccNumber);

			boolean isCritical = transactionService.isTransferCritical(amount);

			// create the transaction object
			int status = 0;
			String status_quo = "pending";

			// java.sql.Date date = new
			// java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

			// Date date1= new Date(Calendar.getInstance().getTime().getTime());
			senderTransaction = new Transaction(0, currentTimestamp, account.getCustomerId(), toAccount.getCustomerId(),
					amount, status, 1, status_quo, Integer.parseInt(request.getParameter("senderAccNumber")),
					receiverAccNumber, 0, transCritical);
			System.out.println("Sender Transaction created: " + senderTransaction);

			// Check if Debit amount is < balance in the account
			if (account.getAccountBalance() - amount <= 0) {
				System.out.println("No balance in account");
				return "redirect:/customer/home";
			}

			Transaction receiverTransaction = new Transaction(1, currentTimestamp, account.getCustomerId(),
					toAccount.getCustomerId(), amount, status, 0, status_quo,
					Integer.parseInt(request.getParameter("senderAccNumber")), receiverAccNumber, 0, transCritical);

			System.out.println("Receiver Transaction created: " + receiverTransaction);

			try {
				senderTransaction.setStatus(1);
				senderTransaction.setStatus_quo("approved");
				senderTransaction.setAuth(1);
				transactionService.addTransaction(senderTransaction);
				transactionService.addTransaction(receiverTransaction);

			} catch (Exception e) {
				System.out.println("Transfer unsuccessful. Please try again or contact the admin.");
				return "redirect:/employee/home";
			}

			System.out
					.println("Transaction completed successfully. Transaction should show up on the user account now");

		} else {
			System.out.println("Transfer unsucessful. Please try again or contact the admin");
		}

		// redirect to the view page
		return "redirect:/customer/home";
	}

	@RequestMapping(value = "/customer/deleteTransaction/{id}", method = RequestMethod.GET)
	public ModelAndView optSuccess(@PathVariable("id") int transactionId) {
		Transaction t = transactionService.get(transactionId);
		transactionService.deleteTransaction(t);
		return new ModelAndView("customerHome");
	}

	@RequestMapping(value = "/customer/bankStatements", method = RequestMethod.GET)
	public String getBankStatements(ModelMap model) {
		ExternalUser user = externalUserService.findByUserName();
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("accounts", accounts);
		model.addAttribute("customerId", user.getCustomerId());
		// modelAndView.addObject("customerForm", externalUserService.findByUserName());
		System.out.println("user is: " + externalUserService.findByUserName().getCustomerId());
		return "bankStatements";

	}
	
	
	@RequestMapping(value = "/customer/bankStatements", method = RequestMethod.POST)
	public String postStatements(ModelMap model, HttpServletRequest request, RedirectAttributes attr) {
		System.out.println("Entering the bank statements for POST");
		ExternalUser user = externalUserService.findByUserName();
		model.put("user", user);
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SystemLog systemLog = new SystemLog(timestamp, user.getFirstName(), user.getLastName(), "bank-statements");
		systemLogService.addSystemLog(systemLog);
		
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("title", "Account Statements " + user.getFirstName());
		model.addAttribute("fullname", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);

		Account account = accountService.getAccountByNumber(Integer.parseInt(request.getParameter("accountId")));
		// Exit the transaction if Account doesn't exist
		if (account == null) {
			System.out.println("Someone tried statements functionality for some other account. Details:");
			System.out.println("no Acc No: " + request.getParameter("accountId"));
			System.out.println("Customer ID: " + user.getCustomerId());
			attr.addFlashAttribute("statementFailureMsg", "Could not process your request. Please try again later.");
			return "redirect:/customer/bankStatements";
		}

		System.out.println("yes Acc No: " + account.getAccountId() + " " + account.getCustomerId());
		List<Transaction> statements = transactionService
				.getTransactionsForAccount(Integer.parseInt(request.getParameter("accountId")));
		System.out.println("Acc No: " + statements.get(0).getTransactionId());
		model.addAttribute("statements", statements);
		model.addAttribute("accNumber", request.getParameter("accountId"));

		return "bankStatements";
	}
	
	@RequestMapping(value = "/customer/bankStatements/download", method = RequestMethod.POST)
	public ModelAndView postDownloadStatement(ModelMap model, HttpServletRequest request, RedirectAttributes attr) {
		System.out.println("in download");

		ExternalUser user = externalUserService.findByUserName();
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.put("user", user);

		// If account is empty or null, skip the account service check
		Account account = accountService.getAccountByNumber(Integer.parseInt(request.getParameter("accountId")));

		if (account == null) {
			System.out.println("In NULL...Someone tried view statement functionality for some other account. Details:");
			System.out.println("null Acc No: " + request.getParameter("accountId"));
			System.out.println("Customer ID: " + user.getCustomerId());
			attr.addFlashAttribute("statementFailureMsg", "Could not process your request. Please try again later.");
			return new ModelAndView("redirect:/customer/bankStatements");
		}

		System.out.println("working Acc No: " + account.getAccountId() + " " + account.getCustomerId());
		List<Transaction> statements = transactionService
				.getTransactionsForAccount(Integer.parseInt(request.getParameter("accountId")));
		System.out.println("working trans No: " + statements.get(0).getTransactionId());
		model.addAttribute("transactions", statements);
		model.addAttribute("accNumber", request.getParameter("accountId"));
		model.addAttribute("title", "Account Statements");
		model.addAttribute("account", account);
		model.addAttribute("statementName", "Your Statements");

		return new ModelAndView("statementFile", "model", model);
	}
	
	@RequestMapping(value = "/customer/request-money", method = RequestMethod.GET)
	public String returnCustomerRequestMoney(ModelMap model) {
		System.out.println("Fetch the user: " + externalUserService.findByUserName().getUserName());

		ExternalUser user = externalUserService.findByUserName();
		model.put("user", user);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SystemLog systemLog = new SystemLog(timestamp, user.getFirstName(), user.getLastName(), "request-money");
		systemLogService.addSystemLog(systemLog);
		List<Account> accounts = accountService.getAccountByCustomerId(user.getCustomerId());
		model.addAttribute("title", "Welcome " + user.getFirstName() + " " + user.getLastName());
		model.addAttribute("fullname", user.getFirstName() + " " + user.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("customerId", user.getCustomerId());
		return "requestMoney";
	}

	@RequestMapping(value = "/customer/requestMoneySuccess", method = RequestMethod.POST)
	public String processMoneyRequest(ModelMap model, HttpServletRequest request,

			@ModelAttribute("transaction") Transaction senderTransaction, BindingResult result,
			RedirectAttributes attr) {

		boolean isManager = request.isUserInRole("ROLE_MANAGER");
		boolean isTransferAccountValid;
		ExternalUser receiver = externalUserService.findByUserName();

		// get account of the sender
		Account senderAccount = accountService.getAccountByNumber(senderTransaction.getSenderAccNumber());

		List<Account> accList = null;
		Account receiverAcc = null;
		int receiverAccNumber = 0;
		accList = accountService.getAccountByCustomerId(receiver.getCustomerId());
		for (int i = 0; i < accList.size(); i++) {
			if (accList.get(i).getAccountType() == 0) {
				receiverAcc = accList.get(i);
				break;
			}
		}

		receiverAccNumber = receiverAcc.getAccountId();
		// Exit the transaction if Account doesn't exist

		int senderAccNumber = 0;

		senderAccNumber = Integer.parseInt(request.getParameter("senderAccNumber"));

		System.out.println("Account Number:" + request.getParameter("senderAccNumber"));
		System.out.println("Request through accountNum.");

		if (receiverAccNumber == Integer.parseInt(request.getParameter("senderAccNumber"))) {
			// same account transfer not allowed
			return "redirect:/customer/home";
		}

		// get the to account details

		if (receiverAcc != null) {
			isTransferAccountValid = true;
		} else {
			isTransferAccountValid = false;
		}

		System.out.println("isTransferAccountValid: " + isTransferAccountValid);
		if (isTransferAccountValid) {

			double amount = Double.parseDouble((request.getParameter("amount")));

			System.out.println("receiverAccNumber: " + receiverAccNumber);

			boolean isCritical = transactionService.isTransferCritical(amount);

			// create the transaction object
			int sender_status = 0;
			int requester_status = 1;
			String status_quo = "pending";

			// java.sql.Date date = new
			// java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
			Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

			// Date date1= new Date(Calendar.getInstance().getTime().getTime());
			senderTransaction = new Transaction(4, currentTimestamp, senderAccount.getCustomerId(),
					receiver.getCustomerId(), amount, sender_status, 0, status_quo,
					Integer.parseInt(request.getParameter("senderAccNumber")), receiverAccNumber,0,1);
			System.out.println("Sender Transaction created: " + senderTransaction);

			// Check if Debit amount is < balance in the account
			if (senderAccount.getAccountBalance() - amount <= 0) {
				System.out.println("No balance in account");
				return "redirect:/customer/home";
			}

			Transaction requesterTransaction = new Transaction(4, currentTimestamp, senderAccount.getCustomerId(),
					receiver.getCustomerId(), amount, requester_status, 1, status_quo,
					Integer.parseInt(request.getParameter("senderAccNumber")), receiverAccNumber,0,1);
			System.out.println("Receiver Transaction created: " + requesterTransaction);
			try {
				System.out.println("Trying to request funds");

				transactionService.addTransaction(senderTransaction);

				requesterTransaction.setStatus_quo("approved");
				transactionService.addTransaction(requesterTransaction);
				System.out
						.println("Transaction requested successfully. Request should show up on the user account now");
				return "redirect:/customer/home";

			} catch (Exception e) {
				System.out.println("Request unsuccessful. Please try again or contact the admin.");
				return "redirect:/customer/home";
			}
		} else {
			System.out.println("Transfer unsucessful. Please try again or contact the admin");
		}
		// redirect to the view page
		return "redirect:/customer/home";
	}

	/** show pending transactions **/
	@RequestMapping(value="/customer/requests-pending",method=RequestMethod.GET)
	public String getPendingTransactions(ModelMap model) {
		System.out.println("Fetching all pending transactions for customers");
		ExternalUser user = externalUserService.findByUserName();
		List<Transaction> transactions = transactionService.getPendingTransactions(user.getCustomerId());
		model.put("user", user);
		model.put("transactions", transactions);
		return "customerPendingTrans";
	}
	
	/** approve non-critical pending( approved) transaction **/
	@RequestMapping(value="/customer/approve-request/{id}",method=RequestMethod.GET)
	public String approveCustomerPendingTrans(@PathVariable("id") int id){
		System.out.println("Approving the pending request");
		Transaction transaction = transactionService.get(id);
		System.out.println("transaction approve id  "+transaction.getTransactionId());
		// update the respective accounts to reflect changes
		transactionService.approveTransaction(transaction);
		// redirection not working
		return "redirect:/customer/requests-pending";
	}
	
	/** approve non-critical pending( approved) transaction **/
	@RequestMapping(value="/customer/decline-request/{id}",method=RequestMethod.GET)
	public String declineCustomerPendingTrans(@PathVariable("id") int id){
		System.out.println("Declining the pending customer transactions");
		Transaction transaction = transactionService.get(id);
		transaction.setStatus(2);
		transaction.setStatus_quo("declined");
		transactionService.updateTransaction(transaction);
		// redirecting not working
		return "redirect:/customer/requests-pending";
	}
	
	
	
	
	
}