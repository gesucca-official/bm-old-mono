package com.gsc.bm.server.repo.internal;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CRD003_CHARACTERS")

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class CharactersGuiRecord implements Serializable {

    @Id
    @Column(name = "C_REF_CLASS")
    private String clazz;

    @Column(name = "T_GUI_NAME")
    private String guiName;

    @Column(name = "T_GUI_SPRITE")
    private String guiImage;
}
