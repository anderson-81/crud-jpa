package emcrud.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "SeqPersonId", sequenceName = "SEQPERSONID", initialValue = 1, allocationSize = 1)
@DiscriminatorColumn(
        discriminatorType = DiscriminatorType.STRING,
        name = "TYPE_PERSON",
        columnDefinition = "CHAR(2)"
)
public abstract class Person implements Serializable {

    private static final long serialVersionUID = 472664872747126984L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;
    
    @Column(name = "NAME", nullable = false, length = 45)
    private String name;
    
    @Column(name = "EMAIL", nullable = false, unique = true, length = 45)
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
