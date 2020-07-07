package land.llama.birthday.domain;

import java.time.LocalDate;

public class Citizen {

    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String email;

    private Citizen(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthday = builder.birthday;
        this.email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("Citizen: %s %s born on %s - %s", firstName, lastName, birthday, email);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private LocalDate birthday;
        private String email;

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public Citizen build() {
            return new Citizen(this);
        }
    }

}
