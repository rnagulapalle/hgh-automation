package com.lazerycode.selenium.util;

import com.sun.mail.imap.IMAPFolder;
import org.apache.commons.lang.StringUtils;
import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class MailHelper {

    private static final String EMAIL_ADDRESS = "happygrasshopperauto@gmail.com";
    private static final String PASSWORD = "Grass785";
    private static final int MAX_RECENT_MESSAGES_TO_SEARCH = 100;
    private static final String INBOX_FOLDER = "INBOX";

    private static final String TEST_EMAIL_SUBJECT = "Happy Grasshopper- Your Account has been created";

    private String to;

    public MailHelper(String to) {
        this.to = to;
    }

    public String getPassword() throws MessagingException, IOException {
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flag flag = null;
        List<Message> messages = null;
        try
        {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com",EMAIL_ADDRESS, PASSWORD);

            folder = (IMAPFolder) store.getFolder(INBOX_FOLDER); //This works for both email account


            if(!folder.isOpen())
                folder.open(Folder.READ_WRITE);
            messages = Arrays.asList(folder.getMessages());

            messages = messages.stream()
                    .limit(MAX_RECENT_MESSAGES_TO_SEARCH)
                    .filter(this::isTestEmail)
                    .collect(Collectors.toList());

            System.out.println("No of Messages : " + folder.getMessageCount());
            System.out.println("No of Unread Messages : " + folder.getUnreadMessageCount());
            System.out.println(messages.size());
            for (int i=0; i < messages.size();i++) {

                System.out.println("*****************************************************************************");
                System.out.println("MESSAGE " + (i + 1) + ":");
                Message msg =  messages.get(i);
                //System.out.println(msg.getMessageNumber());
                //Object String;
                //System.out.println(folder.getUID(msg)

                subject = msg.getSubject();

                System.out.println("Subject: " + subject);
                System.out.println("From: " + msg.getFrom()[0]);
                System.out.println("To: "+msg.getAllRecipients()[0]);
                System.out.println("Date: "+msg.getReceivedDate());
                System.out.println("Size: "+msg.getSize());
                System.out.println(msg.getFlags());
                System.out.println("Body: \n"+ msg.getContent());
                System.out.println(msg.getContentType());
                String body = (String) msg.getContent();
                return parseMessage(body);
            }
        }
        finally
        {
            if (folder != null && folder.isOpen()) { folder.close(true); }
            if (store != null) { store.close(); }
        }
        return "";
    }

    private boolean isTestEmail(Message message) {
        try {
            return message.getSubject().equals(TEST_EMAIL_SUBJECT) && this.to.equals(message.getAllRecipients()[0].toString());
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String parseMessage(String body) throws IOException, MessagingException {
        return StringUtils.substringBetween(body, "Temporary Password (you'll choose a new one when you log in):</span><br>", "<br><br>").trim();
    }
}
