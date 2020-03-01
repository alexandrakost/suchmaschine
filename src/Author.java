
public class Author {

    private String firstName;
    public String lastName;
    private Date birthday;
    private String residence;
    private String email;

    public Author (String theFirstName, String theLastName, Date theBirthday, String theResidence,
                   String theEmail){
        this.firstName = theFirstName;
        this.lastName = theLastName;
        this.birthday = theBirthday;
        this.residence = theResidence;
        this.email = theEmail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName (){
        return this.firstName;
    }

    public String getLastName (){
        return this.lastName;
    }


    public Date getBirthday (){ return this.birthday; }

    public String getResidence (){
        return this.residence;
    }

    public String getEmail (){
        return this.email;
    }

    public String getContactInformation (){
        return this.lastName + "," +  this.firstName + Terminal.NEWLINE + this.email
                + Terminal.NEWLINE +this.residence;
    }

    public int getAge (Date today){
        return this.birthday.getAgeInYears(today);
    }

    public boolean equals (Author author){
        if (author == null) {
            return false;
        }
        if (this.firstName.equals(author.firstName) && this.lastName.equals(author.lastName)&&
                this.birthday.equals(author.birthday) && this.residence.equals(author.residence) &&
                this.email.equals(author.email)){
            return true;
        }
        return false;
    }

    public String toString() {
        return "Author: " + this.lastName + ", " + this.firstName + "; born on " + this.birthday + ".";
    }
}

