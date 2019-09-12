package com.wzvtcsoft.sys.service.impl;

import com.wzvtcsoft.common.BeanUtil;
import com.wzvtcsoft.common.util.PageUtil;
import com.wzvtcsoft.common.vo.PageQueryParam;
import com.wzvtcsoft.sys.entity.Role;
import com.wzvtcsoft.sys.entity.RolePermission;
import com.wzvtcsoft.sys.entity.User;
import com.wzvtcsoft.sys.entity.UserRole;
import com.wzvtcsoft.sys.repository.UserRepository;
import com.wzvtcsoft.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<User> listByCondition(PageQueryParam<User> pageQueryParam) {
        // 解出参数
        User userParam = pageQueryParam.getEntityParam();
        Pageable pageable = PageUtil.initPage(pageQueryParam.getPageParam());

        // 执行多条件分页查询
        return userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<String> nameField = root.get("name");
                Path<String> mobileField = root.get("mobile");

                List<Predicate> list = new ArrayList<>();
                if (userParam != null){
                    if (!StringUtils.isEmpty(userParam.getName())){
                        list.add(criteriaBuilder.like(nameField,'%' + userParam.getName() + "%"));
                    }
                    if (!StringUtils.isEmpty(userParam.getMobile())){
                        list.add(criteriaBuilder.equal(mobileField,userParam.getMobile() ));
                    }
                }

                Predicate[] arr = new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(arr));
            }
        },pageable);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User insert(User user) {
        if (!CollectionUtils.isEmpty(user.getUserRoles())){
            for (UserRole userRole : user.getUserRoles()){
                userRole.setUser(user);
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        User originalUser = userRepository.getOne(user.getId());
        BeanUtil.copyProperties(user, originalUser);

        for (UserRole userRole : originalUser.getUserRoles()){
            if (userRole.getUser() == null){
                userRole.setUser(originalUser);
            }
        }
        return userRepository.save(originalUser);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteAll(List<User> users) {
        userRepository.deleteAll(users);
    }
}
