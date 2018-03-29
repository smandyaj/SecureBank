package edu.asu.sbs.utilities;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.format.DateTimeFormat;
import org.springframework.ui.ModelMap;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.security.MakeSignature;

import edu.asu.sbs.model.Account;
import edu.asu.sbs.model.ExternalUser;
import edu.asu.sbs.model.Transaction;


/**
 * Generates PDF on the fly
 */
public class MakePDF extends AbstractITextPdfView {

	private static String secretkey="";
    private static Certificate [] certificateChain;
    
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> viewModel, Document doc,PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// get data model which is passed by the Spring container
		ModelMap model = (ModelMap) viewModel.get("model");
		
		List<Transaction> transactions = (List<Transaction>) model
				.get("transactions");
		
		ExternalUser user = (ExternalUser) model.get("user");
		
		Account account = (Account) model.get("account");

		doc.add(new Paragraph("Account Statement: " + (String) model.get("statementName")));
		
		doc.add(new Paragraph(user.getFirstName() + " " + user.getLastName() ));
		
		String addressLine = "";
		if(user.getCustomerAddress() != null && !user.getCustomerAddress().isEmpty()) {
			addressLine = user.getCustomerAddress();
		}
		
		doc.add(new Paragraph("Address: "+" " + addressLine));
		doc.add(new Paragraph("Balance: " + account.getAccountBalance() ));
		doc.add(new Paragraph("Acc Number: " + account.getAccountId()));

		PdfPTable table = new PdfPTable(4);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 3.0f, 2.0f, 2.0f, 2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(2);

		// write table header
		cell.setPhrase(new Phrase("Transaction ID", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Debit/Credit", font));
		table.addCell(cell);


		cell.setPhrase(new Phrase("Transfer Amount", font));
		table.addCell(cell);
		
		Double balance = 0.0;
		for (Transaction transaction : transactions) {			
			table.addCell(transaction.getTransactionId()+"");
			table.addCell(transaction.getTransactionCreateTime()+"");

			table.addCell(transaction.getTransactionType()=='1'? "Credit" :"Debit");

			table.addCell(transaction.getTransactionAmount()+"");
		}

		doc.add(table);
		
		/*System.out.println("Trying to sign the document");
		OutputStream baosPDF = new ByteArrayOutputStream();
		byte[] bytearrayb = ((ByteArrayOutputStream)baosPDF).toByteArray();
		PdfReader reader = new PdfReader(bytearrayb);
		PdfStamper stamper = PdfStamper.createSignature(reader, baosPDF, '\0', null, true);
		///////////// Getting the key from certificate////////////

	      KeyStore ks =null;
	      BufferedReader in =new BufferedReader(new InputStreamReader(System.in));
	      secretkey= in.readLine();
	      ks =KeyStore.getInstance("pkcs12");
	      ks.load(new FileInputStream(KeyPath), secretkey
	    		  .toCharArray());
	      String alias =(String) ks.aliases().nextElement();
	      PrivateKey privateKey= (PrivateKey) ks.getKey(alias, secretkey
	    		  .toCharArray());
	      certificateChain = ks.getCertificateChain(alias);
		//////////////// making signature///////////
	      PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
	      appearance.setCryptoDictionary(privateKey,certificateChain,null,
	    		  PdfSignatureAppearance.WINCER_SIGNED);
        appearance.setReason("Security");
        appearance.setLocation("Footer");
        appearance.setVisibleSignature(new Rectangle(100, 100, 200, 200), 1, "DVA");
        if (certified) appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
        if (graphic) {
            appearance.setSignatureGraphic(Image.getInstance(RESOURCE));
            appearance.setRenderingMode(PdfSignatureAppearance.RenderingMode.GRAPHIC);
        }
    
      
        /* appearance.setSignDate(Calendar.getInstance());
        appearance.setSignatureCreator("test");
        ExternalSignature es = new PrivateKeySignature(pk, "SHA-256", "BC");
        ExternalDigest digest = new BouncyCastleDigest();*/
        /*MakeSignature.signDetached(appearance, digest, es, chain, null, null, null, 0, CryptoStandard.CMS);
        baosPDF.flush();
        baosPDF.close();

        BufferedOutputStream fs = new BufferedOutputStream(new FileOutputStream(new File("myFile121.pdf")));
        fs.write(((ByteArrayOutputStream) baosPDF).toByteArray());
        fs.flush();
        fs.close();*/
		

	}
}