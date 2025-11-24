package com.hansung.leafly.domain.library.service;

import com.hansung.leafly.domain.library.entity.Library;
import com.hansung.leafly.domain.library.exception.LibraryAlreadyExistsException;
import com.hansung.leafly.domain.library.repository.LibraryRepository;
import com.hansung.leafly.domain.library.web.dto.LibraryReq;
import com.hansung.leafly.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;

    @Override
    @Transactional
    public void createLibrary(Member member, String isbn, LibraryReq req) {
        if (libraryRepository.existsByMemberAndIsbn(member, isbn)) {
            throw new LibraryAlreadyExistsException();
        }

        Library library = Library.of(member, isbn, req);
        libraryRepository.save(library);
    }
}
