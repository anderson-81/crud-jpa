package emcrud.models;

import emcrud.models.Gender;
import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-03-17T03:21:25")
@StaticMetamodel(PhysicalPerson.class)
public class PhysicalPerson_ extends Person_ {

    public static volatile SingularAttribute<PhysicalPerson, Date> birthday;
    public static volatile SingularAttribute<PhysicalPerson, Gender> gender;
    public static volatile SingularAttribute<PhysicalPerson, Float> salary;

}