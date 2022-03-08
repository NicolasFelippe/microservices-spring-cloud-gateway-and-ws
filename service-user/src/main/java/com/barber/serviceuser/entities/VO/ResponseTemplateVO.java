package com.barber.serviceuser.entities.VO;

import com.barber.serviceuser.entities.User;

public class ResponseTemplateVO {

    private User user;

    public ResponseTemplateVO(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
