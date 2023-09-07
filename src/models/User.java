package models;


public class User {
    private String name;
    private String pinCode;
    private String phoneNumber;
    private UserType type;

    public User(String name, String pinCode, String phoneNumber, UserType type) {
        this.name = name;
        this.pinCode = pinCode;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getUserRole(){
        if(this.type == UserType.MANAGER){
            return "Мениджър";
        }else {
            return "Сервитьор";
        }
    }
}
