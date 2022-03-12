package com.barber.serviceuser.entities.VO;

import javax.validation.constraints.NotBlank;

public class UserVO {

    private Long id;
    @NotBlank(message = "Campo obrigátorio!")
    private String firstName;
    @NotBlank(message = "Campo obrigátorio!")
    private String lastName;
    @NotBlank(message = "Campo obrigátorio!")
    private String email;
    @NotBlank(message = "Campo obrigátorio!")
    private String password;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
