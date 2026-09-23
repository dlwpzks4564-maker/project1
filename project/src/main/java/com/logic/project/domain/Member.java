package com.logic.project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private MemberRole role = MemberRole.USER;

    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.ACTIVE;

    private boolean termsAgreed;
    private boolean privacyAgreed;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
}
