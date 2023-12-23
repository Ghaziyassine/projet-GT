package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Terrain.
 */
@Entity
@Table(name = "terrain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Terrain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_terrain")
    private String nomTerrain;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "jhi_rank")
    private Long rank;

    @Column(name = "type")
    private String type;

    @Column(name = "etat")
    private String etat;

    @Column(name = "description")
    private String description;

    @Column(name = "type_sal")
    private String typeSal;

    @Column(name = "tarif")
    private String tarif;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomTerrain")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nomTerrain" }, allowSetters = true)
    private Set<Photo> photos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "nomTerrain")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "reservation", "nomTerrain" }, allowSetters = true)
    private Set<ReservationTerrain> reservationTerrains = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "terrains" }, allowSetters = true)
    private Club nomClub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "terrains", "nomVille" }, allowSetters = true)
    private Zone nomZone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Terrain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomTerrain() {
        return this.nomTerrain;
    }

    public Terrain nomTerrain(String nomTerrain) {
        this.setNomTerrain(nomTerrain);
        return this;
    }

    public void setNomTerrain(String nomTerrain) {
        this.nomTerrain = nomTerrain;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Terrain photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Terrain photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public Terrain adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getLatitude() {
        return this.latitude;
    }

    public Terrain latitude(Float latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return this.longitude;
    }

    public Terrain longitude(Float longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Long getRank() {
        return this.rank;
    }

    public Terrain rank(Long rank) {
        this.setRank(rank);
        return this;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public String getType() {
        return this.type;
    }

    public Terrain type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return this.etat;
    }

    public Terrain etat(String etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return this.description;
    }

    public Terrain description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeSal() {
        return this.typeSal;
    }

    public Terrain typeSal(String typeSal) {
        this.setTypeSal(typeSal);
        return this;
    }

    public void setTypeSal(String typeSal) {
        this.typeSal = typeSal;
    }

    public String getTarif() {
        return this.tarif;
    }

    public Terrain tarif(String tarif) {
        this.setTarif(tarif);
        return this;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public Set<Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(Set<Photo> photos) {
        if (this.photos != null) {
            this.photos.forEach(i -> i.setNomTerrain(null));
        }
        if (photos != null) {
            photos.forEach(i -> i.setNomTerrain(this));
        }
        this.photos = photos;
    }

    public Terrain photos(Set<Photo> photos) {
        this.setPhotos(photos);
        return this;
    }

    public Terrain addPhoto(Photo photo) {
        this.photos.add(photo);
        photo.setNomTerrain(this);
        return this;
    }

    public Terrain removePhoto(Photo photo) {
        this.photos.remove(photo);
        photo.setNomTerrain(null);
        return this;
    }

    public Set<ReservationTerrain> getReservationTerrains() {
        return this.reservationTerrains;
    }

    public void setReservationTerrains(Set<ReservationTerrain> reservationTerrains) {
        if (this.reservationTerrains != null) {
            this.reservationTerrains.forEach(i -> i.setNomTerrain(null));
        }
        if (reservationTerrains != null) {
            reservationTerrains.forEach(i -> i.setNomTerrain(this));
        }
        this.reservationTerrains = reservationTerrains;
    }

    public Terrain reservationTerrains(Set<ReservationTerrain> reservationTerrains) {
        this.setReservationTerrains(reservationTerrains);
        return this;
    }

    public Terrain addReservationTerrain(ReservationTerrain reservationTerrain) {
        this.reservationTerrains.add(reservationTerrain);
        reservationTerrain.setNomTerrain(this);
        return this;
    }

    public Terrain removeReservationTerrain(ReservationTerrain reservationTerrain) {
        this.reservationTerrains.remove(reservationTerrain);
        reservationTerrain.setNomTerrain(null);
        return this;
    }

    public Club getNomClub() {
        return this.nomClub;
    }

    public void setNomClub(Club club) {
        this.nomClub = club;
    }

    public Terrain nomClub(Club club) {
        this.setNomClub(club);
        return this;
    }

    public Zone getNomZone() {
        return this.nomZone;
    }

    public void setNomZone(Zone zone) {
        this.nomZone = zone;
    }

    public Terrain nomZone(Zone zone) {
        this.setNomZone(zone);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Terrain)) {
            return false;
        }
        return getId() != null && getId().equals(((Terrain) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Terrain{" +
            "id=" + getId() +
            ", nomTerrain='" + getNomTerrain() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", rank=" + getRank() +
            ", type='" + getType() + "'" +
            ", etat='" + getEtat() + "'" +
            ", description='" + getDescription() + "'" +
            ", typeSal='" + getTypeSal() + "'" +
            ", tarif='" + getTarif() + "'" +
            "}";
    }
}
