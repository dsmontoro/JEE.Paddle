package business.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Court {

    @Id
    private int id;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            Court other = (Court) obj;
            return id == other.id;
        }
    }

    @Override
    public String toString() {
        return "Court [id=" + id + ", active=" + active + "]";
    }

}
