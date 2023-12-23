package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ville.
 */
@Entity
@Table(name = "ville")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ville implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_ville")
    private String nomVille;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomVille")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "terrains", "nomVille" }, allowSetters = true)
    private Set<Zone> zones = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ville id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomVille() {
        return this.nomVille;
    }

    public Ville nomVille(String nomVille) {
        this.setNomVille(nomVille);
        return this;
    }

    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    public Set<Zone> getZones() {
        return this.zones;
    }

    public void setZones(Set<Zone> zones) {
        if (this.zones != null) {
            this.zones.forEach(i -> i.setNomVille(null));
        }
        if (zones != null) {
            zones.forEach(i -> i.setNomVille(this));
        }
        this.zones = zones;
    }

    public Ville zones(Set<Zone> zones) {
        this.setZones(zones);
        return this;
    }

    public Ville addZone(Zone zone) {
        this.zones.add(zone);
        zone.setNomVille(this);
        return this;
    }

    public Ville removeZone(Zone zone) {
        this.zones.remove(zone);
        zone.setNomVille(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ville)) {
            return false;
        }
        return getId() != null && getId().equals(((Ville) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ville{" +
            "id=" + getId() +
            ", nomVille='" + getNomVille() + "'" +
            "}";
    }
}
