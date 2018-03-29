package edu.asu.sbs.services;

import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import edu.asu.sbs.model.Email;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private ResourceLoader resouceLoader;
	
	@Override
	public void sendEmail(Email email) {
		
		try{
			final String senderEmailID = "cse545g7@gmail.com";
			final String senderPassword = "QAZ?@963";
			final String emailSMTPserver = "smtp.gmail.com";
			final String emailServerPort = "587";
			
            System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", emailSMTPserver); //SMTP Host
            props.put("mail.smtp.port", emailServerPort); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

                //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderEmailID, senderPassword);
                }
            };
            Session session = Session.getInstance(props, auth);

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmailID));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getEmailId()));

            System.out.println("Mail Check 2");

            message.setSubject(email.getSubject());
            message.setText(email.getBody());
            
/*            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "test.pdf";
            String fileName = "testFile";
            //ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Resource fileResource = resouceLoader.getResource("classpath:test.pdf");
            DataSource source = new FileDataSource(fileResource.getFile());
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);*/


            System.out.println("Mail Check 3");

            Transport.send(message);
            System.out.println("Mail Sent");
        }catch(Exception ex){
            System.out.println("Mail fail");
            System.out.println(ex);
        }
	}

}