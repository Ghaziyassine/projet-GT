package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Club.
 */
@Entity
@Table(name = "club")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Club implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_club")
    private String nomClub;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomClub")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "photos", "reservationTerrains", "nomClub", "nomZone" }, allowSetters = true)
    private Set<Terrain> terrains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Club id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClub() {
        return this.nomClub;
    }

    public Club nomClub(String nomClub) {
        this.setNomClub(nomClub);
        return this;
    }

    public void setNomClub(String nomClub) {
        this.nomClub = nomClub;
    }

    public Set<Terrain> getTerrains() {
        return this.terrains;
    }

    public void setTerrains(Set<Terrain> terrains) {
        if (this.terrains != null) {
            this.terrains.forEach(i -> i.setNomClub(null));
        }
        if (terrains != null) {
            terrains.forEach(i -> i.setNomClub(this));
        }
        this.terrains = terrains;
    }

    public Club terrains(Set<Terrain> terrains) {
        this.setTerrains(terrains);
        return this;
    }

    public Club addTerrain(Terrain terrain) {
        this.terrains.add(terrain);
        terrain.setNomClub(this);
        return this;
    }

    public Club removeTerrain(Terrain terrain) {
        this.terrains.remove(terrain);
        terrain.setNomClub(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Club)) {
            return false;
        }
        return getId() != null && getId().equals(((Club) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Club{" +
            "id=" + getId() +
            ", nomClub='" + getNomClub() + "'" +
            "}";
    }
}
