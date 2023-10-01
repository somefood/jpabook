package model;

import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;


class MemberTest {

    EntityManagerFactory emf;

    @BeforeEach
    void clearEMF() {
        emf = Persistence.createEntityManagerFactory("jpabook");
    }

    @Test
    @DisplayName("순수한객체 양방향 테스트")
    void t1() {
        Team team = new Team("team1", "팀1");
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        member1.setTeam(team);
        member2.setTeam(team);

        List<Member> members = team.getMembers();

        Assertions.assertEquals(2, members.size());
    }

    @Test
    @DisplayName("ORM 양방향")
    void t2() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Team team = new Team("team1", "팀1");
        entityManager.persist(team);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team);
        entityManager.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team);
        entityManager.persist(member2);

        transaction.commit();

        List<Member> members = team.getMembers();
        Assertions.assertEquals(2, members.size());
    }

    @Test
    @DisplayName("ORM 양방향 편의 메서드 개선")
    void t3() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        Team team = new Team("team1", "팀1");
        entityManager.persist(team);

        Team team2 = new Team("team2", "팀2");
        entityManager.persist(team2);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team);
        entityManager.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team);
        entityManager.persist(member2);

        member1.setTeam(team2);

        transaction.commit();

        List<Member> members = team.getMembers();
        Assertions.assertEquals(1, members.size());
    }
}