package com.qa.app.service;

import com.qa.app.dao.request.SignUpRequest;
import com.qa.app.dao.request.SigninRequest;
import com.qa.app.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

}
