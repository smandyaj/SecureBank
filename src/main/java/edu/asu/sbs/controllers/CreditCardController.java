package edu.asu.sbs.controllers;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.Email;
import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.Otp;
import edu.asu.sbs.model.Transaction;
import edu.asu.sbs.services.AccountService;
import edu.asu.sbs.services.BCryptHashService;
import edu.asu.sbs.services.EmailService;
import edu.asu.sbs.services.ExternalUserService;
import edu.asu.sbs.services.OTPService;
import edu.asu.sbs.services.TransactionService;

@Controller
public class CreditCardController {
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ExternalUserService externalUserService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	private BCryptHashService hashService;
	
	
	
	/**************************External Users********************************/

	
	@RequestMapping(value="/customer/credit-home",method = RequestMethod.GET)
	public ModelAndView getCreditCardsSummary() {
		ModelAndView modelAndView = new ModelAndView("credit-home");
		ExternalUser customer = externalUserService.findByUserName();
		List<Account> accounts = this.accountService.getAccountByAccountType(customer.getCustomerId(), 2);
		modelAndView.addObject("accounts",accounts);
		return modelAndView;
	}
	
	@RequestMapping(value="/customer/creditcard/{accountId}",method = RequestMethod.GET)
	public ModelAndView getCreditCardInfo(@PathVariable("accountId") Integer accountId) {
		ModelAndView modelAndView = new ModelAndView("credit-info");
		Account account = this.accountService.getAccountByAccountId(accountId);
		if (account.getAccountType()!=2){
				System.out.println("This is not a credit card!");
				return modelAndView;
		}
		
		double latefee=0.0;
		int accountDue = account.getAccountDue();
		int today = Calendar.getInstance().get(Calendar.DATE);
		if(today > accountDue)
			latefee = 35.0;
		
		double interest = account.getAccountBalance()*0.01;
		
		List<Transaction> transactions = this.transactionService.getOrderedTransactionsByPayerOrReceiverId(accountId);
		
		modelAndView.addObject("account",account);
		modelAndView.addObject("latefee",latefee);
		modelAndView.addObject("interest",interest);
		modelAndView.addObject("transactions",transactions);
					
		return modelAndView;
	}
	
	@RequestMapping(value="/customer/creditcard/{accountId}/payment",method= RequestMethod.GET)
	public ModelAndView getPaymentInfo(@PathVariable("accountId") Integer accountId) {
		ModelAndView modelAndView = new ModelAndView("credit-payment");
		modelAndView.addObject("accountId",accountId);
		modelAndView.addObject("paymentForm",new Transaction());
		return modelAndView;
	}

	
	@RequestMapping(value="/customer/creditcard/{accountId}/make-payment",method = RequestMethod.POST)
	public String payCreditCard(@PathVariable("accountId") Integer accountId,@ModelAttribute("transaction") Transaction transaction, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView("credit-payment");
		ExternalUser customer = externalUserService.findByUserName();
		Account account = this.accountService.getAccountByNumber(transaction.getSenderAccNumber());
		if (account==null || account.getCustomerId()!= customer.getCustomerId() || account.getAccountType()==2) {
			System.out.println("Account information is wrong!");
			modelAndView.addObject("msg","Account information is wrong!");
			return "redirect:/customer/home";
		}
		if (account.getAccountBalance()<transaction.getTransactionAmount()) {
			System.out.println("Payment information is wrong!");
			modelAndView.addObject("msg","Payment information is wrong!");
			return "redirect:/customer/home";
		}
		
      System.out.println("$$$$$$$Credit card payment in process$$$$$$$$$$");
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
		
		Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
		Email email = new Email(customer.getEmailId(), "SBS OTP For Transaction", "This OTP will expire in 2 min : " + otp);
		emailService.sendEmail(email);
		transaction.setStatus(4);
		transaction.setStatus_quo("otp");
		transaction.setAuth(1);
		transaction.setPayerId(customer.getCustomerId());
		transaction.setReceiverId(customer.getCustomerId());
		transaction.setTransactionCreateTime(currentTimestamp);
		
		int transactionId = transactionService.addTransaction(transaction);
		Otp otpObj = new Otp(hashService.getBCryptHash(otp), currentTimestamp,customer.getCustomerId(), transactionId, "creditdebit");
		int otpId = otpService.addOTP(otpObj);
		
		
		String content = "You have made a new request to for Credit / Debit "
				+ "The payment request will expire in the next 10 minutes from now.\n\n"
				+ "Please use the following OTP to accept the payment: " + "\n\n"
				+ "You can accept the payment or cancel it.";

		model.addAttribute("heading", "Bill payment");
		model.addAttribute("title", "Verify Transaction For Credit");
		model.addAttribute("transactionId", transactionId);
		model.addAttribute("type", "credit");
		model.addAttribute("otpId", otpId); 
		
		
		
		List<Account> accounts = accountService.getAccountByCustomerId(customer.getCustomerId());
		model.addAttribute("fullname", customer.getFirstName() + " " + customer.getLastName());
		model.addAttribute("accounts", accounts);
		model.addAttribute("title", "Welcome " + customer.getFirstName());
		
		return "CreditCardOTP";
		
	}
	
	
	@RequestMapping(value = "/customer/creditcard/process-otp", method = RequestMethod.POST)
	public String processCreditCardOTP(ModelMap model, HttpServletRequest request,RedirectAttributes attr) {
		System.out.println("process OTP");
		String otp = request.getParameter("otp");
		String otpId = request.getParameter("otpId");
		//String originalOtp = otpService.getOTP(transactionId);
		String transactionId = request.getParameter("transactionId");
		String type = request.getParameter("type");
		String redirectUrl = "redirect:/CreditCardOTP";
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
			return "redirect:/customer/customerHome";
		}
		
		System.out.println("OTP matched and updating the transactions");
		// get the transaction using the id and change the status to pending
		Transaction transaction = transactionService.getTransactionById(Integer.parseInt(transactionId));
		transaction.setStatus(0);
		transaction.setStatus_quo("pending");
		transactionService.updateTransaction(transaction);
		return "redirect:/customer/home";
	}


		
      /*
      
    
      
      Transaction t = new Transaction(3, currentTimestamp, customer.getCustomerId(), customer.getCustomerId(),
    		  transaction.getTransactionAmount(), 0, 1, "pending" , transaction.getSenderAccNumber(),
    		  accountId,0, 0);
        

        this.transactionService.addTransaction(t);
		System.out.println("Payment schedule succeed! Waiting for approvement!");
		modelAndView.addObject("msg","Payment schedule succeed! Waiting for approvement!");
		return "redirect:/customer/home";
	*/
	
	

}
