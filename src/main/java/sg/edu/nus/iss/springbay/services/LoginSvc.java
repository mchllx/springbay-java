package sg.edu.nus.iss.springbay.services;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class LoginSvc {

    private Logger logger = Logger.getLogger(LoginSvc.class.getName());

    public boolean userExist(String username, String password) {
        
        if (username.equals("admin") && (password.equals("admin"))) {
            logger.info("User exists");
            return true;
        } else if (username.equals("demo") && (password.equals("demo"))) {
            logger.info("User exists");
            return true;
        } else if (username.equals("user") && (password.equals("user"))) {
            logger.info("User exists");
            return true;            
        } else {
            logger.info("User does not exist");
            return false;
        }
    }
}
