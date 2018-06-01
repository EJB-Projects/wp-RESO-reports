package com.mycompany.mavenproject1;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateful
public class userDAO implements userDAOservic{
/*
    @PersistenceContext
    private EntityManager entityManager;

    */

    @PersistenceContext(unitName = "users")
    private EntityManager entityManager;


    @Override
    //public Test addBid(Test test) {
        public void addBid(Test test) {
        entityManager.persist(test);
        //return entityManager.merge(test);
    }
/*
    // Получаем пользователя по id
    public Test get(long id){
        return entityManager.find(Test.class, id);
    }

    // удаляем User по id
    public void delete(long id){
        entityManager.remove(get(id));
    }

*/
    // Получаем все пользователей с БД
    public List<Test> getAll(){
       List<Test> listPersons = entityManager.createQuery(
                "SELECT p FROM Test p").getResultList();
        //TypedQuery<Test> namedQuery = entityManager.createNamedQuery("Test.getAll", Test.class);
        return listPersons;
    }

}
