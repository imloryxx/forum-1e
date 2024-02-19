import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@WebServlet("/submitForm")
public class FormSubmitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String problem = request.getParameter("problem");
        String materia = request.getParameter("materia");
        String prof = request.getParameter("prof");

        // Creazione del messaggio email
        String subject = "Nuova richiesta dal forum";
        String body = "Categoria: " + category + "\n";
        body += "Problema: " + problem + "\n";
        if (category.equals("compiti")) {
            body += "Materia: " + materia + "\n";
        } else if (category.equals("insegnamento")) {
            body += "Prof: " + prof + "\n";
        }

        // Invio dell'email
        String to = "lorytognati88@gmail.com";
        String from = "lorytognati88@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tuoindirizzoemail@gmail.com", "tuapassword");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": true}");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"success\": false}");
        }
    }
}

