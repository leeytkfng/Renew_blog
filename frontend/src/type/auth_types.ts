export interface LoginRequest {
    email: string;
    password: string;
}

export interface LoginResponse {
    accessToken: string;
    refreshToken: string;
    user: UserInfo;
}

export interface UserInfo {
    id: number;
    email: string;
    name: string;
}

export interface SignupRequest {
    email: string;
    password: string;
    name: string;
    phone: string;
}

export interface SignupResponse {
    id: number;
    email: string;
    name: string;
    message: string;
}

export interface AuthToken {
    accessToken: string;
    refreshToken: string;
}

export enum AuthStatus {
    AUTHENTICATED = 'AUTHENTICATED',
    UNAUTHENTICATED = 'UNAUTHENTICATED',
    LOADING = 'LOADING'
}