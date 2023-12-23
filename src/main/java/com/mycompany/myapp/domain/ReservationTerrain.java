package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReservationTerrain.
 */
@Entity
@Table(name = "reservation_terrain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReservationTerrain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "heure")
    private Integer heure;

    @Column(name = "date")
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "reservationTerrains", "nomClient" }, allowSetters = true)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "photos", "reservationTerrains", "nomClub", "nomZone" }, allowSetters = true)
    private Terrain nomTerrain;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReservationTerrain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getHeure() {
        return this.heure;
    }

    public ReservationTerrain heure(Integer heure) {
        this.setHeure(heure);
        return this;
    }

    public void setHeure(Integer heure) {
        this.heure = heure;
    }

    public Instant getDate() {
        return this.date;
    }

    public ReservationTerrain date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ReservationTerrain reservation(Reservation reservation) {
        this.setReservation(reservation);
        return this;
    }

    public Terrain getNomTerrain() {
        return this.nomTerrain;
    }

    public void setNomTerrain(Terrain terrain) {
        this.nomTerrain = terrain;
    }

    public ReservationTerrain nomTerrain(Terrain terrain) {
        this.setNomTerrain(terrain);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReservationTerrain)) {
            return false;
        }
        return getId() != null && getId().equals(((ReservationTerrain) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReservationTerrain{" +
            "id=" + getId() +
            ", heure=" + getHeure() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
