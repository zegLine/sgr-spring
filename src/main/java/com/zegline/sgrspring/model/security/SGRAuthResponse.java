package com.zegline.sgrspring.model.security;

public record SGRAuthResponse (SGRAuthResponseType responseType, String tokenOptional) {
}
