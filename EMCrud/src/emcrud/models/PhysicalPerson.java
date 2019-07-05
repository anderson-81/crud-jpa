package emcrud.models;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity
@Table(name = "PHYSICALPERSON")
@PrimaryKeyJoinColumn(name = "PERSON_ID")
@DiscriminatorValue("PP")

@NamedQuery(name = "PhysicalPerson.GetPhysicalPersonByName",
        query = "Select p, pp FROM Person p, PhysicalPerson pp WHERE p.id = pp.id AND p.name LIKE :name")
public class PhysicalPerson extends Person implements Serializable {

    private static final long serialVersionUID = -4617261075337139875L;

    @Column(name = "SALARY", precision = 12, scale = 2, nullable = false)
    private Float salary;

    @Column(name = "BIRTHDAY", nullable = false)
    //@Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "GENDER", nullable = false, length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "PhysicalPerson{" + "id=" + this.getId() + ", name=" + this.getName() + ", salary=" + salary + ", birthday=" + sdf.format(birthday) + ", gender=" + gender + '}';
    }
}
