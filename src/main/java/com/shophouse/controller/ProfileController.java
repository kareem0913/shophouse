package com.shophouse.controller;

import com.shophouse.model.dto.user.UserProfileResponse;
import com.shophouse.security.UserPrincipal;
import com.shophouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public UserProfileResponse httpGetCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        return userService.getCurrentUserProfile(currentUser);
    }

}