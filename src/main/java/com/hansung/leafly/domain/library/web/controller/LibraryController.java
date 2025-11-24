package com.hansung.leafly.domain.library.web.controller;

import com.hansung.leafly.domain.library.service.LibraryService;
import com.hansung.leafly.domain.library.web.dto.LibraryReq;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libraries")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @PostMapping("/{isbn}")
    public ResponseEntity<SuccessResponse<?>> createLibrary(
            @AuthenticationPrincipal CustomMemberDetails memberDetails,
            @PathVariable String isbn,
            @RequestBody @Valid LibraryReq req
    ) {
        libraryService.createLibrary(memberDetails.getMember(), isbn, req);

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(null));
    }
}
