package com.tribble.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * This class is used to store user translations.
 */
@Entity(name = "translation")
@Table(name = "translation")
public class Translation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "translationId", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "source_lang_id")
    private Language sourceLang;

    @OneToOne
    @JoinColumn(name = "target_lang_id")
    private Language targetLang;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "target", nullable = false)
    private String target;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Language getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(Language sourceLang) {
        this.sourceLang = sourceLang;
    }

    public Language getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(Language targetLang) {
        this.targetLang = targetLang;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Translation that = (Translation) o;

        if (getSourceLang() != null ? !getSourceLang().equals(that.getSourceLang()) : that.getSourceLang() != null)
            return false;
        if (getTargetLang() != null ? !getTargetLang().equals(that.getTargetLang()) : that.getTargetLang() != null)
            return false;
        if (!getSource().equals(that.getSource())) return false;
        if (!getTarget().equals(that.getTarget())) return false;
        return getUser().equals(that.getUser());

    }

    @Override
    public int hashCode() {
        int result = getSourceLang() != null ? getSourceLang().hashCode() : 0;
        result = 31 * result + (getTargetLang() != null ? getTargetLang().hashCode() : 0);
        result = 31 * result + getSource().hashCode();
        result = 31 * result + getTarget().hashCode();
        result = 31 * result + getUser().hashCode();
        return result;
    }
}
