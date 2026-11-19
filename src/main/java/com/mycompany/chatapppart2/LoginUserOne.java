
package com.mycompany.chatapppart2;

public class LoginUserOne {

    private RegistrationClassOne registeredUser;
    private boolean loginStatus = false;

    public LoginUserOne(RegistrationClassOne registeredUser) {
        this.registeredUser = registeredUser;
    }

    public boolean loginUser(String username, String password) {

        loginStatus = registeredUser != null
                && registeredUser.getUsername() != null
                && registeredUser.getPassword() != null
                && registeredUser.getUsername().equals(username)
                && registeredUser.getPassword().equals(password);

        return loginStatus;
    }

    public String returnLoginStatus() {
        if (loginStatus) {
            return "Welcome " + registeredUser.getFirstName()
                    + " " + registeredUser.getLastName()
                    + ", it is great to see you again.";
        }

        return "Username or password incorrect, please try again.";
    }
}
