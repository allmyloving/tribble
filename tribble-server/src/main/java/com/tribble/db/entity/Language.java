package com.tribble.db.entity;

import javax.persistence.*;

@Entity(name = "language")
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "code", unique = true, nullable = false, length = 2)
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        return getCode().equals(language.getCode());

    }

    @Override
    public int hashCode() {
        int result = getCode().hashCode();
        return 31 * result;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", code='" + code + '\'' +
                '}';
    }
}
