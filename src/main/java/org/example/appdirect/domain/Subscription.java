package org.example.appdirect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SUBSCRIPTION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Subscription implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "account_identifier")
    private String accountIdentifier;

    @NotNull
    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "edition")
    private String edition;

    @Column(name = "name")
    private String name;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getAccountIdentifier() {

        return accountIdentifier;
    }

    public void setAccountIdentifier(final String accountIdentifier) {

        this.accountIdentifier = accountIdentifier;
    }

    public String getEventType() {

        return eventType;
    }

    public void setEventType(String eventType) {

        this.eventType = eventType;
    }

    public String getEdition() {

        return edition;
    }

    public void setEdition(String edition) {

        this.edition = edition;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subscription subscription = (Subscription) o;

        if (!Objects.equals(id, subscription.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {

        return Objects.hashCode(id);
    }

    @Override
    public String toString() {

        return "Subscription{" +
            "id=" + id +
            ", accountIdentifier='" + accountIdentifier + "'" +
            ", eventType='" + eventType + "'" +
            ", edition='" + edition + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
