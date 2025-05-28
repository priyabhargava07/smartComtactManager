package com.scm.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entity.Contact;
import com.scm.entity.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(String userId);

    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.userId = :userId AND c.createdAt >= :startOfDay")
    List<Contact> findNewContactsToday(@Param("userId") String userId, @Param("startOfDay") Date startOfDay);

    // Get contacts updated today
    @Query("SELECT c FROM Contact c WHERE c.user.userId = :userId AND c.updatedAt >= :startOfDay")
    List<Contact> findRecentlyUpdatedToday(@Param("userId") String userId, @Param("startOfDay") Date startOfDay);
}
