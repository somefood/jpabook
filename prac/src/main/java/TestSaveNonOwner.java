import model.Member;
import model.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class TestSaveNonOwner {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Member member1 = new Member("member1", "회원1");
        entityManager.persist(member1);

        Member member2 = new Member("member2", "회원2");
        entityManager.persist(member2);

        Team team = new Team("team1", "팀1");

        team.getMembers().add(member1);
        team.getMembers().add(member2);

        entityManager.persist(team);

        transaction.commit();
    }
}
