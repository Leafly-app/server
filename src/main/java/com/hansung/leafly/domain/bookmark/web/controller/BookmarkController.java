package com.hansung.leafly.domain.bookmark.web.controller;

import com.hansung.leafly.domain.bookmark.service.BookmarkService;
import com.hansung.leafly.domain.bookmark.web.dto.BookmarkReq;
import com.hansung.leafly.domain.bookmark.web.dto.BookmarkToggleRes;
import com.hansung.leafly.global.auth.security.CustomMemberDetails;
import com.hansung.leafly.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final BookmarkService bookmarkService;

    @PostMapping("/{isbn}")
    public ResponseEntity<SuccessResponse<BookmarkToggleRes>> toggleBookmark(
            @AuthenticationPrincipal CustomMemberDetails memberDetails,
            @PathVariable String isbn,
            @RequestBody(required = false) @Valid BookmarkReq req
    ) {
        Long isbnLong = Long.valueOf(isbn);
        boolean isOn = bookmarkService.toggle(memberDetails.getMember(), isbnLong, req);
        BookmarkToggleRes res = new BookmarkToggleRes(isOn ? "on" : "off");

        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(res));
    }
}
