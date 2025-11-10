export enum UserRole {
    USER = 'USER',
    ADMIN = 'ADMIN'
}

export interface USER {
    id: string,
    email: string,
    name: string,
    role:UserRole,
    createdAt:string;
}

export interface UserCreateRequest {
    email: string;
    password: string;
    name: string;
}

export interface UserUpdatedRequest {
    name?:string;
    password?: string;
}

export interface UserResponse {
    id: string;
    email: string;
    name: string;
    role: UserRole;
    createdAt: string;
}

