package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class BoardMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {

        logic();
    }

    static void logic() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Board board = new Board();
        System.out.println("board.getId() tx before before = " + board.getId());
        em.persist(board);
        System.out.println("board.getId() tx before = " + board.getId());
        tx.commit();
        System.out.println("board.getId() tx after = " + board.getId());
    }
}
