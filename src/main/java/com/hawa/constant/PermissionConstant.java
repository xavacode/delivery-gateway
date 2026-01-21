package com.hawa.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum PermissionConstant {
    USER_CREATE("user:create", "Create New User", Category.USER),
    USER_EDIT("user:edit", "Edit User Details", Category.USER),
    USER_DELETE("user:delete", "Delete User", Category.USER),
    USER_VIEW("user:view", "View User Details", Category.USER),
    RESTAURANT_CREATE("restaurant:create", "Create New Restaurant", Category.RESTAURANT),
    RESTAURANT_EDIT("restaurant:edit", "Edit Restaurant Details", Category.RESTAURANT),
    RESTAURANT_DELETE("restaurant:delete", "Delete Restaurant", Category.RESTAURANT),
    RESTAURANT_VIEW("restaurant:view", "View Restaurant Details", Category.RESTAURANT),
    ROLE_CREATE("role:create", "Create New Role", Category.ROLE),
    ROLE_EDIT("role:edit", "Edit Role Details", Category.ROLE),
    ROLE_DELETE("role:delete", "Delete Role", Category.ROLE),
    ROLE_VIEW("role:view", "View Role Details", Category.ROLE);

    @Getter
    private final String name;
    @Getter
    private final String description;
    @Getter
    private final Category category;


    public static PermissionConstant getPermission(String name) {
        for (PermissionConstant permission : PermissionConstant.values()) {
            if (permission.getName().equals(name)) {
                return permission;
            }
        }
        return null;
    }

    public static Map<Category, List<PermissionConstant>> getPermissionsByCategory() {
        return Arrays.stream(values())
                .collect(Collectors.groupingBy(PermissionConstant::getCategory));
    }

    // Get all permissions for a specific category
    public static List<PermissionConstant> getByCategory(Category category) {
        return Arrays.stream(values())
                .filter(permission -> permission.getCategory() == category)
                .collect(Collectors.toList());
    }

    public static List<PermissionConstant> getRestaurantOwnerPermissions(){
        return Arrays.asList(
                PermissionConstant.RESTAURANT_EDIT,
                PermissionConstant.RESTAURANT_VIEW,
                PermissionConstant.USER_CREATE,
                PermissionConstant.USER_DELETE,
                PermissionConstant.USER_EDIT,
                PermissionConstant.USER_VIEW,
                PermissionConstant.ROLE_CREATE,
                PermissionConstant.ROLE_DELETE,
                PermissionConstant.ROLE_EDIT,
                PermissionConstant.ROLE_VIEW
        );
    }

    @RequiredArgsConstructor
    public enum Category {
        USER("User Management", "Manage system users"),
        RESTAURANT("Restaurant Management", "Manage restaurants and basic info"),
        ROLE("Role Management", "Manage Roles");
//        ORDER("Order Management", "Process and manage orders"),
//        ANALYTICS("Analytics", "View reports and statistics"),
//        SETTINGS("System Settings", "Configure system settings");

        @Getter
        private final String name;
        @Getter
        private final String description;


    }


}


