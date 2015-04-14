package org.example.appdirect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ACCESS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Access implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "account_identifier")
    private String accountIdentifier;

    @NotNull
    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "email")
    private String email;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "name")
    private String name;

    public Long getId() {

        return id;
    }

    public void setId(final Long id) {

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

    public void setEventType(final String eventType) {

        this.eventType = eventType;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(final String email) {

        this.email = email;
    }

    public String getOpenId() {

        return openId;
    }

    public void setOpenId(final String openId) {

        this.openId = openId;
    }

    public String getName() {

        return name;
    }

    public void setName(final String name) {

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

        Access subscription = (Access) o;

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
            ", email='" + email + "'" +
            ", openId='" + openId + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
