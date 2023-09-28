package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ExamMergeMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        Member member = createMember("memberA", "회원1");

        member.setUsername("회원명 변경");

        mergeMember(member);
    }

    private static Member createMember(String id, String username) {
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();

        tx1.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        em1.persist(member);
        tx1.commit();

        em1.close(); // 준영속 상태 만들기

        return member;
    }

    private static void mergeMember(Member member) {
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        member = em2.merge(member);
        tx2.commit();

        // 준영속 상태
        System.out.println("member.getUsername() = " + member.getUsername());

        // 영속 상태
//        System.out.println("mergedMember.getUsername() = " + mergedMember.getUsername());

        System.out.println("em2 contains member = " + em2.contains(member));
//        System.out.println("em2 contains mergedMember = " + em2.contains(mergedMember));

        em2.close();
    }
}
