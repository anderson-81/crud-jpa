package hibernatecrud.controllers;

import hibernatecrud.connections.ConnectionSession;
import hibernatecrud.models.Gender;
import hibernatecrud.models.PhysicalPerson;
import java.sql.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class PhysicalPersonController {

    public static int InsertPhysicalPerson(String name, String email, Float salary, Date birthday, Gender gender) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                session.beginTransaction().begin();
                PhysicalPerson pp = new PhysicalPerson();
                pp.setName(name);
                pp.setEmail(email);
                pp.setSalary(salary);
                pp.setBirthday(birthday);
                pp.setGender(gender);
                session.save(pp);
                session.beginTransaction().commit();
                session.close();
                return 1;
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
            }
        }
        return -1;
    }

    public static int EditPhysicalPerson(int id, String name, String email, Float salary, Date birthday, Gender gender) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                PhysicalPerson pp = (PhysicalPerson) session.load(PhysicalPerson.class, id);
                if (pp != null) {
                    session.beginTransaction().begin();
                    pp.setName(name);
                    pp.setEmail(email);
                    pp.setSalary(salary);
                    pp.setBirthday(birthday);
                    pp.setGender(gender);
                    session.save(pp);
                    session.beginTransaction().commit();
                    session.close();
                    return 1;
                } else {
                    return 0;
                }
            } catch (HibernateException e) {
                return -1;
            }
        }
        return -1;
    }

    public static int DeletePhysicalPerson(int id) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                PhysicalPerson pp = (PhysicalPerson) session.load(PhysicalPerson.class, id);
                if (pp != null) {
                    session.beginTransaction().begin();
                    session.delete(pp);
                    session.beginTransaction().commit();
                    session.close();
                    return 1;
                } else {
                    return 0;
                }
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
                return -1;
            }
        }
        return -1;
    }

    public static PhysicalPerson GetPhysicalPersonByID(Integer id) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String sql = "from PhysicalPerson where id = ?";
                Query query = session.createQuery(sql);
                query.setParameter(0, id);
                PhysicalPerson pp = (PhysicalPerson) query.uniqueResult();
                return pp;
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
            }
            return null;
        }
        return null;
    }

    public static List<PhysicalPerson> GetPhysicalPersonByName(String name) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String hql = "from PhysicalPerson p where p.name LIKE :name  ORDER BY p.name ASC";
                List<PhysicalPerson> list = session.createQuery(hql).setParameter("name", name + '%').list();
                session.close();
                return list;
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
                session.close();
                return null;
            }
        }
        return null;
    }

    public static boolean CheckEmailRegistered(String email) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String hql = "from PhysicalPerson p where p.email = :email";
                List<PhysicalPerson> list = session.createQuery(hql).setParameter("email", email).list();
                session.close();
                return list.size() > 0;
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
                session.close();
                return true;
            }
        }
        return true;
    }

    public static List<PhysicalPerson> GetPersonByBetweenBirthday(Date birthday1, Date birthday2) {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String hql = "from PhysicalPerson p where p.birthday BETWEEN :birthday1 AND :birthday2 ORDER BY p.birthday ASC";
                List<PhysicalPerson> list = session.createQuery(hql).setParameter("birthday1", birthday1).setParameter("birthday2", birthday2).list();
                session.close();
                return list;
            } catch (HibernateException e) {
                System.err.println(e.getMessage());
                session.close();
            }
        }
        return null;
    }

    //Get Statistics Of Physical Person:
    public static long CountPhysicalPerson() {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                Query query = session.createQuery("SELECT COUNT(id) FROM Person");
                long count_person = (long) query.uniqueResult();
                return count_person;
            } catch (HibernateException e) {
                System.err.println(e);
            }
            session.close();
        }
        return -1;
    }

    public static PhysicalPerson GetYoungerPhysicalPerson() {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String sql = "SELECT pp FROM PhysicalPerson pp WHERE pp.birthday = (SELECT MAX(pp.birthday) FROM PhysicalPerson pp)";
                Query query = session.createQuery(sql);
                PhysicalPerson pp = (PhysicalPerson) query.uniqueResult();
                return pp;
            } catch (HibernateException e) {
                System.err.println(e);
            }
            session.close();
        }
        return null;
    }

    public static PhysicalPerson GetOlderPhysicalPerson() {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String sql = "SELECT pp FROM PhysicalPerson pp WHERE pp.birthday = (SELECT MIN(pp.birthday) FROM PhysicalPerson pp)";
                Query query = session.createQuery(sql);
                PhysicalPerson pp = (PhysicalPerson) query.uniqueResult();
                return pp;
            } catch (HibernateException e) {
                System.err.println(e);
            }
            session.close();
        }
        return null;
    }

    public static Double GetAVGPhysicalPersonSalary() {
        Session session = ConnectionSession.OpenSession();
        if (session != null) {
            try {
                String sql = "SELECT AVG(pp.salary) FROM PhysicalPerson pp";
                Query query = session.createQuery(sql);
                Double avg_salary = (Double) query.uniqueResult();
                return avg_salary;
            } catch (HibernateException e) {
                System.err.println(e);
            }
            session.close();
        }
        return null;
    }
}
