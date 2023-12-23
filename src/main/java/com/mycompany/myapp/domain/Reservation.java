package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Reservation.
 */
@Entity
@Table(name = "reservation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Column(name = "etat")
    private String etat;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reservation")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservation", "nomTerrain" }, allowSetters = true)
    private Set<ReservationTerrain> reservationTerrains = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "packClients", "reservations" }, allowSetters = true)
    private Client nomClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Reservation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Reservation date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getEtat() {
        return this.etat;
    }

    public Reservation etat(String etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Set<ReservationTerrain> getReservationTerrains() {
        return this.reservationTerrains;
    }

    public void setReservationTerrains(Set<ReservationTerrain> reservationTerrains) {
        if (this.reservationTerrains != null) {
            this.reservationTerrains.forEach(i -> i.setReservation(null));
        }
        if (reservationTerrains != null) {
            reservationTerrains.forEach(i -> i.setReservation(this));
        }
        this.reservationTerrains = reservationTerrains;
    }

    public Reservation reservationTerrains(Set<ReservationTerrain> reservationTerrains) {
        this.setReservationTerrains(reservationTerrains);
        return this;
    }

    public Reservation addReservationTerrain(ReservationTerrain reservationTerrain) {
        this.reservationTerrains.add(reservationTerrain);
        reservationTerrain.setReservation(this);
        return this;
    }

    public Reservation removeReservationTerrain(ReservationTerrain reservationTerrain) {
        this.reservationTerrains.remove(reservationTerrain);
        reservationTerrain.setReservation(null);
        return this;
    }

    public Client getNomClient() {
        return this.nomClient;
    }

    public void setNomClient(Client client) {
        this.nomClient = client;
    }

    public Reservation nomClient(Client client) {
        this.setNomClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reservation)) {
            return false;
        }
        return getId() != null && getId().equals(((Reservation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reservation{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", etat='" + getEtat() + "'" +
            "}";
    }
}
