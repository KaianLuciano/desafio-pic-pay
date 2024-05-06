package com.picpaychallenge.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("User Controller")
@RequestMapping("${picpay.challenge.base.path}/${picpay.challenge.base.version}/clinic-units")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
}
