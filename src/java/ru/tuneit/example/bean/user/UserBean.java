package ru.tuneit.example.bean.user;

import ru.tuneit.example.model.User;
import com.icesoft.faces.component.ext.RowSelectorEvent;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.Highlight;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import ru.tuneit.example.utils.faces.FacesUtils;


@ManagedBean (name = "userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger _log = Logger.getLogger(UserBean.class.getName());
    
    private List<User> users;
    
    private UserForm userForm = new UserForm();
    
    @ManagedProperty("190103, г. Санкт-Петербург, 10-я Красноармейская ул., д.22 литера А")
    private String address;
    
    private Effect effect = new Highlight("#C4C4C4");
    
    
    public UserBean() {
    }

    @PostConstruct
    public void initUsers() {
        users = new ArrayList<User>() {{
            add(new User("Иван", "Иванов", 11L, "Санкт-Петербург, Измайловский пр., д2"));
            add(new User("Петр", "Петров", 19L, "Санкт-Петербург, Рижский пр., д3"));
            add(new User("Сергей", "Сергеев", 21L, "Санкт-Петербург, Средний пр., д4"));
            add(new User("Пух", "Винни", 77L, "Санкт-Петербург, Вознесенский пр., д2"));
            add(new User("Пята", "Чок", 74L, "Санкт-Петербург, Ленинский пр., д2"));
        }};
    }
    
    public void selectUser(RowSelectorEvent event) {
        try {
            User userSelected = (User)FacesUtils.getVariable("user");
            for (User u : users) {
                if (!u.equals(userSelected)) {
                    u.setSelected(false);
                }
            }
            address = userSelected.getAddress();
            FacesUtils.refresh();

        } catch (Exception e) {
            _log.error(e, e);
        }
    }
    
    public void save(ActionEvent event) {
        users.add(new User(userForm));
        userForm.reset();
        effect.setFired(false);
    }
    
    public void cancel(ActionEvent event) {
        userForm.reset();
    }
    
    public int getUsersSize() {
        return users != null ? users.size() : 0;
    }

    public UserForm getUserForm() {
        return userForm;
    }

    public void setUserForm(UserForm userForm) {
        this.userForm = userForm;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
    
}
