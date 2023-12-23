package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pack.
 */
@Entity
@Table(name = "pack")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_pack")
    private String nomPack;

    @Column(name = "tarif")
    private String tarif;

    @Column(name = "nbr_de_matches")
    private Long nbrDeMatches;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomPack")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomClient", "nomPack" }, allowSetters = true)
    private Set<PackClient> packClients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pack id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomPack() {
        return this.nomPack;
    }

    public Pack nomPack(String nomPack) {
        this.setNomPack(nomPack);
        return this;
    }

    public void setNomPack(String nomPack) {
        this.nomPack = nomPack;
    }

    public String getTarif() {
        return this.tarif;
    }

    public Pack tarif(String tarif) {
        this.setTarif(tarif);
        return this;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public Long getNbrDeMatches() {
        return this.nbrDeMatches;
    }

    public Pack nbrDeMatches(Long nbrDeMatches) {
        this.setNbrDeMatches(nbrDeMatches);
        return this;
    }

    public void setNbrDeMatches(Long nbrDeMatches) {
        this.nbrDeMatches = nbrDeMatches;
    }

    public Set<PackClient> getPackClients() {
        return this.packClients;
    }

    public void setPackClients(Set<PackClient> packClients) {
        if (this.packClients != null) {
            this.packClients.forEach(i -> i.setNomPack(null));
        }
        if (packClients != null) {
            packClients.forEach(i -> i.setNomPack(this));
        }
        this.packClients = packClients;
    }

    public Pack packClients(Set<PackClient> packClients) {
        this.setPackClients(packClients);
        return this;
    }

    public Pack addPackClient(PackClient packClient) {
        this.packClients.add(packClient);
        packClient.setNomPack(this);
        return this;
    }

    public Pack removePackClient(PackClient packClient) {
        this.packClients.remove(packClient);
        packClient.setNomPack(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pack)) {
            return false;
        }
        return getId() != null && getId().equals(((Pack) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pack{" +
            "id=" + getId() +
            ", nomPack='" + getNomPack() + "'" +
            ", tarif='" + getTarif() + "'" +
            ", nbrDeMatches=" + getNbrDeMatches() +
            "}";
    }
}
