package com.hansung.leafly.domain.bookmark.service;

import com.hansung.leafly.domain.bookmark.entity.Bookmark;
import com.hansung.leafly.domain.bookmark.exception.BookmarkBadRequest;
import com.hansung.leafly.domain.bookmark.repository.BookmarkRepository;
import com.hansung.leafly.domain.bookmark.web.dto.BookmarkReq;
import com.hansung.leafly.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Override
    @Transactional
    public boolean toggle(Member member, Long isbn, BookmarkReq req) {
        Optional<Bookmark> found =
                bookmarkRepository.findByMember_IdAndIsbn(member.getId(), isbn);

        // 이미 북마크 되어있으면 삭제 → OFF
        if (found.isPresent()) {
            bookmarkRepository.delete(found.get());
            return false;
        }

        // 북마크 등록 → ON
        if (req == null) {
            throw new BookmarkBadRequest();
        }

        Bookmark bookmark = Bookmark.of(member, isbn, req);
        bookmarkRepository.save(bookmark);
        return true;
    }
}
