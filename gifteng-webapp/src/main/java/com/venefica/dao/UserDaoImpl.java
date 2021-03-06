package com.venefica.dao;

import com.venefica.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link UserDao}.
 *
 * @author Sviatoslav Grebenchukov
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserDaoImpl extends DaoBase<User> implements UserDao {

    @Override
    public User get(Long id) {
        return getEntity(id);
    }
    
    @Override
    public User getEager(Long userId) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("id", userId));
        criteria.setFetchMode("userData", FetchMode.JOIN); //EAGER
        criteria.setFetchMode("userPoint", FetchMode.JOIN); //EAGER
        return (User) criteria.uniqueResult();
    }
    
    @Override
    public List<User> getAll() {
        List<User> users = createQuery(""
                + "from " + getDomainClassName() + " u "
                + "")
                .list();
        return users;
    }
    
    @Override
    public List<User> getAdminUsers() {
        List<User> users = createQuery(""
                + "select u "
                + "from " + getDomainClassName() + " u where "
                + "u.admin = true and "
                + "u.deleted = false"
                + "")
                .list();
        return users;
    }
    
    @Override
    public List<User> getTopUsers(int numberUsers) {
        List<User> users = createQuery(""
                + "select u "
                + "from " + getDomainClassName() + " u where "
                + "u.deleted = false "
                + "order by u.userPoint.givingNumber desc "
                + "")
                .setMaxResults(numberUsers)
                .list();
        return users;
    }
    
    @Override
    public List<User> getFollowers(Long userId) {
        List<User> followers = createQuery(""
                + "select u.followers "
                + "from " + getDomainClassName() + " u "
                + "where "
                + "u.id = :userId and "
                + "u.deleted = false"
                + "")
                .setParameter("userId", userId)
                .list();
        
        List<User> validFollowers = User.getValidUsers(followers);
        return validFollowers;
    }

    @Override
    public User findUserByName(String name) {
        List<User> users = createQuery(""
                + "from " + getDomainClassName() + " u where "
                + "u.name = :name and "
                + "u.deleted = false"
                + "")
                .setParameter("name", name)
                .list();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findUserByEmail(String email) {
        List<User> users = createQuery(""
                + "from " + getDomainClassName() + " u where "
                + "u.email = :email and "
                + "u.deleted = false"
                + "")
                .setParameter("email", email)
                .list();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findUserByPhoneNumber(String phoneNumber) {
        List<User> users = createQuery(""
                + "from " + getDomainClassName() + " u where "
                + "u.userData.phoneNumber = :phoneNumber and "
                + "u.deleted = false"
                + "")
                .setParameter("phoneNumber", phoneNumber)
                .list();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public Long save(User user) {
        user.setJoinedAt(new Date());
        return saveEntity(user);
    }

    @Override
    public void update(User user) {
        updateEntity(user);
    }

    @Override
    public boolean removeByName(String name) {
        int numUserDeleted = createQuery(""
                + "delete from " + getDomainClassName() + " u where "
                + "u.name = :name"
                + "")
                .setParameter("name", name)
                .executeUpdate();
        return numUserDeleted != 0;
    }

    @Override
    public boolean removeByEmail(String email) {
        int numUserDeleted = createQuery(""
                + "delete from " + getDomainClassName() + " u where "
                + "u.email = :email"
                + "")
                .setParameter("email", email)
                .executeUpdate();
        return numUserDeleted != 0;
    }

    @Override
    public Long getMaxUserId() {
        Long maxId = (Long) createQuery(""
                + "select max(u.id) from " + getDomainClassName() + " u"
                + "").uniqueResult();
        return maxId != null ? maxId : 0;
    }
}
