package emcrud.controllers;

import emcrud.connections.ConnectionSession;
import emcrud.models.Gender;
import emcrud.models.PhysicalPerson;
import java.sql.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PhysicalPersonController {

    public static int InsertPhysicalPerson(String name, String email, Float salary, Date birthday, Gender gender) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                em.getTransaction().begin();
                PhysicalPerson pp = new PhysicalPerson();
                pp.setName(name);
                pp.setEmail(email);
                pp.setSalary(salary);
                pp.setBirthday(birthday);
                pp.setGender(gender);
                em.persist(pp);
                em.getTransaction().commit();
                em.close();
                return 1;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return -1;
    }

    public static int EditPhysicalPerson(int id, String name, String email, Float salary, Date birthday, Gender gender) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                PhysicalPerson pp = em.find(PhysicalPerson.class, id);
                if (pp != null) {
                    em.getTransaction().begin();
                    pp.setName(name);
                    pp.setEmail(email);
                    pp.setSalary(salary);
                    pp.setBirthday(birthday);
                    pp.setGender(gender);
                    em.persist(pp);
                    em.getTransaction().commit();
                    em.close();
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }

    public static int DeletePhysicalPerson(int id) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                PhysicalPerson pp = em.find(PhysicalPerson.class, id);
                if (pp != null) {
                    em.getTransaction().begin();
                    em.remove(pp);
                    em.getTransaction().commit();
                    em.close();
                    return 1;
                } else {
                    return 0;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                return -1;
            }
        }
        return -1;
    }

    public static PhysicalPerson GetPhysicalPersonByID(Integer id) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                PhysicalPerson pp = em.find(PhysicalPerson.class, id);
                if (pp != null) {
                    em.close();
                    return pp;
                } else {
                    return null;
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return null;
        }
        return null;
    }

    public static List<PhysicalPerson> GetPhysicalPersonByName(String name) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                /*
                Query query = em.createNamedQuery("PhysicalPerson.GetPhysicalPersonByName");
                query.setParameter("name", name + '%');
                List<PhysicalPerson> list = query.getResultList();
                 */
                String hql = "from PhysicalPerson p where p.name LIKE :name  ORDER BY p.name ASC";
                List list = em.createQuery(hql).setParameter("name", name + '%').getResultList();
                em.close();
                return list;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                em.close();
                return null;
            }
        }
        return null;
    }

    public static boolean CheckEmailRegistered(String email) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                String hql = "from PhysicalPerson p where p.email = :email";
                List list = em.createQuery(hql).setParameter("email", email).getResultList();
                em.close();
                return list.size() > 0;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                em.close();
                return true;
            }
        }
        return true;
    }

    public static List<PhysicalPerson> GetPersonByBetweenBirthday(Date birthday1, Date birthday2) {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                String hql = "from PhysicalPerson p where p.birthday BETWEEN :birthday1 AND :birthday2 ORDER BY p.birthday ASC";
                List<PhysicalPerson> list = em.createQuery(hql).setParameter("birthday1", birthday1).setParameter("birthday2", birthday2).getResultList();
                em.close();
                return list;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                em.close();
            }
        }
        return null;
    }

    //Get Statistics Of Physical Person:
    public static long CountPhysicalPerson() {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                String sql = "SELECT COUNT(p.id) FROM Person p";
                Query query = em.createQuery(sql);
                long count = (long) query.getSingleResult();
                em.close();
                return count;
            } catch (Exception e) {
                em.close();
                System.err.println(e);
            }
            em.close();
        }
        return -1;
    }

    public static PhysicalPerson GetYoungerPhysicalPerson() {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                String sql = "SELECT pp FROM PhysicalPerson pp WHERE pp.birthday = (SELECT MAX(pp.birthday) FROM PhysicalPerson pp)";
                Query query = em.createQuery(sql);
                PhysicalPerson pp = (PhysicalPerson) query.getSingleResult();
                return pp;
            } catch (Exception e) {
                System.err.println(e);
            }
            em.close();
        }
        return null;
    }

    public static PhysicalPerson GetOlderPhysicalPerson() {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                String sql = "SELECT pp FROM PhysicalPerson pp WHERE pp.birthday = (SELECT MIN(pp.birthday) FROM PhysicalPerson pp)";
                Query query = em.createQuery(sql);
                PhysicalPerson pp = (PhysicalPerson) query.getSingleResult();
                return pp;
            } catch (Exception e) {
                System.err.println(e);
            }
            em.close();
        }
        return null;
    }

    public static Double GetAVGPhysicalPersonSalary() {
        EntityManager em = ConnectionSession.GetEntityManager();
        if (em != null) {
            try {
                String sql = "SELECT AVG(pp.salary) FROM PhysicalPerson pp";
                Query query = em.createQuery(sql);
                Double avg_salary = (Double) query.getSingleResult();
                return avg_salary;
            } catch (Exception e) {
                System.err.println(e);
            }
            em.close();
        }
        return null;
    }

}
