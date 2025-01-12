package com.chatsul.domain;

import com.chatsul.domain.common.BaseEntity;
import com.chatsul.domain.enums.Role;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "provider", nullable = true, length = 10)
    private String provider;

    @Column(name = "provider_id", nullable = true, length = 50)
    private String providerId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    public void encodePassword(String password) {
        this.password = password;
    }
}
