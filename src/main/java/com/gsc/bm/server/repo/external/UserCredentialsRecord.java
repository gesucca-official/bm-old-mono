package com.gsc.bm.server.repo.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USR001_CREDENTIALS")

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserCredentialsRecord {

    @Id
    @Column(name = "C_USERNAME")
    private String username;

    @Column(name = "T_EMAIL")
    private String email;

    @Column(name = "T_SALTED_HASH")
    private String saltedHash;
}
