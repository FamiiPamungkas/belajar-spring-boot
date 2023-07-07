/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2023-07-07 14:17:03.

export interface BaseEntity extends Serializable {
    id: number;
    active: boolean;
    createAt: Date;
    updatedAt: Date;
}

export interface Menu extends BaseEntity {
    authority: string;
    name: string;
    description: string;
    link: string;
    group: string;
    seq: number;
    showOnNav: boolean;
    icon: string;
    parent: Menu;
    children: Menu[];
    authorities: string[];
}

export interface MenuBuilder {
}

export interface Role extends BaseEntity {
    name: string;
    authority: string;
    description: string;
    menus: Menu[];
}

export interface RoleBuilder {
}

export interface User extends BaseEntity, UserDetails {
    firstname: string;
    lastname: string;
    birthdate: Date;
    email: string;
    roles: Role[];
    treeMenus: Menu[];
}

export interface UserBuilder {
}

export interface Serializable {
}

export interface GrantedAuthority extends Serializable {
    authority: string;
}

export interface UserDetails extends Serializable {
    enabled: boolean;
    password: string;
    username: string;
    accountNonExpired: boolean;
    credentialsNonExpired: boolean;
    authorities: GrantedAuthority[];
    accountNonLocked: boolean;
}
