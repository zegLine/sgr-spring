package com.zegline.sgrspring.controller.security;

import com.zegline.sgrspring.model.security.SGRAuthResponse;
import com.zegline.sgrspring.model.security.SGRAuthResponseType;
import com.zegline.sgrspring.model.security.SGRLoginRequest;
import com.zegline.sgrspring.service.security.SGRUserService;
import com.zegline.sgrspring.utils.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class SGRAuthController {

    @Autowired
    private SGRUserService userService;

    @Autowired
    private JWTUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<SGRAuthResponse> loginUser(@RequestBody SGRLoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new SGRAuthResponse(SGRAuthResponseType.SGR_AUTH_FAIL, ""));
        }

        UserDetails userDetails = userService.loadUserByUsername(request.username());
        String token = jwtUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new SGRAuthResponse(SGRAuthResponseType.SGR_AUTH_SUCCESS, token));
    }

}
