package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Zone.
 */
@Entity
@Table(name = "zone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Zone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_zone")
    private String nomZone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomZone")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "photos", "reservationTerrains", "nomClub", "nomZone" }, allowSetters = true)
    private Set<Terrain> terrains = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "zones" }, allowSetters = true)
    private Ville nomVille;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Zone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomZone() {
        return this.nomZone;
    }

    public Zone nomZone(String nomZone) {
        this.setNomZone(nomZone);
        return this;
    }

    public void setNomZone(String nomZone) {
        this.nomZone = nomZone;
    }

    public Set<Terrain> getTerrains() {
        return this.terrains;
    }

    public void setTerrains(Set<Terrain> terrains) {
        if (this.terrains != null) {
            this.terrains.forEach(i -> i.setNomZone(null));
        }
        if (terrains != null) {
            terrains.forEach(i -> i.setNomZone(this));
        }
        this.terrains = terrains;
    }

    public Zone terrains(Set<Terrain> terrains) {
        this.setTerrains(terrains);
        return this;
    }

    public Zone addTerrain(Terrain terrain) {
        this.terrains.add(terrain);
        terrain.setNomZone(this);
        return this;
    }

    public Zone removeTerrain(Terrain terrain) {
        this.terrains.remove(terrain);
        terrain.setNomZone(null);
        return this;
    }

    public Ville getNomVille() {
        return this.nomVille;
    }

    public void setNomVille(Ville ville) {
        this.nomVille = ville;
    }

    public Zone nomVille(Ville ville) {
        this.setNomVille(ville);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Zone)) {
            return false;
        }
        return getId() != null && getId().equals(((Zone) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Zone{" +
            "id=" + getId() +
            ", nomZone='" + getNomZone() + "'" +
            "}";
    }
}
