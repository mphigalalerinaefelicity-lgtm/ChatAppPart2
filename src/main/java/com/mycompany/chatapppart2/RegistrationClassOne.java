package com.mycompany.chatapppart2;

public class RegistrationClassOne {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphoneNumber;

    public String registerUser(String firstName, String lastName,
            String username, String password, String cellphoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;

        if (!checkUserName()) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User registered successfully.";
    }

    public boolean checkUserName() {
        return username != null && username.length() <= 5
                && username.contains("_");
    }

    public boolean checkPasswordComplexity() {
        if (password == null) return false;

        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }

    public boolean checkCellPhoneNumber() {
        return cellphoneNumber != null
                && cellphoneNumber.matches("\\+27[678][0-9]{8}");
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getCellphoneNumber() {
        return cellphoneNumber;
    }
}
