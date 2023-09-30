import model.Member;
import model.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        tx.begin();

        Team team = new Team("team1", "팀1");
        em.persist(team);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team);
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team);
        em.persist(member2);

        tx.commit();

        queryLogicJoin(em);

        updateRelation(em);

        deleteRelation(em);
    }

    private static void queryLogicJoin(EntityManager entityManager) {
        String jpql = "SELECT m FROM Member m JOIN m.team t WHERE " +
                "t.name=:teamName";

        List<Member> resultList = entityManager.createQuery(jpql, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for (Member member : resultList) {
            System.out.println("[query] member.username = " +
                    member.getUsername());
        }
    }

    private static void updateRelation(EntityManager entityManager) {
        System.out.println("===========릴레이선 변경===========");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Team team = new Team("team2", "팀2");
        entityManager.persist(team);

        Member member = entityManager.find(Member.class, "member1");
        member.setTeam(team);

        transaction.commit();
    }

    private static void deleteRelation(EntityManager entityManager) {
        System.out.println("===========릴레이선 삭제===========");

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Member member = entityManager.find(Member.class, "member1");
        member.setTeam(null);

        transaction.commit();
    }
}
