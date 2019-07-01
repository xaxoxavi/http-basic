package com.esliceu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class HttpBasicServlet extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req,res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Get Authorization header
        String auth = req.getHeader("Authorization");

        // Do we allow that user?
        if (!allowUser(auth)) {
            // Not allowed, so report he's unauthorized
            res.setHeader("WWW-Authenticate", "BASIC realm=\"realm test\"");
            res.sendError(res.SC_UNAUTHORIZED);
            // Could offer to add him to the allowed user list
        } else {
            // Allowed, so show him the secret stuff
            out.println("Top-secret stuff");
        }
    }
    // This method checks the user information sent in the Authorization
    // header against the database of users maintained in the users Hashtable.
    protected boolean allowUser(String auth) throws IOException {

        if (auth == null) {
            System.out.println("No Auth");
            return false;
        }
        if (!auth.toUpperCase().startsWith("BASIC ")) {
            System.out.println("Only Accept Basic Auth");
            return false;
        }

        // Get encoded user and password, comes after "BASIC "
        String userpassEncoded = auth.substring(6);
        // Decode it, using any base 64 decoder
        sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
        String userpassDecoded = new String(dec.decodeBuffer(userpassEncoded));

        String account[] = userpassDecoded.split(":");
        System.out.println("User = "+account[0]);
        System.out.println("Pass = "+account[1]);

        // Check our user list to see if that user and password are "allowed"
        // Use here Mock Object

        return true;

    }
}
