package com.hansung.leafly.domain.library.web.dto;

import com.hansung.leafly.domain.bookreview.web.dto.ReviewReq;
import com.hansung.leafly.domain.library.entity.enums.LibraryStatus;

public class LibraryReqBuilder {

    private final ReviewReq req;

    public LibraryReqBuilder(ReviewReq req) {
        this.req = req;
    }

    public LibraryReq buildDoneReq() {
        LibraryReq libraryReq = new LibraryReq();
        libraryReq.setTitle(req.getTitle());
        libraryReq.setAuthor(req.getAuthor());
        libraryReq.setCover(req.getThumbnail());
        libraryReq.setStatus(LibraryStatus.DONE);
        return libraryReq;
    }
}