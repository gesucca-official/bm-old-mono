package com.gsc.bm.server.repo.internal;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "DCK002_STARTERS")

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class StarterDeckBasicCardsRecord {

    @Id
    @Column(name = "C_PG_REF_CLASS")
    private String pgClazz;

    @Column(name = "T_BASIC_REF_CLASS")
    private String basicClazz;

    @Column(name = "T_CH_BOUND_1_REF_CLASS")
    private String chBoundClazz1;

    @Column(name = "T_CH_BOUND_2_REF_CLASS")
    private String chBoundClazz2;

    @Column(name = "T_LAST_RESORT_REF_CLASS")
    private String lastResortClazz;
}
