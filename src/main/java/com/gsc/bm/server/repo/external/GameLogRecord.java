package com.gsc.bm.server.repo.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOG001_GAMES")

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameLogRecord {

    @Id
    @Column(name = "C_GAME_ID")
    private String gameId;

    @Column(name = "T_PLAYERS")
    private String players;

    @Column(name = "T_DATE")
    private String date;

    @Column(name = "T_FULL_LOG")
    private String fullLog;

}
